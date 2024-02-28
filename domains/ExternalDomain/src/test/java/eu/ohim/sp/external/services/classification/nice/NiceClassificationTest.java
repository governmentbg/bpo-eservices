package eu.ohim.sp.external.services.classification.nice;

import eu.ohim.sp.external.domain.classification.wrapper.SearchMode;
import eu.ohim.sp.external.domain.classification.wrapper.TaxonomyCriteria;
import eu.ohim.sp.external.util.JavaBeanTester;
import org.junit.Assert;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 03/02/14
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */
public class NiceClassificationTest {
    @Test
    public void testGetTaxonomyConstructor() throws IntrospectionException {
        ObjectFactory objectFactory = new ObjectFactory();
        GetTaxonomy getTaxonomy = objectFactory.createGetTaxonomy();
        getTaxonomy.setCriteria(new TaxonomyCriteria("office", "language", "term", SearchMode.FULLPHRASE, new ArrayList<BigInteger>(), BigInteger.ONE, "taxoConceptNodeId"));

        Assert.assertEquals(getTaxonomy.getCriteria().getOffice(), "office");
    }

    @Test
    public void testGetTaxonomy() throws IntrospectionException {
        JavaBeanTester.test(GetTaxonomy.class);
    }


    @Test
    public void testGetTaxonomyResponse() throws IntrospectionException {
        ObjectFactory objectFactory = new ObjectFactory();
		GetTaxonomyResponse getAutocompleteResponse = objectFactory.createGetTaxonomyResponse();
        JavaBeanTester.test(GetTaxonomyResponse.class);
    }


    @Test
	public void testGetAutocomplete() throws IntrospectionException {
        ObjectFactory objectFactory = new ObjectFactory();
		GetAutocomplete getAutocomplete = objectFactory.createGetAutocomplete();
		JavaBeanTester.test(GetAutocomplete.class);
    }

    @Test
	public void testGetAutocompleteResponse() throws IntrospectionException {
        ObjectFactory objectFactory = new ObjectFactory();
		GetAutocompleteResponse getAutocompleteResponse = objectFactory.createGetAutocompleteResponse();
		JavaBeanTester.test(GetAutocompleteResponse.class);
    }

    @Test
    public void testVerifyListOfTerms() throws IntrospectionException {
        ObjectFactory objectFactory = new ObjectFactory();
        VerifyListOfTerms verifyListOfTerms = objectFactory.createVerifyListOfTerms();
        JavaBeanTester.test(VerifyListOfTerms.class);
    }

    @Test
    public void testVerifyListOfTermsResponse() throws IntrospectionException {
        ObjectFactory objectFactory = new ObjectFactory();
        VerifyListOfTermsResponse verifyListOfTermsResponse = objectFactory.createVerifyListOfTermsResponse();
        JavaBeanTester.test(VerifyListOfTermsResponse.class);
    }


    @Test
    public void testGetClassScopes() throws IntrospectionException {
        ObjectFactory objectFactory = new ObjectFactory();
        GetClassScopes getClassScopes = objectFactory.createGetClassScopes();
        JavaBeanTester.test(GetClassScopes.class);
    }

    @Test
    public void testGetClassScopesResponse() throws IntrospectionException {
        ObjectFactory objectFactory = new ObjectFactory();
        GetClassScopesResponse getClassScopesResponse = objectFactory.createGetClassScopesResponse();
        JavaBeanTester.test(GetClassScopesResponse.class);
    }

    @Test
    public void testGetNiceClassHeading() throws IntrospectionException {
        ObjectFactory objectFactory = new ObjectFactory();
        GetNiceClassHeading getNiceClassHeading = objectFactory.createGetNiceClassHeading();
        JavaBeanTester.test(GetNiceClassHeading.class);
    }

    @Test
    public void testGetNiceClassHeadingResponse() throws IntrospectionException {
        ObjectFactory objectFactory = new ObjectFactory();
        GetNiceClassHeadingResponse getNiceClassHeadingResponse = objectFactory.createGetNiceClassHeadingResponse();
        JavaBeanTester.test(GetNiceClassHeading.class);
    }


}
