package eu.ohim.sp.core.report;

import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.report.util.ThumbnailUtil;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ImageProviderImpl implements ImageProvider {

	private static final Logger logger = Logger.getLogger(ImageProviderImpl.class);

	private DocumentService documentService;

	public ImageProviderImpl(DocumentService documentService) {
		this.documentService = documentService;
	}

	@Override
	public BufferedImage getImage(String id) {
		Document document = documentService.getDocument(id);

		if (document == null) {
			return getFallbackImage();
		}

		String fileFormat = document.getCustomProperties().get("fileFormat");

		if (fileFormat == null) {
			fileFormat = "image/jpg";
		}

		try {

			if (fileFormat.startsWith("image/")) {
				return ImageIO.read(new ByteArrayInputStream(getThumbnail(document.getData())));
			}

			if (fileFormat.startsWith("audio/")){

				return ImageIO.read(this.getClass().getResourceAsStream("/img/note256.png"));
			}

			return ImageIO.read(this.getClass().getResourceAsStream("/img/file256.png"));


		} catch (Throwable e) {
			logger.error(e);
		}

		return getFallbackImage();
	}


	private static byte[] getThumbnail(byte[] img) throws IOException {
		return ThumbnailUtil.createThumbnail(img, 400, 400);
	}

	private BufferedImage getFallbackImage(){
		BufferedImage fallback = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = fallback.getGraphics();
		graphics.setColor(Color.black);
		graphics.fillRect(0,0,400,400);
		graphics.setColor(Color.white);
		graphics.fillRect(2,2,396,396);
		graphics.setColor(Color.red);
		char[] charText = "No image available".toCharArray();
		graphics.setFont(new Font("Serif", Font.BOLD, 25));
		graphics.drawChars(charText, 0, charText.length, 100, 100);
		return fallback;
	}

}
