package eu.ohim.sp.core.domain.dossier.publications;

import java.io.Serializable;
import java.util.Date;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.resources.Document;

public class Publication extends Id implements Serializable {

	private static final long serialVersionUID = 1L;

	private int dossierId;
	private Document document;
	private String externalRef;
	private String type;
	private PublicationStatus status;
	private String bulletinNumber;
	private String srcPublicationFile;
	private String comment;

	// Audit fields
	private String creatorUser;
	private Date creationDate;
	private Date updatedDate;

	public String getExternalRef() {
		return externalRef;
	}

	public void setExternalRef(String externalRef) {
		this.externalRef = externalRef;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PublicationStatus getStatus() {
		return status;
	}

	public void setStatus(PublicationStatus status) {
		this.status = status;
	}

	public String getBulletinNumber() {
		return bulletinNumber;
	}

	public void setBulletinNumber(String bulletinNumber) {
		this.bulletinNumber = bulletinNumber;
	}

	public String getSrcPublicationFile() {
		return srcPublicationFile;
	}

	public void setSrcPublicationFile(String srcPublicationFile) {
		this.srcPublicationFile = srcPublicationFile;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreatorUser() {
		return creatorUser;
	}

	public void setCreatorUser(String creatorUser) {
		this.creatorUser = creatorUser;
	}

	public Date getCreationDate() {
		return DateUtil.cloneDate(creationDate);
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = DateUtil.cloneDate(creationDate);
	}

	public Date getUpdatedDate() {
		return DateUtil.cloneDate(updatedDate);
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = DateUtil.cloneDate(updatedDate);
	}

	public int getDossierId() {
		return dossierId;
	}

	public void setDossierId(int dossierId) {
		this.dossierId = dossierId;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document pDocument) {
		this.document = pDocument;
	}

}
