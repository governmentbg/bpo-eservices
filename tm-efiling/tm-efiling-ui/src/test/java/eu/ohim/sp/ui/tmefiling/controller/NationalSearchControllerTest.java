package eu.ohim.sp.ui.tmefiling.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;
import eu.ohim.sp.ui.tmefiling.form.MainForm;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class NationalSearchControllerTest {
	
	@InjectMocks
	private NationalSearchController nationalSearchController;
	
	@Mock
    private TMFlowBean flowBean;
	
	@Before
	public void before(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void changeCollectiveTest() {
		boolean value = false;
		
		MainForm mainform = mock(MainForm.class);
		when(flowBean.getMainForm()).thenReturn(mainform );
		ModelAndView r = nationalSearchController.changeCollective(value);
		
		assertNotNull(r);
		verify(mainform).setNationalSearchReport(value);
		
		
	}
}
