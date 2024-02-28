package eu.ohim.sp.eservices.ui.controllerTest;



import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.webflow.engine.model.FlowModel;
import org.springframework.webflow.engine.model.registry.FlowModelRegistry;

import eu.ohim.sp.common.ui.webflow.SpFlowRegistry;
import eu.ohim.sp.eservices.ui.controller.IndexController;


public class IndexControllerTest {

	@Mock
	private SpFlowRegistry flowRegistry;
	
	@InjectMocks
	IndexController indexController=new IndexController();
	
	@Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void index() {
		String[] stringL ={"test1"};
		
		FlowModel model = Mockito.mock(FlowModel.class);
		FlowModelRegistry flowModelRegistry = Mockito.mock(FlowModelRegistry.class);
		Mockito.when(flowRegistry.getFlowModelRegistry()).thenReturn(flowModelRegistry);
		Mockito.when(flowRegistry.getFlowDefinitionIds()).thenReturn(stringL);
		Mockito.when(flowRegistry.getFlowModelRegistry().getFlowModel("test1")).thenReturn(model);
		indexController.index();
		Mockito.when(model.getAbstract()).thenReturn("test");
		indexController.index();
	}
}
