package eu.ohim.sp.ptefiling.ui.adapter;

import eu.ohim.sp.common.ui.adapter.patent.PCTFactory;
import eu.ohim.sp.common.ui.form.patent.PCTForm;
import eu.ohim.sp.core.domain.patent.PCT;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Raya
 * 13.05.2019
 */
public class PCTFactoryTest {

    private PCTFactory pctFactory;

    private PCTForm form;
    private PCT core;

    @Before
    public void init(){
        pctFactory = new PCTFactory();
        form = PTFactoriesTestSource.createTestPctForm();
        core = PTFactoriesTestSource.createTestPct();
    }

    @Test
    public void test_convertFrom(){
        PCTForm res = pctFactory.convertFrom(core);

        assertEquals(res.getApplicationDate(), form.getApplicationDate());
        assertEquals(res.getApplicationNumber(), form.getApplicationNumber());
        assertEquals(res.getPublicationDate(), form.getPublicationDate());
        assertEquals(res.getPublicationNumber(), form.getPublicationNumber());
        assertEquals(res.getHolderName(), form.getHolderName());
        assertEquals(res.isPayedFees(), form.isPayedFees());
    }

    @Test
    public void test_convertTo(){
        PCT res = pctFactory.convertTo(form);

        assertEquals(res.getApplicationDate(), core.getApplicationDate());
        assertEquals(res.getApplicationNumber(), core.getApplicationNumber());
        assertEquals(res.getPublicationDate(), core.getPublicationDate());
        assertEquals(res.getPublicationNumber(), core.getPublicationNumber());
        assertEquals(res.getHolderName(), core.getHolderName());
        assertEquals(res.isPayedFees(), core.isPayedFees());

    }
}
