package eu.ohim.sp.external.person.inside;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.external.injectors.PersonInjector;
import eu.ohim.sp.external.person.ApplicantClient;
import eu.ohim.sp.external.person.PersonSearchClientInside;
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
 * This class is the router that connects to the JBoss ESB adapter for person
 * search/get. It is called from the fsp core service. Can be connecting to
 * tmview or esearch depends on the environment It connect to the esb using the
 * jboss-remoting library. In this case the url to connect is passed by system
 * properties. It contains all the dependencies to the esb.
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent @PersonSearchClientInside
public class ApplicantClientBean implements ApplicantClient {
    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(ApplicantClientBean.class);

    /**
     * The Constant APPLICANT_ADAPTER_NAME.
     */
    public static final String APPLICANT_ADAPTER_NAME = "applicant";

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
     * Injector
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


    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors({AdapterSetup.Applicant.class, AdapterEnabled.class})
    public String getApplicantAutocomplete(String module, String office, String text, int numberOfRows) {
        String results = null;
        results = personInjector.getApplicantAutocomplete(module, office, text, String.valueOf(numberOfRows));
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors({AdapterSetup.Applicant.class, AdapterEnabled.class})
    public List<Applicant> searchApplicant(String module, String office, String applicantId, String applicantName, String applicantNationality,
                                           int numberOfResults) {
        List<Applicant> results = null;
        List<eu.ohim.sp.external.domain.person.Applicant> externalResult = null;
        externalResult = personInjector.searchApplicant(module, office, applicantId,
                        applicantName, applicantNationality, String.valueOf(numberOfResults));
        results = new ArrayList<Applicant>();
        for (eu.ohim.sp.external.domain.person.Applicant applicant : externalResult) {
            results.add(mapper.map(applicant, Applicant.class));
        }
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors({AdapterSetup.Applicant.class, AdapterEnabled.class})
    public Applicant getApplicant(String module, String office, String applicantId) {
        Applicant result = null;
        eu.ohim.sp.external.domain.person.Applicant externalResult = null;
        externalResult = personInjector.getApplicant(module, office, applicantId);
        if (externalResult != null) {
            result = mapper.map(externalResult, Applicant.class);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors({AdapterSetup.Applicant.class, AdapterEnabled.class})
    public List<Applicant> matchApplicant(String module, String office, Applicant applicant, int numberOfResults) {
        List<Applicant> result = new ArrayList<Applicant>();
        eu.ohim.sp.external.domain.person.Applicant extApplicant
                = mapper.map(applicant, eu.ohim.sp.external.domain.person.Applicant.class);

        if (extApplicant != null) {
            List<eu.ohim.sp.external.domain.person.Applicant> externalResult = null;
            externalResult = personInjector.matchApplicant(module, office, extApplicant, String.valueOf(numberOfResults));
            if (externalResult != null) {
                result = new ArrayList<Applicant>();
                for (eu.ohim.sp.external.domain.person.Applicant matchedApplicant : externalResult) {
                    result.add(mapper.map(matchedApplicant, Applicant.class));
                }
            }
        }
        return result;
    }

    /**
     * Makes the call to the client to save an applicant.
     *
     * @param office    the office code
     * @param user      the user
     * @param applicant the applicant to save
     * @return the result of the call
     */
    @Override
    @Interceptors({AdapterSetup.Applicant.class, AdapterEnabled.class})
    public Result saveApplicant(String module, String office, String user,
                                      Applicant applicant) {
        Result results = null;
        eu.ohim.sp.external.domain.person.Applicant externalObject = mapper.map(applicant, eu.ohim.sp.external.domain.person.Applicant.class);
        if (externalObject != null) {
            eu.ohim.sp.external.domain.common.Result externalResult = null;
            externalResult = personInjector.saveApplicant(module, office, user, externalObject);
            if (externalResult != null) {
                results = mapper.map(externalResult, Result.class);
            } else {
                LOGGER.error("getUserPerson. The call to the ws returns a null value.");
            }
        }
        return results;
    }
}
