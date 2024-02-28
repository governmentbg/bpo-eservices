package eu.ohim.sp.ui.tmefiling.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import eu.ohim.sp.core.domain.classification.wrapper.OrderBy;
import eu.ohim.sp.core.domain.classification.wrapper.SearchTermCriteria;
import eu.ohim.sp.core.domain.classification.wrapper.SortBy;
import eu.ohim.sp.ui.tmefiling.service.util.ClassificationCriteriaUtil;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ClassificationCriteriaUtilTest {

	@Test
	public void sortByNiceClassTest() {

		String office = "office";
		String termToSearch = "search term";
		String language = "hu";
		String term = "term";
		String filterClass = null;
		String taxoConceptNodeId = null;
		Integer size = null;
		String page = null;
		String orderBy = null;
		String sortBy = ClassificationCriteriaUtil.NICECLASS;
		SearchTermCriteria criteria = ClassificationCriteriaUtil.constructSearchTermCriteria(office, termToSearch,
				language, term, filterClass, taxoConceptNodeId, size, page, sortBy, orderBy, null);

		assertEquals(criteria.getSortBy(), SortBy.NICECLASS);

	}

	@Test
	public void sortByTEXTTest() {

		String office = "office";
		String termToSearch = "search term";
		String language = "hu";
		String term = "term";
		String filterClass = null;
		String taxoConceptNodeId = null;
		Integer size = null;
		String page = null;
		String orderBy = null;
		String sortBy = ClassificationCriteriaUtil.TEXT;
		SearchTermCriteria criteria = ClassificationCriteriaUtil.constructSearchTermCriteria(office, termToSearch,
				language, term, filterClass, taxoConceptNodeId, size, page, sortBy, orderBy,null);

		assertEquals(criteria.getSortBy(), SortBy.TEXT);

	}

	public static final Object[] getRelevanceParam() {
		return new String[] { null, "wer2341234123", "Relevance" };
	}

	@Test
	@Parameters(method = "getRelevanceParam")
	public void sortByRelevanceTest(String param) {

		String office = "office";
		String termToSearch = "search term";
		String language = "hu";
		String term = "term";
		String filterClass = null;
		String taxoConceptNodeId = null;
		Integer size = null;
		String page = null;
		String orderBy = null;
		String sortBy = param;
		SearchTermCriteria criteria = ClassificationCriteriaUtil.constructSearchTermCriteria(office, termToSearch,
				language, term, filterClass, taxoConceptNodeId, size, page, sortBy, orderBy, null);

		assertEquals(criteria.getSortBy(), SortBy.RELEVANCE);

	}

	@Test
	public void orderbyDescTest() {

		String office = "office";
		String termToSearch = "search term";
		String language = "hu";
		String term = "term";
		String filterClass = null;
		String taxoConceptNodeId = null;
		Integer size = null;
		String page = null;
		String orderBy = ClassificationCriteriaUtil.DESC;
		String sortBy = "wer2341234123 12341";
		SearchTermCriteria criteria = ClassificationCriteriaUtil.constructSearchTermCriteria(office, termToSearch,
				language, term, filterClass, taxoConceptNodeId, size, page, sortBy, orderBy, null);

		assertEquals(criteria.getOrderBy(), OrderBy.DESC);

	}

	public static final Object[] getASCOrderbyParam() {
		return new String[] { null, "asfasdf", "ASC" };
	}

	@Test
	@junitparams.Parameters(method = "getASCOrderbyParam")
	public void orderbyASCTest(String param) {

		String office = "office";
		String termToSearch = "search term";
		String language = "hu";
		String term = "term";
		String filterClass = null;
		String taxoConceptNodeId = null;
		Integer size = null;
		String page = null;
		String orderBy = param;
		String sortBy = "wer2341234123 12341";
		SearchTermCriteria criteria = ClassificationCriteriaUtil.constructSearchTermCriteria(office, termToSearch,
				language, term, filterClass, taxoConceptNodeId, size, page, sortBy, orderBy, null);

		assertEquals(criteria.getOrderBy(), OrderBy.ASC);

	}


	public static final Object[] getFilterClasses() {
		return new String[] { "1", "12312312", "99999" };
	}

	@Test
	@junitparams.Parameters(method = "getFilterClasses")
	public void filterClassTest(String param) {

		String office = "office";
		String termToSearch = "search term";
		String language = "hu";
		String term = "term";
		String filterClass = param;
		String taxoConceptNodeId = null;
		Integer size = null;
		String page = null;
		String orderBy = null;
		String sortBy = "wer2341234123 12341";
		SearchTermCriteria criteria = ClassificationCriteriaUtil.constructSearchTermCriteria(office, termToSearch,
				language, term, filterClass, taxoConceptNodeId, size, page, sortBy, orderBy, null);

		assertEquals(criteria.getNiceClassList().size(), 1);
		assertTrue(criteria.getNiceClassList().get(0).equals(Integer.parseInt(filterClass)));

	}
}
