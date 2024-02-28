package eu.ohim.sp.external.register.inside;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;

import eu.ohim.sp.external.domain.userdoc.FilteredUserdocs;
import eu.ohim.sp.core.register.BPOBackOfficeSearchService;
import eu.ohim.sp.external.domain.design.DesignApplication;
import eu.ohim.sp.external.domain.patent.Patent;
import eu.ohim.sp.external.domain.trademark.TradeMark;
import eu.ohim.sp.external.injectors.BackOfficeObjectInjector;
import eu.ohim.sp.external.register.BPOBackOfficeSearchClientInside;
import eu.ohim.sp.external.utils.AdapterEnabled;
import eu.ohim.sp.external.utils.AdapterSetup;
import org.dozer.DozerBeanMapper;

import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import java.util.Map;

@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent
@BPOBackOfficeSearchClientInside
public class BPOBackOfficeSearchClientBean implements BPOBackOfficeSearchService {

    private BackOfficeObjectInjector backOfficeObjectInjector;

    private DozerBeanMapper mapper;

    public BPOBackOfficeSearchClientBean() {
        this.backOfficeObjectInjector = new BackOfficeObjectInjector();
        this.mapper = new DozerBeanMapper();
    }

    @Override
    public String getUnpublishedApplicationsAutocomplete(String user, String applicationType, String text) {
        return backOfficeObjectInjector.getUnpublishedApplicationsAutocomplete(user, applicationType, text);
    }

    @Override
    @Interceptors({AdapterSetup.BackOffice.class, AdapterEnabled.class})
    public eu.ohim.sp.core.domain.trademark.TradeMark getUnpublishedTrademark(String user, String trademarkId) {
        TradeMark externalMark = backOfficeObjectInjector.getUnpublishedTrademark(user, trademarkId);
        if(externalMark != null){
            return mapper.map(externalMark, eu.ohim.sp.core.domain.trademark.TradeMark.class);
        }
        return null;
    }

    @Override
    @Interceptors({AdapterSetup.BackOffice.class, AdapterEnabled.class})
    public eu.ohim.sp.core.domain.design.DesignApplication getUnpublishedDesignApp(String user, String designApplicationId) {
        DesignApplication externalDSApp = backOfficeObjectInjector.getUnpublishedDesignApplication(user, designApplicationId);
        if(externalDSApp != null){
            return mapper.map(externalDSApp, eu.ohim.sp.core.domain.design.DesignApplication.class);
        }
        return null;
    }

    @Override
    @Interceptors({AdapterSetup.BackOffice.class, AdapterEnabled.class})
    public eu.ohim.sp.core.domain.patent.Patent getUnpublishedPatent(String user, String patentId) {
        Patent externalPatent = backOfficeObjectInjector.getUnpublishedPatent(user, patentId);
        if(externalPatent != null){
            return mapper.map(externalPatent, eu.ohim.sp.core.domain.patent.Patent.class);
        }
        return null;
    }

    @Override
    @Interceptors({AdapterSetup.BackOffice.class, AdapterEnabled.class})
    public eu.ohim.sp.core.domain.userdoc.FilteredUserdocs getUserdocsForApplication(String applicationNumber, String applicationYear, String user, Map<String, Object> eserviceDetails) {
        FilteredUserdocs filteredUserdocs = backOfficeObjectInjector.getUserdocs(applicationNumber, applicationYear, user, eserviceDetails);
        if(filteredUserdocs != null){
            eu.ohim.sp.core.domain.userdoc.FilteredUserdocs result = mapper.map(filteredUserdocs, eu.ohim.sp.core.domain.userdoc.FilteredUserdocs.class);
            return result;
        }
        return null;
    }
}
