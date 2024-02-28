package eu.ohim.sp.ui.tmefiling.validator;

import eu.ohim.sp.common.ui.adapter.DocumentFactory;
import eu.ohim.sp.common.ui.adapter.ErrorFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.claim.*;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.common.ui.validator.Validatable;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.claim.*;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.validation.ErrorCore;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.register.IPApplicationService;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.service.interfaces.ApplicationServiceInterface;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;
import org.springframework.webflow.definition.FlowDefinition;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.RequestContextHolder;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FormValidatorTest {

    @Mock
    ApplicationServiceInterface applicationService;

    @Mock
    private FlowBeanFactory flowBeanFactory;

    @Mock
    private PersonServiceInterface personService;

    @Mock
    private ErrorFactory errorFactory;

    @Mock
    private DocumentFactory documentFactory;

    @Mock
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Mock
    private IPApplicationService ipApplicationService;

    @Mock
    private DocumentService documentService;

    @InjectMocks
	private FormValidator formValidator;

    private Errors errors;
    private FlowScopeDetails flowScopeDetails;
    private ErrorList errorList;


    @Before
    public void setup() throws Exception {

        errors = Mockito.mock(Errors.class);
        flowScopeDetails = Mockito.mock(FlowScopeDetails.class);
        errorList = Mockito.mock(ErrorList.class);
        ErrorCore errorCore = Mockito.mock(ErrorCore.class);

        Mockito.when(errorList.getErrorList()).thenReturn(Arrays.asList(errorCore));
    }


    @Test
    public void supportsTest() {

        boolean result = formValidator.supports(Validatable.class);

        Assert.assertTrue(result);
    }

    @Test
    public void validateWithoutFlowScopeDetailsTestWhenNotValidatable() {

        Object target = Mockito.mock(Object.class);

        formValidator.validate(target, errors);

        verify(errorFactory, never()).coreErrorToUIError(Mockito.any(ErrorList.class), Mockito.any(Errors.class));

    }

    @Test
    public void validateWithoutFlowScopeDetailsTestWhenFlowBeanImpl() {

        FlowBeanImpl target = Mockito.mock(FlowBeanImpl.class);

        Mockito.when(applicationService.validateApplication(Mockito.any(FlowBeanImpl.class), Mockito.any(RulesInformation.class), Mockito.anyString(), Mockito.anyString())).thenReturn(errorList);

        Mockito.when(target.getAvailableSectionName()).thenReturn(AvailableSection.APPEAL);

        formValidator.validate(target, errors);

        verify(errorFactory, times(1)).coreErrorToUIError(Mockito.any(ErrorList.class), Mockito.any(Errors.class));

    }

    @Test
    public void validateTestWhenFlowBeanImpl() {

        FlowBeanImpl target = Mockito.mock(FlowBeanImpl.class);
        ApplicantForm applicantForm = Mockito.mock(ApplicantForm.class);
        RepresentativeForm representativeForm = Mockito.mock(RepresentativeForm.class);

        Mockito.when(applicationService.validateApplication(Mockito.any(FlowBeanImpl.class), Mockito.any(RulesInformation.class), Mockito.anyString(), Mockito.anyString())).thenReturn(errorList);

        Mockito.when(target.getApplicants()).thenReturn(Arrays.asList(applicantForm));
        Mockito.when(target.getRepresentatives()).thenReturn(Arrays.asList(representativeForm));

        testError(target);

    }

    @Test
    public void validateTestWhenStoredFile() {

        StoredFile target = Mockito.mock(StoredFile.class);
        RequestContext requestContext = Mockito.mock(RequestContext.class);
        FlowDefinition flowDefinition = Mockito.mock(FlowDefinition.class);
        StateDefinition stateDefinition = Mockito.mock(StateDefinition.class);
        Sections sections = Mockito.mock(Sections.class);

        Mockito.when(documentService.validateDocument(Mockito.anyString(), Mockito.any(Document.class), Mockito.any(RulesInformation.class))).thenReturn(errorList);
        Mockito.when(configurationServiceDelegator.getObjectFromComponent(Mockito.anyString(), Mockito.anyString(), Mockito.any(Class.class))).thenReturn(sections);

        Mockito.when(requestContext.getActiveFlow()).thenReturn(flowDefinition);
        Mockito.when(requestContext.getCurrentState()).thenReturn(stateDefinition);

        RequestContextHolder.setRequestContext(requestContext);

        testError(target);

    }

    @Test
    public void validateTestWhenPriorityForm() {

        Mockito.when(ipApplicationService.validatePriorityClaim(Mockito.anyString(), Mockito.any(IPPriority.class), Mockito.any(RulesInformation.class))).thenReturn(errorList);

        testClaim(Mockito.mock(PriorityForm.class));
    }

    @Test
    public void validateTestWhenExhPriorityForm() {

        Mockito.when(ipApplicationService.validateExhibitionPriorityClaim(Mockito.anyString(), Mockito.any(ExhibitionPriority.class), Mockito.any(RulesInformation.class))).thenReturn(errorList);

        testClaim(Mockito.mock(ExhPriorityForm.class));
    }

    @Test
    public void validateTestWhenTransformationForm() {

        Mockito.when(ipApplicationService.validateTransformation(Mockito.anyString(), Mockito.any(TransformationPriority.class), Mockito.any(RulesInformation.class))).thenReturn(errorList);

        testClaim(Mockito.mock(TransformationForm.class));
    }

    @Test
    public void validateTestWhenSeniorityForm() {

        Mockito.when(ipApplicationService.validateSeniorityClaim(Mockito.anyString(), Mockito.any(Seniority.class), Mockito.any(RulesInformation.class))).thenReturn(errorList);

        testClaim(Mockito.mock(SeniorityForm.class));
    }

    @Test
    public void validateTestWhenConversionForm() {

        Mockito.when(ipApplicationService.validateConversion(Mockito.anyString(), Mockito.any(ConversionPriority.class), Mockito.any(RulesInformation.class))).thenReturn(errorList);

        testClaim(Mockito.mock(ConversionForm.class));
    }

    @Test
    public void validateTestWhenApplicantLegalEntityForm() {
        testApplicant(Mockito.mock(LegalEntityForm.class));
    }

    @Test
    public void validateTestWhenApplicantNaturalPersonForm() {
        testApplicant(Mockito.mock(NaturalPersonForm.class));
    }

    @Test
    public void validateTestWhenApplicantNaturalPersonSpecialForm() {
        testApplicant(Mockito.mock(NaturalPersonSpecialForm.class));
    }

    @Test
    public void validateTestWhenRepresentativeAssociationForm() {
        testRepresentative(Mockito.mock(RepresentativeAssociationForm.class));
    }

    @Test
    public void validateTestWhenRepresentativeNaturalPersonForm() {
        testRepresentative(Mockito.mock(RepresentativeNaturalPersonForm.class));
    }

    @Test
    public void validateTestWhenRepresentativeLegalEntityForm() {
        testRepresentative(Mockito.mock(RepresentativeLegalEntityForm.class));
    }

    @Test
    public void validateTestWhenEmployeeRepresentativeForm() {
        testRepresentative(Mockito.mock(EmployeeRepresentativeForm.class));
    }

    @Test
    public void validateTestWhenProfessionalPractitionerForm() {
        testRepresentative(Mockito.mock(ProfessionalPractitionerForm.class));
    }

    @Test
    public void validateTestWhenLegalPractitionerForm() {
        testRepresentative(Mockito.mock(LegalPractitionerForm.class));
    }


    private void testError(Validatable target) {

        formValidator.validate(target, errors, flowScopeDetails);

        verify(errorFactory, times(1)).coreErrorToUIError(Mockito.any(ErrorList.class), Mockito.any(Errors.class));
    }

    private void testClaim(Validatable target) {

        UIFactory factory = Mockito.mock(UIFactory.class);

        Mockito.when(flowBeanFactory.getFactory(Mockito.any())).thenReturn(factory);

        testError(target);
    }

    private void testApplicant(Validatable target) {

        Mockito.when(personService.validateApplicant(Mockito.anyString(), Mockito.any(ApplicantForm.class), Mockito.any(RulesInformation.class), Mockito.any(Errors.class), Mockito.anyString())).thenReturn(errorList);

        testError(target);
    }

    private void testRepresentative(Validatable target) {

        Mockito.when(personService.validateRepresentative(Mockito.anyString(), Mockito.any(RepresentativeForm.class), Mockito.any(RulesInformation.class), Mockito.any(Errors.class), Mockito.anyString())).thenReturn(errorList);

        testError(target);
    }
}
