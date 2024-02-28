/*
 *  CoreDomain:: LegalActVersion 02/10/13 16:05 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.opposition;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.id.Id;

public class LegalActVersion extends Id implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codeVersion;
	private String nameVersion;
	private Date entryForceDate;
	private Date endApplicabilityDate;
	private List<LawArticle> articles;
	
	
	/**
	 * @return the codeVersion
	 */
	public String getCodeVersion() {
		return codeVersion;
	}
	/**
	 * @param codeVersion the codeVersion to set
	 */
	public void setCodeVersion(String codeVersion) {
		this.codeVersion = codeVersion;
	}
	/**
	 * @return the articles
	 */
	public List<LawArticle> getArticles() {
		return articles;
	}
	/**
	 * @param articles the articles to set
	 */
	public void setArticles(List<LawArticle> articles) {
		this.articles = articles;
	}
	/**
	 * @return the nameVersion
	 */
	public String getNameVersion() {
		return nameVersion;
	}
	/**
	 * @param nameVersion the nameVersion to set
	 */
	public void setNameVersion(String nameVersion) {
		this.nameVersion = nameVersion;
	}
	/**
	 * @return the entryForceDate
	 */
	public Date getEntryForceDate() {
		return DateUtil.cloneDate(entryForceDate);
	}
	/**
	 * @param entryForceDate the entryForceDate to set
	 */
	public void setEntryForceDate(Date entryForceDate) {
		this.entryForceDate = DateUtil.cloneDate(entryForceDate);
	}
	/**
	 * @return the endApplicabilityDate
	 */
	public Date getEndApplicabilityDate() {
		return DateUtil.cloneDate(endApplicabilityDate);
	}
	/**
	 * @param endApplicabilityDate the endApplicabilityDate to set
	 */
	public void setEndApplicabilityDate(Date endApplicabilityDate) {
		this.endApplicabilityDate = DateUtil.cloneDate(endApplicabilityDate);
	}
		
		
}
