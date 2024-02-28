package eu.ohim.sp.core.classification;

import eu.ohim.sp.core.domain.classification.wrapper.DistributionCriteria;
import eu.ohim.sp.core.domain.classification.wrapper.SearchTermCriteria;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyCriteria;
import eu.ohim.sp.core.domain.classification.wrapper.TermsToBeValidated;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;

/**
 * Created with IntelliJ IDEA.
 * Date: 20/1/2014
 * Time: 10:23 μμ
 * To change this template use File | Settings | File Templates.
 */
public class NiceClassificationServiceBeanTest {

    @InjectMocks
    private NiceClassificationServiceBean niceClassificationServiceBean;

    @Mock
    private NiceClassificationServiceLocal adapter;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchTerm() {
        SearchTermCriteria searchTermCriteria = new SearchTermCriteria();
        niceClassificationServiceBean.searchTerm(searchTermCriteria);
        Mockito.verify(adapter, Mockito.times(1)).searchTerm(Matchers.eq(searchTermCriteria));
		Assert.assertNull(niceClassificationServiceBean.searchTerm(searchTermCriteria));
    }

    @Test
    public void testVerifyListOfTerms() {
        List<TermsToBeValidated> termsToBeValidated = new ArrayList<TermsToBeValidated>();
        niceClassificationServiceBean.verifyListOfTerms(termsToBeValidated);
        Mockito.verify(adapter, Mockito.times(1)).verifyListOfTerms(Matchers.eq(termsToBeValidated));
		Assert.assertNotNull(niceClassificationServiceBean.verifyListOfTerms(termsToBeValidated));
    }

    @Test
    public void testGetTaxonomy() {
        TaxonomyCriteria taxonomyCriteria = new TaxonomyCriteria();
        niceClassificationServiceBean.getTaxonomy(taxonomyCriteria);
        Mockito.verify(adapter, Mockito.times(1)).getTaxonomy(Matchers.eq(taxonomyCriteria));
		Assert.assertNotNull(niceClassificationServiceBean.getTaxonomy(taxonomyCriteria));
    }

    @Test
    public void testGetTermDistribution() {
        DistributionCriteria distributionCriteria = new DistributionCriteria();
        niceClassificationServiceBean.getTermDistribution(distributionCriteria);
        Mockito.verify(adapter, Mockito.times(1)).getTermDistribution(Matchers.eq(distributionCriteria));
    }

    @Test
    public void testGetNiceClassHeading() {
        niceClassificationServiceBean.getNiceClassHeading("1", "en");
        Mockito.verify(adapter, Mockito.times(1)).getNiceClassHeading(Matchers.eq("1"), Matchers.eq("en"));
		Assert.assertNotNull(niceClassificationServiceBean.getNiceClassHeading("1", "en"));
    }

    @Test
    public void testGetDidYouMean() {
		niceClassificationServiceBean.getAutocomplete("en", "test");
		Mockito.verify(adapter, Mockito.times(1)).getAutocomplete(Matchers.eq("en"), Matchers.eq("test"));
		Assert.assertNotNull(niceClassificationServiceBean.getAutocomplete("en", "test"));
    }

    @Test
    public void testGetClassScopes() {
        niceClassificationServiceBean.getClassScopes("en", "1,2");
        Mockito.verify(adapter, Mockito.times(1)).getClassScopes(Matchers.eq("en"), Matchers.eq("1,2"));
		Assert.assertNotNull(niceClassificationServiceBean.getClassScopes("en", "1,2"));
    }
}
