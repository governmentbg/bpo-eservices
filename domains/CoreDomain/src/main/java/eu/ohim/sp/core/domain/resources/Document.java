/*
 *  CoreDomain:: Document 15/10/13 14:20 karalch $
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
package eu.ohim.sp.core.domain.resources;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class CommonDocument. Used to store documents for the different projects
 * in the FSP
 *
 * @author jaraful
 */
public class Document extends Id implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -3190760014624001178L;
	
	/** The Constant DOCUMENT_ID. */
	private static final String DOCUMENT_ID = "documentId";

	/**
	 * The data of the file stored in the document
	 */
	private byte[] data;

	/**
	 * The date when the document was created
	 */
	private Date dateCreated;

	/**
	 * The date when the document was received
	 */
	private Date dateReceived;

	/**
	 * The date when the file was attached to the document
	 */
	private Date fileDate;

	/**
	 * The size of the file attached to the document
	 */
	private Long fileSize;

	/**
	 * The custom properties map
	 */
	private Map<String, String> customProperties;
	
	
	/**
	 * Instantiates a new document.
	 */
	public Document() {
		customProperties = new HashMap<String, String>();
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public byte[] getData() {
		return (data!=null?data.clone():null);
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(byte[] data) {
		this.data = (data!=null?data.clone():null);
	}

	/**
	 * Gets the document id.
	 *
	 * @return the document id
	 */
	public String getDocumentId() {
		return (customProperties != null) ? customProperties.get(DOCUMENT_ID) : null;
	}

	/**
	 * Sets the document id.
	 *
	 * @param documentId the new document id
	 */
	public void setDocumentId(String documentId) {
		if(customProperties == null){
			customProperties = new HashMap<String, String>();
		}
		customProperties.put(DOCUMENT_ID, documentId);
	}

	/**
	 * Gets the date created.
	 *
	 * @return the date created
	 */
	public Date getDateCreated() {
		return DateUtil.cloneDate(dateCreated);
	}

	/**
	 * Sets the date created.
	 *
	 * @param dateCreated the new date created
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = DateUtil.cloneDate(dateCreated);
	}

	/**
	 * Gets the date received.
	 *
	 * @return the date received
	 */
	public Date getDateReceived() {
		return DateUtil.cloneDate(dateReceived);
	}

	/**
	 * Sets the date received.
	 *
	 * @param dateReceived the new date received
	 */
	public void setDateReceived(Date dateReceived) {
		this.dateReceived = DateUtil.cloneDate(dateReceived);
	}

	/**
	 * Gets the file date.
	 *
	 * @return the file date
	 */
	public Date getFileDate() {
		return DateUtil.cloneDate(fileDate);
	}

	/**
	 * Sets the file date.
	 *
	 * @param fileDate the new file date
	 */
	public void setFileDate(Date fileDate) {
		this.fileDate = DateUtil.cloneDate(fileDate);
	}

	/**
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return (customProperties != null) ? customProperties.get("comment") : null;
	}

	/**
	 * Sets the comment.
	 *
	 * @param comment the new comment
	 */
	public void setComment(String comment) {
		if(customProperties == null){
			customProperties = new HashMap<String, String>();
		}
		customProperties.put("comment", comment);
	}

	/**
	 * Gets the file format.
	 *
	 * @return the file format
	 */
	public String getFileFormat() {
		return (customProperties != null) ? customProperties.get("fileFormat") : null;
	}

	/**
	 * Sets the file format.
	 *
	 * @param fileFormat the new file format
	 */
	public void setFileFormat(String fileFormat) {
		if(customProperties == null){
			customProperties = new HashMap<String, String>();
		}
		customProperties.put("fileFormat", fileFormat);
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return (customProperties != null) ? customProperties.get("fileName") : null;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName(String fileName) {
		if(customProperties == null){
			customProperties = new HashMap<String, String>();
		}
		customProperties.put("fileName", fileName);
	}

	/**
	 * Gets the file size.
	 *
	 * @return the file size
	 */
	public Long getFileSize() {
		return fileSize;
	}

	/**
	 * Sets the file size.
	 *
	 * @param fileSize the new file size
	 */
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage() {
		return (customProperties != null) ? customProperties.get("language") : null;
	}

	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage(String language) {
		if(customProperties == null){
			customProperties = new HashMap<String, String>();
		}
		customProperties.put("language", language);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return (customProperties != null) ? customProperties.get("name") : null;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		if(customProperties == null){
			customProperties = new HashMap<String, String>();
		}
		customProperties.put("name", name);
	}

	/**
	 * Gets the uri.
	 *
	 * @return the uri
	 */
	public String getUri() {
		return (customProperties != null) ? customProperties.get("uri") : null;
	}

	/**
	 * Sets the uri.
	 *
	 * @param uri the new uri
	 */
	public void setUri(String uri) {
		if(customProperties == null){
			customProperties = new HashMap<String, String>();
		}
		customProperties.put("uri", uri);
	}

	/**
	 * Gets custom properties.
	 *
	 * @return the custom properties
	 */
	public Map<String, String> getCustomProperties() {
		return customProperties;
	}

	/**
	 * Sets custom properties.
	 *
	 * @param customProperties the custom properties
	 */
	public void setCustomProperties(Map<String, String> customProperties) {
		if(this.customProperties == null){
			this.customProperties = new HashMap<String, String>();
		}
		if(customProperties != null) {
			this.customProperties.putAll(customProperties);
		}
	}

	/**
	 * Gets temporary reference.
	 *
	 * @return the temporary reference
	 */
	public String getTemporaryReference() {
		return (customProperties != null) ? customProperties.get("temporaryReference") : null;
	}

	/**
	 * Sets temporary reference.
	 *
	 * @param temporaryReference the temporary reference
	 */
	public void setTemporaryReference(String temporaryReference) {
		if(customProperties == null){
			customProperties = new HashMap<String, String>();
		}
		customProperties.put("temporaryReference", temporaryReference);
	}

	/**
	 * Gets temporary reference.
	 *
	 * @return the module
	 */
	public String getModule() {
		return (customProperties != null) ? customProperties.get("module") : null;
	}

	/**
	 * Sets module
	 *
	 * @param module the module
	 */
	public void setModule(String module) {
		if(customProperties == null){
			customProperties = new HashMap<String, String>();
		}
		customProperties.put("module", module);
	}

    /**
     * Gets the value of the documentIdentifier.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentIdentifier() {
    	return (customProperties != null) ? customProperties.get(DOCUMENT_ID) : null;
    }

    /**
     * Sets the value of the documentIdentifier.
     * 
     * @param documentIdentifier
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentIdentifier(String documentIdentifier) {
    	if(customProperties == null){
			customProperties = new HashMap<String, String>();
		}
		customProperties.put(DOCUMENT_ID, documentIdentifier);
    }
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
		if (o == null || getClass() != o.getClass()) {
            return false;
        }

		Document document = (Document) o;

        String thisDocId = null;
        String thatDocId = null;
        if (customProperties!=null) {
            thisDocId = customProperties.get(DOCUMENT_ID);
        }
        if (document.customProperties!=null) {
            thatDocId = document.customProperties.get(DOCUMENT_ID);
        }

		if (thatDocId == null
                || thisDocId == null
                || !thisDocId.equals(thatDocId)) {
            return false;
        }

		return true;
	}

	@Override
	public int hashCode() {
		return customProperties.hashCode();
	}

	public String getStatus() {
		return (customProperties != null) ? customProperties.get("status") : null;
	}


	public void setStatus(String status) {
		if(customProperties == null){
			customProperties = new HashMap<String, String>();
		}
		customProperties.put("status", status);
	}
}
