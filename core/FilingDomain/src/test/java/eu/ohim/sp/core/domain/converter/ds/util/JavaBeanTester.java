/*
 *  CoreDomain:: JavaBeanTester 04/11/13 12:30 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.converter.ds.util;

import eu.ohim.sp.core.domain.validation.ApplicationIdentifier;
import eu.ohim.sp.filing.domain.ds.*;
import org.junit.Assert;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.math.BigInteger;
import java.util.*;

/**
 * This helper class can be used to unit test the get/set methods of JavaBean-style Value Objects.
 * 
 */
public class JavaBeanTester {
    /**
     * Tests the get/set methods of the specified class.
     * 
     * @param <T> the type parameter associated with the class under test
     * @param clazz the Class under test
     * @param skipThese the names of any properties that should not be tested
     * @throws java.beans.IntrospectionException thrown if the Introspector.getBeanInfo() method throws this exception
     *             for the class under test
     */
    public static <T> void test(final Class<T> clazz, final String... skipThese) throws IntrospectionException {
        final PropertyDescriptor[] props = Introspector.getBeanInfo(clazz).getPropertyDescriptors();

        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Object[] parameters = new Object[constructor.getParameterTypes().length];
            try {
                for (int i = 0 ; i < constructor.getParameterTypes().length ; i++) {
                    parameters[i] =  buildValue(constructor.getParameterTypes()[i]);
                }
                constructor.newInstance(parameters);
            } catch (InstantiationException e) {
                e.printStackTrace();
                Assert.assertFalse(true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Assert.assertFalse(true);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                Assert.assertFalse(true);
            }
        }


        nextProp:
        for (PropertyDescriptor prop : props) {
            // Check the list of properties that we don't want to test
            for (String skipThis : skipThese) {
                if (skipThis.equals(prop.getName())) {
                    continue nextProp;
                }
            }
            final Method getter = prop.getReadMethod();
            final Method setter = prop.getWriteMethod();

            if (getter != null && setter != null) {
                // We have both a get and set method for this property
                final Class<?> returnType = getter.getReturnType();
                final Class<?>[] params = setter.getParameterTypes();

                if (params.length == 1 && params[0] == returnType) {
                    Object expectedValue = null;
                    Object actualValue = null;
                    // The set method has 1 argument, which is of the same type as the return type of the get method, so
                    // we can test this property
                    try {
                        // Build a value of the correct type to be passed to the set method
                        Object value = buildValue(returnType);

                        // Build an instance of the bean that we are testing (each property test gets a new instance)
                        T bean = clazz.newInstance();

                        // Call the set method, then check the same value comes back out of the get method
                        setter.invoke(bean, value);

                        expectedValue = value;
                        actualValue = getter.invoke(bean);

                        if (expectedValue instanceof byte[]) {
                            Assert.assertArrayEquals(String.format("Failed while testing property array %s", prop.getName()),
                                    (byte[]) expectedValue, (byte[]) actualValue);
                        } else {
                            Assert.assertEquals(String.format("Failed while testing property %s", prop.getName()),
                                    expectedValue, actualValue);
                        }

                    } catch (InstantiationException e) {
                        Assert.assertEquals(String.format("Failed while testing property %s", prop.getName()),
                                expectedValue, actualValue);
                    } catch (IllegalAccessException e) {
                        Assert.assertEquals(String.format("Failed while testing property %s", prop.getName()),
                                expectedValue, actualValue);
                    } catch (IllegalArgumentException e) {
                        Assert.assertEquals(String.format("Failed while testing property %s", prop.getName()),
                                expectedValue, actualValue);
                    } catch (SecurityException e) {
                        Assert.assertEquals(String.format("Failed while testing property %s", prop.getName()),
                                expectedValue, actualValue);
                    } catch (InvocationTargetException e) {
                        Assert.assertEquals(String.format("Failed while testing property %s", prop.getName()),
                                expectedValue, actualValue);
                    }
                }
            }
        }
    }

    private static Object buildMockValue(Class<?> clazz) {
        if (!Modifier.isFinal(clazz.getModifiers())) {
            // Insert a call to your favourite mocking framework here
            return null;
        } else {
            return null;
        }
    }

    private static Object buildValue(Class<?> clazz) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, SecurityException, InvocationTargetException {
        // If we are using a Mocking framework try that first...
        final Object mockedObject = buildMockValue(clazz);

        if (mockedObject != null) {
            return mockedObject;
        } else if (clazz == XMLGregorianCalendar.class) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            XMLGregorianCalendar gregCalendar = null;
            try {
                gregCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            } catch (DatatypeConfigurationException e) {
                return null;
            }
            return gregCalendar;
        }

        // Next check for a no-arg constructor
        final Constructor<?>[] ctrs = clazz.getConstructors();
        for (Constructor<?> ctr : ctrs) {
            if (ctr.getParameterTypes().length == 0) {
                // The class has a no-arg constructor, so just call it
                return ctr.newInstance();
            }
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
        else if (clazz == short.class || clazz == Short.class)
            return 5;
        else if (clazz == long.class || clazz == Long.class)
            return 1L;
        else if (clazz == double.class || clazz == Double.class)
            return 1.0D;
        else if (clazz == float.class || clazz == Float.class)
            return 1.0F;
        else if (clazz == char.class || clazz == Character.class)
            return 'Y';
        else if (clazz == List.class || clazz == Collection.class) {
            List list = new ArrayList();
            return list;
        } else if (clazz == DesignKindType.class) {
            return DesignKindType.DRAWING;
        } else if (clazz == DesignApplicationCurrentStatusCodeType.class) {
            return DesignApplicationCurrentStatusCodeType.FILED;
        } else if (clazz == DesignCurrentStatusCodeType.class) {
            return DesignCurrentStatusCodeType.ENDED;
        } else if (clazz == OpponentEntitlementKindType.class) {
            return OpponentEntitlementKindType.LICENSEE;
        } else if (clazz == BigInteger.class) {
            return BigInteger.ONE;
        } else if (clazz == PreferredCorrespondenceKind.class) {
            return PreferredCorrespondenceKind.FAX;
        } else if (clazz == WIPOST3Code.class) {
            return WIPOST3Code.AU;
        } else if (clazz == RepresentativeKindCode.class) {
            return RepresentativeKindCode.EMPLOYEE;
        } else if (clazz == ISOCountryCode.class) {
            return ISOCountryCode.DE;
        } else if (clazz == ISOLanguageCode.class) {
            return ISOLanguageCode.DE;
        } else if (clazz == PaymentStatusCode.class) {
            return PaymentStatusCode.ATTACHED;
        } else if (clazz == GenderType.class) {
            return GenderType.FEMALE;
        } else if (clazz == Role.class) {
            return Role.APPLICANT;
        } else if (clazz == NameKind.class) {
            return NameKind.LEGAL_ENTITY;
        } else if (clazz == PaymentKindType.class) {
            return PaymentKindType.CREDIT_CARD;
        } else if (clazz == DocumentStatusCode.class) {
            return DocumentStatusCode.TO_FOLLOW;
        } else if (clazz == CardKindCode.class) {
            return CardKindCode.CREDIT_CARD;
        } else if (clazz == ChipCode.class) {
            return ChipCode.CHIP;
        } else if (clazz == DocumentKind.class) {
            return DocumentKind.APPLICATION_RECEIPT;
        } else if (clazz == AccountDebitKind.class) {
            return AccountDebitKind.BASIC_AND_CLASS_FEE_IMMEDIATE;
        } else if (clazz == ExhibitionStatusCodeType.class) {
            return ExhibitionStatusCodeType.CLAIMED;
        } else if (clazz == PriorityStatusCodeType.class) {
            return PriorityStatusCodeType.ACCEPTED;
        } else if (clazz == DocumentKind.class) {
            return DocumentKind.APPLICATION_RECEIPT;
        } else if (clazz == Map.class)
            return new HashMap<Object, Object>();
        else if (clazz == ApplicationIdentifier.class)
            return new ApplicationIdentifier("","","","", "");
        else {
            Assert.fail("Unable to build an instance of class " + clazz.getName() + ", please add some code to the "
                    + JavaBeanTester.class.getName() + " class to do this.");
            return null; // for the compiler
        }
    }
}
