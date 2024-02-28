/*******************************************************************************
 * * $Id:: RepresentativeTest.java 157237 2013-12-02 17:00:12Z velasca           $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.person;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class RepresentativeTest {

	private static final String SKIP_PROPERTY = "representedPartyKindCode";
	private static final String ECONOMIC_CONNECTION_NATURE = "economicConnectionNature";
	private static final String TEXT = "representedPartyKindText";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Representative.class, SKIP_PROPERTY);
	}

	@Test
	public void testEquals() {
		Representative representative = new Representative();
		representative.setRepresentativeKind(RepresentativeKind.EMPLOYEE);
		representative.setEconomicConnectionIndicator(true);
		representative.setEconomicConnectionNature(ECONOMIC_CONNECTION_NATURE);
		representative.setRepresentedPartyKindText(TEXT);
		representative
				.setRepresentedPartyKindCode(RepresentedPartyKindCode.OWNER);

		Representative representative2 = new Representative();
		representative2.setRepresentativeKind(RepresentativeKind.EMPLOYEE);
		representative2.setEconomicConnectionIndicator(true);
		representative2.setEconomicConnectionNature(ECONOMIC_CONNECTION_NATURE);
		representative2.setRepresentedPartyKindText(TEXT);
		representative2
				.setRepresentedPartyKindCode(RepresentedPartyKindCode.OWNER);

		assertEquals(representative.getRepresentativeKind(),
				representative2.getRepresentativeKind());
		assertEquals(representative.isEconomicConnectionIndicator(),
				representative2.isEconomicConnectionIndicator());
		assertEquals(representative.getEconomicConnectionNature(),
				representative2.getEconomicConnectionNature());
		assertEquals(representative.getRepresentedPartyKindText(),
				representative2.getRepresentedPartyKindText());
		assertEquals(representative.getRepresentedPartyKindCode(),
				representative.getRepresentedPartyKindCode());
	}
}
