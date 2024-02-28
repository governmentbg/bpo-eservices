/*******************************************************************************
 * * $Id:: MultipartFileForm.java 49264 2012-10-29 13:23:34Z karalch             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.resources;

import eu.ohim.sp.common.ui.form.MainForm;
import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.form.claim.SeniorityForm;
import eu.ohim.sp.common.ui.form.claim.TransformationForm;
import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import org.springframework.web.multipart.MultipartFile;

/**
 * A spring help form that is used to submit all the different attachments.
 *
 * @author karalch
 */
public class MultipartFileForm {

	/** a MultipartFile for the filePriorityCopy of {@link PriorityForm}. */
	private MultipartFile filePriorityCopy;
	
	/** a MultipartFile for the filePriorityTranslation of {@link PriorityForm}. */
	private MultipartFile filePriorityTranslation;
	
	/** a MultipartFile for the filePriorityCertificate of {@link PriorityForm}. */
	private MultipartFile filePriorityCertificate;
	
	/** a MultipartFile for the fileSeniorityCopy of {@link SeniorityForm}. */
	private MultipartFile fileSeniorityCopy;
	
	/** a MultipartFile for the fileSeniorityTranslation of {@link SeniorityForm}. */
	private MultipartFile fileSeniorityTranslation;
	
	/** a MultipartFile for the fileSeniorityCertificate of {@link SeniorityForm}. */
	private MultipartFile fileSeniorityCertificate;
	
	/** a MultipartFile for the fileDocumentAttachment of {@link TransformationForm}. */
	private MultipartFile fileDocumentAttachment;
	
	/** a MultipartFile for the fileWrapperImage of {@link MainForm}. */
	private MultipartFile fileWrapperImage;

	/** a MultipartFile for the fileWrapperMultimedia of {@link MainForm}. */
	private MultipartFile fileWrapperMultimedia;

	/** a MultipartFile for the soundFile of {@link MainForm}. */
	private MultipartFile soundFile;
	
	/** a MultipartFile for the otherAttachments of {@link MainForm}. */
	private MultipartFile otherAttachments;
	
	/** a MultipartFile for the trademarkRegulationDocuments of {@link MainForm}. */
	private MultipartFile trademarkRegulationDocuments;
	
	/** a MultipartFile for the trademarkRegulationDocuments of {@link MainForm}. */
	private MultipartFile trademarkTranslationDocuments;
	
	/** a MultipartFile for the trademarkApplicantDocuments of {@link MainForm}. */
	private MultipartFile trademarkApplicantDocuments;
	
    /** a MultipartFile for the views of {@link DesignViewForm}. */
    private MultipartFile view;

	/** A file wrapper that contains all the other information. */
	private FileWrapper fileWrapper;
	
	/** The field that is used to retrieve the MultipartFile that has been submitted. */
	private MultipartFile fileForm;
	
    /** a MultipartFile for the fileDivisionalApplication of {@link DSDivisionalApplicationForm}. */
	private MultipartFile fileDivisionalApplication;
	
	/** a MultipartFile for the representativeAttachment of {@link RepresentativeForm}. */
	private MultipartFile representativeAttachment;
	

	private MultipartFile designOfficiaryFiles;
	
	private MultipartFile designNotOfficiaryFiles;
	
	private MultipartFile payLaterAttachment;
	
	//Basis of Opposition
	private MultipartFile gAExplanationGroundsMarksAttachment;
	
	private MultipartFile gAProposalDecideAttachment;
	
	private MultipartFile gAEvidenceAttachment;
	
	private MultipartFile gRExplanationGroundsMarksAttachment;
	
	private MultipartFile gRProposalDecideAttachment;
	
	private MultipartFile gREvidenceAttachment;
	
	private MultipartFile gRReputationClaimAttachment;

	/* Patent efiling specific attachment types*/
	private MultipartFile patentOfficiaryFiles;
	private MultipartFile patentNotOfficiaryFiles;
	private MultipartFile transferContractFiles;
	private MultipartFile smallCompanyFiles;
	private MultipartFile inventorsAreRealFiles;
	private MultipartFile patentDescriptions;
	private MultipartFile patentClaims;
	private MultipartFile patentDrawings;
	private MultipartFile sequencesListing;
	private MultipartFile inventorAttachment;
	private MultipartFile postponementOfPublicationFiles;

	/**
	 * @return the gRReputationClaimAttachment
	 */
	public MultipartFile getgRReputationClaimAttachment() {
		return gRReputationClaimAttachment;
	}

	/**
	 * @param gRReputationClaimAttachment the gRReputationClaimAttachment to set
	 */
	public void setgRReputationClaimAttachment(
			MultipartFile gRReputationClaimAttachment) {
		fileForm = gRReputationClaimAttachment;
		this.gRReputationClaimAttachment = gRReputationClaimAttachment;
	}

	/**
	 * @return the gRExplanationGroundsMarksAttachment
	 */
	public MultipartFile getgRExplanationGroundsMarksAttachment() {
		return gRExplanationGroundsMarksAttachment;
	}

	/**
	 * @param gRExplanationGroundsMarksAttachment the gRExplanationGroundsMarksAttachment to set
	 */
	public void setgRExplanationGroundsMarksAttachment(
			MultipartFile gRExplanationGroundsMarksAttachment) {
		fileForm = gRExplanationGroundsMarksAttachment;
		this.gRExplanationGroundsMarksAttachment = gRExplanationGroundsMarksAttachment;
	}

	/**
	 * @return the gAProposalDecideAttachment
	 */
	public MultipartFile getgAProposalDecideAttachment() {
		return gAProposalDecideAttachment;
	}

	/**
	 * @param gAProposalDecideAttachment the gAProposalDecideAttachment to set
	 */
	public void setgAProposalDecideAttachment(
			MultipartFile gAProposalDecideAttachment) {
		fileForm = gAProposalDecideAttachment;
		this.gAProposalDecideAttachment = gAProposalDecideAttachment;
	}

	/**
	 * @return the gAEvidenceAttachment
	 */
	public MultipartFile getgAEvidenceAttachment() {
		return gAEvidenceAttachment;
	}

	/**
	 * @param gAEvidenceAttachment the gAEvidenceAttachment to set
	 */
	public void setgAEvidenceAttachment(MultipartFile gAEvidenceAttachment) {
		fileForm = gAEvidenceAttachment;
		this.gAEvidenceAttachment = gAEvidenceAttachment;
	}

	/**
	 * @return the gRProposalDecideAttachment
	 */
	public MultipartFile getgRProposalDecideAttachment() {
		return gRProposalDecideAttachment;
	}

	/**
	 * @param gRProposalDecideAttachment the gRProposalDecideAttachment to set
	 */
	public void setgRProposalDecideAttachment(
			MultipartFile gRProposalDecideAttachment) {
		fileForm = gRProposalDecideAttachment;
		this.gRProposalDecideAttachment = gRProposalDecideAttachment;
	}

	/**
	 * @return the gREvidenceAttachment
	 */
	public MultipartFile getgREvidenceAttachment() {
		return gREvidenceAttachment;
	}

	/**
	 * @param gREvidenceAttachment the gREvidenceAttachment to set
	 */
	public void setgREvidenceAttachment(MultipartFile gREvidenceAttachment) {
		fileForm = gREvidenceAttachment;
		this.gREvidenceAttachment = gREvidenceAttachment;
	}

	/**
	 * @return the gAExplanationGroundsMarksAttachment
	 */
	public MultipartFile getgAExplanationGroundsMarksAttachment() {
		return gAExplanationGroundsMarksAttachment;
	}

	/**
	 * @param gAExplanationGroundsMarksAttachment the gAExplanationGroundsMarksAttachment to set
	 */
	public void setgAExplanationGroundsMarksAttachment(
			MultipartFile gAExplanationGroundsMarksAttachment) {
		fileForm = gAExplanationGroundsMarksAttachment;
		this.gAExplanationGroundsMarksAttachment = gAExplanationGroundsMarksAttachment;
	}

	/**
	 * Gets the file priority translation.
	 *
	 * @return the file priority translation
	 */
	public MultipartFile getFilePriorityTranslation() {
		return filePriorityTranslation;
	}
	
	/**
	 * Sets the file priority translation.
	 *
	 * @param filePriorityTranslation the new file priority translation
	 */
	public void setFilePriorityTranslation(MultipartFile filePriorityTranslation) {
		fileForm = filePriorityTranslation;
		this.filePriorityTranslation = filePriorityTranslation;
	}
	
	/**
	 * Gets the file priority copy.
	 *
	 * @return the file priority copy
	 */
	public MultipartFile getFilePriorityCopy() {
		return filePriorityCopy;
	}
	
	/**
	 * Sets the file priority copy.
	 *
	 * @param filePriorityCopy the new file priority copy
	 */
	public void setFilePriorityCopy(MultipartFile filePriorityCopy) {
		fileForm = filePriorityCopy;
		this.filePriorityCopy = filePriorityCopy;
	}
	
	/**
	 * Gets the file seniority copy.
	 *
	 * @return the file seniority copy
	 */
	public MultipartFile getFileSeniorityCopy() {
		return fileSeniorityCopy;
	}
	
	/**
	 * Sets the file seniority copy.
	 *
	 * @param fileSeniorityCopy the new file seniority copy
	 */
	public void setFileSeniorityCopy(MultipartFile fileSeniorityCopy) {
		fileForm = fileSeniorityCopy;
		this.fileSeniorityCopy = fileSeniorityCopy;
	}
	
	/**
	 * Gets the file document attachment.
	 *
	 * @return the file document attachment
	 */
	public MultipartFile getFileDocumentAttachment() {
		return fileDocumentAttachment;
	}
	
	/**
	 * Sets the file document attachment.
	 *
	 * @param fileDocumentAttachment the new file document attachment
	 */
	public void setFileDocumentAttachment(MultipartFile fileDocumentAttachment) {
		fileForm = fileDocumentAttachment;
		this.fileDocumentAttachment = fileDocumentAttachment;
	}
	
	/**
	 * Gets the other attachments.
	 *
	 * @return the other attachments
	 */
	public MultipartFile getOtherAttachments() {
		return otherAttachments;
	}
	
	/**
	 * Sets the other attachments.
	 *
	 * @param otherAttachments the new other attachments
	 */
	public void setOtherAttachments(MultipartFile otherAttachments) {
		fileForm = otherAttachments;
		this.otherAttachments = otherAttachments;
	}
	
	/**
	 * Gets the file form.
	 *
	 * @return the file form
	 */
	public MultipartFile getFileForm() {
		return fileForm;
	}
	
	/**
	 * Gets the file wrapper.
	 *
	 * @return the file wrapper
	 */
	public FileWrapper getFileWrapper() {
		return fileWrapper;
	}
	
	/**
	 * Sets the file wrapper.
	 *
	 * @param fileWrapper the new file wrapper
	 */
	public void setFileWrapper(FileWrapper fileWrapper) {
		this.fileWrapper = fileWrapper;
	}
	
	/**
	 * Gets the file priority certificate.
	 *
	 * @return the file priority certificate
	 */
	public MultipartFile getFilePriorityCertificate() {
		return filePriorityCertificate;
	}
	
	/**
	 * Sets the file priority certificate.
	 *
	 * @param filePriorityCertificate the new file priority certificate
	 */
	public void setFilePriorityCertificate(MultipartFile filePriorityCertificate) {
		fileForm = filePriorityCertificate;
		this.filePriorityCertificate = filePriorityCertificate;
	}
	
	/**
	 * Gets the file seniority translation.
	 *
	 * @return the file seniority translation
	 */
	public MultipartFile getFileSeniorityTranslation() {
		return fileSeniorityTranslation;
	}
	
	/**
	 * Sets the file seniority translation.
	 *
	 * @param fileSeniorityTranslation the new file seniority translation
	 */
	public void setFileSeniorityTranslation(MultipartFile fileSeniorityTranslation) {
		fileForm = fileSeniorityTranslation;
		this.fileSeniorityTranslation = fileSeniorityTranslation;
	}
	
	/**
	 * Gets the file seniority certificate.
	 *
	 * @return the file seniority certificate
	 */
	public MultipartFile getFileSeniorityCertificate() {
		return fileSeniorityCertificate;
	}
	
	/**
	 * Sets the file seniority certificate.
	 *
	 * @param fileSeniorityCertificate the new file seniority certificate
	 */
	public void setFileSeniorityCertificate(MultipartFile fileSeniorityCertificate) {
		fileForm = fileSeniorityCertificate;
		this.fileSeniorityCertificate = fileSeniorityCertificate;
	}
	
	/**
	 * Gets the collective regulations.
	 *
	 * @return the collective regulations
	 */
	public MultipartFile getTrademarkRegulationDocuments() {
		return trademarkRegulationDocuments;
	}
	
	/**
	 * Sets the collective regulations.
	 *
	 * @param trademarkRegulationDocuments the new collective regulations
	 */
	public void setTrademarkRegulationDocuments(MultipartFile trademarkRegulationDocuments) {
		fileForm = trademarkRegulationDocuments;
		this.trademarkRegulationDocuments = trademarkRegulationDocuments;
	}
	
	/**
	 * Gets the collective translations.
	 *
	 * @return the collective translations
	 */
	public MultipartFile getTrademarkTranslationDocuments() {
		return trademarkTranslationDocuments;
	}
	
	/**
	 * Sets the collective translations.
	 *
	 * @param trademarkTranslationDocuments the new collective translations
	 */
	public void setTrademarkTranslationDocuments(MultipartFile trademarkTranslationDocuments) {
		fileForm = trademarkTranslationDocuments;
		this.trademarkTranslationDocuments = trademarkTranslationDocuments;
	}
	
	/**
	 * Gets the collective applicants.
	 *
	 * @return the collective applicants
	 */
	public MultipartFile getTrademarkApplicantDocuments() {
		return trademarkApplicantDocuments;
	}
	
	/**
	 * Sets the collective applicants.
	 *
	 * @param trademarkApplicantDocuments the new collective applicants
	 */
	public void setTrademarkApplicantDocuments(MultipartFile trademarkApplicantDocuments) {
		fileForm = trademarkApplicantDocuments;
		this.trademarkApplicantDocuments = trademarkApplicantDocuments;
	}
	
	/**
	 * Gets the file wrapper image.
	 *
	 * @return the file wrapper image
	 */
	public MultipartFile getFileWrapperImage() {
		return fileWrapperImage;
	}
	
	/**
	 * Sets the file wrapper image.
	 *
	 * @param fileWrapperImage the new file wrapper image
	 */
	public void setFileWrapperImage(MultipartFile fileWrapperImage) {
		fileForm = fileWrapperImage;
		this.fileWrapperImage = fileWrapperImage;
	}

	/**
	 * Gets the file wrapper multimedia.
	 *
	 * @return the file wrapper multimedia
	 */
	public MultipartFile getFileWrapperMultimedia() {
		return fileWrapperMultimedia;
	}

	/**
	 * Sets the file wrapper multimedia.
	 *
	 * @param fileWrapperMultimedia the new file wrapper multimedia
	 */
	public void setFileWrapperMultimedia(MultipartFile fileWrapperMultimedia) {
		fileForm = fileWrapperMultimedia;
		this.fileWrapperMultimedia = fileWrapperMultimedia;
	}

	/**
	 * Gets the sound file.
	 *
	 * @return the sound file
	 */
	public MultipartFile getSoundFile() {
		return soundFile;
	}
	
	/**
	 * Sets the sound file.
	 *
	 * @param soundFile the new sound file
	 */
	public void setSoundFile(MultipartFile soundFile) {
		fileForm = soundFile;
		this.soundFile = soundFile;
	}

    /**
     * Gets the views file.
     *
     * @return the views file
     */
    public MultipartFile getView() {
        return view;
    }

    /**
     * Sets the views file.
     *
     * @param views the new views
     */
    public void setView(MultipartFile view) {
        fileForm = view;
        this.view = view;
    }
    
	/**
	 * Gets the file divisional application file.
	 *
	 * @return the divisional application file.
	 */
	public MultipartFile getFileDivisionalApplication() {
		return fileDivisionalApplication;
	}
	
	/**
	 * Sets the divisional application file.
	 *
	 * @param fileDivisionalApplication the new divisional application file
	 */
	public void setFileDivisionalApplication(MultipartFile fileDivisionalApplication) {
		fileForm = fileDivisionalApplication;
		this.fileDivisionalApplication = fileDivisionalApplication;
	}
	/**
	 * Gets the file document attachment.
	 *
	 * @return the file document attachment
	 */
	public MultipartFile getRepresentativeAttachment() {
		return representativeAttachment;
	}

	public void setRepresentativeAttachment(MultipartFile representativeAttachment) {
		fileForm = representativeAttachment;
		this.representativeAttachment = representativeAttachment;
	}

	public MultipartFile getDesignOfficiaryFiles() {
		return designOfficiaryFiles;
	}

	public void setDesignOfficiaryFiles(MultipartFile designOfficiaryFiles) {
		fileForm = designOfficiaryFiles;
		this.designOfficiaryFiles = designOfficiaryFiles;
	}

	public MultipartFile getDesignNotOfficiaryFiles() {
		return designNotOfficiaryFiles;
	}

	public void setDesignNotOfficiaryFiles(MultipartFile designNotOfficiaryFiles) {
		  fileForm = designNotOfficiaryFiles;
		this.designNotOfficiaryFiles = designNotOfficiaryFiles;
	}

	public MultipartFile getPayLaterAttachment() {
		return payLaterAttachment;
	}

	public void setPayLaterAttachment(MultipartFile payLaterAttachment) {
		  fileForm = payLaterAttachment;
		this.payLaterAttachment = payLaterAttachment;
	}

	public MultipartFile getPatentOfficiaryFiles() {
		return patentOfficiaryFiles;
	}

	public void setPatentOfficiaryFiles(MultipartFile patentOfficiaryFiles) {
		fileForm = patentOfficiaryFiles;
		this.patentOfficiaryFiles = patentOfficiaryFiles;
	}

	public MultipartFile getPatentNotOfficiaryFiles() {
		return patentNotOfficiaryFiles;
	}

	public void setPatentNotOfficiaryFiles(MultipartFile patentNotOfficiaryFiles) {
		fileForm = patentNotOfficiaryFiles;
		this.patentNotOfficiaryFiles = patentNotOfficiaryFiles;
	}

	public MultipartFile getTransferContractFiles() {
		return transferContractFiles;
	}

	public void setTransferContractFiles(MultipartFile transferContractFiles) {
		fileForm = transferContractFiles;
		this.transferContractFiles = transferContractFiles;
	}

	public MultipartFile getPatentDescriptions() {
		return patentDescriptions;
	}

	public void setPatentDescriptions(MultipartFile patentDescriptions) {
		fileForm = patentDescriptions;
		this.patentDescriptions = patentDescriptions;
	}

	public MultipartFile getPatentClaims() {
		return patentClaims;
	}

	public void setPatentClaims(MultipartFile patentClaims) {
		fileForm = patentClaims;
		this.patentClaims = patentClaims;
	}

	public MultipartFile getPatentDrawings() {
		return patentDrawings;
	}

	public void setPatentDrawings(MultipartFile patentDrawings) {
		fileForm = patentDrawings;
		this.patentDrawings = patentDrawings;
	}

	public MultipartFile getSequencesListing() {
		return sequencesListing;
	}

	public void setSequencesListing(MultipartFile sequencesListing) {
		fileForm = sequencesListing;
		this.sequencesListing = sequencesListing;
	}

	public MultipartFile getInventorAttachment() {
		return inventorAttachment;
	}

	public void setInventorAttachment(MultipartFile inventorAttachment) {
		fileForm = inventorAttachment;
		this.inventorAttachment = inventorAttachment;
	}

	public MultipartFile getSmallCompanyFiles() {
		return smallCompanyFiles;
	}

	public void setSmallCompanyFiles(MultipartFile smallCompanyFiles) {
		fileForm = smallCompanyFiles;
		this.smallCompanyFiles = smallCompanyFiles;
	}

	public MultipartFile getInventorsAreRealFiles() {
		return inventorsAreRealFiles;
	}

	public void setInventorsAreRealFiles(MultipartFile inventorsAreRealFiles) {
		fileForm = inventorsAreRealFiles;
		this.inventorsAreRealFiles = inventorsAreRealFiles;
	}

	public MultipartFile getPostponementOfPublicationFiles() {
		return postponementOfPublicationFiles;
	}

	public void setPostponementOfPublicationFiles(MultipartFile postponementOfPublicationFiles) {
		fileForm = postponementOfPublicationFiles;
		this.postponementOfPublicationFiles = postponementOfPublicationFiles;
	}
}
