package eu.ohim.sp.external.classification.outside;

import eu.ohim.sp.core.classification.NiceClassificationService;
import eu.ohim.sp.core.domain.classification.wrapper.ClassScope;
import eu.ohim.sp.core.domain.classification.wrapper.DistributionCriteria;
import eu.ohim.sp.core.domain.classification.wrapper.DistributionResults;
import eu.ohim.sp.core.domain.classification.wrapper.SearchResults;
import eu.ohim.sp.core.domain.classification.wrapper.SearchTermCriteria;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyConceptNode;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyCriteria;
import eu.ohim.sp.core.domain.classification.wrapper.Term;
import eu.ohim.sp.core.domain.classification.wrapper.TermsToBeValidated;
import eu.ohim.sp.core.domain.classification.wrapper.TermsValidated;
import eu.ohim.sp.external.classification.NiceClassificationClientOutside;
import eu.ohim.sp.external.classification.outside.ws.client.NiceClassificationWSClient;
import eu.ohim.sp.external.classification.outside.ws.client.NiceClassificationWSClientService;
import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.classification.wrapper.*;
import eu.ohim.sp.external.ws.exception.NiceClassificationFaultException;
import eu.ohim.sp.external.utils.AbstractWSClient;
import eu.ohim.sp.external.domain.classification.wrapper.DistributionResult;
import eu.ohim.sp.external.domain.classification.wrapper.*;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is the router that connects to the JBoss ESB adapter for
 * classification. It is called from the fsp core service. It connect to the esb
 * using the jboss-remoting library. In this case the url to connect is passed
 * by system properties. It contains all the dependencies to the esb.
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent @NiceClassificationClientOutside
public class NiceClassificationClientBean extends AbstractWSClient implements NiceClassificationService {

    /**
     * The Constant ADAPTER_NAME.
     */
    protected static final String ADAPTER_NAME = "classification";

    /**
     * The Constant logger.
     */
    private static final Logger LOGGER = Logger.getLogger(NiceClassificationClientBean.class);

    /**
     * The system configuration service.
     */
	@EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private Mapper mapper;

    /**
     * Actual client to the web service
     */
    private NiceClassificationWSClient webServiceRef;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        super.init(ADAPTER_NAME);
        mapper = new DozerBeanMapper();

        if (getAdapterEnabled()) {
            webServiceRef = new NiceClassificationWSClientService(
                    getWsdlLocation()).getManageClassificationPort();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see eu.ohim.sp.external.adapters.classification.
     * NiceClassificationService#searchTerm(eu.ohim.sp.core
     * .domain.classification.SearchTermCriteria)
     */
    @Override
    public SearchResults searchTerm(SearchTermCriteria searchTermCriteria) {
        SearchResults results = null;
        if (getAdapterEnabled()) {
            eu.ohim.sp.external.domain.classification.wrapper.SearchTermCriteria externalObject =
                    mapper.map(searchTermCriteria, eu.ohim.sp.external.domain.classification.wrapper.SearchTermCriteria.class);

            if (externalObject != null) {
                eu.ohim.sp.external.domain.classification.wrapper.SearchResults externalResult = null;
                try {
                    externalResult = webServiceRef.searchTerm(externalObject);
                } catch (NiceClassificationFaultException exc) {
                    LOGGER.error(" searchTermCriteria ERROR WS SOAP: "
                            + exc.getMessage());
                }
                if (externalResult != null) {
                    results = mapper.map(externalResult,
                            eu.ohim.sp.core.domain.classification.wrapper.SearchResults.class);

                }
            } else {
                LOGGER.error("Search Terms Adapter Service. Can not be converted core domain object to external domain object");
            }

        } else {
            results = new SearchResults();
        }

        return results;
    }

    /*
     * (non-Javadoc)
     *
     * @see eu.ohim.sp.external.adapters.classification.
     * NiceClassificationService#verifyListOfTerms(eu.ohim.
     * fsp.core.domain.classification.TermsToBeValidated)
     */
    @Override
    public List<TermsValidated> verifyListOfTerms(List<TermsToBeValidated> terms) {
        List<TermsValidated> results = null;
        if (getAdapterEnabled()) {
            List<eu.ohim.sp.external.domain.classification.wrapper.TermsToBeValidated> externalObject = new ArrayList<eu.ohim.sp.external.domain.classification.wrapper.TermsToBeValidated>();

            for (TermsToBeValidated term : terms) {
                externalObject.add(mapper.map(term, eu.ohim.sp.external.domain.classification.wrapper.TermsToBeValidated.class));
            }
            if (!externalObject.isEmpty()) {
                List<eu.ohim.sp.external.domain.classification.wrapper.TermsValidated> externalResult = null;
                try {
                    externalResult = webServiceRef
                            .verifyListOfTerms(externalObject);
                } catch (NiceClassificationFaultException exc) {
                    LOGGER.error(" verifyListOfTerms ERROR WS SOAP: "
                            + exc.getMessage());
                }
                if (externalResult != null) {
                    results = new ArrayList<TermsValidated>();
                    for (eu.ohim.sp.external.domain.classification.wrapper.TermsValidated result : externalResult) {
                        results.add(mapper.map(result, eu.ohim.sp.core.domain.classification.wrapper.TermsValidated.class));
                    }
                }
            } else {
                LOGGER.error("Search Terms Adapter Service. Can not be converted core domain object to external domain object");
            }
        } else {
            results = new ArrayList<TermsValidated>();
        }
        return results;
    }

    /*
     * (non-Javadoc)
     *
     * @see eu.ohim.sp.external.adapters.classification.
     * NiceClassificationService#getTaxonomy(eu.ohim.sp.core
     * .domain.classification.TaxonomyCriteria)
     */
    @Override
    public Collection<TaxonomyConceptNode> getTaxonomy(
            TaxonomyCriteria taxononyCriteria) {
        Collection<TaxonomyConceptNode> results = null;
        if (getAdapterEnabled()) {
            eu.ohim.sp.external.domain.classification.wrapper.TaxonomyCriteria externalObject = mapper.map(taxononyCriteria,
                    eu.ohim.sp.external.domain.classification.wrapper.TaxonomyCriteria.class);

            if (externalObject != null) {
                Collection<eu.ohim.sp.external.domain.classification.wrapper.TaxonomyConceptNode> externalResult = null;
                try {
                    externalResult = webServiceRef.getTaxonomy(externalObject);
                } catch (NiceClassificationFaultException exc) {
                    LOGGER.error(" getTaxonomy ERROR WS SOAP: "
                            + exc.getMessage());
                }
                if (externalResult != null) {

                    results = new ArrayList<TaxonomyConceptNode>();
                    for (eu.ohim.sp.external.domain.classification.wrapper.TaxonomyConceptNode result : externalResult) {
                        results.add(mapper.map(result,
                                eu.ohim.sp.core.domain.classification.wrapper.TaxonomyConceptNode.class));
                    }
                }
            } else {
                LOGGER.error("Search Terms Adapter Service. Can not be converted core domain object to external domain object");
            }
        } else {
            results = new ArrayList<TaxonomyConceptNode>();
        }
        return results;
    }

    /*
     * (non-Javadoc)
     *
     * @see eu.ohim.sp.external.adapters.classification.
     * NiceClassificationService#getNiceClassHeading(java.lang
     * .String, java.lang.String)
     */
    @Override
    public Collection<Term> getNiceClassHeading(String niceClassHeading,
                                                String language) {
        Collection<Term> results = null;
        if (getAdapterEnabled()) {
            NiceHeadingCriteria externalObject = new NiceHeadingCriteria(
                    language, niceClassHeading);
            Collection<eu.ohim.sp.external.domain.classification.wrapper.Term> externalResult = null;
            try {
                externalResult = webServiceRef
                        .getNiceClassHeading(externalObject);
            } catch (NiceClassificationFaultException exc) {
                LOGGER.error(" getNiceClassHeading ERROR WS SOAP: "
                        + exc.getMessage());
            }
            if (externalResult != null) {
                results = new ArrayList<Term>();
                for (eu.ohim.sp.external.domain.classification.wrapper.Term result : externalResult) {
                    results.add(mapper.map(result, eu.ohim.sp.core.domain.classification.wrapper.Term.class));
                }
            }
        } else {
            results = new ArrayList<Term>();

            Term term = new Term();
            term.setLang(language);
            term.setNiceClass(Integer.valueOf(niceClassHeading));
            term.setText("Boots");
            results.add(term);

        }

        return results;
    }

    /*
     * (non-Javadoc)
     *
     * @see eu.ohim.sp.external.adapters.classification.
     * NiceClassificationService
     * #getDistribution(eu.ohim.sp.core.domain
     * .classification.DistributionResults)
     */
    @Override
    public DistributionResults getTermDistribution(
            DistributionCriteria distributionCriteria) {
        DistributionResults distributionResults = new DistributionResults();
        if (getAdapterEnabled()) {
            eu.ohim.sp.external.domain.classification.wrapper.DistributionCriteria externalObject = null;

            externalObject = mapper.map(distributionCriteria, eu.ohim.sp.external.domain.classification.wrapper.DistributionCriteria.class);

            if (externalObject != null) {
                eu.ohim.sp.external.domain.classification.wrapper.DistributionResults externalResult = null;
                try {
                    externalResult = webServiceRef
                            .getDistribution(externalObject);
                } catch (NiceClassificationFaultException exc) {
                    LOGGER.error(" getDistribution ERROR WS SOAP: "
                            + exc.getMessage());
                }
                if (externalResult != null
                        && externalResult.getDistributionResult()!=null) {
                    distributionResults.setClassificationTerms(new ArrayList<ClassificationTerm>());
                    for (DistributionResult distributionResult : externalResult.getDistributionResult()) {
                        distributionResults.getClassificationTerms().add(mapper.map(distributionResult, ClassificationTerm.class));
                    }
                }

            }
        }

        return distributionResults;
    }

	public List<String> getAutocomplete(String language, String searchCriteria) {
        List<String> results = new ArrayList<String>();
        if (getAdapterEnabled()) {
            DidYouMeanCriteria externalObject = new DidYouMeanCriteria(
                    language, searchCriteria);
            try {
				results = webServiceRef.getAutocomplete(externalObject);
            } catch (NiceClassificationFaultException exc) {
				LOGGER.error(" getAutocomplete ERROR WS SOAP: " + exc.getMessage());
            }
        }
        return results;
    }

    public Collection<ClassScope> getClassScopes(String language,
                                                 String niceClassHeadings) {
        Collection<ClassScope> results = null;
        if (getAdapterEnabled()) {
            ClassScopesCriteria externalObject = new ClassScopesCriteria(
                    language, niceClassHeadings);
            Collection<eu.ohim.sp.external.domain.classification.wrapper.ClassScope> externalResult = null;
            try {
                externalResult = webServiceRef.getClassScopes(externalObject);
            } catch (NiceClassificationFaultException exc) {
                LOGGER.error(" getClassScopes ERROR WS SOAP: "
                        + exc.getMessage());
            }
            if (externalResult != null) {
                results = new ArrayList<ClassScope>();
                for (eu.ohim.sp.external.domain.classification.wrapper.ClassScope result : externalResult) {
                    results.add(mapper.map(result, ClassScope.class));
                }
            }
        }
        return results;
    }

    /*
     * (non-Javadoc)
     *
     * @see eu.ohim.sp.external.adaptors.AbstractWSClient#getErrorCode()
     */
    @Override
    protected String getErrorCode() {
        return "error.generic";
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * eu.ohim.sp.external.adaptors.AbstractWSClient#getSystemConfigurationService
     * ()
     */
    public ConfigurationService getConfigurationService() {
        return configurationService;
    }

}