package eu.ohim.sp.ptefiling.ui.adapter;

import eu.ohim.sp.common.ui.adapter.ListAttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.patent.PTPriorityFactory;
import eu.ohim.sp.common.ui.form.patent.PTPriorityForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.domain.patent.PatentPriority;
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
 * 11.04.2019
 */
public class PTPriorityFactoryTest {

    @Mock
    private ListAttachedDocumentFactory listAttachedDocumentFactory;

    @InjectMocks
    private PTPriorityFactory ptPriorityFactory;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_convertTo(){
        PTPriorityForm form = PTFactoriesTestSource.createTestPTPriorityForm();
        form.setFilePriorityCertificate(PTFactoriesTestSource.createTestFileWrapper());
        AttachedDocument doc = PTFactoriesTestSource.createTestAttachedDocument();
        when(listAttachedDocumentFactory.convertTo(form.getFilePriorityCertificate())).
            thenReturn(Arrays.asList(doc));

        PatentPriority core = ptPriorityFactory.convertTo(form);

        assertEquals(form.getCountry(), core.getFilingOffice());
        assertEquals(form.getNumber(), core.getFilingNumber());
        assertEquals(form.getDate(), core.getFilingDate());
        assertEquals(doc.getDocument().getDocumentId(), core.getAttachedDocuments().get(0).getDocument().getDocumentId());

    }

    @Test
    public void test_convertFrom(){
        PatentPriority core = PTFactoriesTestSource.createTestPTPriority();
        core.setAttachedDocuments(Arrays.asList(PTFactoriesTestSource.createTestAttachedDocument()));
        FileWrapper testWr = PTFactoriesTestSource.createTestFileWrapper();
        when(listAttachedDocumentFactory.convertFrom(core.getAttachedDocuments())).
            thenReturn(testWr);
        PTPriorityForm form = ptPriorityFactory.convertFrom(core);

        assertEquals(core.getFilingOffice(), form.getCountry());
        assertEquals(core.getFilingNumber(), form.getNumber());
        assertEquals(core.getFilingDate(), form.getDate());
        assertEquals(testWr.getStoredFiles().get(0).getDocumentId(), form.getFilePriorityCertificate().getStoredFiles().get(0).getDocumentId());
    }
}
