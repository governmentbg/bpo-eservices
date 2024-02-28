package eu.ohim.sp.core.domain.dossier.letters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.domain.dossier.letters.enums.LetterTypeSendMethod;
import eu.ohim.sp.core.domain.resources.Document;
import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class LetterClientTest {

	private static final String BODY_LETTER = "bodyLetter";
	private static final String DOCUMENT_ID = "13212";
	private static final String SEND_STATUS = "sendStatus";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(LetterClient.class);
	}

	@Test
	public void testEquals() {
		LetterClient letterClient1 = new LetterClient();
		letterClient1.setBodyLetter(BODY_LETTER);
		letterClient1.setContentFile(null);
		letterClient1.setContentFile(BODY_LETTER.getBytes());
		letterClient1.setDocument(new Document());
		letterClient1.getDocument().setDocumentId(DOCUMENT_ID);
		letterClient1.setSendMethod(LetterTypeSendMethod.ELECTRONIC);
		letterClient1.setSendStatus(SEND_STATUS);

		LetterClient letterClient2 = new LetterClient();
		letterClient2.setBodyLetter(BODY_LETTER);
		letterClient2.setDocument(new Document());
		letterClient2.getDocument().setDocumentId(DOCUMENT_ID);
		letterClient2.setSendMethod(LetterTypeSendMethod.ELECTRONIC);
		letterClient2.setSendStatus(SEND_STATUS);

		assertEquals(letterClient1.getBodyLetter(),
				letterClient2.getBodyLetter());
		assertTrue(letterClient1.getContentFile() != null);
		assertEquals(letterClient2.getContentFile(), null);
		assertEquals(letterClient1.getDocument().getDocumentId(), letterClient2
				.getDocument().getDocumentId());
		assertEquals(letterClient1.getSendMethod(),
				letterClient2.getSendMethod());
		assertEquals(letterClient1.getSendStatus(),
				letterClient2.getSendStatus());
	}

}
