package eu.ohim.sp.dsefiling.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.form.design.ContainsDesignsLinkForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.dsefiling.ui.controller.bind.DSBindingInitializer;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;

/**
 * 
 * @author serrajo
 */
public abstract class AddDesignsLinkedController extends AddAbstractController {
    
	@Autowired
    private DSFlowBean flowBean;
	
	@Autowired
	private DSDesignsServiceInterface designsService;
   
	@Autowired
	private DSBindingInitializer addDesignsLinkedBinder;
	
	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
		addDesignsLinkedBinder.initBinder(binder, request);
    }
    
    /**
     * Fills form designs lists.
     * @param modelAndView
     * @param keyModel
     */
    protected void fillListsDesignsFromToLink(ModelAndView modelAndView, String keyModel) {
    	ContainsDesignsLinkForm form = (ContainsDesignsLinkForm) modelAndView.getModel().get(keyModel);
    	designsService.fillDesignsListsFromToLink(flowBean, form);
    }

    /**
     * 
     * @param modelAndView
     * @param keyModel
     */
    protected void fillDesignerDesignsListsFromToLink(DesignerForm designerForm) {
    	designsService.fillDesignerDesignsListsFromToLink(flowBean, designerForm);
    }

    /**
     * 
     * @return
     */
	protected DSDesignsServiceInterface getDesignsService() {
		return designsService;
	}

}
