package eu.ohim.sp.external.ws.exception;

import javax.xml.ws.WebFault;

import eu.ohim.sp.external.services.resource.ResourceFault;

@WebFault(name = "resourceFault", targetNamespace = "http://ohim.eu/sp/services/resource/v3")
public class ResourceFaultException  extends Exception{
	private ResourceFault faultInfo;
	public ResourceFaultException(String message,Throwable stacktrace){
		super(message,stacktrace);
	}
	
	public ResourceFaultException(String message){
		super(message);
	}
	
	public ResourceFaultException(String message,ResourceFault fault){
		super(message);
		this.faultInfo=fault;
	}

	public ResourceFaultException(String message,ResourceFault fault,Throwable stacktrace){
		super(message,stacktrace);
		this.faultInfo=fault;
	}
	
	public ResourceFaultException(Throwable stacktrace,ResourceFault fault){
		super(stacktrace);
		this.faultInfo=fault;
	}
	
    /**
     * 
     * @return
     *     returns fault bean: eu.ohim.sp.external.classification.ws.client.ClassificationFault
     */
    public ResourceFault getFaultInfo() {
        return faultInfo;
    }
}
