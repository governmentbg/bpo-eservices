package eu.ohim.sp.core.domain.resources;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class ColourTest {

	private static final String FORMAT = "format";
	private static final String VALUE = "red";
	
	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Colour.class);
	}

	@Test
	public void testConstructor() {
		Colour colour = new Colour(FORMAT, VALUE);
		assertEquals(colour.getFormat(), FORMAT);
		assertEquals(colour.getValue(), VALUE);
	}
	
}
