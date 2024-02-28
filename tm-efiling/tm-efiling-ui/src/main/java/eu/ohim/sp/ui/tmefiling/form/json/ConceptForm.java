package eu.ohim.sp.ui.tmefiling.form.json;

import java.io.Serializable;

/**
 * The Class ConceptForm.
 */
public class ConceptForm implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8674114308107994173L;

	/** The description. */
	private String description;
	
	/** The id. */
	private String id;
	
	/** The level. */
	private Integer level;
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * Gets the level.
	 *
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}
	
	/**
	 * Sets the level.
	 *
	 * @param level the new level
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
}