package eu.ohim.sp.ui.tmefiling.service.util;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.common.logging.StatisticalLoggerImpl;
import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.core.domain.classification.wrapper.OrderBy;
import eu.ohim.sp.core.domain.classification.wrapper.SearchTermCriteria;
import eu.ohim.sp.core.domain.classification.wrapper.SortBy;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyCriteria;
import org.apache.log4j.Logger;

public class ClassificationCriteriaUtil {

	private static final Logger logger = Logger.getLogger(ClassificationCriteriaUtil.class);

	public static final String NICECLASS = "niceClass";
	public static final String TEXT = "text";
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	/** The Constant HARMONIZED_SOURCE. */
	public static final String HARMONIZED_SOURCE = "Harmonized";

	public static SearchTermCriteria constructSearchTermCriteria(String office, String termToSearch, String language, String term, String filterClass,
			String taxoConceptNodeId, Integer size, String page, String sortBy, String orderBy, String sources) {
		SearchTermCriteria searchTermCriteria = new SearchTermCriteria();
		searchTermCriteria.setLanguage(language);
		searchTermCriteria.setOffice(office);
		searchTermCriteria.setTerm(termToSearch);
		searchTermCriteria.setSources(sources);

		if (StringUtils.isNotBlank(sortBy) && sortBy.equals(NICECLASS)) {
			searchTermCriteria.setSortBy(SortBy.NICECLASS);
		} else if (StringUtils.isNotBlank(sortBy) && sortBy.equals(TEXT)) {
			searchTermCriteria.setSortBy(SortBy.TEXT);
		} else {
			searchTermCriteria.setSortBy(SortBy.RELEVANCE);
		}
		if (StringUtils.isNotBlank(orderBy) && orderBy.equals(DESC)) {
			searchTermCriteria.setOrderBy(OrderBy.DESC);
		} else {
			searchTermCriteria.setOrderBy(OrderBy.ASC);
		}
		if (StringUtils.isNotBlank(filterClass)) { 
			try {
				List<Integer> filterClasses = new ArrayList<Integer>();
				filterClasses.add(Integer.parseInt(filterClass));
				searchTermCriteria.setNiceClassList(filterClasses);
			} catch (NumberFormatException nfe) {
				//ignore field if it is not parseable
				logger.warn(nfe);
			}
		}

		searchTermCriteria.setTaxoConceptNodeId(taxoConceptNodeId);
		if (page != null) {
			searchTermCriteria.setPage(Integer.valueOf(page));
		}

		searchTermCriteria.setSize(size);
		return searchTermCriteria;
	}

	public static TaxonomyCriteria constructSearchTaxonomyCriteria(String office, String language, 
			String term, Integer levelLimit, String taxoConceptNodeId) {
		TaxonomyCriteria taxonomyCriteria = new TaxonomyCriteria();

		taxonomyCriteria.setLanguage(language);
		taxonomyCriteria.setOffice(office);

		taxonomyCriteria.setTerm(term);

		taxonomyCriteria.setLevelLimit(levelLimit);
		if (levelLimit != null) {
			taxonomyCriteria.setLevelLimit(levelLimit);
		}
		if (StringUtils.isNotBlank(taxoConceptNodeId)) {
			taxonomyCriteria.setTaxoConceptNodeId(taxoConceptNodeId);
		}

		return taxonomyCriteria;
	}


	/**
	 * Construct search term criteria.
	 *
	 * @param office the office
	 * @param term the term
	 * @param language the language
	 * @param filterClass the filter class
	 * @param taxoConceptNodeId the taxo concept node id
	 * @param size the size
	 * @param showNonTaxoTermsOnly the show non taxo terms only
	 * @param page the page
	 * @param sortBy the sort by
	 * @param orderBy the order by
	 * @return the search term criteria
	 */
	public static SearchTermCriteria constructSearchTermCriteria(String office, String term, String language, String filterClass,
																 String taxoConceptNodeId, Integer size, Boolean showNonTaxoTermsOnly, String page, String sortBy, String orderBy, String sources) {
		SearchTermCriteria searchTermCriteria = new SearchTermCriteria();
		searchTermCriteria.setLanguage(language);
		searchTermCriteria.setOffice(office);
		searchTermCriteria.setTerm(term);
		searchTermCriteria.setShowNonTaxoTermsOnly(showNonTaxoTermsOnly);
		searchTermCriteria.setSources(sources);

		if (StringUtils.isNotBlank(sortBy) && sortBy.equals(NICECLASS)) {
			searchTermCriteria.setSortBy(SortBy.NICECLASS);
		} else if (StringUtils.isNotBlank(sortBy) && sortBy.equals(TEXT)) {
			searchTermCriteria.setSortBy(SortBy.TEXT);
		} else {
			searchTermCriteria.setSortBy(SortBy.RELEVANCE);
		}
		if (StringUtils.isNotBlank(orderBy) && orderBy.equals(DESC)) {
			searchTermCriteria.setOrderBy(OrderBy.DESC);
		} else {
			searchTermCriteria.setOrderBy(OrderBy.ASC);
		}
		if (StringUtils.isNotBlank(filterClass)) {
			try {
				List<Integer> filterClasses = new ArrayList<Integer>();
				filterClasses.add(Integer.parseInt(filterClass));
				searchTermCriteria.setNiceClassList(filterClasses);
			} catch (NumberFormatException nfe) {
				//ignore field if it is not parseable
			}
		}

		searchTermCriteria.setTaxoConceptNodeId(taxoConceptNodeId);
		if (page != null) {
			searchTermCriteria.setPage(Integer.valueOf(page));
		}

		searchTermCriteria.setSize(size);
		return searchTermCriteria;
	}
}