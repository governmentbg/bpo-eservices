package eu.ohim.sp.dsefiling.ui.controller;

import org.apache.commons.lang.StringUtils;
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

import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.form.validator.DSExhPriorityFormValidator;

@Controller
public class AddExhibitionController extends AddDesignsLinkedController {

	private static final String VIEW_DETAILS= "detailsView";
	private static final String MODEL_EXHPRIORITY_FORM = "exhPriorityForm";

	@Autowired
	private DSFlowBean flowBean;
	
    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Autowired
	private DSExhPriorityFormValidator dsExhPriorityFormValidator;
	
	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		binder.setValidator(dsExhPriorityFormValidator);
	}
	
    /**
     * It returns a new Exhibition Priority obj if no parameters are passed, so
     * a new Exhibition Priority to be created by the user. If a parameter is
     * passed by the request then the object to be returned will be populated
     * with the values stored in the session to edit its details.
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the Exhibition Priority object
     */
    @RequestMapping(value = "addExhPriority", method = RequestMethod.GET)
    public ModelAndView formBackingExhPriority(@RequestParam(required = false, value = "id") String id,
            @RequestParam(required = true, value = VIEW_DETAILS) String detailsView) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator, ConfigurationServiceDelegatorInterface.CLAIM_EXHIBITION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        ModelAndView modelAndView = innerFormBackingObject(id, flowBean, new AddParameters(DSExhPriorityForm.class, MODEL_EXHPRIORITY_FORM,
                "claim_exhibition", detailsView, maxNumber));
        
        if (StringUtils.isBlank(id)) { 
        	// When edit we don't have to fill in the lists again
        	fillListsDesignsFromToLink(modelAndView, MODEL_EXHPRIORITY_FORM);
        }

        return modelAndView;
    }

    /**
     * Adds or edits on the collection stored in the session
     * 
     * @param command
     *            object that contains the GoodAndService information.
     * @param result
     *            manage the validation results of the form object
     * @return GoodAndService object view with the new goodandservice added
     */
    @PreAuthorize("hasRole('Exhibition_Priority_Add')")
    @RequestMapping(value = "addExhPriority", method = RequestMethod.POST)
    public ModelAndView onSubmitExhPriority(@ModelAttribute(MODEL_EXHPRIORITY_FORM) @Validated DSExhPriorityForm command,
            BindingResult result, @RequestParam(required = true, value = VIEW_DETAILS) String detailsView,
            @RequestParam(required = true, value = "claimTable") String claimTable) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator, ConfigurationServiceDelegatorInterface.CLAIM_EXHIBITION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        return onSubmit(command, flowBean, new AddParameters(DSExhPriorityForm.class, MODEL_EXHPRIORITY_FORM, claimTable,
                detailsView, maxNumber), result);
    }

}
