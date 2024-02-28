package eu.ohim.sp.core.fee.provided;

import eu.ohim.sp.core.domain.design.DSeServiceApplication;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import eu.ohim.sp.core.fee.FeeCalculationService;
import eu.ohim.sp.external.domain.epayment.Fee;
import eu.ohim.sp.external.services.fee.ObjectFactory;
import eu.ohim.sp.filing.domain.utils.EserviceTransactionUtil;
import eu.ohim.sp.filing.domain.utils.MapperFactory;
import org.dozer.DozerBeanMapper;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

@Stateless
@WebService(targetNamespace = "http://ohim.eu/fsp/services/feemanagement/v3", name = "FeeManagementWS")
@XmlSeeAlso({ObjectFactory.class})
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class DefaultFeeCalculation implements FeeCalculation {

    /** Needed constants for the xml <-> objects */
    private final String TMCONSTANT = "tm";
    private final String DSCONSTANT = "ds";
    private final int PATTERN_SIZE = 3;
    private final String APPLICATION_FORMAT_EXCEPTION = "Bad application type";
    private DozerBeanMapper mapper;

    InitialContext ic;

    protected FeeCalculationService getService() throws NamingException {
        if(ic == null)
            ic = new InitialContext();
        return (FeeCalculationService)ic.lookup("java:global/feeCalculationLocal");
    }

    /**
     * Init
     */
    @PostConstruct
    public void init() {
        mapper = new DozerBeanMapper();
    }

    @Override
    @WebResult(name = "fees", targetNamespace = "")
    @RequestWrapper(localName = "calculateApplicationFees", targetNamespace = "http://ohim.eu/fsp/services/feemanagement/v3", className = "CalculateApplicationFees")
    @WebMethod
    @ResponseWrapper(localName = "calculateApplicationFeesResponse", targetNamespace = "http://ohim.eu/fsp/services/feemanagement/v3", className = "CalculateApplicationFeesResponse")
    public List<Fee> calculateApplicationFees(
            @WebParam(name = "applicationType", targetNamespace = "") String applicationType,
            @WebParam(name = "applicationXML", targetNamespace = "") byte[] applicationXML) throws FeeFault_Exception {

        List<Fee> extFees = null;
        try {
            FeeCalculationService feeService = getService();
            EserviceTransactionUtil eserviceTransactionUtil = (EserviceTransactionUtil) new MapperFactory()
                    .generateEservicesTU();
            // accepted "TM-renewal" "DS-renewal" ...
            IPApplication application = eserviceTransactionUtil.generateIPApplication(applicationXML,
                    getRightName(applicationType, true), false);
            // accepted "tmrenewal" "dsrenewal" ...
            List<eu.ohim.sp.core.domain.payment.Fee> coreFeeList = feeService.calculateFees(
                    getRightName(applicationType, false), applicableObjects(application));
            extFees = new ArrayList<Fee>();

            for (eu.ohim.sp.core.domain.payment.Fee coreFee : coreFeeList) {
                extFees.add(mapper.map(coreFee, Fee.class));
            }
        } catch (NamingException e) {
            throw new FeeFault_Exception(e.getMessage());
        }

        return extFees;
    }

    /**
     * Generate an array of designs or trademarks for fees to be applied
     * 
     * @param application represents an application form
     * @return
     */
    private List<Object> applicableObjects(IPApplication application) {
        List<Object> inputData = new ArrayList<Object>();

        if (application instanceof DSeServiceApplication) {
            for (DesignApplication ds : ((DSeServiceApplication) application).getDesignDetails()) {
                inputData.add(ds);
            }
        } else if (application instanceof TMeServiceApplication) {
            for (LimitedTradeMark ltm : ((TMeServiceApplication) application).getTradeMarks()) {
                inputData.add(ltm);
            }
        }
        return inputData;
    }

    /**
     * Based on the input application type, generates a customized parameter.
     * 
     * @param origin original application type
     * @param upper must begin in uppercase or not
     * @return the customized parameter.
     */
    private String getRightName(String origin, boolean upper) throws FeeFault_Exception {
        String ret = "";
        if (origin != null && origin.length() >= PATTERN_SIZE) {
            String s = origin.substring(0, 2);
            String sr = origin.substring(3);

            if (s.equals(TMCONSTANT)) {
                ret = upper ? TMCONSTANT.toUpperCase() + "-" + sr : TMCONSTANT + sr;
            } else if (s.equals(DSCONSTANT)) {
                ret = upper ? DSCONSTANT.toUpperCase() + "-" + sr : DSCONSTANT + sr;
            } else {
                throw new FeeFault_Exception(APPLICATION_FORMAT_EXCEPTION);
            }
        } else {
            throw new FeeFault_Exception(APPLICATION_FORMAT_EXCEPTION);
        }
        return ret;
    }
}
