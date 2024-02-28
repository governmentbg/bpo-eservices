package eu.ohim.sp.external.epayment;

import javax.ejb.Local;

/**
 * Local decoration of the {@link EPaymentClient} business interface.
 * 
 * @see EPaymentClient
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface EPaymentClientLocal extends EPaymentClient {
}
