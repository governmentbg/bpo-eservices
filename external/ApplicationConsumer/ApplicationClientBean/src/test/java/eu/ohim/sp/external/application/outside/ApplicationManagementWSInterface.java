package eu.ohim.sp.external.application.outside;

import eu.ohim.sp.external.domain.application.NumberingResult;
import eu.ohim.sp.external.domain.common.Result;
import eu.ohim.sp.external.ws.exception.ApplicationFaultException;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 19/09/13
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public interface ApplicationManagementWSInterface {

    /**
     *
     * @param office
     * @param user
     * @param filingNumber
     * @return
     *     returns byte[]
     */
    byte[] loadApplication(String office, String user, String filingNumber) throws ApplicationFaultException;

    /**
     *
     * @param office
     * @param user
     * @param filingNumber
     * @param finalDraft
     * @return
     *     returns eu.ohim.sp.external.application.ws.client.Result
     */
    Result saveApplication(String office, String user, String filingNumber, boolean finalDraft) throws ApplicationFaultException;


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
    Boolean checkExistingApplication(String applicationType, String formName, String applicationNumber, String registrationNumber) throws ApplicationFaultException;


    /**
     *
     * @param provisionalNumber
     * @param applicationType
     * @return
     *     returns eu.ohim.sp.external.application.ws.client.xxx.ApplicationNumber
     * @throws ApplicationFaultException
     */
    NumberingResult getApplicationNumber(String applicationType, String provisionalNumber, String filingNumber)
            throws ApplicationFaultException
    ;

}
