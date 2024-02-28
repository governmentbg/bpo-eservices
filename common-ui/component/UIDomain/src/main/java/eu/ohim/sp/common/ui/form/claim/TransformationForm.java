/*******************************************************************************
 * * $Id:: TransformationForm.java 49264 2012-10-29 13:23:34Z karalch            $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.claim;

import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.util.Date;

public class TransformationForm extends AbstractImportableForm {

	private static final long serialVersionUID = 1L;

	private String irNumber;
	private Date cancelationDate;
	private Date irDate;
	private Date priosenioDate;
	private String transformationCountryCode;
	private boolean transformVisibleSection;

	/**
	 * Method that returns the irNumber
	 * 
	 * @return the irNumber
	 */
	public String getIrNumber() {
		return irNumber;
	}

	/**
	 * Method that sets the irNumber
	 * 
	 * @param irNumber
	 *            the irNumber to set
	 */
	public void setIrNumber(String irNumber) {
		this.irNumber = irNumber;
	}

	/**
	 * Method that returns the cancelationDate
	 * 
	 * @return the cancelationDate
	 */
	public Date getCancelationDate() {
		return DateUtil.cloneDate(cancelationDate);
	}

	/**
	 * Method that sets the cancelationDate
	 * 
	 * @param cancelationDate
	 *            the cancelationDate to set
	 */
	public void setCancelationDate(Date cancelationDate) {
		this.cancelationDate = DateUtil.cloneDate(cancelationDate);
	}

	/**
	 * Method that returns the irDate
	 * 
	 * @return the irDate
	 */
	public Date getIrDate() {
		return DateUtil.cloneDate(irDate);
	}

	/**
	 * Method that sets the irDate
	 * 
	 * @param irDate
	 *            the irDate to set
	 */
	public void setIrDate(Date irDate) {
		this.irDate = DateUtil.cloneDate(irDate);
	}

	/**
	 * Method that returns the priosenioDate
	 * 
	 * @return the priosenioDate
	 */
	public Date getPriosenioDate() {
		return DateUtil.cloneDate(priosenioDate);
	}

	/**
	 * Method that sets the priosenioDate
	 * 
	 * @param priosenioDate
	 *            the priosenioDate to set
	 */
	public void setPriosenioDate(Date priosenioDate) {
		this.priosenioDate = DateUtil.cloneDate(priosenioDate);
	}

	/**
	 * Method that returns the transformVisibleSection
	 * 
	 * @return the transformVisibleSection
	 */
	public boolean isTransformVisibleSection() {
		return transformVisibleSection;
	}

	/**
	 * Method that sets the transformVisibleSection
	 * 
	 * @param transformVisibleSection
	 *            the transformVisibleSection to set
	 */
	public void setTransformVisibleSection(boolean transformVisibleSection) {
		this.transformVisibleSection = transformVisibleSection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TransformationForm clone() throws CloneNotSupportedException {
		TransformationForm transformationForm = new TransformationForm();
		transformationForm.setCancelationDate(cancelationDate);
		transformationForm.setId(id);
		transformationForm.setIrDate(irDate);
		transformationForm.setIrNumber(irNumber);
		transformationForm.setPriosenioDate(priosenioDate);
		transformationForm.setImported(getImported());
		transformationForm.setTransformVisibleSection(transformVisibleSection);
		transformationForm.setTransformationCountryCode(transformationCountryCode);
		return transformationForm;
	}
    
    public AvailableSection getAvailableSectionName() {
		return AvailableSection.TRANSFORMATION;
	}

	public String getTransformationCountryCode() {
		return transformationCountryCode;
	}

	public void setTransformationCountryCode(String transformationCountryCode) {
		this.transformationCountryCode = transformationCountryCode;
	}

}
