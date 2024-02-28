/*******************************************************************************
 * * $Id:: EmployeeRepresentativeForm.java 53367 2012-12-19 13:22:48Z ionitdi    $
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
 * Stores all the necessary information for the employee representatives
 *
 * @author ckara & ionitdi
 */
public class EmployeeRepresentativeForm extends RepresentativeForm implements Cloneable {

	private static final long serialVersionUID = 1L;

	private static final int value31 = 31;
	
	private String nameOfLegalEntity;

	private String firstName;

	private String middleName;

	private String surname;

	private Boolean economicConnections;

	private String natureOfEconomicConnections;

	private String nameOfEmployer;

	public EmployeeRepresentativeForm() {
		setType(RepresentativeKindForm.EMPLOYEE_REPRESENTATIVE);
	}

	/**
	 * Method that returns the middleName
	 *
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Method that sets the middleName
	 *
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
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
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Method that returns the connections
	 *
	 * @return the connections
	 */
	public Boolean getEconomicConnections() {
		return economicConnections;
	}

	/**
	 * Method that sets the economic connections
	 *
	 * @param connections the connections to set
	 */
	public void setEconomicConnections(Boolean connections) {
		this.economicConnections = connections;
	}

	/**
	 * Method that returns the nature of economic connections
	 *
	 * @return the nature of economic connections
	 */
	public String getNatureOfEconomicConnections() {
		return natureOfEconomicConnections;
	}

	/**
	 * Method that sets the nature of economic connections
	 *
	 * @param natureOfEconomicConnections
	 */
	public void setNatureOfEconomicConnections(String natureOfEconomicConnections) {
		this.natureOfEconomicConnections = natureOfEconomicConnections;
	}

	/**
	 * Method that returns the name of employer
	 *
	 * @return the name of employer
	 */
	public String getNameOfEmployer() {
		return nameOfEmployer;
	}

	/**
	 * Method that sets the name of employer
	 *
	 * @param nameOfEmployer
	 */
	public void setNameOfEmployer(String nameOfEmployer) {
		this.nameOfEmployer = nameOfEmployer;
	}

	/*natureOfEconomicConnections
		 * (non-Javadoc)
		 *
		 * @see java.lang.Object#clone()
		 */
	@Override
	public EmployeeRepresentativeForm clone() throws CloneNotSupportedException{
		EmployeeRepresentativeForm employeeRepresentativeForm = copyRep(EmployeeRepresentativeForm.class);

		employeeRepresentativeForm.setEconomicConnections(economicConnections);
		employeeRepresentativeForm.setSurname(surname);
		employeeRepresentativeForm.setMiddleName(middleName);
		employeeRepresentativeForm.setNameOfEmployer(nameOfEmployer);
		employeeRepresentativeForm.setNameOfLegalEntity(nameOfLegalEntity);
		employeeRepresentativeForm.setNatureOfEconomicConnections(natureOfEconomicConnections);
		employeeRepresentativeForm.setFirstName(firstName);
		return employeeRepresentativeForm;
	}

	/**
	 * Gets first name
	 * @return string first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
        return (StringUtils.isEmpty(this.firstName) ? "" : (this.firstName + " ")) +
				(StringUtils.isEmpty(this.middleName) ? "" : (this.middleName + " ")) +
				(StringUtils.isEmpty(this.surname) ? "" : this.surname);
	}

	public AvailableSection getAvailableSectionName() {
		return AvailableSection.REPRESENTATIVE_EMPLOYEEREPRESENTATIVE;
	}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
			return true;
		}
        if (!(o instanceof EmployeeRepresentativeForm)) {
			return false;
		}
        if (!super.equals(o)) {
			return false;
		}

        EmployeeRepresentativeForm that = (EmployeeRepresentativeForm) o;

        if (economicConnections != null ? !economicConnections.equals(that.economicConnections) : that.economicConnections != null) {
			return false;
		}
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) {
			return false;
		}
        if (nameOfEmployer != null ? !nameOfEmployer.equals(that.nameOfEmployer) : that.nameOfEmployer != null) {
			return false;
		}
        if (natureOfEconomicConnections != null ? !natureOfEconomicConnections.equals(
                that.natureOfEconomicConnections) : that.natureOfEconomicConnections != null) {
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
        result = value31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = value31 * result + (surname != null ? surname.hashCode() : 0);
        result = value31 * result + (economicConnections != null ? economicConnections.hashCode() : 0);
        result = value31 * result + (natureOfEconomicConnections != null ? natureOfEconomicConnections.hashCode() : 0);
        result = value31 * result + (nameOfEmployer != null ? nameOfEmployer.hashCode() : 0);
        return result;
    }

    @Override
    public String getOrganization()
    {
        return nameOfEmployer;
    }
}
