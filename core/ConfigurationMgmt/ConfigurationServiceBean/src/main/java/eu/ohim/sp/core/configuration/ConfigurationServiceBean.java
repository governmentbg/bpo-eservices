/*
 *  SystemConfigurationService:: SystemConfigurationService 04/11/13 12:30 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.configuration;

import java.util.Collection;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import eu.ohim.sp.core.configuration.converter.ConfigParamConverter;
import eu.ohim.sp.core.configuration.dao.ConfigFromFile;
import eu.ohim.sp.core.configuration.dao.ConfigParamDAO;
import eu.ohim.sp.core.domain.configuration.ConfigurationParameter;
import eu.ohim.sp.core.domain.configuration.ObjectConfigurationParameter;
import org.apache.log4j.Logger;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.domain.services.xsd.ServiceType;
import eu.ohim.sp.core.configuration.domain.services.xsd.Services;


@Interceptors(ExceptionHandlingInterceptor.class)
@Stateless
public class ConfigurationServiceBean implements ConfigurationServiceRemote, ConfigurationServiceLocal {

    private static final Logger LOGGER = Logger.getLogger(ConfigurationServiceBean.class);

    @Inject
    @ConfigFromFile
    private ConfigParamDAO confParamDao;

    @Override
    public Map<String, ConfigurationParameter> getAllConfigParams(String module) {
        return confParamDao.getConfigParamByModule(module);
    }

    /**
     * Returns the value related to the key specified and the component that
     * makes the call to the method
     *
     * @param key
     *            the name of the key
     * @param component
     *            the name of the component that makes the call
     * @return the value related to the key
     */
    @Override
    public String getValue(String key, String component) {
        LOGGER.debug("Retrieving value, key : " + key + ", value : " + component);
        return confParamDao.findConfigParamValue(key, component);
    }

    /**
     * Returns the collection of values related to the key specified and the
     * component that makes the call to the method
     *
     * @param key
     *            the name of the key
     * @param component
     *            the name of the component that makes the call
     * @return the collection of values related to the key
     */
    @Override
    public Collection<String> getValueList(String key, String component) {
        LOGGER.debug("Retrieving list of values, key : " + key + ", value : " + component);
        return confParamDao.findConfigParamValueList(key, component);
    }

    /**
     * Returns the XML string related to the key specified and the component
     * that makes the call to the method
     *
     * @param key
     *            the name of the key
     * @param component
     *            the name of the component that makes the call
     * @return the XML string related to the key
     */
    @Override
    public String getXml(String key, String component) {
        LOGGER.debug("Retrieving xml, key : " + key + ", value : " + component);
        return confParamDao.findConfigParamXML(key, component);
    }

    /**
     * Returns the Java Object related to the key specified and the component
     * that makes the call to the method
     *
     * @param key
     *            the name of the key
     * @param component
     *            the name of the component that makes the call
     * @return the Java Object related to the key
     */
    @Override
    public <T> T getObject(String key, String component, Class<T> clazz) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Retrieving object, key: " + key + ", component: " + component);
        }
        String objectXML = confParamDao.findConfigParamObject(key, component);
        if(objectXML == null) {
            throw new SPException("Key not found in configuration: " + key + " and component: "+component);
        }
        return ConfigParamConverter.convertXML2Object(objectXML, clazz);
    }

    /**
     * Creates a new Configuration Parameter in the database
     *
     * @param configurationParameter
     *            the component object information to be inserted in the DDBB
     * @return true if saved in the database, otherwise false.
     */
    @Override
    public boolean setConfigurationParameter(ConfigurationParameter configurationParameter) {
        if (configurationParameter instanceof ObjectConfigurationParameter) {
            ((ObjectConfigurationParameter) configurationParameter).setXml(ConfigParamConverter.convertObject2XML(((ObjectConfigurationParameter) configurationParameter).getValue(),
                    ((ObjectConfigurationParameter) configurationParameter).getClazz()));
        }
        return confParamDao.setConfigParam(configurationParameter);
    }

    /**
     * Creates a list Configuration Parameters in the database
     *
     * @param configurationParameter
     *            the component object information to be inserted in the DDBB
     * @return true if saved in the database, otherwise false.
     */
    @Override
    public boolean setConfigurationParameterList(Collection<ConfigurationParameter> configurationParameter) {
        return confParamDao.setConfigParamList(configurationParameter);
    }

    /**
     * Returns the history list of ConfigParam elements.
     *
     * @param name
     *            the name of the ConfigParam element
     * @param comp
     *            the component
     * @return the list of ConfigParams.
     */
    @Override
    public Collection<ConfigurationParameter> getHistory(String name, String comp) {
        return confParamDao.getConfigParamHistory(name, comp);
    }

    @Override
    public boolean isServiceEnabled(String service) {
        return isServiceEnabled(service,"general");
    }

    @Override
    public boolean isServiceEnabled(String service, String component) {
        Services services = getObject("enabled-services", component, Services.class);
        for (ServiceType serviceType : services.getService()) {
            if (serviceType.getName() != null
                    && serviceType.getName().value().equals(service)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Service " + service + " : " + serviceType.isEnabled());
                }
                return serviceType.isEnabled();
            }
        }
        return false;
    }

    /**
     * Returns the String related to the key specified and the component
     * that makes the call to the method
     *
     * @param key the name of the key
     * @param component the name of the component that makes the call
     * @return the String related to the key
     */
    @Override
    public String getFileContent(String key, String component) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Retrieving object, key: " + key + ", component: " + component);
        }
        String stringContent = confParamDao.getFileContent(key, component);
        return stringContent;
    }
}