/*******************************************************************************
 * * $Id::                                                                       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.service.interfaces;

import eu.ohim.sp.common.ui.flow.section.TrademarkFlowBean;

/**
 * Delegation service that calls the core service to validate, file, save and
 * load an application
 * 
 * @author karalch
 * 
 */
public interface ApplicationServiceInterface extends eu.ohim.sp.common.ui.service.interfaces.ApplicationServiceInterface<TrademarkFlowBean> {

    String loadApplicationLocally(byte[] data);

    TrademarkFlowBean loadApplicationLocally(String provisionalId);

    String fileApplication(TrademarkFlowBean flowBean);

    String providePaymentID(String filingNumber);

}
