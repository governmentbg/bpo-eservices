package eu.ohim.sp.core.person;

import javax.ejb.Remote;

/**
 * Remote decoration of the {@link AddressService} business interface.
 * 
 * @see AddressService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface AddressServiceRemote extends  AddressService {
}
