package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.ui.controller.RemoveAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.RemoveParameters;
import eu.ohim.sp.common.ui.form.patent.PatentViewForm;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Raya, copied from RemoveDesignViewController
 * 12.12.2018
 */
@Controller
public class RemovePatentViewController extends RemoveAbstractController {

    @Autowired
    private PTFlowBean flowBean;

    // Model
    private static final String MODEL_PATENT_VIEW_FORM = "patentViewForm";

    // View
    private static final String VIEW_LIST_PATENT_VIEWS = "patent/view_list";


    @RequestMapping(value = "removePatentView", method = RequestMethod.GET)
    public ModelAndView handleRemovePatentView(@RequestParam(value = "id") String id) {
        return handle(id, flowBean, new RemoveParameters(PatentViewForm.class, MODEL_PATENT_VIEW_FORM, VIEW_LIST_PATENT_VIEWS, VIEW_LIST_PATENT_VIEWS));
    }

}
