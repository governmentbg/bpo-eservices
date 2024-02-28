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

import eu.ohim.sp.common.ui.form.person.UniversityForm;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;

@Component
public class UniversityFactory extends ApplicantAbstractFactory<UniversityForm> {

	/**
	 * Receives as a parameter a UI UniversityForm object and converts it to
	 * a core Applicant object.
	 * 
	 * @param form
	 *            the UI UniversityForm to convert
	 * @return the core applicant object
	 */
	@Override
	public Applicant convertTo(UniversityForm form) {
		if (form == null) {
			return new Applicant();
		}

		Applicant core = internalUiApplicantToCoreApplicant(form);
		if (core.getName() == null) {
			PersonName personName = new PersonName();
			core.setName(personName);
		}
		core.getName().setOrganizationName(form.getName());

		core.setKind(PersonKind.OTHER);
		return core;
	}

	/**
	 * Receives as a parameter a core {@link Applicant} object and converts it to a
	 * {@link UniversityForm} object.
	 *
	 * @param applicant the applicant to convert
	 * @return the natural person form object
	 */
	@Override
	public UniversityForm convertFrom(Applicant applicant) {
		if (applicant == null) {
			return new UniversityForm();
		}

		UniversityForm form = createSubApplicant(internalCoreApplicantToUIApplicant(applicant), UniversityForm.class);

		form.setConsentForPublishingPersonalInfo(!applicant.isPrivacyWaiver());
        if(applicant.getName() != null)
        {
            form.setName(applicant.getName().getOrganizationName());
        }

//		if (applicant.getName() != null) {
//			PersonName personName = applicant.getName();
//			form.setName(personName.getTitle());
//		}

		return form;
	}

}
