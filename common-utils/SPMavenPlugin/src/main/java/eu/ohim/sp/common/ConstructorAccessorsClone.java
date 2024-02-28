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
 * It checks if the generated class from JAXB has any reference to Date or byte[]
 * and reconstructs the constructor with cloned copies of the given parameters
 * User: karalch
 * Date: 28/1/2014
 * Time: 1:21 πμ
 */
public class ConstructorAccessorsClone extends Plugin {
    @Override
    public String getOptionName() {
        return "Xconstructor-accessor-clone";
    }

    @Override
    public String getUsage() {
        return "Xconstructor-accessor-clone    : Uses cloned copy of parameters on constructors";
    }

    @Override
    public boolean run(Outline outline, Options options, ErrorHandler errorHandler) throws SAXException {
        for (ClassOutline co : outline.getClasses()) {

            JDefinedClass jdc = co.implClass;

            //Check added for super constructors
            JClass dateUtil = jdc.owner().ref(DateUtil.class);
            if (co.getSuperClass()==null) {
                reconstructConstructor(jdc, dateUtil, null, null);
            } else {
                Iterator<JMethod> superConstructors = co.getSuperClass().implClass.constructors();
                while (superConstructors.hasNext()) {
                    JMethod superConstructor = superConstructors.next();
                    if (superConstructor.params().size()>0) {
                        JVar[] superParameters =  superConstructor.listParams();
                        reconstructConstructor(jdc, dateUtil, superParameters, superConstructor);
                    }
                }
            }

        }
        return true;
    }

    /**
     * Reconstructs the constructor to create clones copy of the parameters
     * Currently it expects one constructor to be refactored
     * @param jdc
     * @param dateUtil
     */
    private void reconstructConstructor(JDefinedClass jdc, JClass dateUtil, JVar[] superParams, JMethod superConstructor) {
        Iterator<JMethod> constructors = jdc.constructors();
        //Removes the constructor only if there are references to Date or byte[]
        JVar[] vars = checkExistingConstructor(constructors);
        //If it has then it recreates the constructor
        if (vars!=null) {
            JMethod constructor = initializeConstructor(jdc, superParams, superConstructor);
            //It sets the values
            for (JVar var : vars) {
                constructor.param(JMod.FINAL, var.type(), var.name());
                boolean existingOnSuper = existOnSuper(superParams, var);
                //It sets the internal fields
                if (!existingOnSuper) {
                    if (var.type().name().equals("Date")) {
                        //on Date field, it creates a cloned copy
                        constructor.body().assign(JExpr._this().ref(var.name()), dateUtil.staticInvoke("cloneDate").arg(var));
                    } else if (var.type().name().equals("byte[]")) {
                        //on byte[] field, it creates a cloned copy
                        constructor.body().assign(JExpr._this().ref(var.name()), JOp.cond(JOp.ne(var, JExpr._null()), JExpr.invoke(var, "clone"), JExpr._null()));
                    } else {
                        //just sets the value
                        constructor.body().assign(JExpr._this().ref(var.name()), var);
                    }
                }
            }
        }
    }

    /**
     * It checks if it is used on the super constructor
     * @param superParams the super parameters
     * @param var the checked field
     * @return true if it is used on super constructor
     */
    private boolean existOnSuper(JVar[] superParams, JVar var) {
        boolean existingOnSuper = false;
        //It should ignore the ones already used on super constructor
        if (superParams!=null) {
            for (JVar superparam : superParams) {
                if (superparam.name().equals(var.name())) {
                    existingOnSuper = true;
                }
            }
        }
        return existingOnSuper;
    }

    /**
     * Initializes the constructor and adds a call to the super constructor if it exists
     * @param jdc the accessed class
     * @param superParams the parameters of the super class if any
     * @param superConstructor
     * @return
     */
    private JMethod initializeConstructor(JDefinedClass jdc, JVar[] superParams, JMethod superConstructor) {
        JMethod constructor = jdc.constructor(JMod.PUBLIC);
        //It calls the super constructor if it has one
        if (superConstructor!=null) {
            JInvocation jInvocation = JExpr.invoke("super");
            if (superParams != null) {
                for (JVar var : superParams) {
                    jInvocation.arg(var);
                }
            }
            constructor.body().add(jInvocation);
        }
        return constructor;
    }

    /**
     * Checks if there is any constructor that contains specific types and removes it
     * @param constructors the available constructors
     * @return the fields passes to the constructor removed
     */
    private JVar[] checkExistingConstructor(Iterator<JMethod> constructors) {
        JVar[] vars = null;
        while (constructors.hasNext()) {
            JMethod constructor = constructors.next();
            if (constructor.params().size()>0) {
                for (JVar var : constructor.params()) {
                    if ((var.type().name().equals("Date")
                            || var.type().name().equals("byte[]"))
                            && vars==null) {
                        vars = constructor.listParams();
                        constructors.remove();
                    }
                }
            }
        }
        return vars;
    }


}