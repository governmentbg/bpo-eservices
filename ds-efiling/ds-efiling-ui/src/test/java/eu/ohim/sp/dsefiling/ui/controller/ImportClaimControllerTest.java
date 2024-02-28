package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.adapter.design.DSPriorityFactory;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.DSDesignsService;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSImportDesignsServiceInterface;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author serrajo
 */
public class ImportClaimControllerTest {
	@Mock
	DSFlowBean dsFlowBean;
	
	@Mock
	DSDesignsService designsService;
	
    @Mock
    HttpServletRequest request;

    @Mock
    ConfigurationServiceDelegatorInterface configurationServiceDelegator;

	@Mock
	DSImportDesignsServiceInterface dsImportDesignsServiceInterface;

    @Mock
    DSPriorityFactory dsPriorityFactory;
    
    @InjectMocks
    ImportClaimController importClaimController = new ImportClaimController();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.CLAIM_PRIORITY_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT))
        .thenReturn("3");
    }

    @Test
     public void importPriorityTest1() {
        importClaimController.importPriority(request, null, "office");
    }

    @Test
    public void importPriorityTest2() {
        ModelAndView result = importClaimController.importPriority(request, "id", "office");
        assertEquals("errors/importError", result.getViewName());
    }

}
