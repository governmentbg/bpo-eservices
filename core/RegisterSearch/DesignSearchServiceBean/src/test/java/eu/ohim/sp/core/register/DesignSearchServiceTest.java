/*
 * DesignService:: DesignServiceTest 16/10/13 10:03 karalch $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */

package eu.ohim.sp.core.register;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignDivisionalApplicationDetails;
import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.validation.ErrorCore;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesServiceLocal;

public class DesignSearchServiceTest {

    @Mock
    RulesServiceLocal businessRulesServiceMock;

    @Mock
    DesignSearchService designServiceProvider;

    @InjectMocks
    DesignSearchServiceBean designService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetDesign() {
        designService.getDesign("EM", "01");
        verify(designServiceProvider, times(1)).getDesign(eq("EM"), eq("01"));
    }

    @Test
    public void testGetDesignApplication() {
        designService.getDesignApplication("EM", "01", null, true);
        verify(designServiceProvider, times(1)).getDesignApplication(eq("EM"), eq("01"), eq(null), eq(true));
    }

    @Test
    public void testGetDesignAutocomplete() {
        designService.getDesignAutocomplete("EM", "test", 5);
        verify(designServiceProvider, times(1)).getDesignAutocomplete(eq("EM"), eq("test"), eq(5));
    }

    @Test
    public void testValidateDesign() {
        ErrorList errorsReturned = new ErrorList();
        errorsReturned.setErrorList(new ArrayList<ErrorCore>());
        errorsReturned.getErrorList().add(new ErrorCore());
        errorsReturned.getErrorList().get(0).setField("applicationLanguage");

        when(businessRulesServiceMock.validate(eq("EM"), eq("design_set"), anyList())).thenReturn(errorsReturned);
        ErrorList errorsExpected = designService.validateDesign("EM", new Design(), new RulesInformation());
        verify(businessRulesServiceMock, times(1)).validate(eq("EM"), eq("design_set"), anyList());

        Assert.assertEquals(errorsExpected, errorsReturned);
    }

    @Test
    public void testValidateDesignView() {
        ErrorList errorsReturned = new ErrorList();
        errorsReturned.setErrorList(new ArrayList<ErrorCore>());
        errorsReturned.getErrorList().add(new ErrorCore());
        errorsReturned.getErrorList().get(0).setField("applicationLanguage");

        when(businessRulesServiceMock.validate(eq("EM"), eq("design_set"), anyList())).thenReturn(errorsReturned);
        ErrorList errorsExpected = designService.validateDesignView("EM", new DesignView(), new RulesInformation());
        verify(businessRulesServiceMock, times(1)).validate(eq("EM"), eq("design_set"), anyList());

        Assert.assertEquals(errorsExpected, errorsReturned);
    }

    @Test
    public void testValidateDivisionalApplication() {
        designService.validateDivisionalApplication("EM", new DesignDivisionalApplicationDetails(),
                new RulesInformation());
        verify(businessRulesServiceMock, times(1)).validate(eq("EM"), eq("divisional_application_set"), anyList());
    }

}
