/*******************************************************************************
 * * $Id:: ApplicantForm.java 49264 2012-10-29 13:23:34Z karalch                 $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.contact.AddressForm;

/**
 * Stores all the necessary information for the Applicants
 * 
 * @author ckara & ionitdi
 */
public class ApplicantForm extends PersonForm implements Cloneable {

	private static final long serialVersionUID = 1L;

	private static final int value31 = 31;
	
	private ApplicantKindForm type;

	private String name;

	private String domicile;

	private String domicileCountry;

	private String contactPerson;
	
	private String internalApplicantId;
	
    private boolean applicantIsOwner;
	
    private Boolean ownerWarning;

	/**
	 * New Constructor of the Applicant trough
	 * just the name and the address
	 */

	public ApplicantForm(String name, String domicile){
		this.name = name;
		this.domicile = domicile;
	}
	
	/**
	 * Method that returns the internalApplicantId
	 * 
	 * @return the internalApplicantId of the applicant
	 */
	public String getInternalApplicantId() {
		return internalApplicantId;
	}

	/**
	 * Method that sets the internalApplicantId
	 * 
	 * @param name
	 *            the internalApplicantId of the applicant
	 */
	public void setInternalApplicantId(String internalApplicantId) {
		this.internalApplicantId = internalApplicantId;
	}

	public ApplicantForm() {
		setAddress(new AddressForm());
		setPersonIdentifierForm(new PersonIdentifierForm());
	}

	/**
	 * Method that returns the name
	 * 
	 * @return the name of the applicant
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method that sets the name
	 * 
	 * @param name
	 *            the name of the applicant
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method that returns the type
	 * 
	 * @return the type
	 */
	public ApplicantKindForm getType() {
		return type;
	}

	/**
	 * Method that sets the type
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(ApplicantKindForm type) {
		this.type = type;
	}

	/**
	 * Method that returns the domicile country
	 * 
	 * @return the domicile country
	 */
	public String getDomicileCountry() {
		return domicileCountry;
	}

	/**
	 * Method that sets the domicile country
	 * 
	 * @param domicileCountry
	 *            the domicile country to set
	 */
	public void setDomicileCountry(String domicileCountry) {
		this.domicileCountry = domicileCountry;
	}

	public <T extends ApplicantForm> T copyApp(Class<T> clazz) {
        try
        {
		T t = copy(clazz);

		t.setDomicileCountry(domicileCountry);
		t.setDomicile(getDomicile());
		t.setType(type);
		t.setName(getName());
		t.setType(type);
		t.setContactPerson(contactPerson);
		t.setInternalApplicantId(internalApplicantId);
		t.setCurrentUserIndicator(getCurrentUserIndicator());
		t.setApplicantIsOwner(applicantIsOwner);
		t.setImported(getImported());
		
		return t;
        }
        catch (CloneNotSupportedException e)
        {
            throw new SPException("Clone not supported", e, "error.form.cloneNotSupported");
        }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ApplicantForm clone() throws CloneNotSupportedException{
		return copyApp(ApplicantForm.class);
	}

	public String getDomicile() {
		return domicile;
	}

	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}

	/**
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson
	 *            the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

    /**
	 * @return the ownerWarning
	 */
	public Boolean getOwnerWarning() {
		return ownerWarning;
	}

	/**
	 * @param ownerWarning the ownerWarning to set
	 */
	public void setOwnerWarning(Boolean ownerWarning) {
		this.ownerWarning = ownerWarning;
	}

	@Override
    public boolean equals(Object o)
    {
        if (this == o) {
			return true;
		}
        if (!(o instanceof ApplicantForm)) {
			return false;
		}
        if (!super.equals(o)) {
			return false;
		}

        ApplicantForm that = (ApplicantForm) o;

        if (contactPerson != null ? !contactPerson.equals(that.contactPerson) : that.contactPerson != null) {
			return false;
		}
        if (domicile != null ? !domicile.equals(that.domicile) : that.domicile != null) {
			return false;
		}
        if (domicileCountry != null ? !domicileCountry.equals(that.domicileCountry) : that.domicileCountry != null) {
			return false;
		}
        if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
        if (type != that.type) {
			return false;
		}
        if (internalApplicantId != that.internalApplicantId) {
			return false;
		}

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = value31 * result + (type != null ? type.hashCode() : 0);
        result = value31 * result + (name != null ? name.hashCode() : 0);
        result = value31 * result + (domicile != null ? domicile.hashCode() : 0);
        result = value31 * result + (domicileCountry != null ? domicileCountry.hashCode() : 0);
        result = value31 * result + (contactPerson != null ? contactPerson.hashCode() : 0);
        result = value31 * result + (internalApplicantId != null ? internalApplicantId.hashCode() : 0);
        return result;
    }

	public boolean isApplicantIsOwner() {
		return applicantIsOwner;
	}

	public void setApplicantIsOwner(boolean applicantIsOwner) {
		this.applicantIsOwner = applicantIsOwner;
	}
}
