package eu.ohim.sp.eservices.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.exception.NoResultsException;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;

@Controller
public class LanguageController {
	/**
	 * Logger for this class and subclasses
	 */
	private static final Logger logger = Logger
			.getLogger(LanguageController.class);

	@Autowired
	private ESFlowBean flowBean;

	@Autowired
	private SectionViewConfiguration sectionViewConfiguration;

	@Autowired
	private FlowScopeDetails flowScopeDetails;
	
	@Value("${sp.languageOffice}")
    private String officeLanguage;

	/**
	 * Handles SP exceptions
	 * 
	 * @param e
	 *            the exception thrown on the controller context
	 * @return a model and view that will be handled in UI
	 */
	@ExceptionHandler(SPException.class)
	public ModelAndView handleException(Throwable e) {
		ModelAndView modelAndView = new ModelAndView("errors/errors");
		logger.error(e);
		modelAndView.addObject("exception", e);
		return modelAndView;
	}

	/**
	 * Handles No Results exceptions
	 * 
	 * @param e
	 *            the exception thrown on the controller context
	 * @return a model and view that will be handled in UI
	 */
	@ExceptionHandler(NoResultsException.class)
	public ModelAndView handleNoResultsException(Throwable e) {
		ModelAndView modelAndView = new ModelAndView("errors/errors");
		logger.error(e);
		modelAndView.addObject("exception", e);
		return modelAndView;
	}

	/**
	 * Ajax controller that is used to update the field change
	 * MainForm.secondLanguageTranslation
	 * 
	 * @param secondLanguageTranslation
	 * @return ModelAndView
	 */
	@RequestMapping(value = "changeLanguageTranslation", method = RequestMethod.GET)
	public ModelAndView changeLanguageTranslation(
			boolean secondLanguageTranslation) {
		return new ModelAndView("marktoprotect_languages");
	}

	/**
	 * Changes the language selected by the user
	 * 
	 * @param language
	 *            selected by the user
	 * @return a modelAndView object with the object
	 */
	@RequestMapping(value = "changeLanguage", method = RequestMethod.GET)
	@ResponseBody
	public String changeLanguage(String language, boolean first,
			boolean collective) {
		// Stores the new state
		if (first) {
			flowBean.setFirstLang(language);
		} else {
			flowBean.setSecLang(language);
		}
		return "success";
	}

	private static class LanguageInfo {
		private String first;
		private String second;
		private String firstLabel;
		private String secondLabel;
		private boolean secondLanguageTranslation;

		public String getFirst() {
			return first;
		}

		public void setFirst(String first) {
			this.first = first;
		}

		public String getSecond() {
			return second;
		}

		public void setSecond(String second) {
			this.second = second;
		}

		public boolean isSecondLanguageTranslation() {
			return secondLanguageTranslation;
		}

		public void setSecondLanguageTranslation(
				boolean secondLanguageTranslation) {
			this.secondLanguageTranslation = secondLanguageTranslation;
		}

		public String getFirstLabel() {
			return firstLabel;
		}

		public String getSecondLabel() {
			return secondLabel;
		}

		public void setFirstLabel(String firstLabel) {
			this.firstLabel = firstLabel;
		}

		public void setSecondLabel(String secondLabel) {
			this.secondLabel = secondLabel;
		}
	}

	@RequestMapping(value = "getLanguageInfo", headers = "Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public LanguageInfo getLanguageInfo(HttpServletRequest request) {
		
		LanguageInfo languageInfo = new LanguageInfo();
		languageInfo.setFirst(officeLanguage);
		languageInfo.setSecond(officeLanguage);

		return languageInfo;
	}

	/**
	 * Checks if the languages have been set, so the user can add GS or mark
	 * information
	 * 
	 * @return 'true' if the user can add GS or mark information
	 */
	@RequestMapping(value = "hasLanguage", method = RequestMethod.GET)
	@ResponseBody
	public String hasLanguage() {


		// Always we expect at least first language to be defined independently
		// of the configuration
		boolean firstLangAvailable =true;//StringUtils.isNotBlank(flowBean.getFirstLang())

		// we depend only if it is usable
		boolean secLangRequired = sectionViewConfiguration.getUsable(
				AvailableSection.LANGUAGES, "secLang",
				flowScopeDetails.getFlowModeId());

		if (secLangRequired) {
			return (firstLangAvailable
					&& StringUtils.isNotBlank(flowBean.getSecLang()) ? "true"
					: "false");
		} else {
			return (firstLangAvailable ? "true" : "false");
			
		
		}

	}

	/**
	 * It checks if the second language has been selected. If not, returns an
	 * predefined error message.
	 * 
	 * @return null if the second language was selected. String error otherwise
	 */
	@RequestMapping(value = "checkSecondLanguage", method = RequestMethod.POST)
	public ModelAndView checkSecondLanguage() {
		ModelAndView modelAndView = new ModelAndView("errors/alertErrors");
		if (StringUtils.isNotBlank(flowBean.getSecLang())) {
			modelAndView.addObject("alertMessage", "language.error.second");
		}
		return modelAndView;
	}
}
