/*******************************************************************************
 * * $Id:: FeeResult.java 124871 2013-06-24 10:27:43Z karalch                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.validation;

import java.io.Serializable;

@Deprecated
public class FeeResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4994449871611809534L;
	
	private ErrorList errors;
	private int feeTotal;
	
	public ErrorList getErrors() {
		return errors;
	}
	public void setErrors(ErrorList errors) {
		this.errors = errors;
	}
	public int getFeeTotal() {
		return feeTotal;
	}
	public void setFeeTotal(int feeTotal) {
		this.feeTotal = feeTotal;
	}	
}
