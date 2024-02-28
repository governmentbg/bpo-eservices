package eu.ohim.sp.core.classification;

import eu.ohim.sp.core.domain.classification.wrapper.TermsToBeValidated;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 29/06/13
 * Time: 16:59
 * To change this template use File | Settings | File Templates.
 */
public class ClassificationServiceTest {

    NiceClassificationServiceRemote classificationServiceInterface = null;

    @Before
    public void setup() {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        try {
            final Context context = new InitialContext(jndiProperties);
            classificationServiceInterface = (NiceClassificationServiceRemote) context.lookup("ejb:core-classification-management/ClassificationService//ClassificationService!eu.ohim.sp.core.classification.ClassificationServiceRemote");
        } catch (NamingException e) {

        }
    }

    @Test
    public void testSearchTerm() throws Exception {

    }

    @Test
    public void testVerifyListOfTerms() throws Exception {
        List<TermsToBeValidated> termsToBeValidatedList = new ArrayList<TermsToBeValidated>();
        TermsToBeValidated termsToBeValidated = new TermsToBeValidated();
        termsToBeValidated.setLanguage("en");
        termsToBeValidated.setNiceClass("25");
        termsToBeValidated.setOffice("EM");
        termsToBeValidated.setTerms("terms");
        termsToBeValidatedList.add(termsToBeValidated);

        //classificationServiceInterface.verifyListOfTerms(termsToBeValidatedList);
    }

    @Test
    public void testGetTaxonomy() throws Exception {

    }

    @Test
    public void testGetTermDistribution() throws Exception {

    }

    @Test
    public void testGetNiceClassHeading() throws Exception {

    }

    @Test
    public void testGetDidYouMean() throws Exception {

    }

    @Test
    public void testGetClassScopes() throws Exception {

    }
}
