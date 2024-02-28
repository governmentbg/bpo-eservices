package eu.ohim.sp.core.register;

import javax.ejb.Remote;

/**
 * Remote decoration of the {@link DesignSearchService} business interface.
 * 
 * @see DesignSearchService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface DesignSearchServiceRemote extends DesignSearchService {
}
