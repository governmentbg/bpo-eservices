/*******************************************************************************
 * * $Id:: EmployeeRepresentativeFactory.java 113496 2013-04-22 15:03:04Z karalc#$
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

import eu.ohim.sp.common.ui.form.person.EmployeeRepresentativeForm;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.Representative;

@Component
public class EmployeeRepresentativeFactory extends RepresentativeAbstractFactory<EmployeeRepresentativeForm> {

	/**
	 * Receives as a parameter a UI EmployeeRepresentativeForm object and
	 * converts it to a core Representative object.
	 * 
	 * @param form
	 *            the UI EmployeeRepresentativeForm to convert
	 * @return the core Representative object
	 */
	@Override
	public Representative convertTo(EmployeeRepresentativeForm form) {
		if (form == null) {
			return new Representative();
		}

		Representative core = internalUiRepresentativeToCoreRepresentative(form);

		core.setRepresentativeKind(EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form.getType(),
				(form.getEconomicConnections() == null ? false : form.getEconomicConnections())));

		if (core.getName() != null) {
			core.getName().setFirstName(StringUtils.isNotEmpty(form.getFirstName()) ? form.getFirstName() : "");
			core.getName().setMiddleName(StringUtils.isNotEmpty(form.getMiddleName()) ? form.getMiddleName() : "");
			core.getName().setLastName(StringUtils.isNotEmpty(form.getSurname()) ? form.getSurname() : "");
			if(form.getLegalForm()!= null && form.getLegalForm().equals("Natural Person")) {
				core.getName().setOrganizationName(StringUtils.isNotEmpty(form.getNameOfEmployer()) ? form.getNameOfEmployer() : "");
			} else {
				core.getName().setOrganizationName(StringUtils.isNotEmpty(form.getNameOfLegalEntity())? form.getNameOfLegalEntity():"");
			}
		} else {
			PersonName personName = new PersonName();
			personName.setFirstName(StringUtils.isNotEmpty(form.getFirstName()) ? form.getFirstName() : "");
			personName.setMiddleName(StringUtils.isNotEmpty(form.getMiddleName()) ? form.getMiddleName() : "");
			personName.setLastName(StringUtils.isNotEmpty(form.getSurname()) ? form.getSurname() : "");
			if(form.getLegalForm()!= null && form.getLegalForm().equals("Natural Person")) {
				personName.setOrganizationName(StringUtils.isNotEmpty(form.getNameOfEmployer()) ? form.getNameOfEmployer() : "");
			} else {
				personName.setOrganizationName(StringUtils.isNotEmpty(form.getNameOfLegalEntity())? form.getNameOfLegalEntity():"");
			}
			core.setName(personName);
		}

		core.setNameOfEmployer(form.getNameOfEmployer());
		FactoryUtils.personUI2CorePhones(core, form);
		boolean economicConnections = form.getEconomicConnections() == null ? false : form.getEconomicConnections().booleanValue();
		core.setEconomicConnectionIndicator(economicConnections);
		core.setEconomicConnectionNature(form.getNatureOfEconomicConnections());
		if(form.getLegalForm()!= null && form.getLegalForm().equals("Legal Person")) {
			core.setKind(PersonKind.LEGAL_ENTITY);
		} else {
			core.setKind(PersonKind.NATURAL_PERSON);
		}
		
		core.setLegalForm(form.getLegalForm());
		return core;
	}

	/**
	 * Receives as a parameter a core {@link Representative} object and converts it to
	 * an {@link EmployeeRepresentativeForm} object.
	 *
	 * @param core the core to convert
	 * @return the employee core form object
	 */
	@Override
	public EmployeeRepresentativeForm convertFrom(Representative core) {
		if (core == null) {
			return new EmployeeRepresentativeForm();
		}

		EmployeeRepresentativeForm form = createSubRepresentative(
				internalCoreRepresentativeToUIRepresentative(core), EmployeeRepresentativeForm.class);

		if (core.getName() != null) {
			PersonName personName = core.getName();
			form.setFirstName(StringUtils.isNotEmpty(personName.getFirstName()) ? personName.getFirstName() : "");
			form.setMiddleName(StringUtils.isNotEmpty(personName.getMiddleName()) ? personName.getMiddleName() : "");
			form.setSurname(StringUtils.isNotEmpty(personName.getLastName()) ? personName.getLastName() : "");
			form.setNameOfEmployer(StringUtils.isNotEmpty(personName.getOrganizationName()) ? personName.getOrganizationName() : "");
			form.setNameOfLegalEntity(StringUtils.isNotEmpty(personName.getOrganizationName()) ? personName.getOrganizationName() : "");
		}
		form.setNameOfEmployer(core.getNameOfEmployer());
		FactoryUtils.personCore2UIphones(core, form);
		form.setEconomicConnections(core.isEconomicConnectionIndicator());
		form.setNatureOfEconomicConnections(core.getEconomicConnectionNature());
		form.setLegalForm(core.getLegalForm());
		return form;
	}

}
