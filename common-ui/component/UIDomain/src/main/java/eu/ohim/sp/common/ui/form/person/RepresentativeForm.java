/*******************************************************************************
 * * $Id:: RepresentativeForm.java 53367 2012-12-19 13:22:48Z ionitdi            $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;

import java.util.Date;
import java.util.Objects;

/**
 * Stores all the necessary information for the Representatives
 *
 * @author ckara & ionitdi
 */
public class RepresentativeForm extends PersonForm implements Cloneable {

	private static final long serialVersionUID = 1L;

	private RepresentativeKindForm type;

	private String name;

	private String nationality;

	private String domicile;

	private String countryOfDomicile;

	private boolean feeByRepresentativeInfo;

	private String reference;	

	private FileWrapper representativeAttachment;
	
    private boolean representativeIsOwner;

    /**
     * Change of representative fields
     */
    private String previousPersonId;
    private String previousPersonName;
    private String previousPersonAddress;
    private ChangePersonType changeType;

	private Date powValidityEndDate;
	private Boolean powReauthorizationIndicator;
	private Boolean powValidityIndefiniteIndicator;
	private Boolean powRevokesPreviousIndicator;
	private String powNote;

	public RepresentativeForm() {
		setAddress(new AddressForm());
		setPersonIdentifierForm(new PersonIdentifierForm());
	}

	/**
	 * Method that returns the name
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method that sets the name
	 *
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method that returns the type
	 *
	 * @return the type
	 */
	public RepresentativeKindForm getType() {
		return type;
	}

	/**
	 * Method that sets the type
	 *
	 * @param type
	 *            the type to set
	 */
	public void setType(RepresentativeKindForm type) {
		this.type = type;
	}

	/**
	 * Method that gets the nationality
	 *
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * Method that sets the nationality
	 *
	 * @param nationality
	 *            the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * Method that returns the domicile
	 *
	 * @return the domicile
	 */
	public String getDomicile() {
		return domicile;
	}

	/**
	 * Method that sets the domicile
	 *
	 * @param domicile
	 *            the domicile to set
	 */
	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}

	/**
	 * Method that gets the country of domicile
	 *
	 * @return the countryOfDomicile
	 */
	public String getCountryOfDomicile() {
		return countryOfDomicile;
	}

	/**
	 * Method that sets the country of domicile
	 *
	 * @param countryOfDomicile
	 *            the countryOfDomicile to set
	 */
	public void setCountryOfDomicile(String countryOfDomicile) {
		this.countryOfDomicile = countryOfDomicile;
	}

	public <T extends RepresentativeForm> T copyRep(Class<T> clazz) {
        try
        {
			T t = copy(clazz);
			t.setCountryOfDomicile(countryOfDomicile);
			t.setDomicile(domicile);
			t.setName(name);
			t.setNationality(nationality);
			t.setType(type);
			t.setFeeByRepresentativeInfo(getFeeByRepresentativeInfo());
			t.setReference(getReference());
			t.setRepresentativeAttachment(representativeAttachment);
			t.setRepresentativeIsOwner(representativeIsOwner);
			t.setPreviousPersonAddress(previousPersonAddress);
			t.setPreviousPersonName(previousPersonName);
            t.setPreviousPersonId(previousPersonId);
			t.setChangeType(changeType);
			t.setPowValidityEndDate(powValidityEndDate);
			t.setPowReauthorizationIndicator(powReauthorizationIndicator);
			t.setPowNote(powNote);
			t.setPowValidityIndefiniteIndicator(powValidityIndefiniteIndicator);
			t.setPowRevokesPreviousIndicator(powRevokesPreviousIndicator);

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
	public RepresentativeForm clone() throws CloneNotSupportedException{
		return copyRep(RepresentativeForm.class);
	}

	/**
	 * @return true if fee by representative info, otherwise false
	 */
	public boolean getFeeByRepresentativeInfo() {
		return feeByRepresentativeInfo;
	}

	/**
	 * @param feeByRepresentativeInfo
	 */
	public void setFeeByRepresentativeInfo(boolean feeByRepresentativeInfo) {
		this.feeByRepresentativeInfo = feeByRepresentativeInfo;
	}

	/**
	 * Gets references
	 * @return String reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

    public String getOrganization()
    {
        return "";
    }
    
	/**
	 * Gets representativeAttachment
	 * @return String representativeAttachment
	 */    
	public FileWrapper getRepresentativeAttachment() {
		return representativeAttachment;
	}

	/**
	 * @param file
	 */	
	public void setRepresentativeAttachment(FileWrapper file) {
		this.representativeAttachment = file;
	}

	public boolean isRepresentativeIsOwner() {
		return representativeIsOwner;
	}

	public void setRepresentativeIsOwner(boolean representativeIsOwner) {
		this.representativeIsOwner = representativeIsOwner;
	}

    public String getPersonRol() {
        if (changeType != null) {
            switch (changeType) {
                case ADD_NEW_REPRESENTATIVE:
                case REPLACE_REPRESENTATIVE:
                case REMOVE_REPRESENTATIVE:
                case CHANGE_REPRESENTATIVE_ADDRESS:
                case CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS:
                    return "Representative";
                case ADD_NEW_CORRESPONDENT:
                case REPLACE_CORRESPONDENT:
                case REMOVE_CORRESPONDENT:
                case CHANGE_CORRESPONDENT_ADDRESS:
                    return "Correspondent";
            }
        }
        return null;
    }

    public ChangePersonType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangePersonType changeType) {
        this.changeType = changeType;
    }

    public String getPreviousPersonId() {
        return previousPersonId;
    }

    public void setPreviousPersonId(String previousPersonId) {
        this.previousPersonId = previousPersonId;
    }

    public String getPreviousPersonName() {
        return previousPersonName;
    }

    public void setPreviousPersonName(String previousPersonName) {
        this.previousPersonName = previousPersonName;
    }

    public String getPreviousPersonAddress() {
        return previousPersonAddress;
    }

    public void setPreviousPersonAddress(String previousPersonAddress) {
        this.previousPersonAddress = previousPersonAddress;
    }

	public Date getPowValidityEndDate() {
		return powValidityEndDate;
	}

	public void setPowValidityEndDate(Date powValidityEndDate) {
		this.powValidityEndDate = powValidityEndDate;
	}

	public Boolean getPowReauthorizationIndicator() {
		return powReauthorizationIndicator;
	}

	public void setPowReauthorizationIndicator(Boolean powReauthorizationIndicator) {
		this.powReauthorizationIndicator = powReauthorizationIndicator;
	}

	public String getPowNote() {
		return powNote;
	}

	public void setPowNote(String powNote) {
		this.powNote = powNote;
	}

	public Boolean getPowValidityIndefiniteIndicator() {
		return powValidityIndefiniteIndicator;
	}

	public void setPowValidityIndefiniteIndicator(Boolean powValidityIndefiniteIndicator) {
		this.powValidityIndefiniteIndicator = powValidityIndefiniteIndicator;
	}

	public Boolean getPowRevokesPreviousIndicator() {
		return powRevokesPreviousIndicator;
	}

	public void setPowRevokesPreviousIndicator(Boolean powRevokesPreviousIndicator) {
		this.powRevokesPreviousIndicator = powRevokesPreviousIndicator;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RepresentativeForm)) return false;
		if (!super.equals(o)) return false;
		RepresentativeForm that = (RepresentativeForm) o;
		return feeByRepresentativeInfo == that.feeByRepresentativeInfo &&
			representativeIsOwner == that.representativeIsOwner &&
			type == that.type &&
			Objects.equals(name, that.name) &&
			Objects.equals(nationality, that.nationality) &&
			Objects.equals(domicile, that.domicile) &&
			Objects.equals(countryOfDomicile, that.countryOfDomicile) &&
			Objects.equals(reference, that.reference) &&
			Objects.equals(representativeAttachment, that.representativeAttachment) &&
			Objects.equals(previousPersonId, that.previousPersonId) &&
			Objects.equals(previousPersonName, that.previousPersonName) &&
			Objects.equals(previousPersonAddress, that.previousPersonAddress) &&
			Objects.equals(powValidityEndDate, that.powValidityEndDate) &&
			Objects.equals(powReauthorizationIndicator, that.powReauthorizationIndicator) &&
			Objects.equals(powNote, that.powNote) &&
			Objects.equals(powValidityIndefiniteIndicator, that.powValidityIndefiniteIndicator) &&
			Objects.equals(powRevokesPreviousIndicator, that.powRevokesPreviousIndicator) &&
			changeType == that.changeType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), type, name, nationality, domicile, countryOfDomicile, feeByRepresentativeInfo, reference, representativeAttachment, representativeIsOwner, previousPersonId, previousPersonName, previousPersonAddress, changeType,
				powValidityEndDate, powReauthorizationIndicator, powNote, powValidityIndefiniteIndicator, powRevokesPreviousIndicator);
	}
}
