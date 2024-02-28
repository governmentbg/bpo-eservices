/*******************************************************************************
 * * $Id:: FormatColourForm.java 14333 2012-10-29 13:23:34Z karalch             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.resources;
@Deprecated
public enum FormatColourForm
{
	HEX("Hexadecimal"),
	PANTONE("Pantone"),
	RAL("RAL"),
	RGB("RGB")
	;
	
	private FormatColourForm(final String text)
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
