package eu.ohim.sp.external.outside.applicant;

import eu.ohim.sp.external.domain.common.Result;
import eu.ohim.sp.external.domain.person.Applicant;
import eu.ohim.sp.external.ws.exception.ApplicantFaultException;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface ApplicantManagementWSInterface {

    /**
     * It searches for applicants whose ID or name contains the text provided in input.
     * It is used to manage the form autocomplete and it returns a JSON text (for performance reasons).
     * The number of results returne is limited by the numberOfRows parameter.
     * 
     * @param text the text
     * @param numberOfRows maximum number of rows to be returned
     * @return JSON text representing the applicants found
     */
    public String getApplicantAutocomplete(String module, String office, String text, int numberOfRows) throws ApplicantFaultException;

    /**
     * Retrieves a list of Applicants matching the search criteria provided as input and returns the
     * subset of fields specified for each matching Applicant. The maximum number of results returned
     * is limited by the numberOfResults parameter.
     * 
     * @param applicantId the applicant Id
     * @param applicantName the applicant name
     * @param applicantNationality the applicant nationality
     * @param numberOfResults maximum number of results to be returned
     * @return a list of applicants matching the search criteria
     */
    public List<Applicant> searchApplicant(String module, String office, String applicantId, String applicantName, String applicantNationality,
                                           int numberOfResults) throws ApplicantFaultException;

    /**
     * Returns the full details of the Applicant with the specified ID, if it exists.
     * 
     * @param office the office identifier
     * @param applicantId the applicant Id of the applicant to find
     * @return the applicant with the provided Id
     */
    public Applicant getApplicant(String module, String office, String applicantId) throws ApplicantFaultException;

    /**
     * Looks for the Applicant provided as input and returns a list of possible alternative Applicants
     * or nothing if there is no match. The matching rules are Office-specific, the number of matches
     * to be returned is limited by the numberOfResults parameter.
     * 
     * @param applicant the applicant
     * @param numberOfResults maximum number of results to be returned
     * @return a list of applicants mathing the given criteria
     */
    
     public List<Applicant> matchApplicant(String module, String office, Applicant applicant, int numberOfResults) throws ApplicantFaultException;
     

    /**
     * Creates the Applicant in the Person Database so that it can be searched/imported in future filings.
     * Returns the Applicant ID assigned to the newly created person. If the person already exists,
     * it returns their Applicant ID.
     * 
     * @param user the user
     * @param office the office
     * @param applicant the applicant
     * @return the saved applicant's ID
     */
     public Result saveApplicant(String module, String office, String user, Applicant applicant) throws ApplicantFaultException;
     

}
