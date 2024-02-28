/*******************************************************************************
 * * $Id:: TermsDecoder.java 14329 2012-10-29 13:02:02Z karalch $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package org.milyn.javabean.decoders;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.milyn.javabean.DataDecodeException;
import org.milyn.javabean.DataDecoder;
import org.milyn.javabean.DecodeType;

import eu.ohim.sp.external.domain.classification.ClassificationTerm;

/**
 * Smooks custom decoder type to return a List of Classification terms from a String with the terms separated by semi
 * colon.
 */
@DecodeType(TermsDecoder.class)
public class TermsDecoder implements DataDecoder {

    /*
     * (non-Javadoc)
     * @see org.milyn.javabean.DataDecoder#decode(java.lang.String)
     */
    @Override
    public Object decode(String data) throws DataDecodeException {
        String[] terms = StringUtils.split(data, ";");
        List<ClassificationTerm> toReturn = new ArrayList<ClassificationTerm>();
        for (int i = 0; i < terms.length; i++) {
            String term = terms[i];
            ClassificationTerm classificationTerm = new ClassificationTerm();
            classificationTerm.setTermText(term);
            toReturn.add(classificationTerm);
        }
        return toReturn;
    }
}
