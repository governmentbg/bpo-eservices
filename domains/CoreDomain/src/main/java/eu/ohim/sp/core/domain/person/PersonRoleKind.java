/*******************************************************************************
 * * $Id:: Role.java 121785 2013-06-06 18:45:12Z karalch                         $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.person;

public enum PersonRoleKind {
    APPLICANT("Applicant"),
    EMPLOYEE("Employee"),
    LEGAL_PRACTITIONER("Legal practitioner"),
    REPRESENTATIVE("Representative"),
    OHIM_PROFESSIONAL_REPRESENTATIVE("OHIM Professional Representative"),
	CONTACT_PERSON("Contact person"),
	OTHER("Other"),
	BAILIFF("Bailiff"),
	ASSIGNEE("Assignee");

	private PersonRoleKind(final String value) { 
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
