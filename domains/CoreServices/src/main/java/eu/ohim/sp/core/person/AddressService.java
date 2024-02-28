/*
 *  PersonService:: AddressService 10/10/13 19:55 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.person;

import eu.ohim.sp.core.domain.contact.Address;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 10/10/13
 * Time: 18:37
 * To change this template use File | Settings | File Templates.
 */
public interface AddressService {

    Collection<Address> getAddressAutocomplete(String module, String text, Address address, int numberOfRows);
    Collection<Address> matchAddress(String module, Address address, int numberOfResults);

}
