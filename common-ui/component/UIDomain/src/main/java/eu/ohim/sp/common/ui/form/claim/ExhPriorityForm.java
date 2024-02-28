/*******************************************************************************
 * * $Id:: ExhPriorityForm.java 49264 2012-10-29 13:23:34Z karalch               $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.claim;

import java.util.Date;

import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

public class ExhPriorityForm extends AbstractImportableForm {

	private static final long serialVersionUID = 1L;
	
	
	private String exhibitionDescription;
	private String exhibitionName;
	private Date firstDate;

	// helper attribute to show warnings
	private Boolean warning;

	private FileWrapper fileWrapper;

	/**
	 * Method that returns the exhibitionName
	 * 
	 * @return the exhibitionName
	 */
	public String getExhibitionName() {
		return exhibitionName;
	}

	/**
	 * Method that sets the exhibitionName
	 * 
	 * @param exhibitionName
	 *            the exhibitionName to set
	 */
	public void setExhibitionName(String exhibitionName) {
		this.exhibitionName = exhibitionName;
	}

	/**
	 * Method that returns the firstDate
	 * 
	 * @return the firstDate
	 */
	public Date getFirstDate() {
		return DateUtil.cloneDate(firstDate);
	}

	/**
	 * Method that sets the firstDate
	 * 
	 * @param firstDate
	 *            the firstDate to set
	 */
	public void setFirstDate(Date firstDate) {
		this.firstDate = DateUtil.cloneDate(firstDate);
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

	public FileWrapper getFileWrapper() {
		if (fileWrapper == null) {
			fileWrapper = new FileWrapper();
		}
		return fileWrapper;
	}

	public void setFileWrapper(FileWrapper fileWrapper) {
		this.fileWrapper = fileWrapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ExhPriorityForm clone() throws CloneNotSupportedException {
		ExhPriorityForm exhPriorityForm = new ExhPriorityForm();
		exhPriorityForm.setFirstDate(firstDate);
		exhPriorityForm.setId(id);
		exhPriorityForm.setExhibitionName(exhibitionName);
		exhPriorityForm.setExhibitionDescription(exhibitionDescription);
		exhPriorityForm.setFileWrapper(fileWrapper);

		return exhPriorityForm;
	}

    public AvailableSection getAvailableSectionName() {
		return AvailableSection.EXHIBITION;
	}

	public String getExhibitionDescription() {
		return exhibitionDescription;
	}

	public void setExhibitionDescription(String exhibitionDescription) {
		this.exhibitionDescription = exhibitionDescription;
	}
}
