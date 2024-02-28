/*******************************************************************************
 * * $Id:: Term.java 14329 2012-10-29 13:02:02Z karalch                          $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eu.ohim.sp.core.domain.classification.MatchedTerm;
import eu.ohim.sp.core.domain.classification.VerifiedTermResult;


public class Term extends TermId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -769327271024584500L;
	
	public static final String REJECTED_TERM="Rejection term";
	public static final String CONTROLLED_TERM="Controlled term found";
	public static final String HOMONYM="Homonym found";
	public static final String WRONG_CLASS="Wrong class";
	public static final String SYNONYM="Synonym found";
	public static final String SYNONYM_WRONG_CLASS="Synonym found in wrong class";
	public static final String NOT_FOUND="Not found";
	public static final String NO_CLASS_GIVEN="No class given";
	public static final String SIMILAR_TERM="Similar term";
	public static final String CONTAINS_CONTROLLED_TERM="Contains controlled term";
	public static final String LEGACY_TERM="Legacy term";
	public static final String CONTAINS_LEGACY_TERM="Contains legacy term";

	private String termId;
	private String lang;
	private Integer niceClass;
	private Boolean scopeAcceptability;
	private List<String> scope;
	private String text;
	private VerifiedTermResult verificationResult;
	private String verificationAssessment;
	private List<String> parentIds;
	private Collection<MatchedTerm> matchedTerms;
	
	public Term() {
		matchedTerms = new ArrayList<MatchedTerm>();
	}

	public String getTermId() {
		return termId;
	}


	public void setTermId(String termId) {
		this.termId = termId;
	}


	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public Integer getNiceClass() {
		return niceClass;
	}
	public void setNiceClass(Integer niceClass) {
		this.niceClass = niceClass;
	}
	public Boolean isScopeAcceptability() {
		return scopeAcceptability;
	}
	public void setScopeAcceptability(Boolean scopeAcceptability) {
		this.scopeAcceptability = scopeAcceptability;
	}
	public List<String> getScope() {
		return scope;
	}
	public void setScope(List<String> scope) {
		this.scope = scope;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public VerifiedTermResult getVerificationResult() {
		return verificationResult;
	}
	public void setVerificationResult(VerifiedTermResult verificationResult) {
		this.verificationResult = verificationResult;
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

	public List<String> getParentIds() {
		return parentIds;
	}

	public void setParentIds(List<String> parentIds) {
		this.parentIds = parentIds;
	}

	public Boolean getScopeAcceptability() {
		return scopeAcceptability;
	}
	
	
}

