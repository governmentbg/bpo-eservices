package eu.ohim.sp.common.ui.form.person;

import java.util.Objects;

/**
 * Created by Raya
 * 06.11.2020
 */
public class PersonOptionForm {

    private PersonOptionSourceField field;
    private PersonForm personForm;
    private boolean alreadyAdded;

    public PersonOptionForm() {
    }

    public PersonOptionForm(PersonOptionSourceField field, PersonForm personForm, boolean alreadyAdded) {
        this.field = field;
        this.personForm = personForm;
        this.alreadyAdded = alreadyAdded;
    }

    public PersonOptionSourceField getField() {
        return field;
    }

    public void setField(PersonOptionSourceField field) {
        this.field = field;
    }

    public PersonForm getPersonForm() {
        return personForm;
    }

    public void setPersonForm(PersonForm personForm) {
        this.personForm = personForm;
    }

    public boolean isAlreadyAdded() {
        return alreadyAdded;
    }

    public void setAlreadyAdded(boolean alreadyAdded) {
        this.alreadyAdded = alreadyAdded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonOptionForm)) return false;
        PersonOptionForm that = (PersonOptionForm) o;
        if(personForm.getImported()){
            return personForm.getId().equals(that.personForm.getId());
        }
        return Objects.equals(personForm, that.personForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personForm);
    }
}
