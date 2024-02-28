package eu.ohim.sp.core.domain.converter.ds;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.dozer.DozerBeanMapper;
import org.junit.Test;

import eu.ohim.sp.core.domain.design.ProductIndicationClass;
import eu.ohim.sp.core.domain.design.ProductIndicationTerm;
import eu.ohim.sp.filing.domain.ds.ClassDescription;
import eu.ohim.sp.filing.domain.ds.ClassNumber;
import eu.ohim.sp.filing.domain.ds.ClassificationTermDetailsType;
import eu.ohim.sp.filing.domain.ds.Text;

public class ProductIndicationToClassDescriptionConverterTest {

	private static final String PARAMETER = "sequence";
	private static final String DESCRIPTION = "description";
	private static final String MAIN_CLASS = "mainClass";
	private static final String SUB_CLASS = "subClass";
	private static final String VALUE = "value.sth";
	private static final String LANGUAGE = "en";

	@Test
	public void testConvertTo() {
		ProductIndicationToClassDescriptionConverter converter = new ProductIndicationToClassDescriptionConverter();

		DozerBeanMapper dozerBeanMapper = CommonSetup.getMapper();
		converter.setMapper(dozerBeanMapper);
		converter.setParameter(PARAMETER);

		ProductIndicationClass productIndicationClass = new ProductIndicationClass();
		productIndicationClass.setDescription(DESCRIPTION);
		productIndicationClass.setMainClass(MAIN_CLASS);
		productIndicationClass.setSubClass(SUB_CLASS);

		productIndicationClass.setTerms(new ArrayList<ProductIndicationTerm>());
		productIndicationClass.getTerms().add(new ProductIndicationTerm());

		ClassDescription classDescription = converter.convertTo(
				productIndicationClass, new ClassDescription());

		assertEquals(classDescription.getClassNumber().getValue().get(0),
				productIndicationClass.getMainClass() + "."
						+ productIndicationClass.getSubClass());

	}

	@Test
	public void testConvertFrom() {
		ProductIndicationToClassDescriptionConverter converter = new ProductIndicationToClassDescriptionConverter();

		DozerBeanMapper dozerBeanMapper = CommonSetup.getMapper();
		converter.setMapper(dozerBeanMapper);
		converter.setParameter(PARAMETER);

		ClassDescription classDescription = new ClassDescription();
		classDescription.setProductDescription(new ArrayList<Text>());
		classDescription.getProductDescription().add(new Text(VALUE, LANGUAGE));
		classDescription
				.setClassNumber(new ClassNumber(new ArrayList<String>()));
		classDescription.getClassNumber().setValue(new ArrayList<String>());
		classDescription.getClassNumber().getValue().add(VALUE);

		classDescription.setClassificationTermDetails(new ClassificationTermDetailsType());
		
		ProductIndicationClass productIndicationClass = converter.convertFrom(
				classDescription, new ProductIndicationClass());

		assertEquals(productIndicationClass.getDescription(), classDescription
				.getProductDescription().get(0).getValue());

		String classNumber = classDescription.getClassNumber().getValue()
				.get(0);
		assertEquals(productIndicationClass.getMainClass(),
				classNumber.subSequence(0, classNumber.indexOf('.')));
		assertEquals(productIndicationClass.getSubClass(),
				classNumber.subSequence(classNumber.indexOf('.') + 1,
						classNumber.length()));

	}
}
