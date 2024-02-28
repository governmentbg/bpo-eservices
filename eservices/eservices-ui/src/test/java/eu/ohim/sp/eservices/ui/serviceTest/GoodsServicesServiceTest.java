package eu.ohim.sp.eservices.ui.serviceTest;

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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.ErrorFactory;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TermForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.classification.NiceClassificationService;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import eu.ohim.sp.core.domain.classification.wrapper.ClassScope;
import eu.ohim.sp.core.domain.classification.wrapper.ClassificationTerm;
import eu.ohim.sp.core.domain.classification.wrapper.DistributionCriteria;
import eu.ohim.sp.core.domain.classification.wrapper.DistributionResults;
import eu.ohim.sp.core.domain.classification.wrapper.Term;
import eu.ohim.sp.core.domain.classification.wrapper.TermsToBeValidated;
import eu.ohim.sp.core.domain.classification.wrapper.TermsValidated;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.GoodsServicesService;

public class GoodsServicesServiceTest {
	
	@Mock
	private NiceClassificationService classificationService;
	
	@Mock
	private RulesService businessRulesService;
	
	@Mock
    private ConfigurationServiceDelegatorInterface configurationService;
    
	@Mock
    private ErrorFactory errorFactory; 
	
	//@Mock
	//private EServicesUtilitiesProviderInterface eServicesProvider;
	
	@InjectMocks
	GoodsServicesService  goodsServicesService=new GoodsServicesService();
	
	@Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(goodsServicesService, "termSeparator", ";");
    }
	
	@Test
	public void didYouMean(){
		String language="EN"; 
		String searchCriteria="criteria";
		Assert.assertEquals(0,goodsServicesService.didYouMean(language, searchCriteria).size());
	} 
	
	@Test
	public void verifyListOfTerms(){
		TermsToBeValidated termsToBeValidated = Mockito.mock(TermsToBeValidated.class);
		Mockito.when(termsToBeValidated.getLanguage()).thenReturn("EN");
		Mockito.when(termsToBeValidated.getNiceClass()).thenReturn("class");
//		String termString=Mockito.mock(String.class);
//		Mockito.when(termString.split(Mockito.anyString())).thenReturn("aa;bb;cc;dd".split(";"));
		Mockito.when(termsToBeValidated.getTerms()).thenReturn("terms");
		List<TermsToBeValidated> terms=new ArrayList<TermsToBeValidated>();
		terms.add(termsToBeValidated);
		Mockito.when(configurationService.isServiceEnabled(AvailableServices.VALIDATE_TERM.value())).thenReturn(true);
		Mockito.when(classificationService.verifyListOfTerms(terms)).thenReturn(null);
		Assert.assertEquals(1,goodsServicesService.verifyListOfTerms(terms).size());
		
	}
	
	@Test
	public void containsAllNiceClassHeading() {
		GoodAndServiceForm goodsAndServiceForm=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(configurationService.isServiceEnabled(AvailableServices.IMPORT_NICE_CLASS_HEADING.value())).thenReturn(false);
		Assert.assertEquals(false,goodsServicesService.containsAllNiceClassHeading(goodsAndServiceForm));
	}


	
//	@Test
//	public void containsAllNiceClassHeadingError() {
//		GoodAndServiceForm goodsAndServiceForm=Mockito.mock(GoodAndServiceForm.class);
//		Mockito.when(configurationService.isServiceEnabled(AvailableServices.IMPORT_NICE_CLASS_HEADING.value())).thenReturn(true);
//		Assert.assertEquals(false,goodsServicesService.containsAllNiceClassHeading(goodsAndServiceForm));
//	}
	
	@Test
	public void importCachedNiceClassHeading(){
		String office=""; 
		String niceClass="";
		String language="";
		Mockito.when(classificationService.getNiceClassHeading(niceClass, language)).thenReturn(null);
		Assert.assertEquals(0,goodsServicesService.importCachedNiceClassHeading(office, niceClass, language).size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void importCachedNiceClassHeadingError(){
		String office=""; 
		String niceClass="";
		String language="";
		Mockito.when(classificationService.getNiceClassHeading(niceClass, language)).thenThrow(SPException.class);
		Assert.assertEquals(0,goodsServicesService.importCachedNiceClassHeading(office, niceClass, language).size());
	}
	
	@Test
	public void importNiceClassHeading() {
		String niceClass="class"; 
		String language="";
		Mockito.when(configurationService.isServiceEnabled(AvailableServices.IMPORT_NICE_CLASS_HEADING.value())).thenReturn(false);
		Assert.assertNotNull(goodsServicesService.importNiceClassHeading(niceClass, language));
	}
	
	@Test
	public void importNiceClassHeading2() {
		String niceClass="class"; 
		String language="";
		Mockito.when(configurationService.isServiceEnabled(AvailableServices.IMPORT_NICE_CLASS_HEADING.value())).thenReturn(true);
		Assert.assertNotNull(goodsServicesService.importNiceClassHeading(niceClass, language));
	}
	
	
	@Test
	public void verifyListOfGS(){
		
		ESFlowBean flowBean=Mockito.mock(ESFlowBean.class);
		
		GoodAndServiceForm goodAndServiceForm=new GoodAndServiceForm();
		
		flowBean.addGoodAndService(goodAndServiceForm);
		flowBean.addGoodAndService(goodAndServiceForm);
		
		flowBean.addGoodAndService(goodAndServiceForm);
		
		flowBean.addGoodAndService(goodAndServiceForm);
		
		
		 String flowModeId = "56";
			Set<GoodAndServiceForm> value=new HashSet<GoodAndServiceForm>();
			
			TermForm termForm=new TermForm();
			
			termForm.setIdClass("idClass");
			
			goodAndServiceForm.addTermForm(termForm);
			
			value.add(goodAndServiceForm);
			
			List<TermsToBeValidated> listTerms=new ArrayList<TermsToBeValidated>();
			
	//	Collection<TermsValidated> collection=new Collections();
			
	//		Mockito.when(classificationService.verifyListOfTerms(listTerms)).thenReturn(collection);
			
			List<GoodAndServiceForm> list=new ArrayList<GoodAndServiceForm>();
			
			list.add(goodAndServiceForm);
			
			TermsToBeValidated termsToBeValidated=new TermsToBeValidated();
			
			termsToBeValidated.setTerms("terms");
			
			listTerms.add(termsToBeValidated);
			
			Mockito.when(configurationService.isServiceEnabled(AvailableServices.VALIDATE_TERM.value())).thenReturn(true);
			
			Collection<TermsValidated> collection=new ArrayList<TermsValidated>();
			
			TermsValidated termsValidated=new TermsValidated();
			
			List<Term> terms=new ArrayList<Term>();
			
			Term term=new Term();
			
			term.setText("text");
			
			terms.add(term);
			
			termsValidated.setDescription("description");
			
			termsValidated.setTerms(terms);
			
			collection.add(termsValidated);
			
			Mockito.when(classificationService.verifyListOfTerms(listTerms)).thenReturn(collection);
		
			
			Mockito.when(goodsServicesService.verifyListOfTerms(listTerms)).thenReturn(list);
			
			
		
			
		//	goodsServicesSecdrvice.getGoodAndServiceForm(termsValidated.getTerms(), termsValidated.getLanguage(), termsValidated.getNiceClass()).
		
		
	Mockito.when(flowBean.getGoodsAndServices()).thenReturn(value);
		
		
		goodsServicesService.verifyListOfGS(flowBean);
	
		
	}
	
	
	
	
	@Test
	public void getClassScope(){
		
		Collection<ClassScope> collection=new ArrayList<ClassScope>();
		
		ClassScope termsValidated=new ClassScope();
		
		List<Term> terms=new ArrayList<Term>();
		
		Term term=new Term();
		
		term.setText("text");
		
		terms.add(term);
		
		termsValidated.setDescription("description");
		
		termsValidated.setClassNumber("classNumber");
		
		collection.add(termsValidated);
		
		
		
		
		
		
		Mockito.when(classificationService.getClassScopes("language", "niceClassHeadings")).thenReturn(collection);
		
		goodsServicesService.getClassScope("language", "niceClassHeadings");
		
		
	}
	
	@Test
	public void verifyListOfTerms2(){
	
	String language="en";
	String niceClass="en";

	ESFlowBean flowBean=Mockito.mock(ESFlowBean.class);
	
	
	List<TermsToBeValidated> listTerms=new ArrayList<TermsToBeValidated>();
	
	//	Collection<TermsValidated> collection=new Collections();
			
	//		Mockito.when(classificationService.verifyListOfTerms(listTerms)).thenReturn(collection);
	
	GoodAndServiceForm goodAndServiceForm=new GoodAndServiceForm();
	
	flowBean.addGoodAndService(goodAndServiceForm);
	flowBean.addGoodAndService(goodAndServiceForm);
	
	flowBean.addGoodAndService(goodAndServiceForm);
	
	flowBean.addGoodAndService(goodAndServiceForm);
	
	
	 String flowModeId = "56";
		Set<GoodAndServiceForm> value=new HashSet<GoodAndServiceForm>();
		
		TermForm termForm=new TermForm();
		
		termForm.setIdClass("idClass");
		
		goodAndServiceForm.addTermForm(termForm);
		
		value.add(goodAndServiceForm);
	
	
	
	
	List<GoodAndServiceForm> list=new ArrayList<GoodAndServiceForm>();
	
	list.add(goodAndServiceForm);
			
		
			
			list.add(goodAndServiceForm);
			
			TermsToBeValidated termsToBeValidated=new TermsToBeValidated();
			
			termsToBeValidated.setTerms("terms");
			
			listTerms.add(termsToBeValidated);
			
	
			Mockito.when(configurationService.isServiceEnabled(AvailableServices.VALIDATE_TERM.value())).thenReturn(true);
			
			
	Collection<TermsValidated> collection=new ArrayList<TermsValidated>();
			
			TermsValidated termsValidated=new TermsValidated();
			
			List<Term> terms=new ArrayList<Term>();
			
			Term term=new Term();
			
			term.setText("text");
			
			terms.add(term);
			
			termsValidated.setDescription("description");
			
			termsValidated.setTerms(terms);
			
			collection.add(termsValidated);
			
			
	Mockito.when(classificationService.verifyListOfTerms(listTerms)).thenReturn(collection);
		
			
	
			
			
			
	Mockito.when(goodsServicesService.verifyListOfTerms(listTerms)).thenReturn(list);

	String terms1="";
	
	goodsServicesService.verifyListOfTerms(language, niceClass, terms1);

	// String flowModeId = "56";
	GoodAndServiceForm value1=new GoodAndServiceForm();
		
		TermForm termForm1=new TermForm();
		
		termForm.setIdClass("idClass");
		
		goodAndServiceForm.addTermForm(termForm1);
		
		value.add(goodAndServiceForm);
	
	//configurationService.isServiceEnabled(AvailableServices.VALIDATE_TERM.value()
	
	//	Mockito.when(goodsServicesService.verifyListOfTerms(language, niceClass, terms1)).thenReturn(value1);
		
		
	String	termSeparator="termSeparator_termSeparator";
		
		 Field field=	ReflectionUtils.findField(GoodsServicesService.class, "termSeparator");
			
			ReflectionUtils.makeAccessible(field);
			
			ReflectionUtils.setField(field, goodsServicesService,termSeparator);
			
		
		
		Mockito.when(configurationService.isServiceEnabled(AvailableServices.VALIDATE_TERM.value())).thenReturn(false);
	
		goodsServicesService.verifyListOfTerms(language, niceClass, terms1);

	
	}
	
	
	@Test
	public void getDistribution2(){
		
		String language="en";
		String term="en";
		
		
		Collection< DistributionResults> distributionResults=Mockito.mock(Collection.class);
				
		
		
	//	DistributionCriteria distributionCriteria=	new DistributionCriteria();
		
		DistributionResults distributionResults1=	new DistributionResults();
		
		Collection<ClassificationTerm> classificationTerms=new ArrayList<ClassificationTerm>();
		
		
		
		
		distributionResults1.setClassificationTerms(classificationTerms);
		
	
	
		
	Mockito.when(classificationService.getTermDistribution(Mockito.any(DistributionCriteria.class))).thenReturn(distributionResults1);
		
		
		
	//	distributionCriteria.setClassificationTerms(classificationTerms);
		
		
	
			
		ClassificationTerm classificationTerm	=	new ClassificationTerm();
		
		classificationTerm.setTotalNumber(2);
//		classificationTerms.add(classificationTerm);
		
		
	//	distributionCriteria.setClassificationTerms(classificationTerms);
		
	//	distributionCriteria.setClassificationTerms(classificationTerms);
				
//	Mockito.when(classificationService.getTermDistribution(Mockito.any(DistributionCriteria.class))).
//			thenReturn();
//		
		
	//Mockito.when(distributionResults.getClassificationTerms())
		
	//	Mockito.when(classificationService.getTermDistribution(distributionCriteria))
		
		List<Integer> list = goodsServicesService.getDistribution(language,  term);
		Assert.assertEquals(0, list.size());
		

		
	}
	
	@Test
	public void containsAllNiceClassHeading2(){
		
		GoodAndServiceForm goodsAndServiceForm= new GoodAndServiceForm();
		
		//Mockito.when(classificationService.getTermDistribution(Mockito.any(DistributionCriteria.class))).
		//thenReturn(new DistributionResults());
		
		Mockito.when(configurationService.isServiceEnabled("Import_Nice_Class_Heading")).thenReturn(true);
		
		
	//	configurationService.isServiceEnabled(AvailableServices.IMPORT_NICE_CLASS_HEADING.value()
		
//		goodsServicesService.containsAllNiceClassHeading(goodsAndServiceForm);
				
	}
	
//	@Test
//	public void containsAllNiceClassHeading2(){
//		
//		GoodAndServiceForm goodsAndServiceForm= Mockito.mock(GoodAndServiceForm.class);
//		
//		//Mockito.when(classificationService.getTermDistribution(Mockito.any(DistributionCriteria.class))).
//		//thenReturn(new DistributionResults());
//		
//		Mockito.when(configurationService.isServiceEnabled(AvailableServices.IMPORT_NICE_CLASS_HEADING.value())).thenReturn(true);
//		
//		Collection<Term> terms = new ArrayList<Term>();
//		Term term = Mockito.mock(Term.class);
//		terms.add(term);
//		Mockito.when(eServicesProvider.getNiceClassHeading(null,goodsAndServiceForm)).thenReturn(terms);
//		Assert.assertFalse(goodsServicesService.containsAllNiceClassHeading(goodsAndServiceForm));
//		Set<TermForm> termsForm = new HashSet<TermForm>();
//		TermForm termForm = Mockito.mock(TermForm.class);
//		termsForm.add(termForm);
//		Mockito.when(goodsAndServiceForm.getTermForms()).thenReturn(termsForm);
//		Assert.assertTrue(goodsServicesService.containsAllNiceClassHeading(goodsAndServiceForm));
//		Mockito.when(term.getText()).thenReturn("TEST");
//		Mockito.when(termForm.getDescription()).thenReturn("TEST");
//		Assert.assertTrue(goodsServicesService.containsAllNiceClassHeading(goodsAndServiceForm));
//		Mockito.when(term.getText()).thenReturn("TEST");
//		Mockito.when(termForm.getDescription()).thenReturn("TEST2");
//		Assert.assertFalse(goodsServicesService.containsAllNiceClassHeading(goodsAndServiceForm));
//	}
	
	
	
	
	@Test
	public void getTaxonomy2(){
		
		
		
		
		goodsServicesService.getTaxonomy("language", "term", 1, "taxoConceptNodeId");
		
	}
	
	
	
	@Test
	public void getParentConceptNodes2(){
		
		
		
		
		
		
		
		goodsServicesService.getParentConceptNodes("language", "term");
		
		
		
	}
	
	@Test
	public void importNiceClassHeading3(){
		String language="en";
		String niceClass="en";
		
		Mockito.when(configurationService.isServiceEnabled(AvailableServices.IMPORT_NICE_CLASS_HEADING.value())).thenReturn(true);
		
		
		Collection<Term> collection=new ArrayList<Term>();
		
		TermsValidated termsValidated=new TermsValidated();
		
		List<Term> terms=new ArrayList<Term>();
		
		Term term=new Term();
		
		term.setText("text");
		
		terms.add(term);
		
		termsValidated.setDescription("description");
		
		termsValidated.setTerms(terms);
		
		collection.add(term);
		
	//	Mockito.when(((GoodsServicesServiceInterface) AopContext.currentProxy()).importCachedNiceClassHeading("office", niceClass, language)).thenReturn(collection);
		
//		goodsServicesService.importNiceClassHeading(niceClass, language);
		
	}
	
//	@Test
//	public void importNiceClassHeading3(){
//		String language="en";
//		String niceClass="en";
//		
//		Mockito.when(configurationService.isServiceEnabled(AvailableServices.IMPORT_NICE_CLASS_HEADING.value())).thenReturn(true);
//		
//		
//		Collection<Term> collection=new ArrayList<Term>();
//		TermsValidated termsValidated=new TermsValidated();
//		List<Term> terms=new ArrayList<Term>();
//		Term term=new Term();
//		term.setText("text");
//		terms.add(term);
//		termsValidated.setDescription("description");
//		termsValidated.setTerms(terms);
//		collection.add(term);
//		Mockito.when(eServicesProvider.getNiceClassHeading(null, niceClass, language)).thenReturn(collection);
//		
//		goodsServicesService.importNiceClassHeading(niceClass, language);
//		
//	}
	
	@Test
	public void validateGoodsAndServicesDescription2(){
		
		GoodAndServiceForm goodsForm=new GoodAndServiceForm();
		
		
		BindingResult result=Mockito.mock(BindingResult.class);
		goodsServicesService.validateGoodsAndServicesDescription(goodsForm, result);
		
	}
	
	
	@Test
	public void  importCachedNiceClassHeading2(){
		
		String office="";
		
		String niceClass="";
		 String language="";
		
		goodsServicesService.importCachedNiceClassHeading(office,niceClass,language);
		
		
	}
	
}
