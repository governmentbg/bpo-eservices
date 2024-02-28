package eu.ohim.sp.dsefiling.ui.controller;

import javax.servlet.http.HttpServletRequest;

import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.ImportAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.ImportParameters;
import eu.ohim.sp.common.ui.controller.wrapper.ImportableWrapper;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;

/**
 * @author serrajo
 */
@Controller
public class ImportDesignerController extends ImportAbstractController {

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;
    
	@Autowired
	private DSFlowBean dsFlowBean;

	@Autowired
	private PersonServiceInterface personService;
	
	@Autowired
	private DSDesignsServiceInterface designsService;

	/**
	 * It returns a new Designer obj if no parameters are passed, so a new
	 * Designer to be created by the user. If a parameter is passed by the
	 * request then the object to be returned will be populated with the values
	 * stored in the session to edit its details.
	 *
	 * @param request
	 * @param identifier the id of the edited object, or a new object if it is null
	 * @return a modelAndView object with the object
	 */
	@PreAuthorize("hasRole('Designer_Import')")
	@RequestMapping(value = "importDesigner", method = RequestMethod.GET)
	public ModelAndView importForm(HttpServletRequest request, @ModelAttribute("id") String identifier) {
        if(StringUtils.isBlank(identifier)) {
            return null;
        }
		String flowModeId = (String) request.getAttribute("flowModeId");
        ModelAndView response = importForm(identifier, null, dsFlowBean, flowModeId);
        if(response.getModelMap().containsKey("errorCode")) {
            response.setViewName("errors/importError");
            String errorCode = response.getModelMap().get("errorCode").toString();
            response.addObject("errorCode", errorCode + ".designer");
        }
        return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ImportableWrapper importObject(String identifier, String office, String flowModeId) {
		// Import designer is not implemented yet, we only have the name that it will come in the identifier.

		DesignerForm uiForm = personService.importDesigner(identifier, flowModeId);

		designsService.fillDesignerDesignsListsFromToLink(dsFlowBean, uiForm);
		Integer maxNoOfEntities = getIntegerSetting(configurationServiceDelegator,
                                                    ConfigurationServiceDelegatorInterface.DESIGNER_ADD_MAXNUMBER,
                                                    ConfigurationServiceDelegatorInterface.PERSON_COMPONENT);
        ImportableWrapper wrapper = new ImportableWrapper();
		wrapper.setImportable(uiForm);
		// When the designer is imported we have to check if he is a group o if he waives and set the correct view.
		// Check designer_card.jsp and review_designer.jsp to display the designer id in the table.
	    wrapper.setImportParameters(new ImportParameters(DesignerForm.class, "designerForm", "designers/designer_notagroup", maxNoOfEntities, "designers"));
        return wrapper;
	}
	
}
