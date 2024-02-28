package eu.ohim.sp.external.epayment.outside;

import eu.ohim.sp.external.domain.common.ParamsMap;
import eu.ohim.sp.external.domain.epayment.wrapper.PaymentRequest;
import eu.ohim.sp.external.domain.epayment.wrapper.PaymentResult;
import eu.ohim.sp.external.domain.epayment.wrapper.RequestPaymentApplication;
import eu.ohim.sp.external.ws.exception.PaymentFaultException;
import eu.ohim.sp.external.domain.common.ParamsMap;
import eu.ohim.sp.external.domain.epayment.wrapper.PaymentRequest;
import eu.ohim.sp.external.domain.epayment.wrapper.RequestPaymentApplication;
import eu.ohim.sp.external.ws.exception.PaymentFaultException;

import javax.jws.WebService;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 09/09/13
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
@WebService
public interface PaymentManagementWSInterface {

    /**
     *
     * @param map
     * @return
     *         returns eu.ohim.sp.external.payment.ws.client.PaymentResult
     */
    public PaymentResult notifyPaymentResult(ParamsMap map) throws PaymentFaultException;

    /**
     *
     * @param requestPaymentApplication
     * @return
     *         returns eu.ohim.sp.external.payment.ws.client.PaymentRequest
     */
    public PaymentRequest requestPayment(
            RequestPaymentApplication requestPaymentApplication) throws PaymentFaultException;



}
