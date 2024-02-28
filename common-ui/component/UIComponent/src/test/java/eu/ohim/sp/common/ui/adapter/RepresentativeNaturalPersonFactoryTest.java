/*******************************************************************************
 * * $Id:: RepresentativeNaturalPersonFactoryTest.java 113496 2013-04-22 15:03:0#$
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

import eu.ohim.sp.common.ui.form.person.RepresentativeNaturalPersonForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.person.RepresentativeKind;

/**
 * @author ionitdi
 */
public class RepresentativeNaturalPersonFactoryTest
{
	@Mock
	private AddressFactory addressFactory;
	
	@Mock
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @InjectMocks
    RepresentativeNaturalPersonFactory representativeNaturalPersonFactory = new RepresentativeNaturalPersonFactory();

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
        Representative result = representativeNaturalPersonFactory.convertTo(null);

        /// Assert
        assertEquals(expected.getIdentifiers(), result.getIdentifiers());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getAddresses(), result.getAddresses());
    }

    @Test
    public void convertTo_filledForm_returnsFilledCore()
    {
        /// Arrange
        RepresentativeNaturalPersonForm form = new RepresentativeNaturalPersonForm();
        form = CoreDomainHelper.formRepresentativeStub(form);
        form.setFirstName("some first name");
        form.setSurname("some surname");
        form.setTitle("title");

        Representative expected = new Representative();
        expected = CoreDomainHelper.coreRepresentativeStub();
        expected.setRepresentativeKind(RepresentativeKind.OTHER);
        expected.setKind(PersonKind.NATURAL_PERSON);
        
        PersonName personName = new PersonName();
        personName.setFirstName("some first name");
        personName.setLastName("some surname");
        personName.setTitle("title");
        expected.setName(personName);


        /// Act
        Representative result = representativeNaturalPersonFactory.convertTo(form);

        /// Assert
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getKind(), result.getKind());
        //assertEquals(expected, result);
    }

    @Test
    public void convertFrom_nullCore_returnsNullForm()
    {
        /// Arrange
        RepresentativeNaturalPersonForm expected = new RepresentativeNaturalPersonForm();

        /// Act
        RepresentativeNaturalPersonForm result = representativeNaturalPersonFactory.convertFrom(null);

        /// Assert
        assertEquals(expected.getAddress(), result.getAddress());
        assertEquals(expected.getName(), result.getName());
        
        //assertEquals(expected, result);
    }

    @Test
    public void convertFrom_filledCore_returnsFilledForm()
    {
        /// Arrange
        Representative core = new Representative();
        core = CoreDomainHelper.coreRepresentativeStub();
        core.setRepresentativeKind(RepresentativeKind.OTHER);
        core.setKind(PersonKind.NATURAL_PERSON);
        
        PersonName personName = new PersonName();
        personName.setFirstName("some first name");
        personName.setLastName("some surname");
        core.setName(personName);

        RepresentativeNaturalPersonForm expected = new RepresentativeNaturalPersonForm();
        expected = CoreDomainHelper.formRepresentativeStub(expected);
        expected.setFirstName("some first name");
        expected.setSurname("some surname");

        /// Act
        RepresentativeNaturalPersonForm result = representativeNaturalPersonFactory.convertFrom(core);

        /// Assert
        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getFirstName(), result.getFirstName());
        assertEquals(expected.getSurname(), result.getSurname());
        assertEquals(expected.getOrganization(), result.getOrganization());
        //assertEquals(expected, result);
    }
}
