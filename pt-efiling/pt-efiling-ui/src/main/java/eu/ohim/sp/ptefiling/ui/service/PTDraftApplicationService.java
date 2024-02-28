package eu.ohim.sp.ptefiling.ui.service;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.security.authorisation.domain.SPUser;
import eu.ohim.sp.common.ui.form.application.SubmittedForm;
import eu.ohim.sp.common.ui.form.application.SubmittedMap;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.FeeServiceInterface;
import eu.ohim.sp.common.ui.util.AuthenticationUtil;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.patent.PatentApplication;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.DocumentKindDefault;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.ptefiling.ui.adapter.PTFlowBeanFactory;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBeanImpl;
import eu.ohim.sp.ptefiling.ui.service.interfaces.PTApplicationServiceInterface;
import eu.ohim.sp.ptefiling.ui.service.interfaces.PTImportServiceInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by Raya
 * 15.04.2019
 */
@Service(value="draftApplicationService")
public class PTDraftApplicationService implements PTApplicationServiceInterface {

    private static final Logger LOGGER = Logger.getLogger(PTDraftApplicationService.class);

    @Value("${sp.office}")
    private String office;

    @Autowired
    private SubmittedMap submittedMap;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private PTFlowBeanFactory flowBeanFactory;

    @Autowired
    private FormUtil formUtil;

    @Autowired(required=false)
    private FlowScopeDetails flowScopeDetails;

    @Autowired
    private FeeServiceInterface feeService;

    @Autowired
    private SectionViewConfiguration sectionViewConfiguration;

    @Autowired
    private PTImportServiceInterface ptImportService;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    private static final String MODULE = "ptefiling";
    private static final FormatXML EXPORT_FORMAT = FormatXML.APPLICATION_XML;
    private static final FormatXML SAVE_PORTAL_FORMAT = FormatXML.APPLICATION_EPUB;

    @Override
    public String loadApplicationLocally(byte[] data) {
        IPApplication patentApplication = applicationService.importApplication(office, MODULE, data, formUtil.getType(), EXPORT_FORMAT);
        if(patentApplication == null){
            throw new SPException("Could not load application");
        }
        if(patentApplication.getApplicationType() != null &&
            !flowScopeDetails.getFlowModeId().replaceAll("-", "_").equalsIgnoreCase(patentApplication.getApplicationType())){
            throw new SPException("Wrong application type loaded for "+flowScopeDetails.getFlowModeId());
        }
        return patentApplication.getApplicationFilingNumber();
    }

    @Override
    public PTFlowBean loadApplicationLocally(String provisionalId) {
        PatentApplication patentApplication =
            (PatentApplication) applicationService.retrieveApplication(office, MODULE, EXPORT_FORMAT, provisionalId);

        PTFlowBean flowBean = flowBeanFactory.convertFrom(patentApplication);
        if(flowBean.getApplicants() != null){
            flowBean.getApplicants().stream().forEach(e -> {
                if(e instanceof NaturalPersonForm)
                    ((NaturalPersonForm)e).setDesignerIndicator(false);
            });
        }

        if(flowBean.getDivisionalApplications() != null){
            flowBean.getDivisionalApplications().stream().forEach(e -> {
                if(e.getImported()){
                    flowBean.setEarlierAppImported(true);
                }
            });
        }

        if(flowBean.getParallelApplications() != null){
            flowBean.getParallelApplications().stream().forEach(e -> {
                if(e.getImported()){
                    flowBean.setEarlierAppImported(true);
                }
            });
        }

        if(flowBean.getTransformations() != null){
            flowBean.getTransformations().stream().forEach(e -> {
                if(e.getImported()){
                    flowBean.setEarlierAppImported(true);
                }
            });
        }

        if (flowBean.getPatent() != null && flowBean.getPatent().getApplicationNumber() != null) {
            ptImportService.getPatentTemplateImport(flowBean, flowBean.getPatent().getApplicationNumber(),
                "application", false);
        }

        return flowBean;
    }

    @Override
    public String fileApplication(PTFlowBean flowBean) {
        feeService.getFeesInformation((PTFlowBeanImpl)flowBean);
        LOGGER.info("***************** Entered PT fileApplication");

        SPUser user = AuthenticationUtil.getAuthenticatedUser();
        String module = "eu.ohim.sp.core.rules." + formUtil.getModule();
        PatentApplication patentApplication = flowBeanFactory.convertTo(flowBean);
        if(user != null) {
            patentApplication.setUser(user.getUsername());
        }
        LOGGER.info("***************** Converted to core patentApplication");
        PatentApplication filedPatentApplication =
            (PatentApplication) applicationService.fileApplication(office, module, patentApplication);
        LOGGER.info("***************** Successfully ran through applicationService.fileApplication");
        LOGGER.info("***************** Preparing to return document id...");

        String receiptId = "";
        for(AttachedDocument attachedDocument : filedPatentApplication.getDocuments()){
            if(DocumentKindDefault.APPLICATION_RECEIPT.equals(attachedDocument.getDocumentKind())){
                receiptId = attachedDocument.getDocument().getDocumentId();
                break;
            }
        }
        return receiptId;
    }


    @Override
    public String provideProvisionalID() {
        DraftApplication draftApplication =  applicationService.initDraftApplication(office, MODULE, formUtil.getType());
        return draftApplication.getProvisionalId();
    }

    @Override
    public void saveApplication(PTFlowBean flowBean, boolean finalDraft) {
    }

    @Override
    public byte[] saveApplicationLocally(PTFlowBean flowBean) {
        PatentApplication patentApplication = flowBeanFactory.convertTo(flowBean);
        applicationService.persistApplication(office, MODULE, EXPORT_FORMAT, patentApplication);
        return applicationService.exportApplication(office, MODULE, EXPORT_FORMAT, patentApplication.getApplicationFilingNumber());
    }

    @Override
    public PTFlowBean loadApplication(String draftId, boolean finalDraft) {
        return null;
    }

    @Override
    public String storeSubmittedApplication(PTFlowBean flowBean) {
        String receiptId = fileApplication(flowBean);

        SubmittedForm submittedForm = new SubmittedForm();
        submittedForm.setProvisionalId(flowBean.getIdreserventity());
        submittedForm.setDocumentId(receiptId);
        submittedForm.setFlowBean(flowBean);
        submittedForm.setFlowModeId(flowScopeDetails.getFlowModeId());

        PaymentForm paymentForm = flowBean.getPaymentForm();
        if (paymentForm != null) {
            submittedForm.setPaymentMethod(paymentForm.getPaymentMethod());
            if (paymentForm.getPaymentResult() != null &&
                paymentForm.getPaymentResult().getPaymentRequest() != null) {
                submittedForm.setPaymentId(flowBean.getPaymentForm().getPaymentResult().getPaymentRequest().getPaymentRequestId());
            }
        }

        String uuid = UUID.randomUUID().toString();
        submittedMap.put(uuid, submittedForm);

        return uuid;
    }

    @Override
    public ErrorList validateApplication(PTFlowBean flowBean, RulesInformation rulesInformation, String flowModeId) {
        PatentApplication patentApplication = flowBeanFactory.convertTo(flowBean);

        Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
        rulesInformation.getCustomObjects().put("sections", sections);
        return applicationService.validateApplication(MODULE,patentApplication, rulesInformation);
    }

    @Override
    public ErrorList validateApplication(PTFlowBean flowBean, RulesInformation rulesInformation, String flowModeId, String stateId) {
        PatentApplication patentApplication = flowBeanFactory.convertTo(flowBean);

        List<Section> sections = sectionViewConfiguration.getSortedSections(flowModeId, stateId);
        rulesInformation.getCustomObjects().put("sections", sections);
        return applicationService.validateApplication(MODULE,patentApplication, rulesInformation);
    }

    public boolean redirectToPortal(PTFlowBean flowBean){
        boolean submitToPortal = configurationServiceDelegator.isServiceEnabled(AvailableServices.SUBMIT_PORTAL.value());
        return submitToPortal;
    }

    public String getRedirectUrl(String provisionalId){
        String url = configurationServiceDelegator.getApplicationManagementUrl(MODULE, "application.management.url", flowScopeDetails.getFlowModeId());
        return url + provisionalId;
    }
}
