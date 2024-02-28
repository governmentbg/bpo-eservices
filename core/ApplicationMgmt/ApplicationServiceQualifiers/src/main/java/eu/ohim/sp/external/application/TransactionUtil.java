/*
 *  ApplicationClientServiceInterface:: TransactionUtil 04/11/13 12:30 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.external.application;

import eu.ohim.sp.core.domain.trademark.IPApplication;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 14/08/13
 * Time: 18:42
 * To change this template use File | Settings | File Templates.
 */
public interface TransactionUtil {

    IPApplication generateIPApplication(byte[] byteArray, String typeApplication, boolean mapAttachments);

    /**
     * Generates a byte[]
     * @param application The IPApplication used to generate the byte[]
     * @param typeApplication The typeApplication of the application
     * @param mapAttachments If it's true the documents will be converted
     * @return
     */
    byte[] generateByte(IPApplication application, String typeApplication, boolean mapAttachments);

}
