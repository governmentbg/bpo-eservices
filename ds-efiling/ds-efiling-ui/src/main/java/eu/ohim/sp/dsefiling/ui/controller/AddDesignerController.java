package eu.ohim.sp.dsefiling.ui.controller;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;

/**
 * Controller in charge of handling the adding and the editing of the designers.
 * @author serrajo
 */
@Controller
public class AddDesignerController extends AddDesignsLinkedController {
    
	private static final String MODEL_DESIGNER_FORM = "designerForm";
    private static final String VIEW_DESIGNERS_TABLE = "designers/designer_card_list";

    private static Logger logger = Logger.getLogger(AddDesignerController.class);
    
    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Autowired
    private DSFlowBean flowBean;

    @Autowired
    private PersonServiceInterface personService;
    
    private Integer maxNumber = -1;

    /**
     * Gets the maximum number of designer entities allowed
     * @return Integer with the maximum number
     */
    public Integer getMaxNumber() {
        if(maxNumber == -1) {
            maxNumber = getIntegerSetting(configurationServiceDelegator,
                            ConfigurationServiceDelegatorInterface.DESIGNER_ADD_MAXNUMBER,
                            ConfigurationServiceDelegatorInterface.PERSON_COMPONENT);
        }
        return maxNumber;
    }
    
    /**
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "displayDesignerWaiver", method = RequestMethod.GET)
    public ModelAndView formWaiver(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = getModelAndView(id, "designers/designer_waiver");
        DesignerForm designerForm = getDesignerFromModel(model);
        designerForm.setWaiver(true);
        fillDesignerDesignsListsFromToLink(designerForm); 
        return model;
    }
    
    /**
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "displayDesignerGroup", method = RequestMethod.GET)
    public ModelAndView formBelongsToAGroup(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = getModelAndView(id, "designers/designer_group");
        DesignerForm designerForm = getDesignerFromModel(model);
        designerForm.setBelongsToAGroup(true);
        fillDesignerDesignsListsFromToLink(designerForm);
        return model;
    }

    /**
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "displayDesignerNotAGroup", method = RequestMethod.GET)
    public ModelAndView formNotBelongsToAGroup(@RequestParam(required = false, value = "id") String id) {
        ModelAndView model = getModelAndView(id, "designers/designer_notagroup");
        fillDesignerDesignsListsFromToLink(getDesignerFromModel(model));
        return model;
    }

    /**
     * 
     * @param id
     * @param formView
     * @return
     */
    private ModelAndView getModelAndView(String id, String formView){
    	return innerFormBackingObject(id, flowBean, new AddParameters(DesignerForm.class, MODEL_DESIGNER_FORM, null, formView, getMaxNumber()));
    }
    
    /**
     * 
     * @param modelAndView
     * @return
     */
    private DesignerForm getDesignerFromModel(ModelAndView modelAndView) {
    	return (DesignerForm) modelAndView.getModelMap().get(MODEL_DESIGNER_FORM);
    }
    
    /**
     * Entry point to get an designer form for edit.
     * If the type is not found or there is no such designer with that id, it returns null.
     * @param id the id of the designer
     * @return the view containing the designer form to edit
     */
    @RequestMapping(value = "getDesignerForEdit", method = RequestMethod.GET)
    public ModelAndView getDesigner(@RequestParam(required = true, value = "id") String id) {
        DesignerForm found = flowBean.getObject(DesignerForm.class, id);
        ModelAndView mav = null;
        if (found != null) {
	        if (found.isWaiver()) {
	        	found.setMayWaiver(true);
	        	mav = getModelAndView(id, "designers/designer_waiver");
	        } else {
	        	found.setMayWaiver(!getDesignsService().isThereADesignerThatWaiver(flowBean));
	        	if (found.isBelongsToAGroup()){
	        		mav = getModelAndView(id, "designers/designer_group");
	        	} else {
	        		mav = getModelAndView(id, "designers/designer_notagroup");
	        	}
	        }
        }
        return mav;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command object that contains the Designer information.
     * @param result manage the validation results of the form object
     * @return Designer object view with the new designer added
     */
    @RequestMapping(value = "addDesigner", method = RequestMethod.POST)
    @PreAuthorize("hasRole('Designer_Add')")
    public ModelAndView onSubmitDesigner(@ModelAttribute(MODEL_DESIGNER_FORM) DesignerForm command,
    										BindingResult result,
                                         @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches) {
    	String formView = command.isWaiver() ? "designers/designer_waiver" : command.isBelongsToAGroup() ? "designers/designer_group" : "designers/designer_notagroup";
        AddParameters addParameters = new AddParameters(DesignerForm.class, MODEL_DESIGNER_FORM, VIEW_DESIGNERS_TABLE, formView, getMaxNumber());
        ModelAndView mv = addDesignerCheckMatches(command, result, addParameters, ignoreMatches);
        
        if (VIEW_DESIGNERS_TABLE.equals(mv.getViewName())) {
        	designerAdded(command, flowBean.getDesigners());	
        }
        if(result.hasErrors()){
            resetCommandImportedDetails(mv, command,
                    ConfigurationServiceDelegatorInterface.RESET_DESIGNER_IMPORTED_KEY);
        }
        
        return mv;
    }

    /**
     * Method that handles the removal of the empty correspondence addresses before adding the designer to the flowbean.
     * @param command
     * @param result
     * @param addParameters
     * @return
     */
    private ModelAndView addDesigner(DesignerForm command, BindingResult result, AddParameters addParameters) {
        if (command != null) {
        	command.removeEmptyCorrespondenceAddresses();
        }
        return onSubmit(command, flowBean, addParameters, result);
    }
    
    /**
     * 
     * @param designerAdded
     * @param allDesigners
     */
    private void designerAdded(DesignerForm designerAdded, List<DesignerForm> allDesigners) {
    	if (designerAdded.isWaiver()) {
        	for (DesignForm designForm : designerAdded.getDesignsLinked()) {
        		// Remove designer waives linked designs from designers not linked lists. 
        		for (DesignerForm designerForm : allDesigners) {
        			if (!designerForm.isWaiver()) {
        				designerForm.getDesignsNotLinked().remove(designForm);
        			}
        		}
        	}
    	} else {
    		DesignerForm designerWhoWaives = getDesignsService().getDesignerWhoWaives(allDesigners);
    		if (designerWhoWaives != null) {
   				designerWhoWaives.getDesignsNotLinked().removeAll(designerAdded.getDesignsLinked());
   				
   				// If the user has removed a design from the linked list.
   				for (DesignForm designForm : designerAdded.getDesignsNotLinked()) {
   					if (!getDesignsService().isDesignLinkedInSomeFormInTheList(designForm, allDesigners)) {
   						designerWhoWaives.getDesignsNotLinked().add(designForm);
   					}
   				}
    		}
    	}
    }

    private ModelAndView addDesignerCheckMatches(DesignerForm command, BindingResult result, AddParameters addParameters,
                                                  Boolean ignoreMatches)
    {
        // if the ignoreMatches boolean is set to true
        // add the designer without checking for matches
        if (ignoreMatches != null && ignoreMatches)
        {
            return addDesigner(command, result, addParameters);
        }

        // otherwise, validate the form before calling the match service
        ModelAndView mv = new ModelAndView();
        boolean validObject = validateCommand(command, result, addParameters);
        if (!validObject)
        {
            mv.setViewName(addParameters.getFormView());
            return mv;
        }
        else
        {
            mv = new ModelAndView();

            // when no model errors are found
            // set the validation trigger to false so that validation is not activated twice
            addParameters.setTriggerValidation(false);
        }

        // call the match service
        List<DesignerForm> matches = checkMatches(command);

        // if no matches found, just add the designer
        if (matches.isEmpty())
        {
            return addDesigner(command, result, addParameters);
        }

        // otherwise, return the matches view
        mv.setViewName("designers/designerMatches");
        mv.addObject("designerMatches", matches);
        return mv;
    }

    private <T extends DesignerForm> List<DesignerForm> checkMatches(T form)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("form:" + form);
        }
        List<DesignerForm> designerMatches = new ArrayList<DesignerForm>();

        // only check for matches if the service is enabled
        if (configurationServiceDelegator.isServiceEnabled(AvailableServices.DESIGNER_MATCH.value())) {
            int maxResults = Integer.parseInt(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.DESIGNER_MATCH_MAXRESULTS));
            designerMatches = personService.matchDesigner(form, maxResults);
        }

        return designerMatches;
    }
}
