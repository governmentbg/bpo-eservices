/*
 *  PaymentService:: PaymentService 17/10/13 16:50 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.epayment;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.domain.application.ApplicationStatus;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.DraftStatus;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequest;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequestParam;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentResult;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentStatus;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.core.domain.user.User;
import eu.ohim.sp.external.epayment.EPaymentClient;
import eu.ohim.sp.external.epayment.EPaymentClientInside;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.Date;
import java.util.Map;

/**
 * PaymentService implementation
 * It has the methods to make the invocations to the PaymentAdapterService
 * In addition it manages the creation and update of the Application Service
 * 
 * @author soriama
 * 
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Stateless
public class EPaymentServiceBean implements EPaymentServiceLocal, EPaymentServiceRemote {

    /**
     * Logger for this class
     */
    private static final Logger LOGGER = Logger.getLogger(EPaymentServiceBean.class);

    /**
     * Injected instance of the applicationService EJB to manage the update of the application
     */
	@EJB(lookup="java:global/tradeMarkLocal")
    private ApplicationService tradeMarkApplicationService;

	@EJB(lookup="java:global/designLocal")
    private ApplicationService designApplicationService;

	@EJB(lookup="java:global/eServiceLocal")
	private ApplicationService eServiceApplicationService;
    
    @Inject @EPaymentClientInside
    private EPaymentClient adapter;

    /**
     * Method that returns the specific PO PaymentRequest object calling the requestPayment service in the payment
     * adapter
     * 
     * @param office The office
     * @param module The module
     * @param application TradeMarkApplication to be paid.
     * @param user The user of the application.
     * @param parameters Map with parameters for the payment request.
     * 
     * @return the PaymentRequest object returned by the Payment Adapter
     */
    @Override
    public PaymentRequest requestPayment(String office, String module, IPApplication application, User user, Map<PaymentRequestParam, String> parameters) {
    	String provisionalId = "";
    	String paymentId = "";
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("requestPayment called");
            }

			if(application == null){
				return null;
			}

            if (StringUtils.isNotBlank(application.getApplicationFilingNumber())) {
            	provisionalId = application.getApplicationFilingNumber();
            }
            
            DraftApplication draftApplication;
            if (application instanceof DesignApplication) {
            	draftApplication = designApplicationService.getDraftApplication(office, module, provisionalId);
            } else if(application instanceof TradeMarkApplication) {
				draftApplication = tradeMarkApplicationService.getDraftApplication(office, module, provisionalId);
			} else {
				draftApplication = eServiceApplicationService.getDraftApplication(office, module, provisionalId);
			}
            
            if (draftApplication != null && StringUtils.isNotBlank(draftApplication.getPaymentId())) {
            	paymentId = draftApplication.getPaymentId();
            } 

            // The paymentId is passed here
            application.setApplicationFilingDate(new Date());
            PaymentRequest pr = adapter.requestPayment(application, paymentId, user, parameters);
            // Persist IPApplication
            if (application instanceof DesignApplication) {
                designApplicationService.persistApplication(office, module, FormatXML.APPLICATION_EPUB, application);
            } else if(application instanceof TradeMarkApplication){
                tradeMarkApplicationService.persistApplication(office, module, FormatXML.APPLICATION_EPUB, application);
            } else {
                eServiceApplicationService.persistApplication(office, module, FormatXML.APPLICATION_EPUB, application);
            }

            //UpdateStatus
        	if (draftApplication == null) {
        		draftApplication = new DraftApplication();
        	}
            if (StringUtils.isNotBlank(provisionalId)) {
            	draftApplication.setProvisionalId(provisionalId);
            }
            if (StringUtils.isNotBlank(paymentId)) {
            	draftApplication.setPaymentId(paymentId);
            }
            
            if (draftApplication.getCurrentStatus() == null) {
            	draftApplication.setCurrentStatus(new DraftStatus());
            }
            draftApplication.getCurrentStatus().setModifiedDate(new Date());
            draftApplication.getCurrentStatus().setStatus(ApplicationStatus.STATUS_CC_PAYMENT_PENDING);
            
            if (application instanceof DesignApplication) {
            	designApplicationService.updateDraftApplication(draftApplication);
            } else if(application instanceof TradeMarkApplication) {
            	tradeMarkApplicationService.updateDraftApplication(draftApplication);
            } else {
				eServiceApplicationService.updateDraftApplication(draftApplication);
			}
            
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Received a PaymentRequest from the payment adapter succesfully");
            }
            return pr;
        } catch (SPException e) {
            // If there is an error, update the application Status and then throw an exception to be intercepted by the
            // controller
        	DraftApplication draftApplication = new DraftApplication();
            
            if (StringUtils.isNotBlank(provisionalId)) {
            	draftApplication.setProvisionalId(provisionalId);
            }
            if (StringUtils.isNotBlank(paymentId)) {
            	draftApplication.setPaymentId(paymentId);
            }
            
            draftApplication.setCurrentStatus(new DraftStatus());
            draftApplication.getCurrentStatus().setMessage("");
            draftApplication.getCurrentStatus().setModifiedDate(new Date());
            draftApplication.getCurrentStatus().setStatus(ApplicationStatus.STATUS_CC_PAYMENT_FAILURE);
        	
            if (application instanceof DesignApplication) {
            	designApplicationService.updateDraftApplication(draftApplication);
            } else if(application instanceof TradeMarkApplication) {
            	tradeMarkApplicationService.updateDraftApplication(draftApplication);
            } else {
				eServiceApplicationService.updateDraftApplication(draftApplication);
			}
            
            LOGGER.error("Fail to call the RequestPayment Adapter");
            throw new SPException("Fail to call the RequestPayment Adapter", e, "error.payment.adapter.call_failed");
        }
    }

    /**
     * Method that returns the specific PO PaymentResult object calling the requestPayment service in the payment
     * adapter
     * 
     * @param office The office
     * @param module The module
     * @param application IPApplication to be paid.
     * @param paymentPlatformData Map of parameters posted by the external payment environment to our callback url
     * 
     * @return a PaymentResult object returned by the PO specific adapter
     */
    @Override
    public PaymentResult notifyPaymentResult(String office, String module, IPApplication application, Map<String, String> paymentPlatformData) {
        PaymentResult pr = null;
        String applicationFilingNumber = application.getApplicationFilingNumber();
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("notifyPaymentResult called");
            }
            
            DraftApplication draftApplication;
            if (application instanceof DesignApplication) {
            	draftApplication = designApplicationService.getDraftApplication(office, module, applicationFilingNumber);
            } else if(application instanceof TradeMarkApplication) {
            	draftApplication = tradeMarkApplicationService.getDraftApplication(office, module, applicationFilingNumber);
            } else {
				draftApplication = eServiceApplicationService.getDraftApplication(office, module, applicationFilingNumber);
			}

            draftApplication.setCurrentStatus(new DraftStatus());
            draftApplication.getCurrentStatus().setMessage("");
            draftApplication.getCurrentStatus().setModifiedDate(new Date());
            
            // Call the payment adapter to get the PaymentResult
            pr = adapter.notifyPaymentResult(paymentPlatformData);
            // Update the payment status in the applicationService depending on the response
            if (pr != null && pr.getPaymentStatus() != null) {
	            if (pr.getPaymentStatus().equals(PaymentStatus.PAYMENT_OK)) {
	            	draftApplication.getCurrentStatus().setStatus(ApplicationStatus.STATUS_CC_PAYMENT_DONE);
	            } else if (pr.getPaymentStatus().equals(PaymentStatus.PAYMENT_CANCELLED)) {
	            	draftApplication.getCurrentStatus().setStatus(ApplicationStatus.STATUS_CC_PAYMENT_CANCELLED);
	            } else if (pr.getPaymentStatus().equals(PaymentStatus.PAYMENT_ERROR)) {
	            	draftApplication.getCurrentStatus().setStatus(ApplicationStatus.STATUS_CC_PAYMENT_FAILURE);
	            }
            }
            
            if (application instanceof DesignApplication) {
            	designApplicationService.updateDraftApplication(draftApplication);
            } else if(application instanceof TradeMarkApplication) {
            	tradeMarkApplicationService.updateDraftApplication(draftApplication);
            } else {
				eServiceApplicationService.updateDraftApplication(draftApplication);
			}

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Received a PaymentResult from the payment adapter succesfully");
            }

        } catch (SPException e) {
            // If there is an error, update the application Status and then throw an exception to be intercepted by the
            // controller
        	DraftApplication draftApplication = new DraftApplication();
            
            if (StringUtils.isNotBlank(applicationFilingNumber)) {
            	draftApplication.setProvisionalId(applicationFilingNumber);
            }
            
            draftApplication.setCurrentStatus(new DraftStatus());
            draftApplication.getCurrentStatus().setMessage("");
            draftApplication.getCurrentStatus().setModifiedDate(new Date());
            draftApplication.getCurrentStatus().setStatus(ApplicationStatus.STATUS_CC_PAYMENT_FAILURE);

            if (application instanceof DesignApplication) {
            	designApplicationService.updateDraftApplication(draftApplication);
            } else if(application instanceof TradeMarkApplication) {
            	tradeMarkApplicationService.updateDraftApplication(draftApplication);
            } else {
				eServiceApplicationService.updateDraftApplication(draftApplication);
			}
            
            LOGGER.error("Fail to call the NotifyPaymentResult Adapter");
            throw new SPException("Fail to call the NotifyPaymentResult Adapter", e,
                    "error.payment.adapter.call_failed");
        }
        return pr;
    }
}
