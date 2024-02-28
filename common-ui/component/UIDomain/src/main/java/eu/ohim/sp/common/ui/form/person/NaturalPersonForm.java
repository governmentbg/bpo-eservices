/*******************************************************************************
 * * $Id:: NaturalPersonForm.java 50053 2012-11-07 15:00:55Z ionitdi             $
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

import java.util.Objects;

/**
 * Stores all the necessary information for the Natural Persons
 * 
 * @author ckara & ionitdi
 * 
 */
public class NaturalPersonForm extends ApplicantForm implements Cloneable {
	private static final long serialVersionUID = 1L;

	private String nationality;

	private String title;
	
	private String firstName;
	
	private String middleName;	

	private String surname;

	private boolean designerIndicator;
	
	public NaturalPersonForm() {
		setType(ApplicantKindForm.NATURAL_PERSON);
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
	 * Method that returns the title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Method that sets the title
	 * 
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public NaturalPersonForm clone() throws CloneNotSupportedException {
		NaturalPersonForm naturalPersonForm = copyApp(NaturalPersonForm.class);

		naturalPersonForm.setTitle(title);
		naturalPersonForm.setSurname(surname);
		naturalPersonForm.setNationality(nationality);
		naturalPersonForm.setFirstName(firstName);
		naturalPersonForm.setMiddleName(middleName);
		naturalPersonForm.setDesignerIndicator(designerIndicator);

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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public boolean getDesignerIndicator() {
		return designerIndicator;
	}

	public void setDesignerIndicator(boolean designerIndicator) {
		this.designerIndicator = designerIndicator;
	}	
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.APPLICANT_NATURALPERSON;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof NaturalPersonForm)) return false;
		if (!super.equals(o)) return false;
		NaturalPersonForm that = (NaturalPersonForm) o;
		return designerIndicator == that.designerIndicator &&
			Objects.equals(nationality, that.nationality) &&
			Objects.equals(title, that.title) &&
			Objects.equals(firstName, that.firstName) &&
			Objects.equals(middleName, that.middleName) &&
			Objects.equals(surname, that.surname);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), nationality, title, firstName, middleName, surname, designerIndicator);
	}
}
