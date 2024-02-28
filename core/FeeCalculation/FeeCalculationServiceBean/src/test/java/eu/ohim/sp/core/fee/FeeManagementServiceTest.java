/*
 *  FeeManagementService:: FeeManagementServiceTest 16/10/13 10:03 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.fee;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.fees.xsd.FeeList;
import eu.ohim.sp.core.configuration.domain.fees.xsd.FeeType;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.rules.RulesService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Χρήστος
 * Date: 15/10/2013
 * Time: 10:26 μμ
 * To change this template use File | Settings | File Templates.
 */
public class FeeManagementServiceTest {

    @Mock
    RulesService businessRulesServiceInterface;

    @Mock
    ConfigurationService configurationService;

    @InjectMocks
    FeeCalculationServiceBean feeManagementService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCalculateFees() {
        FeeList feeList = new FeeList();
        feeList.getFeeTypeList().add(new FeeType());
        feeList.getFeeTypeList().get(0).setCode("F001");
        feeList.getFeeTypeList().get(0).setDescription("Basic Fee");
        feeList.getFeeTypeList().get(0).setDefaultValue(1000);
        feeList.getFeeTypeList().add(new FeeType());
        feeList.getFeeTypeList().get(0).setCode("F002");
        feeList.getFeeTypeList().get(0).setDescription("Extra Fee");
        feeList.getFeeTypeList().get(0).setDefaultValue(300);

        List<Object> objects = new ArrayList<Object>();
        DesignApplication designApplication = new DesignApplication();
        designApplication.setApplicationFilingNumber("000001");
        designApplication.setApplicationFilingDate(new Date());
        objects.add(designApplication);


        Map<String, Object> feesResult = new HashMap<String, Object>();
        Fee feeExpected = new Fee();
        feesResult.put("F001", feeExpected);
        ((Fee) feesResult.get("F001")).setAmount(new Double(1000));
        ((Fee) feesResult.get("F001")).setFeeType(new eu.ohim.sp.core.domain.payment.FeeType());
        ((Fee) feesResult.get("F001")).setQuantity(1);
        ((Fee) feesResult.get("F001")).setStatusDate(new Date());

        when(configurationService
                .getObject(eq("fees-configuration"), eq("eu.ohim.sp.core.rules.tmefiling"), eq(FeeList.class))).thenReturn(feeList);
        when(businessRulesServiceInterface
                .calculate(eq("tmefiling"), eq("fee_set"), eq(objects))).thenReturn(feesResult);


        List<Fee> feeOutcome = feeManagementService.calculateFees("tmefiling", objects);


        verify(configurationService, times(1)).getObject(eq("fees-configuration"), eq("eu.ohim.sp.core.rules.tmefiling"), eq(FeeList.class));
        verify(businessRulesServiceInterface, times(1)).calculate(eq("tmefiling"), eq("fee_set"), eq(objects));


        Assert.assertEquals(feeExpected.getAmount(), feeOutcome.get(0).getAmount());
        Assert.assertEquals(feeExpected.getQuantity(), feeOutcome.get(0).getQuantity());
        Assert.assertEquals(feeExpected.getStatusDate(), feeOutcome.get(0).getStatusDate());
    }


    @Test
    public void testCalculateFeesNull() {
        FeeList feeList = new FeeList();
        feeList.getFeeTypeList().add(new FeeType());
        feeList.getFeeTypeList().get(0).setCode("F001");
        feeList.getFeeTypeList().get(0).setDescription("Basic Fee");
        feeList.getFeeTypeList().get(0).setDefaultValue(1000);
        feeList.getFeeTypeList().add(new FeeType());
        feeList.getFeeTypeList().get(0).setCode("F002");
        feeList.getFeeTypeList().get(0).setDescription("Extra Fee");
        feeList.getFeeTypeList().get(0).setDefaultValue(300);

        List<Object> objects = new ArrayList<Object>();
        DesignApplication designApplication = new DesignApplication();
        designApplication.setApplicationFilingNumber("000001");
        designApplication.setApplicationFilingDate(new Date());
        objects.add(designApplication);

        when(configurationService
                .getObject(eq("fees-configuration"), eq("eu.ohim.sp.core.rules.tmefiling"), eq(FeeList.class))).thenReturn(feeList);
        when(businessRulesServiceInterface
                .calculate(eq("tmefiling"), eq("fee_set"), eq(objects))).thenReturn(null);

        List<Fee> feeOutcome = feeManagementService.calculateFees("tmefiling", objects);

        verify(configurationService, times(1)).getObject(eq("fees-configuration"), eq("eu.ohim.sp.core.rules.tmefiling"), eq(FeeList.class));
        verify(businessRulesServiceInterface, times(1)).calculate(eq("tmefiling"), eq("fee_set"), eq(objects));

        Assert.assertEquals(null, feeOutcome);
    }


    @Test(expected = SPException.class)
    public void testCalculateFeesException() {
        FeeList feeList = new FeeList();
        feeList.getFeeTypeList().add(new FeeType());
        feeList.getFeeTypeList().get(0).setCode("F001");
        feeList.getFeeTypeList().get(0).setDescription("Basic Fee");
        feeList.getFeeTypeList().get(0).setDefaultValue(1000);
        feeList.getFeeTypeList().add(new FeeType());
        feeList.getFeeTypeList().get(0).setCode("F002");
        feeList.getFeeTypeList().get(0).setDescription("Extra Fee");
        feeList.getFeeTypeList().get(0).setDefaultValue(300);

        List<Object> objects = new ArrayList<Object>();
        DesignApplication designApplication = new DesignApplication();
        designApplication.setApplicationFilingNumber("000001");
        designApplication.setApplicationFilingDate(new Date());
        objects.add(designApplication);

        when(configurationService
                .getObject(eq("fees-configuration"), eq("eu.ohim.sp.core.rules.tmefiling"), eq(FeeList.class))).thenReturn(feeList);
        when(businessRulesServiceInterface
                .calculate(eq("tmefiling"), eq("fee_set"), eq(objects))).thenThrow(SPException.class);

        List<Fee> feeOutcome = feeManagementService.calculateFees("tmefiling", objects);

        verify(configurationService, times(1)).getObject(eq("fees-configuration"), eq("eu.ohim.sp.core.rules.tmefiling"), eq(FeeList.class));
        verify(businessRulesServiceInterface, times(1)).calculate(eq("tmefiling"), eq("fee_set"), eq(objects));

        Assert.assertEquals(null, feeOutcome);
    }
}
