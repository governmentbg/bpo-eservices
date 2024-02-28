package eu.ohim.sp.external.epayment.inside.epayment_mock;

import eu.ohim.sp.external.domain.common.ParamsMap;
import eu.ohim.sp.external.domain.epayment.wrapper.PaymentRequest;
import eu.ohim.sp.external.domain.epayment.wrapper.PaymentResult;
import eu.ohim.sp.external.domain.epayment.wrapper.RequestPaymentApplication;
import eu.ohim.sp.external.epayment.inside.epayment_mock.notify.NotifyPaymentResultHandler;
import eu.ohim.sp.external.epayment.inside.epayment_mock.request.RequestPaymentHandler;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class MockEPayment implements EPayment {
	private NotifyPaymentResultHandler nofityPaymentResultHandler;
	private RequestPaymentHandler requestPaymentHandler;

	@Inject
	public MockEPayment(NotifyPaymentResultHandler nofityPaymentResultHandler, RequestPaymentHandler requestPaymentHandler) {
		this.nofityPaymentResultHandler = nofityPaymentResultHandler;
		this.requestPaymentHandler = requestPaymentHandler;
	}

	@Override
	public PaymentResult notifyPaymentResult(ParamsMap map) {
        return nofityPaymentResultHandler.handle(map);
	}

	@Override
	public PaymentRequest requestPayment(RequestPaymentApplication application) {
		return requestPaymentHandler.handle(application);
	}
}
