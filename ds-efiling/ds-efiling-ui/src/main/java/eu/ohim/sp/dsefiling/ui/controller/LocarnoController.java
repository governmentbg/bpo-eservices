package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.classification.LocarnoInformation;
import eu.ohim.sp.common.ui.form.classification.LocarnoTermJSON;
import eu.ohim.sp.common.ui.form.design.*;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.util.LocarnoValidatorUtil;
import eu.ohim.sp.core.domain.design.KeyTextUIClass;
import eu.ohim.sp.core.domain.design.TermsSearch;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.interfaces.ImportServiceInterface;
import eu.ohim.sp.dsefiling.ui.transformer.ValidationServiceTransformer;
import eu.ohim.sp.dsefiling.ui.util.DSEfilingConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * RFC DS Class Integration Manages the action relative to Indications
 * product(/Locarno) Created by Shivomkara Chaturvedi on 26/07/2018.
 */
@RestController
public class LocarnoController extends AddAbstractController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LocarnoController.class);

	/** The configuration service delegator. */
	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

	/** Manage the message. */
	@Autowired
	private MessageSource messageSource;

	/** The locale resolver. */
	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private DSFlowBean dsFlowBean;

	/** The import service */
	@Autowired
	private ImportServiceInterface importService;

	@EJB(lookup="java:global/businessRulesLocal")
	private RulesService businessRulesService;

	@Value("${sp.office}")
    private String offices;
	
	/**
	 * Autocomplete.
	 *
	 * @param args            the args
	 * @param language            the language
	 * @return the string
	 * @throws Exception the exception
	 */

	@RequestMapping(value = "autocompleteProductIndication", method = RequestMethod.GET, produces = DSEfilingConstants.PRODUCES_APPJSON)
	public String autocomplete(@RequestParam(required = true, value = "args") String args,
			@RequestParam(required = true, value = "language") String language) throws Exception {

		LOGGER.debug("START autocomplete");

		Integer maxNumberOfResults = Integer.parseInt(configurationServiceDelegator.getValueFromGeneralComponent(ConfigurationServiceDelegatorInterface.LOCARNO_TERM_AUTOCOMPLETE_MAXRESULTS));
		String response = importService.getLocarnoAutocomplete(language, args, maxNumberOfResults);

		LOGGER.debug("END autocomplete");

		return response;
	}

	/**
	 * Gets the classes.
	 *
	 * @param request the request
	 * @return the classes
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "getLocarnoClassesFromDSClass", method = RequestMethod.GET, produces = DSEfilingConstants.PRODUCES_APPJSON)
	public List<KeyTextUIClass> getClasses(HttpServletRequest request) throws Exception {
		List<KeyTextUIClass> ret = new ArrayList<>();
		ret.add(new KeyTextUIClass(Integer.valueOf(-1), messageSource.getMessage(DSEfilingConstants.DS_LOCARNO_ALL_CLASSES, new String[] {}, localeResolver.resolveLocale(request))));
		String language = dsFlowBean.getFirstLang();
        if (StringUtils.isEmpty(language)){
            language=configurationServiceDelegator.getLanguageOffice();
        }
        List<KeyTextUIClass> keyTextUIClassList = null;
        try{
            keyTextUIClassList = importService.getLocarnoClasses(language, null);
        }
        catch (Exception e){
            LOGGER.error("An error occured with locarno import service.", e);
        }
        if(keyTextUIClassList==null || keyTextUIClassList.isEmpty()) {
            LOGGER.warn("Returning default class list.");
            keyTextUIClassList = configurationServiceDelegator.getDefaultLocarnoClasses(language);
        }
        ret.addAll(keyTextUIClassList);
		return ret;
	}

	/**
	 * Gets the sub classes.
	 *
	 * @param request the request
	 * @param selectedClass the selected class
	 * @return the sub classes
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "getLocarnoSubclassesFromDSClass", method = RequestMethod.GET, produces = DSEfilingConstants.PRODUCES_APPJSON)
	public List<KeyTextUIClass> getSubClasses(HttpServletRequest request,
                                              @RequestParam(required = true, value = "selectedClass") Integer selectedClass) throws Exception {
		List<KeyTextUIClass> ret = new ArrayList<>();
		ret.add(new KeyTextUIClass(Integer.valueOf(-1), messageSource.getMessage(DSEfilingConstants.DS_LOCARNO_ALL_SUBCLASSES, new String[] {}, localeResolver.resolveLocale(request))));
		String language = dsFlowBean.getFirstLang();
        if (StringUtils.isEmpty(language)){
            language=configurationServiceDelegator.getLanguageOffice();
        }
		if (selectedClass != null && selectedClass > 0) {
            List<KeyTextUIClass> keyTextUIClassList = null;
            try{
                keyTextUIClassList = importService.getLocarnoSubclasses(selectedClass, language, null);
            }
            catch (Exception e){
                LOGGER.error("An error occured with locarno import service.", e);
            }
            if(keyTextUIClassList==null || keyTextUIClassList.isEmpty()){
                LOGGER.warn("Returning default subclass list.");
                keyTextUIClassList = configurationServiceDelegator.getDefaultLocarnoSubclasses(selectedClass, language, null);
            }
			ret.addAll(keyTextUIClassList);
		}
		return ret;
	}

	/**
	 * Edits the term.
	 *
	 * @param id the id
	 * @param newDescription the new description
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "editTerm", method = RequestMethod.POST, produces = DSEfilingConstants.PRODUCES_APPJSON)
	public ModelAndView editTerm(@RequestParam("id") String id, @RequestParam(value = "newDescription", required = false) String newDescription) throws Exception {

		LocarnoValidatorUtil validator = new LocarnoValidatorUtil();
		ModelAndView modelAndView = null;
		LocarnoAbstractForm locarnoForm = dsFlowBean.getObject(LocarnoAbstractForm.class, id);
		boolean isComplexForm = locarnoForm instanceof LocarnoComplexForm ? true: false;

		AddParameters addParameters = new AddParameters(isComplexForm? LocarnoComplexForm.class: LocarnoForm.class, isComplexForm? DSEfilingConstants.MODEL_LOCARNO_COMPLEX_FORM : DSEfilingConstants.MODEL_LOCARNO_FORM, DSEfilingConstants.VIEW_LOCARNO_TABLE, null,
			null);
		BindingResult result = new BeanPropertyBindingResult(locarnoForm, isComplexForm? DSEfilingConstants.MODEL_LOCARNO_COMPLEX_FORM : DSEfilingConstants.MODEL_LOCARNO_FORM);

		List<LocarnoAbstractForm> locarnoList = dsFlowBean.getLocarno();
		if(locarnoForm!=null){
			if(StringUtils.isNotBlank(newDescription)){
				if(!isComplexForm) {
					((LocarnoForm)locarnoForm).getLocarnoClassification().setIndication(newDescription);
				} else {
					((LocarnoComplexForm)locarnoForm).setIndication(newDescription);
				}
				if(validator.validateIfNotExistsForEdit(locarnoList, locarnoForm, id)){
					LocarnoAbstractForm validatedForm = importService.validateLocarnoForm(dsFlowBean.getFirstLang(), locarnoForm);
					modelAndView = onSubmit(validatedForm, dsFlowBean, addParameters, result);
				} else{
					dsFlowBean.removeObject(LocarnoAbstractForm.class, locarnoForm.getId());
					modelAndView = new ModelAndView(addParameters.getSuccessView());
				}
			}else{
				modelAndView = onSubmit(locarnoForm, dsFlowBean, addParameters, result);
			}
		}else {
			modelAndView = new ModelAndView(addParameters.getSuccessView());
		    }
		return modelAndView;

	} 

	/**
	 * View the suggested terms and the terms with validation not possible.
	 *
	 * @param langId the lang id
	 * @return the collection
	 */
	@RequestMapping(value = "getTerms", headers = DSEfilingConstants.HEADERS_ACC, method = RequestMethod.GET, produces = DSEfilingConstants.PRODUCES_APPJSON)
	public Collection<LocarnoTermJSON> terms(@RequestParam(required = false, value = "langId") String langId) {

		Collection<LocarnoTermJSON> termsData = new ArrayList<>();
		for (LocarnoAbstractForm form : dsFlowBean.getLocarno()) {
			String name = form.getValidationCode().name();
			if(!StringUtils.equals(name, ValidationCode.valid.name())){
				LocarnoTermJSON generateTermJSON = ValidationServiceTransformer.generateTermJSON(form);
				if (generateTermJSON != null) {
					termsData.add(generateTermJSON);
				}
			}
		}
		return termsData;
	}

	/**
	 * Modify the viewed terms.
	 *
	 * @param id the id
	 * @param term the term
	 * @param locarnoClass the locarno class
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "modifyTerm", method = RequestMethod.GET)
	public ModelAndView modifyTerm(@RequestParam(required = false, value = "id") String id,
                                   @RequestParam(required = false, value = "term") String term,
                                   @RequestParam(required = false, value = "locarnoClass") String locarnoClass) throws Exception {

		AddParameters addParameters = new AddParameters(LocarnoForm.class, DSEfilingConstants.MODEL_LOCARNO_FORM, DSEfilingConstants.VIEW_LOCARNO_TABLE, null, null);
		LocarnoValidatorUtil validator = new LocarnoValidatorUtil();
		ModelAndView modelAndView = null;
		LocarnoAbstractForm locarnoForm = dsFlowBean.getObject(LocarnoAbstractForm.class, id);
		List<LocarnoAbstractForm> locarnoList = dsFlowBean.getLocarno();
		String[] classArray = locarnoClass.split("-");
		if(locarnoForm instanceof LocarnoForm) {
			((LocarnoForm)locarnoForm).getLocarnoClassification().setIndication(term);

			((LocarnoForm)locarnoForm).getLocarnoClassification().getLocarnoClass().setClazz(classArray[0]);
			((LocarnoForm)locarnoForm).getLocarnoClassification().getLocarnoClass().setSubclass(classArray[1]);
			((LocarnoForm)locarnoForm).getLocarnoClassification().setValidation(ValidationCode.valid);
		} else {
			LocarnoComplexForm complexForm = (LocarnoComplexForm)locarnoForm;
			complexForm.setIndication(term);
			complexForm.getClasses().clear();
			LocarnoClass locarnoComplexClass = new LocarnoClass();
			locarnoComplexClass.setClazz(classArray[0]);
			locarnoComplexClass.setSubclass(classArray[1]);
			complexForm.getClasses().add(locarnoComplexClass);
			complexForm.setValidationCode(ValidationCode.valid);
		}
		
		if(validator.validateIfNotExistsForEdit(locarnoList, locarnoForm, id)){
			BindingResult result = new BeanPropertyBindingResult(locarnoForm, DSEfilingConstants.MODEL_LOCARNO_FORM);
			modelAndView = onSubmit(locarnoForm, dsFlowBean, addParameters, result);
		}else{
			dsFlowBean.removeObject(LocarnoForm.class, locarnoForm.getId());
			modelAndView = new ModelAndView(addParameters.getSuccessView());
		}
		
		return modelAndView;
	}

	/**
	 * Get Locarno Classification Taxonomy.
	 *
	 * @param searchMode the search mode
	 * @param page the page
	 * @param pageSize the page size
	 * @param searchInput the search input
	 * @param selectedClass the selected class
	 * @param selectedSubclass the selected subclass
	 * @param nodeId the node id
	 * @return the locarno classification taxonomy
	 * @throws Exception the exception
	 */
	@GetMapping(value = "locarnoClassificationTaxonomy", produces = DSEfilingConstants.PRODUCES_APPJSON)
	public TermsSearch getLocarnoClassificationTaxonomy(
			@RequestParam(required = false, value = "searchMode") String searchMode,
			@RequestParam(required = true, value = "page") Integer page,
			@RequestParam(required = true, value = "pageSize") Integer pageSize,
			@RequestParam(required = false, value = "searchInput") String searchInput,
			@RequestParam(required = false, value = "selectedClass") Integer selectedClass,
			@RequestParam(required = false, value = "selectedSubclass") Integer selectedSubclass,
			@RequestParam(required = false, value = "taxoConceptNodeId") String nodeId) throws Exception {

		LOGGER.debug("START locarnoClassificationTaxonomy");
		String language = dsFlowBean.getFirstLang();
        if (StringUtils.isEmpty(language)){
            language=configurationServiceDelegator.getLanguageOffice();
        }
		TermsSearch response = importService.getTerms(offices, language, page, pageSize, searchInput, selectedClass, selectedSubclass, searchMode, nodeId, null);
		LOGGER.debug("END locarnoClassificationTaxonomy");

		return response;
	}

	/**
	 * Gets product indication translation.
	 *
	 * @param languageTo the language to
	 * @param termTranslate the term translate
	 * @param productIndicationIdentifier            the product indication identifier
	 * @param selectedClass the selected class
	 * @param subclass the subclass
	 * @return the product indication translation
	 * @throws Exception the exception
	 */
	@GetMapping(value = "getProductIndicationTranslation", produces = DSEfilingConstants.PRODUCES_APPJSON)
	public ModelAndView getProductIndicationTranslation(
			@RequestParam(required = false, value = "languageTo") String languageTo,
			@RequestParam(required = false, value = "termTranslate") String termTranslate,
			@RequestParam(required = true, value = "productIndicationIdentifier") String productIndicationIdentifier,
			@RequestParam(required = false, value = "selectedClass") String selectedClass,
			@RequestParam(required = false, value = "subclass") String subclass) throws Exception {
		LOGGER.debug("START getProductIndicationTranslation");
		ModelAndView mav = new ModelAndView();
		String languageFrom = dsFlowBean.getFirstLang();
        if (StringUtils.isEmpty(languageFrom)){
            languageFrom=configurationServiceDelegator.getLanguageOffice();
        }
		List<KeyTextUIClass> translationList = importService.getTranslations(languageFrom, languageTo, termTranslate, productIndicationIdentifier, selectedClass, subclass);
		mav.addObject("translationList", translationList);
		mav.setViewName("locarno/dsclass/locarnoTranslationRow");
		LOGGER.debug("END getProductIndicationTranslation");

		return mav;
	}

	/**
	 * Search product indications.
	 *
	 * @param searchMode the search mode
	 * @param page the page
	 * @param pageSize the page size
	 * @param searchInput the search input
	 * @param selectedClass the selected class
	 * @param selectedSubclass the selected subclass
	 * @param nodeId the node id
	 * @return the terms search
	 * @throws Exception the exception
	 */
	@GetMapping(value = "searchProductIndications", produces = DSEfilingConstants.PRODUCES_APPJSON)
	public TermsSearch searchProductIndications(@RequestParam(required = false, value = "searchMode") String searchMode,
                                                @RequestParam(required = true, value = "page") Integer page,
                                                @RequestParam(required = true, value = "pageSize") Integer pageSize,
                                                @RequestParam(required = false, value = "searchInput") String searchInput,
                                                @RequestParam(required = false, value = "selectedClass") Integer selectedClass,
                                                @RequestParam(required = false, value = "selectedSubclass") Integer selectedSubclass,
                                                @RequestParam(required = false, value = "taxoConceptNodeId") String nodeId,
												@RequestParam(required = false, value = "sortBy")String sortBy) throws Exception {
		LOGGER.debug("START searchProductIndications");
		
		String language = dsFlowBean.getFirstLang();
        if (StringUtils.isEmpty(language)){
            language=configurationServiceDelegator.getLanguageOffice();
        }
		TermsSearch response = importService.getTerms(offices, language, page, pageSize, searchInput, selectedClass, selectedSubclass, searchMode, nodeId, sortBy);
		LOGGER.debug("END searchProductIndications");
		return response;
	}

	/**
	 * Gets the allowed classes.
	 *
	 * @param editMode the edit mode
	 * @return the allowed classes
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "getAllowedLocarnoClasses", method = RequestMethod.GET, produces = DSEfilingConstants.PRODUCES_APPJSON)
	public Collection<String> getAllowedClasses(@RequestParam(required = false, value = "editMode", defaultValue = "false") String editMode) throws Exception {
		
		LocarnoInformation brLocarnoInfo = new LocarnoInformation();
		Set<Integer> allLocarnoClasses = new TreeSet<>();
		allLocarnoClasses.add(-1);
		String language = dsFlowBean.getFirstLang();
        if (StringUtils.isEmpty(language)){
            language=configurationServiceDelegator.getLanguageOffice();
        }
		List<KeyTextUIClass> classList=importService.getLocarnoClasses(language, null);
		Collection<String> classIds=classList.stream().map(ui -> ui.getKey().toString()).collect(Collectors.toCollection(TreeSet::new));
		
		return classIds;
		
	}

	@RequestMapping(value = "switchProductType", method = RequestMethod.POST)
	public ModelAndView switchProductType(@RequestParam("id") String id)  {
		LocarnoAbstractForm form = flowBean.getObject(LocarnoAbstractForm.class, id);
		if(form != null){
			LocarnoComplexKindForm currentType = form.getType();
			switch (currentType){
				case SINGLE_PRODUCT: form.setType(LocarnoComplexKindForm.SET_COMPOSITION); break;
				case SET_COMPOSITION: form.setType(LocarnoComplexKindForm.SINGLE_PRODUCT); break;
				default: form.setType(LocarnoComplexKindForm.SINGLE_PRODUCT); break;
			}

		} else {
			throw new SPException("Locarno Abstract Forn with id " +id+" not found");
		}

		return new ModelAndView(DSEfilingConstants.VIEW_LOCARNO_TABLE);
	}
}
