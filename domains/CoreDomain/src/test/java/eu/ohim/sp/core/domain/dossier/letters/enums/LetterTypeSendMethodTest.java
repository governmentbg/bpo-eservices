package eu.ohim.sp.core.domain.dossier.letters.enums;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class LetterTypeSendMethodTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(LetterTypeSendMethod.class);
	}

	@Test
	public void testValues() {
		for (LetterTypeSendMethod sendMethod : LetterTypeSendMethod.values()) {
			LetterTypeSendMethod letterTypeSendMethod = sendMethod;
			assertEquals(letterTypeSendMethod.getValue(), sendMethod.getValue());
		}
	}

}
