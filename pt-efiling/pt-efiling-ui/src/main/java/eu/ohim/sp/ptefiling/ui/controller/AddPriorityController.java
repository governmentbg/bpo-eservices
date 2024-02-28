package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.patent.PTPriorityForm;
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

/**
 * Created by Raya
 * 18.04.2019
 */
@Controller
public class AddPriorityController extends AddAbstractController {

    @Autowired
    private PTFlowBean ptFlowBean;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    private static final String MODEL_PRIORITY_FORM = "priorityForm";
    private static final String PRIORITY_FORM_VIEW = "claim/priority_details";
    private static final String PRIORITY_TABLE_VIEW = "claim/priority_card_list";


    @RequestMapping(value = "addPTPriority", method = RequestMethod.GET)
    public ModelAndView formBackingPTPriority(@RequestParam(required = false, value = "id") String id) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.CLAIM_PRIORITY_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        ModelAndView modelAndView = innerFormBackingObject(id, ptFlowBean, new AddParameters(PTPriorityForm.class,
                MODEL_PRIORITY_FORM, PRIORITY_FORM_VIEW, PRIORITY_FORM_VIEW, maxNumber));


        return modelAndView;
    }

    @PreAuthorize("hasRole('Priority_Add')")
    @RequestMapping(value = "addPTPriority", method = RequestMethod.POST)
    public ModelAndView onSubmitPTPriority(@ModelAttribute(MODEL_PRIORITY_FORM) PTPriorityForm command, BindingResult result) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.CLAIM_PRIORITY_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        ModelAndView modelAndView = onSubmit(command, ptFlowBean, new AddParameters(PTPriorityForm.class, MODEL_PRIORITY_FORM,
                PRIORITY_TABLE_VIEW, PRIORITY_FORM_VIEW, maxNumber), result);
        return modelAndView;
    }
}
