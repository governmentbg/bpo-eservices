package eu.ohim.sp.core.report;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class WatermarkPageEvent extends PdfPageEventHelper {
	private static String DRAFT_IMG_BG = "img/draft_bg.png";
	private static String DRAFT_IMG_EN = "img/draft_en.png";

	private boolean isDraft;
	private String localeCode;

	private static Logger LOGGER = Logger.getLogger(WatermarkPageEvent.class);

	public WatermarkPageEvent(boolean isDraft, String localeCode) {
		super();
		this.isDraft = isDraft;
		this.localeCode = localeCode;
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		String imgResource = null;

		if (localeCode != null && localeCode.equalsIgnoreCase("bg")) {
			imgResource = DRAFT_IMG_BG;
		} else {
			imgResource = DRAFT_IMG_EN;
		}

		if(isDraft) {
			try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(imgResource);
				 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				BufferedImage buff = ImageIO.read(inputStream);
				ImageIO.write(buff, "png", outputStream);
				Image img = Image.getInstance(outputStream.toByteArray());
				img.setAbsolutePosition(0, 0);
				writer.getDirectContentUnder().addImage(img);
			} catch (Exception e){
				LOGGER.warn(e);
			}
		}
	}
}
