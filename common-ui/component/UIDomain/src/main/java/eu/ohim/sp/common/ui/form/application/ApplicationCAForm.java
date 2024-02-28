package eu.ohim.sp.common.ui.form.application;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.contact.CorrespondenceAddressForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * @author garjuan
 */
public class ApplicationCAForm extends AbstractImportableForm {

	private static final long serialVersionUID = -1656756821680776947L;

	private String person;
	
	private CorrespondenceAddressForm correspondenceAddressForm;

	public String getPerson() {
		return person;
	}


	public void setPerson(String person) {
		this.person = person;
	}
	
	public CorrespondenceAddressForm getCorrespondenceAddressForm() {
		return correspondenceAddressForm;
	}


	public void setCorrespondenceAddressForm(
			CorrespondenceAddressForm correspondenceAddressForm) {
		this.correspondenceAddressForm = correspondenceAddressForm;
	}

	
	public <T extends ApplicationCAForm> T copyApp(Class<T> clazz) throws CloneNotSupportedException {
        try   {
        	T t = clazz.newInstance();		
        	t.setId(id);
        	t.setCorrespondenceAddressForm(correspondenceAddressForm.clone());
        	return t;
		} catch (InstantiationException e) {
			throw new SPException(e);
		} catch (IllegalAccessException e) {
			throw new SPException(e);
		}
	}
		
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ApplicationCAForm clone() throws CloneNotSupportedException{
		return copyApp(ApplicationCAForm.class);
	}

	
    /**
     * {@inheritDoc}
     */
    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.APPLICATION_CA;
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((correspondenceAddressForm == null) ? 0
						: correspondenceAddressForm.hashCode());
		result = prime * result + ((person == null) ? 0 : person.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationCAForm other = (ApplicationCAForm) obj;
		if (correspondenceAddressForm == null) {
			if (other.correspondenceAddressForm != null)
				return false;
		} else if (!correspondenceAddressForm
				.equals(other.correspondenceAddressForm))
			return false;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		return true;
	}

}
