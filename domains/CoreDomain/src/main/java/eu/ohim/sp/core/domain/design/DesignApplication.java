/*
 * CoreDomain:: DesignApplication 01/10/13 17:01 KARALCH $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */

package eu.ohim.sp.core.domain.design;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.application.Entitlement;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.contact.CorrespondenceKind;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.PaymentFee;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import org.apache.commons.lang.StringUtils;

public class DesignApplication implements IPApplication, Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7500400951353521854L;

    /** The receiving date. */
    private Date receivingOfficeDate;

    /** The receiving office. */
    private String receivingOffice;

    private String receiptNumber;

    /** The application date. */
    private Date applicationDate;

    /** The application number. */
    private String applicationNumber;

    /** The application type. */
    private String applicationType;

    /** Flag for fast track design applications*/
    private Boolean fastTrack;

    /** The user that applies */
    private String user;

    private String userEmail;

    /** The publication date. */
    private Date publicationDate;

    /** The application language. */
    private String applicationLanguage;

    /** The second language. */
    private String secondLanguage;

    /** The priority claim later indicator. */
    private Boolean priorityClaimLaterIndicator;

    /** The priority claim later indicator. */
    private Boolean exhibitionPriorityClaimLaterIndicator;

    /** The application filing number. */
    private String applicationFilingNumber;

    /** The application filing date. */
    private Date applicationFilingDate;

    /** The trade mark. */
    private List<Design> designDetails;

    /** The fees. */
    private List<Fee> fees;

    /** The payments. */
    private List<PaymentFee> payments;

    /** The applicants. */
    private List<Applicant> applicants;

    /** The representatives. */
    private List<Representative> representatives;

    private List<AttachedDocument> documentIncluded;

    /** The signatures. */
    private List<Signatory> signatures;

    /** The contact details */
    private List<ContactDetails> contactDetails;

    private String reference;

    /** The correspondence language. */
    private String correspondenceLanguage;

    private CorrespondenceKind correspondenceKind;

    /** The designers. */
    private List<Designer> designers;

    private DesignDivisionalApplicationDetails divisionalApplicationDetails;

    /** The priorities. */
    private List<Priority> priorities;

    /** The exhibition priorities. */
    private List<ExhibitionPriority> exhibitionPriorities;

    private String productDescription;

    private String applicationVerbalElementsEn;

    private Entitlement entitlement;

    private boolean requestDeferredPublication;

    private boolean termsAndConditionsAcceptance;

    private List<String> attachments;
    
    private String regionalCentreOfficeCode;

    private String applicantURI;

    private String representativeURI;

    private String comment;

    private Boolean willPayOnline;

	public Date getReceivingOfficeDate() {
        return DateUtil.cloneDate(receivingOfficeDate);
    }

    public void setReceivingOfficeDate(Date receivingOfficeDate) {
        this.receivingOfficeDate = DateUtil.cloneDate(receivingOfficeDate);
    }

    public String getReceivingOffice() {
        return receivingOffice;
    }

    public void setReceivingOffice(String receivingOffice) {
        this.receivingOffice = receivingOffice;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Date getApplicationDate() {
        return DateUtil.cloneDate(applicationDate);
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = DateUtil.cloneDate(applicationDate);
    }

    @Override
    public String getApplicationNumber() {
        return applicationNumber;
    }

    @Override
    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
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
    public String getUser() {
        return user;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }

    public Date getPublicationDate() {
        return DateUtil.cloneDate(publicationDate);
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = DateUtil.cloneDate(publicationDate);
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

    public Boolean getPriorityClaimLaterIndicator() {
        return priorityClaimLaterIndicator;
    }

    public void setPriorityClaimLaterIndicator(Boolean priorityClaimLaterIndicator) {
        this.priorityClaimLaterIndicator = priorityClaimLaterIndicator;
    }

    public Boolean getExhibitionPriorityClaimLaterIndicator() {
        return exhibitionPriorityClaimLaterIndicator;
    }

    public void setExhibitionPriorityClaimLaterIndicator(Boolean exhibitionPriorityClaimLaterIndicator) {
        this.exhibitionPriorityClaimLaterIndicator = exhibitionPriorityClaimLaterIndicator;
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
        return DateUtil.cloneDate(applicationFilingDate);
    }

    @Override
    public void setApplicationFilingDate(Date applicationFilingDate) {
        this.applicationFilingDate = DateUtil.cloneDate(applicationFilingDate);
    }

    public List<Design> getDesignDetails() {
        return designDetails;
    }

    public void setDesignDetails(List<Design> designDetails) {
        this.designDetails = designDetails;
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

    @Override
    public List<AttachedDocument> getDocuments() {
        return documentIncluded;
    }

    @Override
    public void setDocuments(List<AttachedDocument> documents) {
        this.documentIncluded = documents;
    }

    public List<Signatory> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<Signatory> signatures) {
        this.signatures = signatures;
    }

    public List<ContactDetails> getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(List<ContactDetails> contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public List<Designer> getDesigners() {
        return designers;
    }

    public void setDesigners(List<Designer> designers) {
        this.designers = designers;
    }

    public DesignDivisionalApplicationDetails getDivisionalApplicationDetails() {
        return divisionalApplicationDetails;
    }

    public void setDivisionalApplicationDetails(DesignDivisionalApplicationDetails divisionalApplicationDetails) {
        this.divisionalApplicationDetails = divisionalApplicationDetails;
    }

    public List<Priority> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<Priority> priorities) {
        this.priorities = priorities;
    }

    public List<ExhibitionPriority> getExhibitionPriorities() {
        return exhibitionPriorities;
    }

    public void setExhibitionPriorities(List<ExhibitionPriority> exhibitionPriorities) {
        this.exhibitionPriorities = exhibitionPriorities;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Entitlement getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(Entitlement entitlement) {
        this.entitlement = entitlement;
    }

    public boolean isRequestDeferredPublication() {
        return requestDeferredPublication;
    }

    public void setRequestDeferredPublication(boolean requestDeferredPublication) {
        this.requestDeferredPublication = requestDeferredPublication;
    }

    public boolean isTermsAndConditionsAcceptance() {
        return termsAndConditionsAcceptance;
    }

    public void setTermsAndConditionsAcceptance(boolean termsAndConditionsAcceptance) {
        this.termsAndConditionsAcceptance = termsAndConditionsAcceptance;
    }

    /**
     * @return the attachments
     */
    public List<String> getAttachments() {
        return attachments;
    }

    /**
     * @param attachments the attachments to set
     */
    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }
    
    /**
	 * @return the regionalCentreOfficeCode
	 */
	public String getRegionalCentreOfficeCode() {
		return regionalCentreOfficeCode;
	}

	/**
	 * @param regionalCentreOfficeCode the regionalCentreOfficeCode to set
	 */
	public void setRegionalCentreOfficeCode(String regionalCentreOfficeCode) {
		this.regionalCentreOfficeCode = regionalCentreOfficeCode;
	}

    public String getApplicantURI() {
        return applicantURI;
    }

    public void setApplicantURI(String applicantURI) {
        this.applicantURI = applicantURI;
    }

    public String getRepresentativeURI() {
        return representativeURI;
    }

    public void setRepresentativeURI(String representativeURI) {
        this.representativeURI = representativeURI;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getApplicationVerbalElementsEn() {
        return applicationVerbalElementsEn;
    }

    public void setApplicationVerbalElementsEn(String applicationVerbalElementsEn) {
        this.applicationVerbalElementsEn = applicationVerbalElementsEn;
    }

    @Override
    public Boolean getElectronicCorrespondenceRequested() {
        if(contactDetails != null && contactDetails.size()>0){
            Optional<Boolean> optionalReduceResult = contactDetails.stream().map(c-> c.getElectronicCorrespondence()).reduce( (c1, c2) -> c1 != null && c1 && c2 != null && c2);
            if(optionalReduceResult.isPresent()){
                return optionalReduceResult.get();
            }
        }
        return false;
    }

    public Boolean getFastTrack() {
        return fastTrack;
    }

    public void setFastTrack(Boolean fastTrack) {
        this.fastTrack = fastTrack;
    }

    public Boolean getWillPayOnline() {
        return willPayOnline;
    }

    public void setWillPayOnline(Boolean willPayOnline) {
        this.willPayOnline = willPayOnline;
    }

    @Override
    public String getTitleApplication() {
	    if(designDetails != null && designDetails.size()>0){
            List<String> titleList = designDetails.stream().map( d-> generateDesignTitle(d)).
                    filter(part -> part != null).
                    collect(Collectors.toList());
            return StringUtils.join(titleList, "; ");
        }
        return null;
    }

    public String generateDesignTitle(Design design) {
        List<String> titles = new ArrayList<>();
        if(design.getProductIndications() != null){
            design.getProductIndications().stream()
                    .filter(pi -> pi.getClasses() != null && pi.getClasses().size()>0)
                    .forEach(
                            pi -> titles.add(pi.getClasses().get(0).getDescription()
                    ));
        }
        if(titles != null && titles.size()>0){
            return StringUtils.join(titles, ", ");
        }
        return null;
    }

    @Override
    public String getAppSubtype() {
        return null;
    }
}
