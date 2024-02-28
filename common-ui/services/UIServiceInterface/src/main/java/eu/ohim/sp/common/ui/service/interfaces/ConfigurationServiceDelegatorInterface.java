/*******************************************************************************
 * * $Id:: ConfigurationServiceDelegatorInterface.java 109645 2013-03-25 10:50:3#$
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.service.interfaces;

import eu.ohim.sp.common.ui.form.application.SignatoryKindForm;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKind;
import eu.ohim.sp.common.ui.form.patent.PatentApplicationKind;
import eu.ohim.sp.common.ui.form.resources.AttachmentDocumentType;
import eu.ohim.sp.core.configuration.domain.changeType.xsd.DossierChangeTypes;
import eu.ohim.sp.core.configuration.domain.claims.xsd.Exhibitions.Exhibition;
import eu.ohim.sp.core.configuration.domain.claims.xsd.PriorityTypes.PriorityType;
import eu.ohim.sp.core.configuration.domain.claims.xsd.SeniorityNatures.SeniorityNature;
import eu.ohim.sp.core.configuration.domain.country.xsd.Countries;
import eu.ohim.sp.core.configuration.domain.country.xsd.Countries.Country;
import eu.ohim.sp.core.configuration.domain.country.xsd.Nationalities;
import eu.ohim.sp.core.configuration.domain.country.xsd.States;
import eu.ohim.sp.core.configuration.domain.design.xsd.DesignStatusList;
import eu.ohim.sp.core.configuration.domain.design.xsd.DesignViewPublicationSizes.DesignViewPublicationSize;
import eu.ohim.sp.core.configuration.domain.design.xsd.DesignViewTypes.DesignViewType;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.AbsoluteGrounds.AbsoluteGround;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.CategoriesTradeMark.CategoryTradeMark;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.EarlierEntitlementRightTypes.EarlierEntitlementRightType;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.EntitlementsOpponent.EntitlementOpponent;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RelativeGrounds.RelativeGround;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RevocationGrounds.RevocationGround;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.TypeRightEarlierTradeMarks.TypeRightEarlierTradeMark;
import eu.ohim.sp.core.configuration.domain.language.xsd.Languages.Language;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PayerTypes.PayerType;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PaymentMethods.PaymentMethod;
import eu.ohim.sp.core.configuration.domain.trademarkkind.xsd.TradeMarkKinds;
import eu.ohim.sp.core.configuration.domain.trademarkkind.xsd.TradeMarkStatusList;
import eu.ohim.sp.core.configuration.domain.trademarkkind.xsd.TradeMarkTypes;
import eu.ohim.sp.core.domain.design.KeyTextUIClass;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Delegator that is used to get properties from the system configuration
 *
 * @author karalch
 */
public interface ConfigurationServiceDelegatorInterface {

    /** The minimum char autocomplete locarno. */
    String MINIMUM_CHAR_AUTOCOMPLETE_LOCARNO="service.locarno.autocomplete.maxResults";

    /** The locarno term add max. */
    String LOCARNO_TERM_ADD_MAX = "locarno.add.maxNumber";

    /** The default locarno classes file. */
    String DEFAULT_LOCARNO_CLASSES_FILE = "locarno.classes.json";

    String DEFAULT_NICE_ALPHA_LISTS_FILE = "nice.alpha.lists.json";

    /** The design component. */
    String DESIGN_COMPONENT = "design";

    /** Max lenght of Text in Enter my Terms modal. */
    String LOCARNO_MANUAL_INDICATION_OF_PRODUCT_MAX_LENGTH ="design.locarno.manual.indication.of.product.max.lenght";

    /** The locarno term autocomplete maxresults. */
    String LOCARNO_TERM_AUTOCOMPLETE_MAXRESULTS="service.locarno.term.autocomplete.maxResults";


    /** Parameter name for the minimum character for enabling autocomplete */
    String MINIMUM_CHAR_AUTOCOMPLETE_TRADEMARK = "minimum.characters.autocomplete.trademark";

    // <editor-fold defaultstate="collapsed" desc="Person service parameters">

    /**
     * Person service parameters
     */
    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of applicant matches to return
     */
    String APPLICANT_MATCH_MAXRESULTS = "service.applicant.match.maxResults";

    String DESIGNER_MATCH_MAXRESULTS = "service.designer.match.maxResults";

    String INVENTOR_MATCH_MAXRESULTS = "service.inventor.match.maxResults";

    /************************* PERSONS MAX NUMBERS ****************************************************/

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of applicants to add
     */
    String APPLICANT_ADD_MAXNUMBER = "applicant.add.maxNumber";

    String APPLICANT_ADD_MAXNUMBER_GI_EFILING = "applicant.add.maxNumber.gi-efiling";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representatives to add
     */
    String REPRESENTATIVE_ADD_MAXNUMBER = "representative.add.maxNumber";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of personChange to add
     */
    String PERSONCHANGE_ADD_MAXNUMBER = "personChange.add.maxNumber";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of holders to add
     */
    String HOLDER_ADD_MAXNUMBER = "holder.add.maxNumber";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of assignees to add
     */
    String ASSIGNEE_ADD_MAXNUMBER = "assignee.add.maxNumber";
    
    String SIGNATURE_ADD_MAXNUMBER = "signature.add.maxNumber";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of applicants to add
     */
    String APPLICANT_ADD_MAXNUMBER_CHANGE = "applicant.add.maxNumber.tm-change";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representatives to add
     */
    String REPRESENTATIVE_ADD_MAXNUMBER_CHANGE = "representative.add.maxNumber.tm-change";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of personChange to add
     */
    String PERSONCHANGE_ADD_MAXNUMBER_CHANGE = "personChange.add.maxNumber.tm-change";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of holders to add
     */
    String HOLDER_ADD_MAXNUMBER_CHANGE = "holder.add.maxNumber.tm-change";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of assignees to add
     */
    String ASSIGNEE_ADD_MAXNUMBER_CHANGE = "assignee.add.maxNumber.tm-change";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of applicants to add
     */
    String APPLICANT_ADD_MAXNUMBER_TRANSFER = "applicant.add.maxNumber.tm-transfer";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representatives to add
     */
    String REPRESENTATIVE_ADD_MAXNUMBER_TRANSFER = "representative.add.maxNumber.tm-transfer";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of holders to add
     */
    String HOLDER_ADD_MAXNUMBER_TRANSFER = "holder.add.maxNumber.tm-transfer";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of assignees to add
     */
    String ASSIGNEE_ADD_MAXNUMBER_TRANSFER = "assignee.add.maxNumber.tm-transfer";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of applicants to add
     */
    String APPLICANT_ADD_MAXNUMBER_RENEWAL = "applicant.add.maxNumber.tm-renewal";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representatives to add
     */
    String REPRESENTATIVE_ADD_MAXNUMBER_RENEWAL = "representative.add.maxNumber.tm-renewal";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of holders to add
     */
    String HOLDER_ADD_MAXNUMBER_RENEWAL = "holder.add.maxNumber.tm-renewal";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of assignees to add
     */
    String ASSIGNEE_ADD_MAXNUMBER_RENEWAL = "assignee.add.maxNumber.tm-renewal";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of applicants to add
     */
    String APPLICANT_ADD_MAXNUMBER_OPPOSITION = "applicant.add.maxNumber.tm-opposition";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representatives to add
     */
    String REPRESENTATIVE_ADD_MAXNUMBER_OPPOSITION = "representative.add.maxNumber.tm-opposition";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of holders to add
     */
    String HOLDER_ADD_MAXNUMBER_OPPOSITION = "holder.add.maxNumber.tm-opposition";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of assignees to add
     */
    String ASSIGNEE_ADD_MAXNUMBER_OPPOSITION = "assignee.add.maxNumber.tm-opposition";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of applicants to add
     */
    String APPLICANT_ADD_MAXNUMBER_INVALIDITY = "applicant.add.maxNumber.tm-invalidity";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representatives to add
     */
    String REPRESENTATIVE_ADD_MAXNUMBER_INVALIDITY = "representative.add.maxNumber.tm-invalidity";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of holders to add
     */
    String HOLDER_ADD_MAXNUMBER_INVALIDITY = "holder.add.maxNumber.tm-invalidity";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of assignees to add
     */
    String ASSIGNEE_ADD_MAXNUMBER_INVALIDITY = "assignee.add.maxNumber.tm-invalidity";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of applicants to add
     */
    String APPLICANT_ADD_MAXNUMBER_REVOCATION = "applicant.add.maxNumber.tm-revocation";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representatives to add
     */
    String REPRESENTATIVE_ADD_MAXNUMBER_REVOCATION = "representative.add.maxNumber.tm-revocation";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of holders to add
     */
    String HOLDER_ADD_MAXNUMBER_REVOCATION = "holder.add.maxNumber.tm-revocation";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of assignees to add
     */
    String ASSIGNEE_ADD_MAXNUMBER_REVOCATION = "assignee.add.maxNumber.tm-revocation";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of applicants to add
     */
    String APPLICANT_ADD_MAXNUMBER_DSRENEWAL = "applicant.add.maxNumber.ds-renewal";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representatives to add
     */
    String REPRESENTATIVE_ADD_MAXNUMBER_DSRENEWAL = "representative.add.maxNumber.ds-renewal";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of holders to add
     */
    String HOLDER_ADD_MAXNUMBER_DSRENEWAL = "holder.add.maxNumber.ds-renewal";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of assignees to add
     */
    String ASSIGNEE_ADD_MAXNUMBER_DSRENEWAL = "assignee.add.maxNumber.ds-renewal";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of applicants to add
     */
    String APPLICANT_ADD_MAXNUMBER_DSCHANGE = "applicant.add.maxNumber.ds-change";


    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representatives to add
     */
    String REPRESENTATIVE_ADD_MAXNUMBER_DSCHANGE = "representative.add.maxNumber.ds-change";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of personChange to add
     */
    String PERSONCHANGE_ADD_MAXNUMBER_DSCHANGE = "personChange.add.maxNumber.ds-change";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of holders to add
     */
    String HOLDER_ADD_MAXNUMBER_DSCHANGE = "holder.add.maxNumber.ds-change";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of assignees to add
     */
    String ASSIGNEE_ADD_MAXNUMBER_DSCHANGE = "assignee.add.maxNumber.ds-change";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of applicants to add
     */
    String APPLICANT_ADD_MAXNUMBER_DSTRANSFER = "applicant.add.maxNumber.ds-transfer";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representatives to add
     */
    String REPRESENTATIVE_ADD_MAXNUMBER_DSTRANSFER = "representative.add.maxNumber.ds-transfer";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of holders to add
     */
    String HOLDER_ADD_MAXNUMBER_DSTRANSFER = "holder.add.maxNumber.ds-transfer";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of assignees to add
     */
    String ASSIGNEE_ADD_MAXNUMBER_DSTRANSFER = "assignee.add.maxNumber.ds-transfer";

/***************************************************************************************************************************************/
    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of application correspondence address to add
     */
    String CORRESPONDENCE_ADDRESS_ADD_MAXNUMBER = "applicationCA.add.maxNumber";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of applicants to return for the autocomplete functionality
     */
    String APPLICANT_AUTOCOMPLETE_MAXRESULTS = "service.applicant.autocomplete.maxResults";

    String DESIGNER_AUTOCOMPLETE_MAXRESULTS = "service.designer.autocomplete.maxResults";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representative matches to return
     */
    String REPRESENTATIVE_MATCH_MAXRESULTS = "service.representative.match.maxResults";

    String ADDRESS_AUTOCOMPLETE_MAXRESULTS = "service.address.autocomplete.maxResults";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representative matches to return
     */
    String ASSIGNEE_MATCH_MAXRESULTS = "service.assignee.match.maxResults";


    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of representative to return for the autocomplete functionality
     */
    String REPRESENTATIVE_AUTOCOMPLETE_MAXRESULTS = "service.representative.autocomplete.maxResults";

    String LATIN_AUTOCOMPLETE_MAXRESULTS = "service.latin.autocomplete.maxResults";

    /**
     * Parameters holding the configuration for validation message blocking
     */
    String TM_RENEWABLE_MSG_BLOCKING = "service.trademark.validation.renewable.blocking";

    String TM_OPPOSITION_PUBDATE_FUTURE_MSG_BLOCKING = "service.opposition.trademark.validation.pubdate.future.blocking";

    String TM_OPPOSITION_PUBDATE_OLDERTHAN_MSG_BLOCKING = "service.opposition.trademark.validation.pubdate.older.blocking";

    String TM_RENEWAL_STATUS_MSG_BLOCKING = "service.renewal.trademark.validation.status.blocking";

    String TM_TRANSFER_STATUS_MSG_BLOCKING = "service.transfer.trademark.validation.status.blocking";

    String TM_OPPOSITION_STATUS_MSG_BLOCKING = "service.opposition.trademark.validation.status.blocking";

    String TM_WITHDRAWAL_STATUS_MSG_BLOCKING = "service.withdrawal.trademark.validation.status.blocking";

    String TM_INVALIDITY_STATUS_MSG_BLOCKING = "service.invalidity.trademark.validation.status.blocking";

    String TM_REVOCATION_STATUS_MSG_BLOCKING = "service.revocation.trademark.validation.status.blocking";

    String TM_LICENCE_STATUS_MSG_BLOCKING = "service.licence.trademark.validation.status.blocking";

    String TM_APPEAL_STATUS_MSG_BLOCKING = "service.appeal.trademark.validation.status.blocking";

    String TM_SECURITY_STATUS_MSG_BLOCKING = "service.security.trademark.validation.status.blocking";

    String TM_BANKRUPTCY_STATUS_MSG_BLOCKING = "service.bankruptcy.trademark.validation.status.blocking";

    String TM_SURRENDER_STATUS_MSG_BLOCKING = "service.surrender.trademark.validation.status.blocking";

    String DS_RENEWABLE_MSG_BLOCKING = "service.design.validation.renewable.blocking";

    String DS_RENEWABLE_25_YEARS_MSG_BLOCKING = "service.design.validation.renewable.years.blocking";

    String TM_OPPOSABLE_MSG_BLOCKING = "service.trademark.validation.opposable.blocking";

    String TM_REM_STATUS_MSG_BLOCKING = "service.rem.trademark.validation.status.blocking";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of trademarks to add
     */
    String TM_ADD_MAXNUMBER = "tm.add.maxNumber";

    String DS_ADD_MAXNUMBER = "ds.add.maxNumber";

    String OB_ADD_MAXNUMBER = "oppositionBasics.maxNumber";

    String OBJB_ADD_MAXNUMBER = "objectionBasics.maxNumber";

    String REVOB_ADD_MAXNUMBER = "revocationBasics.maxNumber";

    String PATENT_ADD_MAXNUMBER = "patent.add.maxNumber";


    /**
     * The name of the person component in configuration
     */
    String PERSON_COMPONENT = "eu.ohim.sp.core.person";

	String GENERAL_COMPONENT = "general";

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

    String CLAIM_CONVERSION_ADD_MAXNUMBER = "claim.conversion.add.maxNumber";
    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of exhibition priorities to add
     */
    String CLAIM_EXHIBITION_ADD_MAXNUMBER = "claim.exhibition.add.maxNumber";

    String FOREIGN_REGISTRATION_ADD_MAXNUMBER = "foreign.registration.add.maxNumber";

    String CLAIM_PCT_ADD_MAXNUMBER = "claim.pct.add.maxNumber";

    String CLAIM_PARALLEL_APPLICATION_ADD_MAXNUMBER = "claim.parallelApplication.add.maxNumber";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of designers to add.
     */
    String DESIGNER_ADD_MAXNUMBER = "designer.add.maxNumber";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of designs to add.
     */
    String DESIGN_ADD_MAXNUMBER = "design.add.maxNumber";

    /**
     * Parameter holding the name of the configuration option containing the
     * maximum number of design views to add.
     */
    String DESIGN_VIEW_ADD_MAXNUMBER = "designView.add.maxNumber";

    String MARK_VIEW_ADD_MAXNUMBER = "markView.add.maxNumber";



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
    /** Parameter name for the trademark kind */
    String TMKIND_PARAM = "tmkind-config";
    /** Parameter name for the trademark TYPE */
    String TMTYPE_PARAM = "tmtype-config";
    /** Parameter name for the trademark status */
    String TMSTATUS_PARAM = "tmstatus-config";
    /** Parameter name for the design status */
    String DSSTATUS_PARAM = "dsstatus-config";
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
    /** Parameter name for the publication sizes of a design view */
    String DESIGN_VIEW_PUBLICATION_SIZES_PARAM = "designViewPublicationSizes";
    /** Parameter name for the types of a design view */
    String DESIGN_VIEW_TYPES_PARAM = "designViewTypes";
    /** Parameter name for the types of a priority */
    String PRIORITY_TYPES_PARAM = "priorityTypes";
    /** Parameter name for the available legalActVersions */
    String LEGAL_ACT_VERSIONS_PARAM = "legalActVersion-config";
    /** Parameter name for the available absoluteGrounds */
    String ABSOLUTE_GROUNDS_PARAM = "absoluteGround-config";
    /** Parameter name for the available relativeGrounds */
    String RELATIVE_GROUNDS_PARAM = "relativeGround-config";
    /** Parameter name for the available absoluteGrounds */
    String REVOCATION_GROUNDS_PARAM = "revocationGround-config";
    /** Parameter name for the available earlier entitlement right type */
    String EARLIER_ENTITLEMENT_RIGHT_TYPES_PARAM = "earlierEntitlementRightType-config";
    /** Parameter name for the available type right of earlier trademark */
    String TYPE_RIGHT_EARLIER_TRADE_MARK_PARAM = "typeRightEarlierTradeMark-config";
    /** Parameter name for the available type of dossier change */
    String DOSSIER_CHANGE_TYPE_PARAM = "dossierChangeType-config";
    /** Parameter name for the available category trademark */
    String CATEGORY_TRADE_MARK_PARAM = "categoryTradeMark-config";

    String ENTITLEMENT_OPPONENT = "entitlementOpponent-config";

    String LICENCE_ADD_MAXNUMBER = "licence.add.maxNumber";

    String APPEAL_ADD_MAXNUMBER = "appeal.add.maxNumber";

    String INVENTOR_ADD_MAXNUMBER = "inventor.add.maxNumber";
    String PATENT_VIEW_ADD_MAXNUMBER = "patentView.add.maxNumber";
    String CLAIM_DIVISIONAL_APPLICATION_ADD_MAXNUMBER = "claim.divisionalApplication.add.maxNumber";


    /** Parameter name for the correspondence address types */
    String CORRESPONDENCE_ADDRESS_TYPE_PARAM = "correspondence-address-types";

    String RESET_APPLICANT_IMPORTED_KEY = "applicant.resetImportedEnabled";
    String RESET_REPRESENTATIVE_IMPORTED_KEY = "representative.resetImportedEnabled";
    String RESET_ASSIGNEE_IMPORTED_KEY = "assignee.resetImportedEnabled";
    String RESET_DESIGNER_IMPORTED_KEY = "designer.resetImportedEnabled";
    String RESET_INVENTOR_IMPORTED_KEY = "inventor.resetImportedEnabled";

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

    /**
     * Retrieves the list of available countries
     *
     * @return an object type Countries containing the list of available countries
     */
    Countries getCountriesObject();

    List<TradeMarkKinds.TradeMarkKind> getTradeMarkKinds();

    List<DossierChangeTypes.DossierChangeType> getDossierChangeTypes();

    List<TradeMarkTypes.TradeMarkType> getTradeMarkTypes();

    List<TradeMarkStatusList.TradeMarkStatus> getTradeMarkStatusList();

    List<DesignStatusList.DesignStatus> getDesignStatusList();

    Double getDocumentSize(String provisionalId, String filename);

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
     * Retrieves the list of available legalActVersions
     *
     * @return an object containing the list of available legalActVersions
     */
    public List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion> getLegalActVersions(GroundCategoryKind groundsCategory, List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion> legalActVersions);

    /**
     * Retrieves the list of available absoluteGrounds
     *
     * @return an object containing the list of available absoluteGrounds
     */
    List<AbsoluteGround> getAbsoluteGrounds();

    /**
     * Retrieves the list of available relativeGrounds
     *
     * @return an object containing the list of available relativeGrounds
     */
    List<RelativeGround> getRelativeGrounds();

    /**
     * Retrieves the list of available revocationGrounds
     *
     * @return an object containing the list of available revocationGrounds
     */
    List<RevocationGround> getRevocationGrounds();

    /**
     * Retrieves the list of available earlier entitlement right types
     *
     * @return an object containing the list of available entitlement right types
     */
    List<EarlierEntitlementRightType> getEarlierEntitlementRightTypes();

    /**
     * Retrieves the list of available types of rights for trademark
     *
     * @return an object containing the list of available types of rights for trademark
     */
    List<TypeRightEarlierTradeMark> getTypeRightEarlierTradeMarks();

    /**
     * Retrieves the list of available categories for trademark
     *
     * @return an object containing the list of available categories for trademark
     */
    List<CategoryTradeMark> getCategoriesTradeMark();

    /**
     * Retrieves the list of available entitlements of opponent
     *
     * @return an object containing the list of available entitlements of opponent
     */
    List<EntitlementOpponent> getEntitlementsOpponent();

    /**
     * Retrieves the list of seniority natures
     *
     * @return an object of the seniority natures mapping
     */
    List<SeniorityNature> getSeniorityNatures();
    
    
    /**
     * Retrieves the list of seniority natures
     * 
     * @param module the module
     * 
     * @return an object of the seniority natures mapping
     */
    List<SeniorityNature> getSeniorityNaturesByModule(String module);

    /**
     * Retrieves the list of exhibitions
     *
     * @return an object of the exhibitions mapping
     */
    List<Exhibition> getExhibitions();

    /**
     * Retrieves the list of exhibitions
     *
     * @param module the module
     *
     * @return an object of the exhibitions mapping
     */
    List<Exhibition> getExhibitionsByModule(String module);
    
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
     * Retrieves the object of a parameter configured on system configuration
     *
     * @param <T>
     * @param key
     *            the key parameter
     * @param component
     * 			  the component
     * @param clazz
     *            the class of the returning object
     * @return the value of a parameter
     */
    <T> T getObjectFromComponent(String key, String component, Class<T> clazz);
    
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
     * Retrieves the list of enabled types of holders
     *
     * @return a list of strings representing type names
     */
    List<String> getHolderTypes(String flowModeId);

    /**
     * Retrieves the list of enabled types of bankruptcy assignees
     *
     * @return a list of strings representing type names
     */
    List<String> getAssigneeBankruptcyTypes(String flowModeId);

    /**
     * Retrieves the list of enabled types of security measure assignees
     *
     * @return a list of strings representing type names
     */
    List<String> getAssigneeSecurityTypes(String flowModeId);

    /**
     * Retrieves the list of enabled types of assignee
     *
     * @return a list of strings representing type names
     */
    List<String> getAssigneeTypes(String flowModeId);

    /**
     * Retrieves the list of enabled types of rem creditor
     *
     * @return a list of strings representing type names
     */
    List<String> getRemCreditorTypes(String flowModeId);

    /**
     * Retrieves the list of enabled types of licensees
     *
     * @return a list of strings representing type names
     */
    List<String> getLicenseeTypes(String flowModeId);

    /**
     * Retrieves the list of enabled types of representatives
     *
     * @return a list of strings representing type names
     */
    List<String> getPersonTypes(String flowModeId);

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
     */
    void emptyCacheEServices();

    /**
     * Used to clear the cache of configuration parameter values
     * @param cacheName the cache that will be created
     */
    void emptyCache(String cacheName);

    /**
     * Checks if a service is enabled or not
     *
     * @param service
     *            the service that we are interested
     * @return true if it is enabled
     */
    public boolean isServiceEnabled(String service);

    /**
     * Checks if a service is enabled or not.
     *
     * @param service            the service that we are interested
     * @param flowModeId         the flowModeId that we are interested
     * @return true if it is enabled
     */
    boolean isServiceEnabledByFlowMode(String service, String flowModeId);

    /**
     * Checks if a service is enabled or not in a specific component
     *
     * @param service the service that we are interested
     * @param component the component tha we are interested
     * @return true if it is enabled
     */
    public boolean isServiceEnabled(String service, String component);

    /**
     * Retrieves the list of publication sizes for a design view.
     *
     * @return an object of the publication sizes mapping
     */
    List<DesignViewPublicationSize> getDesignViewPublicationSizes();

    /**
     * Retrieves the list of types for a design view.
     *
     * @return an object of the types mapping
     */
    List<DesignViewType> getDesignViewTypes();

    /**
     * Retrieves the list of priority types.
     *
     * @return an object of the types mapping
     */
    List<PriorityType> getPriorityTypes();

    public String getOffice();

    /**
     * Retrives the national office language
     */
    public String getLanguageOffice();

    public int getUserNaturalPersonCount(String flowModeId);

    public int getUserLegalEntityCount(String flowModeId);

    public int getUserRepresentativeNaturalPersonCount(String flowModeId);

    public int getUserRepresentativeAssociationCount(String flowModeId);

    public List<SignatoryKindForm> getSignatoryKinds(String flowModeId);

    List<String> getTransformationTypes(String flowModeId);

    List<String> getParallelApplicationTypes(String flowModeId);

    List<AttachmentDocumentType> getAttachmentTypeForFlow(String flowModeId, String configSufix, String changeType);

    List<String> getMandatoryAttachmentTypeForFlow(String flowModeId, String configSufix, String changeType);

    /**
     * Gets the default locarno classes.
     *
     * @param language the language
     * @return the default locarno classes
     */
    List<KeyTextUIClass> getDefaultLocarnoClasses(String language);

    /**
     * Gets the default locarno subclasses.
     *
     * @param selectedClass the selected class
     * @param language the language
     * @param version the version
     * @return the default locarno subclasses
     */
    List<KeyTextUIClass> getDefaultLocarnoSubclasses(Integer selectedClass, String language, String version);

    List<PatentApplicationKind> getPatentApplicationKinds(String flowModeId);

    String getApplicationManagementUrl(String module, String key, String flowModeId);

    List<TradeMarkTypes.TradeMarkType> getGiTypes();

    String getPatentSearchExternalUrl(String flowModeId);

    Map<String, Set<String>> getDefaultNiceAlphaLists(String language);

    boolean hasSelectPersonButton(String flowModeId, String sectionId);
}
