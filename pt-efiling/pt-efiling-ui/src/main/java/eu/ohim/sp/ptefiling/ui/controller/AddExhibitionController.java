package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
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
public class AddExhibitionController extends AddAbstractController {

	@Autowired
	private PTFlowBean ptFlowBean;

	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;


    private static final String VIEW_ADD_EXHIBITION = "claim/exhibition_details";
    private static final String VIEW_TABLE_EXHIBITION = "claim/exhibition_card_list";
    private static final String MODEL_EXHIBITION = "exhibitionForm";


	@RequestMapping(value = "addPTExhibition", method = RequestMethod.GET)
	public ModelAndView addPTExhibition(@RequestParam(required = false, value = "id") String id) {
		Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
				ConfigurationServiceDelegatorInterface.CLAIM_EXHIBITION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
		AddParameters addParameters = new AddParameters(ExhPriorityForm.class, MODEL_EXHIBITION, null, VIEW_ADD_EXHIBITION, maxNumber);
		ModelAndView modelAndView = innerFormBackingObject(id, ptFlowBean, addParameters);
		return modelAndView;
	}


	@PreAuthorize("hasRole('Exhibition_Priority_Add')")
    @RequestMapping(value = "addPTExhibition", method = RequestMethod.POST)
    public ModelAndView savePTExhibition(@ModelAttribute(MODEL_EXHIBITION) ExhPriorityForm exhPriorityForm, BindingResult result) {
		Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
				ConfigurationServiceDelegatorInterface.CLAIM_EXHIBITION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
    	AddParameters addParameters = new AddParameters(ExhPriorityForm.class, MODEL_EXHIBITION, VIEW_TABLE_EXHIBITION, VIEW_ADD_EXHIBITION, maxNumber);
    	ModelAndView modelAndView = onSubmit(exhPriorityForm, ptFlowBean, addParameters, result);
    	return modelAndView;
    }
    
}
