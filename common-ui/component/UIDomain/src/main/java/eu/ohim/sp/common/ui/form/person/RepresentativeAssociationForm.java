/*******************************************************************************
 * * $Id:: RepresentativeAssociationForm.java 50053 2012-11-07 15:00:55Z ionitdi $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

public class RepresentativeAssociationForm extends RepresentativeForm {

    private static final long serialVersionUID = 1L;

    private static final int value31 = 31;

    private ProfessionalPractitionerType professionalPractitionerType;

    private String associationId;

    private String associationName;

    public RepresentativeAssociationForm() {
        setType(RepresentativeKindForm.ASSOCIATION);
    }

    /**
     * Method that returns the association id
     * 
     * @return the association id
     */
    public String getAssociationId() {
        return associationId;

    }

    /**
     * Method that sets the association id
     * 
     * @param associationId the association id
     */
    public void setAssociationId(String associationId) {
        this.associationId = associationId;
    }

    /**
     * Method that returns the association name
     * 
     * @return the association name
     */
    public String getAssociationName() {
        return associationName;
    }

    /**
     * Method that sets the association name
     * 
     * @param associationName the association name
     */
    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }

    /**
     * Method that gets the name
     * 
     * @return
     */
    @Override
    public String getName() {
        if (!StringUtils.isEmpty(associationName)) {
            return associationName;
        } else {
            return super.getName();
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public RepresentativeAssociationForm clone() throws CloneNotSupportedException {
        RepresentativeAssociationForm RepresentativeAssociationForm = copyRep(RepresentativeAssociationForm.class);

        RepresentativeAssociationForm.setProfessionalPractitionerType(professionalPractitionerType);
        RepresentativeAssociationForm.setAssociationId(associationId);
        RepresentativeAssociationForm.setAssociationName(associationName);
        return RepresentativeAssociationForm;
    }

    /**
     * Gets professional practitioner type
     * 
     * @return professional practitioner type
     */
    public ProfessionalPractitionerType getProfessionalPractitionerType() {
        return professionalPractitionerType;
    }

    /**
     * @param professionalPractitionerType
     */
    public void setProfessionalPractitionerType(ProfessionalPractitionerType professionalPractitionerType) {
        this.professionalPractitionerType = professionalPractitionerType;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.REPRESENTATIVE_FORM_ASSOCIATION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RepresentativeAssociationForm)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        RepresentativeAssociationForm that = (RepresentativeAssociationForm) o;

        if (associationId != null ? !associationId.equals(that.associationId) : that.associationId != null) {
            return false;
        }
        if (associationName != null ? !associationName.equals(that.associationName) : that.associationName != null) {
            return false;
        }
        if (professionalPractitionerType != that.professionalPractitionerType) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = value31 * result
                + (professionalPractitionerType != null ? professionalPractitionerType.hashCode() : 0);
        result = value31 * result + (associationId != null ? associationId.hashCode() : 0);
        result = value31 * result + (associationName != null ? associationName.hashCode() : 0);
        return result;
    }
}
