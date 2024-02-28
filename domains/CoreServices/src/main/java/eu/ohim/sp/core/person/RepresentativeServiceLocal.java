package eu.ohim.sp.core.person;

import javax.ejb.Local;

/**
 * Local decoration of the {@link RepresentativeService} business interface.
 * 
 * @see RepresentativeService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface RepresentativeServiceLocal extends RepresentativeService {

}
