package eu.ohim.sp.external.classification.inside;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.classification.BPOClassificationService;
import eu.ohim.sp.external.classification.BPOClassificationClientInside;
import eu.ohim.sp.external.injectors.BPOClassificationInjector;
import eu.ohim.sp.external.utils.AdapterEnabled;
import eu.ohim.sp.external.utils.AdapterSetup;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 12.05.2022
 * Time: 14:31
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent
@BPOClassificationClientInside
public class BPOClassificationClientBean implements BPOClassificationService {

    private BPOClassificationInjector bpoClassificationInjector;

    @PostConstruct
    public void init() {
        this.bpoClassificationInjector = new BPOClassificationInjector();
    }

    @Override
    @Interceptors({AdapterSetup.BPOClassification.class, AdapterEnabled.class})
    public String autocompletePlantLatinClassification(String word, int rows) {
        String response = bpoClassificationInjector.getPlantLatinClassificationAutocomplete(word, rows);
        return response;
    }
}
