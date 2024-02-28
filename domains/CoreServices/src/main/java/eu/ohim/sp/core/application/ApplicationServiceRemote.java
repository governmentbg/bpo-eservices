package eu.ohim.sp.core.application;

import javax.ejb.Remote;

/**
 * Remote decoration of the {@link ApplicationService} business interface.
 * 
 * @see ApplicationService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface ApplicationServiceRemote extends ApplicationService {

}
