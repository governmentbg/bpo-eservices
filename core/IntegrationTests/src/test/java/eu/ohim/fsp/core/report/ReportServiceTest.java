package eu.ohim.sp.core.report;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.document.DocumentServiceRemote;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.FODocument;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * User: jaraful
 * Date: 02/09/13
 * Time: 15:38
 */
public class ReportServiceTest {

	ReportServiceInterface reportService = null;
	DocumentServiceRemote documentService = null;

	private FODocument document1;
	private FODocument document2;

	@Before
	public void setup() {
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		try {
			final Context context = new InitialContext(jndiProperties);
			reportService = (ReportServiceInterface) context.lookup("ejb:core-report-management/ReportService//ReportService!eu.ohim.sp.core.report.ReportServiceInterface");
			documentService = (DocumentServiceRemote) context.lookup("ejb:core-document-management/DocumentService//DocumentService!eu.ohim.sp.core.document.DocumentServiceRemote");
		} catch (NamingException e) {
			throw new SPException("There was an error while setting up the test.");
		}

		createDocuments();
	}

	@Test
	public void velocityReportTest() {
		Map<String, Object> objectMap = new HashMap<String, Object>();
		objectMap.put("value", new Integer(1));

		String result = reportService.getVelocityReport("eu.ohim.sp.core.rules.tmefiling",
				"testTemplate",
				objectMap,
				null);

		System.out.println("Result: " + result);
	}

	@Test
	public void mailTest() {
		String mail = "fulgencio.jara@ext.oami.europa.eu";
		String subject = "Test mail";

		Map<String, Object> objectMap = new HashMap<String, Object>();
		objectMap.put("value", new Integer(1));

		String content = reportService.getVelocityReport("eu.ohim.sp.core.rules.tmefiling",
				"testTemplate",
				objectMap,
				null);

		List<Document> documentList = new ArrayList<Document>();
		documentList.add(documentService.saveDocument(document1));
		documentList.add(documentService.saveDocument(document2));

		reportService.sendMail(mail, subject, content, documentList);
	}

	private void createDocuments() {
		// Document 1
		document1 = new FODocument();
		document1.setCustomProperties(new HashMap<String, String>());
		document1.setApplicationType("TEST");
		document1.setFilingNumber("111111");
		document1.setApplicationStatus("draft");
		document1.setAttachmentType(FormatXML.APPLICATION_OTHER.value());
		document1.setFileName("smile.jpg");
		document1.setFileFormat("image/jpeg");
		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("files/smile.jpg");
			document1.setData(IOUtils.toByteArray(is));
		} catch (FileNotFoundException e) {
			throw new SPException("File not found");
		} catch (IOException e) {
			throw new SPException("IOException while reading the resource");
		}

		// Document 2
		document2 = new FODocument();
		document2.setCustomProperties(new HashMap<String, String>());
		document2.setApplicationType("TEST");
		document2.setFilingNumber("111111");
		document2.setApplicationStatus("draft");
		document2.setAttachmentType(FormatXML.APPLICATION_OTHER.value());
		document2.setFileName("smile.jpg");
		document2.setFileFormat("image/jpeg");
		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("files/smile.jpg");
			document2.setData(IOUtils.toByteArray(is));
		} catch (FileNotFoundException e) {
			throw new SPException("File not found");
		} catch (IOException e) {
			throw new SPException("IOException while reading the resource");
		}
	}
}
