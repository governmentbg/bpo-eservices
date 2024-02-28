package eu.ohim.sp.core.domain.user;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class UserGroupTest {

	private static final Integer USER_GROUP_ID = 1;
	private static final String NAME = "name";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(UserGroup.class);
	}

	@Test
	public void testEquals() {
		UserGroup userGroup1 = new UserGroup();
		userGroup1.setId(USER_GROUP_ID);
		userGroup1.setName(NAME);

		UserGroup userGroup2 = new UserGroup();
		userGroup2.setId(USER_GROUP_ID);
		userGroup2.setName(NAME);

		assertTrue(userGroup1.equals(userGroup2));
		assertEquals(userGroup1.hashCode(), userGroup2.hashCode());
	}

	@Test
	public void testToString() {
		UserGroup userGroup = new UserGroup();
		userGroup.setId(USER_GROUP_ID);
		userGroup.setName(NAME);

		assertEquals(userGroup.toString(),
				"UserGroup [name=" + userGroup.getName() + "]");

	}

}
