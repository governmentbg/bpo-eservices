/*
 *  ResourceService:: ResourceService 08/08/13 14:40 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.rules;

import eu.ohim.sp.core.domain.validation.ErrorList;

import java.util.List;
import java.util.Map;

/**
 * The {@code RulesService} support interface provides User Interface modules
 * ({@code TM e-Filing}, {@code DS e-Filing} and {@code e-Services}) with a methods 
 * to execute business rules.
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
public interface RulesService {

	/**
	 * Validates objects with the rule set given.
	 * 
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
	 * @param ruleSet Name of the rule set to be executed.
	 * @param objects List of objects to be validated.

	 * @return List ({@code ErrorList}) containing validation errors and/or warnings.
	 */
	ErrorList validate(String module, String ruleSet, List<Object> objects);

	/**
	 * Calculates objects with the rule set given.
	 *
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
	 * @param ruleSet Name of the rule set to be executed.
	 * @param objects List of objects to be calculated.

	 * @return List ({@code ErrorList}) containing validation errors and/or warnings.
	 */
	Map<String, Object> calculate(String module, String ruleSet, List<Object> objects);
}