package eu.ohim.sp.core.user;

import javax.ejb.Local;

/**
 * Local decoration of the {@link UserSearchService} business interface.
 * 
 * @see UserSearchService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface UserSearchServiceLocal extends UserSearchService {

}
