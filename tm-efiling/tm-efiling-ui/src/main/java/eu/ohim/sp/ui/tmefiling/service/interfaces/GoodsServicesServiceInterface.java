/*******************************************************************************
 * * $Id::                                                                       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.service.interfaces;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TaxonomyConceptNodeTreeView;

import eu.ohim.sp.ui.tmefiling.form.terms.SearchTermResults;
import org.springframework.validation.BindingResult;

import eu.ohim.sp.core.domain.classification.wrapper.ClassScope;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyConceptNode;
import eu.ohim.sp.core.domain.classification.wrapper.Term;
import eu.ohim.sp.ui.tmefiling.flow.GoodsServicesFlowBean;


public interface GoodsServicesServiceInterface {

	/**
	 * Search terms.
	 *
	 * @param language the language
	 * @param term the term
	 * @param filterClass the filter class
	 * @param taxoConceptNodeId the taxo concept node id
	 * @param size the size
	 * @param showNonTaxoTermsOnly the show non taxo terms only
	 * @param page the page
	 * @param sortBy the sort by
	 * @param orderBy the order by
	 * @param sources the sources - Nice, Harmonized or both(null)
	 * @return the search term results
	 */
	SearchTermResults searchTerms(String language, String term, String filterClass,
								  String taxoConceptNodeId, Integer size, Boolean showNonTaxoTermsOnly, String page, String sortBy, String orderBy, String sources);


    /**
     * Searches the terms that are available with the current criteria
     * @param language language on which the search will be done
     * @param term the tern that we are looking for
     * @param filterClass filter specific nice class
     * @param taxoConceptNodeId under which concept id the search will be, if empty on the root
     * @param size number of results that we are waiting for
     * @param page the page of the results that we are interested
     * @param sortBy
     * @param orderBy
     * @return PageForm with the search result
     */
	void searchTerms(GoodsServicesFlowBean flowBean, String language, String term, String filterClass,
			String taxoConceptNodeId, Integer size, String page, String sortBy, String orderBy, String sources);

	/**
	 *
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
	 * @param flowBean
	 */
	void verifyListOfGS(GoodsServicesFlowBean flowBean);
	
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
	 * @return the autocomplete
	 */
	List<String> getAutocomplete(String language, String searchCriteria);

	/**
	 * Verifies the list of terms on an external service
	 * @param language language on which the search will be done
	 * @param niceClass
	 * @param terms
	 * @return GoodAndServiceForm
	 */
	GoodAndServiceForm verifyListOfTerms(String language, String niceClass, String terms);

	/**
	 * Checks that the list of GS contains all the terms of the Alpha List
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
	 * @param goodsForm extra rules information
     * @param result extra rules information
     * @return the list of possible errors
	 */
	void validateGoodsAndServicesDescription(GoodAndServiceForm goodsForm, BindingResult result);
	
	/**
	 * Gets the class numbers that are available to the current search
	 * @param language language on which the search will be done
	 * @param term the tern that we are looking for
	 * @return a list of all available class numbers
	 */
	List<Integer> getDistribution(String language, String term);

	void verifyTermsByNiceAlpha(GoodsServicesFlowBean flowBean, String lang);

	void verifyTermsByNiceAlpha(GoodAndServiceForm goodAndServiceForm);
}
