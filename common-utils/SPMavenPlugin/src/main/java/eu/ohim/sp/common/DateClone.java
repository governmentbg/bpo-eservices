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
public class DateClone extends Plugin {
    @Override
    public String getOptionName() {
        return "Xdate-clone";
    }

    @Override
    public String getUsage() {
        return "Xdate-clone    : replace setters/getters by using clone ";
    }

    @Override
    public boolean run(Outline outline, Options options, ErrorHandler errorHandler) throws SAXException {
        for (ClassOutline co : outline.getClasses()) {

            JDefinedClass jdc = co.implClass;
            // avoid concurrent modification by copying the fields in a new list
            List<JFieldVar> fields = new ArrayList<JFieldVar>(jdc.fields().values());

            Iterator<JMethod> iterator = jdc.methods().iterator();

            JType voidType = null;
            JType dateType = null;
            List<String> setterMethods = new ArrayList<String>();
            List<JDocComment> setterComments = new ArrayList<JDocComment>();
            List<String> getterMethods = new ArrayList<String>();
            List<JDocComment> getterComments = new ArrayList<JDocComment>();

            while (iterator.hasNext()) {
                JMethod method = iterator.next();
                if (method.params().size()==1
                        && method.type().name().equals("void")
                        && method.name().startsWith("set")
                        && method.params().get(0).type().name().equals("Date")) {
                    setterComments.add(method.javadoc());
                    voidType = method.type();
                    dateType = method.params().get(0).type();
                    setterMethods.add(method.name());

                    iterator.remove();
                } else if (method.params().size()==0
                        && method.type().name().equals("Date")
                        && method.name().startsWith("get")) {
                    getterComments.add(method.javadoc());
                    getterMethods.add(method.name());

                    iterator.remove();
                }
            }

            if (voidType!=null && dateType!=null
                    && setterMethods.size() == getterMethods.size()) {
                JClass dateUtil = jdc.owner()
                        .ref(DateUtil.class);
                generateSetters(jdc, fields, voidType, dateType, setterMethods, setterComments, dateUtil);
                generateGetters(jdc, fields, dateType, getterMethods, getterComments, dateUtil);
            }
        }
        return true;
    }

    /**
     * It generates getters for {@link java.util.Date} fields that they use clone
     * @param jdc
     * @param fields
     * @param dateType
     * @param getterMethods
     * @param getterComments
     * @param dateUtil
     */
    private void generateGetters(JDefinedClass jdc, List<JFieldVar> fields, JType dateType, List<String> getterMethods, List<JDocComment> getterComments, JClass dateUtil) {
        for (int i = 0 ; i < getterMethods.size() ; i ++) {
            String removedMethod = getterMethods.get(i);
            JMethod createdMethod = jdc.method(JMod.PUBLIC, dateType, removedMethod);
            createdMethod.javadoc().addReturn()
                    .append("possible object is").append(
                    "         {@link Date }");

            Iterator<Object> iter = getterComments.get(i).iterator();
            while (iter.hasNext()) {
                createdMethod.javadoc().append(iter.next());
            }
            for (JFieldVar field : fields) {
                if (createdMethod.name().substring(3).equalsIgnoreCase(field.name())) {
                    createdMethod.body()._return(dateUtil.staticInvoke("cloneDate").arg(field));
                }
            }

        }
    }

    /**
     *  It generates setters for {@link java.util.Date} fields that they use clone
     * @param jdc
     * @param fields
     * @param voidType
     * @param dateType
     * @param setterMethods
     * @param setterComments
     * @param dateUtil
     */
    private void generateSetters(JDefinedClass jdc, List<JFieldVar> fields, JType voidType, JType dateType, List<String> setterMethods, List<JDocComment> setterComments, JClass dateUtil) {
        for (int i = 0 ; i < setterMethods.size() ; i ++) {
            String removedMethod = setterMethods.get(i);
            JMethod createdMethod = jdc.method(JMod.PUBLIC, voidType, removedMethod);
            createdMethod.javadoc().addParam(createdMethod.param(dateType, "value"))
                    .append("allowed object is").append(
                    "         {@link Date }");


            Iterator<Object> iter = setterComments.get(i).iterator();
            while (iter.hasNext()) {
                createdMethod.javadoc().append(iter.next());
            }

            for (JFieldVar field : fields) {
                if (createdMethod.name().substring(3).equalsIgnoreCase(field.name())) {
                    createdMethod.body().assign(JExpr._this().ref(field), dateUtil.staticInvoke("cloneDate").arg(createdMethod.params().get(0)));
                }
            }
        }
    }

}