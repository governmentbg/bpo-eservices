package eu.ohim.sp.core.application.provided;

import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.domain.common.Result;
import org.jboss.resteasy.core.Dispatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.jboss.resteasy.mock.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.naming.InitialContext;
import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by marcoantonioalberoalbero on 4/11/16.
 */
public class ApplicationProviderTest {

    @Mock
    ApplicationService applicationServiceBean;

    @Mock
    InitialContext ic;

    @InjectMocks
    ApplicationResourceProvider arp;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(ic.lookup(anyString())).thenReturn(applicationServiceBean);
        byte [] array = "result".getBytes();
        when(applicationServiceBean.getApplication(null,null,null,"EF10100924122006")).thenReturn(array);
        Result r = new Result();
        r.setResult("OK");
        when(applicationServiceBean.updateDraftApplicationStatus("EF10100924122006", "received", "test")).thenReturn(r);
    }

    @Test
    public void testGetApplication() throws URISyntaxException {
        Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(arp);

        MockHttpRequest request = MockHttpRequest.get("/EF10100924122006");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        Assert.assertEquals(response.getStatus(), 200);
        dispatcher.getRegistry().removeRegistrations(arp.getClass());
    }

    @Test
    public void testPostApplication() throws URISyntaxException {
        Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(arp);

        MockHttpRequest request = MockHttpRequest.post("/EF10100924122006")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);


        request.addFormHeader("statuscode", "received");
        request.addFormHeader("statusdescription", "test");

        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        Assert.assertEquals(response.getStatus(), 202);
        dispatcher.getRegistry().removeRegistrations(arp.getClass());
    }
}
