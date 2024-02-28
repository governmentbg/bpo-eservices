package eu.ohim.sp.external.application.outside;

import eu.ohim.sp.external.domain.application.NumberingResult;
import eu.ohim.sp.external.domain.common.Fault;
import eu.ohim.sp.external.domain.common.Result;
import eu.ohim.sp.external.services.application.ApplicationFault;
import eu.ohim.sp.external.ws.exception.ApplicationFaultException;
import org.apache.commons.lang.StringUtils;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 09/09/13
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
@WebService(serviceName = "ApplicationService", targetNamespace = "http://ohim.eu/sp/services/application/v3", portName = "ApplicationServicePort", wsdlLocation = "wsdl/ApplicationService.wsdl")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class ApplicationManagementWS implements ApplicationManagementWSInterface {

    /**
     *
     * @param office
     * @param user
     * @param filingNumber
     * @return
     *     returns byte[]
     */
    @WebMethod
    @WebResult(targetNamespace = "", name = "applicationFile")
    public byte[] loadApplication(
            @WebParam(name = "office", targetNamespace = "")
            String office,
            @WebParam(name = "user", targetNamespace = "")
            String user,
            @WebParam(name = "filingNumber", targetNamespace = "")
            String filingNumber) throws ApplicationFaultException {
        if (StringUtils.isBlank(office) && StringUtils.isBlank(user)) {
            ApplicationFault applicationFault = new ApplicationFault();
            applicationFault.setFault(new Fault("business.code.01", "failed to save"));

            try {
                int i = 1/0;
            } catch (Exception e) {
                throw new ApplicationFaultException("error", applicationFault, e);
            }
        }

        System.out.println(office);
        System.out.println(user);
        System.out.println(filingNumber);
        System.out.println("office " + office.getBytes());
        System.out.println(new String(office).getBytes());
        System.out.println(new String(office).getBytes());

        return new String(office).getBytes();
    }

    /**
     *
     * @param office
     * @param user
     * @param filingNumber
     * @param finalDraft
     * @return
     *     returns com.ohim.sp.ext.application.ws.client.Result
     */
    @WebMethod
    @WebResult(targetNamespace = "", name = "result")
    public Result saveApplication(
            @WebParam(name = "office", targetNamespace = "")
            String office,
            @WebParam(name = "user", targetNamespace = "")
            String user,
            @WebParam(name = "filingNumber", targetNamespace = "")
            String filingNumber,
            @WebParam(name = "finalDraft", targetNamespace = "")
            boolean finalDraft) throws ApplicationFaultException {
        if (StringUtils.isBlank(office) && StringUtils.isBlank(user)) {
            ApplicationFault applicationFault = new ApplicationFault();
            applicationFault.setFault(new Fault("business.code.01", "failed to save"));

            throw new ApplicationFaultException("error", applicationFault);
        }
        return new Result("success", office);
    }


    /**
     * Checks if the provided application exists already on the system,
     * under the provided information
     * @param applicationType the type of the application
     * @param formName the form name
     * @param applicationNumber the application number
     * @param registrationNumber the registration number
     * @return
     *     true if the provided application exists, otherwise no
     * @throws ApplicationFaultException
     */
    @WebMethod
    @WebResult(targetNamespace = "", name = "checkResult")
    public Boolean checkExistingApplication(
            @WebParam(name = "applicationType", targetNamespace = "")
            String applicationType,
            @WebParam(name = "formName", targetNamespace = "")
            String formName,
            @WebParam(name = "applicationNumber", targetNamespace = "")
            String applicationNumber,
            @WebParam(name = "registrationNumber", targetNamespace = "")
            String registrationNumber) throws ApplicationFaultException {
        if ("TM_RENEWAL".equals(applicationType)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }


    /**
     *
     * @param filingNumber
     * @param applicationType
     * @return
     *     returns com.ohim.sp.ext.application.ws.client.xxx.ApplicationNumber
     * @throws ApplicationFaultException
     */
    @WebMethod
    @WebResult(targetNamespace = "", name = "numberingResult")
    public NumberingResult getApplicationNumber(
            @WebParam(name = "module", targetNamespace = "")
            String module,
            @WebParam(name = "applicationType", targetNamespace = "")
            String applicationType,
            @WebParam(name = "filingNumber", targetNamespace = "")
            String filingNumber)
            throws ApplicationFaultException {
        NumberingResult applicationNumberingResult = new NumberingResult();
        applicationNumberingResult.setNumber("TEST"+filingNumber);

        if (StringUtils.equals(module, "error")) {
            ApplicationFault applicationFault = new ApplicationFault();
            applicationFault.setFault(new Fault("business.error.02", "Unexpected value"));

            throw new ApplicationFaultException("error", applicationFault);
        }

        return applicationNumberingResult;
    }

}