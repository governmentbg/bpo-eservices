package eu.ohim.sp.external.person.outside;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.external.person.outside.ws.client.designer.DesignerClientWS;
import eu.ohim.sp.external.person.outside.ws.client.designer.DesignerClientWSService;
import eu.ohim.sp.external.person.DesignerClient;
import eu.ohim.sp.external.person.PersonSearchClientOutside;
import eu.ohim.sp.external.utils.AbstractWSClient;
import eu.ohim.sp.external.ws.exception.DesignerFaultException;

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
@Dependent @PersonSearchClientOutside
public class DesignerClientBean extends AbstractWSClient implements DesignerClient {
    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(DesignerClientBean.class);

    /** The Constant APPLICANT_ADAPTER_NAME. */
    public static final String ADAPTER_NAME = "designer";
    public static final String ADAPTER_ENABLED = "adapter enabled";

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
     * Actual client to the web service
     */
    private DesignerClientWS webServiceRef;


    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        super.init(ADAPTER_NAME);
        mapper = new DozerBeanMapper();
        if (getAdapterEnabled()) {
            webServiceRef = new DesignerClientWSService(getWsdlLocation()).getManageDesignerPort();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDesignerAutocomplete(String module, String office, String text, int numberOfRows) {
        String results = null;
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(ADAPTER_ENABLED);
            }
            results = null;
            try {
                results = webServiceRef.getDesignerAutocomplete(module, office, text, numberOfRows);
            } catch (DesignerFaultException exc) {
                LOGGER.error(" getDesignerAutocomplete ERROR WS SOAP: " + exc.getMessage(), exc);
            }


        }

        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Designer> searchDesigner(String module, String office, String designerId, String designerName, String designerNationality,
                                           int numberOfResults) {
        List<Designer> results = null;
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(ADAPTER_ENABLED);
            }

            List<eu.ohim.sp.external.domain.person.Designer> externalResult = null;
            try {
                externalResult = webServiceRef.searchDesigner(module, office, designerId,
                        designerName, designerNationality, numberOfResults);
            } catch (DesignerFaultException exc) {
                LOGGER.error(" searchDesigner ERROR WS SOAP: " + exc.getMessage(), exc);
            }

            results = new ArrayList<Designer>();
            for (eu.ohim.sp.external.domain.person.Designer designer : externalResult) {
                results.add(mapper.map(designer, Designer.class));
            }
        }
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Designer getDesigner(String module, String office, String designerId) {
        Designer result = null;
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(ADAPTER_ENABLED);
            }
            eu.ohim.sp.external.domain.person.Designer externalResult = null;
            try {
                externalResult = webServiceRef.getDesigner(module, office, designerId);
            } catch (DesignerFaultException exc) {
                LOGGER.error(" getDesigner ERROR WS SOAP: " + exc.getMessage(), exc);
            }

            if (externalResult != null) {
                result = mapper.map(externalResult, Designer.class);
            }

        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Designer> matchDesigner(String module, String office, Designer designer, int numberOfResults) {
        List<Designer> result = new ArrayList<Designer>();
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(ADAPTER_ENABLED);
            }
            eu.ohim.sp.external.domain.person.Designer extDesigner
                    = mapper.map(designer, eu.ohim.sp.external.domain.person.Designer.class);

            if (extDesigner != null) {
                List<eu.ohim.sp.external.domain.person.Designer> externalResult = null;
                try {
                    externalResult = webServiceRef.matchDesigner(module, office, extDesigner, numberOfResults);
                } catch (DesignerFaultException exc) {
                    LOGGER.error(" matchDesigner ERROR WS SOAP: " + exc.getMessage(), exc);
                }

                if (externalResult != null) {
                    result = new ArrayList<Designer>();
                    for (eu.ohim.sp.external.domain.person.Designer matchedDesigner : externalResult) {
                        result.add(mapper.map(matchedDesigner, Designer.class));
                    }
                }
            } else {
                LOGGER.error("searchDesigner external to core: Error converting input designer to external domain");
            }


        }

        return result;
    }

    /**
     * Makes the call to the client to save an designer.
     *
     * @param office
     *            the office code
     * @param user
     *            the user
     * @param designer
     *            the designer to save
     * @return the result of the call
     */
    @Override
    public Result saveDesigner(String module, String office, String user,
                                      Designer designer) {

        Result results = null;
        if (getAdapterEnabled()) {

            eu.ohim.sp.external.domain.person.Designer externalObject = mapper.map(designer, eu.ohim.sp.external.domain.person.Designer.class);
            if (externalObject != null) {
                eu.ohim.sp.external.domain.common.Result externalResult = null;
                try {
                    externalResult = webServiceRef.saveDesigner(
                            module, office, user, externalObject);
                } catch (DesignerFaultException exc) {
                    LOGGER.error(
                            " saveDesigner ERROR WS SOAP: " + exc.getMessage(),
                            exc);
                }

                if (externalResult != null) {
                    results = mapper.map(externalResult, Result.class);
                } else {
                    LOGGER.error("getUserPerson. The call to the ws returns a null value.");
                }

            } else {
                LOGGER.error("saveDesigner Service. Can not be converted core domain object to external domain object");
            }

        } else {
            LOGGER.info("saveDesigner: adapter is not enabled. Then there is no call to the ws.");
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
