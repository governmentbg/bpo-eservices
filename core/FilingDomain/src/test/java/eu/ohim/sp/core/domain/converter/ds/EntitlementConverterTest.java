package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.application.Entitlement;
import eu.ohim.sp.core.domain.application.EntitlementKind;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.filing.domain.ds.EntitlementToApplyType;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author ionitdi
 */
public class EntitlementConverterTest
{
    DozerBeanMapper dozerBeanMapper;

    @Before
    public void setUp()
    {
        dozerBeanMapper = CommonSetup.getMapper();
    }

    @Test
    public void testConvertToFSP()
    {
        Entitlement core = new Entitlement();
        core.setDateOfTransfer(new Date(2011, 11, 11));
        core.setDescription("desc");
        core.setKind(EntitlementKind.ACCORDING_TO_ASSOCIATION_TO_COMPANY);
        core.setAttachedDocuments(new ArrayList<AttachedDocument>());
        AttachedDocument doc = new AttachedDocument();
        doc.setDocument(new Document());
        doc.getDocument().setDocumentId("some id");
        core.getAttachedDocuments().add(doc);

        EntitlementToApplyType ext = dozerBeanMapper.map(core, EntitlementToApplyType.class);

        assertEquals(11, ext.getEntitlementTransferDate().getDay());
        assertEquals("desc", ext.getEntitlementKindText());
        assertEquals("According to association of a company", ext.getEntitlementKindCode());
        assertEquals("some id", ext.getEntitlementDocument().get(0).getDocumentId());
    }

    @Test
    public void testConvertToCore()
    {
        EntitlementToApplyType ext = new EntitlementToApplyType();
        ext.setEntitlementKindCode("Transfer of rights");
        ext.setEntitlementKindText("description");

        Entitlement core = dozerBeanMapper.map(ext, Entitlement.class);
        assertEquals(EntitlementKind.TRANSFER_OF_RIGHTS, core.getKind());
        assertEquals("description", core.getDescription());
    }

}
