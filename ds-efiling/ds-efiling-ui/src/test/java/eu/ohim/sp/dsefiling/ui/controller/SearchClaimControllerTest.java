package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author serrajo
 */
public class SearchClaimControllerTest {
    @Mock
    DSFlowBean dsFlowBean;

    @InjectMocks
    SearchClaimController searchClaimController = new SearchClaimController();
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void searchClaim() {
//    	String result = searchClaimController.autocompleteService("some id", "some office");
//    	Assert.assertNotNull(result);
    }
    
}
