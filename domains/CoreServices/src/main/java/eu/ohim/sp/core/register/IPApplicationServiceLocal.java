package eu.ohim.sp.core.register;

import javax.ejb.Local;

/**
 * Local decoration of the {@link IPApplicationService} business interface.
 * 
 * @see IPApplicationService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface IPApplicationServiceLocal extends IPApplicationService {
}
