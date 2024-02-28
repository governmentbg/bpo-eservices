/*******************************************************************************
 * * $Id:: EnumEditor.java 49264 2012-10-29 13:23:34Z karalch                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.util;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;


/**
 * A editor which allows the translation between {@link String} and {@link Enum}
 * 
 */
@SuppressWarnings("unchecked")
public class EnumEditor extends PropertyEditorSupport {
	private Class clazz;

	public EnumEditor(Class clazz) {
		this.clazz = clazz;
	}

	public String getAsText() {
		return (getValue() == null ? "" : ((Enum) getValue()).name());
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if(!StringUtils.isEmpty(text)) {
			setValue(Enum.valueOf(clazz, text));
		} 
	}
}
