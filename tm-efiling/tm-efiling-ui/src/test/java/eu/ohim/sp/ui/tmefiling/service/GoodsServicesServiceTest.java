package eu.ohim.sp.ui.tmefiling.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.*;

import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.core.domain.classification.wrapper.*;
import eu.ohim.sp.test.ui.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.ErrorFactory;
import eu.ohim.sp.common.ui.form.classification.TermForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.classification.NiceClassificationService;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.flow.GoodsServicesFlowBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;

public class GoodsServicesServiceTest {

	@Mock
	private NiceClassificationService classificationService;

	@Mock
	private RulesService businessRulesService;

	@Mock
	private ConfigurationServiceDelegatorInterface configurationService;

	@Mock
	private ErrorFactory errorFactory;

	@InjectMocks
	private GoodsServicesService goodsServicesService;

	private static final String LANGUAGE = "en";
	private static final String TERM = "term";
	private static final String FILTER_CLASS = "filterClass";
	private static final String TAXO_CONCEPT_NODE_ID = "taxoConceptNodeId";
	private static final Integer SIZE = 1;
	private static final String PAGE = "2";
	private static final String SORT_BY = "sortBy";
	private static final String ORDER_BY = "orderBy";
	private static final String TERM_FORM_ID = "termFormId";
	private static final String TEXT = "text";
	private static final Integer NICE_CLASS = 6;
	private static final Integer LEVEL_LIMIT = 8;
	private static final String LANG_ID = "langId";
	private static final String CLASS_ID = "classId";

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(goodsServicesService, "termSeparator", ";");
	}

	@Test
	public void testSearchTerms() {
		SearchResults searchResultsMock = getSearchResultsMock();
		when(
				classificationService.searchTerm(
						any(SearchTermCriteria.class))).thenReturn(
				searchResultsMock);

		GoodsServicesFlowBean flowBean = new FlowBeanImpl();
		flowBean.setTerm(TERM);
		flowBean.setTerms(new ArrayList<TermForm>());
		flowBean.getTerms().add(new TermForm(TERM_FORM_ID));
		goodsServicesService.searchTerms(flowBean, LANGUAGE, TERM,
				FILTER_CLASS, TAXO_CONCEPT_NODE_ID, SIZE, PAGE, SORT_BY,
				ORDER_BY, null);
	}

	@Test(expected = SPException.class)
	public void testSearchTermsNullTermToSearch() {
		when(
				classificationService.searchTerm(
						any(SearchTermCriteria.class))).thenReturn(null);

		GoodsServicesFlowBean flowBean = new FlowBeanImpl();
		flowBean.setTerm(TERM);
		flowBean.setTerms(new ArrayList<TermForm>());
		flowBean.getTerms().add(new TermForm(TERM_FORM_ID));
		goodsServicesService.searchTerms(flowBean, LANGUAGE, null,
				FILTER_CLASS, TAXO_CONCEPT_NODE_ID, SIZE, PAGE, SORT_BY,
				ORDER_BY, null);
	}

	@Test
	public void testSearchTermsIsScopeAcceptabilityNull() {
		SearchResults searchResultsMock = getSearchResultsMock();
		searchResultsMock.setTerms(new ArrayList<Term>());
		Term term = getTermMock();
		term.setScopeAcceptability(null);
		searchResultsMock.getTerms().add(term);
		when(
				classificationService.searchTerm(
						any(SearchTermCriteria.class))).thenReturn(
				searchResultsMock);

		GoodsServicesFlowBean flowBean = new FlowBeanImpl();
		flowBean.setTerm(TERM);
		flowBean.setTerms(new ArrayList<TermForm>());
		flowBean.getTerms().add(new TermForm(TERM_FORM_ID));
		goodsServicesService.searchTerms(flowBean, LANGUAGE, TERM,
				FILTER_CLASS, TAXO_CONCEPT_NODE_ID, SIZE, PAGE, SORT_BY,
				ORDER_BY, null);
	}

	@Test
	public void testGetTaxonomy() {
		assertTrue(goodsServicesService.getTaxonomy(LANGUAGE, TERM,
				LEVEL_LIMIT, TAXO_CONCEPT_NODE_ID) != null);
	}

	@Test
	public void testGetParentConceptNodes() {
		assertTrue(goodsServicesService.getParentConceptNodes(LANGUAGE, TERM) != null);
	}

	@Test
	public void didYouMean() {
		assertTrue(goodsServicesService.getAutocomplete(LANGUAGE, "searchCriteria") != null);
	}

	@Test
	public void testGetClassScope() {
		Collection<ClassScope> scopes = new ArrayList<ClassScope>();
		ClassScope classScope = new ClassScope();
		classScope.setClassNumber("7777");
		scopes.add(classScope);
		when(
				classificationService.getClassScopes(Mockito.anyString(),
						Mockito.anyString())).thenReturn(scopes);

		Map<String, ClassScope> classScopes = goodsServicesService
				.getClassScope(LANGUAGE, "niceClassHeadings");
		assertEquals(classScopes.size(), 1);
	}

	@Test
	public void testGetClassScopeNull() {
		when(
				classificationService.getClassScopes(Mockito.anyString(),
						Mockito.anyString())).thenReturn(null);

		Map<String, ClassScope> classScopes = goodsServicesService
				.getClassScope(LANGUAGE, "niceClassHeadings");
		assertEquals(classScopes.size(), 0);
	}

	private SearchResults getSearchResultsMock() {
		SearchResults searchResultsMock = new SearchResults();
		searchResultsMock.setTerms(new ArrayList<Term>());
		searchResultsMock.getTerms().add(getTermMock());
		return searchResultsMock;
	}

	private Term getTermMock() {
		Term term = new Term();
		term.setId("3");
		term.setText(TEXT);
		term.setNiceClass(NICE_CLASS);
		term.setScopeAcceptability(Boolean.TRUE);
		return term;
	}

	@Test
	public void testVerifyListGS() {
		FlowBeanImpl fb = new FlowBeanImpl();
		fb = TestUtil.FlowBeanGenerator.fillFlowBean(fb);
		GoodAndServiceForm gsf = new GoodAndServiceForm();
		gsf.setClassId("1");
		gsf.setLangId("en");
		gsf.setDesc("desc");
		TermForm tmf = new TermForm();
		tmf.setDescription("pepe");
		tmf.setIdClass("1");
		gsf.setTermForms(new HashSet<>(Arrays.asList(tmf)));
		fb.addGoodAndService(gsf);
		goodsServicesService.verifyListOfGS(fb);
		assertTrue(fb != null);
	}

	@Test
	public void testVerifyListTerms() {
		assertTrue(goodsServicesService.verifyListOfTerms("en", "1", "pok") != null);
	}

	@Test
	public void testVerifyListTermsNull() {
		when(configurationService.isServiceEnabled(any())).thenReturn(true);
		assertTrue(goodsServicesService.verifyListOfTerms("en", "1", "pok") == null);
	}

	@Test
	public void testVerifyListTermsException() {
		when(configurationService.isServiceEnabled(any())).thenReturn(true);
		when(classificationService.verifyListOfTerms(any())).thenReturn(null);
		assertTrue(goodsServicesService.verifyListOfTerms("en", "1", "pok") != null);
	}
}
