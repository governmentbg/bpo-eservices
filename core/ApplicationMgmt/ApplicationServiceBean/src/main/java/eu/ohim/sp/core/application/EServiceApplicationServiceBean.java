/*
 * ApplicationServiceBean:: EServiceApplicationServiceBean 23/10/13 12:13 karalch $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */

package eu.ohim.sp.core.application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import eu.ohim.sp.core.domain.application.ApplicationStatus;
import eu.ohim.sp.external.application.ApplicationClientInside;
import eu.ohim.sp.external.user.UserSearchClientInside;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.application.dao.ApplicationDAO;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.application.EServiceApplication;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.application.PrefixPdfKind;
import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.DocumentKindDefault;
import eu.ohim.sp.core.domain.resources.FODocument;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.core.person.ApplicantService;
import eu.ohim.sp.core.person.RepresentativeService;
import eu.ohim.sp.core.report.ReportService;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.user.UserSearchService;
import eu.ohim.sp.external.application.ApplicationClient;
import eu.ohim.sp.external.application.TransactionUtil;
import eu.ohim.sp.external.application.qualifier.Eservice;
import eu.ohim.sp.filing.domain.utils.ReferenceUtil;

@Interceptors(ExceptionHandlingInterceptor.class)
@Stateless
public class EServiceApplicationServiceBean extends ApplicationServiceBean implements ApplicationServiceRemote,
        ApplicationServiceLocal {

    @Inject
    private ApplicationDAO applicationDAO;

    @EJB(lookup = "java:global/documentLocal")
    private DocumentService documentService;

    @EJB(lookup = "java:global/businessRulesLocal")
    private RulesService businessRulesService;

    @Inject @UserSearchClientInside
    private UserSearchService userService;

    @EJB(lookup = "java:global/configurationLocal")
    private ConfigurationService systemConfigurationService;

    @Inject @ApplicationClientInside
    private ApplicationClient applicationAdapter;

    @EJB(lookup = "java:global/reportLocal")
    private ReportService reportService;

    @EJB(lookup = "java:global/applicantLocal")
    private ApplicantService applicantService;

    @EJB(lookup = "java:global/representativeLocal")
    private RepresentativeService representativeService;

    private static final String RECEIPT_LIST_GENERATION = "reports_list";

    private static final String GENERAL_CONFIG = "general";

    @Inject
    @Eservice
    private TransactionUtil transactionUtil;

    private static final Logger LOGGER = Logger.getLogger(EServiceApplicationServiceBean.class);

    private AttachedDocument callReportAndGenerateDocument(String office, String module, String report,
            IPApplication application, String filename) {
        byte[] data = reportService.generateReport(module, report, application instanceof EServiceApplication
                ? ((EServiceApplication) application).getApplicationLanguage() : "en", application, null, Boolean.FALSE.toString(), new Date());

        FODocument foDocument = new FODocument();
        foDocument.setFilingNumber(application.getApplicationFilingNumber());
        foDocument.setModule(module);
        foDocument.setAttachmentType(FormatXML.APPLICATION_OTHER.value());
        foDocument.setApplicationType(application.getApplicationType());
        foDocument.setOffice(office);
        foDocument.setData(data);
        foDocument.setFileFormat("application/pdf");
        foDocument.setFileName(filename + ".pdf");
        foDocument.setName(foDocument.getFileName());
        foDocument.setDateCreated(new Date());

        Document persistedDocument = documentService.saveDocument(foDocument);

        AttachedDocument attachedDocument = new AttachedDocument();
        attachedDocument.setDocumentKind(DocumentKindDefault.APPLICATION_RECEIPT);
        attachedDocument.setDocument(persistedDocument);

        return attachedDocument;
    }

    @Override
    public IPApplication generateReceipts(String office, String module, IPApplication application,
            PrefixPdfKind prefixPdfKind) {
        Collection<String> reports = systemConfigurationService.getValueList(RECEIPT_LIST_GENERATION, module);
        String filename = null;
        if (prefixPdfKind != null) {
            filename = systemConfigurationService.getValue(prefixPdfKind.toString(), GENERAL_CONFIG);
        }
        if (reports != null && !reports.isEmpty()) {
            for (String report : reports) {
                if (application.getDocuments() == null) {
                    application.setDocuments(new ArrayList<AttachedDocument>());
                }
                if (StringUtils.isNotBlank(filename)) {
                    filename = filename + report;
                } else {
                    filename = report;
                }
                application.getDocuments().add(
                        callReportAndGenerateDocument(office, module, report, application, filename));
            }
        } else {
            if (application.getDocuments() == null) {
                application.setDocuments(new ArrayList<AttachedDocument>());
            }
            application.getDocuments().add(
                    callReportAndGenerateDocument(office, module, ReportService.RECEIPT_REPORT, application,
                            ReportService.RECEIPT_REPORT));
        }

        return application;
    }

    @Override
    public ApplicationDAO getApplicationDAO() {
        return applicationDAO;
    }

    @Override
    public DocumentService getDocumentService() {
        return documentService;
    }

    @Override
    public RulesService getBusinessRulesService() {
        return businessRulesService;
    }

    @Override
    public UserSearchService getUserService() {
        return userService;
    }

    @Override
    public ConfigurationService getSystemConfigurationService() {
        return systemConfigurationService;
    }

    @Override
    public ApplicationClient getApplicationAdapter() {
        return applicationAdapter;
    }

    @Override
    public TransactionUtil getTransactionUtil() {
        return transactionUtil;
    }

    @Override
    protected ApplicantService getApplicantService() {
        return applicantService;
    }

    @Override
    protected RepresentativeService getRepresentativeService() {
        return representativeService;
    }

    @Override
    public IPApplication generateReceipts(String office, String module, IPApplication application) {
        return generateReceipts(office, module, application, null);
    }

    @Override
    public IPApplication fillApplicationDocuments(IPApplication iPApplication) {
       return fillApplicationDocuments(iPApplication, false);
    }

    @Override
    public IPApplication fillApplicationDocuments(IPApplication iPApplication, boolean saveUnsavedDocuments){
        Set<ImageSpecification> imageSpecifications = ReferenceUtil.getReferencesOf(iPApplication,
                ImageSpecification.class);
        for (ImageSpecification imageSpecification : imageSpecifications) {
            if (imageSpecification.getRepresentation() != null) {
                Document document = imageSpecification.getRepresentation();
                document.setData(documentService.getData(document));
                if(saveUnsavedDocuments && StringUtils.isBlank(document.getDocumentId()) && StringUtils.isNotBlank(document.getUri()) && document.getData() != null){
                   imageSpecification.setRepresentation(saveDocumentWithURI(document, iPApplication.getApplicationFilingNumber(), iPApplication.getApplicationType()));
                }
            }
        }
        Set<DesignView> designViews = ReferenceUtil.getReferencesOf(iPApplication, DesignView.class);
        for (DesignView designView : designViews) {
            if (designView.getView() != null && designView.getView().getDocument() != null) {
                Document document = designView.getView().getDocument();
                document.setData(documentService.getData(document));
                if(saveUnsavedDocuments && StringUtils.isBlank(document.getDocumentId()) && StringUtils.isNotBlank(document.getUri()) && document.getData() != null){
                    designView.getView().setDocument(saveDocumentWithURI(document, iPApplication.getApplicationFilingNumber(), iPApplication.getApplicationType()));
                }
            }
        }
        return iPApplication;
    }

    private Document saveDocumentWithURI(Document document, String filingNumber, String applicationType){
        FODocument foDocument = new FODocument();
        foDocument.setData(document.getData());
        foDocument.setUri(document.getUri());
        String fname = document.getUri();
        if(fname.indexOf("/") != -1 && fname.length() >= fname.lastIndexOf("/")+2){
            fname = fname.substring(fname.lastIndexOf("/")+1, fname.length());
        }
        foDocument.setName(fname);
        foDocument.setFileName(fname);
        foDocument.setFilingNumber(filingNumber);
        foDocument.setApplicationType(applicationType);
        foDocument.setDateCreated(new Date());
        foDocument.setFileSize((long)foDocument.getData().length);
        Document saved = documentService.saveDocument(foDocument);
        saved.setData(document.getData());
        return saved;
    }

    @Override
    protected ApplicationStatus getFinalStatus(String applicationType) {
        return ApplicationStatus.STATUS_VALIDATION_SUCCESS;
    }
}
