/*******************************************************************************
 * * $Id:: RepresentativeLegalEntityForm.java 49264 2012-10-29 13:23:34Z karalch $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * @author ionitdi
 */
public class RepresentativeLegalEntityForm extends RepresentativeForm implements Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9113909264996675776L;

	private static final int value31 = 31;
	
	private String legalForm;
	private String businessVatNumber;
	private String nameOfLegalEntity;

	public RepresentativeLegalEntityForm() {
		setType(RepresentativeKindForm.LEGAL_ENTITY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public RepresentativeForm clone() throws CloneNotSupportedException{
		RepresentativeLegalEntityForm repForm = copyRep(RepresentativeLegalEntityForm.class);

		repForm.setBusinessVatNumber(businessVatNumber);
		repForm.setLegalForm(legalForm);
		repForm.setNameOfLegalEntity(nameOfLegalEntity);
		return repForm;
	}

	/**
	 * Gets legal form
	 * @return String legal form
	 */
	public String getLegalForm() {
		return legalForm;
	}

	/**
	 * 
	 * @param legalForm
	 */
	public void setLegalForm(String legalForm) {
		this.legalForm = legalForm;
	}

	/**
	 * Gets business vat number
	 * @return business vat number
	 */
	public String getBusinessVatNumber() {
		return businessVatNumber;
	}

	/**
	 * 
	 * @param businessVatNumber
	 */
	public void setBusinessVatNumber(String businessVatNumber) {
		this.businessVatNumber = businessVatNumber;
	}

	/**
	 * Gets name of legal entity
	 * @return String 
	 */
	public String getNameOfLegalEntity() {
		return nameOfLegalEntity;
	}

	/**
	 * 
	 * @param nameOfLegalEntity
	 */
	public void setNameOfLegalEntity(String nameOfLegalEntity) {
		this.nameOfLegalEntity = nameOfLegalEntity;
	}

	@Override
	public String getName() {
		return this.nameOfLegalEntity;
	}

	public AvailableSection getAvailableSectionName() {
		return AvailableSection.REPRESENTATIVE_LEGALENTITY;
	}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
			return true;
		}
        if (!(o instanceof RepresentativeLegalEntityForm)) {
			return false;
		}
        if (!super.equals(o)) {
			return false;
		}

        RepresentativeLegalEntityForm that = (RepresentativeLegalEntityForm) o;

        if (businessVatNumber != null ? !businessVatNumber.equals(that.businessVatNumber) : that.businessVatNumber != null) {
			return false;
		}
        if (legalForm != null ? !legalForm.equals(that.legalForm) : that.legalForm != null) {
			return false;
		}
        if (nameOfLegalEntity != null ? !nameOfLegalEntity.equals(that.nameOfLegalEntity) : that.nameOfLegalEntity != null) {
			return false;
		}

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = value31 * result + (legalForm != null ? legalForm.hashCode() : 0);
        result = value31 * result + (businessVatNumber != null ? businessVatNumber.hashCode() : 0);
        result = value31 * result + (nameOfLegalEntity != null ? nameOfLegalEntity.hashCode() : 0);
        return result;
    }
}
