/*******************************************************************************
 * * $Id:: MatchedTerm.java 128443 2013-07-11 13:36:47Z velasca                  $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The Class MatchedTerm.
 */
public class MatchedTerm extends Id implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2460312289759385193L;

	/** The term source type. */
	private String termSourceType;
	
	/** The partial. */
	private boolean partial;
	
	/** The match class type. */
	private MatchClassType matchClassType;
	
	/** The match proximity type. */
	private MatchProximityType matchProximityType;
	
	/** The matched class number. */
	private Integer matchedClassNumber;
	
	/** The matched term text. */
	private String matchedTermText;
	
	/** The identifier. */
	private String identifier;
	
	/** The matched term position in input term list. */
	private Integer matchedTermPositionInInputTermList;
	
	/** The matched term position in input term. */
	private Integer matchedTermPositionInInputTerm;
	
	/** The match rank. */
	private Integer matchRank;
	
	/** The legacy term frequency. */
	private Integer legacyTermFrequency;
	
	/** The legacy term first use date. */
	private Date legacyTermFirstUseDate;
	
	/** The legacy term last use date. */
	private Date legacyTermLastUseDate;

	private List<String> matchedTerminologySourceList;

	/**
	 * Gets the term source type.
	 *
	 * @return the term source type
	 */
	public String getTermSourceType() {
		return termSourceType;
	}

	/**
	 * Sets the term source type.
	 *
	 * @param termSourceType the new term source type
	 */
	public void setTermSourceType(String termSourceType) {
		this.termSourceType = termSourceType;
	}

	/**
	 * Checks if is partial.
	 *
	 * @return true, if is partial
	 */
	public boolean isPartial() {
		return partial;
	}

	/**
	 * Sets the partial.
	 *
	 * @param partial the new partial
	 */
	public void setPartial(boolean partial) {
		this.partial = partial;
	}

	/**
	 * Gets the match class type.
	 *
	 * @return the match class type
	 */
	public MatchClassType getMatchClassType() {
		return matchClassType;
	}

	/**
	 * Sets the match class type.
	 *
	 * @param matchClassType the new match class type
	 */
	public void setMatchClassType(MatchClassType matchClassType) {
		this.matchClassType = matchClassType;
	}

	/**
	 * Gets the match proximity type.
	 *
	 * @return the match proximity type
	 */
	public MatchProximityType getMatchProximityType() {
		return matchProximityType;
	}

	/**
	 * Sets the match proximity type.
	 *
	 * @param matchProximityType the new match proximity type
	 */
	public void setMatchProximityType(MatchProximityType matchProximityType) {
		this.matchProximityType = matchProximityType;
	}

	/**
	 * Gets the matched class number.
	 *
	 * @return the matched class number
	 */
	public Integer getMatchedClassNumber() {
		return matchedClassNumber;
	}

	/**
	 * Sets the matched class number.
	 *
	 * @param matchedClassNumber the new matched class number
	 */
	public void setMatchedClassNumber(Integer matchedClassNumber) {
		this.matchedClassNumber = matchedClassNumber;
	}

	/**
	 * Gets the matched term text.
	 *
	 * @return the matched term text
	 */
	public String getMatchedTermText() {
		return matchedTermText;
	}

	/**
	 * Sets the matched term text.
	 *
	 * @param matchedTermText the new matched term text
	 */
	public void setMatchedTermText(String matchedTermText) {
		this.matchedTermText = matchedTermText;
	}

	/**
	 * Gets the identifier.
	 *
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Sets the identifier.
	 *
	 * @param identifier the new identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Gets the matched term position in input term list.
	 *
	 * @return the matched term position in input term list
	 */
	public Integer getMatchedTermPositionInInputTermList() {
		return matchedTermPositionInInputTermList;
	}

	/**
	 * Sets the matched term position in input term list.
	 *
	 * @param matchedTermPositionInInputTermList the new matched term position in input term list
	 */
	public void setMatchedTermPositionInInputTermList(
			Integer matchedTermPositionInInputTermList) {
		this.matchedTermPositionInInputTermList = matchedTermPositionInInputTermList;
	}

	/**
	 * Gets the matched term position in input term.
	 *
	 * @return the matched term position in input term
	 */
	public Integer getMatchedTermPositionInInputTerm() {
		return matchedTermPositionInInputTerm;
	}

	/**
	 * Sets the matched term position in input term.
	 *
	 * @param matchedTermPositionInInputTerm the new matched term position in input term
	 */
	public void setMatchedTermPositionInInputTerm(
			Integer matchedTermPositionInInputTerm) {
		this.matchedTermPositionInInputTerm = matchedTermPositionInInputTerm;
	}

	/**
	 * Gets the match rank.
	 *
	 * @return the match rank
	 */
	public Integer getMatchRank() {
		return matchRank;
	}

	/**
	 * Sets the match rank.
	 *
	 * @param matchRank the new match rank
	 */
	public void setMatchRank(Integer matchRank) {
		this.matchRank = matchRank;
	}

	/**
	 * Gets the legacy term frequency.
	 *
	 * @return the legacy term frequency
	 */
	public Integer getLegacyTermFrequency() {
		return legacyTermFrequency;
	}

	/**
	 * Sets the legacy term frequency.
	 *
	 * @param legacyTermFrequency the new legacy term frequency
	 */
	public void setLegacyTermFrequency(Integer legacyTermFrequency) {
		this.legacyTermFrequency = legacyTermFrequency;
	}

	/**
	 * Gets the legacy term first use date.
	 *
	 * @return the legacy term first use date
	 */
	public Date getLegacyTermFirstUseDate() {
		return DateUtil.cloneDate(legacyTermFirstUseDate);
	}

	/**
	 * Sets the legacy term first use date.
	 *
	 * @param legacyTermFirstUseDate the new legacy term first use date
	 */
	public void setLegacyTermFirstUseDate(Date legacyTermFirstUseDate) {
		this.legacyTermFirstUseDate = DateUtil
				.cloneDate(legacyTermFirstUseDate);
	}

	/**
	 * Gets the legacy term last use date.
	 *
	 * @return the legacy term last use date
	 */
	public Date getLegacyTermLastUseDate() {
		return DateUtil.cloneDate(legacyTermLastUseDate);
	}

	/**
	 * Sets the legacy term last use date.
	 *
	 * @param legacyTermLastUseDate the new legacy term last use date
	 */
	public void setLegacyTermLastUseDate(Date legacyTermLastUseDate) {
		this.legacyTermLastUseDate = DateUtil.cloneDate(legacyTermLastUseDate);
	}

	public List<String> getMatchedTerminologySourceList() {
		return matchedTerminologySourceList;
	}

	public void setMatchedTerminologySourceList(List<String> matchedTerminologySourceList) {
		this.matchedTerminologySourceList = matchedTerminologySourceList;
	}
}