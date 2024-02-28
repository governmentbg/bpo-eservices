package eu.ohim.sp.common.security.esapi;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads properties file from location [value-of-jndi-variable-with-key jndiName]/filePath
 *
 * System.out.println is used for logging because log4j is not initialized yet.
 *
 * @author Maciej Walkowiak
 */
class EsapiJndiPropertiesLoader {

    private static final Logger logger = Logger.getLogger(EsapiJndiPropertiesLoader.class);

    /**
     * @see EsapiJndiConstants.JNDI_NAME
     */
    private final String jndiName;
    private final String filePath;
    private final JndiLookupWrapper jndiLookupWrapper;

    public EsapiJndiPropertiesLoader(String jndiName, String filePath) {
        this.jndiName = jndiName;
        this.filePath = filePath;
        this.jndiLookupWrapper = new JndiLookupWrapper();
    }

    public EsapiJndiPropertiesLoader(String jndiName, String filePath, JndiLookupWrapper jndiLookupWrapper) {
        this.jndiName = jndiName;
        this.filePath = filePath;
        this.jndiLookupWrapper = jndiLookupWrapper;
    }

    Properties loadProperties() throws EsapiJndiException {
        System.out.println("Attempting to load esapi filename from JNDI using JNDI name: " + jndiName);

        FileInputStream fis = null;
        try {
            String esapiFileName = jndiLookupWrapper.lookup(jndiName);

            String esapiPath = filePath + "/" + esapiFileName;

            System.out.println("Attempting to load " + esapiPath);

            Properties properties = new Properties();
            fis = new FileInputStream(new File(esapiPath));
            properties.load(fis);

            return properties;
        } catch (Exception e) {
            throw new EsapiJndiException("Exception during loading esapi properties using JNDI name", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.warn(e);
                }
            }
        }
    }
}
