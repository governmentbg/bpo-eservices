/*******************************************************************************
 * * $Id:: ProfessionalPractitionerFactory.java 113496 2013-04-22 15:03:04Z kara#$
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
import java.util.Optional;

import eu.ohim.sp.core.domain.contact.Phone;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import eu.ohim.sp.core.domain.person.PersonRoleRelationship;
import eu.ohim.sp.core.domain.person.PersonRoleRelationKind;
import eu.ohim.sp.common.ui.form.person.LegalPractitionerType;
import eu.ohim.sp.common.ui.form.person.ProfessionalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.ProfessionalPractitionerType;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.PersonRole;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.person.RepresentativeKind;

@Component
public class ProfessionalPractitionerFactory extends RepresentativeAbstractFactory<ProfessionalPractitionerForm> {

	/**
	 * Receives as a parameter a UI ProfessionalPractitionerForm object and
	 * converts it to a core Representative object.
	 * 
	 * @param form
	 *            the UI ProfessionalPractitionerForm to convert
	 * @return the core Representative object
	 */
	@Override
	public Representative convertTo(ProfessionalPractitionerForm form) {
		if (form == null) {
			return new Representative();
		}

		Representative core = internalUiRepresentativeToCoreRepresentative(form);

		core.setRepresentativeKind(EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form.getType(), false));

		if (core.getName() != null) {
			core.getName().setFirstName(StringUtils.isNotEmpty(form.getFirstName()) ? form.getFirstName() : "");
			core.getName().setLastName(StringUtils.isNotEmpty(form.getSurname()) ? form.getSurname() : "");
		} else {
			PersonName personName = new PersonName();
			personName.setFirstName(StringUtils.isNotEmpty(form.getFirstName()) ? form.getFirstName() : "");
			personName.setLastName(StringUtils.isNotEmpty(form.getSurname()) ? form.getSurname() : "");
			core.setName(personName);
		}
		core.getName().setOrganizationName(form.getAssociationName());		

		if (ProfessionalPractitionerType.ASSOCIATION.equals(form.getProfessionalPractitionerType())) {
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
//      core.setIsAssociation(form.getProfessionalPractitionerType().equals(ProfessionalPractitionerType.ASSOCIATION));
		core.setLegalForm(form.getLegalForm());
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
	public ProfessionalPractitionerForm convertFrom(Representative representative) {
		if (representative == null) {
			return new ProfessionalPractitionerForm();
		}

		ProfessionalPractitionerForm form = createSubRepresentative(
				internalCoreRepresentativeToUIRepresentative(representative), ProfessionalPractitionerForm.class);

		if (representative.getName() != null) {
			PersonName personName = representative.getName();
			form.setSurname(personName.getLastName());
			form.setFirstName(personName.getFirstName());
			form.setAssociationName(personName.getOrganizationName());
		}

		FactoryUtils.personCore2UIphones(representative, form);

//		form.setAssociationId(representative.getAssociationId());
		form.setLegalForm(representative.getLegalForm());
		return form;
	}

}
