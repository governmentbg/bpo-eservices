package eu.ohim.sp.integration.adapter.openam.authentication.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    /**
     * Read a property from a property file
     * 
     * @param propertyName, name of property to retrieve
     * @param fileName, name of property file
     * @return a string with the property or null in case that is not present
     * @throws IOException, in case that file is not present
     */
    public static String getProperty(String propertyName, String fileName) throws IOException {
        Properties configProp = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream in = loader.getResourceAsStream(fileName);
        configProp.load(in);
        return configProp.getProperty(propertyName);
    }

}
