package eu.ohim.sp.core.person;

import javax.ejb.Local;

/**
 * Local decoration of the {@link AddressService} business interface.
 * 
 * @see AddressService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface AddressServiceLocal extends AddressService {
}
