package eu.ohim.sp.eservices.ui.domainTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.eservices.ui.domain.ESMainForm;

public class ESMainFormTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@InjectMocks
	ESMainForm eSFlowBean = new ESMainForm();
	
	@Test
	public void testGetsAndSets(){
		
		Boolean test=true;
		
		eSFlowBean.setPersonalDataSection(test);
		Assert.assertEquals(test, eSFlowBean.getPersonalDataSection());
		
		eSFlowBean.setClaimSection(test);
		Assert.assertEquals(test, eSFlowBean.getClaimSection());
		
		eSFlowBean.setLanguageSection(test);
		Assert.assertEquals(test, eSFlowBean.getLanguageSection());
		
		eSFlowBean.setMarkRepresentationSection(test);
		Assert.assertEquals(test, eSFlowBean.getMarkRepresentationSection());
		
		eSFlowBean.setSignatureSection(test);
		Assert.assertEquals(test, eSFlowBean.getSignatureSection());
		
		eSFlowBean.setGsSection(test);
		Assert.assertEquals(test, eSFlowBean.getGsSection());

		eSFlowBean.setPaymentDataSection(test);
		Assert.assertEquals(test, eSFlowBean.getPaymentDataSection());
		
		eSFlowBean.setReferenceSection(test);
		Assert.assertEquals(test, eSFlowBean.getReferenceSection());
		
		eSFlowBean.setDivisionalSection(test);
		Assert.assertEquals(test, eSFlowBean.getDivisionalSection());

		eSFlowBean.setOtherAttachments(test);
		Assert.assertEquals(test, eSFlowBean.getOtherAttachments());
		
		eSFlowBean.setDesignSection(test);
		Assert.assertEquals(test, eSFlowBean.getDesignSection());
	}
	
}
