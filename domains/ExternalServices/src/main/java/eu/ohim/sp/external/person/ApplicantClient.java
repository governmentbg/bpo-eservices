/*
 *  PersonAdapterServiceInterface:: ApplicantAdapterServiceInterface 06/09/13 12:59 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.external.person;

import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.core.domain.person.Applicant;

import java.util.List;

/**
 * The Interface ApplicantClientServiceInterface.
 */
public interface ApplicantClient {
	
    /**
     * It searches for applicants whose ID or name contains the text provided in input.
     * It is used to manage the form autocomplete and it returns a JSON text (for performance reasons).
     * The number of results returne is limited by the numberOfRows parameter.
     *
     * @param module the module that made the call
     * @param text the text
     * @param numberOfRows maximum number of rows to be returned
     * @return JSON text representing the applicants found
     */
    String getApplicantAutocomplete(String module, String office, String text, int numberOfRows);

    /**
     * Retrieves a list of Applicants matching the search criteria provided as input and returns the
     * subset of fields specified for each matching Applicant. The maximum number of results returned
     * is limited by the numberOfResults parameter.
     *
     * @param module the module that made the call
     * @param applicantId the applicant Id
     * @param applicantName the applicant name
     * @param applicantNationality the applicant nationality
     * @param numberOfResults maximum number of results to be returned
     * @return a list of applicants matching the search criteria
     */
    List<Applicant> searchApplicant(String module, String office, String applicantId, String applicantName, String applicantNationality, int numberOfResults);

    /**
     * Returns the full details of the Applicant with the specified ID, if it exists.
     *
     * @param module the module that made the call
     * @param office the office identifier
     * @param applicantId the applicant Id of the applicant to find
     * @return the applicant with the provided Id
     */
    Applicant getApplicant(String module, String office, String applicantId);

    /**
     * Looks for the Applicant provided as input and returns a list of possible alternative Applicants
     * or nothing if there is no match. The matching rules are Office-specific, the number of matches
     * to be returned is limited by the numberOfResults parameter.
     *
     * @param module the module that made the call
     * @param applicant the applicant
     * @param numberOfResults maximum number of results to be returned
     * @return a list of applicants mathing the given criteria
     */
    List<Applicant> matchApplicant(String module, String office, Applicant applicant, int numberOfResults);

    /**
     * Creates the Applicant in the Person Database so that it can be searched/imported in future filings.
     * Returns the Applicant ID assigned to the newly created person. If the person already exists,
     * it returns their Applicant ID.
     *
     * @param module the module that made the call
     * @param office the office
     * @param user the user
     * @param applicant the applicant
     * @return the saved applicant's ID
     */
    Result saveApplicant(String module, String office, String user, Applicant applicant);
}
