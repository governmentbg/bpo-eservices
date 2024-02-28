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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.RemoveAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.RemoveParameters;
import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;

/**
 * Controller to remove the divisional application.
 *
 * @author serrajo
 *
 */
@Controller
public class RemoveDivisionalApplicationController extends RemoveAbstractController {

    @Autowired
    private DSFlowBean flowBean;

    // Model
    private static final String MODEL_DIVISIONAL_APLICATION_FORM = "divisionalApplicationForm";

	// View
    private static final String VIEW_TABLE_DIVISIONAL_APPLICATION = "divisionalApplication/divisional_application_table";
    
    /**
     * Delete the divisional application.
     *
     * @param id the id of the divisional application
     * @return model and view
     */
    @RequestMapping(value = "removeDivisionalApplication", method = RequestMethod.GET)
    public ModelAndView handleRemoveDivisionalApplication(@RequestParam(value = "id") String id) {
    	return handle(id, flowBean, new RemoveParameters(DSDivisionalApplicationForm.class, MODEL_DIVISIONAL_APLICATION_FORM, VIEW_TABLE_DIVISIONAL_APPLICATION, VIEW_TABLE_DIVISIONAL_APPLICATION));
    }

}

