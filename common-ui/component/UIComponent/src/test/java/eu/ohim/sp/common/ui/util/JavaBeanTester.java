/*******************************************************************************
 * * $Id:: JavaBeanTester.java 109645 2013-03-25 10:50:36Z ionitdi               $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import org.junit.Assert;
import org.springframework.web.multipart.MultipartFile;

import eu.ohim.sp.common.ui.form.application.SignatoryKindForm;
import eu.ohim.sp.common.ui.form.claim.SeniorityKindForm;
import eu.ohim.sp.common.ui.form.person.ApplicantKindForm;
import eu.ohim.sp.common.ui.form.person.LegalPractitionerType;
import eu.ohim.sp.common.ui.form.person.ProfessionalPractitionerType;
import eu.ohim.sp.common.ui.form.person.RepresentativeKindForm;
import eu.ohim.sp.common.ui.form.resources.AttachmentDocumentType;

/**
 * This helper class can be used to unit test the get/set methods of JavaBean-style Value Objects.
 *
 */
public class JavaBeanTester
{
    /**
     * Tests the get/set methods of the specified class.
     *
     * @param <T>       the type parameter associated with the class under test
     * @param clazz     the Class under test
     * @param skipThese the names of any properties that should not be tested
     * @throws IntrospectionException thrown if the Introspector.getBeanInfo() method throws this exception
     *                                for the class under test
     */
    public static <T> void test(final Class<T> clazz, final String... skipThese) throws IntrospectionException
    {
        final PropertyDescriptor[] props = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
        nextProp:
            for (PropertyDescriptor prop : props)
            {
                // Check the list of properties that we don't want to test
                for (String skipThis : skipThese)
                {
                    if (skipThis.equals(prop.getName()))
                    {
                        continue nextProp;
                    }
                }
                final Method getter = prop.getReadMethod();
                final Method setter = prop.getWriteMethod();

                if (getter != null && setter != null)
                {
                    // We have both a get and set method for this property
                    final Class<?> returnType = getter.getReturnType();
                    final Class<?>[] params = setter.getParameterTypes();

                    if (params.length == 1 && params[0] == returnType)
                    {
                        // The set method has 1 argument, which is of the same type as the return type of the get method, so we can test this property
                        try
                        {
                            // Build a value of the correct type to be passed to the set method
                            Object value = buildValue(returnType);

                            // Build an instance of the bean that we are testing (each property test gets a new instance)
                            T bean = clazz.newInstance();

                            // Call the set method, then check the same value comes back out of the get method
                            setter.invoke(bean, value);

                            final Object expectedValue = value;
                            final Object actualValue = getter.invoke(bean);

                            Assert.assertEquals(String.format("Failed while testing property %s", prop.getName()), expectedValue,
                                    actualValue);



                        } catch (InstantiationException e) {
                            Assert.fail(String.format("An exception was thrown while testing the property %s: %s", prop.getName(),
                                    e.toString()));
                        } catch (IllegalAccessException e) {
                            Assert.fail(String.format("An exception was thrown while testing the property %s: %s", prop.getName(),
                                    e.toString()));
                        } catch (IllegalArgumentException e) {
                            Assert.fail(String.format("An exception was thrown while testing the property %s: %s", prop.getName(),
                                    e.toString()));
                        } catch (InvocationTargetException e) {
                            Assert.fail(String.format("An exception was thrown while testing the property %s: %s", prop.getName(),
                                    e.toString()));
                        }
                    }
                }
            }
    }

    private static Object buildMockValue(Class<?> clazz)
    {
        if (!Modifier.isFinal(clazz.getModifiers()))
            // Insert a call to your favourite mocking framework here
            return null;
        else
            return null;
    }

    private static Object buildValue(Class<?> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
    SecurityException, InvocationTargetException
    {
        // If we are using a Mocking framework try that first...
        final Object mockedObject = buildMockValue(clazz);
        if (mockedObject != null)
            return mockedObject;

        // Next check for a no-arg constructor
        final Constructor<?>[] ctrs = clazz.getConstructors();
        for (Constructor<?> ctr : ctrs)
        {
            if (ctr.getParameterTypes().length == 0)
                // The class has a no-arg constructor, so just call it
                return ctr.newInstance();
        }

        // Specific rules for common classes
        if (clazz == String.class)
            return "testvalue";
        else if (clazz.isArray())
            return Array.newInstance(clazz.getComponentType(), 1);
        else if (clazz == boolean.class || clazz == Boolean.class)
            return true;
        else if (clazz == int.class || clazz == Integer.class)
            return 1;
        else if (clazz == long.class || clazz == Long.class)
            return 1L;
        else if (clazz == double.class || clazz == Double.class)
            return 1.0D;
        else if (clazz == float.class || clazz == Float.class)
            return 1.0F;
        else if (clazz == char.class || clazz == Character.class)
            return 'Y';
        else if (clazz == List.class || clazz == Collection.class)
        {
            List list = new ArrayList();
            return list;
        }
        else if(clazz == SeniorityKindForm.class)
            return SeniorityKindForm.INTERNATIONAL_TRADE_MARK;
        else if(clazz == MultipartFile.class)
            return null;
        else if(clazz == SignatoryKindForm.class)
            return SignatoryKindForm.APPLICANT;
        else if(clazz == Set.class)
            return new HashSet();
        else if(clazz == ApplicantKindForm.class)
            return ApplicantKindForm.LEGAL_ENTITY;
        else if(clazz == RepresentativeKindForm.class)
            return RepresentativeKindForm.EMPLOYEE_REPRESENTATIVE;
        else if(clazz == LegalPractitionerType.class)
            return LegalPractitionerType.LEGAL_PRACTITIONER;
        else if(clazz == ProfessionalPractitionerType.class)
            return ProfessionalPractitionerType.PROFESSIONAL_PRACTITIONER;
        else if(clazz == AttachmentDocumentType.class)
            return AttachmentDocumentType.POWER_OF_ATTORNEY;
        else if(clazz == Map.class)
            return new HashMap<Object, Object>();
        else
        {
            Assert.fail(
                    "Unable to build an instance of class " + clazz.getName() + ", please add some code to the " + JavaBeanTester.class.getName() + " class to do this.");
            return null; // for the compiler
        }
    }
}
