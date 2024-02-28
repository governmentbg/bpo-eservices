package eu.ohim.sp.external.user.outside;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Endpoint;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters;
import eu.ohim.sp.core.domain.user.FOUser;
import eu.ohim.sp.core.domain.user.User;
import eu.ohim.sp.core.domain.user.UserGroup;
import eu.ohim.sp.core.domain.user.UserRole;


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

    static Endpoint endpoint = null;

    @BeforeClass
    public static void setupEnpoint() {
    	endpoint = Endpoint.publish("http://localhost:8380/fsp/ws/user/services", new UserManagementWS());

        assertTrue(endpoint.isPublished());
    }
    
    @Before
    public void setup() {

        Adapters adapters = new Adapters();
        Adapters.Adapter adapter = new Adapters.Adapter();

        adapter.setName("user");
        adapter.setEnabled(true);
        adapter.setWsdlLocation("http://localhost:8380/fsp/ws/user/services?wsdl");
        adapters.getAdapter().add(adapter);

        MockitoAnnotations.initMocks(this);
        when(systemConfigurationServiceInterface.getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class))).thenReturn(adapters);

        userClientService.init();

        verify(systemConfigurationServiceInterface, times(1)).getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class));
    }

    @AfterClass
    public static void shutdown() {
        endpoint.stop();
    }

    @Test
    public void testGetUser() {
        assertEquals(userClientService.getUser("fo", "EM", "abril23").getUserName(), "abril23");
    }

    @Test
    public void testGetUserError() {
        assertNull(userClientService.getUser("", "EM", "abril23"));
    }

    @Test
    public void testSearchUser() {
        Map<String, String> criteria = new HashMap<String, String>();
        criteria.put("user", "abril23");

        List<? extends User> users = userClientService.searchUser("fo", "EM", criteria);

        assertEquals(users.size(), 1);
        for (Object foUser : users) {
            assertTrue(foUser instanceof FOUser);
            assertEquals(((FOUser) foUser).getUserPersonDetails().getApplicants().size(), 0);
            assertEquals(((FOUser) foUser).getUserPersonDetails().getRepresentatives().size(), 0);
            assertEquals(((FOUser) foUser).getUserName(), "abril23");
        }
    }

    @Test
    public void testSearchUserBO() {
        Map<String, String> criteria = new HashMap<String, String>();
        criteria.put("user", "abril23");

        List<? extends User> users = userClientService.searchUser("bo", "EM", criteria);

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

    @Test
    public void testGetRoles() {
        List<UserRole> roles = userClientService.getAllRoles("bo", "EM");
        assertEquals(roles.size(), 1);
        for (UserRole role : roles) {
            assertTrue(role.getName().contains("bo"));
        }

    }


    @Test
    public void testGetRolesError() {
        List<UserRole> roles = userClientService.getAllRoles("", "EM");
        assertNull(roles);

    }


    @Test
    public void testGetGroups() {
        List<UserGroup> groups = userClientService.getAllGroups("fo", "EM");
        assertEquals(groups.size(), 1);
        for (UserGroup group : groups) {
            assertTrue(group.getName().contains("fo"));
        }

    }

}
