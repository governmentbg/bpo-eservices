package eu.ohim.sp.dsefiling.ui.controller;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.RemoveAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.RemoveParameters;
import eu.ohim.sp.common.ui.form.design.*;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.util.LocarnoValidatorUtil;
import eu.ohim.sp.dsefiling.ui.service.interfaces.ImportServiceInterface;
import eu.ohim.sp.dsefiling.ui.transformer.ValidationServiceTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSLocarnoServiceInterface;

import static eu.ohim.sp.dsefiling.ui.util.DSEfilingConstants.*;

/**
 * Controller in charge of handling the adding and the editing of the Locarno classes.
 * @author serrajo
 */
@Controller
public class AddLocarnoController extends AddAbstractController {

	@Autowired
    private DSFlowBean dsFlowBean;

	@Autowired
	private DSLocarnoServiceInterface dsLocarnoService;
	
    // Models
    private static final String MODEL_LOCARNO_SEARCH_FORM = "locarnoSearchForm";
    private static final String MODEL_LOCARNO_FORM = "locarnoForm";
    private static final String MODEL_LOCARNO_COMPLEX_FORM = "locarnoComplexForm";
    
    // Views
    private static final String VIEW_LOCARNO_LIST = "locarno/locarno_list";
    private static final String VIEW_LOCARNO_ADD_CLASS = "locarno/locarno_addClass";
    private static final String VIEW_LOCARNO_LIST_CLASS = "locarno/locarno_listClass";
    private static final String VIEW_LOCARNO_ADD_NEW_PRODUCT = "locarno/locarno_addNewProduct";
    private static final String VIEW_LOCARNO_ADD_NEW_COMPLEX_PRODUCT = "locarno/locarno_addNewComplexProduct";

	/** The configuration service delegator. */
	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

	/** The import service. */
	@Autowired
	private ImportServiceInterface importService;
    
    /**
     * Returns the model and view to add a new Locarno class. 
     *
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
     * @param locarnoSearchForm object that contains the Locarno information.
     * @param result manage the validation results of the form object
     * @return Locarno object view with the new Locarno new product added
     */
    @PreAuthorize("hasRole('Locarno_Add')")
    @RequestMapping(value = "addLocarnoClass", method = RequestMethod.POST)
    public ModelAndView onSubmitAddClass(@ModelAttribute LocarnoSearchForm locarnoSearchForm, BindingResult result) {
    	ModelAndView mv = new ModelAndView(VIEW_LOCARNO_LIST);
   		for (LocarnoComplexForm locarnoForm : locarnoSearchForm.getLocarnoClassificationsSelected()){
   			if (locarnoForm != null) {
   				// We check if the object has already been added.
   				SEARCH_IF_EXISTS: {
   					for (LocarnoAbstractForm locarnoFormAdded : dsFlowBean.getLocarno()) {
   						if (StringUtils.equals(locarnoFormAdded.getId(), locarnoForm.getId())){
   							break SEARCH_IF_EXISTS;
   						}
   					}
   					dsFlowBean.addObject(locarnoForm);	
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
    	return innerFormBackingObject(id, dsFlowBean, new AddParameters(LocarnoForm.class, MODEL_LOCARNO_FORM, null, VIEW_LOCARNO_ADD_NEW_PRODUCT, null));
    }

	/**
	 * Adds the locarno.
	 *
	 * @param addProductsRequest the add products request
	 * @param result the result
	 * @return the model and view
	 */
	@PostMapping(value = "addProductIndication", consumes = REQUEST_CONSUMES_JSON)
	public ModelAndView addLocarno(@RequestBody AddProductsRequest addProductsRequest, BindingResult result) {
		ModelAndView modelAndView=null;
		AddParameters addParameters = new AddParameters(LocarnoForm.class, MODEL_LOCARNO_FORM, VIEW_LOCARNO_TABLE, VIEW_LOCARNO_MODAL, null);
		List<LocarnoAbstractForm> locarnoList = dsFlowBean.getLocarno();
		LocarnoValidatorUtil validator = new LocarnoValidatorUtil();

		if (addProductsRequest.getProductIndications() != null && CollectionUtils.isNotEmpty(addProductsRequest.getProductIndications())) {
			IndicationProductForm toAdd = new IndicationProductForm();
			for (IndicationProductForm piParam : addProductsRequest.getProductIndications()) {
				if(validator.validateIfNotExistsForAdd(locarnoList, ValidationServiceTransformer.convertToLocarnoForm(piParam, new LocarnoForm()))){
					if (StringUtils.isNotEmpty(piParam.getIdentifier())) {
						toAdd.setIdentifier(piParam.getIdentifier());
						toAdd.setClassCode(piParam.getClassCode());
						toAdd.setSubclassCode(piParam.getSubclassCode());
						toAdd.setText(piParam.getText());
						if(piParam.getText().contains("’"))
							toAdd.setText(piParam.getText().replace("’", "\'"));
						toAdd.setValidation(ValidationCode.valid);
					}
					LocarnoForm locarnoForm = new LocarnoForm();
					if(StringUtils.isNotBlank(addProductsRequest.getDesignIdentifier())){
						RemoveAbstractController remove = new RemoveAbstractController();
						remove.handle(addProductsRequest.getDesignIdentifier(), dsFlowBean, new RemoveParameters(LocarnoAbstractForm.class, MODEL_LOCARNO_FORM, VIEW_LOCARNO_TABLE, VIEW_LOCARNO_TABLE));
					}

					LocarnoForm convertToLocarnoForm = ValidationServiceTransformer.convertToLocarnoForm(toAdd, locarnoForm);
					modelAndView = onSubmit(convertToLocarnoForm, dsFlowBean, addParameters, result);
				}else {
					modelAndView = new ModelAndView(addParameters.getSuccessView());
				}
			}
		}
		return modelAndView;
	}

	/**
	 * Adds a new Locarno product in the collection stored in the session.
	 *
	 * @param locarnoForm object that contains the Locarno information.
	 * @param result manage the validation results of the form object
	 * @return Locarno object view with the new Locarno new product added
	 */
	@PreAuthorize("hasRole('Locarno_Add')")
	@RequestMapping(value = "addLocarnoNewProduct", method = RequestMethod.POST)
	public ModelAndView onSubmitAddNewProduct(@ModelAttribute LocarnoForm locarnoForm, BindingResult result) {
		AddParameters addParameters = new AddParameters(LocarnoForm.class, MODEL_LOCARNO_FORM, VIEW_LOCARNO_TABLE, VIEW_LOCARNO_MODAL, null);

		List<LocarnoAbstractForm> locarnoList = dsFlowBean.getLocarno();
		LocarnoValidatorUtil validator = new LocarnoValidatorUtil();

		String indication = locarnoForm.getLocarnoIndication();
		String[] arrIndication = indication.split(";");
		ModelAndView modelAndView=null;
		for (String strIndi : arrIndication) {
			LocarnoForm lForm= new LocarnoForm();

			try {
				lForm=(LocarnoForm) locarnoForm.clone();
			}catch (CloneNotSupportedException e) {
				throw new SPException(e);
			}

			lForm.getLocarnoClassification().setIndication(strIndi);

			if(validator.validateIfNotExistsForAdd(locarnoList, lForm)){
				LocarnoAbstractForm validatedForm = importService.validateLocarnoForm(getLang(), locarnoForm);
				modelAndView = onSubmit(validatedForm, dsFlowBean, addParameters, result);
			}else{
				modelAndView = new ModelAndView(addParameters.getSuccessView());
			}
		}
		if (modelAndView!=null && modelAndView.getViewName().equals(VIEW_LOCARNO_MODAL)){
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
    	return innerFormBackingObject(id, dsFlowBean, new AddParameters(LocarnoComplexForm.class, MODEL_LOCARNO_COMPLEX_FORM, null, VIEW_LOCARNO_ADD_NEW_COMPLEX_PRODUCT, null));
    }
    
    /**
     * Adds a new Locarno complex product in the collection stored in the session.
     * 
     * @param locarnoComplexForm
     * @param result
     * @return
     */
    @PreAuthorize("hasRole('Locarno_Add')")
    @RequestMapping(value = "addNewLocarnoComplexProduct", method = RequestMethod.POST)
    public ModelAndView onSubmitAddNewComplexProduct(@ModelAttribute LocarnoComplexForm locarnoComplexForm, BindingResult result) {
    	AddParameters addParameters = new AddParameters(LocarnoComplexForm.class, MODEL_LOCARNO_COMPLEX_FORM, VIEW_LOCARNO_TABLE, VIEW_LOCARNO_MODAL, null);
		LocarnoValidatorUtil validator = new LocarnoValidatorUtil();
		List<LocarnoAbstractForm> locarnoList = dsFlowBean.getLocarno();
		ModelAndView modelAndView=null;

		if(validator.validateIfNotExistsForAdd(locarnoList, locarnoComplexForm)){
			LocarnoAbstractForm validatedForm = importService.validateLocarnoForm(getLang(), locarnoComplexForm);
			modelAndView = onSubmit(validatedForm, dsFlowBean, addParameters, result);
		}else{
			modelAndView = new ModelAndView(addParameters.getSuccessView());
		}

    	return modelAndView;
    }

    /**
     * Entry point to get a new product form for edit.
     * 
     * @param id the id of the new Locarno product.
     * @return the view containing the new Locarno product form to edit.
     */
    @RequestMapping(value = "getLocarnoNewProductForEdit", method = RequestMethod.GET)
    public ModelAndView getNewProduct(@RequestParam(required = true, value = "id") String id) {
    	LocarnoAbstractForm found = dsFlowBean.getObject(LocarnoAbstractForm.class, id);
    	if (found != null){
    		// Set classes
        	setLocarnoClasses();
    		if (found instanceof LocarnoForm) {
	    		// We obtain the Locarno subclasses from the Locarno class to fill the select.
    			setLocarnoSubclasses(((LocarnoForm) found).getLocarnoClassification().getLocarnoClass().getClazz());
	    		return innerFormBackingObject(id, dsFlowBean, new AddParameters(LocarnoForm.class, MODEL_LOCARNO_FORM, null, VIEW_LOCARNO_ADD_NEW_PRODUCT, null));
    		} else {
    			// LocarnoComplexForm
    			return innerFormBackingObject(id, dsFlowBean, new AddParameters(LocarnoComplexForm.class, MODEL_LOCARNO_COMPLEX_FORM, null, VIEW_LOCARNO_ADD_NEW_COMPLEX_PRODUCT, null));
    		}
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
            	LocarnoClassification classification = dsFlowBean.getLocarnoClassifications().get(id);
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
    	List<LocarnoClass> locarnoClasses = dsLocarnoService.getLocarnoClasses(getLang());
    	dsFlowBean.setLocarnoClasses(locarnoClasses);
    }
    
    /**
     * This method sets in the dsFlowBean the list of Locarno subclasses.
     * @param clazz Locarno class for obtaining the subclasses.
     */
    private void setLocarnoSubclasses(String clazz) {
    	List<LocarnoClass> subclasses;
    	if (StringUtils.isNotEmpty(clazz)) {
    		subclasses = this.getLocarnoSubclasses(clazz);
    	} else {
    		subclasses = new ArrayList<LocarnoClass>();
    	}
    	dsFlowBean.setLocarnoSubclasses(subclasses);
    }
    
    /**
     * 
     * @param command
     * @return
     */
    @RequestMapping(value = "getLocarnoCassifications", method = RequestMethod.POST)
    public ModelAndView getLocarnoCassifications(@ModelAttribute("locarnoSearchForm") LocarnoSearchForm command, BindingResult result) {
    	ModelAndView modelAndView = new ModelAndView();
 		modelAndView.addObject("locarnoSearchForm", command);
    	AddParameters parameters = new AddParameters(null, null, VIEW_LOCARNO_LIST_CLASS, VIEW_LOCARNO_ADD_CLASS, null);
    	
    	// The rules have to validate that at least one search field has been informed.
    	if (validateCommand(command, result, parameters)) {
    		
    		LocarnoClassification searchData = command.getSearchData();
    		Map<String, LocarnoClassification> locarnoClassifications = dsLocarnoService.getLocarnoClassifications(searchData.getIndication(), searchData.getLocarnoClass().getClazz(), searchData.getLocarnoClass().getSubclass(), dsFlowBean.getFirstLang());

     		dsFlowBean.setLocarnoClassifications(locarnoClassifications);
     		
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
    	return dsLocarnoService.getLocarnoSubclasses(clazz, getLang());
    }

	/**
	 * Gets the lang.
	 *
	 * @return the lang
	 */
	private String getLang(){
		String lang = dsFlowBean.getFirstLang();
		if (StringUtils.isEmpty(lang)){
			lang=configurationServiceDelegator.getLanguageOffice();
		}
		return lang;
	}

}
