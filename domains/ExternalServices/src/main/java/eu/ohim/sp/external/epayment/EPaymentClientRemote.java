package eu.ohim.sp.external.epayment;

import javax.ejb.Remote;

/**
 * Remote decoration of the {@link EPaymentClient} business interface.
 * 
 * @see EPaymentClient
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface EPaymentClientRemote extends EPaymentClient {
}