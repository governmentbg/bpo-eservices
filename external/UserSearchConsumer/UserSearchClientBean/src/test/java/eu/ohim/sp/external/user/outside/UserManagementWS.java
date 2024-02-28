package eu.ohim.sp.external.user.outside;

import eu.ohim.sp.external.domain.common.Entry;
import eu.ohim.sp.external.domain.common.Fault;
import eu.ohim.sp.external.domain.user.*;
import eu.ohim.sp.external.domain.user.ObjectFactory;
import eu.ohim.sp.external.services.user.*;
import eu.ohim.sp.external.ws.exception.UserFaultException;
import eu.ohim.sp.external.services.user.SearchUser;
import org.apache.commons.lang.StringUtils;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingType;
import javax.xml.ws.ResponseWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 09/09/13
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */
@WebService(serviceName = "UserSearchService", targetNamespace = "http://ohim.eu/sp/services/user-search/v3", portName = "UserSearchServicePort", wsdlLocation = "wsdl/UserSearchService.wsdl")
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class UserManagementWS implements UserManagementWSInterface {

    /**
     * Search user.
     *
     * @param parameters
     * @return
     *     returns SearchUserResponse
     * @throws UserFaultException
     */
    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public SearchUserResponse searchUser(
            @WebParam(name = "searchUser", targetNamespace = "http://ohim.eu/sp/services/user-search/v3", partName = "parameters")
            SearchUser parameters)
            throws UserFaultException {
        SearchUserResponse searchUserResponse = new SearchUserResponse();
        ObjectFactory objectFactory = new ObjectFactory();
        eu.ohim.sp.external.services.user.ObjectFactory userServiceObjectFactory = new eu.ohim.sp.external.services.user.ObjectFactory();
        if (parameters.getModule().equals("fo")) {
            FOUser foUser = objectFactory.createFOUser();
            foUser.setUserPersonDetails(objectFactory.createUserPersonDetails());

            System.out.println("+++++++++++++++");
            for (Entry entry : parameters.getSearchCriteria().getEntry()) {
                System.out.println("+++++++++++++++ : " + entry.getKey());
                if (entry.getKey().equals("user")) {
                    foUser.setUserName(entry.getValue());
                }
            }
            List<JAXBElement<? extends User>> foUsers = new ArrayList<JAXBElement<? extends User>>();
            foUsers.add(userServiceObjectFactory.createFoUser(foUser));

            searchUserResponse.setUser(foUsers);

        } else {
            BOUser boUser = objectFactory.createBOUser();

            for (Entry entry : parameters.getSearchCriteria().getEntry()) {
                if (entry.getKey().equals("user")) {
                    boUser.setUserName(entry.getValue());
                }
            }

            boUser.setGroups(new ArrayList<String>());
            boUser.getGroups().add("random");
            boUser.getGroups().add("group");
            boUser.setFullName("Nikos Papas");
            boUser.setStatus("active");
            boUser.setRole(new ArrayList<String>());
            boUser.getRole().add("role");

            List<JAXBElement<? extends User>> boUsers = new ArrayList<JAXBElement<? extends User>>();
            boUsers.add(userServiceObjectFactory.createBoUser(boUser));
            searchUserResponse.setUser(boUsers);

        }

        return searchUserResponse;
    }

    /**
     *
     * @param parameters
     * @return
     *     returns GetUserResponse
     * @throws UserFaultException
     */
    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public GetUserResponse getUser  (
            @WebParam(name = "getUser", targetNamespace = "http://ohim.eu/sp/services/user-search/v3", partName = "parameters")
            GetUser parameters)
            throws UserFaultException {
        if (StringUtils.isBlank(parameters.getModule())) {
            try {
                StringUtils.defaultIfBlank(parameters.getModule(), null).equals("error");
            } catch (NullPointerException e) {
                UserFault userFault = new UserFault();
                userFault.setReturnedObject(new Fault());
                userFault.getReturnedObject().setCode("error.code");
                userFault.getReturnedObject().setMessage("system error");
                throw new UserFaultException("system error", userFault, e);
            }
        }

        GetUserResponse getUserResponse = new GetUserResponse();
        if (parameters.getModule().equals("fo")) {
            FOUser foUser = new FOUser();
            foUser.setUserName(parameters.getUsername());

            getUserResponse.setUser(new JAXBElement<FOUser>(new QName("http://ohim.eu/sp/services/user-search/v3", "foUser"), FOUser.class, foUser));

        } else {
            BOUser boUser = new BOUser();
            boUser.setUserName(parameters.getUsername());

            getUserResponse.setUser(new JAXBElement<BOUser>(new QName("http://ohim.eu/sp/services/user-search/v3", "boUser"), BOUser.class, boUser));

        }


        return getUserResponse;
    }

    /**
     * Gets the all roles.
     *
     * @return the all roles
     * @throws UserFaultException the user fault exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @ResponseWrapper(localName = "getAllRolesResponse", targetNamespace = "http://ohim.eu/sp/services/user-search/v3", className = "eu.ohim.sp.ext.services.user.GetAllRolesResponse")
    public List<UserRole> getAllRoles(
            @WebParam(name = "module", targetNamespace = "")
            String module,
            @WebParam(name = "office", targetNamespace = "")
            String office) throws UserFaultException {
        if (StringUtils.isBlank(module)) {
            try {
                StringUtils.defaultIfBlank(module, null).equals("error");
            } catch (NullPointerException e) {
                UserFault userFault = new UserFault();
                userFault.setReturnedObject(new Fault());
                userFault.getReturnedObject().setCode("error.code");
                userFault.getReturnedObject().setMessage("system error");
                throw new UserFaultException("system error", userFault);
            }
        }

        List<UserRole> userRoles = new ArrayList<UserRole>();
        userRoles.add(new UserRole());
        userRoles.get(0).setName(module+"Role");

        return userRoles;
    }

    /**
     * Gets the all groups.
     *
     * @return the all groups
     * @throws UserFaultException the user fault exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @ResponseWrapper(localName = "getAllGroupsResponse", targetNamespace = "http://ohim.eu/sp/services/user-search/v3", className = "eu.ohim.sp.ext.services.user.GetAllGroupsResponse")
    public List<UserGroup> getAllGroups(
            @WebParam(name = "module", targetNamespace = "")
            String module,
            @WebParam(name = "office", targetNamespace = "")
            String office) throws UserFaultException {

        List<UserGroup> userGroups = new ArrayList<UserGroup>();
        userGroups.add(new UserGroup());
        userGroups.get(0).setName(module+"Group");

        return userGroups;
    }


}
