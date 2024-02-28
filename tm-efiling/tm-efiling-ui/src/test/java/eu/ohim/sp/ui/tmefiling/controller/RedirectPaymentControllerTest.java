package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.LocaleComponent;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequest;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.user.User;
import eu.ohim.sp.core.epayment.EPaymentService;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RedirectPaymentControllerTest {

    @Mock
    private EPaymentService paymentService;

    @Mock
    private FlowBeanImpl flowBean;

    @Mock
    private FlowBeanFactory flowBeanFactory;

    @Mock
    private LocaleComponent localeComponent;

    @Mock
    private PersonServiceInterface personService;

    @InjectMocks
	private RedirectPaymentController redirectPaymentController;

    @Test(expected = SPException.class)
    public void paymentIFrameTestWhenUnexpectedException() {

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        LocaleComponent.SPLocale sPLocale = Mockito.mock(LocaleComponent.SPLocale.class);
        String flowKey = "tm-test";
        String flowMode = "wizard";
        StringBuffer url = new StringBuffer("http://paymentIFrame");

        Mockito.when(localeComponent.getLocale(request)).thenReturn(sPLocale);
        Mockito.when(paymentService.requestPayment(Mockito.anyString(), Mockito.anyString(), Mockito.any(IPApplication.class), Mockito.any(User.class), Mockito.any())).thenThrow(RuntimeException.class);

        Mockito.when(request.getRequestURL()).thenReturn(url);

        redirectPaymentController.paymentIFrame(request, flowKey, flowMode);

    }

    @Test(expected = SPException.class)
    public void paymentIFrameTestPaymentException() {

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        LocaleComponent.SPLocale sPLocale = Mockito.mock(LocaleComponent.SPLocale.class);
        String flowKey = "tm-test";
        String flowMode = "wizard";
        StringBuffer url = new StringBuffer("http://paymentIFrame");

        Mockito.when(localeComponent.getLocale(request)).thenReturn(sPLocale);

        Mockito.when(request.getRequestURL()).thenReturn(url);

        redirectPaymentController.paymentIFrame(request, flowKey, flowMode);

    }

    @Test
    public void paymentIFrameTest() {

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        LocaleComponent.SPLocale sPLocale = Mockito.mock(LocaleComponent.SPLocale.class);
        PaymentRequest paymentRequest = Mockito.mock(PaymentRequest.class);
        String flowKey = "tm-test";
        String flowMode = "wizard";
        String redirectionURL = "http://RedirectionURL";
        StringBuffer url = new StringBuffer("http://paymentIFrame");

        Mockito.when(localeComponent.getLocale(request)).thenReturn(sPLocale);
        Mockito.when(paymentService.requestPayment(Mockito.anyString(), Mockito.anyString(), Mockito.any(IPApplication.class), Mockito.any(User.class), Mockito.any())).thenReturn(paymentRequest);

        Mockito.when(request.getRequestURL()).thenReturn(url);
        Mockito.when(paymentRequest.getRedirectionURL()).thenReturn(redirectionURL);

        ModelAndView model = redirectPaymentController.paymentIFrame(request, flowKey, flowMode);

        assertEquals(model.getModel().get("urlPayment"), redirectionURL);
        verify(paymentService, atLeastOnce()).requestPayment(Mockito.anyString(), Mockito.anyString(), Mockito.any(IPApplication.class), Mockito.any(User.class), Mockito.any());

    }

    @Test
    public void handleExceptionTest() {

        Throwable throwable = Mockito.mock(Throwable.class);

        ModelAndView model = redirectPaymentController.handleException(throwable);

        assertNotNull(model);

    }

}
