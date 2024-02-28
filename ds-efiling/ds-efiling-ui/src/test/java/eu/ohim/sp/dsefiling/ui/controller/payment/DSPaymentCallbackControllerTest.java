package eu.ohim.sp.dsefiling.ui.controller.payment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentResult;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentStatus;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.epayment.EPaymentService;
import eu.ohim.sp.dsefiling.ui.adapter.DSFlowBeanFactory;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;

public class DSPaymentCallbackControllerTest
{
    private Map<String, String> paramMap;

    @Mock
    private EPaymentService paymentService;

    @Mock
    private DSFlowBean flowBean;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    private DSFlowBeanFactory flowBeanFactory;

    @InjectMocks
    DSPaymentCallbackController paymentCallbackController = new DSPaymentCallbackController();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);

        paramMap = new HashMap<String, String>();
        paramMap.put("a key", "a value");
        when(request.getParameterMap()).thenAnswer(setupDummyMapAnswer(paramMap));

    }

    @Test
    public void paymentCallbackTest1()
    {
        PaymentResult payResult = new PaymentResult();
        payResult.setPaymentStatus(PaymentStatus.PAYMENT_OK);
        DesignApplication app = new DesignApplication();
        PaymentForm paymentForm = new PaymentForm();
        when(flowBeanFactory.convertTo(any(DSFlowBean.class))).thenReturn(app);
        when(flowBean.getPaymentForm()).thenReturn(paymentForm);
        when(paymentService.notifyPaymentResult(any(String.class), any(String.class), any(IPApplication.class), any(Map.class)))
                .thenReturn(payResult);
        ModelAndView result = paymentCallbackController.paymentCallback(request, null);

        assertEquals(PaymentStatus.PAYMENT_OK.name(), result.getModel().get("resultStatus"));
    }

    @Test(expected = SPException.class)
    public void paymentCallbackTest2()
    {
        PaymentResult payResult = new PaymentResult();
        payResult.setPaymentStatus(PaymentStatus.PAYMENT_OK);
        DesignApplication app = new DesignApplication();
        PaymentForm paymentForm = new PaymentForm();
        when(flowBeanFactory.convertTo(any(DSFlowBean.class))).thenReturn(app);
        when(flowBean.getPaymentForm()).thenReturn(paymentForm);
        when(paymentService.notifyPaymentResult(any(String.class), any(String.class), any(IPApplication.class), any(Map.class)))
                .thenReturn(null);

        paymentCallbackController.paymentCallback(request, null);
    }

    @Test
    public void handleExceptionTest()
    {
        ModelAndView result = paymentCallbackController.handleException(new Exception("some message"));
        assertEquals("some message", result.getModel().get("message"));
    }

    public static <T, V> Answer<Map<T, V>> setupDummyMapAnswer(Map<T, V> map)
    {
        final Map<T, V> someMap = map;

        Answer<Map<T, V>> answer = new Answer<Map<T, V>>() {
            public Map<T, V> answer(InvocationOnMock invocation) throws Throwable {
                return someMap;
            }
        };
        return answer;
    }
}
