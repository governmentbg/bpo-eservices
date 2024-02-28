package eu.ohim.sp.eservices.ui.controllerTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.opposition.AbsoluteGrounds;
import eu.ohim.sp.common.ui.form.opposition.EarlierEntitlementRightDetails;
import eu.ohim.sp.common.ui.form.opposition.EarlierEntitlementRightInf;
import eu.ohim.sp.common.ui.form.opposition.GroundsRelativeForOpposition;
import eu.ohim.sp.common.ui.form.opposition.LegalActVersion;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.opposition.RelativeGrounds;
import eu.ohim.sp.common.ui.form.opposition.RevocationGrounds;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.eservices.ui.controller.AddOppositionBasisController;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.ESTrademarkService;


public class AddOppositionBasisControllerTest {

	@Mock
	 private FormValidator validator;
	
	@Mock
    private ESFlowBean flowBean;

	@Mock
	private FlowScopeDetails flowScopeDetails;
    
	@Mock
    private ESTrademarkService esTrademarkService;
	
	@Mock
	private ConfigurationServiceDelegatorInterface configurationService;
	
	@Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);

		Mockito.when(flowScopeDetails.getFlowModeId()).thenReturn("tm-objection");

    }
	
	@InjectMocks
	AddOppositionBasisController addOppositionBasisController=new AddOppositionBasisController();
	
    @Test
    public void getErrorEE(){
  
    	RelativeGrounds relativeGrounds=Mockito.mock(RelativeGrounds.class);
    	Mockito.when(relativeGrounds.getEarlierEntitlementRightTypeCode()).thenReturn("earlier");
    	LegalActVersion legalActVersion=Mockito.mock(LegalActVersion.class);
    	Mockito.when(legalActVersion.getCode()).thenReturn("code");
    	OppositionBasisForm command=Mockito.mock(OppositionBasisForm.class);
    	Mockito.when(command.getLegalActVersion()).thenReturn(legalActVersion);
    	Mockito.when(command.getRelativeGrounds()).thenReturn(relativeGrounds);
    	
    	Assert.assertNotNull(addOppositionBasisController.getErrorEE(command));   	 
    }
    
    @Test
    public void getErrorETM(){
    	 
    	RelativeGrounds relativeGrounds=Mockito.mock(RelativeGrounds.class);
    	Mockito.when(relativeGrounds.getEarlierEntitlementRightTypeCode()).thenReturn("earlier");
    	LegalActVersion legalActVersion=Mockito.mock(LegalActVersion.class);
    	Mockito.when(legalActVersion.getCode()).thenReturn("code");
    	OppositionBasisForm command=Mockito.mock(OppositionBasisForm.class);
    	Mockito.when(command.getLegalActVersion()).thenReturn(legalActVersion);
    	Mockito.when(command.getRelativeGrounds()).thenReturn(relativeGrounds);
    	
    	Assert.assertNotNull(addOppositionBasisController.getErrorETM(command));   	 
    	 
    }
    
//    @Test
//    public void getEditEE(){
//    	String id ="id"; 
//    	Mockito.when(addOppositionBasisController.innerFormBackingObject(Mockito.anyString(),flowBean,Mockito.any(AddParameters.class))).thenReturn(null);
//    	Assert.assertNotNull(addOppositionBasisController.getEditEE(id));     	
//    }
    
    
    @Test
    public void importTMDetails() {
    	
    	EarlierEntitlementRightDetails earlierEntitlementRightDetails=Mockito.mock(EarlierEntitlementRightDetails.class);
    	EarlierEntitlementRightInf earlierEntitlementRightInf=Mockito.mock(EarlierEntitlementRightInf.class); 
    	Mockito.when(earlierEntitlementRightInf.getEarlierEntitlementRightDetails()).thenReturn(earlierEntitlementRightDetails);
    	RelativeGrounds relativeGrounds=Mockito.mock(RelativeGrounds.class);
    	Mockito.when(relativeGrounds.getEarlierEntitlementRightTypeCode()).thenReturn("earlier");
    	Mockito.when(relativeGrounds.getEarlierEntitlementRightInf()).thenReturn(earlierEntitlementRightInf);
    	LegalActVersion legalActVersion=Mockito.mock(LegalActVersion.class);
    	Mockito.when(legalActVersion.getCode()).thenReturn("code");
    	OppositionBasisForm command=Mockito.mock(OppositionBasisForm.class);
    	Mockito.when(command.getLegalActVersion()).thenReturn(legalActVersion);
    	Mockito.when(command.getRelativeGrounds()).thenReturn(relativeGrounds);
    	
    	BindingResult result=Mockito.mock(BindingResult.class);
		String tradeMarkId="tradeMarkId";
		String earlierEntitleMent="earlierEntitleMent";
		String officeImport="officeImport";
    	String languagesFilter="en";
		Assert.assertNotNull(addOppositionBasisController.importTMDetails(command, result, tradeMarkId, earlierEntitleMent, officeImport, languagesFilter)); 
    	
		TMDetailsForm tm1 = Mockito.mock(TMDetailsForm.class);
		Mockito.when(esTrademarkService.getTradeMark(Mockito.anyString(), Mockito.anyString())).thenReturn(tm1);
		
		Assert.assertNotNull(addOppositionBasisController.importTMDetails(command, result, tradeMarkId, earlierEntitleMent, officeImport, languagesFilter)); 
    	
    	officeImport="NONE";
    	Assert.assertNotNull(addOppositionBasisController.importTMDetails(command, result, tradeMarkId, earlierEntitleMent, officeImport, languagesFilter)); 
    	
    	officeImport="";
    	Assert.assertNotNull(addOppositionBasisController.importTMDetails(command, result, tradeMarkId, earlierEntitleMent, officeImport, languagesFilter));
    	
    	Assert.assertNotNull(addOppositionBasisController.importTMDetails(command, result, null, earlierEntitleMent, officeImport, languagesFilter));

	}
    
    @Test
    public void importTMDetails2() {
    	
    	EarlierEntitlementRightDetails earlierEntitlementRightDetails=Mockito.mock(EarlierEntitlementRightDetails.class);
    	EarlierEntitlementRightInf earlierEntitlementRightInf=Mockito.mock(EarlierEntitlementRightInf.class); 
    	Mockito.when(earlierEntitlementRightInf.getEarlierEntitlementRightDetails()).thenReturn(earlierEntitlementRightDetails);
    	RelativeGrounds relativeGrounds=Mockito.mock(RelativeGrounds.class);
    	Mockito.when(relativeGrounds.getEarlierEntitlementRightTypeCode()).thenReturn("earlier");
    	Mockito.when(relativeGrounds.getEarlierEntitlementRightInf()).thenReturn(earlierEntitlementRightInf);
    	LegalActVersion legalActVersion=Mockito.mock(LegalActVersion.class);
    	Mockito.when(legalActVersion.getCode()).thenReturn("code");
    	OppositionBasisForm command=Mockito.mock(OppositionBasisForm.class);
    	Mockito.when(command.getLegalActVersion()).thenReturn(legalActVersion);
    	Mockito.when(command.getRelativeGrounds()).thenReturn(relativeGrounds);
    	
    	BindingResult result=Mockito.mock(BindingResult.class);
    	Mockito.when(result.hasErrors()).thenReturn(true);
		String tradeMarkId="tradeMarkId";
		String earlierEntitleMent="earlierEntitleMent";
		String officeImport="officeImport";
		String languagesFilter="en";
		ApplicantForm applicantForm=Mockito.mock(ApplicantForm.class);
		List<ApplicantForm> listaf=new ArrayList<ApplicantForm>();
		listaf.add(applicantForm);
		TMDetailsForm tm = Mockito.mock(TMDetailsForm.class);
		Mockito.when(tm.getApplicants()).thenReturn(listaf);
		Mockito.when(esTrademarkService.getTradeMark(Mockito.anyString(), Mockito.anyString())).thenReturn(tm);
		
		Assert.assertNotNull(addOppositionBasisController.importTMDetails(command, result, tradeMarkId, earlierEntitleMent, officeImport, languagesFilter)); 

	}
    

    
    
    @SuppressWarnings("unchecked")
	@Test
    public void importTMDetails3() {
    	
    	EarlierEntitlementRightDetails earlierEntitlementRightDetails=Mockito.mock(EarlierEntitlementRightDetails.class);
    	EarlierEntitlementRightInf earlierEntitlementRightInf=Mockito.mock(EarlierEntitlementRightInf.class); 
    	Mockito.when(earlierEntitlementRightInf.getEarlierEntitlementRightDetails()).thenReturn(earlierEntitlementRightDetails);
    	RelativeGrounds relativeGrounds=Mockito.mock(RelativeGrounds.class);
    	Mockito.when(relativeGrounds.getEarlierEntitlementRightTypeCode()).thenReturn("earlier");
    	Mockito.when(relativeGrounds.getEarlierEntitlementRightInf()).thenReturn(earlierEntitlementRightInf);
    	LegalActVersion legalActVersion=Mockito.mock(LegalActVersion.class);
    	Mockito.when(legalActVersion.getCode()).thenReturn("code");
    	OppositionBasisForm command=Mockito.mock(OppositionBasisForm.class);
    	Mockito.when(command.getLegalActVersion()).thenReturn(legalActVersion);
    	Mockito.when(command.getRelativeGrounds()).thenReturn(relativeGrounds);
    	
    	BindingResult result=Mockito.mock(BindingResult.class);
		String tradeMarkId="tradeMarkId";
		String earlierEntitleMent="earlierEntitleMent";
		String officeImport="officeImport";
		String languagesFilter="en";
		ApplicantForm applicantForm=Mockito.mock(ApplicantForm.class);
		List<ApplicantForm> listaf=new ArrayList<ApplicantForm>();
		listaf.add(applicantForm);
		TMDetailsForm tm = Mockito.mock(TMDetailsForm.class);
		Mockito.when(tm.getApplicants()).thenReturn(listaf);
		Mockito.when(esTrademarkService.getTradeMark(Mockito.anyString(), Mockito.anyString())).thenThrow(SPException.class);
		
		Assert.assertNotNull(addOppositionBasisController.importTMDetails(command, result, tradeMarkId, earlierEntitleMent, officeImport, languagesFilter));  

	}
	
	@Test
	public void onSubmitOppositionBasis(){
		
		TMDetailsForm  tMDetailsForm = Mockito.mock(TMDetailsForm.class);
		EarlierEntitlementRightDetails earlierEntitlementRightDetails=Mockito.mock(EarlierEntitlementRightDetails.class);
		Mockito.when(earlierEntitlementRightDetails.getEarlierTradeMarkDetails()).thenReturn(tMDetailsForm);
		GroundsRelativeForOpposition  groundsRelativeForOpposition = Mockito.mock(GroundsRelativeForOpposition.class);
    	EarlierEntitlementRightInf earlierEntitlementRightInf=Mockito.mock(EarlierEntitlementRightInf.class); 
    	Mockito.when(earlierEntitlementRightInf.getEarlierEntitlementRightDetails()).thenReturn(earlierEntitlementRightDetails);
    	Mockito.when(earlierEntitlementRightInf.getGroundsRelativeForOpposition()).thenReturn(groundsRelativeForOpposition);
    	RelativeGrounds relativeGrounds=Mockito.mock(RelativeGrounds.class);
    	Mockito.when(relativeGrounds.getEarlierEntitlementRightTypeCode()).thenReturn("earlier");
    	Mockito.when(relativeGrounds.getEarlierEntitlementRightInf()).thenReturn(earlierEntitlementRightInf);
    	LegalActVersion legalActVersion=Mockito.mock(LegalActVersion.class);
    	Mockito.when(legalActVersion.getCode()).thenReturn("code");
    	OppositionBasisForm command=Mockito.mock(OppositionBasisForm.class);
    	Mockito.when(command.getLegalActVersion()).thenReturn(legalActVersion);
    	Mockito.when(command.getRelativeGrounds()).thenReturn(relativeGrounds);
		
    	BindingResult  result=Mockito.mock(BindingResult.class);
    	Mockito.when(result.hasErrors()).thenReturn(true);
    	Mockito.when(result.getErrorCount()).thenReturn(1);
    	Mockito.when(result.getFieldError("formWarnings")).thenReturn(null);
    	Boolean ignoreMatches=false;
		String countries="ES;DE";
		
		Mockito.when(configurationService.getValue("oppositionBasics.maxNumber", "general")).thenReturn("3");
		Assert.assertNotNull(addOppositionBasisController.onSubmitOppositionBasis(command, result, ignoreMatches, countries));

		
	}
	
	@Test
	public void onSubmitLegalActVersion(){
		TMDetailsForm  tMDetailsForm = Mockito.mock(TMDetailsForm.class);
		EarlierEntitlementRightDetails earlierEntitlementRightDetails=Mockito.mock(EarlierEntitlementRightDetails.class);
		Mockito.when(earlierEntitlementRightDetails.getEarlierTradeMarkDetails()).thenReturn(tMDetailsForm);
		GroundsRelativeForOpposition  groundsRelativeForOpposition = Mockito.mock(GroundsRelativeForOpposition.class);
	    EarlierEntitlementRightInf earlierEntitlementRightInf=Mockito.mock(EarlierEntitlementRightInf.class); 
	    Mockito.when(earlierEntitlementRightInf.getEarlierEntitlementRightDetails()).thenReturn(earlierEntitlementRightDetails);
	    Mockito.when(earlierEntitlementRightInf.getGroundsRelativeForOpposition()).thenReturn(groundsRelativeForOpposition);
	    RelativeGrounds relativeGrounds=Mockito.mock(RelativeGrounds.class);
	    Mockito.when(relativeGrounds.getEarlierEntitlementRightTypeCode()).thenReturn("earlier");
	    Mockito.when(relativeGrounds.getEarlierEntitlementRightInf()).thenReturn(earlierEntitlementRightInf);
	    LegalActVersion legalActVersion=Mockito.mock(LegalActVersion.class);
	    Mockito.when(legalActVersion.getCode()).thenReturn("code");
	    OppositionBasisForm command=Mockito.mock(OppositionBasisForm.class);
	    Mockito.when(command.getLegalActVersion()).thenReturn(legalActVersion);
	    Mockito.when(command.getRelativeGrounds()).thenReturn(relativeGrounds);
		
	    BindingResult  result=Mockito.mock(BindingResult.class);
	    Mockito.when(result.hasErrors()).thenReturn(true);
	    Mockito.when(result.getErrorCount()).thenReturn(1);
	    Mockito.when(result.getFieldError("formWarnings")).thenReturn(null);
	    
	    Boolean ignoreMatches=false;
		 
	    Assert.assertNotNull(addOppositionBasisController.onSubmitLegalActVersion(command, result, ignoreMatches));
	}
	
	@Test
	public void addCountry() {
		String country="DE";
		Assert.assertNotNull(addOppositionBasisController.addCountry(country));	
	}
	
	@Test
	public void removeCountry1() {
		String inputCountry="DE";
		String country="ES";
		Mockito.when(flowBean.getReputationCountries()).thenReturn(null);
		Assert.assertNotNull(addOppositionBasisController.removeCountry(inputCountry, country));	
   	}
	
	@Test
	public void removeCountry() {
		String inputCountry="DE";
		String country="ES";
		List <String> listCountries = new ArrayList<String>();
		listCountries.add("DE");
		Mockito.when(flowBean.getReputationCountries()).thenReturn(listCountries);
		Assert.assertNotNull(addOppositionBasisController.removeCountry(inputCountry, country));	
   	}
	
	@Test
	public void countries() {

		List <String> listCountries = new ArrayList<String>();
		listCountries.add("DE");
		Mockito.when(flowBean.getReputationCountries()).thenReturn(listCountries);
		Assert.assertNotNull(addOppositionBasisController.countries());	
   	}
	
	@Test
	public void getMaxNumberEarlierRight()
    {
		Assert.assertNotNull(addOppositionBasisController.getMaxNumberEarlierRight());	
    }
	
	@Test(expected=SPException.class)
	public void onSubmitE1(){
		OppositionBasisForm command=null;
		FlowBean flowBean=this.flowBean; 
		AddParameters addParameters=Mockito.mock(AddParameters.class);
        BindingResult result=Mockito.mock(BindingResult.class);
		
		addOppositionBasisController.onSubmit(command, flowBean, addParameters, result);
	}
	
	@Test(expected=SPException.class)
	public void onSubmitE2(){
		OppositionBasisForm command=Mockito.mock(OppositionBasisForm.class);
		FlowBean flowBean=null; 
		AddParameters addParameters=Mockito.mock(AddParameters.class);
        BindingResult result=Mockito.mock(BindingResult.class);
		
		addOppositionBasisController.onSubmit(command, flowBean, addParameters, result);
	}
	
	@Test(expected=SPException.class)
	public void onSubmitE3(){
		OppositionBasisForm command=Mockito.mock(OppositionBasisForm.class);
		FlowBean flowBean=this.flowBean; 
		AddParameters addParameters=null;
        BindingResult result=Mockito.mock(BindingResult.class);
		
		addOppositionBasisController.onSubmit(command, flowBean, addParameters, result);
	}
	
	@Test(expected=SPException.class)
	public void onSubmitE4(){
		OppositionBasisForm command=Mockito.mock(OppositionBasisForm.class);
		FlowBean flowBean=this.flowBean; 
		AddParameters addParameters=Mockito.mock(AddParameters.class);
        BindingResult result=null;
		
		addOppositionBasisController.onSubmit(command, flowBean, addParameters, result);
	}
	
	@Test
	public void onSubmit1(){
		TMDetailsForm  tMDetailsForm = Mockito.mock(TMDetailsForm.class);
		EarlierEntitlementRightDetails earlierEntitlementRightDetails=Mockito.mock(EarlierEntitlementRightDetails.class);
		Mockito.when(earlierEntitlementRightDetails.getEarlierTradeMarkDetails()).thenReturn(tMDetailsForm);
		GroundsRelativeForOpposition  groundsRelativeForOpposition = Mockito.mock(GroundsRelativeForOpposition.class);
	    EarlierEntitlementRightInf earlierEntitlementRightInf=Mockito.mock(EarlierEntitlementRightInf.class); 
	    Mockito.when(earlierEntitlementRightInf.getEarlierEntitlementRightDetails()).thenReturn(earlierEntitlementRightDetails);
	    Mockito.when(earlierEntitlementRightInf.getGroundsRelativeForOpposition()).thenReturn(groundsRelativeForOpposition);
	    RelativeGrounds relativeGrounds=Mockito.mock(RelativeGrounds.class);
	    Mockito.when(relativeGrounds.getEarlierEntitlementRightTypeCode()).thenReturn("earlier");
	    Mockito.when(relativeGrounds.getEarlierEntitlementRightInf()).thenReturn(earlierEntitlementRightInf);
	    LegalActVersion legalActVersion=Mockito.mock(LegalActVersion.class);
	    Mockito.when(legalActVersion.getCode()).thenReturn("code");
	    OppositionBasisForm command=Mockito.mock(OppositionBasisForm.class);
	    Mockito.when(command.getLegalActVersion()).thenReturn(legalActVersion);
	    Mockito.when(command.getRelativeGrounds()).thenReturn(relativeGrounds);
		
	    
	    AddParameters addParameters=Mockito.mock(AddParameters.class);
        BindingResult result=Mockito.mock(BindingResult.class);
		
        
		Assert.assertNotNull(addOppositionBasisController.onSubmit(command, flowBean, addParameters, result));
	}
	
	@Test
	public void onSubmit2(){
		TMDetailsForm  tMDetailsForm = Mockito.mock(TMDetailsForm.class);
		EarlierEntitlementRightDetails earlierEntitlementRightDetails=Mockito.mock(EarlierEntitlementRightDetails.class);
		Mockito.when(earlierEntitlementRightDetails.getEarlierTradeMarkDetails()).thenReturn(tMDetailsForm);
		GroundsRelativeForOpposition  groundsRelativeForOpposition = Mockito.mock(GroundsRelativeForOpposition.class);
	    EarlierEntitlementRightInf earlierEntitlementRightInf=Mockito.mock(EarlierEntitlementRightInf.class); 
	    Mockito.when(earlierEntitlementRightInf.getEarlierEntitlementRightDetails()).thenReturn(earlierEntitlementRightDetails);
	    Mockito.when(earlierEntitlementRightInf.getGroundsRelativeForOpposition()).thenReturn(groundsRelativeForOpposition);
	    RelativeGrounds relativeGrounds=Mockito.mock(RelativeGrounds.class);
	    Mockito.when(relativeGrounds.getEarlierEntitlementRightTypeCode()).thenReturn("earlier");
	    Mockito.when(relativeGrounds.getEarlierEntitlementRightInf()).thenReturn(earlierEntitlementRightInf);
	    LegalActVersion legalActVersion=Mockito.mock(LegalActVersion.class);
	    Mockito.when(legalActVersion.getCode()).thenReturn("code");
	    OppositionBasisForm command=Mockito.mock(OppositionBasisForm.class);
	    Mockito.when(command.getLegalActVersion()).thenReturn(legalActVersion);
	    Mockito.when(command.getRelativeGrounds()).thenReturn(relativeGrounds);
		
	    
	    AddParameters addParameters=Mockito.mock(AddParameters.class);
        BindingResult result=Mockito.mock(BindingResult.class);
		
        Mockito.when(flowBean.existsObject(Mockito.any(Class.class), Mockito.anyString())).thenReturn(true);
        
        Assert.assertNotNull(addOppositionBasisController.onSubmit(command, flowBean, addParameters, result));
	}
	
	@Test(expected=SPException.class)
	public void onSubmit3(){
		TMDetailsForm  tMDetailsForm = Mockito.mock(TMDetailsForm.class);
		EarlierEntitlementRightDetails earlierEntitlementRightDetails=Mockito.mock(EarlierEntitlementRightDetails.class);
		Mockito.when(earlierEntitlementRightDetails.getEarlierTradeMarkDetails()).thenReturn(tMDetailsForm);
		GroundsRelativeForOpposition  groundsRelativeForOpposition = Mockito.mock(GroundsRelativeForOpposition.class);
	    EarlierEntitlementRightInf earlierEntitlementRightInf=Mockito.mock(EarlierEntitlementRightInf.class); 
	    Mockito.when(earlierEntitlementRightInf.getEarlierEntitlementRightDetails()).thenReturn(earlierEntitlementRightDetails);
	    Mockito.when(earlierEntitlementRightInf.getGroundsRelativeForOpposition()).thenReturn(groundsRelativeForOpposition);
	    RelativeGrounds relativeGrounds=Mockito.mock(RelativeGrounds.class);
	    Mockito.when(relativeGrounds.getEarlierEntitlementRightTypeCode()).thenReturn("earlier");
	    Mockito.when(relativeGrounds.getEarlierEntitlementRightInf()).thenReturn(earlierEntitlementRightInf);
	    LegalActVersion legalActVersion=Mockito.mock(LegalActVersion.class);
	    Mockito.when(legalActVersion.getCode()).thenReturn("code");
	    OppositionBasisForm command=Mockito.mock(OppositionBasisForm.class);
	    Mockito.when(command.getLegalActVersion()).thenReturn(legalActVersion);
	    Mockito.when(command.getRelativeGrounds()).thenReturn(relativeGrounds);
		
	    
	    AddParameters addParameters=Mockito.mock(AddParameters.class);
        BindingResult result=Mockito.mock(BindingResult.class);
		
        Mockito.when(flowBean.existsObject(Mockito.any(Class.class), Mockito.anyString())).thenThrow(IllegalArgumentException.class);
        
		addOppositionBasisController.onSubmit(command, flowBean, addParameters, result);
	}
	
	@Test
	public void onSubmitOppositionBasis2(){
		
		
		OppositionBasisForm command=new OppositionBasisForm();
		 BindingResult  result=Mockito.mock(BindingResult.class);
		 Boolean ignoreMatches=false;
		 String countries="";
		 
		 
		 //FlowBean  flowBean=Mockito.mock(FlowBean.class);
		 
				 
		 //flowBean.addObject(Mockito.mock(AbstractForm.class));
		 
		 //Mockito.when(flowBean.equals(null)).thenReturn(value);
		 
		 AddParameters addParameters;//=new AddParameters();
		 
		 
		 FormValidator formValidator=Mockito.mock(FormValidator.class);
		 
	//	 Mockito.when(formValidator.equals(null)).
		 
		
	//	 Mockito.when(addOppositionBasisController.addOppositionBasisCheckMatches(command, result, addParameters, ignoreMatches))
		
		// Mockito.when(validator.equals(null)).thenReturn(true);
		 
		 command.setAbsoluteGrounds(new AbsoluteGrounds());
		 Mockito.when(configurationService.getValue("oppositionBasics.maxNumber", "general")).thenReturn("3");
		 
		
		addOppositionBasisController.onSubmitOppositionBasis(command, result, ignoreMatches, countries);
		
		command.setAbsoluteGrounds(null);
		
		command.setRevocationGrounds(new RevocationGrounds());
		addOppositionBasisController.onSubmitOppositionBasis(command, result, ignoreMatches, countries);
		
		
		
		command.setRelativeGrounds(new RelativeGrounds());
		
		addOppositionBasisController.onSubmitOppositionBasis(command, result, ignoreMatches, countries);
		
		
		
		//addOppositionBasisController.onSubmitOppositionBasis(command, result, ignoreMatches, countries);
		
	}
}
