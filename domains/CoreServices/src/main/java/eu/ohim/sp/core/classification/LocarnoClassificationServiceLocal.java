package eu.ohim.sp.core.classification;

import javax.ejb.Local;

/**
 * Local decoration of the {@link LocarnoClassificationService} business interface.
 * 
 * @see LocarnoClassificationService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface LocarnoClassificationServiceLocal extends LocarnoClassificationService {
}
