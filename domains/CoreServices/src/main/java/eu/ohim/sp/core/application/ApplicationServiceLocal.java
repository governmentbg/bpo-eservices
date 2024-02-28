package eu.ohim.sp.core.application;

import javax.ejb.Local;

/**
 * Local decoration of the {@link ApplicationService} business interface.
 * 
 * @see ApplicationService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface ApplicationServiceLocal extends ApplicationService {

}
