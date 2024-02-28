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

import eu.ohim.sp.common.ui.form.person.LawyerAssociationForm;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.Representative;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class LawyerAssociationFactory extends RepresentativeAbstractFactory<LawyerAssociationForm> {

	@Override
	public Representative convertTo(LawyerAssociationForm form) {
		if (form == null) {
			return new Representative();
		}

		Representative core = internalUiRepresentativeToCoreRepresentative(form);

		core.setRepresentativeKind(EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form.getType(), false));

		PersonName name = new PersonName();
		name.setOrganizationName(form.getName());
		core.setName(name);
		core.setKind(PersonKind.LEGAL_ENTITY);
		return core;
	}

	@Override
	public LawyerAssociationForm convertFrom(Representative representative) {
		if (representative == null) {
			return new LawyerAssociationForm();
		}

		LawyerAssociationForm form = createSubRepresentative(
				internalCoreRepresentativeToUIRepresentative(representative), LawyerAssociationForm.class);
		if(representative.getName() != null && StringUtils.isNotEmpty(representative.getName().getOrganizationName())) {
			form.setAssociationName(representative.getName().getOrganizationName());
		}
		return form;
	}

}
