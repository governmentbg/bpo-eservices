/*******************************************************************************
 * * $Id:: GoodsServicesServiceInterface.java 124565 2013-06-20 15:30:56Z karalc#$
 * *       . * .
 * *     * RRRR  *    Copyright Â© 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.eservices.ui.service.interfaces;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TaxonomyConceptNodeTreeView;
import eu.ohim.sp.core.domain.classification.wrapper.ClassScope;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyConceptNode;
import eu.ohim.sp.core.domain.classification.wrapper.Term;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;


public interface GoodsServicesServiceInterface {

	/** 
	 * 
	 * This has change in the tm-efilling version of OHIM; if I need it, I should ask Christos before I proceed to use it.
	 *  
	 * Searches the terms that are available with the current criteria
	 * Results are loaded on the flowbean currently
	 * @param office the office on which it will be the search
	 * @param language language on which the search will be done
	 * @param term the tern that we are looking for
	 * @param filterClass filter specific nice class
	 * @param taxoConceptNodeId under which concept id the search will be, if empty on the root
	 * @param size number of results that we are waiting for
	 * @param page the page of the results that we are interested
	 * @param sortBy
	 * @param orderBy
	 * @return PageForm with the search result
	 
	Integer searchTerms(ESFlowBean flowBean, String language, String term, String filterClass,
			String taxoConceptNodeId, Integer size, Boolean showNonTaxoTermsOnly, String page, String sortBy, String orderBy);
	
	*/

	/**
	 * 
	 * @param office the office on which it will be the search
	 * @param language language on which the search will be done
	 * @param term
	 * @param levelLimit
	 * @param taxoConceptNodeId under which concept id the search will be, if empty on the root
	 * @return TaxonomyConceptNodeTreeView collection
	 */
	Collection<TaxonomyConceptNodeTreeView> getTaxonomy(String language, 
			String term, Integer levelLimit, String taxoConceptNodeId);
	
	Collection<TaxonomyConceptNode> getParentConceptNodes(String language, 
			String term);

	/**
	 * Verifies the collection of goodAndServiceForms of a flow bean on an external 
	 * service and returns a list of verified goodAndServiceForms 
	 * @param office the office on which it will be the search
	 * @param goodAndServiceForms 
	 */
	void verifyListOfGS(ESFlowBean flowBean);
	
	/**
	 * Returns a map containing all the class scope for each class
	 * @param language language on which the search will be done
	 * @param niceClassHeadings if it is null returns all the nice class scopes
	 * @return a map containing all the class scope for each class
	 */
	Map<String, ClassScope> getClassScope(String language, String niceClassHeadings);

	/**
	 * Returns alternative suggestions on the one provided
	 * @param language the language of the suggestion
	 * @param searchCriteria the term entered
	 * @return
	 */
	List<String> didYouMean(String language, String searchCriteria);

	/**
	 *  This has change in the tm-efilling version of OHIM; if I need it, I should ask Christos before I proceed to use it.
	 *  
	 * Returns an list of terms that match the text provided
	 * @param language the language of the term provided 
	 * @param text the input text
	 * @return a list of terms
	 
	public List<String> autocompleteTerm(String language, String text);
	*/


	/**
	 * Verifies the list of terms on an external service
	 * @param office the office on which it will be the search
	 * @param language language on which the search will be done
	 * @param niceClass
	 * @param terms
	 * @return GoodAndServiceForm
	 */
	GoodAndServiceForm verifyListOfTerms(String language, String niceClass, String terms);

	/**
	 * Checks that the list of GS contains all the terms of the Alpha List
	 * @param office the office on which it will be the search
	 * @param goodsAndServiceForm the GS containing the terms
	 * @return
	 */
	boolean containsAllNiceClassHeading(GoodAndServiceForm goodsAndServiceForm);
	
	/**
	 * 
	 * @param office the office on which it will be the search
	 * @param niceClass 
	 * @param language the language for which we import the GS
	 * @return
	 */
	Collection<Term> importCachedNiceClassHeading(String office, String niceClass, String language);

	/**
	 * Calls an external service to get a nice class heading
	 * @param niceClass the imported nice class heading
	 * @param language the language of the imported nice class heading
	 * @return the imported nice class heading
	 */
	GoodAndServiceForm importNiceClassHeading(String niceClass, String language);
	
	/**
	 * Validates the description of the Goods and Services introduced by the user
	 * @param rulesInformation extra rules information
	 * @return the list of possible errors
	 */
	void validateGoodsAndServicesDescription(GoodAndServiceForm goodsForm, BindingResult result);
	
	/**
	 * Gets the class numbers that are available to the current search
	 * @param office the office on which it will be the search
	 * @param language language on which the search will be done
	 * @param term the tern that we are looking for
	 * @return a list of all available class numbers
	 */
	List<Integer> getDistribution(String language, String term);

}
