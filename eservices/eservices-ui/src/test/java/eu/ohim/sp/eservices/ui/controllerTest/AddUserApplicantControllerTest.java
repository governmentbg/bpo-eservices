package eu.ohim.sp.eservices.ui.controllerTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.eservices.ui.controller.AddUserApplicantController;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;


public class AddUserApplicantControllerTest {

	@Mock
    private ESFlowBean flowBean;

	@Mock
    private PersonServiceInterface personService;
	
	@Mock
    private FlowScopeDetails flowScopeDetails;
    
	@Mock
    private ApplicantFactory applicantFactory;
	
	@Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }
	
	@InjectMocks
	AddUserApplicantController addUserApplicantController=new AddUserApplicantController();
//	@Test(expected=SPException.class)
	
	@Test
    public void addUserPersonDetails() {
		
		List<String> selectedApplicantUser=new ArrayList<String>();
		selectedApplicantUser.add("user1");
		
		ApplicantForm applicantForm=Mockito.mock(ApplicantForm.class);
		Mockito.when(applicantForm.getId()).thenReturn("user1");
		List<ApplicantForm> listapps=new ArrayList<ApplicantForm>();
		listapps.add(applicantForm);
		
		Mockito.when(flowBean.getUserApplicantsForm()).thenReturn(listapps);
		
		Assert.assertNotNull(addUserApplicantController.addUserPersonDetails(selectedApplicantUser));
    }
	
	@Test
	public void addUserNaturalPersonDetails() {	
			
		Applicant applicant=Mockito.mock(Applicant.class);
		Mockito.when(applicant.getKind()).thenReturn(PersonKind.NATURAL_PERSON);
		List <Applicant> applicants = new ArrayList<Applicant>();
		applicants.add(applicant);
		UserPersonDetails userPersonDetails = Mockito.mock(UserPersonDetails.class);
		Mockito.when(userPersonDetails.getApplicants()).thenReturn(applicants);
		Mockito.when(personService.getUserPersonDetails(flowScopeDetails.getFlowModeId())).thenReturn(userPersonDetails);
		Mockito.when(applicantFactory.convertFrom(applicant)).thenReturn(Mockito.mock(ApplicantForm.class));
		Assert.assertNotNull(addUserApplicantController.addUserNaturalPersonDetails());
	
		applicants.add(applicant);
		Assert.assertNotNull(addUserApplicantController.addUserNaturalPersonDetails());
	}
	
	
	@Test
	public void addUserLegalEntityDetails() {	
			
		Applicant applicant=Mockito.mock(Applicant.class);
		Mockito.when(applicant.getKind()).thenReturn(PersonKind.LEGAL_ENTITY);
		List <Applicant> applicants = new ArrayList<Applicant>();
		applicants.add(applicant);
		UserPersonDetails userPersonDetails = Mockito.mock(UserPersonDetails.class);
		Mockito.when(userPersonDetails.getApplicants()).thenReturn(applicants);
		Mockito.when(personService.getUserPersonDetails(flowScopeDetails.getFlowModeId())).thenReturn(userPersonDetails);
		Mockito.when(applicantFactory.convertFrom(applicant)).thenReturn(Mockito.mock(ApplicantForm.class));
		Assert.assertNotNull(addUserApplicantController.addUserLegalEntityDetails());
	
		applicants.add(applicant);
		Assert.assertNotNull(addUserApplicantController.addUserLegalEntityDetails());
	}
	
	
	
	
	
	
	
}
