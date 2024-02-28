package eu.ohim.sp.common.ui.form.trademark;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 05.05.2022
 * Time: 18:04
 */
public class ForeignRegistrationForm extends AbstractImportableForm implements Cloneable, Serializable {

    private String registrationNumber;
    private Date registrationDate;
    private String registrationCountry;

    @Override
    public AbstractForm clone() throws CloneNotSupportedException {
        ForeignRegistrationForm form = new ForeignRegistrationForm();
        form.setId(this.getId());
        form.setRegistrationCountry(this.registrationCountry);
        form.setRegistrationNumber(this.registrationNumber);
        form.setRegistrationDate(this.registrationDate);
        return form;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.FOREIGN_REGISTRATION;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationCountry() {
        return registrationCountry;
    }

    public void setRegistrationCountry(String registrationCountry) {
        this.registrationCountry = registrationCountry;
    }
}
