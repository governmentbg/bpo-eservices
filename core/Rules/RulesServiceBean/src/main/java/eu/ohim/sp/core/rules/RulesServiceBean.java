/*
 *  BusinessRulesService:: BusinessRulesService 07/10/13 21:16 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.rules;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.rules.drools.DroolsCore;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class BusinessRulesService.
 */
@Stateless
public class RulesServiceBean implements RulesServiceRemote,
		RulesServiceLocal {

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = Logger
			.getLogger(RulesServiceBean.class);

	/**
	 * The drools core.
	 */
	@Inject
	private DroolsCore droolsCore;

	/**
	 * Initializes the drools core
	 */
	@PostConstruct
	public void init() {
		droolsCore.initialize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * RulesService#validate(java.util
	 * .List, java.lang.String, java.lang.String)
	 */
	@Override
	public ErrorList validate(String module, String ruleSet, List<Object> objects) {
		LOGGER.debug("	>>> validate START: " + ruleSet);

		ErrorList errors = new ErrorList();

		// Fires the rules adding all the errors to the error list
		errors.addAllErrors(droolsCore.fireRulesValidate(module, ruleSet, objects));

		LOGGER.debug("	<<< validate END");

		return errors;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * RulesService#calculate(java.util
	 * .List, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> calculate(String module, String ruleSet, List<Object> objects) {
		LOGGER.debug("	>>> calculate START: " + ruleSet);

		// Creates the result object and add it to the object list for the session
		Map<String, Object> resultMap = new LinkedHashMap<>();
		objects.add(resultMap);

		// Fires the rules and return the result map
		if (droolsCore.fireRulesCalculate(module, ruleSet, objects)) {
			LOGGER.debug("	<<< calculate END");
			return resultMap;
		}

		LOGGER.debug("		> ERROR: DroolsCore.fireRules returned an error.");
		return null;
	}
}
