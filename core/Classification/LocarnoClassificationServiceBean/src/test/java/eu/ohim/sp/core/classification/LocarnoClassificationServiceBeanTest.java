package eu.ohim.sp.core.classification;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;

/**
 * Created with IntelliJ IDEA.
 * User: Χρήστος
 * Date: 20/1/2014
 * Time: 10:39 μμ
 * To change this template use File | Settings | File Templates.
 */
public class LocarnoClassificationServiceBeanTest {

    @Mock
    private LocarnoClassificationService locarnoClassificationService;

    @Mock
    private RulesService businessRulesService;

    @InjectMocks
    private LocarnoClassificationServiceBean locarnoClassificationServiceBean;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetFullClassification() {
        locarnoClassificationServiceBean.getFullClassification("en");
        Mockito.verify(locarnoClassificationService, Mockito.times(1)).getFullClassification(Matchers.eq("en"));
    }

    @Test
    public void testSearchProductIndication() {
        locarnoClassificationServiceBean.searchProductIndication("ind", "clazz", "subclass", "en");
        Mockito.verify(locarnoClassificationService, Mockito.times(1)).searchProductIndication(Matchers.eq("ind"),
                Matchers.eq("clazz"), Matchers.eq("subclass"), Matchers.eq("en"));
    }

    @Test
    public void testValidateProductIndication() {
        ProductIndication productIndication = new ProductIndication();
        RulesInformation rulesInformation = new RulesInformation();
        locarnoClassificationServiceBean.validateProductIndication("ds", productIndication, rulesInformation);

        Mockito.verify(businessRulesService, Mockito.times(1)).validate(Matchers.eq("ds"),
                Matchers.eq("indication_product_set"), Matchers.anyList());
    }
}
