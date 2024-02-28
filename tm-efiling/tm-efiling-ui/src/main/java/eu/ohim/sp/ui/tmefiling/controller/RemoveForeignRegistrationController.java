package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.common.ui.controller.RemoveAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.RemoveParameters;
import eu.ohim.sp.common.ui.form.trademark.ForeignRegistrationForm;
import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 09.05.2022
 * Time: 12:01
 */
@Controller
public class RemoveForeignRegistrationController extends RemoveAbstractController {

    private static final String VIEW_LIST = "foreign_registration/foreign_registration_card_list";

    @Autowired
    private TMFlowBean flowBean;

    @RequestMapping(value = "removeForeignRegistration", method = RequestMethod.GET)
    public ModelAndView handleRemoveForeignRegistration(@RequestParam(value = "id") String id) {

        return handle(id, flowBean, new RemoveParameters(
                ForeignRegistrationForm.class, VIEW_LIST, VIEW_LIST,
                VIEW_LIST));
    }
}
