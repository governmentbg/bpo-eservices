package eu.ohim.sp.eservices.ui.controller;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.common.ui.form.design.LocarnoClass;
import eu.ohim.sp.common.ui.form.design.LocarnoClassification;
import eu.ohim.sp.common.ui.form.design.LocarnoComplexForm;
import eu.ohim.sp.common.ui.form.design.LocarnoComplexKindForm;
import eu.ohim.sp.common.ui.form.design.LocarnoForm;
import eu.ohim.sp.common.ui.form.design.LocarnoSearchForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.interfaces.DSLocarnoServiceInterface;

/**
 * Controller in charge of handling the adding and the editing of the Locarno classes.
 * @author serrajo
 */
@Controller
public class AddLocarnoController extends AddAbstractController {

	@Autowired
    private ESFlowBean esFlowBean;

	@Autowired
	private DSLocarnoServiceInterface dsLocarnoService;
	
	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;
	
    // Models
    private static final String MODEL_LOCARNO_SEARCH_FORM = "locarnoSearchForm";
    private static final String MODEL_LOCARNO_FORM = "locarnoForm";
    private static final String MODEL_LOCARNO_COMPLEX_FORM = "locarnoComplexForm";
    
    // Views
    private static final String VIEW_LOCARNO_LIST = "designs/locarno/locarno_list";
    private static final String VIEW_LOCARNO_ADD_CLASS = "designs/locarno/locarno_addClass";
    private static final String VIEW_LOCARNO_LIST_CLASS = "designs/locarno/locarno_listClass";
    private static final String VIEW_LOCARNO_ADD_NEW_PRODUCT = "designs/locarno/locarno_addNewProduct";
    private static final String VIEW_LOCARNO_ADD_NEW_COMPLEX_PRODUCT = "designs/locarno/locarno_addNewComplexProduct";
    
    /**
     * Returns the model and view to add a new Locarno class. 
     * @param id
     * @return
     */
    @RequestMapping(value = "displayAddLocarnoClass", method = RequestMethod.GET)
    public ModelAndView formAddClass() {
    	ModelAndView modelAndView = new ModelAndView(VIEW_LOCARNO_ADD_CLASS);
    	modelAndView.addObject(MODEL_LOCARNO_SEARCH_FORM, new LocarnoSearchForm());
    	// Set classes and reset Locarno subclasses
    	setLocarnoClasses();
    	setLocarnoSubclasses(null);
    	return modelAndView;
    }
    
    /**
     * Adds a list of Locarno classifications in the collection stored in the session.
     *
     * @param command object that contains the Locarno information.
     * @param result manage the validation results of the form object
     * @return Locarno object view with the new Locarno new product added
     */
    
    @RequestMapping(value = "addLocarnoClass", method = RequestMethod.POST)
    public ModelAndView onSubmitAddClass(@ModelAttribute LocarnoSearchForm locarnoSearchForm, BindingResult result) {
     	ModelAndView mv = new ModelAndView(VIEW_LOCARNO_LIST);
   		for (LocarnoComplexForm locarnoForm : locarnoSearchForm.getLocarnoClassificationsSelected()){
   			if (locarnoForm != null) {
   				// We check if the object has already been added.
   				SEARCH_IF_EXISTS: {
   					for (LocarnoAbstractForm locarnoFormAdded : esFlowBean.getLocarno()) {
   						if (StringUtils.equals(locarnoFormAdded.getId(), locarnoForm.getId())){
   							break SEARCH_IF_EXISTS;
   						}
   					}
   					esFlowBean.addObject(locarnoForm);	
   				}
   			}
    	}
    	return mv;
    }	

    /**
     * Returns the model and view to add a new Locarno product class.
     * @param id
     * @return
     */
    @RequestMapping(value = "displayAddLocarnoNewProduct", method = RequestMethod.GET)
    public ModelAndView formAddNewProduct(@RequestParam(required = false, value = "id") String id) {
    	// Set classes and reset Locarno subclasses
    	setLocarnoClasses();
    	setLocarnoSubclasses(null);
    	ModelAndView model = innerFormBackingObject(id, esFlowBean, new AddParameters(LocarnoForm.class, MODEL_LOCARNO_FORM, null, VIEW_LOCARNO_ADD_NEW_PRODUCT, null)); 
    	model.addObject("locarnoId", id);
    	return model;
    }

    /**
     * Adds a new Locarno product in the collection stored in the session.
     *
     * @param command object that contains the Locarno information.
     * @param result manage the validation results of the form object
     * @return Locarno object view with the new Locarno new product added
     */
    
    @RequestMapping(value = "addLocarnoNewProduct", method = RequestMethod.POST)
    public ModelAndView onSubmitAddNewProduct(@ModelAttribute LocarnoForm locarnoForm, BindingResult result, @RequestParam(required = false, value = "id") String id) {
        AddParameters addParameters = new AddParameters(LocarnoForm.class, MODEL_LOCARNO_FORM, VIEW_LOCARNO_LIST, VIEW_LOCARNO_ADD_NEW_PRODUCT, null);
        locarnoForm.setId(id);
    	ModelAndView modelAndView = onSubmit(locarnoForm, esFlowBean, addParameters, result);
    	if (modelAndView.getViewName().equals(VIEW_LOCARNO_ADD_NEW_PRODUCT)){
    		// If there are errors we reload Locarno subclasses.
    		setLocarnoSubclasses(locarnoForm.getLocarnoClassification().getLocarnoClass().getClazz());
    	}
    	return modelAndView;
    }
    
    /**
     * Returns the model and view to add a new complex Locarno product class.
     * @param id
     * @return
     */
    @RequestMapping(value = "displayAddLocarnoNewComplexProduct", method = RequestMethod.GET)
    public ModelAndView formAddNewComplexProduct(@RequestParam(required = false, value = "id") String id) {
    	// Set classes and reset Locarno subclasses
    	setLocarnoClasses();
    	setLocarnoSubclasses(null);
    	
    	ModelAndView model  =  innerFormBackingObject(id, esFlowBean, new AddParameters(LocarnoComplexForm.class, MODEL_LOCARNO_COMPLEX_FORM, null, VIEW_LOCARNO_ADD_NEW_COMPLEX_PRODUCT, null));
    	model.addObject("locarnoId", id);
    	return model;
    }
    
    /**
     * Adds a new Locarno complex product in the collection stored in the session.
     * 
     * @param locarnoComplexForm
     * @param result
     * @return
     */
    
    @RequestMapping(value = "addNewLocarnoComplexProduct", method = RequestMethod.POST)
    public ModelAndView onSubmitAddNewComplexProduct(@ModelAttribute LocarnoComplexForm locarnoComplexForm, BindingResult result, @RequestParam(required = false, value = "id") String id) {
    	AddParameters addParameters = new AddParameters(LocarnoComplexForm.class, MODEL_LOCARNO_COMPLEX_FORM, VIEW_LOCARNO_LIST, VIEW_LOCARNO_ADD_NEW_COMPLEX_PRODUCT, null);
    	locarnoComplexForm.setId(id);
    	return onSubmit(locarnoComplexForm, esFlowBean, addParameters, result);
    }

    /**
     * Entry point to get a new product form for edit.
     * 
     * @param id the id of the new Locarno product.
     * @return the view containing the new Locarno product form to edit.
     */
    @RequestMapping(value = "getLocarnoNewProductForEdit", method = RequestMethod.GET)
    public ModelAndView getNewProduct(@RequestParam(required = true, value = "id") String id) {
    	LocarnoAbstractForm found = esFlowBean.getObject(LocarnoAbstractForm.class, id);
    	if (found != null){
    		// Set classes
        	setLocarnoClasses();
        	ModelAndView model;
    		if (found instanceof LocarnoForm) {
	    		// We obtain the Locarno subclasses from the Locarno class to fill the select.
    			setLocarnoSubclasses(((LocarnoForm) found).getLocarnoClassification().getLocarnoClass().getClazz());
	    		model = innerFormBackingObject(id, esFlowBean, new AddParameters(LocarnoForm.class, MODEL_LOCARNO_FORM, null, VIEW_LOCARNO_ADD_NEW_PRODUCT, null));
    		} else {
    			// LocarnoComplexForm
    			model = innerFormBackingObject(id, esFlowBean, new AddParameters(LocarnoComplexForm.class, MODEL_LOCARNO_COMPLEX_FORM, null, VIEW_LOCARNO_ADD_NEW_COMPLEX_PRODUCT, null));
    		}
    		model.addObject("locarnoId", id);
    		return model;
    	} else {
    		return null;
    	}
    }
    
    /**
     * Binder that converts a Locarno classification id to a Locarno form for the LocarnoSearchModel.
     */
    @InitBinder
    @Override
    public void initBinder(WebDataBinder binder) {
    	super.initBinder(binder);
        binder.registerCustomEditor(List.class, "locarnoClassificationsSelected", new CustomCollectionEditor(List.class) {
            @Override
            protected Object convertElement(Object id) {
            	LocarnoClassification classification = esFlowBean.getLocarnoClassifications().get(id);
				LocarnoComplexForm form;
            	if (classification != null) {
					form = new LocarnoComplexForm();
					List<LocarnoClass> classList = new ArrayList<LocarnoClass>();
					classList.add(classification.getLocarnoClass());
					form.setClasses(classList);
					form.setType(LocarnoComplexKindForm.SINGLE_PRODUCT);
					form.setIndication(classification.getIndication());
					form.setId(classification.getId());
				} else {
            		form = null;
            	}
            	return form;
            }
        });
        
        binder.registerCustomEditor(List.class, "classes", new CustomCollectionEditor(List.class) {
            @Override
            protected Object convertElement(Object id) {
            	try {
            		String classAndSubclass = (String) id;
            		String clazz[] = StringUtils.split(classAndSubclass, '-');
            		return new LocarnoClass(clazz[0], clazz[1]);
            	} catch(Exception e){
            		return null;
            	}
            }
        });
        
        binder.registerCustomEditor(LocarnoComplexKindForm.class, new PropertyEditorSupport() {
        	 @Override
             public void setAsText(String value) {
                 setValue(LocarnoComplexKindForm.fromValue(value));
             }
        });
    }
    
    /**
     * 
     */
    private void setLocarnoClasses() {
    	String lang = "en";//esFlowBean.getFirstLang
    	if (StringUtils.isEmpty(lang)){
    		lang=configurationServiceDelegator.getLanguageOffice();
    	}
    	List<LocarnoClass> locarnoClasses = dsLocarnoService.getLocarnoClasses(lang);
    	esFlowBean.setLocarnoClasses(locarnoClasses);
    }
    
    /**
     * This method sets in the esFlowBean the list of Locarno subclasses.
     * @param clazz Locarno class for obtaining the subclasses.
     */
    private void setLocarnoSubclasses(String clazz) {
    	List<LocarnoClass> subclasses;
    	if (StringUtils.isNotEmpty(clazz)) {
    		subclasses = this.getLocarnoSubclasses(clazz);
    	} else {
    		subclasses = new ArrayList<LocarnoClass>();
    	}
    	esFlowBean.setLocarnoSubclasses(subclasses);
    }
    
    /**
     * 
     * @param clazz
     * @return
     */
    @RequestMapping(value = "getLocarnoClassifications", method = RequestMethod.POST)
    public ModelAndView getLocarnoClassifications(@ModelAttribute("locarnoSearchForm") LocarnoSearchForm command, BindingResult result) {
    	ModelAndView modelAndView = new ModelAndView();
 		modelAndView.addObject("locarnoSearchForm", command);
    	AddParameters parameters = new AddParameters(null, null, VIEW_LOCARNO_LIST_CLASS, VIEW_LOCARNO_ADD_CLASS, null);
    	
    	// The rules have to validate that at least one search field has been informed.
    	if (validateCommand(command, result, parameters)) {
    		
    		LocarnoClassification searchData = command.getSearchData();
    		String lang = "en";//esFlowBean.getFirstLang();
        	if (StringUtils.isEmpty(lang)){
        		lang=configurationServiceDelegator.getLanguageOffice();
        	}
    		Map<String, LocarnoClassification> locarnoClassifications = dsLocarnoService.getLocarnoClassifications(searchData.getIndication(), searchData.getLocarnoClass().getClazz(), searchData.getLocarnoClass().getSubclass(), lang);

     		esFlowBean.setLocarnoClassifications(locarnoClassifications);
     		
     		modelAndView.setViewName(parameters.getSuccessView());
    	} else {
    		// If there are errors we reload Locarno subclasses.
    		setLocarnoSubclasses(command.getSearchData().getLocarnoClass().getClazz());
    		modelAndView.setViewName(parameters.getFormView());
    	}
    	return modelAndView;
    }

    /**
     * 
     * @param clazz
     * @return
     */
    @RequestMapping(value = "getLocarnoSubclasses", method = RequestMethod.GET, headers = "accept=application/json")
    @ResponseBody
    public List<LocarnoClass> getLocarnoSubclasses(@RequestParam(required = true, value = "clazz") String clazz) {
    	String lang = esFlowBean.getFirstLang();
    	if (StringUtils.isEmpty(lang)){
    		lang=configurationServiceDelegator.getLanguageOffice();
    	}
    	return dsLocarnoService.getLocarnoSubclasses(clazz, lang);
    }

}
