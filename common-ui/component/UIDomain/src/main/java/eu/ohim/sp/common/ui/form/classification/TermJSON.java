/*******************************************************************************
 * * $Id:: TermJSON.java 55918 2013-01-31 21:29:37Z karalch                      $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.classification;

import java.util.ArrayList;
import java.util.Collection;

public class TermJSON {

	public static final String EDITABLE = "editable";
	public static final String VALID = "valid";
	public static final String NOTFOUND = "notfound";
	public static final String INVALID = "invalid";

	public static final String NICE_VALID = "nicevalid";
	public static final String NICE_NOTFOUND = "nicenotfound";

	private String id;
	private String name;
	private String cat;
	private String langId;
	private String termStatus;
	private String validationState;
	private String niceValidationState;
	private String firstLanguageDescription;
	private String parents;
	private boolean secondLanguage;
	private Collection<TermJSON> relatedTerms;
	private boolean containsAllNiceClassHeading;
	private boolean disabledRemoval = false;
	private String taxonomyPath;

	public String getTaxonomyPath() {
		return taxonomyPath;
	}

	public void setTaxonomyPath(String taxonomyPath) {
		this.taxonomyPath = taxonomyPath;
	}

	public TermJSON() {
		relatedTerms = new ArrayList<TermJSON>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public String getTermStatus() {
		return termStatus;
	}
	public void setTermStatus(String termStatus) {
		this.termStatus = termStatus;
	}
	public String getValidationState() {
		return validationState;
	}
	public void setValidationState(String validationState) {
		this.validationState = validationState;
	}
	public Collection<TermJSON> getRelatedTerms() {
		return relatedTerms;
	}
	public void setRelatedTerms(Collection<TermJSON> relatedTerms) {
		this.relatedTerms = relatedTerms;
	}

	/**
	 * @return the langId
	 */
	public String getLangId() {
		return langId;
	}

	/**
	 * @param langId the langId to set
	 */
	public void setLangId(String langId) {
		this.langId = langId;
	}

	public String getFirstLanguageDescription() {
		return firstLanguageDescription;
	}

	public void setFirstLanguageDescription(String firstLanguageDescription) {
		this.firstLanguageDescription = firstLanguageDescription;
	}

	public boolean isSecondLanguage() {
		return secondLanguage;
	}

	public void setSecondLanguage(boolean secondLanguage) {
		this.secondLanguage = secondLanguage;
	}

	public boolean isContainsAllNiceClassHeading() {
		return containsAllNiceClassHeading;
	}

	public void setContainsAllNiceClassHeading(boolean containsAllNiceClassHeading) {
		this.containsAllNiceClassHeading = containsAllNiceClassHeading;
	}

	public boolean isDisabledRemoval() {
		return disabledRemoval;
	}

	public void setDisabledRemoval(boolean disabledRemoval) {
		this.disabledRemoval = disabledRemoval;
	}

	public String getNiceValidationState() {
		return niceValidationState;
	}

	public void setNiceValidationState(String niceValidationState) {
		this.niceValidationState = niceValidationState;
	}
}
