package eu.ohim.sp.common.xml;

import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;

/**
 *
 *
 * Date: 08/04/14
 * Time: 14:16
 *
 * @author gregor.lah@ext.oami.europa.eu
 */
public class RemoveEmptyElementsUtilTest extends TestCase {

//    @Test
    public void testRemoveEmptyElements() throws Exception {

        byte fileContent[] = null;

        InputStream fin;

        try {
            fin = getClass().getResourceAsStream("/application.xml");
            fileContent = IOUtils.toByteArray(fin);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        }
        catch (IOException ioe) {
            System.out.println("Exception while reading file " + ioe);
        }

        byte[] cleanedFile = RemoveEmptyElementsUtil.removeEmptyElements(fileContent);

        String xml = new String(cleanedFile, "UTF-8");

        System.out.println(xml);

    }

}
