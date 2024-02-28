package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.person.PersonRoleKind;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * @author ionitdi
 */
public class SignatureConverterTest
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
        Signatory core = new Signatory();
        core.setCapacity(PersonRoleKind.APPLICANT);
        core.setDate(new Date(2012, 11, 11));
        core.setPlace("some place");
        core.setName("some name");
        core.setUserId("user id");
        core.setAssociatedText("assoc text");


        /// Act
        eu.ohim.sp.filing.domain.ds.Signatory ext = dozerBeanMapper.map(core, eu.ohim.sp.filing.domain.ds.Signatory.class);


        /// Assert
        assertEquals("some place", ext.getPlace());
        assertEquals(new Date(2012, 11, 11), ext.getDate());
        assertEquals(eu.ohim.sp.filing.domain.ds.Role.APPLICANT, ext.getCapacity());
        assertEquals("some name",
                     ext.getName().getFreeFormatName().getFreeFormatNameDetails().getFreeFormatNameLine().get(0).getValue());
        assertEquals("user id", ext.getSignatoryUserId());
        assertEquals("assoc text", ext.getSignatureAssociatedText());
    }

    @Test
    public void testConvertToCore()
    {
        /// Arrange
        eu.ohim.sp.filing.domain.ds.Signatory ext = new eu.ohim.sp.filing.domain.ds.Signatory();
        ext.setCapacity(eu.ohim.sp.filing.domain.ds.Role.APPLICANT);
        ext.setDate(new Date(2012, 11, 11));
        // ext.setDocumentTrueCopyConfirmation(Boolean.TRUE);
        ext.setPlace("some place");

        // build name
        eu.ohim.sp.filing.domain.ds.Name name = new eu.ohim.sp.filing.domain.ds.Name();
        name.setFreeFormatName(new eu.ohim.sp.filing.domain.ds.FreeFormatNameType());
        name.getFreeFormatName().setFreeFormatNameDetails(new eu.ohim.sp.filing.domain.ds.FreeFormatNameDetailsType());
        name.getFreeFormatName().getFreeFormatNameDetails().setFreeFormatNameLine(new ArrayList<eu.ohim.sp.filing.domain.ds.Text>());
        name.getFreeFormatName().getFreeFormatNameDetails().getFreeFormatNameLine().add(
                new eu.ohim.sp.filing.domain.ds.Text("some name", null));
        ext.setName(name);


        /// Act
        Signatory core = dozerBeanMapper.map(ext, Signatory.class);


        /// Assert
        assertEquals("some place", core.getPlace());
        assertEquals(new Date(2012, 11, 11), core.getDate());
        assertEquals(PersonRoleKind.APPLICANT, core.getCapacity());
        assertEquals("some name", core.getName());
    }
}
