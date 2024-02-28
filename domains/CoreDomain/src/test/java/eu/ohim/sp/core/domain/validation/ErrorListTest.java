/*******************************************************************************
 * * $Id:: ErrorListTest.java 157090 2013-12-02 11:54:19Z velasca                $
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
import java.util.ArrayList;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class ErrorListTest {

	private static final String FIELD = "field";
	private static final String ERROR_CODE = "1234";
	private static final String DISPLAY_MESSAGE = "error message";
	private static final String STACK_TRACE = "stackTrace";
	private static final String SERVICE_NAME = "serviceName";
	private static final String SECTION = "section";
	private static final String BUSINESS_RULE = "businessRule";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ErrorList.class);
	}

	@Test
	public void testEquals() {
		ErrorList errorList = new ErrorList();
		errorList.setErrorList(new ArrayList<ErrorCore>());
		errorList.getErrorList().add(
				new ErrorCore(FIELD, ERROR_CODE, new ArrayList<String>(),
						DISPLAY_MESSAGE, STACK_TRACE, SERVICE_NAME, SECTION,
						BUSINESS_RULE));

		assertEquals(errorList.getErrorList().size(), 1);
	}

	@Test
	public void testAddAllErrors() {
		ErrorCore errorCore = new ErrorCore(FIELD, ERROR_CODE, new ArrayList<String>(),
				DISPLAY_MESSAGE, STACK_TRACE, SERVICE_NAME, SECTION,
				BUSINESS_RULE);
		
		ErrorList errorList = new ErrorList();
		errorList.setErrorList(new ArrayList<ErrorCore>());
		errorList.getErrorList().add(errorCore);

		ErrorList errors = new ErrorList();
		errors.addError(errorCore);
		
		errorList.addAllErrors(errors);
		assertEquals(errorList.getErrorList().size(), 2);

	}
}
