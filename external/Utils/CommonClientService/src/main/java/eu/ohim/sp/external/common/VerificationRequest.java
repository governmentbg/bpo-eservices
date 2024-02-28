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
import java.util.List;

/**
 * #DS Class Integration changes.
 * The Class VerificationRequest.
 * @author Ramittal
 *
 */
public class VerificationRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The office code. */
	@JsonProperty("OfficeCode")
	private String officeCode;

	/** The term list. */
	@JsonProperty("TermList")
	private List<Term> termList;

	/** The classification list. */
	@JsonProperty("ClassificationList")
	private List<String> classificationList;

	/**
	 * Gets the office code.
	 *
	 * @return the office code
	 */
	public String getOfficeCode() {
		return officeCode;
	}

	/**
	 * Sets the office code.
	 *
	 * @param officeCode the new office code
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	/**
	 * Gets the term list.
	 *
	 * @return the term list
	 */
	public List<Term> getTermList() {
		return termList;
	}

	/**
	 * Sets the term list.
	 *
	 * @param termList the new term list
	 */
	public void setTermList(List<Term> termList) {
		this.termList = termList;
	}

	/**
	 * Gets the classification list.
	 *
	 * @return the classification list
	 */
	public List<String> getClassificationList() {
		return classificationList;
	}

	/**
	 * Sets the classification list.
	 *
	 * @param classificationList the new classification list
	 */
	public void setClassificationList(List<String> classificationList) {
		this.classificationList = classificationList;
	}

}
