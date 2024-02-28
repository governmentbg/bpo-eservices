package eu.ohim.sp.dsefiling.ui.validator;



import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import eu.ohim.sp.common.ui.adapter.ErrorFactory;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.common.ui.form.design.LocarnoComplexForm;
import eu.ohim.sp.common.ui.form.design.LocarnoForm;
import eu.ohim.sp.common.ui.form.design.LocarnoSearchForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.common.ui.form.person.LegalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeNaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.UniversityForm;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;
import eu.ohim.sp.dsefiling.ui.service.DSDesignsService;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSApplicationServiceInterface;

public class DSFormValidatorTest
{
    FlowScopeDetails flow;

    @Mock
    private DSApplicationServiceInterface dsApplicationService;

    @Mock
    private PersonServiceInterface personService;

    @Mock
    private DSDesignsService designsService;

    @Mock
    private ErrorFactory errorFactory;

    @Mock
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;


    @InjectMocks
    DSFormValidator formValidator = new DSFormValidator();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        flow = new FlowScopeDetails();
    }


    @Test
    public void validateTest0()
    {
   	
        formValidator.validate(new DSFlowBeanImpl(), null);

        verify(dsApplicationService, times(1)).validateApplication(any(DSFlowBeanImpl.class), any(RulesInformation.class),
                                                                   eq(flow.getFlowModeId()), any(String.class));
    }

    @Test
    public void validateTest1()
    {
        formValidator.validate("test", null);
    }

    @Test
    public void validateTest2()
    {
        formValidator.validate(new DSPriorityForm(), null, flow);

        verify(designsService, times(1)).validatePriority(eq("dsefiling"), any(DSPriorityForm.class), any(RulesInformation.class),
                                                          any(Errors.class), eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest3()
    {
        formValidator.validate(new DSExhPriorityForm(), null, flow);

        verify(designsService, times(1)).validateExhPriority(eq("dsefiling"), any(DSExhPriorityForm.class),
                                                             any(RulesInformation.class), any(Errors.class), eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest4()
    {
        formValidator.validate(new ApplicationCAForm(), null, flow);

        verify(designsService, times(1)).validateApplicationCA(eq("dsefiling"), any(ApplicationCAForm.class),
                                                               any(RulesInformation.class), any(Errors.class),
                                                               eq(flow.getFlowModeId()));
    }


    @Test
    public void validateTest5()
    {
        formValidator.validate(new LegalEntityForm(), null, flow);

      verify(personService, times(1)).validateApplicant(eq("dsefiling"), any(LegalEntityForm.class), any(RulesInformation.class),
                                                          any(Errors.class), eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest6()
    {
        formValidator.validate(new NaturalPersonForm(), null, flow);

        verify(personService, times(1)).validateApplicant(eq("dsefiling"), any(NaturalPersonForm.class), any(RulesInformation.class),
                                                          any(Errors.class), eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest7()
    {
        formValidator.validate(new RepresentativeLegalEntityForm(), null, flow);

        verify(personService, times(1)).validateRepresentative(eq("dsefiling"), any(RepresentativeLegalEntityForm.class),
                                                               any(RulesInformation.class), any(Errors.class),
                                                               eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest8()
    {
        formValidator.validate(new RepresentativeNaturalPersonForm(), null, flow);
        verify(personService, times(1)).validateRepresentative(eq("dsefiling"), any(RepresentativeNaturalPersonForm.class),
                                                               any(RulesInformation.class), any(Errors.class),
                                                               eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest9()
    {
        formValidator.validate(new LegalPractitionerForm(), null, flow);

        verify(personService, times(1)).validateRepresentative(eq("dsefiling"), any(LegalPractitionerForm.class),
                                                               any(RulesInformation.class), any(Errors.class),
                                                               eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest10()
    {
        formValidator.validate(new DesignForm(), null, flow);

        verify(designsService, times(1)).validateDesign(eq("dsefiling"), any(DesignForm.class), any(RulesInformation.class),
                                                        any(Errors.class), eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest11()
    {
        formValidator.validate(new DesignViewForm(), null, flow);

        verify(designsService, times(1)).validateDesignView(eq("dsefiling"), any(DesignViewForm.class), any(RulesInformation.class),
                                                            any(Errors.class), eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest12()
    {
        formValidator.validate(new LocarnoForm(), null, flow);

        verify(designsService, times(1)).validateLocarno(eq("dsefiling"), any(LocarnoForm.class), any(RulesInformation.class),
                                                         any(Errors.class), eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest13()
    {
        formValidator.validate(new LocarnoSearchForm(), null, flow);

        verify(designsService, times(1)).validateLocarnoSearch(eq("dsefiling"), any(LocarnoSearchForm.class),
                                                               any(RulesInformation.class), any(Errors.class),
                                                               eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest14()
    {
        formValidator.validate(new DesignerForm(), null, flow);

        verify(personService, times(1)).validateDesigner(eq("dsefiling"), any(DesignerForm.class), any(RulesInformation.class),
                                                         any(Errors.class), eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest15()
    {
        DesignerForm form = new DesignerForm();
        form.setWaiver(true);
        formValidator.validate(form, null, flow);

        verify(personService, times(1)).validateDesigner(eq("dsefiling"), any(DesignerForm.class), any(RulesInformation.class),
                                                         any(Errors.class), eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest16()
    {
        DesignerForm form = new DesignerForm();
        form.setBelongsToAGroup(true);
        formValidator.validate(form, null, flow);

        verify(personService, times(1)).validateDesigner(eq("dsefiling"), any(DesignerForm.class), any(RulesInformation.class),
                                                         any(Errors.class), eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest17()
    {
        formValidator.validate(new DSDivisionalApplicationForm(), null, flow);

        verify(designsService, times(1)).validateDivisionalApplication(eq("dsefiling"), any(DSDivisionalApplicationForm.class),
                                                                       any(RulesInformation.class), any(Errors.class),
                                                                       eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest18()
    {
        formValidator.validate(new StoredFile(), null, flow);

        verify(designsService, times(1)).validateStoredFile(any(String.class), any(StoredFile.class),
                                                                       any(RulesInformation.class), any(Errors.class),
                                                                       eq(flow.getFlowModeId()));
    }

    @Test
    public void validateTest19()
    {
        DSFlowBeanImpl flow = new DSFlowBeanImpl();
        flow.addObject(new ApplicantForm());

        formValidator.validate(flow, null);
    }

    @Test
    public void validateTest20()
    {
        formValidator.validate(new UniversityForm(), null, flow);
    }

    @Test
    public void validateTest21()
    {
        formValidator.validate(new LocarnoComplexForm(), null, flow);
    }

    @Test
    public void supportsTest1()
    {
        formValidator.supports(String.class);
    }
}
