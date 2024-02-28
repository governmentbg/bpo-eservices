package eu.ohim.sp.eservices.ui.controllerTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.adapter.AssigneeFactory;
import eu.ohim.sp.common.ui.form.person.AssigneeForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Assignee;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.eservices.ui.controller.AddUserAssigneeController;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;

public class AddUserAssigneeControllerTest {

	@Mock
	private ESFlowBean flowBean;

	@Mock
	private PersonServiceInterface personService;

	@Mock
	private FlowScopeDetails flowScopeDetails;

	@Mock
	private AssigneeFactory assigneeFactory;

	@InjectMocks
	AddUserAssigneeController addUserAssigneeControllerTest = new AddUserAssigneeController();

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
		addUserAssigneeControllerTest.addUserNaturalPersonDetails();
		applicantsL.add(applicant);
		Mockito.when(applicant.getKind()).thenReturn(PersonKind.NATURAL_PERSON);
		Mockito.when(user.getApplicants()).thenReturn(applicantsL);
		AssigneeForm assigneeForm = Mockito.mock(AssigneeForm.class);
		Mockito.when(assigneeFactory.convertFrom((Assignee) Mockito.anyObject())).thenReturn(assigneeForm);
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
		addUserAssigneeControllerTest.addUserLegalEntityDetails();
		applicantsL.add(applicant);
		AssigneeForm assigneeForm = Mockito.mock(AssigneeForm.class);
		Mockito.when(assigneeFactory.convertFrom((Assignee) Mockito.anyObject())).thenReturn(assigneeForm);
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
