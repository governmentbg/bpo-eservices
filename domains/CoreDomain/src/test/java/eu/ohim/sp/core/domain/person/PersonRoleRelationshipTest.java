package eu.ohim.sp.core.domain.person;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;
import java.util.ArrayList;

import org.junit.Test;

import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.util.JavaBeanTester;

public class PersonRoleRelationshipTest {

	private static final String SKIP_PROPERTY = "personRoleRelationKind";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(PersonRoleRelationship.class, SKIP_PROPERTY);
	}

	@Test
	public void testEquals() {
		PersonRoleRelationship personRoleRelationship1 = new PersonRoleRelationship();
		personRoleRelationship1.setDocuments(new ArrayList<AttachedDocument>());
		personRoleRelationship1.setPersonRole(new PersonRole());
		personRoleRelationship1.getPersonRole().setPersonNumber("personNumber");
		personRoleRelationship1
				.setPersonRoleRelationKind(PersonRoleRelationKind.ASSOCIATION);

		PersonRoleRelationship personRoleRelationship2 = new PersonRoleRelationship();
		personRoleRelationship2.setDocuments(new ArrayList<AttachedDocument>());
		personRoleRelationship2.setPersonRole(new PersonRole());
		personRoleRelationship2.getPersonRole().setPersonNumber("personNumber");
		personRoleRelationship2
				.setPersonRoleRelationKind(PersonRoleRelationKind.ASSOCIATION);

		assertEquals(personRoleRelationship1.getDocuments().size(),
				personRoleRelationship2.getDocuments().size());
		assertEquals(personRoleRelationship1.getPersonRole().getPersonNumber(),
				personRoleRelationship2.getPersonRole().getPersonNumber());
		assertEquals(personRoleRelationship1.getPersonRoleRelationKind(),
				personRoleRelationship2.getPersonRoleRelationKind());
	}

}
