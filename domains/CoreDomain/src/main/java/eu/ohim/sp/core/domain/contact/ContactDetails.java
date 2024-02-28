/*
 *  CoreDomain:: ContactDetails 23/08/13 10:00 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.contact;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 22/08/13
 * Time: 12:24
 */
public class ContactDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * the address of contact details
     */
    private List<Address> address;

    /**
     * the phone of contact details
     */
    private List<Phone> phone;
    /**
     * the fax of contact details
     */
    private List<Phone> fax;
    /**
     * the email of contact details
     */
    private List<String> email;
    /**
     * url of contact details
     */
    private List<String> url;

    private Boolean electronicCorrespondence;

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<Phone> getPhone() {
        return phone;
    }

    public void setPhone(List<Phone> phone) {
        this.phone = phone;
    }

    public List<Phone> getFax() {
        return fax;
    }

    public void setFax(List<Phone> fax) {
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

    public Boolean getElectronicCorrespondence() {
        return electronicCorrespondence;
    }

    public void setElectronicCorrespondence(Boolean electronicCorrespondence) {
        this.electronicCorrespondence = electronicCorrespondence;
    }
}
