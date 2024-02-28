package eu.ohim.sp.external.ws.exception;

import javax.xml.ws.WebFault;

import eu.ohim.sp.external.services.applicant.ApplicantFault;

@WebFault(name = "applicantFault", targetNamespace = "http://ohim.eu/sp/services/applicant/v3")
public class ApplicantFaultException extends Exception{
	
	private ApplicantFault faultInfo;

	public ApplicantFaultException(String message,Throwable stacktrace){
		super(message,stacktrace);
	}
	
	public ApplicantFaultException(String message){
		super(message);
	}
	
	public ApplicantFaultException(String message,ApplicantFault fault){
		super(message);
		this.faultInfo=fault;
	}

	public ApplicantFaultException(String message, ApplicantFault fault, Throwable stacktrace){
		super(message,stacktrace);
		this.faultInfo=fault;
	}

	public ApplicantFaultException(Throwable stacktrace, ApplicantFault fault){
		super(stacktrace);
		this.faultInfo=fault;
	}

    /**
     * 
     * @return
     *     returns fault bean: eu.ohim.sp.external.eu.ohim.sp.external.adapters.classification.ws.client.ClassificationFault
     */
    public ApplicantFault getFaultInfo() {
        return faultInfo;
    }
}
