package eu.ohim.sp.eservices.ui.controllerTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.eservices.ui.controller.LanguageController;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;

public class LanguageControllerTest {
	
	@Mock
	private ESFlowBean flowBean;

	@Mock
	private SectionViewConfiguration sectionViewConfiguration;

	@Mock
	private ConfigurationServiceDelegatorInterface configurationService;

	/** The reloadable resource bundle message source. */
	@Mock
	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;

	@Mock
	FlowScopeDetails flowScopeDetails;
	
	@InjectMocks
	LanguageController  languageController=new LanguageController();
	
	@Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	 public void changeLanguageTranslation(){
		
		
		languageController.changeLanguageTranslation(true);
		
	}
	
	
	@Test
	 public void changeLanguage(){
		
		
		languageController.changeLanguage("",true,true);
		
	}
	
	
	@Test
	 public void hasLanguage(){
		
		
		Mockito.when(sectionViewConfiguration.getUsable(
				AvailableSection.LANGUAGES, "secLang",
				flowScopeDetails.getFlowModeId())).thenReturn(true);
		
		languageController.hasLanguage();
		
		
	}
	
	
	@Test
	 public void handleNoResultsException(){
		
		
		Throwable e=new Throwable();
		
		
		languageController.handleNoResultsException(e);
		
	}

	
	@Test
	 public void handleException(){
		
		
		Throwable e=new Throwable();
		
		
		languageController.handleException(e);
		
	}
	
	@Test
	 public void checkSecondLanguage(){
		
		flowBean=Mockito.mock(ESFlowBean.class);
		
		Mockito.when(flowBean.getSecLang()).thenReturn("value");
		
		languageController.checkSecondLanguage();
		
	}
	
}
