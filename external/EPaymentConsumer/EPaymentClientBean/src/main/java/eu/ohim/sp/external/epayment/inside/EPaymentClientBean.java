package eu.ohim.sp.external.epayment.inside;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.design.DSeServiceApplication;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequest;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequestParam;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentResult;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.core.domain.user.User;
import eu.ohim.sp.external.domain.common.Entry;
import eu.ohim.sp.external.domain.common.ParamsMap;
import eu.ohim.sp.external.domain.epayment.wrapper.RequestPaymentApplication;
import eu.ohim.sp.external.epayment.EPaymentClient;
import eu.ohim.sp.external.epayment.EPaymentClientInside;
import eu.ohim.sp.external.epayment.inside.epayment_mock.MockEPayment;
import eu.ohim.sp.external.epayment.outside.ws.client.PaymentWSClientService;
import eu.ohim.sp.external.utils.AdapterEnabled;
import eu.ohim.sp.external.utils.AdapterSetup;
import eu.ohim.sp.external.utils.CorePersonKindToExternalPersonKind;
import org.apache.log4j.Logger;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PaymentAdapterService implementation
 * It has the methods to make the invocations to the specific PO ESB Adapter
 *
 * @author soriama
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent @EPaymentClientInside
public class EPaymentClientBean implements EPaymentClient {


    protected static final String ADAPTER_NAME = "payment";

    /**
     * The Constant logger.
     */
    private static final Logger LOGGER = Logger
            .getLogger(EPaymentClientBean.class);

    /**
     * The system configuration service.
     */
	@EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private DozerBeanMapper mapper;

    @Inject
    private MockEPayment epayment;

    /**
     * Initialization method that reads the Adapter properties file and set the payment_url
     */
    @PostConstruct
    public void init() {
        List<String> customConverters = new ArrayList<String>();
        customConverters.add("mapping.xml");
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(customConverters);

        List<CustomConverter> customConvertersx = new ArrayList<CustomConverter>();
        customConvertersx.add(new CorePersonKindToExternalPersonKind());
        mapper.setCustomConverters(customConvertersx);
    }


    /**
     * This method creates a wrapper with the objects sent as parameters
     * and invokes the requestPayment service in the ESB
     *
     * @param application:         TradeMarkApplication to be paid
     * @param paymentId:  ID of the payment transaction. If not null it means it was tried to pay previously
     * @param parameters: Map with parameters for the payment request.
     *                    See {@link PaymentRequestParam}.
     * @return: the PaymentRequest object returned by the ESB
     */
    @Override
    @Interceptors({AdapterSetup.Payment.class, AdapterEnabled.class})
    public PaymentRequest requestPayment(IPApplication application, String paymentId, User user,
                                         Map<PaymentRequestParam, String> parameters) {

        PaymentRequest result = null;

        // Create the wrapper object
        RequestPaymentApplication wrapperApplication = wrapApplicationToRequest(application, paymentId, user);

        if (wrapperApplication != null) {

            //set the paramters map of the external service
            ParamsMap externalParams = new ParamsMap();
            for (Map.Entry entryMap : parameters.entrySet()) {
                externalParams.getEntry().add(new Entry(entryMap.getKey().toString(), entryMap.getValue().toString()));
            }
            wrapperApplication.setParameters(externalParams);

            eu.ohim.sp.external.domain.epayment.wrapper.PaymentRequest externalResult = null;
            externalResult = epayment.requestPayment(wrapperApplication);

            if (externalResult != null) {
                result = mapper.map(externalResult, PaymentRequest.class);
                if (externalParams!=null && externalResult.getRedirectionParams()!=null) {
                    result.setRedirectionParams(new HashMap<String, String>());
                    for (Entry entry : externalResult.getRedirectionParams().getEntry()) {
                        result.getRedirectionParams().put(entry.getKey(), entry.getValue());
                    }
                }

            }
        }

        return result;
    }

    private RequestPaymentApplication wrapApplicationToRequest(IPApplication application, String paymentId, User user) {
        RequestPaymentApplication wrapperApplication = null;

        eu.ohim.sp.external.domain.application.IPApplication externalTA = null;
        if (application instanceof TradeMarkApplication || application instanceof TMeServiceApplication) {
            externalTA = mapper.map(application, eu.ohim.sp.external.domain.trademark.TradeMarkApplication.class);
            wrapperApplication = new RequestPaymentApplication();
            wrapperApplication.setApplication(new JAXBElement<eu.ohim.sp.external.domain.trademark.TradeMarkApplication>(new QName(PaymentWSClientService.DOMAIN_NAMESPACE, "application"), eu.ohim.sp.external.domain.trademark.TradeMarkApplication.class,
                    (eu.ohim.sp.external.domain.trademark.TradeMarkApplication) externalTA));
        } else if (application instanceof DesignApplication || application instanceof DSeServiceApplication) {
            externalTA = mapper.map(application, eu.ohim.sp.external.domain.design.DesignApplication.class);
            wrapperApplication = new RequestPaymentApplication();
            wrapperApplication.setApplication(new JAXBElement<eu.ohim.sp.external.domain.design.DesignApplication>(new QName(PaymentWSClientService.DOMAIN_NAMESPACE, "designApplication"), eu.ohim.sp.external.domain.design.DesignApplication.class,
                    (eu.ohim.sp.external.domain.design.DesignApplication) externalTA));
        }
        if (wrapperApplication!=null) {
            wrapperApplication.setPaymentId(paymentId);
            if (user != null) {
                eu.ohim.sp.external.domain.user.User externalUser
                        = mapper.map(user, eu.ohim.sp.external.domain.user.User.class);
                if (externalUser != null) {
                    wrapperApplication.setUser(externalUser);
                }
            }
        }
        return wrapperApplication;
    }

    /**
     * Invokes the notifyPaymentResult service in the ESB
     *
     * @param paymentPlatformData: Map of parameters posted by the external payment environment to our callback url
     * @return: the PaymentResult object returned by the ESB
     */
    @Override
    @Interceptors({AdapterSetup.Payment.class, AdapterEnabled.class})
    public PaymentResult notifyPaymentResult(Map<String, String> paymentPlatformData) {
        ParamsMap xsdMap= new ParamsMap();
        for (Map.Entry entryMap : paymentPlatformData.entrySet()) {
            xsdMap.getEntry().add(new Entry(entryMap.getKey().toString(), entryMap.getValue().toString()));
        }

        eu.ohim.sp.external.domain.epayment.wrapper.PaymentResult externalResult = null;
        externalResult = epayment.notifyPaymentResult(xsdMap);

        PaymentResult result = null;
        if (externalResult != null) {
            result = mapper.map(externalResult, PaymentResult.class);
        }

        return result;
    }
}
