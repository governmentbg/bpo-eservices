package eu.ohim.sp.external.outside.applicant;

import eu.ohim.sp.external.domain.common.Fault;
import eu.ohim.sp.external.domain.common.Result;
import eu.ohim.sp.external.domain.person.Applicant;
import eu.ohim.sp.external.domain.person.Gender;
import eu.ohim.sp.external.services.applicant.ApplicantFault;
import eu.ohim.sp.external.ws.exception.ApplicantFaultException;
import org.apache.commons.lang.StringUtils;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "ApplicantService", targetNamespace = "http://ohim.eu/sp/services/applicant/v3", portName = "ApplicantServicePort", wsdlLocation = "wsdl/ApplicantService.wsdl")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class ApplicantManagementWS implements ApplicantManagementWSInterface {

    /**
     * It searches for applicants whose ID or name contains the text provided in input.
     * It is used to manage the form autocomplete and it returns a JSON text (for performance reasons).
     * The number of results returne is limited by the numberOfRows parameter.
     *
     * @param text         the text
     * @param numberOfRows maximum number of rows to be returned
     * @return JSON text representing the applicants found
     */
    @WebMethod
    public String getApplicantAutocomplete(@WebParam(name = "module", targetNamespace = "http://ohim.eu/sp/domain/person/v3")String module,
                                           @WebParam(name = "office", targetNamespace = "http://ohim.eu/sp/domain/common/v3")String office,
                                           @WebParam(name = "text", targetNamespace = "http://ohim.eu/sp/domain/common/v3")String text,
                                           @WebParam(name = "numberOfRows", targetNamespace = "http://ohim.eu/sp/domain/common/v3")int numberOfRows) throws ApplicantFaultException {
        if (StringUtils.isBlank(module)) {
            try {
                StringUtils.defaultIfBlank(module, null).equals("error");
            } catch (NullPointerException e) {
                ApplicantFault applicantFault = new ApplicantFault();
                applicantFault.setFault(new Fault());
                applicantFault.getFault().setCode("error.code");
                applicantFault.getFault().setMessage("system error");
                throw new ApplicantFaultException("system error", applicantFault, e);
            }
        }return "testApplicantAutocomplete : " + module + office;
    }

    /**
     * Retrieves a list of Applicants matching the search criteria provided as input and returns the
     * subset of fields specified for each matching Applicant. The maximum number of results returned
     * is limited by the numberOfResults parameter.
     *
     * @param applicantId          the applicant Id
     * @param applicantName        the applicant name
     * @param applicantCountry the applicant nationality
     * @param numberOfResults      maximum number of results to be returned
     * @return a list of applicants matching the search criteria
     */
    @WebMethod
    public List<Applicant> searchApplicant(@WebParam(name = "module", targetNamespace = "")String module,
                                           @WebParam(name = "office", targetNamespace = "")String office,
                                           @WebParam(name = "applicantId", targetNamespace = "")String applicantId,
                                           @WebParam(name = "applicantName", targetNamespace = "")String applicantName,
                                           @WebParam(name = "applicantCountry", targetNamespace = "")String applicantCountry,
                                           @WebParam(name = "numberOfResults", targetNamespace = "")int numberOfResults) throws ApplicantFaultException {
        List<Applicant> applicants = new ArrayList<Applicant>();
        for (int i = 0 ; i<numberOfResults; i++) {
            Applicant applicant1 = new Applicant();
            applicant1.setGender(Gender.MALE);
            applicant1.setNationality(applicantCountry);
            applicant1.setPersonNumber(applicantId);
            applicants.add(applicant1);
        }

        return applicants;
    }

    /**
     * Returns the full details of the Applicant with the specified ID, if it exists.
     *
     * @param office      the office identifier
     * @param applicantId the applicant Id of the applicant to find
     * @return the applicant with the provided Id
     */
    @WebMethod
    public Applicant getApplicant(@WebParam(name = "module", targetNamespace = "")String module,
                                  @WebParam(name = "office", targetNamespace = "")String office,
                                  @WebParam(name = "applicantId", targetNamespace = "")String applicantId) throws ApplicantFaultException {
        Applicant applicant = new Applicant();
        if (StringUtils.isNotBlank(applicantId)) {
            applicant.setPersonNumber(applicantId);
            applicant.setGender(Gender.MALE);
        } else {
            ApplicantFault applicantFault = new ApplicantFault();
            applicantFault.setFault(new Fault());
            applicantFault.getFault().setCode("error.code");
            applicantFault.getFault().setMessage("Empty applicantId");
            throw new ApplicantFaultException("Empty applicantId", applicantFault);
        }


        return applicant;
    }

    /**
     * Looks for the Applicant provided as input and returns a list of possible alternative Applicants
     * or nothing if there is no match. The matching rules are Office-specific, the number of matches
     * to be returned is limited by the numberOfResults parameter.
     *
     * @param applicant       the applicant
     * @param numberOfResults maximum number of results to be returned
     * @return a list of applicants mathing the given criteria
     */
    @WebMethod
    public List<Applicant> matchApplicant(@WebParam(name = "module", targetNamespace = "")String module,
                                          @WebParam(name = "office", targetNamespace = "")String office,
                                          @WebParam(name = "applicant", targetNamespace = "")Applicant applicant,
                                          @WebParam(name = "numberOfResults", targetNamespace = "")int numberOfResults) throws ApplicantFaultException {
        List<Applicant> applicants = new ArrayList<Applicant>();
        for (int i = 0 ; i<numberOfResults; i++) {
            Applicant applicant1 = new Applicant();
            applicant1.setGender(Gender.MALE);
            applicant1.setNationality(applicant.getNationality());
            applicant1.setDateOfBirth(applicant.getDateOfBirth());
            applicants.add(applicant1);
        }

        return applicants;
    }


    /**
     * Creates the Applicant in the Person Database so that it can be searched/imported in future filings.
     * Returns the Applicant ID assigned to the newly created person. If the person already exists,
     * it returns their Applicant ID.
     *
     * @param user      the user
     * @param office    the office
     * @param applicant the applicant
     * @return the saved applicant's ID
     */
    @WebMethod
    public Result saveApplicant(String module, String office, String user, Applicant applicant) throws ApplicantFaultException {
        return new Result();
    }


}
