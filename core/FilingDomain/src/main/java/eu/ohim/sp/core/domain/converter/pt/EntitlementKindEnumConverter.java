/*
 *  FspDomain:: EntitlementKindEnumConverter 12/11/13 18:02 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter.pt;

import eu.ohim.sp.core.domain.application.EntitlementKind;
import org.dozer.DozerConverter;

/**
 * Copied from @author ionitdi
 */
public class EntitlementKindEnumConverter extends DozerConverter<EntitlementKind, String>
{
    public EntitlementKindEnumConverter()
    {
        super(EntitlementKind.class, String.class);
    }

    @Override
    public String convertTo(EntitlementKind core, String ext)
    {
        String toReturn;
        switch (core)
        {
            case PATENT_IS_NOT_OFFICIARY:
            {
                toReturn = "Patent is not officiary";
                break;
            }
            case PATENT_IS_OFFICIARY:
            {
                toReturn = "Patent is officiary";
                break;
            }
            case OTHER_GROUNDS:
            {
                toReturn = "Other grounds";
                break;
            }
            case TRANSFER_OF_RIGHTS:
            {
                toReturn = "Transfer of rights";
                break;
            }
            default:
            {
                toReturn = null;
                break;
            }
        }
        return toReturn;
    }

    @Override
    public EntitlementKind convertFrom(String ext, EntitlementKind core)
    {
        EntitlementKind toReturn = null;
        if (ext.equals("Patent is not officiary"))  {
            toReturn = EntitlementKind.PATENT_IS_NOT_OFFICIARY;
        } else if (ext.equals("Patent is officiary")) {
            toReturn = EntitlementKind.PATENT_IS_OFFICIARY;
        } else if (ext.equals("Other grounds")) {
            toReturn = EntitlementKind.OTHER_GROUNDS;
        } else if (ext.equals("Transfer of rights")) {
            toReturn = EntitlementKind.TRANSFER_OF_RIGHTS;
        }

        return toReturn;
    }
}
