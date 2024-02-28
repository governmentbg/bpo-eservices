/*******************************************************************************
 * * $Id:: RepresentativeAssociationFactoryTest.java 113496 2013-04-22 15:03:04Z#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import static org.junit.Assert.assertEquals;

import eu.ohim.sp.core.domain.person.PersonName;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.form.person.RepresentativeAssociationForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.person.RepresentativeKind;

/**
 * @author ionitdi
 */
public class RepresentativeAssociationFactoryTest
{
	@Mock
	private AddressFactory addressFactory;
	
	@Mock
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @InjectMocks
    RepresentativeAssociationFactory representativeAssociationFactory = new RepresentativeAssociationFactory();

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
        Representative result = representativeAssociationFactory.convertTo(null);

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
        RepresentativeAssociationForm form = new RepresentativeAssociationForm();
        form = CoreDomainHelper.formRepresentativeStub(form);
        form.setAssociationId("some assoc id");
        form.setAssociationName("some assoc name");
        form.setName("some assoc name");


        Representative expected = new Representative();
        expected = CoreDomainHelper.coreRepresentativeStub();
        expected.setRepresentativeKind(RepresentativeKind.ASSOCIATION);
        expected.setKind(PersonKind.LEGAL_ENTITY);
        PersonName name = new PersonName();
        name.setOrganizationName(form.getName());
        expected.setName(name);

        /// Act
        Representative result = representativeAssociationFactory.convertTo(form);

        /// Assert
        assertEquals(expected.getKind(), result.getKind());
        assertEquals(expected.getName(), result.getName());
        //assertEquals(expected, result);
    }

    @Test
    public void convertFrom_nullCore_returnsNullForm()
    {
        /// Arrange
        RepresentativeAssociationForm expected = new RepresentativeAssociationForm();

        /// Act
        RepresentativeAssociationForm result = representativeAssociationFactory.convertFrom(null);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertFrom_filledCore_returnsFilledForm()
    {
        /// Arrange
        Representative core = new Representative();
        core = CoreDomainHelper.coreRepresentativeStub();
        core.setRepresentativeKind(RepresentativeKind.ASSOCIATION);

        RepresentativeAssociationForm expected = new RepresentativeAssociationForm();
        expected = CoreDomainHelper.formRepresentativeStub(expected);
        expected.setAssociationId("some assoc id");
        expected.setAssociationName("some assoc name");

        /// Act
        RepresentativeAssociationForm result = representativeAssociationFactory.convertFrom(core);

        /// Assert
        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getOrganization(), result.getOrganization());
        //assertEquals(expected, result);
    }
}
