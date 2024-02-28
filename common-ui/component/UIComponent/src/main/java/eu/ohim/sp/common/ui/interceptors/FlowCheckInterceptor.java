package eu.ohim.sp.common.ui.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.webflow.context.ExternalContextHolder;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.execution.FlowExecution;
import org.springframework.webflow.execution.FlowExecutionKey;
import org.springframework.webflow.execution.repository.FlowExecutionAccessException;
import org.springframework.webflow.execution.repository.FlowExecutionLock;
import org.springframework.webflow.execution.repository.FlowExecutionRepository;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.executor.FlowExecutorImpl;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Checks if the flowKey corresponds to the flowId in the url.
 * To avoid that the user changes the flowId reusing an old flowKey. This will load all the flowScope data into the current flow.
 * 
 * @author rubiojo
 *
 */
public class FlowCheckInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
    private FlowHandlerMapping flowHandlerMapping;
	
    @Autowired
    private FlowExecutor flowExecutor;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	          throws ServletException {

		return securityCheck(request);
	}
	 
    private boolean securityCheck(HttpServletRequest request){
    	String requestFlowId = flowHandlerMapping.getFlowUrlHandler().getFlowId(request);
    	
    	String flowKey = flowHandlerMapping.getFlowUrlHandler().getFlowExecutionKey(request);
    	if(flowKey != null){
    		FlowExecution flowExecution = getFlowExecution(flowKey, request);
	    	String currentFlowId = flowExecution.getDefinition().getId();
	    	
	    	if(!requestFlowId.equals(currentFlowId)){ //compare the two flowIds
	    		throw new FlowExecutionAccessException(flowExecution.getKey(), "Flowkey doesn`t match with the current flow"){};
	    	}
    	}
    	return true;
    }
    
    private FlowExecution getFlowExecution(String flowKey, HttpServletRequest request) {
    	ExternalContextHolder.setExternalContext(new ServletExternalContext(null, request, null));
        FlowExecutionRepository executionRepository = ((FlowExecutorImpl) flowExecutor).getExecutionRepository();
        
        FlowExecutionKey key = executionRepository.parseFlowExecutionKey(flowKey);
        FlowExecutionLock lock = executionRepository.getLock(key);
		lock.lock();
		try {
			return  executionRepository.getFlowExecution(key);
		} finally {
			lock.unlock();
			ExternalContextHolder.setExternalContext(null);
		}
    }


}
