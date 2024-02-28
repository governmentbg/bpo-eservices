/*******************************************************************************
 * * $Id:: PaymentStatusCode.java 121785 2013-06-06 18:45:12Z karalch            $
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

/**
 * @author ionitdi
 * 
 */
public enum PaymentStatusCode {
	DONE("Done"),
	ATTACHED("Attached"),
	TO_FOLLOW("To follow"),
	BANK_TRANSFER_TO_FOLLOW("Bank transfer to follow"),
	UNDEFINED("Undefined");

	private PaymentStatusCode(final String value) {
		this.value = value;
	}

	private final String value;
	
	public String value() {
		return value;
	}

}
