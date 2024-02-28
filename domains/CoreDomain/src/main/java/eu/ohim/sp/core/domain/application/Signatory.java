/*
 *  CoreDomain:: Signatory 02/10/13 16:05 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.application;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.person.PersonRoleKind;

import java.io.Serializable;
import java.util.Date;

public class Signatory extends Id implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 8851659981747610863L;

    private Address address;
    private String name;
	private Date date;
	private PersonRoleKind capacity;
	private String place;
    private String associatedText;
    private String capacityText;

    private String manner;
    private Boolean confirmation;
    private String email;
    private String userId;


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return DateUtil.cloneDate(date);
    }

    public void setDate(Date date) {
        this.date = DateUtil.cloneDate(date);
    }

    public PersonRoleKind getCapacity() {
        return capacity;
    }

    public void setCapacity(PersonRoleKind capacity) {
        this.capacity = capacity;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAssociatedText() {
        return associatedText;
    }

    public void setAssociatedText(String associatedText) {
        this.associatedText = associatedText;
    }

    public String getCapacityText() {
        return capacityText;
    }

    public void setCapacityText(String capacityText) {
        this.capacityText = capacityText;
    }

    public String getManner() {
        return manner;
    }

    public void setManner(String manner) {
        this.manner = manner;
    }

    public Boolean getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(Boolean confirmation) {
        this.confirmation = confirmation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
    
}
