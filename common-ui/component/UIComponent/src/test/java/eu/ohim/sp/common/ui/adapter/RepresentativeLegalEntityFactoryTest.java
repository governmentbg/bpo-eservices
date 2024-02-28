/*******************************************************************************
 * * $Id:: RepresentativeLegalEntityFactoryTest.java 113496 2013-04-22 15:03:04Z#$
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

import eu.ohim.sp.common.ui.form.person.RepresentativeLegalEntityForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.person.RepresentativeKind;

/**
 * @author ionitdi
 */
public class RepresentativeLegalEntityFactoryTest
{
	@Mock
	private AddressFactory addressFactory;
	
	@Mock
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @InjectMocks
    RepresentativeLegalEntityFactory representativeLegalEntityFactory = new RepresentativeLegalEntityFactory();

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
        Representative result = representativeLegalEntityFactory.convertTo(null);

        /// Assert
        assertEquals(expected.getIdentifiers(), result.getIdentifiers());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getAddresses(), result.getAddresses());
    }

    @Test
    public void convertTo_filledForm_returnsFilledCore()
    {
        /// Arrange
        RepresentativeLegalEntityForm form = new RepresentativeLegalEntityForm();
        form = CoreDomainHelper.formRepresentativeStub(form);
        form.setBusinessVatNumber("some vat");
        form.setLegalForm("some name of legal entity");
        form.setNameOfLegalEntity("some name of legal entity");

        Representative expected = new Representative();
        expected = CoreDomainHelper.coreRepresentativeStub();
        expected.setRepresentativeKind(RepresentativeKind.OTHER);
        expected.setKind(PersonKind.LEGAL_ENTITY);
        
        List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
        PersonIdentifier personIdentifier = new PersonIdentifier();
        personIdentifier.setIdentifierKindCode("VAT Number");
        personIdentifier.setValue("some vat");
        identifiers.add(personIdentifier);
        expected.setIdentifiers(identifiers);
        
        PersonName personName = new PersonName();
        personName.setOrganizationName("some name of legal entity");
        expected.setName(personName);
        
        expected.setLegalForm("some name of legal entity");


        /// Act
        Representative result = representativeLegalEntityFactory.convertTo(form);

        /// Assert
        assertEquals(expected.getLegalForm(), result.getLegalForm());
        //assertEquals(expected, result);
    }

    @Test
    public void convertFrom_nullCore_returnsNullForm()
    {
        /// Arrange
        RepresentativeLegalEntityForm expected = new RepresentativeLegalEntityForm();

        /// Act
        RepresentativeLegalEntityForm result = representativeLegalEntityFactory.convertFrom(null);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertFrom_filledCore_returnsFilledForm()
    {
        /// Arrange
        Representative core = new Representative();
        core = CoreDomainHelper.coreRepresentativeStub();
        core.setRepresentativeKind(RepresentativeKind.OTHER);
        core.setKind(PersonKind.LEGAL_ENTITY);
        core.setLegalForm("some name of legal entity");
        
        List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
        PersonIdentifier personIdentifier = new PersonIdentifier();
        personIdentifier.setIdentifierKindCode("VAT Number");
        personIdentifier.setValue("some tax no");
        identifiers.add(personIdentifier);
        core.setIdentifiers(identifiers);
        
        PersonName personName = new PersonName();
        personName.setOrganizationName("some name of legal entity");
        core.setName(personName);
        
        RepresentativeLegalEntityForm expected = new RepresentativeLegalEntityForm();
        expected = CoreDomainHelper.formRepresentativeStub(expected);
        expected.setBusinessVatNumber("some tax no");
        expected.setLegalForm("some name of legal entity");
        expected.setNameOfLegalEntity("some name of legal entity");

        /// Act
        RepresentativeLegalEntityForm result = representativeLegalEntityFactory.convertFrom(core);

        /// Assert
        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getBusinessVatNumber(), result.getBusinessVatNumber());
        assertEquals(expected.getTaxIdNumber(), result.getTaxIdNumber());
        //assertEquals(expected, result);
    }
}
