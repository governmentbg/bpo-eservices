/*******************************************************************************
 * * $Id:: NaturalPersonForm.java 50053 2012-11-07 15:00:55Z ionitdi             $
 * *       . * .
 * *     * RRRR  *    Copyright Â© 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

/**
 * Stores all the necessary information for the Natural Persons
 * 
 * @author ckara & ionitdi
 * 
 */
public class HolderNaturalPersonForm extends HolderForm implements Cloneable {
	private static final long serialVersionUID = 1L;

	private String nationality;

	private String firstName;
	
	private String middleName;

	private String surname;
	
	private String title;

	public HolderNaturalPersonForm() {
		setType(HolderKindForm.NATURAL_PERSON);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public HolderNaturalPersonForm clone() throws CloneNotSupportedException {
		HolderNaturalPersonForm naturalPersonForm = copyHol(HolderNaturalPersonForm.class);

		naturalPersonForm.setSurname(surname);
		naturalPersonForm.setNationality(nationality);
		naturalPersonForm.setFirstName(firstName);
		naturalPersonForm.setTitle(title);
		naturalPersonForm.setMiddleName(middleName);
		return naturalPersonForm;
	}

	/**
	 * Method that returns the nationality
	 * 
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * Method that gets the nationality
	 * 
	 * @param nationality
	 *            the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	

	@Override
	public String getName() {
		return (StringUtils.isEmpty(this.firstName) ? "" : (this.firstName + " ")) +
				(StringUtils.isEmpty(this.middleName) ? "" : (this.middleName + " ")) +
				(StringUtils.isEmpty(this.surname) ? "" : this.surname);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public AvailableSection getAvailableSectionName() {
		return AvailableSection.HOLDER_NATURALPERSON;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof HolderNaturalPersonForm)) return false;
		if (!super.equals(o)) return false;
		HolderNaturalPersonForm that = (HolderNaturalPersonForm) o;
		return Objects.equals(nationality, that.nationality) &&
			Objects.equals(firstName, that.firstName) &&
			Objects.equals(middleName, that.middleName) &&
			Objects.equals(surname, that.surname) &&
			Objects.equals(title, that.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), nationality, firstName, middleName, surname, title);
	}
}
