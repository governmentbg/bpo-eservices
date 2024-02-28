package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.form.design.DesignViewForm;
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
public class RemoveDesignViewControllerTest {
    @Mock
    DSFlowBean dsFlowBean;

    @InjectMocks
    RemoveDesignViewController removeDesignViewController = new RemoveDesignViewController();
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void removeDesignView() {
    	DesignViewForm form = new DesignViewForm();
    	form.setId("some id");
    	when(dsFlowBean.getObject(DesignViewForm.class, "some id")).thenReturn(form);

    	ModelAndView result = removeDesignViewController.handleRemoveDesignView("some id");
    	assertEquals("designs/views/view_list", result.getViewName());
    }
    
}
