/*
 * ApplicationServiceBean:: TMApplicationServiceBean 01/10/13 17:01 karalch $
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
import java.util.Date;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.application.dao.ApplicationDAO;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.application.ApplicationStatus;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.application.PrefixPdfKind;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.DocumentKindDefault;
import eu.ohim.sp.core.domain.resources.FODocument;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.core.person.ApplicantService;
import eu.ohim.sp.core.person.RepresentativeService;
import eu.ohim.sp.core.report.ReportService;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.user.UserSearchService;
import eu.ohim.sp.external.application.ApplicationClient;
import eu.ohim.sp.external.application.ApplicationClientInside;
import eu.ohim.sp.external.application.TransactionUtil;
import eu.ohim.sp.external.application.qualifier.TradeMark;
import eu.ohim.sp.external.user.UserSearchClientInside;
import eu.ohim.sp.filing.domain.utils.ReferenceUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

@Interceptors(ExceptionHandlingInterceptor.class)
@Stateless
public class TMApplicationServiceBean extends ApplicationServiceBean implements ApplicationServiceRemote,
        ApplicationServiceLocal {

    private static final Logger LOGGER = Logger.getLogger(TMApplicationServiceBean.class);

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

    @EJB(lookup = "java:global/applicantLocal")
    private ApplicantService applicantService;

    @EJB(lookup = "java:global/representativeLocal")
    private RepresentativeService representativeService;

    @EJB(lookup = "java:global/reportLocal")
    private ReportService reportService;

    @Inject
    @TradeMark
    private TransactionUtil transactionUtil;

    @Override
    public IPApplication generateReceipts(String office, String module, IPApplication application) {
        byte[] data = reportService.generateReport(module, ReportService.RECEIPT_REPORT,
                application instanceof TradeMarkApplication ? ((TradeMarkApplication) application).getTradeMark()
                        .getApplicationLanguage() : "en", application, null, false, null);

        FODocument foDocument = new FODocument();
        foDocument.setFilingNumber(application.getApplicationFilingNumber());
        foDocument.setModule(module);
        foDocument.setAttachmentType(FormatXML.APPLICATION_OTHER.value());
        foDocument.setApplicationType(application.getApplicationType());
        foDocument.setOffice(office);
        foDocument.setData(data);
        foDocument.setFileFormat("application/pdf");
        foDocument.setFileName(ReportService.RECEIPT_REPORT + ".pdf");
        foDocument.setName(ReportService.RECEIPT_REPORT + application.getApplicationFilingNumber() + ".pdf");
        foDocument.setDateCreated(new Date());

        Document persistedDocument = documentService.saveDocument(foDocument);

        AttachedDocument attachedDocument = new AttachedDocument();
        attachedDocument.setDocumentKind(DocumentKindDefault.APPLICATION_RECEIPT);
        attachedDocument.setDocument(persistedDocument);

        application.getDocuments().add(attachedDocument);

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

    public void setTransactionUtil(TransactionUtil transactionUtil) {
        this.transactionUtil = transactionUtil;
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
    public IPApplication generateReceipts(String office, String module, IPApplication application,
            PrefixPdfKind prefixPdfKind) {
        return generateReceipts(office, module, application);
    }

    @Override
    public IPApplication fillApplicationDocuments(IPApplication iPApplication) {
        Set<ImageSpecification> imageSpecifications = ReferenceUtil.getReferencesOf(iPApplication,
                ImageSpecification.class);
        for (ImageSpecification imageSpecification : imageSpecifications) {
            if (imageSpecification.getRepresentation() != null) {
                Document document = imageSpecification.getRepresentation();
                document.setData(documentService.getData(document));
            }
        }
        return iPApplication;
    }

    @Override
    public IPApplication fillApplicationDocuments(IPApplication iPApplication, boolean saveUnsavedDocuments) {
        return fillApplicationDocuments(iPApplication);
    }

    @Override
    protected ApplicationStatus getFinalStatus(String applicationType) {
        if(applicationType.equals("GI_EFILING")){
            return ApplicationStatus.STATUS_VALIDATION_SUCCESS;
        }
        return ApplicationStatus.STATUS_SUBMITTED;
    }
}
