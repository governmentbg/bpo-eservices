package eu.ohim.sp.external.epayment.inside.epayment_mock.request;

import javax.enterprise.context.Dependent;
import java.util.HashMap;
import java.util.Map;

@Dependent
public class RequestPaymentDataConverter {
	private static class Constants {
		private static final String PAN = "PAN";
		private static final String PAYER_NAME = "PAYER_NAME";
		private static final String PAYMENT_FEE_AMOUNT = "FEE_AMOUNT";
		private static final String PAYMENT_REFERENCE = "REFERENCE";
		private static final String PAYMENT_ID = "PAYMENT_ID";
		private static final String DT_FILING = "DT_FILING";
	}

	public Map<String, String> convert(PaymentRequestData data) {
		Map<String, String> result = new HashMap<String, String>();

		result.put(Constants.PAYMENT_ID, data.getPaymentId());
		result.put(Constants.PAN, data.getPAN());
		result.put(Constants.PAYMENT_FEE_AMOUNT, String.valueOf(data.getFeeAmount()));
		result.put(Constants.PAYMENT_REFERENCE, String.valueOf(data.getReference()));
		result.put(Constants.PAYER_NAME, data.getPayerName());
		result.put(Constants.DT_FILING, data.getDtFiling());

		return result;
	}
}
