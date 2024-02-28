/*******************************************************************************
 * * $Id:: UIFactory.java 49264 2012-10-29 13:23:34Z karalch                     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

/**
 * Interface that is used for Front-End POJO objects
 * to convert to Core Domain Objects
 * @author karalch
 *
 * @param <V> the Core Domain object
 * @param <T> the Front-End Form object
 */
public interface UIFactory<V,T> {

	/**
	 * Interface that is used to convert from 
	 * Form object to Core Domain Object
	 * @param form the form object
	 * @return a Core Domain Object 
	 */
	V convertTo(T form);
	
	/**
	 * Interface that is used to convert from 
	 * Core Domain Object to Form object 
	 * @param core a Core Domain Object 
	 * @return the form object
	 */
	T convertFrom(V core);
}
