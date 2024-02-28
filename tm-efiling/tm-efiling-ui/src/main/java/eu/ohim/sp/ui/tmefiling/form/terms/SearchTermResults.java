package eu.ohim.sp.ui.tmefiling.form.terms;

import java.io.Serializable;
import java.util.List;

/**
 * The Class SearchTermResults.
 */
public class SearchTermResults implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The terms. */
	private List<TermForm> terms;
	
	/** The total results. */
	private int totalResults;
	
	/**
	 * Instantiates a new search term results.
	 *
	 * @param terms the terms
	 * @param totalResults the total results
	 */
	public SearchTermResults(List<TermForm> terms, int totalResults) {
		this.terms = terms;
		this.totalResults = totalResults;
	}

	/**
	 * Gets the terms.
	 *
	 * @return the terms
	 */
	public List<TermForm> getTerms() {
		return terms;
	}

	/**
	 * Sets the terms.
	 *
	 * @param terms the new terms
	 */
	public void setTerms(List<TermForm> terms) {
		this.terms = terms;
	}

	/**
	 * Gets the total results.
	 *
	 * @return the total results
	 */
	public int getTotalResults() {
		return totalResults;
	}

	/**
	 * Sets the total results.
	 *
	 * @param totalResults the new total results
	 */
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	
}
