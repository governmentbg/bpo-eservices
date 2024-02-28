/*
 *  FspDomain:: EntitlementKindEnumConverter 12/11/13 18:02 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.application.EntitlementKind;
import org.dozer.DozerConverter;

/**
 * @author ionitdi
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
            case ACCORDING_TO_ASSOCIATION_TO_COMPANY:
            {
                toReturn = "According to association of a company";
                break;
            }
            case DESIGN_IS_NOT_OFFICIARY:
            {
                toReturn = "Design is not officiary";
                break;
            }
            case DESIGN_IS_OFFICIARY:
            {
                toReturn = "Design is officiary";
                break;
            }
            case DUE_TO_SUCESSION:
            {
                toReturn = "Due to succession";
                break;
            }
            case NOT_APPLICANTS_WITH_WAIVED:
            {
                toReturn = "Not applicant with waived";
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
        if (ext.equals("Design is officiary"))  {
            toReturn = EntitlementKind.DESIGN_IS_OFFICIARY;
        } else if (ext.equals("Design is not officiary")) {
            toReturn = EntitlementKind.DESIGN_IS_NOT_OFFICIARY;
        } else if (ext.equals("Not applicant with waived")) {
            toReturn = EntitlementKind.NOT_APPLICANTS_WITH_WAIVED;
        } else if (ext.equals("Due to succession")) {
            toReturn = EntitlementKind.DUE_TO_SUCESSION;
        } else if (ext.equals("According to association of a company")) {
            toReturn = EntitlementKind.ACCORDING_TO_ASSOCIATION_TO_COMPANY;
        } else if (ext.equals("Transfer of rights")) {
            toReturn = EntitlementKind.TRANSFER_OF_RIGHTS;
        } else if (ext.equals("Other grounds")) {
            toReturn = EntitlementKind.OTHER_GROUNDS;
        }

        return toReturn;
    }
}
