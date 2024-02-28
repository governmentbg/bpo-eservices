/*
 *  PersonService:: ApplicantService 01/10/13 17:01 KARALCH $
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
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Assignee;
import eu.ohim.sp.core.domain.person.Holder;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.util.SectionUtil;
import eu.ohim.sp.external.person.ApplicantClient;
import eu.ohim.sp.external.person.PersonSearchClientInside;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Core Applicant service that offers useful methods for the Applicant section
 * 
 * @author ionitdi
 * 
 */
@Stateless
public class ApplicantServiceBean implements ApplicantServiceRemote, ApplicantServiceLocal {

	private Logger logger = Logger.getLogger(ApplicantService.class);

	@EJB(lookup="java:global/businessRulesLocal")
	private RulesService businessRulesService;

    @Inject @PersonSearchClientInside
	private ApplicantClient adapter;
	
	/**
	 * The constant APPLICANTLIST
	 */
    private static final String APPLICANT_SET = "applicant_set";
    private static final String ASSIGNEE_SET = "assignee_set";
    private static final String HOLDER_SET = "holder_set";


    /**
	 * {@inheritDoc}
	 */
	@Override
	public String getApplicantAutocomplete(String module, String office, String text, int numberOfRows) {
		// this method should not call the external service if the input is invalid
		return StringUtils.isBlank(text) || numberOfRows <= 0 ?  null :
                adapter.getApplicantAutocomplete(module, office, text, numberOfRows);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Applicant> searchApplicant(String module, String office, String applicantId, String applicantName, String applicantNationality,
			int numberOfResults) {
		// this method should not call the external service if the input is invalid
		return numberOfResults <= 0 ? null : adapter.searchApplicant(module, office, applicantId, applicantName, applicantNationality,
				numberOfResults);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Applicant getApplicant(String module, String office, String applicantId) {
		// this method should not call the external service if the input is
		// invalid
		return applicantId == null ? null : adapter.getApplicant(module, office, applicantId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Applicant> matchApplicant(String module, String office, Applicant applicant, int numberOfResults) {
		// if the given applicant to match is null, return null
		// this method should not call the external service if the input is
		// invalid, otherwise call external service that does the matching
		return applicant == null || numberOfResults <= 0 ? null : adapter.matchApplicant(module, office, applicant, numberOfResults);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ErrorList validateApplicant(String module, Applicant applicant, RulesInformation rulesInformation) {
		if (applicant != null) {
			logger.debug(">>> Validate Applicant: " + applicant.getKind() + ", " 
					+ applicant.getIncorporationCountry());
		}
		

		//Variable declaration
		List<Object> objects = new ArrayList<Object>();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);
        
        // Prepares the objects to insert in the session
     	objects.add(section);
     	objects.add(applicant);
        objects.add(rulesInformation);     	

     	// Starts the check
        ErrorList errors = businessRulesService.validate(module, APPLICANT_SET, objects);
        
     	if (logger.isDebugEnabled()) {
			logger.debug("<<< Validate Applicant END");
		}
        
        return errors;
		
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public ErrorList validateAssignee(String module, Assignee assignee, RulesInformation rulesInformation) {
        if (assignee != null) {
            logger.debug(">>> Validate Assignee: " + assignee.getKind() + ", "
                    + assignee.getIncorporationCountry());
        }

        //Variable declaration
        List<Object> objects = new ArrayList<Object>();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        // Prepares the objects to insert in the session
        objects.add(section);
        objects.add(assignee);
		objects.add(rulesInformation);

		// Starts the check
        ErrorList errors = businessRulesService.validate(module, ASSIGNEE_SET, objects);

        if (logger.isDebugEnabled()) {
            logger.debug("<<< Validate Assignee END");
        }

        return errors;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ErrorList validateHolder(String module, Holder holder, RulesInformation rulesInformation) {
        if (holder != null) {
            logger.debug(">>> Validate Holder: " + holder.getKind() + ", "
                    + holder.getIncorporationCountry());
        }

        //Variable declaration
        List<Object> objects = new ArrayList<Object>();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        // Prepares the objects to insert in the session
        objects.add(section);
        objects.add(holder);
        objects.add(rulesInformation);

        // Starts the check
        ErrorList errors = businessRulesService.validate(module, HOLDER_SET, objects);

        if (logger.isDebugEnabled()) {
            logger.debug("<<< Validate Holder END");
        }

        return errors;
    }

    @Override
    public Applicant saveApplicant(String module, String office, String user, Applicant applicant) {

        if (applicant != null
                && applicant.getPersonNumber()!=null) {
            Result result = adapter.saveApplicant(module, office, user, applicant);

            if (result.getErrorCode().equals(Result.SUCCESS)) {
                applicant.setPersonNumber(result.getResult());
            }
        }
        return applicant;
    }

}
