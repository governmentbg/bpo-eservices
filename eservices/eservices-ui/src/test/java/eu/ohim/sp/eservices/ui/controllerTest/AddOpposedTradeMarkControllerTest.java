package eu.ohim.sp.eservices.ui.controllerTest;

import java.lang.reflect.Field;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.common.ui.adapter.TrademarkFactory;
import eu.ohim.sp.common.ui.adapter.opposition.LegalActVersionFactory;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.form.opposition.OpposedTradeMarkForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.AbsoluteGrounds;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.AbsoluteGrounds.AbsoluteGround;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RelativeGrounds;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RelativeGrounds.RelativeGround;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RevocationGrounds;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RevocationGrounds.RevocationGround;
import eu.ohim.sp.core.configuration.domain.legalActVersion.xsd.LegalActVersions;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.eservices.ui.controller.AddOpposedTradeMarkController;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.interfaces.ESTrademarkServiceInterface;

public class AddOpposedTradeMarkControllerTest {
	
		
	 @Mock
	    private ESFlowBean flowBean;
	    
	 @Mock
	    private TrademarkFactory trademarkFactory;
	    
	 @Mock
	    private ESTrademarkServiceInterface trademarkServiceInterface;
	 @Mock
	    private LegalActVersionFactory legalActVersionFactory;
	
	    private Integer maxNumber = -1;
	    
	 @Mock
	    private ConfigurationService configurationService;
	
	 @Mock
	    private  FormValidator validator ;
	 
	 
	 @InjectMocks
	 AddOpposedTradeMarkController  addOpposedTradeMarkController=new AddOpposedTradeMarkController();
	 
	 
	
	 @Before
	   public void setUp()
	    {
	        MockitoAnnotations.initMocks(this);
	    }
	 
	 
	 
	 @Test
	 public void  addOpposedTradeMarkCheckMatches(){
		 
		 
		
	//	 addOpposedTradeMarkController.addOpposedTradeMarkCheckMatches(null, null, null, null);
		 
	 }
	 
	 
	 @Test
	 public void  onSubmitOpposedTradeMark(){
		 
		 
	//	 OpposedTradeMarkForm command, BindingResult result, Boolean ignoreMatches, String applicationType
		 
		 OpposedTradeMarkForm command=new OpposedTradeMarkForm();
		 command.setApplicant("applicant");
		 
		 command.setApplicationExtent("applicationExtent");
		 
		 BindingResult result=Mockito.mock(BindingResult.class);
		 Boolean ignoreMatches=true; 
		 String applicationType="applicationType";
		 TradeMark tradeMark=new TradeMark();
		 
		 tradeMark.setApplicationLanguage("applicationLanguage");
		 
		Mockito.when(trademarkFactory.convertTo(command)).thenReturn(tradeMark);
		
		LegalActVersions value=new LegalActVersions();
		
		eu.ohim.sp.core.configuration.domain.legalActVersion.xsd.LegalActVersions.LegalActVersion e=new eu.ohim.sp.core.configuration.domain.legalActVersion.xsd.LegalActVersions.LegalActVersion();
		
		value.getLegalActVersion().add(e);
		
		Mockito.when(configurationService.getObject(ConfigurationServiceDelegatorInterface.LEGAL_ACT_VERSIONS_PARAM , null, LegalActVersions.class)).thenReturn(value);
		
		
//		 List<RelativeGrounds> relativeGrounds=new ArrayList<RelativeGrounds>();
//		 
//		 RelativeGround relativeGround=new RelativeGround();
//		 
//		 relativeGround= new RelativeGround();
//		 
//		 relativeGround.setCode("value");
//		 
//		 relativeGrounds.add(relativeGround);
//		
		
		//Mockito.when(configurationService.getObject(ConfigurationServiceDelegatorInterface.RELATIVE_GROUNDS_PARAM , null, RelativeGrounds.class).getRelativeGround(relativeGround);
		
		
		

		
		
		
		
		
		
 Mockito.when( configurationService.getObject(ConfigurationServiceDelegatorInterface.ABSOLUTE_GROUNDS_PARAM , null, AbsoluteGrounds.class)).thenReturn( new AbsoluteGrounds());
		 
		 Mockito.when( configurationService.getObject(ConfigurationServiceDelegatorInterface.RELATIVE_GROUNDS_PARAM , null, RelativeGrounds.class)).thenReturn( new RelativeGrounds());
		 
		 Mockito.when( configurationService.getObject(ConfigurationServiceDelegatorInterface.REVOCATION_GROUNDS_PARAM , null, RevocationGrounds.class)).thenReturn( new RevocationGrounds());
		 
		//  validator.validate(command, result, flowScopeDetails);
		 
		 FlowScopeDetails flowScopeDetails= new FlowScopeDetails();
		 
		 flowScopeDetails.setStateId("stateId");
		 
		 Field field=	ReflectionUtils.findField(AddOpposedTradeMarkController.class, "flowScopeDetails");
			
			ReflectionUtils.makeAccessible(field);
			
			ReflectionUtils.setField(field, addOpposedTradeMarkController,flowScopeDetails);
			
//			Object target=new Object();
//			Errors errors=Mockito.mock(Errors.class);
			
			AddAbstractController addAbstractController=new AddAbstractController();
			
			addAbstractController.setValidator(validator);
			
		 
			//Mockito.when(validator.validate(target, errors)):
		 
		 
		 addOpposedTradeMarkController.onSubmitOpposedTradeMark(command, result, ignoreMatches, applicationType);
		 
	 }
	 
	 
	 
	 @Test
	 public void  formBackingOpposedTradeMark(){
		 
		 
		 
		 
		 addOpposedTradeMarkController.formBackingOpposedTradeMark("id");
		 
	 }
	 
	 @Test
	 public void  refreshAvaibleLegalActVersions(){
		 
		 TradeMark tm=new TradeMark();
		 
		 String applicationType="";
		 
		 String   generalComponent=null;
		 
		 
		 RelativeGrounds relativeGrounds= new RelativeGrounds();
		 
		 RelativeGround relativeGround=new RelativeGround();
		 
		 relativeGround.setCode("value");
		 
		 relativeGround.setEarlierEntitlementRightType("value");
		 
		 
		 relativeGround.setApplicationType("value");
		 
		 relativeGround.setLegalActVersion("value");
		 
		 relativeGround.setEnabled(true);
		 
		 relativeGrounds.getRelativeGround().add(relativeGround);
		 
	
		 
		 
	 AbsoluteGrounds absoluteGrounds= new AbsoluteGrounds();
		 
	 AbsoluteGround absoluteGround=new AbsoluteGround();
		 
	 absoluteGround.setCode("value");
		 
	 absoluteGround.setLanguage("value");
	 absoluteGround.setApplicationType("");
	 
	 absoluteGround.setLegalActVersion("value");
	 
	 absoluteGround.setEnabled(true);
		 
	 absoluteGrounds.getAbsoluteGround().add(absoluteGround);
		 
	
	 
	 
	 
	 RevocationGrounds revocationGrounds= new RevocationGrounds();
	 
	 RevocationGround revocationGround=new RevocationGround();
		 
	 revocationGround.setCode("value");
		 
	 revocationGround.setLanguage("value");
	 
	 revocationGround.setApplicationType("");
	 
	 revocationGround.setLegalActVersion("value");
	 
	 revocationGround.setEnabled(true);
 		 
	 revocationGrounds.getRevocationGround().add(revocationGround);
		 
		
	 
	 
	 LegalActVersions value=new LegalActVersions();
		
	 eu.ohim.sp.core.configuration.domain.legalActVersion.xsd.LegalActVersions.LegalActVersion e=new eu.ohim.sp.core.configuration.domain.legalActVersion.xsd.LegalActVersions.LegalActVersion();
		
		value.getLegalActVersion().add(e);
	 
	 
	 
	 
	 
	 
	 
	 
		 
		 Mockito.when( configurationService.getObject(ConfigurationServiceDelegatorInterface.LEGAL_ACT_VERSIONS_PARAM , generalComponent, LegalActVersions.class)).thenReturn(value);
	 
	 
	// configurationService.getObject(ConfigurationServiceDelegatorInterface.ABSOLUTE_GROUNDS_PARAM , generalComponent, AbsoluteGrounds.class).getAbsoluteGround();
		 
		 Mockito.when( configurationService.getObject(ConfigurationServiceDelegatorInterface.ABSOLUTE_GROUNDS_PARAM , generalComponent, AbsoluteGrounds.class)).thenReturn(absoluteGrounds);
		 
		// configurationService.getObject(ConfigurationServiceDelegatorInterface.RELATIVE_GROUNDS_PARAM , generalComponent, RelativeGrounds.class).getRelativeGround();
		 
		 Mockito.when( configurationService.getObject(ConfigurationServiceDelegatorInterface.RELATIVE_GROUNDS_PARAM , generalComponent, RelativeGrounds.class)).thenReturn(relativeGrounds);
		 
		 Mockito.when( configurationService.getObject(ConfigurationServiceDelegatorInterface.REVOCATION_GROUNDS_PARAM , generalComponent, RevocationGrounds.class)).thenReturn(revocationGrounds);
		 
		 //List< RelativeGround> relativeGroundList=new ArrayList<RelativeGround>();
		 
	//	 relativeGroundList.add(new RelativeGround());
		 
		 RelativeGround gtr=new RelativeGround();
		 
		 gtr.setApplicationType("");
		 
		//dfgdfg ////Mockito.when( configurationService.getObject(configurationService.getObject(ConfigurationServiceDelegatorInterface.RELATIVE_GROUNDS_PARAM , generalComponent, RelativeGrounds.class)).thenReturn(gtr));
		 
		 
		
		 
		 
		 
		 
		 addOpposedTradeMarkController.refreshAvaibleLegalActVersions(tm,applicationType);
		 
		 addOpposedTradeMarkController.refreshAvaibleLegalActVersions(tm,"tm-revocation");
		 
	 }
	 
	 
	 @Test
	 public void getOpposedTM(){
		 
		 
		 addOpposedTradeMarkController.getOpposedTM("id");
		 
		 
	 }
	 
	 
	 @Test
	 public void getMaxNumber(){
		 
		 
		 addOpposedTradeMarkController.getMaxNumber();
		 
		 
	 }
	 
	 @Test
	 public void convertToLegalActUI(){
		 
		
		
		 LegalActVersions.LegalActVersion la = new LegalActVersions.LegalActVersion();
		 String applicationType="tm-revocation";
		 
		 la.setCode("code");
		 la.setEntryIntoForceDate(new Date());
		 la.setEndApplicabilityDate(new Date());
		 
		 la.setValue("");
		 
		 addOpposedTradeMarkController.convertToLegalActUI(la,applicationType);
		 
		 
		  applicationType="tm-invalidity";
		 
		 addOpposedTradeMarkController.convertToLegalActUI(la,applicationType);
		  applicationType="tm-opposition";
			 
			 addOpposedTradeMarkController.convertToLegalActUI(la,applicationType);
		 
	 }
	    
	

}
