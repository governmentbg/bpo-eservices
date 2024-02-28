package eu.ohim.sp.eservices.ui.serviceTest;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.eservices.ui.adapter.ESFlowBeanFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.adapter.LimitedTrademarkFactory;
import eu.ohim.sp.common.ui.adapter.design.ESDesignFactory;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.ESFeeService;
import eu.ohim.sp.eservices.ui.service.FormUtil;
import eu.ohim.sp.eservices.ui.service.FormUtil.FormMainType;

public class ESFeeServiceTest extends ESFeeService {
	
	@Mock
	private FormUtil formUtil;
	
	@Mock
    private LimitedTrademarkFactory limitedTrademarkFactory;
	
	@Mock
	private ESDesignFactory designFactory;

	@Mock
	private ESFlowBeanFactory flowBeanFactory;
	
	@Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

	@InjectMocks
	ESFeeService eSFeeService=this;
	 
	@Test
	public void testProtecteds(){
		//getModule
		Mockito.when(formUtil.getModule()).thenReturn("module");
		Assert.assertEquals("module", this.getModule());
		
		//getFeesInputData
		List<TMDetailsForm> listtms = new ArrayList<TMDetailsForm>(); 
		listtms.add(Mockito.mock(TMDetailsForm.class));
		List<ESDesignDetailsForm> listdss = new ArrayList<ESDesignDetailsForm>(); 
		listdss.add(Mockito.mock(ESDesignDetailsForm.class));
		ESFlowBean flowBean = Mockito.mock(ESFlowBean.class);
		Mockito.when(flowBean.getTmsList()).thenReturn(listtms);
		Mockito.when(flowBean.getDssList()).thenReturn(listdss);
		
		Mockito.when(formUtil.getMainType()).thenReturn(FormMainType.TM);
		Assert.assertNotNull(this.getFeesInputData(flowBean));
		
		Mockito.when(formUtil.getMainType()).thenReturn(FormMainType.DS);
		Assert.assertNotNull(this.getFeesInputData(flowBean));
		
		Mockito.when(formUtil.getMainType()).thenReturn(null);
		Assert.assertNotNull(this.getFeesInputData(flowBean));
	}
	
	
}
