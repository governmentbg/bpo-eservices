/*******************************************************************************
 * * $Id:: FileWrapper.java 49264 2012-10-29 13:23:34Z karalch                   $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;


/**
 * Wrapper class that is used to store and manage information about fileuploads
 * It has a reference to the actual filename. It can store a list of {@link StoredFile}
 * @author karalch
 *
 */
public class FileWrapper implements Serializable {

	/** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8550569187847604862L;

	/** Default value of maximum file upload if otherwise is not specified by configuration. */
	private static final int DEFAULT_MAXIMUM_FILES = 1;

    /** Temp property for storing and transferring the description of the stored file. */
	private String description;
	
	/** Property for the label code of file upload that will be resolved by message source handler. */
	private String labelCode;
	
	private String sectionId;

    /** Property for showing or not the label before the file button. */
    private Boolean showPreLabelInputFileButton;

    /** Property for holding the classes of the span input file tag. */
    private String inputFileClass;

    /** Property for the label code of file upload button that will be resolved by message source handler. */
    private String inputFileLabelCode;

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	/** The path filename. It is used by MultipartFileForm to have one common upload controller for file upload */
	private String pathFilename;

	/** The filename. */
	private String filename;
	
	/** The file type */
	private String docType;
	
	/** Helper field that specifies if the attachments are attached or to follow. */
	private Boolean attachment = Boolean.FALSE;

	private FileAttachmentStatus attachmentStatus = FileAttachmentStatus.NOT_ATTACHED;

	private Boolean toFollowWarning;
	
	/** It specifies the maximum files that can be uploaded to the specific file wrapper. */
	private Integer maximumFiles;
	
	/** A list of all the stored files stored for the current file wrapper. */
	private List<StoredFile> storedFiles = LazyList.decorate(new ArrayList<StoredFile>(),
			FactoryUtils.instantiateFactory(StoredFile.class));

	private Boolean thumbnail;
	
	/**
	 * Instantiates a new file wrapper.
	 */
	public FileWrapper() {
		this.maximumFiles = DEFAULT_MAXIMUM_FILES;

		attachment = Boolean.FALSE;
		attachmentStatus = FileAttachmentStatus.NOT_ATTACHED;
		thumbnail = Boolean.FALSE;
		toFollowWarning = Boolean.FALSE;
	}

	/**
	 * Gets the label code.
	 *
	 * @return the label code
	 */
	public String getLabelCode() {
		return labelCode;
	}

	/**
	 * Sets the label code.
	 *
	 * @param labelCode the new label code
	 */
	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	/**
	 * Gets the path filename.
	 *
	 * @return the path filename
	 */
	public String getPathFilename() {
		return pathFilename;
	}

	/**
	 * Sets the path filename.
	 *
	 * @param pathFilename the new path filename
	 */
	public void setPathFilename(String pathFilename) {
		this.pathFilename = pathFilename;
	}

	/**
	 * Gets the attachment.
	 *
	 * @return the attachment
	 */
	public Boolean getAttachment() {
		return attachment;
	}

	/**
	 * Sets the attachment.
	 *
	 * @param attachment the new attachment
	 */
	public void setAttachment(Boolean attachment) {
		if(attachment != null &&  attachment){
			attachmentStatus = FileAttachmentStatus.ATTACHED;
		} else {
			attachmentStatus = FileAttachmentStatus.NOT_ATTACHED;
		}

		this.attachment = attachment;
	}

	/**
	 * Gets the stored files.
	 *
	 * @return the stored files
	 */
	public List<StoredFile> getStoredFiles() {
		return storedFiles;
	}

	/**
	 * Sets the stored files.
	 *
	 * @param storedFiles the new stored files
	 */
	public void setStoredFiles(List<StoredFile> storedFiles) {
		this.storedFiles = storedFiles;
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
	 * Gets the maximum files.
	 *
	 * @return the maximum files
	 */
	public Integer getMaximumFiles() {
		return maximumFiles;
	}

	/**
	 * Sets the maximum files.
	 *
	 * @param maximumFiles the new maximum files
	 */
	public void setMaximumFiles(Integer maximumFiles) {
		this.maximumFiles = maximumFiles;
	}

    /**
     * Gets if the label before the file button should be shown
     *
     * @return true if the label before the file button should be shown
     */
    public Boolean getShowPreLabelInputFileButton() {
        return showPreLabelInputFileButton;
    }

    /**
     * Set if the label before the file button should be shown
     *
     * @param showPreLabelInputFileButton if the label before the file button should be shown
     */
    public void setShowPreLabelInputFileButton(Boolean showPreLabelInputFileButton) {
        this.showPreLabelInputFileButton = showPreLabelInputFileButton;
    }

    /**
     * Get the input file classes
     *
     * @return the input file classes
     */
    public String getInputFileClass() {
        return inputFileClass;
    }

    /**
     * Set the input file classes
     *
     * @param inputFileClass the input file classes
     */
    public void setInputFileClass(String inputFileClass) {
        this.inputFileClass = inputFileClass;
    }

    /**
     * Get the input file label code
     * @return the input file label code
     */
    public String getInputFileLabelCode() {
        return inputFileLabelCode;
    }

    /**
     * Set the input file label code
     *
     * @param inputFileLabelCode the input file label code
     */
    public void setInputFileLabelCode(String inputFileLabelCode) {
        this.inputFileLabelCode = inputFileLabelCode;
    }
    
    /**
     * Get the document type
     * @return the document type
     */
    public String getDocType() {
		return docType;
	}

    /**
     * Set the document type
     *
     * @param docType the input document type
     */    
	public void setDocType(String docType) {
		this.docType = docType;
	}

	public Boolean getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Boolean thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	/**
	 * @return the toFollowWarning
	 */
	public Boolean getToFollowWarning() {
		return toFollowWarning;
	}

	/**
	 * @param toFollowWarning the toFollowWarning to set
	 */
	public void setToFollowWarning(Boolean toFollowWarning) {
		this.toFollowWarning = toFollowWarning;
	}


	public FileAttachmentStatus getAttachmentStatus() {
		return attachmentStatus;
	}

	public void setAttachmentStatus(FileAttachmentStatus attachmentStatus) {
		if(attachmentStatus != null){
			switch (attachmentStatus){
				case ATTACHED: this.attachment = true; break;
				default: this.attachment = false; break;
			}
		} else {
			this.attachment = false;
		}

		this.attachmentStatus = attachmentStatus;
	}

	/* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
	@Override
	public String toString() {
		return "FileWrapper [DEFAULT_MAXIMUM_FILES=" + DEFAULT_MAXIMUM_FILES + ", description=" + description
				+ ", labelCode=" + labelCode + ", pathFilename=" + pathFilename + ", filename=" + filename
				+ ", attachment=" + attachment + ", maximumFiles=" + maximumFiles + ", storedFiles=" + storedFiles
                + ", showPreLabelInputFileButton=" + showPreLabelInputFileButton + ", inputFileClass=" + inputFileClass
                + ", inputFileLabelCode=" + inputFileLabelCode + ", attachmentStatus" + attachmentStatus.getValue() + "]";
	}

}
