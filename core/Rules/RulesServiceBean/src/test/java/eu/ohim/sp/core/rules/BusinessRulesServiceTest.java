/*******************************************************************************
 * * $Id:: BusinessRulesServiceTest.java 113493 2013-04-22 15:01:04Z karalc#$
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.rules;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.rules.drools.DroolsCore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.when;

public class BusinessRulesServiceTest {

	@Mock
	DroolsCore droolsCore;
	
	@Mock
	ConfigurationService configurationService;
	
	@InjectMocks
	RulesServiceBean businessRulesService;

	// Static constants to create the scenarios to test
	public static String MODULE = "dsefiling";
	public static String LIST = "applicant_set";

	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        createDroolsCoreMock();
    }

	@Test(expected=IndexOutOfBoundsException.class)
	public void testValidateNoModuleFail(){
		String module = null;
		String list = LIST;
		List<Object> objectList = new ArrayList<Object>();

		businessRulesService.validate(module, list, objectList);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void testValidateNoListFail(){
		String module = MODULE;
		String list = null;
		List<Object> objectList = new ArrayList<Object>();

		businessRulesService.validate(module, list, objectList);
	}

	@Test(expected = NullPointerException.class)
	public void testValidateNoObjectListFail(){
		String module = MODULE;
		String list = LIST;
		List<Object> objectList = null;

		ErrorList errorList = businessRulesService.validate(module, list, objectList);
		Assert.assertEquals(errorList.getErrorList().size(), 0);
	}

	@Test
	public void testValidateNoObjectListOk(){
		String module = MODULE;
		String list = LIST;
		List<Object> objectList = new ArrayList<Object>();

		ErrorList errorList = businessRulesService.validate(module, list, objectList);
		Assert.assertEquals(errorList.getErrorList().size(), 0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testValidateDummyModuleFail(){
		String module = "dummy_module";
		String list = LIST;
		List<Object> objectList = new ArrayList<Object>();

		businessRulesService.validate(module, list, objectList);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testValidateDummyListFail(){
		String module = MODULE;
		String list = "dummy_list";
		List<Object> objectList = new ArrayList<Object>();

		businessRulesService.validate(module, list, objectList);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void testCalculateNoModuleFail(){
		String module = null;
		String list = LIST;
		List<Object> objectList = new ArrayList<Object>();

		businessRulesService.calculate(module, list, objectList);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void testCalculateNoListFail(){
		String module = MODULE;
		String list = null;
		List<Object> objectList = new ArrayList<Object>();

		businessRulesService.calculate(module, list, objectList);
	}

	@Test(expected=NullPointerException.class)
	public void testCalculateNoObjectListFail(){
		String module = MODULE;
		String list = LIST;
		List<Object> objectList = null;

		businessRulesService.calculate(module, list, objectList);
	}

	@Test
	public void testCalculateNoObjectListOk(){
		String module = MODULE;
		String list = LIST;
		List<Object> objectList = new ArrayList<Object>();

		businessRulesService.calculate(module, list, objectList);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testCalculateDummyModuleFail(){
		String module = "dummy_module";
		String list = LIST;
		List<Object> objectList = new ArrayList<Object>();

		businessRulesService.calculate(module, list, objectList);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testCalculateDummyListFail(){
		String module = MODULE;
		String list = "dummy_list";
		List<Object> objectList = new ArrayList<Object>();

		businessRulesService.calculate(module, list, objectList);
	}

	private void createDroolsCoreMock(){
		// Null issues for validation
		when(droolsCore.fireRulesValidate((String) isNull(), (String) isNotNull(), (Collection<Object>) isNotNull())).thenThrow(IndexOutOfBoundsException.class);
		when(droolsCore.fireRulesValidate((String) isNotNull(), (String) isNull(), (Collection<Object>) isNotNull())).thenThrow(IndexOutOfBoundsException.class);
		when(droolsCore.fireRulesValidate((String) isNotNull(), (String) isNotNull(), (Collection<Object>) isNull())).thenThrow(NullPointerException.class);
		when(droolsCore.fireRulesValidate((String) isNotNull(), (String) isNotNull(), (Collection<Object>) isNotNull())).thenReturn(new ErrorList());
		// Not recognized parameters
		when(droolsCore.fireRulesValidate(eq("dummy_module"), (String) isNotNull(), (Collection<Object>) isNotNull())).thenThrow(IndexOutOfBoundsException.class);
		when(droolsCore.fireRulesValidate((String) isNotNull(), eq("dummy_list"), (Collection<Object>) isNotNull())).thenThrow(IndexOutOfBoundsException.class);

		// Null issues for calculation
		when(droolsCore.fireRulesCalculate((String) isNull(), (String) isNotNull(), (Collection<Object>) isNotNull())).thenThrow(IndexOutOfBoundsException.class);
		when(droolsCore.fireRulesCalculate((String) isNotNull(), (String) isNull(), (Collection<Object>) isNotNull())).thenThrow(IndexOutOfBoundsException.class);
		when(droolsCore.fireRulesCalculate((String) isNotNull(), (String) isNotNull(), (Collection<Object>) isNull())).thenThrow(NullPointerException.class);
		when(droolsCore.fireRulesCalculate(eq(MODULE), eq(LIST), (Collection<Object>) isNotNull())).thenReturn(true);
		// Not recognized parameters
		when(droolsCore.fireRulesCalculate(eq("dummy_module"), (String) isNotNull(), (Collection<Object>) isNotNull())).thenThrow(IndexOutOfBoundsException.class);
		when(droolsCore.fireRulesCalculate((String) isNotNull(), eq("dummy_list"), (Collection<Object>) isNotNull())).thenThrow(IndexOutOfBoundsException.class);
	}
}
