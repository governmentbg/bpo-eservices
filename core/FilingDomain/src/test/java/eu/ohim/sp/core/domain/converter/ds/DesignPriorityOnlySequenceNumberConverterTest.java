package eu.ohim.sp.core.domain.converter.ds;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.dozer.DozerBeanMapper;
import org.junit.Test;

import eu.ohim.sp.core.domain.design.Priority;

public class DesignPriorityOnlySequenceNumberConverterTest {

	private static final int SEQUENCE_NUMBER = 5;
	private static final String PARAMETER = "";

	@Test
	public void testConvertTo() {
		DesignPriorityOnlySequenceNumberConverter converter = new DesignPriorityOnlySequenceNumberConverter();

		DozerBeanMapper dozerBeanMapper = CommonSetup.getMapper();
		converter.setMapper(dozerBeanMapper);
		converter.setParameter(PARAMETER);

		Priority core = new Priority();
		core.setSequenceNumber(SEQUENCE_NUMBER);

		eu.ohim.sp.filing.domain.ds.Priority ext = converter.convertTo(core,
				new eu.ohim.sp.filing.domain.ds.Priority());
		assertEquals(ext.getPrioritySequenceNumber(), null);
	}

	@Test
	public void testConvertFrom() {
		DesignPriorityOnlySequenceNumberConverter converter = new DesignPriorityOnlySequenceNumberConverter();

		DozerBeanMapper dozerBeanMapper = CommonSetup.getMapper();
		converter.setMapper(dozerBeanMapper);
		converter.setParameter(PARAMETER);

		eu.ohim.sp.filing.domain.ds.Priority ext = new eu.ohim.sp.filing.domain.ds.Priority();
		ext.setPrioritySequenceNumber(BigInteger.TEN);
		Priority core = converter.convertFrom(ext, new Priority());
		assertEquals(core.getSequenceNumber(), BigInteger.TEN.intValue());
	}

}
