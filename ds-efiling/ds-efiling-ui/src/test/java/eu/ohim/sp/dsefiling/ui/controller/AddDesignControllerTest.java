package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.DSDesignsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author serrajo
 */
public class AddDesignControllerTest {
    @Mock
    DSFlowBean dsFlowBean;

    @Mock
    ConfigurationServiceDelegatorInterface configurationServiceDelegator;
    
    @Mock
    FormValidator validator;

    @InjectMocks
    AddDesignController addDesignController = new AddDesignController();
    
    @Mock
    DSDesignsService designsService;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.DESIGN_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT)).thenReturn("5");
    }
    
    @Test
    public void getDesignInvalidId() {
    	when(dsFlowBean.getObject(DesignForm.class, "some id")).thenReturn(null);

    	ModelAndView result = addDesignController.formBackingDesign("some id");

    	assertEquals(new DesignForm(), result.getModel().get("designForm"));
    }

    @Test
    public void addDesign() {
    	ModelAndView result = addDesignController.formBackingDesign(null);
    	
    	assertEquals("designs/design_form", result.getViewName());
    	assertEquals(new DesignForm(), result.getModel().get("designForm"));
    }
    
    @Test
    public void getDesign() {
    	DesignForm form = new DesignForm();
    	form.setId("some id");
    	when(dsFlowBean.getObject(DesignForm.class, "some id")).thenReturn(form);
    	
    	ModelAndView result = addDesignController.formBackingDesign("some id");
    
    	assertEquals("designs/design_form", result.getViewName());
    }
    
    @Test
    public void saveDesign() {
    	BindingResult binding = mock(BindingResult.class);
    	
    	DesignForm form = new DesignForm();
    	ModelAndView result = addDesignController.onSubmitDesign(form, binding);

    	assertEquals("designs/designs_list", result.getViewName());
    }
    
    @Test
    public void saveDesign_failsValidation() {
    	BindingResult binding = mock(BindingResult.class);
    	when(binding.hasErrors()).thenReturn(true);

    	DesignForm form = new DesignForm();
    	ModelAndView result = addDesignController.onSubmitDesign(form, binding);

    	assertEquals("designs/design_form", result.getViewName());
    }

    @Test
    public void viewDesign() {
    	DesignForm form = new DesignForm();
    	form.setId("some id");
    	when(dsFlowBean.getObject(DesignForm.class, "some id")).thenReturn(form);
    	
    	ModelAndView result = addDesignController.viewDesign("some id");
    
    	assertEquals("designs/design_view", result.getViewName());
    }

    @Test
    public void duplicateDesign() {
    	DesignForm form = new DesignForm();
    	form.setId("some id");
    	
    	when(dsFlowBean.getObject(DesignForm.class, "some id")).thenReturn(form);
    	
    	ModelAndView result = addDesignController.duplicateDesign("some id", 1);
    	
    	assertEquals("designs/designs_list", result.getViewName());
    	assertEquals(Boolean.FALSE, result.getModel().get("maximumEntitiesReached"));
    }
    
}
