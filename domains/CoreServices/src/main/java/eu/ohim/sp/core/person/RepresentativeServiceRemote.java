package eu.ohim.sp.core.person;

import javax.ejb.Remote;

/**
 * Remote decoration of the {@link RepresentativeService} business interface.
 * 
 * @see RepresentativeService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface RepresentativeServiceRemote extends RepresentativeService {

}
