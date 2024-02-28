package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.trademark.ForeignRegistrationForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 09.05.2022
 * Time: 11:52
 */
@Controller
public class AddForeignRegistrationController extends AddAbstractController {

    // Models
    private static final String FORM = "foreignRegistrationForm";

    // Views
    private static final String VIEW_DETAILS = "foreign_registration/foreign_registration_details";
    private static final String VIEW_LIST = "foreign_registration/foreign_registration_card_list";

    @Autowired
    private FlowBean flowBean;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Override
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        super.initBinder(binder);
    }

    @RequestMapping(value = "addForeignRegistration", method = RequestMethod.GET)
    public ModelAndView formBackingForeignRegistration(@RequestParam(required = false, value = "id") String id) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.FOREIGN_REGISTRATION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        return innerFormBackingObject(id, flowBean, new AddParameters(ForeignRegistrationForm.class, FORM,
                VIEW_DETAILS, VIEW_DETAILS, maxNumber));
    }

    @RequestMapping(value = "addForeignRegistration", method = RequestMethod.POST)
    public ModelAndView onSubmitForeignRegistration(@ModelAttribute(FORM) @Validated ForeignRegistrationForm command,
                                            BindingResult result) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
                ConfigurationServiceDelegatorInterface.FOREIGN_REGISTRATION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        return onSubmit(command, flowBean, new AddParameters(ForeignRegistrationForm.class, FORM, VIEW_LIST,
                VIEW_DETAILS, maxNumber), result);
    }
}
