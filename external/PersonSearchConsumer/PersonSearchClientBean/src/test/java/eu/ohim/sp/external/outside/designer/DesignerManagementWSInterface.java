package eu.ohim.sp.external.outside.designer;

import eu.ohim.sp.external.domain.common.Result;
import eu.ohim.sp.external.domain.person.Designer;
import eu.ohim.sp.external.ws.exception.DesignerFaultException;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface DesignerManagementWSInterface {

    /**
     * It searches for designers whose ID or name contains the text provided in input.
     * It is used to manage the form autocomplete and it returns a JSON text (for performance reasons).
     * The number of results returne is limited by the numberOfRows parameter.
     * 
     * @param text the text
     * @param numberOfRows maximum number of rows to be returned
     * @return JSON text representing the designers found
     */
    public String getDesignerAutocomplete(String module, String office, String text, int numberOfRows) throws DesignerFaultException;

    /**
     * Retrieves a list of Designers matching the search criteria provided as input and returns the
     * subset of fields specified for each matching Designer. The maximum number of results returned
     * is limited by the numberOfResults parameter.
     * 
     * @param designerId the designer Id
     * @param designerName the designer name
     * @param designerNationality the designer nationality
     * @param numberOfResults maximum number of results to be returned
     * @return a list of designers matching the search criteria
     */
    public List<Designer> searchDesigner(String module, String office, String designerId, String designerName, String designerNationality,
                                           int numberOfResults) throws DesignerFaultException;

    /**
     * Returns the full details of the Designer with the specified ID, if it exists.
     * 
     * @param office the office identifier
     * @param designerId the designer Id of the designer to find
     * @return the designer with the provided Id
     */
    public Designer getDesigner(String module, String office, String designerId) throws DesignerFaultException;

    /**
     * Looks for the Designer provided as input and returns a list of possible alternative Designers
     * or nothing if there is no match. The matching rules are Office-specific, the number of matches
     * to be returned is limited by the numberOfResults parameter.
     * 
     * @param designer the designer
     * @param numberOfResults maximum number of results to be returned
     * @return a list of designers mathing the given criteria
     */
    
     public List<Designer> matchDesigner(String module, String office, Designer designer, int numberOfResults) throws DesignerFaultException;
     

    /**
     * Creates the Designer in the Person Database so that it can be searched/imported in future filings.
     * Returns the Designer ID assigned to the newly created person. If the person already exists,
     * it returns their Designer ID.
     * 
     * @param user the user
     * @param office the office
     * @param designer the designer
     * @return the saved designer's ID
     */
     public Result saveDesigner(String module, String office, String user, Designer designer) throws DesignerFaultException;
     

}
