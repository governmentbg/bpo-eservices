package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.ui.controller.ImportAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.ImportParameters;
import eu.ohim.sp.common.ui.controller.wrapper.ImportableWrapper;
import eu.ohim.sp.common.ui.form.person.InventorForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author serrajo
 */
@Controller
public class ImportInventorController extends ImportAbstractController {

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;
    
	@Autowired
	private PTFlowBean ptFlowBean;

	@Autowired
	private PersonServiceInterface personService;


	@PreAuthorize("hasRole('Inventor_Import')")
	@RequestMapping(value = "importInventor", method = RequestMethod.GET)
	public ModelAndView importForm(HttpServletRequest request, @ModelAttribute("id") String identifier) {
        if(StringUtils.isBlank(identifier)) {
            return null;
        }
		String flowModeId = (String) request.getAttribute("flowModeId");
        ModelAndView response = importForm(identifier, null, ptFlowBean, flowModeId);
        if(response.getModelMap().containsKey("errorCode")) {
            response.setViewName("errors/importError");
            String errorCode = response.getModelMap().get("errorCode").toString();
            response.addObject("errorCode", errorCode + ".inventor");
        }
        return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ImportableWrapper importObject(String identifier, String office, String flowModeId) {
		// Import designer is not implemented yet, we only have the name that it will come in the identifier.

		InventorForm uiForm = personService.importInventor(identifier, flowModeId);

		Integer maxNoOfEntities = getIntegerSetting(configurationServiceDelegator,
                                                    ConfigurationServiceDelegatorInterface.INVENTOR_ADD_MAXNUMBER,
                                                    ConfigurationServiceDelegatorInterface.PERSON_COMPONENT);
        ImportableWrapper wrapper = new ImportableWrapper();
		wrapper.setImportable(uiForm);
		// When the designer is imported we have to check if he is a group o if he waives and set the correct view.
		// Check designer_card.jsp and review_designer.jsp to display the designer id in the table.
	    wrapper.setImportParameters(new ImportParameters(InventorForm.class, "inventorForm", "inventor/inventor_notagroup", maxNoOfEntities, "inventors"));
        return wrapper;
	}
	
}
