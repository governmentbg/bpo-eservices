/*******************************************************************************
 * * $Id:: SearchResults.java 14329 2012-10-29 13:02:02Z karalch                 $
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


public class SearchResults extends Id implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6095486130630269669L;
	
	private Collection<Term> terms;
	private int totalResults;
	
	public Collection<Term> getTerms() {
		return terms;
	}
	public void setTerms(Collection<Term> terms) {
		this.terms = terms;
	}
	public int getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

}
