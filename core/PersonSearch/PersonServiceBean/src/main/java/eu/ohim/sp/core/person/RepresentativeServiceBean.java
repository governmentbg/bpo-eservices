/*
 *  PersonService:: RepresentativeService 01/10/13 17:01 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.person;

import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.util.SectionUtil;
import eu.ohim.sp.external.person.PersonSearchClientInside;
import eu.ohim.sp.external.person.RepresentativeClient;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Core Representative service that offers useful methods for the Representative section
 *
 * @author ionitdi
 */
@Stateless
public class RepresentativeServiceBean implements RepresentativeServiceRemote, RepresentativeServiceLocal {

	private Logger logger = Logger.getLogger(RepresentativeService.class);

	@EJB(lookup="java:global/businessRulesLocal")
	private RulesService businessRulesService;

	@Inject @PersonSearchClientInside
	private RepresentativeClient adapter;

	/**
	 *  The Constant REPRESENTATIVELIST. 
	 *  */
    private static final String REPRESENTATIVESET = "representative_set";
	
    /**
     * {@inheritDoc}
     */
    @Override
	public String getRepresentativeAutocomplete(String module, String office, String text, int numberOfRows) {
		// this method should not call the external service if the input is invalid
		// call external service to get result
		return text == null || numberOfRows <= 0 ? null : adapter.getRepresentativeAutocomplete(module, office, text, numberOfRows);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Representative> searchRepresentative(String module, String office, String representativeId, String representativeName,
													 String representativeNationality, int numberOfResults) {
		// this method should not call the external service if the input is invalid
		return numberOfResults <= 0 ? null : adapter.searchRepresentative(module, office, representativeId, representativeName, representativeNationality, numberOfResults);
		
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public Representative getRepresentative(String module, String office, String representativeId) {
		// this method should not call the external service if the input is invalid
		// call external service to get the representative with the given Id
		return representativeId == null ? null : adapter.getRepresentative(module, office, representativeId);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Representative> matchRepresentative(String module, String office, Representative representative, int numberOfResults) {
		// if the given representative to match is null, return null
		// this method should not call the external service if the input is invalid
		// call external service that does the matching
		return representative == null || numberOfResults <= 0 ? null : adapter.matchRepresentative(module, office, representative, numberOfResults);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	//TODO: Define the key of sections
	public ErrorList validateRepresentative(String module, Representative representative,
			RulesInformation rulesInformation){
		
		if (representative != null) {
			logger.debug(">>> Validate Representative: " + representative.getKind() + ", " 
					+ representative.getIncorporationCountry());
		}
		
		//Variable declaration
		List<Object> objects = new ArrayList<Object>();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);
        
        // Prepares the objects to insert in the session
     	objects.add(section);
     	objects.add(representative);
        objects.add(rulesInformation);         	

     	// Starts the check
        ErrorList errors = businessRulesService.validate(module, REPRESENTATIVESET, objects);
        
     	if (logger.isDebugEnabled()) {
			logger.debug("<<< Validate Representative END");
		}
        
        return errors;

	}

    @Override
    public Representative saveRepresentative(String module, String office, String user, Representative representative) {
        if (representative != null
                && representative.getPersonNumber() != null) {
            Result result = adapter.saveRepresentative(module, office, user, representative);
            if (StringUtils.equals(result.getErrorCode(), Result.SUCCESS)) {
                representative.setPersonNumber(result.getResult());
            }
        }
        return representative;
    }


	@Override
	public Representative getIntlPRepresentative(String representativeId) {
		return representativeId == null ? null : adapter.getIntlPRepresentative(representativeId);
	}

	@Override
	public String getIntlPRepresentativeList() {
		return adapter.getIntlPRepresentativeList();
	}
}
