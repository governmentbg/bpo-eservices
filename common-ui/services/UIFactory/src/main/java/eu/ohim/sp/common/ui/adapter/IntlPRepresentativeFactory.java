package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.person.IntlPRepresentativeForm;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.Representative;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 13.10.2022
 * Time: 10:15
 */
@Component
public class IntlPRepresentativeFactory extends RepresentativeAbstractFactory<IntlPRepresentativeForm>{

    @Override
    public Representative convertTo(IntlPRepresentativeForm form) {
        if (form == null) {
            return new Representative();
        }

        Representative core = internalUiRepresentativeToCoreRepresentative(form);

        core.setRepresentativeKind(EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form.getType(), false));
        core.setKind(PersonKind.NATURAL_PERSON);

        if (core.getName() != null) {
            core.getName().setFirstName(StringUtils.isNotEmpty(form.getFirstName()) ? form.getFirstName() : "");
            core.getName().setLastName(StringUtils.isNotEmpty(form.getSurname()) ? form.getSurname() : "");
            core.getName().setMiddleName(form.getMiddleName());
            core.getName().setTitle(StringUtils.isNotEmpty(form.getTitle()) ? form.getTitle() : "");
        } else {
            PersonName personName = new PersonName();
            personName.setFirstName(StringUtils.isNotEmpty(form.getFirstName()) ? form.getFirstName() : "");
            personName.setLastName(StringUtils.isNotEmpty(form.getSurname()) ? form.getSurname() : "");
            personName.setTitle(StringUtils.isNotEmpty(form.getTitle()) ? form.getTitle() : "");
            personName.setMiddleName(form.getMiddleName());
            core.setName(personName);
        }

        return core;
    }

    @Override
    public IntlPRepresentativeForm convertFrom(Representative core) {
        if (core == null) {
            return new IntlPRepresentativeForm();
        }

        IntlPRepresentativeForm form = createSubRepresentative(
                internalCoreRepresentativeToUIRepresentative(core), IntlPRepresentativeForm.class);

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
