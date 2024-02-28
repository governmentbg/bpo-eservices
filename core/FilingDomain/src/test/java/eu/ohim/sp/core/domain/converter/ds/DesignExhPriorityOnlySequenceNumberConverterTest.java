package eu.ohim.sp.core.domain.converter.ds;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import eu.ohim.sp.core.domain.design.ExhibitionPriority;

public class DesignExhPriorityOnlySequenceNumberConverterTest {

	private static final DesignExhPriorityOnlySequenceNumberConverter CONVERTER = new DesignExhPriorityOnlySequenceNumberConverter();

	private static final int SEQUENCE_NUMBER = 10;

	@Test
	public void testConvertTo() {
		ExhibitionPriority core = new ExhibitionPriority();
		core.setSequenceNumber(SEQUENCE_NUMBER);
		eu.ohim.sp.filing.domain.ds.ExhibitionPriority ext = CONVERTER.convertTo(core,
				new eu.ohim.sp.filing.domain.ds.ExhibitionPriority());
		assertEquals(core.getSequenceNumber(),
				ext.getExhibitionPrioritySequenceNumber().intValue());
	}
	
	@Test
	public void testConvertFrom() {
		eu.ohim.sp.filing.domain.ds.ExhibitionPriority ext = new eu.ohim.sp.filing.domain.ds.ExhibitionPriority();
		ext.setExhibitionPrioritySequenceNumber(BigInteger.TEN);
		ExhibitionPriority core = CONVERTER.convertFrom(ext, new ExhibitionPriority());
		assertEquals(core.getSequenceNumber(),
				ext.getExhibitionPrioritySequenceNumber().intValue());
	}

}
