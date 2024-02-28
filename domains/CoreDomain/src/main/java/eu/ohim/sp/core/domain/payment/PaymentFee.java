/*******************************************************************************
 * * $Id:: PaymentFee.java 124359 2013-06-19 17:43:34Z karalch                   $
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
package eu.ohim.sp.core.domain.payment;

import java.io.Serializable;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentFee.
 *
 * @author ionitdi
 */
public class PaymentFee extends Payment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4430647282591406678L;

	/** The fees. */
	private List<MatchedFee> fees;

	/**
	 * Gets the fees.
	 *
	 * @return the fees
	 */
	public List<MatchedFee> getFees() {
		return fees;
	}

	/**
	 * Sets the fees.
	 *
	 * @param fees the new fees
	 */
	public void setFees(List<MatchedFee> fees) {
		this.fees = fees;
	}

}
