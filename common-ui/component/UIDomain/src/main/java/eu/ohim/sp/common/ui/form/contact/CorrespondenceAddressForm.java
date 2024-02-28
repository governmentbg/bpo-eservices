package eu.ohim.sp.common.ui.form.contact;

import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.apache.commons.lang.StringUtils;


/**
 * @author ionitdi
 */
public class CorrespondenceAddressForm extends AbstractImportableForm implements java.io.Serializable, Cloneable
{
    private static final long serialVersionUID = 1L;

    private AddressForm addressForm;

    private String correspondenceName;

    private String correspondenceEmail;

    private String correspondencePhone;
    
    private String correspondenceFax;
    
    //Valid for OBI office-designs
    private boolean submittedJurisdictionIndicator;

    private Boolean electronicCorrespondence;
    

   

	public String getCorrespondenceFax() {
		return correspondenceFax;
	}

	public void setCorrespondenceFax(String correspondenceFax) {
		this.correspondenceFax = correspondenceFax;
	}

	/**
     *
     * @return
     */
    public AddressForm getAddressForm()
    {
        return addressForm;
    }

    /**
     *
     * @param addressForm
     */
    public void setAddressForm(AddressForm addressForm)
    {
        this.addressForm = addressForm;
    }

    /**
     *
     * @return
     */
    public String getCorrespondenceEmail()
    {
        return correspondenceEmail;
    }

    /**
     *
     * @param correspondenceEmail
     */
    public void setCorrespondenceEmail(String correspondenceEmail)
    {
        this.correspondenceEmail = correspondenceEmail;
    }

    /**
     *
     * @return
     */
    public String getCorrespondenceName()
    {
        return correspondenceName;
    }

    /**
     *
     * @param correspondenceName
     */
    public void setCorrespondenceName(String correspondenceName)
    {
        this.correspondenceName = correspondenceName;
    }

    /**
     *
     * @return
     */
    public String getCorrespondencePhone()
    {
        return correspondencePhone;
    }

    /**
     *
     * @param correspondencePhone
     */
    public void setCorrespondencePhone(String correspondencePhone)
    {
        this.correspondencePhone = correspondencePhone;
    }

    public Boolean getElectronicCorrespondence() {
        return electronicCorrespondence;
    }

    public void setElectronicCorrespondence(Boolean electronicCorrespondence) {
        this.electronicCorrespondence = electronicCorrespondence;
    }

    public boolean isSubmittedJurisdictionIndicator() {
		return submittedJurisdictionIndicator;
	}

	public void setSubmittedJurisdictionIndicator(
			boolean submittedJurisdictionIndicator) {
		this.submittedJurisdictionIndicator = submittedJurisdictionIndicator;
	}

    public boolean isEmpty()
    {
        return (addressForm == null || addressForm.isEmpty()) &&
                StringUtils.isBlank(correspondenceEmail) &&
                StringUtils.isBlank(correspondenceName) &&
                StringUtils.isBlank(correspondencePhone) &&
                StringUtils.isBlank(correspondenceFax) && (!submittedJurisdictionIndicator)
            && electronicCorrespondence == null;
    }

    public CorrespondenceAddressForm clone() throws CloneNotSupportedException
    {
        CorrespondenceAddressForm caForm = new CorrespondenceAddressForm();
        caForm.setAddressForm((AddressForm)addressForm.clone());
        caForm.setCorrespondenceEmail(correspondenceEmail);
        caForm.setCorrespondenceName(correspondenceName);
        caForm.setCorrespondencePhone(correspondencePhone);
        caForm.setCorrespondenceFax(correspondenceFax);
        caForm.setImported(getImported());
        caForm.setSubmittedJurisdictionIndicator(submittedJurisdictionIndicator);
        caForm.setElectronicCorrespondence(electronicCorrespondence);
        return caForm;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.APPLICATION_CA;
    }
}
