/*******************************************************************************
 * * $Id:: PaymentService.java 150992 2013-11-04 11:28:14Z serrajo      $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.epayment;

import java.util.Map;

import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequest;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequestParam;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentResult;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.user.User;

/**
 * It has the methods to make the invocations to the PaymentAdapterService
 * 
 * @author soriama
 *
 */
public interface EPaymentService {

	/**
	 * Method that returns a PaymentRequest object
	 * 
	 * @param office The office
	 * @param module The module
	 * @param application IPApplication to be paid.
	 * @param user The user of the application.
	 * @param parameters Map with parameters for the payment request. 
	 *    See {@link eu.ohim.sp.core.domain.payment.wrapper.PaymentRequestParam}.
	 * 
	 * @return the PaymentRequest object returned by the Payment Adapter
	 */
	PaymentRequest requestPayment(String office, String module, IPApplication application, User user, Map<PaymentRequestParam, String> parameters);

	/**
	 * Method that returns a PaymentResult object
	 * 
	 * @param office The office
	 * @param module The module
	 * @param application IPApplication to be paid.
	 * @param paymentPlatformData Map of parameters posted by the external payment environment to our callback url
	 * 
	 * @return a PaymentResult object returned by the PO specific adapter
	 */
	PaymentResult notifyPaymentResult(String office, String module, IPApplication application, Map<String, String> paymentPlatformData);	
}
