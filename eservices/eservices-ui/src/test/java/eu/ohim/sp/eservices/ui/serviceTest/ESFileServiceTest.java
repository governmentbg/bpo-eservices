package eu.ohim.sp.eservices.ui.serviceTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.eservices.ui.service.ESFileService;
import eu.ohim.sp.eservices.ui.service.FormUtil;

public class ESFileServiceTest {
	
	@Mock
	private FormUtil formUtil;
	
	@InjectMocks
	ESFileService eSFileService=new ESFileService();
	
	@Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void getModule(){
		
		eSFileService.getModule();
		
	}

}
