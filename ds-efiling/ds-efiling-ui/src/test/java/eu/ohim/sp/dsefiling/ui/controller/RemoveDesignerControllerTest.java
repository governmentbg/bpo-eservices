package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;
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
public class RemoveDesignerControllerTest {
    @Mock
    DSFlowBean dsFlowBean;

    @Mock
    DSDesignsServiceInterface dsDesignsService;
    
    @InjectMocks
    RemoveDesignerController removeDesignerController = new RemoveDesignerController();
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void removeDesigner() {
    	DesignerForm form = new DesignerForm();
    	form.setId("some id");
    	when(dsFlowBean.getObject(DesignerForm.class, "some id")).thenReturn(form);

    	ModelAndView result = removeDesignerController.handleRemoveDesigner("some id");
    	assertEquals("designers/designer_card_list", result.getViewName());
    }
    
}
