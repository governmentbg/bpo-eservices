package eu.ohim.sp.core.rules;

import javax.ejb.Local;

/**
 * Local decoration of the {@link RulesService} support interface.
 * 
 * @see RulesService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface RulesServiceLocal extends RulesService {
}
