package eu.ohim.sp.core.report;

import java.awt.image.BufferedImage;

public interface ImageProvider {
	BufferedImage getImage(String id);
}
