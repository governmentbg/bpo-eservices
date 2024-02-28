package eu.ohim.sp.external.common;

/**
 * Created by marcoantonioalberoalbero on 30/8/16.
 */
import org.apache.commons.lang.StringUtils;
import org.milyn.javabean.binding.xml.XMLBinding;
import org.milyn.payload.StringSource;

public class XMLConsumerResponseTransformer<T extends Object>
        implements ConsumerResponseTransformer<T, String> {

    final String pathToTransformFile;
    final Class<T> returnClass;

    public XMLConsumerResponseTransformer(final Class<T> returnClass, final String pathToTransformFile){
        this.returnClass = returnClass;
        this.pathToTransformFile = pathToTransformFile;
    }

    public T transform(final String inputXml) {
        try {
            if (StringUtils.isBlank(inputXml)) {
                return null;
            }
            final XMLBinding xmlBinding = initializeXMLBinding();
            T returnObj = xmlToObjectTransform(xmlBinding, inputXml);
            return returnObj;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private XMLBinding initializeXMLBinding() {
        try {
            return new XMLBinding().add(pathToTransformFile).intiailize();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private T xmlToObjectTransform(final XMLBinding xmlBinding, final String inputXml) {
        try {
            T transformObj = (T) xmlBinding.fromXML(new StringSource(inputXml.trim().replaceFirst("^([\\W]+)<","<")), returnClass);
            return transformObj;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}