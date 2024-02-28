package eu.ohim.sp.external.register.outside;

import eu.ohim.sp.external.domain.common.Fault;
import eu.ohim.sp.external.domain.trademark.TradeMark;
import eu.ohim.sp.external.services.trademark.TrademarkFault;
import eu.ohim.sp.external.ws.exception.TradeMarkFaultException;
import org.apache.commons.lang.StringUtils;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "TrademarkSearchService", targetNamespace = "http://ohim.eu/sp/services/trademark-search/v3", portName = "TrademarkSearchServicePort", wsdlLocation = "wsdl/TrademarkSearchService.wsdl")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class TrademarkManagementWS implements TrademarkManagementWSInterface{

    /**
     * Gets the trade mark.
     * 
     * @param office the office
     * @param tradeMarkId the trade mark id
     * @return the trade mark
     */
    @WebMethod
    public TradeMark getTradeMark(@WebParam(name = "office", targetNamespace = "") String office,
                                  @WebParam(name = "tradeMarkId", targetNamespace = "") String tradeMarkId) throws TradeMarkFaultException {
        if (StringUtils.isBlank(office)) {
            try {
                StringUtils.defaultIfBlank(office, null).equals("error");
            } catch (NullPointerException e) {
                TrademarkFault trademarkFault = new TrademarkFault();
                trademarkFault.setReturnedObject(new Fault());
                trademarkFault.getReturnedObject().setCode("error.code");
                trademarkFault.getReturnedObject().setMessage("system error");
                throw new TradeMarkFaultException("system error", trademarkFault);
            }
        }

        TradeMark toReturn = new TradeMark();
        toReturn.setReceivingOffice(office);
        toReturn.setApplicationNumber(tradeMarkId);
        return toReturn;
    }

    @WebMethod
    public TradeMark getInternationalTradeMark(@WebParam(name = "office", targetNamespace = "") String office,
                                               @WebParam(name = "tradeMarkId", targetNamespace = "") String tradeMarkId,
                                               @WebParam(name = "option", targetNamespace = "") Boolean extraImport) throws TradeMarkFaultException {
        if (StringUtils.isBlank(office)) {
            try {
                StringUtils.defaultIfBlank(office, null).equals("error");
            } catch (NullPointerException e) {
                TrademarkFault trademarkFault = new TrademarkFault();
                trademarkFault.setReturnedObject(new Fault());
                trademarkFault.getReturnedObject().setCode("error.code");
                trademarkFault.getReturnedObject().setMessage("system error");
                throw new TradeMarkFaultException("system error", trademarkFault, e);
            }
        }

        TradeMark toReturn = new TradeMark();
        toReturn.setReceivingOffice(office);
        toReturn.setApplicationNumber(tradeMarkId);
        return toReturn;
    }

    /**
     * Gets the trade mark autocomplete.
     * 
     * @param office the office
     * @param text the text
     * @param numberOfResults the number of results
     * @return json with the result of the autocomplete search
     */
    @WebMethod
    public String getTradeMarkAutocomplete(
            @WebParam(name = "office", targetNamespace = "http://ohim.eu/sp/domain/common/v3") String office,
            @WebParam(name = "text", targetNamespace = "http://ohim.eu/sp/domain/common/v3") String text,
            @WebParam(name = "numberOfResults", targetNamespace =  "http://ohim.eu/sp/domain/common/v3" ) int numberOfResults) throws TradeMarkFaultException {
        if (StringUtils.isBlank(office)) {
            TrademarkFault trademarkFault = new TrademarkFault();
            trademarkFault.setReturnedObject(new Fault());
            trademarkFault.getReturnedObject().setCode("error.code");
            trademarkFault.getReturnedObject().setMessage("Business error");
            throw new TradeMarkFaultException("Business Error", trademarkFault);
        }

        return "getTradeMarkAutocomplete"+office+text;
    }

    /**
     * Gets the preclearance report.
     * 
     * @param office the office
     * @param tradeMark the trademark
     * @return the trademark list preclearance report
     */
    @WebMethod
    public List<TradeMark> getPreclearanceReport(
            @WebParam(name = "office", targetNamespace = "")String office,
            @WebParam(name = "tradeMark", targetNamespace = "")TradeMark tradeMark) throws TradeMarkFaultException {
        ArrayList<TradeMark> tradeMarks = new ArrayList<TradeMark>();

        if (StringUtils.isBlank(office)) {
            try {
                //sure way to produce a dummy null pointer excpetion
                StringUtils.defaultIfBlank(office, null).equals("error");
            } catch (NullPointerException e) {
                TrademarkFault trademarkFault = new TrademarkFault();
                trademarkFault.setReturnedObject(new Fault());
                trademarkFault.getReturnedObject().setCode("error.code");
                trademarkFault.getReturnedObject().setMessage("system error");
                throw new TradeMarkFaultException(e, trademarkFault);
            }
        }
        System.out.println(office);
        System.out.println(office);
        System.out.println(office);
        System.out.println(tradeMark.getApplicationLanguage());
        System.out.println(tradeMark.getApplicationLanguage());
        System.out.println(tradeMark.getApplicationLanguage());

        TradeMark trademark = new TradeMark();
        trademark.setReceivingOffice(office);
        trademark.setApplicationLanguage(tradeMark.getApplicationLanguage());
        tradeMarks.add(trademark);

        return tradeMarks;
    }

}
