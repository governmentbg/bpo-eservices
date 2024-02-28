package eu.ohim.sp.common.ui.fileupload.util;

import java.text.DecimalFormat;

public class FileUtil {

    private static final double KILOBYTE = 1024;

    private static final DecimalFormat df2 = new DecimalFormat("###.##");

	public static String getCanonicalFileSize(Long fileSize) {
		Double canonical = null;
		String type = " KB";
		if (fileSize!=null) {
			canonical = fileSize/KILOBYTE;
			if (canonical>1000) {
				canonical /= KILOBYTE;
				type = " MB";
			}
		}
		return (canonical==null ? "" : df2.format(canonical) + type) ;
	}

}
