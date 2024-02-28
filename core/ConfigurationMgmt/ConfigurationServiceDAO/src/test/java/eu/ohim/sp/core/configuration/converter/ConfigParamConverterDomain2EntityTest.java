package eu.ohim.sp.core.configuration.converter;

import eu.ohim.sp.core.configuration.domain.colour.xsd.Colour;
import eu.ohim.sp.core.configuration.entity.ConfigParam;
import eu.ohim.sp.core.domain.configuration.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 30/01/14
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */
public class ConfigParamConverterDomain2EntityTest {
    @Test
    public void testConvertDomain2EntitySimple() {
        ConfigurationParameter configurationParameter = new SimpleConfigurationParameter("autocomplete.characters", "general", "5");
        configurationParameter.setDescription("my description");
        configurationParameter.setValidfrom(new Date());
        configurationParameter.setValidto(new GregorianCalendar(2025, 1, 1).getTime());

        ConfigParam configParam = ConfigParamConverter.convertDomain2Entity(configurationParameter);

        Assert.assertEquals(configurationParameter.getName(), configParam.getName());
        Assert.assertEquals(configurationParameter.getComponent(), configParam.getComponent().getName());
        Assert.assertEquals(configurationParameter.getDescription(), configParam.getDescription());
        Assert.assertEquals(configurationParameter.getParamtype(), configParam.getParamtype());
        Assert.assertEquals(configurationParameter.getConfigSettingId(), configParam.getId());
        Assert.assertEquals(((SimpleConfigurationParameter) configurationParameter).getValue(), configParam.getEntries().get(0).getValues().get(0).getParamvalue());
        Assert.assertEquals(configurationParameter.getModifiedBy(), configParam.getEntries().get(0).getModifiedBy());
        Assert.assertEquals(configurationParameter.getValidfrom(), configParam.getEntries().get(0).getValidfrom());
        Assert.assertEquals(configurationParameter.getValidto(), configParam.getEntries().get(0).getValidto());
    }


    @Test
    public void testConvertDomain2EntityList() {
        ConfigurationParameter configurationParameter = new ListConfigurationParameter("report.list", "general", new ArrayList<String>());
        ((ListConfigurationParameter) configurationParameter).getValue().add("report1");
        ((ListConfigurationParameter) configurationParameter).getValue().add("report2");

        configurationParameter.setDescription("my description");
        configurationParameter.setValidfrom(new Date());
        configurationParameter.setValidto(new GregorianCalendar(2025, 1, 1).getTime());

        ConfigParam configParam = ConfigParamConverter.convertDomain2Entity(configurationParameter);

        Assert.assertEquals(configurationParameter.getName(), configParam.getName());
        Assert.assertEquals(configurationParameter.getComponent(), configParam.getComponent().getName());
        Assert.assertEquals(configurationParameter.getDescription(), configParam.getDescription());
        Assert.assertEquals(configurationParameter.getParamtype(), configParam.getParamtype());
        Assert.assertEquals(configurationParameter.getConfigSettingId(), configParam.getId());
        Assert.assertEquals(configurationParameter.getModifiedBy(), configParam.getEntries().get(0).getModifiedBy());
        Assert.assertEquals(configurationParameter.getValidfrom(), configParam.getEntries().get(0).getValidfrom());
        Assert.assertEquals(configurationParameter.getValidto(), configParam.getEntries().get(0).getValidto());
        Assert.assertTrue(((ListConfigurationParameter) configurationParameter).getValue().contains(configParam.getEntries().get(0).getValues().get(0).getParamvalue()));
        Assert.assertTrue(((ListConfigurationParameter) configurationParameter).getValue().contains(configParam.getEntries().get(0).getValues().get(1).getParamvalue()));

    }

    @Test
    public void testConvertDomain2EntityXML() {
        ConfigurationParameter configurationParameter = new ObjectConfigurationParameter("report.list", "general", "xml content");
        configurationParameter.setConfigSettingId(new Long(56));

        configurationParameter.setDescription("my description");
        configurationParameter.setValidfrom(new Date());
        configurationParameter.setValidto(new GregorianCalendar(2025, 1, 1).getTime());

        ConfigParam configParam = ConfigParamConverter.convertDomain2Entity(configurationParameter);

        Assert.assertEquals(configurationParameter.getName(), configParam.getName());
        Assert.assertEquals(configurationParameter.getComponent(), configParam.getComponent().getName());
        Assert.assertEquals(configurationParameter.getDescription(), configParam.getDescription());
        Assert.assertEquals(configurationParameter.getConfigSettingId(), configParam.getEntries().get(0).getId());
        Assert.assertEquals(configurationParameter.getParamtype(), configParam.getParamtype());
        Assert.assertEquals(configurationParameter.getParamtype(), ConfigParamType.XML);
        Assert.assertEquals(configurationParameter.getModifiedBy(), configParam.getEntries().get(0).getModifiedBy());
        Assert.assertEquals(configurationParameter.getValidfrom(), configParam.getEntries().get(0).getValidfrom());
        Assert.assertEquals(configurationParameter.getValidto(), configParam.getEntries().get(0).getValidto());
        Assert.assertTrue(((ObjectConfigurationParameter) configurationParameter).getXml().equals(configParam.getEntries().get(0).getValues().get(0).getParamvalue()));
    }

    @Test
    public void testConvertDomain2EntityObject() {

        Colour colour = ConfigParamConverter.convertXML2Object("<?xml version=\"1.0\" encoding=\"UTF-8\"?> <tns:colour xmlns:tns=\"http://www.example.org/test/\" \txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \txsi:schemaLocation=\"http://www.example.org/test/ configuration-xsd/src/main/xsd/colour/colour-configuration.xsd \"> \t<colourList> \t\t<country>en</country> \t\t<values>White</values> \t\t<values>Black</values> \t\t<values>Blue</values> \t\t<values>Red</values> \t\t<values>Green</values> \t\t<values>Grey</values> \t\t<values>Yellow</values> \t\t<values>Orange</values> \t\t<values>Brown</values> \t\t<values>Dark blue</values> \t\t<values>Light blue</values> \t\t<values>Gold</values> \t\t<values>Pink</values> \t</colourList> </tns:colour>", Colour.class);

        ConfigurationParameter configurationParameter = new ObjectConfigurationParameter("report.list", "general", colour, Colour.class);
        configurationParameter.setConfigSettingId(new Long(56));

        configurationParameter.setDescription("my description");
        configurationParameter.setValidfrom(new Date());
        configurationParameter.setValidto(new GregorianCalendar(2025, 1, 1).getTime());

        ConfigParam configParam = ConfigParamConverter.convertDomain2Entity(configurationParameter);

        Assert.assertEquals(configurationParameter.getName(), configParam.getName());
        Assert.assertEquals(configurationParameter.getComponent(), configParam.getComponent().getName());
        Assert.assertEquals(configurationParameter.getDescription(), configParam.getDescription());
        Assert.assertEquals(configurationParameter.getConfigSettingId(), configParam.getEntries().get(0).getId());
        Assert.assertEquals(configurationParameter.getParamtype(), configParam.getParamtype());
        Assert.assertEquals(configurationParameter.getParamtype(), ConfigParamType.OBJECT);
        Assert.assertEquals(configurationParameter.getModifiedBy(), configParam.getEntries().get(0).getModifiedBy());
        Assert.assertEquals(configurationParameter.getValidfrom(), configParam.getEntries().get(0).getValidfrom());
        Assert.assertEquals(configurationParameter.getValidto(), configParam.getEntries().get(0).getValidto());
        Assert.assertTrue(configParam.getEntries().get(0).getValues().get(0).getParamvalue().contains("<values>Red</values>"));
    }

}
