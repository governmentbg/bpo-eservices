/*******************************************************************************
 * * $Id:: DesignClientService.java 135807 2013-08-22 19:28:49Z karalc#$
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.external.register.outside;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.design.DesignDivisionalApplicationDetails;
import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.external.register.DesignSearchClientOutside;
import eu.ohim.sp.external.utils.AbstractWSClient;
import eu.ohim.sp.external.register.outside.ws.client.DesignSearchClientWS;
import eu.ohim.sp.external.register.outside.ws.client.DesignSearchClientWSService;
import eu.ohim.sp.external.ws.exception.DesignFaultException;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import eu.ohim.sp.core.register.*;
import org.apache.commons.lang.NotImplementedException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;

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
@DesignSearchClientOutside
public class DesignSearchClientBean extends AbstractWSClient implements DesignSearchService {

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
     * Actual client to the web service
     */
    private DesignSearchClientWS webServiceRef;

    /**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		super.init(ADAPTER_NAME);
		mapper = new DozerBeanMapper();
		if (getAdapterEnabled()) {
			webServiceRef = new DesignSearchClientWSService(getWsdlLocation()).getManageDesignPort();
		}
	}

	/**
	 * Gets design.
	 *
	 * @param office the office
	 * @param designId the design id
	 * @return the design
	 */
	public Design getDesign(String office, String designId) {
        Design design = null;
        try {
            eu.ohim.sp.external.domain.design.Design external = webServiceRef.getDesign(office, designId);
            design = mapper.map(external, Design.class);
        } catch (DesignFaultException e) {
            LOGGER.error(e);
        }
        return design;
	}

	/**
	 * Gets design application.
	 *
	 * @param office the office
	 * @param designId the design id
	 * @param extraImport the extra import
	 * @return the design application
	 */
	public DesignApplication getDesignApplication(String office, String designId, String applicationId, Boolean extraImport) {
        DesignApplication design = null;
        try {
            eu.ohim.sp.external.domain.design.DesignApplication external = webServiceRef.getDesignApplication(office, designId, extraImport);
            design = mapper.map(external, DesignApplication.class);
        } catch (DesignFaultException e) {
            LOGGER.error(e);
        }
        return design;
	}

	/**
	 * Gets design autocomplete.
	 *
	 * @param office the office
	 * @param text the text
	 * @param numberOfResults the number of results
	 * @return the design autocomplete
	 */
	public String getDesignAutocomplete(String office, String text, Integer numberOfResults) {
        try {
            return webServiceRef.getDesignAutocomplete(office, text, numberOfResults);
        } catch (DesignFaultException e) {
            throw new SPException(e);
        }
	}

	@Override
	protected String getErrorCode() {
		return "";
	}

	@Override
	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	@Override
	public ErrorList validateDesign(String s, Design design, RulesInformation rulesInformation) {
		throw new NotImplementedException();
	}

	@Override
	public ErrorList validateDesignApplication(String s, DesignApplication designApplication, RulesInformation rulesInformation) {
        throw new NotImplementedException();
	}

	@Override
	public ErrorList validateDesignView(String s, DesignView designView, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }

    @Override
    public ErrorList validateDivisionalApplication(String module, DesignDivisionalApplicationDetails dsDivisionalApplication, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }

    @Override
	public ErrorList validateApplicationCA(String s, ContactDetails contactDetails, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }
}
