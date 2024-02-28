/*
 *  PersonService:: DesignerService 09/09/13 13:39 karalch $
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
 * @author ionitdi
 * 
 */
@Stateless
public class DesignerServiceBean implements DesignerServiceRemote, DesignerServiceLocal {

	private Logger logger = Logger.getLogger(DesignerService.class);

	@EJB(lookup="java:global/businessRulesLocal")
	private RulesService businessRulesService;

	@Inject @PersonSearchClientInside
	private DesignerClient adapter;
	
	/**
	 * The constant DESIGNERLIST
	 */
    private static final String DESIGNERSET = "designer_set";
	
    /**
	 * {@inheritDoc}
	 */
	@Override
	public String getDesignerAutocomplete(String module, String office, String text, int numberOfRows) {
		// this method should not call the external service if the input is
		// invalid
		return text == null || numberOfRows <= 0 ? null : adapter.getDesignerAutocomplete(module, office, text, numberOfRows);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Designer> searchDesigner(String module, String office, String designerId,
                                         String designerName, String designerNationality,
			                             int numberOfResults) {
		// this method should not call the external service if the input is
		// invalid
		return numberOfResults <= 0 ? null : adapter.searchDesigner(module, office, designerId, designerName, designerNationality, numberOfResults);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Designer getDesigner(String module, String office, String designerId) {
		// this method should not call the external service if the input is
		// invalid
		return designerId == null ? null : adapter.getDesigner(module, office, designerId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Designer> matchDesigner(String module, String office, Designer designer, int numberOfResults) {
		// if the given designer to match is null, return null
		// this method should not call the external service if the input is
		// invalid
		// call external service that does the matching
		return designer == null || numberOfResults <= 0 ? null : adapter.matchDesigner(module, office, designer, numberOfResults);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ErrorList validateDesigner(String module, Designer designer, RulesInformation rulesInformation) {
		if (designer != null) {
			logger.debug(">>> Validate Designer: " + designer.getKind() + ", "
					+ designer.getIncorporationCountry());
		}
		
		//Variable declaration
		List<Object> objects = new ArrayList<Object>();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        // Prepares the objects to insert in the session
     	objects.add(section);
     	objects.add(designer);
     	objects.add(rulesInformation);
     	
     	// Starts the check
        ErrorList errors = businessRulesService.validate(module, DESIGNERSET, objects);
        
     	if (logger.isDebugEnabled()) {
			logger.debug("<<< Validate Designer END");
		}
        
        return errors;
		
	}

}
