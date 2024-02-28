package eu.ohim.sp.core.domain.user;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;
import java.util.ArrayList;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class FOUserTest {

	private static final Integer FOUSER_ID = 1;
	private static final String FULL_NAME = "fullName";
	private static final String ROLE = "role";
	private static final String STATUS = "status";
	private static final String USER_NAME = "userName";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(FOUser.class);
	}

	@Test
	public void testEquals() {

		UserPersonDetails userPersonDetails = new UserPersonDetails();
		userPersonDetails.setId(1);
		
		FOUser foUser1 = new FOUser();
		foUser1.setFullName(FULL_NAME);
		foUser1.setId(FOUSER_ID);
		foUser1.setRole(new ArrayList<String>());
		foUser1.getRole().add(ROLE);
		foUser1.setStatus(STATUS);
		foUser1.setUserName(USER_NAME);
		foUser1.setUserPersonDetails(userPersonDetails);

		FOUser foUser2 = new FOUser();
		foUser2.setFullName(FULL_NAME);
		foUser2.setId(FOUSER_ID);
		foUser2.setRole(new ArrayList<String>());
		foUser2.getRole().add(ROLE);
		foUser2.setStatus(STATUS);
		foUser2.setUserName(USER_NAME);
		foUser2.setUserPersonDetails(userPersonDetails);

		assertTrue(foUser1.equals(foUser2));
		assertEquals(foUser1.hashCode(), foUser2.hashCode());
	}

	@Test
	public void testToString() {
		UserPersonDetails userPersonDetails = new UserPersonDetails();
		userPersonDetails.setId(1);

		FOUser foUser = new FOUser();
		foUser.setFullName(FULL_NAME);
		foUser.setId(FOUSER_ID);
		foUser.setRole(new ArrayList<String>());
		foUser.getRole().add(ROLE);
		foUser.setStatus(STATUS);
		foUser.setUserName(USER_NAME);
		foUser.setUserPersonDetails(userPersonDetails);
		
		assertEquals(foUser.toString(), "FOUser [userPersonDetails=" + foUser.getUserPersonDetails() + "]");
	}

}
