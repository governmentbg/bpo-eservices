package eu.ohim.sp.core.domain.person;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class PersonRoleRelationKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(PersonRoleRelationKind.class);
	}

	@Test
	public void testValues() {
		PersonRoleRelationKind personRoleRelationKindAssociation = PersonRoleRelationKind.ASSOCIATION;
		assertEquals(personRoleRelationKindAssociation.value(),
				PersonRoleRelationKind.ASSOCIATION.value());

		PersonRoleRelationKind personRoleRelationKindRepresentation = PersonRoleRelationKind.REPRESENTATION;
		assertEquals(personRoleRelationKindRepresentation.value(),
				PersonRoleRelationKind.REPRESENTATION.value());
	}

	@Test
	public void testFromValue() {
		assertEquals(PersonRoleRelationKind.ASSOCIATION,
				PersonRoleRelationKind
						.fromValue(PersonRoleRelationKind.ASSOCIATION.value()));
		assertEquals(PersonRoleRelationKind.REPRESENTATION,
				PersonRoleRelationKind
						.fromValue(PersonRoleRelationKind.REPRESENTATION.value()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExpectedException() {
		PersonRoleRelationKind.fromValue("wrong value");
	}

}
