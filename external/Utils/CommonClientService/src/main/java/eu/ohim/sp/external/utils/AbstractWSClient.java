/*******************************************************************************
 * * $Id:: AbstractWSClient.java 15017 2012-11-06 16:13:54Z villama $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.external.utils;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters;
import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters.Adapter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Abstract class that should be extended by all the adapter classes which
 * invoke an ESB Exceptions are managed in this class so it is not necessary to
 * manage the Exceptions in the Child class.
 *
 * @author virgida
 */
public abstract class AbstractWSClient {
    /** Logger for this class and subclasses. */
    private static final Logger LOGGER = Logger.getLogger(AbstractWSClient.class);

    /** The Constant KEY_CONFIGURATION_SERVICE. */
    public static final String KEY_CONFIGURATION_SERVICE = "service.adapters.list";

    /** The adapter enabled. */
    private URL wsdlLocation;

    /** The adapter enabled. */
    private boolean adapterEnabled = false;

    /**
     * Gets whether adapter is enabled
     *
     * @return boolean true if it's enabled, false if it isn't
     */
    public boolean getAdapterEnabled() {
        return adapterEnabled;
    }

    /**
     * Gets the wsdl location
     *
     * @return the wsdl location
     */
    public URL getWsdlLocation() {
        return wsdlLocation;
    }

    /** The Constant COMPONENT_CONFIGURATION_SERVICE. */
    public static final String COMPONENT_CONFIGURATION_SERVICE = "general";

    /**
     * Inits the clients for webservices. Checks the destination url and
     * and if they are enabled or not
     *
     * @param idAdapter
     *            the id adapter
     */
    protected void init(String idAdapter) {
        Adapters adaptersConf = this.getConfigurationService().getObject(KEY_CONFIGURATION_SERVICE,
                COMPONENT_CONFIGURATION_SERVICE, Adapters.class);
        if (adaptersConf!=null
                && adaptersConf.getAdapter()!=null
                && !adaptersConf.getAdapter().isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("initialization of the adapter service idAdapter=" + idAdapter);
            }

            List<Adapter> adapters = adaptersConf.getAdapter();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Read the adapter list");
                if (adapters != null) {
                    LOGGER.debug("Adapters size=" + adapters.size());
                } else {
                    LOGGER.debug("Adapters is null");
                }
            }

            if(adapters != null){
                for (Adapter adapter : adapters) {
                    if (StringUtils.equals(adapter.getName(), idAdapter)) {
                        checkAdapter(adapter);
                    }
                }
            }
        } else {
            throw new SPException("NOT VALID CONFIGURATION ADAPTER : " + adaptersConf);
        }
    }

    private void checkAdapter(Adapter adapter){
        adapterEnabled = adapter.isEnabled();
        String wsdlUrl = adapter.getWsdlLocation();
        if (StringUtils.isNotBlank(wsdlUrl)) {
            try {
                wsdlLocation = new URL(wsdlUrl);
            } catch(MalformedURLException e){
                throw new SPException("THE WSDL LOCATION FOR ADAPTER "+adapter.getName() +": "+wsdlUrl +" DOES NOT EXISTS", e);
            }
        } else {
            throw new SPException("THE WSDL LOCATION HAS NOT BEEN DEFINED FOR ADAPTER "+adapter.getName());
        }
        LOGGER.info("esb client name=" + adapter.getName() + " isEnabled=" + adapterEnabled + " adapterURL= "
                + wsdlUrl);
    }

    /**
     * Abstract method that must be implemented by all the subclasses It defines
     * the error code used to throw the FSPException.
     *
     * @return the error code to be thrown by the FSPException
     */
    protected abstract String getErrorCode();

    /**
     * Gets the system configuration service.
     *
     * @return the system configuration service
     */
    protected abstract ConfigurationService getConfigurationService();


}
