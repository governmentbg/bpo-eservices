package eu.ohim.sp.common.ui.adapter;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.person.HolderNaturalPersonForm;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.Holder;
import eu.ohim.sp.core.domain.person.PersonKind;

@Component
public class HolderNaturalPersonFactory extends HolderAbstractFactory<HolderNaturalPersonForm> {

	/**
	 * Receives as a parameter a UI HolderNaturalPersonForm object and
	 * converts it to a core Holder object.
	 * 
	 * @param form
	 *            the UI HolderNaturalPersonForm to convert
	 * @return the core Holder object
	 */
	@Override
	public Holder convertTo(HolderNaturalPersonForm form) {
		if (form == null) {
			return new Holder();
		}

		Holder core = internalUiHolderToCoreHolder(form);

		core.setKind(PersonKind.NATURAL_PERSON);
		core.setNationality(form.getNationality());

		if (core.getName() != null) {
			core.getName().setFirstName(StringUtils.isNotEmpty(form.getFirstName()) ? form.getFirstName() : "");
			core.getName().setMiddleName(StringUtils.isNotEmpty(form.getMiddleName()) ? form.getMiddleName() : "");
			core.getName().setLastName(StringUtils.isNotEmpty(form.getSurname()) ? form.getSurname() : "");
			core.getName().setTitle(StringUtils.isNotEmpty(form.getTitle()) ? form.getTitle() : "");
		} else {
			PersonName personName = new PersonName();
			personName.setFirstName(StringUtils.isNotEmpty(form.getFirstName()) ? form.getFirstName() : "");
			personName.setMiddleName(StringUtils.isNotEmpty(form.getMiddleName()) ? form.getMiddleName() : "");
			personName.setLastName(StringUtils.isNotEmpty(form.getSurname()) ? form.getSurname() : "");
			personName.setTitle(StringUtils.isNotEmpty(form.getTitle()) ? form.getTitle() : "");
			core.setName(personName);
		}
		
		return core;
	}

	/**
	 * Receives as a parameter a core {@link Holder} object and converts it to a
	 * {@link HolderNaturalPersonForm} object.
	 *
	 * @param core the representative to convert
	 * @return the natural person form object
	 */
	@Override
	public HolderNaturalPersonForm convertFrom(Holder core) {
		if (core == null) {
			return new HolderNaturalPersonForm();
		}

		HolderNaturalPersonForm form = createSubHolder(
				internalCoreHolderToUIHolder(core), HolderNaturalPersonForm.class);
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
