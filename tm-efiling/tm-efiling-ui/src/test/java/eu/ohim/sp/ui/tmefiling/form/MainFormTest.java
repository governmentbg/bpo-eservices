package eu.ohim.sp.ui.tmefiling.form;

import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.claim.TransformationForm;
import eu.ohim.sp.common.ui.form.resources.ColourForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.service.constant.AppConstants;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;


@RunWith(JUnitParamsRunner.class)
public class MainFormTest {

	
	public static final Object[] getStrings() {
		return new String[] { null, "", " ", "   ", "qwerqwerqewrqwer"," 234123412341234123 " };
	}
	
	
	@Test
	@Parameters(method = "getStrings")
	public void getterSertterTestStringFields(String param) {
		MainForm mainForm = new MainForm();
		
		mainForm.setFirstDisclaimer(param);
		assertEquals(param, mainForm.getFirstDisclaimer());
		
		mainForm.setSecondDisclaimer(param);
		assertEquals(param, mainForm.getSecondDisclaimer());
		
		mainForm.setMarkRepresentation(param);
		assertEquals(param, mainForm.getMarkRepresentation());
		
		mainForm.setCharset(param);
		assertEquals(param, mainForm.getCharset());
		
		mainForm.setTranscriptionDetails(param);
		assertEquals(param, mainForm.getTranscriptionDetails());
		
		mainForm.setLangId(param);
		assertEquals(param, mainForm.getLangId());
		
		mainForm.setDisplayedGoods(param);
		assertEquals(param, mainForm.getDisplayedGoods());
		
		mainForm.setDisplayedTerms(param);
		assertEquals(param, mainForm.getDisplayedTerms());
		
		mainForm.setNumberDivisionalApplication(param);
		assertEquals(param, mainForm.getNumberDivisionalApplication());
		
	}

	public static final Object[] getIntegers() {
		return new Integer[] { null, 0, 1, Integer.MIN_VALUE, Integer.MAX_VALUE };
	}
	
	@Test
	@Parameters(method = "getIntegers")
	public void setIntegers(String param){
		MainForm mainForm = new MainForm();
		mainForm.setSeriesNumber(param);
		assertEquals(param, mainForm.getSeriesNumber());
	}
	
	
	public static final Object[] getFileWrappers() {
		return new FileWrapper[] { null, new FileWrapper() };
	}
		
	@Test
	@Parameters(method = "getFileWrappers")
	public void testFileWrapperFields(FileWrapper param) {
MainForm mainForm = new MainForm();
		
		mainForm.setSoundFile(param);
		mainForm.setFileWrapperImage(param);
		mainForm.setTrademarkRegulationDocuments(param);
		mainForm.setTrademarkTranslationDocuments(param);
		mainForm.setTrademarkApplicantDocuments(param);
		
		assertNotNull(mainForm.getSoundFile());
		assertNotNull(mainForm.getFileWrapperImage());
		assertNotNull(mainForm.getTrademarkRegulationDocuments());
		assertNotNull(mainForm.getTrademarkTranslationDocuments());
		assertNotNull(mainForm.getTrademarkApplicantDocuments());
		
		if (param!=null) {
			assertTrue(param == mainForm.getSoundFile());
			assertTrue(param == mainForm.getFileWrapperImage());
			assertTrue(param == mainForm.getTrademarkRegulationDocuments());
			assertTrue(param == mainForm.getTrademarkTranslationDocuments());
			assertTrue(param == mainForm.getTrademarkApplicantDocuments());
		}
	}
	
	public static final Object[] getBooleans() {
		return new Boolean[] { null, Boolean.TRUE,  Boolean.FALSE};
	}
		
	@Test
	@Parameters(method = "getBooleans")
	public void testBooleanFields(Boolean param) {
		MainForm mainForm = new MainForm();
		
		mainForm.setSecondLanguageTranslation(param);
		assertEquals(param, mainForm.getSecondLanguageTranslation());
		
		
		mainForm.setCorrespondenceEmail(param);
		assertEquals(param, mainForm.getCorrespondenceEmail());
		
		
		mainForm.setPersonalDataSection(param);
		assertEquals(param, mainForm.getPersonalDataSection());
		
		mainForm.setClaimSection(param);
		assertEquals(param, mainForm.getClaimSection());
		
		mainForm.setLanguageSection(param);
		assertEquals(param, mainForm.getLanguageSection());
		
		mainForm.setCollectiveMark(param);
		assertEquals(param, mainForm.getCollectiveMark());
		
		mainForm.setYourReference(param);
		assertEquals(param, mainForm.getYourReference());
		
		mainForm.setMarkRepresentationSection(param);
		assertEquals(param, mainForm.getMarkRepresentationSection());
		
		
		mainForm.setSignatureSection(param);
		assertEquals(param, mainForm.getSignatureSection());
		
		mainForm.setShowWarning(param);
		assertEquals(param, mainForm.getShowWarning());
		
		mainForm.setShowWarningInvoice(param);
		assertEquals(param, mainForm.getShowWarningInvoice());
		
		mainForm.setFinalSubmit(param);
		assertEquals(param, mainForm.getFinalSubmit());
		
		mainForm.setAddSecondSign(param);
		assertEquals(param, mainForm.getAddSecondSign());
		
		
		mainForm.setPaymentMethodSection(param);
		assertEquals(param, mainForm.getPaymentMethodSection());
		
		mainForm.setTransformationSection(param);
		assertEquals(param, mainForm.getTransformationSection());
		
		
		mainForm.setCorrespondenceLanguageCheckBox(param);
		assertEquals(param, mainForm.getCorrespondenceLanguageCheckBox());
		
		mainForm.setSeriesPresent(param);
		assertEquals(param, mainForm.getSeriesPresent());
		
		mainForm.setShowErrors(param);
		assertEquals(param, mainForm.getShowErrors());
		
		mainForm.setGsSection(param);
		assertEquals(param, mainForm.getGsSection());
		
		mainForm.setCollectiveTranslationDoc(param);
		assertEquals(param, mainForm.getCollectiveTranslationDoc());
		
		mainForm.setCollectiveRegulationDoc(param);
		assertEquals(param, mainForm.getCollectiveRegulationDoc());
		
		mainForm.setTermsAndConditions(param);
		assertEquals(param, mainForm.getTermsAndConditions());
		
		mainForm.setClaimDivisionalApplication(param);
		assertEquals(param, mainForm.getClaimDivisionalApplication());
		
		mainForm.setReferenceSection(param);
		assertEquals(param, mainForm.getReferenceSection());
		
		mainForm.setPaymentDataSection(param);
		assertEquals(param, mainForm.getPaymentDataSection());
		
		mainForm.setDivisionalSection(param);
		assertEquals(param, mainForm.getDivisionalSection());
		
		mainForm.setOtherAttachments(param);
		assertEquals(param, mainForm.getOtherAttachments());
		
		mainForm.setClaimPriority(param);
		assertEquals(param, mainForm.getClaimPriority());
		
		mainForm.setClaimExhibitionPriority(param);
		assertEquals(param, mainForm.getClaimExhibitionPriority());
		
		mainForm.setNationalSearchReport(param);
		assertEquals(param, mainForm.getNationalSearchReport());
		
		
	}
	
	@Test
	public void getterSetterTest(){
		MainForm mainForm = new MainForm();
		
		
		
		SignatureForm signatoryForm = new SignatureForm();
		mainForm.setSignatoryForm(signatoryForm);
		assertTrue(signatoryForm == mainForm.getSignatoryForm());
		
		
		signatoryForm = new SignatureForm();
		mainForm.setSecondSignatoryForm(signatoryForm);
		assertTrue(signatoryForm == mainForm.getSecondSignatoryForm());
		
		
		Date d = new Date();
		mainForm.setDateOfSigning(d);
		assertEquals(d, mainForm.getDateOfSigning());
		
		mainForm.setDateDivisionalApplication(d);
		assertEquals(d, mainForm.getDateDivisionalApplication());
		
		
		
	
		
		
		
		 
		
		TransformationForm transformation = mock(TransformationForm.class);
		mainForm.setTransformation(transformation );
		assertTrue(mainForm.getTransformation() == transformation);
		
		ExhPriorityForm form = mock(ExhPriorityForm.class);
		mainForm.setExhpriority(form);
		assertTrue(mainForm.getExhpriority() == form);
		
		
		
		
		
		
	}
	
	@Test
	public void toStringTest(){
		MainForm mainForm = new MainForm();
		assertNotNull(mainForm.toString());
	}
	
	
	@Test
	public void clearTest(){
		MainForm form = new MainForm();
		
		form.setFirstDisclaimer("asdfasd");
		form.setMarkType("qewrqw");
		form.setSecondDisclaimer("qewrqw");
		form.setMarkColorsIndicationSecond("asdfasd");
		form.setMarkDescription("asdfasdf");
		form.setMarkDescriptionSecond("asdfasdf");
		form.setMarkColorsIndicationSecond("asdf");
		form.setSoundRepresentation("q12341234123");
		form.setMarkRepresentation("qwerqwer");
		form.setWordRepresentation("qwerqwerqwe");
		form.setCollectiveMark(true);
		form.setSeriesPresent(true);
		form.setSeriesNumber("123");
		form.setColourChecked(true);
		
		form.setFileWrapperImage(null);
		form.setSoundFile(null);
		form.setCharset("qwerqwer");
		form.setTranscriptionDetails("qwerqwer");
		form.setYourReference(true);
		List<ColourForm> colourList = new ArrayList<>();
		colourList.add(new ColourForm());
		form.setColourList(colourList );
		
		
		form.clearInformation();
		
		
		
		assertEquals(AppConstants.MarkTypeSelect, form.getMarkType());
		assertEquals("", form.getFirstDisclaimer());
		assertEquals("", form.getSecondDisclaimer());
		assertEquals("", form.getMarkColorsIndicationSecond());
		assertEquals("", form.getMarkDescription());
		assertEquals("", form.getMarkDescriptionSecond());
		assertEquals("", form.getMarkColorsIndicationSecond());
		assertEquals("", form.getSoundRepresentation());
		assertEquals("", form.getMarkRepresentation());
		assertEquals("", form.getWordRepresentation());
		assertEquals("", form.getCharset());
		
		assertEquals(false, form.getCollectiveMark());
		assertEquals(false, form.getColourChecked());
		assertEquals(false, form.getYourReference());
		assertNotNull(form.getFileWrapperImage());
		assertNotNull(form.getSoundFile());
		assertNotNull(form.getColourList());
		assertTrue(form.getColourList().isEmpty());
		assertNull(form.getSeriesNumber());
		
		
	}
}
