package eu.ohim.sp.external.classification.outside;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters;
import eu.ohim.sp.core.domain.classification.wrapper.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

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
    ConfigurationService systemConfigurationServiceInterface;

    static Endpoint endpoint = null;

    @BeforeClass
    public static void setupEnpoint() {
        endpoint = Endpoint.publish("http://localhost:8380/fsp/ws/classification/services", new ClassificationManagementWS());
    	
        assertTrue(endpoint.isPublished());
    }
    
    @Before
    public void setup() {
        Adapters adapters = new Adapters();
        Adapters.Adapter adapter = new Adapters.Adapter();

        String local_address = "localhost";

        adapter.setName("classification");
        adapter.setEnabled(true);
        adapter.setWsdlLocation("http://" + local_address + ":8380/fsp/ws/classification/services?WSDL");
        adapters.getAdapter().add(adapter);

        MockitoAnnotations.initMocks(this);
        when(systemConfigurationServiceInterface.getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class))).thenReturn(adapters);

        classificationClientService.init();

        verify(systemConfigurationServiceInterface, times(1)).getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class));
    }

    @AfterClass
    public static void shutdown() {
        endpoint.stop();
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

        assertNull(taxonomy);
    }

    @Test
    public void testGetTaxonomyException() {
        TaxonomyCriteria taxonomyCriteria = new TaxonomyCriteria();
        taxonomyCriteria.setLanguage("es");
        Collection<TaxonomyConceptNode> taxonomy = classificationClientService.getTaxonomy(taxonomyCriteria);

        assertNull(taxonomy);
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
            assertEquals(termsList.get(0).getTerms(), termsValidated.get(0).getDescription());
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

        DistributionResults distributionResults = classificationClientService.getTermDistribution(distributionCriteria);

        assertNull(distributionResults.getClassificationTerms());
    }

    @Test
    public void testSearch() {
        SearchTermCriteria searchTermCriteria = new SearchTermCriteria();
        searchTermCriteria.setLanguage("en");
        searchTermCriteria.setTerm("term");

        eu.ohim.sp.core.domain.classification.wrapper.SearchResults searchResults = classificationClientService.searchTerm(searchTermCriteria);

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

        eu.ohim.sp.core.domain.classification.wrapper.SearchResults searchResults = classificationClientService.searchTerm(searchTermCriteria);

        assertNull(searchResults);
    }

}
