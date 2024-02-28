/*******************************************************************************
 * * $Id:: InternationalTradeMarkCode.java 121785 2013-06-06 18:45:12Z karalch   $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.claim;

/**
 * User: ionitdi Date: 14/05/12 Time: 16:43
 */
public enum InternationalTradeMarkCode {
	MADRID("Madrid"), EU("EU");

	private InternationalTradeMarkCode(final String value) {
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
