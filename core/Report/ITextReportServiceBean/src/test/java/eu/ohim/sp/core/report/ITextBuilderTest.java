package eu.ohim.sp.core.report;

import com.lowagie.text.DocumentException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.xhtmlrenderer.swing.SwingReplacedElementFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class ITextBuilderTest {

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	@Test
	public void testCreatePdf() throws DocumentException, IOException {

		ImageProvider imageprovider = new ImageProvider() {

			@Override
			public BufferedImage getImage(String id) {
				try {
					return ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("a.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		};

		ImageElementReplacer mediaReplacedElementFactory = new ImageElementReplacer(new SwingReplacedElementFactory(),
			imageprovider);

		String arialFont = this.getClass().getClassLoader().getResource("arialuni.ttf").getPath();

		ITextPdfBuilder itextPdfBuilder = new ITextPdfBuilder();

		File file = new File(this.getClass().getClassLoader().getResource("test.ftl").getFile());
		String htmlTemplate = readFile(file.getPath(), Charset.defaultCharset());

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		htmlTemplate = htmlTemplate.replace("#{changeit}", 1 + ". at " +new Date().getTime());
		itextPdfBuilder.createPdf(htmlTemplate,null,null, mediaReplacedElementFactory, new String[]{arialFont} , bos);
		FileUtils.writeByteArrayToFile(new File("result.pdf"), bos.toByteArray());
		bos.close();
		System.out.println("PDF Genereation has been finished");

	}
}
