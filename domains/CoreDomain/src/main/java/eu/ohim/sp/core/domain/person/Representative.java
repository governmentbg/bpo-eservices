/*
 *  CoreDomain:: Representative 10/10/13 14:34 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.person;

import java.util.Date;

/**
 * The Class Representative.
 */
public class Representative extends PersonRole {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3624687882076461566L;

	/** The kind of Representative. */
	private RepresentativeKind representativeKind;

	/** The economic connection indicator. */
	private boolean economicConnectionIndicator;
	
	/** The economic connection nature. */
	private String economicConnectionNature;

	/** The name of employer. */
	private String nameOfEmployer;

    /** the represented party kind text */
    private String representedPartyKindText;

    /** the represented party kind code */
    private RepresentedPartyKindCode representedPartyKindCode;

	private Date powValidityEndDate;
	private Boolean powReauthorizationIndicator;
	private Boolean powValidityIndefiniteIndicator;
	private Boolean powRevokesPreviousIndicator;
	private String powNote;

    /**
	 * Gets the representative kind.
	 *
	 * @return the representative kind
	 */
	public RepresentativeKind getRepresentativeKind() {
		return representativeKind;
	}

	/**
	 * Sets the kind.
	 *
	 * @param representativeKind the new kind
	 */
	public void setRepresentativeKind(RepresentativeKind representativeKind) {
		this.representativeKind = representativeKind;
	}

	/**
	 * Checks if is economic connection indicator.
	 *
	 * @return true, if is economic connection indicator
	 */
	public boolean isEconomicConnectionIndicator() {
		return economicConnectionIndicator;
	}

	/**
	 * Sets the economic connection indicator.
	 *
	 * @param economicConnectionIndicator the new economic connection indicator
	 */
	public void setEconomicConnectionIndicator(boolean economicConnectionIndicator) {
		this.economicConnectionIndicator = economicConnectionIndicator;
	}

	/**
	 * Gets the economic connection nature.
	 *
	 * @return the economic connection nature
	 */
	public String getEconomicConnectionNature() {
		return economicConnectionNature;
	}

	/**
	 * Sets the economic connection nature.
	 *
	 * @param economicConnectionNature the new economic connection nature
	 */
	public void setEconomicConnectionNature(String economicConnectionNature) {
		this.economicConnectionNature = economicConnectionNature;
	}

    public String getRepresentedPartyKindText() {
        return representedPartyKindText;
    }

    public void setRepresentedPartyKindText(String representedPartyKindText) {
        this.representedPartyKindText = representedPartyKindText;
    }

    public RepresentedPartyKindCode getRepresentedPartyKindCode() {
        return representedPartyKindCode;
    }

    public void setRepresentedPartyKindCode(RepresentedPartyKindCode representedPartyKindCode) {
        this.representedPartyKindCode = representedPartyKindCode;
    }

	public String getNameOfEmployer() {
		return nameOfEmployer;
	}

	public void setNameOfEmployer(String nameOfEmployer) {
		this.nameOfEmployer = nameOfEmployer;
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
}
