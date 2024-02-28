/*******************************************************************************
 * * $Id::                                                                       $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.service.interfaces;

import eu.ohim.sp.core.configuration.domain.claims.xsd.Exhibitions.Exhibition;
import eu.ohim.sp.core.configuration.domain.claims.xsd.SeniorityNatures.SeniorityNature;
import eu.ohim.sp.core.configuration.domain.country.xsd.Countries.Country;
import eu.ohim.sp.core.configuration.domain.country.xsd.Nationalities;
import eu.ohim.sp.core.configuration.domain.country.xsd.States;
import eu.ohim.sp.core.configuration.domain.language.xsd.Languages.Language;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PayerTypes.PayerType;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PaymentMethods.PaymentMethod;

import java.util.List;

/**
 * Delegator that is used to get properties from the system configuration
 * 
 * @author karalch
 */
public interface ConfigurationServiceDelegatorInterface {

    /** Parameter name for the minimum character for enabling autocomplete */
    String MINIMUM_CHAR_AUTOCOMPLETE_TRADEMARK = "minimum.characters.autocomplete";

    // <editor-fold defaultstate="collapsed" desc="Person service parameters">
    /**
     * Person service parameters
     */
    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of applicant matches to return
     */
    String APPLICANT_MATCH_MAXRESULTS = "service.applicant.match.maxResults";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of applicants to add
     */
    String APPLICANT_ADD_MAXNUMBER = "applicant.add.maxNumber";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of applicants to return for the autocomplete functionality
     */
    String APPLICANT_AUTOCOMPLETE_MAXRESULTS = "service.applicant.autocomplete.maxResults";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representative matches to return
     */
    String REPRESENTATIVE_MATCH_MAXRESULTS = "service.representative.match.maxResults";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representatives to add
     */
    String REPRESENTATIVE_ADD_MAXNUMBER = "representative.add.maxNumber";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representative to return for the autocomplete functionality
     */
    String REPRESENTATIVE_AUTOCOMPLETE_MAXRESULTS = "service.representative.autocomplete.maxResults";

    /**
     * The name of the person component in configuration
     */
    String PERSON_COMPONENT = "eu.ohim.sp.core.person";
    // </editor-fold>

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of priorities to add
     */
    String CLAIM_PRIORITY_ADD_MAXNUMBER = "claim.priority.add.maxNumber";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of seniorities to add
     */
    String CLAIM_SENIORITY_ADD_MAXNUMBER = "claim.seniority.add.maxNumber";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of transformation priorities to add
     */
    String CLAIM_TRANSFORMATION_ADD_MAXNUMBER = "claim.transformation.add.maxNumber";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of exhibition priorities to add
     */
    String CLAIM_EXHIBITION_ADD_MAXNUMBER = "claim.exhibition.add.maxNumber";

    /**
     * Parameter name for the initial level of nodes that are prefetched by the
     * taxonomy service
     */
    String CONCEPT_TREE_NODES_PRE = "service.goods.limit.taxonomy";
    /** Parameter name for the limit of search results of G&S terms */
    String RESULTS_TERMS_LIMIT = "service.goods.search.limit";
    /** Parameter name for the available first languages */
    String FIRST_LANG_PARAM = "first-lang-conf";
    /** Parameter name for the available second languages */
    String SECOND_LANG_PARAM = "second-lang-conf";
    /** Parameter name for the available countries */
    String COUNTRIES_PARAM = "country-config";
    /** Parameter name for the available nationalities */
    String NATIONALITIES_PARAM = "nationality-config";
    /** Parameter name for the available states */
    String STATES_PARAM = "states-config";
    /** Parameter name for the mapping roles and permissions */
    String MAPPING_ROLES_PARAM = "mapping-roles";
    /** Parameter name for the configuration of jcr */
    String FILE_UPLOAD_CONFIGURATION = "fileUploadConfiguration";
    /** Parameter name for the available seniority natures */
    String SENIORITY_NATURE_PARAM = "seniority-natures";
    /** Parameter name for the available exhibitions */
    String EXHIBITION_PARAM = "exhibitions";
    /** Parameter name for the methods of payment */
    String PAYMENT_METHOD_PARAM = "payment-methods";
    /** Parameter name for the tm-efiling product code (related to payment) */
    String TM_EFILING_PRODUCT_CODE = "tmefiling.product.code";
    /** Parameter name for the currency code */
    String CURRENCY_CODE = "currency.code";
    /** Parameter name for the types of payer */
    String PAYER_TYPE_PARAM = "payer-types";

    /**
     * Retrieves the list of languages that can be used as first language
     * 
     * @return an object containing the list of available first languages
     */
    List<Language> getFirstLanguages();

    /**
     * Resolves the language code to a language according to configuration of
     * first languages
     * 
     * @param languageCode
     *            the language code
     * @return the language that we are interested
     */
    Language resolveFirstLanguageCode(String languageCode);

    /**
     * Retrieves the list of languages that can be used as second language
     * 
     * @return an object containing the list of available second languages
     */
    List<Language> getSecondLanguages();

    /**
     * Resolves the language code to a language according to configuration of
     * second languages
     * 
     * @param languageCode
     *            the language code
     * @return the language that we are interested
     */
    Language resolveSecondLanguageCode(String languageCode);

    /**
     * Retrieves the list of available countries
     * 
     * @return an object containing the list of available countries
     */
    List<Country> getCountries();
    
	Integer getDocumentSize(String provisionalId, String filename);

    /**
     * Retrieves the list of available nationalities
     * 
     * @return an object containing the list of available nationalities
     */
    List<Nationalities.Nationality> getNationalities();

    /**
     * Retrieves the list of available states
     * 
     * @return an object containing the list of available states
     */
    List<States.Country.State> getStates(String countryCode);

    /**
     * Retrieves the list of seniority natures
     * 
     * @return an object of the seniority natures mapping
     */
    List<SeniorityNature> getSeniorityNatures();

    /**
     * Retrieves the list of exhibitions
     * 
     * @return an object of the exhibitions mapping
     */
    List<Exhibition> getExhibitions();

    /**
     * Retrieves the list of methods of payment
     * 
     * @return an object of the methods of payment mapping
     */
    List<PaymentMethod> getActivePaymentMethods();

    /**
     * Retrieves the list of types of payer
     * 
     * @return an object types of payer mapping
     */
    List<PayerType> getActivePayerTypes();

    /**
     * Retrieves the value of a parameter configured on system configuration
     * 
     * @param key
     *            the key parameter
     * @return the value of a parameter
     */
    String getValueFromGeneralComponent(String key);

    /**
     * Retrieves the xml of a parameter configured on system configuration
     * 
     * @param key
     *            the key parameter
     * @return the value of a parameter
     */
    String getXMLFromGeneralComponent(String key);

    /**
     * Retrieves the xml of a parameter configured on system configuration
     * 
     * @param key
     *            the key parameter
     * @param component
     * @return the value of a parameter
     */
    String getValue(String key, String component);

    /**
     * Retrieves the object of a parameter configured on system configuration
     * 
     * @param <T>
     * @param key
     *            the key parameter
     * @param clazz
     *            the class of the returning object
     * @return the value of a parameter
     */
    <T> T getObjectFromGeneralComponent(String key, Class<T> clazz);

    /**
     * Retrieves the list of enabled types of applicants
     * 
     * @return a list of strings representing type names
     */
    List<String> getApplicantTypes(String flowModeId);

    /**
     * Retrieves the list of enabled types of representatives
     *
     * @return a list of strings representing type names
     */
    List<String> getRepresentativeTypes(String flowModeId);

    /**
     * Retrieves the list of enabled types of representatives
     *
     * @return a list of strings representing type names
     */
    List<String> getPersonTypes(String flowModeId);

    /**
     * Checks if a service is enabled or not
     * 
     * @param service
     *            the service that we are interested
     * @return true if it is enabled
     */
    boolean isServiceEnabled(String service);

    /**
     * Gets a resource message using the messageKey provided
     * @param messageKey
     * @return
     */
    String getMessage(String messageKey);

    /**
     * Configuration if the security is enabled
     * 
     * @return true if the security is enabled
     */
    boolean isSecurityEnabled();

    /**
     * Used to clear the cache of configuration parameter values
     */
    void emptyCache();

    /**
     * Used to clear the cache of configuration parameter values
     * @param cacheName the cache that will be created
     */
    void emptyCache(String cacheName);

}
