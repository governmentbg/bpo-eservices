package eu.ohim.sp.core.domain.dossier.letters.enums;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class LetterStatusKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(LetterStatusKind.class);
	}

	@Test
	public void testValues() {
		for (LetterStatusKind kind : LetterStatusKind.values()) {
			LetterStatusKind letterStatusKind = kind;
			assertEquals(letterStatusKind.getValue(), kind.getValue());
		}
	}

}
