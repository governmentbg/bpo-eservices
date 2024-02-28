package eu.ohim.sp.external.outside.representative;

import eu.ohim.sp.external.domain.common.Fault;
import eu.ohim.sp.external.domain.common.Result;
import eu.ohim.sp.external.domain.person.Gender;
import eu.ohim.sp.external.domain.person.Representative;
import eu.ohim.sp.external.services.representative.RepresentativeFault;
import eu.ohim.sp.external.ws.exception.RepresentativeFaultException;
import org.apache.commons.lang.StringUtils;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 06/09/13
 * Time: 16:05
 * To change this template use File | Settings | File Templates.
 */
@WebService(serviceName = "RepresentativeService", targetNamespace = "http://ohim.eu/sp/services/representative/v3", portName = "RepresentativeServicePort", wsdlLocation = "wsdl/RepresentativeService.wsdl")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class RepresentativeManagementWS implements  RepresentativeManagementWSInterface {

    @WebMethod
    public String getRepresentativeAutocomplete(@WebParam(name = "module", targetNamespace = "http://ohim.eu/sp/domain/person/v3")String module,
                                                @WebParam(name = "office", targetNamespace = "http://ohim.eu/sp/domain/common/v3")String office,
                                                @WebParam(name = "text", targetNamespace = "http://ohim.eu/sp/domain/common/v3")String text,
                                                @WebParam(name = "numberOfRows", targetNamespace = "http://ohim.eu/sp/domain/common/v3")int numberOfRows) throws RepresentativeFaultException {
        if (StringUtils.isBlank(module)) {
            try {
                StringUtils.defaultIfBlank(module, null).equals("error");
            } catch (NullPointerException e) {
                RepresentativeFault representativeFault = new RepresentativeFault();
                representativeFault.setReturnedObject(new Fault());
                representativeFault.getReturnedObject().setCode("error.code");
                representativeFault.getReturnedObject().setMessage("system error");
                throw new RepresentativeFaultException("system error", representativeFault, e);
            }
        }
        return "testRepresentativeAutocomplete : " + module + office;
    }

    @WebMethod
    public List<Representative> searchRepresentative(@WebParam(name = "module", targetNamespace = "")String module,
                                                     @WebParam(name = "office", targetNamespace = "")String office,
                                                     @WebParam(name = "representativeId", targetNamespace = "")String representativeId,
                                                     @WebParam(name = "representativeName", targetNamespace = "")String representativeName,
                                                     @WebParam(name = "nationality", targetNamespace = "")String representativeNationality,
                                                     @WebParam(name = "numberOfResults", targetNamespace = "")int numberOfResults) throws RepresentativeFaultException {
        List<Representative> representatives = new ArrayList<Representative>();
        for (int i = 0 ; i<numberOfResults; i++) {
            Representative representative = new Representative();
            representative.setGender(Gender.MALE);
            representative.setNationality(representativeNationality);
            representative.setPersonNumber(representativeId);
            representatives.add(representative);
        }

        return representatives;
    }

    @WebMethod
    public Representative getRepresentative(@WebParam(name = "module", targetNamespace = "")String module,
                                            @WebParam(name = "office", targetNamespace = "")String office,
                                            @WebParam(name = "representativeId", targetNamespace = "")String representativeId) throws RepresentativeFaultException {
        Representative representative = new Representative();
        if (StringUtils.isNotBlank(representativeId)) {
            representative.setPersonNumber(representativeId);
            representative.setGender(Gender.MALE);
        } else {
            RepresentativeFault representativeFault = new RepresentativeFault();
            representativeFault.setReturnedObject(new Fault());
            representativeFault.getReturnedObject().setCode("error.code");
            representativeFault.getReturnedObject().setMessage("Empty representativeId");
            throw new RepresentativeFaultException("Empty representativeId", representativeFault);
        }

        return  representative;
    }

    @WebMethod
    public List<Representative> matchRepresentative(@WebParam(name = "module", targetNamespace = "")String module,
                                                    @WebParam(name = "office", targetNamespace = "")String office,
                                                    @WebParam(name = "representative", targetNamespace = "")Representative representative,
                                                    @WebParam(name = "numberOfResults", targetNamespace = "")int numberOfResults) throws RepresentativeFaultException {
        List<Representative> representatives = new ArrayList<Representative>();
        for (int i = 0 ; i<numberOfResults; i++) {
            Representative representative1 = new Representative();
            representative1.setGender(Gender.MALE);
            representative1.setNationality(representative.getNationality());
            representative1.setDateOfBirth(representative.getDateOfBirth());
            representatives.add(representative1);
        }

        return representatives;
    }

    @WebMethod
    public Result saveRepresentative(String module, String office, String user, Representative representative) throws RepresentativeFaultException {
        return new Result();
    }
}
