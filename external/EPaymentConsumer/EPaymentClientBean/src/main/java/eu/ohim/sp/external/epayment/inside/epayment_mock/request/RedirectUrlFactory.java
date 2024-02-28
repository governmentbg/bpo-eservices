package eu.ohim.sp.external.epayment.inside.epayment_mock.request;

import eu.ohim.sp.external.domain.epayment.wrapper.PaymentRequestParam;
import eu.ohim.sp.external.domain.epayment.wrapper.RequestPaymentApplication;
import eu.ohim.sp.external.epayment.inside.epayment_mock.util.MapToXsdMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.enterprise.context.Dependent;
import java.util.Map;

@Dependent
public class RedirectUrlFactory {
	
	private static final Logger logger = Logger.getLogger(RedirectUrlFactory.class);
	private String urlMockPaymentEnvironment;

	public RedirectUrlFactory() {
		this.urlMockPaymentEnvironment = "http://www.google.com";
	}

	public RedirectUrlFactory(String urlMockPaymentEnvironment) {
		this.urlMockPaymentEnvironment = urlMockPaymentEnvironment;
	}

	public String createRedirectUrl(RequestPaymentApplication requestPaymentApplication) {
		Map<String, String> params = MapToXsdMap.convertToMap(requestPaymentApplication.getParameters());
		
		String callBackSource =params.get("INVOKER_URL");
		 
		String callBack=urlMockPaymentEnvironment;
		
		if (!StringUtils.isEmpty(callBackSource)){
			callBack=callBackSource+"/mockpayment";
			logger.info("createRedirectUrl :: detected URL to return["+callBack+"]");
		}else{
			logger.info("createRedirectUrl :: NOT detected URL to return :: RETURNING DEFAULT ]");
		}

		StringBuilder sb= new StringBuilder()
				.append(callBack)
				.append("?applicationFilingNumber=").append(requestPaymentApplication.getApplication().getValue().getApplicationFilingNumber())
				.append("&flowKey=").append(params.get(PaymentRequestParam.FLOW_KEY.value()))
				.append("&flowMode=").append(params.get(PaymentRequestParam.FLOW_MODE.value()));

		logger.info("createRedirectUrl :: FINAL url ["+sb.toString()+"]");
		return sb.toString();
	}
}
