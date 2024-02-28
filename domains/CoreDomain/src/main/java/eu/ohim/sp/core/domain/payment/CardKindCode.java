/*******************************************************************************
 * * $Id:: CardKindCode.java 53083 2012-12-14 08:59:24Z virgida                  $
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
public enum CardKindCode {
	CREDIT_CARD("Credit Card"),
	DEBIT_CARD("Debit Card")
	;
	
	private CardKindCode(final String text) {
		this.text = text;
	}

	private final String text;

	@Override
	public String toString() {
		return text;
	}
}
