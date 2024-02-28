/*******************************************************************************
 * * $Id:: OrderBy.java 14329 2012-10-29 13:02:02Z karalch                       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification.wrapper;

/**
 * OrderBy enumeration
 */
public enum OrderBy {
	ASC("ASC"), DESC("DESC");

	private final String value;

	OrderBy(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
