package eu.ohim.sp.common.security.esapi;

class EsapiJndiConstants {
    /**
     * JNDI environment variable name containing filename of project-specific esapi.properties. Environment variable is created ususally in META-INF/context.xml for example:
     * <pre>
     *     {@code
     *      <Context>
     *          <Environment name="esapi.path" type="java.lang.String" value="tm-efiling-esapi.properties" override="true"/>
     *      </Context>
     *     }
     * </pre>
     *
     */
    static final String JNDI_NAME = "esapiPath";

    /**
     * Environmental variable containing path to directory where esapi configuration file is located
     */
    static final String SP_CONFIG_SYSTEM_PROPERTY = "sp.config";
}
