package eu.ohim.sp.eservices.ui.validatorTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.webflow.execution.RequestContextHolder;

import eu.ohim.sp.common.ui.adapter.DocumentFactory;
import eu.ohim.sp.common.ui.adapter.ErrorFactory;
import eu.ohim.sp.common.ui.adapter.FlowBeanFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.design.ESDesignApplicationDataForm;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.opposition.EarlierEntitlementRightDetails;
import eu.ohim.sp.common.ui.form.opposition.EarlierEntitlementRightInf;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKind;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.opposition.RelativeGrounds;
import eu.ohim.sp.common.ui.form.person.AssigneeLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.AssigneeNaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.HolderLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.HolderNaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeNaturalPersonForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.PersonService;
import eu.ohim.sp.common.ui.service.interfaces.ApplicationServiceInterface;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.domain.application.ApplicationKind;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.eservices.ui.domain.ESFlowBeanImpl;
import eu.ohim.sp.eservices.ui.service.ESDesignService;
import eu.ohim.sp.eservices.ui.service.interfaces.ESApplicationServiceInterface;
import eu.ohim.sp.eservices.ui.service.interfaces.ESTrademarkServiceInterface;
import eu.ohim.sp.eservices.ui.validator.ESFormValidator;

public class ESFormValidatorTest {
	
	
	
	@Mock
	private FlowBeanFactory flowBeanFactory;
		
	@Mock
	private DocumentFactory documentFactory;

	@Mock
	private PersonService personService;
	
	@Mock
	private ESDesignService designService;
	
	@Mock
	private ESTrademarkServiceInterface tradeMarkService;	
	
	@Mock
	private ESApplicationServiceInterface applicationService;
	
	@Mock
    private ErrorFactory errorFactory;
    
	@Mock
	private ConfigurationServiceDelegatorInterface configurationService;    
	
	@Mock
	private UIFactory factory;
	
	@InjectMocks
	ESFormValidator eSFormValidator =new ESFormValidator();
	
	@Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }
	
	
	Errors errors=Mockito.mock(Errors.class);
	FlowScopeDetails flowScopeDetails=new FlowScopeDetails();


	@Test
	public void supports(){
		eSFormValidator.supports(null);
	}
	
	@Test
	public void validationString(){
		eSFormValidator.validate("target", errors);
	}

	@Test
	public void validationESFlowBeanImpl(){
		ESFlowBeanImpl target=Mockito.mock(ESFlowBeanImpl.class);
		Mockito.when(applicationService.validateApplication(Mockito.any(ESFlowBeanImpl.class), Mockito.any(RulesInformation.class), Mockito.anyString())).thenReturn(Mockito.mock(ErrorList.class));
		eSFormValidator.validate(target, errors);
	}
	
	@Test
	public void validationESDesignDetailsForm(){
		ESDesignDetailsForm target=Mockito.mock(ESDesignDetailsForm.class);
		Mockito.when(designService.validateDesign(Mockito.anyString(),Mockito.any(ESDesignDetailsForm.class), Mockito.any(RulesInformation.class),Mockito.any(Errors.class), Mockito.anyString())).thenReturn(Mockito.mock(ErrorList.class));
		Mockito.when(configurationService.isServiceEnabled(Mockito.anyString())).thenReturn(true);
		eSFormValidator.validate(target, errors);
		ESDesignApplicationDataForm eSDesignApplicationDataForm=Mockito.mock(ESDesignApplicationDataForm.class);
		Mockito.when(eSDesignApplicationDataForm.getApplicationNumber()).thenReturn("AppN");
		Mockito.when(target.geteSDesignApplicationData()).thenReturn(eSDesignApplicationDataForm);
		eSFormValidator.validate(target, errors);
	}
	
	@Test
	public void validationESDesignDetailsForm2(){
		ESDesignDetailsForm target=Mockito.mock(ESDesignDetailsForm.class);
		Mockito.when(designService.validateDesign(Mockito.anyString(),Mockito.any(ESDesignDetailsForm.class), Mockito.any(RulesInformation.class),Mockito.any(Errors.class), Mockito.anyString())).thenReturn(Mockito.mock(ErrorList.class));
		eSFormValidator.validate(target, errors);
	}
	
	@Test
	public void validationSignatureForm(){
		SignatureForm target=Mockito.mock(SignatureForm.class);
		Mockito.when(personService.validateSignature(Mockito.anyString(),Mockito.any(SignatureForm.class), Mockito.any(RulesInformation.class),Mockito.any(Errors.class), Mockito.anyString())).thenReturn(Mockito.mock(ErrorList.class));
		eSFormValidator.validate(target, errors);
	}
	
	@Test
	public void validationTMDetailsForm(){
		TMDetailsForm target=Mockito.mock(TMDetailsForm.class);
		Mockito.when(configurationService.isServiceEnabled(Mockito.anyString())).thenReturn(true);
		Mockito.when(applicationService.checkExistingApplication(Mockito.anyString(),Mockito.anyString(), Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		eSFormValidator.validate(target, errors);
		Mockito.when(configurationService.isServiceEnabled(Mockito.anyString())).thenReturn(false);
		eSFormValidator.validate(target, errors);
	}
	
	@Test
	public void validationOppositionBasisForm(){
		OppositionBasisForm target=Mockito.mock(OppositionBasisForm.class);
		Mockito.when(tradeMarkService.validateOpposition(Mockito.anyString(),Mockito.any(OppositionBasisForm.class), Mockito.any(RulesInformation.class),Mockito.any(Errors.class), Mockito.anyString())).thenReturn(Mockito.mock(ErrorList.class));
		Mockito.when(target.getGroundCategory()).thenReturn(GroundCategoryKind.RELATIVE_GROUNDS);
		EarlierEntitlementRightDetails earlierEntitlementRightDetails = Mockito.mock(EarlierEntitlementRightDetails.class);
		Mockito.when(earlierEntitlementRightDetails.getFilterCountriesEarlierTradeMark()).thenReturn(false);
		EarlierEntitlementRightInf  earlierEntitlementRightInf = Mockito.mock(EarlierEntitlementRightInf.class);
		Mockito.when(earlierEntitlementRightInf.getEarlierEntitlementRightDetails()).thenReturn(earlierEntitlementRightDetails);
		RelativeGrounds relativeGrounds=Mockito.mock(RelativeGrounds.class);
		Mockito.when(relativeGrounds.getEarlierEntitlementRightInf()).thenReturn(earlierEntitlementRightInf);
		Mockito.when(target.getRelativeGrounds()).thenReturn(relativeGrounds);
		eSFormValidator.validate(target, errors);
	}
	
	@Test
	public void validationOppositionBasisForm2(){
		OppositionBasisForm target=Mockito.mock(OppositionBasisForm.class);
		Mockito.when(tradeMarkService.validateOpposition(Mockito.anyString(),Mockito.any(OppositionBasisForm.class), Mockito.any(RulesInformation.class),Mockito.any(Errors.class), Mockito.anyString())).thenReturn(Mockito.mock(ErrorList.class));
		Mockito.when(target.getGroundCategory()).thenReturn(GroundCategoryKind.RELATIVE_GROUNDS);
		EarlierEntitlementRightDetails earlierEntitlementRightDetails = Mockito.mock(EarlierEntitlementRightDetails.class);
		Mockito.when(earlierEntitlementRightDetails.getFilterCountriesEarlierTradeMark()).thenReturn(true);
		EarlierEntitlementRightInf  earlierEntitlementRightInf = Mockito.mock(EarlierEntitlementRightInf.class);
		Mockito.when(earlierEntitlementRightInf.getEarlierEntitlementRightDetails()).thenReturn(earlierEntitlementRightDetails);
		RelativeGrounds relativeGrounds=Mockito.mock(RelativeGrounds.class);
		Mockito.when(relativeGrounds.getEarlierEntitlementRightInf()).thenReturn(earlierEntitlementRightInf);
		Mockito.when(target.getRelativeGrounds()).thenReturn(relativeGrounds);
		eSFormValidator.validate(target, errors);
		
		Mockito.when(target.getGroundCategory()).thenReturn(GroundCategoryKind.ABSOLUTE_GROUNDS);
		eSFormValidator.validate(target, errors);
	}
	
	
	@Test
	public void validateAssigneeNaturalPersonForm(){
		
		
		AssigneeNaturalPersonForm target=new AssigneeNaturalPersonForm();
	//	RequestContextHolder requestContextHolder;
		
//		Mockito.when(classificationService.getTermDistribution(Mockito.any(DistributionCriteria.class))).
//		thenReturn(new DistributionResults());
//	
//		List<Integer> list = goodsServicesService.getDistribution(language,  term);
//		Assert.assertEquals(0, list.size());
//	
		
		//Mockito.when(requestContextHolder.
		
		
		
		Mockito.when(flowBeanFactory.getFactory(target.getClass())).thenReturn( Mockito.mock(UIFactory.class));
	

		factory=Mockito.mock(UIFactory.class);
		
		
		eSFormValidator.validate(target, errors, flowScopeDetails);
		
	}
	
	@Test
	public void validateOppositionBasisForm(){
		
		
		OppositionBasisForm target=new OppositionBasisForm();
		Mockito.when(flowBeanFactory.getFactory(target.getClass())).thenReturn( Mockito.mock(UIFactory.class));
		
		
		//Mockito.when(oppositionBasisForm.getGroundCategory().equals(null)).
		
		//oppositionBasisForm.setGroundCategory("Relative Grounds");
		
		//GroundCategory es un enumerado y no se puede instanciar
		
		//Mockito.when(oppositionBasisForm.getGroundCategory().equals(null)).thenReturn(true);
		
		
			
		//eSFormValidator.validate(target, errors, flowScopeDetails);
		
	}
	
		
	@Test
	public void validateNaturalPersonForm(){
		
		
		NaturalPersonForm target=new NaturalPersonForm();
		Mockito.when(flowBeanFactory.getFactory(target.getClass())).thenReturn( Mockito.mock(UIFactory.class));
		
			
		eSFormValidator.validate(target, errors, flowScopeDetails);
		
	}
	
	
	
	@Test
	public void validateRepresentativeNaturalPersonForm(){
		
		
		RepresentativeNaturalPersonForm target=new RepresentativeNaturalPersonForm();
		Mockito.when(flowBeanFactory.getFactory(target.getClass())).thenReturn( Mockito.mock(UIFactory.class));
		
			
		eSFormValidator.validate(target, errors, flowScopeDetails);
		
	}
	
		
	@Test
	public void validateHolderNaturalPersonForm(){
		
		
		HolderNaturalPersonForm target=new HolderNaturalPersonForm();
		Mockito.when(flowBeanFactory.getFactory(target.getClass())).thenReturn( Mockito.mock(UIFactory.class));
		
			
		eSFormValidator.validate(target, errors, flowScopeDetails);
		
	}
	
	@Test
	public void validateLegalEntityForm(){
		
		
		LegalEntityForm target=new LegalEntityForm();
		Mockito.when(flowBeanFactory.getFactory(target.getClass())).thenReturn( Mockito.mock(UIFactory.class));
		
			
		eSFormValidator.validate(target, errors, flowScopeDetails);
		
	}
	
	
	
	@Test
	public void validateHolderLegalEntityForm(){
		
		
		HolderLegalEntityForm target=new HolderLegalEntityForm();
		Mockito.when(flowBeanFactory.getFactory(target.getClass())).thenReturn( Mockito.mock(UIFactory.class));
		
			
		eSFormValidator.validate(target, errors, flowScopeDetails);
		
	}
	

	@Test
	public void validateAssigneeLegalEntityForm(){
		
		
		AssigneeLegalEntityForm target=new AssigneeLegalEntityForm();
		Mockito.when(flowBeanFactory.getFactory(target.getClass())).thenReturn( Mockito.mock(UIFactory.class));
		
			
		eSFormValidator.validate(target, errors, flowScopeDetails);
		
	}
	
	
	
	
	@Test
	public void validateRepresentativeLegalEntityForm(){
		
		
		RepresentativeLegalEntityForm target=new RepresentativeLegalEntityForm();
		Mockito.when(flowBeanFactory.getFactory(target.getClass())).thenReturn( Mockito.mock(UIFactory.class));
		
			
		eSFormValidator.validate(target, errors, flowScopeDetails);
		
	}
	
	@Test
	public void validateESFlowBeanImpl(){
		
		
		ESFlowBeanImpl target=new ESFlowBeanImpl();
		Mockito.when(flowBeanFactory.getFactory(target.getClass())).thenReturn( Mockito.mock(UIFactory.class));
		
			
		eSFormValidator.validate(target, errors, flowScopeDetails);
		
	}
	
	@Test
	public void validateTMDetailsForm(){
		
		
		TMDetailsForm target=new TMDetailsForm();
		Mockito.when(flowBeanFactory.getFactory(target.getClass())).thenReturn( Mockito.mock(UIFactory.class));
		
			
		eSFormValidator.validate(target, errors, flowScopeDetails);
		
	}
}
