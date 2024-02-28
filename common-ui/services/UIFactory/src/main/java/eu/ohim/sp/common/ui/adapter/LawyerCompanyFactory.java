/*******************************************************************************
 * * $Id:: RepresentativeLegalEntityFactory.java 113496 2013-04-22 15:03:04Z kar#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.person.LawyerCompanyForm;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.Representative;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class LawyerCompanyFactory extends RepresentativeAbstractFactory<LawyerCompanyForm> {


	@Override
	public Representative convertTo(LawyerCompanyForm form) {
		if (form == null) {
			return new Representative();
		}

		Representative core = internalUiRepresentativeToCoreRepresentative(form);
		core.setRepresentativeKind(EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form.getType(), false));
		core.setKind(PersonKind.LEGAL_ENTITY);

		PersonName personName = new PersonName();
		personName.setOrganizationName(form.getNameOfLegalEntity());
		
		core.setName(personName);
		
		return core;
	}


	@Override
	public LawyerCompanyForm convertFrom(Representative core) {
		if (core == null) {
			return new LawyerCompanyForm();
		}

		LawyerCompanyForm form = createSubRepresentative(
				internalCoreRepresentativeToUIRepresentative(core), LawyerCompanyForm.class);

		if(core.getName() != null && StringUtils.isNotEmpty(core.getName().getOrganizationName())) {
			form.setNameOfLegalEntity(core.getName().getOrganizationName());
		}

		return form;
	}


}
