package eu.ohim.sp.eservices.ui.serviceTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.ohim.sp.eservices.ui.util.GroundsUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.security.authorisation.domain.SPUser;
import eu.ohim.sp.common.ui.form.application.SubmittedMap;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.EServiceApplication;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.DocumentKindDefault;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.report.ReportService;
import eu.ohim.sp.eservices.ui.adapter.ESFlowBeanFactory;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.ESDraftApplicationService;
import eu.ohim.sp.eservices.ui.service.FormUtil;

public class ESDraftApplicationServiceTest {

    @Mock
    TMeServiceApplication filedApplication;

    @Mock
    private SubmittedMap submittedMap;

    @Mock
    private ApplicationService applicationServiceInterface;

    @Mock
    private ReportService reportService;

    @Mock
    private FileServiceInterface fileService;

    @Mock
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Mock
    private ESFlowBeanFactory flowBeanFactory;

    @Mock
    private SectionViewConfiguration sectionViewConfiguration;

    @Mock
    private FormUtil formUtil;

    @Mock
    private FlowScopeDetails flowScopeDetails;

    @Mock
    private GroundsUtil groundsUtil;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    ESDraftApplicationService eSDraftApplicationService = new ESDraftApplicationService();

    @Test
    public void provideProvisionalID() {
        DraftApplication draftApplication = Mockito.mock(DraftApplication.class);
        Mockito.when(draftApplication.getProvisionalId()).thenReturn("testId");
        Mockito.when(formUtil.getType()).thenReturn("test");
        Mockito.when(
                applicationServiceInterface.initDraftApplication(Matchers.anyString(), Matchers.anyString(),
                        Matchers.anyString())).thenReturn(draftApplication);
        String ret = eSDraftApplicationService.provideProvisionalID();
        Assert.assertEquals("testId", ret);
    }

    @Test
    public void providePaymentID1() {
        Mockito.when(configurationServiceDelegator.isServiceEnabled(Matchers.anyString())).thenReturn(false);
        Assert.assertNull(eSDraftApplicationService.providePaymentID(Matchers.anyString()));
    }

    @Test
    public void providePaymentID2() {
        Mockito.when(configurationServiceDelegator.isServiceEnabled(Matchers.anyString())).thenReturn(true);
        DraftApplication draftApplication = Mockito.mock(DraftApplication.class);
        Mockito.when(
                applicationServiceInterface.getDraftApplication(Matchers.anyString(), Matchers.anyString(),
                        Matchers.anyString())).thenReturn(draftApplication);
        Assert.assertNotNull(eSDraftApplicationService.providePaymentID(Matchers.anyString()));
    }

    @Test
    public void saveApplicationLocally() {
        ESFlowBean flowBean = Mockito.mock(ESFlowBean.class);
        EServiceApplication eServiceApplication = Mockito.mock(EServiceApplication.class);
        Mockito.when(eServiceApplication.getApplicationFilingNumber()).thenReturn("test");
        Mockito.when(flowBeanFactory.convertTo(flowBean)).thenReturn(eServiceApplication);
        Mockito.when(
                applicationServiceInterface.exportApplication(Matchers.anyString(), Matchers.anyString(),
                        Matchers.any(FormatXML.class), Matchers.anyString())).thenReturn(new byte[5]);
        Assert.assertNotNull(eSDraftApplicationService.saveApplicationLocally(flowBean));
    }

    @Test
    public void StringLoadApplicationLocally() {
        EServiceApplication eServiceApplication = Mockito.mock(EServiceApplication.class);
        Mockito.when(eServiceApplication.getApplicationFilingNumber()).thenReturn("testId");
        Mockito.when(
                applicationServiceInterface.importApplication(Matchers.anyString(), Matchers.anyString(),
                        Matchers.any(byte[].class), Matchers.anyString(), Matchers.any(FormatXML.class))).thenReturn(
                eServiceApplication);
        Assert.assertEquals("testId", eSDraftApplicationService.loadApplicationLocally(new byte[2]));
    }

    @Test
    public void ESFlowBeanLoadApplicationLocally() {
        EServiceApplication eServiceApplication = Mockito.mock(EServiceApplication.class);
        Mockito.when(eServiceApplication.getApplicationType()).thenReturn("type");
        Mockito.when(
                (EServiceApplication) applicationServiceInterface.retrieveApplication(Matchers.anyString(),
                        Matchers.anyString(), Matchers.any(FormatXML.class), Matchers.anyString())).thenReturn(
                eServiceApplication);
        ESFlowBean eSFlowBean = Mockito.mock(ESFlowBean.class);
        List<TMDetailsForm> tmlist = new ArrayList<TMDetailsForm>();
        Mockito.when(eSFlowBean.getTmsList()).thenReturn(tmlist);
        Mockito.when(flowBeanFactory.convertFrom(Matchers.any(EServiceApplication.class))).thenReturn(eSFlowBean);
        Mockito.when(flowScopeDetails.getFlowModeId()).thenReturn("tm-invalidity");
        Mockito.when(formUtil.getType()).thenReturn("type");
        Assert.assertNotNull(eSDraftApplicationService.loadApplicationLocally("test"));
        Mockito.when(flowScopeDetails.getFlowModeId()).thenReturn("tm-opposition");
        Assert.assertNotNull(eSDraftApplicationService.loadApplicationLocally("test"));
        Mockito.when(flowScopeDetails.getFlowModeId()).thenReturn("tm-revocation");
        Assert.assertNotNull(eSDraftApplicationService.loadApplicationLocally("test"));
        Mockito.when(flowScopeDetails.getFlowModeId()).thenReturn("");
        Assert.assertNotNull(eSDraftApplicationService.loadApplicationLocally("test"));

        Mockito.when(flowScopeDetails.getFlowModeId()).thenReturn("tm-revocation");

        tmlist.add(null);
        tmlist.add(null);
        Assert.assertNotNull(eSDraftApplicationService.loadApplicationLocally("test"));
        Mockito.when(eSFlowBean.getTmsList()).thenReturn(null);
        Assert.assertNotNull(eSDraftApplicationService.loadApplicationLocally("test"));

    }

    @Test(expected = SPException.class)
    public void ESFlowBeanLoadApplicationLocally2() {
        EServiceApplication eServiceApplication = Mockito.mock(EServiceApplication.class);
        Mockito.when(eServiceApplication.getApplicationType()).thenReturn("type");
        Mockito.when(
                (EServiceApplication) applicationServiceInterface.retrieveApplication(Matchers.anyString(),
                        Matchers.anyString(), Matchers.any(FormatXML.class), Matchers.anyString())).thenReturn(
                eServiceApplication);
        ESFlowBean eSFlowBean = Mockito.mock(ESFlowBean.class);
        List<TMDetailsForm> tmlist = new ArrayList<TMDetailsForm>();

        Mockito.when(eSFlowBean.getTmsList()).thenReturn(tmlist);
        Mockito.when(flowBeanFactory.convertFrom(Matchers.any(EServiceApplication.class))).thenReturn(eSFlowBean);
        Mockito.when(flowScopeDetails.getFlowModeId()).thenReturn("tm-invalidity");
        Mockito.when(formUtil.getType()).thenReturn("type2");
        eSDraftApplicationService.loadApplicationLocally("test");

    }

    @Test
    public void storeSubmittedApplication() throws IOException {

        ESFlowBean flowBean = Mockito.mock(ESFlowBean.class);

        TMeServiceApplication meServiceApplication = new TMeServiceApplication();

        meServiceApplication.setApplicationLanguage("applicationLanguage");

        meServiceApplication.setApplicationType("ES");

        meServiceApplication.setApplicationFilingNumber("applicationFilingNumber");

        meServiceApplication.setDocuments(new ArrayList<AttachedDocument>());

        meServiceApplication.setReceivingOffice("SK");

        List<AttachedDocument> list = new ArrayList<AttachedDocument>();

        AttachedDocument attachedDocument = new AttachedDocument();

        attachedDocument.setDocumentKind("documentKind");

        Document document = new Document();

        document.setComment("comment");

        document.setDocumentId("documentId");

        attachedDocument.setDocument(document);

        list.add(attachedDocument);

        meServiceApplication.setDocuments(list);

        Mockito.when(flowBeanFactory.convertTo(flowBean)).thenReturn(meServiceApplication);

        Mockito.when(formUtil.getModule()).thenReturn("test");

        Mockito.when(flowBean.isReadOnly()).thenReturn(true);

        Mockito.when(
                applicationServiceInterface.fileApplication(null, "eu.ohim.sp.core.rules.test", meServiceApplication))
                .thenReturn(meServiceApplication);

        Mockito.when(
                applicationServiceInterface.generateReceipts(meServiceApplication.getReceivingOffice(),
                        "eu.ohim.sp.core.rules.test", meServiceApplication)).thenReturn(meServiceApplication);

        Mockito.when(applicationServiceInterface.getDraftApplication(null, "eu.ohim.sp.core.rules.test", null))
                .thenReturn(new DraftApplication());

        Mockito.when(
                fileService.addFile(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
                        Matchers.any(byte[].class), Matchers.anyBoolean())).thenReturn(new StoredFile());

        // Mockito.when(formUtil.getModule()).thenReturn("");
        // ESFlowBean eSFlowBean=Mockito.mock(ESFlowBean.class);

        // String iPApplication=Mockito.mock(IPApplication.class);

        // Mockito.when(//).thenReturn("");

        Mockito.when(configurationServiceDelegator.isServiceEnabled(AvailableServices.SUBMIT_PORTAL.value()))
                .thenReturn(true);

        Mockito.when(flowBean.isReadOnly()).thenReturn(false);

        byte[] data = new byte[9];

        Mockito.when(
                reportService.generateReport("eu.ohim.sp.core.rules.", "receipt", "applicationLanguage",
                        meServiceApplication, null, Boolean.FALSE.toString(), null)).thenReturn(data);

        StoredFile storedFile = new StoredFile();
        storedFile.setDescription("description");
        storedFile.setDocumentId("documentId");

        Mockito.when(
                fileService.addFile(meServiceApplication.getApplicationFilingNumber(), "receipt" + ".pdf",
                        "application/pdf", data, false)).thenReturn(storedFile);

        // Mockito.when(fileService.addFile(meServiceApplication.getApplicationFilingNumber(),
        // "receipt" + ".pdf", "application/pdf", data,
        // false).getDocumentId()).thenReturn("documentId");
        //

        Authentication authentication = Mockito.mock(Authentication.class);
        SPUser principal = Mockito.mock(SPUser.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(principal.getUsername()).thenReturn("userName");

        Result result = Mockito.mock(Result.class);
        Mockito.when(result.getResult()).thenReturn(Result.SUCCESS);
        Mockito.when(
                applicationServiceInterface.saveApplication(Matchers.anyString(), Matchers.anyString(),
                        Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyBoolean()))
                .thenReturn(result);

        eSDraftApplicationService.storeSubmittedApplication(flowBean);
    }

    @Test
    public void fileApplication() {

        ESFlowBean flowBean = Mockito.mock(ESFlowBean.class);

        TMeServiceApplication meServiceApplication = new TMeServiceApplication();

        meServiceApplication.setApplicationLanguage("applicationLanguage");

        meServiceApplication.setApplicationType("ES");

        meServiceApplication.setApplicationFilingNumber("applicationFilingNumber");

        List<AttachedDocument> list = new ArrayList<AttachedDocument>();

        AttachedDocument attachedDocument = new AttachedDocument();

        attachedDocument.setDocumentKind("documentKind");

        Document document = new Document();

        document.setComment("comment");

        document.setDocumentId("documentId");

        attachedDocument.setDocument(document);

        list.add(attachedDocument);

        meServiceApplication.setDocuments(list);

        Mockito.when(flowBeanFactory.convertTo(flowBean)).thenReturn(meServiceApplication);

        Mockito.when(formUtil.getModule()).thenReturn("test");

        Mockito.when(flowBean.isReadOnly()).thenReturn(true);

        Mockito.when(applicationServiceInterface.fillApplicationDocuments(meServiceApplication)).thenReturn(
                meServiceApplication);

        Mockito.when(
                applicationServiceInterface.fileApplication(null, "eu.ohim.sp.core.rules.test", meServiceApplication))
                .thenReturn(meServiceApplication);

        // Mockito.when(formUtil.getModule()).thenReturn("");
        // ESFlowBean eSFlowBean=Mockito.mock(ESFlowBean.class);

        // String iPApplication=Mockito.mock(IPApplication.class);

        // Mockito.when(//).thenReturn("");

        Mockito.when(configurationServiceDelegator.isServiceEnabled(AvailableServices.SUBMIT_PORTAL.value()))
                .thenReturn(true);

        Mockito.when(flowBean.isReadOnly()).thenReturn(false);

        byte[] data = new byte[9];

        Mockito.when(
                reportService.generateReport("eu.ohim.sp.core.rules.", "receipt", "applicationLanguage",
                        meServiceApplication, null, Boolean.FALSE.toString(), null)).thenReturn(data);

        StoredFile storedFile = new StoredFile();
        storedFile.setDescription("description");
        storedFile.setDocumentId("documentId");

        // Mockito.when(fileService.addFile(meServiceApplication.getApplicationFilingNumber(),
        // "receipt" + ".pdf", "application/pdf", data,
        // false)).thenReturn(storedFile);
        //
        //

        // Mockito.when(fileService.addFile(meServiceApplication.getApplicationFilingNumber(),
        // "receipt" + ".pdf", "application/pdf", data,
        // false).getDocumentId()).thenReturn("documentId");
        //

        eSDraftApplicationService.fileApplication(flowBean);
        attachedDocument.setDocumentKind(DocumentKindDefault.APPLICATION_RECEIPT);
        eSDraftApplicationService.fileApplication(flowBean);

    }

    @Test
    public void validateApplication() {

        ESFlowBean flowBean = Mockito.mock(ESFlowBean.class);

        TMeServiceApplication meServiceApplication = new TMeServiceApplication();

        meServiceApplication.setApplicationLanguage("applicationLanguage");

        meServiceApplication.setApplicationType("ES");

        meServiceApplication.setApplicationFilingNumber("applicationFilingNumber");

        Mockito.when(flowBeanFactory.convertTo(flowBean)).thenReturn(meServiceApplication);

        String flowModeId = "56";
        Map<String, Sections> value = new HashMap<String, Sections>();

        value.put(flowModeId, new Sections());

        Mockito.when(sectionViewConfiguration.getViewConfiguration()).thenReturn(value);

        RulesInformation rulesInformation = new RulesInformation();

        rulesInformation.setId(1);

        eSDraftApplicationService.validateApplication(flowBean, rulesInformation, flowModeId);

    }

}
