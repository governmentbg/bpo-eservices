package eu.ohim.sp.dsefiling.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;
import eu.ohim.sp.core.report.ReportServiceLocal;
import eu.ohim.sp.dsefiling.ui.adapter.DSFlowBeanFactory;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;

/**
 * @author serrajo
 */
public class DSReportControllerTest {
	@Mock
	DSFlowBean dsFlowBean;
	
	@Mock
	DSFlowBeanFactory flowBeanFactory;
	
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;
    
    @Mock
	FileServiceInterface fileService;

	@Mock
	ReportServiceLocal reportService;
	 
    @InjectMocks
    DSReportController dsReportController = new DSReportController();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void getReceipt() {
    	byte[] pdf = dsReportController.getReceipt(request, response);
    	
    	// Assert.assertNotEquals(pdf, null);
    }
    
}
