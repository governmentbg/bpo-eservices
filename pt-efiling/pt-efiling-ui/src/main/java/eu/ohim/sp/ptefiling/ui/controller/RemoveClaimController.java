package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.ui.controller.RemoveAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.RemoveParameters;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.patent.*;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Raya
 * 18.04.2019
 */
@Controller
public class RemoveClaimController extends RemoveAbstractController {

    @Autowired
    private PTFlowBean ptFlowBean;

    @RequestMapping(value = "removePTPriority", method = RequestMethod.POST)
    public ModelAndView removePTPriority(@RequestParam(value = "id") String id) {
        ModelAndView mv = handle(id, ptFlowBean, new RemoveParameters(PTPriorityForm.class, "priorityForm", "claim/priority_card_list",
                "claim/priority_card_list"));
        return mv;
    }

    @RequestMapping(value = "removePTExhibition", method = RequestMethod.POST)
    public ModelAndView removePTExhibition(@RequestParam(value = "id") String id) {
        ModelAndView mv = handle(id, ptFlowBean, new RemoveParameters(ExhPriorityForm.class, "exhibitionForm", "claim/exhibition_card_list",
                "claim/exhibition_card_list"));
        return mv;
    }

    @RequestMapping(value = "removePTDivisionalApplication", method = RequestMethod.POST)
    public ModelAndView removePTDivisionalApplication(@RequestParam(value = "id") String id) {
        PTDivisionalApplicationForm toRemove = ptFlowBean.getObject(PTDivisionalApplicationForm.class, id);

        ModelAndView mv = handle(id, ptFlowBean, new RemoveParameters(PTDivisionalApplicationForm.class, "divisionalApplicationForm", "claim/divisional_application_card_list",
                "claim/divisional_application_card_list"));

        cleaFlowBeanEarlierAppImportedData(toRemove);
        return mv;
    }

    @RequestMapping(value = "removePct", method = RequestMethod.POST)
    public ModelAndView removePct(@RequestParam(value = "id") String id) {
        ModelAndView mv = handle(id, ptFlowBean, new RemoveParameters(PCTForm.class, "pctForm", "claim/pct_card_list",
            "claim/pct_card_list"));
        return mv;
    }

    @RequestMapping(value = "removeTransformation", method = RequestMethod.POST)
    public ModelAndView removeTransformation(@RequestParam(value = "id") String id) {
        PTTransformationForm toRemove = ptFlowBean.getObject(PTTransformationForm.class, id);

        ModelAndView mv = handle(id, ptFlowBean, new RemoveParameters(PTTransformationForm.class, "transformationForm", "claim/transformation_card_list",
            "claim/transformation_card_list"));

        cleaFlowBeanEarlierAppImportedData(toRemove);
        return mv;
    }

    @RequestMapping(value = "removeParallelApplication", method = RequestMethod.POST)
    public ModelAndView removeParallelApplication(@RequestParam(value = "id") String id) {
        PTParallelApplicationForm toRemove = ptFlowBean.getObject(PTParallelApplicationForm.class, id);

        ModelAndView mv = handle(id, ptFlowBean, new RemoveParameters(PTParallelApplicationForm.class, "parallelApplicationForm", "claim/parallel_application_card_list",
            "claim/parallel_application_card_list"));

        cleaFlowBeanEarlierAppImportedData(toRemove);
        return mv;
    }

    private void cleaFlowBeanEarlierAppImportedData(AbstractImportableForm toRemove){
        if(toRemove != null && toRemove.getImported()){
            ptFlowBean.setEarlierAppImported(false);
            ptFlowBean.getEarlierAppRepresentatives().clear();
        }
    }
}
