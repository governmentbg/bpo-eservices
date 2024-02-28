package eu.ohim.sp.core.domain.trademark;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.util.ArrayList;

import eu.ohim.sp.core.domain.model.Vienna;
import org.junit.Test;

import eu.ohim.sp.core.domain.common.Text;
import eu.ohim.sp.core.domain.resources.Colour;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.util.JavaBeanTester;

public class ImageSpecificationTest {

	private static final short SERIES_IDENTIFIER = 1;
	private static final String DOCUMENT_ID = "documentId";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ImageSpecification.class);
	}

	@Test
	public void testEquals() {
		ImageSpecification imageSpecification1 = new ImageSpecification();
		imageSpecification1.setColourClaimedIndicator(true);
		imageSpecification1.setColourClaimedText(new ArrayList<Text>());
		imageSpecification1.setColours(new ArrayList<Colour>());
		imageSpecification1.setRepresentation(new Document());
		imageSpecification1.getRepresentation().setDocumentId(DOCUMENT_ID);
		imageSpecification1.setSeriesIdentifier(SERIES_IDENTIFIER);
		imageSpecification1.setVienna(new ArrayList<Vienna>());

		ImageSpecification imageSpecification2 = new ImageSpecification();
		imageSpecification2.setColourClaimedIndicator(true);
		imageSpecification2.setColourClaimedText(new ArrayList<Text>());
		imageSpecification2.setColours(new ArrayList<Colour>());
		imageSpecification2.setRepresentation(new Document());
		imageSpecification2.getRepresentation().setDocumentId(DOCUMENT_ID);
		imageSpecification2.setSeriesIdentifier(SERIES_IDENTIFIER);
		imageSpecification2.setVienna(new ArrayList<Vienna>());

		assertEquals(imageSpecification1.isColourClaimedIndicator(),
				imageSpecification2.isColourClaimedIndicator());
		assertEquals(imageSpecification1.getColourClaimedText().size(),
				imageSpecification2.getColourClaimedText().size());
		assertEquals(imageSpecification1.getColours().size(),
				imageSpecification2.getColours().size());
		assertEquals(imageSpecification1.getRepresentation().getDocumentId(),
				imageSpecification2.getRepresentation().getDocumentId());
		assertEquals(imageSpecification1.getSeriesIdentifier(),
				imageSpecification2.getSeriesIdentifier());
		assertEquals(imageSpecification1.getVienna().size(),
				imageSpecification2.getVienna().size());
	}

}
