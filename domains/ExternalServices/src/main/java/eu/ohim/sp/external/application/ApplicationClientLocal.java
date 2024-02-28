package eu.ohim.sp.external.application;

import javax.ejb.Local;

/**
 * Local decoration of the {@link ApplicationClient} business interface.
 * 
 * @see ApplicationClient
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface ApplicationClientLocal extends ApplicationClient {
}