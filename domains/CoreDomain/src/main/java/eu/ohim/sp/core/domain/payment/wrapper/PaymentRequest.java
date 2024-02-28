/*******************************************************************************
 * * $Id:: PaymentRequest.java 14329 2012-10-29 13:02:02Z karalch                $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
/**
 * 
 */
package eu.ohim.sp.core.domain.payment.wrapper;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;
import java.util.Map;

/**
 * @author soriama
 * This CORE DOM object contains the necessary information to make a redirection 
 * to the PO external payment environment
 * 
 */
public class PaymentRequest extends Id implements Serializable
{
	private static final long serialVersionUID = 7887521242014427087L;
	
	/**
	 * paymentId generated with the specific PO implementation
	 */
	private String paymentRequestId;
	
	/**
	 * Url where the form has to be POST to reach the external payment environment 
	 */
	private String redirectionURL;
	
	/**
	 * Map that contains the parameters and their values to be POST to the external redirectionURL
	 */
	private Map<String, String> redirectionParams;

	/**
	 * @return the paymentRequestId
	 */
	public String getPaymentRequestId() {
		return paymentRequestId;
	}

	/**
	 * @param paymentRequestId the paymentRequestId to set
	 */
	public void setPaymentRequestId(String paymentRequestId) {
		this.paymentRequestId = paymentRequestId;
	}

	/**
	 * @return the redirectionURL
	 */
	public String getRedirectionURL() {
		return redirectionURL;
	}

	/**
	 * @param redirectionURL the redirectionURL to set
	 */
	public void setRedirectionURL(String redirectionURL) {
		this.redirectionURL = redirectionURL;
	}

	/**
	 * @return the redirectionParams
	 */
	public Map<String, String> getRedirectionParams() {
		return redirectionParams;
	}

	/**
	 * @param redirectionParams the redirectionParams to set
	 */
	public void setRedirectionParams(Map<String, String> redirectionParams) {
		this.redirectionParams = redirectionParams;
	} 	
}
