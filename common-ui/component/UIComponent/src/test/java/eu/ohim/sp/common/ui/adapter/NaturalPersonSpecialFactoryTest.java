/*******************************************************************************
 * * $Id:: NaturalPersonSpecialFactoryTest.java 113496 2013-04-22 15:03:04Z kara#$
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
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.form.contact.CorrespondenceAddressForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonSpecialForm;
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
public class NaturalPersonSpecialFactoryTest
{
	
	@Mock
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @InjectMocks
    NaturalPersonSpecialFactory naturalPersonSpecialFactory = new NaturalPersonSpecialFactory();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        naturalPersonSpecialFactory.setAddressFactory(new AddressFactory());
    }

    @Test
    public void convertTo_nullForm_returnsEmptyCore()
    {
        /// Arrange
        Applicant expected = new Applicant();

        /// Act
        Applicant result = naturalPersonSpecialFactory.convertTo(null);

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
        NaturalPersonSpecialForm form = new NaturalPersonSpecialForm();
        form = CoreDomainHelper.formApplicantStub(form);
        form.setNationality("some nationality");

        Applicant expected = new Applicant();
        expected = CoreDomainHelper.coreApplicantStub();
        expected.setKind(PersonKind.NATURAL_PERSON_SPECIAL);
        expected.setName(new PersonName());
        expected.setNationality("some nationality");

        /// Act
        Applicant result = naturalPersonSpecialFactory.convertTo(form);

        /// Assert
        assertEquals(expected.getNationality(), result.getNationality());
        assertEquals(expected.getKind(), result.getKind());
        assertEquals(expected.getName(), result.getName());
        //assertEquals(expected, result);
    }

    @Test
    public void convertTo_filledCorrespAddress_returnsFilledCore()
    {
        /// Arrange
        NaturalPersonSpecialForm form = new NaturalPersonSpecialForm();
        form.setCorrespondenceAddresses(new ArrayList<CorrespondenceAddressForm>());
        CorrespondenceAddressForm caf1 = new CorrespondenceAddressForm();
        caf1.setAddressForm(CoreDomainHelper.formAddressStub());
        form.getCorrespondenceAddresses().add(caf1);

        Applicant expected = new Applicant();
        expected.setKind(PersonKind.NATURAL_PERSON_SPECIAL);
        expected.setCorrespondenceAddresses(new ArrayList<Address>());
        expected.setPrivacyWaiver(true);
        expected.setAddresses(new ArrayList<Address>());
        expected.getAddresses().add(new Address());
        expected.setName(new PersonName());
        expected.setEmails(new ArrayList<Email>());
        expected.setPhones(new ArrayList<Phone>());
        expected.setUrls(new ArrayList<String>());

        Address ca1 = CoreDomainHelper.coreAddressStub();
        expected.getCorrespondenceAddresses().add(ca1);

        /// Act
        Applicant result = naturalPersonSpecialFactory.convertTo(form);

        
        List<Address> addresses = expected.getAddresses();
        List<Address> addresses2 = result.getAddresses();
        
        List<Address> corresp = expected.getCorrespondenceAddresses();
        List<Address> corresp2 = result.getCorrespondenceAddresses();
        
        assertEquals(expected.getKind(), result.getKind());
        assertEquals(expected.getCorrespondenceAddresses(), result.getCorrespondenceAddresses());
        assertEquals(expected.isPrivacyWaiver(), result.isPrivacyWaiver());
        assertEquals(expected.getAddresses(), result.getAddresses());
        assertEquals(expected.getEmails(), result.getEmails());
        assertEquals(expected.getPhones(), result.getPhones());
        assertEquals(expected.getUrls(), result.getUrls());
        /// Assert
        //assertEquals(expected, result);
    }

    @Test
    public void convertFrom_nullCore_returnsEmptyForm()
    {
        Applicant core = new Applicant();
        core.setKind(PersonKind.NATURAL_PERSON_SPECIAL);

        NaturalPersonSpecialForm expected = new NaturalPersonSpecialForm();

        NaturalPersonSpecialForm result = naturalPersonSpecialFactory.convertFrom(core);

        assertEquals(expected.getType(), result.getType());
        
        //assertEquals(expected, result);
    }

    @Test
    public void convertFrom_filledCore_returnsFilledForm()
    {
        /// Arrange
        Applicant core;
        core = CoreDomainHelper.coreApplicantStub();
        core.setKind(PersonKind.NATURAL_PERSON_SPECIAL);
        core.setNationality("some nationality");

        NaturalPersonSpecialForm expected = new NaturalPersonSpecialForm();
        expected = CoreDomainHelper.formApplicantStub(expected);
        expected.setNationality("some nationality");

        /// Act
        NaturalPersonSpecialForm result = naturalPersonSpecialFactory.convertFrom(core);

        /// Assert
        assertEquals(expected.getNationality(), result.getNationality());
        assertEquals(expected.getType(), result.getType());
        //assertEquals(expected, result);
    }
}
