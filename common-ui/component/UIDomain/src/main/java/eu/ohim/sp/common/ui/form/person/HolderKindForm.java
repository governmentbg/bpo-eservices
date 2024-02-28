/*******************************************************************************
 * * $Id:: ApplicantKindForm.java 49264 2012-10-29 13:23:34Z karalch             $
 * *       . * .
 * *     * RRRR  *    Copyright Â© 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

public enum HolderKindForm
{
    LEGAL_ENTITY("Legal Entity"),
	NATURAL_PERSON("Natural Person"),
	;
	
	private HolderKindForm(final String text)
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
