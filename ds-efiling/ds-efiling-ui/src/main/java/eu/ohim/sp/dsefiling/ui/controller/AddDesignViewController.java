package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.design.DesignViewFormList;
import eu.ohim.sp.common.ui.form.validation.DragDropError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller in charge of handling the adding and the editing of the views of a design.
 * @author serrajo
 */
@Controller
public class AddDesignViewController extends AddAbstractController {

	@Autowired
    private DSFlowBean dsFlowBean;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;
    
    @Autowired
	private DSDesignsServiceInterface designsService;
    
    private Integer maxNumber = -1;
    
    // Models
    private static final String MODEL_DESIGN_VIEW_FORM = "designViewForm";
    
    // Views
    private static final String VIEW_ADD_DESIGNVIEW = "designs/views/view_add";
    private static final String VIEW_LIST_DESIGNVIEWS = "designs/views/view_list";
    private static final String VIEW_VIEW_DESIGNVIEW = "designs/views/view_view";
    
    /**
     * Gets the maximum number of design views allowed
     * @return Integer with the maximum number
     */
	public Integer getMaxNumber() {
		if(maxNumber == -1) {
			maxNumber = getIntegerSetting(configurationServiceDelegator,
                            ConfigurationServiceDelegatorInterface.DESIGN_VIEW_ADD_MAXNUMBER,
                            ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
		}
        return maxNumber;
    }
    
    /**
     * Returns the model and view to add a new design view. 
     * @return the view and model containing the design view form to add.
     */
    @RequestMapping(value = "addDesignView", method = RequestMethod.GET)
    public ModelAndView addDesignView() {
    	AddParameters addParameters = new AddParameters(DesignViewForm.class, MODEL_DESIGN_VIEW_FORM, null, VIEW_ADD_DESIGNVIEW, getMaxNumber()); 
        return innerFormBackingObject(null, dsFlowBean, addParameters);
    }
    
    /**
     * Adds a new design view in the collection stored in the session.
     *
     * @param designViewForms object that contains the design views information.
     * @param result manage the validation results of the form object
     * @return the view and model containing the list of views or the same form in case of error. 
     */
    @PreAuthorize("hasRole('DesignView_Add')")
    @RequestMapping(value = "saveDesignView", method = RequestMethod.POST)
    public ModelAndView saveDesignView(@RequestBody DesignViewFormList designViewForms, BindingResult result) {
        AddParameters addParameters = new AddParameters(DesignViewForm.class, MODEL_DESIGN_VIEW_FORM, VIEW_LIST_DESIGNVIEWS, VIEW_ADD_DESIGNVIEW, getMaxNumber());
        ModelAndView modelAndView = null;

        if(designViewForms == null || designViewForms.getDesignViewForms() == null ||
            designViewForms.getDesignViewForms().size() == 0){
            throw new SPException("No views to save. Request body is empty");
        }
        Optional<Boolean> isEditOptional = designViewForms.getDesignViewForms().stream().map(e -> e.getId()!= null && !e.getId().isEmpty()).reduce((e1, e2) -> e1&&e2);
        boolean isEdit = false;
        if(isEditOptional.isPresent()){
            isEdit = isEditOptional.get();
        }


        List<DragDropError> dragDropErrors = new ArrayList<>();
        int lastIndex = 0;
        for(DesignViewForm designViewForm: designViewForms.getDesignViewForms()) {

            designViewForm.setValidateImported(true);
            modelAndView = onSubmit(designViewForm, dsFlowBean, addParameters, result);

            if(result.hasErrors() && result.getFieldErrors().size() > lastIndex){

                for(int i = lastIndex; i < result.getFieldErrors().size(); i++) {
                    FieldError fieldError = result.getFieldErrors().get(i);
                    dragDropErrors.add(new DragDropError(new StringBuilder(fieldError.getField()).append("_").append(designViewForm.getView().getStoredFiles().get(0).getDocumentId()).toString(),
                        fieldError.getDefaultMessage(), fieldError.getCode()));

                }

                lastIndex = result.getFieldErrors().size();
            }
        }

        if(dragDropErrors.size()>0){
            addParameters = new AddParameters(DesignViewForm.class, MODEL_DESIGN_VIEW_FORM, null, VIEW_ADD_DESIGNVIEW, getMaxNumber());
            addParameters.setMaximumEntities(null);
            modelAndView = innerFormBackingObject(null, dsFlowBean, addParameters);
            modelAndView.addObject("errorList", dragDropErrors);
        }

    	if (VIEW_LIST_DESIGNVIEWS.equals(modelAndView.getViewName())) {
    		// If success we will order the views depending on the view type
    		designsService.addViewsNumber(dsFlowBean);
    	} else if(!isEdit) {
            designViewForms.getDesignViewForms().stream().filter(e -> e.getId() != null && !e.getId().isEmpty()).forEach(e->
                flowBean.removeObject(DesignViewForm.class, e.getId())
            );
        }
    	return modelAndView;
    }
    
    /**
     * Entry point to get a design view form for edit.
     *
     * @return the view and model containing the design view form to edit.
     */
    @RequestMapping(value = "getDesignView", method = RequestMethod.GET)
    public ModelAndView getDesignView() {
    	return innerFormBackingObject(null, dsFlowBean, new AddParameters(DesignViewForm.class, MODEL_DESIGN_VIEW_FORM, null, VIEW_ADD_DESIGNVIEW, null));
    }
    
    /**
     * Return the view of the design view.
     * @param id the id of the design view form.
     * @return the view and model containing the design view view.
     */
    @RequestMapping(value = "viewDesignView", method = RequestMethod.GET)
    public ModelAndView viewDesignView(@RequestParam(required = true, value = "id") String id) {
		DesignViewForm designViewForm = dsFlowBean.getObject(DesignViewForm.class, id);
		ModelAndView modelAndView = new ModelAndView(VIEW_VIEW_DESIGNVIEW);
		modelAndView.addObject(MODEL_DESIGN_VIEW_FORM, designViewForm);
		return modelAndView;
    }
}
