package eu.ohim.sp.eservices.ui.utilTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.eservices.ui.util.ESStatisticalLoggingInfoProvider;

public class ESStatisticalLoggingInfoProviderTest {
	
	 @InjectMocks
	 ESStatisticalLoggingInfoProvider eSStatisticalLoggingInfoProvider=new ESStatisticalLoggingInfoProvider();
	
	 @Before
	    public void setUp()
	    {
	        MockitoAnnotations.initMocks(this);
	    }
	 
	  @Test
	    public void getApplicationType(){
		  
		  String  flowModeId=null; 
		  
		  eSStatisticalLoggingInfoProvider.getApplicationType(flowModeId);
		  
		  flowModeId="oneform"; 
		  
		  eSStatisticalLoggingInfoProvider.getApplicationType(flowModeId);
		  
		  flowModeId="wizard"; 
		  
		  eSStatisticalLoggingInfoProvider.getApplicationType(flowModeId);
		  
		  flowModeId="other"; 
		  
		  eSStatisticalLoggingInfoProvider.getApplicationType(flowModeId);
		  
	  }
	
	

}
