package eu.ohim.sp.external.register.outside;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters;
import eu.ohim.sp.core.domain.trademark.TradeMark;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.ws.Endpoint;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 30/06/13
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public class TradeMarkSearchClientBeanTest {

    @InjectMocks
    TradeMarkSearchClientBean trademarkClientService;

    @Mock
    ConfigurationService systemConfigurationServiceInterface;

    static Endpoint endpoint =  null;

    @BeforeClass
    public static void setupEnpoint() {
    	endpoint = Endpoint.publish("http://localhost:8380/fsp/ws/trademark/services", new TrademarkManagementWS());
    	
    	assertTrue(endpoint.isPublished());
    }
    
    @Before
    public void setup() {

        Adapters adapters = new Adapters();
        Adapters.Adapter adapter = new Adapters.Adapter();

        adapter.setName("trademark");
        adapter.setEnabled(true);
        adapter.setWsdlLocation("http://localhost:8380/fsp/ws/trademark/services?wsdl");
        adapters.getAdapter().add(adapter);

        MockitoAnnotations.initMocks(this);
        when(systemConfigurationServiceInterface.getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class))).thenReturn(adapters);

        trademarkClientService.init();

        verify(systemConfigurationServiceInterface, times(1)).getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class));
    }

    @AfterClass
    public static void shutdown() {
        endpoint.stop();
    }

    @Test
    public void testGetTrademark() {
        TradeMark tradeMark = trademarkClientService.getTradeMark("EM", "0001");

        assertEquals(tradeMark.getReceivingOffice(), "EM");
        assertEquals(tradeMark.getApplicationNumber(), "0001");
    }

    @Test
    public void testGetTrademarkError() {
        TradeMark tradeMark = trademarkClientService.getTradeMark("", "0001");

        assertNull(tradeMark);
    }

    @Test
    public void testGetInternationalTrademark() {
        TradeMark tradeMark = trademarkClientService.getInternationalTradeMark("EM", "0001", true);

        assertEquals(tradeMark.getReceivingOffice(), "EM");
        assertEquals(tradeMark.getApplicationNumber(), "0001");
    }

    @Test
    public void testGetInternationalTrademarkError() {
        TradeMark tradeMark = trademarkClientService.getInternationalTradeMark("", "0001", true);

        assertNull(tradeMark);
    }

    @Test
    public void testGetTrademarkAutocomplete() {
        String office = "EM";
        String text = "test";

        String response = trademarkClientService.getTradeMarkAutocomplete(office, text, 5);

        assertEquals(response, "getTradeMarkAutocomplete"+office+text);
    }


    @Test
    public void testGetTrademarkAutocompleteError() {
        String office = "";
        String text = "test";

        String response = trademarkClientService.getTradeMarkAutocomplete(office, text, 5);

        assertNull(response);
    }

    @Test
    public void testGetForeignTrademark() {
        TradeMark tradeMark = trademarkClientService.getForeignTradeMark("EM", "0001");

        assertEquals(tradeMark.getReceivingOffice(), "EM");
        assertEquals(tradeMark.getApplicationNumber(), "0001");
    }

    @Test
    public void getForeignTradeMarkAutocomplete() {
        String office = "ET";
        String text = "test";

        String response = trademarkClientService.getForeignTradeMarkAutocomplete(office, text, 5);

        assertEquals(response, "getTradeMarkAutocomplete"+office+text);
    }


    @Test
    public void testGetPreclearanceReport() {
        TradeMark tradeMark = new TradeMark();
        tradeMark.setApplicationLanguage("en");
        List<TradeMark> tradeMarks = trademarkClientService.getPreclearanceReport("EM", tradeMark);

        assertEquals(tradeMarks.size(), 1);

        assertEquals(tradeMarks.get(0).getReceivingOffice(), "EM");
        assertEquals(tradeMarks.get(0).getApplicationLanguage(), tradeMark.getApplicationLanguage());
    }

    @Test
    public void testGetPreclearanceReportExcpetion() {
        TradeMark tradeMark = new TradeMark();
        tradeMark.setApplicationLanguage("en");
        List<TradeMark> tradeMarks = trademarkClientService.getPreclearanceReport("", tradeMark);

        assertNull(tradeMarks);
    }
}
