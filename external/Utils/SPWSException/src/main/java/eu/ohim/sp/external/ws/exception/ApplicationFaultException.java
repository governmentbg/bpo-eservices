package eu.ohim.sp.external.ws.exception;

import javax.xml.ws.WebFault;

import eu.ohim.sp.external.services.application.ApplicationFault;

@WebFault(name = "applicationFault", targetNamespace = "http://ohim.eu/sp/services/application/v3")
public class ApplicationFaultException extends Exception{
	
	private ApplicationFault faultInfo;
	public ApplicationFaultException(String message,Throwable stacktrace){
		super(message,stacktrace);
	}
	
	public ApplicationFaultException(String message){
		super(message);
	}

	public ApplicationFaultException(String message,ApplicationFault fault){
		super(message);
		this.faultInfo=fault;
	}
	
	public ApplicationFaultException(String message,ApplicationFault fault,Throwable stacktrace){
		super(message,stacktrace);
		this.faultInfo=fault;
	}
	
	public ApplicationFaultException(Throwable stacktrace,ApplicationFault fault){
		super(stacktrace);
		this.faultInfo=fault;
	}
    /**
     * 
     * @return
     *     returns fault bean: eu.ohim.sp.external.classification.ws.client.ClassificationFault
     */
    public ApplicationFault getFaultInfo() {
        return faultInfo;
    }
}
