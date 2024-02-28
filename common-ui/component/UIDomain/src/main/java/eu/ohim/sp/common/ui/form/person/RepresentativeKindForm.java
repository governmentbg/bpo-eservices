/*******************************************************************************
 * * $Id:: RepresentativeKindForm.java 49264 2012-10-29 13:23:34Z karalch        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

public enum RepresentativeKindForm
{
	PROFESSIONAL_PRACTITIONER("OHIM Professional Practitioner"),
	LEGAL_PRACTITIONER("Legal Practitioner"),
	LAWYER_ASSOCIATION("Lawyer Association"),
	LAWYER_COMPANY("Lawyer Company"),
	TEMPORARY_REPRESENTATIVE("Temporary Representative"),
	EMPLOYEE_REPRESENTATIVE("Employee Representative"),
	NATURAL_PERSON("Natural Person"),
	LEGAL_ENTITY("Legal Entity"),
	ASSOCIATION("Association"),
	INTELLECTUAL_PROPERTY_REPRESENTATIVE("Intellectual Property Representative"),
	;

	private RepresentativeKindForm(final String text)
	{
		this.text = text;
	}

	private final String text;

	@Override
	public String toString()
	{
		return text;
	}
}
