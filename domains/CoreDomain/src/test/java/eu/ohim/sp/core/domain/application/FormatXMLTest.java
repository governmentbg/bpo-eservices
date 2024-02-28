package eu.ohim.sp.core.domain.application;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class FormatXMLTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(FormatXML.class);
	}

	@Test
	public void testValues() {
		for (FormatXML xml : FormatXML.values()) {
			FormatXML formatXML = xml;
			assertEquals(formatXML.value(), xml.value());
		}
	}
}
