package eu.ohim.sp.ptefiling.ui.adapter;

import eu.ohim.sp.common.ui.adapter.*;
import eu.ohim.sp.common.ui.adapter.design.ContactDetailsFactory;
import eu.ohim.sp.common.ui.adapter.patent.*;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.form.patent.PatentApplicationKind;
import eu.ohim.sp.core.domain.patent.PTeServiceApplication;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.domain.patent.PatentApplication;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.MatchedFee;
import eu.ohim.sp.core.domain.payment.PaymentFee;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBeanImpl;
import eu.ohim.sp.ptefiling.ui.domain.PTMainForm;
import eu.ohim.sp.ptefiling.ui.service.FormUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Raya
 * 09.11.2018
 */
@Component
public class PTFlowBeanFactory implements UIFactory<PatentApplication, PTFlowBean> {

    @Autowired
    private FeesFactory feesFactory;

    @Autowired
    private PatentFactory patentFactory;

    @Autowired
    private PTDivisionalApplicationFactory ptDivisionalApplicationFactory;

    @Autowired
    private PTParallelApplicationFactory ptParallelApplicationFactory;

    @Autowired
    private PCTFactory pctFactory;

    @Autowired
    private ContactDetailsFactory contactDetailsFactory;

    @Autowired
    private PTEntitlementFactory ptEntitlementFactory;

    @Autowired
    private PTPriorityFactory ptPriorityFactory;

    @Autowired
    private ExhibitionPriorityFactory exhibitionPriorityFactory;

    @Autowired
    private PTTransformationFactory ptTransformationFactory;

    @Autowired
    private ApplicantFactory applicantFactory;

    @Autowired
    private RepresentativeFactory representativeFactory;

    @Autowired
    private InventorFactory inventorFactory;

    @Autowired
    private SignatoryFactory signatoryFactory;

    @Autowired
    private ListAttachedDocumentFactory listAttachedDocumentFactory;

    @Autowired
    private PaymentFactory paymentFactory;

    @Autowired
    private AttachedDocumentFactory attachedDocumentFactory;

    @Autowired
    private FormUtil formUtil;

    @Autowired
    private PatentViewFactory patentViewFactory;

    @Autowired
    private TechnicalQuestionnaireFactory technicalQuestionnaireFactory;

    @Autowired
    private ESPatentFactory esPatentFactory;

    @Value("${sp.office}")
    private String office;

    @Override
    public PatentApplication convertTo(PTFlowBean form) {

        PatentApplication core = new PatentApplication();

        core.setUser(form.getCurrentUserName());
        core.setUserEmail(form.getCurrentUserEmail());

        List<AttachedDocument> documents = new ArrayList<AttachedDocument>();
        core.setDocuments(documents);
        if (form == null) {
            return core;
        }

        core.setEsignDocDeclaration(form.getEsignDocDeclaration());
        core.setApplicationFilingNumber(form.getIdreserventity());
        core.setApplicationType(formUtil.getType());
        core.setApplicantsImportedFromTemplate(form.getApplicantsImportedFromTemplate());

        core.setPatent(patentFactory.convertTo(form.getPatent()));
        core.setTechnicalQuestionnaire(technicalQuestionnaireFactory.convertTo(form.getTechnicalQuestionnaireForm()));
        core.setCertificateRequestedIndicator(form.getCertificateRequestedIndicator());

        if(form.getMainForm() != null){
            PTMainForm mainForm = form.getMainForm();
            mainFormToCore(mainForm, core);
        }

        core.getPatent().setApplicationLanguage(form.getFirstLang());
        core.getPatent().setSecondLanguage(form.getSecLang());
        core.getPatent().setReceivingOffice(office);

        if (form.getSpcPatents() != null){
            core.setSpcPatents(form.getSpcPatents().stream().map(e -> esPatentFactory.convertTo(e)).collect(Collectors.toList()));
        }

        if(form.getDivisionalApplications() != null){
            core.getPatent().setDivisionalApplicationDetails(form.getDivisionalApplications().stream().map(e -> ptDivisionalApplicationFactory.convertTo(e)).collect(Collectors.toList()));
        }

        if(form.getPriorities() != null){
            core.getPatent().setPriorities(form.getPriorities().stream().map(e -> ptPriorityFactory.convertTo(e)).collect(Collectors.toList()));
        }

        if(form.getTransformations() != null){
            core.getPatent().setTransformationPriorities(form.getTransformations().stream().map(e -> ptTransformationFactory.convertTo(e)).collect(Collectors.toList()));
        }

        if(form.getParallelApplications() != null){
            core.getPatent().setParallelApplications(form.getParallelApplications().stream().map(e -> ptParallelApplicationFactory.convertTo(e)).collect(Collectors.toList()));
        }

        if(form.getPcts() != null){
            core.getPatent().setPcts(form.getPcts().stream().map(e -> pctFactory.convertTo(e)).collect(Collectors.toList()));
        }

        if(form.getExhibitions() != null){
            core.getPatent().setExhibitions(form.getExhibitions().stream().map(e -> exhibitionPriorityFactory.convertTo(e)).collect(Collectors.toList()));
        }

        if(form.getApplicants() != null){
            core.getPatent().setApplicants(form.getApplicants().stream().map(e -> applicantFactory.convertTo(e)).collect(Collectors.toList()));
        }

        if(form.getRepresentatives() != null){
            core.getPatent().setRepresentatives(form.getRepresentatives().stream().map(e -> representativeFactory.convertTo(e)).collect(Collectors.toList()));
        }

        if(form.getInventors() != null){
            core.getPatent().setInventors(form.getInventors().stream().map(e -> inventorFactory.convertTo(e)).collect(Collectors.toList()));
        }

        core.setDocuments(new ArrayList<AttachedDocument>());
        if(!form.getOtherAttachments().getStoredFiles().isEmpty()){
            core.getDocuments().addAll(form.getOtherAttachments().getStoredFiles().stream().map(e-> attachedDocumentFactory.convertTo(e)).collect(Collectors.toList()));

            core.setTrueDocumentsIndicator(form.getTrueDocumentsIndicator());
        }

        core.setComment(form.getComment());

        if (form.getFeesForm() != null) {
            core.setFees(feesFactory.convertFrom(form.getFeesForm()));
        }

        core.setPayments(new ArrayList<PaymentFee>());

        PaymentFee paymentFee = paymentFactory.convertTo(form.getPaymentForm());
        if(core.getFees() != null && !core.getFees().isEmpty()){
            List <MatchedFee> matchedFees = new ArrayList<MatchedFee>();
            for (Fee fee: core.getFees()){
                MatchedFee matchedFee = new MatchedFee();
                matchedFee.setFee(fee);
                matchedFees.add(matchedFee);
            }
            paymentFee.setFees(matchedFees);
        }
        core.getPayments().add(paymentFee);

        if(form.getSignatures() != null){
            core.setSignatures(form.getSignatures().stream().map(e -> signatoryFactory.convertTo(e)).collect(Collectors.toList()));
        }

        return core;
    }

    private void mainFormToCore(PTMainForm mainForm, PatentApplication core){
        core.setApplicationKind(mainForm.getApplicationKind() != null ? mainForm.getApplicationKind().getValue(): null);

        core.setAnticipationOfPublication(mainForm.isAnticipationOfPublication());
        core.setDefermentOfPublication(mainForm.isPostponementOfPublication());
        if(mainForm.isPostponementOfPublication() && mainForm.getPostponementOfPublicationFiles() != null){
            List<AttachedDocument> documents = listAttachedDocumentFactory.convertTo(mainForm.getPostponementOfPublicationFiles());
            core.setDefermentOfPublicationDocuments(documents);
        }

        core.setExaminationRequested(mainForm.isExaminationRequested());
        core.setSmallCompany(mainForm.isSmallCompany());
        if(mainForm.isSmallCompany() && mainForm.getSmallCompanyFiles() != null){
            List<AttachedDocument> documents = listAttachedDocumentFactory.convertTo(mainForm.getSmallCompanyFiles());
            core.setSmallCompanyDocuments(documents);
        }
        core.setEpoDecisionCopy(mainForm.isEpoDecisionCopy());
        core.setEpoTransferChangeForm(mainForm.isEpoTransferChangeForm());

        core.setLicenceAvailability(mainForm.isLicenceAvailability());
        core.setInventorsAreReal(mainForm.isInventorsAreReal());
        if(mainForm.isInventorsAreReal() && mainForm.getInventorsAreRealFiles() != null){
            List<AttachedDocument> documents = listAttachedDocumentFactory.convertTo(mainForm.getInventorsAreRealFiles());
            core.setInventorsAreRealDocuments(documents);
        }

        core.setClassifiedForDefense(mainForm.isClassifiedForDefense());
        core.setClassifiedForNationalSecurity(mainForm.isClassifiedForNationalSecurity());

        core.setEntitlement(ptEntitlementFactory.convertTo(mainForm.getEntitlement()));

        core.getPatent().setContactDetails(mainForm.getCorrespondanceAddresses().stream().map(e -> contactDetailsFactory.convertTo(e.getCorrespondenceAddressForm())).collect(Collectors.toList()));
    }

    @Override
    public PTFlowBean convertFrom(PatentApplication core) {
        PTFlowBean form = new PTFlowBeanImpl();

        if(core == null){
            return form;
        }

        form.setEsignDocDeclaration(core.getEsignDocDeclaration());
        if(core.getPatent() != null) {
            Patent patentCore = core.getPatent();
            form.setFirstLang(patentCore.getApplicationLanguage());
            form.setSecLang(patentCore.getSecondLanguage());

            if(patentCore.getDivisionalApplicationDetails() != null){
                patentCore.getDivisionalApplicationDetails().forEach(
                        e -> form.addObject(ptDivisionalApplicationFactory.convertFrom(e)));
            }

            if(patentCore.getPriorities() != null){
                patentCore.getPriorities().forEach(
                        e -> form.addObject(ptPriorityFactory.convertFrom(e)));
            }

            if(patentCore.getParallelApplications() != null){
                patentCore.getParallelApplications().forEach(
                    e -> form.addObject(ptParallelApplicationFactory.convertFrom(e))
                );
            }

            if(patentCore.getPcts() != null){
                patentCore.getPcts().forEach(
                    e -> form.addObject(pctFactory.convertFrom(e))
                );
            }

            if(patentCore.getTransformationPriorities() != null){
                patentCore.getTransformationPriorities().forEach(
                        e -> form.addObject(ptTransformationFactory.convertFrom(e)));
            }

            if(patentCore.getExhibitions() != null){
                patentCore.getExhibitions().forEach(
                    e -> form.addObject(exhibitionPriorityFactory.convertFrom(e))
                );
            }

            if(patentCore.getRepresentatives() != null) {
                patentCore.getRepresentatives().forEach( e -> form.addObject(representativeFactory.convertFrom(e)));
            }

            if(patentCore.getApplicants() != null) {
                patentCore.getApplicants().forEach( e -> form.addObject(applicantFactory.convertFrom(e)));
            }

            if(patentCore.getInventors() != null) {
                patentCore.getInventors().forEach(e -> form.addObject(inventorFactory.convertFrom(e)));
            }

        }
        form.setIdreserventity(core.getApplicationFilingNumber());
        form.setApplicantsImportedFromTemplate(core.getApplicantsImportedFromTemplate());

        coreToMainForm(core, form);

        form.setPatent(patentFactory.convertFrom(core.getPatent()));
        form.setTechnicalQuestionnaireForm(technicalQuestionnaireFactory.convertFrom(core.getTechnicalQuestionnaire()));
        form.setCertificateRequestedIndicator(core.getCertificateRequestedIndicator());
        form.clearPatentViews();

        if(core.getSpcPatents() != null){
            core.getSpcPatents().stream().forEach(e -> form.addObject(esPatentFactory.convertFrom(e)));
        }

        if(core.getPatent().getPatentViews() != null){
            core.getPatent().getPatentViews().stream().forEach(e -> form.addObject(patentViewFactory.convertFrom(e)));
        }

        if(CollectionUtils.isNotEmpty(core.getDocuments())) {
            form.setOtherAttachments(listAttachedDocumentFactory.convertFrom(core.getDocuments()));
            form.setTrueDocumentsIndicator(core.getTrueDocumentsIndicator());
        }

        form.setComment(core.getComment());

        if(core.getFees() != null) {
            form.setFeesForm(feesFactory.convertTo(core.getFees()));
        }

        coreToPaymentForm(core.getPayments(), form);

        if(core.getSignatures() != null){
            core.getSignatures().forEach(e -> form.addObject(signatoryFactory.convertFrom(e)));
        }

        return form;
    }

    private void coreToMainForm(PatentApplication core, PTFlowBean flowBean){
        PTMainForm mainForm = flowBean.getMainForm();

        PatentApplicationKind applicationKind = PatentApplicationKind.fromValue(core.getApplicationKind());
        if(applicationKind != null){
            mainForm.setApplicationKind(applicationKind);
        }

        mainForm.setAnticipationOfPublication(core.isAnticipationOfPublication());
        mainForm.setPostponementOfPublication(core.isDefermentOfPublication());
        if(core.getDefermentOfPublicationDocuments() != null){
            mainForm.setPostponementOfPublicationFiles(listAttachedDocumentFactory.convertFrom(core.getDefermentOfPublicationDocuments()));
        }

        mainForm.setSmallCompany(core.isSmallCompany());
        if(core.getSmallCompanyDocuments() != null){
            mainForm.setSmallCompanyFiles(listAttachedDocumentFactory.convertFrom(core.getSmallCompanyDocuments()));
        }
        mainForm.setEpoDecisionCopy(core.isEpoDecisionCopy());
        mainForm.setEpoTransferChangeForm(core.isEpoTransferChangeForm());

        mainForm.setInventorsAreReal(core.isInventorsAreReal());
        if(core.getInventorsAreRealDocuments() != null){
            mainForm.setInventorsAreRealFiles(listAttachedDocumentFactory.convertFrom(core.getInventorsAreRealDocuments()));
        }
        mainForm.setExaminationRequested(core.isExaminationRequested());
        mainForm.setLicenceAvailability(core.isLicenceAvailability());

        mainForm.setClassifiedForDefense(core.isClassifiedForDefense());
        mainForm.setClassifiedForNationalSecurity(core.isClassifiedForNationalSecurity());

        mainForm.setEntitlement(ptEntitlementFactory.convertFrom(core.getEntitlement()));

        if(core.getPatent()!= null && core.getPatent().getContactDetails() != null) {
            core.getPatent().getContactDetails().stream().forEach(e -> {
                ApplicationCAForm ca = new ApplicationCAForm();
                ca.setCorrespondenceAddressForm(contactDetailsFactory.convertFrom(e));
                flowBean.addObject(ca);
            });
        }
    }


    private void coreToPaymentForm(List<PaymentFee> payments, PTFlowBean form) {
        if (CollectionUtils.isNotEmpty(payments)) {
            for (PaymentFee payment : payments) {
                form.setPaymentForm(paymentFactory.convertFrom(payment));
                break;
            }
        }
    }
}
