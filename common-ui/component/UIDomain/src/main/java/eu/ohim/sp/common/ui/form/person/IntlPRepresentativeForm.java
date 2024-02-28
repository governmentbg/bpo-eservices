package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 12.10.2022
 * Time: 18:53
 */
public class IntlPRepresentativeForm extends RepresentativeForm implements Cloneable {

    private static final int value31 = 31;

    private String surname;
    private String firstName;
    private String middleName;
    private String title;

    public IntlPRepresentativeForm() {
        setType(RepresentativeKindForm.INTELLECTUAL_PROPERTY_REPRESENTATIVE);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    @Override
    public IntlPRepresentativeForm clone() throws CloneNotSupportedException{
        IntlPRepresentativeForm repForm = copyRep(IntlPRepresentativeForm.class);

        repForm.setFirstName(firstName);
        repForm.setSurname(surname);
        repForm.setMiddleName(middleName);
        repForm.setTitle(title);
        return repForm;
    }

    /**
     * Gets surname
     * @return string surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets first name
     * @return String first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    @Override
    public String getName() {
        return (StringUtils.isEmpty(this.firstName) ? "" : (this.firstName + " ")) +
                (StringUtils.isEmpty(this.middleName) ? "" : (this.middleName + " ")) +
                (StringUtils.isEmpty(this.surname) ? "" : this.surname);

    }

    public AvailableSection getAvailableSectionName() {
        return AvailableSection.REPRESENTATIVE_INTLPREPRESENTATIVE;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntlPRepresentativeForm)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        IntlPRepresentativeForm that = (IntlPRepresentativeForm) o;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
