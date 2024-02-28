package eu.ohim.sp.external.injectors;

import eu.ohim.sp.external.domain.contact.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcoantonioalberoalbero on 13/10/16.
 */
public class AddressInjector {
    public static List<Address> getAddressAutocomplete(String module, String text, eu.ohim.sp.external.domain.contact.Address address, Integer numberOfRows) {
        List<eu.ohim.sp.external.domain.contact.Address> addresses = new ArrayList<Address>();

        eu.ohim.sp.external.domain.contact.Address addressResult = new eu.ohim.sp.external.domain.contact.Address();
        addressResult.setCountry("España");
        addressResult.setCity("Alicante");
        addressResult.setState("Alicante");
        addressResult.setStreet("Avenida de Europa");
        addressResult.setStreetNumber("4");
        addressResult.setPostCode("03008");

        eu.ohim.sp.external.domain.contact.Address address2 = new eu.ohim.sp.external.domain.contact.Address();
        address2.setCountry("Estonia");
        address2.setCity("Tallinn");
        address2.setState("Tallinn");
        address2.setStreet("Suur-Karja");
        address2.setStreetNumber("4");
        address2.setPostCode("10133");

        eu.ohim.sp.external.domain.contact.Address address3 = new eu.ohim.sp.external.domain.contact.Address();
        address3.setCountry("Estonia");
        address3.setCity("Tallinn");
        address3.setState("Tallinn");
        address3.setStreet("Suur-Sõjamäe");
        address3.setStreetNumber("8");
        address3.setPostCode("10133");

        addresses.add(addressResult);
        addresses.add(address2);
        addresses.add(address3);

        return addresses;
    }

    public static List<eu.ohim.sp.external.domain.contact.Address> matchAddress(String module, eu.ohim.sp.external.domain.contact.Address address, Integer numberOfResults) {
        return new ArrayList<eu.ohim.sp.external.domain.contact.Address>();
    }
}
