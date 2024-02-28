package eu.ohim.sp.external.classification.outside;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.classification.wrapper.*;
import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 12/11/13
 * Time: 19:09
 * To change this template use File | Settings | File Templates.
 */
public class AdapterDisabledTest {


    @InjectMocks
    NiceClassificationClientBean classificationClientBean;

    @Mock
    ConfigurationService configurationService;

    @Before
    public void setup() {
        Adapters adapters = new Adapters();
        Adapters.Adapter adapter = new Adapters.Adapter();

        String local_address = "localhost";

        adapter.setName("classification");
        adapter.setEnabled(false);
        adapter.setWsdlLocation("http://" + local_address + ":8380/fsp/ws/classification/services?WSDL");
        adapters.getAdapter().add(adapter);

        MockitoAnnotations.initMocks(this);
        when(configurationService.getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class))).thenReturn(adapters);

        classificationClientBean.init();

        verify(configurationService, times(1)).getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class));
    }


    @Test
    public void testGetClassScopes() {
        Collection<ClassScope> classScopes = classificationClientBean.getClassScopes("en", "1");

        assertNull(classScopes);
    }

    @Test
    public void testDidYouMean() {
		List<String> results = classificationClientBean.getAutocomplete("en", "term");

        assertTrue(results.isEmpty());

    }

    @Test
    public void testGetTaxonomy() {
        TaxonomyCriteria taxonomyCriteria = new TaxonomyCriteria();
        taxonomyCriteria.setLanguage("en");
        Collection<TaxonomyConceptNode> taxonomy = classificationClientBean.getTaxonomy(taxonomyCriteria);

        assertTrue(taxonomy.isEmpty());
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


        List<TermsValidated> termsValidated = classificationClientBean.verifyListOfTerms(termsList);

        assertTrue(termsValidated.isEmpty());

    }


    @Test
    public void testGetNiceClassHeading() {
        //currently it returns not an empty list
        Collection<Term> niceClassHeading = classificationClientBean.getNiceClassHeading("1", "en");

        assertTrue(!niceClassHeading.isEmpty());
    }

    @Test
    public void testGetDistribution() {
        //currently it does not return empty object
        DistributionCriteria distributionCriteria = new DistributionCriteria();
        distributionCriteria.setLanguage("en");
        distributionCriteria.setTerm("term");
        distributionCriteria.setNiceClassList(new ArrayList<Integer>());
        distributionCriteria.getNiceClassList().add(5);

        DistributionResults distributionResults = classificationClientBean.getTermDistribution(distributionCriteria);

        assertNotNull(distributionResults);
    }

    @Test
    public void testSearch() {
        SearchTermCriteria searchTermCriteria = new SearchTermCriteria();
        searchTermCriteria.setLanguage("en");
        searchTermCriteria.setTerm("term");

        eu.ohim.sp.core.domain.classification.wrapper.SearchResults searchResults = classificationClientBean.searchTerm(searchTermCriteria);

        assertNull(searchResults.getTerms());
    }


}
