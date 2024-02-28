package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.common.ui.controller.RemoveAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.RemoveParameters;
import eu.ohim.sp.common.ui.form.trademark.MarkViewForm;
import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;
import eu.ohim.sp.ui.tmefiling.util.MarkViewUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raya
 * 15.08.2019
 */
@Controller
public class RemoveMarkViewController extends RemoveAbstractController {


    @Autowired
    private TMFlowBean flowBean;

    // Model
    private static final String MODEL_MARK_VIEW_FORM = "markViewForm";

    // View
    private static final String VIEW_LIST_MARK_VIEWS = "marks/views/view_list";
    private static final String VIEW_ADD_MARKVIEW = "marks/views/view_add";


    @RequestMapping(value = "removeMarkView", method = RequestMethod.GET)
    public ModelAndView handleRemoveMarkView(@RequestParam(required = true, value = "sequences") String sequences) {

        List<MarkViewForm> flowBeanViews = new ArrayList<>();
        flowBeanViews.addAll(flowBean.getMarkViews());

        ModelAndView modelAndView = null;

        String[] sequenceList = sequences.split(";");
        List<Integer> removedSequences = new ArrayList<>();
        for (int i = 0; i < sequenceList.length; i++) {
            Integer sequence = Integer.valueOf(sequenceList[i]);
            removedSequences.add(sequence);
            for (MarkViewForm flowBeanView : flowBeanViews) {
                if (flowBeanView.getSequence() == sequence) {
                    modelAndView = handle(flowBeanView.getId(), flowBean, new RemoveParameters(MarkViewForm.class, MODEL_MARK_VIEW_FORM, VIEW_LIST_MARK_VIEWS, VIEW_ADD_MARKVIEW));
                }
            }
        }

        modelAndView.addObject("sectionId", MarkViewUtil.createViewsSectionId(flowBean.getMainForm().getMarkType()));

        return modelAndView;
    }
}
