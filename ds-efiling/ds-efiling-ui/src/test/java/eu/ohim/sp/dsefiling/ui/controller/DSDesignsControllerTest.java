package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.dsefiling.ui.action.DSFlowBeanAction;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;
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
public class DSDesignsControllerTest {
	
	@Mock
	DSFlowBeanImpl flowBean;
    
    @Mock
    DSFlowBeanAction dsFlowBeanAction;

    @Mock
    DSDesignsService dsDesignsService;
    
    @InjectMocks
    DSDesignsController dsDesignsController = new DSDesignsController();
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        flowBean = new DSFlowBeanImpl();
    }
    
    @Test
    public void isDesignLinked() {
    	DesignForm designForm = new DesignForm();
    	designForm.setId("some id");
    	flowBean.addObject(designForm);
    	
    	when(dsDesignsService.isDesignLinkedInSomeForm(designForm, flowBean)).thenReturn(Boolean.FALSE);
    	
    	Boolean isDesignLinked = new Boolean(dsDesignsController.isDesignLinked("some id"));
    	assertEquals(isDesignLinked, Boolean.FALSE);
    }
    
    @Test
    public void getDivisionalApplicationTable() {
    	ModelAndView result = dsDesignsController.getDivisionalApplicationTable();
    	assertEquals("divisionalApplication/divisional_application_table", result.getViewName());
    }
    
    @Test
    public void getClaimsTable() {
    	ModelAndView result = dsDesignsController.getClaimsTable();
    	assertEquals("claim/claim_table_common", result.getViewName());
    }

    @Test
    public void getDesignersTable() {
    	ModelAndView result = dsDesignsController.getDesignersTable();
    	assertEquals("designers/designer_card_list", result.getViewName());
    }
    
    @Test
    public void addDesignerFromApplicant() {
    	ModelAndView result = dsDesignsController.addDesignerFromApplicant();
    	assertEquals("designers/designer_card_list", result.getViewName());
    	assertEquals(Boolean.FALSE, result.getModelMap().get("isThere"));
    }
    
    @Test
    public void updateApplicantFromDesigner() {
    	ModelAndView result = dsDesignsController.updateApplicantFromDesigner();
    	assertEquals("applicant/applicant_card_list", result.getViewName());
    }
}
