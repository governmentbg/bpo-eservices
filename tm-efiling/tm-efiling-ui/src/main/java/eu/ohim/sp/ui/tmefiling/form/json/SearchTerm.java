/*******************************************************************************
 * * $Id:: TermForm.java 113879 2013-04-24 07:34:32Z karalch                     $
 * *       . * .
 * *     * RRRR  *    Copyright � 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.form.json;

import java.util.List;

/**
 * The Class SearchTerm.
 */
public class SearchTerm implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8674114308107994173L;
	
	/** The parents. */
	private List<ConceptForm> parents;
	
	/** The results. */
	private List<TermResult> results;
	
	/** The total results. */
	private Integer totalResults;
	
	/** The page size. */
	private Integer pageSize;
	
	/**
	 * Gets the parents.
	 *
	 * @return the parents
	 */
	public List<ConceptForm> getParents() {
		return parents;
	}
	
	/**
	 * Sets the parents.
	 *
	 * @param parents the new parents
	 */
	public void setParents(List<ConceptForm> parents) {
		this.parents = parents;
	}
	
	/**
	 * Gets the results.
	 *
	 * @return the results
	 */
	public List<TermResult> getResults() {
		return results;
	}
	
	/**
	 * Sets the results.
	 *
	 * @param results the new results
	 */
	public void setResults(List<TermResult> results) {
		this.results = results;
	}
	
	/**
	 * Gets the page size.
	 *
	 * @return the page size
	 */
	public Integer getPageSize() {
		return pageSize;
	}
	
	/**
	 * Sets the page size.
	 *
	 * @param pageSize the new page size
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * Gets the total results.
	 *
	 * @return the total results
	 */
	public Integer getTotalResults() {
		return totalResults;
	}
	
	/**
	 * Sets the total results.
	 *
	 * @param totalResults the new total results
	 */
	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}
}
