package eu.ohim.sp.external.ws.exception;

import javax.xml.ws.WebFault;

import eu.ohim.sp.external.services.representative.RepresentativeFault;

@WebFault(name = "representativeFault", targetNamespace = "http://ohim.eu/sp/services/representative/v3")
public class RepresentativeFaultException extends Exception{
	
    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private RepresentativeFault faultInfo;
	
	public RepresentativeFaultException(String message,Throwable stacktrace){
		super(message,stacktrace);
	}
	
	public RepresentativeFaultException(String message){
		super(message);
	}
	
	
	public RepresentativeFaultException(Throwable stacktrace,RepresentativeFault fault){
		super(stacktrace);
		this.faultInfo=fault;
	}

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public RepresentativeFaultException(String message, RepresentativeFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public RepresentativeFaultException(String message, RepresentativeFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: eu.ohim.sp.external.classification.ws.client.ClassificationFault
     */
    public RepresentativeFault getFaultInfo() {
        return faultInfo;
    }
}
