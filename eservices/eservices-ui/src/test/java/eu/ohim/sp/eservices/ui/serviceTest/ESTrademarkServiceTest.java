package eu.ohim.sp.eservices.ui.serviceTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.common.ui.adapter.LimitedTrademarkFactory;
import eu.ohim.sp.common.ui.adapter.TrademarkFactory;
import eu.ohim.sp.common.ui.adapter.opposition.LegalActVersionFactory;
import eu.ohim.sp.common.ui.adapter.opposition.OppositionGroundFactory;
import eu.ohim.sp.common.ui.form.opposition.EarlierEntitlementRightDetails;
import eu.ohim.sp.common.ui.form.opposition.EarlierEntitlementRightInf;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKind;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKindLegalAct;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.opposition.RelativeGrounds;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.service.ConfigurationServiceDelegator;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.legalActVersion.xsd.LegalActVersions;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.opposition.LegalActVersion;
import eu.ohim.sp.core.domain.opposition.OppositionAbsoluteGrounds;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.register.TradeMarkSearchService;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.eservices.ui.service.ESTrademarkService;
import eu.ohim.sp.eservices.ui.service.interfaces.ESApplicationServiceInterface;

public class ESTrademarkServiceTest {
	
	@Mock
	private TradeMarkSearchService tradeMarkService;
	
	@Mock
	private ESApplicationServiceInterface applicationService;
	
	@Mock
	private SectionViewConfiguration sectionViewConfiguration;	
	
	@Mock
	private TrademarkFactory tmFactory;
	
	@Mock
	private LimitedTrademarkFactory tmLimitedFactory;
	
	@Mock
    private LegalActVersionFactory legalActVersionFactory;
	
	@Mock
    private OppositionGroundFactory oppositionGroundFactory;
	
	@Mock
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;	
	
	@Mock
	private RulesService businessRulesService;

	@Mock
	private OppositionBasisForm oppositionBasisForm;

	@Mock
	private RulesInformation rulesInformation;

	@Mock
	private Errors errors;

	@Mock
	private ConfigurationServiceDelegatorInterface configurationServiceDelegatorInterface;
	
	@Mock
	private ConfigurationService configurationServiceInterface;

	@Mock
	private TMDetailsForm tMDetailsForm;

	@Mock
	private ConfigurationService configurationService;
	

	@Before
	public void setUp() throws SecurityException, NoSuchMethodException
	{
		MockitoAnnotations.initMocks(this);       
	}
	
	@InjectMocks
	ESTrademarkService eSTrademarkService = new ESTrademarkService();
	
	 private ESTrademarkService product; // the class under test
	 private Method m;
	 private static String METHOD_NAME = "configureBlockingMessages";
	 private Class[] parameterTypes;
	 private Object[] parameters;
	
	
	@SuppressWarnings("unchecked")
	@Test(expected=SPException.class)
	public void getTradeMark() {
		Mockito.when(tmFactory.convertFrom(Mockito.any(TradeMark.class))).thenThrow(Exception.class);
		eSTrademarkService.getTradeMark("", "");
	}
	
	@Test
	public void getTradeMarkAutocomplete(){
		Assert.assertNull(eSTrademarkService.getTradeMarkAutocomplete("", "", 0, false, "tm-renewal"));
	}
	
	@Test
	public void validateTradeMark(){
		String module="module";
		RulesInformation rulesInformation = Mockito.mock(RulesInformation.class);
		Mockito.when(rulesInformation.getCustomObjects()).thenReturn(new HashMap<String, Object>());
		Mockito.when(configurationServiceDelegator.getValue(Mockito.anyString(), Mockito.anyString())).thenReturn("true");
		TMDetailsForm tradeMark = Mockito.mock(TMDetailsForm.class);
		Mockito.when(tradeMark.getCheckBdBlocking()).thenReturn(true);
		Errors errors = Mockito.mock(Errors.class);
		String flowModeId="ID";
		
		Mockito.when(tradeMarkService.validateTradeMark(Mockito.anyString(),Mockito.any(TradeMark.class),Mockito.any(RulesInformation.class)))
			.thenReturn(Mockito.mock(ErrorList.class));
		
		Assert.assertNotNull(eSTrademarkService.validateTradeMark(module, tradeMark, rulesInformation, errors, flowModeId));
	}
	
	@Test
	public void filter(){
		String applicationType = "";
		TMDetailsForm tm = Mockito.mock(TMDetailsForm.class);
		GroundCategoryKindLegalAct category = GroundCategoryKindLegalAct.ABSOLUTE_GROUNDS;
		List<String> codesFiltered = new ArrayList<String>();
		
		LegalActVersions  legalActVersions = Mockito.mock(LegalActVersions.class);
		Mockito.when(legalActVersions.getLegalActVersion()).thenReturn(new ArrayList<LegalActVersions.LegalActVersion>());
		Mockito.when(configurationServiceDelegator.getObjectFromGeneralComponent(ConfigurationServiceDelegatorInterface.LEGAL_ACT_VERSIONS_PARAM , LegalActVersions.class))
		.thenReturn(legalActVersions);
		
		List<Object> listObj=new ArrayList<Object>();
		listObj.add(Mockito.mock(LegalActVersion.class));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("key",listObj);
		Mockito.when(businessRulesService.calculate(Mockito.anyString(),Mockito.anyString(),Mockito.anyListOf(Object.class)))
		.thenReturn(resultMap);
		
		Mockito.when(legalActVersionFactory.convertFrom(Mockito.any(LegalActVersion.class))).thenReturn(Mockito.mock(eu.ohim.sp.common.ui.form.opposition.LegalActVersion.class));
		
		Assert.assertEquals(1,eSTrademarkService.filter(applicationType, tm, category, codesFiltered).size());
		
	}
	
	@Test
	public void validateOpposition(){
		String module="MOD";
		OppositionBasisForm oppositionBasisForm=Mockito.mock(OppositionBasisForm.class);
		RulesInformation rulesInformation=Mockito.mock(RulesInformation.class); 
		Errors errors=Mockito.mock(Errors.class); 
		String flowModeId="ID";
		
		Mockito.when(oppositionBasisForm.getCheckBdBlocking()).thenReturn(true);
		Mockito.when(rulesInformation.getCustomObjects()).thenReturn(new HashMap<String, Object>());
		
		TMDetailsForm tMDetailsForm = Mockito.mock(TMDetailsForm.class);
		EarlierEntitlementRightDetails  earlierEntitlementRightDetails = Mockito.mock(EarlierEntitlementRightDetails.class); 
		Mockito.when(earlierEntitlementRightDetails.getEarlierTradeMarkDetails()).thenReturn(tMDetailsForm);
		EarlierEntitlementRightInf earlierEntitlementRightInf =Mockito.mock(EarlierEntitlementRightInf.class);
		Mockito.when(earlierEntitlementRightInf.getEarlierEntitlementRightDetails()).thenReturn(earlierEntitlementRightDetails);
		RelativeGrounds relativeGrounds = Mockito.mock(RelativeGrounds.class);
		Mockito.when(relativeGrounds.getEarlierEntitlementRightInf()).thenReturn(earlierEntitlementRightInf);
		Mockito.when(oppositionBasisForm.getRelativeGrounds()).thenReturn(relativeGrounds);
		Mockito.when(oppositionBasisForm.getGroundCategory()).thenReturn(GroundCategoryKind.ABSOLUTE_GROUNDS);
		
		Assert.assertTrue(eSTrademarkService.validateOpposition(module, oppositionBasisForm, rulesInformation, errors, flowModeId).getErrorList().isEmpty());
	}

	@Test
	public void validateTradeMark2() {

		String module = "";

		String flowModeId = "0";

		TMDetailsForm tradeMark = new TMDetailsForm();

		rulesInformation = Mockito.mock(RulesInformation.class);

		errors = Mockito.mock(Errors.class);

		Map<String, Sections> viewConfiguration;

		viewConfiguration = new HashMap();

		viewConfiguration.put("1", new Sections());

		sectionViewConfiguration = new SectionViewConfiguration();

		sectionViewConfiguration.setViewConfiguration(viewConfiguration);

		sectionViewConfiguration.getViewConfiguration();

		Sections sections = new Sections();

		sections.setAnonymousMode(true);
		sections.setNavigationPath(true);

		sectionViewConfiguration = new SectionViewConfiguration();

		// new HashSet<"fv",sections>()

		// sectionViewConfiguration.setViewConfiguration();

		// Mockito.when(sectionViewConfiguration).thenReturn(new
		// SectionViewConfiguration());

		// sectionViewConfiguration.getViewConfiguration().put("0", sections);

		// Fallo por aqui

		// Mockito.when(
		// sectionViewConfiguration.getViewConfiguration().get(0)).thenReturn(sections);

		// flowModeId=sectionViewConfiguration.getViewConfiguration().get(0).getSection().get(0).getCoreName();

		// String module, TMDetailsForm tradeMark,
		// RulesInformation rulesInformation, Errors errors, String flowModeId
		
		
		LimitedTradeMark  limitedTradeMark=new LimitedTradeMark();

		Mockito.when(tmLimitedFactory.convertTo(tradeMark)).thenReturn(limitedTradeMark);
		
		
		eSTrademarkService.validateTradeMark(module, tradeMark,
			rulesInformation, errors, flowModeId);

	}

	//@Test
	public void configureBlockingMessages() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		
		  parameters[0] = "someIdentifier";
		  String result = (String) m.invoke(product, parameters); 
		  
	}
	
	@Test
	public void filter2() {

		String applicationType = "";

		// List<GroundCategoryKindLegalAct> groundCategoryKindLegalAct =new
		// ArrayList();

		// List<TMDetailsForm> tMDetailsForm=new ArrayList();

		// Mockito.when(eSTrademarkService.getCoreObjects(applicationType,
		// codesFiltered, filteredCore, filteredCommon)).thenReturn(true);;

		List<String> codesFiltered = new ArrayList<String>();

		List<LegalActVersion> filteredCore = new ArrayList<LegalActVersion>();

		List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion> filteredCommon = new ArrayList<eu.ohim.sp.common.ui.form.opposition.LegalActVersion>();

		
	
		
		
		LegalActVersions egalActVersions=	new LegalActVersions();
		

		
	//	configurationServiceDelegator=new ConfigurationServiceDelegator();
		
	
	LegalActVersions legalActVersions	=new LegalActVersions();
		
		legalActVersions.getLegalActVersion().add(new LegalActVersions.LegalActVersion());
		
		
		Mockito.when( configurationServiceDelegator.getObjectFromGeneralComponent(ConfigurationServiceDelegatorInterface.LEGAL_ACT_VERSIONS_PARAM , LegalActVersions.class)).thenReturn(legalActVersions);
		
		
		
		LegalActVersions.LegalActVersion legalActVersion1=new  LegalActVersions.LegalActVersion();
		
		List objects1=new ArrayList();
		
		LegalActVersion legalActVersion=new LegalActVersion();
		
		
		legalActVersion.setCodeVersion("codeVersion");
		//legalActVersion.set
		
		objects1.add(legalActVersion);
		
		
		List<Object> objects=new ArrayList<Object>();
		
		List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion> objectsLegalActVersion=new ArrayList<eu.ohim.sp.common.ui.form.opposition.LegalActVersion>();
		
		eu.ohim.sp.common.ui.form.opposition.LegalActVersion objectsLegalActVersion1=new eu.ohim.sp.common.ui.form.opposition.LegalActVersion();
		
		objectsLegalActVersion1.setGroundCategory(GroundCategoryKindLegalAct.ABSOLUTE_GROUNDS);
		
		objectsLegalActVersion.add(objectsLegalActVersion1);
		
		RulesInformation rulesInformation		=new RulesInformation();
		
		
		rulesInformation.getCustomObjects().put("type", "");
		rulesInformation.getCustomObjects().put("category", "Relative Grounds");
		
		
		
		objects.add(rulesInformation);
		
		objects.add(new ArrayList());
		
	
		
		Map<String, Object> value=new HashMap<String,Object>();
		
		Sections sections	=new Sections();
		
		Section section=	new Section();
		
		section.setCoreName("value");
		
		
		
		value.put("0", objects1);
		
		
		Map<String, Object> value1=new HashMap<String,Object>();
		
		value1.put("0", new ArrayList());
		
		
		TradeMark tradeMark	=	new TradeMark();
		tradeMark.setApplicationLanguage("applicationLanguage");
		
		TMDetailsForm tMDetailsForm=new TMDetailsForm();
		
		objects.add(tradeMark);
		
		
		Mockito.when(tmFactory.convertTo(tMDetailsForm)).thenReturn(tradeMark);
		
	//	Mockito.when(legalActVersionFactory.convertFrom((LegalActVersion)objects1)).thenReturn(legalActVersion);
		
		Mockito.when(businessRulesService.calculate(Mockito.anyString(), Mockito.anyString(), Mockito.anyList())).thenReturn(value1);
		 //Mockito.when( configurationServiceDelegator.getObjectFromGeneralComponent(ConfigurationServiceDelegatorInterface.LEGAL_ACT_VERSIONS_PARAM , LegalActVersions.class).getLegalActVersion());
		
	//	configurationServiceDelegator
		
	//	Mockito.mock(GroundCategoryKindLegalAct.class);
		
		codesFiltered.add("dssd");
		
		eSTrademarkService.getCoreObjects(applicationType, codesFiltered,
				filteredCore, filteredCommon);
		
		
		

		eSTrademarkService.filter(applicationType, tMDetailsForm,
				GroundCategoryKindLegalAct.ABSOLUTE_GROUNDS, codesFiltered);

	}

	@Test
	public void getCoreObjects() {

		String applicationType = "";
		List<String> codesFiltered = new ArrayList<String>();
		List<LegalActVersion> filteredCore = new ArrayList<LegalActVersion>();
		List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion> filteredCommon = new ArrayList<eu.ohim.sp.common.ui.form.opposition.LegalActVersion>();

		ConfigurationServiceDelegatorInterface configurationServiceDelegatorInterface = Mockito
				.mock(ConfigurationServiceDelegatorInterface.class);

		configurationServiceDelegatorInterface
				.getObjectFromGeneralComponent(
						ConfigurationServiceDelegatorInterface.LEGAL_ACT_VERSIONS_PARAM,
						LegalActVersions.class);

		// configurationServiceDelegatorInterface

		// , List<LegalActVersion> filteredCore,
		// List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion>
		// filteredCommon
		
		LegalActVersions legalActVersions	=new LegalActVersions();
		
		LegalActVersions.LegalActVersion legalActVersion	=new LegalActVersions.LegalActVersion();
		
		List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion> legalActVersion1=new ArrayList<eu.ohim.sp.common.ui.form.opposition.LegalActVersion>();
		
		legalActVersions.getLegalActVersion().add(new LegalActVersions.LegalActVersion());
		
		legalActVersion.setCode("ded");
		
		legalActVersion.setEnabled(true);
		
		legalActVersions.getLegalActVersion().set(0, legalActVersion);
		
	
	
		
		Mockito.when( configurationServiceDelegator.getObjectFromGeneralComponent(ConfigurationServiceDelegatorInterface.LEGAL_ACT_VERSIONS_PARAM , LegalActVersions.class)).thenReturn(legalActVersions);

		codesFiltered.add("ded");
		
		
		eu.ohim.sp.common.ui.form.opposition.LegalActVersion m=	new eu.ohim.sp.common.ui.form.opposition.LegalActVersion();
		
		m.setCode("ded");
		
		filteredCommon.add(0, m);
		
		
		Mockito.when(legalActVersionFactory.convertToLegalActUI(legalActVersion, applicationType)).thenReturn(m);
		
		
		eSTrademarkService.getCoreObjects(applicationType, codesFiltered,
				filteredCore, filteredCommon);

	}

	@Test
	public void validateOpposition2() {

		String module = "";

		//OppositionBasisForm oppositionBasisForm = new OppositionBasisForm();



	//	Map<String, Sections> viewConfiguration;

		// viewConfiguration=new HashMap();
		//
		// viewConfiguration.put("1", new Sections());
		//
		// sectionViewConfiguration =new SectionViewConfiguration();
		//
		// sectionViewConfiguration.setViewConfiguration(viewConfiguration);
		//
		// sectionViewConfiguration.getViewConfiguration();
		//
		String flowModeId = "56";
		Map<String, Sections> value=new HashMap<String,Sections>();
		
		value.put(flowModeId, new Sections());

		
		Mockito.when(sectionViewConfiguration.getViewConfiguration()).thenReturn(value);
	
	
		
		//Sections sections = new Sections();

		//sectionViewConfiguration.getViewConfiguration().get(flowModeId);
		

		rulesInformation = Mockito.mock(RulesInformation.class);
		errors = Mockito.mock(Errors.class);
	
		OppositionAbsoluteGrounds oppositionGround=new OppositionAbsoluteGrounds();
		
		
		
	//	oppositionBasisForm.setGroundCategory(grounategory);
		
		
		GroundCategoryKind grounategory= GroundCategoryKind.ABSOLUTE_GROUNDS;
		
		
		OppositionBasisForm oppositionBasisForm=new OppositionBasisForm();
		
		
		oppositionBasisForm.setGroundCategory(grounategory);
		
		Mockito.when(oppositionGroundFactory.convertFrom(oppositionGround)).thenReturn(oppositionBasisForm);

		eSTrademarkService.validateOpposition(module, oppositionBasisForm,
				rulesInformation, errors, flowModeId);
		
	 grounategory= GroundCategoryKind.RELATIVE_GROUNDS;
		
		oppositionBasisForm.setGroundCategory(grounategory);
		
		eSTrademarkService.validateOpposition(module, oppositionBasisForm,
				rulesInformation, errors, flowModeId);
		
		
		 grounategory= GroundCategoryKind.REVOCATION_GROUNDS;
			
			oppositionBasisForm.setGroundCategory(grounategory);
			
			eSTrademarkService.validateOpposition(module, oppositionBasisForm,
					rulesInformation, errors, flowModeId);

	}

	@Test
	public void getTradeMarkAutocomplete2() {

		String office = "dfdf";
		String text = "dfdf";
		Integer numberOfResults = 2;
		boolean previousCTM = true;

		tradeMarkService = Mockito.mock(TradeMarkSearchService.class);

		eSTrademarkService.getTradeMarkAutocomplete(office, text,
				numberOfResults, previousCTM, "tm-renewal");

	}

	@Test
	public void getTradeMark2() {

		String tradeMarkId = "1";
		
		Mockito.when(applicationService.provideProvisionalID()).thenReturn("draftAppID");
	
		Mockito.when(tradeMarkService.getTradeMark("${office.instance}", tradeMarkId)).thenReturn(new TradeMark());

		applicationService = Mockito.mock(ESApplicationServiceInterface.class);
		
		TradeMark tradeMark	=	new TradeMark();
		tradeMark.setApplicationLanguage("applicationLanguage");
		
		TMDetailsForm tMDetailsForm=new TMDetailsForm();
		
		
		
		Mockito.when(tmFactory.convertFrom(tradeMark)).thenReturn(tMDetailsForm);

		eSTrademarkService.getTradeMark(tradeMarkId);

	}

}
