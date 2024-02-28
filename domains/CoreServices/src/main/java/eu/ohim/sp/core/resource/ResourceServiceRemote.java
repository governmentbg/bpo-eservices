package eu.ohim.sp.core.resource;

import javax.ejb.Remote;

/**
 * Remote decoration of the {@link ResourceService} system interface.
 * 
 * @see ResourceService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface ResourceServiceRemote extends ResourceService {
}
