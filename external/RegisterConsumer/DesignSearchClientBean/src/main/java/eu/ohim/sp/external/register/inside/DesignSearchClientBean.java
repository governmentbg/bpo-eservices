/*******************************************************************************
 * * $Id:: DesignClientService.java 135807 2013-08-22 19:28:49Z karalc#$
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.external.register.inside;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.design.DesignDivisionalApplicationDetails;
import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.register.DesignSearchService;
import eu.ohim.sp.external.domain.design.Design;
import eu.ohim.sp.external.domain.design.DesignApplication;
import eu.ohim.sp.external.injectors.DesignInjector;
import eu.ohim.sp.external.injectors.ImageInjector;
import eu.ohim.sp.external.injectors.PersonInjector;
import eu.ohim.sp.external.register.DesignSearchClientInside;
import eu.ohim.sp.external.utils.AdapterEnabled;
import eu.ohim.sp.external.utils.AdapterSetup;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class is the router that connects to the JBoss ESB adapter for trademark search/get.
 * It is called from the fsp core service.
 * Can be connecting to tmview or esearch depends on the environment
 * It connect to the esb using the jboss-remoting library. In this case the url to connect is passed by system
 * properties.
 * It contains all the dependencies to the esb.
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent
@DesignSearchClientInside
public class DesignSearchClientBean implements DesignSearchService {

    public static final String ADAPTER_NAME = "design";

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = Logger.getLogger(DesignSearchClientBean.class);

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
	 * Design injector
	 */
	private DesignInjector ds_injector;

	/**
	 * Person injector
	 */
	private PersonInjector person_injector;

    /**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		mapper = new DozerBeanMapper();
		ds_injector = new DesignInjector();
		person_injector = new PersonInjector();
	}

	/**
	 * Gets design.
	 *
	 * @param office the office
	 * @param designId the design id
	 * @return the design
	 */
	@Interceptors({AdapterSetup.Design.class, AdapterEnabled.class})
	public eu.ohim.sp.core.domain.design.Design getDesign(String office, String designId) {
        Design externalResult = ds_injector.getDesign(office, designId);
		if(externalResult != null) {
			ImageInjector.inject(externalResult);
			person_injector.injectApplicants(externalResult);
			person_injector.injectRepresentatives(externalResult);
			return mapper.map(externalResult, eu.ohim.sp.core.domain.design.Design.class);
		}
        return null;
	}

	/**
	 * Gets design application.
	 *
	 * @param office the office
	 * @param designId the design id
	 * @return the design application
	 */
	@Interceptors({AdapterSetup.Design.class, AdapterEnabled.class})
	public eu.ohim.sp.core.domain.design.DesignApplication getDesignApplication(String office, String designId, String applicationId, Boolean extraImport) {
        DesignApplication externalResult = ds_injector.getDesignApplication(office, designId, applicationId);
		if(externalResult != null) {
			externalResult.getDesignDetails().stream()
					.filter(d -> Objects.nonNull(d))
					.map(d -> {
						ImageInjector.inject(d);
						return d;
					})
					.collect(Collectors.toList());
			person_injector.injectApplicants(externalResult);
			person_injector.injectRepresentatives(externalResult);
			return mapper.map(externalResult, eu.ohim.sp.core.domain.design.DesignApplication.class);
		}
        return null;
	}

	/**
	 * Gets design autocomplete.
	 *
	 * @param office the office
	 * @param text the text
	 * @param numberOfResults the number of results
	 * @return the design autocomplete
	 */
	@Interceptors({AdapterSetup.Design.class, AdapterEnabled.class})
	public String getDesignAutocomplete(String office, String text, Integer numberOfResults) {
		throw new NotImplementedException();
	}

	@Override
	@Interceptors({AdapterSetup.Design.class, AdapterEnabled.class})
	public ErrorList validateDesignView(String s, DesignView designView, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }

    @Override
	@Interceptors({AdapterSetup.Design.class, AdapterEnabled.class})
    public ErrorList validateDivisionalApplication(String module, DesignDivisionalApplicationDetails dsDivisionalApplication, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }

    @Override
	@Interceptors({AdapterSetup.Design.class, AdapterEnabled.class})
	public ErrorList validateApplicationCA(String s, ContactDetails contactDetails, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }

	@Override
	@Interceptors({AdapterSetup.Design.class, AdapterEnabled.class})
	public ErrorList validateDesign(String module, eu.ohim.sp.core.domain.design.Design design, RulesInformation rulesInformation) {
		throw new NotImplementedException();
	}

	@Override
	@Interceptors({AdapterSetup.Design.class, AdapterEnabled.class})
	public ErrorList validateDesignApplication(String module, eu.ohim.sp.core.domain.design.DesignApplication designApplication, RulesInformation rulesInformation) {
		throw new NotImplementedException();
	}
}
