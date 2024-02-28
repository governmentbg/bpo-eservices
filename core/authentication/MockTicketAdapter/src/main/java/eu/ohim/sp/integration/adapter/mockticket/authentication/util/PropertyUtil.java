package eu.ohim.sp.integration.adapter.mockticket.authentication.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	

	public static final String MOCK_USERS_PROPERTIES_NAME= "mockUsers.properties";

 
    public static Properties readProperties(String fileName) throws IOException {
        Properties configProp = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream in = loader.getResourceAsStream(fileName);
        configProp.load(in);
        return configProp;
    }

}
