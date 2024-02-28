package eu.ohim.sp.external.person.inside;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.person.AddressService;
import eu.ohim.sp.external.injectors.AddressInjector;
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
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 10/10/13
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
@Dependent @PersonSearchClientInside
public class AddressClientBean implements AddressService {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(AddressClientBean.class);

    /** The Constant APPLICANT_ADAPTER_NAME. */
    public static final String ADAPTER_NAME = "address";

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
     * Inits the.
     */
    @PostConstruct
    public void init() {
        mapper = new DozerBeanMapper();
    }

    @Interceptors({AdapterSetup.Address.class, AdapterEnabled.class})
    public Collection<Address> getAddressAutocomplete(String module, String text, Address address, int numberOfRows) {
        eu.ohim.sp.external.domain.contact.Address addressExt = mapper.map(address, eu.ohim.sp.external.domain.contact.Address.class);
        Collection<Address> addresses = new ArrayList<Address>();
        Collection<eu.ohim.sp.external.domain.contact.Address> addressesExt = AddressInjector.getAddressAutocomplete(module, text, addressExt, numberOfRows);
        for (eu.ohim.sp.external.domain.contact.Address addressExtResp : addressesExt) {
            addresses.add(mapper.map(addressExtResp, Address.class));
        }
        return addresses;
    }

    @Interceptors({AdapterSetup.Address.class, AdapterEnabled.class})
    public Collection<Address> matchAddress(String module, Address address, int numberOfResults) {
        eu.ohim.sp.external.domain.contact.Address addressExt = mapper.map(address, eu.ohim.sp.external.domain.contact.Address.class);
        Collection<Address> addresses = new ArrayList<Address>();
        Collection<eu.ohim.sp.external.domain.contact.Address> addressesExt = AddressInjector.matchAddress(module, addressExt, numberOfResults);
        for (eu.ohim.sp.external.domain.contact.Address addressExtResp : addressesExt) {
            addresses.add(mapper.map(addressExtResp, Address.class));
        }
        return addresses;
    }
}
