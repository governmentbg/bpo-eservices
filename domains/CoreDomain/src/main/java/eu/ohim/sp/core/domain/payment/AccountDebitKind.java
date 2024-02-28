/*******************************************************************************
 * * $Id:: AccountDebitKind.java 121785 2013-06-06 18:45:12Z karalch             $
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
public enum AccountDebitKind {
	BASIC_FEE_IMMEDIATE("Basic Fee Immediate"),
    BASIC_FEE_END_OF_PERIOD("Basic Fee End of Period"),
    BASIC_AND_CLASS_FEE_IMMEDIATE("Basic and Class Fee Immediate"),
    BASIC_AND_CLASS_FEE_END_OF_PERIOD("Basic and Class Fee End of Period"),
    DO_NOT_USE_CURRENT_ACCOUNT("Do not use Current Account")
	;	
	
	private AccountDebitKind(final String value) {
		this.value = value;
	}

	private final String value;

	@Override
	public String toString() {
		return value;
	}

	public String value() {
		return value;
	}
}
