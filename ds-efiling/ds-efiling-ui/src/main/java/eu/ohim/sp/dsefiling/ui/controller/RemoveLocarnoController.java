package eu.ohim.sp.dsefiling.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.RemoveAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.RemoveParameters;
import eu.ohim.sp.common.ui.form.design.LocarnoForm;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;

/**
 * Controller that could be used to remove an LocarnoForm object from the collection
 * The request should contain the id of the LocarnoForm object that we want to remove.
 * 
 */
@Controller
public class RemoveLocarnoController extends RemoveAbstractController {

	@Autowired
	private DSFlowBean dsFlowBean;		

    // Model
    private static final String MODEL_LOCARNO_FORM = "locarnoForm";

	// View
	private static final String VIEW_LOCARNO_LIST = "locarno/locarno_list";

    //DS Class Integration Changes Start
    private static final String VIEW_LOCARNO_TABLE = "locarno/dsclass/locarno_Table";
    //DS Class Integration Changes End
	
	/**
     * It returns the selected Locarno object by id
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the IR transformation
     */
    @RequestMapping(value = "removeLocarno", method = RequestMethod.GET)
    public ModelAndView handleRemoveLocarno(@RequestParam(value = "id") String id) {
        //DS Class Integration Changes start
        return handle(id, dsFlowBean, new RemoveParameters(LocarnoForm.class, MODEL_LOCARNO_FORM, VIEW_LOCARNO_TABLE, VIEW_LOCARNO_TABLE));
        //DS Class Integration Changes End
    }
}