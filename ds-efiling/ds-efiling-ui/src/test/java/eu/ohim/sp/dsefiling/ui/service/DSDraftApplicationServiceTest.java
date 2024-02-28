package eu.ohim.sp.dsefiling.ui.service;

import eu.ohim.sp.common.ui.form.application.SubmittedForm;
import eu.ohim.sp.common.ui.form.application.SubmittedMap;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.validation.ErrorCore;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.dsefiling.TestHelper;
import eu.ohim.sp.dsefiling.ui.adapter.DSFlowBeanFactory;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author ionitdi
 */
public class DSDraftApplicationServiceTest {
	private static final String OFFICE = "office";
	private static final String MODULE = "dsefiling";
	private static final String APPLICATION_TYPE = "DS";

	DSFlowBeanImpl flowBean;

    @Mock
    private ApplicationService applicationService;

    @Mock
    private SectionViewConfiguration sectionViewConfiguration;

    @Mock
    private DSFlowBeanFactory flowBeanFactory;

    @Mock
    private SubmittedMap submittedMap;

    @InjectMocks
    DSDraftApplicationService draftService = new DSDraftApplicationService();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        flowBean = new DSFlowBeanImpl();
		ReflectionTestUtils.setField(draftService, "office", OFFICE);
    }

    @Test
    public void provideProvisionalIDTest()
    {
        DraftApplication draft = new DraftApplication();
        draft.setProvisionalId("some id");
        when(applicationService.initDraftApplication(eq(OFFICE), eq(MODULE), eq(APPLICATION_TYPE))).thenReturn(draft);

        String id = draftService.provideProvisionalID();

        assertEquals("some id", id);
    }

    @Test
    public void saveApplicationLocallyTest()
    {
        DesignApplication app = new DesignApplication();
        app.setApplicationFilingNumber("filingNo");
        when(flowBeanFactory.convertTo(flowBean)).thenReturn(app);
        byte[] byteArray = new byte[10];
        when(applicationService.exportApplication(eq(OFFICE), eq(MODULE), eq(FormatXML.APPLICATION_XML),
                                                  eq(app.getApplicationFilingNumber()))).thenReturn(byteArray);

        byte[] result = draftService.saveApplicationLocally(flowBean);

        verify(applicationService, times(1)).persistApplication(eq(OFFICE), eq(MODULE), eq(FormatXML.APPLICATION_XML),
                                                                eq(app));
        assertEquals(byteArray, result);
    }

    @Test
    public void storeSubmittedApplicationTest()
    {
        flowBean.setIdreserventity("some id");
        
        DesignApplication designApplication = new DesignApplication();
        designApplication.setDocuments(Collections.EMPTY_LIST);
        
        Mockito.when(
				flowBeanFactory.convertTo(flowBean)).thenReturn(designApplication);
        Mockito.when(
        		applicationService.fileApplication(Mockito.anyString(), Mockito.anyString(),
        				Mockito.any(IPApplication.class))).thenReturn(designApplication);

        String result = draftService.storeSubmittedApplication(flowBean);

        verify(submittedMap, times(1)).put(any(String.class), any(SubmittedForm.class));
    }

    @Test
    public void loadApplicationLocallyTest1()
    {
        DesignApplication app = new DesignApplication();
        app.setApplicationFilingNumber("filingNo");

        byte[] byteArray = new byte[10];

        when(applicationService.importApplication(eq(OFFICE), eq(MODULE), eq(byteArray), eq(APPLICATION_TYPE),
                                                  eq(FormatXML.APPLICATION_XML))).thenReturn(app);

        String result = draftService.loadApplicationLocally(byteArray);

        assertEquals("filingNo", result);
    }

    @Test
    public void loadApplicationLocallyTest2()
    {
        DesignApplication app = new DesignApplication();
        app.setApplicationFilingNumber("filingNo");

        String data = "some data";

        when(applicationService.retrieveApplication(eq(OFFICE), eq(MODULE), eq(FormatXML.APPLICATION_XML),
                                                    eq(data))).thenReturn(app);

        flowBean.setIdreserventity("some id");

        when(flowBeanFactory.convertFrom(eq(app))).thenReturn(flowBean);

        DSFlowBean result = draftService.loadApplicationLocally(data);

        assertEquals("some id", result.getIdreserventity());
    }

    @Test
    public void fileApplicationTest()
    {
        DesignApplication app = new DesignApplication();

        DesignApplication filedApp = new DesignApplication();
        filedApp.setDocuments(new ArrayList<AttachedDocument>());
        filedApp.getDocuments().add(new AttachedDocument());
        filedApp.getDocuments().get(0).setDocument(new Document());
        filedApp.getDocuments().get(0).getDocument().setDocumentId("");

        when(flowBeanFactory.convertTo(flowBean)).thenReturn(app);
        when(applicationService.fileApplication(any(), any(), any())).thenReturn(filedApp);

        String result = draftService.fileApplication(flowBean);

        assertEquals("", result);
    }

    @Test
    public void validateApplicationTest1()
    {
        DesignApplication app = new DesignApplication();


        RulesInformation rules = new RulesInformation();
        rules.setCustomObjects(new HashMap<String, Object>());

        ErrorList errors = new ErrorList();
        errors.addError(new ErrorCore());
        errors.getErrorList().get(0).setId(12);

        Map<String, Sections> map = new HashMap<String, Sections>();
        map.put("flow", new Sections());
        when(sectionViewConfiguration.getViewConfiguration()).thenReturn(map);

        when(flowBeanFactory.convertTo(flowBean)).thenReturn(app);
        when(applicationService.validateApplication(eq(MODULE), eq(app), any(RulesInformation.class))).thenReturn(errors);


        ErrorList result = draftService.validateApplication(flowBean, rules, "flow");

        assertEquals(12, result.getErrorList().get(0).getId().intValue());
    }

    @Test
    public void validateApplicationTest2()
    {
        DesignApplication app = new DesignApplication();


        RulesInformation rules = new RulesInformation();
        rules.setCustomObjects(new HashMap<String, Object>());

        ErrorList errors = new ErrorList();
        errors.addError(new ErrorCore());
        errors.getErrorList().get(0).setId(12);

        Answer<List<Sections>> sectionsList = TestHelper.setupDummyListAnswer(new Sections());
        when(sectionViewConfiguration.getSortedSections("flow", "state")).thenAnswer(sectionsList);

        when(flowBeanFactory.convertTo(flowBean)).thenReturn(app);
        when(applicationService.validateApplication(eq(MODULE), eq(app), any(RulesInformation.class))).thenReturn(errors);


        ErrorList result = draftService.validateApplication(flowBean, rules, "flow", "state");

        assertEquals(12, result.getErrorList().get(0).getId().intValue());
    }
}
