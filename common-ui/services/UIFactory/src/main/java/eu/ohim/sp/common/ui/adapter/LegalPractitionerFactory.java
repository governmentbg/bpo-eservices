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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.person.LegalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.LegalPractitionerType;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.PersonRole;
import eu.ohim.sp.core.domain.person.PersonRoleRelationKind;
import eu.ohim.sp.core.domain.person.PersonRoleRelationship;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.person.RepresentativeKind;

@Component
public class LegalPractitionerFactory extends RepresentativeAbstractFactory<LegalPractitionerForm> {

	/**
	 * Receives as a parameter a UI LegalPractitionerForm object and
	 * converts it to a core Representative object.
	 * 
	 * @param form
	 *            the UI LegalPractitionerForm to convert
	 * @return the core Representative object
	 */
	@Override
	public Representative convertTo(LegalPractitionerForm form) {
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
		core.getName().setOrganizationName(form.getAssociationName());
		
		if (form.getLegalPractitionerType() != null && form.getLegalPractitionerType().equals(LegalPractitionerType.ASSOCIATION)) {
            PersonRoleRelationship pr = new PersonRoleRelationship();
            PersonName name = new PersonName();
            name.setOrganizationName(form.getAssociationName());

            PersonRole personRole = new PersonRole();
            personRole.setName(name);
			pr.setPersonRoleRelationKind(PersonRoleRelationKind.ASSOCIATION);
			pr.setPersonRole(personRole);
			
			if (core.getPersonRoleRelationships() == null){
				List<PersonRoleRelationship> listPersonRole = new ArrayList<PersonRoleRelationship>();
				core.setPersonRoleRelationships(listPersonRole);
			}
			core.getPersonRoleRelationships().add(pr);
			core.setKind(PersonKind.LEGAL_ENTITY);
		}else{
			core.setKind(PersonKind.NATURAL_PERSON);
		}

		FactoryUtils.personUI2CorePhones(core, form);

		//TODO to be fixed when CoreDomain is fixed
//		core.setAssociationId(form.getAssociationId());
//      core.setIsAssociation(form.getLegalPractitionerType().equals(LegalPractitionerType.ASSOCIATION));
		core.setLegalForm(form.getLegalForm());
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
	public LegalPractitionerForm convertFrom(Representative representative) {
		if (representative == null) {
			return new LegalPractitionerForm();
		}

		LegalPractitionerForm form = createSubRepresentative(
				internalCoreRepresentativeToUIRepresentative(representative), LegalPractitionerForm.class);
		if (representative.getName() != null) {
			PersonName personName = representative.getName();
			form.setSurname(personName.getLastName());
			form.setFirstName(personName.getFirstName());
			form.setMiddleName(personName.getMiddleName());
			form.setAssociationName(personName.getOrganizationName());
		}

		FactoryUtils.personCore2UIphones(representative, form);

//		form.setAssociationId(representative.getAssociationId());
		form.setLegalForm(representative.getLegalForm());
		return form;
	}

}
