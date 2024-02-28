/*******************************************************************************


 * * $Id:: GoodsServicesService.java 131035 2013-07-23 17:23:51Z karalch         $
 * *       . * .
 * *     * RRRR  *    Copyright Â© 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.eservices.ui.service;

import java.util.*;

import eu.ohim.sp.common.ui.adapter.ErrorFactory;
import eu.ohim.sp.common.ui.adapter.GoodsServicesFactory;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.domain.classification.MatchedTerm;
import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TaxonomyConceptNodeTreeView;
import eu.ohim.sp.common.ui.form.classification.TermForm;
import eu.ohim.sp.common.ui.form.validation.ErrorType;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.util.ClassificationCriteriaUtil;
import eu.ohim.sp.core.classification.NiceClassificationService;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import eu.ohim.sp.core.domain.classification.VerifiedTermResult;
import eu.ohim.sp.core.domain.classification.wrapper.ClassScope;
import eu.ohim.sp.core.domain.classification.wrapper.ClassificationTerm;
import eu.ohim.sp.core.domain.classification.wrapper.DistributionCriteria;
import eu.ohim.sp.core.domain.classification.wrapper.DistributionResults;
import eu.ohim.sp.core.domain.classification.wrapper.SearchMode;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyConceptNode;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyCriteria;
import eu.ohim.sp.core.domain.classification.wrapper.Term;
import eu.ohim.sp.core.domain.classification.wrapper.TermsToBeValidated;
import eu.ohim.sp.core.domain.classification.wrapper.TermsValidated;
import eu.ohim.sp.core.domain.trademark.ClassificationErrorEnum;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.interfaces.GoodsServicesServiceInterface;
import eu.ohim.sp.eservices.ui.util.EServicesConstants;
import eu.ohim.sp.eservices.ui.util.TaxonomyUtil;

@Service
public class GoodsServicesService implements GoodsServicesServiceInterface {
	
	private static final Logger logger = Logger.getLogger(GoodsServicesService.class);
	
	@Autowired
	private NiceClassificationService classificationService;
	
    @Autowired
    private ConfigurationServiceDelegatorInterface configurationService;

	@Autowired
	private GoodsServicesFactory goodsServicesFactory;

	@Autowired
	private ErrorFactory errorFactory;

	@Autowired
	private RulesService businessRulesService;

	private static final String MODULE = "eservices";

	private static final String GOODS_AND_SERVICES_SET = "goodsandservices_set";

    @Value("${sp.office}")
    private String office;

    @Value("${gs.terms.seperator}")
    private String termSeparator;

	private static final String SECTION_NAME = "sectionName";
    
    /**
     * This has change in the tm-efilling version of OHIM; if I need it, I should ask Christos before I proceed to use it.
     * 
     
	@Override
	public Integer searchTerms(ESFlowBean flowBean, String language, String term, String filterClass,
			String taxoConceptNodeId, Integer size, Boolean showNonTaxoTermsOnly, String page, String sortBy, String orderBy) {
		if (logger.isDebugEnabled()) {
			logger.debug(EServicesConstants.CONST_LANG + language + EServicesConstants.CONST_TERM + term + ", filterClass = " + filterClass + ", taxoConceptNodeId = " + taxoConceptNodeId
					+ ", size = " + size + ", page = " + page + ", sortBy = " + sortBy + ", orderBy = " + orderBy);
		}

		SearchTermCriteria searchTermCriteria = ClassificationCriteriaUtil.constructSearchTermCriteria(office, term, language, 
				filterClass, taxoConceptNodeId, size, showNonTaxoTermsOnly, page, sortBy, orderBy);
		if (taxoConceptNodeId!=null) {
			searchTermCriteria.setShowMaster(true);
		}
		
		SearchResults terms = classificationService
				.searchTerm(searchTermCriteria);

		if (terms==null) {
			throw new SPException("Failed to get any terms!", null, "error.gs.search.noresults");
		}
		
		logger.debug("Total results : " + terms.getTotalResults());
		int totalResults = terms.getTotalResults();
		flowBean.getTerms().clear();

		for (Term termResult : terms.getTerms()) {
			TermForm termForm = new TermForm();
			termForm.setDescription(termResult.getText());
			termForm.setIdClass(termResult.getNiceClass().toString());
			
			TaxonomyConceptNodeTreeView node = null;
			if (termResult.getParentIds()!=null 
					&& termResult.getParentIds().size() > 0) {
				node = TaxonomyUtil.findTaxonomyNode(((GoodsServicesServiceInterface) AopContext.currentProxy()).getTaxonomy(language, null, null, null), termResult.getParentIds().get(0));
			}
			List<ConceptForm> parentIds = new ArrayList<ConceptForm>();
			while (node!=null) {
				if (node.getId()!=null
						&& !node.getId().equals("0")) {
					ConceptForm conceptForm = new ConceptForm();
					conceptForm.setId(node.getId());
					conceptForm.setDescription(node.getText());
					parentIds.add(conceptForm);
				}
				node = node.getParent();
			}
			
			termForm.setParentIds(parentIds);
			termForm.setScopeAvailabilty((termResult.isScopeAcceptability()!=null?termResult.isScopeAcceptability():true));

			GoodAndServiceForm goodAndServiceForm = flowBean.getGoodsAndService(language, termForm.getIdClass());
			termForm.setAdded(goodAndServiceForm != null && goodAndServiceForm.contains(termResult.getText()));
					
//			logger.debug(termForm.getIdClass());
			flowBean.getTerms().add(termForm);
		}
		logger.debug("Returned terms : " + flowBean.getTerms().size());
		return totalResults;
	}
	
	*/

	//Caches only for a full taxonomy tree
	@Override
	@Cacheable(value = "getTaxonomy", condition="#term == null or #term.length() == 0")
	public Collection<TaxonomyConceptNodeTreeView> getTaxonomy(String language, 
			String term, Integer levelLimit, String taxoConceptNodeId) {
		if (logger.isDebugEnabled()) {
			logger.debug(EServicesConstants.CONST_LANG + language + EServicesConstants.CONST_TERM + term + 
					", levelLimit = " + levelLimit + ", taxoConceptNodeId = " + taxoConceptNodeId);
		}

		TaxonomyCriteria taxonomyCriteria = ClassificationCriteriaUtil.constructSearchTaxonomyCriteria(office, language, 
				term, levelLimit, taxoConceptNodeId);

		Collection<TaxonomyConceptNode> nodes = classificationService.getTaxonomy(taxonomyCriteria);
		
		return TaxonomyUtil.getTaxonomyConceptNodes(nodes, taxoConceptNodeId);
	}

	@Override
	public Collection<TaxonomyConceptNode> getParentConceptNodes(String language, 
			String term) {
		if (logger.isDebugEnabled()) {
			logger.debug(EServicesConstants.CONST_LANG + language + EServicesConstants.CONST_TERM + term);
		}

		TaxonomyCriteria taxonomyCriteria = ClassificationCriteriaUtil.constructSearchTaxonomyCriteria(office, language, 
				term, null, null);
		taxonomyCriteria.setSearchMode(SearchMode.EXACTMATCH);

		return classificationService.getTaxonomy(taxonomyCriteria);
	}
	
	@Override
	public List<String> didYouMean(String language, 
			String searchCriteria) {
		if (logger.isDebugEnabled()) {
			logger.debug(EServicesConstants.CONST_LANG + language + ", searchCriteria = " + searchCriteria);
		}
		return classificationService.getAutocomplete(language, searchCriteria);
	}

	/**
	 * 
	 *
	@Override
	public List<String> autocompleteTerm(String language, 
			String text) {
		if (logger.isDebugEnabled()) {
			logger.debug(EServicesConstants.CONST_LANG + language + ", text = " + text);
		}
		AutoCompleteCriteria autoCompleteCriteria = new AutoCompleteCriteria();
		autoCompleteCriteria.setText(text);
		autoCompleteCriteria.setLanguage(language);
		
		return classificationService.autocompleteTerm(autoCompleteCriteria).getResults();
	}*/

	@Override
	@Cacheable(value = "classScopes")
	public Map<String, ClassScope> getClassScope(String language, 
			String niceClassHeadings) {
		if (logger.isDebugEnabled()) {
			logger.debug(EServicesConstants.CONST_LANG + language + EServicesConstants.CONST_CLAS + niceClassHeadings);
		}
		
		Collection<ClassScope> scopes = classificationService.getClassScopes(language, niceClassHeadings);
		
		Map<String, ClassScope> classScopes = new HashMap<String, ClassScope>();
		if (scopes!=null) {
			for (ClassScope scope : scopes) {
				classScopes.put(scope.getClassNumber(), scope);
			}
		}

		return classScopes;
	}
	
	@Override
	public void verifyListOfGS(ESFlowBean flowBean) {
		logger.debug("Starting validation of GS");
		List<GoodAndServiceForm> verifiedGoodsAndServiceForms = new ArrayList<GoodAndServiceForm>();
		if (flowBean.getGoodsAndServices()!=null) {
			List<TermsToBeValidated> listTerms=new ArrayList<TermsToBeValidated>();
			for (GoodAndServiceForm goodAndServiceForm : flowBean.getGoodsAndServices()) {
				StringBuffer strBuffer = new StringBuffer();
				int verifiedTerms = goodAndServiceForm.getTermForms().size();
				int i = 0;
				for (TermForm term : goodAndServiceForm.getTermForms()) {
					strBuffer.append(term.getDescription()).append(((++i)<verifiedTerms?termSeparator+" ":""));
				}
				TermsToBeValidated termsToBeValidated = new TermsToBeValidated();
				
				termsToBeValidated.setLanguage(goodAndServiceForm.getLangId());
				termsToBeValidated.setOffice(office);
				termsToBeValidated.setNiceClass(goodAndServiceForm.getClassId());
				termsToBeValidated.setTerms(strBuffer.toString());
				listTerms.add(termsToBeValidated);
				//verifiedGoodsAndServiceForms.add(verifyListOfTerms(goodAndServiceForm.getLangId(), goodAndServiceForm.getClassId(), strBuffer.toString()))
			}
			verifiedGoodsAndServiceForms.addAll(verifyListOfTerms(listTerms));
		}
		flowBean.clearGandS();
		for (GoodAndServiceForm verified : verifiedGoodsAndServiceForms) {
			flowBean.addGoodAndService(verified);
		}
		logger.debug("Finishing validation of GS");
	}
	
	@Override
	public GoodAndServiceForm verifyListOfTerms(String language, String niceClass, String terms) {
		if (logger.isDebugEnabled()) {
			logger.debug(EServicesConstants.CONST_LANG + language + EServicesConstants.CONST_CLAS + niceClass + EServicesConstants.CONST_TERM + terms);
		}

		TermsToBeValidated termsToBeValidated = new TermsToBeValidated();
		
		termsToBeValidated.setLanguage(language);
		termsToBeValidated.setOffice(office);
		termsToBeValidated.setNiceClass(niceClass);
		termsToBeValidated.setTerms(terms);
		List<TermsToBeValidated> termsList=new ArrayList<TermsToBeValidated>();
		termsList.add(termsToBeValidated);

		List<GoodAndServiceForm> goodAndServiceForms = verifyListOfTerms(termsList);
	
		if (goodAndServiceForms==null || goodAndServiceForms.size()>0) {
			return goodAndServiceForms.get(0);
		} else {
			return null;
		}
	}
	
	private GoodAndServiceForm createNotFoundTerm(String language, String niceClass, String termString) {
		if (logger.isDebugEnabled()) {
			logger.debug(EServicesConstants.CONST_LANG + language + EServicesConstants.CONST_CLAS + niceClass + EServicesConstants.CONST_TERM + termString);
		}
		
    	GoodAndServiceForm goodAndServiceForm = null;
    	//Check for better ways to get terms
    	goodAndServiceForm = new GoodAndServiceForm();
    	goodAndServiceForm.setLangId(language);
    	goodAndServiceForm.setClassId(niceClass);
        for (String term : termString.split(termSeparator)) {
            TermForm termForm = new TermForm();
            termForm.setIdClass(niceClass);
            termForm.setDescription(term);

            ErrorType errorType = new ErrorType();
            errorType.setErrorEnum(ClassificationErrorEnum.NOT_OK);
            errorType.setVerificationAssessment(ErrorType.NOT_FOUND);
            termForm.setError(errorType);

			termForm.setNiceError(errorType);

            goodAndServiceForm.addTermForm(termForm);
        }

        return goodAndServiceForm;
	}

	public List<GoodAndServiceForm> verifyListOfTerms(List<TermsToBeValidated> terms) {
		List<GoodAndServiceForm> toReturn=new ArrayList<GoodAndServiceForm>();
		
		if (configurationService.isServiceEnabled(AvailableServices.VALIDATE_TERM.value())) {
			try {
				Collection<TermsValidated> validatedTerms = classificationService.verifyListOfTerms(terms);
				if (validatedTerms!=null) {
					for(TermsValidated termsValidatedByClass:validatedTerms){
						GoodAndServiceForm goodAndServiceForm = null;
						goodAndServiceForm = getGoodAndServiceForm(termsValidatedByClass.getTerms(), termsValidatedByClass.getLanguage(), termsValidatedByClass.getNiceClass());
						goodAndServiceForm.setDesc(termsValidatedByClass.getDescription());
						toReturn.add(goodAndServiceForm);
					}
				} else if (terms.size()>0) {
					throw new SPException("Failed to get any terms!", null, "error.gs.search.noresults");
				}
			} catch (SPException e) {
				logger.error(e);
	            for(TermsToBeValidated termsToBeValidated:terms){
	            	toReturn.add(createNotFoundTerm(termsToBeValidated.getLanguage(), termsToBeValidated.getNiceClass(), termsToBeValidated.getTerms()));
	            }
			}
        } else {
            for(TermsToBeValidated termsToBeValidated:terms){
            	toReturn.add(createNotFoundTerm(termsToBeValidated.getLanguage(), termsToBeValidated.getNiceClass(), termsToBeValidated.getTerms()));
            }
        }
		return toReturn;
	}

	private GoodAndServiceForm getGoodAndServiceForm(Collection<Term> terms, String language, String niceClass)  {
		GoodAndServiceForm goodsForm = new GoodAndServiceForm();
		
		goodsForm.setClassId(niceClass);
		goodsForm.setLangId(language);

		for (Term validatedTerm : terms) {
			TermForm termForm = new TermForm(validatedTerm.getNiceClass() != null?validatedTerm.getNiceClass()
					.toString():niceClass, validatedTerm.getText(), false);
			if (validatedTerm.getVerificationResult()!=null){

				if(!validatedTerm.getVerificationResult().equals(
					VerifiedTermResult.OK)) {
					ErrorType errorType = new ErrorType();
					if (validatedTerm.getVerificationResult()
						.equals(VerifiedTermResult.NONE)) {
						errorType.setErrorEnum(ClassificationErrorEnum.NONE);
					} else if (validatedTerm.getVerificationResult()
						.equals(VerifiedTermResult.HINT)) {
						errorType.setErrorEnum(ClassificationErrorEnum.HINT);
					} else if (validatedTerm.getVerificationResult()
						.equals(VerifiedTermResult.NOT_OK)) {
						errorType.setErrorEnum(ClassificationErrorEnum.NOT_OK);
					}
					errorType.setMatchedTerms(validatedTerm.getMatchedTerms());
					errorType.setVerificationAssessment(validatedTerm
						.getVerificationAssessment());

					termForm.setError(errorType);
					termForm.setNiceError(createNiceErrorType());
				} else if(validatedTerm.getMatchedTerms() != null && validatedTerm.getMatchedTerms().size() >0){
					Optional<MatchedTerm> matchedOptional = validatedTerm.getMatchedTerms().stream().findFirst();
					if(!matchedOptional.isPresent() || matchedOptional.get().getMatchedTerminologySourceList() == null
						|| !matchedOptional.get().getMatchedTerminologySourceList().contains("Nice")){
						termForm.setNiceError(createNiceErrorType());
					}
				} else {
					termForm.setNiceError(createNiceErrorType());
					logger.warn("Verified term has no verification result");
				}
			}

			goodsForm.addTermForm(termForm);
		}
		
		return goodsForm;
	}

	private ErrorType createNiceErrorType(){
		ErrorType niceErrorType = new ErrorType();
		niceErrorType.setVerificationAssessment(ErrorType.NOT_FOUND);
		niceErrorType.setErrorEnum(ClassificationErrorEnum.NOT_OK);
		return niceErrorType;
	}
	
	public List<Integer> getDistribution(String language, String term) {
		DistributionCriteria distributionCriteria = new DistributionCriteria();
		distributionCriteria.setLanguage(language);
		distributionCriteria.setOffice(office);
		distributionCriteria.setTerm(term);
		
		DistributionResults distributionResults = classificationService.getTermDistribution(distributionCriteria);
		
		List<Integer> classNumbers = new ArrayList<Integer>();
		if (distributionResults.getClassificationTerms()!=null) {
			for (ClassificationTerm classificationTerm : distributionResults.getClassificationTerms()) {
				classNumbers.add(classificationTerm.getClassNum());
			}
		}
		
		return classNumbers;
	}

	@Override
	public boolean containsAllNiceClassHeading(GoodAndServiceForm goodsAndServiceForm) {
		if (configurationService.isServiceEnabled(AvailableServices.IMPORT_NICE_CLASS_HEADING.value())) {
			try {
				Collection<Term> importedHeading = ((GoodsServicesServiceInterface) AopContext.currentProxy()).importCachedNiceClassHeading(office, goodsAndServiceForm.getClassId(), goodsAndServiceForm.getLangId());
				for (Term term : importedHeading) {
					boolean contained = false;
					for (TermForm termForm : goodsAndServiceForm.getTermForms()) {
						contained = StringUtils.equals((termForm.getDescription()!=null?termForm.getDescription().trim():null), 
								(term.getText()!=null?term.getText().trim():null));
						if (contained) {
							break;
						}
					}
					if (!contained) {
						return false;
					}
				}
				return true;
			} catch(SPException e) {
				logger.error(e);
				return false;
			}
		} else {
			return false;
		}
	}
	

	@Override
	@Cacheable(value = "importedNiceClassHeading")
	public Collection<Term> importCachedNiceClassHeading(String office, String niceClass, String language) {
		logger.debug("importCachedNiceClassHeading : " + office + ", " + niceClass + ", " + language);
 		Collection<Term> importedCachedNiceClassHeading = new ArrayList<Term>();
		try {
			importedCachedNiceClassHeading = classificationService.getNiceClassHeading(niceClass, language);
			if (importedCachedNiceClassHeading == null) {
				importedCachedNiceClassHeading = new ArrayList<Term>();
			}
			logger.debug("RESPONSE importCachedNiceClassHeading : " + importedCachedNiceClassHeading.size());
		} catch (SPException e) {
			logger.error(e);
		}
		return importedCachedNiceClassHeading;
	}
	
	@Override
	@Cacheable(value="validatedNiceClassHeading")
	public GoodAndServiceForm importNiceClassHeading(String niceClass, String language) {
		GoodAndServiceForm goodAndServiceForm = new GoodAndServiceForm();
		if (configurationService.isServiceEnabled(AvailableServices.IMPORT_NICE_CLASS_HEADING.value())) {
			if (StringUtils.isNotBlank(niceClass)
					&& StringUtils.isNotBlank(language)) {
				Collection<Term> importedHeading = ((GoodsServicesServiceInterface) AopContext.currentProxy()).importCachedNiceClassHeading(office, niceClass, language);
				int verifiedTerms = importedHeading.size();
				int i = 0;
				StringBuffer strBuffer = new StringBuffer();
				for (Term term : importedHeading) {
					strBuffer.append(term.getText()).append(((++i)<verifiedTerms?termSeparator+" ":""));
				}
				goodAndServiceForm = verifyListOfTerms(language, niceClass, strBuffer.toString());
			}
			
		}
		return goodAndServiceForm;
	}

	@Override
	public void validateGoodsAndServicesDescription(GoodAndServiceForm goodsForm, BindingResult result) {
		if (logger.isDebugEnabled()) {
			logger.debug("Validating GoodsAndServices description");
		}
		RulesInformation rulesInformation = new RulesInformation();
		rulesInformation.getCustomObjects().put(SECTION_NAME, AvailableSection.GOODSANDSERVICES.value());

		ClassDescription classDescription = goodsServicesFactory.convertPartialGSTo(goodsForm);

		List<Object> objects = new ArrayList<Object>();
		objects.add(rulesInformation);
		objects.add(classDescription);

		errorFactory.coreErrorToUIError(businessRulesService.validate(MODULE, GOODS_AND_SERVICES_SET, objects), result);
	}

}