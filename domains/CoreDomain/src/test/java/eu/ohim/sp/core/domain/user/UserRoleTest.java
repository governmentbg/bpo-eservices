package eu.ohim.sp.core.domain.user;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;
import java.util.ArrayList;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class UserRoleTest {

	private static final String DESCRIPTION = "description";
	private static final String NAME = "name";
	private static final String PERMISSION = "permission";
	private static final String STATUS = "status";
	private static final String USER_ROLE_ID = "userRoleId";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(UserRole.class);
	}

	@Test
	public void testEquals() {
		UserRole userRole1 = new UserRole();
		userRole1.setDescription(DESCRIPTION);
		userRole1.setName(NAME);
		userRole1.setPermissions(new ArrayList<String>());
		userRole1.getPermissions().add(PERMISSION);
		userRole1.setStatus(STATUS);
		userRole1.setUserRoleId(USER_ROLE_ID);

		UserRole userRole2 = new UserRole();
		userRole2.setDescription(DESCRIPTION);
		userRole2.setName(NAME);
		userRole2.setPermissions(new ArrayList<String>());
		userRole2.getPermissions().add(PERMISSION);
		userRole2.setStatus(STATUS);
		userRole2.setUserRoleId(USER_ROLE_ID);

		assertTrue(userRole1.equals(userRole2));
		assertEquals(userRole1.hashCode(), userRole2.hashCode());
	}

	@Test
	public void testToString() {
		UserRole userRole = new UserRole();
		userRole.setDescription(DESCRIPTION);
		userRole.setName(NAME);
		userRole.setPermissions(new ArrayList<String>());
		userRole.getPermissions().add(PERMISSION);
		userRole.setStatus(STATUS);
		userRole.setUserRoleId(USER_ROLE_ID);

		assertEquals(
				userRole.toString(),
				"UserRole [userRoleId=" + userRole.getUserRoleId() + ", name="
						+ userRole.getName() + ", description="
						+ userRole.getDescription() + ", status="
						+ userRole.getStatus() + "]");
	}
}
