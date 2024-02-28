package eu.ohim.sp.ui.tmefiling.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.test.ui.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.form.application.SubmittedMap;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;

public class DraftApplicationServiceTest {

	@Mock
	private SubmittedMap submittedMap;

	@Mock
	private ApplicationService applicationServiceInterface;

	@Mock
	private FlowBeanFactory flowBeanFactory;

	@Mock
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

	@Mock
	private SectionViewConfiguration sectionViewConfiguration;

	@Mock
	private FlowScopeDetails flowScopeDetails;

	@InjectMocks
	private DraftApplicationService draftApplicationService;

	private static final String FILING_NUMBER = "11111";
	private static final String DRAFT_ID = "55555";

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		final byte[] TESTDOC = new byte[] { (byte)0xe0, 0x4f, (byte)0xd0,
				0x20, (byte)0xea, 0x3a, 0x69, 0x10, (byte)0xa2, (byte)0xd8, 0x08, 0x00, 0x2b,
				0x30, 0x30, (byte)0x9d };
		TradeMarkApplication application = TestUtil.CoreTMGenerator.fillTMApplication();
		when(flowBeanFactory.convertFromTradeMarkApplication(any())).thenReturn(new FlowBeanImpl());
		when(flowBeanFactory.convertToTradeMarkApplication(any())).thenReturn(application);
		when(applicationServiceInterface.persistApplication(any(), any(), any(), any())).thenReturn(new Document());
		when(applicationServiceInterface.exportApplication(any(), any(), any(), any())).thenReturn(TESTDOC);
		when(applicationServiceInterface.importApplication(any(), any(), any(), any(), any())).thenReturn(application);
		when(applicationServiceInterface.retrieveApplication(any(), any(), any(), any())).thenReturn(application);
		when(applicationServiceInterface.validateApplication(any(), any(), any())).thenReturn(new ErrorList());
		when(applicationServiceInterface.fileApplication(any(), any(), any())).thenReturn(application);
	}

	@Test
	public void testProvideProvisionalID() {
		DraftApplication draftApplicationMock = getDraftApplicationMock();
		Mockito.when(applicationServiceInterface.initDraftApplication(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(draftApplicationMock);

		assertEquals(draftApplicationService.provideProvisionalID(), "1234");
	}

	@Test
	public void testProvidePaymentIDRetrievePaymentNumberTrue() {
		Mockito.when(configurationServiceDelegator.isServiceEnabled(Mockito.anyString())).thenReturn(Boolean.TRUE);
		Mockito.when(applicationServiceInterface.getDraftApplication(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(getDraftApplicationMock());

		assertEquals(draftApplicationService.providePaymentID(FILING_NUMBER), FILING_NUMBER);
	}

	@Test
	public void testProvidePaymentIDRetrievePaymentNumberFalse() {
		Mockito.when(configurationServiceDelegator.isServiceEnabled(Mockito.anyString())).thenReturn(Boolean.FALSE);

		assertEquals(draftApplicationService.providePaymentID(FILING_NUMBER), null);
	}

	@Test
	public void testSaveApplicationLocally() {
		FlowBeanImpl flowBean = new FlowBeanImpl();
		flowBean.setIdreserventity("some");
		assertTrue(draftApplicationService.saveApplicationLocally(flowBean) != null);
	}

	@Test
	public void testLoadApplicationLocallyReturnsString() {
		byte[] data = FILING_NUMBER.getBytes();
		assertTrue(draftApplicationService.loadApplicationLocally(data) != null);
	}

	@Test
	public void testLoadApplicationLocallyReturnsTradeMarkFlowBean() {
		assertTrue(draftApplicationService.loadApplicationLocally(FILING_NUMBER) != null);
	}

	@Test
	public void testLoadApplication() {
		assertTrue(draftApplicationService.loadApplication(DRAFT_ID, true) != null);
	}

	@Test
	public void testSaveApplication() {
		draftApplicationService.saveApplication(null, true);
	}

	@Test
	public void testStoreSubmittedApplication() {
		FlowBeanImpl flowBean = new FlowBeanImpl();
		flowBean.setIdreserventity("some");
		assertTrue(draftApplicationService.storeSubmittedApplication(flowBean) != null);
	}

	@Test
	public void testFileApplication() {
		assertTrue(draftApplicationService.fileApplication(null) != null);
	}

	@Test
	public void testValidateApplication() {
		assertTrue(draftApplicationService.validateApplication(null, new RulesInformation(), null) != null);
	}

	@Test
	public void testValidateApplication2() {
		assertTrue(draftApplicationService.validateApplication(null, new RulesInformation(), null, null) != null);
	}

	private DraftApplication getDraftApplicationMock() {
		DraftApplication draftApplicationMock = new DraftApplication();
		draftApplicationMock.setProvisionalId("1234");
		return draftApplicationMock;
	}
}
