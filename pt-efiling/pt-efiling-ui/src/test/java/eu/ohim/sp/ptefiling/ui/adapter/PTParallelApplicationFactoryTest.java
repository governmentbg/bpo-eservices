package eu.ohim.sp.ptefiling.ui.adapter;

import eu.ohim.sp.common.ui.adapter.patent.PTParallelApplicationFactory;
import eu.ohim.sp.common.ui.form.patent.PTEuropeanParallelApplicationForm;
import eu.ohim.sp.common.ui.form.patent.PTInternationalParallelApplicationForm;
import eu.ohim.sp.common.ui.form.patent.PTNationalParallelApplicationForm;
import eu.ohim.sp.common.ui.form.patent.PTParallelApplicationForm;
import eu.ohim.sp.core.domain.patent.ParallelApplication;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Raya
 * 11.04.2019
 */
public class PTParallelApplicationFactoryTest {

    private PTParallelApplicationFactory ptParallelApplicationFactory;

    private PTParallelApplicationForm nationalForm;
    private ParallelApplication nationalCore;

    private PTParallelApplicationForm woForm;
    private ParallelApplication woCore;

    private PTParallelApplicationForm emForm;
    private ParallelApplication emCore;

    @Before
    public void init(){
        ptParallelApplicationFactory = new PTParallelApplicationFactory();
        nationalForm = PTFactoriesTestSource.createTestNationalParallelForm();
        nationalCore = PTFactoriesTestSource.createTestBGParallelApp();

        woForm = PTFactoriesTestSource.createTestInternationalParallelForm();
        woCore = PTFactoriesTestSource.createTestWOParallelApp();

        emForm = PTFactoriesTestSource.createTestEUParallelForm();
        emCore = PTFactoriesTestSource.createTestEMParallelApp();
    }

    @Test
    public void test_convertTo(){

        ParallelApplication nationalRes = ptParallelApplicationFactory.convertTo(nationalForm);
        ParallelApplication woRes = ptParallelApplicationFactory.convertTo(woForm);
        ParallelApplication emRes = ptParallelApplicationFactory.convertTo(emForm);

        assertEquals(nationalForm.getCountryCode(), nationalRes.getCountryCode());
        assertEquals(nationalForm.getApplicationDate(), nationalRes.getApplicationDate());
        assertEquals(nationalForm.getApplicationNumber(), nationalRes.getApplicationNumber());
        assertEquals(nationalForm.getPublicationDate(), nationalRes.getPublicationDate());
        assertEquals(nationalForm.getPublicationNumber(), nationalRes.getPublicationNumber());
        assertEquals(nationalForm.getHolderName(), nationalRes.getHolderName());

        assertEquals(woForm.getCountryCode(), woRes.getCountryCode());
        assertEquals(woForm.getApplicationDate(), woRes.getApplicationDate());
        assertEquals(woForm.getApplicationNumber(), woRes.getApplicationNumber());
        assertEquals(woForm.getPublicationDate(), woRes.getPublicationDate());
        assertEquals(woForm.getPublicationNumber(), woRes.getPublicationNumber());
        assertEquals(woForm.getHolderName(), woRes.getHolderName());

        assertEquals(emForm.getCountryCode(), emRes.getCountryCode());
        assertEquals(emForm.getApplicationDate(), emRes.getApplicationDate());
        assertEquals(emForm.getApplicationNumber(), emRes.getApplicationNumber());
        assertEquals(emForm.getPublicationDate(), emRes.getPublicationDate());
        assertEquals(emForm.getPublicationNumber(), emRes.getPublicationNumber());
        assertEquals(emForm.getHolderName(), emRes.getHolderName());


    }

    @Test
    public void test_convertFrom(){
        PTParallelApplicationForm nationalRes = ptParallelApplicationFactory.convertFrom(nationalCore);
        PTParallelApplicationForm woRes = ptParallelApplicationFactory.convertFrom(woCore);
        PTParallelApplicationForm emRes = ptParallelApplicationFactory.convertFrom(emCore);

        assertEquals(nationalCore.getApplicationDate(), nationalRes.getApplicationDate());
        assertEquals(nationalCore.getCountryCode(), nationalRes.getCountryCode());
        assertEquals(nationalCore.getApplicationNumber(), nationalRes.getApplicationNumber());
        assertEquals(nationalCore.getPublicationDate(), nationalRes.getPublicationDate());
        assertEquals(nationalCore.getPublicationNumber(), nationalRes.getPublicationNumber());
        assertEquals(nationalCore.getHolderName(), nationalRes.getHolderName());
        assertTrue(nationalRes instanceof PTNationalParallelApplicationForm);

        assertEquals(woCore.getApplicationDate(), woRes.getApplicationDate());
        assertEquals(woCore.getCountryCode(), woRes.getCountryCode());
        assertEquals(woCore.getApplicationNumber(), woRes.getApplicationNumber());
        assertEquals(woCore.getPublicationDate(), woRes.getPublicationDate());
        assertEquals(woCore.getPublicationNumber(), woRes.getPublicationNumber());
        assertEquals(woCore.getHolderName(), woRes.getHolderName());
        assertTrue(woRes instanceof PTInternationalParallelApplicationForm);

        assertEquals(emCore.getApplicationDate(), emRes.getApplicationDate());
        assertEquals(emCore.getCountryCode(), emRes.getCountryCode());
        assertEquals(emCore.getApplicationNumber(), emRes.getApplicationNumber());
        assertEquals(emCore.getPublicationDate(), emRes.getPublicationDate());
        assertEquals(emCore.getPublicationNumber(), emRes.getPublicationNumber());
        assertEquals(emCore.getHolderName(), emRes.getHolderName());
        assertTrue(emRes instanceof PTEuropeanParallelApplicationForm);


    }
}


