package eu.ohim.sp.external.transformers;

import eu.ohim.sp.external.common.PersonMatchRequest;
import eu.ohim.sp.external.domain.person.PersonRole;

/**
 * Created by Raya
 * 05.11.2020
 */
public class PersonMatchRequestConverter {

    public static PersonMatchRequest convertPersonToMatchRequest(PersonRole person){
        PersonMatchRequest request = new PersonMatchRequest();
        if(person !=null) {
            if(person.getName() != null) {
                request.setFirstName(person.getName().getFirstName());
                request.setMiddleName(person.getName().getMiddleName());
                request.setLastName(person.getName().getLastName());
                request.setOrganizationName(person.getName().getOrganizationName());
            }
            if(person.getAddresses() != null && person.getAddresses().size()>0){
                request.setCountryCode(person.getAddresses().get(0).getCountry());
                request.setCity(person.getAddresses().get(0).getCity());
                request.setPostCode(person.getAddresses().get(0).getPostCode());
                request.setStreet(person.getAddresses().get(0).getStreet());
            }
        }
        return request;
    }
}
