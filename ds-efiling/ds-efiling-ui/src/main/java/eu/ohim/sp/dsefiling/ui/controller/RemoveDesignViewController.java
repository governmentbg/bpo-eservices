/*******************************************************************************
 * * $$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.dsefiling.ui.controller;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.RemoveAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.RemoveParameters;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controller to remove design views from the collection.
 *
 * @author serrajo
 *
 */
@Controller
public class RemoveDesignViewController extends RemoveAbstractController {

    @Autowired
    private DSFlowBean flowBean;

    // Model
    private static final String MODEL_DESIGN_VIEW_FORM = "designViewForm";

	// View
    private static final String VIEW_LIST_DESIGN_VIEWS = "designs/views/view_list";
    private static final String VIEW_ADD_DESIGNVIEW = "designs/views/view_add";

    /**
     * Delete a design view
     *
     * @param sequences
     * @return model and view
     */
    @RequestMapping(value = "removeDesignView", method = RequestMethod.GET)
    public ModelAndView handleRemoveDesignView(@RequestParam(required = true, value = "sequences") String sequences) {

        List<DesignViewForm> flowBeanViews = new ArrayList<DesignViewForm>();
        flowBeanViews.addAll(flowBean.getDesignViews());

        ModelAndView modelAndView = null;

        String[] sequenceList = sequences.split(";");
        List<Integer> removedSequences = new ArrayList<>();
        for (int i = 0; i < sequenceList.length; i++) {
            Integer sequence = Integer.valueOf(sequenceList[i]);
            removedSequences.add(sequence);
            for (DesignViewForm flowBeanView : flowBeanViews) {
                if (flowBeanView.getSequence() == sequence) {
                    modelAndView = handle(flowBeanView.getId(), flowBean, new RemoveParameters(DesignViewForm.class, MODEL_DESIGN_VIEW_FORM, VIEW_LIST_DESIGN_VIEWS, VIEW_ADD_DESIGNVIEW));
                }
            }
        }

        normalizeViewsSequences(removedSequences);

        return modelAndView;
    }

    private void normalizeViewsSequences(List<Integer> removedSequences) {
        removedSequences.sort(Comparator.reverseOrder());
        if (CollectionUtils.isNotEmpty(flowBean.getDesignViews())) {
            for (Integer removedSeq : removedSequences) {
                for (DesignViewForm designViewForm : flowBean.getDesignViews()) {
                    if (designViewForm.getSequence() > removedSeq) {
                        designViewForm.setSequence(designViewForm.getSequence() - 1);
                    }
                }
            }
        }

    }


}

