package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.patent.PTDivisionalApplicationForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddDivisionalApplicationController extends AddAbstractController {

	@Autowired
	private PTFlowBean ptFlowBean;

	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;


    private static final String VIEW_ADD_DIVISIONAL_APPLICATION = "claim/divisional_application_details";
    private static final String VIEW_TABLE_DIVISIONAL_APPLICATION = "claim/divisional_application_card_list";
    private static final String MODEL_DIVISIONAL_APPLICATION = "divisionalApplicationForm";

	/**
	 * To add a divisional application.
	 * @return Model and view.
	 */
	@RequestMapping(value = "addPTDivisionalApplication", method = RequestMethod.GET)
	public ModelAndView addDivisionalApplication(@RequestParam(required = false, value = "id") String id) {
		Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
				ConfigurationServiceDelegatorInterface.CLAIM_DIVISIONAL_APPLICATION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
		AddParameters addParameters = new AddParameters(PTDivisionalApplicationForm.class, MODEL_DIVISIONAL_APPLICATION, null, VIEW_ADD_DIVISIONAL_APPLICATION, maxNumber);
		ModelAndView modelAndView = innerFormBackingObject(id, ptFlowBean, addParameters);
		return modelAndView;
	}

	/**
	 * To save a divisional application.
	 * @param divisisionalApplicationForm Model.
	 * @param result Binding result.
	 * @return Model and view.
	 */
	@PreAuthorize("hasRole('DivisionalApplication_Add')")
    @RequestMapping(value = "addPTDivisionalApplication", method = RequestMethod.POST)
    public ModelAndView saveDivisionalApplication(@ModelAttribute(MODEL_DIVISIONAL_APPLICATION) PTDivisionalApplicationForm divisisionalApplicationForm, BindingResult result) {
		Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
				ConfigurationServiceDelegatorInterface.CLAIM_DIVISIONAL_APPLICATION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
    	AddParameters addParameters = new AddParameters(PTDivisionalApplicationForm.class, MODEL_DIVISIONAL_APPLICATION, VIEW_TABLE_DIVISIONAL_APPLICATION, VIEW_ADD_DIVISIONAL_APPLICATION, maxNumber);
    	ModelAndView modelAndView = onSubmit(divisisionalApplicationForm, ptFlowBean, addParameters, result);
    	return modelAndView;
    }
    
}
