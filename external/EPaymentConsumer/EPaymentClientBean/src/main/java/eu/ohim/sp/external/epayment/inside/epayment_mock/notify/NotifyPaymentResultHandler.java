package eu.ohim.sp.external.epayment.inside.epayment_mock.notify;

import eu.ohim.sp.external.domain.common.ParamsMap;
import eu.ohim.sp.external.domain.epayment.wrapper.PaymentRequest;
import eu.ohim.sp.external.domain.epayment.wrapper.PaymentResult;
import eu.ohim.sp.external.domain.epayment.wrapper.PaymentStatus;
import eu.ohim.sp.external.epayment.inside.epayment_mock.util.MapToXsdMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import java.util.HashMap;
import java.util.Map;

@Dependent
public class NotifyPaymentResultHandler {
	private static final String MSG_PAYMENT_OK = "Payment was OK";
	private static final String MSG_PAYMENT_CANCELLED ="Payment was Cancelled";
	private static final String MSG_PAYMENT_ERROR = "There was an Error in the Payment Process";

	private Map<String, StatusAndMessage> statusMap = new HashMap<String, StatusAndMessage>();

	private StatusAndMessage defaultStatusAndMessage = new StatusAndMessage(PaymentStatus.PAYMENT_ERROR, MSG_PAYMENT_ERROR);

	@PostConstruct
	public void init() {
		statusMap.put(NotifyPaymentStatus.ERROR, defaultStatusAndMessage);
		statusMap.put(NotifyPaymentStatus.SUCCESS, new StatusAndMessage(PaymentStatus.PAYMENT_OK, MSG_PAYMENT_OK));
		statusMap.put(NotifyPaymentStatus.CANCELLED, new StatusAndMessage(PaymentStatus.PAYMENT_CANCELLED, MSG_PAYMENT_CANCELLED));
	}

	public PaymentResult handle(ParamsMap paramsMap) {
		NotifyPaymentResultRequest request = NotifyPaymentResultRequest.create(MapToXsdMap.convertToMap(paramsMap));

		PaymentResult paymentResult = new PaymentResult();

		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setRedirectionParams(paramsMap);
		paymentRequest.setPaymentRequestId(request.getPaymentId());

		paymentResult.setPaymentRequest(paymentRequest);

		StatusAndMessage statusAndMessage = defaultStatusAndMessage;

		if (statusMap.containsKey(request.getStatus())) {
			statusAndMessage = statusMap.get(request.getStatus());
		}

		paymentResult.setPaymentStatus(statusAndMessage.getPaymentStatus());
		paymentResult.setResultMessage(statusAndMessage.getMessage());

		return paymentResult;
	}

	private static class StatusAndMessage {
		private PaymentStatus paymentStatus;
		private String message;

		private StatusAndMessage(PaymentStatus paymentStatus, String message) {
			this.paymentStatus = paymentStatus;
			this.message = message;
		}

		private PaymentStatus getPaymentStatus() {
			return paymentStatus;
		}

		private String getMessage() {
			return message;
		}
	}
}
