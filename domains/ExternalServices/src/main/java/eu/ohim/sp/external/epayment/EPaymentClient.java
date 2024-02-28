/*
 *  EPaymentClient:: EPaymentClient 17/10/13 16:50 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.external.epayment;

import java.util.Map;

import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequest;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequestParam;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentResult;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.user.User;

/**
 * It has the methods to make the invocations to the specific PO ESB Adapter
 * 
 * @author soriama
 *
 */
public interface EPaymentClient {
	
	/**
	 * Interface to the requestPayment service in the ESB 
	 * 
	 * @param application TradeMarkApplication to be paid.
	 * @param paymentId ID of the payment transaction. If not null it means it was tried to pay previously.
	 * @param user The user of the application.
	 * @param parameters Map with parameters for the payment request. 
	 * 
	 * @return the PaymentRequest object returned by the ESB
	 */
	PaymentRequest requestPayment(IPApplication application, String paymentId, User user, Map<PaymentRequestParam, String> parameters);

	/**
	 * Interface to the notifyPaymentResult service in the ESB
	 * 
	 * @param paymentPlatformData Map of parameters posted by the external payment environment to our callback url
	 * 
	 * @return the PaymentResult object returned by the ESB
	 */
	PaymentResult notifyPaymentResult(Map<String, String> paymentPlatformData);
}