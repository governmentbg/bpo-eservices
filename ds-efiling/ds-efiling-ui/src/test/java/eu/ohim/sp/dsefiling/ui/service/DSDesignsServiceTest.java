package eu.ohim.sp.dsefiling.ui.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.adapter.design.ContactDetailsFactory;
import eu.ohim.sp.common.ui.adapter.design.DSDivisionalApplicationFactory;
import eu.ohim.sp.common.ui.adapter.design.DSExhPriorityFactory;
import eu.ohim.sp.common.ui.adapter.design.DSPriorityFactory;
import eu.ohim.sp.common.ui.adapter.design.DesignFactory;
import eu.ohim.sp.common.ui.adapter.design.DesignViewFactory;
import eu.ohim.sp.common.ui.adapter.design.ProductIndicationFactory;
import eu.ohim.sp.common.ui.adapter.design.ProductIndicationSearchFactory;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.application.EntitlementForm;
import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.common.ui.form.design.LocarnoClass;
import eu.ohim.sp.common.ui.form.design.LocarnoClassification;
import eu.ohim.sp.common.ui.form.design.LocarnoForm;
import eu.ohim.sp.common.ui.form.design.LocarnoSearchForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.classification.LocarnoClassificationService;
import eu.ohim.sp.core.configuration.domain.design.xsd.DesignViewTypes;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.design.DesignDivisionalApplicationDetails;
import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.design.ExhibitionPriority;
import eu.ohim.sp.core.domain.design.Priority;
import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.register.DesignSearchService;
import eu.ohim.sp.core.register.IPApplicationService;
import eu.ohim.sp.dsefiling.ui.adapter.DSFlowBeanFactory;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;
import eu.ohim.sp.dsefiling.ui.domain.DSMainForm;


public class DSDesignsServiceTest
{
    DSFlowBeanImpl flowBean;

    @Mock
    ConfigurationServiceDelegatorInterface configurationService;

    @Mock
    DesignSearchService designCoreService;

    @Mock
    SectionViewConfiguration sectionViewConfiguration;

    @Mock
    DesignFactory designFactory;

    @Mock
    private DesignViewFactory designViewFactory;

    @Mock
    private DSExhPriorityFactory dsExhPriorityFactory;

    @Mock
    private DSPriorityFactory dsPriorityFactory;

    @Mock
    private DSDivisionalApplicationFactory dsDivisionapplicationFactory;

    @Mock
    private ProductIndicationFactory productIndicationFactory;

    @Mock
    private ProductIndicationSearchFactory productIndicationSearchFactory;


    @Mock
    private IPApplicationService ipApplicationService;

    @Mock
    private LocarnoClassificationService locarnoClassificationService;

    @Mock
    ContactDetailsFactory contactDetailsFactory;

    @Mock
    DSFlowBeanFactory flowBeanFactory;

    @InjectMocks
    DSDesignsService designsService = new DSDesignsService();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        flowBean = new DSFlowBeanImpl();
    }

    @Test
    public void addViewsNumberTest()
    {
        DesignViewForm formObj1 = new DesignViewForm();
        formObj1.setType("design view type code");
        DesignViewForm formObj2 = new DesignViewForm();
        formObj2.setType("another type code");

        List<DesignViewTypes.DesignViewType> types = new ArrayList<DesignViewTypes.DesignViewType>();
        types.add(new DesignViewTypes.DesignViewType("design view type code", "design view type value", 1));

        when(configurationService.getDesignViewTypes()).thenReturn(types);

        flowBean.addObject(formObj1);
        flowBean.addObject(formObj2);

        designsService.addViewsNumber(flowBean);

        assertEquals(1, flowBean.getDesignViews().get(0).getSequence());
        assertEquals("another type code", flowBean.getDesignViews().get(0).getType());
        assertEquals(2, flowBean.getDesignViews().get(1).getSequence());
        assertEquals("design view type code", flowBean.getDesignViews().get(1).getType());
    }

    @Test
    public void addDesignToLinkedListsTest()
    {
        DesignForm designForm = new DesignForm();
        designForm.setId("design id");

        DesignerForm containerForm = new DesignerForm();
        containerForm.setId("some id");

        flowBean.addObject(containerForm);

        designsService.addDesignToLinkedLists(designForm, flowBean);

        assertEquals("design id", flowBean.getDesigners().get(0).getDesignsLinked().get(0).getId());
    }

    @Test
    public void isDesignLinkedInSomeFormTest()
    {
        DesignForm designForm = new DesignForm();
        designForm.setId("design id");

        DesignerForm containerForm = new DesignerForm();
        containerForm.setId("some id");

        containerForm.getDesignsLinked().add(designForm);

        flowBean.addObject(containerForm);

        boolean result = designsService.isDesignLinkedInSomeForm(designForm, flowBean);

        assertEquals(true, result);
    }

    @Test
    public void addDesignToListsTest()
    {
        DesignForm designForm = new DesignForm();
        designForm.setId("design id");

        designsService.addDesignToLists(designForm, flowBean);
    }

    @Test
    public void replaceDesignInListsTest()
    {
        DesignForm designForm = new DesignForm();
        designForm.setId("design id");

        DesignerForm containerForm = new DesignerForm();
        containerForm.setId("some id");

        containerForm.getDesignsLinked().add(designForm);

        flowBean.addObject(containerForm);

        designsService.replaceDesignInLists(designForm, flowBean);
    }

    @Test
    public void removeDesignFromListsTest()
    {
        DesignForm designForm = new DesignForm();
        designForm.setId("design id");

        DesignerForm containerForm = new DesignerForm();
        containerForm.setId("some id");

        containerForm.getDesignsLinked().add(designForm);

        flowBean.addObject(containerForm);

        designsService.removeDesignFromLists(designForm, flowBean);

        assertEquals(0, flowBean.getDesigners().get(0).getDesignsLinked().size());
    }

    @Test
    public void fillListsDesignsFromToLinkTest1()
    {
        DesignerForm containerForm = new DesignerForm();
        containerForm.setId("some id");

        flowBean.addObject(containerForm);

        designsService.fillDesignerDesignsListsFromToLink(flowBean, containerForm);
    }

    @Test
    public void fillListsDesignsFromToLinkTest2()
    {
        DSDivisionalApplicationForm containerForm = new DSDivisionalApplicationForm();
        containerForm.setId("some id");

        flowBean.addObject(containerForm);

        when(configurationService.getValue("designs.link.automatically.divisionalApplication",
                                           "eu.ohim.sp.common.ui.form")).thenReturn("true");

        designsService.fillDesignsListsFromToLink(flowBean, containerForm);
    }

    @Test
    public void addFormLocarnoToFlowTest()
    {
        DesignForm designForm = new DesignForm();
        designForm.setId("design id");

        LocarnoForm form = new LocarnoForm();
        form.setId("some id");

        designForm.setLocarno(new ArrayList<LocarnoAbstractForm>());
        designForm.getLocarno().add(form);

        designsService.addFormLocarnoToFlow(designForm, flowBean);

        assertEquals("some id", flowBean.getLocarno().get(0).getId());
    }

    @Test
    public void addFlowLocarnoToFormTest()
    {
        DesignForm designForm = new DesignForm();
        designForm.setId("design id");

        LocarnoForm form = new LocarnoForm();
        form.setId("some id");
        flowBean.addObject(form);

        flowBean.addObject(designForm);

        designsService.addFlowLocarnoToForm(flowBean, designForm);

        assertEquals("some id", flowBean.getDesigns().get(0).getLocarno().get(0).getId());
    }

    @Test
    public void clearLocarnoFlowTest()
    {
        flowBean.addObject(new LocarnoForm());

        designsService.clearLocarnoFlow(flowBean);

        assertEquals(0, flowBean.getLocarno().size());
    }

    @Test
    public void clearLocarnoFormTest()
    {
        DesignForm designForm = new DesignForm();
        designForm.setLocarno(new ArrayList<LocarnoAbstractForm>());
        designForm.getLocarno().add(new LocarnoForm());

        designsService.clearLocarnoForm(designForm);

        assertEquals(0, designForm.getLocarno().size());
    }

    @Test
    public void getDesignTest1()
    {
        DesignForm form = new DesignForm();
        form.setId("id");
        flowBean.addObject(form);


        when(designCoreService.getDesign(any(String.class), any(String.class))).thenReturn(new Design());
        when(designFactory.convertFrom(any(Design.class))).thenReturn(new DesignForm());

        DesignForm result = designsService.getDesign("id");

        assertNotNull(result);
    }

    @Test
    public void getDesignTest2()
    {
        DesignForm form = new DesignForm();
        form.setId("id");
        flowBean.addObject(form);

        DesignForm result = designsService.getDesign(flowBean, "id");

        assertNotNull(result);
    }

    @Test
    public void addFlowViewsToDesignTest()
    {
        DesignForm form = new DesignForm();
        flowBean.addObject(new DesignViewForm());

        designsService.addFlowViewsToDesign(flowBean, form);
    }

    @Test
    public void validateDesignTest()
    {
        Map<String, Sections> map = new HashMap<String, Sections>();
        map.put("flow", new Sections());
        when(sectionViewConfiguration.getViewConfiguration()).thenReturn(map);

        designsService.validateDesign("module", new DesignForm(), new RulesInformation(), null, "flow");

        verify(designCoreService, times(1)).validateDesign(eq("module"), any(Design.class), any(RulesInformation.class));
    }

    @Test
    public void validateDesignViewTest()
    {
        Map<String, Sections> map = new HashMap<String, Sections>();
        map.put("flow", new Sections());
        when(sectionViewConfiguration.getViewConfiguration()).thenReturn(map);

        designsService.validateDesignView("module", new DesignViewForm(), new RulesInformation(), null, "flow");

        verify(designCoreService, times(1)).validateDesignView(eq("module"), any(DesignView.class), any(RulesInformation.class));
    }

    @Test
    public void validatePriorityTest()
    {
        Map<String, Sections> map = new HashMap<String, Sections>();
        map.put("flow", new Sections());
        when(sectionViewConfiguration.getViewConfiguration()).thenReturn(map);

        designsService.validatePriority("module", new DSPriorityForm(), new RulesInformation(), null, "flow");

        verify(ipApplicationService, times(1)).validatePriorityClaim(eq("module"), any(Priority.class), any(RulesInformation.class));
    }

    @Test
    public void validateExhPriorityTest()
    {
        Map<String, Sections> map = new HashMap<String, Sections>();
        map.put("flow", new Sections());
        when(sectionViewConfiguration.getViewConfiguration()).thenReturn(map);

        designsService.validateExhPriority("module", new DSExhPriorityForm(), new RulesInformation(), null, "flow");

        verify(ipApplicationService, times(1)).validateExhibitionPriorityClaim(eq("module"), any(ExhibitionPriority.class),
                                                                               any(RulesInformation.class));
    }

    @Test
    public void validateApplicationCATest()
    {
        Map<String, Sections> map = new HashMap<String, Sections>();
        map.put("flow", new Sections());
        when(sectionViewConfiguration.getViewConfiguration()).thenReturn(map);

        designsService.validateApplicationCA("module", new ApplicationCAForm(), new RulesInformation(), null, "flow");

        verify(designCoreService, times(1)).validateApplicationCA(eq("module"), any(ContactDetails.class),
                                                                  any(RulesInformation.class));
    }

    @Test
    public void validateDivisionalApplicationTest()
    {
        Map<String, Sections> map = new HashMap<String, Sections>();
        map.put("flow", new Sections());
        when(sectionViewConfiguration.getViewConfiguration()).thenReturn(map);

        designsService.validateDivisionalApplication("module", new DSDivisionalApplicationForm(), new RulesInformation(), null,
                                                     "flow");

        verify(designCoreService, times(1)).validateDivisionalApplication(eq("module"), any(DesignDivisionalApplicationDetails.class),
                                                                          any(RulesInformation.class));
    }

    @Test
    public void validateLocarnoTest()
    {
        Map<String, Sections> map = new HashMap<String, Sections>();
        map.put("flow", new Sections());
        when(sectionViewConfiguration.getViewConfiguration()).thenReturn(map);

        designsService.validateLocarno("module", new LocarnoForm(), new RulesInformation(), null, "flow");

        verify(locarnoClassificationService, times(1)).validateProductIndication(eq("module"), any(ProductIndication.class),
                                                                                 any(RulesInformation.class));
    }

    @Test
    public void validateLocarnoSearchTest()
    {
        Map<String, Sections> map = new HashMap<String, Sections>();
        map.put("flow", new Sections());
        when(sectionViewConfiguration.getViewConfiguration()).thenReturn(map);

        designsService.validateLocarnoSearch("module", new LocarnoSearchForm(), new RulesInformation(), null, "flow");

        verify(locarnoClassificationService, times(1)).validateProductIndication(eq("module"), any(ProductIndication.class),
                                                                                 any(RulesInformation.class));
    }

    @Test
    public void addDesignViewsToFlowTest()
    {
        DesignForm form = new DesignForm();
        form.setViews(new ArrayList<DesignViewForm>());
        form.getViews().add(new DesignViewForm());
        form.getViews().get(0).setDescription("description");

        designsService.addDesignViewsToFlow(form, flowBean);

        assertEquals("description", flowBean.getDesignViews().get(0).getDescription());
    }

    @Test
    public void clearViewsFlowTest()
    {
        flowBean.getDesignViews().add(new DesignViewForm());

        designsService.clearViewsFlow(flowBean);

        assertEquals(0, flowBean.getDesignViews().size());
    }

    @Test
    public void clearViewsDesignFormTest()
    {
        DesignForm form = new DesignForm();
        form.setViews(new ArrayList<DesignViewForm>());
        form.getViews().add(new DesignViewForm());

        designsService.clearViewsDesignForm(form);

        assertEquals(0, form.getViews().size());
    }

    @Test
    public void getDesignApplicationTest()
    {
        DesignApplication app = new DesignApplication();

        flowBean.setFilingNumber("123");
        flowBean.setLocarno(new ArrayList<LocarnoAbstractForm>());
        flowBean.setLocarnoClasses(new ArrayList<LocarnoClass>());
        flowBean.getLocarnoClasses().add(new LocarnoClass("1", "6"));
        flowBean.setLocarnoSubclasses(new ArrayList<LocarnoClass>());
        flowBean.getLocarnoSubclasses().add(new LocarnoClass("5", "10"));

        flowBean.setLocarnoClassifications(new HashMap<String, LocarnoClassification>());
        LocarnoClassification lc = new LocarnoClassification();
        lc.setId("3");
        flowBean.getLocarnoClassifications().put("1", lc);

        DSPriorityForm prio = new DSPriorityForm();
        prio.setId("id");
        flowBean.setPriorities(new ArrayList<DSPriorityForm>());
        flowBean.getPriorities().add(prio);

        DSExhPriorityForm exh = new DSExhPriorityForm();
        flowBean.setExhpriorities(new ArrayList<DSExhPriorityForm>());
        flowBean.getExhpriorities().add(exh);

        flowBean.setMainForm(new DSMainForm());
        flowBean.getMainForm().setEntitlement(new EntitlementForm());

        flowBean.getMainForm().setPersonalDataSection(true);
        flowBean.getMainForm().setDesignDataSection(true);
        flowBean.getMainForm().setLanguageSection(true);
        flowBean.getMainForm().setReferenceSection(true);
        flowBean.getMainForm().setDivisionalSection(true);
        flowBean.getMainForm().setClaimSection(true);
        flowBean.getMainForm().setApplicantDataSection(true);
        flowBean.getMainForm().setRepresentativeDataSection(true);
        flowBean.getMainForm().setApplicantionCADataSection(true);
        flowBean.getMainForm().setDesignerDataSection(true);
        flowBean.getMainForm().setOtherAttachments(true);
        flowBean.getMainForm().setSignatureSection(true);
        flowBean.getMainForm().setTermsAndConditionsSection(true);
        flowBean.getMainForm().setPaymentDataSection(true);
        flowBean.getMainForm().setDefermentofPublicationSection(true);
        flowBean.getMainForm().setEntitlementSection(true);

        when(designCoreService.getDesignApplication(any(String.class), eq("id"), any(String.class), any(Boolean.class))).thenReturn(app);
        when(flowBeanFactory.convertFrom(app)).thenReturn(flowBean);


        DSFlowBean result = designsService.getDesignApplication("id");

        assertEquals("123", result.getFilingNumber());
        assertEquals("1", result.getLocarnoClasses().get(0).getClazz());
        assertEquals("5", result.getLocarnoSubclasses().get(0).getClazz());
        assertEquals("3", result.getLocarnoClassifications().get("1").getId());
        assertEquals("id", result.getPriorities().get(0).getId());
        assertEquals(true, result.getMainForm().isPersonalDataSection());
        assertEquals(true, result.getMainForm().isDesignDataSection());
        assertEquals(true, result.getMainForm().isLanguageSection());
        assertEquals(true, result.getMainForm().isReferenceSection());
        assertEquals(true, result.getMainForm().isDivisionalSection());
        assertEquals(true, result.getMainForm().isClaimSection());
        assertEquals(true, result.getMainForm().isApplicantDataSection());
        assertEquals(true, result.getMainForm().isRepresentativeDataSection());
        assertEquals(true, result.getMainForm().isApplicantionCADataSection());
        assertEquals(true, result.getMainForm().isDesignerDataSection());
        assertEquals(true, result.getMainForm().isLanguageSection());
        assertEquals(true, result.getMainForm().isOtherAttachments());
        assertEquals(true, result.getMainForm().isSignatureSection());
        assertEquals(true, result.getMainForm().isTermsAndConditionsSection());
        assertEquals(true, result.getMainForm().isPaymentDataSection());
        assertEquals(true, result.getMainForm().isDefermentofPublicationSection());
        assertEquals(true, result.getMainForm().isEntitlementSection());
    }

    @Test
    public void getDesignAutocompleteTest1()
    {
        when(designCoreService.getDesignAutocomplete("es", "text", 10)).thenReturn("autocomplete string");

        String result = designsService.getDesignAutocomplete("es", "text", 10);

        assertEquals("autocomplete string", result);
    }

    @Test
    public void getDesignAutocompleteTest2()
    {
        when(designCoreService.getDesignAutocomplete(any(String.class), eq("text"), eq(11))).thenReturn("autocomplete string");

        String result = designsService.getDesignAutocomplete("text", 11);

        assertEquals("autocomplete string", result);
    }
}