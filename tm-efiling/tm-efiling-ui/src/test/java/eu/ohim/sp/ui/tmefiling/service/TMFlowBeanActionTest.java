package eu.ohim.sp.ui.tmefiling.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.flow.section.TrademarkFlowBean;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.ui.tmefiling.action.TMFlowBeanAction;
import eu.ohim.sp.ui.tmefiling.service.interfaces.ApplicationServiceInterface;

@RunWith(MockitoJUnitRunner.class)
public class TMFlowBeanActionTest {

	@InjectMocks
	private TMFlowBeanAction action;

	@Mock
	private PersonServiceInterface personService;

	@Mock
	private ApplicantFactory applicantFactory;

	@Mock
	ApplicationServiceInterface draftApplicationService;

	@Before
	public void before() {
		// action = new TMFlowBeanAction(personService, applicantFactory);
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void adduserpersonDetails() {
		UserPersonDetails pd = mock(UserPersonDetails.class);

		List<eu.ohim.sp.core.domain.person.Applicant> applicants = new ArrayList<>();
		eu.ohim.sp.core.domain.person.Applicant a1 = mock(eu.ohim.sp.core.domain.person.Applicant.class);
		eu.ohim.sp.core.domain.person.Applicant a2 = mock(eu.ohim.sp.core.domain.person.Applicant.class);
		applicants.add(a1);
		applicants.add(a2);
		when(applicantFactory.convertFrom(a1)).thenReturn(mock(ApplicantForm.class));
		when(applicantFactory.convertFrom(a2)).thenReturn(mock(ApplicantForm.class));
		when(pd.getApplicants()).thenReturn(applicants);
		String flowModId = "flowModId";
		FlowBean flowBean = mock(FlowBean.class);

		when(personService.addUserPersonDetails(flowBean, flowModId)).thenReturn(pd);

		assertEquals(pd, action.addUserPersonDetails(flowBean, flowModId));
		verify(personService).addUserPersonDetails(flowBean, flowModId);
		verify(applicantFactory).convertFrom(a1);
		verify(applicantFactory).convertFrom(a2);
	}

	@Test
	public void adduserpersonDetails2() {

		String flowModId = "flowModId";
		FlowBean flowBean = mock(FlowBean.class);
		when(personService.addUserPersonDetails(flowBean, flowModId)).thenThrow(Exception.class);

		assertNull(action.addUserPersonDetails(flowBean, flowModId));
		verify(personService).addUserPersonDetails(flowBean, flowModId);
	}

	@Test
	public void loadApplicationTest() {

		String formId = null;
		String lid = null;
		assertNotNull(action.loadApplication(formId, lid, mock(FlowScopeDetails.class)));
		verify(draftApplicationService, never()).loadApplicationLocally(formId);
	}

	@Test
	public void loadApplicationTest2() {

		String formId = "asdf";
		String temp = "temp";
		String lid = temp;
		FlowScopeDetails flowScopeSession = mock(FlowScopeDetails.class);
		when(flowScopeSession.getLid()).thenReturn(temp);
		when(draftApplicationService.loadApplicationLocally(formId)).thenReturn(mock(TrademarkFlowBean.class));

		assertNotNull(action.loadApplication(formId, lid, flowScopeSession));
		verify(draftApplicationService).loadApplicationLocally(formId);

	}

	@Test
	public void loadApplicationTest3() {

		String formId = "asdf";
		String temp = "temp";
		String lid = "werqe";
		FlowScopeDetails flowScopeSession = mock(FlowScopeDetails.class);
		when(flowScopeSession.getLid()).thenReturn(temp);

		assertNotNull(action.loadApplication(formId, lid, flowScopeSession));
		verify(draftApplicationService, never()).loadApplicationLocally(formId);
	}
}
