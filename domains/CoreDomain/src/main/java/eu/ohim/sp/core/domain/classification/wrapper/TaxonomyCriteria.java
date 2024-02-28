/*
 *  CoreDomain:: TaxonomyCriteria 16/10/13 20:16 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.classification.wrapper;

import java.io.Serializable;

//Id has been removed has no sense for criteria
public class TaxonomyCriteria extends GeneralTermCriteria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2500140599798830169L;
	
	private Integer levelLimit;
	private String taxoConceptNodeId;
	
	public Integer getLevelLimit() {
		return levelLimit;
	}
	public void setLevelLimit(Integer levelLimit) {
		this.levelLimit = levelLimit;
	}
	public String getTaxoConceptNodeId() {
		return taxoConceptNodeId;
	}
	public void setTaxoConceptNodeId(String taxoConceptNodeId) {
		this.taxoConceptNodeId = taxoConceptNodeId;
	}

}