package eu.ohim.sp.core.domain.converter.tm.bean;

import eu.ohim.sp.core.domain.converter.tm.util.JavaBeanTester;
import eu.ohim.sp.filing.domain.tm.*;
import org.junit.Test;

import javax.xml.datatype.XMLGregorianCalendar;
import java.beans.IntrospectionException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 04/02/14
 * Time: 17:15
 * To change this template use File | Settings | File Templates.
 */
public class TradeMarkTest {

    @Test
    public void testTradeMark() throws IntrospectionException {
        JavaBeanTester.test(TradeMark.class);
    }

    @Test
    public void testClaimantType() throws IntrospectionException {
        JavaBeanTester.test(ClaimantType.class);
    }

    @Test
    public void testOpponentType() throws IntrospectionException {
        JavaBeanTester.test(OpponentType.class);
    }

    @Test
    public void testOppositionType() throws IntrospectionException {
        JavaBeanTester.test(OppositionType.class);
    }

    @Test
    public void testBasicRecord() throws IntrospectionException {
        JavaBeanTester.test(BasicRecord.class);
    }

    @Test
    public void testTradeMarkApplication() throws IntrospectionException {
        JavaBeanTester.test(TradeMarkApplication.class);
    }

    @Test
    public void testRepresentative() throws IntrospectionException {
        JavaBeanTester.test(Representative.class);
    }

    @Test
    public void testApplicant() throws IntrospectionException {
        JavaBeanTester.test(Applicant.class);
    }

    @Test
    public void testClassificationTermType() throws IntrospectionException {
        JavaBeanTester.test(ClassificationTermType.class);
    }

    @Test
    public void testReimbursementType() throws IntrospectionException {
        JavaBeanTester.test( ReimbursementType.class);
    }

    @Test
    public void testTradeMarkServicesApplication() throws IntrospectionException {
        JavaBeanTester.test(TradeMarkServicesApplication.class);
    }

    @Test
    public void testFormattedName() throws IntrospectionException {
        JavaBeanTester.test(FormattedName.class);
    }

    @Test
    public void testFormattedAddress() throws IntrospectionException {
        JavaBeanTester.test(FormattedAddress.class);
    }

    @Test
    public void testAddressBook() throws IntrospectionException {
        JavaBeanTester.test(AddressBook.class);
    }

    @Test
    public void testDocument() throws IntrospectionException {
        JavaBeanTester.test(Document.class);
    }

    @Test
    public void testPayment() throws IntrospectionException {
        JavaBeanTester.test(Payment.class);
    }

    @Test
    public void testAccount() throws IntrospectionException {
        JavaBeanTester.test(Account.class);
    }


    @Test
    public void testCardAccount() throws IntrospectionException {
        JavaBeanTester.test(CardAccount.class);
    }


    @Test
    public void testSenderDetailsType() throws IntrospectionException {
        JavaBeanTester.test(SenderDetailsType.class);
    }


    @Test
    public void testPriority() throws IntrospectionException {
        JavaBeanTester.test(Priority.class);
    }

    @Test
    public void testSeniority() throws IntrospectionException {
        JavaBeanTester.test(Seniority.class);
    }

    @Test
    public void testExhibitionPriority() throws IntrospectionException {
        JavaBeanTester.test(ExhibitionPriority.class);
    }

    @Test
    public void testTransformationPriority() throws IntrospectionException {
        JavaBeanTester.test(TransformationPriority.class);
    }

}
