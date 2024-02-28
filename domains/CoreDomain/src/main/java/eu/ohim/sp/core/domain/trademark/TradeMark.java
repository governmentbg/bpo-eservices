/*
 *  CoreDomain:: TradeMark 02/10/13 14:54 KARALCH $
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
package eu.ohim.sp.core.domain.trademark;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.application.DivisionalApplicationDetails;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.claim.ConversionPriority;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.claim.Seniority;
import eu.ohim.sp.core.domain.claim.TransformationPriority;
import eu.ohim.sp.core.domain.claim.trademark.Priority;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The Class TradeMark.
 *
 * @author ionitdi
 */
public class TradeMark extends Id implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4717490525974661249L;

    /** The receiving date. */
    private Date receivingDate;
    
    /** The receiving office. */
    private String receivingOffice;

    /** The registration date. */
    private Date registrationDate;
    
    /** The registration number. */
    private String registrationNumber;
    
    /** The registration office. */
    private String registrationOffice;

    /** The application date. */
    private Date applicationDate;
    
    /** The application number. */
    private String applicationNumber;

    /** The filing date. */
    private Date filingDate;
    
    /** The filing number. */
    private String filingNumber;
    
    /** The filing user. */
    private String filingUser;

    /** The publication date. */
    private Date publicationDate;

    /** The application reference. */
    private String applicationReference;
    
    /** The expiration date. */
    private Date expirationDate;
    
    /** The termination date. */
    private Date terminationDate;
    
    /** The application language. */
    private String applicationLanguage;
    
    /** The second language. */
    private String secondLanguage;
    
    /** The correspondence language. */
    private String correspondenceLanguage;

    /** The current status. */
    private String currentStatus;
    
    /** The current status date. */
    private Date currentStatusDate;
    
    /** The opposition period start. */
    private Date oppositionPeriodStart;
    
    /** The opposition period end. */
    private Date oppositionPeriodEnd;

    /** The class descriptions. */
    private List<ClassDescription> classDescriptions;

	private String goodsCharacteristicsDescription;
	private String goodsProductionDescription;
	private String goodsGeographyDescription;
	private String goodsFactorsDescription;

	/** The description. */
	private List<MarkDescription> markDescriptions;
	
	/** The disclaimer. */
	private List<MarkDisclaimer> markDisclaimers;

	/** The word specifications. */
	private List<WordSpecification> wordSpecifications;
	
	/** The image specifications. */
	private List<ImageSpecification> imageSpecifications;

	/** The image specifications. */
	private List<MediaRepresentation> mediaRepresentations;

	/** The sound representations. */
	private List<SoundSpecification> soundRepresentations;
	
	/** The mark right kind. */
	private MarkKind markRightKind;

	/** The series indicator. */
	private boolean seriesIndicator;
	
	/** The series number. */
	private String seriesNumber;
	
	/** The mark kind. */
	private MarkFeature markKind;

    /** The priority claim later indicator. */
    private Boolean priorityClaimLaterIndicator;
    
    /** The priorities. */
    private List<Priority> priorities;

    /** The exhibition priority claim later indicator. */
    private Boolean exhibitionPriorityClaimLaterIndicator;
    
    /** The exhibition priorities. */
    private List<ExhibitionPriority> exhibitionPriorities;

    /** The transformation priorities. */
    private List<TransformationPriority> transformationPriorities;
    
    /** The conversion priorities. */
    private List<ConversionPriority> conversionPriorities;

    /** The seniorities. */
    private List<Seniority> seniorities;

    /** The applicants. */
    private List<Applicant> applicants;
    
    /** The representatives. */
    private List<Representative> representatives;

    /** The trade mark documents. */
    private List<AttachedDocument> tradeMarkDocuments;

    /** The second language translation. */
    private Boolean secondLanguageTranslation;
    
    /** The application use reference. */
    private Boolean applicationUseReference;
    
    /** The international trade mark. */
    private Boolean internationalTradeMark;

    /** The divisional application details. */
    private DivisionalApplicationDetails divisionalApplicationDetails;

    /** The signatures. */
    private List<Signatory> signatures;
    
    /** The national search report indicator. */
    private boolean nationalSearchReportIndicator;
    
    /** The second signatory exists. */
    private boolean secondSignatoryExists;
    
    /** The contact details */
    private List<ContactDetails> contactDetails;

    /** The office specific id. */
    private String officeSpecificId;

    /** The comment. */
    private String comment;
    
    /** the number of renewals */
    private int numberOfRenewals;

	/**
	 * Comment on the gs - some explanations or other
	 */
	private String goodsServicesComment;

	private Boolean unpublished;

	private List<ForeignRegistration> foreignRegistrations;

	/**
	 * Gets the receiving date.
	 *
	 * @return the receiving date
	 */
	public Date getReceivingDate() {
		return DateUtil.cloneDate(receivingDate);
	}

	/**
	 * Sets the receiving date.
	 *
	 * @param receivingDate the new receiving date
	 */
	public void setReceivingDate(Date receivingDate) {
		this.receivingDate = DateUtil.cloneDate(receivingDate);
	}

	/**
	 * Gets the receiving office.
	 *
	 * @return the receiving office
	 */
	public String getReceivingOffice() {
		return receivingOffice;
	}

	/**
	 * Sets the receiving office.
	 *
	 * @param receivingOffice the new receiving office
	 */
	public void setReceivingOffice(String receivingOffice) {
		this.receivingOffice = receivingOffice;
	}

	/**
	 * Gets the registration date.
	 *
	 * @return the registration date
	 */
	public Date getRegistrationDate() {
		return DateUtil.cloneDate(registrationDate);
	}

	/**
	 * Sets the registration date.
	 *
	 * @param registrationDate the new registration date
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = DateUtil.cloneDate(registrationDate);
	}

	/**
	 * Gets the registration number.
	 *
	 * @return the registration number
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	/**
	 * Sets the registration number.
	 *
	 * @param registrationNumber the new registration number
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	/**
	 * Gets the registration office.
	 *
	 * @return the registration office
	 */
	public String getRegistrationOffice() {
		return registrationOffice;
	}

	/**
	 * Sets the registration office.
	 *
	 * @param registrationOffice the new registration office
	 */
	public void setRegistrationOffice(String registrationOffice) {
		this.registrationOffice = registrationOffice;
	}

	/**
	 * Gets the application date.
	 *
	 * @return the application date
	 */
	public Date getApplicationDate() {
		return DateUtil.cloneDate(applicationDate);
	}

	/**
	 * Sets the application date.
	 *
	 * @param applicationDate the new application date
	 */
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = DateUtil.cloneDate(applicationDate);
	}

	/**
	 * Gets the application number.
	 *
	 * @return the application number
	 */
	public String getApplicationNumber() {
		return applicationNumber;
	}

	/**
	 * Sets the application number.
	 *
	 * @param applicationNumber the new application number
	 */
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	/**
	 * Gets the filing date.
	 *
	 * @return the filing date
	 */
	public Date getFilingDate() {
		return DateUtil.cloneDate(filingDate);
	}

	/**
	 * Sets the filing date.
	 *
	 * @param filingDate the new filing date
	 */
	public void setFilingDate(Date filingDate) {
		this.filingDate = DateUtil.cloneDate(filingDate);
	}

	/**
	 * Gets the filing number.
	 *
	 * @return the filing number
	 */
	public String getFilingNumber() {
		return filingNumber;
	}

	/**
	 * Sets the filing number.
	 *
	 * @param filingNumber the new filing number
	 */
	public void setFilingNumber(String filingNumber) {
		this.filingNumber = filingNumber;
	}

	/**
	 * Gets the filing user.
	 *
	 * @return the filing user
	 */
	public String getFilingUser() {
		return filingUser;
	}

	/**
	 * Sets the filing user.
	 *
	 * @param filingUser the new filing user
	 */
	public void setFilingUser(String filingUser) {
		this.filingUser = filingUser;
	}

	/**
	 * Gets the publication date.
	 *
	 * @return the publication date
	 */
	public Date getPublicationDate() {
		return DateUtil.cloneDate(publicationDate);
	}

	/**
	 * Sets the publication date.
	 *
	 * @param publicationDate the new publication date
	 */
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = DateUtil.cloneDate(publicationDate);
	}

	/**
	 * Gets the application reference.
	 *
	 * @return the application reference
	 */
	public String getApplicationReference() {
		return applicationReference;
	}

	/**
	 * Sets the application reference.
	 *
	 * @param applicationReference the new application reference
	 */
	public void setApplicationReference(String applicationReference) {
		this.applicationReference = applicationReference;
	}

	/**
	 * Gets the expiration date.
	 *
	 * @return the expiration date
	 */
	public Date getExpirationDate() {
		return DateUtil.cloneDate(expirationDate);
	}

	/**
	 * Sets the expiration date.
	 *
	 * @param expirationDate the new expiration date
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = DateUtil.cloneDate(expirationDate);
	}

	/**
	 * Gets the termination date.
	 *
	 * @return the termination date
	 */
	public Date getTerminationDate() {
		return DateUtil.cloneDate(terminationDate);
	}

	/**
	 * Sets the termination date.
	 *
	 * @param terminationDate the new termination date
	 */
	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = DateUtil.cloneDate(terminationDate);
	}

	/**
	 * Gets the application language.
	 *
	 * @return the application language
	 */
	public String getApplicationLanguage() {
		return applicationLanguage;
	}

	/**
	 * Sets the application language.
	 *
	 * @param applicationLanguage the new application language
	 */
	public void setApplicationLanguage(String applicationLanguage) {
		this.applicationLanguage = applicationLanguage;
	}

	/**
	 * Gets the second language.
	 *
	 * @return the second language
	 */
	public String getSecondLanguage() {
		return secondLanguage;
	}

	/**
	 * Sets the second language.
	 *
	 * @param secondLanguage the new second language
	 */
	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}

	/**
	 * Gets the correspondence language.
	 *
	 * @return the correspondence language
	 */
	public String getCorrespondenceLanguage() {
		return correspondenceLanguage;
	}

	/**
	 * Sets the correspondence language.
	 *
	 * @param correspondenceLanguage the new correspondence language
	 */
	public void setCorrespondenceLanguage(String correspondenceLanguage) {
		this.correspondenceLanguage = correspondenceLanguage;
	}

	/**
	 * Gets the current status.
	 *
	 * @return the current status
	 */
	public String getCurrentStatus() {
		return currentStatus;
	}

	/**
	 * Sets the current status.
	 *
	 * @param currentStatus the new current status
	 */
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	/**
	 * Gets the current status date.
	 *
	 * @return the current status date
	 */
	public Date getCurrentStatusDate() {
		return DateUtil.cloneDate(currentStatusDate);
	}

	/**
	 * Sets the current status date.
	 *
	 * @param currentStatusDate the new current status date
	 */
	public void setCurrentStatusDate(Date currentStatusDate) {
		this.currentStatusDate = DateUtil.cloneDate(currentStatusDate);
	}

	/**
	 * Gets the opposition period start.
	 *
	 * @return the opposition period start
	 */
	public Date getOppositionPeriodStart() {
		return DateUtil.cloneDate(oppositionPeriodStart);
	}

	/**
	 * Sets the opposition period start.
	 *
	 * @param oppositionPeriodStart the new opposition period start
	 */
	public void setOppositionPeriodStart(Date oppositionPeriodStart) {
		this.oppositionPeriodStart = DateUtil.cloneDate(oppositionPeriodStart);
	}

	/**
	 * Gets the opposition period end.
	 *
	 * @return the opposition period end
	 */
	public Date getOppositionPeriodEnd() {
		return DateUtil.cloneDate(oppositionPeriodEnd);
	}

	/**
	 * Sets the opposition period end.
	 *
	 * @param oppositionPeriodEnd the new opposition period end
	 */
	public void setOppositionPeriodEnd(Date oppositionPeriodEnd) {
		this.oppositionPeriodEnd = DateUtil.cloneDate(oppositionPeriodEnd);
	}

	/**
	 * Gets the class descriptions.
	 *
	 * @return the class descriptions
	 */
	public List<ClassDescription> getClassDescriptions() {
		return classDescriptions;
	}

	/**
	 * Sets the class descriptions.
	 *
	 * @param classDescriptions the new class descriptions
	 */
	public void setClassDescriptions(List<ClassDescription> classDescriptions) {
		this.classDescriptions = classDescriptions;
	}

	/**
	 * Gets the mark descriptions.
	 *
	 * @return the mark descriptions
	 */
	public List<MarkDescription> getMarkDescriptions() {
		return markDescriptions;
	}

	/**
	 * Sets the mark descriptions.
	 *
	 * @param markDescriptions the new mark descriptions
	 */
	public void setMarkDescriptions(List<MarkDescription> markDescriptions) {
		this.markDescriptions = markDescriptions;
	}

	/**
	 * Gets the mark disclaimers.
	 *
	 * @return the mark disclaimers
	 */
	public List<MarkDisclaimer> getMarkDisclaimers() {
		return markDisclaimers;
	}

	/**
	 * Sets the mark disclaimers.
	 *
	 * @param markDisclaimers the new mark disclaimers
	 */
	public void setMarkDisclaimers(List<MarkDisclaimer> markDisclaimers) {
		this.markDisclaimers = markDisclaimers;
	}

	/**
	 * Gets the word specifications.
	 *
	 * @return the word specifications
	 */
	public List<WordSpecification> getWordSpecifications() {
		return wordSpecifications;
	}

	/**
	 * Sets the word specifications.
	 *
	 * @param wordSpecifications the new word specifications
	 */
	public void setWordSpecifications(List<WordSpecification> wordSpecifications) {
		this.wordSpecifications = wordSpecifications;
	}

	/**
	 * Gets the image specifications.
	 *
	 * @return the image specifications
	 */
	public List<ImageSpecification> getImageSpecifications() {
		return imageSpecifications;
	}

	/**
	 * Sets the image specifications.
	 *
	 * @param imageSpecifications the new image specifications
	 */
	public void setImageSpecifications(List<ImageSpecification> imageSpecifications) {
		this.imageSpecifications = imageSpecifications;
	}

	public List<MediaRepresentation> getMediaRepresentations() {
		return mediaRepresentations;
	}

	public void setMediaRepresentations(List<MediaRepresentation> mediaRepresentations) {
		this.mediaRepresentations = mediaRepresentations;
	}

	/**
	 * Gets the sound representations.
	 *
	 * @return the sound representations
	 */
	public List<SoundSpecification> getSoundRepresentations() {
		return soundRepresentations;
	}

	/**
	 * Sets the sound representations.
	 *
	 * @param soundRepresentations the new sound representations
	 */
	public void setSoundRepresentations(
			List<SoundSpecification> soundRepresentations) {
		this.soundRepresentations = soundRepresentations;
	}

	/**
	 * Gets the mark right kind.
	 *
	 * @return the mark right kind
	 */
	public MarkKind getMarkRightKind() {
		return markRightKind;
	}

	/**
	 * Sets the mark right kind.
	 *
	 * @param markRightKind the new mark right kind
	 */
	public void setMarkRightKind(MarkKind markRightKind) {
		this.markRightKind = markRightKind;
	}

	/**
	 * Checks if is series indicator.
	 *
	 * @return true, if is series indicator
	 */
	public boolean isSeriesIndicator() {
		return seriesIndicator;
	}

	/**
	 * Sets the series indicator.
	 *
	 * @param seriesIndicator the new series indicator
	 */
	public void setSeriesIndicator(boolean seriesIndicator) {
		this.seriesIndicator = seriesIndicator;
	}

	/**
	 * Gets the series number.
	 *
	 * @return the series number
	 */
	public String getSeriesNumber() {
		return seriesNumber;
	}

	/**
	 * Sets the series number.
	 *
	 * @param seriesNumber the new series number
	 */
	public void setSeriesNumber(String seriesNumber) {
		this.seriesNumber = seriesNumber;
	}

	/**
	 * Gets the mark kind.
	 *
	 * @return the mark kind
	 */
	public MarkFeature getMarkKind() {
		return markKind;
	}

	/**
	 * Sets the mark kind.
	 *
	 * @param markKind the new mark kind
	 */
	public void setMarkKind(MarkFeature markKind) {
		this.markKind = markKind;
	}

	/**
	 * Gets the priority claim later indicator.
	 *
	 * @return the priority claim later indicator
	 */
	public Boolean getPriorityClaimLaterIndicator() {
		return priorityClaimLaterIndicator;
	}

	/**
	 * Sets the priority claim later indicator.
	 *
	 * @param priorityClaimLaterIndicator the new priority claim later indicator
	 */
	public void setPriorityClaimLaterIndicator(Boolean priorityClaimLaterIndicator) {
		this.priorityClaimLaterIndicator = priorityClaimLaterIndicator;
	}

	/**
	 * Gets the priorities.
	 *
	 * @return the priorities
	 */
	public List<Priority> getPriorities() {
		return priorities;
	}

	/**
	 * Sets the priorities.
	 *
	 * @param priorities the new priorities
	 */
	public void setPriorities(List<Priority> priorities) {
		this.priorities = priorities;
	}

	/**
	 * Gets the exhibition priority claim later indicator.
	 *
	 * @return the exhibition priority claim later indicator
	 */
	public Boolean getExhibitionPriorityClaimLaterIndicator() {
		return exhibitionPriorityClaimLaterIndicator;
	}

	/**
	 * Sets the exhibition priority claim later indicator.
	 *
	 * @param exhibitionPriorityClaimLaterIndicator the new exhibition priority claim later indicator
	 */
	public void setExhibitionPriorityClaimLaterIndicator(
			Boolean exhibitionPriorityClaimLaterIndicator) {
		this.exhibitionPriorityClaimLaterIndicator = exhibitionPriorityClaimLaterIndicator;
	}

	/**
	 * Gets the exhibition priorities.
	 *
	 * @return the exhibition priorities
	 */
	public List<ExhibitionPriority> getExhibitionPriorities() {
		return exhibitionPriorities;
	}

	/**
	 * Sets the exhibition priorities.
	 *
	 * @param exhibitionPriorities the new exhibition priorities
	 */
	public void setExhibitionPriorities(
			List<ExhibitionPriority> exhibitionPriorities) {
		this.exhibitionPriorities = exhibitionPriorities;
	}

	/**
	 * Gets the transformation priorities.
	 *
	 * @return the transformation priorities
	 */
	public List<TransformationPriority> getTransformationPriorities() {
		return transformationPriorities;
	}

	/**
	 * Sets the transformation priorities.
	 *
	 * @param transformationPriorities the new transformation priorities
	 */
	public void setTransformationPriorities(
			List<TransformationPriority> transformationPriorities) {
		this.transformationPriorities = transformationPriorities;
	}

	/**
	 * Gets the seniorities.
	 *
	 * @return the seniorities
	 */
	public List<Seniority> getSeniorities() {
		return seniorities;
	}

	/**
	 * Sets the seniorities.
	 *
	 * @param seniorities the new seniorities
	 */
	public void setSeniorities(List<Seniority> seniorities) {
		this.seniorities = seniorities;
	}

	/**
	 * Gets the applicants.
	 *
	 * @return the applicants
	 */
	public List<Applicant> getApplicants() {
		return applicants;
	}

	/**
	 * Sets the applicants.
	 *
	 * @param applicants the new applicants
	 */
	public void setApplicants(List<Applicant> applicants) {
		this.applicants = applicants;
	}

	/**
	 * Gets the representatives.
	 *
	 * @return the representatives
	 */
	public List<Representative> getRepresentatives() {
		return representatives;
	}

	/**
	 * Sets the representatives.
	 *
	 * @param representatives the new representatives
	 */
	public void setRepresentatives(List<Representative> representatives) {
		this.representatives = representatives;
	}

	/**
	 * Gets the trade mark documents.
	 *
	 * @return the trade mark documents
	 */
	public List<AttachedDocument> getTradeMarkDocuments() {
		return tradeMarkDocuments;
	}

	/**
	 * Sets the trade mark documents.
	 *
	 * @param tradeMarkDocuments the new trade mark documents
	 */
	public void setTradeMarkDocuments(List<AttachedDocument> tradeMarkDocuments) {
		this.tradeMarkDocuments = tradeMarkDocuments;
	}

	/**
	 * Gets the second language translation.
	 *
	 * @return the second language translation
	 */
	public Boolean getSecondLanguageTranslation() {
		return secondLanguageTranslation;
	}

	/**
	 * Sets the second language translation.
	 *
	 * @param secondLanguageTranslation the new second language translation
	 */
	public void setSecondLanguageTranslation(Boolean secondLanguageTranslation) {
		this.secondLanguageTranslation = secondLanguageTranslation;
	}

	/**
	 * Gets the application use reference.
	 *
	 * @return the application use reference
	 */
	public Boolean getApplicationUseReference() {
		return applicationUseReference;
	}

	/**
	 * Sets the application use reference.
	 *
	 * @param applicationUseReference the new application use reference
	 */
	public void setApplicationUseReference(Boolean applicationUseReference) {
		this.applicationUseReference = applicationUseReference;
	}

	/**
	 * Gets the international trade mark.
	 *
	 * @return the international trade mark
	 */
	public Boolean getInternationalTradeMark() {
		return internationalTradeMark;
	}

	/**
	 * Sets the international trade mark.
	 *
	 * @param internationalTradeMark the new international trade mark
	 */
	public void setInternationalTradeMark(Boolean internationalTradeMark) {
		this.internationalTradeMark = internationalTradeMark;
	}

	/**
	 * Gets the divisional application details.
	 *
	 * @return the divisional application details
	 */
	public DivisionalApplicationDetails getDivisionalApplicationDetails() {
		return divisionalApplicationDetails;
	}

	/**
	 * Sets the divisional application details.
	 *
	 * @param divisionalApplicationDetails the new divisional application details
	 */
	public void setDivisionalApplicationDetails(
			DivisionalApplicationDetails divisionalApplicationDetails) {
		this.divisionalApplicationDetails = divisionalApplicationDetails;
	}

	/**
	 * Gets the signatures.
	 *
	 * @return the signatures
	 */
	public List<Signatory> getSignatures() {
		return signatures;
	}

	/**
	 * Sets the signatures.
	 *
	 * @param signatures the new signatures
	 */
	public void setSignatures(List<Signatory> signatures) {
		this.signatures = signatures;
	}

	/**
	 * Checks if is national search report indicator.
	 *
	 * @return true, if is national search report indicator
	 */
	public boolean isNationalSearchReportIndicator() {
		return nationalSearchReportIndicator;
	}

	/**
	 * Sets the national search report indicator.
	 *
	 * @param nationalSearchReportIndicator the new national search report indicator
	 */
	public void setNationalSearchReportIndicator(
			boolean nationalSearchReportIndicator) {
		this.nationalSearchReportIndicator = nationalSearchReportIndicator;
	}

	/**
	 * Checks if is second signatory exists.
	 *
	 * @return true, if is second signatory exists
	 */
	public boolean isSecondSignatoryExists() {
		return secondSignatoryExists;
	}

	/**
	 * Sets the second signatory exists.
	 *
	 * @param secondSignatoryExists the new second signatory exists
	 */
	public void setSecondSignatoryExists(boolean secondSignatoryExists) {
		this.secondSignatoryExists = secondSignatoryExists;
	}

	/**
	 * Gets the office specific id.
	 *
	 * @return the office specific id
	 */
	public String getOfficeSpecificId() {
		return officeSpecificId;
	}

	/**
	 * Sets the office specific id.
	 *
	 * @param officeSpecificId the new office specific id
	 */
	public void setOfficeSpecificId(String officeSpecificId) {
		this.officeSpecificId = officeSpecificId;
	}

	/**
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment.
	 *
	 * @param comment the new comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the numberOfRenewals
	 */
	public int getNumberOfRenewals() {
		return numberOfRenewals;
	}

	/**
	 * @param numberOfRenewals the numberOfRenewals to set
	 */
	public void setNumberOfRenewals(int numberOfRenewals) {
		this.numberOfRenewals = numberOfRenewals;
	}

	public List<ConversionPriority> getConversionPriorities() {
		return conversionPriorities;
	}

	public void setConversionPriorities(
			List<ConversionPriority> conversionPriorities) {
		this.conversionPriorities = conversionPriorities;
	}

	public List<ContactDetails> getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(List<ContactDetails> contactDetails) {
		this.contactDetails = contactDetails;
	}

	public String getGoodsServicesComment() {
		return goodsServicesComment;
	}

	public void setGoodsServicesComment(String goodsServicesComment) {
		this.goodsServicesComment = goodsServicesComment;
	}

	public Boolean getUnpublished() {
		return unpublished;
	}

	public void setUnpublished(Boolean unpublished) {
		this.unpublished = unpublished;
	}

	public List<ForeignRegistration> getForeignRegistrations() {
		return foreignRegistrations;
	}

	public void setForeignRegistrations(List<ForeignRegistration> foreignRegistrations) {
		this.foreignRegistrations = foreignRegistrations;
	}

	public String getGoodsCharacteristicsDescription() {
		return goodsCharacteristicsDescription;
	}

	public void setGoodsCharacteristicsDescription(String goodsCharacteristicsDescription) {
		this.goodsCharacteristicsDescription = goodsCharacteristicsDescription;
	}

	public String getGoodsProductionDescription() {
		return goodsProductionDescription;
	}

	public void setGoodsProductionDescription(String goodsProductionDescription) {
		this.goodsProductionDescription = goodsProductionDescription;
	}

	public String getGoodsGeographyDescription() {
		return goodsGeographyDescription;
	}

	public void setGoodsGeographyDescription(String goodsGeographyDescription) {
		this.goodsGeographyDescription = goodsGeographyDescription;
	}

	public String getGoodsFactorsDescription() {
		return goodsFactorsDescription;
	}

	public void setGoodsFactorsDescription(String goodsFactorsDescription) {
		this.goodsFactorsDescription = goodsFactorsDescription;
	}
}
