/*******************************************************************************
 * * $Id:: RepresentativeNaturalPersonFactory.java 113496 2013-04-22 15:03:04Z k#$
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

import eu.ohim.sp.common.ui.form.person.RepresentativeNaturalPersonForm;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.person.PersonKind;

@Component
public class RepresentativeNaturalPersonFactory extends RepresentativeAbstractFactory<RepresentativeNaturalPersonForm> {

	/**
	 * Receives as a parameter a UI RepresentativeNaturalPersonForm object and
	 * converts it to a core Representative object.
	 * 
	 * @param form
	 *            the UI RepresentativeNaturalPersonForm to convert
	 * @return the core Representative object
	 */
	@Override
	public Representative convertTo(RepresentativeNaturalPersonForm form) {
		if (form == null) {
			return new Representative();
		}

		Representative core = internalUiRepresentativeToCoreRepresentative(form);

		core.setRepresentativeKind(EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form.getType(), false));
		core.setKind(PersonKind.NATURAL_PERSON);

		if (core.getName() != null) {
			core.getName().setFirstName(StringUtils.isNotEmpty(form.getFirstName()) ? form.getFirstName() : "");
			core.getName().setLastName(StringUtils.isNotEmpty(form.getSurname()) ? form.getSurname() : "");
			core.getName().setMiddleName(form.getMiddleName());
			core.getName().setTitle(StringUtils.isNotEmpty(form.getTitle()) ? form.getTitle() : "");
		} else {
			PersonName personName = new PersonName();
			personName.setFirstName(StringUtils.isNotEmpty(form.getFirstName()) ? form.getFirstName() : "");
			personName.setLastName(StringUtils.isNotEmpty(form.getSurname()) ? form.getSurname() : "");
			personName.setTitle(StringUtils.isNotEmpty(form.getTitle()) ? form.getTitle() : "");
			personName.setMiddleName(form.getMiddleName());
			core.setName(personName);
		}
		
		return core;
	}

	/**
	 * Receives as a parameter a core {@link Representative} object and converts it to a
	 * {@link RepresentativeNaturalPersonForm} object.
	 *
	 * @param core the representative to convert
	 * @return the natural person form object
	 */
	@Override
	public RepresentativeNaturalPersonForm convertFrom(Representative core) {
		if (core == null) {
			return new RepresentativeNaturalPersonForm();
		}

		RepresentativeNaturalPersonForm form = createSubRepresentative(
				internalCoreRepresentativeToUIRepresentative(core), RepresentativeNaturalPersonForm.class);

		if (core.getName() != null) {
			PersonName personName = core.getName();
			form.setSurname(StringUtils.isNotEmpty(personName.getLastName()) ? personName.getLastName() : "");
			form.setFirstName(StringUtils.isNotEmpty(personName.getFirstName()) ? personName.getFirstName() : "");
			form.setMiddleName(StringUtils.isNotEmpty(personName.getMiddleName()) ? personName.getMiddleName() : "");
			form.setTitle(StringUtils.isNotEmpty(personName.getTitle()) ? personName.getTitle() : "");
		}

		return form;
	}

}
