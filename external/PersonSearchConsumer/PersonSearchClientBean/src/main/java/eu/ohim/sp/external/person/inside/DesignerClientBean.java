package eu.ohim.sp.external.person.inside;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.external.injectors.PersonInjector;
import eu.ohim.sp.external.person.DesignerClient;
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
public class DesignerClientBean implements DesignerClient {
    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(DesignerClientBean.class);

    /** The Constant APPLICANT_ADAPTER_NAME. */
    public static final String ADAPTER_NAME = "designer";

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
     * Person injector
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
    @Interceptors({AdapterSetup.Designer.class, AdapterEnabled.class})
    public String getDesignerAutocomplete(String module, String office, String text, int numberOfRows) {
        String results;
        results = personInjector.getDesignerAutocomplete(module, office, text, String.valueOf(numberOfRows));
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors({AdapterSetup.Designer.class, AdapterEnabled.class})
    public List<Designer> searchDesigner(String module, String office, String designerId, String designerName, String designerNationality,
                                           int numberOfResults) {
        List<Designer> results;
        List<eu.ohim.sp.external.domain.person.Designer> externalResult = null;
            externalResult = personInjector.searchDesigner(module, office, designerId,
                    designerName, designerNationality, String.valueOf(numberOfResults));

        results = new ArrayList<Designer>();
        for (eu.ohim.sp.external.domain.person.Designer designer : externalResult) {
            results.add(mapper.map(designer, Designer.class));
        }
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors({AdapterSetup.Designer.class, AdapterEnabled.class})
    public Designer getDesigner(String module, String office, String designerId) {
        Designer result = null;
        eu.ohim.sp.external.domain.person.Designer externalResult = null;
        externalResult = personInjector.getDesigner(module, office, designerId);
        if (externalResult != null) {
            result = mapper.map(externalResult, Designer.class);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Interceptors({AdapterSetup.Designer.class, AdapterEnabled.class})
    public List<Designer> matchDesigner(String module, String office, Designer designer, int numberOfResults) {
        List<Designer> result = new ArrayList<Designer>();
        eu.ohim.sp.external.domain.person.Designer extDesigner
                    = mapper.map(designer, eu.ohim.sp.external.domain.person.Designer.class);
        if (extDesigner != null) {
            List<eu.ohim.sp.external.domain.person.Designer> externalResult = null;

            externalResult = personInjector.matchDesigner(module, office, extDesigner, String.valueOf(numberOfResults));
            if (externalResult != null) {
                result = new ArrayList<Designer>();
                for (eu.ohim.sp.external.domain.person.Designer matchedDesigner : externalResult) {
                    result.add(mapper.map(matchedDesigner, Designer.class));
                }
            }
        } else {
            LOGGER.error("searchDesigner external to core: Error converting input designer to external domain");
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
    @Interceptors({AdapterSetup.Designer.class, AdapterEnabled.class})
    public Result saveDesigner(String module, String office, String user,
                                      Designer designer) {
        Result results = null;
        eu.ohim.sp.external.domain.person.Designer externalObject = mapper.map(designer, eu.ohim.sp.external.domain.person.Designer.class);
        if (externalObject != null) {
            eu.ohim.sp.external.domain.common.Result externalResult = null;
                externalResult = personInjector.saveDesigner(
                        module, office, user, externalObject);

            if (externalResult != null) {
                results = mapper.map(externalResult, Result.class);
            } else {
                LOGGER.error("getUserPerson. The call to the ws returns a null value.");
            }
        } else {
            LOGGER.error("saveDesigner Service. Can not be converted core domain object to external domain object");
        }
        return results;
    }
}
