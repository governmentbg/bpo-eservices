/*******************************************************************************
 * * $Id:: StoredFile.java 52561 2012-12-07 16:48:00Z karalch                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.resources;

import java.io.Serializable;

import eu.ohim.sp.common.ui.fileupload.util.FileUtil;
import eu.ohim.sp.common.ui.validator.Validatable;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * POJO that contains information about a file that has been uploaded to the application
 */
public class StoredFile implements Validatable, Serializable {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 168091757934989217L;
	
	/** The filename with which the file has been uploaded. */
	private String filename;
	
	/** The filename that is used to reference the file internally on the application. */
    private String originalFilename;
    
    /** Any given description that can be given by the user that has uploaded the application. */
    private String description;
    
    private Long fileSize;
    
    /** The content type of the uploaded file. */
    private String contentType;
    
    /** The number of pages of the uploaded file, if it's a document. */
    private Integer numPages;
    
    /** The type of document */
    private AttachmentDocumentType docType;
    
    private String documentId;

    private StoredFile thumbnail;
    
	/**
     * Instantiates a new stored file.
     */
    public StoredFile() {
    }
    
    /**
     * Instantiates a new stored file.
     *
     * @param filename the filename
     * @param originalFilename the original filename
     * @param contentType the content type
     * @param description the description
     */
    public StoredFile(String filename, String originalFilename, String contentType, String description) {
		this.filename = filename;
		this.originalFilename = originalFilename;
		this.contentType = contentType;
		this.description = description;
    }
    
	/**
	 * Gets the filename.
	 *
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	
	/**
	 * Sets the filename.
	 *
	 * @param filename the new filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	/**
	 * Gets the original filename.
	 *
	 * @return the original filename
	 */
	public String getOriginalFilename() {
		return originalFilename;
	}
	
	/**
	 * Sets the original filename.
	 *
	 * @param originalFilename the new original filename
	 */
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the content type.
	 *
	 * @return the content type
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Sets the content type.
	 *
	 * @param contentType the new content type
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.OTHER_ATTACHMENTS;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getCanonicalFileSize() {
		return FileUtil.getCanonicalFileSize(fileSize);
	}

    public Integer getNumPages() {
		return numPages;
	}

	public void setNumPages(Integer numPages) {
		this.numPages = numPages;
	}

	public AttachmentDocumentType getDocType() {
		return docType;
	}

	public void setDocType(AttachmentDocumentType docType) {
		this.docType = docType;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	
	public StoredFile getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(StoredFile thumbnail) {
		this.thumbnail = thumbnail;
	}

	
}
