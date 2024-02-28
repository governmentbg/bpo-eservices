package eu.ohim.sp.external.resource.outside;

import eu.ohim.sp.external.domain.common.Fault;
import eu.ohim.sp.external.services.resource.ResourceFault;
import eu.ohim.sp.external.ws.exception.ResourceFaultException;
import org.apache.commons.lang.StringUtils;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

@WebService(serviceName = "ResourceService", targetNamespace = "http://ohim.eu/sp/services/resource/v3", portName = "ResourceServicePort", wsdlLocation = "wsdl/ResourceService.wsdl")
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class ResourceManagementWS implements ResourceManagementWSInterface {

    @WebMethod
    @WebResult(name = "getMessageResponse", targetNamespace = "http://ohim.eu/sp/services/resource/v3", partName = "parameters")
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public String getMessage(
            @WebParam(name = "getMessage", targetNamespace = "http://ohim.eu/sp/services/resource/v3", partName = "parameters")
            String parameters)
            throws ResourceFaultException {
        if (StringUtils.isBlank(parameters)) {
            try {
                StringUtils.defaultIfBlank(parameters, null).equals("error");
            } catch (NullPointerException e) {
                ResourceFault resourceFault = new ResourceFault();
                resourceFault.setReturnedObject(new Fault());
                resourceFault.getReturnedObject().setCode("error.code");
                resourceFault.getReturnedObject().setMessage("system error");
                throw new ResourceFaultException("system error", resourceFault, e);
            }
        }

        return "resource : " + parameters;

    }


}
