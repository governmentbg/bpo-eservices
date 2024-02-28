/*******************************************************************************
 * * $Id:: FlowScopeInterceptor.java 109645 2013-03-25 10:50:36Z ionitd#$
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.interceptors;

import eu.ohim.sp.common.ui.flow.FlowBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.webflow.context.ExternalContextHolder;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.execution.FlowExecution;
import org.springframework.webflow.execution.repository.FlowExecutionRepository;
import org.springframework.webflow.execution.repository.FlowExecutionRestorationFailureException;
import org.springframework.webflow.execution.repository.NoSuchFlowExecutionException;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.executor.FlowExecutorImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Interceptor that allows for loading request scope bean to the flow
 * bean
 * 
 * @author soriama, ckar
 * @since 20.05.2012
 * @see org.springframework.webflow.executor.FlowExecutorImpl
 */
public class FlowScopeInterceptor extends HandlerInterceptorAdapter {

    private final Logger log = Logger.getLogger(FlowScopeInterceptor.class);

    public static final String DEFAULT_PARAM_NAME = "flowKey";

    public static final String DEFAULT_BEAN_NAME = "flowBean";

    private String paramName = DEFAULT_PARAM_NAME;

    private String beanName = DEFAULT_BEAN_NAME;

    @Autowired
    private FlowExecutor flowExecutor;

    /**
     * Set the name of the parameter that contains a locale specification
     * in a locale change request. Default is "locale".
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * Return the name of the parameter that contains a locale specification
     * in a locale change request.
     */
    public String getParamName() {
        return this.paramName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    /**
     * Extreme look should be done during stress testing because it is based on static thread
     * Replaces the request scope bean with the flow scope
     * 
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException {

        // Set a header in the response that will make IE to work without compatibility view
        // This is used because setting this as a META tag in tilesLayout.jsp doesn't fix the problem
        response.addHeader("X-UA-Compatible", "IE=9,chrome=1");

        // Get the flowScope bean and inject as request Bean to the Controller
        String flowKey = request.getParameter(this.paramName);
        if(flowKey == null)
        {
            flowKey = request.getParameter("execution");
        }
        if (flowKey != null) {
            try {
                ExternalContextHolder.setExternalContext(new ServletExternalContext(null, request, null));
                FlowExecution flowExecution = getFlowExecution(flowKey, request);
                FlowBean flowBean = (FlowBean) getFromFlowScope(this.beanName, flowExecution);
                request.setAttribute("scopedTarget." + this.beanName, flowBean);
                // Create Flow State Details
                FlowScopeDetails flowScopeDetails = new FlowScopeDetails();
                flowScopeDetails.setFlowModeId(flowExecution.getActiveSession().getDefinition().getId());
                flowScopeDetails.setStateId(flowExecution.getActiveSession().getState().getId());
                request.setAttribute("scopedTarget.flowScopeDetails", flowScopeDetails);
                request.setAttribute("flowModeId", flowExecution.getActiveSession().getDefinition().getId());
            } catch (NoSuchFlowExecutionException nsfee) {
                try {
                    response.sendRedirect(request.getRequestURI());
                } catch (IOException e) {
                    log.warn(e);
                }
                return false;
            } catch (FlowExecutionRestorationFailureException ferfe)
            {
                try {
                    response.sendRedirect(request.getRequestURI());
                } catch (IOException e) {
                    log.warn(e);
                }
                return false;
            } finally {
                ExternalContextHolder.setExternalContext(null);
            }
        }
        return true;
    }

    /**
     * Extreme look should be done during stress testing because it is based on static thread
     * Replaces the flow scope bean with the requests
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        // Put the flowScope bean back to the web flow
        String flowKey = request.getParameter(this.paramName);
        if (flowKey != null) {
            try {
                ExternalContextHolder.setExternalContext(new ServletExternalContext(null, request, null));
                FlowBean flowBean = (FlowBean) request.getAttribute("scopedTarget." + this.beanName);
                putToFlowScope(flowBean, this.beanName, getFlowExecution(flowKey, request));
            } finally {
                ExternalContextHolder.setExternalContext(null);
            }
        }
    }

    private FlowExecution getFlowExecution(String flowKey, HttpServletRequest request) {
        FlowExecutionRepository executionRepository = ((FlowExecutorImpl) flowExecutor).getExecutionRepository();
        FlowExecution flowExecution = executionRepository.getFlowExecution(executionRepository
                .parseFlowExecutionKey(flowKey));
        return flowExecution;
    }

    private Object getFromFlowScope(String paramName, FlowExecution flowExecution) {
        return flowExecution.getActiveSession().getScope().get(paramName);
    }

    private void putToFlowScope(Object object, String paramName, FlowExecution flowExecution) {
        flowExecution.getActiveSession().getScope().put(paramName, object);
        ((FlowExecutorImpl) flowExecutor).getExecutionRepository().putFlowExecution(flowExecution);
    }
}
