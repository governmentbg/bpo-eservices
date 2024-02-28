package eu.ohim.sp.external.ws.exception;

import javax.xml.ws.WebFault;

import eu.ohim.sp.external.services.fee.FeeFault;

@WebFault(name = "feeFault", targetNamespace = "http://ohim.eu/sp/services/fee-calculation/v3")
public class FeeFaultException extends Exception{

    private FeeFault faultInfo;

    public FeeFaultException(String message,Throwable stacktrace){
        super(message,stacktrace);
    }

    public FeeFaultException(String message){
        super(message);
    }

    public FeeFaultException(String message,FeeFault fault){
        super(message);
        this.faultInfo=fault;
    }

    public FeeFaultException(String message,FeeFault fault,Throwable stacktrace){
        super(message,stacktrace);
        this.faultInfo=fault;
    }

    public FeeFaultException(Throwable stacktrace,FeeFault fault){
        super(stacktrace);
        this.faultInfo=fault;
    }
    /**
     *
     * @return
     *     returns fault bean: eu.ohim.sp.external.classification.ws.client.ClassificationFault
     */
    public FeeFault getFaultInfo() {
        return faultInfo;
    }
}
