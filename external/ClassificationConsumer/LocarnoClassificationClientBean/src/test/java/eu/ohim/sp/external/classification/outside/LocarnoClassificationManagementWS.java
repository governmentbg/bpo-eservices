package eu.ohim.sp.external.classification.outside;

import eu.ohim.sp.external.domain.common.Fault;
import eu.ohim.sp.external.domain.design.ProductIndication;
import eu.ohim.sp.external.domain.design.ProductIndicationClass;
import eu.ohim.sp.external.services.classification.locarno.LocarnoClassificationFault;
import eu.ohim.sp.external.ws.exception.LocarnoClassificationFaultException;
import org.apache.commons.lang.StringUtils;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 09/09/13
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
@WebService(serviceName = "LocarnoClassificationService", targetNamespace = "http://ohim.eu/sp/services/locarno-classification/v3", portName = "LocarnoClassificationPort", wsdlLocation = "wsdl/LocarnoClassificationService.wsdl")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class LocarnoClassificationManagementWS implements LocarnoClassificationManagementWSInterface {


    /**
     * @param indicationClass
     * @param subclass
     * @param lang
     * @return returns java.util.List<eu.ohim.sp.external.domain.design.ProductIndication>
     * @throws eu.ohim.sp.external.ws.exception.LocarnoClassificationFaultException
     *
     */
    @WebMethod
    public List<ProductIndication> getProductIndications(
            @WebParam(name = "indicationClass", targetNamespace = "")
            String indicationClass,
            @WebParam(name = "subclass", targetNamespace = "")
            String subclass,
            @WebParam(name = "lang", targetNamespace = "")
            String lang)
            throws LocarnoClassificationFaultException {
        if (StringUtils.isBlank(indicationClass)) {
            try {
                StringUtils.defaultIfBlank(indicationClass, null).equals("error");
            } catch (NullPointerException e) {
                LocarnoClassificationFault locarnoClassificationFault = new LocarnoClassificationFault();
                locarnoClassificationFault.setFault(new Fault());
                locarnoClassificationFault.getFault().setCode("error.code");
                locarnoClassificationFault.getFault().setMessage("system error");
                throw new LocarnoClassificationFaultException("system error", locarnoClassificationFault, e);
            }
        }

        ProductIndication productIndication = new ProductIndication();
        productIndication.setLanguageCode(lang);

        ProductIndicationClass productIndicationClass = new ProductIndicationClass();
        productIndicationClass.setMainClass(indicationClass);
        productIndicationClass.setSubClass(subclass);

        productIndication.setClasses(new ArrayList<ProductIndicationClass>());
        productIndication.getClasses().add(productIndicationClass);

        List<ProductIndication> productIndications = new ArrayList<ProductIndication>();
        productIndications.add(productIndication);

        return productIndications;
    }

    /**
     * @param lang
     * @return returns java.util.List<eu.ohim.sp.external.design.ProductIndication>
     * @throws LocarnoClassificationFaultException
     *
     */
    @WebMethod
    public List<ProductIndication> getFullClassification(
            @WebParam(name = "lang", targetNamespace = "")
            String lang)
            throws LocarnoClassificationFaultException {

        if (StringUtils.isBlank(lang)) {
            try {
                StringUtils.defaultIfBlank(lang, null).equals("error");
            } catch (NullPointerException e) {
                LocarnoClassificationFault locarnoClassificationFault = new LocarnoClassificationFault();
                locarnoClassificationFault.setFault(new Fault());
                locarnoClassificationFault.getFault().setCode("error.code");
                locarnoClassificationFault.getFault().setMessage("system error");
                throw new LocarnoClassificationFaultException("system error", locarnoClassificationFault, e);
            }
        }

        ProductIndication productIndication = new ProductIndication();
        productIndication.setLanguageCode(lang);

        ProductIndicationClass productIndicationClass = new ProductIndicationClass();
        productIndicationClass.setMainClass("indicationClass");
        productIndicationClass.setSubClass("subclass");

        productIndication.setClasses(new ArrayList<ProductIndicationClass>());
        productIndication.getClasses().add(productIndicationClass);

        List<ProductIndication> productIndications = new ArrayList<ProductIndication>();
        productIndications.add(productIndication);

        return productIndications;
    }

    /**
     * @param indicationProduct
     * @param lang
     * @return returns java.util.List<eu.ohim.sp.external.domain.design.ProductIndication>
     * @throws LocarnoClassificationFaultException
     *
     */
    @WebMethod
    public List<ProductIndication> searchProductIndication(
            @WebParam(name = "indicationProduct", targetNamespace = "")
            String indicationProduct,
            @WebParam(name = "clazz", targetNamespace = "")
            String clazz,
            @WebParam(name = "subclass", targetNamespace = "")
            String subclass,
            @WebParam(name = "lang", targetNamespace = "")
            String lang)
            throws LocarnoClassificationFaultException {

        if (StringUtils.isBlank(indicationProduct)) {
            LocarnoClassificationFault locarnoClassificationFault = new LocarnoClassificationFault();
            locarnoClassificationFault.setFault(new Fault());
            locarnoClassificationFault.getFault().setCode("error.code");
            locarnoClassificationFault.getFault().setMessage("system error");
            throw new LocarnoClassificationFaultException("system error", locarnoClassificationFault);
        }

        ProductIndication productIndication = new ProductIndication();
        productIndication.setLanguageCode(lang);

        ProductIndicationClass productIndicationClass = new ProductIndicationClass();
        productIndicationClass.setMainClass(clazz);
        productIndicationClass.setSubClass(subclass);

        productIndication.setClasses(new ArrayList<ProductIndicationClass>());
        productIndication.getClasses().add(productIndicationClass);

        List<ProductIndication> productIndications = new ArrayList<ProductIndication>();
        productIndications.add(productIndication);

        return productIndications;

    }


}
