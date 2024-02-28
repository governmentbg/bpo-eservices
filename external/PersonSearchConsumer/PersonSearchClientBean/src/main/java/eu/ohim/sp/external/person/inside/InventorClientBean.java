package eu.ohim.sp.external.person.inside;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.person.Inventor;
import eu.ohim.sp.external.injectors.PersonInjector;
import eu.ohim.sp.external.person.InventorClient;
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
 * Created by Raya
 * 05.06.2019
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent
@PersonSearchClientInside
public class InventorClientBean implements InventorClient {

    private static final Logger LOGGER = Logger.getLogger(InventorClientBean.class);

    /** The Constant APPLICANT_ADAPTER_NAME. */
    public static final String ADAPTER_NAME = "inventor";

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

    @Override
    @Interceptors({AdapterSetup.Inventor.class, AdapterEnabled.class})
    public String getInventorAutocomplete(String module, String office, String text, int numberOfRows) {
        String results;
        results = personInjector.getInventorAutocomplete(module, office, text, String.valueOf(numberOfRows));
        return results;
    }

    @Override
    @Interceptors({AdapterSetup.Inventor.class, AdapterEnabled.class})
    public Inventor getInventor(String module, String office, String inventorId) {
        Inventor result = null;
        eu.ohim.sp.external.domain.person.Inventor externalResult = null;
        externalResult = personInjector.getInventor(module, office, inventorId);
        if (externalResult != null) {
            result = mapper.map(externalResult, Inventor.class);
        }
        return result;
    }

    @Override
    @Interceptors({AdapterSetup.Inventor.class, AdapterEnabled.class})
    public List<Inventor> matchInventor(String module, String office, Inventor inventor, int numberOfResults) {
        List<Inventor> result = new ArrayList<Inventor>();
        eu.ohim.sp.external.domain.person.Inventor extInventor
            = mapper.map(inventor, eu.ohim.sp.external.domain.person.Inventor.class);
        if (extInventor != null) {
            List<eu.ohim.sp.external.domain.person.Inventor> externalResult = null;

            externalResult = personInjector.matchInventor(module, office, extInventor, String.valueOf(numberOfResults));
            if (externalResult != null) {
                result = new ArrayList<Inventor>();
                for (eu.ohim.sp.external.domain.person.Inventor matchedInventor : externalResult) {
                    result.add(mapper.map(matchedInventor, Inventor.class));
                }
            }
        } else {
            LOGGER.error("matchInventor external to core: Error converting input inventor to external domain");
        }
        return result;
    }
}
