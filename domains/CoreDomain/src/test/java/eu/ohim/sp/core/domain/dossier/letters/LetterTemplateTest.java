package eu.ohim.sp.core.domain.dossier.letters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.domain.dossier.DossierKind;
import eu.ohim.sp.core.domain.dossier.letters.enums.LetterTypeSendMethod;
import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class LetterTemplateTest {

	private static final String NAME_SHORT = "nameShort";
	private static final String PATH_TEMPLATE = "pathTemplate";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(LetterTemplate.class);
	}

	@Test
	public void testEquals() {
		LetterTemplate letterTemplate1 = new LetterTemplate();
		letterTemplate1.setNameShort(NAME_SHORT);
		letterTemplate1.setPathTemplate(PATH_TEMPLATE);
		letterTemplate1.setTypeDossier(DossierKind.TRADEMARK_APPLICATION);
		letterTemplate1.setTypeMethod(LetterTypeSendMethod.ELECTRONIC);

		LetterTemplate letterTemplate2 = new LetterTemplate();
		letterTemplate2.setNameShort(NAME_SHORT);
		letterTemplate2.setPathTemplate(PATH_TEMPLATE);
		letterTemplate2.setTypeDossier(DossierKind.TRADEMARK_APPLICATION);
		letterTemplate2.setTypeMethod(LetterTypeSendMethod.ELECTRONIC);

		assertTrue(letterTemplate1.equals(letterTemplate2));
		assertTrue(letterTemplate1.equals(letterTemplate1));
		assertEquals(letterTemplate1.hashCode(), letterTemplate2.hashCode());
	}

	@Test
	public void testNotEquals() {
		LetterTemplate letterTemplate = new LetterTemplate();
		letterTemplate.setNameShort(NAME_SHORT);
		letterTemplate.setPathTemplate(PATH_TEMPLATE);
		letterTemplate.setTypeDossier(DossierKind.TRADEMARK_APPLICATION);
		letterTemplate.setTypeMethod(LetterTypeSendMethod.ELECTRONIC);

		assertEquals(letterTemplate.equals(null), false);
		assertEquals(letterTemplate.equals(new Letter()), false);
	}

	@Test
	public void testToString() {
		LetterTemplate letterTemplate = new LetterTemplate();
		letterTemplate.setNameShort(NAME_SHORT);
		letterTemplate.setPathTemplate(PATH_TEMPLATE);
		letterTemplate.setTypeDossier(DossierKind.TRADEMARK_APPLICATION);
		letterTemplate.setTypeMethod(LetterTypeSendMethod.ELECTRONIC);

		assertTrue(letterTemplate.toString().contains(NAME_SHORT));
	}

}
