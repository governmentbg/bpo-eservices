//@formatter:off
/**
 *  $Id$
 *       . * .
 *     * RRRR  *    Copyright (c) 2015 OHIM: Office for Harmonization
 *   .   RR  R   .  in the Internal Market (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 */
//@formatter:on
package eu.ohim.sp.core.domain.classification;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by estevca on 08/04/2015.
 */
public class LocarnoSubClassHeading implements Serializable {

	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** The subclass code. */
	@JsonProperty("SubClassCode")
	private Integer subClassCode;

	/** The subclass description. */
	@JsonProperty("Description")
	private String description;

	/**
	 * gets the subclass code.
	 *
	 * @return
	 */
	public Integer getSubClassCode() {
		return subClassCode;
	}

	/**
	 * sets the subclass code.
	 *
	 * @param subClassCode
	 */
	public void setSubClassCode(Integer subClassCode) {
		this.subClassCode = subClassCode;
	}

	/**
	 * gets the description.
	 *
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * sets the description.
	 *
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
