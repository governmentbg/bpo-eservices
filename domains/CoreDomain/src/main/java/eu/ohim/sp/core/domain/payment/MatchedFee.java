/*
 *  CoreDomain:: MatchedFee 09/08/13 16:12 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.payment;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;

/**
 * The Class MatchedFee.
 */
public class MatchedFee extends Id implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6255283575959770929L;

	/** The matched amount. */
	private Double matchedAmount;
	
	/** The Fee. */
	private Fee fee;
	
	/**
	 * Gets the matched amount.
	 *
	 * @return the matched amount
	 */
	public Double getMatchedAmount() {
		return matchedAmount;
	}
	
	/**
	 * Sets the matched amount.
	 *
	 * @param matchedAmount the new matched amount
	 */
	public void setMatchedAmount(Double matchedAmount) {
		this.matchedAmount = matchedAmount;
	}
	
	/**
	 * Gets the fee.
	 *
	 * @return the fee
	 */
	public Fee getFee() {
		return fee;
	}
	
	/**
	 * Sets the fee.
	 *
	 * @param fee the new fee
	 */
	public void setFee(Fee fee) {
		this.fee = fee;
	}
	
}
