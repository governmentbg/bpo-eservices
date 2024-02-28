package eu.ohim.sp.external.ws.exception;

import eu.ohim.sp.external.services.inventor.InventorFault;

import javax.xml.ws.WebFault;

@WebFault(name = "inventorFault", targetNamespace = "http://ohim.eu/sp/services/inventor/v3")
public class InventorFaultException extends Exception{

	private InventorFault faultInfo;
	public InventorFaultException(String message, Throwable stacktrace){
		super(message,stacktrace);
	}

	public InventorFaultException(String message){
		super(message);
	}

	public InventorFaultException(String message, InventorFault fault){
		super(message);
		this.faultInfo=fault;
	}

	public InventorFaultException(String message, InventorFault fault, Throwable stacktrace){
		super(message,stacktrace);
		this.faultInfo=fault;
	}

	public InventorFaultException(Throwable stacktrace, InventorFault fault){
		super(stacktrace);
		this.faultInfo=fault;
	}
	
    /**
     * 
     * @return
     *     returns fault bean: eu.ohim.sp.external.classification.ws.client.ClassificationFault
     */
    public InventorFault getFaultInfo() {
        return faultInfo;
    }
}
