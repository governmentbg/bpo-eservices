package eu.ohim.sp.core.rules;

import javax.ejb.Remote;

/**
 * Remote decoration of the {@link RulesService} support interface.
 * 
 * @see RulesService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface RulesServiceRemote extends RulesService {
}
