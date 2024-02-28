/*******************************************************************************
 * * $Id:: TaxonomyConceptNodeTest.java 157090 2013-12-02 11:54:19Z velasca      $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification.wrapper;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class TaxonomyConceptNodeTest {
	private static final Integer LEVEL = 1;
	private static final Integer NICE_CLASS = 2;
	private static final Integer NUM_TERMS = 3;
	private static final String PARENT_ID = "parentId";
	private static final String TEXT = "text";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(TaxonomyConceptNode.class);
	}

	@Test
	public void testEquals() {
		TaxonomyConceptNode taxonomyConceptNode1 = new TaxonomyConceptNode();
		taxonomyConceptNode1.setLeaf(true);
		taxonomyConceptNode1.setLevel(LEVEL);
		taxonomyConceptNode1.setNiceClass(NICE_CLASS);
		taxonomyConceptNode1.setNonTaxonomyParent(false);
		taxonomyConceptNode1.setNumTermsSatisfyingCriteria(NUM_TERMS);
		taxonomyConceptNode1.setParentId(PARENT_ID);
		taxonomyConceptNode1
				.setTaxonomyConceptNodeId("TAXONOMY_CONCEPT_NODE_ID");
		taxonomyConceptNode1.setText(TEXT);

		TaxonomyConceptNode taxonomyConceptNode2 = new TaxonomyConceptNode();
		taxonomyConceptNode2.setLeaf(true);
		taxonomyConceptNode2.setLevel(LEVEL);
		taxonomyConceptNode2.setNiceClass(NICE_CLASS);
		taxonomyConceptNode2.setNonTaxonomyParent(false);
		taxonomyConceptNode2.setNumTermsSatisfyingCriteria(NUM_TERMS);
		taxonomyConceptNode2.setParentId(PARENT_ID);
		taxonomyConceptNode2
				.setTaxonomyConceptNodeId("TAXONOMY_CONCEPT_NODE_ID");
		taxonomyConceptNode2.setText(TEXT);

		assertEquals(taxonomyConceptNode1.isLeaf(),
				taxonomyConceptNode2.isLeaf());
		assertEquals(taxonomyConceptNode1.getLevel(),
				taxonomyConceptNode2.getLevel());
		assertEquals(taxonomyConceptNode1.getNiceClass(),
				taxonomyConceptNode2.getNiceClass());
		assertEquals(taxonomyConceptNode1.isNonTaxonomyParent(),
				taxonomyConceptNode2.isNonTaxonomyParent());
		assertEquals(taxonomyConceptNode1.getNumTermsSatisfyingCriteria(),
				taxonomyConceptNode2.getNumTermsSatisfyingCriteria());
		assertEquals(taxonomyConceptNode1.getParentId(),
				taxonomyConceptNode2.getParentId());
		assertEquals(taxonomyConceptNode1.getTaxonomyConceptNodeId(),
				taxonomyConceptNode2.getTaxonomyConceptNodeId());
		assertEquals(taxonomyConceptNode1.getText(),
				taxonomyConceptNode2.getText());
	}

	@Test
	public void testToString() {
		TaxonomyConceptNode taxonomyConceptNode = new TaxonomyConceptNode();
		taxonomyConceptNode.setLeaf(true);
		taxonomyConceptNode.setLevel(LEVEL);
		taxonomyConceptNode.setNiceClass(NICE_CLASS);
		taxonomyConceptNode.setNonTaxonomyParent(false);
		taxonomyConceptNode.setNumTermsSatisfyingCriteria(NUM_TERMS);
		taxonomyConceptNode.setParentId(PARENT_ID);
		taxonomyConceptNode
				.setTaxonomyConceptNodeId("TAXONOMY_CONCEPT_NODE_ID");
		taxonomyConceptNode.setText(TEXT);

		assertEquals(
				taxonomyConceptNode.toString(),
				"TaxonomyConceptNode [id=" + taxonomyConceptNode.getId()
						+ ", taxonomyConceptNodeId="
						+ taxonomyConceptNode.getTaxonomyConceptNodeId()
						+ ", parentId=" + taxonomyConceptNode.getParentId()
						+ ", text=" + taxonomyConceptNode.getText()
						+ ", level=" + taxonomyConceptNode.getLevel()
						+ ", numTermsSatisfyingCriteria="
						+ taxonomyConceptNode.getNumTermsSatisfyingCriteria()
						+ ", niceClass=" + taxonomyConceptNode.getNiceClass()
						+ ", nonTaxonomyParent="
						+ taxonomyConceptNode.isNonTaxonomyParent() + ", leaf="
						+ taxonomyConceptNode.isLeaf() + "]");
	}
}
