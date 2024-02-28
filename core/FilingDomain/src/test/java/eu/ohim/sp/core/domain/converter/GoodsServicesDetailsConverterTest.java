package eu.ohim.sp.core.domain.converter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.filing.domain.tm.ClassDescriptionDetails;
import eu.ohim.sp.filing.domain.tm.ClassNumber;
import eu.ohim.sp.filing.domain.tm.ClassificationTermDetails;
import eu.ohim.sp.filing.domain.tm.ClassificationTermType;
import eu.ohim.sp.filing.domain.tm.GoodsServices;
import eu.ohim.sp.filing.domain.tm.GoodsServicesDetails;
import eu.ohim.sp.filing.domain.tm.Text;

public class GoodsServicesDetailsConverterTest {

	private static final String CLASS_NUMBER = "classNumber";
	private static final String VALUE = "value";
	private static final String VERSION = "version";
	private static final String LANGUAGE = "en";

	@Test
	public void testConvertString() {
		GoodsServicesDetailsConverter converter = new GoodsServicesDetailsConverter();
		assertEquals(converter.convert(String.class, CLASS_NUMBER), null);
	}

	@Test
	public void testConvert() {
		GoodsServicesDetailsConverter converter = new GoodsServicesDetailsConverter();
		List<ClassDescription> classDescriptionList = new ArrayList<ClassDescription>();

		GoodsServicesDetails goodsServicesDetails = (GoodsServicesDetails) converter
				.convert(GoodsServicesDetails.class, classDescriptionList);

		assertEquals(goodsServicesDetails.getGoodsServices().size(), 1);
	}

	@Test
	public void testFspGoodsServicestToCoreGoodsServices() {
		GoodsServicesDetailsConverter converter = new GoodsServicesDetailsConverter();
		GoodsServices fsp = new GoodsServices();
		fsp.setClassDescriptionDetails(new ClassDescriptionDetails());
		List<eu.ohim.sp.filing.domain.tm.ClassDescription> classDescriptionList = new ArrayList<eu.ohim.sp.filing.domain.tm.ClassDescription>();
		fsp.getClassDescriptionDetails().setClassDescription(
				classDescriptionList);
		fsp.getClassDescriptionDetails()
				.getClassDescription()
				.add(new eu.ohim.sp.filing.domain.tm.ClassDescription(new ClassNumber(
						VALUE), CLASS_NUMBER, new ArrayList<Text>(),
						Boolean.TRUE,
						new ArrayList<ClassificationTermDetails>(),
						Boolean.FALSE));
		fsp.getClassDescriptionDetails()
				.getClassDescription()
				.get(0)
				.setClassificationTerms(
						new ArrayList<ClassificationTermDetails>());
		fsp.getClassDescriptionDetails()
				.getClassDescription()
				.get(0)
				.getClassificationTerms()
				.add(new ClassificationTermDetails(
						new ArrayList<ClassificationTermType>(),
						eu.ohim.sp.filing.domain.tm.ISOLanguageCode.AA));
		fsp.setClassificationKindCode(VALUE);
		fsp.setClassificationVersion(VERSION);
		fsp.setComment(new Text(VALUE, LANGUAGE));
		fsp.setProposedLeadingClassNumber(new ClassNumber(CLASS_NUMBER));

		converter.fspGoodsServicestToCoreGoodsServices(fsp);
	}

}
