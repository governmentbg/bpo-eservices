/*
 *  SystemConfigurationServiceDao:: ConfigParamDAOIT 15/11/13 19:30 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.configuration.dao;

import eu.ohim.sp.common.path.AbsolutePathFileResolutionStrategy;
import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.configuration.converter.ConfigParamConverter;
import eu.ohim.sp.core.configuration.entity.Component;
import eu.ohim.sp.core.domain.configuration.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.exception.Nestable;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author karalch
 */
//@RunWith(Arquillian.class)
public class ConfigParamDAOIT {

	@Deployment
	public static Archive<?> createDeployment() {
		// or jar packaging...
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
				.addPackage(ConfigurationParameter.class.getPackage())
				.addPackage(ConfigParamDAO.class.getPackage())
				.addPackage(ConfigParamConverter.class.getPackage())
				.addPackage(Component.class.getPackage())
				.addPackage(HashCodeBuilder.class.getPackage())
				.addClass(StringUtils.class)
				.addPackage(DateUtil.class.getPackage())
				.addPackage(FileUtils.class.getPackage())
				.addPackage(TrueFileFilter.class.getPackage())
				.addPackage(ByteArrayOutputStream.class.getPackage())
				.addPackage(Validate.class.getPackage())
				.addPackage(Nestable.class.getPackage())
				.addPackage(AbsolutePathFileResolutionStrategy.class.getPackage())

				//.addAsManifestResource("application-params-loading.sql", "application-params-loading.sql")
				.addAsManifestResource("test-persistence.xml", "persistence.xml")
				.addAsManifestResource("META-INF/mysql/orm.xml", "orm.xml")
				.addAsManifestResource("test-absolute-beans.xml", "beans.xml")
				.addAsManifestResource("jbossas-ds.xml");


		return jar;
	}

	@PersistenceContext
	EntityManager em;

	@Inject @ConfigFromFile
	ConfigParamDAO configParamDAO;

	@Inject
	UserTransaction utx;

	@Before
	public void preparePersistenceTest() throws Exception {
		System.out.println("Before...");
		clearData();
		System.out.println("Clear...");
		insertData();
		System.out.println("Insert...");
		startTransaction();
	}

	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("delete from DSETTINGVALUE").executeUpdate();
		em.createQuery("delete from DCONFIGSETTING").executeUpdate();
		em.createQuery("delete from PCONFIGPARAM").executeUpdate();
		em.createQuery("delete from CCOMPONENT").executeUpdate();
		utx.commit();
	}

	private void insertData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Inserting records...");
		em.createNativeQuery("insert into CCOMPONENT (IdComponent, NmComponent) values (4, 'general')").executeUpdate();

		em.createNativeQuery("insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) " +
				"values (7, 'number', 0, 'minimum.characters.autocomplete.trademark', 0, 4, null)").executeUpdate();

		em.createNativeQuery("insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) " +
				"values (7, 7, null, '2013-10-10', '2014-01-01', '2012-10-10')").executeUpdate();
		em.createNativeQuery("insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) " +
				"values (7, '30',  7, '2013-10-10')").executeUpdate();

		em.createNativeQuery("insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) " +
				"values (10, 7, null, '2014-01-01', null, '2012-10-10')").executeUpdate();
		em.createNativeQuery("insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) " +
				"values (10, '3',  10, '2014-01-01')").executeUpdate();

		em.createNativeQuery("insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (8, 'The number of characters to be entered before autocomplete service for searching a mark is invoked.', 0, 'minimum.characters.autocomplete.eservices', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0)").executeUpdate();

		em.createNativeQuery("insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (8, 8, null, '2050-10-10', '2070-10-10', '2012-10-10')").executeUpdate();
		em.createNativeQuery("insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (8, '3',  8, '2012-10-10')").executeUpdate();

		em.createNativeQuery("insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (9, 8, null, '2010-10-10', '2050-10-9', '2012-10-10')").executeUpdate();
		em.createNativeQuery("insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (9, '6',  9, '2012-10-10')").executeUpdate();

		em.createNativeQuery("insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (61, 'Configuration for the oneform sections and fields', 0, 'oneform-conf', 2, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'))").executeUpdate();
		em.createNativeQuery("insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (61, 61, null, '2012-10-10', null, '2012-10-10')").executeUpdate();
		em.createNativeQuery("insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (76, './target/test-classes/conf/COMMON/ct-address-kind.xml', 61, '2012-10-10')").executeUpdate();

		em.createNativeQuery("insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (70, 'Configuration of Country list', 0, 'country-config', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'))").executeUpdate();
		em.createNativeQuery("insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (70, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'country-config'), null, '2012-10-10', null, '2012-10-10')").executeUpdate();
		em.createNativeQuery("insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (87, './target/test-classes/conf/EM/countries.xml', (select IdParam from PCONFIGPARAM where NmParam = 'country-config'), '2012-10-10')").executeUpdate();

		em.createNativeQuery("insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (71, 'Configuration of Nationality list', 0, 'nationality-config', 3, 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'))").executeUpdate();
		em.createNativeQuery("insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (71, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'nationality-config'), null, '2012-10-10', null, '2012-10-10')").executeUpdate();
		em.createNativeQuery("insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (88, '<nationalities xmlns=\"http://www.example.org/nationality-configuration\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.example.org/nationality-configuration nationality-configuration.xsd \"><nationality code=\"\" isRepresentative=\"true\" isApplicant=\"true\">selectBox.SELECT</nationality></nationalities>', (select IdParam from PCONFIGPARAM where NmParam = 'nationality-config'), '2012-10-10')").executeUpdate();

		em.createNativeQuery("insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (9, 'The number of characters to be entered before autocomplete service for searching a mark is invoked.', 0, 'eservices.reports.list', 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0)").executeUpdate();
		em.createNativeQuery("insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (11, 9, null, '2050-10-10', '2070-10-10', null)").executeUpdate();
		em.createNativeQuery("insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (99, '55',  11, '2012-10-10')").executeUpdate();
		em.createNativeQuery("insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (100, '66',  11, '2012-10-10')").executeUpdate();


		em.createNativeQuery("insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) " +
				"values (600, 'number', 0, 'minimum.characters.autocomplete.history', 0, 4, 0)").executeUpdate();

		em.createNativeQuery("insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) " +
				"values (600, 600, null, '2012-10-10', null, '2012-10-10')").executeUpdate();
		em.createNativeQuery("insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) " +
				"values (600, '5',  600, '2012-10-10')").executeUpdate();

		em.createNativeQuery("insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) " +
				"values (601, 600, null, '2012-10-10', null, '2012-10-10')").executeUpdate();
		em.createNativeQuery("insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) " +
				"values (601, '3',  601, '2012-10-10')").executeUpdate();


		utx.commit();
		// clear the persistence context (first-level cache)
		em.clear();
	}

	@After
	public void commitTransaction() throws Exception {
		utx.commit();
	}


	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}

	//@Test
	public void testFindConfigParamValue() {
		String configParam =
				configParamDAO.findConfigParamValue("minimum.characters.autocomplete.trademark", "general");
		// then
		System.out.println("Found " + configParam + " component (using JPQL):");
		Assert.assertEquals(configParam, "3");
	}

	//@Test
	public void testFindConfigParamValueNotExistent() {
		String configParam =
				configParamDAO.findConfigParamValue("minimum.characters.autocomplete.design", "general");
		// then
		System.out.println("Found " + configParam + " component (using JPQL):");
		Assert.assertNull(configParam);
	}

	//@Test
	public void testFindConfigParamValueComponentNotExistent() {
		String configParam =
				configParamDAO.findConfigParamValue("minimum.characters.autocomplete.design", "generic");
		// then
		System.out.println("Found " + configParam + " component (using JPQL):");
		Assert.assertNull(configParam);
	}


	//@Test
	public void testFindConfigParamValueComponentMultipleConfiguration() {
		String configParam =
				configParamDAO.findConfigParamValue("minimum.characters.autocomplete.eservices", "general");
		// then
		System.out.println("Found " + configParam + " component (using JPQL):");
		//TODO fix unexpected response
		Assert.assertEquals(configParam, "6");
	}

	//@Test
	public void testFindConfigParamXMLNotExistent() {
		String configParam =
				configParamDAO.findConfigParamXML("minimum.characters.autocomplete.trademark", "general");
		// then
		System.out.println("Found " + configParam + " component (using JPQL):");
		Assert.assertNull(configParam);
	}

	//@Test
	public void testFindConfigParamXML() {
		String configParam =
				configParamDAO.findConfigParamXML("oneform-conf", "general");

		// then
		System.out.println("Found " + configParam + " component (using JPQL):");
		Assert.assertNotNull(configParam);
		Assert.assertTrue(configParam.contains("cotypes"));
	}

	//@Test
	public void testFindConfigParamObject() {
		String configParam =
				configParamDAO.findConfigParamObject("country-config", "general");

		// then
		System.out.println("Found " + configParam.substring(0, 4) + " component (using JPQL):");
		Assert.assertNotNull(configParam);
		Assert.assertTrue(configParam.contains("test") || configParam.contains("countries"));
	}

	//@Test
	public void testFindConfigParamObjectXML() {
		String configParam =
				configParamDAO.findConfigParamObject("nationality-config", "general");

		// then
		System.out.println("Found " + configParam + " component (using JPQL):");
		Assert.assertNotNull(configParam);
		Assert.assertTrue(configParam.contains("nationalities"));
	}


	//@Test
	public void testFindConfigParamObjectAsXML() {
		String configParam =
				configParamDAO.findConfigParamXML("country-config", "general");

		// then
		System.out.println("Found " + configParam + " component (using JPQL):");
		Assert.assertNull(configParam);
	}


	//@Test
	public void testSetConfigParamSimpleConfigurationParameterUpdate() throws Exception {
		SimpleConfigurationParameter configurationParameter = new SimpleConfigurationParameter();
		configurationParameter.setName("minimum.characters.autocomplete.trademark");
		configurationParameter.setComponent("general");
		configurationParameter.setConfigSettingId(Long.parseLong("7"));
		try {
			configurationParameter.setValidfrom(new SimpleDateFormat("yyyy-mm-dd").parse("2014-1-2"));
		} catch (ParseException e) {

		}
		configurationParameter.setValue("25");

		boolean updated =
				configParamDAO.setConfigParam(configurationParameter);
		utx.commit();
		// then
		System.out.println("Result : " + em.createNativeQuery("select c.* from PCONFIGPARAM c, DCONFIGSETTING d, DSETTINGVALUE v where c.IdParam = d.IdParam and v.IdConfigSetting = d.IdConfigSetting and NmParam = 'minimum.characters.autocomplete.trademark'").getResultList().size());
		utx.begin();
		em.joinTransaction();

		// then
		Assert.assertEquals(updated, true);
		Assert.assertEquals(configParamDAO.findConfigParamValue("minimum.characters.autocomplete.trademark", "general"), "25");
	}

	//@Test
	public void testSetConfigParamObjectConfigurationParameterUpdate() throws Exception {
		ObjectConfigurationParameter configurationParameter = new ObjectConfigurationParameter("country-config", "general", "test");
		configurationParameter.setConfigSettingId(Long.parseLong("70"));
		try {
			configurationParameter.setValidfrom(new SimpleDateFormat("yyyy-mm-dd").parse("2013-10-10"));
		} catch (ParseException e) {

		}
		System.out.println("============================================");
		System.out.println("============================================");
		System.out.println("============================================");
		System.out.println("test : " + configurationParameter);

		boolean updated =
				configParamDAO.setConfigParam(configurationParameter);
		utx.commit();
		// then
		System.out.println("Result : " + em.createNativeQuery("select c.* from PCONFIGPARAM c, DCONFIGSETTING d, DSETTINGVALUE v where c.IdParam = d.IdParam and v.IdConfigSetting = d.IdConfigSetting and NmParam = 'minimum.characters.autocomplete.trademark'").getResultList().size());
		utx.begin();
		em.joinTransaction();

		//then
		Assert.assertEquals(updated, true);
	}

	//@Test
	public void testSetConfigParamObjectConfigurationParameterUpdateNew() throws Exception {
		ObjectConfigurationParameter configurationParameter = new ObjectConfigurationParameter("country-config", "general", "test");
		try {
			configurationParameter.setValidfrom(new SimpleDateFormat("yyyy-mm-dd").parse("2013-10-10"));
		} catch (ParseException e) {

		}
		System.out.println("============================================");
		System.out.println("============================================");
		System.out.println("============================================");
		System.out.println("test : " + configurationParameter);

		boolean updated =
				configParamDAO.setConfigParam(configurationParameter);
		utx.commit();
		// then
		System.out.println("Result : " + em.createNativeQuery("select c.* from PCONFIGPARAM c, DCONFIGSETTING d, DSETTINGVALUE v where c.IdParam = d.IdParam and v.IdConfigSetting = d.IdConfigSetting and NmParam = 'minimum.characters.autocomplete.trademark'").getResultList().size());
		utx.begin();
		em.joinTransaction();

		// then
		Assert.assertEquals(updated, true);
		System.out.println("testSetConfigParamObjectConfigurationParameterUpdateNew : " + configParamDAO.getConfigParamHistory("country-config", "general").size());

	}

	//@Test
	public void testSetConfigParamSimpleConfigurationParameterNewMissingValidFrom() {
		SimpleConfigurationParameter configurationParameter = new SimpleConfigurationParameter();
		configurationParameter.setName("minimum.characters.autocomplete.design");
		configurationParameter.setComponent("general");
		configurationParameter.setValue("25");

		boolean updated = configParamDAO.setConfigParam(configurationParameter);

		// then
		Assert.assertEquals(true, updated);
		Assert.assertEquals(configParamDAO.findConfigParamValue("minimum.characters.autocomplete.design", "general"), null);
	}

	//@Test
	public void testSetConfigParamList() throws Exception {
		ListConfigurationParameter configurationParameter = new ListConfigurationParameter();
		configurationParameter.setName("eservices.reports.list");
		configurationParameter.setComponent("general");
		configurationParameter.setValue(new ArrayList<String>());
		configurationParameter.getValue().add("25");
		configurationParameter.getValue().add("26");
		configurationParameter.getValue().add("27");
		configurationParameter.setValidfrom(new SimpleDateFormat("yyyy-mm-dd").parse("2014-01-01"));
		configurationParameter.setConfigSettingId(new Long(11));

		boolean updated = configParamDAO.setConfigParam(configurationParameter);

		utx.commit();
		// then
		System.out.println("Result : " + em.createNativeQuery("select v.TxtValue from PCONFIGPARAM c, DCONFIGSETTING d, DSETTINGVALUE v where c.IdParam = d.IdParam and v.IdConfigSetting = d.IdConfigSetting and NmParam = 'minimum.characters.autocomplete.trademark'").getResultList().get(0));
		System.out.println("Result : " + em.createNativeQuery("select c.* from PCONFIGPARAM c, DCONFIGSETTING d, DSETTINGVALUE v where c.IdParam = d.IdParam and v.IdConfigSetting = d.IdConfigSetting and NmParam = 'minimum.characters.autocomplete.trademark'").getResultList().size());
		System.out.println("Result : " + em.createNativeQuery("select c.* from PCONFIGPARAM c, DCONFIGSETTING d, DSETTINGVALUE v where c.IdParam = d.IdParam and v.IdConfigSetting = d.IdConfigSetting and NmParam = 'minimum.characters.autocomplete.trademark'").getResultList().size());
		System.out.println("Result : " + em.createNativeQuery("select c.* from PCONFIGPARAM c, DCONFIGSETTING d, DSETTINGVALUE v where c.IdParam = d.IdParam and v.IdConfigSetting = d.IdConfigSetting and NmParam = 'minimum.characters.autocomplete.trademark'").getResultList().size());
		utx.begin();
		em.joinTransaction();

		// then
		List<String> values = configParamDAO.findConfigParamValueList("eservices.reports.list", "general");
		Assert.assertEquals(updated, true);
		Assert.assertEquals(values.size(), 3);

		Assert.assertTrue(values.contains("25"));
		Assert.assertTrue(values.contains("26"));
		Assert.assertTrue(values.contains("27"));
	}


	//@Test
	public void testGetConfigParamHistory() {

		System.out.println(configParamDAO.findActiveEntry("minimum.characters.autocomplete.trademark", "general", ConfigParamType.SIMPLE).getValues().get(0).getParamvalue());
		//Collection<ConfigurationParameter> parameters = configParamDAO.getConfigParamHistory("minimum.characters.autocomplete.history", "general");
		//Assert.assertEquals(parameters.size(), 2);
	}

}
