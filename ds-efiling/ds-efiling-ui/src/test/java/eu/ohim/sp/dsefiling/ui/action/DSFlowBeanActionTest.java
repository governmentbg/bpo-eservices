package eu.ohim.sp.dsefiling.ui.action;

import eu.ohim.sp.common.ui.adapter.design.DesignerFactory;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.PersonService;
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.dsefiling.ui.adapter.DSFilterImportable;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSApplicationServiceInterface;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSImportDesignsServiceInterface;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DSFlowBeanActionTest
{
    DSFlowBeanImpl flowBean;

    @Mock
    DSDesignsServiceInterface dsService;

    @Mock
    DSApplicationServiceInterface draftApplicationService;

    @Mock
    DSImportDesignsServiceInterface dsImportService;
    
    @Mock
    PersonService personService;
    
    @Mock
    DesignerFactory designerFactory;
    
    @Mock
    DSFilterImportable filterImportable;

    @Mock
    FlowScopeDetails flowScopeSession;
    
    @InjectMocks
    DSFlowBeanAction flowBeanAction = new DSFlowBeanAction();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        
        flowBean = new DSFlowBeanImpl();
    }

    
    @Test
    public void checkAddUserPersonDetails_1() {
    	/// Arrange
    	UserPersonDetails userPersonDetails = new UserPersonDetails();
    	
    	Designer designer = new Designer();
    	userPersonDetails.getDesigners().add(designer);
    	when(personService.addUserPersonDetails(flowBean, "wizard")).thenReturn(userPersonDetails);
    	
    	final DesignerForm designerForm = new DesignerForm();
    	when(designerFactory.convertFrom(designer)).thenReturn(designerForm);
    	
    	when(filterImportable.filterSingleObject(designerForm, "wizard", designerForm.getAvailableSectionName(), null)).thenReturn(designerForm);
    	
    	Mockito.doAnswer(new Answer<DSFlowBean>() {
			@Override
			public DSFlowBean answer(InvocationOnMock invocation) throws Throwable {
				flowBean.addObject(designerForm);
				return flowBean;
			}
		}).when(personService).addPersonFormDetails(flowBean, designerForm, "wizard");
    	
    	/// Act
    	userPersonDetails = flowBeanAction.addUserPersonDetails(flowBean, "wizard");
    	
        /// Assert
    	assertNotNull(userPersonDetails);
    	assertEquals(userPersonDetails.getDesigners().size(), 1);
    	assertEquals(flowBean.getDesigners().size(), 1);
    }
    
    @Test
    public void checkAddUserPersonDetails_2() {
    	/// Arrange
    	UserPersonDetails userPersonDetails = new UserPersonDetails();
    	
    	when(personService.addUserPersonDetails(flowBean, "wizard")).thenReturn(userPersonDetails);
    	
    	/// Act
    	userPersonDetails = flowBeanAction.addUserPersonDetails(flowBean, "wizard");
    	
        /// Assert
    	assertNotNull(userPersonDetails);
    	assertEquals(userPersonDetails.getDesigners().size(), 0);
    	assertEquals(flowBean.getDesigners().size(), 0);
    }
    
    @Test
    public void checkAddUserPersonDetails_3() {
    	/// Arrange
    	UserPersonDetails userPersonDetails = null;
    
    	when(personService.addUserPersonDetails(flowBean, "wizard")).thenReturn(userPersonDetails);
    	
    	/// Act
    	userPersonDetails = flowBeanAction.addUserPersonDetails(flowBean, "wizard");
    	
        /// Assert
    	assertNull(userPersonDetails);
    	assertEquals(flowBean.getDesigners().size(), 0);
    }
    
    
    @Test
    public void checkAddUserPersonDetails_4() {
    	/// Arrange
    	UserPersonDetails userPersonDetails = new UserPersonDetails();
    	
    	Designer designer = new Designer();
    	userPersonDetails.getDesigners().add(designer);
    	
    	when(personService.addUserPersonDetails(flowBean, "wizard")).thenReturn(userPersonDetails);
    	
    	DesignerForm designerForm = new DesignerForm();
    	designerForm.setId("some id");
    	flowBean.addObject(designerForm);
    	when(designerFactory.convertFrom(designer)).thenReturn(designerForm);
    	
    	/// Act
    	userPersonDetails = flowBeanAction.addUserPersonDetails(flowBean, "wizard");
    	
        /// Assert
    	assertNotNull(userPersonDetails);
    	assertEquals(userPersonDetails.getDesigners().size(), 1);
    	assertEquals(flowBean.getDesigners().size(), 1);
    }
    
    @Test
    public void checkThereIsOnlyOneDesign_oneDesign_dsServiceCalled()
    {
        /// Arrange
        flowBean.getDesigns().add(new DesignForm());

        /// Act
        flowBeanAction.checkThereIsOnlyOneDesign(flowBean);


        /// Assert
        verify(dsService, times(1)).removeDesignFromLists(flowBean.getDesigns().get(0), flowBean);
        verify(dsService, times(1)).addDesignToLinkedLists(flowBean.getDesigns().get(0), flowBean);
    }

    @Test
    public void importDesignApplication_dsImportServiceCalled()
    {
        /// Arrange

        /// Act
        flowBeanAction.importDesignApplication(flowBean, "some design id", null);

        /// Assert
        verify(dsImportService, times(1)).importDesignApplication("some design id", null, flowBean);
    }

    @Test
    public void clearLocarno_callsService()
    {
        /// Arrange

        /// Act
        flowBeanAction.clearLocarno(flowBean);

        /// Assert
        verify(dsService, times(1)).clearLocarnoFlow(flowBean);
    }

    @Test
    public void addDesignerFromApplicant_noApplicants_returnsFalse()
    {
        boolean result = flowBeanAction.addDesignerFromApplicant(flowBean);

        assertEquals(false, result);
    }

    @Test
    public void addDesignerFromApplicant_naturalPersonIsDesigner_returnsTrue()
    {
        NaturalPersonForm app = new NaturalPersonForm();
        app.setDesignerIndicator(true);
        flowBean.addObject(app);
        boolean result = flowBeanAction.addDesignerFromApplicant(flowBean);

        assertEquals(true, result);
    }

    @Test
    public void addDesignerFromApplicant_naturalPersonIsDesignerAndAlsoDefinedInDesigners_returnsTrue()
    {
        NaturalPersonForm app = new NaturalPersonForm();
        app.setDesignerIndicator(true);
        app.setId("applicant id");
        flowBean.getApplicants().add(app);
        flowBean.setDesignersFromApplicants(new HashMap<String, String>());
        flowBean.getDesignersFromApplicants().put("applicant id", "applicant id");

        DesignerForm designer = new DesignerForm();
        designer.setId("applicant id");
        flowBean.addObject(designer);

        boolean result = flowBeanAction.addDesignerFromApplicant(flowBean);

        assertEquals(true, result);
    }

    @Test
    public void addDesignerFromApplicant_designerNotFound_returnsTrue()
    {
        NaturalPersonForm app = new NaturalPersonForm();
        app.setDesignerIndicator(true);
        app.setId("applicant id");
        flowBean.getApplicants().add(app);
        flowBean.setDesignersFromApplicants(new HashMap<String, String>());
        flowBean.getDesignersFromApplicants().put("designer id", "applicant id");

        DesignerForm designer = new DesignerForm();
        designer.setId("applicant id");
        flowBean.addObject(designer);

        boolean result = flowBeanAction.addDesignerFromApplicant(flowBean);

        assertEquals(true, result);
    }

    @Test
    public void updateApplicantFromDesigerTest_isDesigner()
    {
        DesignerForm designer = new DesignerForm();
        designer.setId("designer id");
        flowBean.addObject(designer);

        NaturalPersonForm app = new NaturalPersonForm();
        app.setId("designer id");
        app.setDesignerIndicator(true);
        flowBean.addObject(app);

        flowBean.setDesignersFromApplicants(new HashMap<String, String>());
        flowBean.getDesignersFromApplicants().put("designer id", "designer id");

        flowBeanAction.updateApplicantFromDesigner(flowBean);
    }

    @Test
    public void updateApplicantFromDesigerTest_isNotDesigner()
    {
        DesignerForm designer = new DesignerForm();
        designer.setId("designer id");
        flowBean.addObject(designer);

        NaturalPersonForm app = new NaturalPersonForm();
        app.setId("designer id");
        app.setDesignerIndicator(false);
        flowBean.addObject(app);

        flowBean.setDesignersFromApplicants(new HashMap<String, String>());
        flowBean.getDesignersFromApplicants().put("designer id", "designer id");

        flowBeanAction.updateApplicantFromDesigner(flowBean);
    }

    @Test
    public void loadApplication_nullId()
    {
        DSFlowBean result = flowBeanAction.loadApplication(null, null);

    }

    @Test
    public void loadApplication_nonNullId()
    {
        when(flowScopeSession.getLid()).thenReturn("lid");

        DSFlowBean result = flowBeanAction.loadApplication("some id", "lid");

        verify(draftApplicationService, times(1)).loadApplicationLocally("some id");
    }
}