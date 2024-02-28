/*******************************************************************************
 * * $Id:: SearchMode.java 14329 2012-10-29 13:02:02Z karalch                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification.wrapper;

public enum SearchMode {
	WORDSPREFIX("wordsprefix"), 
	FULLPHRASE("fullphrase"), 
	EXACTMATCH("exactmatch");

	private final String value;

	SearchMode(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
