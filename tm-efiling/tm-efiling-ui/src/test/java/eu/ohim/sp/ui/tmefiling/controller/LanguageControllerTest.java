package eu.ohim.sp.ui.tmefiling.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.flow.section.LanguagesFlowBean;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.ui.tmefiling.controller.LanguageController.LanguageInfo;
import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;
import eu.ohim.sp.ui.tmefiling.form.MainForm;

public class LanguageControllerTest {

	@InjectMocks
	private LanguageController languageController;

	@Mock
	private LanguagesFlowBean languagesFlowBean;

	@Mock
	private TMFlowBean tmFlowBean;

	@Mock
	private SectionViewConfiguration sectionViewConfiguration;

	@Mock
	private ConfigurationServiceDelegatorInterface configurationService;

	@Mock
	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;

	@Mock
	FlowScopeDetails flowScopeDetails;

	@Before
	public void before() {

		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void changeLanguageTest1() {

		String lang = "hu";
		boolean firsLang = true;
		boolean collective = true;

		when(tmFlowBean.getMainForm()).thenReturn(mock(MainForm.class));
		String result = languageController.changeLanguage(lang, firsLang, collective);

		assertNotNull(result);
		assertEquals("success", result);
		verify(languagesFlowBean).setFirstLang(lang);
		verify(tmFlowBean.getMainForm()).setCollectiveMark(collective);
	}

	@Test
	public void changeLanguageTest2() {

		String lang = "hu";
		boolean firsLang = false;
		boolean collective = true;

		when(tmFlowBean.getMainForm()).thenReturn(mock(MainForm.class));
		String result = languageController.changeLanguage(lang, firsLang, collective);

		assertNotNull(result);
		assertEquals("success", result);
		verify(languagesFlowBean).setSecLang(lang);
		verify(languagesFlowBean, never()).setFirstLang(lang);
		verify(tmFlowBean.getMainForm()).setCollectiveMark(collective);
	}

	@Test
	public void changeLanguageTranslation() {
		boolean secondLanguageTranslation = true;
		when(tmFlowBean.getMainForm()).thenReturn(mock(MainForm.class));

		ModelAndView result = languageController.changeLanguageTranslation(secondLanguageTranslation);

		assertNotNull(result);
		assertEquals("marktoprotect_languages", result.getViewName());
		verify(tmFlowBean.getMainForm()).setSecondLanguageTranslation(secondLanguageTranslation);

		secondLanguageTranslation = false;
		result = languageController.changeLanguageTranslation(secondLanguageTranslation);

		assertNotNull(result);
		assertEquals("marktoprotect_languages", result.getViewName());
		verify(tmFlowBean.getMainForm()).setSecondLanguageTranslation(secondLanguageTranslation);
	}

	@Test
	public void checkSecondLanguageTestBlank() {

		String[] blanks = new String[] { null, " ", "", "       " };

		for (String b : blanks) {
			when(languagesFlowBean.getSecLang()).thenReturn(b);

			ModelAndView r = languageController.checkSecondLanguage();

			assertNotNull(r);
			assertNull(r.getModelMap().get("alertMessage"));
		}
	}

	@Test
	public void checkSecondLanguageTestError() {

		when(languagesFlowBean.getSecLang()).thenReturn("hu");

		ModelAndView r = languageController.checkSecondLanguage();

		assertNotNull(r);
		assertEquals("language.error.second", r.getModelMap().get("alertMessage"));
	}

	@Test
	public void hadleExceptionTest() {
		Throwable e = mock(Throwable.class);
		ModelAndView m = languageController.handleException(e);

		assertNotNull(m);
		assertEquals(e, m.getModelMap().get("exception"));
	}

	@Test
	public void hadleNoResultsExceptionTest() {
		Throwable e = mock(Throwable.class);
		ModelAndView m = languageController.handleNoResultsException(e);

		assertNotNull(m);
		assertEquals(e, m.getModelMap().get("exception"));
	}

	@Test
	public void hasLanguageTestFirstLangAndSecondLangAvailable() {

		String firtlang = "hu";
		String secLang = "en";
		when(languagesFlowBean.getFirstLang()).thenReturn(firtlang);
		when(languagesFlowBean.getSecLang()).thenReturn(secLang);

		String flowModeId = "flowmodeid";
		when(flowScopeDetails.getFlowModeId()).thenReturn(flowModeId);
		Boolean secLangRequired = true;
		when(sectionViewConfiguration.getUsable(AvailableSection.LANGUAGES, "secLang", flowModeId))
				.thenReturn(secLangRequired);

		String result = languageController.hasLanguage();

		assertEquals("true", result);

	}

	@Test
	public void hasLanguageTestFirstLangAvailableSecondLangNotRequiered() {

		String firtlang = "hu";
		String secLang = "en";
		when(languagesFlowBean.getFirstLang()).thenReturn(firtlang);
		when(languagesFlowBean.getSecLang()).thenReturn(secLang);

		String flowModeId = "flowmodeid";
		when(flowScopeDetails.getFlowModeId()).thenReturn(flowModeId);
		Boolean secLangRequired = false;
		when(sectionViewConfiguration.getUsable(AvailableSection.LANGUAGES, "secLang", flowModeId))
				.thenReturn(secLangRequired);

		String result = languageController.hasLanguage();

		assertEquals("true", result);

	}

	@Test
	public void getLanguageInfoTest1() {
		when(languagesFlowBean.getFirstLang()).thenReturn(null);
		when(languagesFlowBean.getSecLang()).thenReturn(null);
		HttpServletRequest req = mock(HttpServletRequest.class);
		LanguageInfo r = languageController.getLanguageInfo(req);

		assertNotNull(r);

	}

	@Test
	public void LangInfoTest() throws InstantiationException, IllegalAccessException {

		LanguageInfo info = languageController.new LanguageInfo();
		String first = "hu";
		info.setFirst(first);
		String second = "en";
		info.setSecond(second);
		String firstLabel = "first";
		info.setFirstLabel(firstLabel);
		String secondLabel = "seconde";
		info.setSecondLabel(secondLabel);
		boolean secondLanguageTranslation = true;
		info.setSecondLanguageTranslation(secondLanguageTranslation);

		assertEquals(first, info.getFirst());
		assertEquals(second, info.getSecond());
		assertEquals(firstLabel, info.getFirstLabel());
		assertEquals(secondLabel, info.getSecondLabel());
		assertEquals(secondLanguageTranslation, info.isSecondLanguageTranslation());

	}

}
