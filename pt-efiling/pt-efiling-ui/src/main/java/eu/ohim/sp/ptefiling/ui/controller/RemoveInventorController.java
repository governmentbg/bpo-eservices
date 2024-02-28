package eu.ohim.sp.ptefiling.ui.controller;

import eu.ohim.sp.common.ui.controller.RemoveAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.RemoveParameters;
import eu.ohim.sp.common.ui.form.person.InventorForm;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * Controller that could be used to remove an InventorForm object from the collection
 * The request should contain the id of the InventorForm object that we want to remove.
 * 
 */
@Controller
public class RemoveInventorController extends RemoveAbstractController {

	@Autowired
	private PTFlowBean flowBean;
	

    @RequestMapping(value = "removeInventor", method = RequestMethod.GET)
    public ModelAndView handleRemoveInventor(@RequestParam(value = "id") String id) {
        ModelAndView mv = handle(id, flowBean, new RemoveParameters(InventorForm.class, "inventorForm", "inventor/inventor_card_list",
				"inventor/inventor_failure"));
        return mv;
        
    }
    
}
