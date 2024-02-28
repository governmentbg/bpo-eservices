package eu.ohim.sp.dsefiling.ui.service;




import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.form.design.LocarnoClass;
import eu.ohim.sp.common.ui.form.design.LocarnoClassification;
import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.domain.design.ProductIndicationClass;
import eu.ohim.sp.core.domain.design.ProductIndicationTerm;
import eu.ohim.sp.dsefiling.TestHelper;
import eu.ohim.sp.dsefiling.ui.service.interfaces.LocarnoServiceInterface;

/**
 * @author ionitdi
 */
public class DSLocarnoServiceTest
{
    @Mock
    LocarnoServiceInterface locarnoService;

    @InjectMocks
    DSLocarnoService dsLocarno = new DSLocarnoService();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getLocarnoClassesTest()
    {
        ProductIndication p1 = new ProductIndication();
        p1.setClasses(new ArrayList<ProductIndicationClass>());

        ProductIndicationClass pc1 = new ProductIndicationClass();
        pc1.setMainClass("2");

        p1.getClasses().add(pc1);

        when(locarnoService.getFullClassification("en")).thenAnswer(TestHelper.setupDummyListAnswer(p1));

        List<LocarnoClass> result = dsLocarno.getLocarnoClasses("en");

        assertEquals("02", result.get(0).getClazz());
    }


    @Test
    public void getLocarnoSubclassesTest()
    {
        ProductIndication p1 = new ProductIndication();
        p1.setClasses(new ArrayList<ProductIndicationClass>());

        ProductIndicationClass pc1 = new ProductIndicationClass();
        pc1.setMainClass("2");
        pc1.setSubClass("7");

        p1.getClasses().add(pc1);

        when(locarnoService.getFullClassification("en")).thenAnswer(TestHelper.setupDummyListAnswer(p1));

        List<LocarnoClass> result = dsLocarno.getLocarnoSubclasses("02", "en");

        assertEquals("02", result.get(0).getClazz());
        assertEquals("07", result.get(0).getSubclass());
    }

    @Test
    public void getLocarnoClassificationsTest()
    {
        ProductIndication p1 = new ProductIndication();
        p1.setClasses(new ArrayList<ProductIndicationClass>());

        ProductIndicationClass pc1 = new ProductIndicationClass();
        pc1.setMainClass("2");
        pc1.setSubClass("7");

        ProductIndicationTerm t1 = new ProductIndicationTerm();
        t1.setIndprodID("some id");
        t1.setText("txt");

        pc1.setTerms(new ArrayList<ProductIndicationTerm>());
        pc1.getTerms().add(t1);

        p1.getClasses().add(pc1);

        when(locarnoService.searchProductIndication("indication", "2", "7", "en")).thenAnswer(TestHelper.setupDummyListAnswer(p1));

        Map<String, LocarnoClassification> result = dsLocarno.getLocarnoClassifications("indication", "2", "07", "en");

        assertEquals("some id", result.get("some id").getId());
        assertEquals("02", result.get("some id").getLocarnoClass().getClazz());
        assertEquals("07", result.get("some id").getLocarnoClass().getSubclass());
        assertEquals("txt", result.get("some id").getIndication());
    }
}
