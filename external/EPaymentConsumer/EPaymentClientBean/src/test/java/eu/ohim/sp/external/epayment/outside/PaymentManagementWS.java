package eu.ohim.sp.external.epayment.outside;

import eu.ohim.sp.external.domain.common.Entry;
import eu.ohim.sp.external.domain.common.Fault;
import eu.ohim.sp.external.domain.common.ParamsMap;
import eu.ohim.sp.external.domain.epayment.BankTransfer;
import eu.ohim.sp.external.domain.epayment.Payment;
import eu.ohim.sp.external.domain.epayment.PaymentKind;
import eu.ohim.sp.external.domain.epayment.PaymentStatusCode;
import eu.ohim.sp.external.domain.epayment.wrapper.PaymentRequest;
import eu.ohim.sp.external.domain.epayment.wrapper.PaymentResult;
import eu.ohim.sp.external.domain.epayment.wrapper.PaymentStatus;
import eu.ohim.sp.external.domain.epayment.wrapper.RequestPaymentApplication;
import eu.ohim.sp.external.domain.person.PersonRoleKind;
import eu.ohim.sp.external.services.epayment.PaymentFault;
import eu.ohim.sp.external.ws.exception.PaymentFaultException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 09/09/13
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
@WebService(serviceName = "EPaymentService", targetNamespace = "http://ohim.eu/sp/services/epayment/v3", portName = "EPaymentServicePort", wsdlLocation = "wsdl/EPaymentService.wsdl")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class PaymentManagementWS implements PaymentManagementWSInterface {

    /**
     *
     * @param map
     * @return
     *         returns com.ohim.sp.ext.payment.ws.client.PaymentResult
     */
    @WebMethod
    public PaymentResult notifyPaymentResult(
            @WebParam(name = "map", targetNamespace = "") ParamsMap map) throws PaymentFaultException {
        if (map.getEntry().isEmpty()) {
            PaymentFault paymentFault = new PaymentFault();
            paymentFault.setReturnedObject(new Fault("system.error.01", "empty map"));

            throw new PaymentFaultException("empty map", paymentFault);
        }

        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setPayment(new Payment(PaymentKind.BANK_TRANSFER, "identifier", "reference", PaymentStatusCode.DONE, new Double(200), "EUR", new Date(), "comment", null, null, new BankTransfer("bankTransferIdentifier", new Date(), "originBankName", "bankDestinationAccount"), null, PersonRoleKind.APPLICANT));
        paymentResult.setPaymentStatus(PaymentStatus.PAYMENT_OK);

        for (Entry entry : map.getEntry()) {
            if (entry.getKey().equals("comment")) {
                paymentResult.setResultMessage(entry.getValue());
            }
        }
        return paymentResult;
    }

    /**
     *
     * @param application
     * @return
     *         returns com.ohim.sp.ext.payment.ws.client.PaymentRequest
     */
    @WebMethod
    public PaymentRequest requestPayment(
            @WebParam(name = "application", targetNamespace = "") RequestPaymentApplication application) throws PaymentFaultException {
        if (application.getApplication() == null) {
            try {
                application.getApplication().getName();
            } catch (NullPointerException e) {
                PaymentFault paymentFault = new PaymentFault();
                paymentFault.setReturnedObject(new Fault("system.error.01", "empty map"));

                throw new PaymentFaultException("empty map", paymentFault);
            }
        }

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setRedirectionParams(new ParamsMap());
        paymentRequest.getRedirectionParams().setEntry(new ArrayList<Entry>());

        Entry entry = new Entry();
        entry.setKey("FLOW_MODE");
        if (application.getParameters()!=null
                && application.getParameters().getEntry()!=null
                && !application.getParameters().getEntry().isEmpty()) {
            entry.setValue(application.getParameters().getEntry().get(0).getValue());
        }

        paymentRequest.getRedirectionParams().getEntry().add(entry);

        entry = new Entry();
        entry.setKey("paymentId");
        entry.setValue(application.getPaymentId());
        paymentRequest.getRedirectionParams().getEntry().add(entry);

        entry = new Entry();
        entry.setKey("applicationNumber");
        entry.setValue(application.getApplication().getValue().getApplicationFilingNumber());
        paymentRequest.getRedirectionParams().getEntry().add(entry);

        entry = new Entry();
        entry.setKey("username");
        entry.setValue(application.getUser().getUserName());
        paymentRequest.getRedirectionParams().getEntry().add(entry);

        return paymentRequest;
    }

}
