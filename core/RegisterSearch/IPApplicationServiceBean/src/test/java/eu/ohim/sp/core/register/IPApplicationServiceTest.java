/*
 *  IPApplicationService:: IPApplicationServiceTest 16/10/13 10:11 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.register;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.claim.Seniority;
import eu.ohim.sp.core.domain.claim.TransformationPriority;
import eu.ohim.sp.core.domain.claim.trademark.Priority;
import eu.ohim.sp.core.domain.design.ExhibitionPriority;
import eu.ohim.sp.core.domain.validation.ErrorCore;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 16/10/2013
 * Time: 12:04 πμ
 * To change this template use File | Settings | File Templates.
 */
public class IPApplicationServiceTest {

    @Mock
    private RulesService businessRulesService;

    @InjectMocks
    IPApplicationServiceBean applicationService;

    RulesInformation rulesInformation;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Sections sections = new Sections();
        rulesInformation = new RulesInformation();
        rulesInformation.setCustomObjects(new HashMap<String, Object>());
        rulesInformation.getCustomObjects().put("sections", sections);
        sections.getSection().add(new Section());
        sections.getSection().get(0).setId(AvailableSection.CLAIM);
        rulesInformation.getCustomObjects().put("sectionName", AvailableSection.CLAIM.value());
    }

    @Test
    public void testPriorityClaim() {
        ErrorList errorsReturned = new ErrorList();
        errorsReturned.setErrorList(new ArrayList<ErrorCore>());
        errorsReturned.getErrorList().add(new ErrorCore());
        errorsReturned.getErrorList().get(0).setField("applicationLanguage");

        when(businessRulesService.validate(eq("tmefiling"), eq("claim_set"), anyList())).thenReturn(errorsReturned);
        ErrorList errorsExpected = applicationService.validatePriorityClaim("tmefiling", new Priority(), rulesInformation);
        verify(businessRulesService, times(1)).validate(eq("tmefiling"), eq("claim_set"), anyList());

        Assert.assertEquals(errorsExpected, errorsReturned);
    }

    @Test
    public void testPriorityClaimNull() {
        ErrorList errorsExpected = applicationService.validatePriorityClaim("tmefiling", new Priority(), new RulesInformation());

        Assert.assertEquals(errorsExpected.getErrorList().size(), 0);
    }

    @Test
    public void testSeniorityClaim() {
        ErrorList errorsReturned = new ErrorList();
        errorsReturned.setErrorList(new ArrayList<ErrorCore>());
        errorsReturned.getErrorList().add(new ErrorCore());
        errorsReturned.getErrorList().get(0).setField("applicationLanguage");

        when(businessRulesService.validate(eq("tmefiling"), eq("claim_set"), anyList())).thenReturn(errorsReturned);
        ErrorList errorsExpected = applicationService.validateSeniorityClaim("tmefiling", new Seniority(), rulesInformation);
        verify(businessRulesService, times(1)).validate(eq("tmefiling"), eq("claim_set"), anyList());

        Assert.assertEquals(errorsExpected, errorsReturned);
    }

    @Test
    public void testExhibitionPriority() {
        ErrorList errorsReturned = new ErrorList();
        errorsReturned.setErrorList(new ArrayList<ErrorCore>());
        errorsReturned.getErrorList().add(new ErrorCore());
        errorsReturned.getErrorList().get(0).setField("applicationLanguage");

        when(businessRulesService.validate(eq("tmefiling"), eq("claim_set"), anyList())).thenReturn(errorsReturned);
        ErrorList errorsExpected = applicationService.validateExhibitionPriorityClaim("tmefiling", new ExhibitionPriority(), rulesInformation);
        verify(businessRulesService, times(1)).validate(eq("tmefiling"), eq("claim_set"), anyList());

        Assert.assertEquals(errorsExpected, errorsReturned);
    }

    @Test
    public void testTransformation() {
        ErrorList errorsReturned = new ErrorList();
        errorsReturned.setErrorList(new ArrayList<ErrorCore>());
        errorsReturned.getErrorList().add(new ErrorCore());
        errorsReturned.getErrorList().get(0).setField("applicationLanguage");

        when(businessRulesService.validate(eq("tmefiling"), eq("claim_set"), anyList())).thenReturn(errorsReturned);
        ErrorList errorsExpected = applicationService.validateTransformation("tmefiling", new TransformationPriority(), rulesInformation);
        verify(businessRulesService, times(1)).validate(eq("tmefiling"), eq("claim_set"), anyList());

        Assert.assertEquals(errorsExpected, errorsReturned);
    }
}
