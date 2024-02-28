/*******************************************************************************
 * * $Id:: LegalPractitionerFactory.java 113496 2013-04-22 15:03:04Z karalch     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.person.LegalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeTemporaryForm;
import eu.ohim.sp.core.domain.person.*;
import org.springframework.stereotype.Component;

@Component
public class RepresentativeTemporaryFactory extends RepresentativeAbstractFactory<RepresentativeTemporaryForm> {


	@Override
	public Representative convertTo(RepresentativeTemporaryForm form) {
		if (form == null) {
			return new Representative();
		}

		Representative core = internalUiRepresentativeToCoreRepresentative(form);

		core.setRepresentativeKind(EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form.getType(), false));
		if (core.getName() == null) {
			PersonName personName = new PersonName();
			core.setName(personName);
		} 
		core.getName().setFirstName(form.getFirstName());
		core.getName().setMiddleName(form.getMiddleName());
		core.getName().setLastName(form.getSurname());
		core.setKind(PersonKind.NATURAL_PERSON);

		return core;
	}

	/**
	 * Receives as a parameter a core {@link Representative} object and converts it to a
	 * {@link LegalPractitionerForm} object.
	 *
	 * @param representative the representative to convert
	 * @return the legal practitioner form object
	 */
	@Override
	public RepresentativeTemporaryForm convertFrom(Representative representative) {
		if (representative == null) {
			return new RepresentativeTemporaryForm();
		}

		RepresentativeTemporaryForm form = createSubRepresentative(
				internalCoreRepresentativeToUIRepresentative(representative), RepresentativeTemporaryForm.class);
		if (representative.getName() != null) {
			PersonName personName = representative.getName();
			form.setSurname(personName.getLastName());
			form.setFirstName(personName.getFirstName());
			form.setMiddleName(personName.getMiddleName());
		}

		return form;
	}

}
