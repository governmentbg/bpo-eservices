package eu.ohim.sp.external.classification.outside;

import eu.ohim.sp.external.domain.design.ProductIndication;
import eu.ohim.sp.external.ws.exception.LocarnoClassificationFaultException;
import eu.ohim.sp.external.ws.exception.LocarnoClassificationFaultException;

import javax.jws.WebService;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 09/09/13
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
@WebService
public interface LocarnoClassificationManagementWSInterface {


    /**
     * @param indicationClass
     * @param subclass
     * @param lang
     * @return returns java.util.List<eu.ohim.sp.external.design.ws.client.client.ProductIndication>
     * @throws eu.ohim.sp.external.ws.exception.LocarnoClassificationFaultException
     *
     */
    public List<ProductIndication> getProductIndications(
            String indicationClass,
            String subclass,
            String lang)
            throws LocarnoClassificationFaultException
    ;

    /**
     * @param lang
     * @return returns java.util.List<eu.ohim.sp.external.design.ws.client.client.ProductIndication>
     * @throws LocarnoClassificationFaultException
     *
     */
    public List<ProductIndication> getFullClassification(
            String lang)
            throws LocarnoClassificationFaultException
    ;

    /**
     * @param indicationProduct
     * @param lang
     * @return returns java.util.List<eu.ohim.sp.external.design.ws.client.client.ProductIndication>
     * @throws LocarnoClassificationFaultException
     *
     */
    public List<ProductIndication> searchProductIndication(
            String indicationProduct,
            String clazz,
            String subclass,
            String lang)
            throws LocarnoClassificationFaultException
    ;

}
