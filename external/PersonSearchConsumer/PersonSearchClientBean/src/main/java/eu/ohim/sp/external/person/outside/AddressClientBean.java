package eu.ohim.sp.external.person.outside;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.person.AddressService;
import eu.ohim.sp.external.person.outside.ws.client.address.AddressClientWS;
import eu.ohim.sp.external.person.outside.ws.client.address.AddressClientWSService;
import eu.ohim.sp.external.person.PersonSearchClientOutside;
import eu.ohim.sp.external.utils.AbstractWSClient;
import eu.ohim.sp.external.ws.exception.AddressFaultException;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 10/10/13
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
@Dependent @PersonSearchClientOutside
public class AddressClientBean extends AbstractWSClient implements AddressService {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(AddressClientBean.class);

    /** The Constant APPLICANT_ADAPTER_NAME. */
    public static final String ADAPTER_NAME = "address";
    public static final String ADAPTER_ENABLED = "adapter enabled";

    /**
     * The system configuration service.
     */
	@EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Actual client to the web service
     */
    private AddressClientWS webServiceRef;

    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private DozerBeanMapper mapper;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        super.init(ADAPTER_NAME);
        mapper = new DozerBeanMapper();
        if (getAdapterEnabled()) {
            webServiceRef = new AddressClientWSService(getWsdlLocation()).getManageAddressPort();
        }
    }


    public Collection<Address> getAddressAutocomplete(String module, String text, Address address, int numberOfRows) {
        eu.ohim.sp.external.domain.contact.Address addressExt = mapper.map(address, eu.ohim.sp.external.domain.contact.Address.class);
        Collection<Address> addresses = new ArrayList<Address>();
        try {
            Collection<eu.ohim.sp.external.domain.contact.Address> addressesExt = webServiceRef.getAddressAutocomplete(module, text, addressExt, numberOfRows);
            for (eu.ohim.sp.external.domain.contact.Address addressExtResp : addressesExt) {
                addresses.add(mapper.map(addressExtResp, Address.class));
            }
        } catch (AddressFaultException exc) {
            LOGGER.error(
                    " getAddressAutocomplete ERROR WS SOAP: " + exc.getMessage(),
                    exc);
        }
        return addresses;
    }

    public Collection<Address> matchAddress(String module, Address address, int numberOfResults) {
        eu.ohim.sp.external.domain.contact.Address addressExt = mapper.map(address, eu.ohim.sp.external.domain.contact.Address.class);
        Collection<Address> addresses = new ArrayList<Address>();
        try {
            Collection<eu.ohim.sp.external.domain.contact.Address> addressesExt = webServiceRef.matchAddress(module, addressExt, numberOfResults);
            for (eu.ohim.sp.external.domain.contact.Address addressExtResp : addressesExt) {
                addresses.add(mapper.map(addressExtResp, Address.class));
            }
        } catch (AddressFaultException exc) {
            LOGGER.error(
                    " getAddressAutocomplete ERROR WS SOAP: " + exc.getMessage(),
                    exc);
        }
        return addresses;
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
