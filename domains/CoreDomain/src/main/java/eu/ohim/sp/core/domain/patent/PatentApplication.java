package eu.ohim.sp.core.domain.patent;

import eu.ohim.sp.core.domain.application.Entitlement;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.PaymentFee;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Raya
 * 11.04.2019
 */
public class PatentApplication implements Serializable, IPApplication {

    private String applicationReferenceNumber;

    private Date applicationReferenceDate;

    private String applicationType;

    private String applicationKind;

    private String applicationFilingNumber;

    private Date applicationFilingDate;

    private String user;
    private String userEmail;

    private Patent patent;

    private List<Patent> spcPatents;

    private List<Fee> fees;

    private List<PaymentFee> payments;

    private List<Signatory> signatures;

    private boolean anticipationOfPublication;

    private boolean defermentOfPublication;
    private List<AttachedDocument> defermentOfPublicationDocuments;

    private boolean licenceAvailability;
    private boolean inventorsAreReal;
    private List<AttachedDocument> inventorsAreRealDocuments;
    private boolean smallCompany;
    private List<AttachedDocument> smallCompanyDocuments;

    private boolean epoDecisionCopy;
    private boolean epoTransferChangeForm;

    private boolean examinationRequested;

    private boolean classifiedForNationalSecurity;
    private boolean classifiedForDefense;

    private Entitlement entitlement;

    private List<AttachedDocument> documentIncluded;
    private Boolean trueDocumentsIndicator;

    private String comment;

    private Boolean applicantsImportedFromTemplate;

    private Boolean esignDocDeclaration;

    private TechnicalQuestionnaire technicalQuestionnaire;

    private Boolean certificateRequestedIndicator;

    @Override
    public List<AttachedDocument> getDocuments() {
        return documentIncluded;
    }

    @Override
    public void setDocuments(List<AttachedDocument> documents) {
        this.documentIncluded = documents;
    }

    @Override
    public String getApplicationType() {
        return applicationType;
    }

    @Override
    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    @Override
    public String getApplicationFilingNumber() {
        return applicationFilingNumber;
    }

    @Override
    public void setApplicationFilingNumber(String applicationFilingNumber) {
        this.applicationFilingNumber = applicationFilingNumber;
    }

    @Override
    public Date getApplicationFilingDate() {
        return applicationFilingDate;
    }

    @Override
    public void setApplicationFilingDate(Date applicationFilingDate) {
        this.applicationFilingDate = applicationFilingDate;
    }

    public List<Fee> getFees() {
        return fees;
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }

    public List<PaymentFee> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentFee> payments) {
        this.payments = payments;
    }

    public List<Signatory> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<Signatory> signatures) {
        this.signatures = signatures;
    }

    public boolean isAnticipationOfPublication() {
        return anticipationOfPublication;
    }

    public void setAnticipationOfPublication(boolean anticipationOfPublication) {
        this.anticipationOfPublication = anticipationOfPublication;
    }

    public boolean isDefermentOfPublication() {
        return defermentOfPublication;
    }

    public void setDefermentOfPublication(boolean defermentOfPublication) {
        this.defermentOfPublication = defermentOfPublication;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }

    public Patent getPatent() {
        return patent;
    }

    public void setPatent(Patent patent) {
        this.patent = patent;
    }

    public List<Patent> getSpcPatents() {
        return spcPatents;
    }

    public void setSpcPatents(List<Patent> spcPatents) {
        this.spcPatents = spcPatents;
    }

    public boolean isLicenceAvailability() {
        return licenceAvailability;
    }

    public void setLicenceAvailability(boolean licenceAvailability) {
        this.licenceAvailability = licenceAvailability;
    }

    public boolean isInventorsAreReal() {
        return inventorsAreReal;
    }

    public void setInventorsAreReal(boolean inventorsAreReal) {
        this.inventorsAreReal = inventorsAreReal;
    }

    public boolean isSmallCompany() {
        return smallCompany;
    }

    public void setSmallCompany(boolean smallCompany) {
        this.smallCompany = smallCompany;
    }

    public boolean isExaminationRequested() {
        return examinationRequested;
    }

    public void setExaminationRequested(boolean examinationRequested) {
        this.examinationRequested = examinationRequested;
    }

    public boolean isClassifiedForNationalSecurity() {
        return classifiedForNationalSecurity;
    }

    public void setClassifiedForNationalSecurity(boolean classifiedForNationalSecurity) {
        this.classifiedForNationalSecurity = classifiedForNationalSecurity;
    }

    public boolean isClassifiedForDefense() {
        return classifiedForDefense;
    }

    public void setClassifiedForDefense(boolean classifiedForDefense) {
        this.classifiedForDefense = classifiedForDefense;
    }

    public Entitlement getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(Entitlement entitlement) {
        this.entitlement = entitlement;
    }

    public String getApplicationNumber() {
        return (patent!=null?patent.getApplicationNumber():null);
    }

    public void setApplicationNumber(String applicationNumber) {
        if (patent==null) {
            patent = new Patent();
        }
        patent.setApplicationNumber(applicationNumber);
    }
    public Boolean getTrueDocumentsIndicator() {
        return trueDocumentsIndicator;
    }

    public void setTrueDocumentsIndicator(Boolean trueDocumentsIndicator) {
        this.trueDocumentsIndicator = trueDocumentsIndicator;
    }

    public List<AttachedDocument> getInventorsAreRealDocuments() {
        return inventorsAreRealDocuments;
    }

    public void setInventorsAreRealDocuments(List<AttachedDocument> inventorsAreRealDocuments) {
        this.inventorsAreRealDocuments = inventorsAreRealDocuments;
    }

    public List<AttachedDocument> getSmallCompanyDocuments() {
        return smallCompanyDocuments;
    }

    public void setSmallCompanyDocuments(List<AttachedDocument> smallCompanyDocuments) {
        this.smallCompanyDocuments = smallCompanyDocuments;
    }

    public List<AttachedDocument> getDefermentOfPublicationDocuments() {
        return defermentOfPublicationDocuments;
    }

    public void setDefermentOfPublicationDocuments(List<AttachedDocument> defermentOfPublicationDocuments) {
        this.defermentOfPublicationDocuments = defermentOfPublicationDocuments;
    }

    public String getApplicationKind() {
        return applicationKind;
    }

    public void setApplicationKind(String applicationKind) {
        this.applicationKind = applicationKind;
    }

    public Boolean getApplicantsImportedFromTemplate() {
        return applicantsImportedFromTemplate;
    }

    public void setApplicantsImportedFromTemplate(Boolean applicantsImportedFromTemplate) {
        this.applicantsImportedFromTemplate = applicantsImportedFromTemplate;
    }

    public String getApplicationReferenceNumber() {
        return applicationReferenceNumber;
    }

    public void setApplicationReferenceNumber(String applicationReferenceNumber) {
        this.applicationReferenceNumber = applicationReferenceNumber;
    }

    public Date getApplicationReferenceDate() {
        return applicationReferenceDate;
    }

    public void setApplicationReferenceDate(Date applicationReferenceDate) {
        this.applicationReferenceDate = applicationReferenceDate;
    }

    public boolean isEpoDecisionCopy() {
        return epoDecisionCopy;
    }

    public void setEpoDecisionCopy(boolean epoDecisionCopy) {
        this.epoDecisionCopy = epoDecisionCopy;
    }

    public boolean isEpoTransferChangeForm() {
        return epoTransferChangeForm;
    }

    public void setEpoTransferChangeForm(boolean epoTransferChangeForm) {
        this.epoTransferChangeForm = epoTransferChangeForm;
    }

    public Boolean getEsignDocDeclaration() {
        return esignDocDeclaration;
    }

    public void setEsignDocDeclaration(Boolean esignDocDeclaration) {
        this.esignDocDeclaration = esignDocDeclaration;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public Boolean getElectronicCorrespondenceRequested() {
        if(patent != null && patent.getContactDetails() != null && patent.getContactDetails().size()>0){
            Optional<Boolean> optionalReduceResult = patent.getContactDetails().stream().map(c-> c.getElectronicCorrespondence()).reduce( (c1, c2) -> c1 != null && c1 && c2 != null && c2);
            if(optionalReduceResult.isPresent()){
                return optionalReduceResult.get();
            }
        }
        return false;
    }

    @Override
    public String getTitleApplication() {
        if(patent != null && patent.getPatentTitle() != null){
            return patent.getPatentTitle();
        }
        return null;
    }

    public TechnicalQuestionnaire getTechnicalQuestionnaire() {
        return technicalQuestionnaire;
    }

    public void setTechnicalQuestionnaire(TechnicalQuestionnaire technicalQuestionnaire) {
        this.technicalQuestionnaire = technicalQuestionnaire;
    }

    public Boolean getCertificateRequestedIndicator() {
        return certificateRequestedIndicator;
    }

    public void setCertificateRequestedIndicator(Boolean certificateRequestedIndicator) {
        this.certificateRequestedIndicator = certificateRequestedIndicator;
    }

    @Override
    public String getAppSubtype() {
        if(StringUtils.isNotEmpty(applicationKind)){
            return applicationKind;
        }
        return null;
    }
}
