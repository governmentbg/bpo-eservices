/*******************************************************************************
 * * $Id:: ConfigurationServiceDelegator.java 109645 2013-03-25 10:50:36Z ionitd#$
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.service;

import eu.ohim.sp.common.ui.form.application.SignatoryKindForm;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKind;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKindLegalAct;
import eu.ohim.sp.common.ui.form.patent.PatentApplicationKind;
import eu.ohim.sp.common.ui.form.person.PersonOptionSourceField;
import eu.ohim.sp.common.ui.form.resources.AttachmentDocumentType;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.util.LocarnoTranformer;
import eu.ohim.sp.common.ui.service.util.NiceTransformer;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.changeType.xsd.DossierChangeTypes;
import eu.ohim.sp.core.configuration.domain.changeType.xsd.DossierChangeTypes.DossierChangeType;
import eu.ohim.sp.core.configuration.domain.claims.xsd.Exhibitions;
import eu.ohim.sp.core.configuration.domain.claims.xsd.Exhibitions.Exhibition;
import eu.ohim.sp.core.configuration.domain.claims.xsd.PriorityTypes;
import eu.ohim.sp.core.configuration.domain.claims.xsd.PriorityTypes.PriorityType;
import eu.ohim.sp.core.configuration.domain.claims.xsd.SeniorityNatures;
import eu.ohim.sp.core.configuration.domain.claims.xsd.SeniorityNatures.SeniorityNature;
import eu.ohim.sp.core.configuration.domain.country.xsd.Countries;
import eu.ohim.sp.core.configuration.domain.country.xsd.Countries.Country;
import eu.ohim.sp.core.configuration.domain.country.xsd.Nationalities;
import eu.ohim.sp.core.configuration.domain.country.xsd.States;
import eu.ohim.sp.core.configuration.domain.design.xsd.DesignStatusList;
import eu.ohim.sp.core.configuration.domain.design.xsd.DesignViewPublicationSizes;
import eu.ohim.sp.core.configuration.domain.design.xsd.DesignViewPublicationSizes.DesignViewPublicationSize;
import eu.ohim.sp.core.configuration.domain.design.xsd.DesignViewTypes;
import eu.ohim.sp.core.configuration.domain.design.xsd.DesignViewTypes.DesignViewType;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.*;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.AbsoluteGrounds.AbsoluteGround;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.CategoriesTradeMark.CategoryTradeMark;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.EarlierEntitlementRightTypes.EarlierEntitlementRightType;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.EntitlementsOpponent.EntitlementOpponent;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RelativeGrounds.RelativeGround;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RevocationGrounds.RevocationGround;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.TypeRightEarlierTradeMarks.TypeRightEarlierTradeMark;
import eu.ohim.sp.core.configuration.domain.language.xsd.Languages;
import eu.ohim.sp.core.configuration.domain.language.xsd.Languages.Language;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PayerTypes;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PayerTypes.PayerType;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PaymentMethods;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PaymentMethods.PaymentMethod;
import eu.ohim.sp.core.configuration.domain.trademarkkind.xsd.TradeMarkKinds;
import eu.ohim.sp.core.configuration.domain.trademarkkind.xsd.TradeMarkKinds.TradeMarkKind;
import eu.ohim.sp.core.configuration.domain.trademarkkind.xsd.TradeMarkStatusList;
import eu.ohim.sp.core.configuration.domain.trademarkkind.xsd.TradeMarkTypes;
import eu.ohim.sp.core.configuration.domain.trademarkkind.xsd.TradeMarkTypes.TradeMarkType;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.classification.ClassificationHeading;
import eu.ohim.sp.core.domain.classification.LocarnoClassHeading;
import eu.ohim.sp.core.domain.classification.LocarnoSubClassHeading;
import eu.ohim.sp.core.domain.design.KeyTextUIClass;
import eu.ohim.sp.core.domain.patent.RegKind;
import eu.ohim.sp.core.domain.patent.SVKind;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.person.RepresentativeKind;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.core.resource.ResourceService;
import eu.ohim.sp.core.rules.RulesService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Configuration service that gets the configuration of the application and manages caching
 * of the configuration parameter values
 * 
 * @author karalch
 * 
 */
@Service
public class ConfigurationServiceDelegator implements ConfigurationServiceDelegatorInterface {

    private static Logger logger = Logger.getLogger(ConfigurationServiceDelegator.class);

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private SectionViewConfiguration viewConfiguration;
    
    @Autowired
    private DocumentService documentService;
    
    @Autowired
    private RulesService businessRulesService;

    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private PersonService personService;

    @Autowired
    private CacheManager cacheManager;
    
    @Value("${general.component}")
    private String generalComponent;

    @Value("${securityEnabled}")
    private boolean securityEnabled;
    
    @Value("${sp.office}")
    public String office;

    @Value("${sp.module}")
    private String component;
    
    @Value("${sp.languageOffice}")
    public String languageOffice;

    private Map<String, Language> firstLanguages;
    private Map<String, Language> secondLanguages;

    @Override
    @Cacheable(value = "firstLanguages")
    public List<Language> getFirstLanguages() {
        if (logger.isDebugEnabled()) {
            logger.debug("Loading list of first languages ....");
        }

        List<Language> listOfLanguages = configurationService.getObject(FIRST_LANG_PARAM, generalComponent,
                Languages.class).getLanguage();
        firstLanguages = new TreeMap<String, Language>();
        for (Language language : listOfLanguages) {
            firstLanguages.put(language.getCode(), language);
        }
        return listOfLanguages;
    }

    @Override
    public Language resolveFirstLanguageCode(String languageCode) {
        if (firstLanguages == null) {
            getFirstLanguages();
        }
        return (languageCode==null?null:firstLanguages.get(languageCode));
    }

    @Override
    @Cacheable(value = "secondLanguages")
    public List<Language> getSecondLanguages() {
        if (logger.isDebugEnabled()) {
            logger.debug("Loading list of second languages ....");
        }

        List<Language> listOfLanguages = configurationService.getObject(SECOND_LANG_PARAM, generalComponent,
                Languages.class).getLanguage();
        secondLanguages = new TreeMap<String, Language>();
        for (Language language : listOfLanguages) {
            secondLanguages.put(language.getCode(), language);
        }
        return listOfLanguages;
    }

    @Override
    public Language resolveSecondLanguageCode(String languageCode) {
        if (secondLanguages == null) {
            getSecondLanguages();
        }
        return (languageCode==null?null:secondLanguages.get(languageCode));
    }
    
	public Double getDocumentSize(String provisionalId, String filename) {
		return documentService.getDocument(provisionalId).getFileSize().doubleValue();
	}


    @Override
    @Cacheable(value = "countries")
    public List<Country> getCountries() {
        return configurationService.getObject(COUNTRIES_PARAM, generalComponent, Countries.class).getCountry();
    }
    
    @Override
    @Cacheable(value = "countriesObject")    
    public Countries getCountriesObject() {
        return configurationService.getObject(COUNTRIES_PARAM, generalComponent, Countries.class);
    }   

    @Override
    @Cacheable(value = "tradeMarkKinds")
    public List<TradeMarkKind> getTradeMarkKinds() {
        return configurationService.getObject(TMKIND_PARAM, generalComponent, TradeMarkKinds.class).getTradeMarkKind();
    }  
    
    @Override
    @Cacheable(value = "tradeMarkTypes")
    public List<TradeMarkType> getTradeMarkTypes() {
        List<TradeMarkType> types = configurationService.getObject(TMTYPE_PARAM, generalComponent, TradeMarkTypes.class).getTradeMarkType();
        return types.stream().filter(t -> t.isEnabledGI() == false).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "giTypes")
    public List<TradeMarkType> getGiTypes() {
        List<TradeMarkType> types = configurationService.getObject(TMTYPE_PARAM, generalComponent, TradeMarkTypes.class).getTradeMarkType();
        return types.stream().filter(t -> t.isEnabledGI() == true).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "dossierChangeTypes")
    public List<DossierChangeType> getDossierChangeTypes() {
        return configurationService.getObject(DOSSIER_CHANGE_TYPE_PARAM, generalComponent, DossierChangeTypes.class).getDossierChangeType();
    }

    @Override
    @Cacheable(value = "tradeMarkStatusList")
    public List<TradeMarkStatusList.TradeMarkStatus> getTradeMarkStatusList() {
        return configurationService.getObject(TMSTATUS_PARAM, generalComponent, TradeMarkStatusList.class).getTradeMarkStatus();
    }
    
    @Override
    @Cacheable(value = "designStatusList")
    public List<DesignStatusList.DesignStatus> getDesignStatusList() {
        return configurationService.getObject(DSSTATUS_PARAM, generalComponent, DesignStatusList.class).getDesignStatus();
    }
    
    @Override
    @Cacheable(value = "nationalities")
    public List<Nationalities.Nationality> getNationalities() {
        List<Nationalities.Nationality> natioanlities = configurationService.getObject(NATIONALITIES_PARAM, generalComponent, Nationalities.class)
                .getNationality();
       return natioanlities;
    }

    @Override
    @Cacheable(value = "states")
    public List<States.Country.State> getStates(String countryCode) {
        List<States.Country> statesInCountries = configurationService.getObject(STATES_PARAM, generalComponent,
                States.class).getCountry();
        // Filter by country code
        List<States.Country.State> filtered = new ArrayList<States.Country.State>();
        for (States.Country item : statesInCountries) {
            if (item.getCountryCode().equals(countryCode)) {
                filtered.addAll(item.getState());
            }
        }
        return filtered;
    }
    
    @Override
    public List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion> getLegalActVersions(GroundCategoryKind groundsCategory, List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion> legalActVersions) {
    	List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion> filtered = new ArrayList<eu.ohim.sp.common.ui.form.opposition.LegalActVersion>();
        for (eu.ohim.sp.common.ui.form.opposition.LegalActVersion itemLa : legalActVersions) {
        	
        	if(!filtered.contains(itemLa)){
        		if (itemLa.getGroundCategory() == GroundCategoryKindLegalAct.BOTH){	
        			filtered.add(itemLa);     
        		}
        		else{
        			
        			if (itemLa.getGroundCategory() == convertToGroundCategoryKindLegalAct(groundsCategory))
        				filtered.add(itemLa);
        		}
        	}
        }
        return filtered;
    }
    
    public GroundCategoryKindLegalAct convertToGroundCategoryKindLegalAct(GroundCategoryKind groundCategory){
    	GroundCategoryKindLegalAct gCLA;
    	if (groundCategory == GroundCategoryKind.ABSOLUTE_GROUNDS)
    		gCLA = GroundCategoryKindLegalAct.ABSOLUTE_GROUNDS;
    	else
    	if (groundCategory == GroundCategoryKind.RELATIVE_GROUNDS)
    		gCLA = GroundCategoryKindLegalAct.RELATIVE_GROUNDS;
    		else
    			if (groundCategory == GroundCategoryKind.REVOCATION_GROUNDS)
    	    		gCLA = GroundCategoryKindLegalAct.REVOCATION_GROUNDS;
    			else
    				gCLA = null;
    	return gCLA;
    }

    @Override
    @Cacheable(value = "absoluteGrounds")
    public List<AbsoluteGround> getAbsoluteGrounds() {
        return configurationService.getObject(ABSOLUTE_GROUNDS_PARAM , generalComponent, AbsoluteGrounds.class).getAbsoluteGround();
    }
    
    @Override
    @Cacheable(value = "relativeGrounds")
    public List<RelativeGround> getRelativeGrounds() {
        return configurationService.getObject(RELATIVE_GROUNDS_PARAM , generalComponent, RelativeGrounds.class).getRelativeGround();
    }
    
    @Override
    @Cacheable(value = "revocationGrounds")
    public List<RevocationGround> getRevocationGrounds() {
        return configurationService.getObject(REVOCATION_GROUNDS_PARAM , generalComponent, RevocationGrounds.class).getRevocationGround();
    }
    
    @Override
    @Cacheable(value = "earlierEntitlementRightTypes")
    public List<EarlierEntitlementRightType> getEarlierEntitlementRightTypes() {
        return configurationService.getObject(EARLIER_ENTITLEMENT_RIGHT_TYPES_PARAM , generalComponent, EarlierEntitlementRightTypes.class).getEarlierEntitlementRightType();
    }
    
    @Override
    @Cacheable(value = "typeRightEarlierTradeMarks")
    public List<TypeRightEarlierTradeMark> getTypeRightEarlierTradeMarks() {
        return configurationService.getObject(TYPE_RIGHT_EARLIER_TRADE_MARK_PARAM , generalComponent, TypeRightEarlierTradeMarks.class).getTypeRightEarlierTradeMark();
    }
    
    @Override
    @Cacheable(value = "categoriesTradeMark")
    public List<CategoryTradeMark> getCategoriesTradeMark() {
    	return configurationService.getObject(CATEGORY_TRADE_MARK_PARAM , generalComponent, CategoriesTradeMark.class).getCategoryTradeMark();
    }

    @Override
    @Cacheable(value = "entitlementsOpponent")
    public List<EntitlementOpponent> getEntitlementsOpponent() {
    	return configurationService.getObject(ENTITLEMENT_OPPONENT , generalComponent, EntitlementsOpponent.class).getEntitlementOpponent();
    }

    
    @Override
    @Cacheable(value = "helpMessages")
    public String getMessage(String messageKey)
    {
        return resourceService.getMessage(messageKey);
//        return null;
    }

    @Override
    @Cacheable(value = "seniorityNatures")
    public List<SeniorityNature> getSeniorityNatures() {
        return configurationService.getObject(SENIORITY_NATURE_PARAM, generalComponent, SeniorityNatures.class)
                .getSeniorityNature();
    }
    
    @Override
    public List<SeniorityNature> getSeniorityNaturesByModule(String module) {
        return configurationService.getObject(SENIORITY_NATURE_PARAM, StringUtils.isBlank(module) ? generalComponent : module, SeniorityNatures.class)
                .getSeniorityNature();
    }

    @Override
    @Cacheable(value = "exhibitions")
    public List<Exhibition> getExhibitions() {
    	List<Exhibition> toReturn= configurationService.getObject(EXHIBITION_PARAM, generalComponent, Exhibitions.class).getExhibition();
        return toReturn;
    }
    
    @Override
    public List<Exhibition> getExhibitionsByModule(String module) {
    	return configurationService.getObject(EXHIBITION_PARAM, StringUtils.isBlank(module) ? generalComponent : module, Exhibitions.class).getExhibition();
    }

    @Override
    @Cacheable(value = "paymentMethods")
    public List<PaymentMethod> getActivePaymentMethods() {
        List<PaymentMethod> activePaymentMethods = new ArrayList<PaymentMethod>();
        List<PaymentMethod> allPaymentMethods = configurationService.getObject(PAYMENT_METHOD_PARAM, generalComponent,
                PaymentMethods.class).getPaymentMethod();
        for (PaymentMethod p : allPaymentMethods) {
            if (p.isActive()) {
                activePaymentMethods.add(p);
            }
        }
        return activePaymentMethods;
    }

    @Override
    @Cacheable(value = "payerTypes")
    public List<PayerType> getActivePayerTypes() {
        List<PayerType> activePayerTypes = new ArrayList<PayerType>();
        List<PayerType> allPayerTypes = configurationService.getObject(PAYER_TYPE_PARAM, generalComponent,
                PayerTypes.class).getPayerType();
        for (PayerType p : allPayerTypes) {
            if (p.isActive()) {
                activePayerTypes.add(p);
            }
        }
        return activePayerTypes;
    }

    @Override
    public String getValueFromGeneralComponent(String key) {
        return configurationService.getValue(key, generalComponent);
    }

    @Override
    public String getValue(String key, String component) {
        return configurationService.getValue(key, component);
    }

    @Override
    public String getXMLFromGeneralComponent(String key) {
        return configurationService.getXml(key, generalComponent);
    }

    @Override
    public <T> T getObjectFromGeneralComponent(String key, Class<T> clazz) {
        return configurationService.getObject(key, generalComponent, clazz);
    }
    
    @Override
    public <T> T getObjectFromComponent(String key, String component, Class<T> clazz) {
        return configurationService.getObject(key, component, clazz);
    }

    @Override
    public List<String> getApplicantTypes(String flowModeId) {
        List<Section> sections = viewConfiguration.getSortedSubsections(flowModeId, "", AvailableSection.APPLICANT);
        List<String> types = new ArrayList<String>();
        for (Section s : sections) {
            types.add(s.getPath());
        }
        return types;
    }
    @Override
    public List<String> getRepresentativeTypes(String flowModeId) {
        List<Section> sections = viewConfiguration.getSortedSubsections(flowModeId, "", AvailableSection.REPRESENTATIVE);
        List<String> types = new ArrayList<String>();
        for (Section s : sections) {
            types.add(s.getPath());
        }
        return types;
    }
    public List<String> getRepresentativeTypesDisabled(String flowModeId) {
        List<Section> sections = viewConfiguration.getSortedSubsections(flowModeId, "", AvailableSection.REPRESENTATIVE);
        List<String> types = new ArrayList<String>();
        for (Section s : sections) {
            if(Boolean.FALSE.equals(s.isSelectable())) {
                types.add(s.getPath());
            }
        }
        return types;
    }

    @Override
    public List<String> getRemCreditorTypes(String flowModeId) {
        List<Section> sections = viewConfiguration.getSortedSubsections(flowModeId, "", AvailableSection.REMCREDITOR);
        List<String> types = new ArrayList<String>();
        for (Section s : sections) {
            types.add(s.getPath());
        }
        return types;
    }

    @Override
    public List<String> getLicenseeTypes(String flowModeId) {
        List<Section> sections = viewConfiguration.getSortedSubsections(flowModeId, "", AvailableSection.LICENSEE);
        List<String> types = new ArrayList<String>();
        for (Section s : sections) {
            types.add(s.getPath());
        }
        return types;
    }

    @Override
    public List<String> getAssigneeTypes(String flowModeId) {
        List<Section> sections = viewConfiguration.getSortedSubsections(flowModeId, "", AvailableSection.ASSIGNEE);
        List<String> types = new ArrayList<String>();
        for (Section s : sections) {
            types.add(s.getPath());
        }
        return types;
    }

    @Override
    public List<String> getAssigneeSecurityTypes(String flowModeId) {
        List<Section> sections = viewConfiguration.getSortedSubsections(flowModeId, "", AvailableSection.ASSIGNEE_SECURITY);
        List<String> types = new ArrayList<String>();
        for (Section s : sections) {
            types.add(s.getPath());
        }
        return types;
    }


    @Override
    public List<String> getAssigneeBankruptcyTypes(String flowModeId) {
        List<Section> sections = viewConfiguration.getSortedSubsections(flowModeId, "", AvailableSection.ASSIGNEE_BANKRUPTCY);
        List<String> types = new ArrayList<String>();
        for (Section s : sections) {
            types.add(s.getPath());
        }
        return types;
    }

    @Override
    public List<String> getHolderTypes(String flowModeId) {
        List<Section> sections = viewConfiguration.getSortedSubsections(flowModeId, "", AvailableSection.HOLDER);
        List<String> types = new ArrayList<String>();
        for (Section s : sections) {
            types.add(s.getPath());
        }
        return types;
    }

    @Override
    public List<String> getPersonTypes(String flowModeId) {
        List<Section> sections = viewConfiguration.getSortedSubsections(flowModeId, "", AvailableSection.PERSON);
        List<String> types = new ArrayList<String>();
        for (Section s : sections) {
            types.add(s.getPath());
        }
        return types;
    }

    public boolean isServiceEnabled(String service) {
        if(component!=null) {
            return isServiceEnabled(service,component);
        } else {
            return isServiceEnabled(service,generalComponent);
        }
    }

    public boolean isServiceEnabledByFlowMode(String service, String flowModeId) {
        if (StringUtils.isNotEmpty(flowModeId)) {
            service = service.concat("_").concat(flowModeId);
        }
        if(component!=null) {
            return isServiceEnabled(service,component);
        } else {
            return isServiceEnabled(service,generalComponent);
        }
    }

    public boolean isServiceEnabled(String service, String sp_component) {
        boolean enabled = configurationService.isServiceEnabled(service,sp_component);
        if (logger.isDebugEnabled()) {
            logger.debug("Service " + service + " : " + enabled);
        }
        return enabled;
    }
    
    @Override
    public boolean isSecurityEnabled() {
        return securityEnabled;
    }

    @Override
    @CacheEvict(value = {"firstLanguages", "secondLanguages", "countries", "seniorityNatures", "exhibitions",
            "paymentMethods", "payerTypes", "importedNiceClassHeading", "validatedNiceClassHeading", "designViewPublicationSizes", "designViewTypes", "priorityTypes"},
            allEntries=true)
    public void emptyCache() {
        if (logger.isDebugEnabled()) {
            logger.debug("Emptying cache of the application.");
        }
    }
    
    @Override
    @CacheEvict(value = {"firstLanguages", "secondLanguages", "countries", "seniorityNatures", "exhibitions",
            "paymentMethods", "payerTypes", "importedNiceClassHeading", "validatedNiceClassHeading", "personDetails", "tradeMarkKinds", "tradeMarkStatusList", 
            "designStatusList", "legalActVersions", "absoluteGrounds", "relativeGrounds", "revocationGrounds", "earlierEntitlementRightTypes", 
            "typeRightEarlierTradeMarks", "categoriesTradeMark", "entitlementsOpponent", "nationalities", "states", "fullClassification",
            "searchProductIndication", "searchProductIndication", "locarnoClasses", "locarnoSubclasses", "locarnoClassifications"},
            allEntries=true)
    public void emptyCacheEServices() {
        if (logger.isDebugEnabled()) {
            logger.debug("Emptying cache of the application.");
        }
    }
    
    @Override
    public void emptyCache(String cacheName) {
        logger.debug("Emptying cache : " + cacheName);
        if (cacheManager.getCache(cacheName)!=null) {
        	cacheManager.getCache(cacheName).clear();
        }
    }

    @Override
	@Cacheable(value = "designViewPublicationSizes")
	public List<DesignViewPublicationSize> getDesignViewPublicationSizes() {
		return configurationService.getObject(DESIGN_VIEW_PUBLICATION_SIZES_PARAM, generalComponent, DesignViewPublicationSizes.class).getDesignViewPublicationSize();
	}

	@Override
	@Cacheable(value = "designViewTypes")
	public List<DesignViewType> getDesignViewTypes() {
		return configurationService.getObject(DESIGN_VIEW_TYPES_PARAM, generalComponent, DesignViewTypes.class).getDesignViewType();	
	}

	@Override
	@Cacheable(value = "priorityTypes")
	public List<PriorityType> getPriorityTypes() {
		return configurationService.getObject(PRIORITY_TYPES_PARAM, generalComponent, PriorityTypes.class).getPriorityType();
	}
	
	@Override
	public String getOffice() {
		return office;
	}

	@Override
	@Cacheable(value = "languageOffice")
	public String getLanguageOffice() {
		return languageOffice;
	}
	
	@Override
	public int getUserNaturalPersonCount(String flowModeId){
		int count = 0;
		UserPersonDetails userPersonDetails = personService.getUserPersonDetails(flowModeId);
		if (userPersonDetails != null){
			List <Applicant> applicants = userPersonDetails.getApplicants();
	        if (CollectionUtils.isNotEmpty(applicants)) {
				for (Applicant applicant : applicants) {
					if (applicant.getKind() == PersonKind.NATURAL_PERSON || applicant.getKind() == PersonKind.NATURAL_PERSON_SPECIAL) 
						count++;
				}
			}
		}
		return count;
	}
	
	@Override
	public int getUserLegalEntityCount(String flowModeId){
		int count = 0;
		UserPersonDetails userPersonDetails = personService.getUserPersonDetails(flowModeId);
		if (userPersonDetails != null){
			List <Applicant> applicants = userPersonDetails.getApplicants();
	        if (CollectionUtils.isNotEmpty(applicants)) {
				for (Applicant applicant : applicants) {
					if (applicant.getKind() == PersonKind.LEGAL_ENTITY)
						count++;
				}
			}
		}
		return count;
	}

    @Override
    public int getUserRepresentativeNaturalPersonCount(String flowModeId) {
        int count = 0;
        UserPersonDetails userPersonDetails = personService.getUserPersonDetails(flowModeId);
        if (userPersonDetails != null) {
            List<Representative> representatives = userPersonDetails.getRepresentatives();
            for(Representative representative: representatives){
                if(representative.getRepresentativeKind().equals(RepresentativeKind.OTHER)){
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public int getUserRepresentativeAssociationCount(String flowModeId) {
        int count = 0;
        UserPersonDetails userPersonDetails = personService.getUserPersonDetails(flowModeId);
        if (userPersonDetails != null) {
            List<Representative> representatives = userPersonDetails.getRepresentatives();
            for(Representative representative: representatives){
                if(representative.getRepresentativeKind().equals(RepresentativeKind.ASSOCIATION)){
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public List<SignatoryKindForm> getSignatoryKinds(String flowModeId){
        List<SignatoryKindForm> kindsCopied  = new ArrayList<>();
        for(SignatoryKindForm sigKind: SignatoryKindForm.values()){
            kindsCopied.add(sigKind);
        }
        if(!flowModeId.endsWith("-security")){
            kindsCopied.remove(SignatoryKindForm.BAILIFF);
        }
        if(!flowModeId.endsWith("-bankruptcy")){
            kindsCopied.remove(SignatoryKindForm.ASSIGNEE);
        }
        return kindsCopied;
    }

    @Override
    public List<String> getTransformationTypes(String flowModeId){
        List<Section> sections = viewConfiguration.getSortedSubsections(flowModeId, "", AvailableSection.TRANSFORMATION);
        List<String> types = new ArrayList<String>();
        for (Section s : sections) {
            types.add(s.getPath());
        }
        return types;
    }

    @Override
    public List<String> getParallelApplicationTypes(String flowModeId){
        List<Section> sections = viewConfiguration.getSortedSubsections(flowModeId, "", AvailableSection.PARALLEL_APPLICATION);
        List<String> types = new ArrayList<String>();
        for (Section s : sections) {
            types.add(s.getPath());
        }
        return types;
    }

    @Override
    public List<AttachmentDocumentType> getAttachmentTypeForFlow(String flowModeId, String configSufix, String changeType){
        String attachmentTypeNames = "";
        StringBuilder key = new StringBuilder();
        key.append("attachment.types");
        if(configSufix != null && !configSufix.isEmpty()){
            key.append(".");
            key.append(configSufix);
        }
        if(changeType != null && !changeType.isEmpty()){
            key.append(".");
            key.append(changeType);
        }
        attachmentTypeNames = getValue(key.toString(), flowModeId);

        List<AttachmentDocumentType> resultDocTypes = new ArrayList<>();
        resultDocTypes.addAll(splitStringToAttachmentTypes(attachmentTypeNames));

        if (!flowModeId.endsWith("-providepow") && !flowModeId.endsWith("-paidtaxes") && !flowModeId.endsWith("-invdenial") && !flowModeId.endsWith("-generic") && !flowModeId.endsWith("-withdrawal")) {
            String defaultAttachmentTypeNames = getValue("attachment.types", "default");
            resultDocTypes.addAll(splitStringToAttachmentTypes(defaultAttachmentTypeNames));
        }

        return resultDocTypes;
    }

    @Override
    public List<String> getMandatoryAttachmentTypeForFlow(String flowModeId, String configSufix, String changeType){
        String attachmentTypeNames = "";
        StringBuilder key = new StringBuilder();
        key.append("attachment.types");
        if(configSufix != null && !configSufix.isEmpty()){
            key.append(".");
            key.append(configSufix);
        }
        if(changeType != null && !changeType.isEmpty()){
            key.append(".");
            key.append(changeType);
        }
        key.append(".mandatory");
        attachmentTypeNames = getValue(key.toString(), flowModeId);

        List<AttachmentDocumentType> resultDocTypes = new ArrayList<>();
        resultDocTypes.addAll(splitStringToAttachmentTypes(attachmentTypeNames));

        return resultDocTypes.stream().map(AttachmentDocumentType::value).collect(Collectors.toList());
    }

    public boolean attachmentTypeMandatory(String flowModeId, String configSufix, String changeType, AttachmentDocumentType attachmentType){
        String mandatoryAttachmentTypeNames;
        StringBuilder key = new StringBuilder();
        key.append("attachment.types");
        if(configSufix != null && !configSufix.isEmpty()){
            key.append(".");
            key.append(configSufix);
        }
        if(changeType != null && !changeType.isEmpty()){
            key.append(".");
            key.append(changeType);
        }
        key.append(".mandatory");
        mandatoryAttachmentTypeNames = getValue(key.toString(), flowModeId);

        List<AttachmentDocumentType> mandatoryDocTypes = new ArrayList<>(splitStringToAttachmentTypes(mandatoryAttachmentTypeNames));

        if (mandatoryDocTypes.contains(attachmentType)) {
            return true;
        }
        return false;
    }

    private List<AttachmentDocumentType> splitStringToAttachmentTypes(String theString){
        List<AttachmentDocumentType> attachmentDocumentTypes = new ArrayList<>();
        if(theString != null && !theString.isEmpty()) {
            String[] separatedNames = theString.split(",");
            attachmentDocumentTypes.addAll(Arrays.stream(separatedNames).map(a -> AttachmentDocumentType.fromValue(a)).collect(Collectors.toList()));
        }
        return attachmentDocumentTypes;
    }

    @Override
    @Cacheable(value = "defaultLocarnoClasses", key="#p0", condition = "#p0!=null")
    public List<KeyTextUIClass> getDefaultLocarnoClasses(String language){

        List<KeyTextUIClass> ret = new ArrayList<>();
        try {
            String defaultLocarnoClassesJson = configurationService.getFileContent(DEFAULT_LOCARNO_CLASSES_FILE, DESIGN_COMPONENT);
            ClassificationHeading classifications = LocarnoTranformer.locarnoClasses(defaultLocarnoClassesJson);

            if (classifications != null && classifications.getClassification() != null) {
                for (LocarnoClassHeading lch : classifications.getClassification()) {
                    ret.add(new KeyTextUIClass(padClass(lch.getClassCode()), padClass(lch.getClassCode())));
                }
            }
        }
        catch (Exception e){
            logger.error("There was an error getting default locarno classes.");
        }

        return ret;
    }

    @Override
    @Cacheable(value = "defaultNiceAlphaLists", key="#p0", condition = "#p0!=null")
    public Map<String, Set<String>> getDefaultNiceAlphaLists(String language){
        try {
            String defaultNiceAlphaJson = configurationService.getFileContent(DEFAULT_NICE_ALPHA_LISTS_FILE, GENERAL_COMPONENT);
            return NiceTransformer.niceAlphaLists(defaultNiceAlphaJson, language);
        }
        catch (Exception e){
            logger.error("There was an error getting default locarno classes.");
        }

        return new HashMap<>();
    }

    @Override
    @Cacheable(value = "defaultLocarnoSubclasses", key="{#p0,#p1,#p2}")
    public List<KeyTextUIClass> getDefaultLocarnoSubclasses(Integer selectedClass, String language, String version){
        List<KeyTextUIClass> ret = new ArrayList<>();

        try {
            String defaultLocarnoClassesJson = configurationService.getFileContent(DEFAULT_LOCARNO_CLASSES_FILE, DESIGN_COMPONENT);
            ClassificationHeading classifications = LocarnoTranformer.locarnoClasses(defaultLocarnoClassesJson);
            List<LocarnoSubClassHeading> listSubClassifications = LocarnoTranformer.getLocarnoSubClassHeadings(selectedClass, classifications);

            if (listSubClassifications != null) {
                for (LocarnoSubClassHeading lsh : listSubClassifications) {
                    ret.add(new KeyTextUIClass(padClass(lsh.getSubClassCode()), padClass(lsh.getSubClassCode())));
                }
            }
        }
        catch(Exception e){
            logger.error("There was an error getting default locarno subclasses.");
        }
        return ret;
    }

    /**
     * Pad class.
     *
     * @param integer the integer
     * @return the string
     */
    private String padClass(Integer integer) {
        return StringUtils.leftPad(String.valueOf(integer), 2, '0');
    }

    public List<PatentApplicationKind> getPatentApplicationKinds(String flowModeId){
        return Arrays.asList(PatentApplicationKind.values());
    }

    public List<SVKind> getSVKinds(){
        return Arrays.asList(SVKind.values());
    }

    public List<RegKind> getRegKinds(){
        return Arrays.asList(RegKind.values());
    }

    @Override
    public String getApplicationManagementUrl(String module, String key, String flowModeId) {
        String result = null;
        if(flowModeId != null){
            result = getValue(key+"."+flowModeId, module);
        } else {
            result = getValue(key, module);
        }

        return result;
    }

    @Override
    public String getPatentSearchExternalUrl(String flowModeId) {
        if(flowModeId.startsWith("pt-")){
            return getValue("patent.search.external.url", "general");
        } else if(flowModeId.startsWith("um-")){
            return getValue("model.search.external.url", "general");
        } else if(flowModeId.startsWith("ep-")){
            return getValue("epopatent.search.external.url", "general");
        }
        return null;
    }

    @Override
    public boolean hasSelectPersonButton(String flowModeId, String sectionId) {
        AvailableSection availableSection = AvailableSection.fromValue(sectionId);
        List<PersonOptionSourceField> sourceFields = PersonOptionSourceField.fieldsForSection(availableSection);
        if(sourceFields != null){
            return sourceFields.stream()
                .map(source -> viewConfiguration.getRender(availableSection, source.getValue(), flowModeId))
                .reduce((b1, b2) -> b1 || b2).get();
        }
        return false;
    }
}

