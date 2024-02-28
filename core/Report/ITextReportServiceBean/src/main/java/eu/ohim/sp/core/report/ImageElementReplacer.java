package eu.ohim.sp.core.report;

import com.lowagie.text.Image;
import org.apache.log4j.Logger;
import org.imgscalr.Scalr;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This Media factory class will serve to render the IMG elements in the
 * freemarker template that contain the data-src="documentService" from the
 * Jackrabbit content repository.
 *
 * @author Kristjan Cocev
 * @author Istvan Benedek
 */
public class ImageElementReplacer implements ReplacedElementFactory {

	private static final Logger logger = Logger.getLogger(ImageElementReplacer.class);

	private final ReplacedElementFactory superFactory;

	private final ImageProvider imageProvider;

	public ImageElementReplacer(ReplacedElementFactory superFactory, ImageProvider imageProvider) {
		this.superFactory = superFactory;
		this.imageProvider = imageProvider;
	}

	private Image getImage(Element element) {

		String documentId = element.getAttribute("src");
		
		if (documentId == null) { 
			logger.warn("there is no document id in the element's src attribute");
			return null;
		}

		BufferedImage bufferedImage = imageProvider.getImage(documentId);
		
		if (bufferedImage != null) {
			try {
				ImageIO.scanForPlugins();
				BufferedImage scaledImage = Scalr.resize(bufferedImage, 400);
				return Image.getInstance(scaledImage, null);		
				
			} catch (Exception e) {
				throw new ReportException(
						"SEVERE: Problem replacing image from the Jackrabbit content repository (DocumentService): documentId="
								+ documentId + "; " + e.getMessage(),
						e);
			}	
			
		}

		logger.warn("ImageProvider gave back null for documentid " + documentId);
		return null;
	}

	@Override
	public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox,
			UserAgentCallback userAgentCallback, int cssWidth, int cssHeight) {

		Element element = blockBox.getElement();

		if (element == null) {
			logger.warn("BlockBox Element is null");
			return null;
		}
		
		if (isImageElement(element)) {
			Image image = getImage(element);
			return new ITextImageElement(scaleImage(image, cssWidth, cssHeight)); 	
		}

		// for other elements use the default factory...
		return this.superFactory.createReplacedElement(layoutContext, blockBox, userAgentCallback, cssWidth, cssHeight);
	}

	@Override
	public void reset() {
		this.superFactory.reset();
	}

	@Override
	public void remove(Element e) {
		this.superFactory.remove(e);
	}

	@Override
	public void setFormSubmissionListener(FormSubmissionListener listener) {
		this.superFactory.setFormSubmissionListener(listener);
	}

	public static boolean isImageElement(Element element) {
		return (element != null && "img".equals(element.getNodeName()) && element.hasAttribute("data-src")
				&& "documentService".equals(element.getAttribute("data-src")));
	}
	
	public static FSImage scaleImage(Image image, int cssWidth, int cssHeight) {
		final FSImage fsImage = new ITextFSImage(image);

		if ((cssWidth != -1) || (cssHeight != -1)) {
			fsImage.scale(cssWidth, cssHeight);
		} else {
			logger.info("scale has not performed because the function invoked with the following [cssWidth="+cssWidth+"], [cssHeight="+cssHeight+"]");
		}

		return fsImage;
	}

}