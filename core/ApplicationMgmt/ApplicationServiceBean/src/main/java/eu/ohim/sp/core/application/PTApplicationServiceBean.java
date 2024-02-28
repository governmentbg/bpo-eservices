package eu.ohim.sp.core.application;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.application.dao.ApplicationDAO;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.application.ApplicationStatus;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.application.PrefixPdfKind;
import eu.ohim.sp.core.domain.patent.PatentApplication;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.DocumentKindDefault;
import eu.ohim.sp.core.domain.resources.FODocument;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.person.ApplicantService;
import eu.ohim.sp.core.person.RepresentativeService;
import eu.ohim.sp.core.report.ReportService;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.user.UserSearchService;
import eu.ohim.sp.external.application.ApplicationClient;
import eu.ohim.sp.external.application.ApplicationClientInside;
import eu.ohim.sp.external.application.TransactionUtil;
import eu.ohim.sp.external.application.qualifier.Patent;
import eu.ohim.sp.external.user.UserSearchClientInside;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.Date;

/**
 * Created by Raya
 * 16.04.2019
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Stateless
public class PTApplicationServiceBean  extends ApplicationServiceBean implements ApplicationServiceRemote,
    ApplicationServiceLocal {

    @EJB(lookup = "java:global/documentLocal")
    private DocumentService documentService;

    @EJB(lookup = "java:global/businessRulesLocal")
    private RulesService businessRulesService;

    @Inject
    @UserSearchClientInside
    private UserSearchService userService;

    @EJB(lookup = "java:global/configurationLocal")
    private ConfigurationService systemConfigurationService;

    @Inject @ApplicationClientInside
    private ApplicationClient applicationAdapter;

    @Inject
    private ApplicationDAO applicationDAO;

    @Inject
    @Patent
    private TransactionUtil transactionUtil;

    @EJB(lookup = "java:global/reportLocal")
    private ReportService reportService;

    @EJB(lookup = "java:global/applicantLocal")
    private ApplicantService applicantService;

    @EJB(lookup = "java:global/representativeLocal")
    private RepresentativeService representativeService;

    @Override
    public IPApplication generateReceipts(String office, String module, IPApplication application) {
        byte[] data = reportService.generateReport(module, ReportService.RECEIPT_REPORT,
            application instanceof PatentApplication ? ((PatentApplication) application).getPatent().getApplicationLanguage()
                : "en", application, null, Boolean.FALSE.toString(), null);

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
    public IPApplication generateReceipts(String office, String module, IPApplication application, PrefixPdfKind prefixPdfKind) {
        return generateReceipts(office, module, application);
    }

    @Override
    protected ApplicationDAO getApplicationDAO() {
        return applicationDAO;
    }

    @Override
    protected DocumentService getDocumentService() {
        return documentService;
    }

    @Override
    protected RulesService getBusinessRulesService() {
        return businessRulesService;
    }

    @Override
    protected UserSearchService getUserService() {
        return userService;
    }

    @Override
    protected ConfigurationService getSystemConfigurationService() {
        return systemConfigurationService;
    }

    @Override
    protected ApplicationClient getApplicationAdapter() {
        return applicationAdapter;
    }

    @Override
    protected TransactionUtil getTransactionUtil() {
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
    public IPApplication fillApplicationDocuments(IPApplication iPApplication) {
        return iPApplication;
    }

    @Override
    public IPApplication fillApplicationDocuments(IPApplication iPApplication, boolean saveUnsavedDocuments) {
        return fillApplicationDocuments(iPApplication);
    }

    @Override
    protected ApplicationStatus getFinalStatus(String applicationType) {
        return ApplicationStatus.STATUS_VALIDATION_SUCCESS;
    }
}
