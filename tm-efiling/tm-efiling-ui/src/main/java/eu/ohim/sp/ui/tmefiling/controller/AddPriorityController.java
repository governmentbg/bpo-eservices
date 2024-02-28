package eu.ohim.sp.ui.tmefiling.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.tmefiling.ui.form.validator.TMPriorityFormValidator;
import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;

@Controller
public class AddPriorityController extends AddAbstractController {

	private static final String VIEW_DETAILS= "detailsView";
	private static final String MODEL_PRIORITY_FORM = "priorityForm";

	@Autowired
	private TMFlowBean flowBean;
	
    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Autowired
	private TMPriorityFormValidator tmPriorityFormValidator;
    
	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		binder.setValidator(tmPriorityFormValidator);
	}
	
    /**
     * It returns a new Priority object if no parameters are passed, so a new
     * Priority to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the priority
     */
    @RequestMapping(value = "addPriority", method = RequestMethod.GET)
    public ModelAndView formBackingPriority(@RequestParam(required = false, value = "id") String id,
            @RequestParam(required = true, value = VIEW_DETAILS) String detailsView) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.CLAIM_PRIORITY_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        ModelAndView modelAndView = innerFormBackingObject(id, (eu.ohim.sp.common.ui.flow.FlowBean) flowBean, new AddParameters(PriorityForm.class,
        		MODEL_PRIORITY_FORM, "claim_priority", detailsView, maxNumber));
        
        
        return modelAndView;
    }

    /**
     * Adds or edits on the collection stored in the session
     * 
     * @param command
     *            object that contains the Priority information.
     * @param result
     *            manage the validation results of the form object
     * @return Priority object view with the new priority added
     */
    @PreAuthorize("hasRole('Priority_Add')")
    @RequestMapping(value = "addPriority_new", method = RequestMethod.POST)
    public ModelAndView onSubmitPriority(@ModelAttribute(MODEL_PRIORITY_FORM) @Validated PriorityForm command, BindingResult result,
            @RequestParam(required = true, value = VIEW_DETAILS) String detailsView,
            @RequestParam(required = true, value = "claimTable") String claimTable) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.CLAIM_PRIORITY_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        ModelAndView modelAndView = onSubmit(command, (eu.ohim.sp.common.ui.flow.FlowBean) flowBean, new AddParameters(PriorityForm.class, MODEL_PRIORITY_FORM,
                claimTable, detailsView, maxNumber), result);
        return modelAndView;
    }
    
}
