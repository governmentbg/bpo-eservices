package eu.ohim.sp.external.register.inside;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.application.Appeal;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.licence.Licence;
import eu.ohim.sp.core.domain.opposition.OppositionGround;
import eu.ohim.sp.core.domain.trademark.ForeignRegistration;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.register.TradeMarkSearchService;
import eu.ohim.sp.external.domain.trademark.TradeMark;
import eu.ohim.sp.external.injectors.PersonInjector;
import eu.ohim.sp.external.register.TradeMarkSearchClientInside;
import eu.ohim.sp.external.utils.AdapterEnabled;
import eu.ohim.sp.external.utils.AdapterSetup;
import eu.ohim.sp.external.utils.PreclearanceReportTransformer;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import eu.ohim.sp.external.injectors.ImageInjector;
import eu.ohim.sp.external.injectors.TradeMarkInjector;

/**
 * Created by marcoantonioalberoalbero on 30/8/16.
 */
@Dependent @TradeMarkSearchClientInside
public class TradeMarkSearchClientBean implements TradeMarkSearchService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(TradeMarkSearchClientBean.class);

    /** The system configuration service. */
    @EJB(lookup = "java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private DozerBeanMapper mapper;

    /**
     * Trademark injector
     */
    private TradeMarkInjector tm_injector;

    /**
     * Person injector
     */
    private PersonInjector person_injector;

    /**
     * Init
     */
    @PostConstruct
    public void init() {
        mapper = new DozerBeanMapper();
        tm_injector = new TradeMarkInjector();
        person_injector = new PersonInjector();
    }

    @Override
    @Interceptors({AdapterSetup.Trademark.class, AdapterEnabled.class})
    public eu.ohim.sp.core.domain.trademark.TradeMark getTradeMark(String office, String tradeMarkId) {
        TradeMark externalResult = tm_injector.getTrademark(office, tradeMarkId);
        if (externalResult != null) {
            ImageInjector.inject(externalResult);
            person_injector.injectApplicants(externalResult);
            person_injector.injectRepresentatives(externalResult);
            return mapper.map(externalResult, eu.ohim.sp.core.domain.trademark.TradeMark.class);
        }
        return null;
    }

    @Override
    @Interceptors({AdapterSetup.Trademark.class, AdapterEnabled.class})
    public eu.ohim.sp.core.domain.trademark.TradeMark getForeignTradeMark(String office, String tradeMarkId) {
        return getTradeMark(office, tradeMarkId);
    }

    @Override
    @Interceptors({AdapterSetup.Trademark.class, AdapterEnabled.class})
    public eu.ohim.sp.core.domain.trademark.TradeMark getInternationalTradeMark(String office, String tradeMarkId, Boolean extraImport) {
        if(extraImport) {
            return getTradeMark(office, tradeMarkId);
        }
        TradeMark externalResult = tm_injector.getTrademarkMinimal(office, tradeMarkId);
        if (externalResult != null) {
            return mapper.map(externalResult, eu.ohim.sp.core.domain.trademark.TradeMark.class);
        }
        return null;
    }

    @Override
    @Interceptors({AdapterSetup.Trademark.class, AdapterEnabled.class})
    public String getTradeMarkAutocomplete(String office, String text, Integer numberOfResults) {
        return tm_injector.getTrademarkAutocomplete(office, text, numberOfResults);
    }

    @Override
    @Interceptors({AdapterSetup.Trademark.class, AdapterEnabled.class})
    public String getForeignTradeMarkAutocomplete(String office, String text, Integer numberOfResults) {
        return getTradeMarkAutocomplete(office, text, numberOfResults);
    }

    @Override
    @Interceptors({AdapterSetup.Trademark.class, AdapterEnabled.class})
    public List<eu.ohim.sp.core.domain.trademark.TradeMark> getPreclearanceReport(String office, eu.ohim.sp.core.domain.trademark.TradeMark tradeMark) {
        List<eu.ohim.sp.core.domain.trademark.TradeMark> result = null;
        TradeMark externalObject = mapper.map(tradeMark, TradeMark.class);
        if (externalObject != null) {
            Map<String, Object> map = (Map<String, Object>)PreclearanceReportTransformer.doTransform(externalObject, "UTF-8");
            List<TradeMark> externalResult = tm_injector.getPreclearanceReport(
                    (String)map.get(PreclearanceReportTransformer.TRADEMARK_NAME),
                    (String)map.get(PreclearanceReportTransformer.NICE_CLASSES),
                    (String)map.get(PreclearanceReportTransformer.FORMAT));
            if (externalResult != null) {
                result = externalResult.stream()
                        .map(a -> mapper.map(a, eu.ohim.sp.core.domain.trademark.TradeMark.class))
                        .collect(Collectors.toList());
            }
        }
        return result;
    }

    @Override
    @Interceptors({AdapterSetup.Trademark.class, AdapterEnabled.class})
    public ErrorList validateTradeMark(String module, eu.ohim.sp.core.domain.trademark.TradeMark tradeMark, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }

    @Override
    @Interceptors({AdapterSetup.Trademark.class, AdapterEnabled.class})
    public ErrorList validateOpposition(String module, OppositionGround opposition, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }

    @Override
    public ErrorList validateLicence(String module, Licence licence, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }

    @Override
    public ErrorList validateApplicationCA(String module, ContactDetails contactDetails, RulesInformation rulesInformation){
        throw new NotImplementedException();
    }

    @Override
    public ErrorList validateAppeal(String module, Appeal appeal, RulesInformation rulesInformation){
        throw new NotImplementedException();
    }

    @Override
    public ErrorList validateMarkView(String module, ImageSpecification markView, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }

    @Override
    public ErrorList validateForeignRegistration(String module, ForeignRegistration foreignRegistration, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }
}