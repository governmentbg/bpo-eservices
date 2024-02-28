package eu.ohim.sp.common.security.esapi;

import org.owasp.esapi.SecurityConfiguration;
import org.owasp.esapi.reference.DefaultSecurityConfiguration;

/**
 * Used to load esapi configuration using JNDI name. Its purpose is to enable different applications deployed in Tomcat to use different esapi.properties file.
 *
 * In order to do it 2 variables needs to be defined:
 * - JNDI name containing project specific file name {@link EsapiJndiConstants.JNDI_NAME}
 * - system property containing properties file location {@link EsapiJndiConstants.SP_CONFIG_SYSTEM_PROPERTY}
 *
 * In order to use this class as Esapi configuration loader you need to run tomcat with additional variable:
 * <pre>
 *      -Dorg.owasp.esapi.SecurityConfiguration=EsapiJndiConfiguration
 * </pre>
 *
 * If JNDI loading fails it fallbacks to default loading strategy defined in {@link DefaultSecurityConfiguration}
 *
 * @author Maciej Walkowiak
 */
public class EsapiJndiConfiguration extends DefaultSecurityConfiguration {
    private static volatile SecurityConfiguration instance = null;

    private static EsapiJndiPropertiesLoader esapiJndiPropertiesLoader = new EsapiJndiPropertiesLoader(
            EsapiJndiConstants.JNDI_NAME,
            System.getProperty(EsapiJndiConstants.SP_CONFIG_SYSTEM_PROPERTY)
    );

    public static synchronized SecurityConfiguration getInstance() {
        if ( instance == null ) {
            try {
                instance = new DefaultSecurityConfiguration(esapiJndiPropertiesLoader.loadProperties());
            } catch (EsapiJndiException e) {
                instance = new DefaultSecurityConfiguration();
            }
        }
        return instance;
    }
}
