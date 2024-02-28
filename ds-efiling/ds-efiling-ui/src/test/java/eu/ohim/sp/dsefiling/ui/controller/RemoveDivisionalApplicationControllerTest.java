package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
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
public class RemoveDivisionalApplicationControllerTest {
    @Mock
    DSFlowBean dsFlowBean;

    @InjectMocks
    RemoveDivisionalApplicationController removeDivisionalApplicationController = new RemoveDivisionalApplicationController();
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void removeDesignView() {
    	DSDivisionalApplicationForm form = new DSDivisionalApplicationForm();
    	form.setId("some id");
    	when(dsFlowBean.getObject(DSDivisionalApplicationForm.class, "some id")).thenReturn(form);

    	ModelAndView result = removeDivisionalApplicationController.handleRemoveDivisionalApplication("some id");
    	assertEquals("divisionalApplication/divisional_application_table", result.getViewName());
    }
    
}
