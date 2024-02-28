/*******************************************************************************
 * * $Id:: SubmittedForm.java 49264 2012-10-29 13:23:34Z karalch                 $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.application;

import java.io.Serializable;

import eu.ohim.sp.common.ui.flow.FlowBean;

public class SubmittedForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String provisionalId;
	private String paymentId;
	private String bankPaymentId;
	private String paymentMethod;
	private String documentId;
	private FlowBean flowBean;
	private String flowModeId;
	private String applicationId;

	
	/**
	 * @return the payment id
	 */
	public String getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId the payment id
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	/**
	 * @return the bank payment id
	 */
	public String getBankPaymentId() {
		return bankPaymentId;
	}

	/**
	 * @param bankPaymentId the bank payment id
	 */
	public void setBankPaymentId(String bankPaymentId) {
		this.bankPaymentId = bankPaymentId;
	}

	/**
	 * @return the payment method
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod the payment method
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the provisionalId
	 */
	public String getProvisionalId() {
		return provisionalId;
	}

	/**
	 * @param provisionalId the provisionalId to set
	 */
	public void setProvisionalId(String provisionalId) {
		this.provisionalId = provisionalId;
	}
	
	public String getDocumentId() {
		return documentId;
	}
	
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public FlowBean getFlowBean() {
		return flowBean;
	}

	public void setFlowBean(FlowBean flowBean) {
		this.flowBean = flowBean;
	}

	public String getFlowModeId() {
		return flowModeId;
	}

	public void setFlowModeId(String flowModeId) {
		this.flowModeId = flowModeId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
		
}
