package eu.ohim.sp.core.user;

import javax.ejb.Remote;

/**
 * Local decoration of the {@link UserSearchService} business interface.
 * 
 * @see UserSearchService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface UserSearchServiceRemote extends UserSearchService {

}
