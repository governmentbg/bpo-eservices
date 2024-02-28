package eu.ohim.sp.ptefiling.ui.adapter;

import eu.ohim.sp.common.ui.adapter.ListAttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.patent.PatentFactory;
import eu.ohim.sp.common.ui.adapter.patent.PatentViewFactory;
import eu.ohim.sp.common.ui.form.patent.PatentForm;
import eu.ohim.sp.common.ui.form.patent.PatentViewForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.domain.patent.PatentView;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Raya
 * 11.04.2019
 */
public class PatentFactoryTest {

    @Mock
    private ListAttachedDocumentFactory listAttachedDocumentFactory;

    @Mock
    private PatentViewFactory patentViewFactory;

    @InjectMocks
    private PatentFactory patentFactory;

    private PatentForm testPTForm;
    private Patent testPatent;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        testPTForm = PTFactoriesTestSource.createTestPatentForm();
        testPatent = PTFactoriesTestSource.createTestPatent();
    }

    @Test
    public void test_convertTo(){
        AttachedDocument att = PTFactoriesTestSource.createTestAttachedDocument();
        List<AttachedDocument> attachments = Arrays.asList(att);

        testPTForm.setPatentDrawings(PTFactoriesTestSource.createTestFileWrapper());
        testPTForm.setPatentClaims(PTFactoriesTestSource.createTestFileWrapper());
        testPTForm.setPatentDescriptions(PTFactoriesTestSource.createTestFileWrapper());

        PatentView view = PTFactoriesTestSource.createTestPatentView();
        testPTForm.getPatentViews().add(PTFactoriesTestSource.createTestPatentViewForm());


        when(listAttachedDocumentFactory.convertTo(testPTForm.getPatentDrawings())).thenReturn(attachments);
        when(listAttachedDocumentFactory.convertTo(testPTForm.getPatentDescriptions())).thenReturn(attachments);
        when(listAttachedDocumentFactory.convertTo(testPTForm.getPatentClaims())).thenReturn(attachments);
        when(patentViewFactory.convertTo(testPTForm.getPatentViews().get(0))).thenReturn(view);

        Patent pt = patentFactory.convertTo(testPTForm);

        assertEquals(testPTForm.getPatentTitle(), pt.getPatentTitle());
        assertEquals(testPTForm.getPatentTitleSecondLang(), pt.getPatentTitleSecondLang());
        assertEquals(testPTForm.getPatentAbstract(), pt.getPatentAbstract());
        assertEquals(testPTForm.getPatentAbstractSecondLang(), pt.getPatentAbstractSecondLang());

        assertEquals(testPTForm.getPagesCount(), pt.getPagesCount());
        assertEquals(testPTForm.getIndependentClaimsCount(), pt.getIndependentClaimsCount());
        assertEquals(testPTForm.getClaimsCount(), pt.getClaimsCount());
        assertEquals(testPTForm.getDrawingsCount(), pt.getDrawingsCount());
        assertEquals(testPTForm.getDrawingNumber(), pt.getDrawingNumber());

        assertEquals(testPTForm.getApplicationNumber(), pt.getApplicationNumber());
        assertEquals(testPTForm.getId(), pt.getApplicationNumber());
        assertEquals(testPTForm.getRegistrationNumber(), pt.getRegistrationNumber());
        assertEquals(testPTForm.getApplicationDate(), pt.getApplicationDate());
        assertEquals(testPTForm.getRegistrationDate(), pt.getRegistrationDate());
        assertEquals(testPTForm.getRegistrationPublicationDate(), pt.getRegistrationPublicationDate());
        assertEquals(testPTForm.getPatentValidated(), pt.getPatentValidated());
        assertEquals(testPTForm.getExternalReferenceNumber(), pt.getExternalReferenceNumber());
        assertEquals(testPTForm.getPatentPublicationsInfo(), pt.getPatentPublicationsInfo());

        assertEquals(att.getDocument().getDocumentId(), pt.getPatentDrawings().get(0).getDocument().getDocumentId());
        assertEquals(att.getDocument().getDocumentId(), pt.getPatentClaims().get(0).getDocument().getDocumentId());
        assertEquals(att.getDocument().getDocumentId(), pt.getPatentDescriptions().get(0).getDocument().getDocumentId());
        assertEquals(view.getSequence(), pt.getPatentViews().get(0).getSequence());

    }

    @Test
    public void test_convertFrom(){
        FileWrapper fl = PTFactoriesTestSource.createTestFileWrapper();
        testPatent.setPatentDrawings(Arrays.asList(PTFactoriesTestSource.createTestAttachedDocument()));
        testPatent.setPatentClaims(Arrays.asList(PTFactoriesTestSource.createTestAttachedDocument()));
        testPatent.setPatentDescriptions(Arrays.asList(PTFactoriesTestSource.createTestAttachedDocument()));
        testPatent.setPatentViews(Arrays.asList(PTFactoriesTestSource.createTestPatentView()));
        PatentViewForm ptViewForm = PTFactoriesTestSource.createTestPatentViewForm();

        when(patentViewFactory.convertFrom(testPatent.getPatentViews().get(0))).thenReturn(ptViewForm);
        when(listAttachedDocumentFactory.convertFrom(testPatent.getPatentClaims())).thenReturn(fl);
        when(listAttachedDocumentFactory.convertFrom(testPatent.getPatentDescriptions())).thenReturn(fl);
        when(listAttachedDocumentFactory.convertFrom(testPatent.getPatentDrawings())).thenReturn(fl);

        PatentForm ptForm = patentFactory.convertFrom(testPatent);

        assertEquals(testPatent.getPatentTitle(), ptForm.getPatentTitle());
        assertEquals(testPatent.getPatentTitleSecondLang(), ptForm.getPatentTitleSecondLang());
        assertEquals(testPatent.getPatentAbstract(), ptForm.getPatentAbstract());
        assertEquals(testPatent.getPatentAbstractSecondLang(), ptForm.getPatentAbstractSecondLang());
        assertEquals(testPatent.getClaimsCount(), ptForm.getClaimsCount());
        assertEquals(testPatent.getIndependentClaimsCount(), ptForm.getIndependentClaimsCount());
        assertEquals(testPatent.getPagesCount(), ptForm.getPagesCount());
        assertEquals(testPatent.getDrawingsCount(), ptForm.getDrawingsCount());
        assertEquals(testPatent.getDrawingNumber(), ptForm.getDrawingNumber());

        assertEquals(testPatent.getApplicationNumber(), ptForm.getApplicationNumber());
        assertEquals(testPatent.getRegistrationNumber(), ptForm.getRegistrationNumber());
        assertEquals(testPatent.getApplicationDate(), ptForm.getApplicationDate());
        assertEquals(testPatent.getRegistrationDate(), ptForm.getRegistrationDate());
        assertEquals(testPatent.getRegistrationPublicationDate(), ptForm.getRegistrationPublicationDate());
        assertEquals(testPatent.getPatentValidated(), ptForm.getPatentValidated());
        assertEquals(testPatent.getExternalReferenceNumber(), ptForm.getExternalReferenceNumber());
        assertEquals(testPatent.getPatentPublicationsInfo(), ptForm.getPatentPublicationsInfo());

        assertEquals(fl.getStoredFiles().get(0).getDocumentId(), ptForm.getPatentClaims().getStoredFiles().get(0).getDocumentId());
        assertEquals(fl.getStoredFiles().get(0).getDocumentId(), ptForm.getPatentDescriptions().getStoredFiles().get(0).getDocumentId());
        assertEquals(fl.getStoredFiles().get(0).getDocumentId(), ptForm.getPatentDrawings().getStoredFiles().get(0).getDocumentId());
    }
}
