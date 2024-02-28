package eu.ohim.sp.core.report;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.apache.log4j.Logger;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFCreationListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Creates PDF binary output based on freemarker HTML content using iText and
 * flying-saucer.
 *
 * The generated PDF document is PDFA1B compliant. The template for document
 * generation is in form of freemarker template (receipt*.ftl). All the needed
 * resources can be found in configuration folder iTextReport
 * (conf/sp-fo/configuration/app-server/MODULE_NAME/iTextReport).
 *
 * The (embedded) font in use is "Arial Unicode MS", which should cover the most
 * unicode characters.
 *
 * The Jackrabbit resources can be fetched from the content repository by
 * including the attribute data-src="documentService" in the IMG tag. All the
 * other resources will be fetched from the iTextReport configuration folder.
 *
 * @author Kristjan Cocev, Maciej Walkowiak
 * @author Istvan Benedek
 */
class ITextPdfBuilder {

	private static final Logger logger = Logger.getLogger(ITextPdfBuilder.class);
	
	public static byte[] createPdf(String pdfContent, String resourceFolder, PDFCreationListener creationListener, ReplacedElementFactory replacedElementFactory, String[] fontPaths) throws DocumentException, IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    createPdf(pdfContent, resourceFolder, creationListener, replacedElementFactory, fontPaths, outputStream);
		return outputStream.toByteArray();
	}
	
	public static void createPdf(String pdfContent, String resourceFolder, PDFCreationListener listener, ReplacedElementFactory replacedElementFactory, String[] fontPaths, java.io.OutputStream outputStream) throws DocumentException, IOException {
		ITextRenderer iTextRenderer = new ITextRenderer();
		iTextRenderer.getSharedContext().setReplacedElementFactory(replacedElementFactory);
		iTextRenderer.setListener(listener);
		if (fontPaths != null && fontPaths.length > 0) {
			for (String path : fontPaths) {
				iTextRenderer.getFontResolver().addFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			}
		} else {
			logger.warn("Provided fontPath was null or empty.");
		}
		
		iTextRenderer.setDocumentFromString(pdfContent,resourceFolder);
		iTextRenderer.layout();
		try {
			iTextRenderer.createPDF(outputStream);
		} catch (DocumentException e) {
			throw new ReportException("Problem at creating PDF document with iText: " + e.getMessage(), e);
		}
		
	}

	public static String[] getFontPaths(String fontDirectory, String[] extensions) {
		File f = new File(fontDirectory);
		HashSet<String> fontPaths = new HashSet<>();
		final List<String> exts = Arrays.asList(extensions);
		if (f.isDirectory()) {
			Arrays.asList(f.listFiles()).stream()
					.filter(file -> exts.stream().anyMatch(s -> file.getAbsolutePath().toLowerCase().endsWith(s)))
					.forEach(file -> fontPaths.add(file.getAbsolutePath()));

		} else {
			logger.warn("The provided fontDirectory is not a directory.");
		}

		String[] r = new String[fontPaths.size()];
		return fontPaths.toArray(r);
	}
}
