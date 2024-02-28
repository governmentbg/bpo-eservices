/*******************************************************************************
 * * $Id:: ImportParameters.java 14218 2012-10-26 16:13:14Z villama              $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller.parameters;

import eu.ohim.sp.common.ui.form.AbstractForm;

/**
 * Parameters passed when an Import request is done
 *
 * @author ionitdi
 */
public class ImportParameters extends RequestParameters {

	/**
	 * Maximum entities that are allowed to add to the collection
	 */
	private Integer maximumEntities;
	/**
	 * The name of the collection that will be returned after the
	 * submission.
	 */
	private String collectionName;

	public ImportParameters(Class<? extends AbstractForm> commandClass, String commandName, String formView, Integer maximumEntities,
							String collectionName) {
		super(commandClass, commandName, null, formView);
		this.maximumEntities = maximumEntities;
		this.collectionName = collectionName;
	}

	public Integer getMaximumEntities() {
		return maximumEntities;
	}

	public void setMaximumEntities(Integer maximumEntities) {
		this.maximumEntities = maximumEntities;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
}
