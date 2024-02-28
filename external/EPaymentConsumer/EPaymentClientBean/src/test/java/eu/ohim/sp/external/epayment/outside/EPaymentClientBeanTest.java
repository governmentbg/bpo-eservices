package eu.ohim.sp.external.epayment.outside;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters;

import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequest;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequestParam;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentResult;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentStatus;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.ws.Endpoint;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

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

    @InjectMocks
    EPaymentClientBean paymentClientService;

    static Endpoint endpoint = null;

    @BeforeClass
    public static void setupEnpoint() {
    	endpoint = Endpoint.publish("http://localhost:8380/fsp/ws/payment/services", new PaymentManagementWS());
    	
    	assertTrue(endpoint.isPublished());
    }
    
    @Before
    public void setup() {

        Adapters adapters = new Adapters();
        Adapters.Adapter adapter = new Adapters.Adapter();

        adapter.setName("payment");
        adapter.setEnabled(true);
        adapter.setWsdlLocation("http://localhost:8380/fsp/ws/payment/services?wsdl");
        adapters.getAdapter().add(adapter);

        MockitoAnnotations.initMocks(this);
        when(systemConfigurationServiceInterface.getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class))).thenReturn(adapters);

        paymentClientService.init();

        verify(systemConfigurationServiceInterface, times(1)).getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class));
    }

    @AfterClass
    public static void shutdown() {
        endpoint.stop();
    }

    @Test
    public void testNotifyPaymentResult() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("comment", "comment");

        PaymentResult paymentResult = paymentClientService.notifyPaymentResult(map);
        //to be checked where is payment on response
        assertEquals(paymentResult.getPaymentStatus(), PaymentStatus.PAYMENT_OK);
        assertEquals(paymentResult.getResultMessage(), "comment");
    }

    @Test
    public void testNotifyPaymentResultError() {
        Map<String, String> map = new HashMap<String, String>();

        PaymentResult paymentResult = paymentClientService.notifyPaymentResult(map);
        assertNull(paymentResult);
    }

    @Test
    public void testRequestPayment() {
        Map<PaymentRequestParam, String> map = new HashMap<PaymentRequestParam, String>();
        map.put(PaymentRequestParam.FLOW_MODE, "whatever");

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
