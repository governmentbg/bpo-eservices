/*******************************************************************************
 * * $Id:: StubForm.java 49264 2012-10-29 13:23:34Z karalch                      $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.util;

import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * @author ionitdi
 */
public class StubForm extends AbstractImportableForm
{
    private volatile int hashCode = 0;

    @Override
    public AbstractImportableForm clone() throws CloneNotSupportedException
    {
        StubForm stub = new StubForm();
        stub.setId(id);
        return stub;
    }

    @Override
    public AvailableSection getAvailableSectionName()
    {
        return null;
    }

    @Override
    public boolean equals(Object other)
    {
        if(!(other instanceof StubForm))
        {
            return false;
        }
        StubForm otherStubForm = (StubForm)other;

        if(this.getId() == otherStubForm.getId())
        {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        final int multiplier = 17;
        if(hashCode == 0)
        {
            int code = 133;

            if(id != null)
            {
                code = multiplier * code + id.hashCode();
            }

            hashCode = code;
        }
        return hashCode;
    }
}
