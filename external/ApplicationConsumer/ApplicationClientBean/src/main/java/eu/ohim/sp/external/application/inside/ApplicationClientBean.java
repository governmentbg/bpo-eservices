package eu.ohim.sp.external.application.inside;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.application.ApplicationBasicKind;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.wrapper.ApplicationNumber;
import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.external.application.ApplicationClient;
import eu.ohim.sp.external.application.ApplicationClientInside;
import eu.ohim.sp.external.domain.application.NumberingResult;
import eu.ohim.sp.external.domain.common.Fault;
import eu.ohim.sp.external.services.application.ApplicationFault;
import eu.ohim.sp.external.utils.AdapterEnabled;
import eu.ohim.sp.external.utils.AdapterSetup;
import eu.ohim.sp.external.ws.exception.ApplicationFaultException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import javax.jms.*;
import javax.jms.Queue;
import java.io.IOException;
import java.util.*;

/**
 * The User Adapter Service class
 */
@Dependent
@ApplicationClientInside
public class ApplicationClientBean implements ApplicationClient {

    private static final Logger LOGGER = Logger.getLogger(ApplicationClientBean.class);

    public static final String APPLICATION_XML = "application.epub";

    public static final String OFFICE_CODE = "EM";

    public static final String ILLEGAL_ARGUMENT_ERROR_CODE = "100";
    public static final String MISSING_MANDATORY_PARAMETERS = "Missing mandatory parameter(s)";

    public static final String SERVER_ERROR_CODE = "400";
    public static final String UNEXPECTED_ERROR = "An unexpected error has occurred";

    private static final String BUSINESS_CONTEXT = "BusinessContext";
    private static final String OFFICE_CODE_QUEUE = "OfficeCode";
    private static final String FILING_NUMBER = "FilingNumber";
    private static final String APPLICATION_URI = "ApplicationURI";

    private static final String COMPONENT_GENERAL = "general";
    private static final String URL_APPLICATION = "sp.getApplication.url";

    @Resource(mappedName = "java:/ConnectionFactorySP")
    private ConnectionFactory connectionFactory;

    @EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private Mapper mapper;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        mapper = new DozerBeanMapper();
    }

    @Override
    @Interceptors({AdapterSetup.Application.class, AdapterEnabled.class})
    public ApplicationNumber getApplicationNumber(String module, String applicationType, String provisionalNumber) {
        NumberingResult externalResult = null;
        ApplicationNumber applicationNumber = null;
        try {
            if (module == null || applicationType == null) {
                throwMissingMandatoryParametersException();
            }
            if(provisionalNumber != null) {
                externalResult = new NumberingResult("TEST" + provisionalNumber, Calendar.getInstance().getTime());
            } else {
                externalResult = getNumberingResult();
            }
        } catch (Exception e) {
            LOGGER.error("getApplicationNumber: error in the call", e);
        }
        if (externalResult != null) {
            applicationNumber = mapper.map(externalResult,
                    eu.ohim.sp.core.domain.application.wrapper.ApplicationNumber.class);
        } else {
            LOGGER.error("getApplicationNumber returns a null value.");
        }
        return applicationNumber;
    }

    @Override
    @Interceptors({AdapterSetup.Application.class, AdapterEnabled.class})
    public Result saveApplication(String office, String user, String provisionalId, boolean finalDraft) {
        Result results = null;
        eu.ohim.sp.external.domain.common.Result externalResult = null;
        try {
            if(provisionalId == null || user == null) {
                throwMissingMandatoryParametersException();
            }
            externalResult =  new eu.ohim.sp.external.domain.common.Result("success", "OK");
        } catch (Exception e) {
            LOGGER.error(" saveApplication ERROR: " + e.getMessage(), e);
        }
        if (externalResult != null) {
            results = mapper.map(externalResult, Result.class);
        } else {
            LOGGER.error("saveApplication returns a null value.");
        }
        return results;
    }

    @Override
    @Interceptors({AdapterSetup.Application.class, AdapterEnabled.class})
    public Result saveApplication(String office, String user, String provisionalId) {
        return saveApplication(office, user, provisionalId, false);
    }

    @Override
    public Boolean checkExistingApplication(String applicationType, String formName, String applicationNumber, String registrationNumber) {
        try {
            if(applicationType == null || (applicationNumber == null && registrationNumber == null)) {
                throwMissingMandatoryParametersException();
            }
        } catch (Exception e) {
            LOGGER.error(e);
            throw new SPException("Failed to call checkExistingApplication : ", e);
        }
        return false;
    }

    @Override
    @Interceptors({AdapterSetup.Application.class, AdapterEnabled.class})
    public byte[] loadApplication(String office, String user, String provisionalId) {
        byte[] results;
        try {
            if(provisionalId == null) {
                throwMissingMandatoryParametersException();
            }
            try {
                results = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream(APPLICATION_XML));
            } catch (IOException e) {
                Fault fault = new Fault(SERVER_ERROR_CODE, UNEXPECTED_ERROR);
                ApplicationFault applicationFault = new ApplicationFault(fault);
                throw new ApplicationFaultException(e.getMessage(), applicationFault);
            }
        } catch (Exception e) {
            throw new SPException(e);
        }
        return results;
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

                message.setString(OFFICE_CODE_QUEUE, draftApplication.getOffice());
                message.setString(BUSINESS_CONTEXT, getBusinessContext(draftApplication.getTyApplication()));
                message.setString(FILING_NUMBER, draftApplication.getProvisionalId());
                String url = (configurationService.getValue(URL_APPLICATION, COMPONENT_GENERAL)!=null?
                        configurationService.getValue(URL_APPLICATION, COMPONENT_GENERAL).replace("{provisionalId}", draftApplication.getProvisionalId()) : null);
                message.setString(APPLICATION_URI, StringUtils.defaultIfBlank(url, draftApplication.getProvisionalId()));
                session.createProducer(queue).send(message);

                LOGGER.info("Message written in the queue: " + OFFICE_CODE_QUEUE + "=" + draftApplication.getOffice()
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

    private NumberingResult getNumberingResult() {
        String number = OFFICE_CODE + Calendar.getInstance().get(Calendar.YEAR);
        Random random = new Random();
        number += String.format("%09d",  random.nextInt(99999) + 1);
        System.out.println(Calendar.getInstance().getTime());
        return new NumberingResult(number, Calendar.getInstance().getTime());
    }

    private void throwMissingMandatoryParametersException() throws ApplicationFaultException {
        Fault fault = new Fault(ILLEGAL_ARGUMENT_ERROR_CODE, MISSING_MANDATORY_PARAMETERS);
        ApplicationFault applicationFault = new ApplicationFault(fault);
        throw new ApplicationFaultException(MISSING_MANDATORY_PARAMETERS, applicationFault);
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
}
