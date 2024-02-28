/*******************************************************************************
 * * $Id:: Colour.java 128442 2013-07-11 13:35:23Z jaraful                       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.resources;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;

public class Colour extends Id implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6114408076747993274L;

	private String format;
	private String value;
	
	public Colour() {
		this.format = "";
		this.value = "";
	}

	public Colour(String format, String value) {
		this.format = format;
		this.value = value;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
