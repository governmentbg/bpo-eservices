/*******************************************************************************
 * * $Id:: TaxonomyConceptNodeTreeView.java 54642 2013-01-16 19:45:47Z karalch   $
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

import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyConceptNode;

public class TaxonomyConceptNodeTreeView extends TaxonomyConceptNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2352722144545927581L;
	
	private TaxonomyConceptNodeTreeView parent;
	private Collection<TaxonomyConceptNodeTreeView> children;
	private boolean classScope = false;
	private String classScopeDescription = null;

	public TaxonomyConceptNodeTreeView(TaxonomyConceptNode original) {
		this.setId(original.getId());
		this.setLeaf(original.isLeaf());
		this.setNiceClass(original.getNiceClass());
		this.setLevel(original.getLevel());
		this.setNumTermsSatisfyingCriteria(original.getNumTermsSatisfyingCriteria());
		this.setParentId(original.getParentId());
		this.setText(original.getText());
		
		this.children = new ArrayList<TaxonomyConceptNodeTreeView>();
	}
	
	public TaxonomyConceptNodeTreeView getParent() {
		return parent;
	}
	public void setParent(TaxonomyConceptNodeTreeView parent) {
		this.parent = parent;
	}
	public Collection<TaxonomyConceptNodeTreeView> getChildren() {
		return children;
	}
	public void setChildren(Collection<TaxonomyConceptNodeTreeView> children) {
		this.children = children;
	}

	public boolean isClassScope() {
		return classScope;
	}

	public void setClassScope(boolean classScope) {
		this.classScope = classScope;
	}

	public String getClassScopeDescription() {
		return classScopeDescription;
	}

	public void setClassScopeDescription(String classScopeDescription) {
		this.classScopeDescription = classScopeDescription;
	}

}