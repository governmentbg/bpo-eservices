/*******************************************************************************
 * * $Id:: NaturalPersonFactoryTest.java 113496 2013-04-22 15:03:04Z karalch     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.form.contact.CorrespondenceAddressForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;

/**
 * @author ionitdi
 */
public class NaturalPersonFactoryTest
{
	
	@Mock
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @InjectMocks
    NaturalPersonFactory naturalPersonFactory = new NaturalPersonFactory();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        naturalPersonFactory.setAddressFactory(new AddressFactory());
    }

    @Test
    public void convertTo_nullForm_returnsEmptyCore()
    {
        /// Arrange
        Applicant expected = new Applicant();

        /// Act
        Applicant result = naturalPersonFactory.convertTo(null);

        /// Assert
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getAddresses(), result.getAddresses());
        assertEquals(expected.getCorrespondenceAddresses(), result.getCorrespondenceAddresses());
        
        //assertEquals(expected, result);
    }

    @Test
    public void convertTo_filledForm_returnsFilledCore()
    {
        /// Arrange
        NaturalPersonForm form = new NaturalPersonForm();
        form = CoreDomainHelper.formApplicantStub(form);
        form.setFirstName("some first name");
        form.setSurname("some surname");
        form.setNationality("some nationality");

        Applicant expected = new Applicant();
        expected = CoreDomainHelper.coreApplicantStub();
        expected.setKind(PersonKind.NATURAL_PERSON);
        
        PersonName personName = new PersonName();
        personName.setFirstName("some first name");
        personName.setLastName("some surname");
        expected.setName(personName);
        
        expected.setNationality("some nationality");

        /// Act
        Applicant result = naturalPersonFactory.convertTo(form);

        /// Assert
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getKind(), result.getKind());
        assertEquals(expected.getNationality(), result.getNationality());
        //assertEquals(expected, result);
    }

    @Test
    public void convertTo_filledCorrespAddress_returnsFilledCore()
    {
        /// Arrange
        NaturalPersonForm form = new NaturalPersonForm();
        form.setCorrespondenceAddresses(new ArrayList<CorrespondenceAddressForm>());
        CorrespondenceAddressForm caf1 = new CorrespondenceAddressForm();
        caf1.setAddressForm(CoreDomainHelper.formAddressStub());
        form.getCorrespondenceAddresses().add(caf1);

        Applicant expected = new Applicant();
        expected.setKind(PersonKind.NATURAL_PERSON);
        expected.setCorrespondenceAddresses(new ArrayList<Address>());
        expected.setAddresses(new ArrayList<Address>());
        expected.getAddresses().add(new Address());
        expected.setPrivacyWaiver(true);
        
        expected.setName(new PersonName());
        expected.setEmails(new ArrayList<Email>());
        expected.setPhones(new ArrayList<Phone>());
        expected.setUrls(new ArrayList<String>());
        
        Address ca1 = CoreDomainHelper.coreAddressStub();
        expected.getCorrespondenceAddresses().add(ca1);

        /// Act
        Applicant result = naturalPersonFactory.convertTo(form);

        /// Assert
        assertEquals(expected.getKind(), result.getKind());
        assertEquals(expected.getCorrespondenceAddresses(), result.getCorrespondenceAddresses());
        assertEquals(expected.getAddresses(), result.getAddresses());
        assertEquals(expected.isPrivacyWaiver(), result.isPrivacyWaiver());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getEmails(), result.getEmails());
        assertEquals(expected.getPhones(), result.getPhones());
        assertEquals(expected.getUrls(), result.getUrls());
        //assertEquals(expected, result);
    }

    @Test
    public void convertFrom_nullCore_returnsEmptyForm()
    {
        Applicant core = new Applicant();
        core.setKind(PersonKind.NATURAL_PERSON);

        NaturalPersonForm expected = new NaturalPersonForm();

        NaturalPersonForm result = naturalPersonFactory.convertFrom(core);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getAddress(), result.getAddress());
        assertEquals(expected.getName(), result.getName());
        //assertEquals(expected, result);
    }

    @Test
    public void convertFrom_filledCore_returnsFilledForm()
    {
        /// Arrange
        Applicant core;
        core = CoreDomainHelper.coreApplicantStub();
        core.setKind(PersonKind.NATURAL_PERSON);
        
        PersonName personName = new PersonName();
        personName.setFirstName("some first name");
        personName.setLastName("some surname");
        core.setName(personName);
        
        core.setNationality("some nationality");

        NaturalPersonForm expected = new NaturalPersonForm();
        expected = CoreDomainHelper.formApplicantStub(expected);
        expected.setFirstName("some first name");
        expected.setSurname("some surname");
        expected.setNationality("some nationality");

        /// Act
        NaturalPersonForm result = naturalPersonFactory.convertFrom(core);

        /// Assert
        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getFirstName(), result.getFirstName());
        assertEquals(expected.getSurname(), result.getSurname());
        assertEquals(expected.getNationality(), result.getNationality());
        //assertEquals(expected, result);
    }
}
