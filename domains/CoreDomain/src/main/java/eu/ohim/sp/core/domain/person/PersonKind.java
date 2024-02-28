/*******************************************************************************
 * * $Id:: PersonKind.java 150534 2013-10-30 16:57:01Z garjuan                   $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.person;

public enum PersonKind {
	NATURAL_PERSON("Natural Person"),
	LEGAL_ENTITY("Legal Entity"),
    NATURAL_PERSON_SPECIAL("Natural Person Special case"),
	CORPORATION_BY_CIVIL_LAW("Corporation by Civil Law"),
	OTHER("Other")
	;
	
	private PersonKind(final String value) {
		this.value = value;
	}

	private final String value;

	public String value() {
		return value;
	}
	
	public String getEnumName(){
		return super.name();
	}
}
