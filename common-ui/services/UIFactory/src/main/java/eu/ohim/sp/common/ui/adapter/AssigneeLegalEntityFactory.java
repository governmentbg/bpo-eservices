package eu.ohim.sp.common.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.person.AssigneeLegalEntityForm;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Assignee;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;

@Component
public class AssigneeLegalEntityFactory extends AssigneeAbstractFactory<AssigneeLegalEntityForm> {

	/**
	 * Receives as a parameter a UI AssigneeLegalEntityForm object and converts it to a
	 * core Assignee object.
	 * 
	 * @param form
	 *            the UI assignee legal entity form to convert
	 * @return the core assignee object
	 */
	@Override
	public Assignee convertTo(AssigneeLegalEntityForm form) {
		if (form == null) {
			return new Assignee();
		}

		Assignee core = internalUiAssigneeToCoreAssignee(form);

		core.setIncorporationState(form.getStateOfIncorporation());

		core.setLegalForm(form.getLegalForm());

		if (core.getName() != null) {
			core.getName().setOrganizationName(form.getName());
		} else {
			PersonName personName = new PersonName();
			personName.setOrganizationName(form.getName());
			core.setName(personName);
		}
		core.setIncorporationCountry(uiNationalityToCoreNationality(form.getCountryOfRegistration()));

		core.setKind(PersonKind.LEGAL_ENTITY);
		return core;
	}

	/**
	 * Receives as a parameter a core {@link Assignee} object and converts it to a
	 * {@link AssigneeLegalEntityForm} object.
	 *
	 * @param assignee the assignee to convert
	 * @return the assignee legal entity form object
	 */
	@Override
	public AssigneeLegalEntityForm convertFrom(Assignee assignee) {
		if (assignee == null) {
			return new AssigneeLegalEntityForm();
		}
		AssigneeLegalEntityForm form = createSubAssignee(internalCoreAssigneeToUIAssignee(assignee), AssigneeLegalEntityForm.class);

		form.setStateOfIncorporation(assignee.getIncorporationState());
		form.setLegalForm(assignee.getLegalForm());
		
        form.setCountryOfRegistration(coreNationalityToUiNationality(assignee.getIncorporationCountry()));

		if (assignee.getName() != null) {
			PersonName personName = assignee.getName();
			form.setName(StringUtils.isNotEmpty(personName.getOrganizationName()) ? personName.getOrganizationName() : "");
		}

		return form;
	}

}
