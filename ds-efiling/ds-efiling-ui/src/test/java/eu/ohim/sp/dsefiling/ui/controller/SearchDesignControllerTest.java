package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author serrajo
 */
public class SearchDesignControllerTest {
    @Mock
    DSFlowBean dsFlowBean;

    @Mock
	ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Mock
	DSDesignsServiceInterface designsService;

    @InjectMocks
    SearchDesignController searchDesignController = new SearchDesignController();
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(configurationServiceDelegator.getValueFromGeneralComponent("service.design.autocomplete.maxResults")).thenReturn("5");
    }
    
    @Test
    public void searchDesign() {
    	String result = searchDesignController.autocompleteService(anyString());
    	Assert.assertNull(result);
    }
    
}
