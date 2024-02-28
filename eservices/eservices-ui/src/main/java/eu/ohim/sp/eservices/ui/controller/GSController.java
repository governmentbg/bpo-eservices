/*******************************************************************************
 * * $Id:: GSController.java 128403 2013-07-11 12:11:23Z karalch                 $
 * *       . * .
 * *     * RRRR  *    Copyright Â© 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/

package eu.ohim.sp.eservices.ui.controller;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.FieldPropertyEditor;
import eu.ohim.sp.common.ui.exception.NoResultsException;
import eu.ohim.sp.common.ui.form.classification.AddedTermForm;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TaxonomyConceptNodeTreeView;
import eu.ohim.sp.common.ui.form.classification.TermForm;
import eu.ohim.sp.common.ui.form.classification.TermJSON;
import eu.ohim.sp.core.domain.classification.wrapper.ClassScope;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyConceptNode;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.interfaces.GoodsServicesServiceInterface;
import eu.ohim.sp.eservices.ui.util.EServicesConstants;
import eu.ohim.sp.eservices.ui.util.Result;
import eu.ohim.sp.eservices.ui.util.TaxonomyUtil;

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

	/**
	 * session bean
	 */
	@Autowired
	private ESFlowBean flowBean;

	@Autowired
	private GoodsServicesServiceInterface goodsServicesService;

	@Autowired
	private FieldPropertyEditor fieldPropertyEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, fieldPropertyEditor);
	}
	
	@Value("${level.class.scope}")
	private Integer levelScope;

	@Value("${first.level.class.taxonomy}")
	private Integer firstLevelVisible;
	
	@Value("${gs.languages}")
    public String gsLanguage;
	
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
	@ResponseBody
	public Result handleException(SPException e) {
		return new Result(e.getErrorCode());
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
	public Result handleNoResultsException(SPException e) {
		return new Result(e.getErrorCode());
	}


	/**
	 * Outputs a json containing information about terms not validated in the
	 * given language
	 * 
	 * @param langId
	 *            the lang id of GS that we are interested
	 * @return the json information
	 */
	@RequestMapping(value = "terms", headers = EServicesConstants.HEADERS_ACC, method = RequestMethod.POST, produces = EServicesConstants.PRODUCES_APPJSON)
	@ResponseBody
	public Collection<TermJSON> terms(
			@RequestParam(required = false, value = EServicesConstants.LANGID) String langId) {

		logger.debug("START terms");
		Collection<TermJSON> termsData = new ArrayList<TermJSON>();
		for (GoodAndServiceForm good : flowBean.getGoodsServices()) {
			boolean containsAllNiceClassHeading = goodsServicesService.containsAllNiceClassHeading(good);
			
			boolean disabledRemoval = good.isDisabledRemoval();
			List<TermJSON> terms = TaxonomyUtil.generateTermJSON(good, flowBean, langId, containsAllNiceClassHeading, disabledRemoval);
			if (terms != null) {
				termsData.addAll(terms);
			}
		}
		logger.debug("END terms");
		return termsData;
	}

	/**
	 * It returns the nodes of the taxonomy on a tree view
	 * @param langId 
	 * 			the language of the expected taxonomy view
	 * @param term 
	 * 			the term for which we expect the taxonomy
	 * @param taxoConceptNodeId 
	 *			the taxonomy concept id that we expect
	 */
	@RequestMapping(value = "conceptNodes", headers = EServicesConstants.HEADERS_ACC, method = RequestMethod.POST, produces = EServicesConstants.PRODUCES_APPJSON)
	@ResponseBody
	public Collection<TaxonomyConceptNodeTreeView> parentConceptNodes(
			@RequestParam(value = EServicesConstants.LANGID) String langId, 
			@RequestParam(required = false, value=EServicesConstants.TERM) String term, 
			@RequestParam(required = false, value="taxoConceptNodeId") String taxoConceptNodeId) {

		Collection<TaxonomyConceptNodeTreeView> taxonomyConceptNodes = goodsServicesService.getTaxonomy(langId, term, null, taxoConceptNodeId);
		Collection<TaxonomyConceptNodeTreeView> minified = TaxonomyUtil.limitMinifiedView(TaxonomyUtil.getMinifiedView(taxonomyConceptNodes), firstLevelVisible, levelScope);
		for (TaxonomyConceptNodeTreeView node : minified) {
			Map<String, ClassScope> scopes = goodsServicesService.getClassScope(langId, null);
			if (node.isClassScope() && node.getNiceClass()!=null) {
				node.setClassScopeDescription(scopes.get(node.getNiceClass().toString())!=null?scopes.get(node.getNiceClass().toString()).getDescription():"");
			}
		}
		return minified;
	}

	@RequestMapping(value = "parentConceptNodes", headers = EServicesConstants.HEADERS_ACC, method = RequestMethod.POST, produces = EServicesConstants.PRODUCES_APPJSON)
	@ResponseBody
	public Collection<TaxonomyConceptNode> parentConceptNodes(
			@RequestParam(required = false, value = EServicesConstants.LANGID) String langId, @RequestParam(EServicesConstants.TERM) String term) {
		return goodsServicesService.getParentConceptNodes(langId, term);
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
	@RequestMapping(value = "loadNiceClassHeading", method = RequestMethod.POST)
	@ResponseBody
	public GoodAndServiceForm loadNiceClassHeading(
			@RequestParam(EServicesConstants.CLASSID) String niceClass,
			@RequestParam(EServicesConstants.LANGID) String language) {
		return goodsServicesService.importNiceClassHeading(niceClass, language);
	}

	/**
	 * Calls the external service of "Did you mean" 
	 * @param searchCriteria the criteria of search
	 * @param language the language of GS
	 * @return
	 */
	@RequestMapping(value = "didYouMean", method = RequestMethod.POST)
	@ResponseBody
	public List<String> didYouMean(
			@RequestParam(EServicesConstants.TERM) String searchCriteria,
			@RequestParam(EServicesConstants.LANGID) String language) {
			return goodsServicesService.didYouMean(language, searchCriteria);
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
	public Result importNiceClassHeading(
			@RequestParam(EServicesConstants.CLASSID) String niceClass,
			@RequestParam(EServicesConstants.LANGID) String language) {
		try {
			GoodAndServiceForm goodAndServiceForm = flowBean.getGoodsAndService(language, niceClass);
			GoodAndServiceForm importedGoodAndServiceForm = goodsServicesService.importNiceClassHeading(niceClass, language);
			//Removes the existent terms from class heading, so they are rearranged on the list in the order received
			if (importedGoodAndServiceForm!=null) {
				if (goodAndServiceForm!=null) {
					for (TermForm termForm : importedGoodAndServiceForm.getTermForms()) {
						goodAndServiceForm.getTermForms().remove(termForm);
					}
				}
				//Add again all the imported class headings
				flowBean.addGoodAndService(importedGoodAndServiceForm);
			}
		} catch (Exception e) {
			return new Result("failure");
		}
		return new Result(EServicesConstants.SUCCESS);
	}

	@RequestMapping(value = "updateDisabledRemoval", method = RequestMethod.POST)
	@ResponseBody
	public String disableRemoval(@RequestParam(EServicesConstants.CLASSID) String niceClass,
			@RequestParam(EServicesConstants.LANGID) String language,
			@RequestParam("removal") boolean canNotBeRemoved) {
		flowBean.getGoodsAndService(language, niceClass).setDisabledRemoval(
				canNotBeRemoved);
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
	 * @return the model and view of the modified list of GS
	 */
	@RequestMapping(value = "modifyTerm", method = RequestMethod.POST)
	public ModelAndView modifyTerm(
			@RequestParam(required = false, value = "oldTerm") String oldTerm,
			@RequestParam(required = false, value = "oldNiceClass") String oldNiceClass,
			@RequestParam(required = false, value = EServicesConstants.LANGID) String langId,
			@RequestParam(required = false, value = EServicesConstants.TERM) String term,
			@RequestParam(required = false, value = "niceClass") String niceClass) {
		ModelAndView model = new ModelAndView(
				"empty");

		GoodAndServiceForm goodForm = flowBean.getGoodsAndService(langId, oldNiceClass);
		if (goodForm!=null) {
			TermForm termForm = new TermForm();
			termForm.setDescription(oldTerm);
			termForm.setIdClass(oldNiceClass);
			goodForm.getTermForms().remove(termForm);
		}
		
		TermForm termForm = new TermForm();
		termForm.setDescription(term);
		termForm.setIdClass(niceClass);

		GoodAndServiceForm goodsForm = new GoodAndServiceForm();
		goodsForm.setClassId(niceClass);
		goodsForm.setLangId(langId);

		goodsForm.addTermForm(termForm);
		flowBean.addGoodAndService(goodsForm);

		model.addObject("numberOfClasses", flowBean.getGoodsAndServices()
				.size());

		return model;
	}

	
	/**
	 * It returns a list of classes number that contains the specific term
	 * @param language 
	 * 			the language of the expected taxonomy view
	 * @param term 
	 * 			the term for which we expect the taxonomy
	 * @param taxoConceptNodeId 
	 *			the taxonomy concept id that we expect
	 */
	@RequestMapping(value = "getDistribution", headers = EServicesConstants.HEADERS_ACC, method = RequestMethod.POST, produces = EServicesConstants.PRODUCES_APPJSON)
	@ResponseBody
	public List<Integer> getDistribution(
			@RequestParam(required = false, value = EServicesConstants.LANGID) String language,
			@RequestParam(required = false, value = EServicesConstants.TERM) String term,
			@RequestParam(required = false, value = "taxoConceptNodeId") String taxoConceptNodeId) {
		return goodsServicesService.getDistribution(language, term);
	}

	/**
	 * Adds term to the GS of a form
	 * @param goodsForm
	 * @return
	 */
	@RequestMapping(value = "addTerms", headers = EServicesConstants.HEADERS_ACC, method = RequestMethod.POST, produces = EServicesConstants.PRODUCES_APPJSON)
	@ResponseBody
	public Result addTerms(@ModelAttribute("addedTerms") AddedTermForm goodsForm) {
		for (TermForm termForm : goodsForm.getTerms()) {
			GoodAndServiceForm gsForm = new GoodAndServiceForm();

			gsForm.setClassId(termForm.getIdClass());
			gsForm.setDesc(termForm.getDescription());
			gsForm.setLangId(flowBean.getFirstLang());
			gsForm.addTermForm(termForm);

			flowBean.addGoodAndService(gsForm);
		}
		
		return new Result(EServicesConstants.SUCCESS);
	}
	

	/**
	 * Removes an entirely class from the list of GS
	 * 
	 * @param classId
	 *            the class id of the term
	 * @param langId
	 *            the language of the term
	 * @return modelAndView
	 */
	
	@RequestMapping(value = "resetTerms", headers = EServicesConstants.HEADERS_ACC, method = RequestMethod.POST, produces = EServicesConstants.PRODUCES_APPJSON)
	@ResponseBody
	public Result resetTerms(@RequestParam(required = false, value = EServicesConstants.LANGID) String langId)
	{
		Set<GoodAndServiceForm> resetedGS = new TreeSet<GoodAndServiceForm>();
		for(GoodAndServiceForm originalGS : flowBean.getOriginalGS()){
			try{
				GoodAndServiceForm reseted = originalGS.clone();
				resetedGS.add(reseted);
            }
            catch (CloneNotSupportedException e) {
                throw new SPException("Failed to find duplicate object", e, "error.genericError");
        	}
		}
		flowBean.setGoodsServices(TaxonomyUtil.filterGoodsAndServices(resetedGS, langId));
		return new Result(EServicesConstants.SUCCESS);
	}
	
	/**
	 * Removes an entirely class from the list of GS
	 * 
	 * @param classId
	 *            the class id of the term
	 * @param langId
	 *            the language of the term
	 * @return modelAndView
	 */
	
	@RequestMapping(value = "cleanGS", headers = EServicesConstants.HEADERS_ACC, method = RequestMethod.POST, produces = EServicesConstants.PRODUCES_APPJSON)
	@ResponseBody
	public Result cleanGS(@RequestParam(required = false, value = EServicesConstants.LANGID) String langId)
	{
		flowBean.setGoodsServices(new TreeSet<GoodAndServiceForm>());
		return new Result(EServicesConstants.SUCCESS);
	}
	
	/**
	 * Removes an entirely class from the list of GS
	 * 
	 * @param classId
	 *            the class id of the term
	 * @param langId
	 *            the language of the term
	 * @return modelAndView
	 */
	@RequestMapping(value = "removeClass", headers = EServicesConstants.HEADERS_ACC, method = RequestMethod.POST, produces = EServicesConstants.PRODUCES_APPJSON)
	@ResponseBody
	public Result removeClass(
			@RequestParam(required = true, value = EServicesConstants.CLASSID) String classId,
			@RequestParam(required = true, value = EServicesConstants.LANGID) String langId,
			@RequestParam(required = true, value = "imported") String imported){

		GoodAndServiceForm goodForm = null;
		String[] langsFilter = langId.split(",");
		for (int i=0;i<langsFilter.length;i++){
			goodForm = flowBean.getGoodsAndService(langsFilter[i], classId);
			if(goodForm != null){
				break;
			}
		}

		if(goodForm!=null) {
			if(flowBean.getGoodsServices().size() > 1){
				flowBean.getGoodsServices().remove(goodForm);
			}
			else if("false".equals(imported)){
				flowBean.getGoodsServices().remove(goodForm);
			}
		}
		return new Result(EServicesConstants.SUCCESS);
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
	@RequestMapping(value = "removeTerm", headers = EServicesConstants.HEADERS_ACC, method = RequestMethod.POST, produces = EServicesConstants.PRODUCES_APPJSON)
	@ResponseBody
	public Result removeTerm(
			@RequestParam(required = true, value = EServicesConstants.TERM) String term,
			@RequestParam(required = true, value = EServicesConstants.CLASSID) String classId,
			@RequestParam(required = true, value = EServicesConstants.LANGID) String langId,
			@RequestParam(required = true, value = "imported") String imported) {

		GoodAndServiceForm goodForm = null;
		String[] langsFilter = langId.split(",");
		for (int i=0;i<langsFilter.length;i++){
			goodForm = flowBean.getGoodsAndService(langsFilter[i], classId);
			if(goodForm != null){
				break;
			}
		}

		if (goodForm!=null) {
			goodForm.setDisabledRemoval(false);
			TermForm termForm = new TermForm();
			termForm.setDescription(term);
			termForm.setIdClass(classId);
			
			TermForm removeTerm = removeAux(goodForm.getTermForms(), termForm); 
			if(removeTerm != null){
				if(goodForm.getTermForms().size() > 1){
					goodForm.getTermForms().remove(removeTerm);
				}
				else if(goodForm.getTermForms().size() == 1){
					if("false".equals(imported)){
						goodForm.getTermForms().remove(removeTerm);
					}
					else if(flowBean.getGoodsServices().size() != 1){				
						goodForm.getTermForms().remove(removeTerm);
					}
					if(goodForm.getTermForms().size() == 0){
						flowBean.getGoodsServices().remove(goodForm);
					}
				}
			}
		}		
		return new Result(EServicesConstants.SUCCESS);
	}
	
	public TermForm removeAux(Set<TermForm> terms, TermForm term){
		for(TermForm addedTerm : terms){
			if(addedTerm.equals(term)){
				return addedTerm;
			}
		}
		return null;
	}

	/**
	 * Controller that returns the view where a user can provide manually terms
	 * in the application
	 * 
	 * @return the model and view of providing manually terms
	 */
	@RequestMapping(value = "provideListOnMyOwn", method = RequestMethod.POST)
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
	@RequestMapping(value = "loadModalPopup", method = RequestMethod.POST)
	public ModelAndView loadModalPopup() {
		return new ModelAndView("goods_services_redesign/modal-gs");
	}
	
	/**
	 * Receives a form of goods & services and appends it on the current list of
	 * terms
	 * 
	 * @param goodsForm
	 *            the received goods and service
	 * @return the view where request should respond
	 */
	@RequestMapping(value = "provideListOnMyOwnJSON", headers = EServicesConstants.HEADERS_ACC, method = RequestMethod.POST, produces = EServicesConstants.PRODUCES_APPJSON)
	@ResponseBody
	public List<ObjectError> submitListOnMyOwnJSON(
			@RequestParam(value = "replaceClass", required = false, defaultValue = "false") Boolean replaceClass,
			@ModelAttribute("goodAndServiceForm") GoodAndServiceForm goodsForm,
			BindingResult result) {
		logger.debug(goodsForm.getClassId());

		if (result.getErrorCount() == 0) {
			goodsServicesService.validateGoodsAndServicesDescription(goodsForm, result);
		}

		if (result.getErrorCount() == 0) {
			GoodAndServiceForm returnedForm = null;
			try {
				returnedForm = goodsServicesService.verifyListOfTerms(goodsForm.getLangId(), goodsForm.getClassId(),
						goodsForm.getDesc());
			} catch (SPException e) {
				result.rejectValue("desc", e.getErrorCode());
			}

			if (returnedForm != null) {
				GoodAndServiceForm existingGS = flowBean.getGoodsAndService(goodsForm.getLangId(), goodsForm.getClassId());
				if(existingGS != null && replaceClass){
					flowBean.getGoodsServices().remove(existingGS);
				}
				flowBean.addGoodAndService(returnedForm);
			}
		}

		return result.getAllErrors();
	}

	/**
	 * It returns the list of classes that are selected by the user
	 * @return the list of classes that are selected
	 */
    @RequestMapping(value = "getGSClasses", headers= EServicesConstants.HEADERS_ACC, method = RequestMethod.POST, produces = EServicesConstants.PRODUCES_APPJSON)
    @ResponseBody 
    public List<String> getGSClasses() {
        List<String> classes = new ArrayList<String>();
        for(GoodAndServiceForm form : flowBean.getGoodsAndServices()) {
            classes.add(form.getClassId());
        }
        
        return classes;
    }

	@RequestMapping(value = "editTerm", headers = "Accept=application/json", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<ObjectError> editTerm(
		@RequestParam("old") String old,
		@RequestParam("description") String description,
		@RequestParam("classId") String classId,
		@RequestParam(value = "langId", required = false) String lang) {

		String[] langsFilter = lang.split(",");
		GoodAndServiceForm flowBeanGSForm = null;
		for (int i=0;i<langsFilter.length;i++){
			flowBeanGSForm = flowBean.getGoodsAndService(langsFilter[i], classId);
			if(flowBeanGSForm != null){
				break;
			}
		}

		if(flowBeanGSForm != null && flowBeanGSForm.isDisabledRemoval()){
			return new ArrayList<>();
		}

		final String langId = lang;
		GoodAndServiceForm gsForm = new GoodAndServiceForm(langId, classId);
		gsForm.setDesc(description);

		BindingResult result = new BeanPropertyBindingResult(gsForm, "editForm");
		goodsServicesService.validateGoodsAndServicesDescription(gsForm, result);

		if (result.getErrorCount() == 0 && flowBeanGSForm != null) {
			final GoodAndServiceForm goodsAndService = flowBeanGSForm;
			final Set<TermForm> termForms = goodsAndService.getTermForms();
			TermForm termForm = new TermForm(classId, old, false);
			Iterator<TermForm> it = termForms.iterator();
			for (termForms.iterator(); it.hasNext();) {
				TermForm itTermForm = it.next();
				if (itTermForm.equals(termForm)) {
					it.remove();
					termForms.addAll(goodsServicesService.verifyListOfTerms(langId, classId, description).getTermForms());
					break;
				}
			}
		}
		return result.getAllErrors();
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
