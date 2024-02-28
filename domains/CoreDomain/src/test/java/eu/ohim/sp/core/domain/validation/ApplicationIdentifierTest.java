/*******************************************************************************
 * * $Id:: ApplicationIdentifierTest.java 157090 2013-12-02 11:54:19Z velasca    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.validation;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class ApplicationIdentifierTest {
	
	private static final String OFFICE = "EM";
	private static final String YEAR = "2013";
	private static final String CODE = "code";
	private static final String FINAL_CODE = "finalCode";
	private static final String APPLICATION_TYPE = "applicationType";
	private static final String OFFICE2 = "EM";
	private static final String YEAR2= "2013";
	private static final String CODE2 = "code";
	private static final String FINAL_CODE2 = "finalCode";
	private static final String APPLICATION_TYPE2 = "applicationType";
	
	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ApplicationIdentifier.class);
	}

	@Test
	public void testEquals() {
		ApplicationIdentifier identifier = new ApplicationIdentifier(OFFICE,
				YEAR, CODE, FINAL_CODE, APPLICATION_TYPE);

		assertEquals(identifier.getApplicationType(), APPLICATION_TYPE);
		assertEquals(identifier.getCode(), CODE);
		assertEquals(identifier.getFinalCode(), FINAL_CODE);
		assertEquals(identifier.getOffice(), OFFICE);
		assertEquals(identifier.getYear(), YEAR);
		
		identifier.setApplicationType(APPLICATION_TYPE2);
		identifier.setCode(CODE2);
		identifier.setFinalCode(FINAL_CODE2);
		identifier.setOffice(OFFICE2);
		identifier.setYear(YEAR2);
		
		assertEquals(identifier.getApplicationType(), APPLICATION_TYPE2);
		assertEquals(identifier.getCode(), CODE2);
		assertEquals(identifier.getFinalCode(), FINAL_CODE2);
		assertEquals(identifier.getOffice(), OFFICE2);
		assertEquals(identifier.getYear(), YEAR2);
		
	}
}
