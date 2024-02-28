/*******************************************************************************
 * * $Id:: FeeServiceInterface.java 50925 2012-11-15 17:10:35Z karalch           $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.service.interfaces;

import eu.ohim.sp.common.ui.flow.CommonSpFlowBean;

/**
 * The Interface FeeServiceInterface.
 */
public interface FeeServiceInterface {

	/**
	 * Gets the fees information.
	 *
	 * @param flowBean 
	 * @return flowBean object
	 */
	void getFeesInformation(CommonSpFlowBean flowBean);

	/**
	 * Updates fees information.
	 *
	 * @param flowBean 
	 * @return flowBean object updated
	 */
	void updateFeesInformation(CommonSpFlowBean flowBean);

}
