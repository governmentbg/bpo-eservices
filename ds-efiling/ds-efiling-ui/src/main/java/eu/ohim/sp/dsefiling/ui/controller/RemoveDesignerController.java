package eu.ohim.sp.dsefiling.ui.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.RemoveAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.RemoveParameters;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;

/**
 * Controller that could be used to remove an DesignerForm object from the collection
 * The request should contain the id of the DesignerForm object that we want to remove.
 * 
 */
@Controller
public class RemoveDesignerController extends RemoveAbstractController {

	@Autowired
	private DSFlowBean dsFlowBean;		
	
	@Autowired
	private DSDesignsServiceInterface dsDesignsService;
	
	/**
     * It returns the selected Designers object by id
     * 
     * @param id
     *            the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception
     *             if it fails to load the IR transformation
     */
    @RequestMapping(value = "removeDesigner", method = RequestMethod.GET)
    public ModelAndView handleRemoveDesigner(@RequestParam(value = "id") String id) {
    	DesignerForm designer = getDesigner(id);
        ModelAndView mv = handle(id, dsFlowBean, new RemoveParameters(DesignerForm.class, "designerForm", "designers/designer_card_list", "designer/designer_failure"));
        
        if ("designers/designer_card_list".equals(mv.getViewName())) {
        	designerRemoved(designer, dsFlowBean.getDesigners());
        }
        
        return mv;
        
    }
    
    /**
     * 
     * @param id
     * @return
     */
    private DesignerForm getDesigner(String id) {
    	if (StringUtils.isNotBlank(id)) {
    		return dsFlowBean.getObject(DesignerForm.class, id);	
    	} else {
    		return null;
    	}
    }

    /**
     * 
     * @param designerRemoved
     * @param allDesigners
     */
    private void designerRemoved(DesignerForm designerRemoved, List<DesignerForm> allDesigners) {
    	if (designerRemoved.isWaiver()) {
    		for (DesignerForm designerForm : allDesigners) {
    			designerForm.getDesignsNotLinked().addAll(designerRemoved.getDesignsLinked());
    		}
    	} else {
    		DesignerForm designerWhoWaives = dsDesignsService.getDesignerWhoWaives(allDesigners);
    		if (designerWhoWaives != null) {
    			for (DesignForm designForm : designerRemoved.getDesignsLinked()) {
    				if (!dsDesignsService.isDesignLinkedInSomeFormInTheList(designForm, allDesigners)) {
    					designerWhoWaives.getDesignsNotLinked().add(designForm);
    				}
    			}
    		}
    	}
    }
    
}
