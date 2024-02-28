package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.application.ApplicationBasicKind;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.filing.domain.ds.Transaction;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author ionitdi
 */
public class ApplicationTransactionConverterTest
{
    DozerBeanMapper dozerBeanMapper;

    @Before
    public void setUp() throws Exception
    {
        dozerBeanMapper = CommonSetup.getMapper();
    }

    @Test
    public void testConvertToFSP()
    {
        DesignApplication core = new DesignApplication();
        core.setApplicationFilingNumber("some number");
        core.setUser("some user");

        Transaction ext = dozerBeanMapper.map(core, Transaction.class);
        assertEquals(TransactionInformation.DS_XSD_VERSION, ext.getTransactionHeader().getSenderDetails().getRequestXSDVersion());
        assertEquals("some user", ext.getTransactionHeader().getSenderDetails().getLoginInformation().getLogin());
        assertEquals(ApplicationBasicKind.DESIGN_EFILING.value(),
                     ext.getDesignTransactionBody().get(0).getTransactionContentDetails().getTransactionCode());
        assertEquals(TransactionInformation.DS_FORM_NAME, ext.getDesignTransactionBody().get(
                0).getTransactionContentDetails().getTransactionData().getDesignApplication().getDesignApplicationFormName());
        assertEquals(TransactionInformation.REQUEST_SOFTWARE_NAME, ext.getDesignTransactionBody().get(
                0).getTransactionContentDetails().getTransactionData().getDesignApplication().getRequestSoftware().getRequestSoftwareName());
        assertEquals(TransactionInformation.REQUEST_SOFTWARE_VERSION, ext.getDesignTransactionBody().get(
                0).getTransactionContentDetails().getTransactionData().getDesignApplication().getRequestSoftware().getRequestSoftwareVersion());
    }


    @Test
    public void testConvertToCore()
    {

    }
}
