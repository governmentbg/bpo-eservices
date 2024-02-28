package eu.ohim.sp.core.report;

import eu.ohim.sp.common.path.PathResolutionStrategy;
import eu.ohim.sp.common.path.SpConfigDirPathResolutionStrategy;
import eu.ohim.sp.core.configuration.ConfigurationServiceLocal;
import eu.ohim.sp.core.document.DocumentServiceLocal;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.mockito.Mockito.when;

@RunWith(value = Parameterized.class)
public class ITextReportServiceBeanTest {

	private String lang;

	private String module;

	private static final String TEMPLATE_PATH = "test-classes";

	@InjectMocks
	private ITextReportServiceBean iTextReportServiceBean;

	@Mock
	private PathResolutionStrategy pathResolver;

	@Mock
	private DocumentServiceLocal documentService;

	@Mock
	private ConfigurationServiceLocal configurationService;

	@Mock
	private LimitedTradeMark limitedTradeMark;

	@Mock
	private TMeServiceApplication tmeserviceApplication;

	public ITextReportServiceBeanTest(String module, String lang) {
		this.module = module;
		this.lang = lang;
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		pathResolverSetup();
		TestHelper.documentServiceSetup(documentService);
		configurationServiceSetup();
		TestHelper.limitedTrademarkSetup(limitedTradeMark);
		TestHelper.tmEserviceApplicationSetup(tmeserviceApplication, limitedTradeMark);
		System.setProperty(SpConfigDirPathResolutionStrategy.SP_CONFIG_DIR_PROPERTY,
			new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent());
		iTextReportServiceBean.init();
	}
	private void configurationServiceSetup() {
		when(configurationService.getValue(Matchers.anyString(), Matchers.anyString())).thenReturn(TEMPLATE_PATH);

	}

	private String getResourceFolder() {
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		String from = "/core/Report/ITextReportServiceBean/target/test-classes/";
		String to = "/conf/sp-fo/configuration/app-server/freemarker";

		String resourceFolder = path.replaceFirst(from, to);
		return resourceFolder;
	}

	private void pathResolverSetup() {

		String resourceFolder = getResourceFolder();

		when(pathResolver.resolvePath(TEMPLATE_PATH)).thenReturn(resourceFolder);
	}

	@Test
	public void testReceipt() throws IOException {

		String filename = System.getProperty("user.home") + "/" + module + "_receipt_" + lang + "_"
			+ new Date().getTime() + ".pdf";

		System.out.print("Generating " + lang + " receipt for " + module + " ... ");

		String template = ReportService.RECEIPT_REPORT;

		byte[] data = iTextReportServiceBean.generateReport("", template, lang,
			tmeserviceApplication, null, Boolean.TRUE, new Date(), true);

		FileUtils.writeByteArrayToFile(new File(filename), data);
		Assert.assertEquals(TEMPLATE_PATH, configurationService.getValue("asdfa", "asdfasd"));
		Assert.assertEquals(getResourceFolder(), pathResolver.resolvePath(TEMPLATE_PATH));
		Assert.assertNotNull(pathResolver);
		Assert.assertNotNull(documentService);
		Assert.assertNotNull(configurationService);

		System.out.print("DONE - " + "pdf generated into " + filename + "\n");
	}

	@Test
	public void testSimilarityExcell() throws IOException {
		String filename = System.getProperty("user.home") + "/" + module + "_receipt_" + lang + "_"
			+ new Date().getTime() + ".xlsx";
		byte[] data = iTextReportServiceBean.generateSimilaritySpreadSheet("", Arrays.asList(limitedTradeMark));
		FileUtils.writeByteArrayToFile(new File(filename), data);
	}

	@Parameterized.Parameters
	public static Collection<String[]> data() {
		return Arrays.asList(new String[][] { { "tmrenewal", "bg" } });
	}
}
