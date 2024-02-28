package eu.ohim.sp.external.ws.exception;

import javax.xml.ws.WebFault;

import eu.ohim.sp.external.services.epayment.PaymentFault;

@WebFault(name = "paymentFault", targetNamespace = "http://ohim.eu/sp/services/epayment/v3")
public class PaymentFaultException extends Exception{
	
    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private PaymentFault faultInfo;
	
	public PaymentFaultException(String message,Throwable stacktrace){
		super(message,stacktrace);
	}
	
	public PaymentFaultException(String message){
		super(message);
	}
	
	
	public PaymentFaultException(Throwable stacktrace,PaymentFault fault){
		super(stacktrace);
		this.faultInfo=fault;
	}

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public PaymentFaultException(String message, PaymentFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public PaymentFaultException(String message, PaymentFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: eu.ohim.sp.external.classification.ws.client.ClassificationFault
     */
    public PaymentFault getFaultInfo() {
        return faultInfo;
    }
	
}
