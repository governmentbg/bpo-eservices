package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores all the necessary information for the Designers.
 * 
 * @author serrajo
 */
public class InventorForm extends PersonForm {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The waiver. */
    private boolean waiver;

    /** The belongs to a group. */
    private boolean belongsToAGroup;

    // If designer does not waive and does not belong to a group:
    /** The first name. */
    private String firstName;

    /** The middle name. */
    private String middleName;

    /** The surname. */
    private String surname;

    /** The contact person. */
    private String contactPerson;

    // If designer does not waive and belongs to a group:
    /** The group name. */
    private String groupName;

    /** The may waiver. */
    private boolean mayWaiver;

    /** The imported from xml. */
    private boolean importedFromXml;

    /** The imported from applicant. */
    private boolean importedFromApplicant;

    /** The nationality. */
    private String nationality;

    private FileWrapper inventorAttachment;

    /**
     * Constructor.
     */
    @SuppressWarnings("unchecked")
    public InventorForm() {
        setAddress(new AddressForm());
    }

    /**
     * Returns designer's name.
     * 
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
     * 
     * @return true, if is waiver
     */
    public boolean isWaiver() {
        return waiver;
    }

    /**
     * Set whether this designer waives right to be cited.
     * 
     * @param waiver the waiver to set
     */
    public void setWaiver(boolean waiver) {
        this.waiver = waiver;
    }

    /**
     * Returns whether this designer belongs to a group of designers.
     * 
     * @return true, if is belongs to a group
     */
    public boolean isBelongsToAGroup() {
        return belongsToAGroup;
    }

    /**
     * Set whether this designer belongs to a group of designers.
     * 
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
     * @param surname the new surname
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
     * Gets the contact person.
     * 
     * @return the contactPerson
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * Sets the contact person.
     * 
     * @param contactPerson the contactPerson to set
     */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    /**
     * Returns the name of the available section.
     * 
     * @return name of the available section.
     */
    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.INVENTOR;
    }

    /**
     * Copy des.
     * 
     * @param <T> the generic type
     * @param clazz the clazz
     * @return the t
     */
    public <T extends InventorForm> T copyDes(Class<T> clazz) {
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
            t.setImportedFromApplicant(importedFromApplicant);
            t.setMayWaiver(mayWaiver);
            t.setInventorAttachment(inventorAttachment);

            return t;
        } catch (CloneNotSupportedException e) {
            throw new SPException("Clone not supported", e, "error.form.cloneNotSupported");
        }
    }

    /*
     * (non-Javadoc)
     * @see eu.ohim.sp.common.ui.form.AbstractForm#clone()
     */
    @Override
    public InventorForm clone() {
        return copyDes(InventorForm.class);
    }


    /**
     * Checks if is may waiver.
     * 
     * @return true, if is may waiver
     */
    public boolean isMayWaiver() {
        return mayWaiver;
    }

    /**
     * Sets the may waiver.
     * 
     * @param mayWaiver the new may waiver
     */
    public void setMayWaiver(boolean mayWaiver) {
        this.mayWaiver = mayWaiver;
    }

    /**
     * Checks if is imported from xml.
     * 
     * @return true, if is imported from xml
     */
    public boolean isImportedFromXml() {
        return importedFromXml;
    }

    /**
     * Sets the imported from xml.
     * 
     * @param importedFromXml the new imported from xml
     */
    public void setImportedFromXml(boolean importedFromXml) {
        this.importedFromXml = importedFromXml;
    }

    /**
     * Checks if is imported from applicant.
     * 
     * @return true, if is imported from applicant
     */
    public boolean isImportedFromApplicant() {
        return importedFromApplicant;
    }

    /**
     * Sets the imported from applicant.
     * 
     * @param importedFromApplicant the new imported from applicant
     */
    public void setImportedFromApplicant(boolean importedFromApplicant) {
        this.importedFromApplicant = importedFromApplicant;
    }

    /**
     * Gets the nationality.
     * 
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the nationality.
     * 
     * @param nationality the new nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public FileWrapper getInventorAttachment() {
        return inventorAttachment;
    }

    public void setInventorAttachment(FileWrapper inventorAttachment) {
        this.inventorAttachment = inventorAttachment;
    }
}
