package eu.ohim.sp.core.person;

import javax.ejb.Local;

/**
 * Local decoration of the {@link DesignerService} business interface.
 * 
 * @see DesignerService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface DesignerServiceLocal extends DesignerService {
}
