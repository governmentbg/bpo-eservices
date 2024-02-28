/*******************************************************************************
 * * $Id::                                                                       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import eu.ohim.sp.common.ui.flow.section.LanguagesFlowBean;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TaxonomyConceptNodeTreeView;
import eu.ohim.sp.common.ui.form.classification.TermForm;
import eu.ohim.sp.common.ui.form.classification.TermJSON;
import eu.ohim.sp.common.ui.form.validation.ErrorType;
import eu.ohim.sp.core.domain.classification.MatchedTerm;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyConceptNode;
import eu.ohim.sp.ui.tmefiling.flow.GoodsServicesFlowBean;
import eu.ohim.sp.ui.tmefiling.service.interfaces.GoodsServicesServiceInterface;

public class TaxonomyUtil {

	/**
	 * Retrieves the taxonomy as a list and it returns it as a tree
	 * @param originalList the original list of the taxonomy
	 * @param taxoConceptNodeId the taxonomy concept id for which we retrieve the subtree.
	 * 		  If it is null it gets the whole tree
	 * @return the taxonomy tree view
	 */
	public static Collection<TaxonomyConceptNodeTreeView> getTaxonomyConceptNodes(
			Collection<TaxonomyConceptNode> originalList,
			String taxoConceptNodeId) {
		Collection<TaxonomyConceptNodeTreeView> treeView = new ArrayList<TaxonomyConceptNodeTreeView>();

		if (originalList != null) {
			for (TaxonomyConceptNode taxonomyConceptNode : originalList) {
				if ((taxonomyConceptNode.getParentId() == null)
						|| (taxoConceptNodeId != null && taxoConceptNodeId.equals(taxonomyConceptNode.getParentId()))) {
					treeView.add(new TaxonomyConceptNodeTreeView(
							taxonomyConceptNode));
				}
			}

			for (TaxonomyConceptNode taxonomyConceptNode : originalList) {
				TaxonomyConceptNodeTreeView newNode = new TaxonomyConceptNodeTreeView(
						taxonomyConceptNode);
				for (TaxonomyConceptNodeTreeView root : treeView) {
					TaxonomyConceptNodeTreeView parentNode = findParent(root,
							taxonomyConceptNode.getParentId(), taxonomyConceptNode.getLevel());
					if (parentNode != null) {
						parentNode.getChildren().add(newNode);
						newNode.setParent(parentNode);
						break;
					}
				}
			}
		}

		return treeView;
	}

	/**
	 * Retrieves the parent node with the given id
	 * @param root root node is the under which browses for the node
	 * @param id the id of the parent node
	 * @param level the level of the node for which we search for his parent. 
	 * 		  This always should be equal the parent_level+1
	 * @return the Taxonomy Node
	 */
	private static TaxonomyConceptNodeTreeView findParent(
			TaxonomyConceptNodeTreeView root, String id, Integer level) {
		Stack<TaxonomyConceptNodeTreeView> stack = new Stack<TaxonomyConceptNodeTreeView>();
		stack.push(root);

		while (!stack.empty()) {
			TaxonomyConceptNodeTreeView node = stack.pop();

			if (node.getId().equals(id) 
					&& node.getLevel()!=null
					&& (node.getLevel()==level-1)) {
				return node;
			}
			if (node.getChildren().size() > 0) {
				for (TaxonomyConceptNodeTreeView child : node.getChildren()) {
					stack.push(child);
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param taxonomyConcept
	 * @return
	 */
	public static Collection<TaxonomyConceptNodeTreeView>  getMinifiedView(Collection<TaxonomyConceptNodeTreeView> taxonomyConcept) {
		Collection<TaxonomyConceptNodeTreeView> minifiedTree = new ArrayList<TaxonomyConceptNodeTreeView>();
		
		for (TaxonomyConceptNodeTreeView node : taxonomyConcept) {
			TaxonomyConceptNodeTreeView minifiedNode = new TaxonomyConceptNodeTreeView(node);
			minifiedNode.setChildren(getMinifiedView(node.getChildren()));
			minifiedTree.add(minifiedNode);
		}
		return minifiedTree;
	}

	/**
	 * 
	 * @param taxonomyConcept
	 * @param levelLimit
	 * @param classScopeLevel
	 * @return
	 */
	public static Collection<TaxonomyConceptNodeTreeView> limitMinifiedView(Collection<TaxonomyConceptNodeTreeView> taxonomyConcept, Integer levelLimit, Integer classScopeLevel) {
		Collection<TaxonomyConceptNodeTreeView> minifiedTree = new ArrayList<TaxonomyConceptNodeTreeView>();
		
		for (TaxonomyConceptNodeTreeView node : taxonomyConcept) {
			if (classScopeLevel.equals(node.getLevel())) {
				node.setClassScope(true);
			}
		}
		
		for (TaxonomyConceptNodeTreeView node : taxonomyConcept) {
			if (levelLimit==null || node.getLevel()<levelLimit) {
				minifiedTree.addAll(limitMinifiedView(node.getChildren(), levelLimit, classScopeLevel));
			} else {
				return taxonomyConcept;
			}
		}
		return minifiedTree;
	}
	
	/**
	 * Generates a json term returned by GS services
	 * @param good the goods and services that are returned by the flowbean
	 * @param flowBean the flow bean that contains more information
	 * @param langId the langid that we expect
	 * @return a json term
	 */
	public static List<TermJSON> generateTermJSON(GoodAndServiceForm good, GoodsServicesFlowBean flowBean, String langId, boolean containsAllNiceClassHeading, boolean disabledRemoval) {
		List<TermJSON> terms = new ArrayList<TermJSON>();
		
		if (good!=null 
				&& good.getLangId() != null
				&& good.getLangId().equals(langId)) {
			if (good.getTermForms().size()==0) {
				TermJSON term = new TermJSON();
				term.setCat(good.getClassId());
				term.setLangId(langId);
				
				term.setSecondLanguage(good.getLangId().equals(((LanguagesFlowBean) flowBean).getSecLang()));
				
				if (term.isSecondLanguage()) {
					StringBuffer str = new StringBuffer();
					for (TermForm firstLanguageDesc : ((GoodsServicesFlowBean) flowBean).getGoodsAndService(((LanguagesFlowBean) flowBean).getFirstLang(), good.getClassId()).getTermForms()) {
						str.append(firstLanguageDesc.getDescription()).append(";");
					}
					term.setFirstLanguageDescription(str.toString());
				}
				term.setContainsAllNiceClassHeading(containsAllNiceClassHeading);
				term.setDisabledRemoval(disabledRemoval);
				terms.add(term);
			}
			for (TermForm termForm : good.getTermForms()) {
				TermJSON term = new TermJSON();
				term.setCat(termForm.getIdClass());
				term.setLangId(langId);

				term.setSecondLanguage(good.getLangId().equals(((LanguagesFlowBean) flowBean).getSecLang()));

				if (term.isSecondLanguage()) {
					StringBuffer str = new StringBuffer();
					for (TermForm firstLanguageDesc : ((GoodsServicesFlowBean) flowBean).getGoodsAndService(((LanguagesFlowBean) flowBean).getFirstLang(), termForm.getIdClass()).getTermForms()) {
						str.append(firstLanguageDesc.getDescription()).append(";");
					}
					term.setFirstLanguageDescription(str.toString());
				}

				term.setName(termForm.getDescription());

				if (termForm.getError() == null) {
					term.setValidationState(TermJSON.VALID);
					term.setRelatedTerms(null);
				} else {
					if (termForm.getError().getVerificationAssessment()
							.equals(ErrorType.NOT_FOUND)) {
						term.setValidationState(TermJSON.NOTFOUND);
					} else if (termForm.getError()
							.getVerificationAssessment()
							.equals(ErrorType.REJECTED_TERM)) {
						term.setValidationState(TermJSON.INVALID);
					} else {
						term.setValidationState(TermJSON.EDITABLE);
					}

					term.setTermStatus(termForm.getError()
							.getVerificationAssessment());
					if (termForm.getError().getMatchedTerms() == null
							|| termForm.getError().getMatchedTerms().size() == 0) {
						term.setRelatedTerms(null);
					} else {
						for (MatchedTerm matchedTerm : termForm.getError()
								.getMatchedTerms()) {
							TermJSON relatedTerm = new TermJSON();
							relatedTerm.setName(matchedTerm
									.getMatchedTermText());
							relatedTerm.setCat(matchedTerm
									.getMatchedClassNumber().toString());
							relatedTerm.setTermStatus("Valid term");
							relatedTerm.setLangId(good.getLangId());
							relatedTerm.setValidationState(TermJSON.VALID);
							term.getRelatedTerms().add(relatedTerm);
						}
					}
				}

				if (termForm.getNiceError() == null) {
					term.setNiceValidationState(TermJSON.NICE_VALID);
				} else {
					term.setNiceValidationState(TermJSON.NICE_NOTFOUND);
				}

				term.setContainsAllNiceClassHeading(containsAllNiceClassHeading);
				term.setDisabledRemoval(disabledRemoval);
				terms.add(term);
			}
		}

		return terms;
	}


	public static void fillInTaxonomyPathInfo(List<TermJSON> jsonList, GoodsServicesServiceInterface goodsServices ){
		for (TermJSON termJSON : jsonList) {
			if (termJSON.getValidationState().equals(TermJSON.VALID)) {
				String classPath = "";
				Collection<TaxonomyConceptNode> taxonomyParentConceptNodes = goodsServices.getParentConceptNodes(termJSON.getLangId(), termJSON.getName());
				for (TaxonomyConceptNode taxonomyParentConceptNode : taxonomyParentConceptNodes) {
					if (taxonomyParentConceptNode.getLevel().equals(1) && termJSON.getCat().equals(taxonomyParentConceptNode.getNiceClass().toString())) {
						classPath = taxonomyParentConceptNode.getText();
					}
					if (taxonomyParentConceptNode.getLevel().equals(2) && !termJSON.getName().equals(taxonomyParentConceptNode.getText())) {
						if (termJSON.getCat().equals(taxonomyParentConceptNode.getNiceClass().toString())) {
							classPath = classPath + " > " + taxonomyParentConceptNode.getText();
							break;
						}
					}
				}
				termJSON.setTaxonomyPath(classPath);
			}
		}
	}

	/**
	 * Find class scope node.
	 *
	 * @param taxonomyConceptNodes the taxonomy concept nodes
	 * @param classScopeId the class scope id
	 * @param level the level
	 * @return the taxonomy concept node tree view
	 */
	public static TaxonomyConceptNodeTreeView findClassScopeNode(Collection<TaxonomyConceptNodeTreeView> taxonomyConceptNodes, String classScopeId, int level) {
		if (taxonomyConceptNodes!=null) {
			for (TaxonomyConceptNodeTreeView node : taxonomyConceptNodes) {
				if (node != null
						&& node.getNiceClass() != null
						&& node.getNiceClass().toString().equals(classScopeId)
						&& node.getLevel().intValue() == level) {
					return node;
				} else if (node != null) {
					TaxonomyConceptNodeTreeView result = findClassScopeNode(node.getChildren(), classScopeId, level);
					if (result != null) {
						return result;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Find taxonomy node.
	 *
	 * @param taxonomyConceptNodes the taxonomy concept nodes
	 * @param id the id
	 * @return the taxonomy concept node tree view
	 */
	public static TaxonomyConceptNodeTreeView findTaxonomyNode(Collection<TaxonomyConceptNodeTreeView> taxonomyConceptNodes, String id) {
		if (taxonomyConceptNodes!=null) {
			for (TaxonomyConceptNodeTreeView node : taxonomyConceptNodes) {
				if (node != null
						&& node.getId().equals(id)) {
					return node;
				} else if (node != null) {
					TaxonomyConceptNodeTreeView result = findTaxonomyNode(node.getChildren(), id);
					if (result != null) {
						return result;
					}
				}
			}
		}
		return null;
	}
}
