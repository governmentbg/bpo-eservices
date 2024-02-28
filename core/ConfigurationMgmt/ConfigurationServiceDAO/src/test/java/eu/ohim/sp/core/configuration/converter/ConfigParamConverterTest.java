package eu.ohim.sp.core.configuration.converter;

import eu.ohim.sp.core.configuration.entity.Component;
import eu.ohim.sp.core.configuration.entity.ConfigParam;
import eu.ohim.sp.core.configuration.entity.ConfigParamValue;
import eu.ohim.sp.core.configuration.entity.ConfigSetting;
import eu.ohim.sp.core.domain.configuration.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 30/01/14
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */
public class ConfigParamConverterTest {

    @Test
    public void testConvertEntity2DomainSimple() {
        ConfigParam configParam = new ConfigParam();
        configParam.setParamtype(ConfigParamType.SIMPLE);
        configParam.setComponent(new Component());
        configParam.getComponent().setName("general");
        configParam.setName("trademark.autocomplete");
        configParam.setDescription("description");
        configParam.setHidden(false);
        configParam.setEntries(new ArrayList<ConfigSetting>());
        configParam.getEntries().add(new ConfigSetting());
        configParam.getEntries().get(0).setId(new Long(1));
        configParam.getEntries().get(0).setModifiedBy("me");
        configParam.getEntries().get(0).setValidfrom(new Date());

        configParam.getEntries().get(0).setValues(new ArrayList<ConfigParamValue>());
        configParam.getEntries().get(0).getValues().add(new ConfigParamValue());
        configParam.getEntries().get(0).getValues().get(0).setParamvalue("value");

        List<ConfigurationParameter> configurationParameter = ConfigParamConverter.convertEntity2Domain(configParam);

        Assert.assertTrue(configurationParameter.get(0) instanceof SimpleConfigurationParameter);
        Assert.assertTrue(configurationParameter.size()==1);
        Assert.assertEquals(configurationParameter.get(0).getName(), configParam.getName());
        Assert.assertEquals(configurationParameter.get(0).getComponent(), configParam.getComponent().getName());
        Assert.assertEquals(configurationParameter.get(0).getDescription(), configParam.getDescription());
        Assert.assertEquals(configurationParameter.get(0).getParamtype(), configParam.getParamtype());
        Assert.assertEquals(configurationParameter.get(0).getConfigSettingId(), configParam.getEntries().get(0).getId());
        Assert.assertEquals(((SimpleConfigurationParameter) configurationParameter.get(0)).getValue(), configParam.getEntries().get(0).getValues().get(0).getParamvalue());
        Assert.assertEquals(configurationParameter.get(0).getModifiedBy(), configParam.getEntries().get(0).getModifiedBy());
        Assert.assertEquals(configurationParameter.get(0).getValidfrom(), configParam.getEntries().get(0).getValidfrom());
        Assert.assertEquals(configurationParameter.get(0).getValidto(), configParam.getEntries().get(0).getValidto());

    }


    @Test
    public void testConvertEntity2DomainMultipleEntries() {
        ConfigParam configParam = new ConfigParam();
        configParam.setParamtype(ConfigParamType.LIST);
        configParam.setComponent(new Component());
        configParam.getComponent().setName("general");
        configParam.setName("trademark.autocomplete");
        configParam.setDescription("description");
        configParam.setHidden(false);
        configParam.setEntries(new ArrayList<ConfigSetting>());
        configParam.getEntries().add(new ConfigSetting());
        configParam.getEntries().get(0).setModifiedBy("me");
        configParam.getEntries().get(0).setValidfrom(new Date());

        configParam.getEntries().add(new ConfigSetting());
        configParam.getEntries().get(1).setModifiedBy("you");
        configParam.getEntries().get(1).setValidfrom(new GregorianCalendar(2025, 1, 1).getTime());

        configParam.getEntries().get(0).setValues(new ArrayList<ConfigParamValue>());
        configParam.getEntries().get(0).getValues().add(new ConfigParamValue());
        configParam.getEntries().get(0).getValues().get(0).setParamvalue("value 1");
        configParam.getEntries().get(0).getValues().add(new ConfigParamValue());
        configParam.getEntries().get(0).getValues().get(1).setParamvalue("value 2");


        configParam.getEntries().get(1).setValues(new ArrayList<ConfigParamValue>());
        configParam.getEntries().get(1).getValues().add(new ConfigParamValue());
        configParam.getEntries().get(1).getValues().get(0).setParamvalue("value 2");
        configParam.getEntries().get(1).getValues().add(new ConfigParamValue());
        configParam.getEntries().get(1).getValues().get(1).setParamvalue("value 3");

        List<ConfigurationParameter> configurationParameter = ConfigParamConverter.convertEntity2Domain(configParam);

        Assert.assertTrue(configurationParameter.get(0) instanceof ListConfigurationParameter);
        Assert.assertTrue(configurationParameter.size()==2);
        Assert.assertEquals(configurationParameter.get(0).getName(), configParam.getName());
        Assert.assertEquals(configurationParameter.get(0).getComponent(), configParam.getComponent().getName());
        Assert.assertEquals(configurationParameter.get(0).getDescription(), configParam.getDescription());
        Assert.assertEquals(configurationParameter.get(0).getParamtype(), configParam.getParamtype());
        Assert.assertEquals(configurationParameter.get(0).getModifiedBy(), configParam.getEntries().get(0).getModifiedBy());
        Assert.assertEquals(configurationParameter.get(0).getValidfrom(), configParam.getEntries().get(0).getValidfrom());
        Assert.assertEquals(configurationParameter.get(0).getValidto(), configParam.getEntries().get(0).getValidto());
        //We don't care about the order of the returned values
        Assert.assertTrue(((ListConfigurationParameter) configurationParameter.get(0)).getValue().contains(configParam.getEntries().get(0).getValues().get(0).getParamvalue()));
        Assert.assertTrue(((ListConfigurationParameter) configurationParameter.get(0)).getValue().contains(configParam.getEntries().get(0).getValues().get(1).getParamvalue()));
        Assert.assertTrue(((ListConfigurationParameter) configurationParameter.get(1)).getValue().contains(configParam.getEntries().get(1).getValues().get(0).getParamvalue()));
        Assert.assertTrue(((ListConfigurationParameter) configurationParameter.get(1)).getValue().contains(configParam.getEntries().get(1).getValues().get(1).getParamvalue()));

    }


    @Test
    public void testConvertEntity2DomainList() {
        ConfigParam configParam = new ConfigParam();
        configParam.setParamtype(ConfigParamType.LIST);
        configParam.setComponent(new Component());
        configParam.getComponent().setName("general");
        configParam.setName("reports.list");
        configParam.setDescription("description");
        configParam.setHidden(false);
        configParam.setEntries(new ArrayList<ConfigSetting>());
        configParam.getEntries().add(new ConfigSetting());
        configParam.getEntries().get(0).setModifiedBy("me");
        configParam.getEntries().get(0).setValidfrom(new Date());

        configParam.getEntries().get(0).setValues(new ArrayList<ConfigParamValue>());
        configParam.getEntries().get(0).getValues().add(new ConfigParamValue());
        configParam.getEntries().get(0).getValues().get(0).setParamvalue("value 1");
        configParam.getEntries().get(0).getValues().add(new ConfigParamValue());
        configParam.getEntries().get(0).getValues().get(1).setParamvalue("value 2");

        List<ConfigurationParameter> configurationParameter = ConfigParamConverter.convertEntity2Domain(configParam);

        Assert.assertTrue(configurationParameter.get(0) instanceof ListConfigurationParameter);
        Assert.assertTrue(configurationParameter.size()==1);
        Assert.assertEquals(configurationParameter.get(0).getName(), configParam.getName());
        Assert.assertEquals(configurationParameter.get(0).getComponent(), configParam.getComponent().getName());
        Assert.assertEquals(configurationParameter.get(0).getDescription(), configParam.getDescription());
        Assert.assertEquals(configurationParameter.get(0).getParamtype(), configParam.getParamtype());
        //We don't care about the order of the returned values
        Assert.assertTrue(((ListConfigurationParameter) configurationParameter.get(0)).getValue().contains(configParam.getEntries().get(0).getValues().get(0).getParamvalue()));
        Assert.assertTrue(((ListConfigurationParameter) configurationParameter.get(0)).getValue().contains(configParam.getEntries().get(0).getValues().get(1).getParamvalue()));
        Assert.assertEquals(configurationParameter.get(0).getModifiedBy(), configParam.getEntries().get(0).getModifiedBy());
        Assert.assertEquals(configurationParameter.get(0).getValidfrom(), configParam.getEntries().get(0).getValidfrom());
        Assert.assertEquals(configurationParameter.get(0).getValidto(), configParam.getEntries().get(0).getValidto());

    }

    @Test
    public void testConvertEntity2DomainObject() {
        ConfigParam configParam = new ConfigParam();
        configParam.setParamtype(ConfigParamType.OBJECT);
        configParam.setComponent(new Component());
        configParam.getComponent().setName("general");
        configParam.setName("beans.xml");
        configParam.setDescription("description");
        configParam.setHidden(false);
        configParam.setEntries(new ArrayList<ConfigSetting>());
        configParam.getEntries().add(new ConfigSetting());
        configParam.getEntries().get(0).setModifiedBy("me");
        configParam.getEntries().get(0).setValidfrom(new Date());

        configParam.getEntries().get(0).setValues(new ArrayList<ConfigParamValue>());
        configParam.getEntries().get(0).getValues().add(new ConfigParamValue());
        configParam.getEntries().get(0).getValues().get(0).setParamvalue("<?xml version=\"1.0\" encoding=\"UTF-8\"?> <beans xmlns=\"http://java.sun.com/xml/ns/javaee\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee       http://java.sun.com/xml/ns/javaee/beans_1_0.xsd\"> </beans>");

        List<ConfigurationParameter> configurationParameter = ConfigParamConverter.convertEntity2Domain(configParam);

        Assert.assertTrue(configurationParameter.get(0) instanceof ObjectConfigurationParameter);
        Assert.assertTrue(configurationParameter.size()==1);
        Assert.assertEquals(configurationParameter.get(0).getName(), configParam.getName());
        Assert.assertEquals(configurationParameter.get(0).getComponent(), configParam.getComponent().getName());
        Assert.assertEquals(configurationParameter.get(0).getDescription(), configParam.getDescription());
        Assert.assertEquals(configurationParameter.get(0).getParamtype(), configParam.getParamtype());
        Assert.assertEquals(((ObjectConfigurationParameter) configurationParameter.get(0)).getXml(), configParam.getEntries().get(0).getValues().get(0).getParamvalue());
        Assert.assertEquals(configurationParameter.get(0).getModifiedBy(), configParam.getEntries().get(0).getModifiedBy());
        Assert.assertEquals(configurationParameter.get(0).getValidfrom(), configParam.getEntries().get(0).getValidfrom());
        Assert.assertEquals(configurationParameter.get(0).getValidto(), configParam.getEntries().get(0).getValidto());

    }


    @Test(expected = IllegalArgumentException.class)
    public void testConvertEntity2DomainSimpleWithMultipleValues() {
        ConfigParam configParam = new ConfigParam();
        configParam.setParamtype(ConfigParamType.SIMPLE);
        configParam.setComponent(new Component());
        configParam.getComponent().setName("general");
        configParam.setName("trademark.autocomplete");
        configParam.setDescription("description");
        configParam.setHidden(false);
        configParam.setEntries(new ArrayList<ConfigSetting>());
        configParam.getEntries().add(new ConfigSetting());
        configParam.getEntries().get(0).setModifiedBy("me");
        configParam.getEntries().get(0).setValidfrom(new Date());

        configParam.getEntries().get(0).setValues(new ArrayList<ConfigParamValue>());
        configParam.getEntries().get(0).getValues().add(new ConfigParamValue());
        configParam.getEntries().get(0).getValues().get(0).setParamvalue("value");
        configParam.getEntries().get(0).getValues().add(new ConfigParamValue());

        ConfigParamConverter.convertEntity2Domain(configParam);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testConvertEntity2DomainNoEntries() {
        ConfigParam configParam = new ConfigParam();
        configParam.setParamtype(ConfigParamType.SIMPLE);
        configParam.setComponent(new Component());
        configParam.getComponent().setName("general");
        configParam.setName("trademark.autocomplete");
        configParam.setDescription("description");
        configParam.setHidden(false);
        ConfigParamConverter.convertEntity2Domain(configParam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertEntity2DomainNoComponent() {
        ConfigParam configParam = new ConfigParam();
        configParam.setParamtype(ConfigParamType.SIMPLE);
        configParam.setName("trademark.autocomplete");
        configParam.setDescription("description");
        configParam.setHidden(false);

        ConfigParamConverter.convertEntity2Domain(configParam);
        configParam.getEntries().add(new ConfigSetting());
        configParam.getEntries().get(0).setModifiedBy("me");
        configParam.getEntries().get(0).setValidfrom(new Date());

        configParam.getEntries().get(0).setValues(new ArrayList<ConfigParamValue>());
        configParam.getEntries().get(0).getValues().add(new ConfigParamValue());
        configParam.getEntries().get(0).getValues().get(0).setParamvalue("value");
    }



}

