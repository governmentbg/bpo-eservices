package eu.ohim.sp.eservices.ui.serviceTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.eservices.ui.service.FormUtil;

public class FormUtilTest {
	@Mock
	private FlowScopeDetails flowScopeDetails;
	
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
	}
	 
	@InjectMocks
	FormUtil formUtil=new FormUtil();
	
	@Test
	public void testing(){
		Mockito.when(flowScopeDetails.getFlowModeId()).thenReturn("TM");
		Assert.assertNotNull(formUtil.getType());
		Assert.assertNotNull(formUtil.getMainType());
		
		Mockito.when(flowScopeDetails.getFlowModeId()).thenReturn("DS");
		Assert.assertNotNull(formUtil.getMainType());
	}
	
	@Test(expected=RuntimeException.class)
	public void testingException(){
		Mockito.when(flowScopeDetails.getFlowModeId()).thenReturn("TD");
		formUtil.getMainType();
	}
}
