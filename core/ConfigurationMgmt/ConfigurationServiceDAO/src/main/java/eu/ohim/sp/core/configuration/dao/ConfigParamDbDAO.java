/*
 *  SystemConfigurationServiceDao:: ConfigParamDAO 14/11/13 11:31 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.configuration.dao;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.service.Dao;
import eu.ohim.sp.core.configuration.entity.ConfigParam;
import eu.ohim.sp.core.configuration.entity.ConfigParamValue;
import eu.ohim.sp.core.configuration.entity.ConfigSetting;
import eu.ohim.sp.core.domain.configuration.ConfigParamSource;
import eu.ohim.sp.core.domain.configuration.ConfigParamType;
import eu.ohim.sp.core.domain.configuration.ConfigurationParameter;
import eu.ohim.sp.core.domain.configuration.ObjectConfigurationParameter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.*;
import java.io.IOException;
import java.util.*;

import static eu.ohim.sp.core.configuration.converter.ConfigParamConverter.*;

@Dao
@ConfigFromDatabase
public class ConfigParamDbDAO {

    private static final String COMPONENT_INFO = ". Component: ";

    private static final String COMPONENT = "component";
    private static final String NAME = "name";

    @PersistenceContext(name = "ConfigurationDomain", unitName = "ConfigurationDomain", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger(ConfigParamDbDAO.class);

    @Inject
    private FileOperations fileOperations;

    private String getXMLFromDB(String name, String component, ConfigParamType typeToBeChecked) throws IOException {
        // Gets the ConfigParam object
        ConfigSetting configSetting = findActiveEntry(name, component, typeToBeChecked);
        // Gets the value
        String xml = null;

        if (configSetting!=null && isNotEmpty(configSetting.getValues())) {
            // Checks if the param type is correct
            String confParamValue = configSetting.getValues().get(0).getParamvalue();

            if (configSetting.getConfigParam().getParamSource() != null
                    && configSetting.getConfigParam().getParamSource() == ConfigParamSource.FILE_CONTENTS) {
                xml = fileOperations.loadFileContent(confParamValue);
            } else {
                xml = confParamValue;
            }
        }

        return xml;
    }

    /**
     * Returns, if exists, the ConfigParam object with the name provided.
     *
     * @param name the unique value that represents the object in the DDBB
     * @param comp the Component of this ConfigParam
     * @return the object related to the name
     */
    public ConfigSetting findActiveEntry(String name, String comp, ConfigParamType configParamType) {
        // Prepares the query
        List<ConfigSetting> entries = em.createNamedQuery("filterActiveEntries")
                .setParameter(NAME, name)
				.setParameter(COMPONENT, comp)
                .setParameter("type", configParamType)
                .setParameter("currentDate", new Date()).getResultList();
        return (isNotEmpty(entries) ? entries.get(0) : null);
    }

    /**
     * @param name
     * @param comp
     * @return
     */
    public ConfigParam findConfigParamByName(String name, String comp) {
		try {
			return (ConfigParam) em.createNamedQuery("ConfigParam.findConfigParam")
					.setParameter(NAME, name).setParameter(COMPONENT, comp)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
    }

    /**
     * Returns, if exists, the String value related to the name of the
     * ConfigParam object and Component given.
     *
     * @param name      of the ConfigParam object
     * @param component name of the Component related to the ConfigParam object
     * @return the value related
     */
    public final String findConfigParamValue(String name, String component) {
        // Gets the ConfigParam object
        ConfigSetting configSetting = findActiveEntry(name, component, ConfigParamType.SIMPLE);
        return (configSetting!=null && isNotEmpty(configSetting.getValues())
                ? configSetting.getValues().get(0).getParamvalue() : null);
    }

    /**
     * Returns, if exists, the ConfigParamValue string object list related to
     * the name of the ConfigParam object and Component given.
     *
     * @param name      of the ConfigParam object
     * @param component name of the Component related to the ConfigParam object
     * @return the list of string values
     */
    public final List<String> findConfigParamValueList(String name, String component) {
        // Gets the ConfigParam object
        ConfigSetting configSetting = findActiveEntry(name, component, ConfigParamType.LIST);
        List<String> confParamValues = null;

        // Gets the values
        if (configSetting!=null && isNotEmpty(configSetting.getValues())) {
            confParamValues = new ArrayList<String>();
            for (ConfigParamValue configParamValue : configSetting.getValues()) {
                confParamValues.add(configParamValue.getParamvalue());
            }
        }

        return confParamValues;
    }

    /**
     * Returns, if exists, the XML value related to the name of the ConfigParam
     * object and Component given.
     *
     * @param name      of the ConfigParam object
     * @param component name of the Component related to the ConfigParam object
     * @return the XML value related
     */
    public final String findConfigParamXML(String name, String component) {
        try {
            return getXMLFromDB(name, component, ConfigParamType.XML);
        } catch (IOException e) {
            LOGGER.error("Error reading file ");
            return null;
        }
    }

    /**
     * Returns, if exists, the OBJECT in the database
     *
     * @param name      the name of the ConfigParam element
     * @param component the name of the Component
     * @return the object if exists, null otherwise
     */
    public final String findConfigParamObject(String name, String component) {
        try {
            return getXMLFromDB(name, component, ConfigParamType.OBJECT);
        } catch (IOException e) {
            LOGGER.error("Error reading file ");
            return null;
        }
    }

    /**
     * Creates in the database a new ConfigParam element
     *
     * @param configurationParameter the configuration parameter to be inserted in the DDBB
     * @return true if inserted, false otherwise
     */
    public final boolean setConfigParam(ConfigurationParameter configurationParameter) {
        //It should contain name, component name, valid from should not be blank on any case
        //validto should never be earlier than validfrom
        if (configurationParameter == null || StringUtils.isBlank(configurationParameter.getComponent())
                || StringUtils.isBlank(configurationParameter.getName())) {
            return false;
        }
        // Prepares the object
        ConfigParam configParam = convertDomain2Entity(configurationParameter);
        //by default if validfrom is empty then will update the active configuration
        ConfigSetting configSetting;
        if (configurationParameter.getValidfrom() == null
                && configurationParameter.getConfigSettingId() == null) {
            configSetting = findActiveEntry(configurationParameter.getName(), configurationParameter.getComponent(), configurationParameter.getParamtype());
            configParam.getEntries().set(0, configSetting);
        } else {
            configSetting = configParam.getEntries().get(0);
        }
        ConfigParam pConfigParam = findConfigParamByName(configurationParameter.getName(), configurationParameter.getComponent());

        //Not checks about conflicts done on the validity periods, should be done on another level
        //if it is required
        if (pConfigParam != null) {
            ConfigSetting pConfigSetting;
            if (configParam.getEntries().get(0).getId() == null) {
                pConfigSetting = new ConfigSetting();
                pConfigSetting.setConfigParam(pConfigParam);
            } else {
                pConfigSetting = em.find(ConfigSetting.class, configSetting.getId());
                if (pConfigSetting.getConfigParam().getId() == null || pConfigParam.getId() == null
                        || !pConfigSetting.getConfigParam().getId().equals(pConfigParam.getId())) {
                    return false;
                }
            }
            pConfigSetting.setValidfrom(configSetting.getValidfrom());
            pConfigSetting.setValidto(configSetting.getValidto());
            pConfigSetting.setModifiedBy(configSetting.getModifiedBy());
            pConfigSetting.setDtCreated(new Date());
            //Check if the configuration parameter is object and saved as file externally
            if ((pConfigParam.getParamtype() == ConfigParamType.XML ||
                    pConfigParam.getParamtype() == ConfigParamType.OBJECT)
                    && pConfigParam.getParamSource() == ConfigParamSource.FILE_CONTENTS) {
                String filePath;
                if (pConfigSetting.getValues() != null
                        && pConfigSetting.getValues().size() == 1) {
                    filePath = pConfigSetting.getValues().get(0).getParamvalue();
                } else {
                    pConfigSetting.setValues(new ArrayList<ConfigParamValue>());
                    filePath = "target/" + pConfigParam.getName() + "-" + new Date().getTime() + ".xml";

                    ConfigParamValue pConfigParamValue = new ConfigParamValue(filePath, new Date());
                    pConfigParamValue.setConfigSetting(pConfigSetting);
                    em.persist(pConfigParamValue);
                    em.persist(pConfigSetting);
                }
                try {
                    fileOperations.saveFileContent(filePath, ((ObjectConfigurationParameter) configurationParameter).getXml());
                } catch (IOException e) {
                    throw new SPException("Failed to create the configuration", e);
                }
            } else {
                pConfigSetting.setValues(new ArrayList<ConfigParamValue>());

                for (ConfigParamValue configParamValue : configSetting.getValues()) {
                    ConfigParamValue pConfigParamValue = new ConfigParamValue(configParamValue.getParamvalue(), new Date());
                    pConfigParamValue.setConfigSetting(pConfigSetting);
                    pConfigSetting.getValues().add(pConfigParamValue);
                    em.persist(pConfigParamValue);
                }

                em.persist(pConfigSetting);
            }
        }
        return true;
    }

    /**
     * Creates a list of ConfigParam elements in the DDBB
     *
     * @param configurationParameter the collection of configuration parameters to be inserted in the DDBB
     * @return true if inserted, false otherwise
     */
    public final boolean setConfigParamList(Collection<ConfigurationParameter> configurationParameter) {
        // Loop that creates each object in the database
        for (ConfigurationParameter configurationParameterIterator : configurationParameter) {
            if (!setConfigParam(configurationParameterIterator)) {
                LOGGER.debug("ERROR. ConfigParamDAO. setConfigParam. Error saving values. Object: "
                        + configurationParameterIterator.getName() + COMPONENT_INFO
                        + configurationParameterIterator.getComponent());
            }
        }
        return true;
    }

    /**
     * Retrieves the whole history of a ConfigParam object
     *
     * @param name      the ConfigParam name
     * @param component the component name
     * @return the whole history list of ConfigParam objects
     */
    public final Collection<ConfigurationParameter> getConfigParamHistory(String name, String component) {
        ConfigParam configParam = findConfigParamByName(name, component);
        return convertEntity2Domain(configParam);
    }

    /**
     * Returns a map with {@see ConfigParamType.SIMPLE} and their values
     *
     * @param module The name of the module
     * @return map configParam - value
     */
    public final Map<String, ConfigurationParameter> getConfigParamByModule(String module) {
        Map<String, ConfigurationParameter> mapConfig = new LinkedHashMap<String, ConfigurationParameter>();
        Query query = em.createNamedQuery("ConfigParam.getConfigParamByModule")
                .setParameter(COMPONENT, module)
                .setParameter("currentDate", new Date());
        try {
            List<ConfigParam> configParamList = query.getResultList();
            for (ConfigParam configParam : configParamList) {
                List<ConfigurationParameter> configurationParameter = convertEntity2Domain(configParam);
                mapConfig.put(configurationParameter.get(0).getName(),
                        configurationParameter.get(0));
            }
        } catch (NoResultException noResultException) {
            LOGGER.debug("ERROR. ConfigParamDAO. getConfigParamByModule. No results found.");
            return null;
        }
        return mapConfig;
    }

}