package eu.ohim.sp.external.ws.exception;

import javax.xml.ws.WebFault;

import eu.ohim.sp.external.services.user.UserFault;

@WebFault(name = "userFault", targetNamespace = "http://ohim.eu/sp/services/user-search/v3")
public class UserFaultException extends Exception{
	
    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private UserFault faultInfo;
	
	public UserFaultException(String message,Throwable stacktrace){
		super(message,stacktrace);
	}
	
	public UserFaultException(String message){
		super(message);
	}
	
	
	public UserFaultException(Throwable stacktrace,UserFault fault){
		super(stacktrace);
		this.faultInfo=fault;
	}

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public UserFaultException(String message, UserFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public UserFaultException(String message, UserFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: eu.ohim.sp.external.classification.ws.client.ClassificationFault
     */
    public UserFault getFaultInfo() {
        return faultInfo;
    }
}
