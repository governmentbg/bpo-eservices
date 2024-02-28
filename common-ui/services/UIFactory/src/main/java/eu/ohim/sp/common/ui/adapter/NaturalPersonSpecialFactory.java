/*******************************************************************************
 * * $Id:: NaturalPersonSpecialFactory.java 113496 2013-04-22 15:03:04Z karalch  $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.person.NaturalPersonSpecialForm;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;

@Component
public class NaturalPersonSpecialFactory extends ApplicantAbstractFactory<NaturalPersonSpecialForm> {

	/**
	 * Receives as a parameter a UI NaturalPersonSpecialForm object and converts
	 * it to a core Applicant object.
	 * 
	 * @param form
	 *            the UI NaturalPersonSpecialForm to convert
	 * @return the core applicant object
	 */
	@Override
	public Applicant convertTo(NaturalPersonSpecialForm form) {
		if (form == null) {
			return new Applicant();
		}

		Applicant core = internalUiApplicantToCoreApplicant(form);

		core.setNationality(uiNationalityToCoreNationality(form.getNationality()));

		if (core.getName() != null) {
			core.getName().setLastName(form.getName());
		} else {
			PersonName personName = new PersonName();
			personName.setLastName(form.getName());
			core.setName(personName);
		}

		core.setKind(PersonKind.NATURAL_PERSON_SPECIAL);
		return core;
	}

	/**
	 * Receives as a parameter a core {@link Applicant} object and converts it to a
	 * {@link NaturalPersonSpecialForm} object.
	 *
	 * @param applicant the applicant to convert
	 * @return the natural person special form object
	 */
	@Override
	public NaturalPersonSpecialForm convertFrom(Applicant applicant) {
		if (applicant == null) {
			return new NaturalPersonSpecialForm();
		}
		NaturalPersonSpecialForm form = createSubApplicant(internalCoreApplicantToUIApplicant(applicant), NaturalPersonSpecialForm.class);

		form.setNationality(coreNationalityToUiNationality(applicant.getNationality()));

		if (applicant.getName() != null && StringUtils.isNotEmpty(applicant.getName().getLastName())) {
			form.setName(applicant.getName().getLastName());
		}

		return form;
	}

}
