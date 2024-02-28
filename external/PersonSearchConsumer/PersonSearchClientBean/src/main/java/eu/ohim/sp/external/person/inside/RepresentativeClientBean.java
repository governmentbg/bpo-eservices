package eu.ohim.sp.external.person.inside;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.external.injectors.PersonInjector;
import eu.ohim.sp.external.person.PersonSearchClientInside;
import eu.ohim.sp.external.person.RepresentativeClient;
import eu.ohim.sp.external.utils.AdapterEnabled;
import eu.ohim.sp.external.utils.AdapterSetup;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the router that connects to the JBoss ESB adapter for representative search/get.
 * It is called from the fsp core service.
 * Can be connecting to tmview or esearch depends on the environment
 * It connect to the esb using the jboss-remoting library. In this case the url to connect is passed by system
 * properties.
 * It contains all the dependencies to the esb.
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent @PersonSearchClientInside
public class RepresentativeClientBean implements RepresentativeClient {

    /** The Constant REPRESENTATIVE_ADAPTER_NAME. */
    public static final String ADAPTER_NAME = "representative";

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(RepresentativeClientBean.class);

    /**
     * The system configuration service.
     */
	@EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;


    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private DozerBeanMapper mapper;

    /**
     * person injector
     */
    private PersonInjector personInjector;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        mapper = new DozerBeanMapper();
        personInjector = new PersonInjector();
    }

    /*
     * (non-Javadoc)
     * @see
     * eu.ohim.sp.external.adapters.person.RepresentativeClientServiceInterface#getRepresentativeAutocomplete(java
     * .lang.String, int)
     */
    @Override
    @Interceptors({AdapterSetup.Representative.class, AdapterEnabled.class})
    public String getRepresentativeAutocomplete(String module, String office, String text, int numberOfRows) {
        String results = null;
        results = personInjector.getRepresentativeAutocomplete(module, office, text, String.valueOf(numberOfRows));
        return results;
    }

    /*
     * (non-Javadoc)
     * @see
     * eu.ohim.sp.external.adapters.person.RepresentativeClientServiceInterface#searchRepresentative(java.lang.String,
     * java.lang.String, java.lang.String, int)
     */
    @Override
    @Interceptors({AdapterSetup.Representative.class, AdapterEnabled.class})
    public List<Representative> searchRepresentative(String module, String office, String representativeId, String representativeName,
                                                     String representativeNationality, int numberOfResults) {
        List<Representative> results = new ArrayList<Representative>();

        List<eu.ohim.sp.external.domain.person.Representative> externalResult = null;
        externalResult = personInjector.searchRepresentative(
                    module, office, representativeId, representativeName, representativeNationality, String.valueOf(numberOfResults));
        if (externalResult != null) {
            for (eu.ohim.sp.external.domain.person.Representative externalRepresentative : externalResult) {
                results.add(mapper.map(externalRepresentative, Representative.class));
            }
        }
        return results;
    }

    /*
     * (non-Javadoc)
     * @see
     * eu.ohim.sp.external.adapters.person.RepresentativeClientServiceInterface#getRepresentative(java.lang.String,
     * java.lang.String)
     */
    @Override
    @Interceptors({AdapterSetup.Representative.class, AdapterEnabled.class})
    public Representative getRepresentative(String module, String office, String representativeId) {
        Representative result = null;
        eu.ohim.sp.external.domain.person.Representative externalResult = null;
        externalResult = personInjector.getRepresentative(module, office,
                        representativeId);
        result = externalResult!=null ? mapper.map(externalResult, Representative.class) : null;
        return result;
    }

    /*
     * (non-Javadoc)
     * @see
     * eu.ohim.sp.external.adapters.person.RepresentativeClientServiceInterface#matchRepresentative(eu.ohim.sp.core
     * .domain.Representative, int)
     */
    @Override
    @Interceptors({AdapterSetup.Representative.class, AdapterEnabled.class})
    public List<Representative> matchRepresentative(String module, String office, Representative representative, int numberOfResults) {
        List<Representative> result = new ArrayList<Representative>();
        eu.ohim.sp.external.domain.person.Representative extRepresentative =
                    mapper.map(representative, eu.ohim.sp.external.domain.person.Representative.class);
        if (extRepresentative != null) {
            List<eu.ohim.sp.external.domain.person.Representative> externalResult = null;
            externalResult = personInjector.matchRepresentative(module, office, extRepresentative, String.valueOf(numberOfResults));
            if (externalResult != null) {
                for (eu.ohim.sp.external.domain.person.Representative matchedRepresentative : externalResult) {
                    result.add(mapper.map(matchedRepresentative, Representative.class));
                }
            }
        } else {
            LOGGER.error("searchApplicant external to core: Error converting input applicant to external domain");
        }
        return result;
    }

    /**
     * Makes the call to the client to save a representative.
     *
     * @param office
     *            the office code
     * @param user
     *            the user
     * @param representative
     *            the representative to save
     * @return the result of the call
     */
    @Override
    @Interceptors({AdapterSetup.Representative.class, AdapterEnabled.class})
    public Result saveRepresentative(String module, String office, String user,
                                           Representative representative) {
        Result results = null;
        eu.ohim.sp.external.domain.person.Representative externalObject =
                    mapper.map(representative, eu.ohim.sp.external.domain.person.Representative.class);
        if (externalObject != null) {
            eu.ohim.sp.external.domain.common.Result externalResult = null;
            externalResult = personInjector.saveRepresentative(module, office, user, externalObject);
            if (externalResult != null) {
                results = mapper.map(externalResult, Result.class);
            } else {
                LOGGER.error("getUserPerson. The call to the ws returns a null value.");
            }
        } else {
            LOGGER.info("saveRepresentative: adapter is not enabled. Then there is no call to the ws.");
        }
        return results;
    }

    @Override
    public Representative getIntlPRepresentative(String representativeId) {
        eu.ohim.sp.external.domain.person.Representative externalResult = personInjector.getIntlPRepresentative(representativeId);
        Representative result = externalResult!=null ? mapper.map(externalResult, Representative.class) : null;
        return result;
    }

    @Override
    public String getIntlPRepresentativeList() {
        String results = personInjector.getIntlPRepresentativeList();
        return results;
    }
}
