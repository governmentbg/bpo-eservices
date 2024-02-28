/*******************************************************************************
 * * $Id:: LegalPractitionerForm.java 57627 2013-02-25 15:58:05Z ionitdi         $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.apache.commons.lang.StringUtils;

/**
 * Stores all the necessary information for the Legal Practitioners
 *
 * @author ckara & ionitdi
 *
 */
public class LegalPractitionerForm extends RepresentativeForm {

	private static final long serialVersionUID = 1L;

	private static final int value31 = 31;
	
	private LegalPractitionerType legalPractitionerType;

	private String firstName;
	private String middleName;
	private String surname;

	private String associationId;
	private String associationName;

	public LegalPractitionerForm() {
		setType(RepresentativeKindForm.LEGAL_PRACTITIONER);
        legalPractitionerType = LegalPractitionerType.LEGAL_PRACTITIONER;
	}

	/**
	 * Method that returns the surname
	 *
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Method that sets the surname
	 *
	 * @param surname
	 *            the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Method that returns the association id
	 *
	 * @return the association id
	 */
	public String getAssociationId() {
		return associationId;
	}

	/**
	 * Method that sets the association id
	 *
	 * @param associationId
	 *            the association id
	 */
	public void setAssociationId(String associationId) {
		this.associationId = associationId;
	}

	/**
	 * Method that returns the association name
	 *
	 * @return the association name
	 */
	public String getAssociationName() {
		return associationName;
	}

	/**
	 * Method that sets the association name
	 *
	 * @param associationName
	 *            the association name
	 */
	public void setAssociationName(String associationName) {
		this.associationName = associationName;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public LegalPractitionerForm clone() throws CloneNotSupportedException{
		LegalPractitionerForm legalPractitionerForm = copyRep(LegalPractitionerForm.class);

		legalPractitionerForm.setSurname(surname);
		legalPractitionerForm.setFirstName(firstName);
		legalPractitionerForm.setMiddleName(middleName);
		legalPractitionerForm.setLegalPractitionerType(legalPractitionerType);
		legalPractitionerForm.setAssociationId(associationId);
		legalPractitionerForm.setAssociationName(associationName);
		return legalPractitionerForm;
	}

	/**
	 * Gets legal practitioner type
	 * @return legal practitioner type
	 */
	public LegalPractitionerType getLegalPractitionerType() {
		return legalPractitionerType;
	}

	/**
	 *
	 * @param legalPractitionerType
	 */
	public void setLegalPractitionerType(LegalPractitionerType legalPractitionerType) {
		this.legalPractitionerType = legalPractitionerType;
	}

	/**
	 * Gets first name
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 *
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Override
	public String getName() {
        if(legalPractitionerType.equals(LegalPractitionerType.ASSOCIATION))
        {
            return getAssociationName();
        }

		return (StringUtils.isEmpty(this.firstName) ? "" : (this.firstName + " ")) +
				(StringUtils.isEmpty(this.middleName) ? "" : (this.middleName + " ")) +
				(StringUtils.isEmpty(this.surname) ? "" : this.surname);
	}

	@Override
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.REPRESENTATIVE_LEGALPRACTITIONER;
	}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
			return true;
		}
        if (!(o instanceof LegalPractitionerForm)) {
			return false;
		}
        if (!super.equals(o)) {
			return false;
		}

        LegalPractitionerForm that = (LegalPractitionerForm) o;

        if (associationId != null ? !associationId.equals(that.associationId) : that.associationId != null) {
			return false;
		}
        if (associationName != null ? !associationName.equals(that.associationName) : that.associationName != null) {
			return false;
		}
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) {
			return false;
		}
        if (legalPractitionerType != that.legalPractitionerType) {
			return false;
		}
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) {
			return false;
		}

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = value31 * result + (legalPractitionerType != null ? legalPractitionerType.hashCode() : 0);
        result = value31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = value31 * result + (surname != null ? surname.hashCode() : 0);
        result = value31 * result + (associationId != null ? associationId.hashCode() : 0);
        result = value31 * result + (associationName != null ? associationName.hashCode() : 0);
        return result;
    }

    @Override
    public String getOrganization()
    {
        return associationName;
    }
}
