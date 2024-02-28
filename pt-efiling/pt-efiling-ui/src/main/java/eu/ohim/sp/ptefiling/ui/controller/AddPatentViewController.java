package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.patent.PatentViewForm;
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
 * Created by Raya, copied from AddDesignViewController
 * 12.12.2018
 */
@Controller
public class AddPatentViewController extends AddAbstractController {
    @Autowired
    private PTFlowBean flowBean;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;


    private Integer maxNumber = -1;

    // Models
    private static final String MODEL_PATENT_VIEW_FORM = "patentViewForm";

    // Views
    private static final String VIEW_ADD_PATENTVIEW = "patent/view_add";
    private static final String VIEW_LIST_PATENTVIEWS = "patent/view_list";
    private static final String VIEW_VIEW_PATENTVIEW = "patent/view_view";


    public Integer getMaxNumber() {
        if(maxNumber == -1) {
            maxNumber = getIntegerSetting(configurationServiceDelegator,
                    ConfigurationServiceDelegatorInterface.PATENT_VIEW_ADD_MAXNUMBER,
                    ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
        }
        return maxNumber;
    }


    @RequestMapping(value = "addPatentView", method = RequestMethod.GET)
    public ModelAndView addPatentView() {
        AddParameters addParameters = new AddParameters(PatentViewForm.class, MODEL_PATENT_VIEW_FORM, null, VIEW_ADD_PATENTVIEW, getMaxNumber());
        return innerFormBackingObject(null, flowBean, addParameters);
    }


    @PreAuthorize("hasRole('PatentView_Add')")
    @RequestMapping(value = "savePatentView", method = RequestMethod.POST)
    public ModelAndView savePatentView(@ModelAttribute PatentViewForm patentViewForm, BindingResult result) {
        AddParameters addParameters = new AddParameters(PatentViewForm.class, MODEL_PATENT_VIEW_FORM, VIEW_LIST_PATENTVIEWS, VIEW_ADD_PATENTVIEW, getMaxNumber());
        ModelAndView modelAndView = onSubmit(patentViewForm, flowBean, addParameters, result);
        return modelAndView;
    }


    @RequestMapping(value = "getPatentView", method = RequestMethod.GET)
    public ModelAndView getPatentView(@RequestParam(required = true, value = "id") String id) {
        return innerFormBackingObject(id, flowBean, new AddParameters(PatentViewForm.class, MODEL_PATENT_VIEW_FORM, null, VIEW_ADD_PATENTVIEW, getMaxNumber()));
    }


    @RequestMapping(value = "viewPatentView", method = RequestMethod.GET)
    public ModelAndView viewDesignView(@RequestParam(required = true, value = "id") String id) {
        PatentViewForm patentViewFormViewForm = flowBean.getObject(PatentViewForm.class, id);
        ModelAndView modelAndView = new ModelAndView(VIEW_VIEW_PATENTVIEW);
        modelAndView.addObject(MODEL_PATENT_VIEW_FORM, patentViewFormViewForm);
        return modelAndView;
    }
}
