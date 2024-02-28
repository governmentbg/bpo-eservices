package eu.ohim.sp.external.classification.inside;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.classification.NiceClassificationService;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.classification.wrapper.*;
import eu.ohim.sp.external.classification.NiceClassificationClientInside;
import eu.ohim.sp.external.domain.classification.wrapper.DistributionResult;
import eu.ohim.sp.external.injectors.NiceClassInjector;
import eu.ohim.sp.external.utils.AdapterEnabled;
import eu.ohim.sp.external.utils.AdapterSetup;
import eu.ohim.sp.integration.domain.smooks.FreeFormatString;
import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is the router that connects to the JBoss ESB adapter for
 * classification. It is called from the fsp core service. It connect to the esb
 * using the jboss-remoting library. In this case the url to connect is passed
 * by system properties. It contains all the dependencies to the esb.
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent @NiceClassificationClientInside
public class NiceClassificationClientBean implements NiceClassificationService {
    private static final String termSeparator = System.getProperty("gs.terms.seperator");
    /**
     * The system configuration service.
     */
	@EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private Mapper mapper;

    /** injectors **/
    private NiceClassInjector niceClassInjector;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        mapper = new DozerBeanMapper();
        niceClassInjector = new NiceClassInjector();
    }

    /*
     * (non-Javadoc)
     *
     * @see eu.ohim.sp.external.adapters.classification.
     * NiceClassificationService#searchTerm(eu.ohim.sp.core
     * .domain.classification.SearchTermCriteria)
     */
    @Override
    @Interceptors({AdapterSetup.Nice.class, AdapterEnabled.class})
    public SearchResults searchTerm(SearchTermCriteria searchTermCriteria) {
        SearchResults results = null;
        eu.ohim.sp.external.domain.classification.wrapper.SearchResults externalResult;
        String page = searchTermCriteria.getPage()!=null?String.valueOf(searchTermCriteria.getPage()):null;
        String size = searchTermCriteria.getSize()!=null?String.valueOf(searchTermCriteria.getSize()):null;
        externalResult = niceClassInjector.getSearchTerms(searchTermCriteria.getOffice(),
                searchTermCriteria.getLanguage(),
                searchTermCriteria.getTerm(),
                searchTermCriteria.getTaxoConceptNodeId(),
                page,
                size,
                searchTermCriteria.getSortBy()!=null?searchTermCriteria.getSortBy().toString():null,
                searchTermCriteria.getOrderBy() != null ? searchTermCriteria.getOrderBy().value() : null,
                searchTermCriteria.getSources());
        if (externalResult != null) {
            results = mapper.map(externalResult,
                    SearchResults.class);

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
    @Interceptors({AdapterSetup.Nice.class, AdapterEnabled.class})
    public List<TermsValidated> verifyListOfTerms(List<TermsToBeValidated> terms) {
        List<TermsValidated> results = null;
        TermsValidated validated = new TermsValidated();
        if(terms != null && !terms.isEmpty()) {
            validated.setLanguage(terms.get(0).getLanguage());
            validated.setNiceClass(terms.get(0).getNiceClass());
            List<eu.ohim.sp.external.domain.classification.wrapper.Term> externalResult;
            externalResult = niceClassInjector.getVerifyTerms(terms.get(0).getOffice(),
                    terms.get(0).getLanguage(),
                    terms.get(0).getTerms(),
                    terms.get(0).getNiceClass());
            if (externalResult != null) {
                results = new ArrayList<TermsValidated>();
                ArrayList<Term> valid_terms = new ArrayList<>();
                for (eu.ohim.sp.external.domain.classification.wrapper.Term result : externalResult) {
                    valid_terms.add((mapper.map(result, Term.class)));
                }
                validated.setTerms(valid_terms);
                results.add(validated);
            }
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
    @Interceptors({AdapterSetup.Nice.class, AdapterEnabled.class})
    public Collection<TaxonomyConceptNode> getTaxonomy(
            TaxonomyCriteria taxononyCriteria) {
        Collection<eu.ohim.sp.external.domain.classification.wrapper.TaxonomyConceptNode> externalResult;
        String taxnonomyLimit = taxononyCriteria.getLevelLimit()!=null?String.valueOf(taxononyCriteria.getLevelLimit()):null;
        externalResult = niceClassInjector.getTaxonomy(taxononyCriteria.getOffice(),
                taxononyCriteria.getLanguage(),
                taxononyCriteria.getTerm(),
                taxnonomyLimit,
                taxononyCriteria.getTaxoConceptNodeId());
        Collection<TaxonomyConceptNode> results = new ArrayList<TaxonomyConceptNode>();
        if (externalResult != null) {
            for (eu.ohim.sp.external.domain.classification.wrapper.TaxonomyConceptNode result : externalResult) {
                results.add(mapper.map(result,
                        TaxonomyConceptNode.class));
            }
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
    @Interceptors({AdapterSetup.Nice.class, AdapterEnabled.class})
    public Collection<Term> getNiceClassHeading(String niceClassHeading,
                                                String language) {
        Collection<Term> results = null;
        Collection<eu.ohim.sp.external.domain.classification.wrapper.Term> externalResult;
        externalResult = niceClassInjector.getHeadings(language, niceClassHeading);
        if (externalResult != null) {
            externalResult = transformNiceClassHeading(externalResult);
            results = new ArrayList<>();
            for (eu.ohim.sp.external.domain.classification.wrapper.Term result : externalResult) {
                results.add(mapper.map(result, Term.class));
            }
        }
        return results;
    }
    private Collection<eu.ohim.sp.external.domain.classification.wrapper.Term> transformNiceClassHeading(Collection<eu.ohim.sp.external.domain.classification.wrapper.Term> terms) {
        return terms
                .stream()
                .map(term -> Arrays.stream(term.getText().split(termSeparator))
                        .map(t -> t.trim()).filter(t -> !t.isEmpty())
                        .map(t -> createTermFromText(term, t)))
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }
    private eu.ohim.sp.external.domain.classification.wrapper.Term createTermFromText(eu.ohim.sp.external.domain.classification.wrapper.Term originalTerm, String text) {
        eu.ohim.sp.external.domain.classification.wrapper.Term toSave = new eu.ohim.sp.external.domain.classification.wrapper.Term();
        toSave.setText(text);
        toSave.setLang(originalTerm.getLang());
        toSave.setNiceClass(originalTerm.getNiceClass());
        return toSave;
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
    @Interceptors({AdapterSetup.Nice.class, AdapterEnabled.class})
    public DistributionResults getTermDistribution(
            DistributionCriteria distributionCriteria) {
        DistributionResults distributionResults = new DistributionResults();
        eu.ohim.sp.external.domain.classification.wrapper.DistributionResults externalResult;
        externalResult = niceClassInjector.getDistribution(
                distributionCriteria.getOffice(),
                distributionCriteria.getLanguage(),
                distributionCriteria.getTerm(),
                "{"+StringUtils.replaceChars(distributionCriteria.getNiceClassList().toString(), "[]", "")+"}"
        );
        if (externalResult != null) {
            distributionResults.setClassificationTerms(new ArrayList<ClassificationTerm>());
            for (DistributionResult distributionResult : externalResult.getDistributionResult()) {
                distributionResults.getClassificationTerms().add(mapper.map(distributionResult, ClassificationTerm.class));
            }
        }
        return distributionResults;
    }

    @Override
    @Interceptors({AdapterSetup.Nice.class, AdapterEnabled.class})
	public List<String> getAutocomplete(String language, String searchCriteria) {
		List<FreeFormatString> res = niceClassInjector.getAutocomplete(language, searchCriteria);
        List<String> results = res.stream().map(e -> e.getValue()).collect(Collectors.toList());
        return results;
    }

    @Override
    @Interceptors({AdapterSetup.Nice.class, AdapterEnabled.class})
    public Collection<ClassScope> getClassScopes(String language,
                                                 String niceClassHeadings) {
        Collection<ClassScope> results = null;
        Collection<eu.ohim.sp.external.domain.classification.wrapper.ClassScope> externalResult;
        externalResult = niceClassInjector.getClassScopes(language, niceClassHeadings);
        if (externalResult != null) {
            results = new ArrayList<ClassScope>();
            for (eu.ohim.sp.external.domain.classification.wrapper.ClassScope result : externalResult) {
                results.add(mapper.map(result, ClassScope.class));
            }
        }
        return results;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * eu.ohim.sp.external.adaptors.AbstractWSClient#getSystemConfigurationService
     * ()
     */
    @Interceptors({AdapterSetup.Nice.class, AdapterEnabled.class})
    public ConfigurationService getConfigurationService() {
        return configurationService;
    }
}