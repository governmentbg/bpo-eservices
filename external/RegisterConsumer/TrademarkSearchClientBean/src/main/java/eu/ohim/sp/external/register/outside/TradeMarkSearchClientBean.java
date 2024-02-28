/*******************************************************************************
 * * $Id:: TrademarkAdapterService.java 139703 2013-09-13 08:11:54Z karalc#$
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
import eu.ohim.sp.core.domain.application.Appeal;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.licence.Licence;
import eu.ohim.sp.core.domain.opposition.OppositionGround;
import eu.ohim.sp.core.domain.trademark.ForeignRegistration;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.register.TradeMarkSearchService;
import eu.ohim.sp.external.register.TradeMarkSearchClientOutside;
import eu.ohim.sp.external.register.outside.ws.client.TrademarkSearchClientWS;
import eu.ohim.sp.external.register.outside.ws.client.TrademarkSearchClientWSService;
import eu.ohim.sp.external.utils.AbstractWSClient;
import eu.ohim.sp.external.ws.exception.TradeMarkFaultException;
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
 * This class is the router that connects to the JBoss ESB adapter for trademark search/get.
 * It is called from the fsp core service.
 * Can be connecting to tmview or esearch depends on the environment
 * It connect to the esb using the jboss-remoting library. In this case the url to connect is passed by system
 * properties.
 * It contains all the dependencies to the esb.
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent
@TradeMarkSearchClientOutside
public class TradeMarkSearchClientBean extends AbstractWSClient implements TradeMarkSearchService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(TradeMarkSearchClientBean.class);

    /** The Constant ADAPTER_NAME. */
    public static final String ADAPTER_NAME = "trademark";

    /** The system configuration service. */
    @EJB(lookup = "java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private DozerBeanMapper mapper;

    /**
     * Actual client to the web service
     */
    private TrademarkSearchClientWS webServiceRef;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        super.init(ADAPTER_NAME);
        mapper = new DozerBeanMapper();
        if (getAdapterEnabled()) {
            webServiceRef = new TrademarkSearchClientWSService(getWsdlLocation()).getManageTrademarkPort();
        }
    }

    /*
     * (non-Javadoc)
     * @see eu.ohim.sp.external.adapters.trademark.TrademarkClientServiceInterface#getTradeMark(java.lang.String,
     * java.lang.String)
     */
    @Override
    public TradeMark getTradeMark(String office, String tradeMarkId) {
        TradeMark result = null;
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("getTradeMark adapter enabled");
            }
            eu.ohim.sp.external.domain.trademark.TradeMark externalResult = null;
            try {
                externalResult = webServiceRef.getTradeMark(office, tradeMarkId);
            } catch (TradeMarkFaultException exc) {
                LOGGER.error(" getTradeMark ERROR WS SOAP: " + exc.getMessage(), exc);
            } catch (javax.xml.ws.soap.SOAPFaultException exc) {
                LOGGER.debug(exc.getMessage());
                LOGGER.error(exc);
                throw new SPException(exc);
            }

            if (externalResult != null) {
                result = mapper.map(externalResult, TradeMark.class);
            }
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * @see
     * eu.ohim.sp.external.adapters.trademark.TrademarkClientServiceInterface#getForeignTradeMark(java.lang.String,
     * java.lang.String)
     */
    @Override
    public TradeMark getForeignTradeMark(String office, String tradeMarkId) {
        return getTradeMark(office, tradeMarkId);
    }

    /*
     * (non-Javadoc)
     * @see
     * eu.ohim.sp.external.adapters.trademark.TrademarkClientServiceInterface#getInternationalTradeMark(java.lang.
     * String, java.lang.String)
     */
    @Override
    public TradeMark getInternationalTradeMark(String office, String tradeMarkId, Boolean extraImport) {
        TradeMark result = null;
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("getInternationalTradeMark adapter enabled");
            }
            eu.ohim.sp.external.domain.trademark.TradeMark externalResult = null;
            try {
                externalResult = webServiceRef.getInternationalTradeMark(office, tradeMarkId, extraImport);
            } catch (TradeMarkFaultException exc) {
                LOGGER.error(" getForeignTradeMark ERROR WS SOAP: " + exc.getMessage(), exc);
            }

            if (externalResult != null) {
                result = mapper.map(externalResult, TradeMark.class);
            }

        }

        return result;
    }

    /*
     * (non-Javadoc)
     * @see
     * eu.ohim.sp.external.adapters.trademark.TrademarkClientServiceInterface#getTradeMarkAutocomplete(java.lang.String
     * , java.lang.String, java.lang.Integer)
     */
    @Override
    public String getTradeMarkAutocomplete(String office, String text, Integer numberOfResults) {
        String results = null;

        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("adapter enabled");
            }
            try {
                results = webServiceRef.getTradeMarkAutocomplete(office, text, numberOfResults);
            } catch (TradeMarkFaultException exc) {
                LOGGER.error(" getTradeMarkAutocomplete ERROR WS SOAP: " + exc.getMessage(), exc);
            }

        }

        return results;
    }

    /*
     * (non-Javadoc)
     * @see
     * eu.ohim.sp.external.adapters.trademark.TrademarkClientServiceInterface#getForeignTradeMarkAutocomplete(java
     * .lang.String, java.lang.String, java.lang.Integer)
     */
    @Override
    public String getForeignTradeMarkAutocomplete(String office, String text, Integer numberOfResults) {
        return getTradeMarkAutocomplete(office, text, numberOfResults);
    }

    /*
     * (non-Javadoc)
     * @see
     * eu.ohim.sp.external.adapters.trademark.TrademarkClientServiceInterface#getPreclearanceReport(java.lang.String,
     * eu.ohim.sp.core.domain.TradeMark)
     */
    @Override
    public List<TradeMark> getPreclearanceReport(String office, TradeMark tradeMark) {
        List<TradeMark> result = null;
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("adapter enabled");
            }
            eu.ohim.sp.external.domain.trademark.TradeMark externalObject = null;
            externalObject = mapper.map(tradeMark, eu.ohim.sp.external.domain.trademark.TradeMark.class);

            if (externalObject != null) {
                List<eu.ohim.sp.external.domain.trademark.TradeMark> externalResult = null;
                try {
                    externalResult = webServiceRef.getPreclearanceReport(office, externalObject);
                } catch (TradeMarkFaultException exc) {
                    LOGGER.error(" getPreclearanceReport ERROR WS SOAP: " + exc.getMessage(), exc);
                }

                if (externalResult != null) {
                    result = new ArrayList<TradeMark>();
                    for (eu.ohim.sp.external.domain.trademark.TradeMark extTradeMark : externalResult) {
                        result.add(mapper.map(extTradeMark, TradeMark.class));
                    }
                }
            }

        }

        return result;
    }

    @Override
    public ErrorList validateOpposition(String module, OppositionGround opposition, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }

    @Override
    public ErrorList validateTradeMark(String module, TradeMark tradeMark, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }

    @Override
    public ErrorList validateLicence(String module, Licence licence, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }

    @Override
    public ErrorList validateAppeal(String module, Appeal appeal, RulesInformation rulesInformation){
        throw new NotImplementedException();
    }

    @Override
    public ErrorList validateApplicationCA(String module, ContactDetails contactDetails, RulesInformation rulesInformation){
        throw new NotImplementedException();
    }

    @Override
    public ErrorList validateMarkView(String module, ImageSpecification markView, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }

    /*
     * (non-Javadoc)
     * @see eu.ohim.sp.external.adaptors.AbstractWSClient#getErrorCode()
     */
    @Override
    protected String getErrorCode() {
        return "";
    }

    /*
     * (non-Javadoc)
     * @see eu.ohim.sp.external.adaptors.AbstractEsbClient#getSystemConfigurationService()
     */
    @Override
    public ConfigurationService getConfigurationService() {
        return configurationService;
    }

    @Override
    public ErrorList validateForeignRegistration(String module, ForeignRegistration foreignRegistration, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }
}
