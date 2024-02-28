package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.ui.controller.parameters.ImportParameters;
import eu.ohim.sp.common.ui.controller.wrapper.ImportableWrapper;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ImportESDesignerController extends ImportAbstractController {

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;
    
    @Autowired
    private PersonServiceInterface personService;

	@Autowired
	protected FlowBean flowBean;

	@PreAuthorize("hasRole('Designer_Import')")
	@RequestMapping(value = "importESDesigner", method = RequestMethod.GET)
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
            response.addObject("errorCode", errorCode + ".designer");
        }
        return response;
	}

	@Override
	protected ImportableWrapper importObject(String identifier, String office, String flowModeId) {
		DesignerForm uiForm = personService.importDesigner(identifier, flowModeId);

		Integer maxNoOfEntities = getIntegerSetting(configurationServiceDelegator,
                                                    ConfigurationServiceDelegatorInterface.DESIGNER_ADD_MAXNUMBER,
                                                    "eu.ohim.sp.core.person");
		ImportableWrapper wrapper = new ImportableWrapper();
		if (uiForm!=null) {
	        wrapper.setImportable(uiForm);
			wrapper.setImportParameters(new ImportParameters(DesignerForm.class, "designerForm",
					"designer/designer_naturalperson", maxNoOfEntities,
					"designers"));
		}
        return wrapper;
	}
}
