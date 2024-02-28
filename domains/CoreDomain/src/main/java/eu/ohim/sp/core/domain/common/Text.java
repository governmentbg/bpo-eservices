/*******************************************************************************
 * * $Id:: Text.java 128443 2013-07-11 13:36:47Z velasca                         $
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
package eu.ohim.sp.core.domain.common;

import java.io.Serializable;

import eu.ohim.sp.core.domain.id.Id;

/**
 * @author ionitdi
 * 
 */
public class Text extends Id implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8057015011156784945L;
	private String value;
	private String language;

	/**
	 * @return the text
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the text to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the languageCode
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the languageCode to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
}
