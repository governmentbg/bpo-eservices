package eu.ohim.sp.core.domain.trademark;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Assert;
import org.junit.Test;

public class SoundSpecificationTest {

	private static final short SERIES_IDENTIFIER = 1;
	private static final String DOCUMENT_ID = "documentId";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(SoundSpecification.class);
	}

	@Test
	public void testEquals() {
		SoundSpecification soundSpecification1 = new SoundSpecification();
		soundSpecification1.setDocument(new Document());
		soundSpecification1.getDocument().setDocumentId(DOCUMENT_ID);
		soundSpecification1.setSeriesIdentifier(SERIES_IDENTIFIER);

		SoundSpecification soundSpecification2 = new SoundSpecification();
		soundSpecification2.setDocument(new Document());
		soundSpecification2.getDocument().setDocumentId(DOCUMENT_ID);
		soundSpecification2.setSeriesIdentifier(SERIES_IDENTIFIER);

		Assert.assertEquals(soundSpecification1.getDocument().getDocumentId(),
                soundSpecification2.getDocument().getDocumentId());
		assertEquals(soundSpecification1.getSeriesIdentifier(),
				soundSpecification2.getSeriesIdentifier());
	}

}
