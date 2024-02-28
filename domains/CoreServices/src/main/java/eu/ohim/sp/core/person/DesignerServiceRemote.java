package eu.ohim.sp.core.person;

import javax.ejb.Remote;

/**
 * Remote decoration of the {@link DesignerService} business interface.
 * 
 * @see DesignerService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface DesignerServiceRemote extends DesignerService {
}
