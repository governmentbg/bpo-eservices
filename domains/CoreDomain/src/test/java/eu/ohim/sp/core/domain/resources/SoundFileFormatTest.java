package eu.ohim.sp.core.domain.resources;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class SoundFileFormatTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(SoundFileFormat.class);
	}

	@Test
	public void testValues() {
		for (SoundFileFormat format : SoundFileFormat.values()) {
			SoundFileFormat soundFileFormat = format;
			assertEquals(soundFileFormat.value(), format.value());
			assertEquals(soundFileFormat.toString(), format.toString());
		}
	}

}
