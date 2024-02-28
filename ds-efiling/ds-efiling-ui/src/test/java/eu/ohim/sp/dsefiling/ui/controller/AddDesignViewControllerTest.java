package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.form.design.DesignViewForm;
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
public class AddDesignViewControllerTest {
//    @Mock
//    DSFlowBean dsFlowBean;
//
//    @Mock
//    ConfigurationServiceDelegatorInterface configurationServiceDelegator;
//
//    @Mock
//    FormValidator validator;
//
//    @InjectMocks
//    AddDesignViewController addDesignViewController = new AddDesignViewController();
//
//    @Mock
//    DSDesignsService designsService;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.DESIGN_VIEW_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT)).thenReturn("5");
//    }
//
//    @Test
//    public void getDesignViewInvalidId() {
//    	when(dsFlowBean.getObject(DesignViewForm.class, "some id")).thenReturn(null);
//
//    	ModelAndView result = addDesignViewController.getDesignView("some id");
//
//    	assertEquals(new DesignViewForm(), result.getModel().get("designViewForm"));
//    }
//
//    @Test
//    public void addDesignView() {
//    	ModelAndView result = addDesignViewController.addDesignView();
//
//    	assertEquals("designs/views/view_add", result.getViewName());
//    	assertEquals(new DesignViewForm(), result.getModel().get("designViewForm"));
//    }
//
//    @Test
//    public void getDesignView() {
//    	DesignViewForm form = new DesignViewForm();
//    	form.setId("some id");
//    	when(dsFlowBean.getObject(DesignViewForm.class, "some id")).thenReturn(form);
//
//    	ModelAndView result = addDesignViewController.getDesignView("some id");
//
//    	assertEquals("designs/views/view_add", result.getViewName());
//    }
//
//    @Test
//    public void saveDesignView() {
//    	BindingResult binding = mock(BindingResult.class);
//
//    	DesignViewForm form = new DesignViewForm();
//    	ModelAndView result = addDesignViewController.saveDesignView(form, binding);
//
//    	assertEquals("designs/views/view_list", result.getViewName());
//    }
//
//    @Test
//    public void saveDesignView_failsValidation() {
//    	BindingResult binding = mock(BindingResult.class);
//    	when(binding.hasErrors()).thenReturn(true);
//
//    	DesignViewForm form = new DesignViewForm();
//    	ModelAndView result = addDesignViewController.saveDesignView(form, binding);
//
//    	assertEquals("designs/views/view_add", result.getViewName());
//    }
//
//    @Test
//    public void viewDesignView() {
//    	DesignViewForm form = new DesignViewForm();
//    	form.setId("some id");
//    	when(dsFlowBean.getObject(DesignViewForm.class, "some id")).thenReturn(form);
//
//    	ModelAndView result = addDesignViewController.viewDesignView("some id");
//
//    	assertEquals("designs/views/view_view", result.getViewName());
//    }
    
}
