package bg.duosoft.xmlreplacerinrepo;

import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.FODocument;
import org.springframework.beans.factory.annotation.Autowired;
import eu.ohim.sp.core.document.DocumentService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Raya
 * 13.01.2021
 */
@SpringBootTest
@ContextConfiguration(locations = "/test-service.xml")
public class DocumentClientTest {

    @Autowired
    private DocumentService documentService;

    @Test
    public void testConnect(){
        Map<String, String> customProperties = new HashMap<String, String>();
        customProperties.put(FODocument.FILING_NUMBER, "TMBG202000000002281");
        customProperties.put(FODocument.ATTACHMENT_TYPE, FormatXML.APPLICATION_EPUB.value());
        List<Document> found = documentService.searchDocument(customProperties, false);
        Assert.isTrue(found != null && found.size() >0, "Could not find doc");
    }

}
