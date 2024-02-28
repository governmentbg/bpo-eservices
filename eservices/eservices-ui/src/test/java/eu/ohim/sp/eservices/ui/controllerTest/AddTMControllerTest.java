package eu.ohim.sp.eservices.ui.controllerTest;

import java.beans.PropertyEditor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.ohim.sp.eservices.ui.util.GroundsUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TermForm;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.eservices.ui.controller.AddTMController;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.ESTrademarkService;

public class AddTMControllerTest {
	
	  @Mock
	    private ESFlowBean flowBean;
	    
	  @Mock
	    private ESTrademarkService esTrademarkService;
	    
	  @Mock
	    private FlowScopeDetails flowScopeDetails;
	    
	  @Mock
		private ConfigurationServiceDelegatorInterface configurationService;
	  
	  
	  @Mock
	    private FormValidator validator;

	@Mock
	private GroundsUtil groundsUtil;

	  
	  
	  @InjectMocks
	  AddTMController addTMController = new AddTMController();

		@Before
		public void setUp() {
			MockitoAnnotations.initMocks(this);
		}
	  
		
		
		@Test  
		public void	autocompleteService(){
			
			
			addTMController.autocomplete("id", "office", true);
			
		}
		
	
	
	@Test  
	public void	formBackingTMDetails(){
		
		String id="-";
	
		  TMDetailsForm command =new TMDetailsForm();//= flowBean.getObject(TMDetailsForm.class, id);
	        
		  GoodAndServiceForm gs=new GoodAndServiceForm();
		  
		  HashSet<GoodAndServiceForm> cv=  new HashSet<GoodAndServiceForm>();
		  
		  
		  GoodAndServiceForm goodAndServiceForm=new GoodAndServiceForm();
		  
		  TermForm termForm=new TermForm();
		  
		  termForm.setDescription("description");
		  
		  termForm.setIdClass("2");
		  
		  termForm.setImportedNiceClassHeading(true);
		  
		  termForm.setScopeAvailabilty(true);
		  
		  goodAndServiceForm.addTermForm(termForm);
		  
		  cv.add(goodAndServiceForm);
		  
		 
		  
		  
		  command.setGoodsAndServices(cv);
		  

		  Mockito.when(flowBean.getObject(TMDetailsForm.class, id)).thenReturn(command);
		  
		//  Mockito.when(command.getGoodsAndServices()).thenReturn(cv);
		  
		//  addTMController
		
	//	addTMController.formBackingTMDetails(id);
		
		
	}
	  
	@Test  
	public void	onSubmitTMDetails(){
		
		
		TMDetailsForm command=new TMDetailsForm();
		BindingResult result=Mockito.mock(BindingResult.class);
		Boolean ignoreMatches=true;
		
		
	
		//AddParamters
		
		//configurationServiceDelegator.getValue(setting, component)
		
		Mockito.when(configurationService.getValue("tm.add.maxNumber", "general")).thenReturn("3");
		
		//Mockito.when(addTMController.getMaxNumber()).thenReturn(3);
		
		
		
		Mockito.when(flowScopeDetails.getFlowModeId()).thenReturn("tm-opposition");
		
		addTMController.onSubmitTMDetails(command,result,ignoreMatches);
		
		
	}
	
	
	@Test  
	public void	importTMDetails(){
		
		String tradeMarkId="";
    	 TMDetailsForm tmDetailsForm=new TMDetailsForm();
    	BindingResult result=Mockito.mock(BindingResult.class);
    	String languagesFilter="en";
    	String	setting="1";
    	
    	String	component="kjnjk";
    	
    	Mockito.when(esTrademarkService.getTradeMark(tradeMarkId)).thenReturn(tmDetailsForm);
    	
    //	configurationService.getValue(setting, component);
    	
    	//Mockito.when(configurationService.getValue(setting, component)).thenReturn("3");
    	
    	
//    	Mockito.when(new AddParameters(TMDetailsForm.class, "tmDetailsForm",
//                "tm_details/tm_card_list", "tm_details/tm_details_section", "3")).thenReturn(tmDetailsForm);
//    	
    	
    	Mockito.when(configurationService.getValue("tm.add.maxNumber", "general")).thenReturn("3");
    	
    	Mockito.when(flowScopeDetails.getFlowModeId()).thenReturn("tm-opposition");
    	
    	addTMController.importTMDetails(tradeMarkId, languagesFilter, "BG", false, tmDetailsForm, result);
    	tradeMarkId=null;
    	addTMController.importTMDetails(tradeMarkId, languagesFilter, "BG", false, tmDetailsForm, result);
	}
	  
	
	
	@Test  
	public void	renewableTM(){
		
		
		TMDetailsForm command =new TMDetailsForm();
		BindingResult result=Mockito.mock(BindingResult.class);
		Boolean ignoreMatches=true;
		
		Mockito.when(configurationService.getValue("tm.add.maxNumber", "general")).thenReturn("3");
    	
    	Mockito.when(flowScopeDetails.getFlowModeId()).thenReturn("tm-opposition");
		
		
		addTMController.renewableTM(command, result, ignoreMatches);
		
	}
	  
	 
	@Test  
	public void	onSubmit(){
		
		
		AbstractImportableForm command=Mockito.mock(AbstractImportableForm.class);
		
		
		FlowBean flowBean=Mockito.mock(FlowBean.class);
		
		AddParameters addParameters=new AddParameters(null, null, null, null, null);
        BindingResult result=Mockito.mock(BindingResult.class);
        
        Mockito.when(result.hasErrors()).thenReturn(true);

        
		
       
		
        Mockito.when(configurationService.getValue("tm.add.maxNumber", "general")).thenReturn("3");
    	
    	Mockito.when(flowScopeDetails.getFlowModeId()).thenReturn("tm-opposition");
		
    	AbstractImportableForm abstractImportableForm;
    	
    	BindingResult bindingResult;
    	
    	
        
        
    //validateCommand(abstractImportableForm, result, addParameters)).
		
		addTMController.onSubmit(command,flowBean,addParameters,result);
		
	}
	  
	
	 @Test
	  public void formBackingTMDetails2() throws CloneNotSupportedException {
		  String id="ID";
		  GoodAndServiceForm goodAndServiceForm=Mockito.mock(GoodAndServiceForm.class);
		  Mockito.when(goodAndServiceForm.clone()).thenReturn(Mockito.mock(GoodAndServiceForm.class));
		  Set<GoodAndServiceForm> setgoodsfs =new HashSet<GoodAndServiceForm>();
		  setgoodsfs.add(goodAndServiceForm);
		  TMDetailsForm command = Mockito.mock(TMDetailsForm.class);
		  Mockito.when(command.getGoodsAndServices()).thenReturn(setgoodsfs);
		  Mockito.when(flowBean.getObject(TMDetailsForm.class, id)).thenReturn(command);
		  Assert.assertNotNull(addTMController.formBackingTMDetails(id));
	  }
		
	  @SuppressWarnings("unchecked")
	  @Test(expected=SPException.class)
	  public void formBackingTMDetails3() throws CloneNotSupportedException{
		  String id="ID";
		  GoodAndServiceForm goodAndServiceForm=Mockito.mock(GoodAndServiceForm.class);
		  Mockito.when(goodAndServiceForm.clone()).thenThrow(CloneNotSupportedException.class);
		  Set<GoodAndServiceForm> setgoodsfs =new HashSet<GoodAndServiceForm>();
		  setgoodsfs.add(goodAndServiceForm);
		  TMDetailsForm command = Mockito.mock(TMDetailsForm.class);
		  Mockito.when(command.getGoodsAndServices()).thenReturn(setgoodsfs);
		  Mockito.when(flowBean.getObject(TMDetailsForm.class, id)).thenReturn(command);
		  addTMController.formBackingTMDetails(id);
	  }
	  
	  @Test
	  public void onSubmitTMDetails2() {
		  FileWrapper fileWrapper=Mockito.mock(FileWrapper.class);
		  Mockito.when(fileWrapper.getStoredFiles()).thenReturn(new ArrayList<StoredFile>());
		  TMDetailsForm command=Mockito.mock(TMDetailsForm.class);
		  Mockito.when(command.getImported()).thenReturn(true);
		  Mockito.when(command.getImageRepresentationURI()).thenReturn("uri");
		  Mockito.when(command.getRepresentationAttachment()).thenReturn(fileWrapper);
		  List<ApplicantForm> listapps=new ArrayList<ApplicantForm>();
		  listapps.add(Mockito.mock(ApplicantForm.class));
		  Mockito.when(flowBean.getOwners()).thenReturn(listapps);
		  BindingResult result=Mockito.mock(BindingResult.class);
		  Boolean ignoreMatches=false;
		  Mockito.when(flowScopeDetails.getFlowModeId()).thenReturn("tm-opposition");
		  List<OppositionBasisForm> listopos=new ArrayList<OppositionBasisForm>();
		  listopos.add(Mockito.mock(OppositionBasisForm.class));
		  Mockito.when(groundsUtil.refreshOppositionBasisList(flowBean)).thenReturn(listopos);
		  
		  Assert.assertNotNull(addTMController.onSubmitTMDetails(command, result, ignoreMatches));
	  }
	  
	  @Test
	  public void formBackiningOwners() {
 			String name="";
 			String domicile="";
 			Assert.assertNotNull(addTMController.formBackiningOwners(name, domicile));
	  }
	  
	  @Test
	  public void formBackiningOwners2() {
 			String name="name";
 			String domicile="domicile";
 			Assert.assertNotNull(addTMController.formBackiningOwners(name, domicile));
	  }
	  
	  @Test
	  public void cleanOwners(){
		  Assert.assertNotNull(addTMController.cleanOwners());
	  }
	  
	  @Test
	  public void removeOwner() {
 			String name="name";
 			String domicile="domicile";
 			Mockito.when(flowBean.getApplicant(name, domicile)).thenReturn(Mockito.mock(ApplicantForm.class));
 			Assert.assertNotNull(addTMController.removeOwner(name, domicile));
	  }
	  
	  @Test(expected=SPException.class)
	  public void onSubmitE1() {
		  AddParameters addParameters=Mockito.mock(AddParameters.class);
         BindingResult result=Mockito.mock(BindingResult.class);
         addTMController.onSubmit(null, flowBean, addParameters, result);
	  }
	  
	  @Test(expected=SPException.class)
	  public void onSubmitE2() {
		  AbstractImportableForm command=Mockito.mock(AbstractImportableForm.class); 
		  AddParameters addParameters=Mockito.mock(AddParameters.class);
         BindingResult result=Mockito.mock(BindingResult.class);
         addTMController.onSubmit(command, null, addParameters, result);
	  }
	  
	  @Test(expected=SPException.class)
	  public void onSubmitE3() {
		  AbstractImportableForm command=Mockito.mock(AbstractImportableForm.class); 
		  BindingResult result=Mockito.mock(BindingResult.class);
         addTMController.onSubmit(command, flowBean, null, result);
	  }
	  
	  @Test(expected=SPException.class)
	  public void onSubmitE4() {
		  AbstractImportableForm command=Mockito.mock(AbstractImportableForm.class); 
		  AddParameters addParameters=Mockito.mock(AddParameters.class);
         addTMController.onSubmit(command, flowBean, addParameters, null);
	  }
	  
	  
	  @SuppressWarnings("unchecked")
	  @Test
	  public void importTMDetails2() {
		  String tradeMarkId="ID"; 
		  String languagesFilter="en";
		  TMDetailsForm tmDetailsForm=Mockito.mock(TMDetailsForm.class); 
		  BindingResult result=Mockito.mock(BindingResult.class); 
		  Mockito.when(esTrademarkService.getTradeMark(tradeMarkId)).thenThrow(SPException.class);
		  Assert.assertNotNull(addTMController.importTMDetails(tradeMarkId, languagesFilter,"BG", false, tmDetailsForm, result));
	  }
	  

}
