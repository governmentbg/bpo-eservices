package eu.ohim.sp.core.domain.dossier.letters.enums;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class LetterRecipientKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(LetterRecipientKind.class);
	}

	@Test
	public void testValues() {
		for (LetterRecipientKind kind : LetterRecipientKind.values()) {
			LetterRecipientKind letterRecipientKind = kind;
			assertEquals(letterRecipientKind.getValue(), kind.getValue());
		}
	}

}
