package eu.ohim.sp.external.classification.inside;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters;
import eu.ohim.sp.core.domain.classification.wrapper.*;
import eu.ohim.sp.external.classification.outside.NiceClassificationClientBean;
import eu.ohim.sp.external.utils.AdapterEnabled;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 12/11/13
 * Time: 19:09
 * To change this template use File | Settings | File Templates.
 */
public class AdapterDisabledTest {


    @InjectMocks
    NiceClassificationClientBean classificationClientBean;

    @Mock
    ConfigurationService configurationService;

    @InjectMocks
    AdapterEnabled adapterEnabled;

    @Before
    public void setup() {
        Adapters adapters = new Adapters();
        Adapters.Adapter adapter = new Adapters.Adapter();

        String local_address = "localhost";

        adapter.setName("classification");
        adapter.setEnabled(false);
        adapter.setWsdlLocation("http://" + local_address + ":8380/fsp/ws/classification/services?WSDL");
        adapters.getAdapter().add(adapter);

        MockitoAnnotations.initMocks(this);
        when(configurationService.getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class))).thenReturn(adapters);

        classificationClientBean.init();

        verify(configurationService, times(1)).getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class));
    }

    public InvocationContext getContext() {
        InvocationContext c = new InvocationContext() {
            @Override
            public Object getTarget() {
                return null;
            }

            @Override
            public Method getMethod() {
                return null;
            }

            @Override
            public Object[] getParameters() {
                return new Object[0];
            }

            @Override
            public void setParameters(Object[] objects) {
            }

            @Override
            public Map<String, Object> getContextData() {
                HashMap<String, Object> a = new HashMap<>();
                a.put("ADAPTER_NAME", "classification");
                return a;
            }

            @Override
            public Object getTimer() {
                return null;
            }

            @Override
            public Object proceed() throws Exception {
                return null;
            }
        };
        c.setParameters(null);
        return c;
    }

    @Test(expected = SPException.class)
    public void testGetClassScopes() throws Exception {
        adapterEnabled.adapterEnabled(getContext());
    }
}
