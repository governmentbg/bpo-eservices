package eu.ohim.sp.external.domain.epayment;

import eu.ohim.sp.external.domain.common.Text;
import eu.ohim.sp.external.domain.person.PersonRoleKind;
import eu.ohim.sp.external.util.JavaBeanTester;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 03/02/14
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
public class PaymentTest {


    @Test
    public void testCardAccount() throws IntrospectionException {
        JavaBeanTester.test(CardAccount.class);
    }

    @Test
    public void testPaymentClass() throws IntrospectionException {
        JavaBeanTester.test(Payment.class);
    }

    @Test
    public void testPayment() {

        Payment payment = new Payment(PaymentKind.BANK_TRANSFER, "identifier", "reference", PaymentStatusCode.DONE, new Double(200), "EUR", new Date(), "comment",
                new Account("accountIdentifier", "accountKind", new Text("en", "accountHolderName"), AccountDebitKind.BASIC_FEE_IMMEDIATE), new CardAccount("cardPrimaryAccountNumber", "cardNetworkIdentifier", CardKindCode.CREDIT_CARD, "cardCustomerIdentifier", new Date(), new Date(), "cardIssuesIdentifier", "cardIssueNumber", "cardCV2Identifier", "chipCodeType", "cardChipApplicationIdentifier", new Text("en", "cardHolderName")),
                new BankTransfer("bankTransferIdentifier", new Date(), "originBankName", "bankDestinationAccount"), null, PersonRoleKind.APPLICANT);

        eu.ohim.sp.core.domain.payment.Payment paymentCore = new DozerBeanMapper().map(payment, eu.ohim.sp.core.domain.payment.Payment.class);

        Assert.assertEquals(payment.getKind().value(), paymentCore.getKind().toString());
        Assert.assertEquals(payment.getIdentifier(), paymentCore.getIdentifier());
        Assert.assertEquals(payment.getReference(), paymentCore.getReference());
        Assert.assertEquals(payment.getAmount(), paymentCore.getAmount());
        Assert.assertEquals(payment.getCardAccount().getCardValidityStartDate(), paymentCore.getCardAccount().getCardValidityStartDate());
        Assert.assertEquals(payment.getCardAccount().getCardCustomerIdentifier(), paymentCore.getCardAccount().getCardCustomerIdentifier());
        Assert.assertEquals(payment.getCardAccount().getCardIssuesIdentifier(), paymentCore.getCardAccount().getCardIssuesIdentifier());
        Assert.assertEquals(payment.getCardAccount().getCardCV2Identifier(), paymentCore.getCardAccount().getCardCV2Identifier());

        Assert.assertEquals(payment.getBankTransfer().getOriginBankName(), paymentCore.getBankTransfer().getOriginBankName());
        Assert.assertEquals(payment.getBankTransfer().getBankTransferIdentifier(), paymentCore.getBankTransfer().getBankTransferIdentifier());
        Assert.assertEquals(payment.getBankTransfer().getBankDestinationAccount(), paymentCore.getBankTransfer().getBankDestinationAccount());
        Assert.assertEquals(payment.getBankTransfer().getBankTransferDate(), paymentCore.getBankTransfer().getBankTransferDate());

        Assert.assertEquals(payment.getAccount().getAccountIdentifier(), paymentCore.getAccount().getAccountIdentifier());
        Assert.assertEquals(payment.getAccount().getAccountKind(), paymentCore.getAccount().getAccountKind());
        Assert.assertEquals(payment.getAccount().getAccountDebitKind().value(), paymentCore.getAccount().getAccountDebitKind().name());
        Assert.assertEquals(payment.getAccount().getAccountHolderName().getLanguage(), paymentCore.getAccount().getAccountHolderName().getLanguage());
        Assert.assertEquals(payment.getAccount().getAccountHolderName().getValue(), paymentCore.getAccount().getAccountHolderName().getValue());

    }

}
