package eu.ohim.sp.external.resource.outside;

import eu.ohim.sp.external.ws.exception.ResourceFaultException;

import javax.jws.WebService;

@WebService
public interface ResourceManagementWSInterface {

    String getMessage(
            String parameters)
            throws ResourceFaultException
            ;

}
