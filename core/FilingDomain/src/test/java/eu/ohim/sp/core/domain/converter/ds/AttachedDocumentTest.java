package eu.ohim.sp.core.domain.converter.ds;

import static org.junit.Assert.assertEquals;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.filing.domain.ds.FileFormat;
import eu.ohim.sp.filing.domain.ds.URI;

/**
 * @author ionitdi
 */
public class AttachedDocumentTest
{
    DozerBeanMapper dozerBeanMapper;

    @Before
    public void setUp() throws Exception
    {
        dozerBeanMapper = CommonSetup.getMapper();
    }

    @Test
    public void testConvertToFSP()
    {
        AttachedDocument core = new AttachedDocument();

        Document doc = new Document();
        doc.setDocumentId("doc id");
        doc.setFileName("somefile.name");
        doc.setFileFormat("JPEG");
        doc.setName("doc name");
        doc.setUri("some uri");

        core.setDocument(doc);

        eu.ohim.sp.filing.domain.ds.Document ext = dozerBeanMapper.map(core, eu.ohim.sp.filing.domain.ds.Document.class);

        assertEquals("doc id", ext.getDocumentId());
        assertEquals("doc name", ext.getName());
        assertEquals("some uri", ext.getUri().getValue());
        assertEquals("somefile.name", ext.getFileName());
        assertEquals("JPEG", ext.getDocumentFileFormat().getValue());
    }

    @Test
    public void testConvertToCore()
    {
        eu.ohim.sp.filing.domain.ds.Document ext = new eu.ohim.sp.filing.domain.ds.Document();
        ext.setDocumentId("some other doc id");
        ext.setName("some other name");
        ext.setUri(new URI("some other uri"));
        ext.setFileName("some other filename");
        ext.setDocumentFileFormat(new FileFormat("GIF"));

        AttachedDocument core = dozerBeanMapper.map(ext, AttachedDocument.class);

        assertEquals("some other doc id", core.getDocument().getDocumentId());
        assertEquals("some other name", core.getDocument().getName());
        assertEquals("some other uri", core.getDocument().getUri());
        assertEquals("some other filename", core.getDocument().getFileName());
        assertEquals("GIF", core.getDocument().getFileFormat());
    }
}
