package eu.ohim.sp.dsefiling.ui.controller;


import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.exception.NoResultsException;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.domain.DSMainForm;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LanguageControllerTest
{
    @Mock
    private DSFlowBean flowBean;

    @Mock
    private SectionViewConfiguration sectionViewConfiguration;

    @Mock
    private FlowScopeDetails flowScopeDetails;

    @InjectMocks
    LanguageController languageController = new LanguageController();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void handleExceptionTest()
    {
        ModelAndView result = languageController.handleException(new SPException());

        assertEquals("errors/errors", result.getViewName());
        assertEquals(SPException.class, result.getModel().get("exception").getClass());
    }

    @Test
    public void handleNoResultsExceptionTest()
    {
        ModelAndView result = languageController.handleNoResultsException(new NoResultsException("message", null, "code"));

        assertEquals("errors/errors", result.getViewName());
        assertEquals(NoResultsException.class, result.getModel().get("exception").getClass());
    }

    @Test
    public void changeLanguageTranslationTest()
    {
        DSMainForm mainFormMock = mock(DSMainForm.class);
        when(flowBean.getMainForm()).thenReturn(mainFormMock);

        String result = languageController.changeLanguageTranslation(true);

        verify(mainFormMock, times(1)).setSecondLanguageTranslation(Boolean.TRUE);
        assertEquals("success", result);
    }

    @Test
    public void changeLanguageTest1()
    {
        DSMainForm mainFormMock = mock(DSMainForm.class);
        when(flowBean.getMainForm()).thenReturn(mainFormMock);

        String result = languageController.changeLanguage("en", true, true);

        verify(flowBean, times(1)).setFirstLang("en");
        verify(mainFormMock, times(1)).setCorrespondenceLanguageCheckBox(Boolean.TRUE);
        assertEquals("success", result);
    }

    @Test
    public void changeLanguageTest2()
    {
        DSMainForm mainFormMock = mock(DSMainForm.class);
        when(flowBean.getMainForm()).thenReturn(mainFormMock);

        String result = languageController.changeLanguage("en", false, true);

        verify(flowBean, times(1)).setSecLang("en");
        verify(mainFormMock, times(1)).setCorrespondenceLanguageCheckBox(Boolean.TRUE);
        assertEquals("success", result);
    }

    @Test
    public void hasLanguageTest1()
    {
        when(flowBean.getFirstLang()).thenReturn("es");
        when(flowBean.getSecLang()).thenReturn("de");
        //when(flowScopeDetails.getFlowModeId()).thenReturn("oneform");
        when(sectionViewConfiguration.getUsable(any(AvailableSection.class), eq("secLang"), any(String.class))).thenReturn(true);

        String result = languageController.hasLanguage();

        assertEquals("true", result);
    }

    @Test
    public void hasLanguageTest2()
    {
        when(flowBean.getFirstLang()).thenReturn("es");
        when(flowBean.getSecLang()).thenReturn(null);
        //when(flowScopeDetails.getFlowModeId()).thenReturn("oneform");
        when(sectionViewConfiguration.getUsable(any(AvailableSection.class), eq("secLang"), any(String.class))).thenReturn(true);

        String result = languageController.hasLanguage();

        assertEquals("false", result);
    }

    @Test
    public void hasLanguageTest3()
    {
        when(flowBean.getFirstLang()).thenReturn(null);
        when(flowBean.getSecLang()).thenReturn(null);
        //when(flowScopeDetails.getFlowModeId()).thenReturn("oneform");
        when(sectionViewConfiguration.getUsable(any(AvailableSection.class), eq("secLang"), any(String.class))).thenReturn(false);

        String result = languageController.hasLanguage();

        assertEquals("false", result);
    }

    @Test
    public void hasLanguageTest4()
    {
        when(flowBean.getFirstLang()).thenReturn("en");
        when(flowBean.getSecLang()).thenReturn(null);
        //when(flowScopeDetails.getFlowModeId()).thenReturn("oneform");
        when(sectionViewConfiguration.getUsable(any(AvailableSection.class), eq("secLang"), any(String.class))).thenReturn(false);

        String result = languageController.hasLanguage();

        assertEquals("true", result);
    }

    @Test
    public void hasLanguageTest5()
    {
        when(flowBean.getFirstLang()).thenReturn("");
        when(flowBean.getSecLang()).thenReturn("es");
        //when(flowScopeDetails.getFlowModeId()).thenReturn("oneform");
        when(sectionViewConfiguration.getUsable(any(AvailableSection.class), eq("secLang"), any(String.class))).thenReturn(true);

        String result = languageController.hasLanguage();

        assertEquals("false", result);
    }
}