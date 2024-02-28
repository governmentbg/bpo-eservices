package eu.ohim.sp.ptefiling.ui.adapter;

import eu.ohim.sp.common.ui.adapter.AttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.DocumentFactory;
import eu.ohim.sp.common.ui.adapter.ListAttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.patent.PatentViewFactory;
import eu.ohim.sp.common.ui.form.patent.PatentViewForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.domain.patent.PatentView;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Raya
 * 14.11.2018
 */
public class PatentViewFactoryTest {

    @Mock
    private AttachedDocumentFactory fileFactory;

    @Mock
    private ListAttachedDocumentFactory listAttachedDocumentFactory;

    @Mock
    private DocumentFactory documentFactory;

    @InjectMocks
    private PatentViewFactory patentViewFactory;

    private PatentViewForm testViewForm;

    private PatentView testView;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        testViewForm = PTFactoriesTestSource.createTestPatentViewForm();
        testView = PTFactoriesTestSource.createTestPatentView();
    }

    @Test
    public void test_convertTo(){
        AttachedDocument att = PTFactoriesTestSource.createTestAttachedDocument();
        testViewForm.setView(PTFactoriesTestSource.createTestFileWrapper());

        when(fileFactory.convertTo(testViewForm.getView().getStoredFiles().get(0))).thenReturn(att);
        when(fileFactory.convertTo(testViewForm.getView().getStoredFiles().get(0).getThumbnail())).thenReturn(att);

        PatentView vw = patentViewFactory.convertTo(testViewForm);
        assertEquals(testViewForm.getDescription(), vw.getDescription());
        assertEquals(testViewForm.getPublicationSize(), vw.getPublicationSize());
        assertEquals(testViewForm.getType(), vw.getType());
        assertEquals(testViewForm.getSequence(), vw.getSequence());
        assertEquals(att.getDocument().getDocumentId(), vw.getView().getDocument().getDocumentId());
        assertEquals(att.getDocument().getDocumentId(), vw.getViewThumbnail().getDocument().getDocumentId());
    }

    @Test
    public void test_convertFrom(){
        FileWrapper fl = PTFactoriesTestSource.createTestFileWrapper();
        testView.setViewThumbnail(PTFactoriesTestSource.createTestAttachedDocument());
        testView.setView(PTFactoriesTestSource.createTestAttachedDocument());
        when(listAttachedDocumentFactory.convertFrom(Arrays.asList(testView.getView()))).thenReturn(fl);

        when(documentFactory.convertFrom(testView.getViewThumbnail().getDocument())).thenReturn(fl.getStoredFiles().get(0));

        PatentViewForm viewForm = patentViewFactory.convertFrom(testView);
        assertEquals(testView.getDescription(), viewForm.getDescription());
        assertEquals(testView.getPublicationSize(), viewForm.getPublicationSize());
        assertEquals(testView.getType(), viewForm.getType());
        assertEquals(testView.getSequence(), viewForm.getSequence());
        assertEquals(fl.getStoredFiles().get(0).getDocumentId(), viewForm.getView().getStoredFiles().get(0).getDocumentId());
        assertEquals(fl.getStoredFiles().get(0).getThumbnail(), viewForm.getView().getStoredFiles().get(0).getThumbnail());
    }


}
