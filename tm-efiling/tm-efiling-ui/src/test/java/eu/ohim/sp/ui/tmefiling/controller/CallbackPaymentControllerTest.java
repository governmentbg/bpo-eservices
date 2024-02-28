package eu.ohim.sp.ui.tmefiling.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentResult;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentStatus;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.core.epayment.EPaymentService;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;

public class CallbackPaymentControllerTest {

	@InjectMocks
	private CallbackPaymentController callbackPaymentController;

	@Mock
	private EPaymentService paymentService;

	@Mock
	private FlowBeanFactory flowBeanFactory;

	@Mock
	private FlowBeanImpl flowBean;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void paymentCallbackTest() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Map<String, String[]> parametermap = new HashMap<>();
		parametermap.put("a", new String[] { "b" });
		parametermap.put("b", new String[] { "c" });
		when(request.getParameterMap()).thenReturn(parametermap);
		String applicationFilingNumber = "13241234";
		TradeMarkApplication application = mock(TradeMarkApplication.class);
		PaymentResult paymentResult = new PaymentResult();
		paymentResult.setPaymentStatus(PaymentStatus.PAYMENT_OK);
		paymentResult.setResultMessage("everything went ok");

		PaymentForm paymentForm = mock(PaymentForm.class);
		when(flowBean.getPaymentForm()).thenReturn(paymentForm);
		when(paymentService.notifyPaymentResult(any(), any(), any(), any())).thenReturn(paymentResult);
		when(flowBeanFactory.convertToTradeMarkApplication(flowBean)).thenReturn(application);

		ModelAndView r = callbackPaymentController.paymentCallback(request, applicationFilingNumber);

		assertNotNull(r);

		verify(paymentForm).setPaymentResult(paymentResult);
		verify(flowBeanFactory).convertToTradeMarkApplication(flowBean);

		assertEquals(paymentResult.getPaymentStatus().name(), r.getModelMap().get("resultStatus"));
		assertEquals(StringEscapeUtils.escapeJavaScript(paymentResult.getResultMessage()),
				r.getModelMap().get("message"));
	}

	@Test(expected = SPException.class)
	public void paymentCallbackTestNullPaymentResult() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Map<String, String[]> parametermap = new HashMap<>();
		parametermap.put("a", new String[] { "b" });
		parametermap.put("b", new String[] { "c" });
		when(request.getParameterMap()).thenReturn(parametermap);
		String applicationFilingNumber = "13241234";
		TradeMarkApplication application = mock(TradeMarkApplication.class);
		PaymentResult paymentResult = new PaymentResult();
		paymentResult.setPaymentStatus(PaymentStatus.PAYMENT_OK);
		paymentResult.setResultMessage("everything went ok");

		PaymentForm paymentForm = mock(PaymentForm.class);
		when(flowBean.getPaymentForm()).thenReturn(paymentForm);
		when(paymentService.notifyPaymentResult(any(), any(), any(), any())).thenReturn(null);
		when(flowBeanFactory.convertToTradeMarkApplication(flowBean)).thenReturn(application);

		ModelAndView r = callbackPaymentController.paymentCallback(request, applicationFilingNumber);

		assertNotNull(r);

		verify(paymentForm).setPaymentResult(paymentResult);
		verify(flowBeanFactory).convertToTradeMarkApplication(flowBean);

		assertEquals(paymentResult.getPaymentStatus().name(), r.getModelMap().get("resultStatus"));
		assertEquals(StringEscapeUtils.escapeJavaScript(paymentResult.getResultMessage()),
				r.getModelMap().get("message"));
	}

	@Test
	public void handleExceptionTest() {

		Throwable e = mock(Throwable.class);
		when(e.getMessage()).thenReturn("error message");
		ModelAndView r = callbackPaymentController.handleException(e);

		assertNotNull(r);
		assertEquals(StringEscapeUtils.escapeJavaScript(e.getMessage()), r.getModelMap().get("message"));
	}

}
