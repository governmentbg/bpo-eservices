/*******************************************************************************
 * * $Id:: PersonForm.java 54183 2013-01-11 12:03:33Z ionitdi                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.contact.CorrespondenceAddressForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ionitdi
 */
public abstract class PersonForm extends AbstractImportableForm implements Cloneable {

	private static final int value31 = 31;

	private AddressForm address;

	private List<CorrespondenceAddressForm> correspondenceAddresses = LazyList.decorate(new ArrayList(),
																		  FactoryUtils.instantiateFactory(CorrespondenceAddressForm.class));

	private String fixed;
	
	private String phone;

	private String fax;

	private String email;

	private String website;

	private String legalForm;

	private NationalIdType nationalIdType;

	private String nationalId;

	private String nationalOfficialBusinessRegister;

	private boolean receiveCorrespondenceViaEmail;

	private String taxIdNumber;

	private boolean consentForPublishingPersonalInfo;

    private boolean partOfEEA;

    private boolean currentUserIndicator;
	
    private boolean hasCorrespondenceAddress;    
    
    private PersonIdentifierForm personIdentifierForm;    

	public <T extends PersonForm> T copy(Class<T> clazz) throws CloneNotSupportedException{
		try {
			T t = clazz.newInstance();
			if(address != null){
				t.setAddress((AddressForm) address.clone());
			}
			t.setConsentForPublishingPersonalInfo(consentForPublishingPersonalInfo);
			t.setReceiveCorrespondenceViaEmail(receiveCorrespondenceViaEmail);
			t.setEmail(email);
			t.setFax(fax);
			t.setId(id);
			t.setNationalOfficialBusinessRegister(nationalOfficialBusinessRegister);
			t.setNationalIdType(nationalIdType);
			t.setNationalId(nationalId);
			t.setPhone(phone);
			t.setFixed(fixed);			
			t.setTaxIdNumber(taxIdNumber);
			t.setWebsite(website);
			t.setLegalForm(legalForm);
			t.setImported(this.getImported());
            t.setPartOfEEA(partOfEEA);
            if (personIdentifierForm != null) {
            	t.setPersonIdentifierForm((PersonIdentifierForm) personIdentifierForm.clone());
            }

			if (correspondenceAddresses != null) {
				for (CorrespondenceAddressForm address : correspondenceAddresses) {
					t.getCorrespondenceAddresses().add((CorrespondenceAddressForm) address.clone());
				}
			}
			return t;
		}
		catch (InstantiationException e) {
			throw new SPException(e);
		}
		catch (IllegalAccessException e) {
			throw new SPException(e);
		}

	}


	/**
	 * Method that returns the address
	 *
	 * @return the address
	 */
	public AddressForm getAddress() {
		return address;
	}

	/**
	 * Method that sets the address
	 *
	 * @param address the address to set
	 */
	public void setAddress(AddressForm address) {
		this.address = address;
	}

	public List<CorrespondenceAddressForm> getCorrespondenceAddresses() {
		return correspondenceAddresses;
	}

	public void setCorrespondenceAddresses(List<CorrespondenceAddressForm> correspondenceAddresses) {
		this.correspondenceAddresses = correspondenceAddresses;
	}

	/**
	 * @return the consentForPublishingPersonalInfo
	 */
	public boolean isConsentForPublishingPersonalInfo() {
		return consentForPublishingPersonalInfo;
	}

	/**
	 * @param consentForPublishingPersonalInfo
	 *         the consentForPublishingPersonalInfo to set
	 */
	public void setConsentForPublishingPersonalInfo(boolean consentForPublishingPersonalInfo) {
		this.consentForPublishingPersonalInfo = consentForPublishingPersonalInfo;
	}

	/**
	 * Method that returns the phone
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Method that sets the phone
	 *
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the fixed
	 */
	public String getFixed() {
		return fixed;
	}

	/**
	 * @param fixed the fixed to set
	 */
	public void setFixed(String fixed) {
		this.fixed = fixed;
	}


	/**
	 * Method that returns the fax
	 *
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Method that sets the fax
	 *
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Method that returns the email
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Method that sets the email
	 *
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Method that returns the website
	 *
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * Method that sets the website
	 *
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * Method that returns the national office business register
	 *
	 * @return the national office business register
	 */
	public String getNationalOfficialBusinessRegister() {
		return nationalOfficialBusinessRegister;
	}

	/**
	 * Method that sets the national office business register
	 *
	 * @param nationalOfficialBusinessRegister
	 *         the national office business register to set
	 */
	public void setNationalOfficialBusinessRegister(String nationalOfficialBusinessRegister) {
		this.nationalOfficialBusinessRegister = nationalOfficialBusinessRegister;
	}

	/**
	 * Method that gets a boolean value specifying if the applicant wishes to
	 * receive correspondence via email
	 *
	 * @return the receiveCorrespondenceViaEmail
	 */
	public boolean isReceiveCorrespondenceViaEmail() {
		return receiveCorrespondenceViaEmail;
	}

	/**
	 * Method that sets whether the applicant wishes to receive correspondence
	 * via email
	 *
	 * @param receiveCorrespondenceViaEmail the value to set
	 */
	public void setReceiveCorrespondenceViaEmail(boolean receiveCorrespondenceViaEmail) {
		this.receiveCorrespondenceViaEmail = receiveCorrespondenceViaEmail;
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


	public void removeEmptyCorrespondenceAddresses() {
		if (correspondenceAddresses.isEmpty()) {
			return;
		}
		List<CorrespondenceAddressForm> toBeRemoved = new ArrayList<CorrespondenceAddressForm>();
		for (CorrespondenceAddressForm a : correspondenceAddresses) {
			if (a.isEmpty()) {
				toBeRemoved.add(a);
			}
		}
		correspondenceAddresses.removeAll(toBeRemoved);
	}

	public AvailableSection getAvailableSectionName() {
		return AvailableSection.PERSON;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PersonForm)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		PersonForm that = (PersonForm) o;

		if (consentForPublishingPersonalInfo != that.consentForPublishingPersonalInfo) {
			return false;
		}
		if (partOfEEA != that.partOfEEA) {
            return false;
        }
		if (receiveCorrespondenceViaEmail != that.receiveCorrespondenceViaEmail) {
			return false;
		}
		if (address != null ? !address.equals(that.address) : that.address != null) {
			return false;
		}
		if (correspondenceAddresses != null) {
			if (!correspondenceAddresses.equals(that.correspondenceAddresses)) {
				return false;
			}
		}
		else {
			if (that.correspondenceAddresses != null && that.correspondenceAddresses.size() != 0) {
				return false;
			}
		}
		if (email != null ? !email.equals(that.email) : that.email != null) {
			return false;
		}
		if (fax != null ? !fax.equals(that.fax) : that.fax != null) {
			return false;
		}
		if (nationalOfficialBusinessRegister != null ? !nationalOfficialBusinessRegister.equals(
				that.nationalOfficialBusinessRegister) : that.nationalOfficialBusinessRegister != null) {
			return false;
		}
		if (phone != null ? !phone.equals(that.phone) : that.phone != null) {
			return false;
		}
		if (taxIdNumber != null ? !taxIdNumber.equals(that.taxIdNumber) : that.taxIdNumber != null) {
			return false;
		}
		if (website != null ? !website.equals(that.website) : that.website != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = value31 * result + (address != null ? address.hashCode() : 0);
		result = value31 * result + (correspondenceAddresses != null && correspondenceAddresses.size() > 0 ? correspondenceAddresses.hashCode() : 0);
		result = value31 * result + (phone != null ? phone.hashCode() : 0);
		result = value31 * result + (fax != null ? fax.hashCode() : 0);
		result = value31 * result + (email != null ? email.hashCode() : 0);
		result = value31 * result + (website != null ? website.hashCode() : 0);
		result = value31 * result + (nationalOfficialBusinessRegister != null ? nationalOfficialBusinessRegister.hashCode() : 0);
		result = value31 * result + (receiveCorrespondenceViaEmail ? 1 : 0);
		result = value31 * result + (taxIdNumber != null ? taxIdNumber.hashCode() : 0);
		result = value31 * result + (consentForPublishingPersonalInfo ? 1 : 0);		
		return result;
	}

    /**
     *
     * @return
     */
    public boolean getPartOfEEA()
    {
        return partOfEEA;
    }

    /**
     *
     * @param partOfEEA
     */
    public void setPartOfEEA(boolean partOfEEA)
    {
        this.partOfEEA = partOfEEA;
    }

    /**
     * Returns whether this person object represents the currently logged-in user details.
     * @return
     */
    public boolean getCurrentUserIndicator()
    {
        return currentUserIndicator;
    }

    /**
     * Sets whether this person object represents the currently logged-in user details.
     * @param currentUserIndicator
     */
    public void setCurrentUserIndicator(boolean currentUserIndicator)
    {
        this.currentUserIndicator = currentUserIndicator;
    }


	public abstract String getName();


	public boolean isHasCorrespondenceAddress() {
		return hasCorrespondenceAddress;
	}


	public void setHasCorrespondenceAddress(boolean hasCorrespondenceAddress) {
		this.hasCorrespondenceAddress = hasCorrespondenceAddress;
	}	
	 
    /**
	 * @return the personIdentifierForm
	 */
	public PersonIdentifierForm getPersonIdentifierForm() {
		return personIdentifierForm;
	}


	/**
	 * @param personIdentifierForm the personIdentifierForm to set
	 */
	public void setPersonIdentifierForm(PersonIdentifierForm personIdentifierForm) {
		this.personIdentifierForm = personIdentifierForm;
	}

	public String getLegalForm() {
		return legalForm;
	}

	public void setLegalForm(String legalForm) {
		this.legalForm = legalForm;
	}

	public NationalIdType getNationalIdType() {
		return nationalIdType;
	}

	public void setNationalIdType(NationalIdType nationalIdType) {
		this.nationalIdType = nationalIdType;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}
}
