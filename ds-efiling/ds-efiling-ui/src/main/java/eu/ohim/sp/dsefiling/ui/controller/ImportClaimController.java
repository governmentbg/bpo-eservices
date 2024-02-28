package eu.ohim.sp.dsefiling.ui.controller;

import javax.servlet.http.HttpServletRequest;

import eu.ohim.sp.common.ui.controller.parameters.ImportParameters;
import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSImportDesignsServiceInterface;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.ImportAbstractController;
import eu.ohim.sp.common.ui.controller.wrapper.ImportableWrapper;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;

import java.util.Date;

/**
 * @author serrajo
 */
@Controller
public class ImportClaimController extends ImportAbstractController {

    /**
	 * flow bean
	 */
	@Autowired
	private DSFlowBean dsFlowBean;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Autowired
    private DSImportDesignsServiceInterface dsImportDesignsService;

    @Autowired
    private CustomDateEditor customDateEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, customDateEditor);
    }


    /**
	 * It returns a new DSPriority obj if no parameters are passed, so a new
	 * DSPriority to be created by the user. If a parameter is passed by the
	 * request then the object to be returned will be populated with the values
	 * stored in the session to edit its details.
	 *
	 * @param request
	 * @param identifier the id of the edited object, or a new object if it is null
	 * @return a modelAndView object with the object
	 */
	@PreAuthorize("hasRole('Priority_Import')")
	@RequestMapping(value = "importPriority", method = RequestMethod.GET)
	public ModelAndView importPriority(HttpServletRequest request, @ModelAttribute("id") String identifier, @ModelAttribute("office") String office) {
        if(StringUtils.isBlank(identifier) || StringUtils.isBlank(office)) {
            return null;
        }
		String flowModeId = (String) request.getAttribute("flowModeId");
        ModelAndView response = importForm(identifier, office, dsFlowBean, flowModeId);
        if(response.getModelMap().containsKey("errorCode")) {
            response.setViewName("errors/importError");
            String errorCode = response.getModelMap().get("errorCode").toString();
            response.addObject("errorCode", errorCode + ".priority");
        }
        return response;
	}

	@Override
	protected ImportableWrapper importObject(String identifier, String office, String flowModeId) {
        DSPriorityForm priorityForm = dsImportDesignsService.importPriority(identifier,office,dsFlowBean);
        ImportableWrapper wrapper = new ImportableWrapper();
        if (priorityForm != null) {
            Integer maxNoOfEntities = getIntegerSetting(configurationServiceDelegator,
                    ConfigurationServiceDelegatorInterface.CLAIM_PRIORITY_ADD_MAXNUMBER,
                    ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
            wrapper.setImportable(priorityForm);
            wrapper.setImportParameters(new ImportParameters(DSPriorityForm.class, "priorityForm",
                    "claim/claim_priority_details_manual_wizard", maxNoOfEntities, "priorities"));
        }
        return wrapper;
	}

}
