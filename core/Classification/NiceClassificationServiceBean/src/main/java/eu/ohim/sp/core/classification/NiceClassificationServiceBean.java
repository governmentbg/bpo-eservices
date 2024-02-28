/*
 *  NiceClassificationService:: NiceClassificationService 04/11/13 12:42 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.classification;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.domain.classification.wrapper.*;
import eu.ohim.sp.external.classification.NiceClassificationClientInside;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.Collection;
import java.util.List;

/**
 * The Class ClassificationService.
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Stateless
public class NiceClassificationServiceBean implements NiceClassificationServiceRemote, NiceClassificationServiceLocal {

	/**
	 * The adapter.
	 */
	@Inject @NiceClassificationClientInside
	private NiceClassificationService adapter;

	@Override
	public SearchResults searchTerm(SearchTermCriteria searchTermCriteria) {
		return adapter.searchTerm(searchTermCriteria);
	}


	@Override
	public Collection<TermsValidated> verifyListOfTerms(List<TermsToBeValidated> terms) {
		return adapter.verifyListOfTerms(terms);
    }

	@Override
	public Collection<TaxonomyConceptNode> getTaxonomy(
			TaxonomyCriteria taxononyCriteria) {
        return adapter.getTaxonomy(taxononyCriteria);
	}

	@Override
	public DistributionResults getTermDistribution(
			DistributionCriteria distributionCriteria) {
		return adapter.getTermDistribution(distributionCriteria);
	}

	@Override
	public Collection<Term> getNiceClassHeading(String niceClassHeading,
												String language) {
		return adapter.getNiceClassHeading(niceClassHeading, language);
	}

	@Override
	public List<String> getAutocomplete(String language, String searchCriteria) {
		return adapter.getAutocomplete(language, searchCriteria);
	}

	@Override
	public Collection<ClassScope> getClassScopes(String language, String niceClassHeadings) {
		return adapter.getClassScopes(language, niceClassHeadings);
	}
}
