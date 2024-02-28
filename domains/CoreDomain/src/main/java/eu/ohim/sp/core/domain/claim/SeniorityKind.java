/*******************************************************************************
 * * $Id:: SeniorityKind.java 121785 2013-06-06 18:45:12Z karalch                $
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
package eu.ohim.sp.core.domain.claim;

/**
 * @author ionitdi
 *
 */
public enum SeniorityKind {
	NATIONAL_OR_REGIONAL_TRADE_MARK("National or regional trade mark"),
	INTERNATIONAL_TRADE_MARK("International trade mark"),
	CTM("CTM")
	;

	private SeniorityKind(final String value) {
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
