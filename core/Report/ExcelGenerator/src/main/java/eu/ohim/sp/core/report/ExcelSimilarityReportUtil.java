package eu.ohim.sp.core.report;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Raya
 * 26.11.2020
 */
public class ExcelSimilarityReportUtil {

    public static final String SIMILAR_TM_PROPERTIES = "similarTM.properties";
    public static final String SLASH = "/";
    public static final String LOADING_PROPERTIES_ERROR_MESSAGE = "Error loading properties file";

    private static Logger LOGGER = Logger.getLogger(ExcelSimilarityReportUtil.class);

    public static Properties loadProperties(String resourceFolder) {
        InputStream propFile = null;
        Properties messages = new Properties();
        try {
            propFile = new FileInputStream(resourceFolder + SLASH + SIMILAR_TM_PROPERTIES);
            messages.load(propFile);
        } catch (IOException e) {
            LOGGER.error(LOADING_PROPERTIES_ERROR_MESSAGE);
            LOGGER.error(e.getMessage());
        } finally {
            if (propFile != null) {
                try {
                    propFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return messages;
    }
}
