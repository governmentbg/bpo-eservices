package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.contact.AddressForm;


public class HolderForm extends PersonForm implements Cloneable {

	private static final long serialVersionUID = 1L;

	private static final int value31 = 31;
	
	private HolderKindForm type;

	private String name;

	private String domicile;

	private String domicileCountry;

	private String contactPerson;
	
	private Boolean holderHasChanged;
	

	public HolderForm() {
		setAddress(new AddressForm());
	}

	/**
	 * Method that returns the name
	 * 
	 * @return the name of the applicant
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method that sets the name
	 * 
	 * @param name
	 *            the name of the applicant
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method that returns the type
	 * 
	 * @return the type
	 */
	public HolderKindForm getType() {
		return type;
	}

	/**
	 * Method that sets the type
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(HolderKindForm type) {
		this.type = type;
	}

	/**
	 * Method that returns the domicile country
	 * 
	 * @return the domicile country
	 */
	public String getDomicileCountry() {
		return domicileCountry;
	}

	/**
	 * Method that sets the domicile country
	 * 
	 * @param domicileCountry
	 *            the domicile country to set
	 */
	public void setDomicileCountry(String domicileCountry) {
		this.domicileCountry = domicileCountry;
	}

	public <T extends HolderForm> T copyHol(Class<T> clazz) {
        try
        {
		T t = copy(clazz);

		t.setDomicileCountry(domicileCountry);
		t.setDomicile(getDomicile());
		t.setType(type);
		t.setName(getName());
		t.setType(type);
		t.setContactPerson(contactPerson);
		t.setCurrentUserIndicator(getCurrentUserIndicator());
		t.setImported(getImported());
		return t;
        }
        catch (CloneNotSupportedException e)
        {
            throw new SPException("Clone not supported", e, "error.form.cloneNotSupported");
        }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public HolderForm clone() throws CloneNotSupportedException{
		return copyHol(HolderForm.class);
	}

	public String getDomicile() {
		return domicile;
	}

	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}

	/**
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson
	 *            the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}


    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
			return true;
		}
        if (!(o instanceof HolderForm)) {
			return false;
		}
        if (!super.equals(o)) {
			return false;
		}

        HolderForm that = (HolderForm) o;

        if (contactPerson != null ? !contactPerson.equals(that.contactPerson) : that.contactPerson != null) {
			return false;
		}
        if (domicile != null ? !domicile.equals(that.domicile) : that.domicile != null) {
			return false;
		}
        if (domicileCountry != null ? !domicileCountry.equals(that.domicileCountry) : that.domicileCountry != null) {
			return false;
		}
        if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
        if (type != that.type) {
			return false;
		}

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = value31 * result + (type != null ? type.hashCode() : 0);
        result = value31 * result + (name != null ? name.hashCode() : 0);
        result = value31 * result + (domicile != null ? domicile.hashCode() : 0);
        result = value31 * result + (domicileCountry != null ? domicileCountry.hashCode() : 0);
        result = value31 * result + (contactPerson != null ? contactPerson.hashCode() : 0);
        return result;
    }

	public Boolean getHolderHasChanged() {
		return holderHasChanged;
	}

	public void setHolderHasChanged(Boolean holderHasChanged) {
		this.holderHasChanged = holderHasChanged;
	}
}
