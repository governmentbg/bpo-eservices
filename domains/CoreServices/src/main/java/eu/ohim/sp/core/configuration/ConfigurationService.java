/*
 *  ConfigurationService:: ConfigurationService 07/10/13 21:16 karalch $
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

import eu.ohim.sp.core.domain.configuration.ConfigurationParameter;

/**
 * Local interface for accessing core system configuration service
 * @author jaraful
 *
 */
public interface ConfigurationService {

    /**
     * Returns the value related to the key specified and the component that makes
     * the call to the method
     *
     * @param key the name of the key
     * @param component the name of the component that makes the call
     * @return the value related to the key
     */
    public String getValue(String key, String component);

    /**
     * Returns the collection of values related to the key specified and the
     * component that makes the call to the method
     *
     * @param key the name of the key
     * @param component the name of the component that makes the call
     * @return the collection of values related to the key
     */
    public Collection<String> getValueList(String key, String component);

    /**
     * Returns the XML string related to the key specified and the component that
     * makes the call to the method
     *
     * @param key the name of the key
     * @param component the name of the component that makes the call
     * @return the XML string related to the key
     */
    public String getXml(String key, String component);

    /**
     * Returns the Java Object related to the key specified and the component that
     * makes the call to the method
     * @param key the name of the key
     * @param component the name of the component that makes the call
     * @param clazz of the object that will expect
     * @return the Java Object related to the key
     */
    public <T> T getObject(String key, String component, Class<T> clazz);

    /**
	 * Returns all the configParams of a module
	 * @param module
	 * @return Map of configParam - value
	 */
    Map<String, ConfigurationParameter> getAllConfigParams(String module);

	/**
	 * Creates a new Configuration Parameter in the database
	 * 
	 * @param configurationParameter the component object information to be inserted in the DDBB
	 * @return true if saved in the database, otherwise false.
	 */
	boolean setConfigurationParameter(ConfigurationParameter configurationParameter);
	
	/**
	 * Creates a list Configuration Parameters in the database
	 * 
	 * @param configurationParameter the component object information to be inserted in the DDBB
	 * @return true if saved in the database, otherwise false.
	 */
	boolean setConfigurationParameterList(Collection<ConfigurationParameter> configurationParameter);

	/**
	 * Returns the history list of ConfigParam elements.
	 * 
	 * @param name the name of the ConfigParam element
	 * @param comp the component
	 * @return the list of ConfigParams.
	 */
	Collection<ConfigurationParameter> getHistory(String name, String comp);

	/**
	 * Checks whether a service is enabled or disabled in the general component
	 * @param service the name of the service
	 * @return true if active, false otherwise.
	 */
	boolean isServiceEnabled(String service);

    /**
     * Checks whether a service is enabled or disabled in a specific component
     * @param service the name of the service
     * @param component the name of the component
     * @return true if active, false otherwise.
     */
    boolean isServiceEnabled(String service, String component);

	/**
	 * Returns the String related to the key specified and the component that
	 * makes the call to the method
	 * @param key the name of the key
	 * @param component the name of the component that makes the call
	 * @return the String related to the key
	 */
	String getFileContent(String key, String component);
}
