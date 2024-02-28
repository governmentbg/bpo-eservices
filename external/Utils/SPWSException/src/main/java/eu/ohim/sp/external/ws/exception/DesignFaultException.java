package eu.ohim.sp.external.ws.exception;

import eu.ohim.sp.external.services.design.DesignFault;

import javax.xml.ws.WebFault;

@WebFault(name = "designFault", targetNamespace = "http://ohim.eu/sp/services/design-search/v3")
public class DesignFaultException extends Exception{

    /**
     * Java type that goes as soapenv:Fault detail element.
     *
     */
    private DesignFault faultInfo;

	public DesignFaultException(String message, Throwable stacktrace){
		super(message,stacktrace);
	}

	public DesignFaultException(String message){
		super(message);
	}


	public DesignFaultException(Throwable stacktrace, DesignFault fault){
		super(stacktrace);
		this.faultInfo=fault;
	}

    /**
     *
     * @param message
     * @param faultInfo
     */
    public DesignFaultException(String message, DesignFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     *
     * @param message
     * @param faultInfo
     * @param cause
     */
    public DesignFaultException(String message, DesignFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: eu.ohim.sp.external.eu.ohim.sp.external.adapters.classification.ws.client.ClassificationFault
     */
    public DesignFault getFaultInfo() {
        return faultInfo;
    }
}
