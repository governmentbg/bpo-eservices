/*******************************************************************************
 * * $Id:: PaymentResult.java 14329 2012-10-29 13:02:02Z karalch                 $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.payment.wrapper;

import java.io.Serializable;
import java.util.List;

import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.payment.AccountDebitKind;
import eu.ohim.sp.core.domain.payment.Payment;
import eu.ohim.sp.core.domain.payment.PaymentStatusCode;

/**
 * @author soriama This CORE DOM object contains the necessary information to
 *         decide whether the payment has been paid, it was cancelled or there
 *         was an error, and extra information to inform the user
 * 
 * 
 */
public class PaymentResult extends Id implements Serializable {
	private static final long serialVersionUID = 7887521242014427087L;

	/**
	 * Status of the payment transaction (not the payment itself)
	 */
	private PaymentStatus paymentStatus;

	/**
	 * Status code of the payment. This is saved in the tm-xml
	 */
	@Deprecated
	private PaymentStatusCode paymentStatusCode;

	private Payment payment;

	/**
	 * Message with the result of the transaction to display to the user
	 */
	private String resultMessage;

	/**
	 * List of strings containing additional information to display to the user
	 */
	@Deprecated
	private List<String> resultInfo;

	/**
	 * Technical code of the result
	 */
	@Deprecated
	private String techincalCode;

	/**
	 * Message code of the result
	 */
	@Deprecated
	private String messageCode;

	/**
	 * Specific PO external Payment Number (not the paymentId)
	 */
	@Deprecated
	private String externalPaymentNumber;

	/**
	 * Specific PO payment method (not the paymentMethod inside PaymentForm.
	 * This one is the returned by the external environment. Could be that the
	 * paymentMethod in PaymentForm is null while this one is not)
	 */
	@Deprecated
	private String externalPaymentMethod;

	/**
	 * PaymentRequest object originally sent
	 */
	private PaymentRequest paymentRequest;

	/**
     *
     */
	@Deprecated
	private AccountDebitKind accountDebitKind;

	/**
	 * Get the payment status
	 * 
	 * @return the payment status
	 */
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * Set the payment status
	 * 
	 * @param paymentStatus
	 *            the payment status to set
	 */
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * Get the result message
	 * 
	 * @return the result message
	 */
	public String getResultMessage() {
		return resultMessage;
	}

	/**
	 * Set the result message
	 * 
	 * @param resultMessage
	 *            the result message to set
	 */
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	/**
	 * Get the payment request
	 * 
	 * @return the payment request
	 */
	public PaymentRequest getPaymentRequest() {
		return paymentRequest;
	}

	/**
	 * Set the payment request
	 * 
	 * @param paymentRequest
	 *            the payment request to set
	 */
	public void setPaymentRequest(PaymentRequest paymentRequest) {
		this.paymentRequest = paymentRequest;
	}

	/**
	 * Get the result info
	 * 
	 * @return the result info
	 */
	public List<String> getResultInfo() {
		return resultInfo;
	}

	/**
	 * Set the result info
	 * 
	 * @param resultInfo
	 *            the result info to set
	 */
	public void setResultInfo(List<String> resultInfo) {
		this.resultInfo = resultInfo;
	}

	/**
	 * Get the technical code
	 * 
	 * @return the techincal code
	 */
	public String getTechincalCode() {
		return techincalCode;
	}

	/**
	 * Set the technical code
	 * 
	 * @param techincalCode
	 *            the techincal code to set
	 */
	public void setTechincalCode(String techincalCode) {
		this.techincalCode = techincalCode;
	}

	/**
	 * Get the message code
	 * 
	 * @return the messageCode
	 */
	public String getMessageCode() {
		return messageCode;
	}

	/**
	 * Set the message code
	 * 
	 * @param messageCode
	 *            the message code to set
	 */
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	/**
	 * Get the external payment number
	 * 
	 * @return the external payment number
	 */
	public String getExternalPaymentNumber() {
		return externalPaymentNumber;
	}

	/**
	 * Set the external payment number
	 * 
	 * @param externalPaymentNumber
	 *            the external payment number to set
	 */
	public void setExternalPaymentNumber(String externalPaymentNumber) {
		this.externalPaymentNumber = externalPaymentNumber;
	}

	/**
	 * Get the external payment method
	 * 
	 * @return the external payment method
	 */
	public String getExternalPaymentMethod() {
		return externalPaymentMethod;
	}

	/**
	 * Set the external payment method
	 * 
	 * @param externalPaymentMethod
	 *            the external payment method to set
	 */
	public void setExternalPaymentMethod(String externalPaymentMethod) {
		this.externalPaymentMethod = externalPaymentMethod;
	}

	/**
	 * Get the payment status code
	 * 
	 * @return the payment status code
	 */
	public PaymentStatusCode getPaymentStatusCode() {
		return paymentStatusCode;
	}

	/**
	 * Set the payment status code
	 * 
	 * @param paymentStatusCode
	 *            the payment status code to set
	 */
	public void setPaymentStatusCode(PaymentStatusCode paymentStatusCode) {
		this.paymentStatusCode = paymentStatusCode;
	}

	/**
	 * 
	 * @return
	 */
	public AccountDebitKind getAccountDebitKind() {
		return accountDebitKind;
	}

	/**
	 * 
	 * @param accountDebitKind
	 */
	public void setAccountDebitKind(AccountDebitKind accountDebitKind) {
		this.accountDebitKind = accountDebitKind;
	}
}
