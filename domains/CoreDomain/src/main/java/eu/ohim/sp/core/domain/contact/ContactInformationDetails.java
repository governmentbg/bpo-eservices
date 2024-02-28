/*
 *  CoreDomain:: ContactInformationDetails 15/10/13 16:15 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
/**
 * 
 */
package eu.ohim.sp.core.domain.contact;

import java.io.Serializable;
import java.util.List;

import eu.ohim.sp.core.domain.id.Id;


/**
 * @author ionitdi
 * 
 */
public class ContactInformationDetails extends Id implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5545859574083241543L;

	private List<Phone> phone;
	private List<String> fax;
	private List<String> email;
	private List<String> url;
	private List<String> otherElectronicAddress;

	public List<Phone> getPhone() {
		return phone;
	}
	public void setPhone(List<Phone> phone) {
		this.phone = phone;
	}
	public List<String> getFax() {
		return fax;
	}
	public void setFax(List<String> fax) {
		this.fax = fax;
	}
	public List<String> getEmail() {
		return email;
	}
	public void setEmail(List<String> email) {
		this.email = email;
	}
	public List<String> getUrl() {
		return url;
	}
	public void setUrl(List<String> url) {
		this.url = url;
	}
	public List<String> getOtherElectronicAddress() {
		return otherElectronicAddress;
	}
	public void setOtherElectronicAddress(List<String> otherElectronicAddress) {
		this.otherElectronicAddress = otherElectronicAddress;
	}

}
