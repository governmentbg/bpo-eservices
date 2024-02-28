/*******************************************************************************
 * * $Id:: LegalEntityFactoryTest.java 113496 2013-04-22 15:03:04Z karalch       $
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
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;

/**
 * @author ionitdi
 */
public class LegalEntityFactoryTest
{
	
	@Mock
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @InjectMocks
    LegalEntityFactory legalEntityFactory = new LegalEntityFactory();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        legalEntityFactory.setAddressFactory(new AddressFactory());
    }

    @Test
    public void convertTo_nullForm_returnsEmptyCore()
    {
        /// Arrange
        Applicant expected = new Applicant();

        /// Act
        Applicant result = legalEntityFactory.convertTo(null);

        /// Assert
        assertEquals(expected.getAddresses(), result.getAddresses());
        assertEquals(expected.getName(), result.getName());
        //assertEquals(expected, result);
    }

    @Test
    public void convertTo_filledForm_returnsFilledCore()
    {
        /// Arrange
        LegalEntityForm form = new LegalEntityForm();
        form = CoreDomainHelper.formApplicantStub(form);
        form.setBusinessVatNumber("some vat");
        form.setLegalForm("legal");
        form.setCountryOfRegistration("some nationality");
        form.setStateOfIncorporation("some state");
        form.setName("some name");

        Applicant expected = new Applicant();
        List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
        PersonIdentifier personIdentifier = new PersonIdentifier();
        personIdentifier.setValue("some id");
        identifiers.add(personIdentifier);
        personIdentifier = new PersonIdentifier();
        personIdentifier.setValue("some nobr");
        identifiers.add(personIdentifier);
        personIdentifier = new PersonIdentifier();
        personIdentifier.setValue("some tax no");
        identifiers.add(personIdentifier);
        expected.setIdentifiers(identifiers);
        
        
        expected = CoreDomainHelper.coreApplicantStub();
        expected.setKind(PersonKind.LEGAL_ENTITY);
        
        expected.setIncorporationCountry("some nationality");
        expected.setIncorporationState("some state");
        
        PersonName personName = new PersonName();
        personName.setOrganizationName("some name");
        expected.setName(personName);
        
        /// Act
        Applicant result = legalEntityFactory.convertTo(form);

        /// Assert
        assertEquals(expected.getKind(), result.getKind());
        assertEquals(expected.getIncorporationCountry(), result.getIncorporationCountry());
        assertEquals(expected.getIncorporationState(), result.getIncorporationState());
        assertEquals(expected.getName(), result.getName());
    }

    @Test
    public void convertTo_filledCorrespAddress_returnsFilledCore()
    {
        /// Arrange
        LegalEntityForm form = new LegalEntityForm();
        form.setCorrespondenceAddresses(new ArrayList<CorrespondenceAddressForm>());
        CorrespondenceAddressForm ca1 = new CorrespondenceAddressForm();
        ca1.setAddressForm(CoreDomainHelper.formAddressStub());
        form.getCorrespondenceAddresses().add(ca1);

        Applicant expected = new Applicant();
        expected.setKind(PersonKind.LEGAL_ENTITY);
        expected.setCorrespondenceAddresses(new ArrayList<Address>());
        expected.setAddresses(new ArrayList<Address>());
        expected.setPrivacyWaiver(true);
        
        expected.setName(new PersonName());
        expected.setEmails(new ArrayList<Email>());
        expected.setPhones(new ArrayList<Phone>());
        expected.setUrls(new ArrayList<String>());

        Address corea1 = CoreDomainHelper.coreAddressStub();
        expected.getCorrespondenceAddresses().add(corea1);

        /// Act
        Applicant result = legalEntityFactory.convertTo(form);

        /// Assert
        assertEquals(expected.getCorrespondenceAddresses(), result.getCorrespondenceAddresses());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getEmails(), result.getEmails());
        assertEquals(expected.getPhones(), result.getPhones());
        assertEquals(expected.getUrls(), result.getUrls());
    }

    @Test
    public void convertFrom_nullCore_returnsEmptyForm()
    {
        Applicant core = new Applicant();
        core.setKind(PersonKind.LEGAL_ENTITY);

        LegalEntityForm expected = new LegalEntityForm();

        LegalEntityForm result = legalEntityFactory.convertFrom(core);

        assertEquals(expected.getType(), result.getType());
    }

    @Test
    public void convertFrom_filledCore_returnsFilledForm()
    {
        /// Arrange
        Applicant core;
        core = CoreDomainHelper.coreApplicantStub();
        
        List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
        PersonIdentifier personIdentifier = new PersonIdentifier();
        personIdentifier.setValue("some id");
        identifiers.add(personIdentifier);
        personIdentifier = new PersonIdentifier();
        personIdentifier.setValue("some vat");
        identifiers.add(personIdentifier);
        core.setIdentifiers(identifiers);
        
        core.setKind(PersonKind.LEGAL_ENTITY);
        core.setIncorporationCountry("some nationality");
        core.setLegalForm("legal");
        core.setIncorporationState("some state");
        
        PersonName personName = new PersonName();
        personName.setOrganizationName("some name");
        core.setName(personName);

        LegalEntityForm expected = new LegalEntityForm();
        expected = CoreDomainHelper.formApplicantStub(expected);
        expected.setStateOfIncorporation("some state");
        expected.setLegalForm("legal");
        expected.setBusinessVatNumber("some vat");
        expected.setCountryOfRegistration("some nationality");
        expected.setName("some name");

        /// Act
        LegalEntityForm result = legalEntityFactory.convertFrom(core);

        /// Assert
        assertEquals(expected.getDomicile(), result.getDomicile());
        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getLegalForm(), result.getLegalForm());
        //assertEquals(expected, result);
    }
}
