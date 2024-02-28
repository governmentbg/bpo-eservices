/*
 *  UserSearchService:: UserSearchService 06/09/13 11:13 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.user;

import eu.ohim.sp.core.domain.user.User;
import eu.ohim.sp.core.domain.user.UserGroup;
import eu.ohim.sp.core.domain.user.UserRole;

import java.util.List;
import java.util.Map;

/**
 * Services provided for users where you can search for a user,
 * to get a user, to get Roles and get the Groups
 */
public interface UserSearchService {
    /**
     * Search user.
     *
     * @param searchCriteria the search criteria
     * @return the list
     */
    List<? extends User> searchUser(String module, String office, Map searchCriteria);

    /**
     * Gets the user details.
     *
     * @param username the username
     * @return the user details
     */
    User getUser(String module, String office, String username);

    /**
     * Gets the all roles.
     *
     * @return the all roles
     */
    List<UserRole> getAllRoles(String module, String office);

    /**
     * Gets the all groups.
     *
     * @return the all groups
     */
    List<UserGroup> getAllGroups(String module, String office);

}
