/*******************************************************************************
 * * $Id:: NaturalPersonSpecialForm.java 49264 2012-10-29 13:23:34Z karalch      $
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
 * Stores all the necessary information for the Natural Persons Special case
 * 
 * @author ionitdi
 * 
 */
public class NaturalPersonSpecialForm extends ApplicantForm implements Cloneable {
	private static final long serialVersionUID = 1L;

	private String nationality;

	public NaturalPersonSpecialForm() {
		setType(ApplicantKindForm.NATURAL_PERSON_SPECIAL);
	}

	/**
	 * Method returning the nationality
	 * 
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * Method that sets the nationality
	 * 
	 * @param nationality
	 *            the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Override
	public NaturalPersonSpecialForm clone() throws CloneNotSupportedException{
		NaturalPersonSpecialForm naturalPersonSpecial = copyApp(NaturalPersonSpecialForm.class);

		naturalPersonSpecial.setNationality(nationality);
		return naturalPersonSpecial;
	}
	
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.APPLICANT_NATURALPERSONSPECIAL;
	}
}
