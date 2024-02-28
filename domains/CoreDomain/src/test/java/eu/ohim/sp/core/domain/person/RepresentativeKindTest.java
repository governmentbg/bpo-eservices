/*******************************************************************************
 * * $Id:: RepresentativeKindTest.java 157237 2013-12-02 17:00:12Z velasca       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.person;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

/**
 * @author ionitdi
 */
public class RepresentativeKindTest {
	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(RepresentativeKind.class);
	}

	@Test
	public void testValues() {
		RepresentativeKind representativeKindAssociation = RepresentativeKind.ASSOCIATION;
		RepresentativeKind representativeKindEmployee = RepresentativeKind.EMPLOYEE;
		RepresentativeKind representativeKindEmployeeEconomic = RepresentativeKind.EMPLOYEE_WITH_ECONOMIC_CONNECTIONS;
		RepresentativeKind representativeKindLawyer = RepresentativeKind.LAWYER;
		RepresentativeKind representativeKindOther = RepresentativeKind.OTHER;
		RepresentativeKind representativeKindProfessional = RepresentativeKind.PROFESSIONAL_REPRESENTATIVE;
		RepresentativeKind representativeKindUndefined = RepresentativeKind.UNDEFINED;

		assertEquals(representativeKindAssociation.value(),
				RepresentativeKind.ASSOCIATION.value());
		assertEquals(representativeKindEmployee.value(),
				RepresentativeKind.EMPLOYEE.value());
		assertEquals(representativeKindEmployeeEconomic.value(),
				RepresentativeKind.EMPLOYEE_WITH_ECONOMIC_CONNECTIONS.value());
		assertEquals(representativeKindLawyer.value(),
				RepresentativeKind.LAWYER.value());
		assertEquals(representativeKindOther.value(),
				RepresentativeKind.OTHER.value());
		assertEquals(representativeKindProfessional.value(),
				RepresentativeKind.PROFESSIONAL_REPRESENTATIVE.value());
		assertEquals(representativeKindUndefined.value(),
				RepresentativeKind.UNDEFINED.value());

		assertEquals(representativeKindAssociation.toString(),
				RepresentativeKind.ASSOCIATION.toString());
		assertEquals(representativeKindEmployee.toString(),
				RepresentativeKind.EMPLOYEE.toString());
		assertEquals(representativeKindEmployeeEconomic.toString(),
				RepresentativeKind.EMPLOYEE_WITH_ECONOMIC_CONNECTIONS.toString());
		assertEquals(representativeKindLawyer.toString(),
				RepresentativeKind.LAWYER.toString());
		assertEquals(representativeKindOther.toString(),
				RepresentativeKind.OTHER.toString());
		assertEquals(representativeKindProfessional.toString(),
				RepresentativeKind.PROFESSIONAL_REPRESENTATIVE.toString());
		assertEquals(representativeKindUndefined.toString(),
				RepresentativeKind.UNDEFINED.toString());

	}
}
