package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSImportDesignsServiceInterface;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author ionitdi
 */
public class ImportDesignControllerTest
{
    HttpServletRequest request;

    @Mock
    DSFlowBean dsFlowBean;

    @Mock
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Mock
    private DSDesignsServiceInterface dsDesignsService;

    @Mock
    private DSImportDesignsServiceInterface dsImportDesignsService;

    @InjectMocks
    ImportDesignController importDesignController = new ImportDesignController();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        request = mock(HttpServletRequest.class);
    }

    @Test
    public void importFormTest1()
    {
        ModelAndView result = importDesignController.importForm(null, null);

        assertNull(result);
    }

    @Test
    public void importFormTest2()
    {
        when(request.getAttribute("flowModeId")).thenReturn("oneform");
        when(configurationServiceDelegator.getValue("design.add.maxNumber", "general")).thenReturn("11");

        ModelAndView result = importDesignController.importForm(request, "id");

        assertEquals("errors/importError", result.getViewName());
    }

    @Test
    public void importFormTest3()
    {
        when(request.getAttribute("flowModeId")).thenReturn("oneform");
        when(configurationServiceDelegator.getValue("design.add.maxNumber", "general")).thenReturn("11");
        when(dsImportDesignsService.importDesign(eq("id"), any(DSFlowBean.class))).thenReturn(new DesignForm());

        ModelAndView result = importDesignController.importForm(request, "id");

        assertEquals("designs/designs_list", result.getViewName());
    }
}
