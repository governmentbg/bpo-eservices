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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.person.RepresentativeLegalEntityForm;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.person.PersonKind;

@Component
public class RepresentativeLegalEntityFactory extends RepresentativeAbstractFactory<RepresentativeLegalEntityForm> {

	/**
	 * Receives as a parameter a UI RepresentativeLegalEntityForm object and
	 * converts it to a core Representative object.
	 * 
	 * @param form
	 *            the UI RepresentativeLegalEntityForm to convert
	 * @return the core Representative object
	 */
	@Override
	public Representative convertTo(RepresentativeLegalEntityForm form) {
		if (form == null) {
			return new Representative();
		}

		Representative core = internalUiRepresentativeToCoreRepresentative(form);
		core.setRepresentativeKind(EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form.getType(), false));
		core.setKind(PersonKind.LEGAL_ENTITY);

		core.setLegalForm(form.getLegalForm());

		PersonName personName = new PersonName();
		personName.setOrganizationName(form.getNameOfLegalEntity());
		
		core.setName(personName);

		if(!StringUtils.isEmpty(form.getBusinessVatNumber())){
			List<PersonIdentifier> identifiers = core.getIdentifiers() != null ? core.getIdentifiers() : new ArrayList<PersonIdentifier>();
			PersonIdentifier personalIdent = new PersonIdentifier();			
			personalIdent.setValue(form.getBusinessVatNumber());
			personalIdent.setIdentifierKindCode("VAT Number");
	        identifiers.add(personalIdent);
	        core.setIdentifiers(identifiers);
		}
		
		return core;
	}

	/**
	 * Receives as a parameter a core {@link Representative} object and converts it to a
	 * {@link RepresentativeLegalEntityForm} object.
	 *
	 * @param core the representative to convert
	 * @return the legal entity form object
	 */
	@Override
	public RepresentativeLegalEntityForm convertFrom(Representative core) {
		if (core == null) {
			return new RepresentativeLegalEntityForm();
		}

		RepresentativeLegalEntityForm form = createSubRepresentative(
				internalCoreRepresentativeToUIRepresentative(core), RepresentativeLegalEntityForm.class);

		if (core.getIdentifiers() != null && !core.getIdentifiers().isEmpty()) {
			form.setBusinessVatNumber(core.getIdentifiers().get(0).getValue());
		} else {
			form.setBusinessVatNumber("");
		}
		form.setLegalForm(core.getLegalForm());
		form.setNameOfLegalEntity(core.getName().getOrganizationName());

		return form;
	}


}
