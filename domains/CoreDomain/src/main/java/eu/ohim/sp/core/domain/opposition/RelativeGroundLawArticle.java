/*
 *  CoreDomain:: RelativeGroundLawArticle 16/10/13 20:16 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.opposition;

import java.util.List;

public class RelativeGroundLawArticle extends LawArticle {
	
	private static final long serialVersionUID = 1L;
	

	private Boolean reputationClaim;
	private Boolean exclusivity;
	private List <String> excludedLawArticleReference;
	/**
	 * @return the reputationClaim
	 */
	public Boolean getReputationClaim() {
		return reputationClaim;
	}
	/**
	 * @param reputationClaim the reputationClaim to set
	 */
	public void setReputationClaim(Boolean reputationClaim) {
		this.reputationClaim = reputationClaim;
	}
	/**
	 * @return the exclusivity
	 */
	public Boolean getExclusivity() {
		return exclusivity;
	}
	/**
	 * @param exclusivity the exclusivity to set
	 */
	public void setExclusivity(Boolean exclusivity) {
		this.exclusivity = exclusivity;
	}
	/**
	 * @return the excludedLawArticleReference
	 */
	public List<String> getExcludedLawArticleReference() {
		return excludedLawArticleReference;
	}
	/**
	 * @param excludedLawArticleReference the excludedLawArticleReference to set
	 */
	public void setExcludedLawArticleReference(
			List<String> excludedLawArticleReference) {
		this.excludedLawArticleReference = excludedLawArticleReference;
	}


}
