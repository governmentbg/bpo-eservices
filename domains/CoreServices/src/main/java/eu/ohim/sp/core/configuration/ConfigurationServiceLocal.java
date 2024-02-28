package eu.ohim.sp.core.configuration;

import javax.ejb.Local;

/**
 * Local decoration of the {@link ConfigurationService} system interface.
 * 
 * @see ConfigurationService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface ConfigurationServiceLocal extends ConfigurationService {
}
