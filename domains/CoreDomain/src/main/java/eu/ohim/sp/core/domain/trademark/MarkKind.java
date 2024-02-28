/*******************************************************************************
 * * $Id:: MarkKind.java 121785 2013-06-06 18:45:12Z karalch                     $
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
package eu.ohim.sp.core.domain.trademark;

/**
 * @author ionitdi
 * 
 */
public enum MarkKind {
	INDIVIDUAL("Individual"),
	COLLECTIVE("Collective"),
	CERTIFICATE("Certificate"),
	CERTIFICATION("Certification"),
	GUARANTEE("Guarantee"),
	DEFENSIVE("Defensive"),
	OTHER("Other");

	private final String value;

	private MarkKind(final String value) {
		this.value = value;
	}
	
	public String value(){
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
}
