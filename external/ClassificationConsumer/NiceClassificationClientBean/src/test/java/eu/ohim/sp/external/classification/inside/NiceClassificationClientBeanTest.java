package eu.ohim.sp.external.classification.inside;

import eu.ohim.sp.core.configuration.ConfigurationService;
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
import eu.ohim.sp.external.domain.classification.MatchClassType;
import eu.ohim.sp.external.domain.classification.MatchProximityType;
import eu.ohim.sp.external.domain.classification.wrapper.DistributionResult;
import eu.ohim.sp.external.injectors.NiceClassInjector;
import eu.ohim.sp.integration.domain.smooks.FreeFormatString;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 04/09/13
 * Time: 13:20
 * To change this template use File | Settings | File Templates.
 */
public class NiceClassificationClientBeanTest {

    @InjectMocks
    NiceClassificationClientBean classificationClientService;

    @Mock
    NiceClassInjector injector;

    @Mock
    ConfigurationService systemConfigurationServiceInterface;

    private eu.ohim.sp.external.domain.classification.wrapper.SearchResults getSearchResults() {
        eu.ohim.sp.external.domain.classification.wrapper.SearchTermCriteria criteria = new eu.ohim.sp.external.domain.classification.wrapper.SearchTermCriteria();
        criteria.setLanguage("en");
        criteria.setTerm("term");
        eu.ohim.sp.external.domain.classification.wrapper.SearchResults searchResults = new eu.ohim.sp.external.domain.classification.wrapper.SearchResults();
        searchResults.setTerms(new ArrayList<eu.ohim.sp.external.domain.classification.wrapper.Term>());
        searchResults.setTotalResults(2);
        eu.ohim.sp.external.domain.classification.wrapper.Term term = new eu.ohim.sp.external.domain.classification.wrapper.Term();
        term.setLang(criteria.getLanguage());
        term.setText(criteria.getTerm() + "sth");

        searchResults.getTerms().add(term);

        term = new eu.ohim.sp.external.domain.classification.wrapper.Term();
        term.setLang(criteria.getLanguage());
        term.setText(criteria.getTerm() + "sth else");


        searchResults.getTerms().add(term);

        return searchResults;
    }

    private List<eu.ohim.sp.external.domain.classification.wrapper.ClassScope> getClassScope(String lang) {
        if(lang!=null) {
            List<eu.ohim.sp.external.domain.classification.wrapper.ClassScope> classScopes = new ArrayList<eu.ohim.sp.external.domain.classification.wrapper.ClassScope>();
            classScopes.add(new eu.ohim.sp.external.domain.classification.wrapper.ClassScope());
            classScopes.get(0).setLanguage(lang);
            return classScopes;
        }
        return null;
    }

    private List<FreeFormatString> getYouMean(String lang) {
        List<FreeFormatString> terms = new ArrayList<FreeFormatString>();
        if(lang!=null) {
            FreeFormatString ff = new FreeFormatString();
            ff.setValue("term_suggestion");
            terms.add(ff);
        }
        return terms;
    }

    private List<eu.ohim.sp.external.domain.classification.wrapper.TaxonomyConceptNode> getTaxonomy(
            String lang
    ) {
        if (!StringUtils.equals(lang, "en")) {
            return null;
        }
        List<eu.ohim.sp.external.domain.classification.wrapper.TaxonomyConceptNode> taxonomyConceptNodes = new ArrayList<eu.ohim.sp.external.domain.classification.wrapper.TaxonomyConceptNode>();
        taxonomyConceptNodes.add(new eu.ohim.sp.external.domain.classification.wrapper.TaxonomyConceptNode());
        taxonomyConceptNodes.get(0).setParentId(null);

        return taxonomyConceptNodes;
    }

    private List<eu.ohim.sp.external.domain.classification.wrapper.Term> getValidated(
            String office,
            String lang,
            String term,
            String nice
    ) {
        List<eu.ohim.sp.external.domain.classification.wrapper.Term> termsValidated = new ArrayList<eu.ohim.sp.external.domain.classification.wrapper.Term>();
        eu.ohim.sp.external.domain.classification.wrapper.Term termValidated = new eu.ohim.sp.external.domain.classification.wrapper.Term();
        termValidated.setLang(lang);
        termValidated.setNiceClass(Integer.valueOf(nice));
        termValidated.setText(term);

        eu.ohim.sp.external.domain.classification.ObjectFactory objectFactory = new eu.ohim.sp.external.domain.classification.ObjectFactory();
        termValidated.getMatchedTerms().add(objectFactory.createMatchedTerm());

        termValidated.getMatchedTerms().get(0).setMatchedClassNumber(BigInteger.ONE);
        termValidated.getMatchedTerms().get(0).setTermSourceType("source");
        termValidated.getMatchedTerms().get(0).setMatchedTermText("term");
        termValidated.getMatchedTerms().get(0).setMatchClassType(MatchClassType.SAME_CLASS);
        termValidated.getMatchedTerms().get(0).setIdentifier("identifier");
        termValidated.getMatchedTerms().get(0).setLegacyTermFirstUseDate(new Date());
        termValidated.getMatchedTerms().get(0).setLegacyTermFrequency(BigInteger.TEN);
        termValidated.getMatchedTerms().get(0).setMatchedTermPositionInInputTerm(BigInteger.ZERO);
        termValidated.getMatchedTerms().get(0).setMatchRank(BigInteger.TEN);
        termValidated.getMatchedTerms().get(0).setPartial(true);
        termValidated.getMatchedTerms().get(0).setMatchedTermPositionInInputTermList(BigInteger.valueOf(6));
        termValidated.getMatchedTerms().get(0).setMatchProximityType(MatchProximityType.MATCH_TO_VERIFY);
        termValidated.getMatchedTerms().get(0).setLegacyTermLastUseDate(new Date());

        termsValidated.add(termValidated);
        return termsValidated;
    }

    private List<eu.ohim.sp.external.domain.classification.wrapper.Term> getClassHeadings() {
        List<eu.ohim.sp.external.domain.classification.wrapper.Term> terms = new ArrayList<eu.ohim.sp.external.domain.classification.wrapper.Term>();
        eu.ohim.sp.external.domain.classification.wrapper.Term term  = new eu.ohim.sp.external.domain.classification.wrapper.Term();
        term.setLang("en");
        terms.add(term);
        return  terms;
    }

    private eu.ohim.sp.external.domain.classification.wrapper.DistributionResults getDistribution() {
        eu.ohim.sp.external.domain.classification.wrapper.DistributionResults distributionResults = new eu.ohim.sp.external.domain.classification.wrapper.DistributionResults();
        distributionResults.setDistributionResult(new ArrayList<DistributionResult>());
        DistributionResult distributionResult = new DistributionResult();
        distributionResult.setClassNum(BigInteger.valueOf(5));
        distributionResult.setTotalMatches(new BigInteger("54"));
        distributionResult.setTotalNumber(new BigInteger("555"));
        distributionResults.getDistributionResult().add(distributionResult);
        return distributionResults;
    }

    private eu.ohim.sp.external.domain.classification.wrapper.SearchResults getTerm() {
        eu.ohim.sp.external.domain.classification.wrapper.SearchResults searchResults = new eu.ohim.sp.external.domain.classification.wrapper.SearchResults();
        searchResults.setTerms(new ArrayList<eu.ohim.sp.external.domain.classification.wrapper.Term>());
        searchResults.setTotalResults(2);
        eu.ohim.sp.external.domain.classification.wrapper.Term term = new eu.ohim.sp.external.domain.classification.wrapper.Term();
        term.setLang("en");
        term.setText("term_sth");
        searchResults.getTerms().add(term);
        term = new eu.ohim.sp.external.domain.classification.wrapper.Term();
        term.setLang("en");
        term.setText("term sth else");
        searchResults.getTerms().add(term);
        return searchResults;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        classificationClientService.init();

        ReflectionTestUtils.setField(classificationClientService, "niceClassInjector", injector);

        when(injector.getSearchTerms(null, "en", "term", null, null, null, null, null, null))
                .thenReturn(getSearchResults());

        when(injector.getSearchTerms(null, "", "term", null, null, null, null, null, null))
                .thenReturn(null);

        when(injector.getClassScopes("en", "1")).thenReturn(getClassScope("en"));

        when(injector.getClassScopes("", "1")).thenReturn(getClassScope(null));

		when(injector.getAutocomplete("en", "term")).thenReturn(getYouMean("en"));

		when(injector.getAutocomplete("", "term")).thenReturn(getYouMean(null));

        when(injector.getTaxonomy(null, "en", null, null, null)).thenReturn(getTaxonomy("en"));

        when(injector.getTaxonomy(null, "", null, null, null)).thenReturn(getTaxonomy(null));

        when(injector.getTaxonomy(null, "es", null, null, null)).thenReturn(getTaxonomy(null));

        when(injector.getVerifyTerms("EM", "en", "shoe", "4")).thenReturn(getValidated("EM", "en", "shoe", "4"));

        when(injector.getHeadings("en", "1")).thenReturn(getClassHeadings());

        when(injector.getHeadings("", "1")).thenReturn(null);

        when(injector.getDistribution(null, "en", "term", "{5}")).thenReturn(getDistribution());

        when(injector.getDistribution(null, null, null, "{}")).thenReturn(null);

        when(injector.getSearchTerms(null, "en", "term", null, null, null, null, null, null)).thenReturn(getTerm());

        when(injector.getSearchTerms(null, "", "term", null, null, null, null, null, null)).thenReturn(null);
    }

    @Test
    public void testGetClassScopes() {
        Collection<ClassScope> classScopes = classificationClientService.getClassScopes("en", "1");

        assertEquals(classScopes.size(), 1);
        for (ClassScope classScope : classScopes) {
            assertEquals(classScope.getLanguage(), "en");
        }
    }

    @Test
    public void testGetClassScopesError() {
        Collection<ClassScope> classScopes = classificationClientService.getClassScopes("", "1");

        assertNull(classScopes);
    }

    @Test
	public void testAutocomplete() {
		List<String> results = classificationClientService.getAutocomplete("en", "term");

        for (String result : results) {
            assertTrue(result.contains("term"));
        }

    }

    @Test
	public void testAutocompleteError() {
		List<String> results = classificationClientService.getAutocomplete("", "term");

        assertTrue(results.size()==0);

    }

    @Test
    public void testGetTaxonomy() {
        TaxonomyCriteria taxonomyCriteria = new TaxonomyCriteria();
        taxonomyCriteria.setLanguage("en");
        Collection<TaxonomyConceptNode> taxonomy = classificationClientService.getTaxonomy(taxonomyCriteria);

        for (TaxonomyConceptNode node : taxonomy) {
            assertEquals(node.getParentId(), taxonomyCriteria.getTaxoConceptNodeId() );
        }
    }

    @Test
    public void testGetTaxonomyError() {
        TaxonomyCriteria taxonomyCriteria = new TaxonomyCriteria();
        taxonomyCriteria.setLanguage("");
        Collection<TaxonomyConceptNode> taxonomy = classificationClientService.getTaxonomy(taxonomyCriteria);

        assertNotNull(taxonomy);
    }

    @Test
    public void testGetTaxonomyException() {
        TaxonomyCriteria taxonomyCriteria = new TaxonomyCriteria();
        taxonomyCriteria.setLanguage("es");
        Collection<TaxonomyConceptNode> taxonomy = classificationClientService.getTaxonomy(taxonomyCriteria);
        assertNotNull(taxonomy);
    }

    @Test
    public void testValidate() {
        List<TermsToBeValidated> termsList = new ArrayList<TermsToBeValidated>();


        TermsToBeValidated termsToBeValidated = new TermsToBeValidated();
        termsToBeValidated.setLanguage("en");
        termsToBeValidated.setOffice("EM");
        termsToBeValidated.setNiceClass("4");
        termsToBeValidated.setTerms("shoe");
        termsList.add(termsToBeValidated);

        termsToBeValidated = new TermsToBeValidated();
        termsToBeValidated.setLanguage("en");
        termsToBeValidated.setOffice("EM");
        termsToBeValidated.setNiceClass("4");
        termsToBeValidated.setTerms("shoes");
        termsList.add(termsToBeValidated);


        List<TermsValidated> termsValidated = classificationClientService.verifyListOfTerms(termsList);

        for (int i=0;i<termsValidated.size();i++) {
            assertEquals(termsList.get(0).getLanguage(), termsValidated.get(0).getLanguage());
            assertEquals(termsList.get(0).getNiceClass(), termsValidated.get(0).getNiceClass());
            assertEquals(termsList.get(0).getTerms(), termsValidated.get(0).getTerms().get(0).getText());
            assertEquals(1, termsValidated.get(0).getTerms().get(0).getMatchedTerms().size());
        }

    }


    @Test
    public void testValidateError() {
        List<TermsToBeValidated> termsList = new ArrayList<TermsToBeValidated>();
        List<TermsValidated> termsValidated = classificationClientService.verifyListOfTerms(termsList);
        assertTrue(termsList.isEmpty());
    }

    @Test
    public void testGetNiceClassHeading() {
        Collection<Term> niceClassHeading = classificationClientService.getNiceClassHeading("1", "en");
        assertTrue(niceClassHeading.size()>0);
        assertEquals(niceClassHeading.iterator().next().getLang(), "en");
    }

    @Test
    public void testGetNiceClassHeadingError() {
        Collection<Term> niceClassHeading = classificationClientService.getNiceClassHeading("1", "");
        assertNull(niceClassHeading);
    }

    @Test
    public void testGetDistribution() {
        DistributionCriteria distributionCriteria = new DistributionCriteria();
        distributionCriteria.setLanguage("en");
        distributionCriteria.setTerm("term");
        distributionCriteria.setNiceClassList(new ArrayList<Integer>());
        distributionCriteria.getNiceClassList().add(5);

        DistributionResults distributionResults = classificationClientService.getTermDistribution(distributionCriteria);

        assertEquals(distributionResults.getClassificationTerms().iterator().next().getClassNum(), new Integer(5));
        assertEquals(distributionResults.getClassificationTerms().iterator().next().getTotalMatches(), new Integer(54));
        assertEquals(distributionResults.getClassificationTerms().iterator().next().getTotalNumber(), new Integer(555));
    }

    @Test
    public void testGetDistributionError() {
        DistributionCriteria distributionCriteria = new DistributionCriteria();
        distributionCriteria.setNiceClassList(new ArrayList<Integer>());
        DistributionResults distributionResults = classificationClientService.getTermDistribution(distributionCriteria);
        assertNull(distributionResults.getClassificationTerms());
    }

    @Test
    public void testSearch() {
        SearchTermCriteria searchTermCriteria = new SearchTermCriteria();
        searchTermCriteria.setLanguage("en");
        searchTermCriteria.setTerm("term");

        SearchResults searchResults = classificationClientService.searchTerm(searchTermCriteria);

        for (Term term : searchResults.getTerms()) {
            assertEquals(searchTermCriteria.getLanguage(), term.getLang());
            assertTrue(term.getText().contains(searchTermCriteria.getTerm()));
        }
    }


    @Test
    public void testSearchError() {
        SearchTermCriteria searchTermCriteria = new SearchTermCriteria();
        searchTermCriteria.setLanguage("");
        searchTermCriteria.setTerm("term");

        SearchResults searchResults = classificationClientService.searchTerm(searchTermCriteria);

        assertNull(searchResults);
    }

}
