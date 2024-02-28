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
package eu.ohim.sp.external.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * #DS Class Integration changes.
 * The Class MatchingResult.
 * @author Ramittal
 *
 */
public class MatchingResult implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2012025708714562983L;

	/** The term id. */
	@JsonProperty("TermId")
	private String termId;

	/** The term class. */
	@JsonProperty(value = "TermClass")
	private String termClass;

	/**
	 * The Term text.
	 */
	@JsonProperty(value="TermText", required = false)
	private String termText;

	/**
	 * Gets the term id.
	 *
	 * @return the termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * Sets the term id.
	 *
	 * @param termId the termId to set
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	/**
	 * Gets the term class.
	 *
	 * @return the termClass
	 */
	public String getTermClass() {
		return termClass;
	}

	/**
	 * Sets the term class.
	 *
	 * @param termClass the termClass to set
	 */
	public void setTermClass(String termClass) {
		this.termClass = termClass;
	}

	/**
	 * Gets term text.
	 *
	 * @return the term text
	 */
	public String getTermText() {
		return termText;
	}

	/**
	 * Sets term text.
	 *
	 * @param termText the term text
	 */
	public void setTermText(String termText) {
		this.termText = termText;
	}
}
