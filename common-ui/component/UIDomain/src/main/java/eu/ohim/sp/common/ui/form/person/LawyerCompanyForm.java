package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * Created by Raya
 * 12.01.2021
 */
public class LawyerCompanyForm extends RepresentativeForm {

    private static final int value31 = 31;

    private String nameOfLegalEntity;

    public LawyerCompanyForm() {
        setType(RepresentativeKindForm.LAWYER_COMPANY);
    }

    @Override
    public LawyerCompanyForm clone() throws CloneNotSupportedException{
        LawyerCompanyForm repForm = copyRep(LawyerCompanyForm.class);
        repForm.setNameOfLegalEntity(nameOfLegalEntity);
        return repForm;
    }

    @Override
    public String getName() {
        return this.nameOfLegalEntity;
    }

    public String getNameOfLegalEntity() {
        return nameOfLegalEntity;
    }

    public void setNameOfLegalEntity(String nameOfLegalEntity) {
        this.nameOfLegalEntity = nameOfLegalEntity;
    }

    public AvailableSection getAvailableSectionName() {
        return AvailableSection.REPRESENTATIVE_LAWYERCOMPANY;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LawyerCompanyForm)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        LawyerCompanyForm that = (LawyerCompanyForm) o;


        if (nameOfLegalEntity != null ? !nameOfLegalEntity.equals(that.nameOfLegalEntity) : that.nameOfLegalEntity != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = value31 * result + (nameOfLegalEntity != null ? nameOfLegalEntity.hashCode() : 0);
        return result;
    }
}
