package eu.ohim.sp.external.ws.exception;

import javax.xml.ws.WebFault;

import eu.ohim.sp.external.services.trademark.TrademarkFault;

@WebFault(name = "trademarkFault", targetNamespace = "http://ohim.eu/sp/services/trademark-search/v3")
public class TradeMarkFaultException extends Exception{
	
    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private TrademarkFault faultInfo;
	
	public TradeMarkFaultException(String message,Throwable stacktrace){
		super(message,stacktrace);
	}
	
	public TradeMarkFaultException(String message){
		super(message);
	}
	
	
	public TradeMarkFaultException(Throwable stacktrace, TrademarkFault fault){
		super(stacktrace);
		this.faultInfo=fault;
	}

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public TradeMarkFaultException(String message, TrademarkFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public TradeMarkFaultException(String message, TrademarkFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: eu.ohim.sp.external.eu.ohim.sp.external.adapters.classification.ws.client.ClassificationFault
     */
    public TrademarkFault getFaultInfo() {
        return faultInfo;
    }
}
