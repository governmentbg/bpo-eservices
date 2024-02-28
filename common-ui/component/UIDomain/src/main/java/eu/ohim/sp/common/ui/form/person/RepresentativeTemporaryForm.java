package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Raya
 * 12.01.2021
 */
public class RepresentativeTemporaryForm extends RepresentativeForm {

    private static final int value31 = 31;

    private String firstName;
    private String middleName;
    private String surname;

    public RepresentativeTemporaryForm() {
        setType(RepresentativeKindForm.TEMPORARY_REPRESENTATIVE);
    }

    @Override
    public String getName() {
        return (StringUtils.isEmpty(this.firstName) ? "" : (this.firstName + " ")) +
            (StringUtils.isEmpty(this.middleName) ? "" : (this.middleName + " ")) +
            (StringUtils.isEmpty(this.surname) ? "" : this.surname);

    }

    @Override
    public RepresentativeTemporaryForm clone() throws CloneNotSupportedException{
        RepresentativeTemporaryForm repForm = copyRep(RepresentativeTemporaryForm.class);
        repForm.setFirstName(firstName);
        repForm.setSurname(surname);
        repForm.setMiddleName(middleName);
        return repForm;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public AvailableSection getAvailableSectionName() {
        return AvailableSection.REPRESENTATIVE_TEMPORARY;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RepresentativeTemporaryForm)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        RepresentativeTemporaryForm that = (RepresentativeTemporaryForm) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) {
            return false;
        }
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) {
            return false;
        }
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = value31 * result + (surname != null ? surname.hashCode() : 0);
        result = value31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = value31 * result + (firstName != null ? firstName.hashCode() : 0);
        return result;
    }
}
