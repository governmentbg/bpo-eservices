package eu.ohim.sp.ptefiling.ui.adapter;

import eu.ohim.sp.common.ui.adapter.patent.PTDivisionalApplicationFactory;
import eu.ohim.sp.common.ui.form.patent.PTDivisionalApplicationForm;
import eu.ohim.sp.core.domain.application.DivisionalApplicationDetails;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Raya
 * 11.04.2019
 */
public class PTDivisionalApplicationFactoryTest {

    private PTDivisionalApplicationFactory ptDivisionalApplicationFactory;

    @Before
    public void init(){
        ptDivisionalApplicationFactory = new PTDivisionalApplicationFactory();
    }

    @Test
    public void test_convertTo(){
        PTDivisionalApplicationForm form = PTFactoriesTestSource.createTestPTDivisionalAppForm();
        DivisionalApplicationDetails core = ptDivisionalApplicationFactory.convertTo(form);

        assertEquals(form.getDateDivisionalApplication(), core.getInitialApplicationDate());
        assertEquals(form.getNumberDivisionalApplication(), core.getInitialApplicationNumber());


    }

    @Test
    public void test_convertFrom(){
        DivisionalApplicationDetails core = PTFactoriesTestSource.createTestPTDivisionalApp();
        PTDivisionalApplicationForm form = ptDivisionalApplicationFactory.convertFrom(core);

        assertEquals(core.getInitialApplicationDate(), form.getDateDivisionalApplication());
        assertEquals(core.getInitialApplicationNumber(), form.getNumberDivisionalApplication());
    }
}
