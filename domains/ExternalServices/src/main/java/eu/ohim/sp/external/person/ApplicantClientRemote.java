package eu.ohim.sp.external.person;

import javax.ejb.Remote;

/**
 * Remote decoration of the {@link ApplicantClient} business interface.
 * 
 * @see ApplicantClient
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface ApplicantClientRemote extends ApplicantClient {
}
