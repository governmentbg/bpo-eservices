package eu.ohim.sp.ui.tmefiling.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.ohim.sp.core.application.ApplicationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.adapter.SimilarMarkFactory;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.core.register.TradeMarkSearchService;
import eu.ohim.sp.core.report.ReportService;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;

public class ReportControllertTest {

	@InjectMocks
	private ReportController reportController;

	@Mock
	private FlowBeanImpl flowBean;

	@Mock
	private TradeMarkSearchService tradeMarkSearchService;

	@Mock
	private FlowBeanFactory flowBeanFactory;

	@Mock
	private SimilarMarkFactory similarMarkFactory;

	@Mock
	private ReportService reportService;

	@Mock
	private ApplicationService applicationService;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getReceiptTest(){
		TradeMarkApplication application = mock(TradeMarkApplication.class);
		String firstLang = "hu";
		when(flowBean.getFirstLang()).thenReturn(firstLang );
		when(flowBeanFactory.convertToTradeMarkApplication(flowBean)).thenReturn(application);
		when(applicationService.fillApplicationDocuments(application)).thenReturn(application);
		
		byte[] bytevalue = new byte[]{1,1,3,3};
		when(reportService.generateReport(ReportController.MODULE, ReportService.RECEIPT_REPORT, firstLang, application,
				null, true, null)).thenReturn(bytevalue );
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		byte[] r = reportController.getReceipt(request, response );
		
		assertNotNull(r);
		assertEquals(bytevalue, r);
		
		
		
	}
	
	@Test
	public void getSimilarTMReportTest() throws IOException{
		TradeMarkApplication application = mock(TradeMarkApplication.class);
		TradeMark trademark = mock(TradeMark.class);
		String office = "office";
		when(trademark.getReceivingOffice()).thenReturn(office);
		
		when(application.getTradeMark()).thenReturn(trademark);
		String firstLang = "hu";
		when(flowBean.getFirstLang()).thenReturn(firstLang );
		
		byte[] bytevalue = new byte[]{1,1,3,3};
		
		when(flowBeanFactory.convertToTradeMarkApplication(flowBean)).thenReturn(application);
		
		List<TradeMark> trademarklist = new ArrayList<>();
		when(tradeMarkSearchService.getPreclearanceReport(office, trademark)).thenReturn(trademarklist );
		
		when(reportService.generateReport(ReportController.MODULE, ReportService.SIMILAR_TRADE_MARK_REPORT, firstLang, trademarklist,
				null, true, null)).thenReturn(bytevalue );
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		
		
		byte[] r = reportController.getSimilarTMReport(request, response);
		
		assertNotNull(r);
		assertEquals(bytevalue, r);
		
		
		
	}

}
