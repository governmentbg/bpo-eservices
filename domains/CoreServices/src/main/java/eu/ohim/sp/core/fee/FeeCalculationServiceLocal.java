package eu.ohim.sp.core.fee;

import javax.ejb.Local;

/**
 * Local decoration of the {@link FeeCalculationService} support interface.
 * 
 * @see FeeCalculationService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface FeeCalculationServiceLocal extends FeeCalculationService {
}
