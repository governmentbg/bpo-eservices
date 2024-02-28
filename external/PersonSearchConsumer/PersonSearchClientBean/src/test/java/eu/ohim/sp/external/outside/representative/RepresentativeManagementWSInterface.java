package eu.ohim.sp.external.outside.representative;

import eu.ohim.sp.external.domain.common.Result;
import eu.ohim.sp.external.domain.person.Representative;
import eu.ohim.sp.external.ws.exception.RepresentativeFaultException;

import java.util.List;

public interface RepresentativeManagementWSInterface {


    /**
     * It searches for representatives whose ID or name contains the text provided in input.
     * It is used to manage the form autocomplete and it returns a JSON text (for performance reasons).
     * The number of results returne is limited by the numberOfRows parameter.
     *
     * @param module the module identifier
     * @param office the office identifier
     * @param text the text
     * @param numberOfRows maximum number of rows to be returned
     * @return JSON text representing the representatives found
     */
    public String getRepresentativeAutocomplete(String module, String office, String text, int numberOfRows) throws RepresentativeFaultException;
    /**
     * Retrieves a list of Representatives matching the search criteria provided as input and returns the
     * subset of fields specified for each matching Representative. The maximum number of results returned
     * is limited by the numberOfResults parameter.
     *
     * @param module the module identifier
     * @param office the office identifier
     * @param representativeId the representative Id
     * @param representativeName the representative name
     * @param representativeNationality the representative nationality
     * @param numberOfResults maximum number of results to be returned
     * @return a list of representatives matching the search criteria
     */
    public List<Representative> searchRepresentative(String module, String office, String representativeId, String representativeName,
                                                     String representativeNationality, int numberOfResults) throws RepresentativeFaultException;
    /**
     * Returns the full details of the Representative with the specified ID, if it exists.
     *
     * @param module the module identifier
     * @param office the office identifier
     * @param representativeId the representative Id of the representative to find
     * @return the representative with the provided Id
     */
    public Representative getRepresentative(String module, String office, String representativeId) throws RepresentativeFaultException;

    /**
     * Looks for the Representative provided as input and returns a list of possible alternative Representatives
     * or nothing if there is no match. The matching rules are Office-specific, the number of matches
     * to be returned is limited by the numberOfResults parameter.
     *
     * @param module the module identifier
     * @param office the office identifier
     * @param representative the representative
     * @param numberOfResults maximum number of results to be returned
     * @return a list of representatives mathing the given criteria
     */
    public List<Representative> matchRepresentative(String module, String office, Representative representative, int numberOfResults)
            throws RepresentativeFaultException;

    /**
     * Creates the Representative in the Person Database so that it can be searched/imported in future filings.
     * Returns the Representative ID assigned to the newly created person. If the person already exists,
     * it returns their Representative ID.
     *
     * @param module the module identifier
     * @param office the office identifier
     * @param user the user
     * @param representative the representative
     * @return the saved representative's ID
     */
    public Result saveRepresentative(String module, String office, String user, Representative representative) throws RepresentativeFaultException;

}
