package eu.ohim.sp.core.register;

import javax.ejb.Local;

/**
 * Local decoration of the {@link DesignSearchService} business interface.
 * 
 * @see DesignSearchService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface DesignSearchServiceLocal extends DesignSearchService {
}
