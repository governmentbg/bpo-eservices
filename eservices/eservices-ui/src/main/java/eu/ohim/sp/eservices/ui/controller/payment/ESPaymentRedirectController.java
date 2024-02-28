package eu.ohim.sp.eservices.ui.controller.payment;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.LocaleComponent;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequest;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequestParam;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentStatus;
import eu.ohim.sp.core.epayment.EPaymentService;
import eu.ohim.sp.eservices.ui.adapter.ESFlowBeanFactory;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
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
 * Controller to go to the paymentIFrame.jsp view where there is a javascript automatic post to the external payment
 * system
 * 
 */
@Controller
public class ESPaymentRedirectController {

    private static final Logger LOGGER = Logger.getLogger(ESPaymentRedirectController.class);

    @Autowired
    private EPaymentService paymentService;

    @Autowired
    private ESFlowBean flowBean;

    @Autowired
    private ESFlowBeanFactory flowBeanFactory;

    @Autowired
    private LocaleComponent localeComponent;

    @Autowired
    private PersonServiceInterface personService;

	@Value("${sp.office}")
    private String office;

	@Value("${sp.module}")
    private String module;
	
    /**
     * This method is executed when the url paymentIFrame.htm is requested
     * It calls to the paymentService remote bean to retrieve the specific PO PaymentRequest object
     * Then it renders the paymentIFrame.jsp where it is executed the redirection with a POST
     * 
     * TODO Add in paymentParameters?
     * -- Defines the TM-EFILING product code related to the payment
     * insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (101, 'Defines the TM-EFILING product code related to the payment', 0, 'tmefiling.product.code', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
     * insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (101, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'tmefiling.product.code'), null, ${valid.from}, null, ${valid.from});
     * insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (118, '32',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'tmefiling.product.code'), ${valid.from});
     * paymentParameters(dot)put(PaymentRequestParam(dot)PRODUCT_CODE, configurationServiceDelegator(dot)getValueFromGeneralComponent(ConfigurationServiceDelegatorInterface(dot)TM_EFILING_PRODUCT_CODE));
     * 
     * @return a modelAndView containing the redirection view and attributes with redirection data
     * @throws eu.ohim.sp.common.SPException if the PaymentRequest object returned by the service is null
     */
    @RequestMapping(value = "paymentIFrame", method = RequestMethod.GET)
    public ModelAndView paymentIFrame(HttpServletRequest request,
            @RequestParam(required = true, value = "flowKey") String flowKey,
            @RequestParam(required = true, value = "flowMode") String flowMode) {
    	
    	// Fill in payment parameters
        Map<PaymentRequestParam, String> paymentParameters = new HashMap<PaymentRequestParam, String>();
        paymentParameters.put(PaymentRequestParam.LANG_UI, localeComponent.getLocale(request).getValue());
        String url = request.getRequestURL().toString();        	
        url = url.substring(0, url.indexOf("paymentIFrame")-1);
        paymentParameters.put(PaymentRequestParam.INVOKER_URL, url);
        paymentParameters.put(PaymentRequestParam.FLOW_KEY, flowKey);
        paymentParameters.put(PaymentRequestParam.FLOW_MODE, flowMode);

        PaymentRequest paymentRequest = paymentService.requestPayment(office, module, flowBeanFactory.convertTo(flowBean), personService.getUser(flowMode), paymentParameters);
        
        if (paymentRequest == null) {
        	throw new SPException("Fail creation the PaymentRequest", null, "error.payment.createRequest.fail");
        }
            
        ModelAndView mv = new ModelAndView("payment/paymentIFrame");
        mv.addObject("urlPayment", paymentRequest.getRedirectionURL());
        mv.addObject("paramsPayment", paymentRequest.getRedirectionParams());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Generated the redirection view with the attributes. Redirection to the external Payment system will be done in the browser");
        }
        return mv;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Throwable e) {
        LOGGER.error("Fail on RedirectPaymentController", e);
        ModelAndView mv = new ModelAndView("payment/paymentCallbackRedirect");
        mv.addObject("resultStatus", PaymentStatus.PAYMENT_ERROR.name());
        mv.addObject("initialError", true);
        mv.addObject("message", StringEscapeUtils.escapeJavaScript(e.getMessage()));
        mv.addObject("resultInfo", new ArrayList<String>());
        return mv;
    }
    
}
