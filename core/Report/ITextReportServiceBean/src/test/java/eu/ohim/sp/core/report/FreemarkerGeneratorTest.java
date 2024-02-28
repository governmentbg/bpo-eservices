package eu.ohim.sp.core.report;

import com.lowagie.text.DocumentException;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.core.report.freemarker.FreemarkerGenerator;
import eu.ohim.sp.core.report.util.ReportUtil;
import freemarker.template.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

public class FreemarkerGeneratorTest {

	@Mock
	private TradeMarkApplication trademarkApplication;

	@Mock
	private TradeMark tradeMark;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		TestHelper.trademarkSetup(tradeMark);
		TestHelper.trademarkApplicationSetup(trademarkApplication, tradeMark);
	}

	private static String getTemplatePath(String templateName) {
		return FreemarkerGeneratorTest.class.getClassLoader().getResource(templateName).getPath();
	}

	private static String getTemplateDir(String resource) {
		String templateFilePath = getTemplatePath(resource);
		return new File(templateFilePath).getParent();
	}

	private static String generateFromResource(String templateDir, String template, HashMap<String, Object> model,
			String languageCode, String charEncoding) throws IOException {

		System.out.println(templateDir);

		Configuration configuration = new Configuration();
		configuration.setDirectoryForTemplateLoading(new File(templateDir));
		FreemarkerGenerator generator = new FreemarkerGenerator(configuration);

		String result = generator.generate(template, model, new Locale(languageCode), charEncoding);

		return result;

	}

	@Test
	public void testGenerator() throws IOException, DocumentException {

		HashMap<String, Object> model = new HashMap<>();
		model.put("args", new Object[]{trademarkApplication});

		String result = generateFromResource(getTemplateDir("template.ftl"), "template.ftl", model, "bg",
				"UTF-8");

		System.out.println("Result");
		System.out.println(result);
		System.out.println("=====");
	}

}
