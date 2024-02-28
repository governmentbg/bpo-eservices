package eu.ohim.sp.core.domain.converter;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.ArrayList;

import org.junit.Test;

import eu.ohim.sp.core.domain.common.Text;
import eu.ohim.sp.core.domain.resources.Colour;
import eu.ohim.sp.core.domain.resources.Image;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.filing.domain.tm.ColourCodeFormatType;
import eu.ohim.sp.filing.domain.tm.ColourCodeType;
import eu.ohim.sp.filing.domain.tm.ColourDetailsType;
import eu.ohim.sp.filing.domain.tm.ColourType;
import eu.ohim.sp.filing.domain.tm.Identifier;
import eu.ohim.sp.filing.domain.tm.MarkImage;
import eu.ohim.sp.filing.domain.tm.URI;

public class MarkImageConverterTest {

	@Test
	public void testAnotherClass() {
		MarkImageConverter converter = new MarkImageConverter();
		assertEquals(converter.convert(BigInteger.class, BigInteger.ONE), null);
	}

	@Test
	public void testConvertToMarkImage() {
		MarkImageConverter converter = new MarkImageConverter();
		ImageSpecification imageSpecification = new ImageSpecification();
		Image representation = new Image();
		representation.setFileName("filename");
		representation.setFileFormat("JPEG");
		representation.setUri("test URI");
		representation.setDocumentId("image id");
		imageSpecification.setRepresentation(representation);

		Colour colour = new Colour();
		colour.setFormat("HEX");
		colour.setValue("D213");
		imageSpecification.setColours(new ArrayList<Colour>());
		imageSpecification.getColours().add(colour);

		colour = new Colour();
		colour.setFormat("RGB");
		colour.setValue("Black");
		imageSpecification.getColours().add(colour);

		colour = new Colour();
		colour.setValue("Black");
		colour.setFormat("PANTONE");
		imageSpecification.getColours().add(colour);

		Text colourText = new Text();
		colourText.setLanguage("en");
		colourText.setValue("colour 1");
		imageSpecification.setColourClaimedText(new ArrayList<Text>());
		imageSpecification.getColourClaimedText().add(colourText);

		MarkImage image = (MarkImage) converter.convert(
				ImageSpecification.class, imageSpecification);

		assertEquals(image.getMarkImageFilename(), "filename");
	}

	@Test
	public void testConvertToImageSpecification() {
		MarkImageConverter converter = new MarkImageConverter();
		MarkImage markImage = new MarkImage();
		markImage
				.setMarkImageColourClaimedText(new ArrayList<eu.ohim.sp.filing.domain.tm.Text>());
		markImage.getMarkImageColourClaimedText().add(
				new eu.ohim.sp.filing.domain.tm.Text());
		markImage.getMarkImageColourClaimedText().get(0).setLanguage("en");
		markImage.getMarkImageColourClaimedText().get(0).setValue("value");

		ColourDetailsType colourDetailsType = new ColourDetailsType();
		colourDetailsType.setColour(new ArrayList<ColourType>());
		ColourType colourType = new ColourType();
		colourType
				.setColourClaimedText(new ArrayList<eu.ohim.sp.filing.domain.tm.Text>());
		colourType.getColourClaimedText().add(new eu.ohim.sp.filing.domain.tm.Text());
		colourType.getColourClaimedText().get(0).setLanguage("en");
		colourType.getColourClaimedText().get(0).setValue("value");
		colourType.setColourCode(new ArrayList<ColourCodeType>());
		colourType.getColourCode().add(new ColourCodeType());
		colourType.getColourCode().get(0).setValue("colour");
		colourType.getColourCode().get(0)
				.setColourCodeFormat(ColourCodeFormatType.HEX);
		colourDetailsType.getColour().add(colourType);

		markImage.setMarkImageColourDetails(colourDetailsType);
		markImage.setMarkImageColourIndicator(true);
		markImage.setMarkImageColourMode("mode");
		markImage.setMarkImageFileFormat("JPEG");
		markImage.setMarkImageFilename("filename");
		markImage.setMarkImageIdentifier(new Identifier());
		markImage.getMarkImageIdentifier().setIdentifierKindCode("id");
		markImage.getMarkImageIdentifier().setValue("identifier");
		markImage.setMarkImageURI(new URI("some uri"));

		ImageSpecification image = (ImageSpecification) converter.convert(
				MarkImage.class, markImage);

		assertEquals(image.getColours().get(0).getValue(), "colour");
	}

	@Test
	public void testConvert() {
		MarkImageConverter converter = new MarkImageConverter();
		MarkImage markImage = new MarkImage();
		markImage
				.setMarkImageColourClaimedText(new ArrayList<eu.ohim.sp.filing.domain.tm.Text>());
		markImage.getMarkImageColourClaimedText().add(
				new eu.ohim.sp.filing.domain.tm.Text());
		markImage.getMarkImageColourClaimedText().get(0).setLanguage("en");
		markImage.getMarkImageColourClaimedText().get(0).setValue("value");

		ColourDetailsType colourDetailsType = new ColourDetailsType();
		colourDetailsType.setColour(new ArrayList<ColourType>());
		ColourType colourType = new ColourType();
		colourType
				.setColourClaimedText(new ArrayList<eu.ohim.sp.filing.domain.tm.Text>());
		colourType.getColourClaimedText().add(new eu.ohim.sp.filing.domain.tm.Text());
		colourType.getColourClaimedText().get(0).setLanguage("en");
		colourType.getColourClaimedText().get(0).setValue("value");
		colourType.setColourCode(new ArrayList<ColourCodeType>());
		colourType.getColourCode().add(new ColourCodeType());
		colourType.getColourCode().get(0).setValue("colour");
		colourType.getColourCode().get(0)
				.setColourCodeFormat(ColourCodeFormatType.HEX);
		colourDetailsType.getColour().add(colourType);

		markImage.setMarkImageColourDetails(colourDetailsType);
		markImage.setMarkImageColourIndicator(true);
		markImage.setMarkImageColourMode("mode");
		markImage.setMarkImageFileFormat("JPEG");
		markImage.setMarkImageFilename("filename");
		markImage.setMarkImageIdentifier(new Identifier());
		markImage.getMarkImageIdentifier().setIdentifierKindCode("id");
		markImage.getMarkImageIdentifier().setValue("identifier");
		markImage.setMarkImageURI(new URI("some uri"));

		ImageSpecification image = (ImageSpecification) converter.convert(
				MarkImage.class, markImage);

		assertEquals(image.getColours().get(0).getValue(), "colour");

		ImageSpecification imageSpecification = (ImageSpecification) converter
				.convert(null, markImage, MarkImage.class,
						ImageSpecification.class);

		assertEquals(imageSpecification.getColours().get(0).getValue(),
				"colour");
	}
}
