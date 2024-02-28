package eu.ohim.sp.dsefiling.ui.controller.payment;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.LocaleComponent;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.epayment.EPaymentService;
import eu.ohim.sp.dsefiling.ui.adapter.DSFlowBeanFactory;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;

public class DSPaymentRedirectControllerTest
{

    @Mock
    private EPaymentService paymentService;

    @Mock
    private DSFlowBean flowBean;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private DSFlowBeanFactory flowBeanFactory;

    @Mock
    private LocaleComponent localeComponent;

    @Mock
    private PersonServiceInterface personService;

    @InjectMocks
    DSPaymentRedirectController paymentRedirectController = new DSPaymentRedirectController();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        //when(localeComponent.getLocale(request)).thenReturn(new LocaleComponent.FSPLocale());//.getValue()).thenReturn("some locale");
    }

    //@Test
//    public void paymentIFrameTest()
//    {
//        paymentRedirectController.paymentIFrame(request, "flowkey", "flowmode");
//    }

    @Test
    public void handleExceptionTest()
    {
        ModelAndView result = paymentRedirectController.handleException(new Exception("some message"));
        assertEquals("some message", result.getModel().get("message"));
    }
}
