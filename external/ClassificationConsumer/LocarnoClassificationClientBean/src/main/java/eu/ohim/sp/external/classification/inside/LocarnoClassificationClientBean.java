package eu.ohim.sp.external.classification.inside;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.classification.LocarnoClassificationService;
import eu.ohim.sp.core.domain.classification.ClassificationHeading;
import eu.ohim.sp.core.domain.classification.LocarnoClassHeading;
import eu.ohim.sp.core.domain.classification.LocarnoSubClassHeading;
import eu.ohim.sp.core.domain.design.ClassDescription;
import eu.ohim.sp.core.domain.design.KeyTextUIClass;
import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.domain.design.TermsSearch;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.external.classification.LocarnoClassificationClientInside;
import eu.ohim.sp.external.injectors.LocarnoInjector;
import eu.ohim.sp.external.utils.AdapterEnabled;
import eu.ohim.sp.external.utils.AdapterSetup;
import org.dozer.DozerBeanMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class is the router that connects to the JBoss ESB adapter for
 * classification. It is called from the fsp core service. It connect to the esb
 * using the jboss-remoting library. In this case the url to connect is passed
 * by system properties. It contains all the dependencies to the esb.
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent @LocarnoClassificationClientInside
public class LocarnoClassificationClientBean implements LocarnoClassificationService {
    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private DozerBeanMapper dozerBeanMapper;

    /**
     * Locarno injector
     */
    private LocarnoInjector locarnoInjector;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        // Mapper init
        dozerBeanMapper = new DozerBeanMapper();
        locarnoInjector = new LocarnoInjector();
    }

    @Override
    @Interceptors({AdapterSetup.Locarno.class, AdapterEnabled.class})
    public List<ProductIndication> getFullClassification(String lang) {
        List<eu.ohim.sp.external.domain.design.ProductIndication> productExtList = locarnoInjector.getFullClassification(lang);
        List<ProductIndication> productCoreList = productExtList.stream()
                .map(e -> dozerBeanMapper.map(e, ProductIndication.class))
                .collect(Collectors.toList());
        return productCoreList;
    }

    @Override
    @Interceptors({AdapterSetup.Locarno.class, AdapterEnabled.class})
	public List<ProductIndication> getProductIndications(String clazz, String subclass, String lang) {
        List<eu.ohim.sp.external.domain.design.ProductIndication> productExtList = locarnoInjector.getProductIndications(clazz, subclass, lang);
        List<ProductIndication> productCoreList = productExtList.stream()
                .map(e -> dozerBeanMapper.map(e, ProductIndication.class))
                .collect(Collectors.toList());
        return productCoreList;
    }

    @Override
    @Interceptors({AdapterSetup.Locarno.class, AdapterEnabled.class})
	public List<ProductIndication> searchProductIndication(String indicationProduct, String clazz, String subclass, String lang) {
        List<eu.ohim.sp.external.domain.design.ProductIndication> productExtList =
                locarnoInjector.searchProductIndication(indicationProduct, clazz, subclass,lang);
        List<ProductIndication> productCoreList = productExtList.stream()
                .map(e -> dozerBeanMapper.map(e, ProductIndication.class))
                .collect(Collectors.toList());
		return productCoreList;
	}

	@Override
    @Interceptors({AdapterSetup.Locarno.class, AdapterEnabled.class})
	public ErrorList validateProductIndication(String module, ProductIndication productIndication, RulesInformation rulesInformation) {
        // throw new NotImplementedException();
        return new ErrorList();
    }

    //DS Class Integration Changes Start
    @Override
    @Interceptors({AdapterSetup.Locarno.class, AdapterEnabled.class})
    public String getLocarnoAutocomplete(String language, String text, Integer numberOfResults) {
        String response = locarnoInjector.getLocarnoAutocomplete(language, text, numberOfResults);
        return response;
    }

    @Override
    public ClassificationHeading getLocarnoClasses(String language, String version) throws Exception{
        return locarnoInjector.getLocarnoClasses(language, version);
    }

    @Override
    public List<LocarnoSubClassHeading> getLocarnoSubclasses(Integer selectedClass, String language, String version)
        throws Exception {
        ClassificationHeading classifications =  locarnoInjector.getLocarnoClasses(language, version);
        return getLocarnoSubClassHeadings(selectedClass, classifications);
    }

    private List<LocarnoSubClassHeading> getLocarnoSubClassHeadings(Integer locarnoClass, ClassificationHeading classifications) {
        List<LocarnoSubClassHeading> ret = new ArrayList<>();
        if (classifications != null && classifications.getClassification() != null) {
            for (LocarnoClassHeading lch : classifications.getClassification()) {
                if (Objects.equals(lch.getClassCode(), locarnoClass)) {
                    if (lch.getLocarnoSubClasses() != null) {
                        ret.addAll(lch.getLocarnoSubClasses());
                    }
                    break;
                }
            }
        }
        return ret;
    }

    @Override
    public List<ClassDescription> validateClassDescriptions(List<ClassDescription> classDescriptions) {
        return locarnoInjector.validateClassDescriptions(classDescriptions);
    }

    @Override
    @Interceptors({AdapterSetup.Locarno.class, AdapterEnabled.class})
    public TermsSearch getTerms(String offices, String language, Integer page, Integer pageSize, String searchInput,
                                Integer selectedClass, Integer selectedSubclass, final Object... arguments) {

        TermsSearch response = locarnoInjector.getTerms(offices,language,page,pageSize,searchInput,selectedClass, selectedSubclass, arguments);

        return response;
    }

    @Override
    @Interceptors({AdapterSetup.Locarno.class, AdapterEnabled.class})
    public List<KeyTextUIClass> getTranslations(String languageFrom, String languageTo, String termTranslate, String productIndicationIdentifier, String selectedClass, String subclass) {
        List<KeyTextUIClass> response = locarnoInjector.getTranslations(languageFrom, languageTo, termTranslate, productIndicationIdentifier, selectedClass, subclass);
        return response;
    }

    //DS Class Integration Changes End
}
