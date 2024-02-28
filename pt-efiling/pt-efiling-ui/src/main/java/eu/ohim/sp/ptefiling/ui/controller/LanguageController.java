package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.exception.NoResultsException;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LanguageController {
	/**
	 * Logger for this class and subclasses
	 */
	private static final Logger LOGGER = Logger.getLogger(LanguageController.class);

	@Autowired
	private PTFlowBean flowBean;
	
	@Autowired
	private SectionViewConfiguration sectionViewConfiguration;
	
	@Autowired
	private FlowScopeDetails flowScopeDetails;

	/**
	 * Handles FSP exceptions
	 * @param e the exception thrown on the controller context
	 * @return a model and view that will be handled in UI
	 */
	@ExceptionHandler(SPException.class)
	public ModelAndView handleException(Throwable e) {
		ModelAndView modelAndView = new ModelAndView("errors/errors");
		LOGGER.error(e);
		modelAndView.addObject("exception", e);
		return modelAndView;
	}
	
	/**
	 * Handles No Results exceptions
	 * @param e the exception thrown on the controller context
	 * @return a model and view that will be handled in UI
	 */
	@ExceptionHandler(NoResultsException.class)
	public ModelAndView handleNoResultsException(Throwable e) {
		ModelAndView modelAndView = new ModelAndView("errors/errors");
		LOGGER.error(e);
		modelAndView.addObject("exception", e);
		return modelAndView;
	}
	
	/**
	 * Ajax controller that is used to update the field change 
	 * MainForm.secondLanguageTranslation
	 * @param secondLanguageTranslation
	 * @return ModelAndView
	 */
	@RequestMapping(value = "changeProvideSecondLanguageTranslation", method = RequestMethod.GET)
	@ResponseBody
	public String changeLanguageTranslation(boolean secondLanguageTranslation) {
		flowBean.getMainForm().setSecondLanguageTranslation(secondLanguageTranslation);
		return "success";
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
	public String changeLanguage(String userLanguage, boolean first, boolean sendCorrespondenceInSecondLang) {
		// Stores the new state
		if (first) {
			flowBean.setFirstLang(userLanguage);
		} else {
			flowBean.setSecLang(userLanguage);
		}
		flowBean.getMainForm().setCorrespondenceLanguageCheckBox(sendCorrespondenceInSecondLang);
		return "success";
	}
	
	/**
	 * Checks if the languages have been set, so the user can add GS or mark information
	 * @return 'true' if the user can add GS or mark information
	 */
	@RequestMapping(value = "hasLanguage", method = RequestMethod.GET)
	@ResponseBody
	public String hasLanguage() {
		//Always we expect at least first language to be defined independently of the configuration
		boolean firstLangAvailable = StringUtils.isNotBlank(flowBean.getFirstLang());
		
		//we depend only if it is usable
		boolean secLangRequired =  sectionViewConfiguration.getUsable(AvailableSection.LANGUAGES, "secLang", flowScopeDetails.getFlowModeId());
		
		if (secLangRequired) {
			return (firstLangAvailable && StringUtils.isNotBlank(flowBean.getSecLang()) ? "true" : "false");
		} else {
			return (firstLangAvailable ? "true" : "false");
		}
	}

}