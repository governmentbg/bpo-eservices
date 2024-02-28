package eu.ohim.sp.external.user.inside;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.user.FOUser;
import eu.ohim.sp.core.domain.user.User;
import eu.ohim.sp.core.domain.user.UserGroup;
import eu.ohim.sp.core.domain.user.UserRole;
import org.apache.commons.lang.NotImplementedException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 09/09/13
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
public class UserSearchClientBeanTest {

    @InjectMocks
    UserSearchClientBean userClientService;

    @Mock
    ConfigurationService systemConfigurationServiceInterface;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userClientService.init();
    }

    @Test
    public void testGetUser() {
        assertEquals(userClientService.getUser("tmefiling", "EM", "carlos").getUserName(), "carlos");
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserError() {
        userClientService.getUser("", "EM", "abril23");
    }

    @Test(expected = NotImplementedException.class)
    public void testSearchUser() {
        Map<String, String> criteria = new HashMap<String, String>();
        criteria.put("user", "abril23");

        List<? extends User> users = userClientService.searchUser("tmefiling", "EM", criteria);

        assertEquals(users.size(), 1);
        for (Object foUser : users) {
            assertTrue(foUser instanceof FOUser);
            assertEquals(((FOUser) foUser).getUserPersonDetails().getApplicants().size(), 0);
            assertEquals(((FOUser) foUser).getUserPersonDetails().getRepresentatives().size(), 0);
            assertEquals(((FOUser) foUser).getUserName(), "abril23");
        }
    }

    @Test(expected = NotImplementedException.class)
    public void testSearchUserBO() {
        Map<String, String> criteria = new HashMap<String, String>();
        criteria.put("user", "abril23");

        List<? extends User> users = userClientService.searchUser("tmefiling", "EM", criteria);

        assertEquals(users.size(), 1);
        for (Object boUser : users) {
            assertTrue(boUser instanceof eu.ohim.sp.core.domain.user.BOUser);
            assertEquals(((eu.ohim.sp.core.domain.user.BOUser) boUser).getFullName(), "Nikos Papas");
            assertEquals(((eu.ohim.sp.core.domain.user.BOUser) boUser).getGroups().size(), 2);
            assertEquals(((eu.ohim.sp.core.domain.user.BOUser) boUser).getRole().size(), 1);
            assertEquals(((eu.ohim.sp.core.domain.user.BOUser) boUser).getStatus(), "active");

            assertEquals(((eu.ohim.sp.core.domain.user.BOUser) boUser).getUserName(), "abril23");
        }
    }

    @Test(expected = NotImplementedException.class)
    public void testGetRoles() {
        List<UserRole> roles = userClientService.getAllRoles("tmefiling", "EM");
        assertEquals(roles.size(), 1);
        for (UserRole role : roles) {
            assertTrue(role.getName().contains("bo"));
        }

    }


    @Test(expected = NotImplementedException.class)
    public void testGetRolesError() {
        List<UserRole> roles = userClientService.getAllRoles("", "EM");
        assertNull(roles);

    }


    @Test(expected = NotImplementedException.class)
    public void testGetGroups() {
        List<UserGroup> groups = userClientService.getAllGroups("tmefiling", "EM");
        assertEquals(groups.size(), 1);
        for (UserGroup group : groups) {
            assertTrue(group.getName().contains("fo"));
        }

    }

}
