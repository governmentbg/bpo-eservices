package eu.ohim.sp.external.person.outside;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.external.person.PersonSearchClientOutside;
import eu.ohim.sp.external.person.RepresentativeClient;
import eu.ohim.sp.external.person.outside.ws.client.representative.RepresentativeClientWS;
import eu.ohim.sp.external.person.outside.ws.client.representative.RepresentativeClientWSService;
import eu.ohim.sp.external.utils.AbstractWSClient;
import eu.ohim.sp.external.ws.exception.RepresentativeFaultException;

import org.apache.commons.lang.NotImplementedException;
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
@Dependent @PersonSearchClientOutside
public class RepresentativeClientBean extends AbstractWSClient implements RepresentativeClient {

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
     * Actual client to the web service
     */
    private RepresentativeClientWS webServiceRef;

    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private DozerBeanMapper mapper;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        mapper = new DozerBeanMapper();
        super.init(ADAPTER_NAME);
        if (getAdapterEnabled()) {
            webServiceRef = new RepresentativeClientWSService(getWsdlLocation()).getManageRepresentativePort();
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * eu.ohim.sp.external.adapters.person.RepresentativeClientServiceInterface#getRepresentativeAutocomplete(java
     * .lang.String, int)
     */
    @Override
    public String getRepresentativeAutocomplete(String module, String office, String text, int numberOfRows) {
        String results = null;
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("adapter enabled");
            }
            try {
                results = webServiceRef.getRepresentativeAutocomplete(module, office, text, numberOfRows);
            } catch (RepresentativeFaultException exc) {
                LOGGER.error(" getRepresentativeAutocomplete ERROR WS SOAP: " + exc.getMessage(), exc);
            }
        }

        return results;
    }

    /*
     * (non-Javadoc)
     * @see
     * eu.ohim.sp.external.adapters.person.RepresentativeClientServiceInterface#searchRepresentative(java.lang.String,
     * java.lang.String, java.lang.String, int)
     */
    @Override
    public List<Representative> searchRepresentative(String module, String office, String representativeId, String representativeName,
                                                     String representativeNationality, int numberOfResults) {
        List<Representative> results = new ArrayList<Representative>();
        if (getAdapterEnabled()) {
            List<eu.ohim.sp.external.domain.person.Representative> externalResult = null;
            try {
                externalResult = webServiceRef.searchRepresentative(
                        module, office, representativeId, representativeName, representativeNationality, numberOfResults);
            } catch (RepresentativeFaultException exc) {
                LOGGER.error(" searchRepresentative ERROR WS SOAP: " + exc.getMessage(), exc);
            }
            if (externalResult != null) {
                for (eu.ohim.sp.external.domain.person.Representative externalRepresentative : externalResult) {
                    results.add(mapper.map(externalRepresentative, eu.ohim.sp.core.domain.person.Representative.class));
                }
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
    public Representative getRepresentative(String module, String office, String representativeId) {
        Representative result = null;
        if (getAdapterEnabled()) {
            eu.ohim.sp.external.domain.person.Representative externalResult = null;
            try {
                externalResult = webServiceRef.getRepresentative(module, office,
                        representativeId);
            } catch (RepresentativeFaultException exc) {
                LOGGER.error(" getRepresentative ERROR WS SOAP: " + exc.getMessage(), exc);
            }
            result = externalResult!=null ? mapper.map(externalResult, eu.ohim.sp.core.domain.person.Representative.class) : null;
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * @see
     * eu.ohim.sp.external.adapters.person.RepresentativeClientServiceInterface#matchRepresentative(eu.ohim.sp.core
     * .domain.Representative, int)
     */
    @Override
    public List<Representative> matchRepresentative(String module, String office, Representative representative, int numberOfResults) {
        List<Representative> result = new ArrayList<Representative>();
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("adapter enabled");
            }
            eu.ohim.sp.external.domain.person.Representative extRepresentative =
                    mapper.map(representative, eu.ohim.sp.external.domain.person.Representative.class);
            if (extRepresentative != null) {
                List<eu.ohim.sp.external.domain.person.Representative> externalResult = null;
                try {
                    externalResult = webServiceRef.matchRepresentative(module, office, extRepresentative, numberOfResults);
                } catch (RepresentativeFaultException exc) {
                    LOGGER.error(" matchRepresentative ERROR WS SOAP: " + exc.getMessage(), exc);
                }

                if (externalResult != null) {
                    for (eu.ohim.sp.external.domain.person.Representative matchedRepresentative : externalResult) {
                        result.add(mapper.map(matchedRepresentative, eu.ohim.sp.core.domain.person.Representative.class));
                    }
                }
            } else {
                LOGGER.error("searchApplicant external to core: Error converting input applicant to external domain");
            }
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
    public Result saveRepresentative(String module, String office, String user,
                                           Representative representative) {
        Result results = null;
        if (getAdapterEnabled()) {
            eu.ohim.sp.external.domain.person.Representative externalObject =
                    mapper.map(representative, eu.ohim.sp.external.domain.person.Representative.class);
            if (externalObject != null) {
                eu.ohim.sp.external.domain.common.Result externalResult = null;
                try {
                    externalResult = webServiceRef
                            .saveRepresentative(module, office, user, externalObject);
                } catch (RepresentativeFaultException exc) {
                    LOGGER.error(
                            " saveRepresentative ERROR WS SOAP: "
                                    + exc.getMessage(), exc);
                }

                if (externalResult != null) {
                    results = mapper.map(externalResult, eu.ohim.sp.core.domain.common.Result.class);
                } else {
                    LOGGER.error("getUserPerson. The call to the ws returns a null value.");
                }
            } else {
                LOGGER.error("saveRepresentative Service. Can not be converted core domain object to external domain object");
            }
        } else {
            LOGGER.info("saveRepresentative: adapter is not enabled. Then there is no call to the ws.");
        }
        return results;
    }

    /*
     * (non-Javadoc)
     * @see eu.ohim.sp.external.adaptors.AbstractWSClient#getErrorCode()
     */
    @Override
    protected String getErrorCode() {
        return "error.generic";
    }

    /*
     * (non-Javadoc)
     * @see eu.ohim.sp.external.adaptors.AbstractWSClient#getSystemConfigurationService()
     */
    @Override
    public ConfigurationService getConfigurationService() {
        return configurationService;
    }

    @Override
    public Representative getIntlPRepresentative(String representativeId) {
        throw new NotImplementedException();
    }

    @Override
    public String getIntlPRepresentativeList() {
        throw new NotImplementedException();
    }
}
