package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * @author serrajo
 */
public class SearchDesignerControllerTest {
    @Mock
    DSFlowBean dsFlowBean;

    @InjectMocks
    SearchDesignerController searchDesignerController = new SearchDesignerController();
    
    @Mock
    ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Mock
    PersonServiceInterface personService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(configurationServiceDelegator.getValueFromGeneralComponent("service.designer.autocomplete.maxResults")).thenReturn("3");
    }
    
    @Test
    public void searchDesigner() {
    	String result = searchDesignerController.autocomplete("XCV((DSFASDF");
    	Assert.assertNull(result);
    }
    
}
