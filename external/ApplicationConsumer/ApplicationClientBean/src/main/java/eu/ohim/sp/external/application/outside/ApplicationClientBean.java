package eu.ohim.sp.external.application.outside;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.application.ApplicationBasicKind;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.wrapper.ApplicationNumber;
import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.external.application.ApplicationClient;
import eu.ohim.sp.external.application.ApplicationClientOutside;
import eu.ohim.sp.external.application.outside.ws.client.ApplicationWSClient;
import eu.ohim.sp.external.application.outside.ws.client.ApplicationWSClientService;
import eu.ohim.sp.external.domain.application.NumberingResult;
import eu.ohim.sp.external.utils.AbstractWSClient;
import eu.ohim.sp.external.ws.exception.ApplicationFaultException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.jms.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The User Adapter Service class
 */
@Dependent
@ApplicationClientOutside
public class ApplicationClientBean extends AbstractWSClient implements ApplicationClient {

    private static final Logger LOGGER = Logger.getLogger(ApplicationClientBean.class);

    /**
     * The Constant ADAPTER_NAME.
     */
    protected static final String ADAPTER_NAME = "application";

    private static final String BUSINESS_CONTEXT = "BusinessContext";
    private static final String OFFICE_CODE = "OfficeCode";
    private static final String FILING_NUMBER = "FilingNumber";
    private static final String APPLICATION_URI = "ApplicationURI";

    private static final String COMPONENT_GENERAL = "general";
    private static final String URL_APPLICATION = "sp.getApplication.url";

    @EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private Mapper mapper;

    @Resource(mappedName = "java:/ConnectionFactorySP")
    private ConnectionFactory connectionFactory;

    private ApplicationWSClient applicationWebServiceRef;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        super.init(ADAPTER_NAME);
        mapper = new DozerBeanMapper();

        LOGGER.debug("initializing ApplicationClientService ");
        if (getAdapterEnabled()) {
            applicationWebServiceRef = new ApplicationWSClientService(
                    getWsdlLocation()).getApplicationServicePort();
        }
    }

    @Override
    public Result saveApplication(String office, String user, String provisionalId) {
        return saveApplication(office, user, provisionalId, false);
    }

    /**
     * Makes the call to the client to save an application.
     *
     * @param office        the office code
     * @param user          the user
     * @param provisionalId the provisional id of the application to save
     * @return the result of the call
     */
    @Override
    public Result saveApplication(String office, String user, String provisionalId, boolean finalDraft) {
        Result results = null;
        if (getAdapterEnabled()) {
            eu.ohim.sp.external.domain.common.Result externalResult = null;
            try {
                externalResult = applicationWebServiceRef.saveApplication(office, user, provisionalId, finalDraft);
            } catch (ApplicationFaultException exc) {
                LOGGER.error(" saveApplication ERROR WS SOAP: " + exc.getMessage(), exc);
            }

            if (externalResult != null) {
                results =
                        mapper.map(externalResult, Result.class);
            } else {
                LOGGER.error("getUserPerson. The call to the ws returns a null value.");
            }
        } else {
            LOGGER.info("saveApplication: adapter is not enabled. Then there is no call to the ws.");
        }

        return results;
    }

    /**
     *
     */
    @Override
    public byte[] loadApplication(String office, String user, String provisionalId) {

        byte[] results = null;
        if (getAdapterEnabled()) {
            try {
                results = applicationWebServiceRef.loadApplication(office, user, provisionalId);
            } catch (ApplicationFaultException exc) {
                throw new SPException(exc);
            }
        } else {
            LOGGER.info("saveApplication: adapter is not enabled. Then there is no call to the ws.");
        }

        return results;
    }

    @Override
    public ApplicationNumber getApplicationNumber(String module, String applicationType,
                                                  String provisionalNumber) {
        ApplicationNumber applicationNumber = null;

        if (getAdapterEnabled()) {
            NumberingResult externalResult = null;
            try {
                externalResult = applicationWebServiceRef.getApplicationNumber(module, applicationType, provisionalNumber);
            } catch (Exception exc) {
                LOGGER.error("getApplicationNumber: error in the ws call", exc);
            }

            if (externalResult != null) {
                applicationNumber = mapper.map(externalResult,
                        eu.ohim.sp.core.domain.application.wrapper.ApplicationNumber.class);
            } else {
                LOGGER.error("getApplicationNumber. The call to the ws returns a null value.");
            }
        } else {
            LOGGER.info("getApplicationNumber: adapter is not enabled. Then there is no call to the ws. Mock result");
            applicationNumber = null;
        }
        if (applicationNumber != null) {
            LOGGER.info("APPLICATION NUMBER RETRIEVED IS: " + applicationNumber.getNumber());
        }

        return applicationNumber;
    }

    @Override
    public Boolean checkExistingApplication(String applicationType,
                                            String formName,
                                            String applicationNumber,
                                            String registrationNumber) {
        Boolean existence;

        try {
            existence = applicationWebServiceRef.checkExistingApplication(applicationType, formName, applicationNumber, registrationNumber);
        } catch (ApplicationFaultException e) {
            LOGGER.error(e);
            throw new SPException("Failed to call checkExistingApplication : ", e);
        }
        return existence;
    }

    @Override
    public void notifyApplicationFiling(String office, String module, DraftApplication draftApplication) {
        Connection conn = null;
        LOGGER.debug("START notifyApplicationFiling: module : " + module +
                "filingNumber : " + (draftApplication != null ? draftApplication.getProvisionalId() : "null"));
        try {
            if (draftApplication != null) {
                conn = connectionFactory.createConnection();
                Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Queue queue = session.createQueue("DefaultQueue");
                MapMessage message = session.createMapMessage();

                message.setString(OFFICE_CODE, draftApplication.getOffice());
                message.setString(BUSINESS_CONTEXT, getBusinessContext(draftApplication.getTyApplication()));
                message.setString(FILING_NUMBER, draftApplication.getProvisionalId());
                String url = (configurationService.getValue(URL_APPLICATION, COMPONENT_GENERAL)!=null?
                        configurationService.getValue(URL_APPLICATION, COMPONENT_GENERAL).replace("{provisionalId}", draftApplication.getProvisionalId()) : null);
                message.setString(APPLICATION_URI, StringUtils.defaultIfBlank(url, draftApplication.getProvisionalId()));
                session.createProducer(queue).send(message);

                LOGGER.info("Message written in the queue: " + OFFICE_CODE + "=" + draftApplication.getOffice()
                        + " " + BUSINESS_CONTEXT + "=" + getBusinessContext(draftApplication.getTyApplication()) + " " + FILING_NUMBER + "=" + draftApplication.getProvisionalId() + " " + APPLICATION_URI + "=" + url);
            } else {
                throw new SPException("Missing Information - DraftApplication : null");
            }
        } catch (JMSException e) {
            throw new SPException("Failed to notify Application Filing ", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (JMSException e) {
                    LOGGER.error(e);
                }
            }
        }
        LOGGER.debug("END notifyApplicationFiling: module : " + module +
                "filingNumber : " + draftApplication.getProvisionalId());
    }

    /**
     *
     * @param applicationFormName
     * @return
     */
    private String getBusinessContext(String applicationFormName){
        Map<List<String>, ApplicationBasicKind> mapping = new HashMap<List<String>,ApplicationBasicKind>();
        mapping.put(Arrays.asList("TM_RENEWAL","TM_CHANGE","TM_TRANSFER","TM_OPPOSITION","TM_REVOCATION","TM_INVALIDITY"),
                ApplicationBasicKind.TRADEMARK_SERVICES_EFILING);
        mapping.put(Arrays.asList("DS_RENEWAL","DS_CHANGE","DS_TRANSFER","DS_OPPOSITION","DS_REVOCATION","DS_INVALIDITY"),
                ApplicationBasicKind.DESIGN_SERVICES_EFILING);
        mapping.put(Arrays.asList("DS"), ApplicationBasicKind.DESIGN_EFILING);
        mapping.put(Arrays.asList("TM"), ApplicationBasicKind.TRADEMARK_EFILING);

        for(Map.Entry<List<String>, ApplicationBasicKind> forms : mapping.entrySet()){
            if(forms.getKey().contains(applicationFormName)){
                return forms.getValue().value();
            }
        }
        return "Unknown";
    }

    @Override
    protected String getErrorCode() {
        return "error.generic";
    }

    @Override
    public ConfigurationService getConfigurationService() {
        return configurationService;
    }

}
