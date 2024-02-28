package eu.ohim.sp.core.fee;

import javax.ejb.Remote;

/**
 * Remote decoration of the {@link FeeCalculationService} support interface.
 * 
 * @see FeeCalculationService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface FeeCalculationServiceRemote extends FeeCalculationService {
}
