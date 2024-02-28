package eu.ohim.sp.core.domain.converter;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

public class XMLGregorianConverterTest {

	@Test
	public void testConvertToXMLGregorianCalendar() {
		XMLGregorianConverter converter = new XMLGregorianConverter();
		XMLGregorianCalendar xmlGregorianCalendar = (XMLGregorianCalendar) converter
				.convert(XMLGregorianCalendar.class, new Date());
		assertEquals(xmlGregorianCalendar.getYear(), Calendar.getInstance()
				.get(Calendar.YEAR));
	}

	@Test
	public void testConvertToDate() throws DatatypeConfigurationException {
		XMLGregorianConverter converter = new XMLGregorianConverter();
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(new Date());
		XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory
				.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		Date date = (Date) converter.convert(Date.class, xmlGregorianCalendar);
		assertEquals(date, gregorianCalendar.getTime());
	}
	
	@Test
	public void testConvertToDate2() throws DatatypeConfigurationException {
		XMLGregorianConverter converter = new XMLGregorianConverter();
		Date actualDate = new Date();
		Date date = (Date) converter.convert(Date.class, actualDate);
		assertEquals(date, actualDate);
	}

	@Test
	public void testConvertError() {
		XMLGregorianConverter converter = new XMLGregorianConverter();
		assertEquals(converter.convert(String.class, new Date()), null);
	}

}
