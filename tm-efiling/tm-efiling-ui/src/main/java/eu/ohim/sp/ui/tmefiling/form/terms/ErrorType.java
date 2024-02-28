/*******************************************************************************
 * * $Id:: ErrorType.java 293593 2017-09-29 16:36:45Z marinca                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.form.terms;

import eu.ohim.sp.core.domain.classification.MatchedTerm;
import eu.ohim.sp.core.domain.trademark.ClassificationErrorEnum;

import java.io.Serializable;
import java.util.Collection;

/**
 * The Class ErrorType.
 */
public class ErrorType implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

	/** The Constant REJECTED_TERM. */
	public static final String REJECTED_TERM="Rejection term";
	
	/** The Constant CONTROLLED_TERM. */
	public static final String CONTROLLED_TERM="Controlled term found";
	
	/** The Constant HOMONYM. */
	public static final String HOMONYM="Homonym found";
	
	/** The Constant WRONG_CLASS. */
	public static final String WRONG_CLASS="Wrong class";
	
	/** The Constant SYNONYM. */
	public static final String SYNONYM="Synonym found";
	
	/** The Constant SYNONYM_WRONG_CLASS. */
	public static final String SYNONYM_WRONG_CLASS="Synonym found in wrong class";
	
	/** The Constant NOT_FOUND. */
	public static final String NOT_FOUND="Not found";
	
	/** The Constant NO_CLASS_GIVEN. */
	public static final String NO_CLASS_GIVEN="No class given";
	
	/** The Constant SIMILAR_TERM. */
	public static final String SIMILAR_TERM="Similar term";
	
	/** The Constant CONTAINS_CONTROLLED_TERM. */
	public static final String CONTAINS_CONTROLLED_TERM="Contains controlled term";
	
	/** The Constant LEGACY_TERM. */
	public static final String LEGACY_TERM="Legacy term";
	
	/** The Constant CONTAINS_LEGACY_TERM. */
	public static final String CONTAINS_LEGACY_TERM="Contains legacy term";

	/** The error enum. */
	private ClassificationErrorEnum errorEnum;
	
	/** The verification assessment. */
	private String verificationAssessment;
	
	/** The matched terms. */
	private Collection<MatchedTerm> matchedTerms;
	
	/**
	 * Method that returns the errorEnum.
	 *
	 * @return the errorEnum
	 */
	public ClassificationErrorEnum getErrorEnum() {
		return errorEnum;
	}
	
	/**
	 * Method that sets the errorEnum.
	 *
	 * @param errorEnum the errorEnum to set
	 */
	public void setErrorEnum(ClassificationErrorEnum errorEnum) {
		this.errorEnum = errorEnum;
	}

	/**
	 * Gets the verification assessment.
	 *
	 * @return the verification assessment
	 */
	public String getVerificationAssessment() {
		return verificationAssessment;
	}
	
	/**
	 * Sets the verification assessment.
	 *
	 * @param verificationAssessment the new verification assessment
	 */
	public void setVerificationAssessment(String verificationAssessment) {
		this.verificationAssessment = verificationAssessment;
	}
	
	/**
	 * Gets the matched terms.
	 *
	 * @return the matched terms
	 */
	public Collection<MatchedTerm> getMatchedTerms() {
		return matchedTerms;
	}
	
	/**
	 * Sets the matched terms.
	 *
	 * @param matchedTerms the new matched terms
	 */
	public void setMatchedTerms(Collection<MatchedTerm> matchedTerms) {
		this.matchedTerms = matchedTerms;
	}
}
