/*******************************************************************************
 * * $Id:: LegalEntityFactory.java 113496 2013-04-22 15:03:04Z karalch           $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LegalEntityFactory extends ApplicantAbstractFactory<LegalEntityForm> {

	/**
	 * Receives as a parameter a UI LegalEntityForm object and converts it to a
	 * core Applicant object.
	 * 
	 * @param form
	 *            the UI legal entity form to convert
	 * @return the core applicant object
	 */
	@Override
	public Applicant convertTo(LegalEntityForm form) {
		if (form == null) {
			return new Applicant();
		}

		Applicant core = internalUiApplicantToCoreApplicant(form);

		core.setIncorporationState(form.getStateOfIncorporation());

		core.setLegalForm(form.getLegalForm());

		if (core.getName() != null) {
			core.getName().setOrganizationName(form.getName());
		} else {
			PersonName personName = new PersonName();
			personName.setOrganizationName(form.getName());
			core.setName(personName);
		}
		core.setIncorporationCountry(uiNationalityToCoreNationality(form.getCountryOfRegistration()));
		

		core.setKind(PersonKind.LEGAL_ENTITY);
		
		return core;
	}

	/**
	 * Receives as a parameter a core {@link Applicant} object and converts it to a
	 * {@link LegalEntityForm} object.
	 *
	 * @param applicant the applicant to convert
	 * @return the legal entity form object
	 */
	@Override
	public LegalEntityForm convertFrom(Applicant applicant) {
		if (applicant == null) {
			return new LegalEntityForm();
		}
		LegalEntityForm form = createSubApplicant(internalCoreApplicantToUIApplicant(applicant), LegalEntityForm.class);

		form.setStateOfIncorporation(applicant.getIncorporationState());
		form.setLegalForm(applicant.getLegalForm());
		
        form.setCountryOfRegistration(coreNationalityToUiNationality(applicant.getIncorporationCountry()));

		if (applicant.getName() != null) {
			PersonName personName = applicant.getName();
			form.setName(StringUtils.isNotEmpty(personName.getOrganizationName()) ? personName.getOrganizationName() : "");
		}

		return form;
	}

}
