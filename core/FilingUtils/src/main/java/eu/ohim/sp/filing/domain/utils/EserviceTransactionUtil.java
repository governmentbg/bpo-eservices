/*
 *  FspDomain:: EserviceTransactionUtil 15/11/13 15:58 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.filing.domain.utils;

import eu.ohim.sp.core.domain.design.DSeServiceApplication;
import eu.ohim.sp.core.domain.patent.PTeServiceApplication;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import eu.ohim.sp.external.application.TransactionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author karalch
 */
public class EserviceTransactionUtil implements TransactionUtil {

    /**
     * Logger of {@see TransactionUtil}
     */
    private static final Logger LOGGER = Logger.getLogger(EserviceTransactionUtil.class);

    private TransactionUtil trademarkUtil;
    private TransactionUtil designUtil;
    private TransactionUtil patentUtil;
    private TransactionUtil objectlessUtil;

    public EserviceTransactionUtil(TransactionUtil trademarkUtil, TransactionUtil designUtil, TransactionUtil patentUtil, TransactionUtil objectlessUtil) {
        this.trademarkUtil = trademarkUtil;
        this.designUtil = designUtil;
        this.patentUtil = patentUtil;
        this.objectlessUtil = objectlessUtil;
    }

    @Override
    public IPApplication generateIPApplication(byte[] byteArray, String typeApplication, boolean mapAttachments) {
        IPApplication ipApplication = null;

        if (StringUtils.startsWith(typeApplication, "TM")) {
            ipApplication = trademarkUtil.generateIPApplication(byteArray, typeApplication, mapAttachments);
        } else if (StringUtils.startsWith(typeApplication, "DS")) {
            ipApplication = designUtil.generateIPApplication(byteArray, typeApplication, mapAttachments);
        } else if (StringUtils.startsWith(typeApplication, "PT") || StringUtils.startsWith(typeApplication, "UM")
                || StringUtils.startsWith(typeApplication, "EP") || StringUtils.startsWith(typeApplication, "SV")
                || StringUtils.startsWith(typeApplication, "SPC")) {
            ipApplication = patentUtil.generateIPApplication(byteArray, typeApplication, mapAttachments);
        } else if(StringUtils.startsWith(typeApplication, "OL")){
            ipApplication = objectlessUtil.generateIPApplication(byteArray, typeApplication, mapAttachments);
        }

        return ipApplication;
    }

    @Override
    public byte[] generateByte(IPApplication application, String typeApplication, boolean mapAttachments) {
        byte[] byteArray;

        if (application instanceof TMeServiceApplication) {
            byteArray = trademarkUtil.generateByte(application, typeApplication, mapAttachments);
        } else if (application instanceof DSeServiceApplication) {
            byteArray = designUtil.generateByte(application, typeApplication, mapAttachments);
        } else if (application instanceof PTeServiceApplication){
            byteArray = patentUtil.generateByte(application, typeApplication, mapAttachments);
        } else {
            byteArray = objectlessUtil.generateByte(application, typeApplication, mapAttachments);
        }

        return byteArray;
    }


}
