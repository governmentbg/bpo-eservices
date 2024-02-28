/*
 *  BusinessRulesDrools:: DroolsCore 16/10/13 10:03 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.rules.drools;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.validation.ErrorCore;
import eu.ohim.sp.core.domain.validation.ErrorList;
import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.*;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Component that wraps the knowledge bases, creates a session,
 * adds objects and fire rules
 *
 * @author jaraful
 */
@ApplicationScoped
public class DroolsCore {
	private static final String PACKAGE = "eu.ohim.sp.core.rules";
	private static final Logger LOGGER = Logger.getLogger(DroolsCore.class);

	@EJB(lookup="java:global/configurationLocal")
	private ConfigurationService configurationService;

	/**
	 * True if the knowledge bases have been  initialized
	 */
	private boolean initialized = false;
	private RulesMap rulesMap;

	/**
	 * Initialization or re initialization of the drools core that creates a knowledge
	 * base for every rule set
	 */
    public void initialize() {
		LOGGER.debug("	>>> initialize START");

		// Checks if the knowledge bases have been initialized
		if (!initialized) {
            createRuleMap();
		}

		LOGGER.debug("	<<< initialize END");

		initialized = true;
	}

	/**
	 * Returns the rule map
	 *
	 * @return the rule map
	 */
	public RulesMap getRulesMap() {
		return rulesMap;
	}

	/**
	 * Fires the rule based on the object that are passed
	 *
	 * @param module  the name of the module that makes the call
	 * @param list    the name of the list of rules that will be applied
	 * @param objects the object that we expect to apply the rules
	 * @return a list of errors
	 */
	@SuppressWarnings("unchecked")
	public ErrorList fireRulesValidate(String module, String list, Collection<Object> objects) {
		LOGGER.info("Firing rules for module: " + module + ", list: " + list);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Objects are: " + objects);

			LOGGER.debug("	>>> fireRules START");
		}

		ErrorList errorList = new ErrorList();
		objects.add(errorList);

		// Cleans the session from previous runs
		KnowledgeBase knowledgeBase = rulesMap.getKnowledgeBase(module, list);

		StatefulKnowledgeSession session = knowledgeBase.newStatefulKnowledgeSession();
		try {
			// Adds the new objects needed for the checks
			addObject(session, objects);
			// Fires the rules and checks the logs
			fireRules(session);
			showErrorsOnLog(errorList);
			// Return the errors found in the checks
			return errorList;
		} finally {
			if (session != null) {
				LOGGER.debug("		> Releasing stateful session : " + list);
				session.dispose();
			}
			LOGGER.debug("	<<< fireRules END");
		}
	}

	/**
	 * Fires the rule based on the object that are passed
	 *
	 * @param module  the name of the module that makes the call
	 * @param list    the name of the list of rules that will be applied
	 * @param objects the object that we expect to apply the rules
	 * @return a list of errors
	 */
	@SuppressWarnings("unchecked")
	public boolean fireRulesCalculate(String module, String list, Collection<Object> objects) {
		LOGGER.debug("	>>> fireRules START");
		LOGGER.debug("		> Creating stateful session : " + list);

		ErrorList errorList = new ErrorList();
		objects.add(errorList);

		// Cleans the session from previous runs
		StatefulKnowledgeSession kstateful = rulesMap.getKnowledgeBase(module, list).newStatefulKnowledgeSession();
		try {
			// Adds the new objects needed for the checks
			addObject(kstateful, objects);
			// Fires the rules and checks the logs
			fireRules(kstateful);
			if (errorList.getErrorList() == null || (errorList.getErrorList() != null && errorList.getErrorList().isEmpty())) {
				return true;
			} else {
				// show the errors found in the checks
				showErrorsOnLog(errorList);
				return false;
			}
		} finally {
			if (kstateful != null) {
				LOGGER.debug("		> Releasing stateful session : " + list);
				kstateful.dispose();
			}
			LOGGER.debug("	<<< fireRules END");
		}
	}

	/**
	 * Logs the list of errors
	 *
	 * @param errors a list of errors
	 */
	private void showErrorsOnLog(ErrorList errors) {
		Iterator<ErrorCore> listOfErrors = errors.getErrorList().iterator();
		while (listOfErrors.hasNext()) {
			LOGGER.debug("      + " + listOfErrors.next().getBusinessRule());
		}
	}

	/**
	 * Fires the rules in the given session
	 *
	 * @param kstateful the session that has been already started
	 * @return true if the rules has been fired
	 */
	private boolean fireRules(StatefulKnowledgeSession kstateful) {
		LOGGER.debug("	>>> fireRules (private) START");

		// Fires the rules
		kstateful.fireAllRules();

		LOGGER.debug("	<<< fireRules (private) END");

		return true;
	}

	/**
	 * Adds a set of rules
	 *
	 * @param kstateful the drools session where to add the objects
	 * @param objects   the object collection to add
	 */
	private void addObject(StatefulKnowledgeSession kstateful, Collection<Object> objects) {
		LOGGER.debug("	>>> addObjects START");
		if (objects != null) {
			for (Object object : objects) {
				LOGGER.debug("      > added");
				kstateful.insert(object);
			}
		}
		LOGGER.debug("	<<< addObjects END");
	}

	/**
	 * Adds a list of drl files with rules on the knowledge builder
	 *
	 * @param kbuilder             the provided knowledge builder where the drls will be added
	 * @param module               the name of the module
	 * @param list                 the name of the configuration parameter with which the list of rules are stored
	 * @return true if the files have been added successfully
	 */
	private boolean addDRLs(KnowledgeBuilder kbuilder, String module, String list) {
		LOGGER.debug("	>>> addDRLs START");
		// Gets the rule list
		Collection<String> ruleList = configurationService.getValueList(list, PACKAGE + "." + module);
		if (ruleList.isEmpty()) {
			return false;
		}
		// Adds all the rules
		Iterator<String> iterator = ruleList.iterator();
		while (iterator.hasNext()) {
			String iteratorValue = iterator.next();
			// Gets the rule information
			String ruleSet = configurationService.getXml(iteratorValue, PACKAGE + "." + module);
			if (ruleSet == null) {
				return false;
			}
			LOGGER.debug("      > " + iteratorValue);
			// Adds the ruleset
			try {
				kbuilder.add(ResourceFactory.newByteArrayResource(ruleSet.getBytes("UTF-8")), ResourceType.DRL);
			} catch (UnsupportedEncodingException e) {
				throw new SPException("Failed to load drool file", e, "error.rules");
			}
			// Checks if there are errors
			KnowledgeBuilderErrors errors = kbuilder.getErrors();
			if (errors.size() > 0) {
				for (KnowledgeBuilderError kbe : errors) {
					LOGGER.error("      > Error: " + kbe.getMessage());
				}
				return false;
			}
		}
		LOGGER.debug("	<<< addDRLs END");
		return true;
	}

	/**
	 * Creates the map of rules map. Each map has the name of the group of rules lists of each project and it's
	 * knowledge base objects
	 *
	 * @return the map of maps
	 */
    private boolean createRuleMap() {
		LOGGER.debug("	>>> createRuleMap START");
		rulesMap = new RulesMap();

		// Retrieves the rule list name of the projects
		Collection<String> ruleList = configurationService.getValueList("rules_map", PACKAGE);

		if (ruleList.isEmpty()) {
			LOGGER.debug("		> Error: No project rules were found.");
			return false;
		}

		// Gets the rules map for each project
		for (String ruleMapName : ruleList) {
			rulesMap.addMap(ruleMapName, fillRuleMap(ruleMapName));
		}

		LOGGER.debug("	<<< createRuleMap END");
		return true;
	}

	/**
	 * Creates a map with the name of the rule list and the knowledge bases created with the DRLs of this rule list.
	 *
	 * @param module               the name of the module
	 * @return the map filled up with the rule list names and their knowledge base objects
	 */
	private Map<String, KnowledgeBase> fillRuleMap(String module) {
		LOGGER.debug("	>>> fillRuleMap START");

		Map<String, KnowledgeBase> ruleKnowledgeMap = new HashMap<String, KnowledgeBase>();

		// Retrieves the amount of rules lists from the DB
		Collection<String> rulesList = configurationService.getValueList(module, PACKAGE + "." + module);

		// Creates the knowledge base for all the rules and adds it to the map
		if (rulesList != null) {
			for (String rule : rulesList) {
				KnowledgeBase knowledgeBase = createKnowledgeBase(module, rule);
				// If the knowledge base couldn't be created, is better not to add it
				if (knowledgeBase != null) {
					ruleKnowledgeMap.put(rule, knowledgeBase);
				}
			}
		}

		LOGGER.debug("	<<< createRuleMap END");

		// Returns the map with all the knowledges
		return ruleKnowledgeMap;
	}

	/**
	 * Creates a knowledge base and adds it to the map of knowledge maps
	 *
	 * @param module               the name of the module
	 * @param ruleList             the name of the configuration parameter with which the list of rules are stored
	 * @return true if the knowledge has been created successfully
	 */
	private KnowledgeBase createKnowledgeBase(String module, String ruleList) {
		LOGGER.debug("	>>> createKnowledgeBase START" + ruleList);
		// Creates the builder, get the objects list and add the specific DRLs to it
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		// Add the rules to the session
		if (!addDRLs(kbuilder, module, ruleList)) {
			LOGGER.debug("		> Error: Couldn't create knowledge base for " + ruleList + " (" + module + ")");
			return null;
		}
		// Creates the knowledge base
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

		LOGGER.debug("	<<< createKnowledgeBase END");
		return kbase;
	}
}
