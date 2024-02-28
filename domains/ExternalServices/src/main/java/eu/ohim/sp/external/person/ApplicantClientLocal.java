package eu.ohim.sp.external.person;

import javax.ejb.Local;

/**
 * Local decoration of the {@link ApplicantClient} business interface.
 * 
 * @see ApplicantClient
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface ApplicantClientLocal extends ApplicantClient {
}
