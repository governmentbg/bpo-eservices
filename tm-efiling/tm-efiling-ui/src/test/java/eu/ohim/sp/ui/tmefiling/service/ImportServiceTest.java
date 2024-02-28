package eu.ohim.sp.ui.tmefiling.service;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.DocumentFactory;
import eu.ohim.sp.common.ui.adapter.SimilarMarkFactory;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.core.register.TradeMarkSearchService;
import eu.ohim.sp.test.ui.util.TestUtil;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by marcoantonioalberoalbero on 2/3/17.
 */
public class ImportServiceTest {
    @Mock
    private FlowBeanFactory flowBeanFactory;

    @Mock
    private TradeMarkSearchService tradeMarkSearchService;

    @Mock
    private FileServiceInterface fileService;

    @Mock
    private SimilarMarkFactory similarMarkFactory;

    @Mock
    private DocumentFactory documentFactory;

    @InjectMocks
    private ImportService importService;

    private static final String TM_ID = "10";
    private static final String OFFICE = "EM";
    private static final String TM_ID_11 = "11";
    private static final String OFFICE_GR = "GR";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TradeMarkApplication application = TestUtil.CoreTMGenerator.fillTMApplication();
        ArrayList<TradeMark> report = new ArrayList<>();
        report.add(application.getTradeMark());
        when(flowBeanFactory.convertFrom(any())).thenReturn(new FlowBeanImpl());
        when(flowBeanFactory.convertTo(any())).thenReturn(application.getTradeMark());
        when(tradeMarkSearchService.getInternationalTradeMark(any(), any(), any())).thenReturn(application.getTradeMark());
        when(tradeMarkSearchService.getInternationalTradeMark(OFFICE_GR, TM_ID_11, Boolean.TRUE)).thenReturn(null);
        when(tradeMarkSearchService.getForeignTradeMark(any(), any())).thenReturn(application.getTradeMark());
        when(tradeMarkSearchService.getForeignTradeMark(OFFICE_GR, TM_ID_11)).thenReturn(null);
        when(tradeMarkSearchService.getTradeMarkAutocomplete("NOP", "pepe2", 50)).thenThrow(new SPException("boom"));
        when(tradeMarkSearchService.getTradeMarkAutocomplete(OFFICE, "pepe", 50)).thenReturn("yeah");
        when(tradeMarkSearchService.getPreclearanceReport(OFFICE, application.getTradeMark())).thenReturn(report);
        when(tradeMarkSearchService.getPreclearanceReport("OH", application.getTradeMark())).thenThrow(new SPException("crash"));
    }

    @Test
    public void testGetTradeMark() {
        FlowBeanImpl original = new FlowBeanImpl();
        original.setIdreserventity("test");
        assertTrue(importService.getTradeMark(OFFICE, TM_ID, true, null, original) != null);
    }

    @Test(expected=SPException.class)
    public void testGetTradeMarkException() {
        importService.getTradeMark(OFFICE, TM_ID, true, null, null);
    }

    @Test
    public void testGetTrademarkPriority() {
        FlowBeanImpl original = new FlowBeanImpl();
        original.setIdreserventity("test2");
        assertTrue(importService.getTradeMarkPriority(original, OFFICE, TM_ID, true, null) != null);
    }

    @Test(expected = SPException.class)
    public void testGetTrademarkPriorityException() {
        importService.getTradeMarkPriority(null, OFFICE_GR, TM_ID_11, false, null);
    }

    @Test
    public void testGetTrademarkSeniority() {
        FlowBeanImpl original = new FlowBeanImpl();
        original.setIdreserventity("test3");
        assertTrue(importService.getTradeMarkSeniority(original, OFFICE, TM_ID, true, null) != null);
    }

    @Test
    public void testGetTradeMarkTransformation() {
        FlowBeanImpl original = new FlowBeanImpl();
        original.setIdreserventity("test3");
        assertTrue(importService.getTradeMarkTransformation(original, OFFICE, TM_ID, true, null) != null);
    }

    @Test
    public void testGetCTMConversion() {
        FlowBeanImpl original = new FlowBeanImpl();
        original.setIdreserventity("test3");
        assertTrue(importService.getCTMConversion(original, OFFICE, TM_ID, true, null) != null);
    }

    public void testGetCTMConversionWizard() {
        FlowBeanImpl original = new FlowBeanImpl();
        original.resetTempGoodsServices("1", "2", "3");
        original.addTempGoodsServices("1", new GoodAndServiceForm());
        original.setIdreserventity("test3");
        assertTrue(importService.getCTMConversion(original, OFFICE, TM_ID, true, "wizard") != null);
    }

    @Test(expected = SPException.class)
    public void testGetCTMConversionException() {
        FlowBeanImpl original = new FlowBeanImpl();
        original.setIdreserventity("test3");
        importService.getCTMConversion(original, OFFICE_GR, TM_ID_11, true, null);
    }

    @Test
    public void testGetTrademarkAutocomplete() {
        assertTrue(importService.getTradeMarkAutocomplete(OFFICE, "pepe", 10, true) != null);
    }

    @Test
    public void testGetTrademarkAutocompleteException() {
        assertTrue("".equals(importService.getTradeMarkAutocomplete("NOP", "pepe2", 50, true)));
    }

    @Test
    public void testGetPreclearanceReport() {
        assertTrue(importService.getSimilarTradeMarks(OFFICE, null) != null);
    }

    @Test
    public void testGetPreclearanceReportException() {
        assertTrue(importService.getSimilarTradeMarks("OH", null).size() == 0);
    }
}
