/*******************************************************************************
 * * $Id:: PriorityForm.java 49264 2012-10-29 13:23:34Z karalch                  $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.claim;

import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PriorityForm extends AbstractImportableForm {

	private static final long serialVersionUID = 1L;

	private Date date;
	private String number;
	private String country;
	private Boolean warning;

	private FileWrapper fileWrapperCopy;
	private FileWrapper fileWrapperTranslation;
	private FileWrapper filePriorityCertificate;

    private boolean partialPriority;
	
	//Partial claim goods and services
	private List<GoodAndServiceForm> goodsServices = LazyList.decorate(new ArrayList<GoodAndServiceForm>(), FactoryUtils.instantiateFactory(GoodAndServiceForm.class));

	/**
	 * Method that returns the date
	 * 
	 * @return the date
	 */
	public Date getDate() {
		if (date != null) {
			return (Date) date.clone();
		} else {
			return null;
		}
	}

	/**
	 * Method that sets the date
	 * 
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		if (date != null) {
			this.date = (Date) date.clone();
		} else {
			this.date = null;
		}
	}

	/**
	 * Method that returns the number
	 * 
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Method that sets the number
	 * 
	 * @param number
	 *            the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
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
	 * Method that returns the warning
	 * 
	 * @return the warning
	 */
	public Boolean getWarning() {
		return warning;
	}

	/**
	 * Method that sets the warning
	 * 
	 * @param warning
	 *            the warning to set
	 */
	public void setWarning(Boolean warning) {
		this.warning = warning;
	}

	public FileWrapper getFileWrapperCopy() {
		if (fileWrapperCopy == null) {
			fileWrapperCopy = new FileWrapper();
		}
		return fileWrapperCopy;
	}

	public FileWrapper getFileWrapperTranslation() {
		if (fileWrapperTranslation == null) {
			fileWrapperTranslation = new FileWrapper();
		}
		return fileWrapperTranslation;
	}

	public void setFileWrapperCopy(FileWrapper fileWrapperCopy) {
		this.fileWrapperCopy = fileWrapperCopy;
	}

	public void setFileWrapperTranslation(FileWrapper fileWrapperTranslation) {
		this.fileWrapperTranslation = fileWrapperTranslation;
	}

	public FileWrapper getFilePriorityCertificate() {
		if (filePriorityCertificate == null) {
			setFilePriorityCertificate(new FileWrapper());
		}
		return filePriorityCertificate;
	}

	public void setFilePriorityCertificate(FileWrapper filePriorityCertificate) {
		this.filePriorityCertificate = filePriorityCertificate;
	}
	
	/**
	 * @return the goodsServices
	 */
	public List<GoodAndServiceForm> getGoodsServices() {
		return goodsServices;		
	}

	/**
	 * @param goodsServices the goodsServices to set
	 */
	public void setGoodsServices(List<GoodAndServiceForm> goodsServices) {
		this.goodsServices = goodsServices;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public PriorityForm clone() throws CloneNotSupportedException {
		PriorityForm priorityForm = new PriorityForm();
		priorityForm.setCountry(country);
		priorityForm.setDate(date);
		priorityForm.setId(id);
		priorityForm.setNumber(number);
		priorityForm.setImported(getImported());
		priorityForm.setFileWrapperCopy(fileWrapperCopy);
		priorityForm.setFileWrapperTranslation(fileWrapperTranslation);
		priorityForm.setFilePriorityCertificate(filePriorityCertificate);
		priorityForm.setGoodsServices(goodsServices);
        priorityForm.setPartialPriority(partialPriority);

		return priorityForm;
	}
    
    public AvailableSection getAvailableSectionName() {
		return AvailableSection.PRIORITY;
	}

    public boolean getPartialPriority()
    {
        return partialPriority;
    }

    public void setPartialPriority(boolean partialPriority)
    {
        this.partialPriority = partialPriority;
    }
}
