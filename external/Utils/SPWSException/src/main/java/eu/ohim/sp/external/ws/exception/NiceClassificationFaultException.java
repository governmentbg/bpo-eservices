package eu.ohim.sp.external.ws.exception;

import javax.xml.ws.WebFault;

import eu.ohim.sp.external.services.classification.nice.NiceClassificationFault;


@WebFault(name = "niceClassificationFault", targetNamespace = "http://ohim.eu/sp/services/nice-classification/v3")
public class NiceClassificationFaultException extends Exception{
	
    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private NiceClassificationFault faultInfo;
	
	public NiceClassificationFaultException(String message, Throwable stacktrace){
		super(message,stacktrace);
	}
	
	public NiceClassificationFaultException(String message){
		super(message);
	}
	
	
	public NiceClassificationFaultException(Throwable stacktrace, NiceClassificationFault fault){
		super(stacktrace);
		this.faultInfo=fault;
	}

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public NiceClassificationFaultException(String message, NiceClassificationFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public NiceClassificationFaultException(String message, NiceClassificationFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: eu.ohim.sp.external.classification.ws.client.ClassificationFault
     */
    public NiceClassificationFault getFaultInfo() {
        return faultInfo;
    }
	
	
}
