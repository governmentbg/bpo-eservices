/*******************************************************************************
 * * $Id:: RulesMap.java 113493 2013-06-05 10:01:04Z jaraful#$
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/

package eu.ohim.sp.core.rules.drools;

import org.drools.KnowledgeBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jaraful
 * @author Maciej Walkowiak
 */
public class RulesMap {

	/** The map with the list of rules maps. */
	private Map<String, Map<String, KnowledgeBase>> rulesMap;

	/**
	 * Instantiates a new rules map.
	 */
	public RulesMap() {
		rulesMap = new HashMap<String, Map<String, KnowledgeBase>>();
	}

	/**
	 * Adds the map.
	 * 
	 * @param mapName - the map name
	 * @param knowledgeList - the knowledge list
	 */
	public void addMap(String mapName, Map<String, KnowledgeBase> knowledgeList) {
		rulesMap.put(mapName, knowledgeList);
	}

	public KnowledgeBase getKnowledgeBase(String module, String list) {
		if (!rulesMap.containsKey(module)) {
			throw new IllegalStateException("No knowledge map found for module " + module);
		}

		Map<String, KnowledgeBase> knowledgeMap = rulesMap.get(module);

		if (!knowledgeMap.containsKey(list)) {
			throw new IllegalStateException("No knowledge base found for module " + module + " and list " + list);
		}

		return knowledgeMap.get(list);
	}

	/**
	 * Returns amount of loaded drools file for specific module.
	 * Visible only for unit tests
	 */
	int getRuleFilesCount(String module) {
		int result;

		if (!rulesMap.containsKey(module)) {
			result = 0;
		} else {
			result = rulesMap.get(module).size();
		}

		return result;
	}
}
