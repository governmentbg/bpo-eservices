/*******************************************************************************
 * * $Id:: ErrorCoreTest.java 53083 2012-12-14 08:59:24Z virgida                 $
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

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

/**
 * @author ionitdi
 */
public class ErrorCoreTest {

	private static final String FIELD = "field";
	private static final String ERROR_CODE = "1234";
	private static final String DISPLAY_MESSAGE = "error message";
	private static final String STACK_TRACE = "stackTrace";
	private static final String SERVICE_NAME = "serviceName";
	private static final String SECTION = "section";
	private static final String BUSINESS_RULE = "businessRule";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ErrorCore.class);
	}

	@Test
    public void testEquals() {
    	
		ErrorCore errorCore = new ErrorCore(FIELD, ERROR_CODE, new ArrayList<String>(),
				DISPLAY_MESSAGE, STACK_TRACE, SERVICE_NAME, SECTION,
				BUSINESS_RULE);
		
		ErrorCore errorCore2 = new ErrorCore();
		errorCore2.setBusinessRule(BUSINESS_RULE);
		errorCore2.setDisplayMessage(DISPLAY_MESSAGE);
		errorCore2.setErrorArgs(new ArrayList<String>());
		errorCore2.setErrorCode(ERROR_CODE);
		errorCore2.setField(FIELD);
		errorCore2.setSection(SECTION);
		errorCore2.setServiceName(SERVICE_NAME);
		errorCore2.setStackTrace(STACK_TRACE);
		errorCore2.setWarning(false);
		
		assertEquals(errorCore.getBusinessRule(), errorCore2.getBusinessRule());
		assertEquals(errorCore.getDisplayMessage(), errorCore2.getDisplayMessage());
		assertEquals(errorCore.getErrorArgs().size(), errorCore2.getErrorArgs().size());
		assertEquals(errorCore.getErrorCode(), errorCore2.getErrorCode());
		assertEquals(errorCore.getField(), errorCore2.getField());
		assertEquals(errorCore.getSection(), errorCore2.getSection());
		assertEquals(errorCore.getServiceName(), errorCore2.getServiceName());
		assertEquals(errorCore.getStackTrace(), errorCore2.getStackTrace());
		assertEquals(errorCore.isWarning(), errorCore2.isWarning());
    }
}
