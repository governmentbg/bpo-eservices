package eu.ohim.sp.ui.tmefiling.adapter;

import eu.ohim.sp.common.ui.adapter.ListAttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.design.ContactDetailsFactory;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.resources.ColourForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Colour;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.*;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.form.MainForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MainFormFactoryTest {

    @Mock
    private ContactDetailsFactory contactDetailsFactory;

    @Mock
    private ListAttachedDocumentFactory listAttachedDocumentFactory;

	@InjectMocks
	private MainFormFactory mainFormFactory;

    @Test
    public void convertToTestWhenNull() {

        FlowBeanImpl flowBean = Mockito.mock(FlowBeanImpl.class);

        TradeMark trademark = mainFormFactory.convertTo(flowBean);

        assertNotNull(trademark);
        assertTrue(trademark.getMarkKind()==null);

    }

    @Test
    public void convertToTest1() {

        FlowBeanImpl flowBean = Mockito.mock(FlowBeanImpl.class);
        MainForm mainForm = Mockito.mock(MainForm.class);
        FileWrapper fileWrapper = Mockito.mock(FileWrapper.class);
        AttachedDocument attachedDocument = Mockito.mock(AttachedDocument.class);

        Mockito.when(flowBean.getMainForm()).thenReturn(mainForm);

        Mockito.when(mainForm.getMarkType()).thenReturn("figurative");
        Mockito.when(mainForm.getFileWrapperImage()).thenReturn(fileWrapper);
        Mockito.when(mainForm.getColourList()).thenReturn(null);
        Mockito.when(mainForm.getSoundFile()).thenReturn(fileWrapper);
        Mockito.when(fileWrapper.getStoredFiles()).thenReturn(Collections.emptyList());

        Mockito.when(listAttachedDocumentFactory.convertTo(fileWrapper)).thenReturn(Collections.nCopies(1, attachedDocument));

        TradeMark trademark = mainFormFactory.convertTo(flowBean);

        assertNotNull(trademark);
        assertTrue(MarkFeature.FIGURATIVE.equals(trademark.getMarkKind()));
        assertTrue(MarkKind.INDIVIDUAL.equals(trademark.getMarkRightKind()));

    }

    @Test
    public void convertToTest2() {

        FlowBeanImpl flowBean = Mockito.mock(FlowBeanImpl.class);
        MainForm mainForm = Mockito.mock(MainForm.class);
        FileWrapper fileWrapper = Mockito.mock(FileWrapper.class);
        StoredFile storedFile = Mockito.mock(StoredFile.class);
        AttachedDocument attachedDocument = Mockito.mock(AttachedDocument.class);
        ColourForm colourForm = Mockito.mock(ColourForm.class);
        ApplicationCAForm applicationCAForm = Mockito.mock(ApplicationCAForm.class);

        Mockito.when(flowBean.getMainForm()).thenReturn(mainForm);

        Mockito.when(mainForm.getMarkType()).thenReturn("wordmark");
        Mockito.when(mainForm.getFileWrapperImage()).thenReturn(fileWrapper);
        Mockito.when(mainForm.getSoundFile()).thenReturn(fileWrapper);
        Mockito.when(mainForm.getColourList()).thenReturn(Collections.nCopies(1, colourForm));
        Mockito.when(mainForm.getMarkDescription()).thenReturn("Mark description");
        Mockito.when(mainForm.getMarkDescriptionSecond()).thenReturn("Mark description second");
        Mockito.when(mainForm.getCollectiveMark()).thenReturn(true);
        Mockito.when(mainForm.getFirstDisclaimer()).thenReturn("First disclaimer");
        Mockito.when(mainForm.getSecondDisclaimer()).thenReturn("Second disclaimer");
        Mockito.when(mainForm.getCorrespondanceAddresses()).thenReturn(Collections.nCopies(1, applicationCAForm));

        Mockito.when(fileWrapper.getStoredFiles()).thenReturn(Collections.nCopies(1, storedFile));

        Mockito.when(listAttachedDocumentFactory.convertTo(fileWrapper)).thenReturn(Collections.nCopies(1, attachedDocument));

        TradeMark trademark = mainFormFactory.convertTo(flowBean);

        assertNotNull(trademark);
        assertTrue(MarkFeature.WORD.equals(trademark.getMarkKind()));
        assertTrue(MarkKind.COLLECTIVE.equals(trademark.getMarkRightKind()));

    }

    @Test
    public void convertFromTestWhenNull() {

        FlowBeanImpl flowBean = mainFormFactory.convertFrom(null);

        assertNotNull(flowBean);
        assertNotNull(flowBean.getMainForm());
        assertEquals(flowBean.getMainForm().getMarkType(), "0");

    }

    @Test
    public void convertFromTest1() {

        TradeMark tradeMark = Mockito.mock(TradeMark.class);
        Mockito.when(tradeMark.getMarkKind()).thenReturn(MarkFeature.FIGURATIVE);

        FlowBeanImpl flowBean = mainFormFactory.convertFrom(tradeMark);

        assertNotNull(flowBean);
        assertNotNull(flowBean.getMainForm());
        assertEquals(flowBean.getMainForm().getMarkType(), "figurative");

    }

    @Test
    public void convertFromTest2() {

        TradeMark tradeMark = Mockito.mock(TradeMark.class);
        MarkDescription markDescription = Mockito.mock(MarkDescription.class);
        MarkDisclaimer markDisclaimer = Mockito.mock(MarkDisclaimer.class);
        WordSpecification wordSpecification = Mockito.mock(WordSpecification.class);
        ImageSpecification imageSpecification = Mockito.mock(ImageSpecification.class);
        Colour colour = Mockito.mock(Colour.class);
        Document document = Mockito.mock(Document.class);
        SoundSpecification soundSpecification = Mockito.mock(SoundSpecification.class);
        String lang = "en";

        Mockito.when(tradeMark.getMarkDescriptions()).thenReturn(Collections.nCopies(1, markDescription));
        Mockito.when(tradeMark.getMarkDisclaimers()).thenReturn(Collections.nCopies(1, markDisclaimer));
        Mockito.when(tradeMark.getWordSpecifications()).thenReturn(Collections.nCopies(1, wordSpecification));
        Mockito.when(tradeMark.getApplicationLanguage()).thenReturn(lang);
        Mockito.when(tradeMark.getSecondLanguage()).thenReturn(lang);
        Mockito.when(tradeMark.getMarkKind()).thenReturn(MarkFeature.WORD);
        Mockito.when(tradeMark.getImageSpecifications()).thenReturn(Collections.nCopies(1, imageSpecification));
        Mockito.when(tradeMark.getSoundRepresentations()).thenReturn(Collections.nCopies(1, soundSpecification));
        Mockito.when(markDescription.getLanguage()).thenReturn(lang);
        Mockito.when(markDisclaimer.getLanguage()).thenReturn(lang);
        Mockito.when(imageSpecification.getColours()).thenReturn(Collections.nCopies(1, colour));
        Mockito.when(imageSpecification.getRepresentation()).thenReturn(document);
        Mockito.when(soundSpecification.getDocument()).thenReturn(document);
        Mockito.when(document.getFileFormat()).thenReturn("jpg");

        FlowBeanImpl flowBean = mainFormFactory.convertFrom(tradeMark);

        assertNotNull(flowBean);
        assertNotNull(flowBean.getMainForm());
        assertEquals(flowBean.getMainForm().getMarkType(), "wordmark");

    }
}
