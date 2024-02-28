package eu.ohim.sp.core.person;

import eu.ohim.sp.core.domain.user.User;
import eu.ohim.sp.core.domain.user.UserGroup;
import eu.ohim.sp.core.domain.user.UserRole;
import eu.ohim.sp.core.user.UserSearchService;
import eu.ohim.sp.core.user.UserSearchServiceLocal;
import eu.ohim.sp.core.user.UserSearchServiceRemote;
import eu.ohim.sp.external.user.UserSearchClientInside;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by marcoantonioalberoalbero on 17/8/16.
 */
@Stateless
public class UserServiceBean implements UserSearchServiceLocal, UserSearchServiceRemote {

    @Inject @UserSearchClientInside
    private UserSearchService userSearchService;

    @Override
    public List<? extends User> searchUser(String module, String office, Map searchCriteria) {
        return userSearchService.searchUser(module, office, searchCriteria);
    }

    @Override
    public User getUser(String module, String office, String username) {
        return userSearchService.getUser(module, office, username);
    }

    @Override
    public List<UserRole> getAllRoles(String module, String office) {
        return userSearchService.getAllRoles(module, office);
    }

    @Override
    public List<UserGroup> getAllGroups(String module, String office) {
        return userSearchService.getAllGroups(module, office);
    }
}
