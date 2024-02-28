package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.tmefiling.ui.form.validator.TMExhPriorityFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddExhibitionPriorityController extends AddAbstractController {

    /**
     * Logger for this class and subclasses
     */
    private static final Logger logger = Logger.getLogger(AddExhibitionPriorityController.class);

    /**
     * session bean
     */
    @Autowired
    private FlowBean flowBean;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Autowired
    private TMExhPriorityFormValidator tmExhPriorityFormValidator;

    @Override
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        super.initBinder(binder);
        binder.setValidator(tmExhPriorityFormValidator);
    }

    /* --------- EXHIBITION ---------- */
    /* ------------------------------- */

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
                                               @RequestParam(required = true, value = "detailsView") String detailsView) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.CLAIM_EXHIBITION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        return innerFormBackingObject(id, flowBean, new AddParameters(ExhPriorityForm.class, "exhPriorityForm",
                "claim_exhibition", detailsView, maxNumber));
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
    @RequestMapping(value = "addExhPriority", method = RequestMethod.POST)
    public ModelAndView onSubmitExhPriority(@ModelAttribute("exhPriorityForm") @Validated ExhPriorityForm command,
                                            BindingResult result, @RequestParam(required = true, value = "detailsView") String detailsView,
                                            @RequestParam(required = true, value = "claimTable") String claimTable) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.CLAIM_EXHIBITION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        return onSubmit(command, flowBean, new AddParameters(ExhPriorityForm.class, "exhPriorityForm", claimTable,
                detailsView, maxNumber), result);
    }
}
