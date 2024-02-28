package eu.ohim.sp.ui.tmefiling.form;

import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(JUnitParamsRunner.class)
public class MultipartFileFormTest {

	public static final Object[] getFiles() {
		return new MultipartFile[] { null, mock(MultipartFile.class)};
	}
	
	
	@Test
	@Parameters(method = "getFiles")
	public void testFilePriorityTranslation(MultipartFile file) {
		MultipartFileForm form = new MultipartFileForm();
		
		form.setFilePriorityTranslation(file);
		assertEquals(file, form.getFilePriorityTranslation());
		assertEquals(file, form.getFileForm());
		
	}
	
	@Test
	@Parameters(method = "getFiles")
	public void testFilePriorityCopy(MultipartFile file) {
		MultipartFileForm form = new MultipartFileForm();
		
		form.setFilePriorityCopy(file);
		assertEquals(file, form.getFilePriorityCopy());
		assertEquals(file, form.getFileForm());
		
	}
	
	@Test
	@Parameters(method = "getFiles")
	public void testFileSeniorityCopy(MultipartFile file) {
		MultipartFileForm form = new MultipartFileForm();
		
		form.setFileSeniorityCopy(file);
		assertEquals(file, form.getFileSeniorityCopy());
		assertEquals(file, form.getFileForm());
		
	}
	
	@Test
	@Parameters(method = "getFiles")
	public void testFileDocumentAttachment(MultipartFile file) {
		MultipartFileForm form = new MultipartFileForm();
		
		form.setFileDocumentAttachment(file);
		assertEquals(file, form.getFileDocumentAttachment());
		assertEquals(file, form.getFileForm());
		
	}
	
	@Test
	@Parameters(method = "getFiles")
	public void testOtherAttachments(MultipartFile file) {
		MultipartFileForm form = new MultipartFileForm();
		
		form.setOtherAttachments(file);
		assertEquals(file, form.getOtherAttachments());
		assertEquals(file, form.getFileForm());
		
	}
	
	@Test
	@Parameters(method = "getFiles")
	public void testFilePriorityCertificate(MultipartFile file) {
		MultipartFileForm form = new MultipartFileForm();
		
		form.setFilePriorityCertificate(file);
		assertEquals(file, form.getFilePriorityCertificate());
		assertEquals(file, form.getFileForm());
		
	}
	
	@Test
	@Parameters(method = "getFiles")
	public void testFileSeniorityTranslation(MultipartFile file) {
		MultipartFileForm form = new MultipartFileForm();
		
		form.setFileSeniorityTranslation(file);
		assertEquals(file, form.getFileSeniorityTranslation());
		assertEquals(file, form.getFileForm());
		
	}
	
	@Test
	@Parameters(method = "getFiles")
	public void testFileSeniorityCertificate(MultipartFile file) {
		MultipartFileForm form = new MultipartFileForm();
		
		form.setFileSeniorityCertificate(file);
		assertEquals(file, form.getFileSeniorityCertificate());
		assertEquals(file, form.getFileForm());
		
	}
	
	@Test
	@Parameters(method = "getFiles")
	public void testTrademarkTranslationDocuments(MultipartFile file) {
		MultipartFileForm form = new MultipartFileForm();
		
		form.setTrademarkTranslationDocuments(file);
		assertEquals(file, form.getTrademarkTranslationDocuments());
		assertEquals(file, form.getFileForm());
		
	}
	
	@Test
	@Parameters(method = "getFiles")
	public void testTrademarkApplicantDocuments(MultipartFile file) {
		MultipartFileForm form = new MultipartFileForm();
		
		form.setTrademarkApplicantDocuments(file);
		assertEquals(file, form.getTrademarkApplicantDocuments());
		assertEquals(file, form.getFileForm());
		
	}
	
	@Test
	@Parameters(method = "getFiles")
	public void testFileWrapperImage(MultipartFile file) {
		MultipartFileForm form = new MultipartFileForm();
		
		form.setFileWrapperImage(file);
		assertEquals(file, form.getFileWrapperImage());
		assertEquals(file, form.getFileForm());
		
	}
	
	@Test
	@Parameters(method = "getFiles")
	public void testSoundFile(MultipartFile file) {
		MultipartFileForm form = new MultipartFileForm();
		
		form.setSoundFile(file);
		assertEquals(file, form.getSoundFile());
		assertEquals(file, form.getFileForm());
		
	}
	
	@Test
	@Parameters(method = "getFiles")
	public void testTrademarkRegulationDocuments(MultipartFile file) {
		MultipartFileForm form = new MultipartFileForm();
		
		form.setTrademarkRegulationDocuments(file);
		assertEquals(file, form.getTrademarkRegulationDocuments());
		assertEquals(file, form.getFileForm());
		
	}
	
	
	public static final Object[] getFileWrappers() {
		return new FileWrapper[] { null, new FileWrapper() };
	}
		
	@Test
	@Parameters(method = "getFileWrappers")
	public void testFileWrapper(FileWrapper param) {
		MultipartFileForm form = new MultipartFileForm();
		form.setFileWrapper(param);
		assertEquals(param, form.getFileWrapper());
	}
	
}
