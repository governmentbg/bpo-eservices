package eu.ohim.sp.core.configuration;

import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters;
import eu.ohim.sp.core.configuration.SystemConfigurationServiceRemote;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 30/06/13
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationServiceTest {

    SystemConfigurationServiceRemote configurationService = null;

    @Before
    public void setup() {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        try {
            final Context context = new InitialContext(jndiProperties);
            configurationService = (SystemConfigurationServiceRemote) context.lookup("ejb:core-configuration-management/SystemConfigurationService//SystemConfigurationService!eu.ohim.sp.core.configuration.SystemConfigurationServiceRemote");
        } catch (NamingException e) {

        }
    }

    @Test
    public void testGetObject() {
        System.out.println(configurationService.getObject("service.adapters.list", "general", Adapters.class));
    }

    @Test
    public void testGetXML() {
        System.out.println(configurationService.getXml("oneform", "general"));
    }

    @Test
    public void testGetValue() {
        System.out.println(configurationService.getValue("service.goods.classes.number", "general"));
    }

}