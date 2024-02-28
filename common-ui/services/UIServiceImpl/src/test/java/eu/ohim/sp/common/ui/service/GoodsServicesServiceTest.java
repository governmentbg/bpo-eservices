package eu.ohim.sp.common.ui.service;
///*******************************************************************************
// * * $Id:: GoodsServicesServiceTest.java 53573 2013-01-02 14:54:04Z ionitdi      $
// * *       . * .
// * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
// * *   .   RR  R   .  in the Internal Market (trade marks and designs)
// * *   *   RRR     *
// * *    .  RR RR  .   ALL RIGHTS RESERVED
// * *     * . _ . *
// ******************************************************************************/
//package eu.ohim.sp.tmefiling.ui.service;

//////////////////////////
//////////////////////////
// TODO: please fix tests
//////////////////////////
//////////////////////////

//
//import eu.ohim.sp.core.classification.ClassificationServiceInterface;
//import eu.ohim.sp.core.domain.classification.*;
//import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyConceptNode;
//import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyCriteria;
//import eu.ohim.sp.core.domain.classification.wrapper.Term;
//import eu.ohim.sp.core.domain.classification.wrapper.TermsToBeValidated;
//import eu.ohim.sp.core.domain.trademark.ClassificationErrorEnum;
//import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
//import eu.ohim.sp.tmefiling.ui.flow.FlowBean;
//import eu.ohim.sp.tmefiling.ui.form.GoodAndServiceForm;
//import eu.ohim.sp.tmefiling.ui.form.TaxonomyConceptNodeTreeView;
//import eu.ohim.sp.tmefiling.ui.form.TermForm;
//import eu.ohim.sp.tmefiling.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.*;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:/test-context.xml"})
//public class GoodsServicesServiceTest extends GoodsServicesService {
//
//    @Autowired
//    private ConfigurationServiceDelegatorInterface configurationService;
//
//	@Autowired
//	ClassificationServiceInterface classificationService;
//
//	@Autowired
//	FlowBean flowBean;
//
//	@Test
//	public void testSearchTerms() {
//
//
//	}
//
//	@Test
//	public void testGetTaxonomy() {
//		//Testing TaxonomyUtil too
//		List<TaxonomyConceptNode> nodes = new ArrayList<TaxonomyConceptNode>();
//		TaxonomyConceptNode node = new TaxonomyConceptNode();
//		node.setId("1235");
//		node.setLeaf(false);
//		node.setLevel(3);
//		node.setNumTermsSatisfyingCriteria(100);
//		node.setParentId("1135");
//		node.setText("Whatever");
//		nodes.add(node);
//
//		node = new TaxonomyConceptNode();
//		node.setId("1335");
//		node.setLeaf(false);
//		node.setLevel(3);
//		node.setNumTermsSatisfyingCriteria(100);
//		node.setParentId("1235");
//		node.setText("Whatever");
//		nodes.add(node);
//
//		node = new TaxonomyConceptNode();
//		node.setId("1435");
//		node.setLeaf(false);
//		node.setLevel(3);
//		node.setNumTermsSatisfyingCriteria(100);
//		node.setParentId("1235");
//		node.setText("Whatever");
//		nodes.add(node);
//
//		node = new TaxonomyConceptNode();
//		node.setId("1535");
//		node.setLeaf(false);
//		node.setLevel(3);
//		node.setNumTermsSatisfyingCriteria(100);
//		node.setParentId("1235");
//		node.setText("Whatever");
//		nodes.add(node);
//
//		node = new TaxonomyConceptNode();
//		node.setId("1635");
//		node.setLeaf(false);
//		node.setLevel(3);
//		node.setNumTermsSatisfyingCriteria(100);
//		node.setParentId("1535");
//		node.setText("Whatever");
//		nodes.add(node);
//
//		node = new TaxonomyConceptNode();
//		node.setId("1735");
//		node.setLeaf(false);
//		node.setLevel(3);
//		node.setNumTermsSatisfyingCriteria(100);
//		node.setParentId("1535");
//		node.setText("Whatever");
//		nodes.add(node);
//
//		when(classificationService.getTaxonomy(any(TaxonomyCriteria.class))).thenReturn(nodes);
//		Collection<TaxonomyConceptNodeTreeView> resultedNodes = getTaxonomy("EN", "shoe", 5, "1235");
//
//		verify(classificationService, times(1)).getTaxonomy(any(TaxonomyCriteria.class));
//		assertEquals(resultedNodes.size(), 3);
//		String[] expectedNodesArray = {"1335", "1435", "1535"};
//		Set<String> expectedNodes = new HashSet<String>(Arrays.asList(expectedNodesArray));
//		for (TaxonomyConceptNodeTreeView resultedNode : resultedNodes) {
//			assertTrue(expectedNodes.contains(resultedNode.getId()));
//			if (resultedNode.getId().equals("1535")) {
//				String[] innerExpectedNodesArray = {"1635", "1735"};
//				Set<String> innerExpectedNodes = new HashSet<String>(Arrays.asList(innerExpectedNodesArray));
//				for (TaxonomyConceptNodeTreeView innerResultedNode : resultedNode.getChildren()) {
//					assertTrue(innerExpectedNodes.contains(innerResultedNode.getId()));
//				}
//			}
//		}
//
//	}
//
//	@Test
//	public void testVerifyListOfTerms() {
//		List<Term> validatedTerms = new ArrayList<Term>();
//
//		Term validatedTerm = new Term();
//		validatedTerm.setVerificationResult(VerifiedTermResult.NOT_OK);
//		validatedTerm.setLang("EN");
//		validatedTerm.setNiceClass(5);
//		validatedTerm.setText("shoes");
//		validatedTerms.add(validatedTerm);
//
//		validatedTerm = new Term();
//		validatedTerm.setVerificationResult(VerifiedTermResult.HINT);
//		validatedTerm.setLang("EN");
//		validatedTerm.setNiceClass(5);
//		validatedTerm.setText("boots");
//		validatedTerms.add(validatedTerm);
//
//		validatedTerm = new Term();
//		validatedTerm.setVerificationResult(VerifiedTermResult.NONE);
//		validatedTerm.setLang("EN");
//		validatedTerm.setNiceClass(5);
//		validatedTerm.setText("shoe laces");
//		validatedTerms.add(validatedTerm);
//
//		when(configurationService.isServiceEnabled(AvailableServices.VALIDATE_TERM.value())).thenReturn(true);
//		List<TermsToBeValidated> list=new ArrayList<TermsToBeValidated>();
//		list.add(any(TermsToBeValidated.class));
//		//when(classificationService.verifyListOfTerms(list)).thenReturn(validatedTerms);
//		GoodAndServiceForm goodAndServiceForm = verifyListOfTerms("EN", "5", "shoes;boots;shoe laces");
//		List<TermsToBeValidated> list2=new ArrayList<TermsToBeValidated>();
//		list.add(any(TermsToBeValidated.class));
//		verify(classificationService, times(1)).verifyListOfTerms(list2);
//
//		assertEquals(goodAndServiceForm.getTermForms().size(), 3);
//		ClassificationErrorEnum[] expectedErrorEnums = {ClassificationErrorEnum.HINT, ClassificationErrorEnum.NONE, ClassificationErrorEnum.NOT_OK};
//		Set<ClassificationErrorEnum> setOfExpectedErrorEnums = new HashSet<ClassificationErrorEnum>(Arrays.asList(expectedErrorEnums));
//
//		assertEquals(setOfExpectedErrorEnums.size(), 3);
//		for (TermForm termForm : goodAndServiceForm.getTermForms()) {
//			setOfExpectedErrorEnums.remove(termForm.getError().getErrorEnum());
//		}
//		assertEquals(setOfExpectedErrorEnums.size(), 0);
//	}
//
//}
