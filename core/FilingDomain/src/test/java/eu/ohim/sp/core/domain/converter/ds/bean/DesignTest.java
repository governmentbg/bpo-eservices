package eu.ohim.sp.core.domain.converter.ds.bean;

import eu.ohim.sp.core.domain.converter.ds.util.JavaBeanTester;
import eu.ohim.sp.filing.domain.ds.*;
import org.junit.Test;

import java.beans.IntrospectionException;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 04/02/14
 * Time: 18:31
 * To change this template use File | Settings | File Templates.
 */
public class DesignTest {

    @Test
    public void testDesignApplication() throws IntrospectionException {
        JavaBeanTester.test(DesignApplication.class);
    }

    @Test
    public void testDesign() throws IntrospectionException {
        JavaBeanTester.test(Design.class);
    }

    @Test
    public void testDesignBasicRecordType() throws IntrospectionException {
        JavaBeanTester.test(DesignBasicRecordType.class);
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
    public void testOppositionBasisType() throws IntrospectionException {
        JavaBeanTester.test(OppositionBasisType.class);
    }

    @Test
    public void testDesignServicesApplicationType() throws IntrospectionException {
        JavaBeanTester.test(DesignServicesApplicationType.class);
    }

    @Test
    public void testEarlierDesignDetailsType() throws IntrospectionException {
        JavaBeanTester.test(EarlierDesignDetailsType.class);
    }

    @Test
    public void testEarlierDesignType() throws IntrospectionException {
        JavaBeanTester.test(EarlierDesignType.class);
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
    public void testRepresentative() throws IntrospectionException {
        JavaBeanTester.test(Representative.class);
    }

    @Test
    public void testApplicant() throws IntrospectionException {
        JavaBeanTester.test(Applicant.class);
    }

    @Test
    public void testDesigner() throws IntrospectionException {
        JavaBeanTester.test(Designer.class);
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
    public void testExhibitionPriority() throws IntrospectionException {
        JavaBeanTester.test(ExhibitionPriority.class);
    }



}
