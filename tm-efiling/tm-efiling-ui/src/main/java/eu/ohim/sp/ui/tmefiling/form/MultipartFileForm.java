/*******************************************************************************
 * * $Id::                                                                       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.form;

import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import org.springframework.web.multipart.MultipartFile;

/**
 * A spring help form that is used to submit all the different attachments.
 *
 * @author karalch
 */
public class MultipartFileForm {

	/** a MultipartFile for the filePriorityCopy of {@link eu.ohim.sp.common.ui.form.claim.PriorityForm}. */
	private MultipartFile filePriorityCopy;
	
	/** a MultipartFile for the filePriorityTranslation of {@link eu.ohim.sp.common.ui.form.claim.PriorityForm}. */
	private MultipartFile filePriorityTranslation;
	
	/** a MultipartFile for the filePriorityCertificate of {@link eu.ohim.sp.common.ui.form.claim.PriorityForm}. */
	private MultipartFile filePriorityCertificate;
	
	/** a MultipartFile for the fileSeniorityCopy of {@link eu.ohim.sp.common.ui.form.claim.SeniorityForm}. */
	private MultipartFile fileSeniorityCopy;
	
	/** a MultipartFile for the fileSeniorityTranslation of {@link eu.ohim.sp.common.ui.form.claim.SeniorityForm}. */
	private MultipartFile fileSeniorityTranslation;
	
	/** a MultipartFile for the fileSeniorityCertificate of {@link eu.ohim.sp.common.ui.form.claim.SeniorityForm}. */
	private MultipartFile fileSeniorityCertificate;
	
	/** a MultipartFile for the fileDocumentAttachment of {@link eu.ohim.sp.common.ui.form.claim.TransformationForm}. */
	private MultipartFile fileDocumentAttachment;
	
	/** a MultipartFile for the fileWrapperImage of {@link MainForm}. */
	private MultipartFile fileWrapperImage;
	
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
	
	/** A file wrapper that contains all the other information. */
	private FileWrapper fileWrapper;
	
	/** The field that is used to retrieve the MultipartFile that has been submitted. */
	private MultipartFile fileForm;
	
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
}
