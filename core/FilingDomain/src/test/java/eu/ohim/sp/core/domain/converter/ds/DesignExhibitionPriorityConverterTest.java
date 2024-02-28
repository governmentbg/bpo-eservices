package eu.ohim.sp.core.domain.converter.ds;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;
import org.junit.Test;

import eu.ohim.sp.core.domain.design.ExhibitionPriority;

public class DesignExhibitionPriorityConverterTest {

	private static final String PARAMETER = "sequence";

	@Test
	public void testConvertExhibitionPriority() {
		DesignExhibitionPriorityConverter converter = new DesignExhibitionPriorityConverter();
		DozerBeanMapper dozerBeanMapper = CommonSetup.getMapper();
		converter.setMapper(dozerBeanMapper);
		converter.setParameter(PARAMETER);

		List<ExhibitionPriority> exhibitionList = new ArrayList<ExhibitionPriority>();
		ExhibitionPriority exhPriority = new ExhibitionPriority();
		Date date = new Date();
		exhPriority.setSequenceNumber(10);
		exhibitionList.add(exhPriority);

		List<eu.ohim.sp.filing.domain.ds.ExhibitionPriority> ext = (List<eu.ohim.sp.filing.domain.ds.ExhibitionPriority>) converter
				.convert(String.class, exhibitionList);

		assertEquals(ext.get(0).getExhibitionPrioritySequenceNumber(), BigInteger.TEN);
	}

	@Test
	public void testConvertExhibitionPriorityZeroSize() {
		DesignExhibitionPriorityConverter converter = new DesignExhibitionPriorityConverter();
		DozerBeanMapper dozerBeanMapper = CommonSetup.getMapper();
		converter.setMapper(dozerBeanMapper);
		converter.setParameter(PARAMETER);

		List<ExhibitionPriority> exhibitionList = new ArrayList<ExhibitionPriority>();

		assertEquals(converter.convert(String.class, exhibitionList), null);
	}

	@Test(expected = MappingException.class)
	public void testConvertExceptedException() {
		DesignExhibitionPriorityConverter converter = new DesignExhibitionPriorityConverter();
		DozerBeanMapper dozerBeanMapper = CommonSetup.getMapper();
		converter.setMapper(dozerBeanMapper);
		converter.setParameter(PARAMETER);

		converter.convert(String.class, null);
	}

	@Test
	public void testConvertExceptedNull() {
		DesignExhibitionPriorityConverter converter = new DesignExhibitionPriorityConverter();
		DozerBeanMapper dozerBeanMapper = CommonSetup.getMapper();
		converter.setMapper(dozerBeanMapper);
		converter.setParameter(PARAMETER);

		assertEquals(converter.convert(String.class, new ArrayList<String>()),
				null);
	}

}
