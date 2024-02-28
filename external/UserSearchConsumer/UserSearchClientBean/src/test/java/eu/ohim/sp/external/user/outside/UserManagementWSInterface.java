package eu.ohim.sp.external.user.outside;

import eu.ohim.sp.external.domain.user.UserGroup;
import eu.ohim.sp.external.domain.user.UserRole;
import eu.ohim.sp.external.services.user.GetUser;
import eu.ohim.sp.external.services.user.GetUserResponse;
import eu.ohim.sp.external.services.user.SearchUser;
import eu.ohim.sp.external.services.user.SearchUserResponse;
import eu.ohim.sp.external.ws.exception.UserFaultException;
import eu.ohim.sp.external.services.user.SearchUser;

import javax.jws.WebService;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 09/09/13
 * Time: 13:23
 * To change this template use File | Settings | File Templates.
 */
@WebService
public interface UserManagementWSInterface {

    /**
     * Search user.
     *
     * @param parameters
     * @return
     *     returns SearchUserResponse
     * @throws eu.ohim.sp.external.ws.exception.UserFaultException
     */
    public SearchUserResponse searchUser(
            SearchUser parameters)
            throws UserFaultException
    ;

    /**
     *
     * @param parameters
     * @return
     *     returns GetUserResponse
     * @throws UserFaultException
     */
    public GetUserResponse getUser  (
            GetUser parameters)
            throws UserFaultException
    ;

    /**
     * Gets the all roles.
     *
     * @return the all roles
     * @throws UserFaultException the user fault exception
     */
    public List<UserRole> getAllRoles(
            String module,
            String office) throws UserFaultException;

    /**
     * Gets the all groups.
     *
     * @return the all groups
     * @throws UserFaultException the user fault exception
     */
    public List<UserGroup> getAllGroups(
            String module,
            String office) throws UserFaultException;

}
