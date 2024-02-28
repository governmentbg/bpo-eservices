/*******************************************************************************
 * * $Id:: TermsToBeValidated.java 14329 2012-10-29 13:02:02Z karalch            $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification.wrapper;

import java.io.Serializable;

import eu.ohim.sp.core.domain.id.Id;

public class TermsToBeValidated extends Id implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3873034249438290848L;
	
	private String office;
	private String language;
	private String terms;
	private String niceClass;
	
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
	public String getNiceClass() {
		return niceClass;
	}
	public void setNiceClass(String niceClass) {
		this.niceClass = niceClass;
	}
	@Override
	public String toString() {
		return "TermsToBeValidated [office=" + office + ", language="
				+ language + ", terms=" + terms + ", niceClass=" + niceClass
				+ "]";
	}
	
	
	
}
