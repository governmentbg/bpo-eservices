/*******************************************************************************
 * * $Id:: ApplicantFormTest.java 54183 2013-01-11 12:03:33Z ionitdi             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.contact.CorrespondenceAddressForm;
import eu.ohim.sp.common.ui.util.JavaBeanTester;

import org.junit.Test;

import java.beans.IntrospectionException;

import static org.junit.Assert.assertEquals;

/**
 * @author ionitdi
 */
public class ApplicantFormTest
{
    @Test
    public void form_testProperties() throws IntrospectionException
    {
        JavaBeanTester.test(ApplicantForm.class);
    }

    @Test
    public void removeEmptyCorrespondenceAddresses_removesCorrectly()
    {
        /// Arrange
        ApplicantForm form = new ApplicantForm();
        AddressForm address1 = new AddressForm();
        address1.setCity("some city");
        CorrespondenceAddressForm ca1 = new CorrespondenceAddressForm();
        ca1.setId("1");
        ca1.setAddressForm(address1);

        AddressForm address2 = new AddressForm();
        CorrespondenceAddressForm ca2 = new CorrespondenceAddressForm();
        ca2.setId("2");
        ca2.setAddressForm(address2);

        form.getCorrespondenceAddresses().add(ca1);
        form.getCorrespondenceAddresses().add(ca2);

        /// Act
        form.removeEmptyCorrespondenceAddresses();

        /// Assert
        assertEquals(1, form.getCorrespondenceAddresses().size());
        assertEquals("some city", form.getCorrespondenceAddresses().get(0).getAddressForm().getCity());
    }
}
