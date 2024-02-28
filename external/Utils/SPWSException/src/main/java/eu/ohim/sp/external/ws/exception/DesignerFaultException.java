package eu.ohim.sp.external.ws.exception;

import eu.ohim.sp.external.services.designer.DesignerFault;

import javax.xml.ws.WebFault;

@WebFault(name = "designerFault", targetNamespace = "http://ohim.eu/sp/services/designer/v3")
public class DesignerFaultException extends Exception{

	private DesignerFault faultInfo;
	public DesignerFaultException(String message, Throwable stacktrace){
		super(message,stacktrace);
	}

	public DesignerFaultException(String message){
		super(message);
	}

	public DesignerFaultException(String message, DesignerFault fault){
		super(message);
		this.faultInfo=fault;
	}

	public DesignerFaultException(String message, DesignerFault fault, Throwable stacktrace){
		super(message,stacktrace);
		this.faultInfo=fault;
	}

	public DesignerFaultException(Throwable stacktrace, DesignerFault fault){
		super(stacktrace);
		this.faultInfo=fault;
	}
	
    /**
     * 
     * @return
     *     returns fault bean: eu.ohim.sp.external.classification.ws.client.ClassificationFault
     */
    public DesignerFault getFaultInfo() {
        return faultInfo;
    }
}
