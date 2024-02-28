/*
 * ApplicationServiceBean:: ApplicationServiceTest 07/11/13 11:58 karalch $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */

package eu.ohim.sp.core.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.ohim.sp.common.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.application.dao.ApplicationDAO;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.application.wrapper.ApplicationNumber;
import eu.ohim.sp.core.domain.claim.Exhibition;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.claim.InternationalTradeMarkCode;
import eu.ohim.sp.core.domain.claim.Seniority;
import eu.ohim.sp.core.domain.claim.SeniorityKind;
import eu.ohim.sp.core.domain.claim.TransformationPriority;
import eu.ohim.sp.core.domain.claim.trademark.Priority;
import eu.ohim.sp.core.domain.common.Text;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.PersonRoleKind;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Colour;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.DocumentKind;
import eu.ohim.sp.core.domain.resources.DocumentKindDefault;
import eu.ohim.sp.core.domain.resources.FODocument;
import eu.ohim.sp.core.domain.resources.FileFormat;
import eu.ohim.sp.core.domain.resources.Image;
import eu.ohim.sp.core.domain.resources.Sound;
import eu.ohim.sp.core.domain.resources.SoundFileFormat;
import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.core.domain.trademark.ClassificationTerm;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.MarkDescription;
import eu.ohim.sp.core.domain.trademark.MarkDisclaimer;
import eu.ohim.sp.core.domain.trademark.MarkFeature;
import eu.ohim.sp.core.domain.trademark.SoundSpecification;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.core.domain.trademark.WordSpecification;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.report.ReportService;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.external.application.ApplicationClient;
import eu.ohim.sp.external.application.TransactionUtil;
import eu.ohim.sp.filing.domain.utils.MapperFactory;

@SuppressWarnings("deprecation")
public class ApplicationServiceTest {

    public static final String OHIM_OFFICE = "EM";
    public static final String MODULE = "efiling";
    @Mock
    ApplicationDAO applicationDAO;

    @Mock
    DocumentService documentServiceMock;

    @Mock
    RulesService businessRulesServiceMock;

    @Mock
    ReportService reportService;

    @Mock
    ApplicationClient applicationAdapterServiceInterface;

    @Mock
    private ConfigurationService systemConfigurationService;

    @InjectMocks
    TMApplicationServiceBean applicationService;

    @InjectMocks
    EServiceApplicationServiceBean eServiceApplicationService;

    @Mock
    TransactionUtil transactionUtil;

    String backApplicantXML;
    String mimeTypeTxt;
    String containerXml;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

//		backApplicantXML = readFileAsString("/backApplicant.xml", true);
		backApplicantXML = FileUtil.readFileAsString("/backApplicant.xml", true);
        mimeTypeTxt = FileUtil.readFileAsString("/files/mimetype.txt", true);
        containerXml = FileUtil.readFileAsString("/files/container.xml", true);
    }

    private String readFileAsString(String path, boolean b) {
        String text = null;
        try {
            text = FileUtils.readFileToString(new File("src/test/resources/" + path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public void fillDataRetrievalApplication(String office, String module, FormatXML format, String filingNumber,
            String typeApplication) {
        Map<String, String> customProperties = new HashMap<String, String>();
        customProperties.put(FODocument.ATTACHMENT_TYPE, format.value());
        customProperties.put(FODocument.OFFICE, office);
        customProperties.put(FODocument.MODULE, module);
        customProperties.put(FODocument.FILING_NUMBER, filingNumber);
        customProperties.put(FODocument.APPLICATION_STATUS, "drafts");
        customProperties.put(FODocument.APPLICATION_TYPE, typeApplication);

        List<Document> documentList = new ArrayList<Document>();
        Document document = new Document();
        document.setComment("some comment");
        document.setCustomProperties(customProperties);
        document.setDateCreated(new Date());
        document.setDocumentId("1234");
        document.setData(backApplicantXML.getBytes());

        documentList.add(document);

        when(documentServiceMock.searchDocument(anyMap(), anyBoolean())).thenReturn(documentList);
        when(documentServiceMock.getContent("1234")).thenReturn(document);

        DraftApplication draftApplication = new DraftApplication();
        draftApplication.setTyApplication(new TypeApplication());
        draftApplication.getTyApplication().setTypeApplication(typeApplication);
        when(applicationService.getApplicationDAO().findDraftApplicationByProvisionalId(filingNumber)).thenReturn(
                draftApplication);

        TradeMarkApplication application = new TradeMarkApplication();
        application.setApplicationFilingDate(new Date());
        application.setApplicationFilingNumber("some filing number");
        application.setTradeMark(fillCoreTradeMark(TradeMark.class));

        when(transactionUtil.generateIPApplication(any(byte[].class), eq(typeApplication), eq(true))).thenReturn(
                application);

    }

    /**
     * Test mainly to check that actually only files that should be deleted, they were deleted
     */
    @Test
    public void test_deleteNonReferenceFiles() {

        Document document = new Document();
        document.setComment("comment");
        document.setFileName("filename priorities");
        document.setFileFormat(FileFormat.PDF.value());
        document.setName("name");
        document.setLanguage("en");
        document.setDocumentId("0000000000010");

        AttachedDocument attachedDocument = new AttachedDocument();
        attachedDocument.setDocument(document);
        attachedDocument.setDocumentKind(DocumentKind.APPLICATION_RECEIPT.value());

        TradeMarkApplication tradeMarkApplication = new TradeMarkApplication();
        tradeMarkApplication.setTradeMark(fillCoreTradeMark(TradeMark.class));
        tradeMarkApplication.setDocuments(new ArrayList<AttachedDocument>());
        tradeMarkApplication.getDocuments().add(attachedDocument);

        List<Document> documents = new ArrayList<Document>();
        Document documentSaved = new Document();
        documentSaved.setDocumentId("0000000000001");
        documents.add(documentSaved);
        documentSaved = new Document();
        documentSaved.setDocumentId("000000000000177");
        documents.add(documentSaved);
        documentSaved = new Document();
        documentSaved.setDocumentId("000000000000179");
        documents.add(documentSaved);
        documentSaved = new Document();
        documentSaved.setDocumentId("0000000000002");
        documents.add(documentSaved);
        documentSaved = new Document();
        documentSaved.setDocumentId("sound id");
        documents.add(documentSaved);
        documentSaved = new Document();
        documentSaved.setDocumentId("0000000000010");
        documents.add(documentSaved);

        when(documentServiceMock.searchDocument(anyMap(), eq(true))).thenReturn(documents);

        List<Document> epub = new ArrayList<Document>();
        epub.add(new Document());
        epub.get(0).setDocumentId("epub-id");

        when(documentServiceMock.searchDocument(anyMap(), eq(false))).thenReturn(epub);
        when(documentServiceMock.getContent(eq("epub-id"))).thenReturn(epub.get(0));

        when(transactionUtil.generateByte(eq(tradeMarkApplication), anyString(), eq(true))).thenReturn(
                new String("epub").getBytes());

        applicationService.persistApplication(OHIM_OFFICE, MODULE, FormatXML.APPLICATION_EPUB, tradeMarkApplication);

        // verify that the transformation is called
        verify(transactionUtil, times(1)).generateByte(eq(tradeMarkApplication), anyString(), eq(true));

        // verify that we search for saved files and then we will delete them
        verify(documentServiceMock, times(1)).searchDocument(anyMap(), eq(true));
        verify(documentServiceMock, times(2)).deleteDocument(any(Document.class));
        //
        verify(documentServiceMock, times(1)).searchDocument(anyMap(), eq(false));
        verify(documentServiceMock, times(1)).getContent(eq("epub-id"));

        verify(documentServiceMock, times(1)).updateDocument(any(Document.class));

    }

    @Test
    public void test_retrieveApplication_Ok() {

        String office = OHIM_OFFICE;
        String module = MODULE;
        FormatXML format = FormatXML.APPLICATION_XML;
        String filingNumber = "some filing number";
        String typeApplication = "TradeMark";

        fillDataRetrievalApplication(office, module, format, filingNumber, typeApplication);

        TradeMarkApplication core = (TradeMarkApplication) applicationService.retrieveApplication(office, module,
                format, filingNumber);

        verify(applicationDAO, times(1)).findDraftApplicationByProvisionalId(filingNumber);
        verify(documentServiceMock, times(1)).searchDocument(anyMap(), eq(false));
        verify(transactionUtil, times(1)).generateIPApplication(any(byte[].class), eq(typeApplication), eq(true));

        // Check the values in backApplicant.xml
        assertEquals(core.getTradeMark().getApplicationLanguage(), "en");
        assertEquals(core.getTradeMark().getSecondLanguage(), "el");
        assertEquals(core.getTradeMark().getApplicants().get(0).getName().getFirstName(), "Christos");
        assertEquals(core.getTradeMark().getRepresentatives().get(0).getEmails().get(0).getEmailAddress(),
                "test@oami.gr");
        assertEquals(core.getTradeMark().getSeniorities().get(0).getKind(), SeniorityKind.INTERNATIONAL_TRADE_MARK);
        assertEquals(core.getTradeMark().getPriorities().get(0).getFilingNumber(), "111324234");
        assertEquals(core.getTradeMark().getClassDescriptions().get(0).getClassNumber(), "32");
    }

    @Test
    public void testInitApplication() {
        String module = "tmefiling";
        String provisionalId = "00000001";

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("provisionalId", provisionalId);
        when(businessRulesServiceMock.calculate(eq(module), eq("Application_Identifier_List"), anyList())).thenReturn(
                result);
        when(applicationDAO.updateStatus(any(eu.ohim.sp.core.domain.application.DraftApplication.class))).thenReturn(
				new DraftApplication());

        eu.ohim.sp.core.domain.application.DraftApplication draftApplicationResult = new eu.ohim.sp.core.domain.application.DraftApplication();
        draftApplicationResult.setProvisionalId(provisionalId);
        when(applicationDAO.getDraftApplication(any(DraftApplication.class))).thenReturn(draftApplicationResult);

        eu.ohim.sp.core.domain.application.DraftApplication draftApplication = applicationService.initDraftApplication(
                OHIM_OFFICE, module, "TM");

        verify(businessRulesServiceMock, times(1)).calculate(eq(module), eq("Application_Identifier_List"), anyList());

        assertEquals(draftApplication.getProvisionalId(), provisionalId);
    }

    @Test
    public void test_retrieveApplication_EPUB_Ok() {
        String office = OHIM_OFFICE;
        String module = MODULE;
        FormatXML format = FormatXML.APPLICATION_EPUB;
        String filingNumber = "some filing number";
        String typeApplication = "TradeMark";

        fillDataRetrievalApplication(office, module, format, filingNumber, typeApplication);

        TradeMarkApplication core = (TradeMarkApplication) applicationService.retrieveApplication(office, module,
                format, filingNumber);

        verify(documentServiceMock, times(1)).unarchiveDocuments(any(Document.class), eq(format.value()), eq(true));
        verify(applicationDAO, times(1)).findDraftApplicationByProvisionalId(filingNumber);
        verify(documentServiceMock, times(1)).searchDocument(anyMap(), eq(false));
        verify(transactionUtil, times(1)).generateIPApplication(any(byte[].class), eq(typeApplication), eq(true));

        // Check the values in backApplicant.xml
        assertEquals(core.getTradeMark().getApplicationLanguage(), "en");
        assertEquals(core.getTradeMark().getSecondLanguage(), "el");
        assertEquals(core.getTradeMark().getApplicants().get(0).getName().getFirstName(), "Christos");
        assertEquals(core.getTradeMark().getRepresentatives().get(0).getEmails().get(0).getEmailAddress(),
				"test@oami.gr");
        assertEquals(core.getTradeMark().getSeniorities().get(0).getKind(), SeniorityKind.INTERNATIONAL_TRADE_MARK);
        assertEquals(core.getTradeMark().getPriorities().get(0).getFilingNumber(), "111324234");
        assertEquals(core.getTradeMark().getClassDescriptions().get(0).getClassNumber(), "32");

    }

    @Test(expected = SPException.class)
    public void test_retrieveApplication_expectedException() {

        String office = OHIM_OFFICE;
        String module = MODULE;
        FormatXML format = FormatXML.APPLICATION_XML;
        String filingNumber = "some filing number";
        String typeApplication = "TradeMark";

        Map<String, String> customProperties = new HashMap<String, String>();
        customProperties.put(FODocument.ATTACHMENT_TYPE, format.value());
        customProperties.put(FODocument.OFFICE, office);
        customProperties.put(FODocument.MODULE, module);
        customProperties.put(FODocument.FILING_NUMBER, filingNumber);
        customProperties.put(FODocument.APPLICATION_STATUS, "drafts");
        customProperties.put(FODocument.APPLICATION_TYPE, typeApplication);

        List<Document> documentList = new ArrayList<Document>();
        documentList.add(new Document());
        documentList.get(0).setDocumentId("1");
        documentList.add(new Document());
        documentList.get(1).setDocumentId("2");

        when(documentServiceMock.searchDocument(anyMap(), anyBoolean())).thenReturn(documentList);

        DraftApplication draftApplication = new DraftApplication();
        draftApplication.setTyApplication(new TypeApplication());
        draftApplication.getTyApplication().setTypeApplication(typeApplication);
        when(applicationService.getApplicationDAO().findDraftApplicationByProvisionalId(filingNumber)).thenReturn(
                draftApplication);

        // The list has two documents. SPException expected
        applicationService.retrieveApplication(office, module, format, filingNumber);
    }

    public byte[] stupidRead(InputStream inputStream) {
        try {
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            return b;
        } catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
        }
        return null;
    }

    @Test
    public void test_exportApplication() {

        String office = OHIM_OFFICE;
        String module = MODULE;
        FormatXML format = FormatXML.APPLICATION_XML;
        String filingNumber = "some filing number";
        String typeApplication = "TradeMark";

        Map<String, String> customProperties = new HashMap<String, String>();
        customProperties.put(FODocument.ATTACHMENT_TYPE, format.value());
        customProperties.put(FODocument.OFFICE, office);
        customProperties.put(FODocument.MODULE, module);
        customProperties.put(FODocument.FILING_NUMBER, filingNumber);
        customProperties.put(FODocument.APPLICATION_STATUS, "drafts");
        customProperties.put(FODocument.APPLICATION_TYPE, typeApplication);

        List<Document> documentList = new ArrayList<Document>();
        Document document = new Document();
        document.setComment("some comment");
        document.setCustomProperties(customProperties);
        document.setDateCreated(new Date());
        document.setDocumentId("1234");
        document.setName("first document");
        document.setData(backApplicantXML.getBytes());

        documentList.add(document);

        FODocument mimDocument = new FODocument("", "mimetype.txt", FormatXML.APPLICATION_MIMETYPE.value(), office,
                module, filingNumber, FormatXML.APPLICATION_MIMETYPE.value(), typeApplication, "",
                mimeTypeTxt.getBytes());

        FODocument conDocument = new FODocument("", "container.xml", FormatXML.APPLICATION_CONTAINER.value(), office,
                module, filingNumber, FormatXML.APPLICATION_CONTAINER.value(), typeApplication, "",
                containerXml.getBytes());

        Map<String, Document> archivedFiles = new HashMap<String, Document>();
        archivedFiles.put(document.getName(), document);

        when(documentServiceMock.searchDocument(anyMap(), anyBoolean())).thenReturn(documentList);
        when(documentServiceMock.getContent("1234")).thenReturn(document);
        when(documentServiceMock.saveDocument(mimDocument)).thenReturn(new Document());
        when(documentServiceMock.saveDocument(conDocument)).thenReturn(new Document());
        when(documentServiceMock.archiveDocuments(anyMap(), anyString(), anyBoolean())).thenReturn(document);

        DraftApplication draftApplication = new DraftApplication();
        draftApplication.setTyApplication(new TypeApplication());
        draftApplication.getTyApplication().setTypeApplication(typeApplication);
        when(applicationService.getApplicationDAO().findDraftApplicationByProvisionalId(filingNumber)).thenReturn(
                draftApplication);

        byte[] data = applicationService.exportApplication(office, module, format, filingNumber);
        if (data == null) {
            System.out.println("	>>> Test ExportApplicationData: Data null");
            fail();
        } else {
            System.out.println("	>>> Test ExportApplicationData: Data not null");
        }
    }

    @Test
    public void test_exportApplication_withoutAttachments() {

        String office = OHIM_OFFICE;
        String module = MODULE;
        FormatXML format = FormatXML.APPLICATION_EPUB;
        String filingNumber = "some filing number";
        String typeApplication = "TradeMark";

        Map<String, String> customProperties = new HashMap<String, String>();
        customProperties.put(FODocument.ATTACHMENT_TYPE, format.value());
        customProperties.put(FODocument.OFFICE, office);
        customProperties.put(FODocument.MODULE, module);
        customProperties.put(FODocument.FILING_NUMBER, filingNumber);
        customProperties.put(FODocument.APPLICATION_STATUS, "drafts");
        customProperties.put(FODocument.APPLICATION_TYPE, typeApplication);

        List<Document> documentList = new ArrayList<Document>();
        Document document = new Document();
        document.setComment("some comment");
        document.setCustomProperties(customProperties);
        document.setDateCreated(new Date());
        document.setDocumentId("1234");
        document.setName("first document");
        document.setData(backApplicantXML.getBytes());

        documentList.add(document);

        when(documentServiceMock.searchDocument(anyMap(), anyBoolean())).thenReturn(documentList);
        when(documentServiceMock.getContent("1234")).thenReturn(document);

        DraftApplication draftApplication = new DraftApplication();
        draftApplication.setTyApplication(new TypeApplication());
        draftApplication.getTyApplication().setTypeApplication(typeApplication);
        when(applicationService.getApplicationDAO().findDraftApplicationByProvisionalId(filingNumber)).thenReturn(
                draftApplication);

        Document archivedDocument = new Document();
        archivedDocument.setData(new String("mock").getBytes());
        when(documentServiceMock.archiveDocuments(anyMap(), eq(format.value()), eq(false)))
                .thenReturn(archivedDocument);

        byte[] data = applicationService.exportApplication(office, module, format, filingNumber);
        if (data == null) {
            System.out.println("	>>> Test ExportApplicationData WithoutAttachments: Data null");
            fail();
        } else {
            System.out.println("	>>> Test ExportApplicationData WithoutAttachments: Data not null");
        }
    }

    public void test_persistApplication() {

        String office = OHIM_OFFICE;
        String module = MODULE;
        FormatXML format = FormatXML.APPLICATION_XML;
        String filingNumber = "some filing number";
        String typeApplication = "TradeMark";

        Map<String, String> customProperties = new HashMap<String, String>();
        customProperties.put(FODocument.ATTACHMENT_TYPE, format.value());
        customProperties.put(FODocument.OFFICE, office);
        customProperties.put(FODocument.MODULE, module);
        customProperties.put(FODocument.FILING_NUMBER, filingNumber);
        customProperties.put(FODocument.APPLICATION_STATUS, "drafts");
        customProperties.put(FODocument.APPLICATION_TYPE, typeApplication);

        Document document = new Document();
        document.setData(backApplicantXML.getBytes());
        document.setCustomProperties(customProperties);

        when(documentServiceMock.saveDocument(document)).thenReturn(document);

        DraftApplication draftApplication = new DraftApplication();
        draftApplication.setTyApplication(new TypeApplication());
        draftApplication.getTyApplication().setTypeApplication(typeApplication);
        when(applicationService.getApplicationDAO().findDraftApplicationByProvisionalId(filingNumber)).thenReturn(
                draftApplication);

        TradeMarkApplication application = new TradeMarkApplication();
        application.setApplicationFilingDate(new Date());
        application.setApplicationFilingNumber("some filing number");
        application.setTradeMark(fillCoreTradeMark(TradeMark.class));

        applicationService.persistApplication(office, module, format, application);
    }

    @Test
    public void persistApplicationEPUB() {

        String office = OHIM_OFFICE;
        String module = MODULE;
        FormatXML format = FormatXML.APPLICATION_EPUB;
        String filingNumber = "some filing number";
        String typeApplication = "TradeMark";

        Map<String, String> customProperties = new HashMap<String, String>();
        customProperties.put(FODocument.ATTACHMENT_TYPE, format.value());
        customProperties.put(FODocument.OFFICE, office);
        customProperties.put(FODocument.MODULE, module);
        customProperties.put(FODocument.FILING_NUMBER, filingNumber);
        customProperties.put(FODocument.APPLICATION_STATUS, "drafts");
        customProperties.put(FODocument.APPLICATION_TYPE, typeApplication);

        Document document = new Document();
        document.setData(backApplicantXML.getBytes());
        document.setCustomProperties(customProperties);
        document.setFileName("filename");
        document.setUri("ATTACHMENTS/test.jpg");

        when(documentServiceMock.saveDocument(document)).thenReturn(document);

        DraftApplication draftApplication = new DraftApplication();
        draftApplication.setTyApplication(new TypeApplication());
        draftApplication.getTyApplication().setTypeApplication(typeApplication);
        when(applicationService.getApplicationDAO().findDraftApplicationByProvisionalId(filingNumber)).thenReturn(
                draftApplication);

        TradeMarkApplication application = new TradeMarkApplication();
        application.setApplicationFilingDate(new Date());
        application.setApplicationFilingNumber("some filing number");
        application.setTradeMark(fillCoreTradeMark(TradeMark.class));
        application.getTradeMark().setTradeMarkDocuments(new ArrayList<AttachedDocument>());

        application.getTradeMark().getTradeMarkDocuments().add(new AttachedDocument());
        application.getTradeMark().getTradeMarkDocuments().get(0)
                .setDocumentKind(DocumentKindDefault.APPLICATION_RECEIPT);
        application.getTradeMark().getTradeMarkDocuments().get(0).setDocument(document);

        when(transactionUtil.generateByte(any(TradeMarkApplication.class), anyString(), eq(true))).thenReturn(
                MapperFactory.getTradeMark().generateByte(application, "", true));

        applicationService.persistApplication(office, module, format, application);

        verify(transactionUtil, times(1)).generateByte(any(TradeMarkApplication.class), anyString(), eq(true));
        verify(documentServiceMock, times(1)).saveDocument(any(Document.class));

    }

    @Test
    public void test_validateApplication() {
        TradeMarkApplication tradeMarkApplication = new TradeMarkApplication();
        tradeMarkApplication.setTradeMark(fillCoreTradeMark(TradeMark.class));
        tradeMarkApplication.setDocuments(new ArrayList<AttachedDocument>());

        applicationService.validateApplication(MODULE, tradeMarkApplication, new RulesInformation());

        verify(businessRulesServiceMock, times(1)).validate(eq(MODULE), eq("application_set"), anyList());
    }

    @Test
    public void test_fileApplication() {
        TradeMarkApplication tradeMarkApplication = new TradeMarkApplication();
        tradeMarkApplication.setApplicationFilingNumber("00000000001");
        tradeMarkApplication.setTradeMark(fillCoreTradeMark(TradeMark.class));
        tradeMarkApplication.setDocuments(new ArrayList<AttachedDocument>());

        DraftApplication draftApplication = new DraftApplication();
        TypeApplication typeApplication = new TypeApplication();
        typeApplication.setTypeApplication("tmefiling");
        draftApplication.setTyApplication(typeApplication);
        when(applicationDAO.findDraftApplicationByProvisionalId(eq("00000000001"))).thenReturn(draftApplication);
        when(applicationDAO.getDraftApplication(eq(draftApplication))).thenReturn(
                new eu.ohim.sp.core.domain.application.DraftApplication());

        when(systemConfigurationService.isServiceEnabled(eq("Retrieve_Application_Number"), eq("tmefiling")))
                .thenReturn(true);
        ApplicationNumber applicationNumber = new ApplicationNumber();
        applicationNumber.setNumber("AppplicationNumber0001");
        when(applicationAdapterServiceInterface.getApplicationNumber(eq(MODULE), eq("tmefiling"), eq("00000000001")))
                .thenReturn(applicationNumber);

        applicationService.fileApplication(OHIM_OFFICE, MODULE, tradeMarkApplication);

        verify(systemConfigurationService, times(1)).isServiceEnabled("Applicant_Save", "tmefiling");
        verify(systemConfigurationService, times(1)).isServiceEnabled("Representative_Save", "tmefiling");
        verify(systemConfigurationService, times(1)).isServiceEnabled("Retrieve_Application_Number", "tmefiling");
        // Do we need two calls?
        // 2 more for ApplicationNumber do we need them?
        verify(applicationDAO, times(5)).findDraftApplicationByProvisionalId(eq("00000000001"));
        // for application number
        verify(applicationDAO, times(1)).saveDraftApplication(any(DraftApplication.class));
        verify(applicationDAO, times(2)).getDraftApplication(eq(draftApplication));
        verify(applicationDAO, times(1)).updateStatus(any(eu.ohim.sp.core.domain.application.DraftApplication.class));

    }

    @Test
    public void persistApplicationEPUB_TMEserviceApplication() {

        String office = OHIM_OFFICE;
        String module = MODULE;
        FormatXML format = FormatXML.APPLICATION_EPUB;
        String filingNumber = "some filing number";
        String typeApplication = "TradeMark";

        Map<String, String> customProperties = new HashMap<String, String>();
        customProperties.put(FODocument.ATTACHMENT_TYPE, format.value());
        customProperties.put(FODocument.OFFICE, office);
        customProperties.put(FODocument.MODULE, module);
        customProperties.put(FODocument.FILING_NUMBER, filingNumber);
        customProperties.put(FODocument.APPLICATION_STATUS, "drafts");
        customProperties.put(FODocument.APPLICATION_TYPE, typeApplication);

        Document document = new Document();
        document.setData(backApplicantXML.getBytes());
        document.setCustomProperties(customProperties);
        document.setFileName("filename");

        when(documentServiceMock.saveDocument(document)).thenReturn(document);

        DraftApplication draftApplication = new DraftApplication();
        draftApplication.setTyApplication(new TypeApplication());
        draftApplication.getTyApplication().setTypeApplication(typeApplication);
        when(applicationService.getApplicationDAO().findDraftApplicationByProvisionalId(filingNumber)).thenReturn(
                draftApplication);

        TMeServiceApplication application = new TMeServiceApplication();
        application.setApplicationFilingDate(new Date());
        application.setApplicationFilingNumber("some filing number");
        application.setTradeMarks(new ArrayList<LimitedTradeMark>());
        application.getTradeMarks().add(fillCoreTradeMark(LimitedTradeMark.class));

        application.setDocuments(new ArrayList<AttachedDocument>());

        application.getDocuments().add(new AttachedDocument());
        application.getDocuments().get(0).setDocumentKind(DocumentKindDefault.APPLICATION_RECEIPT);
        application.getDocuments().get(0).setDocument(document);

        when(transactionUtil.generateByte(any(TradeMarkApplication.class), anyString(), eq(true))).thenReturn(
                MapperFactory.getTradeMark().generateByte(application, "", true));

        eServiceApplicationService.persistApplication(office, module, format, application);

        verify(transactionUtil, times(1)).generateByte(any(TradeMarkApplication.class), anyString(), eq(true));
        verify(documentServiceMock, times(1)).saveDocument(any(Document.class));

    }

    @Test
    public void testMultipleGenerateReports() {

        String office = OHIM_OFFICE;
        String module = "eservices";
        FormatXML format = FormatXML.APPLICATION_EPUB;
        String filingNumber = "some filing number";
        String typeApplication = "TradeMark";

        Collection<String> reports = new ArrayList<String>();
        reports.add("myreport");

        when(systemConfigurationService.getValueList(eq("reports_list"), eq(module))).thenReturn(reports);

        TMeServiceApplication application = new TMeServiceApplication();
        application.setApplicationFilingDate(new Date());
        application.setApplicationFilingNumber(filingNumber);
        application.setApplicationLanguage("es");
        application.setTradeMarks(new ArrayList<LimitedTradeMark>());
        application.getTradeMarks().add(fillCoreTradeMark(LimitedTradeMark.class));

        application.setDocuments(new ArrayList<AttachedDocument>());

        Map<String, String> customProperties = new HashMap<String, String>();
        customProperties.put(FODocument.ATTACHMENT_TYPE, format.value());
        customProperties.put(FODocument.OFFICE, office);
        customProperties.put(FODocument.MODULE, module);
        customProperties.put(FODocument.FILING_NUMBER, filingNumber);
        customProperties.put(FODocument.APPLICATION_STATUS, "drafts");
        customProperties.put(FODocument.APPLICATION_TYPE, typeApplication);

        Document document = new Document();
        document.setData(backApplicantXML.getBytes());
        document.setCustomProperties(customProperties);
        document.setFileName("filename");

        application.getDocuments().add(new AttachedDocument());
        application.getDocuments().get(0).setDocumentKind(DocumentKindDefault.APPLICATION_RECEIPT);
        application.getDocuments().get(0).setDocument(document);

        eServiceApplicationService.generateReceipts(office, module, application);

        verify(systemConfigurationService, times(1)).getValueList(eq("reports_list"), eq(module));

        verify(reportService, times(1)).generateReport(eq(module), eq("myreport"),
                eq(application.getApplicationLanguage()), eq(application), isNull(), eq(Boolean.FALSE.toString()),
                any(Date.class));
        verify(documentServiceMock, times(1)).saveDocument(any(Document.class));
    }

    @Test
    public void testGenerateReport() {

        String office = OHIM_OFFICE;
        String module = "eservices";
        FormatXML format = FormatXML.APPLICATION_EPUB;
        String filingNumber = "some filing number";
        String typeApplication = "TradeMark";

        TMeServiceApplication application = new TMeServiceApplication();
        application.setApplicationFilingDate(new Date());
        application.setApplicationFilingNumber(filingNumber);
        application.setApplicationLanguage("es");
        application.setTradeMarks(new ArrayList<LimitedTradeMark>());
        application.getTradeMarks().add(fillCoreTradeMark(LimitedTradeMark.class));

        application.setDocuments(new ArrayList<AttachedDocument>());

        Map<String, String> customProperties = new HashMap<String, String>();
        customProperties.put(FODocument.ATTACHMENT_TYPE, format.value());
        customProperties.put(FODocument.OFFICE, office);
        customProperties.put(FODocument.MODULE, module);
        customProperties.put(FODocument.FILING_NUMBER, filingNumber);
        customProperties.put(FODocument.APPLICATION_STATUS, "drafts");
        customProperties.put(FODocument.APPLICATION_TYPE, typeApplication);

        Document document = new Document();
        document.setData(backApplicantXML.getBytes());
        document.setCustomProperties(customProperties);
        document.setFileName("filename");

        application.getDocuments().add(new AttachedDocument());
        application.getDocuments().get(0).setDocumentKind(DocumentKindDefault.APPLICATION_RECEIPT);
        application.getDocuments().get(0).setDocument(document);

        eServiceApplicationService.generateReceipts(office, module, application);

        verify(reportService, times(1)).generateReport(eq(module), eq(ReportService.RECEIPT_REPORT),
                eq(application.getApplicationLanguage()), eq(application), isNull(), eq(Boolean.FALSE.toString()),
                any(Date.class));
        verify(documentServiceMock, times(1)).saveDocument(any(Document.class));
    }

    @Test
    public void persistApplicationXML() {

        String office = OHIM_OFFICE;
        String module = MODULE;
        FormatXML format = FormatXML.APPLICATION_XML;
        String filingNumber = "some filing number";
        String typeApplication = "TradeMark";

        Map<String, String> customProperties = new HashMap<String, String>();
        customProperties.put(FODocument.ATTACHMENT_TYPE, format.value());
        customProperties.put(FODocument.OFFICE, office);
        customProperties.put(FODocument.MODULE, module);
        customProperties.put(FODocument.FILING_NUMBER, filingNumber);
        customProperties.put(FODocument.APPLICATION_STATUS, "drafts");
        customProperties.put(FODocument.APPLICATION_TYPE, typeApplication);

        Document document = new Document();
        document.setData(backApplicantXML.getBytes());
        document.setCustomProperties(customProperties);
        document.setFileName("filename");

        when(documentServiceMock.saveDocument(document)).thenReturn(document);

        DraftApplication draftApplication = new DraftApplication();
        draftApplication.setTyApplication(new TypeApplication());
        draftApplication.getTyApplication().setTypeApplication(typeApplication);
        when(applicationService.getApplicationDAO().findDraftApplicationByProvisionalId(filingNumber)).thenReturn(
                draftApplication);

        TradeMarkApplication application = new TradeMarkApplication();
        application.setApplicationFilingDate(new Date());
        application.setApplicationFilingNumber("some filing number");
        application.setTradeMark(fillCoreTradeMark(TradeMark.class));
        application.getTradeMark().setTradeMarkDocuments(new ArrayList<AttachedDocument>());

        application.getTradeMark().getTradeMarkDocuments().add(new AttachedDocument());
        application.getTradeMark().getTradeMarkDocuments().get(0)
                .setDocumentKind(DocumentKindDefault.APPLICATION_RECEIPT);
        application.getTradeMark().getTradeMarkDocuments().get(0).setDocument(document);

        when(transactionUtil.generateByte(any(TradeMarkApplication.class), anyString(), eq(false))).thenReturn(
                MapperFactory.getTradeMark().generateByte(application, "", false));

        applicationService.persistApplication(office, module, format, application);

        verify(transactionUtil, times(1)).generateByte(any(TradeMarkApplication.class), anyString(), eq(false));
        verify(documentServiceMock, times(1)).saveDocument(any(Document.class));

    }

    @Test
    public void test_importApplication() {

        String office = OHIM_OFFICE;
        String module = MODULE;
        FormatXML format = FormatXML.APPLICATION_XML;
        String filingNumber = "some filing number";
        String typeApplication = "TM";

        eu.ohim.sp.core.domain.application.DraftApplication core = new eu.ohim.sp.core.domain.application.DraftApplication();
        core.setProvisionalId("some provisionalId");

        Map<String, String> customProperties = new HashMap<String, String>();
        customProperties.put(FODocument.ATTACHMENT_TYPE, format.value());
        customProperties.put(FODocument.OFFICE, office);
        customProperties.put(FODocument.MODULE, module);
        customProperties.put(FODocument.FILING_NUMBER, filingNumber);
        customProperties.put(FODocument.APPLICATION_STATUS, "drafts");
        customProperties.put(FODocument.APPLICATION_TYPE, typeApplication);

        List<Document> documentList = new ArrayList<Document>();
        Document document = new Document();
        document.setComment("some comment");
        document.setCustomProperties(customProperties);
        document.setDateCreated(new Date());
        document.setDocumentId("1234");
        document.setData(backApplicantXML.getBytes());

        documentList.add(document);

        DraftApplication draftApplication = new DraftApplication();
        draftApplication.setTyApplication(new TypeApplication());
        draftApplication.getTyApplication().setTypeApplication(typeApplication);

        when(applicationDAO.updateStatus(core)).thenReturn(draftApplication);
        when(documentServiceMock.searchDocument(anyMap(), anyBoolean())).thenReturn(documentList);
        when(documentServiceMock.getContent("1234")).thenReturn(document);

        when(applicationService.getApplicationDAO().findDraftApplicationByProvisionalId(filingNumber)).thenReturn(
                draftApplication);

        IPApplication application = applicationService.importApplication(office, module, backApplicantXML.getBytes(),
                typeApplication, format);
        if (application != null) {
            System.out.println("Test importApplication: application is not null: "
                    + application.getApplicationFilingNumber());
        } else {
            System.out.println("Test importApplication: application is null");
        }

    }

    // @Test
    public void test_unarchiveDocuments() {

    }

    private <T extends TradeMark> T fillCoreTradeMark(Class<T> clazz) {

        T tradeMark = null;
        try {
            tradeMark = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
        }
        tradeMark.setApplicationLanguage("en");
        tradeMark.setSecondLanguage("el");
        tradeMark.setApplicationReference("test 123");
        Date date = new Date();

        tradeMark.setExpirationDate(date);
        tradeMark.setFilingDate(date);
        tradeMark.setFilingNumber("test");
        tradeMark.setApplicationNumber("test");
        tradeMark.setReceivingOffice(OHIM_OFFICE);
        tradeMark.setRegistrationOffice(OHIM_OFFICE);
        tradeMark.setRegistrationDate(new Date());
        tradeMark.setRegistrationNumber("R2342334567567");
        tradeMark.setTerminationDate(new Date());
        tradeMark.setComment("comment FSP");
        tradeMark.setPriorityClaimLaterIndicator(true);
        tradeMark.setExhibitionPriorityClaimLaterIndicator(true);
        tradeMark.setApplicationDate(new Date());
        tradeMark.setMarkKind(MarkFeature.WORD);

        // Priorities
        Priority priority = new Priority();
        priority.setFilingDate(new Date());
        priority.setFilingNumber("111324234");
        priority.setFilingOffice(OHIM_OFFICE);

        Document document = new Document();
        document.setComment("comment");
        document.setFileName("filename priorities");
        document.setFileFormat(FileFormat.PDF.value());
        document.setName("name");
        document.setLanguage("en");
        document.setDocumentId("0000000000001");
        document.setUri("priority uri");

        AttachedDocument attachedDocument = new AttachedDocument();
        attachedDocument.setDocument(document);
        attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE.value());

        priority.setAttachedDocuments(new ArrayList<AttachedDocument>());
        priority.getAttachedDocuments().add(attachedDocument);
        priority.setPartialIndicator(true);

        priority.setPartialGoodsServices(new ArrayList<ClassDescription>());
        priority.getPartialGoodsServices().add(new ClassDescription());
        priority.getPartialGoodsServices().get(0).setLanguage("en");
        priority.getPartialGoodsServices().get(0).setClassNumber("42");
        priority.getPartialGoodsServices().get(0).setClassificationTerms(new ArrayList<ClassificationTerm>());
        priority.getPartialGoodsServices().get(0).getClassificationTerms().add(new ClassificationTerm());
        priority.getPartialGoodsServices().get(0).getClassificationTerms().get(0).setTermText("whatever");

        tradeMark.setPriorities(new ArrayList<Priority>());
        tradeMark.getPriorities().add(priority);

        // Seniorities
        Seniority seniority = new Seniority();
        seniority.setFilingNumber("application number");
        seniority.setOffice(OHIM_OFFICE);
        seniority.setFilingDate(new Date());
        seniority.setRegistrationDate(new Date());
        seniority.setRegistrationNumber("R21231");
        seniority.setKind(SeniorityKind.INTERNATIONAL_TRADE_MARK);
        seniority.setInternationalTradeMarkCode(InternationalTradeMarkCode.MADRID);

        document = new Document();
        document.setComment("comment");
        document.setFileName("filename seniority");
        document.setFileFormat(FileFormat.PDF.value());
        document.setName("name");
        document.setDocumentId("0000000000002");
        document.setLanguage("en");

        attachedDocument = new AttachedDocument();
        attachedDocument.setDocumentKind(DocumentKind.SENIORITY_CERTIFICATE.value());
        attachedDocument.setDocument(document);
        seniority.setAttachedDocuments(new ArrayList<AttachedDocument>());
        seniority.getAttachedDocuments().add(attachedDocument);

        seniority.setPartialIndicator(true);
        seniority.setPartialGoodsServices(new ArrayList<ClassDescription>());
        seniority.getPartialGoodsServices().add(new ClassDescription());
        seniority.getPartialGoodsServices().get(0).setLanguage("en");
        seniority.getPartialGoodsServices().get(0).setClassNumber("42");
        seniority.getPartialGoodsServices().get(0).setClassificationTerms(new ArrayList<ClassificationTerm>());
        seniority.getPartialGoodsServices().get(0).getClassificationTerms().add(new ClassificationTerm());
        seniority.getPartialGoodsServices().get(0).getClassificationTerms().get(0).setTermText("whatever");

        tradeMark.setSeniorities(new ArrayList<Seniority>());
        tradeMark.getSeniorities().add(seniority);

        // ExhibitionPriorities
        ExhibitionPriority exhibitionPriority = new ExhibitionPriority();
        exhibitionPriority.setExhibition(new Exhibition());
        exhibitionPriority.getExhibition().setCity("city");
        exhibitionPriority.getExhibition().setCountry("FR");
        exhibitionPriority.setDate(new Date());
        exhibitionPriority.setFirstDisplayDate(new Date());
        exhibitionPriority.getExhibition().setName("name");

        document = new Document();
        document.setComment("comment");
        document.setFileName("filename exhibition");
        document.setFileFormat(FileFormat.PDF.value());
        document.setName("name");
        document.setLanguage("en");
        document.setDocumentId("0000000000003");
        document.setUri("exhibition uri");

        attachedDocument = new AttachedDocument();
        attachedDocument.setDocument(document);
        attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE.value());

        exhibitionPriority.setAttachedDocuments(new ArrayList<AttachedDocument>());
        exhibitionPriority.getAttachedDocuments().add(attachedDocument);

        tradeMark.setExhibitionPriorities(new ArrayList<ExhibitionPriority>());
        tradeMark.getExhibitionPriorities().add(exhibitionPriority);

        // TransformationPriorities
        TransformationPriority transformationPriority = new TransformationPriority();
        transformationPriority.setCancellationDate(new Date());
        transformationPriority.setPriorityDate(new Date());
        transformationPriority.setRegistrationDate(new Date());
        transformationPriority.setRegistrationNumber("R21312313");

        document = new Document();
        document.setComment("comment");
        document.setFileName("filename transformation");
        document.setFileFormat(FileFormat.PDF.value());
        document.setName("name");
        document.setLanguage("en");
        document.setDocumentId("0000000000006");
        document.setUri("transformation uri");

        attachedDocument = new AttachedDocument();
        attachedDocument.setDocument(document);
        attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE.value());

        transformationPriority.setAttachedDocuments(new ArrayList<AttachedDocument>());
        transformationPriority.getAttachedDocuments().add(attachedDocument);

        tradeMark.setTransformationPriorities(new ArrayList<TransformationPriority>());
        tradeMark.getTransformationPriorities().add(transformationPriority);

        // WordSpecifications
        WordSpecification wordSpecification = new WordSpecification();
        wordSpecification.setCharacterSet("en");
        wordSpecification.setTranscriptionDetails("test whatever");
        wordSpecification.setTransliterationDetails("ere");
        wordSpecification.setWordElements("word element");

        tradeMark.setWordSpecifications(new ArrayList<WordSpecification>());
        tradeMark.getWordSpecifications().add(wordSpecification);

        wordSpecification = new WordSpecification();
        wordSpecification.setCharacterSet("en");
        wordSpecification.setTranscriptionDetails("test whatever 2");
        wordSpecification.setTransliterationDetails("ere");
        wordSpecification.setWordElements("word element");

        tradeMark.getWordSpecifications().add(wordSpecification);

        // Applicants
        Applicant applicant = new Applicant();
        applicant.setLegalForm("screw");
        applicant.setNationality("EL");
        applicant.setDomicileCountry("GR");

        applicant.setDomicileLocality("test");
        applicant.setIncorporationCountry("GR");
        applicant.setIncorporationState("Attica");

        applicant.setCorrespondenceAddresses(new ArrayList<Address>());
        applicant.getCorrespondenceAddresses().add(new Address());

        applicant.setEmails(new ArrayList<Email>());
        applicant.getEmails().add(new Email());
        applicant.getEmails().get(0).setEmailAddress("test@oami.gr");
        applicant.getEmails().add(new Email());
        applicant.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

        applicant.setPhones(new ArrayList<Phone>());
        applicant.getPhones().add(new Phone());
        applicant.getPhones().get(0).setNumber("2133542352");
        applicant.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

        applicant.getCorrespondenceAddresses().get(0).setCity("London");
        applicant.getCorrespondenceAddresses().get(0).setStreet("Agias Lauras 36");
        applicant.getCorrespondenceAddresses().get(0).setState("Attica");
        applicant.getCorrespondenceAddresses().get(0).setCountry("GR");
        applicant.getCorrespondenceAddresses().get(0).setStreetNumber("36");
        applicant.getCorrespondenceAddresses().get(0).setPostalName("Mr Christos Paparis");

        applicant.setAddresses(new ArrayList<Address>());

        applicant.getAddresses().add(new Address());
        applicant.getAddresses().get(0).setCity("London Address");
        applicant.getAddresses().get(0).setStreet("Agias Lauras 36 Address");
        applicant.getAddresses().get(0).setState("Attica Address");
        applicant.getAddresses().get(0).setCountry("GR");
        applicant.getAddresses().get(0).setStreetNumber("36 B");

        applicant.setUrls(new ArrayList<String>());
        applicant.getUrls().add("some url");

        applicant.setName(new PersonName());

        applicant.getName().setFirstName("Christos");
        applicant.getName().setMiddleName("Paparas");
        applicant.getName().setLastName("Karalis");

        applicant.getName().setSecondLastName("secondLastName");
        applicant.getName().setOrganizationName("organizationName");

        tradeMark.setApplicants(new ArrayList<Applicant>());
        tradeMark.getApplicants().add(applicant);

        // Signatures
        Signatory signatory = new Signatory();
        signatory.setDate(new Date());
        signatory.setName("Christos");
        signatory.setPlace("here");
        signatory.setId(1231);
        signatory.setCapacity(PersonRoleKind.APPLICANT);

        tradeMark.setSignatures(new ArrayList<Signatory>());
        tradeMark.getSignatures().add(signatory);

        // ImageSpecification
        ImageSpecification imageSpecification = new ImageSpecification();
        Image representation = new Image();
        representation.setFileName("filename");
        representation.setFileFormat(FileFormat.JPEG.value());
        representation.setUri("ATTACHMENTS/test.jpg");
        representation.setDocumentId("image id");
        imageSpecification.setRepresentation(representation);

        Colour colour = new Colour();
        colour.setFormat("HEX");
        colour.setValue("D213");
        imageSpecification.setColours(new ArrayList<Colour>());
        imageSpecification.getColours().add(colour);

        colour = new Colour();
        colour.setFormat("RGB");
        colour.setValue("Black");
        imageSpecification.getColours().add(colour);

        colour = new Colour();
        colour.setValue("Black");
        colour.setFormat("PANTONE");
        imageSpecification.getColours().add(colour);

        Text colourText = new Text();
        colourText.setLanguage("en");
        colourText.setValue("colour 1");
        imageSpecification.setColourClaimedText(new ArrayList<Text>());
        imageSpecification.getColourClaimedText().add(colourText);

        tradeMark.setImageSpecifications(new ArrayList<ImageSpecification>());
        tradeMark.getImageSpecifications().add(imageSpecification);

        // SoundSpecification
        SoundSpecification soundSpecification = new SoundSpecification();
        Sound commonSound = new Sound();
        commonSound.setFileFormat(SoundFileFormat.MP3.value());
        commonSound.setFileName("filename");
        commonSound.setUri("uri");
        commonSound.setDocumentId("sound id");
        soundSpecification.setDocument(commonSound);

        tradeMark.setSoundRepresentations(new ArrayList<SoundSpecification>());
        tradeMark.getSoundRepresentations().add(soundSpecification);

        // ClassDescription
        ClassDescription classDescription = new ClassDescription();
        classDescription.setFullClassCoverageIndicator(Boolean.FALSE);
        classDescription.setLanguage("en");
        classDescription.setClassNumber("32");
        classDescription.setClassificationTerms(new ArrayList<ClassificationTerm>());
        classDescription.setGoodsServicesDescription("descriptions");

        ClassificationTerm classificationTerm = new ClassificationTerm();
        classificationTerm.setTermText("Test");
        classDescription.getClassificationTerms().add(classificationTerm);

        tradeMark.setClassDescriptions(new ArrayList<ClassDescription>());
        tradeMark.getClassDescriptions().add(classDescription);

        // MarkDescriptions
        tradeMark.setMarkDescriptions(new ArrayList<MarkDescription>());
        MarkDescription markDescription = new MarkDescription();
        markDescription.setLanguage("en");
        markDescription.setValue("test papapra");
        tradeMark.getMarkDescriptions().add(markDescription);

        markDescription = new MarkDescription();
        markDescription.setLanguage("ES");
        markDescription.setValue("test papapra");

        tradeMark.getMarkDescriptions().add(markDescription);

        // MarkDisclaimers
        tradeMark.setMarkDisclaimers(new ArrayList<MarkDisclaimer>());
        MarkDisclaimer markDisclaimer = new MarkDisclaimer();
        markDisclaimer.setValue("value");
        markDisclaimer.setLanguage("en");
        tradeMark.getMarkDisclaimers().add(markDisclaimer);

        markDisclaimer = new MarkDisclaimer();
        markDisclaimer.setValue("value");
        markDisclaimer.setLanguage("es");

        tradeMark.getMarkDisclaimers().add(markDisclaimer);

        // Representatives
        Representative representative = new Representative();
        representative.setLegalForm("screw");
        representative.setNationality("EL");
        representative.setDomicileCountry("GR");

        representative.setDomicileLocality("test");
        representative.setIncorporationCountry("GR");
        representative.setIncorporationState("Attica");

        representative.setCorrespondenceAddresses(new ArrayList<Address>());
        representative.getCorrespondenceAddresses().add(new Address());

        representative.setEmails(new ArrayList<Email>());
        representative.getEmails().add(new Email());
        representative.getEmails().get(0).setEmailAddress("test@oami.gr");
        representative.getEmails().add(new Email());
        representative.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

        representative.setPhones(new ArrayList<Phone>());
        representative.getPhones().add(new Phone());
        representative.getPhones().get(0).setNumber("2133542352");
        representative.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

        representative.getCorrespondenceAddresses().get(0).setCity("London");
        representative.getCorrespondenceAddresses().get(0).setStreet("Agias Lauras 36");
        representative.getCorrespondenceAddresses().get(0).setState("Attica");
        representative.getCorrespondenceAddresses().get(0).setCountry("GR");
        representative.getCorrespondenceAddresses().get(0).setStreetNumber("36");
        representative.getCorrespondenceAddresses().get(0).setPostalName("Mr Christos Paparis");

        representative.setAddresses(new ArrayList<Address>());

        representative.getAddresses().add(new Address());
        representative.getAddresses().get(0).setCity("London Address");
        representative.getAddresses().get(0).setStreet("Agias Lauras 36 Address");
        representative.getAddresses().get(0).setState("Attica Address");
        representative.getAddresses().get(0).setCountry("GR");
        representative.getAddresses().get(0).setStreetNumber("36 B");

        representative.setUrls(new ArrayList<String>());
        representative.getUrls().add("some url");
        representative.getUrls().add("some url 2");

        representative.setName(new PersonName());

        representative.getName().setFirstName("Christos");
        representative.getName().setMiddleName("Paparas");
        representative.getName().setLastName("Karalis");

        tradeMark.setRepresentatives(new ArrayList<Representative>());
        tradeMark.getRepresentatives().add(representative);

        tradeMark.setTradeMarkDocuments(new ArrayList<AttachedDocument>());

        document = new Document();
        document.setComment("comment");
        document.setFileName("filename tradeMarkdocuments");
        document.setFileFormat(FileFormat.PDF.value());
        document.setName("name");
        document.setLanguage("en");
        document.setDocumentId("0000000000007");
        document.setUri("tradeMarkDocuments uri");

        attachedDocument = new AttachedDocument();
        attachedDocument.setDocument(document);
        attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE.value());

        tradeMark.setTradeMarkDocuments(new ArrayList<AttachedDocument>());
        tradeMark.getTradeMarkDocuments().add(attachedDocument);

        return tradeMark;
    }
}
