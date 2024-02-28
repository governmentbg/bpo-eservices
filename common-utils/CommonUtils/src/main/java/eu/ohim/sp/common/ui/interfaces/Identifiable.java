/*******************************************************************************
 * * $Id:: Identifiable.java 49260 2012-10-29 13:02:02Z karalch                  $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.interfaces;

/**
 * Classes implementing this interface specify that they have an Id.
 * @author ionitdi
 *
 */
public interface Identifiable
{
	/**
	 * Get id
	 * @return id 
	 */
    public String getId();


	/**
	 * Set id
	 * param id 
	 */
    public void setId(String id);
}
