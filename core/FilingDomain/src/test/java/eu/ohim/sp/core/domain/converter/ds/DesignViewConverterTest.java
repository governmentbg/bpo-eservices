package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

/**
 * @author ionitdi
 */
public class DesignViewConverterTest
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
        /// Arrange
        DesignView core = new DesignView();
        core.setPublicationSize("45x456");
        core.setSequence(1234);
        core.setType("Top view");
        core.setPublishInColour(true);
        core.setDescription("some description");
        core.setPublishInBlackWhite(true);

        AttachedDocument doc = new AttachedDocument();
        doc.setDocument(new Document());
        doc.getDocument().setDocumentId("234");
        doc.getDocument().setUri("some uri");
        doc.getDocument().setFileName("some filename");
        doc.getDocument().setFileFormat("JPEG");
        core.setView(doc);



        /// Act
        eu.ohim.sp.filing.domain.ds.ViewType ext = dozerBeanMapper.map(core, eu.ohim.sp.filing.domain.ds.ViewType.class);


        /// Assert
        assertEquals(new BigInteger("45"), ext.getViewRepresentationSize().getHeight());
        assertEquals(new BigInteger("456"), ext.getViewRepresentationSize().getWidth());
        assertEquals("1234", ext.getViewSequenceNumber());
        assertEquals("Top view", ext.getViewPerspective());
        assertEquals(true, ext.isViewColourIndicator());
        assertEquals("some description", ext.getViewTitle().getValue());
        assertEquals("234", ext.getViewIdentifier());
        assertEquals("some uri", ext.getViewURI().getValue());
        assertEquals("some filename", ext.getViewFilename());
        assertEquals("JPEG", ext.getViewFileFormat().getValue());
        assertEquals(true, ext.isViewBWIndicator());
    }

    @Test
    public void testConvertToCore()
    {
        /// Arrange
        eu.ohim.sp.filing.domain.ds.ViewType ext = new eu.ohim.sp.filing.domain.ds.ViewType();
        ext.setViewIdentifier("123");
        ext.setViewPerspective("Bottom view");
        ext.setViewColourIndicator(Boolean.TRUE);
        ext.setViewBWIndicator(Boolean.TRUE);

        // build representation size
        eu.ohim.sp.filing.domain.ds.ViewRepresentationSizeType size = new eu.ohim.sp.filing.domain.ds.ViewRepresentationSizeType();
        size.setHeight(new BigInteger("123"));
        size.setWidth(new BigInteger("12"));
        size.setUnit(eu.ohim.sp.filing.domain.ds.UnitType.PIXEL);

        ext.setViewRepresentationSize(size);

        /// Act
        DesignView core = dozerBeanMapper.map(ext, DesignView.class);


        /// Assert
        assertEquals("123x12", core.getPublicationSize());
        assertEquals("123", core.getView().getDocument().getDocumentId());
        assertEquals("Bottom view", core.getType());
        assertEquals(true, core.isPublishInColour());
        assertEquals(true, core.isPublishInBlackWhite());
    }
}
