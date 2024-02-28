package eu.ohim.sp.eservices.ui.domainTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eu.ohim.sp.eservices.ui.util.GroundsUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TermForm;
import eu.ohim.sp.common.ui.form.design.LocarnoClassification;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKindLegalAct;
import eu.ohim.sp.common.ui.form.opposition.LegalActVersion;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.form.validation.ErrorType;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.AbsoluteGrounds;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.AbsoluteGrounds.AbsoluteGround;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RelativeGrounds;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RelativeGrounds.RelativeGround;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RevocationGrounds;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RevocationGrounds.RevocationGround;
import eu.ohim.sp.core.configuration.domain.legalActVersion.xsd.LegalActVersions;
import eu.ohim.sp.eservices.ui.domain.ESFlowBeanImpl;
import eu.ohim.sp.eservices.ui.service.interfaces.ESTrademarkServiceInterface;

public class ESFlowBeanImplTest {
	
	@InjectMocks
	ESFlowBeanImpl eSFlowBean = new ESFlowBeanImpl();

	@Mock
	GroundsUtil groundsUtil;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void clears(){
		eSFlowBean.clearDesignRepresentationAttachment();
		eSFlowBean.clearGandS();
	}
	
	@Test
	public void simpleGetandsets(){
		eSFlowBean.setOtherAttachments(Mockito.mock(FileWrapper.class));
		eSFlowBean.getOtherAttachments();
		eSFlowBean.getTmsList();
		eSFlowBean.setDssList(null);
		eSFlowBean.getDssList();
		eSFlowBean.getComment();
		eSFlowBean.setGoodsServices(null);
		eSFlowBean.getGoodsServices();
		eSFlowBean.setOriginalGS(null);
		eSFlowBean.getOriginalGS();
		eSFlowBean.setOwners(null);
		eSFlowBean.getOwners();
		eSFlowBean.getFirstLang();
		eSFlowBean.setOpposedTradeMark(null);
		eSFlowBean.getOpposedTradeMark();
		eSFlowBean.setOppositionBasis(null);
		eSFlowBean.getOppositionBasis();
		eSFlowBean.addReputationCountry("aa");
		eSFlowBean.removeReputationCountry("aa");
		eSFlowBean.setReputationCountries(null);
		eSFlowBean.getReputationCountries();
		eSFlowBean.setTmosList(null);
		eSFlowBean.getTmosList();
		eSFlowBean.setUserApplicantsForm(null);
		eSFlowBean.getUserApplicantsForm();
		eSFlowBean.setLocarnoClassifications(new HashMap<String, LocarnoClassification>());
		eSFlowBean.getLocarnoClassifications();
		eSFlowBean.getLocarnoClassificationsCollection();
		eSFlowBean.setLocarnoSubclasses(null);
		eSFlowBean.getLocarnoSubclasses();
		eSFlowBean.setDesignRepresentationAttachment(null);
		eSFlowBean.getDesignRepresentationAttachment();
		eSFlowBean.setLocarnoClasses(null);
		eSFlowBean.getLocarnoClasses();
		eSFlowBean.setLocarno(null);
		eSFlowBean.getLocarno();
		eSFlowBean.setIdReservePayment(null);
		eSFlowBean.setSignatures(null);
		eSFlowBean.setReadOnly(true);
		eSFlowBean.isReadOnly();
	}
	
	@Test(expected=SPException.class)
	public void addGoodAndService(){
		GoodAndServiceForm gs=Mockito.mock(GoodAndServiceForm.class);
		eSFlowBean.setGoodsServices(null);
		eSFlowBean.addGoodAndService(gs);
	}
	
	@Test
	public void getNotValidatedTerms(){
		TermForm term=Mockito.mock(TermForm.class);
		Mockito.when(term.getError()).thenReturn(Mockito.mock(ErrorType.class));
		Set<TermForm> setterms=new HashSet<TermForm>();
		setterms.add(term);
		GoodAndServiceForm goodAndServiceForm=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(goodAndServiceForm.getTermForms()).thenReturn(setterms);
		eSFlowBean.addGoodAndService(goodAndServiceForm);
		Assert.assertEquals(Integer.valueOf(1), eSFlowBean.getNotValidatedTerms());
	}
	
	@Test
	public void getGoodsAndServices(){
		//first execution void
		eSFlowBean.getGoodsAndServices();
		
		//configuration variables
		eSFlowBean.setFirstLang("EN");
		eSFlowBean.setSecLang("ES");
		eSFlowBean.getMainForm().setSecondLanguageTranslation(true);
		
		//iterations
		eSFlowBean.addGoodAndService(Mockito.mock(GoodAndServiceForm.class));
		
		GoodAndServiceForm gs1=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(gs1.getLangId()).thenReturn("EN");
		eSFlowBean.addGoodAndService(gs1);
		
		TermForm term=Mockito.mock(TermForm.class);
		Mockito.when(term.getError()).thenReturn(Mockito.mock(ErrorType.class));
		Set<TermForm> setterms=new HashSet<TermForm>();
		setterms.add(term);
		GoodAndServiceForm gs2=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(gs2.getLangId()).thenReturn("EN");
		Mockito.when(gs2.getClassId()).thenReturn("ID");
		Mockito.when(gs2.getTermForms()).thenReturn(setterms);
		eSFlowBean.addGoodAndService(gs2);
		
		GoodAndServiceForm gs3=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(gs3.getLangId()).thenReturn("DE");
		eSFlowBean.addGoodAndService(gs3);
		
		GoodAndServiceForm gs4=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(gs4.getLangId()).thenReturn("ES");
		Mockito.when(gs4.getClassId()).thenReturn("ID");
		eSFlowBean.addGoodAndService(gs4);
		
		
		//second execution with iterator
		Assert.assertNotNull(eSFlowBean.getGoodsAndServices());
	}
	
	@Test
	public void getGoodsAndService(){
		String langId="langId";
		String classId="classId";
		eSFlowBean.getGoodsAndService(langId, classId);
	
		eSFlowBean.addGoodAndService(Mockito.mock(GoodAndServiceForm.class));
		eSFlowBean.addGoodAndService(new GoodAndServiceForm(langId, classId));
		Assert.assertNotNull(eSFlowBean.getGoodsAndService(langId, classId));
	}
	
	@Test
	public void getApplicant() {
		String name="name";
		String domicile="domicile";
		eSFlowBean.setOwners(new ArrayList<ApplicantForm>());
		eSFlowBean.getApplicant(name, domicile);
        
		eSFlowBean.getOwners().add(new ApplicantForm("", ""));
		eSFlowBean.getOwners().add(new ApplicantForm(name, ""));
		eSFlowBean.getOwners().add(new ApplicantForm(name, domicile));
		Assert.assertNotNull(eSFlowBean.getApplicant(name, domicile));
    }
	
	@Test
	public void getCounts(){
		eSFlowBean.setAvaibleLegalActVersions(new ArrayList<LegalActVersion>());
		LegalActVersion lav1=Mockito.mock(LegalActVersion.class);
		Mockito.when(lav1.getGroundCategory()).thenReturn(GroundCategoryKindLegalAct.ABSOLUTE_GROUNDS);
		eSFlowBean.getAvaibleLegalActVersions().add(lav1);
		LegalActVersion lav2=Mockito.mock(LegalActVersion.class);
		Mockito.when(lav2.getGroundCategory()).thenReturn(GroundCategoryKindLegalAct.RELATIVE_GROUNDS);
		eSFlowBean.getAvaibleLegalActVersions().add(lav2);
		LegalActVersion lav3=Mockito.mock(LegalActVersion.class);
		Mockito.when(lav3.getGroundCategory()).thenReturn(GroundCategoryKindLegalAct.BOTH);
		eSFlowBean.getAvaibleLegalActVersions().add(lav3);
		LegalActVersion lav4=Mockito.mock(LegalActVersion.class);
		Mockito.when(lav4.getGroundCategory()).thenReturn(GroundCategoryKindLegalAct.REVOCATION_GROUNDS);
		eSFlowBean.getAvaibleLegalActVersions().add(lav4);
		
		Assert.assertEquals(2, eSFlowBean.getAbsoluteGroundsCount());
		Assert.assertEquals(2, eSFlowBean.getRelativeGroundsCount());
		Assert.assertEquals(1, eSFlowBean.getRevocationGroundsCount());
	}
	
	@Test
	public void testExists(){
		//isExistsAvaibleLegalActVersions
		eSFlowBean.isExistsAvaibleLegalActVersions();
		eSFlowBean.setAvaibleLegalActVersions(new ArrayList<LegalActVersion>());
		eSFlowBean.getAvaibleLegalActVersions().add(null);
		Assert.assertEquals(true, eSFlowBean.isExistsAvaibleLegalActVersions());
		
		//isExistsNiceClass
		eSFlowBean.isExistsNiceClass(null, null);
		GoodAndServiceForm gasf1=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(gasf1.getClassId()).thenReturn("");
		GoodAndServiceForm gasf2=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(gasf2.getClassId()).thenReturn("ID");
		Set<GoodAndServiceForm> set=new HashSet<GoodAndServiceForm>();
		set.add(gasf1);set.add(gasf2);
		Assert.assertEquals(true, eSFlowBean.isExistsNiceClass("ID", set));
		
		//isExistsTerm
		eSFlowBean.isExistsTerm(null, null, null);
		TermForm term=Mockito.mock(TermForm.class);
		Mockito.when(term.getDescription()).thenReturn("desc");
		Set<TermForm> setterms=new HashSet<TermForm>();
		setterms.add(term);
		Mockito.when(gasf2.getTermForms()).thenReturn(setterms);
		Assert.assertEquals(true, eSFlowBean.isExistsTerm("ID", "desc", set));
		
		//isContainsRemovedTerm
		eSFlowBean.isContainsRemovedTerm(null, null, null);
		Set<TermForm> setterms2=new HashSet<TermForm>();
		setterms2.add(Mockito.mock(TermForm.class));setterms2.add(Mockito.mock(TermForm.class));
		GoodAndServiceForm gasf3=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(gasf3.getClassId()).thenReturn("ID");
		Mockito.when(gasf3.getTermForms()).thenReturn(setterms2);
		Set<GoodAndServiceForm> originalGS=new HashSet<GoodAndServiceForm>();
		originalGS.add(gasf3);
		Assert.assertEquals(true, eSFlowBean.isContainsRemovedTerm("ID", originalGS, set));
	}
	
	
	@Test
	public void refreshAvaibleLegalActVersions() {

		TMDetailsForm tm = new TMDetailsForm();
		String applicationType = "dedede";
		ConfigurationServiceDelegatorInterface configurationService = Mockito.mock(ConfigurationServiceDelegatorInterface.class);
		ESTrademarkServiceInterface trademarkServiceInterface = Mockito.mock(ESTrademarkServiceInterface.class);

		Mockito.when( configurationService.getObjectFromGeneralComponent(ConfigurationServiceDelegatorInterface.LEGAL_ACT_VERSIONS_PARAM , LegalActVersions.class)).thenReturn(new LegalActVersions());
		
		AbsoluteGrounds absoluteGrounds	= new  AbsoluteGrounds();
		AbsoluteGround absoluteGround=new AbsoluteGround();
		absoluteGround.setApplicationType("dedede");
		absoluteGround.setEnabled(true);
		absoluteGrounds.getAbsoluteGround().add(absoluteGround);
		
		RelativeGrounds relativeGrounds	= new  RelativeGrounds();
		RelativeGround relativeGround=new RelativeGround();		
		relativeGround.setApplicationType("dedede");
		relativeGround.setEnabled(true);		
		relativeGrounds.getRelativeGround().add(relativeGround);

		RevocationGrounds revocationGrounds	= new  RevocationGrounds();
		RevocationGround revocationGround=new RevocationGround();		
		revocationGround.setApplicationType("dedede");
		revocationGround.setEnabled(true);		
		revocationGrounds.getRevocationGround().add(revocationGround);

		Mockito.when(configurationService.getObjectFromGeneralComponent(ConfigurationServiceDelegatorInterface.ABSOLUTE_GROUNDS_PARAM ,  AbsoluteGrounds.class)).thenReturn( absoluteGrounds);
		Mockito.when(configurationService.getObjectFromGeneralComponent(ConfigurationServiceDelegatorInterface.RELATIVE_GROUNDS_PARAM ,  RelativeGrounds.class)).thenReturn( relativeGrounds);

		eSFlowBean.setAvaibleLegalActVersions(groundsUtil.getAvaibleLegalActVersions(tm, applicationType));
		
		
		
		Mockito.when( configurationService.getObjectFromGeneralComponent(ConfigurationServiceDelegatorInterface.REVOCATION_GROUNDS_PARAM, RevocationGrounds.class)).thenReturn( revocationGrounds);
		applicationType="tm-revocation";

		eSFlowBean.setAvaibleLegalActVersions(groundsUtil.getAvaibleLegalActVersions(tm, applicationType));

	}

	@Test
	public void addGoodAndService2() {

		GoodAndServiceForm gs = new GoodAndServiceForm();

		eSFlowBean.addGoodAndService(gs);

		eSFlowBean.addGoodAndService(gs);

	}

	@Mock
	GoodAndServiceForm goods;
	@Mock
	TermForm termForm;

	@Test
	public void getNotValidatedTerms2() {

		goods.addTermForm(termForm);

		eSFlowBean.getNotValidatedTerms();

	}

	@Test
	public void isNonUsePeriod() {

		List<TMDetailsForm> tmsList =new ArrayList<TMDetailsForm>();
		TMDetailsForm tMDetailsForm=new TMDetailsForm();
		tMDetailsForm.setApplicationStatus("applicationStatus");
		tMDetailsForm.setRegistrationDate(new Date());
		tmsList.add(tMDetailsForm);
		
		List<TMDetailsForm> tmsList1=new ArrayList<TMDetailsForm>();
		tmsList1.add(tMDetailsForm);
		tmsList1.add(tMDetailsForm);
		tMDetailsForm.setApplicant("applicant");
		tMDetailsForm.setApplicationDate(new Date());
		tMDetailsForm.setApplicationRepresentation("applicationRepresentation");
		tMDetailsForm.setId("id");
		tmsList.set(0, tMDetailsForm);
			
		eSFlowBean.setTmsList(tmsList1);
		eSFlowBean.isNonUsePeriod(0);
	}
	
	
	@Test
	public void refreshOppositionBasisList(){
		
		List<OppositionBasisForm> obs=new ArrayList<OppositionBasisForm>();
		OppositionBasisForm obsList=new OppositionBasisForm();
		obs.add(obsList);
		eSFlowBean.setObsList(obs);
        groundsUtil.refreshOppositionBasisList(eSFlowBean);
	}
	
	
	@Test
	public void addGoodAndServiceBranches(){
		Set<GoodAndServiceForm> goodsServices=new HashSet<GoodAndServiceForm>();
		GoodAndServiceForm gs=Mockito.mock(GoodAndServiceForm.class);
		Set<TermForm> value=new HashSet<TermForm>();
		value.add(new TermForm());
		Mockito.when(gs.getTermForms()).thenReturn(value);
		goodsServices.add(gs);
		goodsServices.add(Mockito.mock(GoodAndServiceForm.class));
		eSFlowBean.setGoodsServices(goodsServices);
		
		
		eSFlowBean.addGoodAndService(gs);
	}
	
	@Test
	public void getNotValidatedTermsBranches(){
		Set<GoodAndServiceForm> goodsServices=new HashSet<GoodAndServiceForm>();
		GoodAndServiceForm gs=Mockito.mock(GoodAndServiceForm.class);
		Set<TermForm> value=new HashSet<TermForm>();
		value.add(new TermForm());
		Mockito.when(gs.getTermForms()).thenReturn(value);
		goodsServices.add(gs);
		eSFlowBean.setGoodsServices(goodsServices);
		
		eSFlowBean.getNotValidatedTerms();
	}
	
	@Test
	public void fixGoodsAndServices(){
		
		//configuration variables
		eSFlowBean.setFirstLang("EN");
		eSFlowBean.setSecLang("ES");
		eSFlowBean.getMainForm().setSecondLanguageTranslation(false);
		
		TermForm term=Mockito.mock(TermForm.class);
		Mockito.when(term.getError()).thenReturn(Mockito.mock(ErrorType.class));
		Set<TermForm> setterms=new HashSet<TermForm>();
		setterms.add(term);
		GoodAndServiceForm gs2=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(gs2.getLangId()).thenReturn("EN");
		Mockito.when(gs2.getClassId()).thenReturn("ID");
		Mockito.when(gs2.getTermForms()).thenReturn(setterms);
		eSFlowBean.addGoodAndService(gs2);

		Assert.assertNotNull(eSFlowBean.getGoodsAndServices());
		
		eSFlowBean.getMainForm().setSecondLanguageTranslation(true);
		eSFlowBean.setSecLang(null);
		Assert.assertNotNull(eSFlowBean.getGoodsAndServices());
		
		eSFlowBean.getMainForm().setSecondLanguageTranslation(null);
		eSFlowBean.setSecLang("ES");
		Assert.assertNotNull(eSFlowBean.getGoodsAndServices());
	}
	
	@Test
	public void fixGoodsAndServices2(){
		
		//configuration variables
		eSFlowBean.setFirstLang("EN");
		eSFlowBean.setSecLang(null);
		eSFlowBean.getMainForm().setSecondLanguageTranslation(false);		
		TermForm term=Mockito.mock(TermForm.class);
		Mockito.when(term.getError()).thenReturn(Mockito.mock(ErrorType.class));
		Set<TermForm> setterms=new HashSet<TermForm>();
		setterms.add(term);
		GoodAndServiceForm gs2=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(gs2.getLangId()).thenReturn("ES");
		Mockito.when(gs2.getClassId()).thenReturn("ID");
		Mockito.when(gs2.getTermForms()).thenReturn(setterms);
		eSFlowBean.addGoodAndService(gs2);
		Assert.assertNotNull(eSFlowBean.getGoodsAndServices());
		
		eSFlowBean.setSecLang("ES");
		eSFlowBean.getMainForm().setSecondLanguageTranslation(null);
		eSFlowBean.addGoodAndService(gs2);
		Assert.assertNotNull(eSFlowBean.getGoodsAndServices());
		
		eSFlowBean.setSecLang("ES");
		eSFlowBean.getMainForm().setSecondLanguageTranslation(false);
		eSFlowBean.addGoodAndService(gs2);
		Assert.assertNotNull(eSFlowBean.getGoodsAndServices());
	}
	
	@Test
	public void isNonUsePeriodBranches() {
		eSFlowBean.setTmsList(null);
		eSFlowBean.isNonUsePeriod(0);
		
		eSFlowBean.setTmsList(new ArrayList<TMDetailsForm>());
		eSFlowBean.isNonUsePeriod(0);
	}
}
