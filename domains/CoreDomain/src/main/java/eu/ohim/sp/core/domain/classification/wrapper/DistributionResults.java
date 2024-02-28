/*******************************************************************************
 * * $Id:: DistributionResults.java 14329 2012-10-29 13:02:02Z karalch           $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification.wrapper;

import java.io.Serializable;
import java.util.Collection;

import eu.ohim.sp.core.domain.id.Id;


public class DistributionResults extends Id implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5501312862656935936L;

    private Collection<ClassificationTerm> classificationTerms;

	public Collection<ClassificationTerm> getClassificationTerms() {
		return classificationTerms;
	}

	public void setClassificationTerms(
			Collection<ClassificationTerm> classificationTerms) {
		this.classificationTerms = classificationTerms;
	}
	
}
