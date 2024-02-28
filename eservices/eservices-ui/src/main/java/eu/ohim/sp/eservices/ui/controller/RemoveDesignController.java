package eu.ohim.sp.eservices.ui.controller;

import eu.ohim.sp.common.ui.controller.RemoveAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.RemoveParameters;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marcoantonioalberoalbero on 6/4/17.
 */
@Controller
public class RemoveDesignController extends RemoveAbstractController {
    @Autowired
    private ESFlowBean flowBean;

    @RequestMapping(value = "removeEarlierDSDetails", method = RequestMethod.GET)
    public ModelAndView handleRemoveEarlierDS(@RequestParam(value = "id") String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("opposition/basis/ds_earlier_card_list");

        List<ESDesignDetailsForm> arr = flowBean.getDssTempList().stream()
                .filter(d -> !id.equals(d.getDesignIdentifier()))
                .collect(Collectors.toList());
        flowBean.getDssTempList().clear();
        flowBean.getDssTempList().addAll(arr);

        return modelAndView;
    }
}
