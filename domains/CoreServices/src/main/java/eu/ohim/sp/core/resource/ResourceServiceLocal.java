package eu.ohim.sp.core.resource;

import javax.ejb.Local;

/**
 * Local decoration of the {@link ResourceService} system interface.
 * 
 * @see ResourceService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface ResourceServiceLocal extends ResourceService {
}
