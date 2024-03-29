package eu.ohim.sp.common;

import com.sun.codemodel.*;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;
import eu.ohim.sp.common.util.DateUtil;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Χρήστος
 * Date: 28/1/2014
 * Time: 1:21 πμ
 * To change this template use File | Settings | File Templates.
 */
public class ByteArrayCopy extends Plugin {
    @Override
    public String getOptionName() {
        return "Xbyte-array-copy";
    }

    @Override
    public String getUsage() {
        return "Xbyte-array-copy    : copies the arrays ";
    }

    @Override
    public boolean run(Outline outline, Options options, ErrorHandler errorHandler) throws SAXException {
        for (ClassOutline co : outline.getClasses()) {

            JDefinedClass jdc = co.implClass;
            // avoid concurrent modification by copying the fields in a new list
            List<JFieldVar> fields = new ArrayList<JFieldVar>(jdc.fields().values());

            Iterator<JMethod> iterator = jdc.methods().iterator();

            JType voidType = null;
            JType byteArrayType = null;
            List<String> setterMethods = new ArrayList<String>();
            List<JDocComment> setterComments = new ArrayList<JDocComment>();
            List<String> getterMethods = new ArrayList<String>();
            List<JDocComment> getterComments = new ArrayList<JDocComment>();

            while (iterator.hasNext()) {
                JMethod method = iterator.next();

                if (method.params().size()==1
                        && method.type().name().equals("void")
                        && method.name().startsWith("set")
                        && method.params().get(0).type().name().equals("byte[]")) {
                    setterComments.add(method.javadoc());
                    voidType = method.type();
                    byteArrayType = method.params().get(0).type();
                    setterMethods.add(method.name());

                    iterator.remove();
                } else if (method.params().size()==0
                        && method.type().name().equals("byte[]")
                        && method.name().startsWith("get")) {
                    getterComments.add(method.javadoc());
                    getterMethods.add(method.name());

                    iterator.remove();
                }
            }

            if (voidType!=null && byteArrayType!=null
                    && setterMethods.size() > 0) {
                generateSetters(jdc, fields, voidType, byteArrayType, setterMethods, setterComments);
                generateGetters(jdc, fields, byteArrayType, getterMethods, getterComments);
            }
        }
        return true;
    }

    /**
     * It generates getters for {@link byte[]} fields that they use clone
     * @param jdc
     * @param fields the fields of which getter is created
     * @param byteArrayType the returned byte[] type
     * @param getterMethods the getter methods that should be added
     * @param getterComments the comments to be added on the comments
     */
    private void generateGetters(JDefinedClass jdc, List<JFieldVar> fields, JType byteArrayType, List<String> getterMethods, List<JDocComment> getterComments) {
        for (int i = 0 ; i < getterMethods.size() ; i ++) {
            String removedMethod = getterMethods.get(i);
            JMethod createdMethod = jdc.method(JMod.PUBLIC, byteArrayType, removedMethod);
            createdMethod.javadoc().addReturn()
                    .append("possible object is").append(
                    "         {@link byte[] }");

            Iterator<Object> iter = getterComments.get(i).iterator();
            while (iter.hasNext()) {
                createdMethod.javadoc().append(iter.next());
            }
            for (JFieldVar field : fields) {
                if (createdMethod.name().substring(3).equalsIgnoreCase(field.name())) {
                    createdMethod.body()._return(JOp.cond(JOp.ne(field, JExpr._null()), JExpr.invoke(field, "clone"), JExpr._null()));
                }
            }

        }
    }

    /**
     *  It generates setters for {@link java.util.Date} fields that they use clone
     * @param jdc
     * @param fields
     * @param voidType
     * @param byteArrayType
     * @param setterMethods
     * @param setterComments
     */
    private void generateSetters(JDefinedClass jdc, List<JFieldVar> fields, JType voidType, JType byteArrayType, List<String> setterMethods, List<JDocComment> setterComments) {
        for (int i = 0 ; i < setterMethods.size() ; i ++) {
            String removedMethod = setterMethods.get(i);
            JMethod createdMethod = jdc.method(JMod.PUBLIC, voidType, removedMethod);
            JVar var = createdMethod.param(byteArrayType, "value");
            createdMethod.javadoc().addParam(var)
                    .append("allowed object is").append(
                    "         {@link byte[] }");


            Iterator<Object> iter = setterComments.get(i).iterator();
            while (iter.hasNext()) {
                createdMethod.javadoc().append(iter.next());
            }

            for (JFieldVar field : fields) {
                if (createdMethod.name().substring(3).equalsIgnoreCase(field.name())) {
                    createdMethod.body().assign(JExpr._this().ref(field), JOp.cond(JOp.ne(var, JExpr._null()), JExpr.invoke(var, "clone"), JExpr._null()));
                }
            }
        }
    }

}