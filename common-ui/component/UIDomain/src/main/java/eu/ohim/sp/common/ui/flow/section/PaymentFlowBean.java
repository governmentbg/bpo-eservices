package eu.ohim.sp.common.ui.flow.section;

import java.util.List;

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.payment.PayerTypeForm;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PayerTypes.PayerType;

public interface PaymentFlowBean  extends FlowBean{

	  public PaymentForm getPaymentForm();

	  public void setPaymentForm(PaymentForm paymentForm);
	  
	  public List<PayerTypeForm> getPayerTypes(List<PayerType> activePayerTypes);
	
}
