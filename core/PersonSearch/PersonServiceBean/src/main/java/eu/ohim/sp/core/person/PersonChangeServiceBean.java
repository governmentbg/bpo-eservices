/*
 *  PersonService:: PersonChangeService 09/09/13 13:39 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.person;

import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.person.PersonChange;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.util.SectionUtil;
import eu.ohim.sp.external.person.DesignerClient;
import eu.ohim.sp.external.person.PersonSearchClientInside;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Core Designer service that offers useful methods for the Designer section
 * 
 * @author velosma
 * 
 */
@Stateless
public class PersonChangeServiceBean implements PersonChangeServiceRemote, PersonChangeServiceLocal {

	private Logger logger = Logger.getLogger(PersonChangeService.class);

	@EJB(lookup="java:global/businessRulesLocal")
	private RulesService businessRulesService;

	@Inject @PersonSearchClientInside
	private DesignerClient adapter;
	
	/**
	 * The constant PERSONCHANGELIST
	 */
    private static final String PERSONCHANGESET = "personChange_set";
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ErrorList validatePersonChange(String module, PersonChange personChange, RulesInformation rulesInformation) {
		if (personChange != null) {
			logger.debug(">>> Validate PersonChange: " + personChange.getPersonChangeKind());
		}
		
		//Variable declaration
		List<Object> objects = new ArrayList<Object>();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        // Prepares the objects to insert in the session
     	objects.add(section);
     	objects.add(personChange);
     	objects.add(rulesInformation);
     	
     	// Starts the check
        ErrorList errors = businessRulesService.validate(module, PERSONCHANGESET, objects);
        
     	if (logger.isDebugEnabled()) {
			logger.debug("<<< Validate PersonChange END");
		}
        
        return errors;
		
	}

}
