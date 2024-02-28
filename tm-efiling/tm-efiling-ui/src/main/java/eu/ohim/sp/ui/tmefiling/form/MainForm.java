/*******************************************************************************
 * * $Id::                                                  $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.form;

import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.claim.TransformationForm;
import eu.ohim.sp.common.ui.form.resources.ColourForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.service.constant.AppConstants;
import eu.ohim.sp.common.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainForm extends eu.ohim.sp.common.ui.form.MainForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Stores the claim later priority
	 */
	private Boolean claimPriority;

	/**
	 * Stores the claim later exhibition priority
	 */
	private Boolean claimExhibitionPriority;

	private TransformationForm transformation;
	private ExhPriorityForm exhpriority;

	// language section
	private Boolean correspondenceLanguageCheckBox;
	private Boolean secondLanguageTranslation;
	private Boolean correspondenceEmail;

	/**
	 * mark representation Section
	 */
	// all trademark
	private String markType;
	private String firstDisclaimer;
	private String secondDisclaimer;
	private String markRepresentation;
	private String markDescription;
	private String markDescriptionSecond;
	private String markColorsIndicationSecond;
	private String soundRepresentation;
	private FileWrapper soundFile;
	private String wordRepresentation;
	private Boolean collectiveMark;
	private Boolean certificateMark;
	private String transcriptionDetails;
	private List<ColourForm> colourList;
	private Boolean yourReference;
	private Boolean colourChecked;


	private String goodsCharacteristicsDescription;
	private String goodsProductionDescription;
	private String goodsGeographyDescription;
	private String goodsFactorsDescription;

	private FileWrapper fileWrapperImage;

	private FileWrapper fileWrapperMultimedia;

	// language used in gui
	private String langId = null;

	// word mark
	private String charset;
	/*
	 * figurative 3-dimentional colour sound other
	 */

	// Signature section
	private Date dateOfSigning;
	private SignatureForm signatoryForm;
	// Signature section (second)
	private Boolean addSecondSign;
	private SignatureForm secondSignatoryForm;

	/**
	 * Helper variables that are used to create error Box.
	 */
	private Boolean personalDataSection;
	private Boolean markRepresentationSection;
	private Boolean claimSection;
	private Boolean languageSection;
	private Boolean signatureSection;
	private Boolean gsSection;
	private Boolean referenceSection;
	private Boolean paymentDataSection;
	private Boolean divisionalSection;
	private Boolean otherAttachments;
	private boolean applicantionCADataSection;
	private Boolean foreignRegistrationDataSection;
	private Boolean goodsDescriptionDataSection;

	/**
	 * Helper variable that checks whether there were only warnings and during
	 * the next submission don't check for warnings.
	 */
	private Boolean showWarning;
	private Boolean showWarningInvoice;
	private Boolean finalSubmit;
	private Boolean showErrors;

	private Boolean paymentMethodSection;
	private Boolean transformationSection;

	private FileWrapper trademarkRegulationDocuments;
	private FileWrapper trademarkTranslationDocuments;
	private FileWrapper trademarkApplicantDocuments;

	private Boolean seriesPresent;
	private String seriesNumber;

	private Boolean collectiveRegulationDoc;
	private Boolean collectiveTranslationDoc;

	// Variables that display which classes to show
	private String displayedGoods;
	private String displayedTerms;

	// Terms and conditions acceptance
	private Boolean termsAndConditions;

	// Divisional application details
	private Boolean claimDivisionalApplication;
	private String numberDivisionalApplication;
	private Date dateDivisionalApplication;
	private Boolean divisionalApplicationImported;

	/**
	 * Variable representing National Search Report
	 */
	private Boolean nationalSearchReport;

	/**
	 * Constructs a new MainForm.
	 */
	public MainForm() {
		clear();

		showWarning = Boolean.FALSE;
		showErrors = Boolean.TRUE;
		showWarningInvoice = Boolean.FALSE;
		correspondenceLanguageCheckBox = Boolean.FALSE;
		secondLanguageTranslation = Boolean.FALSE;

		this.claimPriority = null;
		this.claimExhibitionPriority = null;
		transformation = new TransformationForm();
		exhpriority = new ExhPriorityForm();
		dateOfSigning = new Date();
		signatoryForm = new SignatureForm();
		secondSignatoryForm = new SignatureForm();

		collectiveRegulationDoc = Boolean.FALSE;
		collectiveTranslationDoc = Boolean.FALSE;
		colourChecked = Boolean.FALSE;
		nationalSearchReport = Boolean.FALSE;
	}
	
	/**
	 * Getter for property 'secondLanguageTranslation'.
	 * 
	 * @return Value for property 'secondLanguageTranslation'.
	 */
	public Boolean getSecondLanguageTranslation() {
		return secondLanguageTranslation;
	}

	/**
	 * Setter for property 'secondLanguageTranslation'.
	 * 
	 * @param secondLanguageTranslation
	 *            Value to set for property 'secondLanguageTranslation'.
	 */
	public void setSecondLanguageTranslation(Boolean secondLanguageTranslation) {
		this.secondLanguageTranslation = secondLanguageTranslation;
	}

	public Boolean getCorrespondenceEmail() {
		return correspondenceEmail;
	}

	public void setCorrespondenceEmail(Boolean correspondenceEmail) {
		this.correspondenceEmail = correspondenceEmail;
	}

	/**
	 * Getter for property 'firstDisclaimer'.
	 * 
	 * @return Value for property 'firstDisclaimer'.
	 */
	public String getFirstDisclaimer() {
		return firstDisclaimer;
	}

	/**
	 * Setter for property 'firstDisclaimer'.
	 * 
	 * @param firstDisclaimer
	 *            Value to set for property 'firstDisclaimer'.
	 */
	public void setFirstDisclaimer(String firstDisclaimer) {
		this.firstDisclaimer = firstDisclaimer;
	}

	/**
	 * Getter for property 'secondDisclaimer'.
	 * 
	 * @return Value for property 'secondDisclaimer'.
	 */
	public String getSecondDisclaimer() {
		return secondDisclaimer;
	}

	/**
	 * Setter for property 'secondDisclaimer'.
	 * 
	 * @param secondDisclaimer
	 *            Value to set for property 'secondDisclaimer'.
	 */
	public void setSecondDisclaimer(String secondDisclaimer) {
		this.secondDisclaimer = secondDisclaimer;
	}

	public void clearInformation() {
		super.clearInformation();
		clear();
	}

	private void clear() {
		markType = AppConstants.MarkTypeSelect;
		firstDisclaimer = "";
		secondDisclaimer = "";
		markColorsIndicationSecond = "";
		markDescription = "";
		markDescriptionSecond = "";
		markColorsIndicationSecond = "";
		soundRepresentation = "";
		markRepresentation = "";
		wordRepresentation = "";
		collectiveMark = Boolean.FALSE;
		certificateMark = Boolean.FALSE;
		seriesPresent = Boolean.FALSE;
		seriesNumber = null;
		colourChecked = Boolean.FALSE;
		fileWrapperImage = new FileWrapper();
		fileWrapperMultimedia = new FileWrapper();
		soundFile = new FileWrapper();
		charset = "";
		transcriptionDetails = "";
		yourReference = Boolean.FALSE;
		colourList = new ArrayList<ColourForm>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "[MainForm: markType=" + markType + "]";
	}

	/**
	 * Method that returns the transformation
	 * 
	 * @return the transformation
	 */
	public TransformationForm getTransformation() {
		return transformation;
	}

	/**
	 * Method that sets the transformation
	 * 
	 * @param transformation
	 *            the transformation to set
	 */
	public void setTransformation(TransformationForm transformation) {
		this.transformation = transformation;
	}

	/**
	 * Method that returns the exhpriority
	 * 
	 * @return the exhpriority
	 */
	public ExhPriorityForm getExhpriority() {
		return exhpriority;
	}

	/**
	 * Method that sets the exhpriority
	 * 
	 * @param exhpriority
	 *            the exhpriority to set
	 */
	public void setExhpriority(ExhPriorityForm exhpriority) {
		this.exhpriority = exhpriority;
	}

	/**
	 * Getter for property 'markRepresentation'.
	 * 
	 * @return Value for property 'markRepresentation'.
	 */
	public String getMarkRepresentation() {
		return markRepresentation;
	}

	/**
	 * Setter for property 'markRepresentation'.
	 * 
	 * @param markRepresentation
	 *            Value to set for property 'markRepresentation'.
	 */
	public void setMarkRepresentation(String markRepresentation) {
		this.markRepresentation = markRepresentation;
	}

	/**
	 * Getter for property 'charset'.
	 * 
	 * @return Value for property 'charset'.
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * Setter for property 'charset'.
	 * 
	 * @param charset
	 *            Value to set for property 'charset'.
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * Getter for property 'markType'.
	 * 
	 * @return Value for property 'markType'.
	 */
	public String getMarkType() {
		return markType;
	}

	/**
	 * Setter for property 'markType'.
	 * 
	 * @param markType
	 *            Value to set for property 'markType'.
	 */
	public void setMarkType(String markType) {
		this.markType = markType;
	}

	/**
	 * Method that returns the personalDataSection
	 * 
	 * @return the personalDataSection
	 */
	public Boolean getPersonalDataSection() {
		return personalDataSection;
	}

	/**
	 * Method that sets the personalDataSection
	 * 
	 * @param personalDataSection
	 *            the personalDataSection to set
	 */
	public void setPersonalDataSection(Boolean personalDataSection) {
		this.personalDataSection = personalDataSection;
	}

	/**
	 * Method that returns the claimSection
	 * 
	 * @return the claimSection
	 */
	public Boolean getClaimSection() {
		return claimSection;
	}

	/**
	 * Method that sets the claimSection
	 * 
	 * @param claimSection
	 *            the claimSection to set
	 */
	public void setClaimSection(Boolean claimSection) {
		this.claimSection = claimSection;
	}

	/**
	 * Method that returns the languageSection
	 * 
	 * @return the languageSection
	 */
	public Boolean getLanguageSection() {
		return languageSection;
	}

	/**
	 * Method that sets the languageSection
	 * 
	 * @param languageSection
	 *            the languageSection to set
	 */
	public void setLanguageSection(Boolean languageSection) {
		this.languageSection = languageSection;
	}

	/**
	 * Getter for property 'collectiveMark'.
	 * 
	 * @return Value for property 'collectiveMark'.
	 */
	public Boolean getCollectiveMark() {
		return collectiveMark;
	}

	public String getTranscriptionDetails() {
		return transcriptionDetails;
	}

	public void setTranscriptionDetails(String transcriptionDetails) {
		this.transcriptionDetails = transcriptionDetails;
	}

	/**
	 * Setter for property 'collectiveMark'.
	 * 
	 * @param collectiveMark
	 *            Value to set for property 'collectiveMark'.
	 */
	public void setCollectiveMark(Boolean collectiveMark) {
		this.collectiveMark = collectiveMark;
	}

	public Boolean getYourReference() {
		return yourReference;
	}

	public void setYourReference(Boolean yourReference) {
		this.yourReference = yourReference;
	}

	public FileWrapper getFileWrapperImage() {
		if (fileWrapperImage == null) {
			fileWrapperImage = new FileWrapper();
		}
		return fileWrapperImage;
	}

	public void setFileWrapperImage(FileWrapper fileWrapperImage) {
		this.fileWrapperImage = fileWrapperImage;
	}

	/**
	 * Getter for property 'markRepresentationSection'.
	 * 
	 * @return Value for property 'markRepresentationSection'.
	 */
	public Boolean getMarkRepresentationSection() {
		return markRepresentationSection;
	}

	/**
	 * Setter for property 'markRepresentationSection'.
	 * 
	 * @param markRepresentationSection
	 *            Value to set for property 'markRepresentationSection'.
	 */
	public void setMarkRepresentationSection(Boolean markRepresentationSection) {
		this.markRepresentationSection = markRepresentationSection;
	}

	/**
	 * @return the signatureSection
	 */
	public Boolean getSignatureSection() {
		return signatureSection;
	}

	/**
	 * @param signatureSection
	 *            the signatureSection to set
	 */
	public void setSignatureSection(Boolean signatureSection) {
		this.signatureSection = signatureSection;
	}

	/**
	 * @return the dateOfSigning
	 */
	public Date getDateOfSigning() {
		return DateUtil.cloneDate(dateOfSigning);
	}

	/**
	 * @param dateOfSigning
	 *            the dateOfSigning to set
	 */
	public void setDateOfSigning(Date dateOfSigning) {
		this.dateOfSigning = DateUtil.cloneDate(dateOfSigning);
	}

	/**
	 * Method that returns the showWarning
	 * 
	 * @return the showWarning
	 */
	public Boolean getShowWarning() {
		return showWarning;
	}

	/**
	 * Method that sets the showWarning
	 * 
	 * @param showWarning
	 *            the showWarning to set
	 */
	public void setShowWarning(Boolean showWarning) {
		this.showWarning = showWarning;
	}

	/**
	 * Method that returns the showWarningInvoice
	 * 
	 * @return the showWarningInvoice
	 */
	public Boolean getShowWarningInvoice() {
		return showWarningInvoice;
	}

	/**
	 * Method that sets the showWarningInvoice
	 * 
	 * @param showWarningInvoice
	 *            the showWarningInvoice to set
	 */
	public void setShowWarningInvoice(Boolean showWarningInvoice) {
		this.showWarningInvoice = showWarningInvoice;
	}

	/**
	 * Method that returns the finalSubmit
	 * 
	 * @return the finalSubmit
	 */
	public Boolean getFinalSubmit() {
		return finalSubmit;
	}

	/**
	 * Method that sets the finalSubmit
	 * 
	 * @param finalSubmit
	 *            the finalSubmit to set
	 */
	public void setFinalSubmit(Boolean finalSubmit) {
		this.finalSubmit = finalSubmit;
	}

	/**
	 * @return the addSecondSign
	 */
	public Boolean getAddSecondSign() {
		return addSecondSign;
	}

	/**
	 * @param addSecondSign
	 *            the addSecondSign to set
	 */
	public void setAddSecondSign(Boolean addSecondSign) {
		this.addSecondSign = addSecondSign;
	}

	/**
	 * @return the paymentMethodSection
	 */
	public Boolean getPaymentMethodSection() {
		return paymentMethodSection;
	}

	/**
	 * @param paymentMethodSection
	 *            the paymentMethodSection to set
	 */
	public void setPaymentMethodSection(Boolean paymentMethodSection) {
		this.paymentMethodSection = paymentMethodSection;
	}

	/**
	 * @return the transformationSection
	 */
	public Boolean getTransformationSection() {
		return transformationSection;
	}

	/**
	 * @param transformationSection
	 *            the transformationSection to set
	 */
	public void setTransformationSection(Boolean transformationSection) {
		this.transformationSection = transformationSection;
	}

	public Boolean getCorrespondenceLanguageCheckBox() {
		return correspondenceLanguageCheckBox;
	}

	public void setCorrespondenceLanguageCheckBox(Boolean correspondenceLanguageCheckBox) {
		this.correspondenceLanguageCheckBox = correspondenceLanguageCheckBox;
	}

	/**
	 * Getter method for gsSection
	 * 
	 * @return the gsSection
	 */
	public Boolean getGsSection() {
		return gsSection;
	}

	/**
	 * Setter method for gsSection
	 * 
	 * @param gsSection
	 *            the gsSection to set
	 */
	public void setGsSection(Boolean gsSection) {
		this.gsSection = gsSection;
	}

	/**
	 * Method that returns the signatoryForm
	 * 
	 * @return the signatoryForm
	 */
	public SignatureForm getSignatoryForm() {
		return signatoryForm;
	}

	/**
	 * Method that sets the signatoryForm
	 * 
	 * @param signatoryForm
	 *            the signatoryForm to set
	 */
	public void setSignatoryForm(SignatureForm signatoryForm) {
		this.signatoryForm = signatoryForm;
	}

	/**
	 * Method that returns the secondSignatoryForm
	 * 
	 * @return the secondSignatoryForm
	 */
	public SignatureForm getSecondSignatoryForm() {
		return secondSignatoryForm;
	}

	/**
	 * Method that sets the secondSignatoryForm
	 * 
	 * @param secondSignatoryForm
	 *            the secondSignatoryForm to set
	 */
	public void setSecondSignatoryForm(SignatureForm secondSignatoryForm) {
		this.secondSignatoryForm = secondSignatoryForm;
	}

	/**
	 * Method that returns the markDescription
	 * 
	 * @return the markDescription
	 */
	public String getMarkDescription() {
		return markDescription;
	}

	/**
	 * Method that sets the markDescription
	 * 
	 * @param markDescription
	 *            the markDescription to set
	 */
	public void setMarkDescription(String markDescription) {
		this.markDescription = markDescription;
	}

	/**
	 * Method that returns the markDescriptionSecond
	 * 
	 * @return the markDescriptionSecond
	 */
	public String getMarkDescriptionSecond() {
		return markDescriptionSecond;
	}

	/**
	 * Method that sets the markDescriptionSecond
	 * 
	 * @param markDescriptionSecond
	 *            the markDescriptionSecond to set
	 */
	public void setMarkDescriptionSecond(String markDescriptionSecond) {
		this.markDescriptionSecond = markDescriptionSecond;
	}

	/**
	 * Method that returns the markColorsIndicationSecond
	 * 
	 * @return the markColorsIndicationSecond
	 */
	public String getMarkColorsIndicationSecond() {
		return markColorsIndicationSecond;
	}

	/**
	 * Method that sets the markColorsIndicationSecond
	 * 
	 * @param markColorsIndicationSecond
	 *            the markColorsIndicationSecond to set
	 */
	public void setMarkColorsIndicationSecond(String markColorsIndicationSecond) {
		this.markColorsIndicationSecond = markColorsIndicationSecond;
	}

	/**
	 * @return the langId
	 */
	public String getLangId() {
		return langId;
	}

	/**
	 * @param langId
	 *            the langId to set
	 */
	public void setLangId(String langId) {
		this.langId = langId;
	}

	/**
	 * @return the showErrors
	 */
	public Boolean getShowErrors() {
		return showErrors;
	}

	/**
	 * @param showErrors
	 *            the showErrors to set
	 */
	public void setShowErrors(Boolean showErrors) {
		this.showErrors = showErrors;
	}

	public FileWrapper getTrademarkRegulationDocuments() {
		if (trademarkRegulationDocuments == null) {
			trademarkRegulationDocuments = new FileWrapper();
		}
		return trademarkRegulationDocuments;
	}

	public void setTrademarkRegulationDocuments(FileWrapper trademarkRegulationDocuments) {
		this.trademarkRegulationDocuments = trademarkRegulationDocuments;
	}

	public FileWrapper getTrademarkTranslationDocuments() {
		if (trademarkTranslationDocuments == null) {
			trademarkTranslationDocuments = new FileWrapper();
		}
		return trademarkTranslationDocuments;
	}

	public void setTrademarkTranslationDocuments(FileWrapper trademarkTranslationDocuments) {
		this.trademarkTranslationDocuments = trademarkTranslationDocuments;
	}

	public FileWrapper getTrademarkApplicantDocuments() {
		if (trademarkApplicantDocuments == null) {
			trademarkApplicantDocuments = new FileWrapper();
		}
		return trademarkApplicantDocuments;
	}

	public void setTrademarkApplicantDocuments(FileWrapper trademarkApplicantDocuments) {
		this.trademarkApplicantDocuments = trademarkApplicantDocuments;
	}

	public Boolean getSeriesPresent() {
		return seriesPresent;
	}

	public void setSeriesPresent(Boolean seriesPresent) {
		this.seriesPresent = seriesPresent;
	}

	public String getSeriesNumber() {
		return seriesNumber;
	}

	public void setSeriesNumber(String seriesNumber) {
		this.seriesNumber = seriesNumber;
	}

	/**
	 * @return the collectiveTranslationDoc
	 */
	public Boolean getCollectiveTranslationDoc() {
		return collectiveTranslationDoc;
	}

	/**
	 * @param collectiveTranslationDoc
	 *            the collectiveTranslationDoc to set
	 */
	public void setCollectiveTranslationDoc(Boolean collectiveTranslationDoc) {
		this.collectiveTranslationDoc = collectiveTranslationDoc;
	}

	/**
	 * @return the collectiveRegulationDoc
	 */
	public Boolean getCollectiveRegulationDoc() {
		return collectiveRegulationDoc;
	}

	/**
	 * @param collectiveRegulationDoc
	 *            the collectiveRegulationDoc to set
	 */
	public void setCollectiveRegulationDoc(Boolean collectiveRegulationDoc) {
		this.collectiveRegulationDoc = collectiveRegulationDoc;
	}

	/**
	 * Method that returns the displayedGoods
	 * 
	 * @return the displayedGoods
	 */
	public String getDisplayedGoods() {
		return displayedGoods;
	}

	/**
	 * Method that sets the displayedGoods
	 * 
	 * @param displayedGoods
	 *            the displayedGoods to set
	 */
	public void setDisplayedGoods(String displayedGoods) {
		this.displayedGoods = displayedGoods;
	}

	/**
	 * Method that returns the displayedTerms
	 * 
	 * @return the displayedTerms
	 */
	public String getDisplayedTerms() {
		return displayedTerms;
	}

	/**
	 * Method that sets the displayedTerms
	 * 
	 * @param displayedTerms
	 *            the displayedTerms to set
	 */
	public void setDisplayedTerms(String displayedTerms) {
		this.displayedTerms = displayedTerms;
	}

	/**
	 * Method that returns the soundRepresentation
	 * 
	 * @return the soundRepresentation
	 */
	public String getSoundRepresentation() {
		return soundRepresentation;
	}

	/**
	 * Method that sets the soundRepresentation
	 * 
	 * @param soundRepresentation
	 *            the soundRepresentation to set
	 */
	public void setSoundRepresentation(String soundRepresentation) {
		this.soundRepresentation = soundRepresentation;
	}

	public FileWrapper getSoundFile() {
		if (soundFile == null) {
			soundFile = new FileWrapper();
		}
		return soundFile;
	}

	public void setSoundFile(FileWrapper soundFile) {
		this.soundFile = soundFile;
	}

	/**
	 * Method that returns the wordRepresentation
	 * 
	 * @return the wordRepresentation
	 */
	public String getWordRepresentation() {
		return wordRepresentation;
	}

	/**
	 * Method that sets the wordRepresentation
	 * 
	 * @param wordRepresentation
	 *            the wordRepresentation to set
	 */
	public void setWordRepresentation(String wordRepresentation) {
		this.wordRepresentation = wordRepresentation;
	}

	/**
	 * Method that returns the Terms and Conditions acceptance
	 * 
	 * @return if terms and conditions has been accepted
	 */
	public Boolean getTermsAndConditions() {
		return termsAndConditions;
	}

	/**
	 * Method that sets the wordRepresentation
	 * 
	 * @param termsAndConditions
	 *            the terms and conditions value to be set
	 */
	public void setTermsAndConditions(Boolean termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	/**
	 * @return the claimDivisionalApplication
	 */
	public Boolean getClaimDivisionalApplication() {
		return claimDivisionalApplication;
	}

	/**
	 * @param claimDivisionalApplication
	 *            the claimDivisionalApplication to set
	 */
	public void setClaimDivisionalApplication(Boolean claimDivisionalApplication) {
		this.claimDivisionalApplication = claimDivisionalApplication;
	}

	/**
	 * @return the numberDivisionalApplication
	 */
	public String getNumberDivisionalApplication() {
		return numberDivisionalApplication;
	}

	/**
	 * @param numberDivisionalApplication
	 *            the numberDivisionalApplication to set
	 */
	public void setNumberDivisionalApplication(String numberDivisionalApplication) {
		this.numberDivisionalApplication = numberDivisionalApplication;
	}

	/**
	 * @return the dateDivisionalApplication
	 */
	public Date getDateDivisionalApplication() {
		return DateUtil.cloneDate(dateDivisionalApplication);
	}

	/**
	 * @param dateDivisionalApplication
	 *            the dateDivisionalApplication to set
	 */
	public void setDateDivisionalApplication(Date dateDivisionalApplication) {
		this.dateDivisionalApplication = DateUtil.cloneDate(dateDivisionalApplication);
	}

	public Boolean getDivisionalApplicationImported() {
		return divisionalApplicationImported;
	}

	public void setDivisionalApplicationImported(Boolean divisionalApplicationImported) {
		this.divisionalApplicationImported = divisionalApplicationImported;
	}

	public Boolean getReferenceSection() {
		return referenceSection;
	}

	public void setReferenceSection(Boolean referenceSection) {
		this.referenceSection = referenceSection;
	}

	public Boolean getColourChecked() {
		return colourChecked;
	}

	public void setColourChecked(Boolean colourChecked) {
		this.colourChecked = colourChecked;
	}

	public Boolean getPaymentDataSection() {
		return paymentDataSection;
	}

	public void setPaymentDataSection(Boolean paymentDataSection) {
		this.paymentDataSection = paymentDataSection;
	}

	public Boolean getDivisionalSection() {
		return divisionalSection;
	}

	public void setDivisionalSection(Boolean divisionalSection) {
		this.divisionalSection = divisionalSection;
	}

	public Boolean getOtherAttachments() {
		return otherAttachments;
	}

	public void setOtherAttachments(Boolean otherAttachments) {
		this.otherAttachments = otherAttachments;
	}

	/**
	 * @return the claimPriority
	 */
	public Boolean getClaimPriority() {
		return claimPriority;
	}

	/**
	 * @param claimPriority
	 *            the claimPriority to set
	 */
	public void setClaimPriority(Boolean claimPriority) {
		this.claimPriority = claimPriority;
	}

	/**
	 * @return the claimExhibitionPriority
	 */
	public Boolean getClaimExhibitionPriority() {
		return claimExhibitionPriority;
	}

	/**
	 * @param claimExhibitionPriority
	 *            the claimExhibitionPriority to set
	 */
	public void setClaimExhibitionPriority(Boolean claimExhibitionPriority) {
		this.claimExhibitionPriority = claimExhibitionPriority;
	}

	public List<ColourForm> getColourList() {
		return colourList;
	}

	public void setColourList(List<ColourForm> colourList) {
		this.colourList = colourList;
	}

	/**
	 * Returns the national search report indicator
	 * 
	 * @return
	 */
	public Boolean getNationalSearchReport() {
		return nationalSearchReport;
	}

	/**
	 * Sets the national search report indicator
	 * 
	 * @param nationalSearchReport
	 */
	public void setNationalSearchReport(Boolean nationalSearchReport) {
		this.nationalSearchReport = nationalSearchReport;
	}

	public FileWrapper getFileWrapperMultimedia() {
		if (fileWrapperMultimedia == null) {
			fileWrapperMultimedia = new FileWrapper();
		}
		return fileWrapperMultimedia;
	}

	public void setFileWrapperMultimedia(FileWrapper fileWrapperMultimedia) {
		this.fileWrapperMultimedia = fileWrapperMultimedia;
	}

	public Boolean getCertificateMark() {
		return certificateMark;
	}

	public void setCertificateMark(Boolean certificateMark) {
		this.certificateMark = certificateMark;
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

	public Boolean getForeignRegistrationDataSection() {
		return foreignRegistrationDataSection;
	}

	public void setForeignRegistrationDataSection(Boolean foreignRegistrationDataSection) {
		this.foreignRegistrationDataSection = foreignRegistrationDataSection;
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

	public Boolean getGoodsDescriptionDataSection() {
		return goodsDescriptionDataSection;
	}

	public void setGoodsDescriptionDataSection(Boolean goodsDescriptionDataSection) {
		this.goodsDescriptionDataSection = goodsDescriptionDataSection;
	}
}