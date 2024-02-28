/*******************************************************************************
 * * $Id:: LegalEntityForm.java 54183 2013-01-11 12:03:33Z ionitdi               $
 * *       . * .
 * *     * RRRR  *    Copyright Â© 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * Stores all the necessary information for the Legal Entities
 * 
 * @author ckara & ionitdi
 * 
 */
public class HolderLegalEntityForm extends HolderForm implements Cloneable {
	private static final long serialVersionUID = 1L;

	private static final int value31 = 31;
	
	private String stateOfIncorporation;
	private String legalForm;
	private String businessVatNumber;
	private String countryOfRegistration;

	public HolderLegalEntityForm() {
		setType(HolderKindForm.LEGAL_ENTITY);
	}

	/**
	 * Creates a clone of the original object
	 */
	@Override
	public HolderLegalEntityForm clone() throws CloneNotSupportedException{
		HolderLegalEntityForm legalEntityForm = copyHol(HolderLegalEntityForm.class);

		legalEntityForm.setStateOfIncorporation(stateOfIncorporation);
		legalEntityForm.setLegalForm(legalForm);
		legalEntityForm.setBusinessVatNumber(businessVatNumber);
		legalEntityForm.setCountryOfRegistration(countryOfRegistration);
		return legalEntityForm;
	}

	/**
	 * Method that returns the state of incorporation
	 * 
	 * @return the stateOfIncorporation
	 */
	public String getStateOfIncorporation() {
		return stateOfIncorporation;
	}

	/**
	 * Method that sets the state of incorporation
	 * 
	 * @param stateOfIncorporation
	 *            the stateOfIncorporation to set
	 */
	public void setStateOfIncorporation(String stateOfIncorporation) {
		this.stateOfIncorporation = stateOfIncorporation;
	}

	/**
	 * Method that returns the legal form
	 * 
	 * @return the legalForm
	 */
	public String getLegalForm() {
		return legalForm;
	}

	/**
	 * Method that sets the legal form
	 * 
	 * @param legalForm
	 *            the legalForm to set
	 */
	public void setLegalForm(String legalForm) {
		this.legalForm = legalForm;
	}

	/**
	 * Method that returns the business/vat number
	 * 
	 * @return the businessVatNumber
	 */
	public String getBusinessVatNumber() {
		return businessVatNumber;
	}

	/**
	 * Method that sets the business/vat number
	 * 
	 * @param businessVatNumber
	 *            the businessVatNumber to set
	 */
	public void setBusinessVatNumber(String businessVatNumber) {
		this.businessVatNumber = businessVatNumber;
	}

	/**
	 * Method that returns the countryOfRegistration of the legal entity
	 * 
	 * @return the countryOfRegistration of the legal entity
	 */
	public String getCountryOfRegistration() {
		return countryOfRegistration;
	}

	/**
	 * Method that sets the nationality of the applicant
	 * 
	 * @param countryOfRegistration
	 *            the countryOfRegistration of the legal entity to set
	 */
	public void setCountryOfRegistration(String countryOfRegistration) {
		this.countryOfRegistration = countryOfRegistration;
	}

	public AvailableSection getAvailableSectionName() {
		return AvailableSection.HOLDER_LEGALENTITY;
	}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
			return true;
		}
        if (!(o instanceof HolderLegalEntityForm)) {
			return false;
		}
        if (!super.equals(o)) {
			return false;
		}

        HolderLegalEntityForm that = (HolderLegalEntityForm) o;

        if (businessVatNumber != null ? !businessVatNumber.equals(that.businessVatNumber) : that.businessVatNumber != null) {
			return false;
		}
        if (legalForm != null ? !legalForm.equals(that.legalForm) : that.legalForm != null) {
			return false;
		}
        if (countryOfRegistration != null ? !countryOfRegistration.equals(that.countryOfRegistration) : that.countryOfRegistration != null) {
			return false;
		}
        if (stateOfIncorporation != null ? !stateOfIncorporation.equals(that.stateOfIncorporation) : that.stateOfIncorporation != null) {
			return false;
		}

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = value31 * result + (stateOfIncorporation != null ? stateOfIncorporation.hashCode() : 0);
        result = value31 * result + (legalForm != null ? legalForm.hashCode() : 0);
        result = value31 * result + (businessVatNumber != null ? businessVatNumber.hashCode() : 0);
        result = value31 * result + (countryOfRegistration != null ? countryOfRegistration.hashCode() : 0);
        return result;
    }
}
