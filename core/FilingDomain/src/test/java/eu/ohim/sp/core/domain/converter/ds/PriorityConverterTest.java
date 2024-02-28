package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.design.Priority;
import eu.ohim.sp.filing.domain.ds.ExtendedWIPOST3Code;
import eu.ohim.sp.filing.domain.ds.Text;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;


public class PriorityConverterTest {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private DozerBeanMapper dozerBeanMapper;

	@Before
	public void setUp() throws Exception {
		dozerBeanMapper = CommonSetup.getMapper();
	}

	@Test
	public void testConvertToFSP() throws Exception {
		/// Arrange
		Priority core = new Priority();
		core.setFilingDate(DATE_FORMAT.parse("2012-12-12"));
		core.setFilingNumber("some number");
		core.setFilingOffice("ES");
		core.setApplicantName("some name");

		core.setSequenceNumber(123);
		core.setInternal(true);

		// TODO : attached files

		/// Act
		eu.ohim.sp.filing.domain.ds.Priority ext = dozerBeanMapper.map(core, eu.ohim.sp.filing.domain.ds.Priority.class, "fullPriority");
		ext.setInternalPriorityIndicator(true);

		/// Assert
		assertEquals(DATE_FORMAT.parse("2012-12-12"), ext.getFilingDate());
		assertEquals("some number", ext.getFilingNumber());
		assertEquals("ES", ext.getFilingOffice().getValue());
		assertEquals(123, ext.getPrioritySequenceNumber().intValue());
		assertEquals("some name", ext.getPriorityHolderName().getValue());
		assertEquals(true, ext.isInternalPriorityIndicator());
	}


	@Test
	public void testConvertToCore() throws Exception {
		/// Arrange
		eu.ohim.sp.filing.domain.ds.Priority ext = new eu.ohim.sp.filing.domain.ds.Priority();

		ext.setFilingDate(DATE_FORMAT.parse("2011-11-11"));
		ext.setFilingNumber("filing number");
		ext.setFilingOffice(new ExtendedWIPOST3Code("FR"));
		ext.setPriorityHolderName(new Text("name of holder", null));
		ext.setPrioritySequenceNumber(new BigInteger("234"));

		// TODO : attached files

		/// Act
		Priority core = dozerBeanMapper.map(ext, Priority.class, "fullPriority");

		/// Assert
		assertEquals(DATE_FORMAT.parse("2011-11-11"), core.getFilingDate());
		assertEquals("filing number", core.getFilingNumber());
		assertEquals("FR", core.getFilingOffice());
		assertEquals("name of holder", core.getApplicantName());
		assertEquals(234, core.getSequenceNumber());
		//assertEquals("type", core.getType());
	}

}
