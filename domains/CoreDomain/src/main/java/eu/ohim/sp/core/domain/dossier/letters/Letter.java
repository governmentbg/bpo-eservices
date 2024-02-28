package eu.ohim.sp.core.domain.dossier.letters;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.dossier.letters.enums.LetterStatusKind;
import eu.ohim.sp.core.domain.dossier.letters.enums.LetterRecipientKind;
import eu.ohim.sp.core.domain.dossier.letters.enums.LetterTypeSendMethod;
import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

/**
 * Class that represents a Letter involved in the TM Backoffice process of
 * validation and manage a Trademark
 * 
 * @author masjose
 * 
 */
public class Letter extends Id implements Serializable {

	/** Serial id **/
	private static final long serialVersionUID = -5978847645324506262L;

	private int idDossier;
	private LetterStatusKind status;
	private Date sendDate;
	private Date receiveDate;
	private String classificationLetter;
	private String typeLetter;
	private LetterTemplate templateLetter;
	private LetterTypeSendMethod typeMethod;
	private String nameSender;
	private boolean read;
	private String idExternalReference;
	private String recipientFullName;
	private LetterRecipientKind typeRecipient;
	private Recipient recipient;

	// Audit fields
	private Date creationDate;
	private Date lastUpdateDate;
	private String userCreatedBy;
	private String userLastModifiedBy;

	private List<AttachedDocument> letterDocuments;

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
	 * @return the status
	 */
	public LetterStatusKind getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(LetterStatusKind status) {
		this.status = status;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return DateUtil.cloneDate(creationDate);
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = DateUtil.cloneDate(creationDate);
	}

	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate() {
		return DateUtil.cloneDate(lastUpdateDate);
	}

	/**
	 * @param lastUpdateDate
	 *            the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = DateUtil.cloneDate(lastUpdateDate);
	}

	/**
	 * @return the sendDate
	 */
	public Date getSendDate() {
		return DateUtil.cloneDate(sendDate);
	}

	/**
	 * @param sendDate
	 *            the sendDate to set
	 */
	public void setSendDate(Date sendDate) {
		this.sendDate = DateUtil.cloneDate(sendDate);
	}

	/**
	 * @return the receiveDate
	 */
	public Date getReceiveDate() {
		return DateUtil.cloneDate(receiveDate);
	}

	/**
	 * @param receiveDate
	 *            the receiveDate to set
	 */
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = DateUtil.cloneDate(receiveDate);
	}

	/**
	 * @return the classificationLetter
	 */
	public String getClassificationLetter() {
		return classificationLetter;
	}

	/**
	 * @param classificationLetter
	 *            the classificationLetter to set
	 */
	public void setClassificationLetter(String classificationLetter) {
		this.classificationLetter = classificationLetter;
	}

	/**
	 * @return the typeLetter
	 */
	public String getTypeLetter() {
		return typeLetter;
	}

	/**
	 * @param typeLetter
	 *            the typeLetter to set
	 */
	public void setTypeLetter(String typeLetter) {
		this.typeLetter = typeLetter;
	}

	/**
	 * @return the idRecipient
	 */
	public Recipient getRecipient() {
		return recipient;
	}

	/**
	 * @param recipient
	 *            the idRecipient to set
	 */
	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	/**
	 * @return the idTemplateLetter
	 */
	public LetterTemplate getTemplateLetter() {
		return templateLetter;
	}

	/**
	 * @param templateLetter
	 *            the idTemplateLetter to set
	 */
	public void setTemplateLetter(LetterTemplate templateLetter) {
		this.templateLetter = templateLetter;
	}

	/**
	 * @return the typeMethod
	 */
	public LetterTypeSendMethod getTypeMethod() {
		return typeMethod;
	}

	/**
	 * @param typeMethod
	 *            the typeMethod to set
	 */
	public void setTypeMethod(LetterTypeSendMethod typeMethod) {
		this.typeMethod = typeMethod;
	}

	/**
	 * @return the nameSender
	 */
	public String getNameSender() {
		return nameSender;
	}

	/**
	 * @param nameSender
	 *            the nameSender to set
	 */
	public void setNameSender(String nameSender) {
		this.nameSender = nameSender;
	}

	/**
	 * @return the read
	 */
	public boolean isRead() {
		return read;
	}

	/**
	 * @param read
	 *            the read to set
	 */
	public void setRead(boolean read) {
		this.read = read;
	}

	/**
	 * @return the idExternalReference
	 */
	public String getIdExternalReference() {
		return idExternalReference;
	}

	/**
	 * @param idExternalReference
	 *            the idExternalReference to set
	 */
	public void setIdExternalReference(String idExternalReference) {
		this.idExternalReference = idExternalReference;
	}

	/**
	 * @return the recipientFullName
	 */
	public String getRecipientFullName() {
		return recipientFullName;
	}

	/**
	 * @param recipientFullName
	 *            the recipientFullName to set
	 */
	public void setRecipientFullName(String recipientFullName) {
		this.recipientFullName = recipientFullName;
	}

	/**
	 * @return the typeRecipient
	 */
	public LetterRecipientKind getTypeRecipient() {
		return typeRecipient;
	}

	/**
	 * @param typeRecipient
	 *            the typeRecipient to set
	 */
	public void setTypeRecipient(LetterRecipientKind typeRecipient) {
		this.typeRecipient = typeRecipient;
	}

	/**
	 * @return the userCreatedBy
	 */
	public String getUserCreatedBy() {
		return userCreatedBy;
	}

	/**
	 * @param userCreatedBy
	 *            the userCreatedBy to set
	 */
	public void setUserCreatedBy(String userCreatedBy) {
		this.userCreatedBy = userCreatedBy;
	}

	/**
	 * @return the userLastModifiedBy
	 */
	public String getUserLastModifiedBy() {
		return userLastModifiedBy;
	}

	/**
	 * @param userLastModifiedBy
	 *            the userLastModifiedBy to set
	 */
	public void setUserLastModifiedBy(String userLastModifiedBy) {
		this.userLastModifiedBy = userLastModifiedBy;
	}

	/**
	 * @return the letterDocuments
	 */
	public List<AttachedDocument> getLetterDocuments() {
		return letterDocuments;
	}

	/**
	 * @param letterDocuments
	 *            the letterDocuments to set
	 */
	public void setLetterDocuments(List<AttachedDocument> letterDocuments) {
		this.letterDocuments = letterDocuments;
	}

}