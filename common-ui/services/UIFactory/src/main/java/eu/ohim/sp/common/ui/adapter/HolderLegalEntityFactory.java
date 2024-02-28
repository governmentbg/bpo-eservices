package eu.ohim.sp.common.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.person.HolderLegalEntityForm;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Holder;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;

@Component
public class HolderLegalEntityFactory extends HolderAbstractFactory<HolderLegalEntityForm> {

	/**
	 * Receives as a parameter a UI HolderLegalEntityForm object and converts it to a
	 * core Holder object.
	 * 
	 * @param form
	 *            the UI holder legal entity form to convert
	 * @return the core holder object
	 */
	@Override
	public Holder convertTo(HolderLegalEntityForm form) {
		if (form == null) {
			return new Holder();
		}

		Holder core = internalUiHolderToCoreHolder(form);

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
	 * Receives as a parameter a core {@link Holder} object and converts it to a
	 * {@link HolderLegalEntityForm} object.
	 *
	 * @param holder the holder to convert
	 * @return the holder legal entity form object
	 */
	@Override
	public HolderLegalEntityForm convertFrom(Holder holder) {
		if (holder == null) {
			return new HolderLegalEntityForm();
		}
		HolderLegalEntityForm form = createSubHolder(internalCoreHolderToUIHolder(holder), HolderLegalEntityForm.class);

		form.setStateOfIncorporation(holder.getIncorporationState());
		form.setLegalForm(holder.getLegalForm());
		
        form.setCountryOfRegistration(coreNationalityToUiNationality(holder.getIncorporationCountry()));

		if (holder.getName() != null) {
			PersonName personName = holder.getName();
			form.setName(StringUtils.isNotEmpty(personName.getOrganizationName()) ? personName.getOrganizationName() : "");
		}

		return form;
	}

}
