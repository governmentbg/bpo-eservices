package eu.ohim.sp.dsefiling.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;

@Controller
public class AddDivisionalApplicationController extends AddDesignsLinkedController {

	@Autowired
	private DSFlowBean dsFlowBean;
	
	@Autowired
	private DSDesignsServiceInterface designsService;

    private static final String VIEW_ADD_DIVISIONAL_APPLICATION = "divisionalApplication/divisional_application";
    private static final String VIEW_TABLE_DIVISIONAL_APPLICATION = "divisionalApplication/divisional_application_table";
    private static final String MODEL_DIVISIONAL_APPLICATION = "divisionalApplicationForm";

	/**
	 * To add a divisional application.
	 * @return Model and view.
	 */
	@RequestMapping(value = "addDivisionalApplication", method = RequestMethod.GET)
	public ModelAndView addDivisionalApplication() {
		AddParameters addParameters = new AddParameters(DSDivisionalApplicationForm.class, MODEL_DIVISIONAL_APPLICATION, null, VIEW_ADD_DIVISIONAL_APPLICATION, 1);
		ModelAndView modelAndView = innerFormBackingObject(null, dsFlowBean, addParameters);
		fillListsDesignsFromToLink(modelAndView, MODEL_DIVISIONAL_APPLICATION);

        // We have to clear the possible Locarno classes added to the flow.
		designsService.clearLocarnoFlow(dsFlowBean);

		return modelAndView;
	}

	/**
	 * To save a divisional application.
	 * @param divisisionalApplicationForm Model.
	 * @param result Binding result.
	 * @return Model and view.
	 */
	@PreAuthorize("hasRole('DivisionalApplication_Add')")
    @RequestMapping(value = "saveDivisionalApplication", method = RequestMethod.POST)
    public ModelAndView saveDivisionalApplication(@ModelAttribute(MODEL_DIVISIONAL_APPLICATION) DSDivisionalApplicationForm divisisionalApplicationForm, BindingResult result) {
    	AddParameters addParameters = new AddParameters(DSDivisionalApplicationForm.class, MODEL_DIVISIONAL_APPLICATION, VIEW_TABLE_DIVISIONAL_APPLICATION, VIEW_ADD_DIVISIONAL_APPLICATION, 1);
    	
    	// We set Locarno to the divisionalApplication form to run the validations, but before we remove the previous ones.
    	designsService.clearLocarnoForm(divisisionalApplicationForm);
    	designsService.addFlowLocarnoToForm(dsFlowBean, divisisionalApplicationForm);
    	
    	ModelAndView modelAndView = onSubmit(divisisionalApplicationForm, dsFlowBean, addParameters, result);
    	if (modelAndView.getViewName().equals(VIEW_TABLE_DIVISIONAL_APPLICATION)) { 
    		// success
    		designsService.clearLocarnoFlow(dsFlowBean);
    	} else {
    		// error
    		designsService.clearLocarnoForm(divisisionalApplicationForm);
    	}
    	return modelAndView;
    }
    
    /**
     * To get the divisional application for editing.
     * @param id divisional application id.
     * @return Model and view.
     */
    @RequestMapping(value = "getDivisionalApplication", method = RequestMethod.GET)
    public ModelAndView getDivisionalApplication(@RequestParam(required = true, value = "id") String id) {
    	ModelAndView modelAndView = innerFormBackingObject(id, dsFlowBean, new AddParameters(DSDivisionalApplicationForm.class, MODEL_DIVISIONAL_APPLICATION, null, VIEW_ADD_DIVISIONAL_APPLICATION, 1));
    	DSDivisionalApplicationForm divisionalApplicationForm = (DSDivisionalApplicationForm) modelAndView.getModel().get(MODEL_DIVISIONAL_APPLICATION);
    	
        // We have to clear the possible Locarno classes added to the flow.
		designsService.clearLocarnoFlow(dsFlowBean);
		// And add the Locarno classes added in the divisional application to the flow.
		designsService.addFormLocarnoToFlow(divisionalApplicationForm, dsFlowBean);
		
		return modelAndView;
    }
    
}
