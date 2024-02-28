package eu.ohim.sp.core.person;

import javax.ejb.Remote;

/**
 * Remote decoration of the {@link ApplicantService} business interface.
 * 
 * @see ApplicantService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface ApplicantServiceRemote extends ApplicantService {
}
