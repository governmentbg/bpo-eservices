/*******************************************************************************
 * * $Id::                                                                   $
 * * . * .
 * * * RRRR * Copyright Â© 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ptefiling.ui.controller.payment;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.domain.patent.PatentApplication;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentResult;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentStatus;
import eu.ohim.sp.core.epayment.EPaymentService;
import eu.ohim.sp.ptefiling.ui.adapter.PTFlowBeanFactory;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Controller that manages the callback from the PO payment web interface
 * It calls to a service in the PO specific adapter that will return an object with the response information
 * Depending on the response it shows the result in the browser
 * 
 * @author soriama
 * 
 */
@Controller
public class PTPaymentCallbackController {

    /** Logger for this class and subclasses */
    private static final Logger LOGGER = Logger.getLogger(PTPaymentCallbackController.class);

    /**
     * CORE PaymentServiceInterface
     */
    @Autowired
    private EPaymentService paymentService;
    
    @Autowired
    private PTFlowBean flowBean;

    @Autowired
    private PTFlowBeanFactory flowBeanFactory;
    
    @Value("${sp.office}")
    private String office;

	@Value("${sp.module}")
    private String module;
	
    /**
     * This method is executed when the PO payment web interface returns the callback
     * It calls to a service in the PO specific adapter that will return an object with the response information
     * Depending on the response it shows the result in the browser
     * 
     * @param request HttpServletRequest
     * @param applicationFilingNumber string with applicationFilingNumber
     * @return a modelAndView containing the response message
     * 
     * @throws SPException if the PaymentResponse object returned by the service is null
     */
    @RequestMapping(value = "paymentCallback", method = RequestMethod.POST)
    public ModelAndView paymentCallback(HttpServletRequest request,
            @RequestParam(required = true, value = "applicationFilingNumber") String applicationFilingNumber) {
        // Retrieve the parameters contained in the request object and send them to the Adapter
        Map<String, String> paymentPlatformData = new HashMap<String, String>();
        for (String key : request.getParameterMap().keySet()) {
            paymentPlatformData.put(key, request.getParameter(key));
        }
        
        PatentApplication application = flowBeanFactory.convertTo(flowBean);
        application.setApplicationFilingNumber(applicationFilingNumber);
        PaymentResult paymentResult = paymentService.notifyPaymentResult(office, module, application, paymentPlatformData);
        if (paymentResult == null) {
            throw new SPException("Fail adapting the Payment Response", null, "error.payment.adaptResponse.fail");
        }
        
        flowBean.getPaymentForm().setPaymentResult(paymentResult);
 
        // Depending on the result returned by the specific adapter we render the correspondant view
        ModelAndView mv = new ModelAndView("payment/paymentCallbackRedirect");
        mv.addObject("resultStatus", paymentResult.getPaymentStatus().name());
        mv.addObject("message", StringEscapeUtils.escapeJavaScript(paymentResult.getResultMessage()));
        
        if (LOGGER.isInfoEnabled()) {
        	LOGGER.info("End of the Payment operation");
        }
        
        return mv;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Throwable e) {
    	LOGGER.error("Fail on RedirectPaymentController", e);
        ModelAndView mv = new ModelAndView("payment/paymentCallbackRedirect");
        mv.addObject("resultStatus", PaymentStatus.PAYMENT_ERROR.name());
        mv.addObject("message", StringEscapeUtils.escapeJavaScript(e.getMessage()));
        mv.addObject("resultInfo", new ArrayList<String>());
        return mv;
    }
    
}
