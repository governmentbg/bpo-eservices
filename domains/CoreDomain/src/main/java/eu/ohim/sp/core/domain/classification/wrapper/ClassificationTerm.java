/*******************************************************************************
 * * $Id:: ClassificationTerm.java 124359 2013-06-19 17:43:34Z karalch           $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification.wrapper;

import java.io.Serializable;

import eu.ohim.sp.core.domain.id.Id;

public class ClassificationTerm extends Id implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -414664325463647565L;

	private Integer classNum;
	private Integer totalMatches;
	private Integer totalNumber;

	public Integer getClassNum() {
		return classNum;
	}

	public void setClassNum(Integer classNum) {
		this.classNum = classNum;
	}

	public Integer getTotalMatches() {
		return totalMatches;
	}

	public void setTotalMatches(Integer totalMatches) {
		this.totalMatches = totalMatches;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

}
