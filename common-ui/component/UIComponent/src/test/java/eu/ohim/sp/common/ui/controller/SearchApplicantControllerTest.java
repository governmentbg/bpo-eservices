/*******************************************************************************
 * * $Id:: SearchApplicantControllerTest.java 50771 2012-11-14 15:10:27Z karalch $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * @author ionitdi
 */
public class SearchApplicantControllerTest
{
    @Mock
    PersonServiceInterface personService;

	@Mock
	ConfigurationServiceDelegatorInterface configurationServiceDelegator;
	
	@Mock
    FlowScopeDetails flowScopeDetails;

    @InjectMocks
    SearchApplicantController searchApplicantController = new SearchApplicantController();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void autocomplete_nullId_returnsNull()
    {
		when(configurationServiceDelegator.getValueFromGeneralComponent("service.applicant.autocomplete.maxResults"))
				.thenReturn("5");
		when(flowScopeDetails.getFlowModeId()).thenReturn("ds-transfer");
		
        String result = searchApplicantController.autocomplete(null);

        assertEquals(null, result);
    }

    @Test
    public void autocomplete_validId_returnsJson()
    {
        when(personService.getApplicantAutocomplete(eq("valid id"), anyInt())).thenReturn("json response");
		when(configurationServiceDelegator.getValueFromGeneralComponent("service.applicant.autocomplete.maxResults"))
				.thenReturn("5");
		when(flowScopeDetails.getFlowModeId()).thenReturn("dsefiling");
        String result = searchApplicantController.autocomplete("valid id");

        assertEquals("json response", result);
    }
}
