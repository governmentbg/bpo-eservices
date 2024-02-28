/*******************************************************************************
 * * $Id:: RepresentativeNaturalPersonForm.java 50053 2012-11-07 15:00:55Z ionit#$
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
 * @author ionitdi
 */
public class RepresentativeNaturalPersonForm extends RepresentativeForm implements Cloneable {
	/**
	 *
	 */
	private static final long serialVersionUID = 7589263893373777748L;

	private static final int value31 = 31;
	
	private String surname;
	private String firstName;
	private String middleName;
	private String title;
	
	public RepresentativeNaturalPersonForm() {
		setType(RepresentativeKindForm.NATURAL_PERSON);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public RepresentativeNaturalPersonForm clone() throws CloneNotSupportedException{
		RepresentativeNaturalPersonForm repForm = copyRep(RepresentativeNaturalPersonForm.class);

		repForm.setFirstName(firstName);
		repForm.setSurname(surname);
		repForm.setMiddleName(middleName);
		repForm.setTitle(title);
		return repForm;
	}

	/**
	 * Gets surname
	 * @return string surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Gets first name
	 * @return String first name
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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	@Override
	public String getName() {
		return (StringUtils.isEmpty(this.firstName) ? "" : (this.firstName + " ")) +
				(StringUtils.isEmpty(this.middleName) ? "" : (this.middleName + " ")) +
				(StringUtils.isEmpty(this.surname) ? "" : this.surname);

	}

	public AvailableSection getAvailableSectionName() {
		return AvailableSection.REPRESENTATIVE_NATURALPERSON;
	}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
			return true;
		}
        if (!(o instanceof RepresentativeNaturalPersonForm)) {
			return false;
		}
        if (!super.equals(o)) {
			return false;
		}

        RepresentativeNaturalPersonForm that = (RepresentativeNaturalPersonForm) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) {
			return false;
		}
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) {
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
        result = value31 * result + (surname != null ? surname.hashCode() : 0);
        result = value31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = value31 * result + (firstName != null ? firstName.hashCode() : 0);
        return result;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
