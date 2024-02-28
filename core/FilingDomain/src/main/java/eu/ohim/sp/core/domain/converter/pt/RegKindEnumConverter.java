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

import eu.ohim.sp.core.domain.patent.RegKind;
import eu.ohim.sp.core.domain.patent.SVKind;
import org.dozer.DozerConverter;

public class RegKindEnumConverter extends DozerConverter<RegKind, eu.ohim.sp.filing.domain.pt.RegKind> {
    public RegKindEnumConverter() {
        super(RegKind.class, eu.ohim.sp.filing.domain.pt.RegKind.class);
    }

    @Override
    public eu.ohim.sp.filing.domain.pt.RegKind convertTo(RegKind core, eu.ohim.sp.filing.domain.pt.RegKind ext) {
        eu.ohim.sp.filing.domain.pt.RegKind toReturn;
        switch (core) {
            case MEDICINE: {
                toReturn = eu.ohim.sp.filing.domain.pt.RegKind.MEDICINE;
                break;
            }
            case PLANT: {
                toReturn = eu.ohim.sp.filing.domain.pt.RegKind.PLANT;
                break;
            }
            default: {
                toReturn = null;
                break;
            }
        }
        return toReturn;
    }

    @Override
    public RegKind convertFrom(eu.ohim.sp.filing.domain.pt.RegKind ext, RegKind core) {
        RegKind toReturn = null;
        if (ext.equals(eu.ohim.sp.filing.domain.pt.RegKind.MEDICINE)) {
            toReturn = RegKind.MEDICINE;
        } else if (ext.equals(eu.ohim.sp.filing.domain.pt.RegKind.PLANT)) {
            toReturn = RegKind.PLANT;
        }

        return toReturn;
    }
}
