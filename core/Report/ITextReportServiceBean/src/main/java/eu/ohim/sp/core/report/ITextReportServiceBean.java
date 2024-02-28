package eu.ohim.sp.core.report;

import com.lowagie.text.DocumentException;
import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.path.SpConfigDirPathResolutionStrategy;
import eu.ohim.sp.core.configuration.ConfigurationServiceLocal;
import eu.ohim.sp.core.document.DocumentServiceLocal;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.report.freemarker.CustomTemplateExceptionHandler;
import eu.ohim.sp.core.report.freemarker.FreemarkerGenerator;
import eu.ohim.sp.core.report.util.ResourceBundleMethod;
import freemarker.template.Configuration;
import org.apache.log4j.Logger;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.util.XRLog;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static eu.ohim.sp.core.report.util.ReportUtil.readMessages;

/**
 * The Report Service that uses iText and Flying Saucer for PDF generation
 * (instead of BIRT). This report engine will create a PDFA1B standard compliant
 * PDF. The (embedded) font used is "Arial Unicode MS", since it seems to
 * support the most unicode characters.
 *
 * For more information on how it works see
 * {@link eu.ohim.sp.core.report.ITextPdfBuilder}.
 *
 * @author Kristjan Cocev, Maciej Walkowiak
 * @author Istvan Benedek
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Stateless
public class ITextReportServiceBean implements ReportServiceRemote, ReportServiceLocal {

	public static final String[] FONT_EXTENSIONS = new String[] { ".ttf" };

	public static final String DEFAULT_FONT_DIR = "/common/fonts";

	private static final String DEFAULT_COMMON_MESSAGES_MESSAGES_DIR = "/common/messages/";

	private static final String TEMPLATE_PATH = "template_path";

	private static final Logger logger = Logger.getLogger(ITextReportServiceBean.class);

	private ConcurrentHashMap<String, Properties> messages;
	private String resourceFolder;
	private FreemarkerGenerator freemarkerGenerator;

	@EJB(lookup = "java:global/configurationLocal")
	private ConfigurationServiceLocal configurationService;

	@EJB(lookup = "java:global/documentLocal")
	private DocumentServiceLocal documentService;

	@PostConstruct
	public void init() {
		XRLog.setLoggingEnabled(true);

		String templatePath = configurationService.getValue(TEMPLATE_PATH, "general");
		String resourceFolder = new SpConfigDirPathResolutionStrategy().resolvePath(templatePath);
		if (resourceFolder == null || "".equals(resourceFolder.trim())) {
			logger.error("init(): SEVERE: Could not resolve the resource folder for iText Report generation!");
			throw new IllegalStateException("SEVERE: could not resolve the resource folder for iText Report generation!");
		}

		Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
		configuration.setTemplateExceptionHandler(new CustomTemplateExceptionHandler());
		try {
			configuration.setDirectoryForTemplateLoading(new File(resourceFolder));
		} catch (IOException e) {
			logger.error("init(): SEVERE: Could not resolve the resource folder for iText Report generation!");
			throw new SPException(e);
		}

		this.messages = new ConcurrentHashMap<>();
		this.resourceFolder = resourceFolder;
		this.freemarkerGenerator = new FreemarkerGenerator(configuration);
	}

	@Override
	public byte[] generateReport(String module, String template, String localeCode, Object... args) {
		Boolean isDraft = false;
		if(args.length > 3 && args[2] != null){
			if(args[2] instanceof String){
				isDraft = Boolean.parseBoolean((String)args[2]);
			} else if(args[2] instanceof Boolean) {
				isDraft = (Boolean) args[2];
			}
		}
		String templateFilePath = configurationService.getValue(template, module);
		if(template == null || templateFilePath.isEmpty()){
			throw new SPException("Empty template file property setting");
		}
		return createPdf(isDraft, templateFilePath, localeCode, args);
	}

	@Override
	public void sendMail(String mail, String subject, String content, List<Document> documentList) {
		throw new IllegalStateException("sendMail(): This method is not yet supported! TODO!");
	}

	private byte[] createPdf(Boolean isDraft, String template, String localeCode, Object... args) {
		try {
			String generatedHTML = generateHTML(template, localeCode, isDraft, args);

			logger.debug("Generated HTML for template [" + template + "], locale ["+ localeCode + "] and " + args.length + " arguments: " + generatedHTML);

			return ITextPdfBuilder.createPdf(generatedHTML,
				new File(new File(resourceFolder+File.separator+template).getParent()).toURI().toString(),
				new SPPdfCreationListener(new WatermarkPageEvent(isDraft, localeCode)),
				new ImageElementReplacer(new ITextRenderer().getSharedContext().getReplacedElementFactory(), new ImageProviderImpl(documentService)),
				ITextPdfBuilder.getFontPaths(resourceFolder + DEFAULT_FONT_DIR, FONT_EXTENSIONS));

		} catch (Exception e) {
			logger.error("generateReport(): Error during generating report: \n" + e.getMessage(), e);
			throw new SPException(e);
		}
	}

	private String generateHTML(String template, String localeCode, boolean isDraft, Object... args) {
		Map<String, Object> model = createModel(localeCode, isDraft, args);

		return freemarkerGenerator.generate(template, model, new Locale(localeCode), StandardCharsets.UTF_8.name());
	}

	private Map<String, Object> createModel(String localeCode, boolean isDraft, Object... args) {
		Map<String, Object> model = new HashMap<>();
		model.put("args", args);
		model.put("isDraft", isDraft);
		model.put("messages", getMessages(localeCode,DEFAULT_COMMON_MESSAGES_MESSAGES_DIR));
		model.put("resourceBundle", new ResourceBundleMethod());
		return model;
	}

	private Properties getMessages(String locale, String commonMessagesDir) {
		Properties pMessages = messages.get(locale);
		if (pMessages == null) {
			pMessages = readMessages(resourceFolder + commonMessagesDir, locale);
			messages.put(locale, pMessages);
		}
		return pMessages;
	}

	@Override
	public byte[] generateSimilaritySpreadSheet(String module, List<TradeMark> tmList) {
		return new ExcelSimilarityReportGenerator(resourceFolder+"/"+DEFAULT_COMMON_MESSAGES_MESSAGES_DIR)
			.generate(tmList)
			.toByteArray();
	}
}