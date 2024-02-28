/*
 * * ApplicationServiceBean:: $DSApplicationServiceTest 4/2/2014 9:47 am karalch $
 * * * . * .
 * * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * * . RR R . in the Internal Market (trade marks and designs)
 * * * * RRR *
 * * * . RR RR . ALL RIGHTS RESERVED
 * * * * . _ . *
 */

package eu.ohim.sp.core.application;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
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
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.DocumentKindDefault;
import eu.ohim.sp.core.domain.resources.FODocument;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import eu.ohim.sp.core.person.ApplicantService;
import eu.ohim.sp.core.person.RepresentativeService;
import eu.ohim.sp.core.report.ReportService;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.user.UserSearchService;
import eu.ohim.sp.external.application.ApplicationClient;
import eu.ohim.sp.external.application.TransactionUtil;

public class DSApplicationServiceTest {

    public static final String OHIM_OFFICE = "EM";
    public static final String MODULE = "efiling";

    @Mock
    private ApplicationDAO applicationDAOMock;
    @Mock
    private DocumentService documentServiceMock;
    @Mock
    private RulesService businessRulesServiceMock;
    @Mock
    private UserSearchService userServiceMock;
    @Mock
    private ConfigurationService systemConfigurationServiceMock;
    @Mock
    private ApplicationClient applicationAdapterMock;
    @Mock
    private TransactionUtil transactionUtilMock;
    @Mock
    private ReportService reportServiceMock;
    @Mock
    private ApplicantService applicantServiceMock;
    @Mock
    private RepresentativeService representativeServiceMock;

    @InjectMocks
    DSApplicationServiceBean dsApplicationService;

    String backApplicantXML;
    String mimeTypeTxt;
    String containerXml;

    private String readFileAsString(String path) {
        String info = null;
        try {
            info = IOUtils.toString(this.getClass().getResourceAsStream(path), "UTF-8");
        } catch (IOException e) {
            throw new SPException(e.getMessage());
        }
        return info;
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        backApplicantXML = readFileAsString("/backApplicant.xml");
        mimeTypeTxt = readFileAsString("/files/mimetype.txt");
        containerXml = readFileAsString("/files/container.xml");
    }

    @Test
    public void testGenerateReceipts() {

        String office = OHIM_OFFICE;
        String module = "eservices";
        FormatXML format = FormatXML.APPLICATION_EPUB;
        String filingNumber = "some filing number";
        String typeApplication = "TradeMark";

        Collection<String> reports = new ArrayList<String>();
        reports.add("myreport");

        when(systemConfigurationServiceMock.getValueList(eq("reports_list"), eq(module))).thenReturn(reports);

        TMeServiceApplication application = new TMeServiceApplication();
        application.setApplicationFilingDate(new Date());
        application.setApplicationFilingNumber(filingNumber);
        application.setApplicationLanguage("es");
        application.setTradeMarks(new ArrayList<LimitedTradeMark>());
        // application.getTradeMarks().add(fillCoreTradeMark(LimitedTradeMark.class));

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

        dsApplicationService.generateReceipts(office, module, application);

        verify(documentServiceMock, times(1)).saveDocument(any(Document.class));
    }

    @Test
    public void testGetApplicationDAO() {
        ApplicationDAO applicationDAO = dsApplicationService.getApplicationDAO();
        assertTrue(applicationDAO != null);
    }

    @Test
    public void testGetDocumentService() {
        DocumentService documentService = dsApplicationService.getDocumentService();
        assertTrue(documentService != null);
    }

    @Test
    public void testGetBusinessRulesService() {
        RulesService businessRulesService = dsApplicationService.getBusinessRulesService();
        assertTrue(businessRulesService != null);
    }

    @Test
    public void testGetUserService() {
        UserSearchService userService = dsApplicationService.getUserService();
        assertTrue(userService != null);
    }

    @Test
    public void testGetSystemConfigurationService() {
        ConfigurationService systemConfigurationService = dsApplicationService.getSystemConfigurationService();
        assertTrue(systemConfigurationService != null);
    }

    @Test
    public void testGetApplicationAdapter() {
        ApplicationClient applicationAdapter = dsApplicationService.getApplicationAdapter();
        assertTrue(applicationAdapter != null);
    }

    @Test
    public void testGetTransactionUtil() {
        TransactionUtil transactionUtil = dsApplicationService.getTransactionUtil();
        assertTrue(transactionUtil != null);
    }

    @Test
    public void testGetApplicationService() {
        ApplicantService applicantService = dsApplicationService.getApplicantService();
        assertTrue(applicantService != null);
    }

    @Test
    public void testGetRepresentativeService() {
        RepresentativeService representativeService = dsApplicationService.getRepresentativeService();
        assertTrue(representativeService != null);
    }
}
