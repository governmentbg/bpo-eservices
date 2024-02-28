/*******************************************************************************
 * * $Id:: LegalPractitionerFactoryTest.java 113496 2013-04-22 15:03:04Z karalch $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.form.person.LegalPractitionerForm;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.person.RepresentativeKind;

/**
 * @author ionitdi
 */
public class LegalPractitionerFactoryTest
{

	@Mock
	private AddressFactory addressFactory;

    @InjectMocks
    LegalPractitionerFactory legalPractitionerFactory = new LegalPractitionerFactory();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void convertTo_nullForm_returnsEmptyCore()
    {
        /// Arrange
        Representative expected = new Representative();

        /// Act
        Representative result = legalPractitionerFactory.convertTo(null);

        /// Assert
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getAddresses(), result.getAddresses());
        //assertEquals(expected, result);
    }

    @Test
    public void convertTo_filledForm_returnsFilledCore()
    {
        /// Arrange
        LegalPractitionerForm form = new LegalPractitionerForm();
        form = CoreDomainHelper.formRepresentativeStub(form);
        form.setFirstName("some first name");
        form.setMiddleName("some middle name");
        form.setSurname("some surname");
        form.setAssociationId("some id");
        form.setAssociationName("some name");

        Representative expected = new Representative();
        expected = CoreDomainHelper.coreRepresentativeStub();
        expected.setRepresentativeKind(RepresentativeKind.LAWYER);
        expected.setKind(PersonKind.NATURAL_PERSON);
        
        PersonName personName = new PersonName();
        personName.setFirstName("some first name");
        personName.setMiddleName("some middle name");
        personName.setLastName("some surname");
        personName.setOrganizationName("some name");
        expected.setName(personName);

        /// Act
        Representative result = legalPractitionerFactory.convertTo(form);

        /// Assert
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getKind(), result.getKind());
        //assertEquals(expected, result);
    }

    @Test
    public void convertFrom_nullCore_returnsNullForm()
    {
        /// Arrange
        LegalPractitionerForm expected = new LegalPractitionerForm();

        /// Act
        LegalPractitionerForm result = legalPractitionerFactory.convertFrom(null);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertFrom_filledCore_returnsFilledForm()
    {
        /// Arrange
        Representative core = new Representative();
        core = CoreDomainHelper.coreRepresentativeStub();
        core.setRepresentativeKind(RepresentativeKind.LAWYER);
        
        PersonName personName = new PersonName();
        personName.setFirstName("some first name");
        personName.setMiddleName("some middle name");
        personName.setLastName("some surname");
        personName.setOrganizationName("some name");
        core.setName(personName);

        LegalPractitionerForm expected = new LegalPractitionerForm();
        expected = CoreDomainHelper.formRepresentativeStub(expected);
        expected.setFirstName("some first name");
        expected.setMiddleName("some middle name");
        expected.setSurname("some surname");
        expected.setAssociationId("some id");
        expected.setAssociationName("some name");


        /// Act
        LegalPractitionerForm result = legalPractitionerFactory.convertFrom(core);

        /// Assert
        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getFirstName(), result.getFirstName());
        assertEquals(expected.getMiddleName(), result.getMiddleName());
        assertEquals(expected.getSurname(), result.getSurname());
        assertEquals(expected.getAssociationName(), result.getAssociationName());
        //assertEquals(expected, result);
    }
}
