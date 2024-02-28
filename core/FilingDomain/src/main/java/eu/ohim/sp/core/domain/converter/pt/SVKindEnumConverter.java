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

import eu.ohim.sp.core.domain.patent.SVKind;
import org.dozer.DozerConverter;

public class SVKindEnumConverter extends DozerConverter<SVKind, eu.ohim.sp.filing.domain.pt.SVKind> {
    public SVKindEnumConverter() {
        super(SVKind.class, eu.ohim.sp.filing.domain.pt.SVKind.class);
    }

    @Override
    public eu.ohim.sp.filing.domain.pt.SVKind convertTo(SVKind core, eu.ohim.sp.filing.domain.pt.SVKind ext) {
        eu.ohim.sp.filing.domain.pt.SVKind toReturn;
        switch (core) {
            case SORT: {
                toReturn = eu.ohim.sp.filing.domain.pt.SVKind.SORT;
                break;
            }
            case BREED: {
                toReturn = eu.ohim.sp.filing.domain.pt.SVKind.BREED;
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
    public SVKind convertFrom(eu.ohim.sp.filing.domain.pt.SVKind ext, SVKind core) {
        SVKind toReturn = null;
        if (ext.equals(eu.ohim.sp.filing.domain.pt.SVKind.BREED)) {
            toReturn = SVKind.BREED;
        } else if (ext.equals(eu.ohim.sp.filing.domain.pt.SVKind.SORT)) {
            toReturn = SVKind.SORT;
        }

        return toReturn;
    }
}
