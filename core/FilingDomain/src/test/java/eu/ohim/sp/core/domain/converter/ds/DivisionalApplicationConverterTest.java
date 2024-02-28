package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.common.Text;
import eu.ohim.sp.core.domain.design.DesignDivisionalApplicationDetails;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author ionitdi
 */
public class DivisionalApplicationConverterTest
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
        DesignDivisionalApplicationDetails core = new DesignDivisionalApplicationDetails();
        core.setComment(new Text());
        core.getComment().setValue("some info");
        core.getComment().setLanguage("EN");
        core.setInitialApplicationDate(new Date(2012, 12, 12));
        core.setInitialApplicationNumber("some number");

        // TODO: productIndications

        /// Act
        eu.ohim.sp.filing.domain.ds.DivisionalApplicationDetailsType ext = dozerBeanMapper.map(core,
                                                                                      eu.ohim.sp.filing.domain.ds.DivisionalApplicationDetailsType.class);

        /// Assert
        assertEquals("some info", ext.getComment().getValue());
        assertEquals("EN", ext.getComment().getLanguage());
        assertEquals(new Date(2012, 12, 12), ext.getInitialApplicationDate());
        assertEquals("some number", ext.getInitialApplicationNumber());
    }

    @Test
    public void testConvertToCore()
    {
        /// Arrange
        eu.ohim.sp.filing.domain.ds.DivisionalApplicationDetailsType ext = new eu.ohim.sp.filing.domain.ds.DivisionalApplicationDetailsType();
        ext.setComment(new eu.ohim.sp.filing.domain.ds.Text("some info", "EN"));
        ext.setInitialApplicationDate(new Date(2011, 11, 11));
        ext.setInitialApplicationNumber("some number");

        /// Act
        DesignDivisionalApplicationDetails core = dozerBeanMapper.map(ext, DesignDivisionalApplicationDetails.class);

        /// Assert
        assertEquals("some info", core.getComment().getValue());
        assertEquals("EN", core.getComment().getLanguage());
        assertEquals(new Date(2011, 11, 11), core.getInitialApplicationDate());
        assertEquals("some number", core.getInitialApplicationNumber());
    }
}