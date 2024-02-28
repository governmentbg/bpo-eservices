/*
 *  FspDomain:: TransactionInformation 31/10/13 17:37 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter.ds;

/**
 * @author ionitdi
 */
public final class TransactionInformation {

    private TransactionInformation() {}

    public static final String DS_XSD_VERSION = "1.0";
    public static final String REQUEST_SOFTWARE_NAME = "DS e-Filing";
    public static final String REQUEST_SOFTWARE_VERSION = "0.01";
    public static final String DS_FORM_NAME = "Design Application";
    
    public static final String ES_REQUEST_SOFTWARE_NAME = "e-Services";
}
