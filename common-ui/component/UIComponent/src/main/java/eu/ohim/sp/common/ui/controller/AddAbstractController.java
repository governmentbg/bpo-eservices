/*******************************************************************************
 * * $Id:: AddAbstractController.java 58309 2013-03-05 08:17:54Z soriama $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.FieldPropertyEditor;
import eu.ohim.sp.common.ui.NotAllowedCharsPropertyEditor;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.exception.MaximumEntitiesException;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.NotAllowedCharsForm;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.claim.SeniorityKindForm;
import eu.ohim.sp.common.ui.form.person.PersonForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.util.EnumEditor;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.common.ui.validator.Validatable;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AddAbstractController {
	private static final Logger logger = Logger.getLogger(AddAbstractController.class);

	/**
	 * session bean
	 */
	@Autowired
	protected FlowBean flowBean;

	@Autowired
	private FormValidator validator;

	@Autowired
	protected FlowScopeDetails flowScopeDetails;

	@Autowired
	private SectionViewConfiguration sectionViewConfiguration;

	@Value("${persons.import.validation}")
	private boolean validateImported;

	/**
	 * Custom Date Editor
	 */
	@Autowired
	private CustomDateEditor customDateEditor;

	@Autowired
	private FieldPropertyEditor fieldPropertyEditor;

	@Autowired
	private ConfigurationServiceDelegatorInterface configurationService;

	protected Integer maxNumberToAdd = -1;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, customDateEditor);
		binder.registerCustomEditor(String.class, fieldPropertyEditor);
		binder.registerCustomEditor(SeniorityKindForm.class, new EnumEditor(SeniorityKindForm.class));
		binder.registerCustomEditor(NotAllowedCharsForm.class, new NotAllowedCharsPropertyEditor());
	}

	public <T extends AbstractForm> ModelAndView innerFormBackingObject(String id, FlowBean flowBean,
																		AddParameters addParameters) throws SPException {
		logger.info("START formBackingObject");
		if (flowBean == null)
			throw new SPException("Null flow bean");
		if (addParameters == null)
			throw new SPException("Null add parameters", "error.genericError");
		ModelAndView modelAndView = new ModelAndView(addParameters.getFormView());

		Class<? extends AbstractForm> cmdClass = addParameters.getCommandClass();
		Integer entityPosition = null;
		try {
			if (StringUtils.isNotBlank(id)) {
				AbstractForm sessionObject = flowBean.getObject(cmdClass, id);
				entityPosition = flowBean.getIndex(cmdClass, id);

				if (sessionObject != null) {
					AbstractForm clonedObject = sessionObject.clone();

					if (clonedObject != null) {
						modelAndView.addObject(addParameters.getCommandName(), clonedObject);
						modelAndView.addObject("entityPosition", 1 + (entityPosition != null ? entityPosition
								: flowBean.getCollection(cmdClass).size()));
						return modelAndView;
					}
				}
			} else if (addParameters.getMaximumEntities() != null) {
				if (flowBean.getCollection(addParameters.getCommandClass()) != null
						&& flowBean.getCollection(addParameters.getCommandClass()).size() >= addParameters
						.getMaximumEntities())
					throw new MaximumEntitiesException("Maximum entities reached", new Exception(), "error.ef.max."
							+ addParameters.getCommandClass().getSimpleName(), addParameters.getMaximumEntities()
							.toString());
			}
			logger.info("END formBackingObject");
			modelAndView.addObject("entityPosition", 1 + (entityPosition != null ? entityPosition : flowBean
					.getCollection(cmdClass).size()));
			modelAndView.addObject(addParameters.getCommandName(), cmdClass.newInstance());
		} catch (InstantiationException e) {
			throw new SPException("Failed to find any object", e, "error.genericError");
		} catch (IllegalArgumentException e) {
			throw new SPException("Failed to find any object", e, "error.genericError");
		} catch (CloneNotSupportedException e) {
			throw new SPException("Failed to find duplicate object", e, "error.genericError");
		} catch (IllegalAccessException e) {
			throw new SPException("Failed to generate any object", e, "error.genericError");
		}

		return modelAndView;
	}

	/**
	 * Handles the submission of the abstract form. If the given form is not imported,
	 * validation for the form is triggered, and if it finds that the form object is not valid,
	 * it returns the form view.
	 * <p/>
	 * If the object is imported and valid or is not imported: it proceeds by adding it to the flow.
	 * If it already exists in the flow, it replaces it with the new version.
	 * The success view is returned in this case.
	 *
	 * @param command
	 * @param flowBean
	 * @param addParameters
	 * @param result
	 * @return modelAndView created from command, flowBean, addParameters and result params
	 * @throws SPException
	 */
	public ModelAndView onSubmit(AbstractImportableForm command, FlowBean flowBean, AddParameters addParameters,
								 BindingResult result) throws SPException {
		logger.info("START onSubmit");
		if (command == null)
			throw new SPException("Command is null", "error.genericError");
		if (flowBean == null)
			throw new SPException("Flow is null", "error.genericError");
		if (addParameters == null)
			throw new SPException("AddParameters is null", "error.genericError");
		if (result == null)
			throw new SPException("Binding result is null", "error.genericError");

		ModelAndView modelAndView = null;

		if (!command.getImported() || command.getValidateImported()) {
			boolean validObject = validateCommand(command, result, addParameters);
			if (!validObject) {
				modelAndView = new ModelAndView(addParameters.getFormView());
				if (flowBean.existsObject(addParameters.getCommandClass(), command.getId())) {
					modelAndView.addObject("entityPosition",
							1 + flowBean.getIndex(addParameters.getCommandClass(), command.getId()));
				} else {
					modelAndView.addObject("entityPosition", 1 + flowBean
							.getCollection(addParameters.getCommandClass()).size());
				}
				return modelAndView;
			}
		}

		modelAndView = new ModelAndView(addParameters.getSuccessView());
		logger.debug("add " + addParameters.getCommandClass());

		try {
			String id = command.getId();

			if (!flowBean.existsObject(addParameters.getCommandClass(), id)) {
				flowBean.addObject(command);
			} else {
				flowBean.replaceObject(command, id);
			}
		} catch (IllegalArgumentException e) {
			logger.error(e);
			throw new SPException("Failed to find any object", e, "error.genericError");
		}
		

		return modelAndView;
	}

	private boolean validateImported(AbstractImportableForm command) {
		if((command instanceof PersonForm || command instanceof SignatureForm)&& validateImported) {
			return true;
		}
		return false;
	}

	public boolean validateCommand(Validatable command, BindingResult result, AddParameters addParameters) {
		if (addParameters.isTriggerValidation()) {
			validator.validate(command, result, flowScopeDetails);
			if (result.hasErrors())
				return false;
		}
		return true;
	}

	public FormValidator getValidator() {
		return validator;
	}

	public void setValidator(FormValidator validator) {
		this.validator = validator;
	}

	protected Integer getIntegerSetting(ConfigurationServiceDelegatorInterface configurationServiceDelegator,
										String setting, String component) throws SPException {
		String result = configurationServiceDelegator.getValue(setting, component);
		try {
			if (result == null || result.isEmpty())
				return null;
			int max = Integer.parseInt(result);
			return max;
		} catch (NumberFormatException e) {
			throw new SPException("Error while parsing a configuration integer", e, "error.configuration.parsing");
		}
	}

	//[component,property]
	protected String[] resolveMaxNumberProperty() {
		return null;
	}

	protected Integer getMaxNumber() {
		if (maxNumberToAdd == -1) { //not initialized
			maxNumberToAdd = Integer.MAX_VALUE;
			if (resolveMaxNumberProperty() != null && resolveMaxNumberProperty().length == 2) {
				Integer propertyValue = getIntegerSetting(configurationService,
						resolveMaxNumberProperty()[1], resolveMaxNumberProperty()[0]);
				if (propertyValue != null) {
					maxNumberToAdd = propertyValue;
				}
			}
		}
		return maxNumberToAdd;
	}


	protected ModelAndView addFormCheckMatches(AbstractImportableForm command, BindingResult result, AddParameters addParameters,
											   Boolean ignoreMatches) {
		// if the ignoreMatches boolean is set to true
		// add the applicant without checking for matches
		if (ignoreMatches != null && ignoreMatches) {
			return onSubmit(command, flowBean, addParameters, result);
		}

		// otherwise, validate the form before calling the match service
		ModelAndView mv = new ModelAndView();
		boolean validObject = validateCommand(command, result, addParameters);
		if (!validObject) {
			mv.setViewName(addParameters.getFormView());
			return mv;
		} else {
			mv = new ModelAndView();
			// when no model errors are found
			// set the validation trigger to false so that validation is not activated twice
			addParameters.setTriggerValidation(false);
		}

		return mv;
	}

	/**
	 * Handles SP exceptions
	 *
	 * @param e the exception thrown on the controller context
	 * @return a model and view that will be handled in UI
	 */
	@ExceptionHandler(SPException.class)
	public ModelAndView handleException(Throwable e) {
		ModelAndView modelAndView = new ModelAndView("errors/errors");
		logger.error(e);
		modelAndView.addObject("exception", e);
		return modelAndView;
	}

	public void resetCommandImportedDetails(ModelAndView model, AbstractImportableForm form, String resetConfigKey){
		if(form.getImported()) {
			String resetConfig = configurationService.getValue(resetConfigKey, configurationService.PERSON_COMPONENT);
			if (resetConfig != null && resetConfig.equalsIgnoreCase("true")) {
				form.setImported(false);
				form.setId(null);
				model.addObject("formImportReset", "true");
			}
		}
	}

	public void resetCommandImportedDetails(List<ObjectError> objectErrorList, AvailableSection section,
											ModelAndView model, AbstractImportableForm form, String resetConfigKey){
		if(form.getImported()) {
			Optional<Boolean> allEditableImport = objectErrorList.stream().filter(e -> e instanceof FieldError).map(e -> {
					Boolean editableImport = sectionViewConfiguration.getEditableImport(section, ((FieldError) e).getField(), flowScopeDetails.getFlowModeId());
					return editableImport != null ? editableImport : false;
			}).reduce((edt1, edt2) -> edt1 && edt2);

			if(allEditableImport.isPresent() && !allEditableImport.get()) {
				String resetConfig = configurationService.getValue(resetConfigKey, configurationService.PERSON_COMPONENT);
				if (resetConfig != null && resetConfig.equalsIgnoreCase("true")) {
					form.setImported(false);
					form.setId(null);
					model.addObject("formImportReset", "true");
				}
			}
		}
	}
}
