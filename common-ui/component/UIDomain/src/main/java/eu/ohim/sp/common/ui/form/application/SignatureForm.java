/*******************************************************************************
 * * $Id:: SignatureForm.java 49264 2012-10-29 13:23:34Z karalch                 $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.application;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class SignatureForm extends AbstractImportableForm  implements java.io.Serializable, Cloneable  {

	private static final long serialVersionUID = 1L;

	private String id;
	private String fullName;
	private SignatoryKindForm personCapacity;
	private String position;
	private String signaturePlace;
	private Date date;
	private String taxIdNumber;
	private String email;
	private String userId;
    private String signatureAssociatedText;
	private String formMessages;
	private String formWarnings;
//	private String personAddress;

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * Method that returns the fullName
	 * 
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Method that sets the fullName
	 * 
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Method that returns the personCapacity
	 * 
	 * @return the personCapacity
	 */
	public SignatoryKindForm getPersonCapacity() {
		return personCapacity;
	}

	/**
	 * Method that sets the personCapacity
	 * 
	 * @param personCapacity
	 *            the personCapacity to set
	 */
	public void setPersonCapacity(SignatoryKindForm personCapacity) {
		this.personCapacity = personCapacity;
	}

	/**
	 * Method that returns the signaturePlace
	 * 
	 * @return the signaturePlace
	 */
	public String getSignaturePlace() {
		return signaturePlace;
	}

	/**
	 * Method that sets the signaturePlace
	 * 
	 * @param signaturePlace
	 *            the signaturePlace to set
	 */
	public void setSignaturePlace(String signaturePlace) {
		this.signaturePlace = signaturePlace;
	}

	/**
	 * Method that returns the date
	 * 
	 * @return the date
	 */
	public Date getDate() {
		return DateUtil.cloneDate(date);
	}

	/**
	 * Method that sets the date
	 * 
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = DateUtil.cloneDate(date);
	}
	
	/**
	 * Method that returns the tax ID number
	 *
	 * @return the taxIdNumber
	 */
	public String getTaxIdNumber() {
		return taxIdNumber;
	}

	/**
	 * Method that sets the tax ID number
	 *
	 * @param taxIdNumber the taxIdNumber to set
	 */
	public void setTaxIdNumber(String taxIdNumber) {
		this.taxIdNumber = taxIdNumber;
	}
	
	

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	
	/**
	 * @return the formMessages
	 */
	public String getFormMessages() {
		return formMessages;
	}

	/**
	 * @param formMessages the formMessages to set
	 */
	public void setFormMessages(String formMessages) {
		this.formMessages = formMessages;
	}

	/**
	 * @return the formWarnings
	 */
	public String getFormWarnings() {
		return formWarnings;
	}

	/**
	 * @param formWarnings the formWarnings to set
	 */
	public void setFormWarnings(String formWarnings) {
		this.formWarnings = formWarnings;
	}

	/* (non-Javadoc)
	 * @see Validatable#getAvailableSectionName()
	 */
	@Override
	public AvailableSection getAvailableSectionName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see AbstractForm#clone()
	 */
	@Override
	public AbstractForm clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		SignatureForm signatureForm = new SignatureForm();
		signatureForm.setCheckBdBlocking(getCheckBdBlocking());
		signatureForm.setDate(date);
		signatureForm.setEmail(email);
		signatureForm.setFullName(fullName);
		signatureForm.setId(id);
		signatureForm.setImported(getImported());
		signatureForm.setPersonCapacity(personCapacity);
		signatureForm.setPosition(position);
		signatureForm.setUserId(userId);
		signatureForm.setSignaturePlace(signaturePlace);
		signatureForm.setTaxIdNumber(taxIdNumber);
		signatureForm.setValidateImported(getValidateImported());
		signatureForm.setSignatureAssociatedText(getSignatureAssociatedText());
		return signatureForm;
	}
	
	public boolean isEmpty(){
       return (StringUtils.isBlank(fullName) && StringUtils.isBlank(email) && StringUtils.isBlank(userId) && personCapacity == null && date == null);
   }

    public String getSignatureAssociatedText()
    {
        return signatureAssociatedText;
    }

    public void setSignatureAssociatedText(String signatureAssociatedText)
    {
        this.signatureAssociatedText = signatureAssociatedText;
    }
}
