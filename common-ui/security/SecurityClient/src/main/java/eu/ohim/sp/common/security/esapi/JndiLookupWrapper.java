package eu.ohim.sp.common.security.esapi;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Isolates loading jndi property to external class in order to make {@link EsapiJndiPropertiesLoader} testable
 */
class JndiLookupWrapper {
    private static final String JNDI_PREFIX = "java:comp/env/";

    String lookup(String jndiName) throws NamingException {
        InitialContext initialContext = new InitialContext();

        return (String) initialContext.lookup(JNDI_PREFIX + jndiName);
    }
}
