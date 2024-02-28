package eu.ohim.sp.core.epayment;

import javax.ejb.Local;

/**
 * Local decoration of the {@link EPaymentService} business interface.
 * 
 * @see EPaymentService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface EPaymentServiceLocal extends EPaymentService {
}
