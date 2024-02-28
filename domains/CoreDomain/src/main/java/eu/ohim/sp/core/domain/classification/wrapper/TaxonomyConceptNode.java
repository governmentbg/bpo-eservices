/*******************************************************************************
 * * $Id:: TaxonomyConceptNode.java 14329 2012-10-29 13:02:02Z karalch           $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification.wrapper;

import java.io.Serializable;

public class TaxonomyConceptNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3184652416832989790L;
	
	private String id;
	private String taxonomyConceptNodeId;
	private String parentId;
	private String text;
	private Integer level;
	private Integer numTermsSatisfyingCriteria;
	private Integer niceClass;
	private boolean nonTaxonomyParent;
	private boolean leaf;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaxonomyConceptNodeId() {
		return taxonomyConceptNodeId;
	}
	public void setTaxonomyConceptNodeId(String taxonomyConceptNodeId) {
		this.taxonomyConceptNodeId = taxonomyConceptNodeId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getNumTermsSatisfyingCriteria() {
		return numTermsSatisfyingCriteria;
	}
	public void setNumTermsSatisfyingCriteria(Integer numTermsSatisfyingCriteria) {
		this.numTermsSatisfyingCriteria = numTermsSatisfyingCriteria;
	}
	public Integer getNiceClass() {
		return niceClass;
	}
	public void setNiceClass(Integer niceClass) {
		this.niceClass = niceClass;
	}
	/**
	 * @return the nonTaxonomyParent
	 */
	public boolean isNonTaxonomyParent() {
		return nonTaxonomyParent;
	}
	/**
	 * @param nonTaxonomyParent the nonTaxonomyParent to set
	 */
	public void setNonTaxonomyParent(boolean nonTaxonomyParent) {
		this.nonTaxonomyParent = nonTaxonomyParent;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	@Override
	public String toString() {
		return "TaxonomyConceptNode [id=" + id + ", taxonomyConceptNodeId="
				+ taxonomyConceptNodeId + ", parentId=" + parentId + ", text="
				+ text + ", level=" + level + ", numTermsSatisfyingCriteria="
				+ numTermsSatisfyingCriteria + ", niceClass=" + niceClass
				+ ", nonTaxonomyParent=" + nonTaxonomyParent + ", leaf=" + leaf
				+ "]";
	}
	
}
