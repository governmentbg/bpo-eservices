package eu.ohim.sp.common.ui.form.patent;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.util.Date;

/**
 * Created by Raya
 * 09.04.2019
 */
public class PTDivisionalApplicationForm extends AbstractImportableForm {

    private String numberDivisionalApplication;
    private Date dateDivisionalApplication;

    @Override
    public AbstractForm clone() throws CloneNotSupportedException {
        PTDivisionalApplicationForm divisionalApplicationForm = new PTDivisionalApplicationForm();
        divisionalApplicationForm.setId(this.getId());
        divisionalApplicationForm.setImported(this.getImported());
        divisionalApplicationForm.setDateDivisionalApplication(this.dateDivisionalApplication);
        divisionalApplicationForm.setNumberDivisionalApplication(this.numberDivisionalApplication);
        return divisionalApplicationForm;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.DIVISIONAL_APPLICATION_SECTION;
    }

    public String getNumberDivisionalApplication() {
        return numberDivisionalApplication;
    }

    public void setNumberDivisionalApplication(String numberDivisionalApplication) {
        this.numberDivisionalApplication = numberDivisionalApplication;
    }

    public Date getDateDivisionalApplication() {
        return dateDivisionalApplication;
    }

    public void setDateDivisionalApplication(Date dateDivisionalApplication) {
        this.dateDivisionalApplication = dateDivisionalApplication;
    }
}
