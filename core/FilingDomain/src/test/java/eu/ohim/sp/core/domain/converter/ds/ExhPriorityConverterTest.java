package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.claim.Exhibition;
import eu.ohim.sp.core.domain.design.ExhibitionPriority;
import eu.ohim.sp.filing.domain.ds.ISOCountryCode;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author ionitdi
 */
public class ExhPriorityConverterTest
{
    DozerBeanMapper dozerBeanMapper;

    @Before
    public void setUp() throws Exception
    {
        dozerBeanMapper = CommonSetup.getMapper();
    }

    @Test
    public void testConvertToFSP()
    {
        /// Arrange
        ExhibitionPriority core = new ExhibitionPriority();
        core.setFirstDisplayDate(new Date(2012, 12, 12));
        core.setExhibition(new Exhibition());
        core.getExhibition().setCity("some city");
        core.getExhibition().setName("some name");
        core.getExhibition().setCountry("DE");
        core.getExhibition().setOpeningDate(new Date(2012, 11, 11));
        core.setSequenceNumber(123);

        // TODO : attached files

        /// Act
        eu.ohim.sp.filing.domain.ds.ExhibitionPriority ext = dozerBeanMapper.map(core, eu.ohim.sp.filing.domain.ds.ExhibitionPriority.class, "fullExhibitionPriority");

        /// Assert
        assertEquals(new Date(2012, 12, 12), ext.getFirstDisplayDate());
        assertEquals("some city", ext.getCity());
        assertEquals("some name", ext.getName());
        assertEquals("DE", ext.getCountry().value());
        assertEquals(123, ext.getExhibitionPrioritySequenceNumber().intValue());
        assertEquals(new Date(2012, 11, 11), ext.getDate());
    }


    @Test
    public void testConvertToCore()
    {
        /// Arrange
        eu.ohim.sp.filing.domain.ds.ExhibitionPriority ext = new eu.ohim.sp.filing.domain.ds.ExhibitionPriority();
        ext.setFirstDisplayDate(new Date(2011, 11, 11));
        ext.setCity("a city");
        ext.setName("a name");
        ext.setCountry(ISOCountryCode.LA);
        ext.setDate(new Date(2010, 10, 10));
        ext.setExhibitionPrioritySequenceNumber(new BigInteger("456"));

        // TODO : attached files

        /// Act
        ExhibitionPriority core = dozerBeanMapper.map(ext, ExhibitionPriority.class, "fullExhibitionPriority");

        /// Assert
        assertEquals(new Date(2011, 11, 11), core.getFirstDisplayDate());
        assertEquals("a city", core.getExhibition().getCity());
        assertEquals("a name", core.getExhibition().getName());
        assertEquals("LA", core.getExhibition().getCountry());
        assertEquals(new Date(2010, 10, 10), core.getExhibition().getOpeningDate());
        assertEquals(456, core.getSequenceNumber());
    }
}
