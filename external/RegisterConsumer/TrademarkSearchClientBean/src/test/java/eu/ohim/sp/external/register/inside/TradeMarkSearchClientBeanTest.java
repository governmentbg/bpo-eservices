package eu.ohim.sp.external.register.inside;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.external.injectors.ImageInjector;
import eu.ohim.sp.external.injectors.PersonInjector;
import eu.ohim.sp.external.injectors.TradeMarkInjector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 30/06/13
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ImageInjector.class)
public class TradeMarkSearchClientBeanTest {

    @InjectMocks
    TradeMarkSearchClientBean trademarkClientService;

    @Mock
    TradeMarkInjector tradeMarkInjector;

    @Mock
    PersonInjector personInjector;

    @Mock
    ConfigurationService systemConfigurationServiceInterface;
    
    @Before
    public void setup() {
        PowerMockito.mockStatic(ImageInjector.class);
        MockitoAnnotations.initMocks(this);
        trademarkClientService.init();
        ReflectionTestUtils.setField(trademarkClientService, "person_injector", personInjector);
        ReflectionTestUtils.setField(trademarkClientService, "tm_injector", tradeMarkInjector);
    }

    @Test
    public void testGetTrademark() {
        BDDMockito.given(ImageInjector.inject(Matchers.<eu.ohim.sp.external.domain.trademark.TradeMark>any())).willReturn(null);
        eu.ohim.sp.external.domain.trademark.TradeMark t = new eu.ohim.sp.external.domain.trademark.TradeMark();
        t.setReceivingOffice("EM");
        t.setApplicationNumber("0001");
        when(tradeMarkInjector.getTrademark(any(), any())).thenReturn(t);
        TradeMark tradeMark = trademarkClientService.getTradeMark("EM", "0001");
        assertEquals(tradeMark.getReceivingOffice(), "EM");
        assertEquals(tradeMark.getApplicationNumber(), "0001");
    }

    @Test
    public void testGetTrademarkError() {
        BDDMockito.given(ImageInjector.inject(Matchers.<eu.ohim.sp.external.domain.trademark.TradeMark>any())).willReturn(null);
        when(tradeMarkInjector.getTrademark(any(), any())).thenReturn(null);
        TradeMark tradeMark = trademarkClientService.getTradeMark("", "0001");
        assertNull(tradeMark);
    }

    @Test
    public void testGetInternationalTrademark() {
        BDDMockito.given(ImageInjector.inject(Matchers.<eu.ohim.sp.external.domain.trademark.TradeMark>any())).willReturn(null);
        eu.ohim.sp.external.domain.trademark.TradeMark t = new eu.ohim.sp.external.domain.trademark.TradeMark();
        t.setReceivingOffice("EM");
        t.setApplicationNumber("0001");
        when(tradeMarkInjector.getTrademark(any(), any())).thenReturn(t);
        TradeMark tradeMark = trademarkClientService.getInternationalTradeMark("EM", "0001", true);
        assertEquals(tradeMark.getReceivingOffice(), "EM");
        assertEquals(tradeMark.getApplicationNumber(), "0001");
    }

    @Test
    public void testGetInternationalTrademarkError() {
        BDDMockito.given(ImageInjector.inject(Matchers.<eu.ohim.sp.external.domain.trademark.TradeMark>any())).willReturn(null);
        when(tradeMarkInjector.getTrademark(any(), any())).thenReturn(null);
        TradeMark tradeMark = trademarkClientService.getInternationalTradeMark("", "0001", true);
        assertNull(tradeMark);
    }

    @Test
    public void testGetTrademarkAutocomplete() {
        String office = "EM";
        String text = "test";
        when(tradeMarkInjector.getTrademarkAutocomplete(office, text, 5)).thenReturn("getTradeMarkAutocomplete"+office+text);
        String response = trademarkClientService.getTradeMarkAutocomplete(office, text, 5);
        assertEquals(response, "getTradeMarkAutocomplete"+office+text);
    }


    @Test
    public void testGetTrademarkAutocompleteError() {
        String office = "";
        String text = "test";
        when(tradeMarkInjector.getTrademarkAutocomplete(office, text, 5)).thenReturn(null);
        String response = trademarkClientService.getTradeMarkAutocomplete(office, text, 5);

        assertNull(response);
    }

    @Test
    public void testGetForeignTrademark() {
        BDDMockito.given(ImageInjector.inject(Matchers.<eu.ohim.sp.external.domain.trademark.TradeMark>any())).willReturn(null);
        eu.ohim.sp.external.domain.trademark.TradeMark t = new eu.ohim.sp.external.domain.trademark.TradeMark();
        t.setReceivingOffice("EM");
        t.setApplicationNumber("0001");
        when(tradeMarkInjector.getTrademark(any(), any())).thenReturn(t);
        TradeMark tradeMark = trademarkClientService.getForeignTradeMark("EM", "0001");
        assertEquals(tradeMark.getReceivingOffice(), "EM");
        assertEquals(tradeMark.getApplicationNumber(), "0001");
    }

    @Test
    public void getForeignTradeMarkAutocomplete() {
        String office = "ET";
        String text = "test";
        when(tradeMarkInjector.getTrademarkAutocomplete(office, text, 5)).thenReturn("getTradeMarkAutocomplete"+office+text);
        String response = trademarkClientService.getForeignTradeMarkAutocomplete(office, text, 5);

        assertEquals(response, "getTradeMarkAutocomplete"+office+text);
    }


    @Test
    public void testGetPreclearanceReport() {
        BDDMockito.given(ImageInjector.inject(Matchers.<eu.ohim.sp.external.domain.trademark.TradeMark>any())).willReturn(null);
        eu.ohim.sp.external.domain.trademark.TradeMark t = new eu.ohim.sp.external.domain.trademark.TradeMark();
        t.setReceivingOffice("EM");
        t.setApplicationNumber("0001");
        t.setApplicationLanguage("en");
        ArrayList<eu.ohim.sp.external.domain.trademark.TradeMark> lst = new ArrayList();
        lst.add(t);
        when(tradeMarkInjector.getPreclearanceReport(any(), any(), any())).thenReturn(lst);

        TradeMark tradeMark = new TradeMark();
        tradeMark.setApplicationLanguage("en");
        List<TradeMark> tradeMarks = trademarkClientService.getPreclearanceReport("EM", tradeMark);

        assertEquals(tradeMarks.size(), 1);

        assertEquals(tradeMarks.get(0).getReceivingOffice(), "EM");
        assertEquals(tradeMarks.get(0).getApplicationLanguage(), tradeMark.getApplicationLanguage());
    }

    @Test
    public void testGetPreclearanceReportExcpetion() {
        BDDMockito.given(ImageInjector.inject(Matchers.<eu.ohim.sp.external.domain.trademark.TradeMark>any())).willReturn(null);
        when(tradeMarkInjector.getPreclearanceReport(any(), any(), any())).thenReturn(null);
        TradeMark tradeMark = new TradeMark();
        tradeMark.setApplicationLanguage("en");
        List<TradeMark> tradeMarks = trademarkClientService.getPreclearanceReport("", tradeMark);

        assertNull(tradeMarks);
    }
}
