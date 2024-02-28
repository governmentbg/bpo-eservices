package eu.ohim.sp.eservices.ui.controllerTest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.classification.AddedTermForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TaxonomyConceptNodeTreeView;
import eu.ohim.sp.common.ui.form.classification.TermForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyConceptNode;
import eu.ohim.sp.eservices.ui.controller.GSController;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.interfaces.GoodsServicesServiceInterface;
import eu.ohim.sp.eservices.ui.util.Result;


public class GSControllerTest {
	
	
	@Mock
	private ESFlowBean flowBean;

	@Mock
	private GoodsServicesServiceInterface goodsServicesService;

	@Mock
	private ConfigurationServiceDelegatorInterface configurationService;
	
//	@Mock
//	private Integer levelScope;
//
	@InjectMocks
	GSController gSController=new GSController();
	
	@Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void modifyTerm(){

		String langId="1";
		String	oldNiceClass="";
		String	oldTerm="";
		String term="";
		String	niceClass="";

		//flowBean=Mockito.mock(ESFlowBean.class);

		GoodAndServiceForm goodForm = flowBean.getGoodsAndService(langId, oldNiceClass);

		gSController.modifyTerm(oldTerm, oldNiceClass, langId, term, niceClass);

		//goodForm=(GoodAndServiceForm) new Object();

		//gSController.modifyTerm(oldTerm, oldNiceClass, langId, term, niceClass);


	}
	
	@Test
	public void addTerms(){
		
		AddedTermForm addedTermForm=new   AddedTermForm();
		
		List<TermForm> termForms=new ArrayList<TermForm>();
		
		TermForm termForm=new TermForm();
		
		termForm.setDescription("description");
		
		termForms.add(termForm);
		
		addedTermForm.setTerms(termForms);
		gSController.addTerms(addedTermForm);

	}
	
	@Test
	public void removeTerm(){
		
	String	term="2";
	String	classId="2";
	String	oldNiceClass="";
	String	langId="1";
	String	imported="2";
		
		
	flowBean=Mockito.mock(ESFlowBean.class);
	
	  // Create a set
    Set<GoodAndServiceForm> goodsServices=new HashSet<GoodAndServiceForm>();
    
    GoodAndServiceForm goodAndServiceForm=  new GoodAndServiceForm();
    
    goodAndServiceForm.setClassId(classId);
    
   
    
    goodAndServiceForm.setLangId(langId);
    
//    goodsServices.add(new GoodAndServiceForm());
//    
//	flowBean.setGoodsServices(goodsServices);
		
	
	
//	termForm.setDescription("dedwedwefwfrewfr");
	
	//goodForm.addTermForm(termForm);
	
	Mockito.when(flowBean.getGoodsAndService("langId", "classId")).thenReturn(goodAndServiceForm);
		
	gSController.removeTerm(term, classId, langId, imported);
		
		
		
	}
	
	
	@Test
	public void removeClass(){

		//flowBean=Mockito.mock(ESFlowBean.class);

		  // Create a set

		   Set<GoodAndServiceForm> goodsServices=new HashSet<GoodAndServiceForm>();
	    GoodAndServiceForm goodAndServiceForm=  new GoodAndServiceForm();

	    Set<TermForm> termForm=new HashSet<TermForm>();

	    TermForm termForms=new TermForm();

	    termForms.setDescription("description");

	    termForm.add(termForms);

	    goodAndServiceForm.setClassId("classId");
	    goodAndServiceForm.setTermForms(termForm);


	    goodAndServiceForm.setLangId("langId");


	    goodsServices.add(goodAndServiceForm);


	  //  goodsServices.add(new GoodAndServiceForm());

	//	flowBean.setGoodsServices(goodsServices);


		Mockito.when(flowBean.getGoodsAndService("1", "")).thenReturn(goodAndServiceForm);

		gSController.removeClass("classId", "langId", "imported");


	}
	
	@Test
	public void submitListOnMyOwnJSON(){
		
		GoodAndServiceForm  goodAndServiceForm=new GoodAndServiceForm();
		
		TermForm termForm=new TermForm();
		
		termForm.setDescription("description");
		
		goodAndServiceForm.addTermForm(termForm);
		
		 BindingResult result=Mockito.mock(BindingResult.class);
		
		
		gSController.submitListOnMyOwnJSON(false, goodAndServiceForm, result);
		
		
	}
	
	
	@Test
	public void cleanGS(){
		
		gSController.cleanGS("langId");
		
	}
	
	
	
	
	
	@Test
	public void resetTerms(){
		
		flowBean=Mockito.mock(ESFlowBean.class);
		
		//flowBean.getOriginalGS()
		GoodAndServiceForm goodAndServiceForm=new GoodAndServiceForm();
		
		 String flowModeId = "56";
		 
			Set<GoodAndServiceForm> value=new HashSet<GoodAndServiceForm>();
			
			TermForm termForm=new TermForm();
			
			termForm.setDescription("description");
			termForm.setIdClass("idClass");
		
			goodAndServiceForm.addTermForm(termForm);
			
			goodAndServiceForm.setDesc("desc");
			
			goodAndServiceForm.setLangId("langId");
			
			
			value.add(goodAndServiceForm);
			
			//flowBean.addGoodAndService(goodAndServiceForm);
		
		Mockito.when(flowBean.getOriginalGS()).thenReturn(value);
		
		Field field2=	ReflectionUtils.findField(GSController.class, "gsLanguage");
		
		ReflectionUtils.makeAccessible(field2);
		
		ReflectionUtils.setField(field2, gSController, "en");
		
		
		gSController.resetTerms("langId");
		
	}
	
	
	@Test
	public void terms(){
		
	//	flowBean=Mockito.mock(ESFlowBean.class);
		
		//flowBean.getOriginalGS()
		GoodAndServiceForm goodAndServiceForm=new GoodAndServiceForm();
		
		GoodAndServiceForm goodAndServiceForm1=new GoodAndServiceForm();
		
		 String flowModeId = "56";
		 
		
		Set<GoodAndServiceForm> value=new HashSet<GoodAndServiceForm>();
		
		TermForm termForm=new TermForm();
		
		termForm.setDescription("description");
		termForm.setIdClass("idClass");
	
		goodAndServiceForm.addTermForm(termForm);
		
		goodAndServiceForm.setDesc("desc");
		
		goodAndServiceForm.setLangId("langId");
		
		goodAndServiceForm.setClassId("classId");
		
		goodAndServiceForm.setError("error");
			
		
		goodAndServiceForm1.addTermForm(termForm);
		goodAndServiceForm1.setDesc("cv vc");
		goodAndServiceForm1.setLangId("langId");
		goodAndServiceForm1.setClassId("ccv cvId");
		goodAndServiceForm1.setError("cv c");
		
		
		value.add(goodAndServiceForm);
		value.add(goodAndServiceForm1);
		
		
		
		
		Mockito.when(flowBean.getGoodsServices()).thenReturn(value);
		
		gSController.terms("langId");
		
	}
	
	
	
	@Test
	public void parentConceptNodes(){
		
		Collection<TaxonomyConceptNodeTreeView> value=new ArrayList<TaxonomyConceptNodeTreeView>();
		
		TaxonomyConceptNode taxonomyConceptNode	=new TaxonomyConceptNode();
		
		TaxonomyConceptNodeTreeView taxonomyConceptNodeTreeView=new TaxonomyConceptNodeTreeView(new TaxonomyConceptNode());

			value.add(taxonomyConceptNodeTreeView);
		
		Mockito.when(configurationService.getValueFromGeneralComponent(ConfigurationServiceDelegatorInterface.CONCEPT_TREE_NODES_PRE)).thenReturn("value");
		
		
		Mockito.when(goodsServicesService.getTaxonomy("langId", "term", null,"taxoConceptNodeId")).thenReturn(value);
		
		Collection<TaxonomyConceptNodeTreeView> value1=new ArrayList<TaxonomyConceptNodeTreeView>();
		
		taxonomyConceptNodeTreeView.setLevel(1);
		
		value1.add(taxonomyConceptNodeTreeView);
				
//		Mockito.when(TaxonomyUtil.getMinifiedView(value1)).thenReturn(value1);
		
	
	Field field=	ReflectionUtils.findField(GSController.class, "levelScope");
		
	ReflectionUtils.makeAccessible(field);
	
	ReflectionUtils.setField(field, gSController, 1);
	
	
	
	Field field1=	ReflectionUtils.findField(GSController.class, "firstLevelVisible");
	
	ReflectionUtils.makeAccessible(field1);
	
	ReflectionUtils.setField(field1, gSController, 1);
	
	
		
		
	//	Mockito.when(TaxonomyUtil.limitMinifiedView(TaxonomyUtil.getMinifiedView(value), null, 1)).thenReturn(value);
		
		
		gSController.parentConceptNodes("langId", "term");
		
//		gSController.parentConceptNodes("langId", "term", "taxoConceptNodeId");
	//	Mockito.when(taxonomyUtil.limitMinifiedView(value1,null,1)).thenReturn(value1);
	
	
		
		gSController.parentConceptNodes("langId", "term", "taxoConceptNodeId");
		
		
	}
	
	
//	@Test
//	public void handleException(){
//		
//		
//		SPException SPException=new SPException();
//		
//		Throwable e=SPException.getCause();
//		
//		gSController.handleException(e);
//		
//		
//	}
	
	
	
	@Test
	public void getImportNiceClassHeading(){
		
		
		gSController.getImportNiceClassHeading();
		
		
	}
	
	
	
	
	@Test
	public void didYouMean(){
		
		
		gSController.didYouMean("searchCriteria", "language");
		
		
	}
	
	
	
	@Test
	public void disableRemoval(){
		
		GoodAndServiceForm goodAndServiceForm=new GoodAndServiceForm();
		
		TermForm termForm=new TermForm();
		
		termForm.setIdClass("idClass");
		
		goodAndServiceForm.addTermForm(termForm);
		
		
		Mockito.when(flowBean.getGoodsAndService("language", "niceClass")).thenReturn(goodAndServiceForm);
		
		gSController.disableRemoval("niceClass", "language", true);
		
		
	}
	
	
	
	
	@Test
	public void importNiceClassHeading(){
		
		
	//flowBean=Mockito.mock(ESFlowBean.class);
		
		//flowBean.getOriginalGS()
		GoodAndServiceForm goodAndServiceForm=new GoodAndServiceForm();
		
		Set<GoodAndServiceForm> value=new HashSet<GoodAndServiceForm>();
		
		TermForm termForm=new TermForm();
		
		termForm.setDescription("description");
		termForm.setIdClass("idClass");
	
		goodAndServiceForm.addTermForm(termForm);
		
		goodAndServiceForm.setDesc("desc");
		
		goodAndServiceForm.setLangId("langId");
		
		
		value.add(goodAndServiceForm);
		
		Mockito.when(flowBean.getGoodsAndService("language", "niceClass")).thenReturn(goodAndServiceForm);
		
		
		
		Mockito.when(goodsServicesService.importNiceClassHeading("niceClass", "language")).thenReturn(goodAndServiceForm);
		
		gSController.importNiceClassHeading("niceClass", "language");
		
		
		
	}
	
	
	@Test
	public void getListOfGoodsServices(){
		
		
		
		gSController.getListOfGoodsServices();
		
	}
	
	@Test
	public void getDistribution(){
		
		
		gSController.getDistribution("language", "term", "taxoConceptNodeId");
		
		
	}
	
	@Test
	public void getGSClasses(){
		
		
GoodAndServiceForm goodAndServiceForm=new GoodAndServiceForm();
		
		Set<GoodAndServiceForm> value=new HashSet<GoodAndServiceForm>();
		
		TermForm termForm=new TermForm();
		
		termForm.setDescription("description");
		termForm.setIdClass("idClass");
	
		goodAndServiceForm.addTermForm(termForm);
		
		goodAndServiceForm.setDesc("desc");
		
		goodAndServiceForm.setLangId("langId");
		
		
		value.add(goodAndServiceForm);
		
		
		
		Mockito.when(flowBean.getGoodsAndServices()).thenReturn(value);
		
		
		gSController.getGSClasses();
		
	}
	
	
	@Test
	public void provideListOnMyOwn(){
		
		
		gSController.provideListOnMyOwn();
		
	}
	
	@Test
	public void loadModalPopup(){
		
		
		gSController.loadModalPopup();
		
	}
	
	
	
	@Test
	public void initBinder(){
		
		Object target=new Object();
		
		
		
		WebDataBinder binder=new WebDataBinder(target);
		
		gSController.initBinder(binder);
		
	}
	
	
	@Test
	public void loadNiceClassHeading(){
		
		
		gSController.loadNiceClassHeading("niceClass", "language");
		
	}
	
	
	@Test
	public void exceptions() {
		SPException e=new SPException();
		Assert.assertNotNull(gSController.handleException(e));
		Assert.assertNotNull(gSController.handleNoResultsException(e));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void importNiceClassHeading2() {

		String niceClass="niceClass";
		String language="language";
		Mockito.when(flowBean.getGoodsAndService(language, niceClass)).thenThrow(Exception.class);
		Assert.assertNotNull(gSController.importNiceClassHeading(niceClass, language));
	}
	
	@Test
	public void modifyTerm2() {
		String oldTerm="oldTerm";
		String oldNiceClass="oldNiceClass";
		String langId="langId";
		String term="term";
		String niceClass="niceClass";
		
		GoodAndServiceForm goodForm = Mockito.mock(GoodAndServiceForm.class); 
		Mockito.when(goodForm.getTermForms()).thenReturn(new HashSet<TermForm>());
		Mockito.when(flowBean.getGoodsAndService(langId, oldNiceClass)).thenReturn(goodForm);
		
		Assert.assertNotNull(gSController.modifyTerm(oldTerm, oldNiceClass, langId, term, niceClass));
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=SPException.class)
	public void resetTerms2() throws CloneNotSupportedException{
		
		Field field2=	ReflectionUtils.findField(GSController.class, "gsLanguage");
		
		ReflectionUtils.makeAccessible(field2);
		
		ReflectionUtils.setField(field2, gSController, "en");
		String langId="EN";
		GoodAndServiceForm goodAndServiceForm1=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(goodAndServiceForm1.clone()).thenReturn(Mockito.mock(GoodAndServiceForm.class));
		GoodAndServiceForm goodAndServiceForm2=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(goodAndServiceForm2.clone()).thenThrow(CloneNotSupportedException.class);
		Set<GoodAndServiceForm> setgs=new HashSet<GoodAndServiceForm>();
		setgs.add(goodAndServiceForm1);
		Mockito.when(flowBean.getOriginalGS()).thenReturn(setgs);

		gSController.resetTerms(langId);
		
		setgs.add(goodAndServiceForm2);
		gSController.resetTerms(langId);
	}
	
	@Test
	public void testResults(){
		Result res=new Result("first");
		res.setValue("second");
		Assert.assertNotEquals("first", res.getValue());
	}
	
	@Test
	public void removeClass2(){

		String classId="classId";
		String langId="langId";
		String imported="false";
		
		Set<GoodAndServiceForm> setgsf=new HashSet<GoodAndServiceForm>();
		setgsf.add(Mockito.mock(GoodAndServiceForm.class));
		Mockito.when(flowBean.getGoodsAndService(langId, classId)).thenReturn(Mockito.mock(GoodAndServiceForm.class));
		Mockito.when(flowBean.getGoodsServices()).thenReturn(setgsf);
		Result res=gSController.removeClass(classId, langId, imported);
		Assert.assertEquals("success", res.getValue());
		
		setgsf.add(Mockito.mock(GoodAndServiceForm.class));
		res=gSController.removeClass(classId, langId, imported);
		Assert.assertEquals("success", res.getValue());
	}
	
	@Test
	public void removeTerm1(){

		String term="term";
		String classId="classId";
		String langId="langId";
		String imported="false";
		
		TermForm termForm = new TermForm();
		termForm.setDescription(term);
		termForm.setIdClass(classId);
		Set<TermForm> setTerms=new HashSet<TermForm>();
		
		setTerms.add(termForm);
		
		Set<GoodAndServiceForm> setgsf=new HashSet<GoodAndServiceForm>();
		setgsf.add(Mockito.mock(GoodAndServiceForm.class));
		GoodAndServiceForm goodAndServiceForm=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(goodAndServiceForm.getTermForms()).thenReturn(setTerms);
		Mockito.when(flowBean.getGoodsAndService(langId, classId)).thenReturn(goodAndServiceForm);
		Mockito.when(flowBean.getGoodsServices()).thenReturn(setgsf);
		Result res=gSController.removeTerm(term,classId, langId, imported);
		Assert.assertEquals("success", res.getValue());
		
		setTerms.add(termForm);
		setgsf.add(Mockito.mock(GoodAndServiceForm.class));
		setgsf.add(Mockito.mock(GoodAndServiceForm.class));
		res=gSController.removeTerm(term,classId, langId, "true");
		Assert.assertEquals("success", res.getValue());
	}
	
	@Test
	public void removeTerm2(){

		String term="term";
		String classId="classId";
		String langId="langId";
		String imported="true";
		
		TermForm termForm = new TermForm();
		termForm.setDescription(term);
		termForm.setIdClass(classId);
		Set<TermForm> setTerms=new HashSet<TermForm>();
		setTerms.add(Mockito.mock(TermForm.class));
		setTerms.add(termForm);
		
		Set<GoodAndServiceForm> setgsf=new HashSet<GoodAndServiceForm>();
		setgsf.add(Mockito.mock(GoodAndServiceForm.class));
		GoodAndServiceForm goodAndServiceForm=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(goodAndServiceForm.getTermForms()).thenReturn(setTerms);
		Mockito.when(flowBean.getGoodsAndService(langId, classId)).thenReturn(goodAndServiceForm);
		Mockito.when(flowBean.getGoodsServices()).thenReturn(setgsf);
		
		Result res=gSController.removeTerm(term,classId, langId, imported);
		Assert.assertEquals("success", res.getValue());
		
		res=gSController.removeTerm(term,classId, langId, imported);
		Assert.assertEquals("success", res.getValue());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void submitListOnMyOwnJSON2() {
		GoodAndServiceForm goodsForm=Mockito.mock(GoodAndServiceForm.class);
		BindingResult result=Mockito.mock(BindingResult.class);
		Mockito.when(result.getErrorCount()).thenReturn(2);
		Assert.assertNotNull(gSController.submitListOnMyOwnJSON(false, goodsForm, result));
		
		Mockito.when(result.getErrorCount()).thenReturn(0);
		Mockito.when(goodsServicesService.verifyListOfTerms(Mockito.anyString(), Mockito.anyString(),Mockito.anyString()))
			.thenReturn(Mockito.mock(GoodAndServiceForm.class));
		Assert.assertNotNull(gSController.submitListOnMyOwnJSON(false, goodsForm, result));
		
		Mockito.when(goodsServicesService.verifyListOfTerms(Mockito.anyString(), Mockito.anyString(),Mockito.anyString()))
			.thenThrow(SPException.class);
		Assert.assertNotNull(gSController.submitListOnMyOwnJSON(false, goodsForm, result));
	}

}
