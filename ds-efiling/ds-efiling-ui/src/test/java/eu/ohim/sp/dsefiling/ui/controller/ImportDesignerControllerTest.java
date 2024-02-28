package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.DSDesignsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * @author serrajo
 */
public class ImportDesignerControllerTest {
	@Mock
	DSFlowBean dsFlowBean;
	
	@Mock
	DSDesignsService designsService;
	
    @Mock
    PersonServiceInterface personService;

    @Mock
    HttpServletRequest request;

    @Mock
    ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @InjectMocks
    ImportDesignerController importDesignerController = new ImportDesignerController();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.DESIGNER_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.PERSON_COMPONENT))
        .thenReturn("5");
    }

//    @Test
//    public void importForm_designerDoesNotExist_returnsErrorView() {
//    	// Arrange
//    	when(personService.importDesigner(eq("some id"), anyString())).thenReturn(null);
//
//        // Act
//        ModelAndView result = importDesignerController.importForm(request, "some id");
//
//        // Assert
//        assertEquals("errors/importError", result.getViewName());
//    }
//    @Test
//    public void importForm_returnedDesignerWaiver_returnsDesignerWaiverView() {
//    	// Arrange
//    	DesignerForm form = new DesignerForm();
//    	form.setWaiver(true);
//    	when(personService.importDesigner(eq("some id"), anyString())).thenReturn(form);
//
//        // Act
//    	ModelAndView result = importDesignerController.importForm(request, "some id");
//
//        // Assert
//    	assertEquals("designers/designer_waiver", result.getViewName());
//    }
//
//    @Test
//    public void importForm_returnedDesignerBelongsToAGroup_returnsDesignerBelongsToAGroupView() {
//    	// Arrange
//    	DesignerForm form = new DesignerForm();
//    	form.setBelongsToAGroup(true);
//    	when(personService.importDesigner(eq("some id"), anyString())).thenReturn(form);
//
//        // Act
//    	ModelAndView result = importDesignerController.importForm(request, "some id");
//
//        // Assert
//    	assertEquals("designers/designer_group", result.getViewName());
//    }
    
    @Test
    public void importForm_returnedDesignerNotBelongsToAGroup_returnsDesignerBelongsNotAGroupView() {
    	// Arrange
    	DesignerForm form = new DesignerForm();
    	when(personService.importDesigner(eq("some id"), anyString())).thenReturn(form);

        // Act
    	ModelAndView result = importDesignerController.importForm(request, "some id");

        // Assert
    	assertEquals("designers/designer_notagroup", result.getViewName());
    }

}
