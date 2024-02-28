/*
 *  PersonService:: ApplicantService 15/10/13 16:15 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.person;

import java.util.List;

import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Assignee;
import eu.ohim.sp.core.domain.person.Holder;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;

public interface ApplicantService {
	/**
	 * It search applicants whose ID or name contains the text provided in
	 * input. It is used to manage the form autocomplete and it returns a JSON
	 * text (for performance reasons). The number of results returne is limited
	 * by the numberOfRows parameter.
	 *
     * @param module the module that made the call
	 * @param text
	 * @param numberOfRows
     * @return String search result in json format
	 */
	String getApplicantAutocomplete(String module, String office, String text, int numberOfRows);

	/**
	 * Retrieve a list of Applicants matching the search criteria provided as
	 * input and return the subset of fields specified for each matching
	 * Applicant. The maximum number of results returned is limited by the
	 * numberOfResults parameter.
	 *
     * @param module the module that made the call
	 * @param applicantId
	 * @param applicantName
	 * @param applicantNationality
	 * @param numberOfResults
	 * @return applicant search result list 
	 */
	List<Applicant> searchApplicant(String module, String office, String applicantId, String applicantName,
			String applicantNationality, int numberOfResults);

    /**
     * Return the full details of the Applicant with the specified ID, if existing.
     *
     * @param module the module that made the call
     * @param applicantId
     * @param office
     * @return applicant corresponding with applicant identifier parameter
     */
    Applicant getApplicant(String module, String office, String applicantId);

	/**
	 * Look for the Applicant provided as input and returns a list of possible
	 * alternative Applicants or nothing if there is no match. The matching
	 * rules are Office-specific, the number of matches to be returned is
	 * limited by the numberOfResults parameter.
	 *
     * @param module the module that made the call
	 * @param applicant
	 * @param numberOfResults
	 * @return applicant list that matches with the parameter
	 */
	List<Applicant> matchApplicant(String module, String office, Applicant applicant, int numberOfResults);

	/**
	 * Validate the Applicant provided in input by checking all the business
	 * rules that apply. It returns a validation result and a list of errors
	 * that will specify which rule has not passed the validation test.
	 * 
	 * @param module
	 * @param applicant
	 * @param rulesInformation
	 * @return error list with validation result 
	 */
	ErrorList validateApplicant(String module, Applicant applicant, 
			RulesInformation rulesInformation);

    /**
     * Validate the Assignee provided in input by checking all the business
     * rules that apply. It returns a validation result and a list of errors
     * that will specify which rule has not passed the validation test.
     *
     * @param module
     * @param assignee
     * @param rulesInformation
     * @return error list with validation result
     */
    ErrorList validateAssignee(String module, Assignee assignee,
                                      RulesInformation rulesInformation);


    /**
     * Validate the Holder provided in input by checking all the business
     * rules that apply. It returns a validation result and a list of errors
     * that will specify which rule has not passed the validation test.
     *
     * @param module
     * @param holder
     * @param rulesInformation
     * @return error list with validation result
     */
    ErrorList validateHolder(String module, Holder holder,
                                    RulesInformation rulesInformation);

    /**
     * Saves an applicant and returns the applicant number
     * @param module the module
     * @param office
     * @param user
     * @param applicant
     * @return if it's successful should return the personNumber as set
     */
    Applicant saveApplicant(String module, String office, String user, Applicant applicant);
}
