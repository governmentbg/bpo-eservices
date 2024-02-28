/*******************************************************************************
 * * $Id:: SeniorityKindForm.java 49264 2012-10-29 13:23:34Z karalch             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.claim;

public enum SeniorityKindForm
{
	NATIONAL_OR_REGIONAL_TRADE_MARK("National or regional trade mark"),
	INTERNATIONAL_TRADE_MARK("International trade mark")
	;
	
	private SeniorityKindForm(final String text)
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
