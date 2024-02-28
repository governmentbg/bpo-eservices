package eu.ohim.sp.core.domain.dossier.annotations;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

/**
 * The Class Annotation. It represents a human annotation associated to a
 * Dossier stored in the TM BackOffice repository. TODO to be reviewed by BO to
 * add an Auditable interface to contains last modifiable dates
 */
public class Annotation extends Id implements Serializable {

	/** Serial Id **/
	private static final long serialVersionUID = -4434120952245996481L;

	/** The annotation text. */
	private int idDossier;
	private String annotationText;
	private String title;
	private String annotationKind;

	// Audit fields
	private Date creationDate;
	private Date lastUpdateDate;
	private String userCreatedBy;
	private String userLastModifiedBy;

	/** The documents. */
	private List<AttachedDocument> documents;

	/**
	 * Gets the annotation text.
	 * 
	 * @return the annotation text
	 */
	public String getAnnotationText() {
		return annotationText;
	}

	/**
	 * Sets the annotation text.
	 * 
	 * @param annotationText
	 *            the new annotation text
	 */
	public void setAnnotationText(String annotationText) {
		this.annotationText = annotationText;
	}

	/**
	 * @return the idDossier
	 */
	public int getIdDossier() {
		return idDossier;
	}

	/**
	 * @param idDossier
	 *            the idDossier to set
	 */
	public void setIdDossier(int idDossier) {
		this.idDossier = idDossier;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the annotationKind
	 */
	public String getAnnotationKind() {
		return annotationKind;
	}

	/**
	 * @param annotationKind
	 *            the annotationKind to set
	 */
	public void setAnnotationKind(String annotationKind) {
		this.annotationKind = annotationKind;
	}

	/**
	 * Gets the creation date.
	 * 
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return DateUtil.cloneDate(creationDate);
	}

	/**
	 * Sets the creation date.
	 * 
	 * @param creationDate
	 *            the new creation date
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = DateUtil.cloneDate(creationDate);
	}

	/**
	 * Gets the last update date.
	 * 
	 * @return the last update date
	 */
	public Date getLastUpdateDate() {
		return DateUtil.cloneDate(lastUpdateDate);
	}

	/**
	 * Sets the last update date.
	 * 
	 * @param lastUpdateDate
	 *            the new last update date
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = DateUtil.cloneDate(lastUpdateDate);
	}

	/**
	 * Gets the user created by.
	 * 
	 * @return the user created by
	 */
	public String getUserCreatedBy() {
		return userCreatedBy;
	}

	/**
	 * Sets the user created by.
	 * 
	 * @param userCreatedBy
	 *            the new user created by
	 */
	public void setUserCreatedBy(String userCreatedBy) {
		this.userCreatedBy = userCreatedBy;
	}

	/**
	 * Gets the user last modified by.
	 * 
	 * @return the user last modified by
	 */
	public String getUserLastModifiedBy() {
		return userLastModifiedBy;
	}

	/**
	 * Sets the user last modified by.
	 * 
	 * @param userLastModifiedBy
	 *            the new user last modified by
	 */
	public void setUserLastModifiedBy(String userLastModifiedBy) {
		this.userLastModifiedBy = userLastModifiedBy;
	}

	/**
	 * Gets the documents.
	 * 
	 * @return the documents
	 */
	public List<AttachedDocument> getDocuments() {
		return documents;
	}

	/**
	 * Sets the documents.
	 * 
	 * @param documents
	 *            the new documents
	 */
	public void setDocuments(List<AttachedDocument> documents) {
		this.documents = documents;
	}
}
