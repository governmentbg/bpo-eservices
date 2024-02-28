package eu.ohim.sp.common.ui.form.person;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.design.ContainsDesignsLinkForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * Stores all the necessary information for the Designers.
 *
 * @author serrajo
 */
public class DesignerForm extends PersonForm implements ContainsDesignsLinkForm {
	
	private static final long serialVersionUID = 1L;

	private boolean waiver;
	private boolean belongsToAGroup;

	// If designer does not waive and does not belong to a group: 
	private String firstName;
	private String middleName;
	private String surname;
	private String contactPerson;
	
	// If designer does not waive and belongs to a group:
	private String groupName;
	
	// Designs linked and not linked to the designer:
	private List<DesignForm> designsLinked;
	private List<DesignForm> designsNotLinked;

	private Integer designSequenceNumber;

	private boolean mayWaiver;

	private String nationality;
	
	/**
	 * Constructor.
	 */
	@SuppressWarnings("unchecked")
	public DesignerForm() {
		setAddress(new AddressForm());
		designsLinked = LazyList.decorate(new ArrayList<DesignForm>(), FactoryUtils.instantiateFactory(DesignForm.class));
		designsNotLinked = LazyList.decorate(new ArrayList<DesignForm>(), FactoryUtils.instantiateFactory(DesignForm.class));
	}
	
	/**
	 * Returns designer's name.
	 * @return designer's name.
	 */
	@Override
	public String getName() {
		String name = null;
		if (waiver) {
			name = "";
		} else if (belongsToAGroup) {
			name = groupName;
		} else {
			List<String> names = new ArrayList<String>();
			if (StringUtils.isNotBlank(firstName)) {
				names.add(firstName);
			}
			if (StringUtils.isNotBlank(middleName)) {
				names.add(middleName);
			}
			if (StringUtils.isNotBlank(surname)) {
				names.add(surname);
			}
			name = StringUtils.join(names, " ");
		}
		return name;
	}
	
	/**
	 * Returns whether this designer waives right to be cited.
	 * @return
	 */
	public boolean isWaiver() {
		return waiver;
	}

	/**
	 * Set whether this designer waives right to be cited. 
	 * @param waiver the waiver to set
	 */
	public void setWaiver(boolean waiver) {
		this.waiver = waiver;
	}

	/**
	 * Returns whether this designer belongs to a group of designers.
	 * @return
	 */
	public boolean isBelongsToAGroup() {
		return belongsToAGroup;
	}

	/**
	 * Set whether this designer belongs to a group of designers.
	 * @param belongs the belongs to set
	 */
	public void setBelongsToAGroup(boolean belongs) {
		this.belongsToAGroup = belongs;
	}
	
	/**
	 * Method that returns the firstName.
	 *
	 * @return the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Method that sets the firstName.
	 *
	 * @param firstName the firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Method that returns the middleName.
	 *
	 * @return the middleName.
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Method that sets the middleName.
	 *
	 * @param middleName the middleName to set.
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	/**
	 * Method that returns the surname.
	 *
	 * @return the surname.
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Method that sets the surname.
	 *
	 * @param surmane the surname to set.
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	/**
	 * Method that returns the groupName.
	 * 
	 * @return the groupName.
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * Method that sets the groupName.
	 * 
	 * @param groupName
	 *            the groupName to set.
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	
	/**
	 * @return the designs linked list.
	 */
	@Override
	public List<DesignForm> getDesignsLinked() {
		return designsLinked;
	}

	/**
	 * @param contactPerson the designs linked list to set
	 */
	@Override
	public void setDesignsLinked(List<DesignForm> designsLinked) {
		this.designsLinked = designsLinked;
	}
	
	/**
	 * @return the designs not linked list.
	 */
	@Override
	public List<DesignForm> getDesignsNotLinked() {
		return designsNotLinked;
	}

	/**
	 * @param contactPerson the designs not linked list to set
	 */
	@Override  
	public void setDesignsNotLinked(List<DesignForm> designsNotLinked) {
		this.designsNotLinked = designsNotLinked;
	}
	
	/**
	 * Returns the name of the available section.
	 * @return name of the available section.
	 */
	@Override
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.DESIGNERS;
	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public <T extends DesignerForm> T copyDes(Class<T> clazz) {
        try {
        	T t = copy(clazz);
        	t.setWaiver(waiver);
        	t.setBelongsToAGroup(belongsToAGroup);
        	t.setFirstName(firstName);
        	t.setMiddleName(middleName);
        	t.setSurname(surname);
        	t.setGroupName(groupName);
        	t.setContactPerson(contactPerson);
        	t.setNationality(nationality);
			if (designsLinked != null) {
				for (DesignForm designLinkedForm : designsLinked) {
					t.getDesignsLinked().add(designLinkedForm.clone());
				}
			}
			if (designsNotLinked != null) {
				for (DesignForm designNotLinkedForm : designsNotLinked) {
					t.getDesignsNotLinked().add(designNotLinkedForm.clone());
				}
			}
			t.setMayWaiver(mayWaiver);
			
			return t;
        } catch (CloneNotSupportedException e) {
            throw new SPException("Clone not supported", e, "error.form.cloneNotSupported");
        }
	}
	
	@Override
	public DesignerForm clone() {
		return copyDes(DesignerForm.class);
	}

	public Integer getDesignSequenceNumber() {
		return designSequenceNumber;
	}

	public void setDesignSequenceNumber(Integer designSequenceNumber) {
		this.designSequenceNumber = designSequenceNumber;
	}
	
	public boolean isMayWaiver() {
		return mayWaiver;
	}

	public void setMayWaiver(boolean mayWaiver) {
		this.mayWaiver = mayWaiver;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
}
