package eu.ohim.sp.core.domain.user;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;
import java.util.ArrayList;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class BOUserTest {

	private static final Integer BOUSER_ID = 1;
	private static final String FULL_NAME = "fullName";
	private static final String GROUP = "group";
	private static final String ROLE = "role";
	private static final String STATUS = "status";
	private static final String USER_NAME = "userName";
	
	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(BOUser.class);
	}
	
	@Test
	public void testEquals() {
		BOUser boUser1 = new BOUser();
		boUser1.setFullName(FULL_NAME);
		boUser1.setGroups(new ArrayList<String>());
		boUser1.getGroups().add(GROUP);
		boUser1.setId(BOUSER_ID);
		boUser1.setRole(new ArrayList<String>());
		boUser1.getRole().add(ROLE);
		boUser1.setStatus(STATUS);
		boUser1.setUserName(USER_NAME);
		
		BOUser boUser2 = new BOUser();
		boUser2.setFullName(FULL_NAME);
		boUser2.setGroups(new ArrayList<String>());
		boUser2.getGroups().add(GROUP);
		boUser2.setId(BOUSER_ID);
		boUser2.setRole(new ArrayList<String>());
		boUser2.getRole().add(ROLE);
		boUser2.setStatus(STATUS);
		boUser2.setUserName(USER_NAME);
		
		assertTrue(boUser1.equals(boUser2));
		assertEquals(boUser1.hashCode(), boUser2.hashCode());
	}
	
	@Test
	public void testToString() {
		BOUser boUser = new BOUser();
		boUser.setFullName(FULL_NAME);
		boUser.setGroups(new ArrayList<String>());
		boUser.getGroups().add(GROUP);
		boUser.setId(BOUSER_ID);
		boUser.setRole(new ArrayList<String>());
		boUser.getRole().add(ROLE);
		boUser.setStatus(STATUS);
		boUser.setUserName(USER_NAME);
		
		assertEquals(boUser.toString(), "BOUser [groups=" + boUser.getGroups() + "]");
	}

}
