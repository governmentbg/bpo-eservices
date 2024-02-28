package eu.ohim.sp.core.classification;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.external.classification.BPOClassificationClientInside;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 12.05.2022
 * Time: 15:39
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Stateless
public class BPOClassificationServiceBean implements BPOClassificationServiceLocal, BPOClassificationServiceRemote {

    @Inject
    @BPOClassificationClientInside
    private BPOClassificationService bpoClassificationService;

    @Override
    public String autocompletePlantLatinClassification(String word, int rows) {
        return bpoClassificationService.autocompletePlantLatinClassification(word, rows);
    }
}
