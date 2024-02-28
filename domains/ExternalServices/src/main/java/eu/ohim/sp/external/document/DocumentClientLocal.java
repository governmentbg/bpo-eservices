package eu.ohim.sp.external.document;

import javax.ejb.Local;

/**
 * Local decoration of the {@link DocumentClient} system interface.
 * 
 * @see DocumentClient
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface DocumentClientLocal extends DocumentClient {
}
