/*******************************************************************************
 * * $Id:: SeniorityForm.java 49264 2012-10-29 13:23:34Z karalch                 $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.claim;

import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.util.Date;

/**
 * Form class for the Seniority object.
 */
public class SeniorityForm extends AbstractImportableForm implements Cloneable
{

    private static final long serialVersionUID = 1L;

    private SeniorityKindForm nature;
    private String country;
    private Date priorityDate;
    private Date filingDate;
    private String filingNumber;
    private Date registrationDate;
    private String registrationNumber;

    private FileWrapper fileWrapperCopy;
    @Deprecated
    private FileWrapper fileWrapperTranslation;
    private FileWrapper fileSeniorityCertificate;

	/**
	 * Method that returns the nature
	 * 
	 * @return the nature
	 */
	public SeniorityKindForm getNature() {
		return nature;
	}

	/**
	 * Method that sets the nature
	 * 
	 * @param nature
	 *            the nature to set
	 */
	public void setNature(SeniorityKindForm nature) {
		this.nature = nature;
	}

	/**
	 * Method that returns the country
	 * 
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Method that sets the country
	 * 
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Method that returns the priorityDate
	 * 
	 * @return the priorityDate
	 */
	public Date getPriorityDate() {
		return DateUtil.cloneDate(priorityDate);
	}

	/**
	 * Method that sets the priorityDate
	 * 
	 * @param priorityDate
	 *            the priorityDate to set
	 */
	public void setPriorityDate(Date priorityDate) {
		this.priorityDate = DateUtil.cloneDate(priorityDate);
	}

	/**
	 * Method that returns the filingDate
	 * 
	 * @return the filingDate
	 */
	public Date getFilingDate() {
		return DateUtil.cloneDate(filingDate);
	}

	/**
	 * Method that sets the filingDate
	 * 
	 * @param filingDate
	 *            the filingDate to set
	 */
	public void setFilingDate(Date filingDate) {
		this.filingDate = DateUtil.cloneDate(filingDate);
	}

	/**
	 * Method that returns the filingNumber
	 * 
	 * @return the filingNumber
	 */
	public String getFilingNumber() {
		return filingNumber;
	}

	/**
	 * Method that sets the filingNumber
	 * 
	 * @param filingNumber
	 *            the filingNumber to set
	 */
	public void setFilingNumber(String filingNumber) {
		this.filingNumber = filingNumber;
	}

	/**
	 * Method that returns the registrationDate
	 * 
	 * @return the registrationDate
	 */
	public Date getRegistrationDate() {
		return DateUtil.cloneDate(registrationDate);
	}

	/**
	 * Method that sets the registrationDate
	 * 
	 * @param registrationDate
	 *            the registrationDate to set
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = DateUtil.cloneDate(registrationDate);
	}

	/**
	 * Method that returns the registrationNumber
	 * 
	 * @return the registrationNumber
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	/**
	 * Method that sets the registrationNumber
	 * 
	 * @param registrationNumber
	 *            the registrationNumber to set
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	/**
	 * Method that gets the fileWrapperCopy
	 *
	 * @return the fileWrapperCopy
	 */
	public FileWrapper getFileWrapperCopy() {
		if (fileWrapperCopy == null) {
			fileWrapperCopy = new FileWrapper();
		}
		return fileWrapperCopy;
	}

	/**
	 * Method that gets the fileWrapperTranslation
	 *
	 * @return the fileWrapperTranslation
	 */
	public FileWrapper getFileWrapperTranslation() {
		if (fileWrapperTranslation == null) {
			fileWrapperTranslation = new FileWrapper();
		}
		return fileWrapperTranslation;
	}

	/**
	 * Method that sets the fileWrapperTranslation
	 *
	 * @param fileWrapperCopy the fileWrapperCopy
	 */
	public void setFileWrapperCopy(FileWrapper fileWrapperCopy) {
		this.fileWrapperCopy = fileWrapperCopy;
	}

	/**
	 * Method that sets the fileWrapperTranslation
	 *
	 * @param fileWrapperTranslation the fileWrapperTranslation
	 */
	public void setFileWrapperTranslation(FileWrapper fileWrapperTranslation) {
		this.fileWrapperTranslation = fileWrapperTranslation;
	}

	/**
	 * Method that gets the fileSeniorityCertificate
	 *
	 * @return the fileSeniorityCertificate
	 */
	public FileWrapper getFileSeniorityCertificate() {
		if (fileSeniorityCertificate == null) {
			setFileSeniorityCertificate(new FileWrapper());
		}
		return fileSeniorityCertificate;
	}

	/**
	 * Method that sets the fileSeniorityCertificate
	 *
	 * @param fileSeniorityCertificate the fileSeniorityCertificate
	 */
	public void setFileSeniorityCertificate(FileWrapper fileSeniorityCertificate) {
		this.fileSeniorityCertificate = fileSeniorityCertificate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("country ").append(getCountry()).append("\n").append("filingNumber ").append(getFilingNumber())
				.append("\n").append("registrationNumber ").append(getRegistrationNumber()).append("\n")
				.append("registrationDate ").append(getRegistrationDate()).append("\n").append("filingDate ")
				.append(getFilingDate()).append("\n").append("nature ").append(getNature()).append("\n");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
    @Override
    public SeniorityForm clone() throws CloneNotSupportedException
    {
        SeniorityForm seniorityForm = new SeniorityForm();
        seniorityForm.setCountry(country);
        seniorityForm.setFilingDate(filingDate);
        seniorityForm.setFilingNumber(filingNumber);
        seniorityForm.setId(id);
        seniorityForm.setNature(nature);
        seniorityForm.setPriorityDate(priorityDate);
        seniorityForm.setRegistrationDate(registrationDate);
        seniorityForm.setRegistrationNumber(registrationNumber);
        seniorityForm.setImported(getImported());
        seniorityForm.setFileWrapperCopy(fileWrapperCopy);
        seniorityForm.setFileSeniorityCertificate(fileSeniorityCertificate);

        return seniorityForm;
    }

	/**
	 * {@inheritDoc}
	 */
    public AvailableSection getAvailableSectionName() {
		return AvailableSection.SENIORITY;
	}
}
