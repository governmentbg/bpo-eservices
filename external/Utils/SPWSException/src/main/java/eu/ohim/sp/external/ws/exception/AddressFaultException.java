package eu.ohim.sp.external.ws.exception;

import eu.ohim.sp.external.services.contact.AddressFault;

import javax.xml.ws.WebFault;


@WebFault(name = "addressFault", targetNamespace = "http://ohim.eu/sp/services/address/v3")
public class AddressFaultException extends Exception{

	private AddressFault faultInfo;

	public AddressFaultException(String message, Throwable stacktrace){
		super(message,stacktrace);
	}

	public AddressFaultException(String message){
		super(message);
	}

	public AddressFaultException(String message, AddressFault fault){
		super(message);
		this.faultInfo=fault;
	}

	public AddressFaultException(String message, AddressFault fault, Throwable stacktrace){
		super(message,stacktrace);
		this.faultInfo=fault;
	}

	public AddressFaultException(Throwable stacktrace, AddressFault fault){
		super(stacktrace);
		this.faultInfo=fault;
	}

    /**
     * 
     * @return
     *     returns fault bean: eu.ohim.sp.external.eu.ohim.sp.external.adapters.classification.ws.client.ClassificationFault
     */
    public AddressFault getFaultInfo() {
        return faultInfo;
    }
}
