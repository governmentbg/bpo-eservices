package eu.ohim.sp.core.domain.converter;

import static org.junit.Assert.*;

import org.junit.Test;

public class NullConverterTest {

	@Test
	public void test() {
		NullConverter nullConverter = new NullConverter();
		assertEquals(nullConverter.convert(null, null, null, null), null);
	}

}
