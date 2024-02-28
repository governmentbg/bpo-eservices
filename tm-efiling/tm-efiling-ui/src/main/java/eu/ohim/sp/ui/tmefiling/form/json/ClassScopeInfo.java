package eu.ohim.sp.ui.tmefiling.form.json;

import org.apache.commons.lang.StringUtils;

/**
 * The Class ClassScopeInfo.
 */
public class ClassScopeInfo implements Comparable<ClassScopeInfo> {

	/** The category. */
	private String cat;
	
	/** The term. */
	private String term;
	
	/** The taxo concept node id. */
	private String taxoConceptNodeId;

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCat() {
		return cat;
	}
	
	/**
	 * Sets the category.
	 *
	 * @param cat the new category
	 */
	public void setCat(String cat) {
		this.cat = cat;
	}
	
	/**
	 * Gets the term.
	 *
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}
	
	/**
	 * Sets the term.
	 *
	 * @param term the new term
	 */
	public void setTerm(String term) {
		this.term = term;
	}
	
	/**
	 * Gets the taxo concept node id.
	 *
	 * @return the taxo concept node id
	 */
	public String getTaxoConceptNodeId() {
		return taxoConceptNodeId;
	}
	
	/**
	 * Sets the taxo concept node id.
	 *
	 * @param taxoConceptNodeId the new taxo concept node id
	 */
	public void setTaxoConceptNodeId(String taxoConceptNodeId) {
		this.taxoConceptNodeId = taxoConceptNodeId;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ClassScopeInfo info) {
		if (StringUtils.isNotBlank(cat)
				&& StringUtils.isNotBlank(info.getCat())) {
			return Integer.valueOf(cat) - Integer.valueOf(info.getCat());
		} else if (StringUtils.isNotBlank(cat)) {
			return Integer.valueOf(cat);
		} else if (StringUtils.isNotBlank(info.getCat())) {
			return Integer.valueOf(info.getCat());
		}
		return 0;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
	        return true;
        }
        if (o == null || getClass() != o.getClass()) {
	        return false;
        }

        ClassScopeInfo that = (ClassScopeInfo) o;

        return !(cat != null ? !cat.equals(that.cat) : that.cat != null);

    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return cat != null ? cat.hashCode() : 0;
    }
}
