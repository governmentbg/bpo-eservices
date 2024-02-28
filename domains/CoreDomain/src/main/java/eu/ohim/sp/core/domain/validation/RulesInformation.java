/*******************************************************************************
 * * $Id:: RulesInformation.java 128442 2013-07-11 13:35:23Z jaraful             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.validation;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Rules information.
 */
public class RulesInformation extends Id implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 4740171793107027316L;

	private Map<String, Object> customObjects;

	/** Constructor */
	public RulesInformation() {
		customObjects = new HashMap<String, Object>();
	}

	/**
	 * Gets custom objects.
	 *
	 * @return the custom objects
	 */
	public Map<String, Object> getCustomObjects() {
		return customObjects;
	}

	/**
	 * Sets custom objects.
	 *
	 * @param customObjects the custom objects
	 */
	public void setCustomObjects(Map<String, Object> customObjects) {
		this.customObjects = customObjects;
	}
}
