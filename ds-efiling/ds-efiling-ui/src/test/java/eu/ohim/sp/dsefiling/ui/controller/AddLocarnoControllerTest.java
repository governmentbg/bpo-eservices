package eu.ohim.sp.dsefiling.ui.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.common.ui.form.design.LocarnoClass;
import eu.ohim.sp.common.ui.form.design.LocarnoClassification;
import eu.ohim.sp.common.ui.form.design.LocarnoComplexForm;
import eu.ohim.sp.common.ui.form.design.LocarnoForm;
import eu.ohim.sp.common.ui.form.design.LocarnoSearchForm;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.dsefiling.TestHelper;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.DSDesignsService;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSLocarnoServiceInterface;

/**
 * @author serrajo
 */
public class AddLocarnoControllerTest
{
    @Mock
    DSFlowBean flowBean;

    @Mock
    FormValidator validator;

    @Mock
    private DSLocarnoServiceInterface dsLocarnoService;

    @InjectMocks
    AddLocarnoController addLocarnoController = new AddLocarnoController();

    @Mock
    DSDesignsService designsService;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addLocarnoClassTest1()
    {
        when(flowBean.getFirstLang()).thenReturn("es");

        Answer<List<LocarnoClass>> classList = TestHelper.setupDummyListAnswer(new LocarnoClass());
        when(dsLocarnoService.getLocarnoClasses("es")).thenAnswer(classList);

        ModelAndView result = addLocarnoController.formAddClass();

        assertEquals("locarno/locarno_addClass", result.getViewName());
        assertEquals(new LocarnoSearchForm(), result.getModel().get("locarnoSearchForm"));
    }


    @Test
    public void saveLocarnoClassTest1()
    {
        BindingResult binding = mock(BindingResult.class);

        LocarnoSearchForm form = new LocarnoSearchForm();
        List<LocarnoComplexForm> locarnoClassificationsSelected = new ArrayList<LocarnoComplexForm>();
        LocarnoComplexForm locarnoForm = new LocarnoComplexForm();
        locarnoForm.setId("23");
        locarnoClassificationsSelected.add(locarnoForm);
        form.setLocarnoClassificationsSelected(locarnoClassificationsSelected);

        LocarnoComplexForm locarnoForm2 = new LocarnoComplexForm();
        locarnoForm2.setId("23");
        when(flowBean.getLocarno()).thenAnswer(TestHelper.setupDummyListAnswer(locarnoForm2));

        ModelAndView result = addLocarnoController.onSubmitAddClass(form, binding);

        assertEquals("locarno/locarno_list", result.getViewName());
    }

    @Test
    public void saveLocarnoClassTest2()
    {
        BindingResult binding = mock(BindingResult.class);

        LocarnoSearchForm form = new LocarnoSearchForm();
        List<LocarnoComplexForm> locarnoClassificationsSelected = new ArrayList<LocarnoComplexForm>();
        LocarnoComplexForm locarnoForm = new LocarnoComplexForm();
        locarnoForm.setId("23");
        locarnoClassificationsSelected.add(locarnoForm);
        form.setLocarnoClassificationsSelected(locarnoClassificationsSelected);


        ModelAndView result = addLocarnoController.onSubmitAddClass(form, binding);

        assertEquals("locarno/locarno_list", result.getViewName());
    }

    @Test
    public void addNewProductTest1()
    {
        ModelAndView result = addLocarnoController.formAddNewProduct(null);

        assertEquals("locarno/locarno_addNewProduct", result.getViewName());
        assertEquals(new LocarnoForm(), result.getModel().get("locarnoForm"));
    }

    @Test
    public void saveNewProductTest1()
    {
        BindingResult binding = mock(BindingResult.class);

        LocarnoForm form = new LocarnoForm();

        ModelAndView result = addLocarnoController.onSubmitAddNewProduct(form, binding);

        assertEquals("locarno/locarno_list", result.getViewName());
    }

    @Test
    public void saveNewProduct_failsValidation()
    {
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(true);

        LocarnoForm form = new LocarnoForm();
        ModelAndView result = addLocarnoController.onSubmitAddNewProduct(form, binding);

        assertEquals("locarno/locarno_addNewProduct", result.getViewName());
    }

    @Test
    public void getNewProduct()
    {
        LocarnoForm form = new LocarnoForm();
        form.setId("some id");

        when(flowBean.getObject(LocarnoAbstractForm.class, "some id")).thenReturn(form);

        ModelAndView result = addLocarnoController.getNewProduct("some id");

        assertEquals("locarno/locarno_addNewProduct", result.getViewName());
    }

    @Test
    public void addNewComplexProduct()
    {
        ModelAndView result = addLocarnoController.formAddNewComplexProduct(null);

        assertEquals("locarno/locarno_addNewComplexProduct", result.getViewName());
        assertEquals(new LocarnoComplexForm(), result.getModel().get("locarnoComplexForm"));
    }

    @Test
    public void saveNewComplexProduct()
    {
        BindingResult binding = mock(BindingResult.class);

        LocarnoComplexForm form = new LocarnoComplexForm();
        ModelAndView result = addLocarnoController.onSubmitAddNewComplexProduct(form, binding);

        assertEquals("locarno/locarno_list", result.getViewName());
    }

    @Test
    public void saveNewComplexProduct_failsValidation()
    {
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(true);

        LocarnoComplexForm form = new LocarnoComplexForm();
        ModelAndView result = addLocarnoController.onSubmitAddNewComplexProduct(form, binding);

        assertEquals("locarno/locarno_addNewComplexProduct", result.getViewName());
    }

    @Test
    public void getNewComplexProduct()
    {
        LocarnoComplexForm form = new LocarnoComplexForm();
        form.setId("some id");

        when(flowBean.getObject(LocarnoAbstractForm.class, "some id")).thenReturn(form);

        ModelAndView result = addLocarnoController.getNewProduct("some id");

        assertEquals("locarno/locarno_addNewComplexProduct", result.getViewName());
    }

    @Test
    public void getProductInvalidId()
    {
        when(flowBean.getObject(LocarnoAbstractForm.class, "some id")).thenReturn(null);

        ModelAndView result = addLocarnoController.getNewProduct("some id");

        assertEquals(null, result);
    }

    @Test
    public void getLocarnoClassifications()
    {
        BindingResult binding = mock(BindingResult.class);

        LocarnoSearchForm form = new LocarnoSearchForm();
        LocarnoClassification searchData = new LocarnoClassification();
        LocarnoClass locarnoClass = new LocarnoClass("01", null);
        searchData.setLocarnoClass(locarnoClass);
        form.setSearchData(searchData);

        ModelAndView result = addLocarnoController.getLocarnoCassifications(form, binding);

        assertEquals("locarno/locarno_listClass", result.getViewName());
    }

    @Test
    public void getLocarnoClassifications_failsValidationSearchData()
    {
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(true);

        LocarnoSearchForm form = new LocarnoSearchForm();

        ModelAndView result = addLocarnoController.getLocarnoCassifications(form, binding);

        assertEquals("locarno/locarno_addClass", result.getViewName());
    }

    @Test
    public void getLocarnoSubclasses()
    {
        when(dsLocarnoService.getLocarnoSubclasses(eq("01"), any(String.class))).thenAnswer(
                TestHelper.setupDummyListAnswer(new LocarnoClass()));

        List<LocarnoClass> subclasses = addLocarnoController.getLocarnoSubclasses("01");

        Assert.notEmpty(subclasses, "The 01 class has to have subclasses");
    }


    @Test
    public void initBindertest1()
    {
        addLocarnoController.initBinder(new WebDataBinder(null));
    }
}
