/*******************************************************************************
 * * $Id::                                                       $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.FieldPropertyEditor;
import eu.ohim.sp.common.ui.exception.NoResultsException;
import eu.ohim.sp.common.ui.flow.section.LanguagesFlowBean;
import eu.ohim.sp.common.ui.form.classification.*;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.domain.classification.wrapper.ClassScope;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyConceptNode;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.flow.GoodsServicesFlowBean;
import eu.ohim.sp.ui.tmefiling.form.json.ClassScopeInfo;
import eu.ohim.sp.ui.tmefiling.form.json.ConceptForm;
import eu.ohim.sp.ui.tmefiling.form.json.SearchTerm;
import eu.ohim.sp.ui.tmefiling.form.json.TermResult;
import eu.ohim.sp.ui.tmefiling.form.terms.SearchTermResults;
import eu.ohim.sp.ui.tmefiling.service.interfaces.GoodsServicesServiceInterface;
import eu.ohim.sp.ui.tmefiling.util.TaxonomyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Manages the action relative to GS.
 * Migrated mostly to JSON services
 * 
 * @author karalch
 * 
 */
@Controller
public class GSController {

    private static final Logger logger = Logger.getLogger(GSController.class);
    private static final String DEFAULT_SEARCH_RESUTLS = "5";
    private static final String MODULE = "eu.ohim.sp.core.rules.tmefiling";

    /**
     * session bean
     */
    @Autowired
    private GoodsServicesFlowBean flowBean;

    @Autowired
    private LanguagesFlowBean languagesFlowBean;

    @Autowired
    private GoodsServicesServiceInterface goodsServices;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationService;

    @Autowired
    private FieldPropertyEditor fieldPropertyEditor;

    /** The Constant GENERIC_SERVICE_ERROR. */
    private static final String GENERIC_SERVICE_ERROR = "generic_errors_service_fail";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, fieldPropertyEditor);
    }

    @Value("${level.class.scope}")
    private Integer levelScope;

    @Value("${first.level.class.taxonomy}")
    private Integer firstLevelVisible;

    @RequestMapping(value = "getGoodsServices", method = RequestMethod.GET)
    public ModelAndView getListOfGoodsServices() {

        ModelAndView model = new ModelAndView("goods_services/provide_terms");

        flowBean.getGoodsAndServices();
        return model;
    }

    /**
     * Handles SP exceptions
     *
     * @param e
     *            the exception thrown on the controller context
     * @return a model and view that will be handled in UI
     */
    @ExceptionHandler(SPException.class)
    public ModelAndView handleException(Throwable e) {
        ModelAndView modelAndView = new ModelAndView("errors/errors");
        logger.error(e);
        modelAndView.addObject("exception", e);
        return modelAndView;
    }

    /**
     * Handles No Results exceptions
     *
     * @param e
     *            the exception thrown on the controller context
     * @return a model and view that will be handled in UI
     */
    @ExceptionHandler(NoResultsException.class)
    @ResponseBody
    public ModelAndView handleNoResultsException(Throwable e) {
        ModelAndView modelAndView = new ModelAndView("errors/errors");
        logger.error(e);
        modelAndView.addObject("exception", e);
        return modelAndView;
    }

    /**
     * Outputs a json containing information about terms not validated in the
     * given language
     *
     * @param langId
     *            the lang id of GS that we are interested
     * @return the json information
     */
    @RequestMapping(value = "terms", headers = "Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Collection<TermJSON> terms(@RequestParam(required = false, value = "langId") String langId,
                                      @RequestParam(required = false, value = "generatePath", defaultValue = "false") boolean generatePath) {

        logger.debug("START terms");
        Collection<TermJSON> termsData = new ArrayList<>();
        for (GoodAndServiceForm good : flowBean.getGoodsAndServices()) {
            boolean containsAllNiceClassHeading = goodsServices.containsAllNiceClassHeading(good);
			boolean disabledRemoval = good.isDisabledRemoval();
			List<TermJSON> terms = TaxonomyUtil.generateTermJSON(good, flowBean, langId, containsAllNiceClassHeading, disabledRemoval);
			if(generatePath){
                TaxonomyUtil.fillInTaxonomyPathInfo(terms, goodsServices);
            }
            if (terms != null) {
                termsData.addAll(terms);
            }
        }
        logger.debug("END terms");
        return termsData;
    }

    /**
     * It returns the nodes of the taxonomy on a tree view
     *
     * @param langId
     *            the language of the expected taxonomy view
     * @param term
     *            the term for which we expect the taxonomy
     * @param taxoConceptNodeId
     *            the taxonomy concept id that we expect
     */
    @RequestMapping(value = "conceptNodes", headers = "Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Collection<TaxonomyConceptNodeTreeView> parentConceptNodes(@RequestParam(value = "langId") String langId,
            @RequestParam(required = false, value = "term") String term,
            @RequestParam(required = false, value = "taxoConceptNodeId") String taxoConceptNodeId) {
        String configuredLevelLimit = configurationService.getValue(
                ConfigurationServiceDelegatorInterface.CONCEPT_TREE_NODES_PRE, MODULE);

        Integer levelLimit = null;
        try {
            if (StringUtils.isNotBlank(configuredLevelLimit)) {
                levelLimit = Integer.parseInt(configuredLevelLimit);
            }
        } catch (Exception e) {
            levelLimit = null;
        }

        Collection<TaxonomyConceptNodeTreeView> taxonomyConceptNodes = goodsServices.getTaxonomy(langId, term == null
                ? "" : term, levelLimit, taxoConceptNodeId);
        Collection<TaxonomyConceptNodeTreeView> minified = TaxonomyUtil.limitMinifiedView(
                TaxonomyUtil.getMinifiedView(taxonomyConceptNodes), firstLevelVisible, levelScope);
        for (TaxonomyConceptNodeTreeView node : minified) {
            Map<String, ClassScope> scopes = goodsServices.getClassScope(langId, null);
            if (node.isClassScope() && node.getNiceClass() != null) {
                node.setClassScopeDescription(scopes.get(node.getNiceClass().toString()) != null ? scopes.get(
                        node.getNiceClass().toString()).getDescription() : "");
            }
        }
        return minified;
    }

    @RequestMapping(value = "parentConceptNodes", headers = "Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Collection<TaxonomyConceptNode> parentConceptNodes(
            @RequestParam(required = false, value = "langId") String langId, @RequestParam("term") String term) {
        return goodsServices.getParentConceptNodes(langId, term);
    }

    /**
     * Controller that returns the page for importing nice class headings
     */
    @RequestMapping(value = "importNiceClassHeading", method = RequestMethod.GET)
    public ModelAndView getImportNiceClassHeading() {
        return new ModelAndView("goods_services/class_headings");
    }

    /**
     * Controller that requests to import a nice class heading
     *
     * @param niceClass
     *            the imported nice class heading
     * @param language
     *            the language that will be imported
     * @return ModelAndView
     */
    @RequestMapping(value = "loadNiceClassHeading", method = RequestMethod.GET)
    @ResponseBody
    public GoodAndServiceForm loadNiceClassHeading(@RequestParam("classId") String niceClass,
            @RequestParam("langId") String language) {
        return goodsServices.importNiceClassHeading(niceClass, language);
    }

    /**
     * Calls the external service of "Did you mean"
     *
     * @param searchCriteria the criteria of search
     * @param language the language of GS
     * @return
     */
	@RequestMapping(value = "autocomplete", method = RequestMethod.GET)
    @ResponseBody
	public List<String> autocomplete(@RequestParam("term") String searchCriteria, @RequestParam("langId") String language) {
		return goodsServices.getAutocomplete(language, searchCriteria);
    }

    /**
     * Controller that requests to import a nice class heading
     *
     * @param niceClass
     *            the imported nice class heading
     * @param language
     *            the language that will be imported
     * @return ModelAndView
     */
    @RequestMapping(value = "importNiceClassHeading", method = RequestMethod.POST)
    @ResponseBody
    public Result importNiceClassHeading(@RequestParam("classId") String niceClass,
            @RequestParam("langId") String language) {
        try {
            GoodAndServiceForm goodAndServiceForm = flowBean.getGoodsAndService(language, niceClass);
            GoodAndServiceForm importedGoodAndServiceForm = goodsServices.importNiceClassHeading(niceClass, language);
            // Removes the existent terms from class heading, so they are rearranged on the list in the order received
            if (importedGoodAndServiceForm != null) {
                if (goodAndServiceForm != null) {
                    for (TermForm termForm : importedGoodAndServiceForm.getTermForms()) {
                        goodAndServiceForm.getTermForms().remove(termForm);
                    }
                }
                // Add again all the imported class headings
                flowBean.addGoodAndService(importedGoodAndServiceForm);
            }
        } catch (Exception e) {
            return new Result("failure");
        }
        return new Result("success");
    }

	/**
	 * Edits the term.
	 *
	 * @param old the old
	 * @param description the description
	 * @param classId the class id
	 * @param lang the lang
	 * @return the list
	 */
	@RequestMapping(value = "editTerm", headers = "Accept=application/json", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<ObjectError> editTerm(
		@RequestParam("old") String old,
        @RequestParam("description") String description,
        @RequestParam("classId") String classId,
        @RequestParam(value = "langId", required = false) String lang) {

        GoodAndServiceForm flowBeanGSForm = flowBean.getGoodsAndService(lang, classId);
        if(flowBeanGSForm != null && flowBeanGSForm.isDisabledRemoval()){
            return new ArrayList<>();
        }

		final String langId = lang;
		GoodAndServiceForm gsForm = new GoodAndServiceForm(langId, classId);
		gsForm.setDesc(description);

		BindingResult result = new BeanPropertyBindingResult(gsForm, "editForm");
		goodsServices.validateGoodsAndServicesDescription(gsForm, result);

		if (result.getErrorCount() == 0) {
			final GoodAndServiceForm goodsAndService = flowBean.getGoodsAndService(langId, classId);
			final Set<TermForm> termForms = goodsAndService.getTermForms();
			TermForm termForm = new TermForm(classId, old, false);
			Iterator<TermForm> it = termForms.iterator();
			for (termForms.iterator(); it.hasNext();) {
				TermForm itTermForm = it.next();
				if (itTermForm.equals(termForm)) {
					it.remove();
					GoodAndServiceForm gsFormVerified = goodsServices.verifyListOfTerms(langId, classId, description);
					termForms.addAll(gsFormVerified.getTermForms());
					break;
				}
			}
		}
		return result.getAllErrors();
	}

    @RequestMapping(value = "updateDisabledRemoval", method = RequestMethod.POST)
    @ResponseBody
    public String disableRemoval(@RequestParam("classId") String niceClass, @RequestParam("langId") String language,
            @RequestParam("removal") boolean canNotBeRemoved) {
        flowBean.getGoodsAndService(language, niceClass).setDisabledRemoval(canNotBeRemoved);
        return "refreshed";
    }

    /**
     * Modify term and move it to another class number with different text
     *
     * @param oldTerm
     *            the old term text
     * @param oldNiceClass
     *            the old nice class number
     * @param langId
     *            the language id of the original and the new term
     * @param term
     *            the new term description
     * @param niceClass
     *            the new nice class number
     * @return true when no exceptions happened
     */
    @RequestMapping(value = "modifyTerm", method = RequestMethod.GET)
    public ModelAndView modifyTerm(@RequestParam(required = false, value = "oldTerm") String oldTerm,
            @RequestParam(required = false, value = "oldNiceClass") String oldNiceClass,
            @RequestParam(required = false, value = "langId") String langId,
            @RequestParam(required = false, value = "term") String term,
            @RequestParam(required = false, value = "niceClass") String niceClass) {
        ModelAndView model = new ModelAndView("empty");

        GoodAndServiceForm goodForm = flowBean.getGoodsAndService(langId, oldNiceClass);
        if (goodForm != null) {
            TermForm termForm = new TermForm();
            termForm.setDescription(oldTerm);
            termForm.setIdClass(oldNiceClass);
            goodForm.getTermForms().remove(termForm);
        }

        GoodAndServiceForm goodsForm = goodsServices.verifyListOfTerms(langId, niceClass, term);
        flowBean.addGoodAndService(goodsForm);

        model.addObject("numberOfClasses", flowBean.getGoodsAndServices().size());

        return model;
    }

    /**
     * Search a term in the external service
     *
     * @param term
     *            the term that we are looking for
     * @param taxoConceptNodeId
     *            the taxonomy concept id under which we look
     * @param page
     *            the page of the results that we are looking for
     * @param sortBy
     *            'niceClass' or 'text'
     * @param orderBy
     *            'asc' or 'desc'
     * @param sources
     *              sources - Nice, Harmonized or both
     * @return the model and view of the returned GS with the hierarchy view and
     *         if taxoConceptNodeId has been set, then we display taxonomy
     *         information too
     */
    @RequestMapping(value = "search", headers = "Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<TermForm> searchTerm(@RequestParam(required = false, value = "langId") String language,
            @RequestParam(required = false, value = "term") String term,
            @RequestParam(required = false, value = "taxoConceptNodeId") String taxoConceptNodeId,
            @RequestParam(required = false, value = "page") String page,
            @RequestParam(required = false, value = "filter") String filter,
            @RequestParam(required = false, value = "sortBy") String sortBy,
            @RequestParam(required = false, value = "orderBy") String orderBy,
            @RequestParam(required = false, value = "sources") String sources) {

        String configuredSearchResultsLimit = configurationService.getValue(
                ConfigurationServiceDelegatorInterface.RESULTS_TERMS_LIMIT, MODULE);
        int limit = Integer.parseInt(configuredSearchResultsLimit != null ? configuredSearchResultsLimit
                : DEFAULT_SEARCH_RESUTLS);

        goodsServices.searchTerms(flowBean, language, "undefined".equals(term) ? null : term, filter, taxoConceptNodeId, limit, page, sortBy, orderBy, sources);

        return flowBean.getTerms();
    }

    /**
     * It returns a list of classes number that contains the specific term
     *
     * @param language
     *            the language of the expected taxonomy view
     * @param term
     *            the term for which we expect the taxonomy
     * @param taxoConceptNodeId
     *            the taxonomy concept id that we expect
     */
    @RequestMapping(value = "getDistribution", headers = "Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<Integer> getDistribution(@RequestParam(required = false, value = "langId") String language,
            @RequestParam(required = false, value = "term") String term,
            @RequestParam(required = false, value = "taxoConceptNodeId") String taxoConceptNodeId) {
        return goodsServices.getDistribution(language, term);
    }

    /**
     * Adds term to the GS of a form
     *
     * @param goodsForm
     * @return
     */
    @RequestMapping(value = "addTerms", headers = "Accept=application/json", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Result addTerms(@ModelAttribute("addedTerms") AddedTermForm goodsForm) {
        List<GoodAndServiceForm> verifiedGS = verifyAddedTermForm(goodsForm, ((FlowBeanImpl)flowBean).getFirstLang());
        verifiedGS.forEach( gs ->  flowBean.addGoodAndService(gs));
        return new Result("success");
    }

    /**
     * Replace edited term.
     *
     * @param goodsForm the goods form
     * @param termClass the term class
     * @param termDescription the term description
     * @return the result
     */
    @RequestMapping(value = "replaceEditedTerm", headers = "Accept=application/json", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Result replaceEditedTerm(@ModelAttribute("addedTerms") AddedTermForm goodsForm, @RequestParam(required = false, value = "termClass") String termClass, @RequestParam(required = false, value = "termDescription") String termDescription) {
        List<GoodAndServiceForm> verifiedGS = verifyAddedTermForm(goodsForm, ((FlowBeanImpl)flowBean).getFirstLang());
        flowBean.replaceEditedTerm(verifiedGS, termClass, termDescription);

        return new Result("success");
    }

    private List<GoodAndServiceForm> verifyAddedTermForm(AddedTermForm goodsForm, String firstLang){
        List<GoodAndServiceForm> verifiedForms = new ArrayList<>();

        if(goodsForm.getTerms() != null && goodsForm.getTerms().size() >0) {
            List<GoodAndServiceForm> gsForms = new ArrayList<>();
            for (TermForm termForm : goodsForm.getTerms()) {
                GoodAndServiceForm gsForm = new GoodAndServiceForm();

                gsForm.setClassId(termForm.getIdClass());
                gsForm.setDesc(termForm.getDescription());
                gsForm.setLangId(languagesFlowBean.getFirstLang());
                gsForm.addTermForm(termForm);
                gsForms.add(gsForm);
            }
            FlowBeanImpl flowBeanTemp = new FlowBeanImpl();
            flowBeanTemp.setFirstLang(firstLang);
            gsForms.forEach(g -> flowBeanTemp.addGoodAndService(g));
            goodsServices.verifyListOfGS(flowBeanTemp);
            verifiedForms.addAll(flowBeanTemp.getGoodsAndServices());
        }
        return verifiedForms;
    }

    public class Result {
        private String value;

        public Result(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * Removes a term from the list of GS
     *
     * @param term
     *            the term where we want to remove
     * @param classId
     *            the class id of the term
     * @param langId
     *            the language of the term
     * @return modelAndView
     */
    @RequestMapping(value = "removeTerm", headers = "Accept=application/json", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Result removeTerm(@RequestParam(required = true, value = "term") String term,
            @RequestParam(required = true, value = "classId") String classId,
            @RequestParam(required = true, value = "langId") String langId) {
        GoodAndServiceForm goodForm = flowBean.getGoodsAndService(langId, classId);
        if (goodForm != null) {
            goodForm.setDisabledRemoval(false);
            TermForm termForm = new TermForm();
            termForm.setDescription(term);
            termForm.setIdClass(classId);
            goodForm.getTermForms().remove(termForm);
        }

        return new Result("success");
    }

    /**
     * Controller that returns the view where a user can provide manually terms
     * in the application
     *
     * @return the model and view of providing manually terms
     */
    @RequestMapping(value = "provideListOnMyOwn", method = RequestMethod.GET)
    public ModelAndView provideListOnMyOwn() {
        ModelAndView model = new ModelAndView("goods_services/provide_terms");
        model.addObject("goodAndServiceForm", new GoodAndServiceForm());
        return model;
    }

    /**
     * Controller that returns the view where a user can provide manually terms
     * in the application
     *
     * @return the model and view of providing manually terms
     */
    @RequestMapping(value = "loadModalPopup", method = RequestMethod.GET)
    public ModelAndView loadModalPopup() {
        return new ModelAndView("goods_services_redesign/modal-gs");
    }

    
    
    
    /**
     * Georgi Georgiev: these three methods startAddingListOnMyOwnGoodsServies/addListOnMyOwnGoodsServies/submitListOnMyOwnJSON/ are required because the ESAPI truncates the requestParameters if they are longer than 2000 symbols 
     * The idea is that resetListOnMyOwnGoodsServies is called at the beginning, the addListOnMyOwnGoodsServies adds terms to the tempGoodsServices
     */
    
    @RequestMapping(value = "resetListOnMyOwnGoodsServices", method = RequestMethod.POST)
    @ResponseBody
    public String startAddingListOnMyOwnGoodsServices(@RequestParam("langId")String langId, @RequestParam("classId") String classId) {
        String unique = UUID.randomUUID().toString();
    	flowBean.resetTempGoodsServices(unique, classId, langId);
        return unique;
    }

    @RequestMapping(value = "addListOnMyOwnGoodsServices", headers = "Accept=application/json", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<ObjectError> addListOnMyOwnGoodsServices(@RequestParam String uuid, @ModelAttribute("goodAndServiceForm") GoodAndServiceForm goodsForm,  BindingResult result) {
    	logger.debug(goodsForm.getClassId());

    	if (result.getErrorCount() == 0) {
            goodsServices.validateGoodsAndServicesDescription(goodsForm, result);
        }
    	
        if (result.getErrorCount() == 0) {
            try {
                goodsForm.setDesc(goodsForm.getDesc() != null ? goodsForm.getDesc().toLowerCase(): goodsForm.getDesc());
            	goodsForm = goodsServices.verifyListOfTerms(goodsForm.getLangId(), goodsForm.getClassId(),
                		goodsForm.getDesc());
            } catch (SPException e) {
                result.rejectValue("desc", e.getErrorCode());
            }
        }
    	if (result.getErrorCount() == 0) {
    		flowBean.addTempGoodsServices(uuid, goodsForm);	
    	}

		List<ObjectError> errors = result.getAllErrors();

    	return removeDuplicateErrors(errors);
    }
    
    /**
     * Receives a form of goods & services and appends it on the current list of
     * terms
     *
     * @param goodsForm
     *            the received goods and service
     * @return the view where request should respond
     */
    @RequestMapping(value = "submitListOnMyOwnJSON"/*, headers = "Accept=application/json"*/, method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<ObjectError> submitListOnMyOwnJSON(@RequestParam("uuid") String uuid, @RequestParam(value = "replaceClass", required = false, defaultValue = "false") Boolean replaceClass,
                                                   @ModelAttribute("goodAndServiceForm") GoodAndServiceForm goodsForm,
            BindingResult result) {
        
    	
    	GoodAndServiceForm tempGoodsForm = flowBean.getTempGoodsServices(uuid);
    	flowBean.removeTempGoodsServices(uuid);
    	
    	//Georgi Georgiev: This should not happen in normal situations! classId and langId should always be equal!
    	if (!goodsForm.getClassId().equals(tempGoodsForm.getClassId()) || !goodsForm.getLangId().equals(tempGoodsForm.getLangId())) {
    		result.addError(new ObjectError("Error...", "Error..."));
    		return result.getAllErrors();
    	}
    	
    	if (result.getErrorCount() == 0) {
    	    GoodAndServiceForm existingGS = flowBean.getGoodsAndService(tempGoodsForm.getLangId(), tempGoodsForm.getClassId());
    	    if(existingGS != null && replaceClass){
    	        flowBean.getGoodsAndServices().remove(existingGS);
            }
    		flowBean.addGoodAndService(tempGoodsForm);
    	}
    	
    	return result.getAllErrors();
    }

    /**
     * It returns the list of classes that are selected by the user
     *
     * @return the list of classes that are selected
     */
    @RequestMapping(value = "getGSClasses", headers = "Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<String> getGSClasses() {
        List<String> classes = new ArrayList<String>();
        for (GoodAndServiceForm form : flowBean.getGoodsAndServices()) {
            classes.add(form.getClassId());
        }

        return classes;
    }

    /**
     * Get class scopes.
     *
     * @param language the language
     * @return the class scopes
     */
    @RequestMapping(value = "getClassScopes", method = RequestMethod.GET)
    @ResponseBody
    public List<ClassScopeInfo> getClassScopes(@RequestParam("langId") String language) {
        List<ClassScopeInfo> classScopeInfos = new ArrayList<>();

        if (StringUtils.isNotBlank(language)) {
            final Map<String,ClassScope> classScopes = goodsServices.getClassScope(language, null);
            final Collection<TaxonomyConceptNodeTreeView> taxonomyConceptNodes = goodsServices.getTaxonomy(language, null, null, null);

            if (MapUtils.isEmpty(classScopes) || CollectionUtils.isEmpty(taxonomyConceptNodes)) {
                throw new SPException("Error calling Euroclass service", GENERIC_SERVICE_ERROR);
            }

            if (logger.isDebugEnabled()) {
                logger.debug("Number of class scopes received: " + classScopes.size())  ;
                logger.debug("Number of taxonomy nodes received: " + taxonomyConceptNodes.size());
            }

            for (Map.Entry<String,ClassScope> entry :  classScopes.entrySet()) {
                ClassScopeInfo classScopeInfo = new ClassScopeInfo();
                classScopeInfo.setCat(entry.getValue().getClassNumber());
                classScopeInfo.setTerm(entry.getValue().getDescription());

                TaxonomyConceptNodeTreeView node = TaxonomyUtil.findClassScopeNode(taxonomyConceptNodes, entry.getValue().getClassNumber(), 1);
                if (node != null) {
                    classScopeInfo.setTaxoConceptNodeId(node.getId());
                }
                classScopeInfos.add(classScopeInfo);
            }
            Collections.sort(classScopeInfos);
        }

        return classScopeInfos;
    }

    /**
     * Returns a searched for term.
     *
     * @param language the language
     * @param term the term
     * @param taxoConceptNodeId the taxo concept node id
     * @param showNonTaxoTermsOnly the show non taxo terms only
     * @param page the page
     * @param filter the filter
     * @param sortBy the sort by
     * @param orderBy the order by
     * @param niceClassList the nice class list
     * @return the search term
     */
    @RequestMapping(value = "searchTerm", headers = "Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public SearchTerm newSearchTerm(
            @RequestParam(required = false, value = "langId") String language,
            @RequestParam(required = false, value = "term") String term,
            @RequestParam(required = false, value = "taxoConceptNodeId") String taxoConceptNodeId,
            @RequestParam(required = false, value = "showNonTaxoTermsOnly") boolean showNonTaxoTermsOnly,
            @RequestParam(required = false, value = "page") String page,
            @RequestParam(required = false, value = "filter") String filter,
            @RequestParam(required = false, value = "sortBy") String sortBy,
            @RequestParam(required = false, value = "orderBy") String orderBy,
            @RequestParam(required = false, value = "niceClassList") String niceClassList,
            @RequestParam(required = false, value = "sources") String sources) {

        String configuredSearchResultsLimit = configurationService.getValueFromGeneralComponent(ConfigurationServiceDelegatorInterface.RESULTS_TERMS_LIMIT);
        int limit = Integer.parseInt(ObjectUtils.defaultIfNull(configuredSearchResultsLimit, DEFAULT_SEARCH_RESUTLS).toString());

        // if there is no term, nothing is searched
        if(StringUtils.isBlank(term) && StringUtils.isBlank(niceClassList) && StringUtils.isBlank(sources)) {
            SearchTerm searchTerm = new SearchTerm();
            searchTerm.setResults(new ArrayList<>());

            searchTerm.setTotalResults(0);
            searchTerm.setPageSize(limit);

            return searchTerm;
        }


        TaxonomyConceptNodeTreeView masterParent = null;
        if (taxoConceptNodeId != null) {
            masterParent = TaxonomyUtil.findTaxonomyNode(goodsServices.getTaxonomy(language, null, null, null), taxoConceptNodeId);
        }

        SearchTermResults result = goodsServices.searchTerms(language, term, niceClassList, taxoConceptNodeId, limit, ((masterParent != null && masterParent.isNonTaxonomyParent())), page, sortBy, orderBy, sources);

        SearchTerm searchTerm = parseSearchResult(masterParent, language, limit, result);

        return searchTerm;
    }

    /**
     * Parse search result.
     *
     * @param masterParent the master parent
     * @param language the language
     * @param limit the limit
     * @param result the result
     * @return the search term
     */
    private SearchTerm parseSearchResult(TaxonomyConceptNodeTreeView masterParent, String language, int limit, SearchTermResults result) {
        SearchTerm searchTerm = new SearchTerm();
        searchTerm.setResults(new ArrayList<>());

        //Get parent path of this term
        getParentPaths(masterParent, searchTerm);
        if (searchTerm.getParents() != null) {
            Collections.reverse(searchTerm.getParents());
        }

        searchTerm.setTotalResults(result.getTotalResults());
        searchTerm.setPageSize(limit);

        // Parse the results from search term
        for (eu.ohim.sp.ui.tmefiling.form.terms.TermForm termForm : result.getTerms()) {
            // Get the concept id if it exists
            TermResult termResult = new TermResult();
            if (masterParent != null) {
                for (TaxonomyConceptNodeTreeView child : masterParent.getChildren()) {
                    if (StringUtils.equals(child.getText(), termForm.getDescription())) {
                        termResult.setId(child.getId());
                    }
                }
            }

            termResult.setCat(termForm.getIdClass());
            termResult.setLang(language);
            termResult.setScopeAvailability(termForm.isScopeAvailabilty());
            termResult.setTerm(termForm.getDescription());
            if (termForm.getParentIds() != null) {
                termResult.setSubcategories(new ArrayList<>());
                termResult.getSubcategories().addAll(termForm.getParentIds());
            }

            if (termResult.getSubcategories() != null) {
                Collections.reverse(termResult.getSubcategories());
            }
            searchTerm.getResults().add(termResult);
        }
        return searchTerm;
    }


    /**
     * Get parent paths.
     *
     * @param masterParent the master parent
     * @param searchTerm the search term
     * @return the parent paths
     */
    private void getParentPaths(TaxonomyConceptNodeTreeView masterParent, SearchTerm searchTerm) {
        if (masterParent != null) {
            List<ConceptForm> parentIds = new ArrayList<>();
            TaxonomyConceptNodeTreeView iterator = masterParent;
            while (iterator != null) {
                if (!StringUtils.equals(iterator.getId(), "0")) {
                    ConceptForm conceptForm = new ConceptForm();
                    conceptForm.setId(iterator.getId());
                    conceptForm.setDescription(iterator.getText());
                    parentIds.add(conceptForm);
                }
                iterator = iterator.getParent();
            }
            searchTerm.setParents(parentIds);
        }
    }

	/**
	 * Remove duplicate errors.
	 *
	 * @param errors the errors
	 * @return the list
	 */
	private List<ObjectError> removeDuplicateErrors(List<ObjectError> errors) {
		if (errors.size() > 0) {
			List<ObjectError> notDuplicateErrors = new ArrayList<>();
			notDuplicateErrors.addAll(errors);
			ObjectError error = errors.get(0);
			for (int i = 1; i < errors.size(); i++) {
				if (Arrays.equals(error.getCodes(), errors.get(i).getCodes())) {
					notDuplicateErrors.remove(errors.get(i));
				}
			}
			return notDuplicateErrors;
		} else {
			return errors;
		}
	}

	@RequestMapping(value = "removeClass", method = RequestMethod.POST)
    @ResponseBody
    public Result removeClass(@RequestParam(required = true, value = "classId") String classId,
                              @RequestParam(required = true, value = "langId") String langId){
        GoodAndServiceForm goodAndServiceForm = flowBean.getGoodsAndService(langId, classId);
        if(goodAndServiceForm != null){
            flowBean.getGoodsAndServices().remove(goodAndServiceForm);
        }
        return new Result("success");
    }

    @RequestMapping(value = "getClassForEdit", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public GoodAndServiceForm getClassForEdit(@RequestParam(required = true, value = "classId") String classId,
                              @RequestParam(required = true, value = "langId") String langId){
        GoodAndServiceForm goodAndServiceForm = flowBean.getGoodsAndService(langId, classId);
        if(goodAndServiceForm != null && goodAndServiceForm.getTermForms() != null){
            StringBuilder stringBuilder = new StringBuilder();
            goodAndServiceForm.getTermForms().stream()
                .filter(term -> term.getDescription() != null)
                .map(term -> term.getDescription())
                .forEach(termText -> stringBuilder.append(termText).append(";"));
            goodAndServiceForm.setDesc(stringBuilder.toString());
        }
        return goodAndServiceForm;
    }
}
