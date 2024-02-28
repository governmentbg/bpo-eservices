package eu.ohim.sp.common.ui.adapter;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.person.AssigneeNaturalPersonForm;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.Assignee;
import eu.ohim.sp.core.domain.person.PersonKind;

@Component
public class AssigneeNaturalPersonFactory extends AssigneeAbstractFactory<AssigneeNaturalPersonForm> {

	/**
	 * Receives as a parameter a UI AssigneeNaturalPersonForm object and
	 * converts it to a core Assignee object.
	 * 
	 * @param form
	 *            the UI AssigneeNaturalPersonForm to convert
	 * @return the core Assignee object
	 */
	@Override
	public Assignee convertTo(AssigneeNaturalPersonForm form) {
		if (form == null) {
			return new Assignee();
		}

		Assignee core = internalUiAssigneeToCoreAssignee(form);

		core.setKind(PersonKind.NATURAL_PERSON);
		core.setNationality(form.getNationality());

		if (core.getName() != null) {
			core.getName().setFirstName(StringUtils.isNotEmpty(form.getFirstName()) ? form.getFirstName() : "");
			core.getName().setLastName(StringUtils.isNotEmpty(form.getSurname()) ? form.getSurname() : "");
			core.getName().setMiddleName(StringUtils.isNotEmpty(form.getMiddleName()) ? form.getMiddleName() : "");
			core.getName().setTitle(StringUtils.isNotEmpty(form.getTitle()) ? form.getTitle() : "");
		} else {
			PersonName personName = new PersonName();
			personName.setFirstName(StringUtils.isNotEmpty(form.getFirstName()) ? form.getFirstName() : "");
			personName.setLastName(StringUtils.isNotEmpty(form.getSurname()) ? form.getSurname() : "");
			personName.setMiddleName(StringUtils.isNotEmpty(form.getMiddleName()) ? form.getMiddleName() : "");
			personName.setTitle(StringUtils.isNotEmpty(form.getTitle()) ? form.getTitle() : "");
			core.setName(personName);
		}
		
		return core;
	}

	/**
	 * Receives as a parameter a core {@link Assignee} object and converts it to a
	 * {@link AssigneeNaturalPersonForm} object.
	 *
	 * @param core the representative to convert
	 * @return the natural person form object
	 */
	@Override
	public AssigneeNaturalPersonForm convertFrom(Assignee core) {
		if (core == null) {
			return new AssigneeNaturalPersonForm();
		}

		AssigneeNaturalPersonForm form = createSubAssignee(
				internalCoreAssigneeToUIAssignee(core), AssigneeNaturalPersonForm.class);
		form.setNationality(core.getNationality());

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
