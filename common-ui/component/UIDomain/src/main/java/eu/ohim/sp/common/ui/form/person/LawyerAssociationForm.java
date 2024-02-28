package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Raya
 * 12.01.2021
 */
public class LawyerAssociationForm extends RepresentativeForm {

    private static final int value31 = 31;

    private String associationName;

    public LawyerAssociationForm() {
        setType(RepresentativeKindForm.LAWYER_ASSOCIATION);
    }

    @Override
    public String getName() {
        return associationName;
    }

    @Override
    public LawyerAssociationForm clone() throws CloneNotSupportedException {
        LawyerAssociationForm representativeForm = copyRep(LawyerAssociationForm.class);

        representativeForm.setAssociationName(associationName);
        return representativeForm;
    }

    public String getAssociationName() {
        return associationName;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.REPRESENTATIVE_LAWYERASSOCIATION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LawyerAssociationForm)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        LawyerAssociationForm that = (LawyerAssociationForm) o;

        if (associationName != null ? !associationName.equals(that.associationName) : that.associationName != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = value31 * result + (associationName != null ? associationName.hashCode() : 0);
        return result;
    }
}
