package eu.ohim.sp.eservices.ui.controllerTest;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsListForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.design.ESDesignApplicationDataForm;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.eservices.ui.controller.AddDSController;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.ESDesignService;


public class AddDSControllerTest {
    
	@Mock
    private ESFlowBean flowBean;
    
	@Mock
    private ESDesignService esDesignService;
    
	@Mock
    private FlowScopeDetails flowScopeDetails;
    
	@Mock
	private ConfigurationServiceDelegatorInterface configurationService;  
	
	@Mock
    private FormValidator validator;
	
	@Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }
	
	@InjectMocks
	AddDSController addDSController=new AddDSController();
	//@Test(expected=SPException.class)
	
    @Test
    public void formBackingDSDetails() {
    	String id="ID";
    	Assert.assertNotNull(addDSController.formBackingDSDetails(id));
    }
    
    @Test
    public void onSubmitDSDetails()
	{
    	ESDesignDetailsForm command=Mockito.mock(ESDesignDetailsForm.class);
    	BindingResult result=Mockito.mock(BindingResult.class);
        Boolean ignoreMatches=false;
        Assert.assertNotNull(addDSController.onSubmitDSDetails(command, result, ignoreMatches));
	}
    
    @Test
    public void onSubmitDSDetails2(){
    	
    	ESDesignDetailsForm command=Mockito.mock(ESDesignDetailsForm.class);
    	BindingResult result=Mockito.mock(BindingResult.class);
        Boolean ignoreMatches=false;
    	List<ApplicantForm> laf = new ArrayList<ApplicantForm>();
        ApplicantForm af = Mockito.mock(ApplicantForm.class);
        laf.add(af);
        Mockito.when(command.getImported()).thenReturn(false);
        Mockito.when(flowBean.getOwners()).thenReturn(laf);
        Mockito.when(command.geteSDesignApplicationData()).thenReturn(Mockito.mock(ESDesignApplicationDataForm.class));       
        
        Assert.assertNotNull(addDSController.onSubmitDSDetails(command, result, ignoreMatches));
    }
    

//    @Test(expected=SPException.class)
//    public void onSubmitDSDetailsNoCommand()
//	{
//    	BindingResult result=Mockito.mock(BindingResult.class);
//        Boolean ignoreMatches=false;
//        addDSController.onSubmitDSDetails(null, result, ignoreMatches);
//	}
   
    @Test(expected=SPException.class)
    public void onSubmitDSDetailsNoResult()
	{
    	ESDesignDetailsForm command=Mockito.mock(ESDesignDetailsForm.class);
    	Boolean ignoreMatches=false;
        addDSController.onSubmitDSDetails(command, null, ignoreMatches);
	}
    
    @Test
    public void importDSDetails() {
    	String designId=null;
    	ESDesignDetailsForm dsDetailsForm=Mockito.mock(ESDesignDetailsForm.class);
    	BindingResult result=Mockito.mock(BindingResult.class);
    	Assert.assertNotNull(addDSController.importDSDetails(designId, false, dsDetailsForm, result));
	} 
    
    @Test
    public void importDSDetails2() {
    	String designId="ID";
		String idreserventity="IDRESER";
    	ESDesignDetailsForm dsDetailsForm=Mockito.mock(ESDesignDetailsForm.class);
    	BindingResult result=Mockito.mock(BindingResult.class);
        ESDesignApplicationDataForm dataForm=Mockito.mock(ESDesignApplicationDataForm.class);
        Mockito.when(dsDetailsForm.geteSDesignApplicationData()).thenReturn(dataForm);
        Mockito.when(flowBean.getIdreserventity()).thenReturn(idreserventity);
    	Mockito.when(esDesignService.getDesignApplication(designId)).thenReturn(new ESDesignDetailsListForm(Lists.newArrayList(dsDetailsForm)));
    	Assert.assertNotNull(addDSController.importDSDetails(designId, false, dsDetailsForm, result));
	}
    
    @SuppressWarnings("unchecked")
    @Test
    public void importDSDetails3() {
    	String designId="ID";
		String idreserventity="IDRESER";
    	ESDesignDetailsForm dsDetailsForm=Mockito.mock(ESDesignDetailsForm.class);
    	BindingResult result=Mockito.mock(BindingResult.class);
		Mockito.when(flowBean.getIdreserventity()).thenReturn(idreserventity);
    	Mockito.when(esDesignService.getDesignApplication(designId)).thenThrow(SPException.class);
    	Assert.assertNotNull(addDSController.importDSDetails(designId, false, dsDetailsForm, result));
	}
    
    @Test
    public void formBackiningOwners() { 	
    	String name="";
		String domicile="";
		Assert.assertNotNull(addDSController.formBackiningOwners(name, domicile));
    }
    
    @Test
    public void formBackiningOwners2() { 	
    	String name="name";
		String domicile="domicile";
		Assert.assertNotNull(addDSController.formBackiningOwners(name, domicile));
    }
    
    @Test
    public void removeHolder() {
    	String name="name";
		String domicile="domicile";
		ApplicantForm holderForm = Mockito.mock(ApplicantForm.class);
		Mockito.when(flowBean.getApplicant(name, domicile)).thenReturn(holderForm);
		Assert.assertNotNull(addDSController.removeHolder(name, domicile));
    }
    
    @Test
    public void getDS() {
    	String id="id";
    	Assert.assertNotNull(addDSController.getDS(id));
    }
    
    @Test
    public void cleanOwners()
    {
    	Assert.assertNotNull(addDSController.cleanOwners());
    }
    
}
