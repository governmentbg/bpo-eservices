/*******************************************************************************
 * * $Id:: RepresentativeAssociationFactory.java 113496 2013-04-22 15:03:04Z kar#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.core.domain.person.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.person.ProfessionalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeAssociationForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeKindForm;

@Component
public class RepresentativeAssociationFactory extends RepresentativeAbstractFactory<RepresentativeAssociationForm> {

	/**
	 * Receives as a parameter a UI ProfessionalPractitionerForm object and
	 * converts it to a core Representative object.
	 * 
	 * @param form
	 *            the UI ProfessionalPractitionerForm to convert
	 * @return the core Representative object
	 */
	@Override
	public Representative convertTo(RepresentativeAssociationForm form) {
		if (form == null) {
			return new Representative();
		}

		Representative core = internalUiRepresentativeToCoreRepresentative(form);

		core.setRepresentativeKind(EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form.getType(), false));

		PersonName name = new PersonName();
		name.setOrganizationName(form.getName());
		core.setName(name);
		core.setKind(PersonKind.LEGAL_ENTITY);
		core.setRepresentativeKind(RepresentativeKind.ASSOCIATION);
		core.setLegalForm(form.getLegalForm());
		FactoryUtils.personUI2CorePhones(core, form);
		return core;
	}
	
	/**
	 * Receives as a parameter a core {@link Representative} object and converts it to a
	 * {@link ProfessionalPractitionerForm} object.
	 *
	 * @param representative the representative to convert
	 * @return the professional practitioner form object
	 */
	@Override
	public RepresentativeAssociationForm convertFrom(Representative representative) {
		if (representative == null) {
			return new RepresentativeAssociationForm();
		}

		RepresentativeAssociationForm form = createSubRepresentative(
				internalCoreRepresentativeToUIRepresentative(representative), RepresentativeAssociationForm.class);
		if(representative.getName() != null && StringUtils.isNotEmpty(representative.getName().getOrganizationName())) {
			form.setName(representative.getName().getOrganizationName());
			form.setAssociationName(representative.getName().getOrganizationName());
		}
		FactoryUtils.personCore2UIphones(representative, form);
		form.setLegalForm(representative.getLegalForm());
		return form;
	}

}
