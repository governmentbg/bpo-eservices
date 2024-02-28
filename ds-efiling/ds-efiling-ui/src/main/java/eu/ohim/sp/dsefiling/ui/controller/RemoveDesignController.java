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
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;

/**
 * Controller to remove designs from the collection.
 *
 * @author monteca
 *
 */
@Controller
public class RemoveDesignController extends RemoveAbstractController {

    @Autowired
    private DSFlowBean flowBean;

    @Autowired
	private DSDesignsServiceInterface designsService;
    
    /**
     * Delete a design
     *
     * @param id  the id of the edited design, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to delete the design
     */
    @RequestMapping(value = "removeDesign", method = RequestMethod.GET)
    public ModelAndView handleRemoveDesign(@RequestParam(value = "id") String id) {
    	DesignForm designForm = flowBean.getObject(DesignForm.class, id);
    	ModelAndView modelAndView = handle(id, flowBean, new RemoveParameters(DesignForm.class, "designForm",
                "designs/designs_list", "designs/design_failure"));
    	
    	// If ok we delete the reference to the DesignForm in the another forms. 
    	if (modelAndView.getViewName().equals("designs/designs_list")) { 
    		// Success!
    		designsService.removeDesignFromLists(designForm, flowBean);
    	}
    	
    	return modelAndView;
    }

}

