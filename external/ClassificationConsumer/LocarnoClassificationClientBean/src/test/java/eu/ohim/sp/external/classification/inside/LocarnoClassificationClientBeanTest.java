package eu.ohim.sp.external.classification.inside;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.external.domain.design.ProductIndicationClass;
import eu.ohim.sp.external.injectors.LocarnoInjector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 04/09/13
 * Time: 13:20
 * To change this template use File | Settings | File Templates.
 */
public class LocarnoClassificationClientBeanTest {

    @InjectMocks
    LocarnoClassificationClientBean locarnoClassificationClientService;

    @Mock
    LocarnoInjector injector;

    @Mock
    ConfigurationService configurationService;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        locarnoClassificationClientService.init();

        ReflectionTestUtils.setField(locarnoClassificationClientService, "locarnoInjector", injector);

        when(injector.searchProductIndication("1", "1", "1", "en"))
                .thenReturn(getProductIndications("en", "1", "1"));

        when(injector.getFullClassification("en"))
                .thenReturn(getProductIndications("en", "1", "1"));

        when(injector.searchProductIndication("", "", "", ""))
                .thenThrow(SPException.class);

        when(injector.getFullClassification(""))
                .thenThrow(SPException.class);
    }

    private List<eu.ohim.sp.external.domain.design.ProductIndication> getProductIndications(String lang, String cls, String subcls) {
        eu.ohim.sp.external.domain.design.ProductIndication productIndication = new eu.ohim.sp.external.domain.design.ProductIndication();
        productIndication.setLanguageCode(lang);

        ProductIndicationClass productIndicationClass = new ProductIndicationClass();
        productIndicationClass.setMainClass(cls);
        productIndicationClass.setSubClass(subcls);

        productIndication.setClasses(new ArrayList<ProductIndicationClass>());
        productIndication.getClasses().add(productIndicationClass);

        List<eu.ohim.sp.external.domain.design.ProductIndication> productIndications = new ArrayList<eu.ohim.sp.external.domain.design.ProductIndication>();
        productIndications.add(productIndication);
        return productIndications;
    }

    @Test
    public void testSearchProductIndication() {
        List<ProductIndication> productIndications = locarnoClassificationClientService.searchProductIndication("1",
                "1", "1", "en");

        assertEquals(productIndications.size(), 1);
        assertEquals(productIndications.get(0).getLanguageCode(), "en");
    }

    @Test(expected = SPException.class)
    public void testSearchProductIndicationError() {
        locarnoClassificationClientService.searchProductIndication("", "", "", "");

    }

    @Test
    public void testGetFullClassification() {
        List<ProductIndication> productIndications = locarnoClassificationClientService.getFullClassification("en");

        assertEquals(productIndications.size(), 1);
        assertEquals(productIndications.get(0).getLanguageCode(), "en");
    }

    @Test(expected = SPException.class)
    public void testGetFullClassificationError() {
        locarnoClassificationClientService.getFullClassification("");

    }

}
