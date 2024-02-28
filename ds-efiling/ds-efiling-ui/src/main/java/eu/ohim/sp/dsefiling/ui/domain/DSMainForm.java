package eu.ohim.sp.dsefiling.ui.domain;

import java.util.Date;

import eu.ohim.sp.common.ui.form.MainForm;
import eu.ohim.sp.common.ui.form.application.EntitlementForm;
import eu.ohim.sp.common.util.DateUtil;

/**
 * 
 */
public class DSMainForm extends MainForm {

	private static final long serialVersionUID = 5425044898681934892L;

	/**
	 * Product descriptions for all the designs.
	 */
	private String productDescription;

	private String applicationVerbalElementsEn;

	/**
	 * Product descriptions for all the designs.
	 */
	private String reference;

	/**
	 * Deferred publication.
	 */
	private Boolean requestDeferredPublication;

	/**
	 * Deferment time.
	 */
	private Date defermentTillDate;

	/**
	 * Priority claim later.
	 */
	private boolean priorityClaimLater;

	/**
	 * Exhibition priority claim later.
	 */
	private boolean exhPriorityClaimLater;

	/**
	 * 
	 */
	private EntitlementForm entitlement;

	/**
	 * Helper variables that are used to create error Box.
	 */
	private boolean personalDataSection;
	private boolean designDataSection;
	private boolean languageSection;
	private boolean referenceSection;
	private boolean divisionalSection;
	private boolean defermentofPublicationSection;
	private boolean claimSection;
	private boolean applicantDataSection;
	private boolean representativeDataSection;
	private boolean applicantionCADataSection;
	private boolean designerDataSection;
	private boolean otherAttachments;
	private boolean signatureSection;
	private boolean termsAndConditionsSection;
	private boolean paymentDataSection;
	private boolean entitlementSection;

	/**
	 * 
	 */
	private Boolean termsAndConditions;

	/**
	 * DSMainForm constructor.
	 */
	public DSMainForm() {
		super();
	}

	/**
	 * Clear the form.
	 */
	@SuppressWarnings(value = "unchecked")
	@Override
	public void clearInformation() {
		super.clearInformation();
		entitlement = new EntitlementForm();
		priorityClaimLater = false;
		exhPriorityClaimLater = false;
		termsAndConditions = false;
		requestDeferredPublication = false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isPriorityClaimLater() {
		return priorityClaimLater;
	}

	/**
	 * 
	 * @param priorityClaimLater
	 */
	public void setPriorityClaimLater(boolean priorityClaimLater) {
		this.priorityClaimLater = priorityClaimLater;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isExhPriorityClaimLater() {
		return exhPriorityClaimLater;
	}

	/**
	 * 
	 * @param exhPriorityClaimLater
	 */
	public void setExhPriorityClaimLater(boolean exhPriorityClaimLater) {
		this.exhPriorityClaimLater = exhPriorityClaimLater;
	}

	/**
	 * Get the request for deferred publication
	 *
	 * @return the request for deferred publication
	 */
	public Boolean getRequestDeferredPublication() {
		return requestDeferredPublication;
	}

	/**
	 * Method that sets the request deferred publication
	 *
	 * @param requestDeferredPublication
	 *            the request deferred publication to set
	 */
	public void setRequestDeferredPublication(Boolean requestDeferredPublication) {
		this.requestDeferredPublication = requestDeferredPublication;
	}

	/**
	 * Get the deferment till date
	 *
	 * @return the deferment till date
	 */
	public Date getDefermentTillDate() {
		return DateUtil.cloneDate(defermentTillDate);
	}

	/**
	 * Method that sets the deferment till date
	 *
	 * @param defermentTillDate
	 *            the deferment till date to set
	 */
	public void setDefermentTillDate(Date defermentTillDate) {
		this.defermentTillDate = DateUtil.cloneDate(defermentTillDate);
	}

	/**
	 * Get the product description
	 *
	 * @return the product description
	 */
	public String getProductDescription() {
		return productDescription;
	}

	/**
	 * Method that sets the product description
	 *
	 * @param productDescription
	 *            the product description to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	/**
	 * 
	 * @return
	 */
	public EntitlementForm getEntitlement() {
		return entitlement;
	}

	/**
	 * 
	 * @param entitlement
	 */
	public void setEntitlement(EntitlementForm entitlement) {
		this.entitlement = entitlement;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isPersonalDataSection() {
		return personalDataSection;
	}

	/**
	 * 
	 * @param personalDataSection
	 */
	public void setPersonalDataSection(boolean personalDataSection) {
		this.personalDataSection = personalDataSection;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean getTermsAndConditions() {
		return termsAndConditions;
	}

	/**
	 * 
	 * @param termsAndConditions
	 */
	public void setTermsAndConditions(Boolean termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	/**
	 * 
	 * @return
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * @param reference
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the designDataSection
	 */
	public boolean isDesignDataSection() {
		return designDataSection;
	}

	/**
	 * @param designDataSection
	 *            the designDataSection to set
	 */
	public void setDesignDataSection(boolean designDataSection) {
		this.designDataSection = designDataSection;
	}

	/**
	 * @return the languageSection
	 */
	public boolean isLanguageSection() {
		return languageSection;
	}

	/**
	 * @param languageSection
	 *            the languageSection to set
	 */
	public void setLanguageSection(boolean languageSection) {
		this.languageSection = languageSection;
	}

	/**
	 * @return the referenceSection
	 */
	public boolean isReferenceSection() {
		return referenceSection;
	}

	/**
	 * @param referenceSection
	 *            the referenceSection to set
	 */
	public void setReferenceSection(boolean referenceSection) {
		this.referenceSection = referenceSection;
	}

	/**
	 * @return the divisionalSection
	 */
	public boolean isDivisionalSection() {
		return divisionalSection;
	}

	/**
	 * @param divisionalSection
	 *            the divisionalSection to set
	 */
	public void setDivisionalSection(boolean divisionalSection) {
		this.divisionalSection = divisionalSection;
	}

	/**
	 * @return the claimSection
	 */
	public boolean isClaimSection() {
		return claimSection;
	}

	/**
	 * @param claimSection
	 *            the claimSection to set
	 */
	public void setClaimSection(boolean claimSection) {
		this.claimSection = claimSection;
	}

	/**
	 * @return the applicantDataSection
	 */
	public boolean isApplicantDataSection() {
		return applicantDataSection;
	}

	/**
	 * @param applicantDataSection
	 *            the applicantDataSection to set
	 */
	public void setApplicantDataSection(boolean applicantDataSection) {
		this.applicantDataSection = applicantDataSection;
	}

	/**
	 * @return the representativeDataSection
	 */
	public boolean isRepresentativeDataSection() {
		return representativeDataSection;
	}

	/**
	 * @param representativeDataSection
	 *            the representativeDataSection to set
	 */
	public void setRepresentativeDataSection(boolean representativeDataSection) {
		this.representativeDataSection = representativeDataSection;
	}

	/**
	 * @return the applicantionCADataSection
	 */
	public boolean isApplicantionCADataSection() {
		return applicantionCADataSection;
	}

	/**
	 * @param applicantionCADataSection
	 *            the applicantionCADataSection to set
	 */
	public void setApplicantionCADataSection(boolean applicantionCADataSection) {
		this.applicantionCADataSection = applicantionCADataSection;
	}

	/**
	 * @return the designerDataSection
	 */
	public boolean isDesignerDataSection() {
		return designerDataSection;
	}

	/**
	 * @param designerDataSection
	 *            the designerDataSection to set
	 */
	public void setDesignerDataSection(boolean designerDataSection) {
		this.designerDataSection = designerDataSection;
	}

	/**
	 * @return the otherAttachments
	 */
	public boolean isOtherAttachments() {
		return otherAttachments;
	}

	/**
	 * @param otherAttachments
	 *            the otherAttachments to set
	 */
	public void setOtherAttachments(boolean otherAttachments) {
		this.otherAttachments = otherAttachments;
	}

	/**
	 * @return the signatureSection
	 */
	public boolean isSignatureSection() {
		return signatureSection;
	}

	/**
	 * @param signatureSection
	 *            the signatureSection to set
	 */
	public void setSignatureSection(boolean signatureSection) {
		this.signatureSection = signatureSection;
	}

	/**
	 * @return the termsAndConditionsSection
	 */
	public boolean isTermsAndConditionsSection() {
		return termsAndConditionsSection;
	}

	/**
	 * @param termsAndConditionsSection
	 *            the termsAndConditionsSection to set
	 */
	public void setTermsAndConditionsSection(boolean termsAndConditionsSection) {
		this.termsAndConditionsSection = termsAndConditionsSection;
	}

	/**
	 * @return the paymentDataSection
	 */
	public boolean isPaymentDataSection() {
		return paymentDataSection;
	}

	/**
	 * @param paymentDataSection
	 *            the paymentDataSection to set
	 */
	public void setPaymentDataSection(boolean paymentDataSection) {
		this.paymentDataSection = paymentDataSection;
	}

	/**
	 * @return the defermentofPublicationSection
	 */
	public boolean isDefermentofPublicationSection() {
		return defermentofPublicationSection;
	}

	/**
	 * @param defermentofPublicationSection
	 *            the defermentofPublicationSection to set
	 */
	public void setDefermentofPublicationSection(boolean defermentofPublicationSection) {
		this.defermentofPublicationSection = defermentofPublicationSection;
	}

	/**
	 * @return the entitlementSection
	 */
	public boolean isEntitlementSection() {
		return entitlementSection;
	}

	/**
	 * @param entitlementSection
	 *            the entitlementSection to set
	 */
	public void setEntitlementSection(boolean entitlementSection) {
		this.entitlementSection = entitlementSection;
	}

	public String getApplicationVerbalElementsEn() {
		return applicationVerbalElementsEn;
	}

	public void setApplicationVerbalElementsEn(String applicationVerbalElementsEn) {
		this.applicationVerbalElementsEn = applicationVerbalElementsEn;
	}
}
