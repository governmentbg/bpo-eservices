/*
 *  CoreDomain:: AddressBook 04/10/13 19:33 KARALCH $
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

import eu.ohim.sp.core.domain.id.Id;


/**
 * @author ionitdi
 */
@Deprecated
public class AddressBook extends Id implements Serializable {

    private static final long serialVersionUID = -3248155475383815293L;

    private Address address;

    /**
     * Get the address
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Set the address
     *
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

}
