package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.form.design.LocarnoComplexForm;
import eu.ohim.sp.common.ui.form.design.LocarnoForm;
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
public class RemoveLocarnoControllerTest {
    @Mock
    DSFlowBean dsFlowBean;

    @InjectMocks
    RemoveLocarnoController removeLocarnoController = new RemoveLocarnoController();
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void removeLocarno() {
    	LocarnoForm form = new LocarnoForm();
    	form.setId("some id");
    	when(dsFlowBean.getObject(LocarnoForm.class, "some id")).thenReturn(form);

    	ModelAndView result = removeLocarnoController.handleRemoveLocarno("some id");
    	assertEquals("locarno/locarno_list", result.getViewName());
    }
    
    @Test
    public void removeLocarnoComplex() {
    	LocarnoComplexForm form = new LocarnoComplexForm();
    	form.setId("some id");
    	when(dsFlowBean.getObject(LocarnoComplexForm.class, "some id")).thenReturn(form);

    	ModelAndView result = removeLocarnoController.handleRemoveLocarno("some id");
    	assertEquals("locarno/locarno_list", result.getViewName());
    }
}
