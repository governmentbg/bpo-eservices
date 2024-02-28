package eu.ohim.sp.eservices.ui.controllerTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.adapter.AssigneeFactory;
import eu.ohim.sp.common.ui.adapter.HolderFactory;
import eu.ohim.sp.common.ui.form.person.HolderForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Holder;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.eservices.ui.controller.AddUserHolderController;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;

public class AddUserHolderControllerTest {

	@Mock
	private ESFlowBean flowBean;

	@Mock
	private PersonServiceInterface personService;

	@Mock
	private FlowScopeDetails flowScopeDetails;

	@Mock
	private AssigneeFactory assigneeFactory;
	
	@Mock
    private HolderFactory holderFactory;

	@InjectMocks
	AddUserHolderController addUserAssigneeControllerTest = new AddUserHolderController();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void addUserNaturalPersonDetails() {
		UserPersonDetails user = Mockito.mock(UserPersonDetails.class);
		Mockito.when(personService.getUserPersonDetails(flowScopeDetails.getFlowModeId())).thenReturn(user);
		
		List<Applicant> applicantsL = new ArrayList<Applicant>();
		Applicant applicant = Mockito.mock(Applicant.class);
		Mockito.when(applicant.getKind()).thenReturn(PersonKind.NATURAL_PERSON);
		Mockito.when(user.getApplicants()).thenReturn(applicantsL);
		HolderForm holderForm = Mockito.mock(HolderForm.class);
		Mockito.when(holderFactory.convertFrom((Holder) Mockito.anyObject())).thenReturn(holderForm);
		addUserAssigneeControllerTest.addUserNaturalPersonDetails();
		applicantsL.add(applicant);
		addUserAssigneeControllerTest.addUserNaturalPersonDetails();
		Applicant applicant2 = Mockito.mock(Applicant.class);
		Mockito.when(applicant2.getKind()).thenReturn(PersonKind.NATURAL_PERSON);
		applicantsL.add(applicant2);
		addUserAssigneeControllerTest.addUserNaturalPersonDetails();
	}

	@Test
	public void addUserLegalEntityDetails() {
		UserPersonDetails user = Mockito.mock(UserPersonDetails.class);
		Mockito.when(personService.getUserPersonDetails(flowScopeDetails.getFlowModeId())).thenReturn(user);
		
		List<Applicant> applicantsL = new ArrayList<Applicant>();
		Applicant applicant = Mockito.mock(Applicant.class);
		Mockito.when(applicant.getKind()).thenReturn(PersonKind.LEGAL_ENTITY);
		Mockito.when(user.getApplicants()).thenReturn(applicantsL);
		HolderForm holderForm = Mockito.mock(HolderForm.class);
		Mockito.when(holderFactory.convertFrom((Holder) Mockito.anyObject())).thenReturn(holderForm);
		addUserAssigneeControllerTest.addUserLegalEntityDetails();
		applicantsL.add(applicant);
		addUserAssigneeControllerTest.addUserLegalEntityDetails();
		Applicant applicant2 = Mockito.mock(Applicant.class);
		Mockito.when(applicant2.getKind()).thenReturn(PersonKind.LEGAL_ENTITY);
		applicantsL.add(applicant2);
		addUserAssigneeControllerTest.addUserLegalEntityDetails();
	}
	
	@Test
	public void addUserPersonDetails() {
		List<String> selectedAssigneeUser = new ArrayList<String>();
		selectedAssigneeUser.add("test");
		addUserAssigneeControllerTest.addUserPersonDetails(selectedAssigneeUser);
	}

}
