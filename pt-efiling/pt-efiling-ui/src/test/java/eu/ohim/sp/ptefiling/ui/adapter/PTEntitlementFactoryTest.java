package eu.ohim.sp.ptefiling.ui.adapter;

import eu.ohim.sp.common.ui.adapter.ListAttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.patent.PTEntitlementFactory;
import eu.ohim.sp.common.ui.form.patent.PTEntitlementForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.domain.application.Entitlement;
import eu.ohim.sp.core.domain.application.EntitlementKind;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Raya
 * 11.04.2019
 */
public class PTEntitlementFactoryTest {
    @Mock
    private ListAttachedDocumentFactory listAttachedDocumentFactory;

    @InjectMocks
    private PTEntitlementFactory ptEntitlementFactory;

    private PTEntitlementForm testFormTransfer;
    private Entitlement testCoreTransfer;

    private PTEntitlementForm testFormOther;
    private Entitlement testCoreOther;

    private PTEntitlementForm testFormOffic;
    private Entitlement testCoreOffic;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        testCoreTransfer = PTFactoriesTestSource.createTestEntitlementTransfer();
        testCoreOther = PTFactoriesTestSource.createTestEntitlementOther();
        testCoreOffic = PTFactoriesTestSource.createTestEntitlementOffic();

        testFormTransfer = PTFactoriesTestSource.createTestPTEntitlementFormTransfer();
        testFormOther = PTFactoriesTestSource.createTestPTEntitlementFormOther();
        testFormOffic = PTFactoriesTestSource.createTestPTEntitlementFormOffic();
    }

    @Test
    public void test_convertTo(){
        AttachedDocument att = PTFactoriesTestSource.createTestAttachedDocument();
        List<AttachedDocument> attachments = Arrays.asList(att);

        testFormTransfer.setTransferContractFiles(PTFactoriesTestSource.createTestFileWrapper());
        testFormOffic.setTransferContractFiles(PTFactoriesTestSource.createTestFileWrapper());
        Mockito.when(listAttachedDocumentFactory.convertTo(testFormTransfer.getTransferContractFiles())).thenReturn(attachments);
        Mockito.when(listAttachedDocumentFactory.convertTo(testFormOffic.getTransferContractFiles())).thenReturn(attachments);

        Entitlement result1 = ptEntitlementFactory.convertTo(testFormTransfer);
        Entitlement result2 = ptEntitlementFactory.convertTo(testFormOther);
        Entitlement result3 = ptEntitlementFactory.convertTo(testFormOffic);
        //1
        assertEquals(EntitlementKind.TRANSFER_OF_RIGHTS, result1.getKind());
        assertEquals(att.getDocument().getDocumentId(), result1.getAttachedDocuments().get(0).getDocument().getDocumentId());

        //2
        assertEquals(testFormOther.getOtherGroundsDescription(), result2.getDescription());
        assertEquals(EntitlementKind.OTHER_GROUNDS, result2.getKind());

        //3
        assertEquals(EntitlementKind.PATENT_IS_OFFICIARY, result3.getKind());
        assertEquals(att.getDocument().getDocumentId(), result1.getAttachedDocuments().get(0).getDocument().getDocumentId());
    }

    @Test
    public void test_convertFrom(){
        AttachedDocument att = PTFactoriesTestSource.createTestAttachedDocument();
        List<AttachedDocument> attachments = Arrays.asList(att);

        FileWrapper fl = PTFactoriesTestSource.createTestFileWrapper();

        testCoreTransfer.setAttachedDocuments(attachments);
        testCoreOffic.setAttachedDocuments(attachments);

        Mockito.when(listAttachedDocumentFactory.convertFrom(testCoreTransfer.getAttachedDocuments())).thenReturn(fl);
        Mockito.when(listAttachedDocumentFactory.convertFrom(testCoreOffic.getAttachedDocuments())).thenReturn(fl);

        PTEntitlementForm result1 = ptEntitlementFactory.convertFrom(testCoreTransfer);
        PTEntitlementForm result2 = ptEntitlementFactory.convertFrom(testCoreOther);
        PTEntitlementForm result3 = ptEntitlementFactory.convertFrom(testCoreOffic);

        //1
        assertEquals(true, result1.isTransferContract());
        assertEquals(false, result1.isPatentOfficiary());
        assertEquals(false, result1.isPatentNotOfficiary());
        assertEquals(false, result1.isOtherGrounds());
        assertEquals(fl.getStoredFiles().get(0).getDocumentId(), result1.getTransferContractFiles().getStoredFiles().get(0).getDocumentId());
        //2
        assertEquals(false, result2.isTransferContract());
        assertEquals(false, result2.isPatentOfficiary());
        assertEquals(false, result2.isPatentNotOfficiary());
        assertEquals(true, result2.isOtherGrounds());
        assertEquals(testCoreOther.getDescription(), result2.getOtherGroundsDescription());

        //3
        assertEquals(false, result3.isTransferContract());
        assertEquals(true, result3.isPatentOfficiary());
        assertEquals(false, result3.isPatentNotOfficiary());
        assertEquals(false, result3.isOtherGrounds());
        assertEquals(fl.getStoredFiles().get(0).getDocumentId(), result3.getPatentOfficiaryFiles().getStoredFiles().get(0).getDocumentId());

    }
}
