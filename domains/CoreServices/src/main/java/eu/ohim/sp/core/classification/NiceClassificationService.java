/*******************************************************************************
 * * $Id:: NiceClassificationService.java 134926 2013-08-19 12:53:49Z j#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.classification;

import java.util.Collection;
import java.util.List;

import eu.ohim.sp.core.domain.classification.wrapper.ClassScope;
import eu.ohim.sp.core.domain.classification.wrapper.DistributionCriteria;
import eu.ohim.sp.core.domain.classification.wrapper.DistributionResults;
import eu.ohim.sp.core.domain.classification.wrapper.SearchResults;
import eu.ohim.sp.core.domain.classification.wrapper.SearchTermCriteria;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyConceptNode;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyCriteria;
import eu.ohim.sp.core.domain.classification.wrapper.Term;
import eu.ohim.sp.core.domain.classification.wrapper.TermsToBeValidated;
import eu.ohim.sp.core.domain.classification.wrapper.TermsValidated;

/**
 * The Nice Classification business interface ({@code NiceClassificationService}) 
 * provides the {@code TM e-Filing} User Interface module with methods to classify
 * trade mark applications (goods and services) according to the Nice classification.
 * <p>
 * The reference implementation {@code NiceClassificationServiceBean} interfaces with 
 * the following SP component:
 * <dl>
 * <dt><b>Business:</b>
 * </dl> 
 * <ul>
 * 		<li>Nice Classification Consumer: external searches and retrievals of goods 
 * 			and services.</li>
 * </ul> 
 *
 * @see <a href="http://www.wipo.int/classifications/nice/en/" target="_blank">Nice Classification</a>
 *
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
public interface NiceClassificationService {

	/**
	 * The purpose of the service is to check presence of the given term in terminology
	 * sources accepted by a specified office. Search can be further enhanced by specifying
	 * Nice classes term should belong to as well as desired search mode.
	 *
	 * @param searchTermCriteria
	 *
	 * @return a collection of terms
	 */
	SearchResults searchTerm(SearchTermCriteria searchTermCriteria);

	/**
	 * The purpose of the service is to check whether a given list of terms in given
	 * class is accepted by specified office. List of terms is a string that may contain
	 * language-specific delimiter which defines boundaries of individual input terms.
	 * Additionally, the verification logic can be enhanced by specifying whether
	 * to perform search on sub-level of expression, whether to search in legacy data
	 * and whether to perform fuzzy search (i.e. searching for similar terms). By default
	 * these three options are all true.
	 * On the service output every input term will be accompanied with an individual verification assessment.
	 *
	 * @param terms the list of terms that we expect to validate
	 *
	 * @return a collection of the validated terms
	 */
	Collection<TermsValidated> verifyListOfTerms(List<TermsToBeValidated> terms);
	
	/**
	 * The purpose of the service is to get the taxonomy structure for goods and 
	 * services, which is filtered on the based of search parameters supplied.
	 *
	 * @param taxononyCriteria
	 *
	 * @return a collection with the taxonomy concepts
	 */
	Collection<TaxonomyConceptNode> getTaxonomy(TaxonomyCriteria taxononyCriteria);
	
	/**
	 * This service returns the distribution of results over nice classes for a 
	 * given input search criteria. Below you may find the technical details on 
	 * the invocation of this web service.
	 *  
	 * @param distributionCriteria
	 *
	 * @return a collection with the classification terms
	 */
	DistributionResults getTermDistribution(DistributionCriteria distributionCriteria);

	/**
	 * Calls an external service to get a nice class heading
	 *
	 * @param niceClassHeading the imported nice class heading
	 * @param language the language of the imported nice class heading
	 *
	 * @return the imported nice class heading collection
	 */
	Collection<Term> getNiceClassHeading(String niceClassHeading, String language);
	
	List<String> getAutocomplete(String language, String searchCriteria);

    Collection<ClassScope> getClassScopes(String language,String niceClassHeadings);

}
