/*******************************************************************************
 * * $Id:: NaturalPersonFactory.java 113496 2013-04-22 15:03:04Z karalch         $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;

@Component
public class NaturalPersonFactory extends ApplicantAbstractFactory<NaturalPersonForm> {

	/**
	 * Receives as a parameter a UI NaturalPersonForm object and converts it to
	 * a core Applicant object.
	 * 
	 * @param form
	 *            the UI NaturalPersonForm to convert
	 * @return the core applicant object
	 */
	@Override
	public Applicant convertTo(NaturalPersonForm form) {
		if (form == null) {
			return new Applicant();
		}

		Applicant core = internalUiApplicantToCoreApplicant(form);
		if (core.getName() == null) {
			PersonName personName = new PersonName();
			core.setName(personName);
		}
		core.getName().setTitle(form.getTitle());
		core.getName().setLastName(form.getSurname());
		core.getName().setFirstName(form.getFirstName());
		core.getName().setMiddleName(form.getMiddleName());
		
		core.setPrivacyWaiver(!form.isConsentForPublishingPersonalInfo());

		core.setNationality(uiNationalityToCoreNationality(form.getNationality()));

		core.setKind(PersonKind.NATURAL_PERSON);
		return core;
	}

	/**
	 * Receives as a parameter a core {@link Applicant} object and converts it to a
	 * {@link NaturalPersonForm} object.
	 *
	 * @param applicant the applicant to convert
	 * @return the natural person form object
	 */
	@Override
	public NaturalPersonForm convertFrom(Applicant applicant) {
		if (applicant == null) {
			return new NaturalPersonForm();
		}

		NaturalPersonForm form = createSubApplicant(internalCoreApplicantToUIApplicant(applicant), NaturalPersonForm.class);
		
		form.setConsentForPublishingPersonalInfo(!applicant.isPrivacyWaiver());
		
		form.setNationality(coreNationalityToUiNationality(applicant.getNationality()));

		if (applicant.getName() != null) {
			PersonName personName = applicant.getName();
			form.setFirstName(personName.getFirstName());
			form.setSurname(personName.getLastName());
			form.setMiddleName(personName.getMiddleName());
			form.setTitle(personName.getTitle());
		}

		return form;
	}

}
