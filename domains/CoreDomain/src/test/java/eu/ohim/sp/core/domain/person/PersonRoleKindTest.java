package eu.ohim.sp.core.domain.person;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class PersonRoleKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(PersonRoleKind.class);
	}

	@Test
	public void testValues() {
		PersonRoleKind personRoleKindApplicant = PersonRoleKind.APPLICANT;
		PersonRoleKind personRoleKindEmployee = PersonRoleKind.EMPLOYEE;
		PersonRoleKind personRoleKindLegalPractitioner = PersonRoleKind.LEGAL_PRACTITIONER;
		PersonRoleKind personRoleKindOhim = PersonRoleKind.OHIM_PROFESSIONAL_REPRESENTATIVE;
		PersonRoleKind personRoleKindOther = PersonRoleKind.OTHER;
		PersonRoleKind personRoleKindRepresentative = PersonRoleKind.REPRESENTATIVE;

		assertEquals(personRoleKindApplicant.toString(),
				PersonRoleKind.APPLICANT.toString());
		assertEquals(personRoleKindEmployee.toString(),
				PersonRoleKind.EMPLOYEE.toString());
		assertEquals(personRoleKindLegalPractitioner.toString(),
				PersonRoleKind.LEGAL_PRACTITIONER.toString());
		assertEquals(personRoleKindOhim.toString(),
				PersonRoleKind.OHIM_PROFESSIONAL_REPRESENTATIVE.toString());
		assertEquals(personRoleKindOther.toString(),
				PersonRoleKind.OTHER.toString());
		assertEquals(personRoleKindRepresentative.toString(),
				PersonRoleKind.REPRESENTATIVE.toString());

		assertEquals(personRoleKindApplicant.value(),
				PersonRoleKind.APPLICANT.value());
		assertEquals(personRoleKindEmployee.value(),
				PersonRoleKind.EMPLOYEE.value());
		assertEquals(personRoleKindLegalPractitioner.value(),
				PersonRoleKind.LEGAL_PRACTITIONER.value());
		assertEquals(personRoleKindOhim.value(),
				PersonRoleKind.OHIM_PROFESSIONAL_REPRESENTATIVE.value());
		assertEquals(personRoleKindOther.value(), PersonRoleKind.OTHER.value());
		assertEquals(personRoleKindRepresentative.value(),
				PersonRoleKind.REPRESENTATIVE.value());
	}

}
