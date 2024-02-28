/*
 *  SystemConfigurationServiceDao:: ConfigParamDAOTest 14/11/13 11:31 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.configuration.dao;

import eu.ohim.sp.core.configuration.entity.*;
import eu.ohim.sp.core.domain.configuration.ConfigParamSource;
import eu.ohim.sp.core.domain.configuration.ConfigParamType;
import eu.ohim.sp.core.domain.configuration.ConfigurationParameter;
import eu.ohim.sp.core.domain.configuration.SimpleConfigurationParameter;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class ConfigParamDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private ConfigParamDbDAO configParamDAO;

    @Mock
    Query mockQuery;

    @Mock
    private FileOperations fileOperations;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetValueNull() {
        prepareConfigParam();

        String value = configParamDAO.findConfigParamValue("parameter", "component");

        Assert.assertNull(value);
    }

    @Test
    public void testGetValue() {
        ConfigSetting configSetting = prepareConfigParam();
        configSetting.getValues().get(0).setParamvalue("value");

        String value = configParamDAO.findConfigParamValue("parameter", "component");

        Assert.assertEquals(value, "value");
    }

    @Test
    public void testGetXML() {
        ConfigSetting configSetting = prepareConfigParam();
        configSetting.getConfigParam().setParamSource(ConfigParamSource.PATH);
        configSetting.getValues().get(0).setParamvalue("value");

        String value = configParamDAO.findConfigParamXML("parameter", "component");


        Assert.assertEquals(value, "value");
    }

    @Test
    public void testGetFileLoadedFromFileSystem() throws Exception {
        // given
        final String fileContent = "fileContent";
        ConfigSetting configSetting = prepareConfigParam();
        configSetting.setConfigParam(new ConfigParam());
        configSetting.getConfigParam().setParamSource(ConfigParamSource.FILE_CONTENTS);
        configSetting.getValues().get(0).setParamvalue("value");

        when(fileOperations.loadFileContent(eq("value"))).thenReturn(fileContent);

        // when
        String result = configParamDAO.findConfigParamXML("parameter", "component");

        // then
        Assert.assertEquals(fileContent, result);
    }

    @Test
    public void testGetObject() throws IOException, URISyntaxException {
        URL url = ClassLoader.getSystemResource("conf/EM/countries.xml");
        String file = FileUtils.readFileToString(new File(url.toURI()), "UTF-8");

        ConfigSetting configSetting = prepareConfigParam();
        configSetting.setConfigParam(new ConfigParam());
        configSetting.getConfigParam().setParamSource(ConfigParamSource.FILE_CONTENTS);
        configSetting.getValues().get(0).setParamvalue("conf/EM/countries.xml");
        when(fileOperations.loadFileContent(eq("conf/EM/countries.xml"))).thenReturn(file);

        String value = configParamDAO.findConfigParamObject("parameter", "component");

        Assert.assertEquals(value, file);
    }

    @Test
    public void testGetObjectContent() throws IOException, URISyntaxException {
        URL url = ClassLoader.getSystemResource("conf/EM/countries.xml");
        String file = url.toURI().getPath();

        ConfigSetting configSetting = prepareConfigParam();

        configSetting.getConfigParam().setParamSource(ConfigParamSource.FILE_CONTENTS);
        configSetting.getValues().get(0).setParamvalue(file);

        when(fileOperations.loadFileContent(eq(url.toURI().getPath()))).thenReturn("any string");

        String value = configParamDAO.findConfigParamObject("parameter", "component");

        verify(fileOperations).loadFileContent(eq(url.toURI().getPath()));

        assertThat(value, is("any string"));
    }


    @Test
    public void testConfigParamHistory() throws IOException, URISyntaxException {
        URL url = ClassLoader.getSystemResource("conf/EM/countries.xml");
        String file = url.toURI().getPath();

        ConfigParam configParam = new ConfigParam();
        configParam.setComponent(new Component());
        configParam.getComponent().setName("general");
        configParam.setParamSource(ConfigParamSource.FILE_CONTENTS);
        configParam.setParamtype(ConfigParamType.SIMPLE);
        configParam.setEntries(new ArrayList<ConfigSetting>());
        configParam.getEntries().add(new ConfigSetting());
        configParam.getEntries().get(0).setValues(new ArrayList<ConfigParamValue>());
        configParam.getEntries().get(0).getValues().add(new ConfigParamValue());
        configParam.getEntries().get(0).getValues().get(0).setParamvalue("value1");
        configParam.getEntries().add(new ConfigSetting());
        configParam.getEntries().get(1).setValues(new ArrayList<ConfigParamValue>());
        configParam.getEntries().get(1).getValues().add(new ConfigParamValue());
        configParam.getEntries().get(1).getValues().get(0).setParamvalue("value2");

        when(em.createNamedQuery( eq("ConfigParam.findConfigParam"))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("name"), eq("parameter"))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("component"), eq("component"))).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(configParam);

        Collection<ConfigurationParameter> values = configParamDAO.getConfigParamHistory("parameter", "component");

        Assert.assertEquals(values.size(), 2);
        Iterator iterator = values.iterator();
        Assert.assertEquals(((SimpleConfigurationParameter) iterator.next()).getValue(), "value1");
        Assert.assertEquals(((SimpleConfigurationParameter) iterator.next()).getValue(), "value2");

    }

    @Test
    public void testGetValueList() {
        ConfigSetting configSetting = prepareConfigParam();
        configSetting.getConfigParam().setParamtype(ConfigParamType.LIST);
        configSetting.getValues().get(0).setParamvalue("value");

        List<String> values = configParamDAO.findConfigParamValueList("parameter", "component");

        Assert.assertEquals(values.size(), 1);
        Assert.assertEquals(values.get(0), "value");

    }

    @Test
    public void testGetValueMultipleResults() {
        when(em.createNamedQuery(eq("filterActiveEntries"))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("name"), eq("parameter"))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("component"), eq("component"))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("type"), eq(ConfigParamType.LIST))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("currentDate"), any(Date.class))).thenReturn(mockQuery);

        when(mockQuery.getSingleResult()).thenThrow(NonUniqueResultException.class);

        List<String> values = configParamDAO.findConfigParamValueList("parameter", "component");

        Assert.assertNull(values);
    }

    @Test
    public void testGetValueNoResultExceptionA() {
        when(em.createNamedQuery( eq("filterActiveEntries"))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("name"), eq("parameter"))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("component"), eq("component"))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("type"), any(ConfigParamType.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("currentDate"), any(Date.class))).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(new ArrayList());

        List<String> values = configParamDAO.findConfigParamValueList("parameter", "component");

        Assert.assertNull(values);
    }

    private ConfigSetting prepareConfigParam() {
        return prepareConfigParam(false);
    }

    private ConfigSetting prepareConfigParam(boolean history) {
        when(em.createNamedQuery( eq("filterActiveEntries"))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("name"), eq("parameter"))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("component"), eq("component"))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("type"), any(ConfigParamType.class))).thenReturn(mockQuery);
        if (!history) {
            when(mockQuery.setParameter(eq("currentDate"), any(Date.class))).thenReturn(mockQuery);
        }

        ConfigSetting configSetting = new ConfigSetting();
        configSetting.setConfigParam(new ConfigParam());
        configSetting.setValues(new ArrayList<ConfigParamValue>());
        configSetting.getValues().add(new ConfigParamValue());

        List<ConfigSetting> entries = new ArrayList<ConfigSetting>();
        entries.add(configSetting);

        when(mockQuery.getResultList()).thenReturn(entries);

        return configSetting;
    }

}
