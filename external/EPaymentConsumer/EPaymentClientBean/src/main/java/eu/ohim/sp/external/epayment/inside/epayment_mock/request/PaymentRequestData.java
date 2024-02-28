package eu.ohim.sp.external.epayment.inside.epayment_mock.request;

public class PaymentRequestData {
	private String PAN;
	private String payerName;
	private Double feeAmount;
	private String reference;
	private String dtFiling;
	private String paymentId;

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public void setPAN(String PAN) {
		this.PAN = PAN;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setDtFiling(String dtFiling) {
		this.dtFiling = dtFiling;
	}

	public String getPAN() {
		return PAN;
	}

	public String getPayerName() {
		return payerName;
	}

	public Double getFeeAmount() {
		return feeAmount;
	}

	public String getReference() {
		return reference;
	}

	public String getDtFiling() {
		return dtFiling;
	}
}
