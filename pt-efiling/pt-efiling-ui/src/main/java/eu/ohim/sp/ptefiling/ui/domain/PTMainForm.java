package eu.ohim.sp.ptefiling.ui.domain;

import eu.ohim.sp.common.ui.form.MainForm;
import eu.ohim.sp.common.ui.form.patent.PTEntitlementForm;
import eu.ohim.sp.common.ui.form.patent.PatentApplicationKind;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;

import java.util.List;

/**
 * Created by Raya
 * 09.04.2019
 */
public class PTMainForm extends MainForm {

    private boolean anticipationOfPublication;
    private boolean postponementOfPublication;
    private FileWrapper postponementOfPublicationFiles;

    private boolean licenceAvailability;

    private boolean inventorsAreReal;
    private FileWrapper inventorsAreRealFiles;

    private boolean smallCompany;
    private FileWrapper smallCompanyFiles;

    private boolean epoDecisionCopy;

    private boolean epoTransferChangeForm;

    private boolean examinationRequested;

    private boolean classifiedForNationalSecurity;
    private boolean classifiedForDefense;

    private PTEntitlementForm entitlement;
    private PatentApplicationKind applicationKind;

    /**
     * Helper variables that are used to create error Box.
     */
    private boolean patentSection;
    private boolean spcPatentSection;
    private boolean technicalQuestionnaireSection;
    private boolean certificateRequestedSection;
    private boolean personalDataSection;
    private boolean languageSection;
    private boolean claimSection;
    private boolean applicantDataSection;
    private boolean representativeDataSection;
    private boolean inventorDataSection;
    private boolean otherAttachments;
    private boolean signatureSection;
    private boolean entitlementSection;
    private boolean applicantionCADataSection;

    public PTMainForm(){
        entitlement = new PTEntitlementForm();
        inventorsAreRealFiles = new FileWrapper();
        smallCompanyFiles = new FileWrapper();
        postponementOfPublicationFiles = new FileWrapper();
        examinationRequested = true;
    }


    public boolean isAnticipationOfPublication() {
        return anticipationOfPublication;
    }

    public void setAnticipationOfPublication(boolean anticipationOfPublication) {
        this.anticipationOfPublication = anticipationOfPublication;
    }

    public boolean isPostponementOfPublication() {
        return postponementOfPublication;
    }

    public void setPostponementOfPublication(boolean postponementOfPublication) {
        this.postponementOfPublication = postponementOfPublication;
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

    public PTEntitlementForm getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(PTEntitlementForm entitlement) {
        this.entitlement = entitlement;
    }

    public boolean isPatentSection() {
        return patentSection;
    }

    public void setPatentSection(boolean patentSection) {
        this.patentSection = patentSection;
    }

    public boolean isSpcPatentSection() {
        return spcPatentSection;
    }

    public void setSpcPatentSection(boolean spcPatentSection) {
        this.spcPatentSection = spcPatentSection;
    }

    public boolean isPersonalDataSection() {
        return personalDataSection;
    }

    public void setPersonalDataSection(boolean personalDataSection) {
        this.personalDataSection = personalDataSection;
    }

    public boolean isLanguageSection() {
        return languageSection;
    }

    public void setLanguageSection(boolean languageSection) {
        this.languageSection = languageSection;
    }

    public boolean isClaimSection() {
        return claimSection;
    }

    public void setClaimSection(boolean claimSection) {
        this.claimSection = claimSection;
    }

    public boolean isApplicantDataSection() {
        return applicantDataSection;
    }

    public void setApplicantDataSection(boolean applicantDataSection) {
        this.applicantDataSection = applicantDataSection;
    }

    public boolean isRepresentativeDataSection() {
        return representativeDataSection;
    }

    public void setRepresentativeDataSection(boolean representativeDataSection) {
        this.representativeDataSection = representativeDataSection;
    }

    public boolean isInventorDataSection() {
        return inventorDataSection;
    }

    public void setInventorDataSection(boolean inventorDataSection) {
        this.inventorDataSection = inventorDataSection;
    }

    public boolean isOtherAttachments() {
        return otherAttachments;
    }

    public void setOtherAttachments(boolean otherAttachments) {
        this.otherAttachments = otherAttachments;
    }

    public boolean isSignatureSection() {
        return signatureSection;
    }

    public void setSignatureSection(boolean signatureSection) {
        this.signatureSection = signatureSection;
    }

    public boolean isEntitlementSection() {
        return entitlementSection;
    }

    public void setEntitlementSection(boolean entitlementSection) {
        this.entitlementSection = entitlementSection;
    }

    public FileWrapper getInventorsAreRealFiles() {
        return inventorsAreRealFiles;
    }

    public void setInventorsAreRealFiles(FileWrapper inventorsAreRealFiles) {
        this.inventorsAreRealFiles = inventorsAreRealFiles;
    }

    public FileWrapper getSmallCompanyFiles() {
        return smallCompanyFiles;
    }

    public void setSmallCompanyFiles(FileWrapper smallCompanyFiles) {
        this.smallCompanyFiles = smallCompanyFiles;
    }

    public boolean isApplicantionCADataSection() {
        return applicantionCADataSection;
    }

    public void setApplicantionCADataSection(boolean applicantionCADataSection) {
        this.applicantionCADataSection = applicantionCADataSection;
    }

    public FileWrapper getPostponementOfPublicationFiles() {
        return postponementOfPublicationFiles;
    }

    public void setPostponementOfPublicationFiles(FileWrapper postponementOfPublicationFiles) {
        this.postponementOfPublicationFiles = postponementOfPublicationFiles;
    }

    public PatentApplicationKind getApplicationKind() {
        return applicationKind;
    }

    public void setApplicationKind(PatentApplicationKind applicationKind) {
        this.applicationKind = applicationKind;
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

    public boolean isTechnicalQuestionnaireSection() {
        return technicalQuestionnaireSection;
    }

    public void setTechnicalQuestionnaireSection(boolean technicalQuestionnaireSection) {
        this.technicalQuestionnaireSection = technicalQuestionnaireSection;
    }

    public boolean isCertificateRequestedSection() {
        return certificateRequestedSection;
    }

    public void setCertificateRequestedSection(boolean certificateRequestedSection) {
        this.certificateRequestedSection = certificateRequestedSection;
    }
}
