package eu.ohim.sp.core.domain.converter.ds;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.dozer.MappingException;
import org.junit.Test;

import eu.ohim.sp.core.domain.design.Priority;

public class DesignPriorityConverterTest {

	private static final String PARAMETER = "sequence";
	
	@Test
	public void testConvert() {
		DesignPriorityConverter converter = new DesignPriorityConverter();
		converter.setParameter(PARAMETER);
		List<Priority> priorityList = new ArrayList<Priority>();
		Priority priority = new Priority();
		priority.setSequenceNumber(5);
		priorityList.add(priority);

		List<eu.ohim.sp.filing.domain.ds.Priority> ext = (List<eu.ohim.sp.filing.domain.ds.Priority>) converter
				.convert(Priority.class, priorityList);
		
		assertEquals(priority.getSequenceNumber(), ext.get(0).getPrioritySequenceNumber().intValue());
	}

	@Test(expected = MappingException.class)
	public void testConvertExceptionExpected() {
		DesignPriorityConverter converter = new DesignPriorityConverter();
		converter.convert(String.class, null);
	}

	@Test
	public void testConvertNullExpected() {
		DesignPriorityConverter converter = new DesignPriorityConverter();
		List<String> list = new ArrayList<String>();
		assertEquals(converter.convert(String.class, list), null);
	}

	@Test
	public void testConvertNullExpected2() {
		DesignPriorityConverter converter = new DesignPriorityConverter();
		List<String> list = new ArrayList<String>();
		list.add("some value");
		assertEquals(converter.convert(String.class, list), null);
	}

}
