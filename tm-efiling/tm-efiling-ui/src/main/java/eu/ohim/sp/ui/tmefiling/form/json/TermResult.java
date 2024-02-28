package eu.ohim.sp.ui.tmefiling.form.json;

import java.io.Serializable;
import java.util.List;

/**
 * The Class TermResult.
 */
public class TermResult implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8674114308107994173L;

	/** The category. */
	private String cat;
	
	/** The id. */
	private String id;
	
	/** The lang. */
	private String lang;
	
	/** The term. */
	private String term;
	
	/** The scope availability. */
	private boolean scopeAvailability;
	
	/** The subcategories. */
	private List<ConceptForm> subcategories;
	
	
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
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the lang.
	 *
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}
	
	/**
	 * Sets the lang.
	 *
	 * @param lang the new lang
	 */
	public void setLang(String lang) {
		this.lang = lang;
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
	 * Checks if is scope availability.
	 *
	 * @return true, if is scope availability
	 */
	public boolean isScopeAvailability () {
		return scopeAvailability;
	}
	
	/**
	 * Sets the scope availability.
	 *
	 * @param scopeAvailability the new scope availability
	 */
	public void setScopeAvailability(boolean scopeAvailability) {
		this.scopeAvailability = scopeAvailability;
	}
	
	/**
	 * Gets the subcategories.
	 *
	 * @return the subcategories
	 */
	public List<ConceptForm> getSubcategories() {
		return subcategories;
	}
	
	/**
	 * Sets the subcategories.
	 *
	 * @param subcategories the new subcategories
	 */
	public void setSubcategories(List<ConceptForm> subcategories) {
		this.subcategories = subcategories;
	}

}