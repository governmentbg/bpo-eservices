/*
 *  CoreDomain:: JavaBeanTester 04/11/13 12:30 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.ohim.sp.core.domain.dossier.DossierKind;
import eu.ohim.sp.core.domain.dossier.letters.enums.LetterRecipientKind;
import eu.ohim.sp.core.domain.dossier.letters.enums.LetterStatusKind;
import eu.ohim.sp.core.domain.dossier.letters.enums.LetterTypeSendMethod;
import eu.ohim.sp.core.domain.dossier.publications.PublicationStatus;
import eu.ohim.sp.core.domain.dossier.tasks.enums.TaskStatusEnum;
import eu.ohim.sp.core.domain.opposition.EarlierEntitlementRightKind;
import eu.ohim.sp.core.domain.opposition.GroundCategoryKind;
import eu.ohim.sp.core.domain.validation.ApplicationIdentifier;
import eu.ohim.sp.core.domain.claim.SeniorityKind;
import eu.ohim.sp.core.domain.classification.MatchClassType;
import eu.ohim.sp.core.domain.classification.MatchProximityType;
import eu.ohim.sp.core.domain.classification.VerifiedTermResult;
import eu.ohim.sp.core.domain.classification.wrapper.OrderBy;
import eu.ohim.sp.core.domain.classification.wrapper.SearchMode;
import eu.ohim.sp.core.domain.classification.wrapper.SortBy;
import eu.ohim.sp.core.domain.common.CharacterSet;
import eu.ohim.sp.core.domain.payment.CardKindCode;
import eu.ohim.sp.core.domain.payment.PaymentStatusCode;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentStatus;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonRoleKind;
import eu.ohim.sp.core.domain.person.RepresentativeKind;
import eu.ohim.sp.core.domain.resources.FileFormat;
import eu.ohim.sp.core.domain.resources.SoundFileFormat;
import eu.ohim.sp.core.domain.trademark.ClassificationErrorEnum;
import eu.ohim.sp.core.domain.trademark.MarkFeature;
import org.junit.Assert;

import eu.ohim.sp.core.domain.claim.InternationalTradeMarkCode;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.payment.AccountDebitKind;
import eu.ohim.sp.core.domain.payment.PaymentKind;
import eu.ohim.sp.core.domain.person.Gender;
import eu.ohim.sp.core.domain.resources.DocumentKind;
import eu.ohim.sp.core.domain.trademark.MarkKind;

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
     * @throws IntrospectionException thrown if the Introspector.getBeanInfo() method throws this exception
     *             for the class under test
     */
    public static <T> void test(final Class<T> clazz, final String... skipThese) throws IntrospectionException {
        final PropertyDescriptor[] props = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
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
        }
        else if (clazz == PersonKind.class) {
            return PersonKind.NATURAL_PERSON;
        } else if (clazz == AccountDebitKind.class)
            return AccountDebitKind.BASIC_AND_CLASS_FEE_END_OF_PERIOD;
        else if (clazz == CharacterSet.class)
            return CharacterSet.GREEK;
        else if (clazz == CardKindCode.class)
            return CardKindCode.CREDIT_CARD;
        else if (clazz == Gender.class)
            return Gender.FEMALE;
        else if (clazz == FileFormat.class)
            return FileFormat.GIF;
        else if (clazz == TaskStatusEnum.class)
            return TaskStatusEnum.COMPLETED;
        else if (clazz == LetterStatusKind.class)
            return LetterStatusKind.CREATE;
        else if (clazz == LetterTypeSendMethod.class)
            return LetterTypeSendMethod.ELECTRONIC;
        else if (clazz == LetterRecipientKind.class)
            return LetterRecipientKind.PERSON;
        else if (clazz == GroundCategoryKind.class)
            return GroundCategoryKind.RELATIVE_GROUNDS;
        else if (clazz == ClassificationErrorEnum.class)
            return ClassificationErrorEnum.HINT;
        else if (clazz == SoundFileFormat.class)
            return SoundFileFormat.MP3;
        else if (clazz == PaymentStatus.class)
            return PaymentStatus.PAYMENT_CANCELLED;
        else if (clazz == PaymentKind.class)
            return PaymentKind.BANK_TRANSFER;
        else if (clazz == PhoneKind.class)
            return PhoneKind.FAX;
        else if (clazz == MarkKind.class)
            return MarkKind.COLLECTIVE;
        else if (clazz == RepresentativeKind.class)
            return RepresentativeKind.ASSOCIATION;
        else if (clazz == EarlierEntitlementRightKind.class)
            return EarlierEntitlementRightKind.CONTESTED_APPLICATION;
        else if (clazz == SeniorityKind.class)
            return SeniorityKind.CTM;
        else if (clazz == PersonRoleKind.class)
            return PersonRoleKind.APPLICANT;
        else if (clazz == DossierKind.class)
            return DossierKind.DESIGN_APPLICATION;
        else if (clazz == PublicationStatus.class)
            return PublicationStatus.PENDING;
        else if (clazz == SearchMode.class)
            return SearchMode.EXACTMATCH;
        else if (clazz == MatchClassType.class)
            return MatchClassType.SAME_CLASS;
        else if (clazz == MatchProximityType.class)
            return MatchProximityType.LINGUISTIC_MATCH;
        else if (clazz == SortBy.class)
            return SortBy.NICECLASS;
        else if (clazz == OrderBy.class)
            return OrderBy.ASC;
        else if (clazz == VerifiedTermResult.class)
            return VerifiedTermResult.HINT;
        else if (clazz == DocumentKind.class)
            return DocumentKind.COLLECTIVE_TRADE_MARK_REGULATION;
        else if (clazz == PaymentStatusCode.class)
            return PaymentStatusCode.ATTACHED;
        else if (clazz == InternationalTradeMarkCode.class)
            return InternationalTradeMarkCode.EU;
        else if (clazz == MarkFeature.class)
            return MarkFeature.COLOUR;
        else if (clazz == Map.class)
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
