package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.common.ui.FieldPropertyEditor;
import eu.ohim.sp.common.ui.flow.section.LanguagesFlowBean;
import eu.ohim.sp.common.ui.form.classification.*;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyConceptNode;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.flow.GoodsServicesFlowBean;
import eu.ohim.sp.ui.tmefiling.service.interfaces.GoodsServicesServiceInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GSControllerTest {

    @Mock
    private FlowBeanImpl flowBean;

    @Mock
    private LanguagesFlowBean languagesFlowBean;

    @Mock
    private GoodsServicesServiceInterface goodsServices;

    @Mock
    private ConfigurationServiceDelegatorInterface configurationService;

    @Mock
    private FieldPropertyEditor fieldPropertyEditor;

    @InjectMocks
    private GSController gsController;

    private GoodAndServiceForm goodAndServiceForm;

    private GoodAndServiceForm niceGoodAndServiceForm;

    @Before
    public void before() {
        ReflectionTestUtils.setField(gsController, "levelScope", 0);
        ReflectionTestUtils.setField(gsController, "firstLevelVisible", 0);

        goodAndServiceForm = Mockito.mock(GoodAndServiceForm.class);
        TermForm termForm = Mockito.mock(TermForm.class);

        niceGoodAndServiceForm = Mockito.mock(GoodAndServiceForm.class);

        Mockito.when(goodsServices.importNiceClassHeading(Mockito.anyString(), Mockito.anyString())).thenReturn(niceGoodAndServiceForm);
        Mockito.when(flowBean.getGoodsAndService(Mockito.anyString(), Mockito.anyString())).thenReturn(goodAndServiceForm);
        Mockito.when(flowBean.getGoodsAndServices()).thenReturn(new HashSet<>(Arrays.asList(goodAndServiceForm)));
        Mockito.when(flowBean.getTempGoodsServices(Mockito.anyString())).thenReturn(goodAndServiceForm);
        Mockito.when(goodAndServiceForm.getClassId()).thenReturn("1");
        Mockito.when(goodAndServiceForm.getLangId()).thenReturn("es");

        Mockito.when(goodAndServiceForm.getTermForms()).thenReturn(new HashSet<TermForm>(Arrays.asList(termForm)));

    }


    @Test
    public void initBinderTest() {

        WebDataBinder binder = Mockito.mock(WebDataBinder.class);

        gsController.initBinder(binder);

        verify(binder, times(1)).registerCustomEditor(String.class, fieldPropertyEditor);

    }

    @Test
    public void getListOfGoodsServicesTest() {

        gsController.getListOfGoodsServices();

        verify(flowBean, times(1)).getGoodsAndServices();

    }

    @Test
    public void handleExceptionTest() {

        Throwable throwable = Mockito.mock(Throwable.class);

        ModelAndView model = gsController.handleException(throwable);

        assertNotNull(model);
        assertEquals(model.getModel().get("exception"), throwable);

    }

    @Test
    public void handleNoResultsExceptionTest() {

        Throwable throwable = Mockito.mock(Throwable.class);

        ModelAndView model = gsController.handleNoResultsException(throwable);

        assertNotNull(model);
        assertEquals(model.getModel().get("exception"), throwable);

    }

    @Test
    public void termsTest() {

        String langId = "es";

        Mockito.when(goodAndServiceForm.getLangId()).thenReturn(langId);

        Collection<TermJSON> terms = gsController.terms(langId, true);

        assertNotNull(terms);
        assertEquals(terms.size(), 1);

    }

    @Test
    public void parentConceptNodesTestWithTaxo() {

        String langId = "es";
        String term = "term";
        String taxoConceptNodeId = "1";
        TaxonomyConceptNodeTreeView taxonomyConceptNodeTreeView = Mockito.mock(TaxonomyConceptNodeTreeView.class);

        Mockito.when(configurationService.getValue(Mockito.anyString(), Mockito.anyString())).thenReturn("3");
        Mockito.when(goodsServices.getTaxonomy(langId, term, 3, taxoConceptNodeId)).thenReturn(Arrays.asList(taxonomyConceptNodeTreeView));

        Mockito.when(taxonomyConceptNodeTreeView.isClassScope()).thenReturn(true);
        Mockito.when(taxonomyConceptNodeTreeView.getNiceClass()).thenReturn(0);

        Collection<TaxonomyConceptNodeTreeView> nodes = gsController.parentConceptNodes(langId, term, taxoConceptNodeId);

        assertNotNull(nodes);
        assertEquals(nodes.size(), 1);

    }

    @Test
    public void parentConceptNodesTest() {

        String langId = "es";
        String term = "term";
        TaxonomyConceptNode taxonomyConceptNode = Mockito.mock(TaxonomyConceptNode.class);

        Mockito.when(goodsServices.getParentConceptNodes(langId, term)).thenReturn(Arrays.asList(taxonomyConceptNode));

        Collection<TaxonomyConceptNode> nodes = gsController.parentConceptNodes(langId, term);

        assertNotNull(nodes);
        assertEquals(nodes.size(), 1);
        assertEquals(nodes.iterator().next(), taxonomyConceptNode);

    }

    @Test
    public void getImportNiceClassHeadingTest() {

        ModelAndView model = gsController.getImportNiceClassHeading();

        assertNotNull(model);
        assertEquals(model.getViewName(), "goods_services/class_headings");

    }

    @Test
    public void loadNiceClassHeadingTest() {

        String niceClass = "1";
        String language = "es";

        GoodAndServiceForm gsForm = gsController.loadNiceClassHeading(niceClass, language);

        assertEquals(gsForm, niceGoodAndServiceForm);

    }

    @Test
    public void didYouMeanTest() {

        String searchCriteria = "criteria";
        String language = "es";
        String result = "result";

		Mockito.when(goodsServices.getAutocomplete(language, searchCriteria)).thenReturn(Arrays.asList(result));

		List<String> results = gsController.autocomplete(searchCriteria, language);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(results.iterator().next(), result);

    }

    @Test
    public void importNiceClassHeadingTest() {

        String niceClass = "1";
        String language = "es";

        GSController.Result result = gsController.importNiceClassHeading(niceClass, language);

        assertNotNull(result);
        assertEquals(result.getValue(), "success");

    }

    @Test
    public void disableRemovalTest() {

        String niceClass = "1";
        String language = "es";
        boolean canNotBeRemoved = false;

        gsController.disableRemoval(niceClass, language, canNotBeRemoved);

        verify(flowBean, times(1)).getGoodsAndService(language, niceClass);
        verify(goodAndServiceForm, times(1)).setDisabledRemoval(canNotBeRemoved);

    }

    @Test
    public void modifyTermTest() {

        String oldTerm = "old term";
        String oldNiceClass = "1";
        String langId = "es";
        String term = "term";
        String niceClass = "1";

        ModelAndView model = gsController.modifyTerm(oldTerm, oldNiceClass, langId, term, niceClass);

        assertEquals(model.getModel().get("numberOfClasses"), 1);

    }

    @Test
    public void searchTermTest() {

        String language = "es";
        String term = "term";
        String taxoConceptNodeId = "123";
        String page = "1";
        String filter = "filter";
        String sortBy = "column";
        String orderBy = "asc";
        Integer limit = 5;

        gsController.searchTerm(language, term, taxoConceptNodeId, page, filter, sortBy, orderBy, null);

        verify(goodsServices, times(1)).searchTerms(flowBean, language, term, filter, taxoConceptNodeId, limit, page, sortBy, orderBy, null);

    }

    @Test
    public void getDistributionTest() {

        String language = "es";
        String term = "term";
        String taxoConceptNodeId = "123";

        gsController.getDistribution(language, term, taxoConceptNodeId);

        verify(goodsServices, times(1)).getDistribution(language, term);

    }

    @Test
    public void addTermsTest() {

        AddedTermForm goodsForm = Mockito.mock(AddedTermForm.class);
        TermForm termForm = Mockito.mock(TermForm.class);

        Mockito.when(goodsForm.getTerms()).thenReturn(Arrays.asList(termForm));

        GSController.Result results = gsController.addTerms(goodsForm);

        assertEquals(results.getValue(), "success");

    }

    @Test
    public void removeTermTest() {

        String term = "term";
        String classId = "1";
        String langId = "es";

        GSController.Result results = gsController.removeTerm(term, classId, langId);

        assertEquals(results.getValue(), "success");

    }

    @Test
    public void provideListOnMyOwnTest() {

        ModelAndView model = gsController.provideListOnMyOwn();

        assertEquals(model.getViewName(), "goods_services/provide_terms");
        assertNotNull(model.getModel().get("goodAndServiceForm"));

    }

    @Test
    public void loadModalPopupTest() {

        ModelAndView model = gsController.loadModalPopup();

        assertEquals(model.getViewName(), "goods_services_redesign/modal-gs");

    }

    @Test
    public void startAddingListOnMyOwnGoodsServicesTest() {

        String langId = "es";
        String classId = "1";

        String unique = gsController.startAddingListOnMyOwnGoodsServices(langId, classId);

        verify(flowBean, times(1)).resetTempGoodsServices(unique, classId, langId);

    }

    @Test
    public void addListOnMyOwnGoodsServicesTest() {

        String uuid = "REWREW4324EHGHG";
        BindingResult result = Mockito.mock(BindingResult.class);

        List<ObjectError> errors = gsController.addListOnMyOwnGoodsServices(uuid, goodAndServiceForm, result);

        assertNotNull(errors);
        assertTrue(errors.isEmpty());

    }

    @Test
    public void submitListOnMyOwnJSONTest() {

        String uuid = "REWREW4324EHGHG";
        BindingResult result = Mockito.mock(BindingResult.class);

        List<ObjectError> errors = gsController.submitListOnMyOwnJSON(uuid, false, goodAndServiceForm, result);

        assertNotNull(errors);
        assertTrue(errors.isEmpty());

    }

    @Test
    public void getGSClassesTest() {

        List<String> classes = gsController.getGSClasses();

        assertNotNull(classes);
        assertFalse(classes.isEmpty());

    }

}