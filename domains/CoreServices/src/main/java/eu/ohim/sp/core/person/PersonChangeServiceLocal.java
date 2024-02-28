package eu.ohim.sp.core.person;

import javax.ejb.Local;

/**
 * Local decoration of the {@link PersonChangeService} business interface.
 * 
 * @see PersonChangeService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface PersonChangeServiceLocal extends PersonChangeService {

}
