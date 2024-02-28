/*******************************************************************************
 * * $$
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/

package eu.ohim.sp.common.ui.form.design;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.ui.form.resources.FileWrapper;



public class ESDesignDetailsForm extends DesignForm {


	private static final long serialVersionUID = 5219254308047787753L;
	
	private String designIdentifier;
	private String registrationNumber;
	private Date registrationDate;
	private Date expiryDate;
	private DesignStatusCode dsStatus;
	private int numberOfRenewals;
	private FileWrapper representationAttachment;
	private ESDesignApplicationDataForm eSDesignApplicationData;
	private String formMessages;
	private String formWarnings;
	private Boolean locarnoEnabled;
	private Boolean showLocarnoAddClass;
	private Boolean showLocarnoNewProduct;
	private Boolean showLocarnoNewComplexProduct;
	private Boolean renewalIndicator;
	private String imageRepresentationURI;
	private boolean selected = true;
	private Boolean unpublished;
	

	/**
	 * @return the dsStatus
	 */
	public DesignStatusCode getDsStatus() {
		return dsStatus;
	}

	/**
	 * @param dsStatus the dsStatus to set
	 */
	public void setDsStatus(DesignStatusCode dsStatus) {
		this.dsStatus = dsStatus;
	}
	
	/**
	 * @return the eSDesignApplicationData
	 */
	public ESDesignApplicationDataForm geteSDesignApplicationData() {
		return eSDesignApplicationData;
	}

	/**
	 * @param eSDesignApplicationData the eSDesignApplicationData to set
	 */
	public void seteSDesignApplicationData(
			ESDesignApplicationDataForm eSDesignApplicationData) {
		this.eSDesignApplicationData = eSDesignApplicationData;
	}

	/**
	 * @return the registrationNumber
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	/**
	 * @param registrationNumber the registrationNumber to set
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	/**
	 * @return the registrationDate
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * @param registrationDate the registrationDate to set
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
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
	

	/**
	 * @return the designIdentifier
	 */
	public String getDesignIdentifier() {
		return designIdentifier;
	}

	/**
	 * @param designIdentifier the designIdentifier to set
	 */
	public void setDesignIdentifier(String designIdentifier) {
		this.designIdentifier = designIdentifier;
	}

	/**
	 * @return the representationAttachment
	 */
	public FileWrapper getRepresentationAttachment() {
		return representationAttachment;
	}

	/**
	 * @param representationAttachment the representationAttachment to set
	 */
	public void setRepresentationAttachment(FileWrapper representationAttachment) {
		this.representationAttachment = representationAttachment;
	}

	/**
	 * @return the locarnoEnabled
	 */
	public Boolean getLocarnoEnabled() {
		return locarnoEnabled;
	}

	/**
	 * @param locarnoEnabled the locarnoEnabled to set
	 */
	public void setLocarnoEnabled(Boolean locarnoEnabled) {
		this.locarnoEnabled = locarnoEnabled;
	}

	/**
	 * @return the showLocarnoAddClass
	 */
	public Boolean getShowLocarnoAddClass() {
		return showLocarnoAddClass;
	}

	/**
	 * @param showLocarnoAddClass the showLocarnoAddClass to set
	 */
	public void setShowLocarnoAddClass(Boolean showLocarnoAddClass) {
		this.showLocarnoAddClass = showLocarnoAddClass;
	}

	/**
	 * @return the showLocarnoNewProduct
	 */
	public Boolean getShowLocarnoNewProduct() {
		return showLocarnoNewProduct;
	}

	/**
	 * @param showLocarnoNewProduct the showLocarnoNewProduct to set
	 */
	public void setShowLocarnoNewProduct(Boolean showLocarnoNewProduct) {
		this.showLocarnoNewProduct = showLocarnoNewProduct;
	}

	/**
	 * @return the showLocarnoNewComplexProduct
	 */
	public Boolean getShowLocarnoNewComplexProduct() {
		return showLocarnoNewComplexProduct;
	}

	/**
	 * @param showLocarnoNewComplexProduct the showLocarnoNewComplexProduct to set
	 */
	public void setShowLocarnoNewComplexProduct(Boolean showLocarnoNewComplexProduct) {
		this.showLocarnoNewComplexProduct = showLocarnoNewComplexProduct;
	}



	/**
	 * @return the renewalIndicator
	 */
	public Boolean getRenewalIndicator() {
		return renewalIndicator;
	}

	/**
	 * @param renewalIndicator the renewalIndicator to set
	 */
	public void setRenewalIndicator(Boolean renewalIndicator) {
		this.renewalIndicator = renewalIndicator;
	}
	
	public int getNumberOfRenewals() {
		return numberOfRenewals;
	}

	public void setNumberOfRenewals(int numberOfRenewals) {
		this.numberOfRenewals = numberOfRenewals;
	}

	/**
	    * (non-Javadoc)
	    *
	    * @see java.lang.Object#clone()
	    */
		@Override
		public ESDesignDetailsForm clone() {
			ESDesignDetailsForm esDesignDetailsForm = new ESDesignDetailsForm();
			esDesignDetailsForm.setId(id);
			esDesignDetailsForm.seteSDesignApplicationData(eSDesignApplicationData);
			esDesignDetailsForm.setRegistrationDate(registrationDate);
			esDesignDetailsForm.setDsStatus(dsStatus);
			esDesignDetailsForm.setRegistrationNumber(registrationNumber);
			esDesignDetailsForm.setExpiryDate(expiryDate);
			esDesignDetailsForm.setFormMessages(formMessages);
			esDesignDetailsForm.setFormWarnings(formWarnings);
			esDesignDetailsForm.setDesignIdentifier(designIdentifier);
			esDesignDetailsForm.setRepresentationAttachment(representationAttachment);
			esDesignDetailsForm.setLocarnoEnabled(locarnoEnabled);
			esDesignDetailsForm.setShowLocarnoAddClass(showLocarnoAddClass);
			esDesignDetailsForm.setShowLocarnoNewComplexProduct(showLocarnoNewComplexProduct);
			esDesignDetailsForm.setShowLocarnoNewProduct(showLocarnoNewProduct);
			esDesignDetailsForm.setImported(getImported());
			esDesignDetailsForm.setLocarno(locarno);
			esDesignDetailsForm.setRenewalIndicator(renewalIndicator);
			esDesignDetailsForm.setNumberOfRenewals(numberOfRenewals);
			esDesignDetailsForm.setSelected(selected);
			esDesignDetailsForm.setUnpublished(unpublished);
			return esDesignDetailsForm;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((dsStatus == null) ? 0 : dsStatus.hashCode());
			result = prime * result + ((registrationDate == null) ? 0 : registrationDate.hashCode());
			result = prime * result + ((registrationNumber == null) ? 0 : registrationNumber.hashCode());
			result = prime * result + ((eSDesignApplicationData == null) ? 0 : eSDesignApplicationData.hashCode());
			result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
			result = prime * result + ((designIdentifier == null) ? 0 : designIdentifier.hashCode());
			result = prime * result + ((representationAttachment == null) ? 0 : representationAttachment.hashCode());
			result = prime * result + ((showLocarnoAddClass == null) ? 0 : showLocarnoAddClass.hashCode());
			result = prime * result + ((showLocarnoNewComplexProduct == null) ? 0 : showLocarnoNewComplexProduct.hashCode());
			result = prime * result + ((showLocarnoNewProduct == null) ? 0 : showLocarnoNewProduct.hashCode());
			result = prime * result + ((renewalIndicator == null) ? 0 : renewalIndicator.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object o)
		   {
		       if (this == o)
		       {
		           return true;
		       }
		       if (!(o instanceof ESDesignDetailsForm))
		       {
		           return false;
		       }

		       ESDesignDetailsForm that = (ESDesignDetailsForm) o;

		       if (eSDesignApplicationData != null ? !eSDesignApplicationData.equals(that.eSDesignApplicationData) : that.eSDesignApplicationData != null)
		    	   return false;
		       if (dsStatus != null ? !dsStatus.equals(that.dsStatus) : that.dsStatus != null)
		    	   return false;
		       if (registrationDate != null ? !registrationDate.equals(that.registrationDate) : that.registrationDate != null)
		    	   return false;
		       if (registrationNumber != null ? !registrationNumber.equals(that.registrationNumber) : that.registrationNumber != null)
		    	   return false;
		       if (expiryDate != null ? !expiryDate.equals(that.expiryDate) : that.expiryDate != null)
		    	   return false;
		       if (designIdentifier != null ? !designIdentifier.equals(that.designIdentifier) : that.designIdentifier != null)
		    	   return false;
		       if (representationAttachment != null ? !representationAttachment.equals(that.representationAttachment) : that.representationAttachment != null)
		    	   return false;
		       if (showLocarnoAddClass != null ? !showLocarnoAddClass.equals(that.showLocarnoAddClass) : that.showLocarnoAddClass != null)
		    	   return false;
		       if (showLocarnoNewComplexProduct != null ? !showLocarnoNewComplexProduct.equals(that.showLocarnoNewComplexProduct) : that.showLocarnoNewComplexProduct != null)
		    	   return false;
		       if (showLocarnoNewProduct != null ? !showLocarnoNewProduct.equals(that.showLocarnoNewProduct) : that.showLocarnoNewProduct != null)
		    	   return false;
		       if (renewalIndicator != null ? !renewalIndicator.equals(that.renewalIndicator) : that.renewalIndicator != null)
		    	   return false;
		       
		       return true;
		   }
		
		public boolean isEmpty()
		   {
			   return (eSDesignApplicationData.isEmpty() && dsStatus == null && StringUtils.isEmpty(designIdentifier) && StringUtils.isEmpty(registrationNumber) && registrationDate == null
					   && expiryDate==null && representationAttachment==null && renewalIndicator==null);
		   }

		public String getImageRepresentationURI() {
			if(unpublished != null && unpublished){
				if(representationAttachment != null && representationAttachment.getStoredFiles()!= null && representationAttachment.getStoredFiles().size()>0 &&
						representationAttachment.getStoredFiles().get(0).getDocumentId() != null){
					return "getDocument.htm?documentId="+representationAttachment.getStoredFiles().get(0).getDocumentId();
				}
			}
			return imageRepresentationURI;
		}

		public void setImageRepresentationURI(String imageRepresentationURI) {
			this.imageRepresentationURI = imageRepresentationURI;
		}

		public boolean getSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

	public Boolean getUnpublished() {
		return unpublished;
	}

	public void setUnpublished(Boolean unpublished) {
		this.unpublished = unpublished;
	}
}
