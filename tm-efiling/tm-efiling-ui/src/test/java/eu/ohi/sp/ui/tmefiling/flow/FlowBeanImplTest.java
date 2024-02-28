package eu.ohi.sp.ui.tmefiling.flow;

import org.apache.pdfbox.util.operator.Invoke;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.TreeSet;

import static org.junit.Assert.*;

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.form.claim.SeniorityForm;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.form.MainForm;

public class FlowBeanImplTest {

	
	
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(expected = CloneNotSupportedException.class)
	public void cloneTest() throws CloneNotSupportedException{
		FlowBeanImpl bean = spy(new FlowBeanImpl());
		
		bean.clone();
	}
	
	@Test
	public void clearTest(){
		FlowBeanImpl bean = spy(new FlowBeanImpl());
		
		bean.setTerms(null);
		bean.setTerm("term");
		MainForm oldMainForm = bean.getMainForm();
		bean.setPaymentForm(null);
		bean.setFeesForm(null);
		bean.setFirstLang("firstlang");
		bean.setSecLang("seclang");
		bean.setId("id");
		bean.setIdreserventity("reserveentity");
		bean.setOtherAttachments(null);
		List<PriorityForm> priorities = bean.getPriorities();
		List<ApplicationCAForm> correspondenceAddress = bean.getMainForm().getCorrespondanceAddresses();
		correspondenceAddress.add(new ApplicationCAForm());
		bean.getSeniorities().add(new SeniorityForm());
		List<SeniorityForm> seniority = bean.getSeniorities();
		seniority.add(new SeniorityForm());
		bean.setSimilarMarks(null);
		bean.setWarningValidated(true);
		bean.setUseReference(true);
		bean.setReference("asdfsad");
		bean.setInitializationErrorCode("qwerqwer");
		bean.setComment("asdfasdfA");
		
		
		bean.clear();
		
		assertNotNull(bean.getTerms());
		assertTrue(bean.getTerms().isEmpty());
		assertNull(bean.getTerm());
		assertNotNull(bean.getMainForm());
		assertNotEquals(oldMainForm, bean.getMainForm());
		assertNotNull(bean.getPriorities());
		assertTrue(priorities != bean.getPriorities());
		assertNotNull(bean.getPaymentForm());
		assertNotNull(bean.getFeesForm());
		assertNotNull(bean.getGoodsAndServices());
		assertNotNull(bean.getOtherAttachments());
		assertTrue(bean.getGoodsAndServices() instanceof TreeSet<?>);
		assertNull(bean.getFirstLang());
		assertNull(bean.getSecLang());
		assertNull(bean.getIdreserventity());
		assertNull(bean.getId());
		assertNull(bean.getFilingNumber());
		assertNotNull(bean.getMainForm().getCorrespondanceAddresses());
		assertTrue(correspondenceAddress != bean.getCorrespondanceAddresses());
		assertNotNull(bean.getSeniorities());
		assertTrue(bean.getSeniorities().isEmpty());
		assertNotEquals(seniority, bean.getSeniorities());
		assertNotNull(bean.getSimilarMarks());
		assertTrue(bean.getSimilarMarks().isEmpty());
		assertEquals(0, bean.getNumberofClasses());
		
		assertNotNull(bean.getExhpriorities());
		assertTrue(bean.getExhpriorities().isEmpty());
		
		assertNotNull(bean.getTransformations());
		assertTrue(bean.getTransformations().isEmpty());
		
		assertNotNull(bean.getConversions());
		assertTrue(bean.getConversions().isEmpty());
		
		assertNull(bean.getWarningValidated());
		assertNull(bean.getUseReference());
		
		assertNull(bean.getReference());
		assertNull(bean.getInitializationErrorCode());
		
		assertNull(bean.getComment());
		assertNull(bean.getAddedAssignees());
		assertNull(bean.getUserDataAssignees());
		assertNull(bean.getAssignees());
		assertNotNull(bean.getPersons());
		
		verify(bean).clearPriorities();
		verify(bean).clearSeniorities();
		verify(bean).clearExhibitions();
		verify(bean).clearTransformations();
		verify(bean).clearConversions();
		verify(bean).clearApplicants();
		verify(bean).clearRepresentatives();
		
		
		
		
		
	}
}
