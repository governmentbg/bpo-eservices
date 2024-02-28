package eu.ohim.sp.external.outside.designer;

import eu.ohim.sp.external.domain.common.Fault;
import eu.ohim.sp.external.domain.common.Result;
import eu.ohim.sp.external.domain.person.Designer;
import eu.ohim.sp.external.domain.person.Gender;
import eu.ohim.sp.external.services.designer.DesignerFault;
import eu.ohim.sp.external.ws.exception.DesignerFaultException;
import org.apache.commons.lang.StringUtils;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "DesignerService", targetNamespace = "http://ohim.eu/sp/services/designer/v3", portName = "DesignerServicePort", wsdlLocation = "wsdl/DesignerService.wsdl")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class DesignerManagementWS implements DesignerManagementWSInterface {

    /**
     * It searches for designers whose ID or name contains the text provided in input.
     * It is used to manage the form autocomplete and it returns a JSON text (for performance reasons).
     * The number of results returne is limited by the numberOfRows parameter.
     *
     * @param text         the text
     * @param numberOfRows maximum number of rows to be returned
     * @return JSON text representing the designers found
     */
    @WebMethod
    public String getDesignerAutocomplete(@WebParam(name = "module", targetNamespace = "http://ohim.eu/sp/domain/person/v3")String module,
                                           @WebParam(name = "office", targetNamespace = "http://ohim.eu/sp/domain/common/v3")String office,
                                           @WebParam(name = "text", targetNamespace = "http://ohim.eu/sp/domain/common/v3")String text,
                                           @WebParam(name = "numberOfRows", targetNamespace = "http://ohim.eu/sp/domain/common/v3")int numberOfRows) throws DesignerFaultException {
        if (StringUtils.isBlank(module)) {
            try {
                StringUtils.defaultIfBlank(module, null).equals("error");
            } catch (NullPointerException e) {
                DesignerFault designerFault = new DesignerFault();
                designerFault.setReturnedObject(new Fault());
                designerFault.getReturnedObject().setCode("error.code");
                designerFault.getReturnedObject().setMessage("system error");
                throw new DesignerFaultException("system error", designerFault, e);
            }
        }
        return "testDesignerAutocomplete : " + module + office;
    }

    /**
     * Retrieves a list of Designers matching the search criteria provided as input and returns the
     * subset of fields specified for each matching Designer. The maximum number of results returned
     * is limited by the numberOfResults parameter.
     *
     * @param designerId          the designer Id
     * @param designerName        the designer name
     * @param designerCountry the designer nationality
     * @param numberOfResults      maximum number of results to be returned
     * @return a list of designers matching the search criteria
     */
    @WebMethod
    public List<Designer> searchDesigner(@WebParam(name = "module", targetNamespace = "")String module,
                                           @WebParam(name = "office", targetNamespace = "")String office,
                                           @WebParam(name = "designerId", targetNamespace = "")String designerId,
                                           @WebParam(name = "designerName", targetNamespace = "")String designerName,
                                           @WebParam(name = "designerCountry", targetNamespace = "")String designerCountry,
                                           @WebParam(name = "numberOfResults", targetNamespace = "")int numberOfResults) throws DesignerFaultException {
        List<Designer> designers = new ArrayList<Designer>();
        for (int i = 0 ; i<numberOfResults; i++) {
            Designer designer1 = new Designer();
            designer1.setGender(Gender.MALE);
            designer1.setNationality(designerCountry);
            designer1.setPersonNumber(designerId);
            designers.add(designer1);
        }

        return designers;
    }

    /**
     * Returns the full details of the Designer with the specified ID, if it exists.
     *
     * @param office      the office identifier
     * @param designerId the designer Id of the designer to find
     * @return the designer with the provided Id
     */
    @WebMethod
    public Designer getDesigner(@WebParam(name = "module", targetNamespace = "")String module,
                                  @WebParam(name = "office", targetNamespace = "")String office,
                                  @WebParam(name = "designerId", targetNamespace = "")String designerId) throws DesignerFaultException {
        Designer designer = new Designer();
        if (StringUtils.isNotBlank(designerId)) {
            designer.setPersonNumber(designerId);
            designer.setGender(Gender.MALE);
        } else {
            DesignerFault designerFault = new DesignerFault();
            designerFault.setReturnedObject(new Fault());
            designerFault.getReturnedObject().setCode("error.code");
            designerFault.getReturnedObject().setMessage("Empty designerId");
            throw new DesignerFaultException("Empty designerId", designerFault);
        }


        return designer;
    }

    /**
     * Looks for the Designer provided as input and returns a list of possible alternative Designers
     * or nothing if there is no match. The matching rules are Office-specific, the number of matches
     * to be returned is limited by the numberOfResults parameter.
     *
     * @param designer       the designer
     * @param numberOfResults maximum number of results to be returned
     * @return a list of designers mathing the given criteria
     */
    @WebMethod
    public List<Designer> matchDesigner(@WebParam(name = "module", targetNamespace = "")String module,
                                          @WebParam(name = "office", targetNamespace = "")String office,
                                          @WebParam(name = "designer", targetNamespace = "")Designer designer,
                                          @WebParam(name = "numberOfResults", targetNamespace = "")int numberOfResults) throws DesignerFaultException {
        List<Designer> designers = new ArrayList<Designer>();
        for (int i = 0 ; i<numberOfResults; i++) {
            Designer designer1 = new Designer();
            designer1.setGender(Gender.MALE);
            designer1.setNationality(designer.getNationality());
            designer1.setDateOfBirth(designer.getDateOfBirth());
            designers.add(designer1);
        }

        return designers;
    }


    /**
     * Creates the Designer in the Person Database so that it can be searched/imported in future filings.
     * Returns the Designer ID assigned to the newly created person. If the person already exists,
     * it returns their Designer ID.
     *
     * @param user      the user
     * @param office    the office
     * @param designer the designer
     * @return the saved designer's ID
     */
    @WebMethod
    public Result saveDesigner(String module, String office, String user, Designer designer) throws DesignerFaultException {
        return new Result();
    }


}
