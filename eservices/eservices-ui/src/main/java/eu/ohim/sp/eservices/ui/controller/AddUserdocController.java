package eu.ohim.sp.eservices.ui.controller;

import eu.ohim.sp.common.ui.form.userdoc.UserdocForm;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 27.04.2022
 * Time: 16:30
 */
@Controller
public class AddUserdocController {

    @Autowired
    private ESFlowBean esFlowBean;

    private static final String VIEW = "userdoc/userdoc_selection";

    @RequestMapping(value = "addUserdocFromUserdoc", method = RequestMethod.POST)
    public ModelAndView addUserdocFromUserdoc(@RequestParam String id){
        UserdocForm selected = esFlowBean.getObject(UserdocForm.class, id);
        esFlowBean.setSelectedUserdoc(selected);
        esFlowBean.setRelateRequestToObject(false);

        return new ModelAndView(VIEW);
    }

    @RequestMapping(value = "addUserdocFromObject", method = RequestMethod.POST)
    public ModelAndView addUserdocFromObject(){
        esFlowBean.setSelectedUserdoc(null);
        esFlowBean.setRelateRequestToObject(true);

        return new ModelAndView(VIEW);
    }
}
