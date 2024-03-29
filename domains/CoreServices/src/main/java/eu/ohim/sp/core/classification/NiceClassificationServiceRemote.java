package eu.ohim.sp.core.classification;

import javax.ejb.Remote;

/**
 * Remote decoration of the {@link NiceClassificationService} business interface.
 * 
 * @see NiceClassificationService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface NiceClassificationServiceRemote extends NiceClassificationService {
}
