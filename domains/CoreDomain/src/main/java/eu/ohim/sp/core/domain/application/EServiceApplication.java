/*
 *  CoreDomain:: EServiceApplication 03/12/13 14:12 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

/**
 * 
 */
package eu.ohim.sp.core.domain.application;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.licence.Licence;
import eu.ohim.sp.core.domain.opposition.OppositionGround;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.PaymentFee;
import eu.ohim.sp.core.domain.person.*;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.trademark.GSHelperDetails;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.userdoc.Userdoc;
import eu.ohim.sp.core.domain.userdoc.UserdocRelationRestriction;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class EServiceApplication extends Id implements IPApplication, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4009532121047742099L;


	/** The application filing number. */
	private String applicationFilingNumber;
	
	/** The application filing date. */
	private Date applicationFilingDate;
	
	/** The application reference. */
    private String applicationReference;
    
    /** The application date. */
    private Date applicationDate;
    
    /** The application number. */
    private String applicationNumber;
    
    /** The application language. */
    private String applicationLanguage;

    /** The application type. */
    private String applicationType;
    
    /** The second language. */
    private String secondLanguage;
    
    /** The receiving office. */
    private String receivingOffice;
    
    /** The registration office. */
    private String registrationOffice;
    
    /** The comment. */
    private String comment;
	private Integer pagesCountAttachment;

	private Boolean holderIsInventor;
	private Boolean partialInvalidity;

	private Boolean processInitiatedBeforePublication;

	/** The applicants. */
    private List<Applicant> applicants;
    
    /** The representatives. */
    private List<Representative> representatives;
    
    /** The holders. */
    private List<Holder> holders;
    
    /** The assignees. */
    private List<Assignee> assignees;

	private List<Designer> designers;

    private List<OppositionGround> oppositionGrounds;

	private List<ContactDetails> contactDetails;

	private List<Licence> licences;

	private List<Appeal> appeals;

	private List<GSHelperDetails> gsHelpers;

	private SecurityMeasure securityMeasure;

    private String changeType;

	private List<PersonChange> personChanges;

	private Boolean esignDocDeclaration;

	private Userdoc selectedUserdoc;

	private UserdocRelationRestriction userdocRelationRestriction;

	private Boolean relateRequestToObject;
    
	/**
	 * @return the oppositionGrounds
	 */
	public List<OppositionGround> getOppositionGrounds() {
		return oppositionGrounds;
	}

	/**
	 * @param oppositionGrounds the oppositionGrounds to set
	 */
	public void setOppositionGrounds(List<OppositionGround> oppositionGrounds) {
		this.oppositionGrounds = oppositionGrounds;
	}

	/** The fees. */
	private List<Fee> fees;
	
	/** The payments. */
	private List<PaymentFee> payments;
	
	/** The documents. */
    private List<AttachedDocument> documents;
    
    /** The signatures. */
    private List<Signatory> signatures;
    
    /** The warning messages. */
    private List<String> warningMessages;

    private String user;

    /**
     * Flag that means that is not the final submission
     */
    private boolean preSubmit;

	private Boolean smallCompany;
	private List<AttachedDocument> smallCompanyFiles;
	private Boolean licenceAvailability;

    /**
	 * Gets the application filing number.
	 *
	 * @return the application filing number
	 */
    @Override
	public String getApplicationFilingNumber() {
		return applicationFilingNumber;
	}
	
	/**
	 * Sets the application filing number.
	 *
	 * @param applicationFilingNumber the new application filing number
	 */
    @Override
	public void setApplicationFilingNumber(String applicationFilingNumber) {
		this.applicationFilingNumber = applicationFilingNumber;
	}
	
	/**
	 * Gets the application filing date.
	 *
	 * @return the application filing date
	 */
	public Date getApplicationFilingDate() {
		return DateUtil.cloneDate(applicationFilingDate);
	}
	
	/**
	 * Sets the application filing date.
	 *
	 * @param applicationFilingDate the new application filing date
	 */
	public void setApplicationFilingDate(Date applicationFilingDate) {
		this.applicationFilingDate = DateUtil.cloneDate(applicationFilingDate);
	}
	
	/**
	 * Gets the fees.
	 *
	 * @return the fees
	 */
	public List<Fee> getFees() {
		return fees;
	}
	
	/**
	 * Sets the fees.
	 *
	 * @param fees the new fees
	 */
	public void setFees(List<Fee> fees) {
		this.fees = fees;
	}
	
	/**
	 * Gets the payments.
	 *
	 * @return the payments
	 */
	public List<PaymentFee> getPayments() {
		return payments;
	}
	
	/**
	 * Sets the payments.
	 *
	 * @param payments the new payments
	 */
	public void setPayments(List<PaymentFee> payments) {
		this.payments = payments;
	}

	/**
	 * @return the applicationReference
	 */
	public String getApplicationReference() {
		return applicationReference;
	}

	/**
	 * @param applicationReference the applicationReference to set
	 */
	public void setApplicationReference(String applicationReference) {
		this.applicationReference = applicationReference;
	}

	/**
	 * @return the applicationDate
	 */
	public Date getApplicationDate() {
		return DateUtil.cloneDate(applicationDate);
	}

	/**
	 * @param applicationDate the applicationDate to set
	 */
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = DateUtil.cloneDate(applicationDate);
	}

	/**
	 * @return the applicationNumber
	 */
	public String getApplicationNumber() {
		return applicationNumber;
	}

	/**
	 * @param applicationNumber the applicationNumber to set
	 */
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	/**
	 * @return the applicationLanguage
	 */
	public String getApplicationLanguage() {
		return applicationLanguage;
	}

	/**
	 * @param applicationLanguage the applicationLanguage to set
	 */
	public void setApplicationLanguage(String applicationLanguage) {
		this.applicationLanguage = applicationLanguage;
	}

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    /**
	 * @return the secondLanguage
	 */
	public String getSecondLanguage() {
		return secondLanguage;
	}

	/**
	 * @param secondLanguage the secondLanguage to set
	 */
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getPagesCountAttachment() {
		return pagesCountAttachment;
	}

	public void setPagesCountAttachment(Integer pagesCountAttachment) {
		this.pagesCountAttachment = pagesCountAttachment;
	}

	public Boolean getHolderIsInventor() {
		return holderIsInventor;
	}

	public void setHolderIsInventor(Boolean holderIsInventor) {
		this.holderIsInventor = holderIsInventor;
	}

	public Boolean getPartialInvalidity() {
		return partialInvalidity;
	}

	public void setPartialInvalidity(Boolean partialInvalidity) {
		this.partialInvalidity = partialInvalidity;
	}

	/**
	 * @return the applicants
	 */
	public List<Applicant> getApplicants() {
		return applicants;
	}

	/**
	 * @param applicants the applicants to set
	 */
	public void setApplicants(List<Applicant> applicants) {
		this.applicants = applicants;
	}

	/**
	 * @return the representatives
	 */
	public List<Representative> getRepresentatives() {
		return representatives;
	}

	/**
	 * @param representatives the representatives to set
	 */
	public void setRepresentatives(List<Representative> representatives) {
		this.representatives = representatives;
	}

	/**
	 * @return the holders
	 */
	public List<Holder> getHolders() {
		return holders;
	}

	/**
	 * @param holders the holders to set
	 */
	public void setHolders(List<Holder> holders) {
		this.holders = holders;
	}

	/**
	 * @return the assignees
	 */
	public List<Assignee> getAssignees() {
		return assignees;
	}

	/**
	 * @param assignees the assignees to set
	 */
	public void setAssignees(List<Assignee> assignees) {
		this.assignees = assignees;
	}

	public List<Designer> getDesigners() {
		return designers;
	}

	public void setDesigners(List<Designer> designers) {
		this.designers = designers;
	}

	/**
	 * @return the documents
	 */
	public List<AttachedDocument> getDocuments() {
		return documents;
	}

	/**
	 * @param documents the documents to set
	 */
	public void setDocuments(List<AttachedDocument> documents) {
		this.documents = documents;
	}

	/**
	 * @return the signatures
	 */
	public List<Signatory> getSignatures() {
		return signatures;
	}

	/**
	 * @param signatures the signatures to set
	 */
	public void setSignatures(List<Signatory> signatures) {
		this.signatures = signatures;
	}

	/**
	 * @return the warningMessages
	 */
	public List<String> getWarningMessages() {
		return warningMessages;
	}

	/**
	 * @param warningMessages the warningMessages to set
	 */
	public void setWarningMessages(List<String> warningMessages) {
		this.warningMessages = warningMessages;
	}

	/**
	 * @return the receivingOffice
	 */
	public String getReceivingOffice() {
		return receivingOffice;
	}

	/**
	 * @param receivingOffice the receivingOffice to set
	 */
	public void setReceivingOffice(String receivingOffice) {
		this.receivingOffice = receivingOffice;
	}

	/**
	 * @return the registrationOffice
	 */
	public String getRegistrationOffice() {
		return registrationOffice;
	}

	/**
	 * @param registrationOffice the registrationOffice to set
	 */
	public void setRegistrationOffice(String registrationOffice) {
		this.registrationOffice = registrationOffice;
	}

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }


    public boolean isPreSubmit() {
        return preSubmit;
    }

    public void setPreSubmit(boolean preSubmit) {
        this.preSubmit = preSubmit;
    }

	public List<ContactDetails> getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(List<ContactDetails> contactDetails) {
		this.contactDetails = contactDetails;
	}

	public List<Licence> getLicences() {
		return licences;
	}

	public void setLicences(List<Licence> licences) {
		this.licences = licences;
	}

	public List<Appeal> getAppeals() {
		return appeals;
	}

	public void setAppeals(List<Appeal> appeals) {
		this.appeals = appeals;
	}

	public List<GSHelperDetails> getGsHelpers() {
		return gsHelpers;
	}

	public void setGsHelpers(List<GSHelperDetails> gsHelpers) {
		this.gsHelpers = gsHelpers;
	}

	public SecurityMeasure getSecurityMeasure() {
		return securityMeasure;
	}

	public void setSecurityMeasure(SecurityMeasure securityMeasure) {
		this.securityMeasure = securityMeasure;
	}

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public List<PersonChange> getPersonChanges() {
		return personChanges;
	}

	public void setPersonChanges(List<PersonChange> personChanges) {
		this.personChanges = personChanges;
	}

	public Boolean getEsignDocDeclaration() {
		return esignDocDeclaration;
	}

	public void setEsignDocDeclaration(Boolean esignDocDeclaration) {
		this.esignDocDeclaration = esignDocDeclaration;
	}

	public Boolean getProcessInitiatedBeforePublication() {
		return processInitiatedBeforePublication;
	}

	public void setProcessInitiatedBeforePublication(Boolean processInitiatedBeforePublication) {
		this.processInitiatedBeforePublication = processInitiatedBeforePublication;
	}

	public Boolean getSmallCompany() {
		return smallCompany;
	}

	public void setSmallCompany(Boolean smallCompany) {
		this.smallCompany = smallCompany;
	}

	public List<AttachedDocument> getSmallCompanyFiles() {
		return smallCompanyFiles;
	}

	public void setSmallCompanyFiles(List<AttachedDocument> smallCompanyFiles) {
		this.smallCompanyFiles = smallCompanyFiles;
	}

	public Boolean getLicenceAvailability() {
		return licenceAvailability;
	}

	public void setLicenceAvailability(Boolean licenceAvailability) {
		this.licenceAvailability = licenceAvailability;
	}

	@Override
	public Boolean getElectronicCorrespondenceRequested() {
		//Not applicable for now for eservices
		return null;
	}

	@Override
	public String getTitleApplication() {
		//Not applicable for now for eservices
		return null;
	}

	public Userdoc getSelectedUserdoc() {
		return selectedUserdoc;
	}

	public void setSelectedUserdoc(Userdoc selectedUserdoc) {
		this.selectedUserdoc = selectedUserdoc;
	}

	public UserdocRelationRestriction getUserdocRelationRestriction() {
		return userdocRelationRestriction;
	}

	public void setUserdocRelationRestriction(UserdocRelationRestriction userdocRelationRestriction) {
		this.userdocRelationRestriction = userdocRelationRestriction;
	}

	public Boolean getRelateRequestToObject() {
		return relateRequestToObject;
	}

	public void setRelateRequestToObject(Boolean relateRequestToObject) {
		this.relateRequestToObject = relateRequestToObject;
	}

	@Override
	public String getAppSubtype() {
		if(StringUtils.isNotEmpty(getChangeType())){
			return getChangeType();
		}
		return null;
	}
}
