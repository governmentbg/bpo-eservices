package eu.ohim.sp.core.domain.converter.tm;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MarkCurrentStatusConverterTest {

	private static final String VALUE = "value";
	private static final String SOME = "some";

	@Test
	public void testConvertToMarkCurrentStatusCode() {
		MarkCurrentStatusConverter converter = new MarkCurrentStatusConverter();
		@SuppressWarnings("unchecked")
		List<String> markCurrentStatusCode = (List<String>) converter.convert(
				String.class, VALUE);
		assertEquals(markCurrentStatusCode.size(), 1);
		assertEquals(markCurrentStatusCode.get(0), VALUE);

	}

	@Test
	public void testConvertToString() {
		MarkCurrentStatusConverter converter = new MarkCurrentStatusConverter();
		List<String> markCurrentStatusCode = new ArrayList<String>();
		markCurrentStatusCode.add(VALUE);
		markCurrentStatusCode.add(SOME);
		assertEquals(converter.convert(String.class, markCurrentStatusCode),
				VALUE + " " + SOME);
	}

	@Test
	public void testConvert() {
		MarkCurrentStatusConverter converter = new MarkCurrentStatusConverter();
		@SuppressWarnings("unchecked")
		List<String> markCurrentStatusCode = (List<String>) converter.convert(
				null, VALUE, String.class, null);
		assertEquals(markCurrentStatusCode.size(), 1);
		assertEquals(markCurrentStatusCode.get(0), VALUE);
	}

	@Test
	public void testConvertError() {
		MarkCurrentStatusConverter converter = new MarkCurrentStatusConverter();
		assertEquals(converter.convert(BigInteger.class, BigInteger.ONE), null);
	}

}
