package eu.ohim.sp.external.domain.trademark;

import eu.ohim.sp.external.domain.common.Text;
import eu.ohim.sp.external.domain.epayment.*;
import org.dozer.DozerBeanMapper;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 27/01/14
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class TradeMarkApplicationTest {
    @Test
    public void testTradeMarkApplication() {
        ObjectFactory objectFactory = new ObjectFactory();
        TradeMarkApplication tradeMarkApplication = objectFactory.createTradeMarkApplication();
        tradeMarkApplication.setPayments(new ArrayList<PaymentFee>());


        eu.ohim.sp.external.domain.epayment.ObjectFactory paymentObjectFactory = new eu.ohim.sp.external.domain.epayment.ObjectFactory();
        PaymentFee paymentFee = paymentObjectFactory.createPaymentFee();
        paymentFee.setAccount(paymentObjectFactory.createAccount());
        paymentFee.getAccount().setAccountIdentifier("000000001");
        paymentFee.getAccount().setAccountKind("accountKind");
        paymentFee.getAccount().setAccountHolderName(new Text());
        paymentFee.getAccount().getAccountHolderName().setLanguage("en");
        paymentFee.getAccount().getAccountHolderName().setValue("value");
        paymentFee.setCardAccount(paymentObjectFactory.createCardAccount());
        paymentFee.getCardAccount().setCardExpiryDate(new Date());
        paymentFee.getCardAccount().setCardCustomerIdentifier("cardIdentifier");
        paymentFee.getCardAccount().setCardKindCode(CardKindCode.CREDIT_CARD);
        paymentFee.getCardAccount().setCardValidityStartDate(new Date());


        paymentFee.setFees(new ArrayList<MatchedFee>());
        paymentFee.getFees().add(paymentObjectFactory.createMatchedFee());

        paymentFee.getFees().get(0).setFee(paymentObjectFactory.createFee());
        paymentFee.getFees().get(0).getFee().setAmount(123);
        paymentFee.getFees().get(0).getFee().setFeeType(paymentObjectFactory.createFeeType());
        paymentFee.getFees().get(0).getFee().getFeeType().setCode("F001");
        paymentFee.getFees().get(0).getFee().getFeeType().setCurrencyCode("EUR");
        paymentFee.getFees().get(0).getFee().getFeeType().setDefaultValue(new Double(135));
        paymentFee.getFees().get(0).getFee().getFeeType().setDescription("description");

        paymentFee.getFees().get(0).setMatchedAmount(new Double(123));


        paymentFee.setCurrencyCode("EUR");
        paymentFee.setBankTransfer(paymentObjectFactory.createBankTransfer());
        paymentFee.getBankTransfer().setBankDestinationAccount("000000000000000001");
        paymentFee.getBankTransfer().setBankTransferDate(new Date());
        paymentFee.getBankTransfer().setBankTransferIdentifier("identi");
        paymentFee.getBankTransfer().setOriginBankName("EUROBANK");

        paymentFee.setComment("comment");
        paymentFee.setReference("reference");
        paymentFee.setKind(PaymentKind.BANK_TRANSFER);

        paymentFee.setIdentifier("Payment0011");
        paymentFee.setStatus(PaymentStatusCode.ATTACHED);

        tradeMarkApplication.getPayments().add(paymentFee);
        tradeMarkApplication.setApplicationFilingDate(new Date());
        tradeMarkApplication.setTradeMark(new TradeMark());
        tradeMarkApplication.setApplicationFilingNumber("filing number");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        eu.ohim.sp.core.domain.trademark.TradeMarkApplication tradeMarkApplicationCore =
                dozerBeanMapper.map(tradeMarkApplication, eu.ohim.sp.core.domain.trademark.TradeMarkApplication.class);


        assertEquals(tradeMarkApplication.getApplicationFilingDate(), tradeMarkApplicationCore.getApplicationFilingDate());
        assertEquals(tradeMarkApplication.getApplicationFilingNumber(), tradeMarkApplicationCore.getApplicationFilingNumber());

        assertEquals(tradeMarkApplication.getPayments().get(0).getCurrencyCode(), tradeMarkApplicationCore.getPayments().get(0).getCurrencyCode());

        assertEquals(tradeMarkApplication.getPayments().size(), tradeMarkApplicationCore.getPayments().size());
        assertEquals(tradeMarkApplication.getPayments().get(0).getAccount().getAccountIdentifier(), tradeMarkApplicationCore.getPayments().get(0).getAccount().getAccountIdentifier());
        assertEquals(tradeMarkApplication.getPayments().get(0).getAccount().getAccountHolderName().getLanguage(), tradeMarkApplicationCore.getPayments().get(0).getAccount().getAccountHolderName().getLanguage());
        assertEquals(tradeMarkApplication.getPayments().get(0).getAccount().getAccountHolderName().getValue(), tradeMarkApplicationCore.getPayments().get(0).getAccount().getAccountHolderName().getValue());
        assertEquals(tradeMarkApplication.getPayments().get(0).getAccount().getAccountKind(), tradeMarkApplicationCore.getPayments().get(0).getAccount().getAccountKind());
        assertEquals(tradeMarkApplication.getPayments().get(0).getCardAccount().getCardExpiryDate(), tradeMarkApplicationCore.getPayments().get(0).getCardAccount().getCardExpiryDate());
        assertEquals(tradeMarkApplication.getPayments().get(0).getCardAccount().getCardCustomerIdentifier(), tradeMarkApplicationCore.getPayments().get(0).getCardAccount().getCardCustomerIdentifier());
        //Same value different string representation
        //assertEquals(tradeMarkApplication.getPayments().get(0).getCardAccount().getCardKindCode().value(), tradeMarkApplicationCore.getPayments().get(0).getCardAccount().getCardKindCode().toString());
        assertEquals(tradeMarkApplication.getPayments().get(0).getCardAccount().getCardValidityStartDate(), tradeMarkApplicationCore.getPayments().get(0).getCardAccount().getCardValidityStartDate());

        assertEquals(tradeMarkApplication.getPayments().get(0).getFees().get(0).getFee().getAmount(), tradeMarkApplicationCore.getPayments().get(0).getFees().get(0).getFee().getAmount().doubleValue(), 0.001);
        assertEquals(tradeMarkApplication.getPayments().get(0).getFees().get(0).getFee().getFeeType().getCode(),
                tradeMarkApplicationCore.getPayments().get(0).getFees().get(0).getFee().getFeeType().getCode());
        assertEquals(tradeMarkApplication.getPayments().get(0).getFees().get(0).getFee().getFeeType().getCurrencyCode(),
                tradeMarkApplicationCore.getPayments().get(0).getFees().get(0).getFee().getFeeType().getCurrencyCode());
        assertEquals(tradeMarkApplication.getPayments().get(0).getFees().get(0).getFee().getFeeType().getDescription(),
                tradeMarkApplicationCore.getPayments().get(0).getFees().get(0).getFee().getFeeType().getDescription());
        assertEquals(tradeMarkApplication.getPayments().get(0).getFees().get(0).getFee().getFeeType().getDefaultValue(),
                tradeMarkApplicationCore.getPayments().get(0).getFees().get(0).getFee().getFeeType().getDefaultValue());

        assertEquals(tradeMarkApplication.getPayments().get(0).getFees().get(0).getMatchedAmount(),
                tradeMarkApplicationCore.getPayments().get(0).getFees().get(0).getMatchedAmount());
        assertEquals(tradeMarkApplication.getPayments().get(0).getBankTransfer().getBankTransferDate(), tradeMarkApplicationCore.getPayments().get(0).getBankTransfer().getBankTransferDate());
        assertEquals(tradeMarkApplication.getPayments().get(0).getBankTransfer().getBankDestinationAccount(), tradeMarkApplicationCore.getPayments().get(0).getBankTransfer().getBankDestinationAccount());
        assertEquals(tradeMarkApplication.getPayments().get(0).getBankTransfer().getBankTransferIdentifier(), tradeMarkApplicationCore.getPayments().get(0).getBankTransfer().getBankTransferIdentifier());
        assertEquals(tradeMarkApplication.getPayments().get(0).getBankTransfer().getOriginBankName(), tradeMarkApplicationCore.getPayments().get(0).getBankTransfer().getOriginBankName());

        assertEquals(tradeMarkApplication.getPayments().get(0).getComment(), tradeMarkApplicationCore.getPayments().get(0).getComment());
        assertEquals(tradeMarkApplication.getPayments().get(0).getReference(), tradeMarkApplicationCore.getPayments().get(0).getReference());
        //Same value different string representation
        //assertEquals(tradeMarkApplication.getPayments().get(0).getKind().value(), tradeMarkApplicationCore.getPayments().get(0).getKind().value());
        assertEquals(tradeMarkApplication.getPayments().get(0).getIdentifier(), tradeMarkApplicationCore.getPayments().get(0).getIdentifier());
        assertEquals(tradeMarkApplication.getPayments().get(0).getStatus().value().toLowerCase(), tradeMarkApplicationCore.getPayments().get(0).getStatus().value().toLowerCase());

    }
}
