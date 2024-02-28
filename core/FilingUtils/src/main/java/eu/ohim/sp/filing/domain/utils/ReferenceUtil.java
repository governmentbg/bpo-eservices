/*
 *  FspDomain:: ReferenceUtil 01/10/13 17:01 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.filing.domain.utils;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Util class that helps to find any reference below a specified object
 * @param <O> the object that should contain references of <R>
 * @param <R> it should be contained under <O>
 */
public class ReferenceUtil<O, R> {

    /**
     * Logger of {@see ReferenceUtil}
     */
    private static final Logger LOGGER = Logger.getLogger(ReferenceUtil.class);

    /**
     * Holder object of the class reference
     */
    private Class<R> reference;

    /**
     * Constructor that requires the reference class
     * @param reference the class reference
     */
    public ReferenceUtil(Class<R> reference) {
        this.reference = reference;
    }

    /**
     * Set the urls from Documents to format ATTACHMENTS/ATT...
     * @param transaction The object on which the document references are excpected
     */
    public void setUrlsDocumentsByTransaction(O transaction) {
        Set<R> documents = getReferencesOf(transaction, reference);
        String attachment = "ATTACHMENTS/";
        for (R document : documents) {
            BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
            try {
                String fileName = beanUtilsBean.getProperty(document, "fileName");
                if (StringUtils.isNotBlank(fileName)) {
                    beanUtilsBean.copyProperty(document, "uri", document.getClass().getDeclaredField("uri").getType().newInstance());
                    beanUtilsBean.copyProperty(document, "uri.value", attachment + fileName);
                }
            } catch (IllegalAccessException e) {
                LOGGER.error(e);
            } catch (InvocationTargetException e) {
                LOGGER.error(e);
            } catch (NoSuchMethodException e) {
                LOGGER.error(e);
            } catch (InstantiationException e) {
                LOGGER.error(e);
            } catch (NoSuchFieldException e) {
                LOGGER.error(e);
            }
        }
    }

    /**
     * Utility method that returns a set of objects that are referenced below
     * this object
     * @param originalObject the object that should contain the references
     * @param referenceClass the class that should be contained under the originalObject
     * @param <O> the type of the original Object
     * @param <R> the type of the references
     * @return a set of references if they are found, otherwise an empty set
     */
    public static <O, R> Set<R> getReferencesOf(O originalObject, Class<R> referenceClass) {
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        Set<R> ref = new HashSet<R>();

        Stack<Object> stack = new Stack<Object>();
        stack.push(originalObject);
        while (!stack.isEmpty()) {
            Object obj = stack.pop();
            if (obj instanceof Collection) {
                Iterator iterator = ((Collection) obj).iterator();
                while (iterator.hasNext()) {
                    Object object = iterator.next();
                    stack.push(object);
                }
            } else if (obj!=null
                    && (obj.getClass().isAssignableFrom(referenceClass)
                    || referenceClass.isAssignableFrom(obj.getClass()))) {
                ref.add((R) obj);
            } else if (obj != null && !(obj instanceof Class)) {
                for (PropertyDescriptor prop : propertyUtilsBean.getPropertyDescriptors(obj)) {
                    try {
                        Object object = (prop.getReadMethod()!= null
                                ? prop.getReadMethod().invoke(obj) : null);
                        if (object != null && !object.getClass().isPrimitive() && !(object instanceof Integer)
                                && !(object instanceof String) && !(object instanceof Boolean)) {
                            stack.push(object);
                        }
                    } catch (IllegalAccessException e) {
                        LOGGER.error(e);
                    } catch (InvocationTargetException e) {
                        LOGGER.error(e);
                    }
                }
            }
        }

        return ref;
    }

}
