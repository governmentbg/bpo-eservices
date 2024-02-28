/*******************************************************************************
 * * $Id:: ClassificationErrorType.java 53083 2012-12-14 08:59:24Z virgida       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.trademark;

import java.io.Serializable;
import java.util.Collection;

import eu.ohim.sp.core.domain.classification.MatchedTerm;


public class ClassificationErrorType implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String REJECTED_TERM = "Rejection term";
	public static final String CONTROLLED_TERM = "Controlled term found";
	public static final String HOMONYM = "Homonym found";
	public static final String WRONG_CLASS = "Wrong class";
	public static final String SYNONYM = "Synonym found";
	public static final String SYNONYM_WRONG_CLASS = "Synonym found in wrong class";
	public static final String NOT_FOUND = "Not found";
	public static final String NO_CLASS_GIVEN = "No class given";
	public static final String SIMILAR_TERM = "Similar term";
	public static final String CONTAINS_CONTROLLED_TERM = "Contains controlled term";
	public static final String LEGACY_TERM = "Legacy term";
	public static final String CONTAINS_LEGACY_TERM = "Contains legacy term";

	private ClassificationErrorEnum errorEnum;
	private String verificationAssessment;
	private Collection<MatchedTerm> matchedTerms;

	/**
	 * Method that returns the errorEnum
	 * 
	 * @return the errorEnum
	 */
	public ClassificationErrorEnum getErrorEnum() {
		return errorEnum;
	}

	/**
	 * Method that sets the errorEnum
	 * 
	 * @param errorEnum
	 *            the errorEnum to set
	 */
	public void setErrorEnum(ClassificationErrorEnum errorEnum) {
		this.errorEnum = errorEnum;
	}

	public String getVerificationAssessment() {
		return verificationAssessment;
	}

	public void setVerificationAssessment(String verificationAssessment) {
		this.verificationAssessment = verificationAssessment;
	}

	public Collection<MatchedTerm> getMatchedTerms() {
		return matchedTerms;
	}

	public void setMatchedTerms(Collection<MatchedTerm> matchedTerms) {
		this.matchedTerms = matchedTerms;
	}
}
