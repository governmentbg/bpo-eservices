package eu.ohim.sp.core.domain.patent;

import eu.ohim.sp.core.domain.application.DivisionalApplicationDetails;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.contact.CorrespondenceKind;
import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Inventor;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Raya
 * 11.04.2019
 */
public class Patent extends Id implements Serializable {

    private static String NUM_REGEX = "\\d+";

    private String externalReferenceNumber;

    private Date receivingOfficeDate;

    private String receivingOffice;

    private Date applicationDate;

    private Date entitlementDate;

    private String applicationNumber;

    private Date registrationDate;

    private String registrationNumber;

    private String registrationOffice;

    private Date expirationDate;

    private Date publicationDate;

    private Date registrationPublicationDate;

    private String publicationNumber;

    private String applicationLanguage;

    private String secondLanguage;

    private String filingUser;

    private String groupStatusCode;

    private String currentStatusCode;

    private Date currentStatusDate;

    private Boolean patentValidated;

    private String patentPublicationsInfo;

    private String patentTitle;
    private String patentTitleSecondLang;
    private String patentAbstract;
    private String patentAbstractSecondLang;

    private String pagesCount;
    private String claimsCount;
    private String independentClaimsCount;
    private String drawingsCount;
    private String drawingNumber;

    private List<PatentView> patentViews;
    private List<AttachedDocument> patentDescriptions;
    private List<AttachedDocument> patentClaims;
    private List<AttachedDocument> patentDrawings;
    private List<AttachedDocument> sequencesListing;

    private List<Applicant> applicants;

    private List<Representative> representatives;

    private List<ContactDetails> contactDetails;

    private List<Inventor> inventors;

    private String correspondenceLanguage;

    private CorrespondenceKind correspondenceKind;

    private String reference;

    private List<DivisionalApplicationDetails> divisionalApplicationDetails;

    private List<PatentPriority> priorities;

    private List<PatentTransformation> transformationPriorities;

    private List<PCT> pcts;

    private List<ExhibitionPriority> exhibitions;

    private List<ParallelApplication> parallelApplications;

    private Boolean unpublished;

    private SVKind svKind;
    private String titleTransliteration;
    private String taxon;
    private String latinClassification;

    private RegKind regKind;
    private String firstPermissionBGNumber;
    private Date firstPermissionBGDate;
    private Date firstPermissionBGNotificationDate;
    private String firstPermissionEUNumber;
    private Date firstPermissionEUDate;

    private List<String> ipcClasses;

    public Date getReceivingOfficeDate() {
        return receivingOfficeDate;
    }

    public void setReceivingOfficeDate(Date receivingOfficeDate) {
        this.receivingOfficeDate = receivingOfficeDate;
    }

    public String getReceivingOffice() {
        return receivingOffice;
    }

    public void setReceivingOffice(String receivingOffice) {
        this.receivingOffice = receivingOffice;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Date getEntitlementDate() {
        return entitlementDate;
    }

    public void setEntitlementDate(Date entitlementDate) {
        this.entitlementDate = entitlementDate;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationOffice() {
        return registrationOffice;
    }

    public void setRegistrationOffice(String registrationOffice) {
        this.registrationOffice = registrationOffice;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getApplicationLanguage() {
        return applicationLanguage;
    }

    public void setApplicationLanguage(String applicationLanguage) {
        this.applicationLanguage = applicationLanguage;
    }

    public String getSecondLanguage() {
        return secondLanguage;
    }

    public void setSecondLanguage(String secondLanguage) {
        this.secondLanguage = secondLanguage;
    }

    public String getFilingUser() {
        return filingUser;
    }

    public void setFilingUser(String filingUser) {
        this.filingUser = filingUser;
    }

    public String getCurrentStatusCode() {
        return currentStatusCode;
    }

    public void setCurrentStatusCode(String currentStatusCode) {
        this.currentStatusCode = currentStatusCode;
    }

    public Date getCurrentStatusDate() {
        return currentStatusDate;
    }

    public void setCurrentStatusDate(Date currentStatusDate) {
        this.currentStatusDate = currentStatusDate;
    }

    public String getPatentTitle() {
        return patentTitle;
    }

    public void setPatentTitle(String patentTitle) {
        this.patentTitle = patentTitle;
    }

    public String getPatentTitleSecondLang() {
        return patentTitleSecondLang;
    }

    public void setPatentTitleSecondLang(String patentTitleSecondLang) {
        this.patentTitleSecondLang = patentTitleSecondLang;
    }

    public String getPatentAbstract() {
        return patentAbstract;
    }

    public void setPatentAbstract(String patentAbstract) {
        this.patentAbstract = patentAbstract;
    }

    public String getPatentAbstractSecondLang() {
        return patentAbstractSecondLang;
    }

    public void setPatentAbstractSecondLang(String patentAbstractSecondLang) {
        this.patentAbstractSecondLang = patentAbstractSecondLang;
    }

    public List<PatentView> getPatentViews() {
        return patentViews;
    }

    public void setPatentViews(List<PatentView> patentViews) {
        this.patentViews = patentViews;
    }

    public List<AttachedDocument> getPatentDescriptions() {
        return patentDescriptions;
    }

    public void setPatentDescriptions(List<AttachedDocument> patentDescriptions) {
        this.patentDescriptions = patentDescriptions;
    }

    public List<AttachedDocument> getPatentClaims() {
        return patentClaims;
    }

    public void setPatentClaims(List<AttachedDocument> patentClaims) {
        this.patentClaims = patentClaims;
    }

    public List<AttachedDocument> getPatentDrawings() {
        return patentDrawings;
    }

    public void setPatentDrawings(List<AttachedDocument> patentDrawings) {
        this.patentDrawings = patentDrawings;
    }

    public List<Applicant> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<Applicant> applicants) {
        this.applicants = applicants;
    }

    public List<Representative> getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(List<Representative> representatives) {
        this.representatives = representatives;
    }

    public List<ContactDetails> getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(List<ContactDetails> contactDetails) {
        this.contactDetails = contactDetails;
    }

    public List<Inventor> getInventors() {
        return inventors;
    }

    public void setInventors(List<Inventor> inventors) {
        this.inventors = inventors;
    }

    public String getCorrespondenceLanguage() {
        return correspondenceLanguage;
    }

    public void setCorrespondenceLanguage(String correspondenceLanguage) {
        this.correspondenceLanguage = correspondenceLanguage;
    }

    public CorrespondenceKind getCorrespondenceKind() {
        return correspondenceKind;
    }

    public void setCorrespondenceKind(CorrespondenceKind correspondenceKind) {
        this.correspondenceKind = correspondenceKind;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<DivisionalApplicationDetails> getDivisionalApplicationDetails() {
        return divisionalApplicationDetails;
    }

    public void setDivisionalApplicationDetails(List<DivisionalApplicationDetails> divisionalApplicationDetails) {
        this.divisionalApplicationDetails = divisionalApplicationDetails;
    }

    public List<PatentPriority> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<PatentPriority> priorities) {
        this.priorities = priorities;
    }

    public List<PatentTransformation> getTransformationPriorities() {
        return transformationPriorities;
    }

    public void setTransformationPriorities(List<PatentTransformation> transformationPriorities) {
        this.transformationPriorities = transformationPriorities;
    }

    public List<ExhibitionPriority> getExhibitions() {
        return exhibitions;
    }

    public void setExhibitions(List<ExhibitionPriority> exhibitions) {
        this.exhibitions = exhibitions;
    }

    public List<ParallelApplication> getParallelApplications() {
        return parallelApplications;
    }

    public void setParallelApplications(List<ParallelApplication> parallelApplications) {
        this.parallelApplications = parallelApplications;
    }

    public List<PCT> getPcts() {
        return pcts;
    }

    public void setPcts(List<PCT> pcts) {
        this.pcts = pcts;
    }

    public String getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(String pagesCount) {
        this.pagesCount = pagesCount;
    }

    public String getClaimsCount() {
        return claimsCount;
    }

    public void setClaimsCount(String claimsCount) {
        this.claimsCount = claimsCount;
    }

    public String getDrawingsCount() {
        return drawingsCount;
    }

    public void setDrawingsCount(String drawingsCount) {
        this.drawingsCount = drawingsCount;
    }

    public String getDrawingNumber() {
        return drawingNumber;
    }

    public void setDrawingNumber(String drawingNumber) {
        this.drawingNumber = drawingNumber;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublicationNumber() {
        return publicationNumber;
    }

    public void setPublicationNumber(String publicationNumber) {
        this.publicationNumber = publicationNumber;
    }

    public String getIndependentClaimsCount() {
        return independentClaimsCount;
    }

    public String getExternalReferenceNumber() {
        return externalReferenceNumber;
    }

    public void setExternalReferenceNumber(String externalReferenceNumber) {
        this.externalReferenceNumber = externalReferenceNumber;
    }

    public void setIndependentClaimsCount(String independentClaimsCount) {
        this.independentClaimsCount = independentClaimsCount;
    }

    public int getClaimsCountInt(){
        if(claimsCount != null && claimsCount.matches(NUM_REGEX)){
            return Integer.parseInt(claimsCount);
        } else {
            return 0;
        }
    }

    public int getIndependentClaimsCountInt(){
        if(independentClaimsCount != null && independentClaimsCount.matches(NUM_REGEX)){
            return Integer.parseInt(independentClaimsCount);
        } else {
            return 0;
        }
    }

    public int getDrawingNumberInt(){
        if(drawingNumber != null && drawingNumber.matches(NUM_REGEX)){
            return Integer.parseInt(drawingNumber);
        } else {
            return 0;
        }
    }

    public int getDrawingsCountInt(){
        if(drawingsCount != null && drawingsCount.matches(NUM_REGEX)){
            return Integer.parseInt(drawingsCount);
        } else {
            return 0;
        }
    }

    public int getPagesCountInt(){
        if(pagesCount != null && pagesCount.matches(NUM_REGEX)){
            return Integer.parseInt(pagesCount);
        } else {
            return 0;
        }
    }

    public Date getRegistrationPublicationDate() {
        return registrationPublicationDate;
    }

    public void setRegistrationPublicationDate(Date registrationPublicationDate) {
        this.registrationPublicationDate = registrationPublicationDate;
    }

    public Boolean getPatentValidated() {
        return patentValidated;
    }

    public void setPatentValidated(Boolean patentValidated) {
        this.patentValidated = patentValidated;
    }

    public String getPatentPublicationsInfo() {
        return patentPublicationsInfo;
    }

    public void setPatentPublicationsInfo(String patentPublicationsInfo) {
        this.patentPublicationsInfo = patentPublicationsInfo;
    }

    public String getGroupStatusCode() {
        return groupStatusCode;
    }

    public void setGroupStatusCode(String groupStatusCode) {
        this.groupStatusCode = groupStatusCode;
    }

    public Boolean getUnpublished() {
        return unpublished;
    }

    public void setUnpublished(Boolean unpublished) {
        this.unpublished = unpublished;
    }

    public SVKind getSvKind() {
        return svKind;
    }

    public void setSvKind(SVKind svKind) {
        this.svKind = svKind;
    }

    public String getTitleTransliteration() {
        return titleTransliteration;
    }

    public void setTitleTransliteration(String titleTransliteration) {
        this.titleTransliteration = titleTransliteration;
    }

    public String getTaxon() {
        return taxon;
    }

    public void setTaxon(String taxon) {
        this.taxon = taxon;
    }

    public String getLatinClassification() {
        return latinClassification;
    }

    public void setLatinClassification(String latinClassification) {
        this.latinClassification = latinClassification;
    }

    public RegKind getRegKind() {
        return regKind;
    }

    public void setRegKind(RegKind regKind) {
        this.regKind = regKind;
    }

    public String getFirstPermissionBGNumber() {
        return firstPermissionBGNumber;
    }

    public void setFirstPermissionBGNumber(String firstPermissionBGNumber) {
        this.firstPermissionBGNumber = firstPermissionBGNumber;
    }

    public Date getFirstPermissionBGDate() {
        return firstPermissionBGDate;
    }

    public void setFirstPermissionBGDate(Date firstPermissionBGDate) {
        this.firstPermissionBGDate = firstPermissionBGDate;
    }

    public Date getFirstPermissionBGNotificationDate() {
        return firstPermissionBGNotificationDate;
    }

    public void setFirstPermissionBGNotificationDate(Date firstPermissionBGNotificationDate) {
        this.firstPermissionBGNotificationDate = firstPermissionBGNotificationDate;
    }

    public String getFirstPermissionEUNumber() {
        return firstPermissionEUNumber;
    }

    public void setFirstPermissionEUNumber(String firstPermissionEUNumber) {
        this.firstPermissionEUNumber = firstPermissionEUNumber;
    }

    public Date getFirstPermissionEUDate() {
        return firstPermissionEUDate;
    }

    public void setFirstPermissionEUDate(Date firstPermissionEUDate) {
        this.firstPermissionEUDate = firstPermissionEUDate;
    }

    public List<AttachedDocument> getSequencesListing() {
        return sequencesListing;
    }

    public void setSequencesListing(List<AttachedDocument> sequencesListing) {
        this.sequencesListing = sequencesListing;
    }

    public List<String> getIpcClasses() {
        return ipcClasses;
    }

    public void setIpcClasses(List<String> ipcClasses) {
        this.ipcClasses = ipcClasses;
    }
}
