/*******************************************************************************
 * * $Id:: ProfessionalPractitionerFactoryTest.java 113496 2013-04-22 15:03:04Z #$
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

import eu.ohim.sp.common.ui.form.person.ProfessionalPractitionerForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.person.RepresentativeKind;

/**
 * @author ionitdi
 */
public class ProfessionalPractitionerFactoryTest
{
	
	@Mock
	private AddressFactory addressFactory;
	
	@Mock
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @InjectMocks
    ProfessionalPractitionerFactory professionalPractitionerFactory = new ProfessionalPractitionerFactory();

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
        Representative result = professionalPractitionerFactory.convertTo(null);

        /// Assert
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getDomicileCountry(), result.getDomicileCountry());
        assertEquals(expected.getDomicileLocality(), result.getDomicileLocality());
        assertEquals(expected.getIdentifiers(), result.getIdentifiers());
        assertEquals(expected.getAddresses(), result.getAddresses());
    }

    @Test
    public void convertTo_filledForm_returnsFilledCore()
    {
        /// Arrange
        ProfessionalPractitionerForm form = new ProfessionalPractitionerForm();
        form = CoreDomainHelper.formRepresentativeStub(form);
        form.setFirstName("some first name");
        form.setSurname("some surname");
        form.setAssociationId("some id");
        form.setAssociationName("some name");


        Representative expected = new Representative();
        expected = CoreDomainHelper.coreRepresentativeStub();
        expected.setRepresentativeKind(RepresentativeKind.PROFESSIONAL_REPRESENTATIVE);
        expected.setKind(PersonKind.NATURAL_PERSON);
        
        PersonName personName = new PersonName();
        personName.setFirstName("some first name");
        personName.setLastName("some surname");
        personName.setOrganizationName("some name");
        expected.setName(personName);
        
        /// Act
        Representative result = professionalPractitionerFactory.convertTo(form);

        /// Assert
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getKind(), result.getKind());
        //assertEquals(expected, result);
    }

    @Test
    public void convertFrom_nullCore_returnsNullForm()
    {
        /// Arrange
        ProfessionalPractitionerForm expected = new ProfessionalPractitionerForm();

        /// Act
        ProfessionalPractitionerForm result = professionalPractitionerFactory.convertFrom(null);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertFrom_filledCore_returnsFilledForm()
    {
        /// Arrange
        Representative core = new Representative();
        core = CoreDomainHelper.coreRepresentativeStub();
        core.setRepresentativeKind(RepresentativeKind.PROFESSIONAL_REPRESENTATIVE);
        
        PersonName personName = new PersonName();
        personName.setFirstName("some first name");
        personName.setLastName("some surname");
        personName.setOrganizationName("some name");
        core.setName(personName);


        ProfessionalPractitionerForm expected = new ProfessionalPractitionerForm();
        expected = CoreDomainHelper.formRepresentativeStub(expected);
        expected.setFirstName("some first name");
        expected.setSurname("some surname");
        expected.setAssociationId("some id");
        expected.setAssociationName("some name");

        /// Act
        ProfessionalPractitionerForm result = professionalPractitionerFactory.convertFrom(core);

        /// Assert
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getFirstName(), result.getFirstName());
        assertEquals(expected.getSurname(), result.getSurname());
        assertEquals(expected.getOrganization(), result.getOrganization());
        //assertEquals(expected, result);
    }
}

