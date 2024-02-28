package eu.ohim.sp.eservices.ui.controllerTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.common.ui.form.design.LocarnoClass;
import eu.ohim.sp.common.ui.form.design.LocarnoClassification;
import eu.ohim.sp.common.ui.form.design.LocarnoComplexForm;
import eu.ohim.sp.common.ui.form.design.LocarnoForm;
import eu.ohim.sp.common.ui.form.design.LocarnoSearchForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.eservices.ui.controller.AddLocarnoController;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.interfaces.DSLocarnoServiceInterface;


public class AddLocarnoControllerTest {

	@Mock
	private DSLocarnoServiceInterface dsLocarnoService;
	
	@Mock
	private ESFlowBean esFlowBean;
    
	@Mock
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;
	
	@Mock
    private FormValidator validator;
	
	@Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }
	
	@InjectMocks
	AddLocarnoController addLocarnoController=new AddLocarnoController();
//	@Test(expected=SPException.class)
	
	
    @Test
    public void formAddClass() {
    	Assert.assertNotNull(addLocarnoController.formAddClass());
    }
    
    @Test
    public void onSubmitAddClass() {
    	LocarnoClassification locarnoClassification=Mockito.mock(LocarnoClassification.class);
    	Mockito.when(locarnoClassification.getId()).thenReturn("ID");
    	LocarnoComplexForm locarnoForm=Mockito.mock(LocarnoComplexForm.class);
//    	Mockito.when(locarnoForm.getLocarnoClassification()).thenReturn(locarnoClassification);
    	List<LocarnoComplexForm> listlocarnos=new ArrayList<LocarnoComplexForm>();
    	listlocarnos.add(locarnoForm);
    	LocarnoSearchForm locarnoSearchForm = Mockito.mock(LocarnoSearchForm.class);
    	Mockito.when(locarnoSearchForm.getLocarnoClassificationsSelected()).thenReturn(listlocarnos);
    	BindingResult result = Mockito.mock(BindingResult.class);

    	LocarnoAbstractForm locarnoAbstractForm=Mockito.mock(LocarnoAbstractForm.class);
    	Mockito.when(locarnoAbstractForm.getLocarnoId()).thenReturn("ID");
    	List<LocarnoAbstractForm> listlocarnoAbstractForm=new ArrayList<LocarnoAbstractForm>();
    	listlocarnoAbstractForm.add(locarnoAbstractForm);
    	Mockito.when(esFlowBean.getLocarno()).thenReturn(listlocarnoAbstractForm);

    	Assert.assertNotNull(addLocarnoController.onSubmitAddClass(locarnoSearchForm, result));

    }
    
    @Test
    public void onSubmitAddClass2() {
    	LocarnoClassification locarnoClassification=Mockito.mock(LocarnoClassification.class);
    	Mockito.when(locarnoClassification.getId()).thenReturn("ID");
		LocarnoComplexForm locarnoForm=Mockito.mock(LocarnoComplexForm.class);
//    	Mockito.when(locarnoForm.getLocarnoClassification()).thenReturn(locarnoClassification);
		List<LocarnoComplexForm> listlocarnos=new ArrayList<LocarnoComplexForm>();
    	listlocarnos.add(locarnoForm);
    	LocarnoSearchForm locarnoSearchForm = Mockito.mock(LocarnoSearchForm.class);
    	Mockito.when(locarnoSearchForm.getLocarnoClassificationsSelected()).thenReturn(listlocarnos);
    	BindingResult result = Mockito.mock(BindingResult.class);

    	LocarnoAbstractForm locarnoAbstractForm=Mockito.mock(LocarnoAbstractForm.class);
    	Mockito.when(locarnoAbstractForm.getLocarnoId()).thenReturn("ID2");
    	List<LocarnoAbstractForm> listlocarnoAbstractForm=new ArrayList<LocarnoAbstractForm>();
    	listlocarnoAbstractForm.add(locarnoAbstractForm);
    	Mockito.when(esFlowBean.getLocarno()).thenReturn(listlocarnoAbstractForm);

    	Assert.assertNotNull(addLocarnoController.onSubmitAddClass(locarnoSearchForm, result));

    }
    
    @Test
    public void formAddNewProduct() {
    	String id="";
    	Assert.assertNotNull(addLocarnoController.formAddNewProduct(id));
    }
    
    @Test
    public void onSubmitAddNewProduct() {
    	LocarnoClassification locarnoClassification=Mockito.mock(LocarnoClassification.class);
    	Mockito.when(locarnoClassification.getId()).thenReturn("ID");
		LocarnoComplexForm locarnoForm=Mockito.mock(LocarnoComplexForm.class);
//    	Mockito.when(locarnoForm.getLocarnoClassification()).thenReturn(locarnoClassification);
		List<LocarnoComplexForm> listlocarnos=new ArrayList<LocarnoComplexForm>();
    	listlocarnos.add(locarnoForm);
    	LocarnoSearchForm locarnoSearchForm = Mockito.mock(LocarnoSearchForm.class);
    	Mockito.when(locarnoSearchForm.getLocarnoClassificationsSelected()).thenReturn(listlocarnos);
    	BindingResult result = Mockito.mock(BindingResult.class);
    	String id="ID";
    	
    	Assert.assertNotNull(addLocarnoController.onSubmitAddNewComplexProduct(locarnoForm, result, id));
    }
    
    @Test
    public void formAddNewComplexProduct() {
    	String id="";
    	Assert.assertNotNull(addLocarnoController.formAddNewComplexProduct(id));
    }
    
//    @Test
//    public void onSubmitAddNewComplexProduct() {
//    	LocarnoComplexForm locarnoComplexForm = Mockito.mock(LocarnoComplexForm.class);
//    	BindingResult result = Mockito.mock(BindingResult.class);
//    	String id="ID";
//    	
//    	addLocarnoController.onSubmitAddNewComplexProduct(locarnoComplexForm, result, id);
//    }
    
    @Test
    public void getNewProduct() {
    	String id="";
    	Assert.assertNull(addLocarnoController.getNewProduct(id));
    }
    
    @Test
    public void getNewProduct2() {
    	String id="ID";
    	LocarnoAbstractForm locarnoAbstractForm = Mockito.mock(LocarnoAbstractForm.class);
    	Mockito.when(esFlowBean.getObject(LocarnoAbstractForm.class,id)).thenReturn(locarnoAbstractForm);
    	
    	Assert.assertNotNull(addLocarnoController.getNewProduct(id));
    }
    
    @Test
    public void getNewProduct3() {
    	String id="ID";
    	LocarnoClass locarnoClass=Mockito.mock(LocarnoClass.class);
    	Mockito.when(locarnoClass.getClazz()).thenReturn("clazz");
    	LocarnoClassification locarnoClassification=Mockito.mock(LocarnoClassification.class);
    	Mockito.when(locarnoClassification.getLocarnoClass()).thenReturn(locarnoClass);
    	LocarnoForm locarnoAbstractForm = Mockito.mock(LocarnoForm.class);
    	Mockito.when(locarnoAbstractForm.getLocarnoClassification()).thenReturn(locarnoClassification);
    	Mockito.when(esFlowBean.getObject(LocarnoAbstractForm.class,id)).thenReturn(locarnoAbstractForm);
    	
    	Assert.assertNotNull(addLocarnoController.getNewProduct(id));
    }
    
    @Test
    public void initBinder(){
    	WebDataBinder binder=Mockito.mock(WebDataBinder.class);
    	addLocarnoController.initBinder(binder);
    }
    
//    @Test
//    public void getLocarnoClassifications() {
//    	LocarnoClassification locarnoClassification=Mockito.mock(LocarnoClassification.class);
//    	Mockito.when(locarnoClassification.getId()).thenReturn("ID");
//    	LocarnoForm locarnoForm=Mockito.mock(LocarnoForm.class);
//    	Mockito.when(locarnoForm.getLocarnoClassification()).thenReturn(locarnoClassification);
//    	List<LocarnoForm> listlocarnos=new ArrayList<LocarnoForm>();
//    	listlocarnos.add(locarnoForm);
//    	LocarnoSearchForm locarnoSearchForm = Mockito.mock(LocarnoSearchForm.class);
//    	Mockito.when(locarnoSearchForm.getLocarnoClassificationsSelected()).thenReturn(listlocarnos);
//    	Mockito.when(locarnoSearchForm.getAvailableSectionName()).thenReturn(AvailableSection.APPLICANT_LEGALENTITY);
//    	Mockito.when(locarnoSearchForm.getFormMessages()).thenReturn("fmsgs");
//    	Mockito.when(locarnoSearchForm.getId()).thenReturn("ID");
//    	Mockito.when(locarnoSearchForm.getSearchData()).thenReturn(Mockito.mock(LocarnoClassification.class));
//    	BindingResult result = Mockito.mock(BindingResult.class);
//    	Mockito.when(result.hasErrors()).thenReturn(true);
//
//    	
//    	Assert.assertNotNull(addLocarnoController.getLocarnoClassifications(locarnoSearchForm, result));
//    
//    }
 

}
