package eu.ohim.sp.external.epayment.inside.epayment_mock.notify;

import java.util.Map;

public class NotifyPaymentResultRequest {
	public static final String PAYMENT_ID = "PAYMENT_ID";
	public static final String STATUS = "STATUS";

	private String status;
	private String paymentId;

	public static NotifyPaymentResultRequest create(Map<String, String> map) {
		NotifyPaymentResultRequest request = new NotifyPaymentResultRequest();
		request.status = map.get(STATUS);
		request.paymentId = map.get(PAYMENT_ID);

		return request;
	}

	public String getStatus() {
		return status;
	}

	public String getPaymentId() {
		return paymentId;
	}
}
