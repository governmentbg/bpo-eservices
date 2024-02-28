package eu.ohim.sp.core.report;

import com.lowagie.text.pdf.PdfWriter;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFCreationListener;

//For PDFA (PDFA1B) conformance
public final class SPPdfCreationListener implements PDFCreationListener {

	private final WatermarkPageEvent watermarkEvent;
	
	public SPPdfCreationListener(WatermarkPageEvent event) {
		this.watermarkEvent = event;
	}
	
	@Override
	public void preOpen(ITextRenderer iTextRenderer) {
		try {
			PdfWriter pdfWriter = iTextRenderer.getWriter();
			pdfWriter.setPdfVersion(PdfWriter.PDF_VERSION_1_5);
			pdfWriter.setPageEvent(watermarkEvent);
			pdfWriter.createXmpMetadata();
		} catch (Exception e) {
			throw new ReportException(
					"SEVERE: Problem setting PDFA1B conformance for the document: " + e.getMessage(), e);
		}

	}

	@Override
	public void preWrite(ITextRenderer iTextRenderer, int pageCount) {
	}

	@Override
	public void onClose(ITextRenderer renderer) {
	}

}
