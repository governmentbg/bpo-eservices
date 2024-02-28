package eu.ohim.sp.external.ws.exception;

import eu.ohim.sp.external.services.classification.locarno.LocarnoClassificationFault;

import javax.xml.ws.WebFault;

@WebFault(name = "locarnoClassificationFault", targetNamespace = "http://ohim.eu/sp/services/locarno-classification/v3")
public class LocarnoClassificationFaultException extends Exception{

    /**
     * Java type that goes as soapenv:Fault detail element.
     *
     */
    private LocarnoClassificationFault faultInfo;

	public LocarnoClassificationFaultException(String message, Throwable stacktrace){
		super(message,stacktrace);
	}

	public LocarnoClassificationFaultException(String message){
		super(message);
	}


	public LocarnoClassificationFaultException(Throwable stacktrace, LocarnoClassificationFault fault){
		super(stacktrace);
		this.faultInfo=fault;
	}

    /**
     *
     * @param message
     * @param faultInfo
     */
    public LocarnoClassificationFaultException(String message, LocarnoClassificationFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     *
     * @param message
     * @param faultInfo
     * @param cause
     */
    public LocarnoClassificationFaultException(String message, LocarnoClassificationFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: eu.ohim.sp.external.eu.ohim.sp.external.adapters.classification.ws.client.ClassificationFault
     */
    public LocarnoClassificationFault getFaultInfo() {
        return faultInfo;
    }
	
	
}
