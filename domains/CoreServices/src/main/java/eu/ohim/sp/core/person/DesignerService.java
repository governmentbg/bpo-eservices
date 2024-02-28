/*
 *  PersonService:: DesignerService 06/09/13 12:59 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.person;

import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;

import java.util.List;

public interface DesignerService {
	/**
	 * It search designers whose ID or name contains the text provided in
	 * input. It is used to manage the form autocomplete and it returns a JSON
	 * text (for performance reasons). The number of results returne is limited
	 * by the numberOfRows parameter.
	 * 
	 * @param text
	 * @param numberOfRows
     * @return String search result in json format
	 */
	String getDesignerAutocomplete(String module, String office, String text, int numberOfRows);

	/**
	 * Retrieve a list of Designers matching the search criteria provided as
	 * input and return the subset of fields specified for each matching
	 * Designer. The maximum number of results returned is limited by the
	 * numberOfResults parameter.
	 * 
	 * @param designerId
	 * @param designerName
	 * @param designerNationality
	 * @param numberOfResults
	 * @return designer search result list
	 */
	List<Designer> searchDesigner(String module, String office,
                                  String designerId, String designerName,
                                  String designerNationality, int numberOfResults);

    /**
     * Return the full details of the Designer with the specified ID, if existing.
     *
     * @param designerId
     * @param office
     * @return designer corresponding with designer identifier parameter
     */
    Designer getDesigner(String module, String office, String designerId);

	/**
	 * Look for the Designer provided as input and returns a list of possible
	 * alternative Designers or nothing if there is no match. The matching
	 * rules are Office-specific, the number of matches to be returned is
	 * limited by the numberOfResults parameter.
	 * 
	 * @param designer
	 * @param numberOfResults
	 * @return designer list that matches with the parameter
	 */
	List<Designer> matchDesigner(String module, String office,
                                 Designer designer, int numberOfResults);

	/**
	 * Validate the Designer provided in input by checking all the business
	 * rules that apply. It returns a validation result and a list of errors
	 * that will specify which rule has not passed the validation test.
	 * 
	 * @param module
	 * @param designer
	 * @param rulesInformation
	 * @return error list with validation result 
	 */
	ErrorList validateDesigner(String module, Designer designer,
                                RulesInformation rulesInformation);
}
