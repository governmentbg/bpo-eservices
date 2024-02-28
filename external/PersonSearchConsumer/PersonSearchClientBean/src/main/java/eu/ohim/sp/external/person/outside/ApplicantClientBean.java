package eu.ohim.sp.external.person.outside;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.external.person.outside.ws.client.applicant.ApplicantClientWS;
import eu.ohim.sp.external.person.outside.ws.client.applicant.ApplicantClientWSService;
import eu.ohim.sp.external.person.ApplicantClient;
import eu.ohim.sp.external.person.PersonSearchClientOutside;
import eu.ohim.sp.external.utils.AbstractWSClient;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.external.ws.exception.ApplicantFaultException;

/**
 * This class is the router that connects to the JBoss ESB adapter for person
 * search/get. It is called from the fsp core service. Can be connecting to
 * tmview or esearch depends on the environment It connect to the esb using the
 * jboss-remoting library. In this case the url to connect is passed by system
 * properties. It contains all the dependencies to the esb.
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent @PersonSearchClientOutside
public class ApplicantClientBean extends AbstractWSClient implements ApplicantClient {
    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(ApplicantClientBean.class);

    /**
     * The Constant APPLICANT_ADAPTER_NAME.
     */
    public static final String APPLICANT_ADAPTER_NAME = "applicant";
    public static final String ADAPTER_ENABLED = "adapter enabled";

    /**
     * The system configuration service.
     */
	@EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Actual client to the web service
     */
    private ApplicantClientWS webServiceRef;

    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private DozerBeanMapper mapper;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        super.init(APPLICANT_ADAPTER_NAME);
        mapper = new DozerBeanMapper();
        if (getAdapterEnabled()) {
            webServiceRef = new ApplicantClientWSService(getWsdlLocation()).getManageApplicantPort();
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getApplicantAutocomplete(String module, String office, String text, int numberOfRows) {
        String results = null;
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(ADAPTER_ENABLED);
            }
            results = null;
            try {
                results = webServiceRef.getApplicantAutocomplete(module, office, text, numberOfRows);
            } catch (ApplicantFaultException exc) {
                LOGGER.error(" getApplicantAutocomplete ERROR WS SOAP: " + exc.getMessage(), exc);
            }


        }

        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Applicant> searchApplicant(String module, String office, String applicantId, String applicantName, String applicantNationality,
                                           int numberOfResults) {
        List<Applicant> results = null;
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(ADAPTER_ENABLED);
            }

            List<eu.ohim.sp.external.domain.person.Applicant> externalResult = null;
            try {
                externalResult = webServiceRef.searchApplicant(module, office, applicantId,
                        applicantName, applicantNationality, numberOfResults);
            } catch (ApplicantFaultException exc) {
                LOGGER.error(" searchApplicant ERROR WS SOAP: " + exc.getMessage(), exc);
            }

            results = new ArrayList<Applicant>();
            for (eu.ohim.sp.external.domain.person.Applicant applicant : externalResult) {
                results.add(mapper.map(applicant, eu.ohim.sp.core.domain.person.Applicant.class));
            }
        }
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Applicant getApplicant(String module, String office, String applicantId) {
        Applicant result = null;
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(ADAPTER_ENABLED);
            }
            eu.ohim.sp.external.domain.person.Applicant externalResult = null;
            try {
                externalResult = webServiceRef.getApplicant(module, office, applicantId);
            } catch (ApplicantFaultException exc) {
                LOGGER.error(" getApplicant ERROR WS SOAP: " + exc.getMessage(), exc);
            }

            if (externalResult != null) {
                result = mapper.map(externalResult, eu.ohim.sp.core.domain.person.Applicant.class);
            }

        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Applicant> matchApplicant(String module, String office, Applicant applicant, int numberOfResults) {
        List<Applicant> result = new ArrayList<Applicant>();
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(ADAPTER_ENABLED);
            }
            eu.ohim.sp.external.domain.person.Applicant extApplicant
                    = mapper.map(applicant, eu.ohim.sp.external.domain.person.Applicant.class);

            if (extApplicant != null) {
                List<eu.ohim.sp.external.domain.person.Applicant> externalResult = null;
                try {
                    externalResult = webServiceRef.matchApplicant(module, office, extApplicant, numberOfResults);
                } catch (ApplicantFaultException exc) {
                    LOGGER.error(" matchApplicant ERROR WS SOAP: " + exc.getMessage(), exc);
                }

                if (externalResult != null) {
                    result = new ArrayList<Applicant>();
                    for (eu.ohim.sp.external.domain.person.Applicant matchedApplicant : externalResult) {
                        result.add(mapper.map(matchedApplicant, eu.ohim.sp.core.domain.person.Applicant.class));
                    }
                }
            } else {
                LOGGER.error("searchApplicant external to core: Error converting input applicant to external domain");
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
    public Result saveApplicant(String module, String office, String user,
                                      Applicant applicant) {

        Result results = null;
        if (getAdapterEnabled()) {

            eu.ohim.sp.external.domain.person.Applicant externalObject = mapper.map(applicant, eu.ohim.sp.external.domain.person.Applicant.class);
            if (externalObject != null) {
                eu.ohim.sp.external.domain.common.Result externalResult = null;
                try {
                    externalResult = webServiceRef.saveApplicant(
                            module, office, user, externalObject);
                } catch (ApplicantFaultException exc) {
                    LOGGER.error(
                            " saveApplicant ERROR WS SOAP: " + exc.getMessage(),
                            exc);
                }

                if (externalResult != null) {
                    results = mapper.map(externalResult, eu.ohim.sp.core.domain.common.Result.class);
                } else {
                    LOGGER.error("getUserPerson. The call to the ws returns a null value.");
                }

            } else {
                LOGGER.error("saveApplicant Service. Can not be converted core domain object to external domain object");
            }

        } else {
            LOGGER.info("saveApplicant: adapter is not enabled. Then there is no call to the ws.");
        }

        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getErrorCode() {
        return "error.generic";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigurationService getConfigurationService() {
        return configurationService;
    }
}
