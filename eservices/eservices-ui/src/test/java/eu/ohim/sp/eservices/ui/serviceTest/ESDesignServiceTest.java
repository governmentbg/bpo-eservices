/*******************************************************************************
 * * $Id:: ESTrademarkService.java 50771 2012-11-14 15:10:27Z medinju        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.eservices.ui.serviceTest;

import java.util.HashMap;
import java.util.List;

import eu.ohim.sp.common.ui.form.design.ESDesignDetailsListForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.design.ESDesignFactory;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.register.DesignSearchService;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.eservices.ui.service.ESDesignService;
import eu.ohim.sp.eservices.ui.service.interfaces.ESApplicationServiceInterface;


public class ESDesignServiceTest{
	
	@Mock
	private DesignSearchService designService;
	
	@Mock
	private ESApplicationServiceInterface applicationService;
	
	@Mock
	private SectionViewConfiguration sectionViewConfiguration;	
	
	@Mock
	private ESDesignFactory dsFactory;
	
	@Mock
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;	
	
	@Mock
	private RulesService businessRulesService;
	
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
	}
	 
	@InjectMocks
	ESDesignService eSDesignService=new  ESDesignService();
	
	
	
	@Test
	public void getDesignApplicationTry() {
		String designId="designId";
		String idreserventity="IDRESER";
        ESDesignDetailsListForm result = eSDesignService.getDesignApplication(designId);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getDesignDetailsFormList()== null || result.getDesignDetailsFormList().isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=SPException.class)
	public void getDesignApplicationCatch() {
		String designId="designId";
		String idreserventity="IDRESER";
		Mockito.when(dsFactory.convertListFromSingle(Mockito.any(DesignApplication.class))).thenThrow(Exception.class);
		ESDesignDetailsListForm result = eSDesignService.getDesignApplication(designId);
		Assert.assertNotNull(result);
		Assert.assertTrue(result.getDesignDetailsFormList()== null || result.getDesignDetailsFormList().isEmpty());
	}
	
	
	@Test
	public void getDesignAutocomplete() {
		String office="office"; 
		String text="text"; 
		Integer numberOfResults=7;
		Assert.assertNull(eSDesignService.getDesignAutocomplete(office, text, numberOfResults, "ds-renewal"));
	}
	
	
	@Test
	public void validateDesign() {

		String module="module";
		ESDesignDetailsForm design=Mockito.mock(ESDesignDetailsForm.class);
		RulesInformation rulesInformation=Mockito.mock(RulesInformation.class);
		Errors errors=Mockito.mock(Errors.class); 
		String flowModeId="flowModeId";
		Mockito.when(sectionViewConfiguration.getViewConfiguration()).thenReturn(new HashMap<String, Sections>());
		Mockito.when(rulesInformation.getCustomObjects()).thenReturn(new HashMap<String, Object>());
		Mockito.when(design.getImported()).thenReturn(true);
		
		Assert.assertNull(eSDesignService.validateDesign(module,design,rulesInformation,errors,flowModeId));
		
		
		Mockito.when(design.getCheckBdBlocking()).thenReturn(true);
		Assert.assertNull(eSDesignService.validateDesign(module,design,rulesInformation,errors,flowModeId));
		
	}
}
