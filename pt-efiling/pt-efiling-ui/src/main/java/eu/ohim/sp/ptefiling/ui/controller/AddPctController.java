package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.patent.PCTForm;
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
 * 14.05.2019
 */
@Controller
public class AddPctController extends AddAbstractController {


    @Autowired
    private PTFlowBean ptFlowBean;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    private static final String MODEL_PCT_FORM = "pctForm";
    private static final String PCT_FORM_VIEW = "claim/pct_details";
    private static final String PCT_TABLE_VIEW = "claim/pct_card_list";


    @RequestMapping(value = "addPct", method = RequestMethod.GET)
    public ModelAndView formBackingPCT(@RequestParam(required = false, value = "id") String id) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
            ConfigurationServiceDelegatorInterface.CLAIM_PCT_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        ModelAndView modelAndView = innerFormBackingObject(id, ptFlowBean, new AddParameters(PCTForm.class,
            MODEL_PCT_FORM, PCT_FORM_VIEW, PCT_FORM_VIEW, maxNumber));


        return modelAndView;
    }

    @PreAuthorize("hasRole('PCT_Add')")
    @RequestMapping(value = "addPct", method = RequestMethod.POST)
    public ModelAndView onSubmitPCT(@ModelAttribute(MODEL_PCT_FORM) PCTForm command, BindingResult result) {
        Integer maxNumber = getIntegerSetting(configurationServiceDelegator,
            ConfigurationServiceDelegatorInterface.CLAIM_PCT_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        ModelAndView modelAndView = onSubmit(command, ptFlowBean, new AddParameters(PCTForm.class, MODEL_PCT_FORM,
            PCT_TABLE_VIEW, PCT_FORM_VIEW, maxNumber), result);
        return modelAndView;
    }
}
