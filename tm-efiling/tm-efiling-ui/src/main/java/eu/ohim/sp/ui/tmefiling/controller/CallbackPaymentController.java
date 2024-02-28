/*******************************************************************************
 * * $Id::                                                                   $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentResult;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentStatus;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.core.epayment.EPaymentService;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;

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
public class CallbackPaymentController {

    /** Logger for this class and subclasses */
    private static final Logger logger = Logger.getLogger(CallbackPaymentController.class);

    /**
     * CORE PaymentServiceInterface
     */
    @Autowired
    private EPaymentService paymentService;
    
    @Autowired
    private FlowBeanFactory flowBeanFactory;

    @Autowired
    private FlowBeanImpl flowBean;

	private static final String MODULE = "tmefiling";

	private static final String OFFICE = "EM";

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

		// Gets the trademark info and sets the filing number
		TradeMarkApplication trademarkApplication=flowBeanFactory.convertToTradeMarkApplication(flowBean);
		trademarkApplication.setApplicationFilingNumber(applicationFilingNumber);

		// Notifies the payment of the trademark to the application
        PaymentResult paymentResult = paymentService.notifyPaymentResult(OFFICE, MODULE, trademarkApplication, paymentPlatformData);
		if (paymentResult == null) {
			throw new SPException("Fail adapting the Payment Response", null, "error.payment.adaptResponse.fail");
		}

		flowBean.getPaymentForm().setPaymentResult(paymentResult);

		// Depending on the result returned by the specific adapter we render the correspondant view
		ModelAndView mv = new ModelAndView("payment/paymentCallbackRedirect");
		mv.addObject("resultStatus", paymentResult.getPaymentStatus().name());
		mv.addObject("message", StringEscapeUtils.escapeJavaScript(paymentResult.getResultMessage()));

        if (logger.isInfoEnabled()) {
            logger.info("End of the Payment operation");
        }
        return mv;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Throwable e) {
        logger.error("Fail on RedirectPaymentController", e);
        ModelAndView mv = new ModelAndView("payment/paymentCallbackRedirect");
        mv.addObject("resultStatus", PaymentStatus.PAYMENT_ERROR.name());
        mv.addObject("message", StringEscapeUtils.escapeJavaScript(e.getMessage()));
        mv.addObject("resultInfo", new ArrayList<String>());
        return mv;
    }
}
