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

import java.util.List;

import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;

public interface RepresentativeService {
	/**
	 * It search Representatives whose ID or name contains the text provided in
	 * input. It is used to manage the form autocomplete and it returns a JSON
	 * text (for performance reasons). The number of results returne is limited
	 * by the numberOfRows parameter.
	 *
     * @param module the module that made the call
     * @param text
	 * @param numberOfRows
	 * @return String search result in json format
	 */
	String getRepresentativeAutocomplete(String module, String office, String text, int numberOfRows);

	/**
	 * Retrieve a list of Representatives matching the search criteria provided
	 * as input and return the subset of fields specified for each matching
	 * Representative. The maximum number of results returned is limited by the
	 * numberOfResults parameter.
	 *
     * @param module the module that made the call
	 * @param representativeId
	 * @param representativeName
	 * @param representativeNationality
	 * @param numberOfResults
	 * @return representative search result list
	 */
	List<Representative> searchRepresentative(String module, String office, String representativeId,
			String representativeName, String representativeNationality,
			int numberOfResults);

	/**
	 * Returns the full details of the Representative with the specified ID, if
	 * it exists
	 *
     * @param module the module that made the call
	 * @param representativeId
	 *            the representative Id of the representative to find
	 * @param office
	 *            the office identifier
	 * @return the representative with the provided Id
	 */
	Representative getRepresentative(String module, String office, String representativeId);

	/**
	 * Look for the Representative provided as input and returns a list of
	 * possible alternative Representatives or nothing if there is no match. The
	 * matching rules are Office-specific, the number of matches to be returned
	 * is limited by the numberOfResults parameter.
	 *
     * @param module the module that made the call
	 * @param representative
	 * @param numberOfResults
	 * @return representative list that matches with the parameter
	 */
	List<Representative> matchRepresentative(String module, String office, Representative representative,
			int numberOfResults);

	/**
	 * Validate the Representative provided in input by checking all the
	 * business rules that apply. It returns a validation result and a list of
	 * errors that will specify which rule has not passed the validation test.
	 * 
	 * @param module
	 * @param representative
	 * @param rulesInformation
	 * @return error list with validation result
	 */
	ErrorList validateRepresentative(String module, Representative representative,
			RulesInformation rulesInformation);

    /**
     * Saves a representative and set the representative number
     * @param module
     * @param office
     * @param user
     * @param representative
     * @return if it's successful should return the personNumber as set
     */
    Representative saveRepresentative(String module, String office, String user, Representative representative);

	Representative getIntlPRepresentative(String representativeId);

	String getIntlPRepresentativeList();

}
