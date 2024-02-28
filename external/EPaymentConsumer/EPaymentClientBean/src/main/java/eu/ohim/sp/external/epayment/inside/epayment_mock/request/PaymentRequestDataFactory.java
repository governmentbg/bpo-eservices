package eu.ohim.sp.external.epayment.inside.epayment_mock.request;

import eu.ohim.sp.external.domain.application.IPApplication;
import eu.ohim.sp.external.domain.epayment.PaymentFee;
import eu.ohim.sp.external.epayment.inside.epayment_mock.PaymentConstants;

import javax.enterprise.context.Dependent;
import java.text.SimpleDateFormat;
import java.util.Date;

@Dependent
public class PaymentRequestDataFactory {
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public PaymentRequestData create(IPApplication ipApplication) {
		IPApplicationWrapper wrapper = new IPApplicationWrapper(ipApplication);

		PaymentRequestData data = new PaymentRequestData();

		if (ipApplication.getApplicationFilingDate() != null) {
			data.setDtFiling(format(ipApplication.getApplicationFilingDate()));
		}

		data.setPaymentId(PaymentConstants.PAYMENT_ID);
		data.setPAN(ipApplication.getApplicationFilingNumber());

		if (wrapper.hasPayments()) {
			PaymentFee fee = wrapper.getPaymentFee();

			data.setFeeAmount(fee.getAmount());
			data.setReference(fee.getReference());

			if (wrapper.hasPayerName()) {
				data.setPayerName(wrapper.getPayerFullName());
			}
		}

		return data;
	}

	private static class IPApplicationWrapper {
		private IPApplication ipApplication;

		private IPApplicationWrapper(IPApplication ipApplication) {
			this.ipApplication = ipApplication;
		}

		public boolean hasPayments() {
			return ipApplication.getPayments() != null && ipApplication.getPayments().size() > 0;
		}


		public PaymentFee getPaymentFee() {
			return ipApplication.getPayments().get(0);
		}

		public boolean hasPayerName() {
			PaymentFee fee = getPaymentFee();

			return fee.getPayer() != null && fee.getPayer().getName() != null;
		}

		public String getPayerFullName() {
			PaymentFee fee = getPaymentFee();

			return fee.getPayer().getName().getFirstName() + " " + fee.getPayer().getName().getLastName();
		}
	}

	private static String format(Date date) {
        if (date == null) {
            return "";
        } else {
            synchronized(SIMPLE_DATE_FORMAT) {
                return SIMPLE_DATE_FORMAT.format(date);
            }
        }
	}

}
