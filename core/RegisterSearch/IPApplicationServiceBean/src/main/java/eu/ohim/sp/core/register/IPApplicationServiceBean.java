/*
 *  IPApplicationService:: IPApplicationService 16/10/13 10:11 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.register;

import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.domain.claim.ConversionPriority;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.claim.IPPriority;
import eu.ohim.sp.core.domain.claim.Seniority;
import eu.ohim.sp.core.domain.claim.TransformationPriority;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.util.SectionUtil;

import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Stateless
public class IPApplicationServiceBean implements IPApplicationServiceLocal, IPApplicationServiceRemote {

	@EJB(lookup="java:global/businessRulesLocal")
    private RulesService businessRulesService;
	
	private static final Logger LOGGER = Logger.getLogger(IPApplicationService.class);
	
	private static final String CLAIM_SET = "claim_set";

	private static final String TM_REGISTRATION_LIST = "registration_list";

    /**
     * Validates the priority according to some rules
     * @param module the module to get the sections
     * @param priority the seniority object that we are interested
     * @param rulesInformation extra rules information
     * @return the list of possible errors
     */
    @Override
    public ErrorList validatePriorityClaim(String module, IPPriority priority, RulesInformation rulesInformation) {
        if (LOGGER.isDebugEnabled()){
            LOGGER.debug(" >>> validateConventionPriorityClaim START");
            if (priority!=null) {
                LOGGER.debug("		- Validating Priority Form : " + priority.getFilingNumber());
            }
        }

        //Variable declaration
        List<Object> objects = new ArrayList<Object>();
        ErrorList errors = new ErrorList();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        if (section != null) {
            // Prepares the objects to insert in the session
            objects.add(section);
            objects.add(priority);
            objects.add(rulesInformation);

            // Starts the check
            errors = businessRulesService.validate(module, CLAIM_SET, objects);
            LOGGER.debug(" <<< validateConventionPriorityClaim END");
        }

        return errors;
    }

	/**
	 * Validates the seniority according to some rules
	 * @param module the module to get the sections
	 * @param seniority the seniority object that we are interested
	 * @param rulesInformation extra rules information
	 * @return the list of possible errors
	 */
    @Override
    public ErrorList validateSeniorityClaim(String module, Seniority seniority, RulesInformation rulesInformation) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(" >>> validateSeniorityClaim START");
            if (seniority!=null) {
                LOGGER.debug("		- Validating Seniority Form : " + seniority.getFilingNumber());
            }
        }

		//Variable declaration
		List<Object> objects = new ArrayList<Object>();
        ErrorList errors = new ErrorList();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        if (section != null) {
	        // Prepares the objects to insert in the session
	     	objects.add(section);
	     	objects.add(seniority);
	
	     	// Starts the check
	     	errors = businessRulesService.validate(module, CLAIM_SET, objects);
			LOGGER.debug(" <<< validateSeniorityClaim END");
        }
		
		return errors;
	}

	/**
	 * Update the registration number field if necessary
	 * @param module the module to get the registration number
	 * @param seniority the seniority object that we are interested
	 * @return the new registration number
	 */
    @Override
    public String validateRegistrationNumber(String module, Seniority seniority) {
		LOGGER.debug(" >>> validateRegistrationNumber START");

		// Creates the list object to validate the seniority.
		List<Object> objectList = new ArrayList<Object>();
		objectList.add(seniority);

		LOGGER.debug("		- Calling the rules validator");
		Map<String, Object> resultMap = businessRulesService.calculate(module, TM_REGISTRATION_LIST, objectList);

		if(resultMap != null && resultMap.containsKey("registrationNumber")){
			LOGGER.debug(" >>> validateRegistrationNumber END");
			return (String) resultMap.get("registrationNumber");
		} else {
			LOGGER.debug("		> Error: couldn't find the registration number in the result map");
			return null;
		}
	}

	/**
	 * Validates the exhibition priority according to some rules
	 * @param module the module to get the sections
	 * @param exhibitionPriority the exhibition priority object that we are interested
	 * @param rulesInformation extra rules information
	 * @return the list of possible errors
	 */
    @Override
    public ErrorList validateExhibitionPriorityClaim(String module, ExhibitionPriority exhibitionPriority, RulesInformation rulesInformation) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(" >>> validateExhibitionPriorityClaim START");
            if (exhibitionPriority!=null
                    && exhibitionPriority.getExhibition()!=null) {
                LOGGER.debug("		- Validating Exhibition Priority Form : " + exhibitionPriority.getExhibition().getName());
            }
        }

		//Variable declaration
		List<Object> objects = new ArrayList<Object>();
        ErrorList errors = new ErrorList();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);
        
        if (section != null) {
	        // Prepares the objects to insert in the session
	     	objects.add(section);
	     	objects.add(exhibitionPriority);
	     	objects.add(rulesInformation);
	
	     	// Starts the check
	     	errors = businessRulesService.validate(module, CLAIM_SET, objects);
	     	LOGGER.debug(" <<< validateExhibitionPriorityClaim END");
        }
        
		return errors;
	}

	/**
	 * Validates the transformation according to some rules
	 * @param module the module to get the sections
	 * @param transformationPriority the transformation priority object that we are interested
	 * @param rulesInformation extra rules information
	 * @return the list of possible errors
	 */
    @Override
    public ErrorList validateTransformation(String module, TransformationPriority transformationPriority, RulesInformation rulesInformation) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(" >>> validateTransformation START");
            if (transformationPriority!=null) {
                LOGGER.debug("		- Validating Transformation Priority Form : " + transformationPriority.getRegistrationNumber());
            }
        }

		//Variable declaration
		List<Object> objects = new ArrayList<Object>();
        ErrorList errors = new ErrorList();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);
        
        if (section != null) {
        	// Prepares the objects to insert in the session
	     	objects.add(section);
	     	objects.add(transformationPriority);
	
	     	// Starts the check
	     	errors = businessRulesService.validate(module, CLAIM_SET, objects);
			LOGGER.debug(" <<< validateTransformation END");
        }
        
		return errors;
	}

    @Override
    public ErrorList validateConversion(String module, ConversionPriority conversionPriority, RulesInformation rulesInformation) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(" >>> validateConversion START");
            if (conversionPriority != null) {
                LOGGER.debug("		- Validating Conversion Priority Form : " + conversionPriority.getConversionApplicationNumber());
            }
        }

		//Variable declaration
		List<Object> objects = new ArrayList<Object>();
        ErrorList errors = new ErrorList();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);
        
        if (section != null) {
        	// Prepares the objects to insert in the session
	     	objects.add(section);
	     	objects.add(conversionPriority);
	
	     	// Starts the check
	     	errors = businessRulesService.validate(module, CLAIM_SET, objects);
			LOGGER.debug(" <<< validateConversion END");
        }
        
		return errors;
	}
	/**
	 * Validates an other attachment document
	 * @param module the module to get the sections 
	 * @param document the document to be validated
	 * @param rulesInformation extra rules information
	 * @return the list of possible errors
	 */
    @Override
    public ErrorList validateOtherAttachment(String module, Document document, RulesInformation rulesInformation) {
		LOGGER.debug(" >>> validateOtherAttachment START");

		//Variable declaration
		List<Object> objects = new ArrayList<Object>();
        ErrorList errors = new ErrorList();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);
        
        if (section != null) {
	        // Prepares the objects to insert in the session
	     	objects.add(section);
	     	objects.add(document);
	
	     	// Starts the check
	     	errors = businessRulesService.validate(module, CLAIM_SET, objects);
			LOGGER.debug(" >>> validateOtherAttachment START");
        }
        
		return errors;
	}	
}
