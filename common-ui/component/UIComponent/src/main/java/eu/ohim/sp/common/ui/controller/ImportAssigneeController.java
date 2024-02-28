package eu.ohim.sp.common.ui.controller;

import javax.servlet.http.HttpServletRequest;

import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.adapter.AssigneeFactory;
import eu.ohim.sp.common.ui.controller.parameters.ImportParameters;
import eu.ohim.sp.common.ui.controller.wrapper.ImportableWrapper;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.AssigneeForm;
import eu.ohim.sp.common.ui.form.person.AssigneeLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.AssigneeNaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonSpecialForm;
import eu.ohim.sp.common.ui.form.person.UniversityForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;

@Controller
public class ImportAssigneeController extends ImportAbstractController {
	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

	@Autowired
	private PersonServiceInterface personService;
	
	@Autowired
	private AssigneeFactory assigneeFactory;

	@Autowired
	private FlowScopeDetails flowScopeDetails;

	/**
	 * flow bean
	 */
	@Autowired
	protected FlowBean flowBean;

	/**
	 * It returns a new Assignee obj if no parameters are passed, so a new
	 * Assignee to be created by the user. If a parameter is passed by the
	 * request then the object to be returned will be populated with the values
	 * stored in the session to edit its details.
	 *
	 * @param request
	 * @param identifier the id of the edited object, or a new object if it is null
	 * @return a modelAndView object with the object
	 */
	@PreAuthorize("hasRole('Assignee_Import')")
	@RequestMapping(value = "importAssignee", method = RequestMethod.GET)
	public ModelAndView importForm(HttpServletRequest request, @ModelAttribute("id") String identifier, BindingResult result) {
		if(StringUtils.isBlank(identifier))
		{
			return null;
		}
		String flowModeId = (String) request.getAttribute("flowModeId");
		ModelAndView response = importForm(identifier, null, flowBean, flowModeId);
		if(response.getModelMap().containsKey("errorCode"))
		{
			response.setViewName("errors/importError");
			String errorCode = response.getModelMap().get("errorCode").toString();
			String applicationType = flowScopeDetails.getFlowModeId();
			response.addObject("errorCode", errorCode + ".assignee");
		}
		return response;
	}

	@Override
	protected ImportableWrapper importObject(String identifier, String office, String flowModeId) {
		ApplicantForm uiForm = personService.importApplicant(identifier, flowModeId);

		if (uiForm!=null && !contains(configurationServiceDelegator.getApplicantTypes(flowModeId)
				,((uiForm.getType().name().toLowerCase())))) {
			return null;
		}

		Integer maxNoOfEntities = getIntegerSetting(configurationServiceDelegator,
				ConfigurationServiceDelegatorInterface.ASSIGNEE_ADD_MAXNUMBER,
				"eu.ohim.sp.core.person");
		ImportableWrapper wrapper = new ImportableWrapper();
		AssigneeForm theImportable = assigneeFactory.convertFromApplicantForm(uiForm);
		if (theImportable !=null) {
			wrapper.setImportable(theImportable);
			switch (theImportable.getType()) {
			case LEGAL_ENTITY: {
				wrapper.setImportParameters(new ImportParameters(AssigneeLegalEntityForm.class, "assigneeLegalEntityForm",
						"assignee/assignee_legalentity", maxNoOfEntities,
						"assignees"));
				break;
			}
			case NATURAL_PERSON: {
				wrapper.setImportParameters(new ImportParameters(AssigneeNaturalPersonForm.class, "assigneeNaturalPersonForm",
						"assignee/assignee_naturalperson", maxNoOfEntities,
						"assignees"));
				break;
			}
			
			}
		}
		return wrapper;
	}
}
