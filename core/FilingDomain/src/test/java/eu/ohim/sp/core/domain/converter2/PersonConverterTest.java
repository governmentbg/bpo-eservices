/*
 *  FspDomain:: PersonConverterTest 17/12/13 13:37 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter2;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.*;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 31/10/13
 * Time: 15:33
 * To change this template use File | Settings | File Templates.
 */
public class PersonConverterTest {


    public static Applicant fillApplicant() {
        Applicant applicant = new Applicant();
        applicant.setLegalForm("screw");
        applicant.setNationality("EL");
        applicant.setDomicileCountry("GR");

        applicant.setDomicileLocality("test");
        applicant.setIncorporationCountry("GR");
        applicant.setIncorporationState("Attica");

        applicant.setCorrespondenceAddresses(new ArrayList<Address>());
        applicant.getCorrespondenceAddresses().add(new Address());

        applicant.setEmails(new ArrayList<Email>());
        applicant.getEmails().add(new Email());
        applicant.getEmails().get(0).setEmailAddress("test@oami.gr");
        applicant.getEmails().add(new Email());
        applicant.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

        applicant.setPhones(new ArrayList<Phone>());
        applicant.getPhones().add(new Phone());
        applicant.getPhones().get(0).setNumber("2133542352");
        applicant.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

        applicant.getCorrespondenceAddresses().get(0).setCity("London");
        applicant.getCorrespondenceAddresses().get(0).setStreet("Agias Lauras 36");
        applicant.getCorrespondenceAddresses().get(0).setState("Attica");
        applicant.getCorrespondenceAddresses().get(0).setCountry("GR");
        applicant.getCorrespondenceAddresses().get(0).setStreetNumber("36");
        applicant.getCorrespondenceAddresses().get(0).setPostalName("Mr Christos Papas");

        applicant.setAddresses(new ArrayList<Address>());

        applicant.getAddresses().add(new Address());
        applicant.getAddresses().get(0).setCity("London Address");
        applicant.getAddresses().get(0).setStreet("Agias Lauras 36 Address");
        applicant.getAddresses().get(0).setState("Attica Address");
        applicant.getAddresses().get(0).setCountry("GR");
        applicant.getAddresses().get(0).setStreetNumber("36 B");

        applicant.setUrls(new ArrayList<String>());
        applicant.getUrls().add("some url");

        applicant.setName(new PersonName());

        applicant.getName().setFirstName("Nikos");
        applicant.getName().setMiddleName("Papas");
        applicant.getName().setLastName("Papadopoulos");

        applicant.getName().setSecondLastName("secondLastName");
        applicant.getName().setOrganizationName("organizationName");
        return applicant;
    }

    @Test
    public void test() {
        fillPerson(Assignee.class);
    }


    public static <T extends PersonRole> T fillPerson(Class<T> clazz) {
        PodamFactory factory = new PodamFactoryImpl(RandomDataProviderStrategy.getInstance(1));

        T personInstance = null;
        try {
            personInstance = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        personInstance.setLegalForm("legalForm");
        personInstance.setPersonNumber("personNumber");
        personInstance.setNationality("nationality");
        personInstance.setDomicileCountry("GR");
        personInstance.setDomicileLocality("locality");
        personInstance.setIncorporationCountry("GR");
        personInstance.setIncorporationState("Attica");

        personInstance.setCorrespondenceAddresses(new ArrayList<Address>());
        personInstance.getCorrespondenceAddresses().add(factory.manufacturePojo(Address.class));
        personInstance.setCorrespondenceEmails(new ArrayList<Email>());
        personInstance.getCorrespondenceEmails().add(factory.manufacturePojo(Email.class));
        personInstance.setCorrespondencePhones(new ArrayList<Phone>());
        personInstance.getCorrespondencePhones().add(factory.manufacturePojo(Phone.class));
        personInstance.setAddresses(new ArrayList<Address>());
        personInstance.getAddresses().add(factory.manufacturePojo(Address.class));

        personInstance.setName(factory.manufacturePojo(PersonName.class));
        personInstance.setReference("reference");

        return personInstance;
    }

    public Representative fillRepresentative() {
        Representative representative = new Representative();
        representative.setLegalForm("screw");
        representative.setNationality("EL");
        representative.setDomicileCountry("GR");

        representative.setDomicileLocality("test");
        representative.setIncorporationCountry("GR");
        representative.setIncorporationState("Attica");

        representative.setCorrespondenceAddresses(new ArrayList<Address>());
        representative.getCorrespondenceAddresses().add(new Address());

        representative.setEmails(new ArrayList<Email>());
        representative.getEmails().add(new Email());
        representative.getEmails().get(0).setEmailAddress("test@oami.gr");
        representative.getEmails().add(new Email());
        representative.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

        representative.setPhones(new ArrayList<Phone>());
        representative.getPhones().add(new Phone());
        representative.getPhones().get(0).setNumber("2133542352");
        representative.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

        representative.getCorrespondenceAddresses().get(0).setCity("London");
        representative.getCorrespondenceAddresses().get(0).setStreet("Agias Lauras 36");
        representative.getCorrespondenceAddresses().get(0).setState("Attica");
        representative.getCorrespondenceAddresses().get(0).setCountry("GR");
        representative.getCorrespondenceAddresses().get(0).setStreetNumber("36");
        representative.getCorrespondenceAddresses().get(0).setPostalName("Mr Christos Papas");

        representative.setAddresses(new ArrayList<Address>());

        representative.getAddresses().add(new Address());
        representative.getAddresses().get(0).setCity("London Address");
        representative.getAddresses().get(0).setStreet("Agias Lauras 36 Address");
        representative.getAddresses().get(0).setState("Attica Address");
        representative.getAddresses().get(0).setCountry("GR");
        representative.getAddresses().get(0).setStreetNumber("36 B");

        representative.setUrls(new ArrayList<String>());
        representative.getUrls().add("some url");
        representative.getUrls().add("some url 2");

        representative.setName(new PersonName());

        representative.getName().setFirstName("Christos");
        representative.getName().setMiddleName("Paparas");
        representative.getName().setLastName("Karalis");
        return representative;
    }

    public static String resolve(Object object, String elExpression, Class clazz) {
        ExpressionFactory factory = new ExpressionFactoryImpl();

        // package de.odysseus.el.util provides a ready-to-use subclass of ELContext
        SimpleContext context = new SimpleContext();

        context.setVariable("object", factory.createValueExpression(object, clazz));
        // parse our expression
        ValueExpression expr = factory.createValueExpression(context, elExpression, String.class);
        System.out.println((String) expr.getValue(context));
        return (String) expr.getValue(context);

    }

    public static <T> T resolve(Object object, String elExpression, Class clazz, Class<T> expectedClass) {
        ExpressionFactory factory = new ExpressionFactoryImpl();

        // package de.odysseus.el.util provides a ready-to-use subclass of ELContext
        SimpleContext context = new SimpleContext();

        context.setVariable("object", factory.createValueExpression(object, clazz));
        // parse our expression
        ValueExpression expr = factory.createValueExpression(context, elExpression, expectedClass);
        return (T) expr.getValue(context);

    }


    //"${object.addressBook.formattedNameAddress.address.formattedAddress.country.value()}"
    public static <J, T extends PersonRole> boolean validatePerson(T personRole, J jaxbObject, Class<J> clazz) throws InstantiationException, IllegalAccessException {
        assertEquals(personRole.getLegalForm(), resolve(jaxbObject, "${object.legalForm}", clazz));
        assertEquals(personRole.getNationality(), resolve(jaxbObject, "${object.nationality}", clazz));
        assertEquals(personRole.getDomicileCountry(), resolve(jaxbObject, "${object.domicileCountry.value()}", clazz));
        assertEquals(personRole.getDomicileLocality(), resolve(jaxbObject, "${object.domicileLocality}", clazz));

        assertEquals(personRole.getIncorporationState(), resolve(jaxbObject, "${object.incorporationState}", clazz));

        assertEquals(personRole.getAddresses().get(0).getCity(), resolve(jaxbObject, "${object.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCity()}", clazz));
        assertEquals(personRole.getAddresses().get(0).getCountry(), resolve(jaxbObject, "${object.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCountry().value()}", clazz));
        assertEquals(personRole.getAddresses().get(0).getState(), resolve(jaxbObject, "${object.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getState()}", clazz));
        assertEquals(personRole.getAddresses().get(0).getStreet(), resolve(jaxbObject, "${object.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreet()}", clazz));
        assertEquals(personRole.getAddresses().get(0).getStreetNumber(), resolve(jaxbObject, "${object.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreetNumber()}", clazz));

        int checker = personRole.getEmails()==null?0:personRole.getEmails().size();
        for (Object emailXML : resolve(jaxbObject, "${object.getAddressBook().getContactInformationDetails().getEmail()}", clazz, ArrayList.class) ) {
            for (Email email : personRole.getEmails()) {
                if (StringUtils.equals((String) emailXML, email.getEmailAddress())) {
                    checker--;
                }
            }
        }
        assertEquals(checker, 0);

        checker = personRole.getPhones()==null?0:personRole.getPhones().size();
        for (Object phoneXML : resolve(jaxbObject, "${object.getAddressBook().getContactInformationDetails().getPhone()}", clazz, ArrayList.class) ) {
            for (Phone phone : personRole.getPhones()) {
                if (StringUtils.equals(resolve(phoneXML, "${object.getValue()}", phoneXML.getClass()), phone.getNumber())
                        && StringUtils.equals(resolve(phoneXML, "${object.getPhoneKind().value()}", phoneXML.getClass()), phone.getPhoneKind().value())) {
                    checker--;
                }
            }
        }
        assertEquals(checker, 0);

        assertEquals(personRole.getName().getFirstName(), resolve(jaxbObject, "${object.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getFirstName()}", clazz));
        assertEquals(personRole.getName().getMiddleName(), resolve(jaxbObject, "${object.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getMiddleName()}", clazz));
        assertEquals(personRole.getName().getLastName(), resolve(jaxbObject, "${object.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getLastName()}", clazz));
        assertEquals(personRole.getName().getSecondLastName(), resolve(jaxbObject, "${object.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getSecondLastName()}", clazz));
        assertEquals(personRole.getName().getOrganizationName(), resolve(jaxbObject, "${object.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getOrganizationName()}", clazz));

        checker = personRole.getCorrespondenceAddresses()==null?0:personRole.getCorrespondenceAddresses().size();
        for (Object addressXML : resolve(jaxbObject, "${object.getCorrespondenceAddresses()}", clazz, ArrayList.class) ) {
            for (Address address : personRole.getCorrespondenceAddresses()) {
                if (StringUtils.equals(resolve(addressXML, "${object.getFormattedNameAddress().getAddress().getFormattedAddress().getCity()}", addressXML.getClass()), address.getCity())
                        && StringUtils.equals(resolve(addressXML, "${object.getFormattedNameAddress().getAddress().getFormattedAddress().getCountry().value()}", addressXML.getClass()), address.getCountry())
                        && StringUtils.equals(resolve(addressXML, "${object.getFormattedNameAddress().getAddress().getFormattedAddress().getStreet()}", addressXML.getClass()), address.getStreet())
                        && StringUtils.equals(resolve(addressXML, "${object.getFormattedNameAddress().getAddress().getFormattedAddress().getStreetNumber()}", addressXML.getClass()), address.getStreetNumber())
                        && StringUtils.equals(resolve(addressXML, "${object.getFormattedNameAddress().getAddress().getFormattedAddress().getState()}", addressXML.getClass()), address.getState())
                        && StringUtils.equals(resolve(addressXML, "${object.getFormattedNameAddress().getName().getFreeFormatName().getFreeFormatNameDetails().getFreeFormatNameLine().get(0).getValue()}", addressXML.getClass()), address.getPostalName())) {
                    checker--;
                }
            }
        }
        assertEquals(checker, 0);

        checker = personRole.getUrls()==null?0:personRole.getUrls().size();
        for (Object urlXML : resolve(jaxbObject, "${object.getAddressBook().getContactInformationDetails().getURL()}", clazz, ArrayList.class) ) {
            for (String url : personRole.getUrls()) {
                if (StringUtils.equals((String) urlXML, url)) {
                    checker--;
                }
            }
        }
        assertEquals(checker, 0);

        return true;
    }

}
