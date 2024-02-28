package eu.ohim.sp.ptefiling.ui.adapter;

import eu.ohim.sp.common.ui.adapter.patent.PTTransformationFactory;
import eu.ohim.sp.common.ui.form.patent.PTConversionForm;
import eu.ohim.sp.common.ui.form.patent.PTInternationalTransformationForm;
import eu.ohim.sp.common.ui.form.patent.PTNationalTransformationForm;
import eu.ohim.sp.common.ui.form.patent.PTTransformationForm;
import eu.ohim.sp.core.domain.patent.PatentTransformation;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Raya
 * 11.04.2019
 */
public class PTTransformationFactoryTest {

    private PTTransformationFactory ptTransformationFactory;

    private PTNationalTransformationForm formNat;
    private PTInternationalTransformationForm formWO;
    private PTConversionForm formEM;

    private PatentTransformation coreNat;
    private PatentTransformation coreWO;
    private PatentTransformation coreEM;

    @Before
    public void init(){
        ptTransformationFactory = new PTTransformationFactory();

        formNat = PTFactoriesTestSource.createTestNationalTransform();
        formWO = PTFactoriesTestSource.createTestInternatTransform();
        formEM = PTFactoriesTestSource.createTestConversion();

        coreNat = PTFactoriesTestSource.createBGTransform();
        coreWO = PTFactoriesTestSource.createWOTransform();
        coreEM = PTFactoriesTestSource.createEMTransform();
    }


    @Test
    public void test_convertTo(){

        PatentTransformation resultNat = ptTransformationFactory.convertTo(formNat);
        PatentTransformation resultWO = ptTransformationFactory.convertTo(formWO);
        PatentTransformation resultEM = ptTransformationFactory.convertTo(formEM);

        //1
        assertEquals(formNat.getApplicationDate(), resultNat.getApplicationDate());
        assertEquals(formNat.getCountryCode(), resultNat.getCountryCode());
        assertEquals(formNat.getApplicationNumber(), resultNat.getApplicationNumber());
        assertEquals(formNat.getPublicationDate(), resultNat.getPublicationDate());
        assertEquals(formNat.getPublicationNumber(), resultNat.getPublicationNumber());
        assertEquals(formNat.getHolderName(), resultNat.getHolderName());
        assertEquals(formNat.isPayedFees(), resultNat.isPayedFees());

        //2
        assertEquals(formWO.getApplicationDate(), resultWO.getApplicationDate());
        assertEquals(formWO.getCountryCode(), resultWO.getCountryCode());
        assertEquals(formWO.getApplicationNumber(), resultWO.getApplicationNumber());
        assertEquals(formWO.getPublicationDate(), resultWO.getPublicationDate());
        assertEquals(formWO.getPublicationNumber(), resultWO.getPublicationNumber());
        assertEquals(formNat.getHolderName(), resultWO.getHolderName());
        assertEquals(formNat.isPayedFees(), resultWO.isPayedFees());

        //3
        assertEquals(formEM.getApplicationDate(), resultEM.getApplicationDate());
        assertEquals(formEM.getCountryCode(), resultEM.getCountryCode());
        assertEquals(formEM.getApplicationNumber(), resultEM.getApplicationNumber());
        assertEquals(formEM.getPublicationDate(), resultEM.getPublicationDate());
        assertEquals(formEM.getPublicationNumber(), resultEM.getPublicationNumber());
        assertEquals(formEM.getHolderName(), resultEM.getHolderName());
        assertEquals(formEM.isPayedFees(), resultEM.isPayedFees());
    }

    @Test
    public void test_convertFrom(){

        PTTransformationForm resultNat = ptTransformationFactory.convertFrom(coreNat);
        PTTransformationForm resultWO = ptTransformationFactory.convertFrom(coreWO);
        PTTransformationForm resultEM = ptTransformationFactory.convertFrom(coreEM);


        //1
        assertTrue(resultNat instanceof PTNationalTransformationForm);
        assertEquals(coreNat.getApplicationDate(), resultNat.getApplicationDate());
        assertEquals(coreNat.getApplicationNumber(), resultNat.getApplicationNumber());
        assertEquals(coreNat.getCountryCode(), resultNat.getCountryCode());
        assertEquals(coreNat.getHolderName(), resultNat.getHolderName());
        assertEquals(coreNat.getPublicationDate(), resultNat.getPublicationDate());
        assertEquals(coreNat.getPublicationNumber(), resultNat.getPublicationNumber());
        assertEquals(coreNat.isPayedFees(), resultNat.isPayedFees());

        //2
        assertTrue(resultWO instanceof PTInternationalTransformationForm);
        assertEquals(coreWO.getApplicationDate(), resultWO.getApplicationDate());
        assertEquals(coreWO.getApplicationNumber(), resultWO.getApplicationNumber());
        assertEquals(coreWO.getCountryCode(), resultWO.getCountryCode());
        assertEquals(coreWO.getHolderName(), resultWO.getHolderName());
        assertEquals(coreWO.getPublicationDate(), resultWO.getPublicationDate());
        assertEquals(coreWO.getPublicationNumber(), resultWO.getPublicationNumber());
        assertEquals(coreWO.isPayedFees(), resultWO.isPayedFees());

        //3
        assertTrue(resultEM instanceof PTConversionForm);
        assertEquals(coreEM.getApplicationDate(), resultEM.getApplicationDate());
        assertEquals(coreEM.getApplicationNumber(), resultEM.getApplicationNumber());
        assertEquals(coreEM.getCountryCode(), resultEM.getCountryCode());
        assertEquals(coreEM.getHolderName(), resultEM.getHolderName());
        assertEquals(coreEM.getPublicationDate(), resultEM.getPublicationDate());
        assertEquals(coreEM.getPublicationNumber(), resultEM.getPublicationNumber());
        assertEquals(coreEM.isPayedFees(), resultEM.isPayedFees());

    }
}
