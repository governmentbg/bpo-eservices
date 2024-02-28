package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.DSDesignsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author serrajo
 */
public class RemoveDesignControllerTest {
    @Mock
    DSFlowBean dsFlowBean;

    @InjectMocks
    RemoveDesignController removeDesignController = new RemoveDesignController();
    
    @Mock
    DSDesignsService designsService;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void removeDesign() {
    	DesignForm form = new DesignForm();
    	form.setId("some id");
    	when(dsFlowBean.getObject(DesignForm.class, "some id")).thenReturn(form);

    	ModelAndView result = removeDesignController.handleRemoveDesign("some id");
    	assertEquals("designs/designs_list", result.getViewName());
    }
    
}
