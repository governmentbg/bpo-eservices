/*
 *  DesignService:: DesignService 18/11/13 18:59 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.register;

import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.design.DesignDivisionalApplicationDetails;
import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.util.SectionUtil;
import eu.ohim.sp.external.register.DesignSearchClientInside;
import org.apache.log4j.Logger;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DesignSearchServiceBean implements DesignSearchServiceLocal, DesignSearchServiceRemote {

	private Logger logger = Logger.getLogger(DesignSearchServiceBean.class);

	@EJB(lookup="java:global/businessRulesLocal")
	private RulesService businessRulesService;

    @Inject @DesignSearchClientInside
    private DesignSearchService designServiceProvider;

	/**
	 * The Constant DESIGN_SET.
	 */
	private static final String DESIGN_SET = "design_set";
	private static final String CORRESPONDENCE_ADDRESS_SET = "correspondenceAddress_set";
    private static final String DIVISIONAL_APPLICATION_SET = "divisional_application_set";


    @Override
    public String getDesignAutocomplete(String office, String text, Integer numberOfResults) {
        return designServiceProvider.getDesignAutocomplete(office, text, numberOfResults);
    }

    @Override
    public Design getDesign(String office, String designId) {
        return designServiceProvider.getDesign(office, designId);
    }

    @Override
    public DesignApplication getDesignApplication(String office, String designId, String applicationId, Boolean extraImport) {
        return  designServiceProvider.getDesignApplication(office, designId, applicationId, extraImport);
    }

    @Override
    public ErrorList validateDesign(String module, Design design, RulesInformation rulesInformation) {
        if (design != null) {
            logger.debug(">>> Validate Design : " + design.getDescription());
        }

        // Variable declaration
        List<Object> objects = new ArrayList<Object>();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        // Prepares the objects to insert in the session
        objects.add(section);
        objects.add(design);
        objects.add(rulesInformation);
        
        // Starts the check
        ErrorList errors = businessRulesService.validate(module, DESIGN_SET, objects);

        if (logger.isDebugEnabled()) {
            logger.debug("<<< Validate Design END");
        }

        return errors;
    }

    @Override
    public ErrorList validateDesignApplication(String module, DesignApplication designApplication, RulesInformation rulesInformation) {
        if (designApplication != null) {
            logger.debug(">>> Validate Design Application: " + designApplication.getApplicationFilingNumber() + ", module: " + module);
        }

        // Variable declaration
        List<Object> objects = new ArrayList<Object>();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        // Prepares the objects to insert in the session
        objects.add(section);
        objects.add(designApplication);
        objects.add(rulesInformation);

        // Starts the check
        ErrorList errors = businessRulesService.validate(module, DESIGN_SET, objects);

        if (logger.isDebugEnabled()) {
            logger.debug("<<< Validate Design Application END");
        }

        return errors;
    }

    @Override
    public ErrorList validateDesignView(String module, DesignView designView, RulesInformation rulesInformation) {
        if (designView != null) {
            logger.debug(">>> Validate Design View Product : " + designView.getDescription());
        }

        // Variable declaration
        List<Object> objects = new ArrayList<Object>();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        // Prepares the objects to insert in the session
        objects.add(section);
        objects.add(designView);
        objects.add(rulesInformation);
        
        // Starts the check
        ErrorList errors = businessRulesService.validate(module, DESIGN_SET, objects);

        if (logger.isDebugEnabled()) {
            logger.debug("<<< Validate Design View END");
        }

        return errors;
    }

    @Override
    public ErrorList validateDivisionalApplication(String module, DesignDivisionalApplicationDetails dsDivisionalApplication, RulesInformation rulesInformation) {
        if (dsDivisionalApplication != null) {
            logger.debug(">>> Validate Divisional Application : " + dsDivisionalApplication.getInitialApplicationNumber());
        }

        // Variable declaration
        List<Object> objects = new ArrayList<Object>();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        // Prepares the objects to insert in the session
        objects.add(section);
        objects.add(dsDivisionalApplication);

        // Starts the check
        ErrorList errors = businessRulesService.validate(module, DIVISIONAL_APPLICATION_SET, objects);

        if (logger.isDebugEnabled()) {
            logger.debug("<<< Validate Divisional Application END");
        }

        return errors;
    }
    
   @Override
    public ErrorList validateApplicationCA(String module, ContactDetails contactDetails, RulesInformation rulesInformation) {
        if (contactDetails != null) {
            logger.debug(">>> Validate ContactDetails : " + contactDetails);
        }

        // Variable declaration
        List<Object> objects = new ArrayList<Object>();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        // Prepares the objects to insert in the session
        objects.add(section);
        objects.add(contactDetails);
        objects.add(rulesInformation);
        
        // Starts the check
        ErrorList errors = businessRulesService.validate(module, CORRESPONDENCE_ADDRESS_SET, objects);

        if (logger.isDebugEnabled()) {
            logger.debug("<<< Validate ContactDetails END");
        }
        return errors;
    }
}
