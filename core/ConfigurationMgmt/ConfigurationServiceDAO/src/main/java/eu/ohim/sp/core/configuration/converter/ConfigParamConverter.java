/*
 *  SystemConfigurationServiceDao:: ConfigParamConverter 16/10/13 20:35 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.configuration.converter;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.domain.configuration.*;
import eu.ohim.sp.core.configuration.entity.*;
import org.apache.commons.io.FileUtils;
import static org.apache.commons.lang.StringUtils.*;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: KARALCH
 * Date: 07/10/13
 * Time: 12:03
 * To change this template use File | Settings | File Templates.
 */
public final class ConfigParamConverter {

    private static final Logger LOGGER = Logger.getLogger(ConfigParamConverter.class);
    public static final String UTF_8 = "UTF-8";

    private ConfigParamConverter() {
    }

    public static boolean isNotEmpty(Collection collection) {
        return (collection!=null && !collection.isEmpty());
    }

    /**
     * Returns the ConfigParam object with the information of the
     * ConfigurationParameter object
     *
     * @param configurationParameter the object with the information to be transformed
     * @return the ConfigParam object with the information
     */
    public static ConfigParam convertDomain2Entity(ConfigurationParameter configurationParameter) {
        ConfigParam configParam = new ConfigParam();

        //Set the configSetting
        ConfigSetting configSetting = new ConfigSetting();
        configSetting.setId(configurationParameter.getConfigSettingId());
        configSetting.setModifiedBy(configurationParameter.getModifiedBy());
        configSetting.setValidfrom(configurationParameter.getValidfrom());
        configSetting.setValidto(configurationParameter.getValidto());
        configSetting.setValues(new ArrayList<ConfigParamValue>());

        if (configurationParameter instanceof SimpleConfigurationParameter) {
            if (!initializeSimpleValue((SimpleConfigurationParameter) configurationParameter, configSetting)) {
                return null;
            }
        } else if (configurationParameter instanceof ListConfigurationParameter) {
            if (!initializeListValues((ListConfigurationParameter) configurationParameter, configSetting)) {
                return null;
            }
        } else if (configurationParameter instanceof ObjectConfigurationParameter) {
            if (!initializeObjectValue((ObjectConfigurationParameter) configurationParameter, configSetting)) {
                return null;
            }
        }
        //Set the component
        Component component = new Component();
        component.setName(configurationParameter.getComponent());

        // Sets the information
        configParam.setDescription(configurationParameter.getDescription());
        configParam.setHidden(configurationParameter.isHidden());
        configParam.setName(configurationParameter.getName());
        configParam.setParamtype(configurationParameter.getParamtype());
        configParam.setEntries(new ArrayList<ConfigSetting>());
        configParam.getEntries().add(configSetting);

        configParam.setComponent(component);

        return configParam;
    }

    private static boolean initializeObjectValue(ObjectConfigurationParameter configurationParameter, ConfigSetting configSetting) {
        boolean initialized = false;
        if (configurationParameter.getValue() != null
                || isNotBlank(configurationParameter.getXml())) {
            configSetting.getValues().add(new ConfigParamValue((configurationParameter.getValue()!=null && configurationParameter.getClazz() != null) ?
                    convertObject2XML(configurationParameter.getValue(), configurationParameter.getClazz()):
                    configurationParameter.getXml(), new Date()));
            initialized = true;
        }
        return initialized;
    }

    private static boolean initializeListValues(ListConfigurationParameter configurationParameter, ConfigSetting configSetting) {
        boolean initialized = false;
        if (isNotEmpty(configurationParameter.getValue())) {
            for (String value : configurationParameter.getValue()) {
                configSetting.getValues().add(new ConfigParamValue(value, new Date()));
            }
            initialized = true;
        }
        return initialized;
    }

    private static boolean initializeSimpleValue(SimpleConfigurationParameter configurationParameter, ConfigSetting configSetting) {
        boolean initialized = false;
        if (isNotBlank(configurationParameter.getValue())) {
            configSetting.getValues().add(
                    new ConfigParamValue(configurationParameter.getValue(), new Date()));
            initialized = true;
        }
        return initialized;
    }

    /**
     * Returns the ConfigParam object with the information of the
     * ConfigurationParameter object
     *
     * @param configParam the object with the information to be transformed
     * @return the ConfigParam object with the information
     */
    public static List<ConfigurationParameter> convertEntity2Domain(ConfigParam configParam) {
        List<ConfigurationParameter> configurationParameters = new ArrayList<ConfigurationParameter>();
        Validate.isTrue(configParam != null
                && isNotEmpty(configParam.getEntries()),  "Not any entries were found for the parameter");

        if (configParam!=null) {
            for (ConfigSetting configSetting : configParam.getEntries()) {
                ConfigurationParameter configurationParameter = null;

                if (configSetting != null && isNotEmpty(configSetting.getValues())) {
                    if (configParam.getParamtype() == ConfigParamType.LIST) {
                        configurationParameter = getListConfigurationParameter(configParam, configSetting);
                    } else if (configParam.getParamtype() == ConfigParamType.OBJECT
                            || configParam.getParamtype() == ConfigParamType.XML) {
                        Validate.isTrue(configSetting.getValues().size() == 1, "Unexpected data found");
                        try {
                            configurationParameter = getObjectConfigurationParameter(configParam, configSetting);
                        } catch (IOException e) {
                            throw new SPException("Failed to read the xml", e);
                        }
                    } else {
                        Validate.isTrue(configSetting.getValues().size() == 1, "Unexpected data found");
                        configurationParameter = new SimpleConfigurationParameter(configParam.getName(),
                                configParam.getComponent().getName(), configSetting.getValues().get(0).getParamvalue());
                    }
                }

                if (configurationParameter!=null) {
                    // Fills the default fields
                    configurationParameter.setDescription(configParam.getDescription());
                    configurationParameter.setHidden(configParam.isHidden());
                    if (configSetting!=null) {
                        configurationParameter.setModifiedBy(configSetting.getModifiedBy());
                        configurationParameter.setValidfrom(configSetting.getValidfrom());
                        configurationParameter.setValidto(configSetting.getValidto());
                        configurationParameter.setConfigSettingId(configSetting.getId());
                    }
                    configurationParameters.add(configurationParameter);
                }
            }
        }

        return configurationParameters;
    }

    /**
     * It generates an {@link ObjectConfigurationParameter} with its data
     * @param configParam the related {@link ConfigParam} stored in database
     * @param configSetting the related {@link ConfigSetting} stored in database
     * @return the generated {@link ObjectConfigurationParameter}
     * @throws IOException
     */
    private static ConfigurationParameter getObjectConfigurationParameter(ConfigParam configParam, ConfigSetting configSetting) throws IOException {
        String confParamValue = configSetting.getValues().get(0).getParamvalue();
        String xml = configParam.getParamSource() != null
                && configParam.getParamSource() == ConfigParamSource.FILE_CONTENTS
                ? FileUtils.readFileToString(new File(confParamValue), UTF_8)
                : confParamValue;
        ConfigurationParameter configurationParameter = new ObjectConfigurationParameter(configParam.getName(),
                configParam.getComponent().getName(), xml);
        //It should be careful, normally by passing xml it is set xml type
        configurationParameter.setParamtype(configParam.getParamtype());
        return configurationParameter;
    }

    /**
     * It generates an {@link ListConfigurationParameter} with its data
     * @param configParam the related {@link ConfigParam} stored in database
     * @param configSetting the related {@link ConfigSetting} stored in database
     * @return the generated {@link ListConfigurationParameter}
     */
    private static ConfigurationParameter getListConfigurationParameter(ConfigParam configParam, ConfigSetting configSetting) {
        ListConfigurationParameter configurationParameter = new ListConfigurationParameter(configParam.getName(),
                configParam.getComponent().getName(), new ArrayList<String>());
        for (ConfigParamValue value : configSetting.getValues()) {
            configurationParameter.getValue().add(value.getParamvalue());
        }
        return configurationParameter;
    }

    /**
     * Creates an object with JAXB with an XML and a class given.
     *
     * @param xml   the object in xml mode
     * @param clazz the class of the object
     * @return the object transformed. If class missmatch, null
     */
    public static <T> T convertXML2Object(String xml, Class<T> clazz) {
        try {
            // Creates the object with the class pattern,
            // XML info and JAXB Library
            StringReader sr = new StringReader(xml);
            StreamSource sc = new StreamSource(sr);
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return (T) unmarshaller.unmarshal(sc);
        } catch (JAXBException jaxbException) {
            LOGGER.debug("ERROR. SystemConfigurationService. convertXML2Object. Error unmarshalling the object.");
            throw new SPException("Failed to convert xml", jaxbException, "error.generic");
        }
    }

    public static <T> String convertObject2XML(Object object, Class<T> clazz) {
        try {
            // Creates the object with the class pattern,
            // XML info and JAXB Library
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Marshaller marshaller = jc.createMarshaller();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            marshaller.marshal(object, output);
            return output.toString("UTF-8");
        } catch (JAXBException jaxbException) {
            LOGGER.debug("ERROR. SystemConfigurationService. convertXML2Object. Error unmarshalling the object.");
            throw new SPException("Failed to convert xml", jaxbException, "error.generic");
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            LOGGER.debug("ERROR. SystemConfigurationService. convertXML2Object. Error unmarshalling the object.");
            throw new SPException("Failed to convert xml", unsupportedEncodingException, "error.generic");
        }
    }

}