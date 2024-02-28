/*******************************************************************************
 * * $$
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/

package eu.ohim.sp.dsefiling.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.exception.MaximumEntitiesException;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;

/**
 * Controller in charge of handling the adding and the editing of the designs.
 * @author monteca
 */
@Controller
public class AddDesignController extends AddAbstractController {

	@Autowired
    private DSFlowBean flowBean;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    private Integer maxNumber = -1;
    
	@Autowired
	private DSDesignsServiceInterface designsService;

	@Value("${designs.locarno.shared}")
	private Boolean isLocarnoShared;
	
	// Model
	public static final String MODEL_DESIGN_FORM = "designForm";
	
	// Views
	private static final String VIEW_DESIGNS_LIST = "designs/designs_list";
	private static final String VIEW_DESIGN_FORM = "designs/design_form";
	private static final String VIEW_DESIGN_VIEW = "designs/design_view";
	
    /**
     * Gets the maximum number of designs allowed
     * @return Integer with the maximum number
     */
	public Integer getMaxNumber() {
		if(maxNumber == -1) {
			maxNumber = getIntegerSetting(configurationServiceDelegator,
                            ConfigurationServiceDelegatorInterface.DESIGN_ADD_MAXNUMBER,
                            ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
		}
        return maxNumber;
    }

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "viewDesign", method = RequestMethod.GET)
	public ModelAndView viewDesign(@RequestParam(required = true, value = "id") String id) {
		DesignForm designForm = flowBean.getObject(DesignForm.class, id);
		
        // First we clear the possible views and Locarno classes added to the flow...
        clearViewsAddedToFlow();
        clearLocarnoAddedToFlow();
        // ...and add the new ones to the flow.
		addViewsToFlow(designForm);
		addLocarnoToFlow(designForm);
		
		ModelAndView modelAndView = new ModelAndView(VIEW_DESIGN_VIEW);
		modelAndView.addObject(MODEL_DESIGN_FORM, designForm);
		return modelAndView;
	}
	
    /**
     * It returns a new Design obj if no parameters are passed, so a new
     * Design to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the design
     */
    @RequestMapping(value = "addDesign", method = RequestMethod.GET)
    public ModelAndView formBackingDesign(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = innerFormBackingObject(id, flowBean,
            new AddParameters(DesignForm.class, MODEL_DESIGN_FORM, VIEW_DESIGNS_LIST, VIEW_DESIGN_FORM, getMaxNumber()));
        
        // First we clear the possible views and Locarno classes added to the flow.
        clearViewsAddedToFlow();
        clearLocarnoAddedToFlow();
        
        if (StringUtils.isNotBlank(id)) {
        	// It is the edit of a design.
        	DesignForm design = (DesignForm) model.getModel().get(MODEL_DESIGN_FORM);
        	addViewsToFlow(design);
        	addLocarnoToFlow(design);
        } else {
        	
        	// TODO New Pepelu 12/11/2013
        	if (Boolean.TRUE.equals(isLocarnoShared)) {
        		DesignForm design = (DesignForm) model.getModel().get(MODEL_DESIGN_FORM);
        		setFirstDesignLocarnoToDesign(design);
        		addLocarnoToFlow(design);
        	}
        	// TODO End
        	
        }
        
        return model;
    }

    /**
     * Adds or edits on the collection of designs stored in the session
     *
     * @param command object that contains the Design information.
     * @param result  manage the validation results of the form object
     * @return Design object view with the new design added
     */
    @PreAuthorize("hasRole('Design_Add')")
    @RequestMapping(value = "addDesign", method = RequestMethod.POST)
    public ModelAndView onSubmitDesign(@ModelAttribute("designForm") DesignForm command, BindingResult result) {
    	boolean isEdit = flowBean.existsObject(DesignForm.class, command.getId());
    	
        AddParameters addParameters = new AddParameters(DesignForm.class, MODEL_DESIGN_FORM, VIEW_DESIGNS_LIST, VIEW_DESIGN_FORM, getMaxNumber());

        // We set the news views and Locarno to the designForm to run the validations, but before we remove the previous ones.
        clearViewsAddedToDesign(command);
        clearLocarnoAddedToDesign(command);
        addViewsToDesign(command);
        addLocarnoToDesign(command);

        ModelAndView modelAndView = onSubmit(command, flowBean, addParameters, result);
        
        if (modelAndView.getViewName().equals(VIEW_DESIGNS_LIST)) { 
        	// success
        	clearViewsAddedToFlow();
        	clearLocarnoAddedToFlow();
        	if (isEdit) {
        		designsService.replaceDesignInLists(command, flowBean);
        	} else {
        		designsService.addDesignToLists(command, flowBean);
        	}
        	
        	// TODO New Pepelu 12/11/2013
        	setDesignLocarnoToAllDesigns(command);
        	// TODO End
        	
        } else {
        	// error
        	clearViewsAddedToDesign(command);
        	clearLocarnoAddedToDesign(command);
        }
        return modelAndView;
    }
    
    /**
     * 
     * @param id
     * @param times
     * @return
     */
    @PreAuthorize("hasRole('Design_Duplicate')")
	@RequestMapping(value = "duplicateDesign", method = RequestMethod.GET)
    public ModelAndView duplicateDesign(@RequestParam(required = true, value = "id") String id, @RequestParam(required = true, value = "times") Integer times) {
    	try {
    		DesignForm designFormToDuplicate = flowBean.getObject(DesignForm.class, id);
    		DesignForm designFormToAdd;
    		ModelAndView modelToReturn = new ModelAndView(VIEW_DESIGNS_LIST);
    		Boolean maximumEntitiesReached = Boolean.FALSE;
    		
    		AddParameters addParameters = new AddParameters(DesignForm.class, MODEL_DESIGN_FORM, null, VIEW_DESIGNS_LIST, getMaxNumber());
    		 
    		try {
    			for (int i = 0; i < times; i++) {
    				ModelAndView model = innerFormBackingObject(null, flowBean, addParameters);
    				DesignForm designFormDuplicated = (DesignForm) model.getModel().get(MODEL_DESIGN_FORM);
    				designFormToDuplicate.duplicate(designFormDuplicated);
    				designFormToAdd = designFormDuplicated.clone();
                	flowBean.addObject(designFormToAdd);
                	designsService.addDesignToLists(designFormToAdd, flowBean);
    			}
    			
    			// TODO New Pepelu 12/11/2013
    			setFirstDesignLocarnoToAllDesigns();
    			// TODO End
    			
    		} catch (MaximumEntitiesException mee) {
    			maximumEntitiesReached = Boolean.TRUE;
    		}

    		modelToReturn.addObject("maximumEntitiesReached", maximumEntitiesReached);
    		return modelToReturn;
    		
    	} catch (CloneNotSupportedException e) {
    		throw new SPException("Failed to find duplicate object", e, "error.genericError");
    	}
    }

    private void clearViewsAddedToDesign(DesignForm design) {
    	designsService.clearViewsDesignForm(design);
    }
    
    private void clearLocarnoAddedToDesign(DesignForm design) {
   		designsService.clearLocarnoForm(design);
    }
    
    private void clearViewsAddedToFlow() {
    	designsService.clearViewsFlow(flowBean);
    }
    
    private void clearLocarnoAddedToFlow() {
   		designsService.clearLocarnoFlow(flowBean);
    }

    private void addViewsToDesign(DesignForm design) {
    	designsService.addFlowViewsToDesign(flowBean, design);
    }
    
    private void addLocarnoToDesign(DesignForm design) {
    	designsService.addFlowLocarnoToForm(flowBean, design);
    }
    
    private void addViewsToFlow(DesignForm design) {
   		designsService.addDesignViewsToFlow(design, flowBean);
    }
    
    private void addLocarnoToFlow(DesignForm design) {
   		designsService.addFormLocarnoToFlow(design, flowBean);
    }
    
    // TODO New Pepelu 12/11/2013
    private void setDesignLocarnoToAllDesigns(DesignForm design) {
    	if (Boolean.TRUE.equals(isLocarnoShared)) {
    		setLocarnoToAllDesigns(design.getLocarno());
    	}
    }
    
    private void setFirstDesignLocarnoToAllDesigns() {
    	if (Boolean.TRUE.equals(isLocarnoShared) && (!flowBean.getDesigns().isEmpty()) ) {
    		setLocarnoToAllDesigns(getFirstDesignLocarno());
    	}
    }
    
    private void setFirstDesignLocarnoToDesign(DesignForm design) {
    	if (Boolean.TRUE.equals(isLocarnoShared)) {
    		design.setLocarno(getFirstDesignLocarno());
    	}
    }
    
    private void setLocarnoToAllDesigns(List<LocarnoAbstractForm> locarno) {
    	for (DesignForm design : flowBean.getDesigns()) {
    		design.setLocarno(locarno);
    	}
    }
    
    private List<LocarnoAbstractForm> getFirstDesignLocarno() {
		return !flowBean.getDesigns().isEmpty() ? flowBean.getDesigns().get(0).getLocarno() : new ArrayList<LocarnoAbstractForm>(); 
    }
    // TODO End

}

