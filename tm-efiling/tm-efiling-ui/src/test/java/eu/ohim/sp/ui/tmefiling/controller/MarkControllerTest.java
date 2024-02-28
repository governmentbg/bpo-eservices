package eu.ohim.sp.ui.tmefiling.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.service.constant.AppConstants;
import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;
import eu.ohim.sp.ui.tmefiling.form.MainForm;

@RunWith(MockitoJUnitRunner.class)
public class MarkControllerTest {

	@InjectMocks
	private MarkController markController;

	@Mock
	private TMFlowBean flowBean;

	private MainForm mainForm;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void changeCollective() {

		mainForm = mock(MainForm.class);

		when(flowBean.getMainForm()).thenReturn(mainForm);
		when(mainForm.getCollectiveMark()).thenReturn(true);

		ModelAndView r = markController.changeCollective();
		assertNotNull(r);
		verify(mainForm).setCollectiveMark(false);
	}

	@Test
	public void changeCollective2() {

		mainForm = mock(MainForm.class);

		when(flowBean.getMainForm()).thenReturn(mainForm);
		when(mainForm.getCollectiveMark()).thenReturn(false);

		ModelAndView r = markController.changeCollective();
		assertNotNull(r);
		verify(mainForm).setCollectiveMark(true);
	}

	@Test
	public void clearMarkTypeTest() {
		mainForm = mock(MainForm.class);

		when(flowBean.getMainForm()).thenReturn(mainForm);

		ModelAndView r = markController.clearMarkType();
		assertNotNull(r);

		verify(mainForm).clearInformation();
		verify(mainForm).setMarkType(AppConstants.MarkTypeSelect);
	}

	@Test
	public void changeMarkTypeTest() {
		mainForm = mock(MainForm.class);

		when(flowBean.getMainForm()).thenReturn(mainForm);

		String id = "id";
		String mode = "mode";
		ModelAndView r = markController.changeMarkType(id, mode);
		assertNotNull(r);
		assertEquals("marks/" + mode + "/" + id, r.getViewName());

		verify(mainForm).clearInformation();
		verify(mainForm).setMarkType(id);
	}

}
