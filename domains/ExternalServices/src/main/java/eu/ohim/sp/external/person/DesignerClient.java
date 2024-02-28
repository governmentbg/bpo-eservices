/*
 *  PersonAdapterServiceInterface:: DesignerAdapterServiceInterface 06/09/13 19:36 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.external.person;

import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.core.domain.design.Designer;

import java.util.List;

/**
 * The Interface DesignerClientServiceInterface.
 */
public interface DesignerClient {
	
    /**
     * It searches for designers whose ID or name contains the text provided in input.
     * It is used to manage the form autocomplete and it returns a JSON text (for performance reasons).
     * The number of results returne is limited by the numberOfRows parameter.
     *
     * @param module the module that made the call
     * @param text the text
     * @param numberOfRows maximum number of rows to be returned
     * @return JSON text representing the designers found
     */
    String getDesignerAutocomplete(String module, String office, String text, int numberOfRows);

    /**
     * Retrieves a list of Designers matching the search criteria provided as input and returns the
     * subset of fields specified for each matching Designer. The maximum number of results returned
     * is limited by the numberOfResults parameter.
     *
     * @param module the module that made the call
     * @param designerId the designer Id
     * @param designerName the designer name
     * @param designerNationality the designer nationality
     * @param numberOfResults maximum number of results to be returned
     * @return a list of designers matching the search criteria
     */
    List<Designer> searchDesigner(String module, String office, String designerId, String designerName, String designerNationality, int numberOfResults);

    /**
     * Returns the full details of the Designer with the specified ID, if it exists.
     *
     * @param module the module that made the call
     * @param office the office identifier
     * @param designerId the designer Id of the designer to find
     * @return the designer with the provided Id
     */
    Designer getDesigner(String module, String office, String designerId);

    /**
     * Looks for the Designer provided as input and returns a list of possible alternative Designers
     * or nothing if there is no match. The matching rules are Office-specific, the number of matches
     * to be returned is limited by the numberOfResults parameter.
     *
     * @param module the module that made the call
     * @param designer the designer
     * @param numberOfResults maximum number of results to be returned
     * @return a list of designers mathing the given criteria
     */
    List<Designer> matchDesigner(String module, String office, Designer designer, int numberOfResults);

    /**
     * Creates the Designer in the Person Database so that it can be searched/imported in future filings.
     * Returns the Designer ID assigned to the newly created person. If the person already exists,
     * it returns their Designer ID.
     *
     * @param module the module that made the call
     * @param office the office
     * @param user the user
     * @param designer the designer
     * @return the saved designer's ID
     */
    Result saveDesigner(String module, String office, String user, Designer designer);
}
