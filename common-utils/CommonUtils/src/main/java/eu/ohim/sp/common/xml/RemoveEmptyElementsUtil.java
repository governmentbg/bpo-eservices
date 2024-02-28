package eu.ohim.sp.common.xml;

import org.apache.log4j.Logger;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Date: 08/04/14
 * Time: 12:10
 *
 * @author gregor.lah@ext.oami.europa.eu
 */
public class RemoveEmptyElementsUtil {

    private static final Logger LOGGER = Logger.getLogger(RemoveEmptyElementsUtil.class);

    public static byte[] removeEmptyElements(byte[] xml) {

        try {

            StringReader reader = new StringReader(new String(xml, "UTF-8"));
            StringWriter writer = new StringWriter();

            TransformerFactory factory = TransformerFactory.newInstance();
            Source text = new StreamSource(reader);
            Transformer transformer;

            Source xslt = new StreamSource(RemoveEmptyElementsUtil.class.getResourceAsStream("/CleanEmptyElements.xslt"));

            transformer = factory.newTransformer(xslt);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            transformer.transform(text, new StreamResult(writer));

            return writer.toString().getBytes();

        } catch (Exception e) {
            LOGGER.error("Error removing empty elements in generated XML.", e);
        }

        return xml;

    }

}
