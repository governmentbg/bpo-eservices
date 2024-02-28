package eu.ohim.sp.external.person;

import javax.ejb.Local;

/**
 * Local decoration of the {@link RepresentativeClient} business interface.
 * 
 * @see RepresentativeClient
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface RepresentativeClientLocal extends RepresentativeClient {
}
