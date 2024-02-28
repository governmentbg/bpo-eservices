package eu.ohim.sp.external.epayment.inside.epayment_mock.request;

import eu.ohim.sp.external.domain.application.IPApplication;
import eu.ohim.sp.external.domain.epayment.wrapper.PaymentRequest;
import eu.ohim.sp.external.domain.epayment.wrapper.RequestPaymentApplication;
import eu.ohim.sp.external.epayment.inside.epayment_mock.util.MapToXsdMap;
import org.apache.log4j.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Map;

@Dependent
public class RequestPaymentHandler {
	
	private static final Logger logger = Logger.getLogger(RequestPaymentHandler.class);
	private PaymentRequestDataFactory paymentRequestDataFactory;
	private RequestPaymentDataConverter converter;
	private RedirectUrlFactory redirectUrlFactory;

	@Inject
	public RequestPaymentHandler(PaymentRequestDataFactory paymentRequestDataFactory, RequestPaymentDataConverter converter, RedirectUrlFactory redirectUrlFactory) {
		this.paymentRequestDataFactory = paymentRequestDataFactory;
		this.converter = converter;
		this.redirectUrlFactory = redirectUrlFactory;
	}

	public PaymentRequest handle(RequestPaymentApplication application) {
		IPApplication ipApplication = application.getApplication().getValue();

		PaymentRequestData requestData = paymentRequestDataFactory.create(ipApplication);
		Map<String, String> responseMap = converter.convert(requestData);
		
		for (Map.Entry<String, String> entry : responseMap.entrySet()) {
			logger.info("KEY ["+entry.getKey()+"]["+ entry.getValue()+"]");
		}
		

		PaymentRequest paymentRequest = new PaymentRequest();

		paymentRequest.setRedirectionParams(MapToXsdMap.convertToXsdMap(responseMap));
		paymentRequest.setRedirectionURL(redirectUrlFactory.createRedirectUrl(application));

		return paymentRequest;
	}

}
