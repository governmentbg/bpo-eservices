package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.payment.*;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.filing.domain.ds.PaymentKindType;
import eu.ohim.sp.filing.domain.ds.Text;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author ionitdi
 */
public class PaymentFeeConverterTest
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
        PaymentFee core = new PaymentFee();
        core.setIdentifier("payment identifier");
        core.setReference("payment reference");
        core.setPayer(new Applicant());
        core.getPayer().setName(new PersonName());
        core.getPayer().getName().setFirstName("some first name");

        core.setComment("some comment");
        core.setStatus(PaymentStatusCode.TO_FOLLOW);
        core.setDate(new Date(2011, 11, 11));
        core.setKind(PaymentKind.BANK_TRANSFER);

        core.setFees(new ArrayList<MatchedFee>());

        MatchedFee fee1 = new MatchedFee();
        fee1.setFee(new Fee());
        fee1.getFee().setQuantity(2);
        fee1.getFee().setUnitAmount(14D);
        fee1.getFee().setFeeType(new FeeType());
        fee1.getFee().getFeeType().setDescription("fee description");
        fee1.getFee().getFeeType().setCode("fee code");
        fee1.getFee().getFeeType().setNameKey("fee namekey");


        MatchedFee fee2 = new MatchedFee();
        fee2.setFee(new Fee());
        fee2.getFee().setQuantity(5);
        fee2.getFee().setUnitAmount(10D);

        core.getFees().add(fee1);
        core.getFees().add(fee2);

        eu.ohim.sp.filing.domain.ds.Payment ext = dozerBeanMapper.map(core, eu.ohim.sp.filing.domain.ds.Payment.class);


        assertEquals("payment identifier", ext.getPaymentIdentifier());
        assertEquals("payment reference", ext.getPaymentReference());
        assertEquals("some first name", ext.getPayerName().getFormattedName().getFirstName());
        assertEquals("some comment", ext.getComment().getValue());
        assertEquals("To follow", ext.getPaymentStatus().value());
        assertEquals(new Date(2011, 11, 11), ext.getPaymentDate());
        assertEquals(PaymentKindType.BANK_TRANSFER, ext.getPaymentKind());
        assertEquals(2, ext.getPaymentFeeDetails().getPaymentFee().get(0).getFeeUnitQuantity().intValue());
        assertEquals(14D, ext.getPaymentFeeDetails().getPaymentFee().get(0).getFeeUnitAmount().getValue().doubleValue(), 0.01);
        assertEquals(5, ext.getPaymentFeeDetails().getPaymentFee().get(1).getFeeUnitQuantity().intValue());
        assertEquals("fee code", ext.getPaymentFeeDetails().getPaymentFee().get(0).getFeeIdentifier());
        assertEquals("fee namekey", ext.getPaymentFeeDetails().getPaymentFee().get(0).getFeeReference());
    }

    @Test
    public void testConvertToCore()
    {
        eu.ohim.sp.filing.domain.ds.Payment ext = new eu.ohim.sp.filing.domain.ds.Payment();
        ext.setComment(new Text("some comment", null));

        PaymentFee core = dozerBeanMapper.map(ext, PaymentFee.class);

        assertEquals("some comment", core.getComment());
    }
}
