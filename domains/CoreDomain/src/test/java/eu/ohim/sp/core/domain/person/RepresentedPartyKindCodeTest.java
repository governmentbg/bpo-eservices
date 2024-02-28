package eu.ohim.sp.core.domain.person;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class RepresentedPartyKindCodeTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(RepresentedPartyKindCode.class);
	}

	@Test
	public void testValues() {
		RepresentedPartyKindCode kindCodeApplicant = RepresentedPartyKindCode.APPLICANT;
		RepresentedPartyKindCode kindCodeAssignee = RepresentedPartyKindCode.ASSIGNEE;
		RepresentedPartyKindCode kindCodeOther = RepresentedPartyKindCode.OTHER;
		RepresentedPartyKindCode kindCodeOwneer = RepresentedPartyKindCode.OWNER;

		assertEquals(kindCodeApplicant.value(),
				RepresentedPartyKindCode.APPLICANT.value());
		assertEquals(kindCodeAssignee.value(),
				RepresentedPartyKindCode.ASSIGNEE.value());
		assertEquals(kindCodeOther.value(),
				RepresentedPartyKindCode.OTHER.value());
		assertEquals(kindCodeOwneer.value(),
				RepresentedPartyKindCode.OWNER.value());

	}

	@Test
	public void testFromValue() {
		assertEquals(RepresentedPartyKindCode.APPLICANT,
				RepresentedPartyKindCode
						.fromValue(RepresentedPartyKindCode.APPLICANT.value()));
		assertEquals(RepresentedPartyKindCode.ASSIGNEE,
				RepresentedPartyKindCode
						.fromValue(RepresentedPartyKindCode.ASSIGNEE.value()));
		assertEquals(RepresentedPartyKindCode.OTHER,
				RepresentedPartyKindCode
						.fromValue(RepresentedPartyKindCode.OTHER.value()));
		assertEquals(RepresentedPartyKindCode.OWNER,
				RepresentedPartyKindCode
						.fromValue(RepresentedPartyKindCode.OWNER.value()));

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExpectedException() {
		RepresentedPartyKindCode.fromValue("wrong value"); 
	}

}
