/*******************************************************************************
 * * $Id:: SearchTermCriteriaTest.java 157090 2013-12-02 11:54:19Z velasca       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification.wrapper;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class SearchTermCriteriaTest {
	
	private static final String LANGUAGE = "en";
	private static final String TAXO_CONCEPT_NODE_ID = "taxoConceptNodeId";
	private static final Integer SIZE = 4;
	private static final Integer PAGE = 5;
	private static final String OFFICE = "EM";
	private static final String TERM = "term";
	
	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(SearchTermCriteria.class);
	}

	@Test
	public void testEquals() {
		List<Integer> niceClassList = new ArrayList<Integer>();
		
		SearchTermCriteria searchTermCriteria1 = new SearchTermCriteria();
		searchTermCriteria1.setLanguage(LANGUAGE);
		searchTermCriteria1.setNiceClassList(niceClassList);
		searchTermCriteria1.setOffice(OFFICE);
		searchTermCriteria1.setOrderBy(OrderBy.ASC);
		searchTermCriteria1.setPage(PAGE);
		searchTermCriteria1.setSearchMode(SearchMode.EXACTMATCH);
		searchTermCriteria1.setShowMaster(true);
		searchTermCriteria1.setShowNonTaxoTermsOnly(Boolean.TRUE);
		searchTermCriteria1.setSize(SIZE);
		searchTermCriteria1.setSortBy(SortBy.NICECLASS);
		searchTermCriteria1.setTaxoConceptNodeId(TAXO_CONCEPT_NODE_ID);
		searchTermCriteria1.setTerm(TERM);
		
		SearchTermCriteria searchTermCriteria2 = new SearchTermCriteria();
		searchTermCriteria2.setLanguage(LANGUAGE);
		searchTermCriteria2.setNiceClassList(niceClassList);
		searchTermCriteria2.setOffice(OFFICE);
		searchTermCriteria2.setOrderBy(OrderBy.ASC);
		searchTermCriteria2.setPage(PAGE);
		searchTermCriteria2.setSearchMode(SearchMode.EXACTMATCH);
		searchTermCriteria2.setShowMaster(true);
		searchTermCriteria2.setShowNonTaxoTermsOnly(Boolean.TRUE);
		searchTermCriteria2.setSize(SIZE);
		searchTermCriteria2.setSortBy(SortBy.NICECLASS);
		searchTermCriteria2.setTaxoConceptNodeId(TAXO_CONCEPT_NODE_ID);
		searchTermCriteria2.setTerm(TERM);
		
		assertEquals(searchTermCriteria1.getLanguage(), searchTermCriteria2.getLanguage());
		assertEquals(searchTermCriteria1.getNiceClassList(), searchTermCriteria2.getNiceClassList());
		assertEquals(searchTermCriteria1.getOffice(), searchTermCriteria2.getOffice());
		assertEquals(searchTermCriteria1.getOrderBy(), searchTermCriteria2.getOrderBy());
		assertEquals(searchTermCriteria1.getPage(), searchTermCriteria2.getPage());
		assertEquals(searchTermCriteria1.getSearchMode(), searchTermCriteria2.getSearchMode());
		assertEquals(searchTermCriteria1.isShowMaster(), searchTermCriteria2.isShowMaster());
		assertEquals(searchTermCriteria1.getShowNonTaxoTermsOnly(), searchTermCriteria2.getShowNonTaxoTermsOnly());
		assertEquals(searchTermCriteria1.getSize(), searchTermCriteria2.getSize());
		assertEquals(searchTermCriteria1.getSortBy(), searchTermCriteria2.getSortBy());
		assertEquals(searchTermCriteria1.getTaxoConceptNodeId(), searchTermCriteria2.getTaxoConceptNodeId());
		assertEquals(searchTermCriteria1.getTerm(), searchTermCriteria2.getTerm());
	}

	@Test
	public void testToString() {
		SearchTermCriteria searchTermCriteria = new SearchTermCriteria();
		searchTermCriteria.setShowMaster(true);
		searchTermCriteria.setSize(SIZE);
		searchTermCriteria.setPage(PAGE);
		searchTermCriteria.setSortBy(SortBy.NICECLASS);
		searchTermCriteria.setOrderBy(OrderBy.ASC);
		searchTermCriteria.setTaxoConceptNodeId(TAXO_CONCEPT_NODE_ID);

		assertEquals(
				searchTermCriteria.toString(),
				"SearchTermCriteria [showMaster="
						+ searchTermCriteria.isShowMaster() + ", size="
						+ searchTermCriteria.getSize() + ", page="
						+ searchTermCriteria.getPage() + ", sortBy="
						+ searchTermCriteria.getSortBy() + ", orderBy="
						+ searchTermCriteria.getOrderBy()
						+ ", taxoConceptNodeId="
						+ searchTermCriteria.getTaxoConceptNodeId() + "]");
	}
}
