package eu.ohim.sp.external.utils;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by marcoantonioalberoalbero on 16/9/16.
 */
public class AdapterEnabled implements Serializable {

    /** The system configuration service. */
    @EJB(lookup = "java:global/configurationLocal")
    private ConfigurationService configurationService;

    /** The Constant KEY_CONFIGURATION_SERVICE. */
    public static final String KEY_CONFIGURATION_SERVICE = "service.adapters.list";

    /** The Constant COMPONENT_CONFIGURATION_SERVICE. */
    public static final String COMPONENT_CONFIGURATION_SERVICE = "general";

    /** Logger for this class and subclasses. */
    private static final Logger LOGGER = Logger.getLogger(AdapterEnabled.class);

    private static final String ADAPTER_NAME = "ADAPTER_NAME";

    @AroundInvoke
    public Object adapterEnabled(InvocationContext context) throws Exception {
        Adapters adaptersConf = configurationService.getObject(KEY_CONFIGURATION_SERVICE,
                COMPONENT_CONFIGURATION_SERVICE, Adapters.class);

        String idAdapter = (String)context.getContextData().get(ADAPTER_NAME);

        if (adaptersConf!=null
                && adaptersConf.getAdapter()!=null
                && !adaptersConf.getAdapter().isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("initialization of the adapter service idAdapter=" + idAdapter);
            }

            List<Adapters.Adapter> adapters = adaptersConf.getAdapter();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Read the adapter list");
                if (adapters != null) {
                    LOGGER.debug("Adapters size=" + adapters.size());
                } else {
                    LOGGER.debug("Adapters is null");
                }
            }

            if(adapters != null){
                for (Adapters.Adapter adapter : adapters) {
                    if (StringUtils.equals(adapter.getName(), idAdapter)) {
                        if(adapter.isEnabled()) {
                            LOGGER.debug("Adapter "+ idAdapter +" is enabled");
                            return context.proceed();
                        } else {
                            throw new SPException("Adapter "+ idAdapter +" is not enabled");
                        }
                    }
                }
                throw new SPException("Adapter "+ idAdapter +" not enabled");
            } else {
                throw new SPException("NOT VALID CONFIGURATION ADAPTER : " + adaptersConf);
            }
        } else {
            throw new SPException("NOT VALID CONFIGURATION ADAPTER : " + adaptersConf);
        }
    }
}
