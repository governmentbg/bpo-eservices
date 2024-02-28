package eu.ohim.sp.common.ui.controller;
///*******************************************************************************
// * * $Id:: GSControllerTest.java 53573 2013-01-02 14:54:04Z ionitdi              $
// * *       . * .
// * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
// * *   .   RR  R   .  in the Internal Market (trade marks and designs)
// * *   *   RRR     *
// * *    .  RR RR  .   ALL RIGHTS RESERVED
// * *     * . _ . *
// ******************************************************************************/
//package eu.ohim.sp.tmefiling.ui.controller;

//////////////////////////
//////////////////////////
// TODO: please fix tests
//////////////////////////
//////////////////////////

//
//import java.util.HashSet;
//import java.util.Set;
//
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.web.servlet.ModelAndView;
//
//import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
//import eu.ohim.sp.tmefiling.ui.flow.FlowBean;
//import eu.ohim.sp.tmefiling.ui.form.GoodAndServiceForm;
//import eu.ohim.sp.tmefiling.ui.form.PageForm;
//import eu.ohim.sp.tmefiling.ui.form.TermForm;
//import eu.ohim.sp.tmefiling.ui.interceptors.FlowScopeDetails;
//import eu.ohim.sp.tmefiling.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
//import eu.ohim.sp.tmefiling.ui.service.interfaces.GoodsServicesServiceInterface;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.times;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Matchers.eq;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:/test-context.xml", "classpath:/test-pojo.xml" })
//public class GSControllerTest extends GSController {
//
//	@Autowired
//	GoodAndServiceForm goodsForm;
//
//	@Autowired
//	ConfigurationServiceDelegatorInterface configurationService;
//
//	@Autowired
//	GoodsServicesServiceInterface goodsServices;
//
//	@Autowired
//	FlowBean flowBean;
//
//	@Autowired
//	FlowScopeDetails flowScopeDetails;
//
//	@Test
//	public void testSearchTerms() {
//
//		when(configurationService.getValueFromGeneralComponent(ConfigurationServiceDelegatorInterface.CONCEPT_TREE_NODES_PRE)).thenReturn("1");
//		when(configurationService.getValueFromGeneralComponent(ConfigurationServiceDelegatorInterface.RESULTS_TERMS_LIMIT)).thenReturn("15");
//		when(configurationService.isServiceEnabled(AvailableServices.SEARCH_TAXONOMY.value())).thenReturn(true);
//
//		PageForm pageForm = new PageForm();
//		pageForm.setTotalResults(100);
//
//		when(goodsServices.searchTerms(flowBean, "en", "shoes", null, null, 15, null, null, null)).thenReturn(pageForm);
//		searchTerms("shoes", null, null, null, null, null);
//
//		verify(goodsServices, times(1)).searchTerms(flowBean, "en", "shoes", null, null, 15, null, null, null);
//		verify(goodsServices, times(1)).getTaxonomy("en", "shoes", 1, null);
//	}
//
//	@Test
//	public void testAddGoodsAndServices() {
//		int initialSize = flowBean.getGoodsAndServices().size();
//
//		addTerm("Shoe buckles", null);
//		assertEquals(flowBean.getGoodsAndServices().size(), ++initialSize);
//		assertEquals(flowBean.getGoodsAndService(flowBean.getFirstLang(), "26").getTermForms().size(), 1);
//
//		addTerm("Shoe eyelets", null);
//		assertEquals(flowBean.getGoodsAndServices().size(), initialSize);
//		assertEquals(flowBean.getGoodsAndService(flowBean.getFirstLang(), "26").getTermForms().size(), 2);
//
//		removeTerm("Shoe eyelets", "26", "en");
//		assertEquals(flowBean.getGoodsAndServices().size(), initialSize);
//		assertEquals(flowBean.getGoodsAndService(flowBean.getFirstLang(), "26").getTermForms().size(), 1);
//
//	}
//
//	@Test
//	public void testAddAndRemoveTerm() {
//		addTerm("Athletics shoes", null);
//
//		// Check if it has been added
//		Set<String> gs = new HashSet<String>();
//		gs.add("Athletics shoes");
//
//		for (GoodAndServiceForm goods : flowBean.getGoodsAndServices()) {
//			for (TermForm term : goods.getTermForms()) {
//				gs.remove(term.getDescription());
//			}
//		}
//		assertEquals(gs.size(), 0);
//
//		removeTerm("Athletics shoes", "25", flowBean.getFirstLang());
//
//		// Check if the given term is removed
//		gs = new HashSet<String>();
//		gs.add("Athletics shoes");
//
//		for (GoodAndServiceForm goods : flowBean.getGoodsAndServices()) {
//			for (TermForm term : goods.getTermForms()) {
//				gs.remove(term.getDescription());
//			}
//		}
//		assertEquals(gs.size(), 1);
//
//	}
//
//	@Test
//	public void testModifyTerm() {
//
//		addTerm("Athletics shoes", null);
//
//		// Check if it has been added
//		Set<String> gs = new HashSet<String>();
//		gs.add("Athletics shoes");
//
//		for (GoodAndServiceForm goods : flowBean.getGoodsAndServices()) {
//			for (TermForm term : goods.getTermForms()) {
//				gs.remove(term.getDescription());
//			}
//		}
//		assertEquals(gs.size(), 0);
//
//		removeTerm("Athletics shoes", "25", flowBean.getFirstLang());
//
//		GoodAndServiceForm gsForm = new GoodAndServiceForm();
//		gsForm.setClassId("24");
//		gsForm.setLangId(flowBean.getFirstLang());
//		TermForm termForm = new TermForm();
//		termForm.setIdClass("24");
//		termForm.setDescription("Athletics shoes");
//
//		Set<TermForm> termForms = new HashSet<TermForm>();
//		termForms.add(termForm);
//
//		gsForm.setTermForms(termForms);
//
//		when(goodsServices.verifyListOfTerms(eq(flowBean.getFirstLang()), eq("24"), eq("Athletics shoes"))).thenReturn(gsForm);
//
//		modifyTerm("Athletics shoes", "25", flowBean.getFirstLang(), "Athletics shoes", "24");
//
//		gs = new HashSet<String>();
//		gs.add("Athletics shoes");
//
//		for (GoodAndServiceForm goods : flowBean.getGoodsAndServices()) {
//			if (goods.getClassId().equals("24")) {
//				for (TermForm term : goods.getTermForms()) {
//					gs.remove(term.getDescription());
//				}
//			}
//		}
//		assertEquals(gs.size(), 0);
//
//		gs = new HashSet<String>();
//		gs.add("Athletics shoes");
//
//		for (GoodAndServiceForm goods : flowBean.getGoodsAndServices()) {
//			if (goods.getClassId().equals("25")) {
//				for (TermForm term : goods.getTermForms()) {
//					gs.remove(term.getDescription());
//				}
//			}
//		}
//		assertEquals(gs.size(), 1);
//	}
//
//	@Test
//	public void testProvideListOnMyOwn() {
//		ModelAndView mav = provideListOnMyOwn();
//		assertEquals(mav.hasView(), true);
//	}
//
//}
