package eu.ohim.sp.core.person;

import javax.ejb.Remote;

/**
 * Remote decoration of the {@link PersonChangeService} business interface.
 * 
 * @see PersonChangeService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface PersonChangeServiceRemote extends PersonChangeService {

}
