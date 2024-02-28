/*******************************************************************************
 * * $Id:: EmployeeRepresentativeFactoryTest.java 113496 2013-04-22 15:03:04Z ka#$
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

import eu.ohim.sp.common.ui.form.person.EmployeeRepresentativeForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.person.RepresentativeKind;

/**
 * @author ionitdi
 */
public class EmployeeRepresentativeFactoryTest
{
	
	@Mock
	private AddressFactory addressFactory;
	
	@Mock
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @InjectMocks
    EmployeeRepresentativeFactory employeeRepresentativeFactory = new EmployeeRepresentativeFactory();

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
        Representative result = employeeRepresentativeFactory.convertTo(null);

        /// Assert
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getAddresses(), result.getAddresses());
    }

    @Test
    public void convertTo_filledForm_returnsFilledCore()
    {
        /// Arrange
        EmployeeRepresentativeForm form = new EmployeeRepresentativeForm();
        form = CoreDomainHelper.formRepresentativeStub(form);
        form.setFirstName("some first name");
        form.setSurname("some surname");
        form.setEconomicConnections(false);
        form.setNatureOfEconomicConnections("some nature");
        form.setNameOfEmployer("some employer");


        Representative expected = new Representative();
        expected = CoreDomainHelper.coreRepresentativeStub();
        expected.setRepresentativeKind(RepresentativeKind.EMPLOYEE);
        expected.setKind(PersonKind.NATURAL_PERSON);
        
        PersonName personName = new PersonName();
        personName.setFirstName("some first name");
        personName.setMiddleName("");
        personName.setLastName("some surname");
        personName.setOrganizationName("");
        expected.setName(personName);

        expected.setEconomicConnectionIndicator(false);
        expected.setEconomicConnectionNature("some nature");

        /// Act
        Representative result = employeeRepresentativeFactory.convertTo(form);

        /// Assert
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getKind(), result.getKind());
        assertEquals(expected.isEconomicConnectionIndicator(), result.isEconomicConnectionIndicator());
        assertEquals(expected.getEconomicConnectionNature(), result.getEconomicConnectionNature());
        //assertEquals(expected, result);
    }

    @Test
    public void convertFrom_nullCore_returnsNullForm()
    {
        /// Arrange
        EmployeeRepresentativeForm expected = new EmployeeRepresentativeForm();

        /// Act
        EmployeeRepresentativeForm result = employeeRepresentativeFactory.convertFrom(null);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertFrom_filledCore_returnsFilledForm()
    {
        /// Arrange
        Representative core = new Representative();
        core = CoreDomainHelper.coreRepresentativeStub();
        core.setRepresentativeKind(RepresentativeKind.EMPLOYEE);
        
        PersonName personName = new PersonName();
        personName.setFirstName("some first name");
        personName.setLastName("some last name");
        personName.setOrganizationName("some employer");
        core.setName(personName);
        
        core.setEconomicConnectionIndicator(false);
        core.setEconomicConnectionNature("some nature");
        core.setNameOfEmployer("some employer");

        EmployeeRepresentativeForm expected = new EmployeeRepresentativeForm();
        expected = CoreDomainHelper.formRepresentativeStub(expected);
        expected.setFirstName("some first name");
        expected.setSurname("some last name");
        expected.setEconomicConnections(false);
        expected.setNatureOfEconomicConnections("some nature");
        expected.setNameOfEmployer("some employer");


        /// Act

        EmployeeRepresentativeForm result = employeeRepresentativeFactory.convertFrom(core);
        
        /// Assert
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getFirstName(), result.getFirstName());
        assertEquals(expected.getSurname(), result.getSurname());
        //assertEquals(expected.getOrganization(), result.getOrganization());
        assertEquals(expected.getNameOfEmployer(), result.getNameOfEmployer());
        assertEquals(expected.isConsentForPublishingPersonalInfo(), 
        		result.isConsentForPublishingPersonalInfo());
        assertEquals(expected.getNatureOfEconomicConnections(), result.getNatureOfEconomicConnections());
        assertEquals(expected.getEconomicConnections(), result.getEconomicConnections());
        //assertEquals(expected, result);
    }
}
