package eu.ohim.sp.dsefiling.ui.controller;

import javax.servlet.http.HttpServletRequest;

import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.dsefiling.ui.service.interfaces.ImportServiceInterface;
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
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSImportDesignsServiceInterface;

/**
 * @author serrajo
 */
@Controller
public class ImportDesignController extends ImportAbstractController {

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

	@Autowired
	private DSFlowBean dsFlowBean;

	@Autowired
	private DSDesignsServiceInterface dsDesignsService;

	@Autowired
	private DSImportDesignsServiceInterface dsImportDesignsService;

	@Autowired
    private ImportServiceInterface importServiceInterface;

	@PreAuthorize("hasRole('Import_Previous')")
	@RequestMapping(value = "importDesign", method = RequestMethod.GET)
	public ModelAndView importForm(HttpServletRequest request, @ModelAttribute("id") String id) {
        if(StringUtils.isBlank(id)) {
            return null;
        }
		String flowModeId = (String) request.getAttribute("flowModeId");
        ModelAndView response = importForm(id, null, dsFlowBean, flowModeId);
        if(response.getModelMap().containsKey("errorCode")) {
            response.setViewName("errors/importError");
            String errorCode = response.getModelMap().get("errorCode").toString();
            response.addObject("errorCode", errorCode + ".design");
        } else {
        	// OK
        	DesignForm designForm = (DesignForm) response.getModel().get("designForm");
        	designForm.setId(id);
            if(designForm.getLocarno() != null){
                for(LocarnoAbstractForm form: designForm.getLocarno()){
                    importServiceInterface.validateLocarnoForm(dsFlowBean.getFirstLang(), form);
                }
            }
        	dsFlowBean.addObject(designForm);
        	dsDesignsService.addDesignToLists(designForm, dsFlowBean);
        }
        return response;
	}

	@PreAuthorize("hasRole('Import_Previous')")
	@RequestMapping(value = "getApplicationsForDesignId", method = RequestMethod.GET)
	public ModelAndView getApplicationsForDesignId(HttpServletRequest request, @ModelAttribute("id") String id) {
		ModelAndView modelAndView = new ModelAndView("importPreviousDesign/selectApplicationToImport");
		modelAndView.addObject("applicationsList", dsDesignsService.getDesignApplicationsForDesignId(id));
		modelAndView.addObject("registrationNumber", id);

		return modelAndView;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ImportableWrapper importObject(String identifier, String office, String flowModeId) {
		DesignForm uiForm = dsImportDesignsService.importDesign(identifier, dsFlowBean);
		Integer maxNoOfEntities = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.DESIGN_ADD_MAXNUMBER,
                ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
		ImportableWrapper wrapper = new ImportableWrapper();
		wrapper.setImportable(uiForm);
		wrapper.setImportParameters(new ImportParameters(DesignForm.class, "designForm", "designs/designs_list", maxNoOfEntities, "designs"));
        return wrapper;
	}
	
}
