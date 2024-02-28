package eu.ohim.sp.external.epayment.inside;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequest;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequestParam;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentResult;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentStatus;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.external.domain.common.Entry;
import eu.ohim.sp.external.domain.common.ParamsMap;
import eu.ohim.sp.external.domain.epayment.BankTransfer;
import eu.ohim.sp.external.domain.epayment.Payment;
import eu.ohim.sp.external.domain.epayment.PaymentKind;
import eu.ohim.sp.external.domain.epayment.PaymentStatusCode;
import eu.ohim.sp.external.domain.person.PersonRoleKind;
import eu.ohim.sp.external.epayment.inside.epayment_mock.MockEPayment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 09/09/13
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
public class EPaymentClientBeanTest {

    @Mock
    ConfigurationService systemConfigurationServiceInterface;

    @Mock
    MockEPayment ePayment;

    @InjectMocks
    EPaymentClientBean paymentClientService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        paymentClientService.init();
    }

    private eu.ohim.sp.external.domain.epayment.wrapper.PaymentResult result(ParamsMap map) {
        eu.ohim.sp.external.domain.epayment.wrapper.PaymentResult paymentResult = new eu.ohim.sp.external.domain.epayment.wrapper.PaymentResult();
        paymentResult.setPayment(new Payment(PaymentKind.BANK_TRANSFER, "identifier", "reference", PaymentStatusCode.DONE, new Double(200), "EUR", new Date(), "comment", null, null, new BankTransfer("bankTransferIdentifier", new Date(), "originBankName", "bankDestinationAccount"), null, PersonRoleKind.APPLICANT));
        paymentResult.setPaymentStatus(eu.ohim.sp.external.domain.epayment.wrapper.PaymentStatus.PAYMENT_OK);

        for (Entry entry : map.getEntry()) {
            if (entry.getKey().equals("comment")) {
                paymentResult.setResultMessage(entry.getValue());
            }
        }
        return paymentResult;
    }

    private eu.ohim.sp.external.domain.epayment.wrapper.PaymentRequest request() {
        eu.ohim.sp.external.domain.epayment.wrapper.PaymentRequest paymentRequest = new eu.ohim.sp.external.domain.epayment.wrapper.PaymentRequest();
        paymentRequest.setRedirectionParams(new ParamsMap());
        paymentRequest.getRedirectionParams().setEntry(new ArrayList<Entry>());

        Entry entry = new Entry();
        entry.setKey("FLOW_MODE");
        entry.setValue("whatever");

        paymentRequest.getRedirectionParams().getEntry().add(entry);

        entry = new Entry();
        entry.setKey("paymentId");
        entry.setValue("paymentId");
        paymentRequest.getRedirectionParams().getEntry().add(entry);

        entry = new Entry();
        entry.setKey("applicationNumber");
        entry.setValue("00001");
        paymentRequest.getRedirectionParams().getEntry().add(entry);

        entry = new Entry();
        entry.setKey("username");
        entry.setValue("papage");
        paymentRequest.getRedirectionParams().getEntry().add(entry);

        return paymentRequest;
    }

    @Test
    public void testNotifyPaymentResult() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("comment", "comment");

        ParamsMap pmap = new ParamsMap();
        pmap.getEntry().add(new Entry("comment", "comment"));

        when(ePayment.notifyPaymentResult(any())).thenReturn(result(pmap));

        PaymentResult paymentResult = paymentClientService.notifyPaymentResult(map);
        //to be checked where is payment on response
        assertEquals(paymentResult.getPaymentStatus(), PaymentStatus.PAYMENT_OK);
        assertEquals(paymentResult.getResultMessage(), "comment");
    }

    @Test
    public void testNotifyPaymentResultError() {
        Map<String, String> map = new HashMap<String, String>();
        when(ePayment.notifyPaymentResult(any())).thenReturn(null);
        PaymentResult paymentResult = paymentClientService.notifyPaymentResult(map);
        assertNull(paymentResult);
    }

    @Test
    public void testRequestPayment() {
        Map<PaymentRequestParam, String> map = new HashMap<PaymentRequestParam, String>();
        map.put(PaymentRequestParam.FLOW_MODE, "whatever");

        when(ePayment.requestPayment(any())).thenReturn(request());

        eu.ohim.sp.core.domain.user.FOUser user = new eu.ohim.sp.core.domain.user.FOUser();
        user.setFullName("George Papadopoulos");
        user.setUserName("papage");
        user.setStatus("status");

        IPApplication application = new DesignApplication();
        application.setApplicationFilingNumber("00001");

        //TO BE CHECKED
        PaymentRequest paymentRequest = paymentClientService.requestPayment(application, "paymentId", user, map);

        assertEquals("whatever", paymentRequest.getRedirectionParams().get("FLOW_MODE"));
        assertEquals(paymentRequest.getRedirectionParams().get("username"), "papage");
        assertEquals(paymentRequest.getRedirectionParams().get("applicationNumber"), "00001");
        assertEquals(paymentRequest.getRedirectionParams().get("paymentId"), "paymentId");

    }

    @Test
    public void testEmptyRequestPayment() {
        Map<PaymentRequestParam, String> map = new HashMap<PaymentRequestParam, String>();
        map.put(PaymentRequestParam.FLOW_MODE, "whatever");

        when(ePayment.requestPayment(any())).thenReturn(null);

        eu.ohim.sp.core.domain.user.User user = new eu.ohim.sp.core.domain.user.FOUser();
        user.setFullName("George Papadopoulos");
        user.setUserName("papage");
        user.setStatus("status");

        IPApplication application = null;

        PaymentRequest paymentRequest = paymentClientService.requestPayment(application, "paymentId", user, map);


        assertNull(paymentRequest);

    }

    @Test
    public void testRequestPaymentTradeMark() {
        Map<PaymentRequestParam, String> map = new HashMap<PaymentRequestParam, String>();
        map.put(PaymentRequestParam.FLOW_MODE, "whatever");

        when(ePayment.requestPayment(any())).thenReturn(request());

        eu.ohim.sp.core.domain.user.User user = new eu.ohim.sp.core.domain.user.FOUser();
        user.setFullName("George Papadopoulos");
        user.setUserName("papage");
        user.setStatus("status");

        IPApplication application = new TradeMarkApplication();
        application.setApplicationFilingNumber("00001");

        //TO BE CHECKED
        PaymentRequest paymentRequest = paymentClientService.requestPayment(application, "paymentId", user, map);

        assertEquals("whatever", paymentRequest.getRedirectionParams().get("FLOW_MODE"));
        assertEquals(paymentRequest.getRedirectionParams().get("applicationNumber"), "00001");
        assertEquals(paymentRequest.getRedirectionParams().get("paymentId"), "paymentId");

    }

}
