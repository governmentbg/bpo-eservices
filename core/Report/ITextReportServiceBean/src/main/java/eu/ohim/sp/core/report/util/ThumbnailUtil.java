package eu.ohim.sp.core.report.util;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ThumbnailUtil {
	
	private static final String JPG = "jpg";

	private static byte[] doConvertIfNecessary( byte[] b) throws IOException {
		BufferedImage buffered = ImageIO.read(new ByteArrayInputStream(b));
		if( buffered.getColorModel().getTransparency() != Transparency.OPAQUE) {
            int w = buffered.getWidth();
            int h = buffered.getHeight();
            BufferedImage image2 = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image2.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0,0,w,h);
            g.drawRenderedImage(buffered, null);
            g.dispose();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
    		ImageIO.write(image2, JPG, bout);
    		return bout.toByteArray();
        } else {
        	return b;
        }
        
	}

	public static byte[] createThumbnail(byte[] b, int maxWidth, int maxHeight)
			throws IOException {
		byte[] bytes = doConvertIfNecessary(b);
		ByteArrayOutputStream out = new ByteArrayOutputStream(32000);
		Thumbnails.of(new ByteArrayInputStream(bytes)).size(maxWidth, maxHeight)
				.outputFormat(JPG).toOutputStream(out);
		return out.toByteArray();
	}
	
}