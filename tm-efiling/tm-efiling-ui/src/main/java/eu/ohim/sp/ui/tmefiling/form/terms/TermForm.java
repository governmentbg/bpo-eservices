/*******************************************************************************
 * * $Id:: TermForm.java 293593 2017-09-29 16:36:45Z marinca                     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.form.terms;

import eu.ohim.sp.ui.tmefiling.form.json.ConceptForm;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds a concept of Goods and Services term.
 */
public class TermForm implements java.io.Serializable, Comparable<TermForm>  {

	/**
	 * Serial version of the class.
	 */
	private static final long serialVersionUID = 1L;
	
	/** The id class. */
	private String idClass;
	
	/** The description. */
	private String description;
	
	/** The scope availabilty. */
	private boolean scopeAvailabilty;
	
	/** The imported nice class heading. */
	private boolean importedNiceClassHeading;
	
	/** The parent ids. */
	private List<ConceptForm> parentIds;
	
	/** The error. */
	private ErrorType error;
	
	/** The related terms. */
	private List<String> relatedTerms;
	
	/** The harm concept. */
	private String harmConcept;
	
	/** The identifier. */
	private String identifier;
	
	/** The taxonomy path. */
	private String taxonomyPath;
	
	/** The generated. */
	private boolean generated;

	/**
	 * Instantiates a new term form.
	 */
	public TermForm() {
	}

    /**
     * Creates a new term object with the given class id and description.
     *
     * @param idClass     the class id to which this term is part of.
     * @param description the wording of the term.
     */
    public TermForm(String idClass, String description) {
		this.idClass = idClass;
		this.description = description;
	}
	
	/**
	 * Method that returns the idClass.
	 *
	 * @return the idClass
	 */
	public String getIdClass() {
		return idClass;
	}
	
	/**
	 * Method that sets the idClass.
	 *
	 * @param idClass the idClass to set
	 */
	public void setIdClass(String idClass) {
		this.idClass = idClass;
	}
	
	/**
	 * Method that returns the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * The term is concept if it is false.
	 *
	 * @return false if the term is a concept
	 */
	public boolean isScopeAvailabilty() {
		return scopeAvailabilty;
	}

	/**
	 * Sets the scope of the term.
	 *
	 * @param scopeAvailabilty the new scope availabilty
	 */
	public void setScopeAvailabilty(boolean scopeAvailabilty) {
		this.scopeAvailabilty = scopeAvailabilty;
	}

	/**
	 * Method that sets the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * It returns true if only the term is the same as on Nice Class Heading.
	 *
	 * @return true if only the term is the same as on Nice Class Heading
	 */
	public boolean isImportedNiceClassHeading() {
		return importedNiceClassHeading;
	}

	/**
	 * This will be set as true if only the term is the same as on Nice Class Heading.
	 *
	 * @param importedNiceClassHeading the new imported nice class heading
	 */
	public void setImportedNiceClassHeading(boolean importedNiceClassHeading) {
		this.importedNiceClassHeading = importedNiceClassHeading;
	}

	/**
	 * Gets the parent ids.
	 *
	 * @return the parent ids
	 */
	public List<ConceptForm> getParentIds() {
		if (parentIds == null) {
			parentIds = new ArrayList<ConceptForm>();
		}
		return parentIds;
	}

	/**
	 * Sets the parent ids.
	 *
	 * @param parentIds the new parent ids
	 */
	public void setParentIds(List<ConceptForm> parentIds) {
		this.parentIds = parentIds;
	}

	/**
	 * Gets the related terms.
	 *
	 * @return the related terms
	 */
	public List<String> getRelatedTerms() {
		return relatedTerms;
	}

	/**
	 * Sets the related terms.
	 *
	 * @param related the related terms to set
	 */
	public void setRelatedTerms(List<String> related) {
		this.relatedTerms = related;
	}

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public ErrorType getError() {
		return error;
	}

	/**
	 * Sets the error.
	 *
	 * @param error the error to set
	 */
	public void setError(ErrorType error) {
		this.error = error;
	}

    /**
     * If the term is in the Harmonized DB, this field is filled with its Harmonized Id.
     * Check EuroClass documentation <a href=https://it.oami.europa.eu/wiki/EuroClass>here</a> and
     * <a href=http://tm.itawiki.org/wiki/EuroClass_Main_Page>here</a>.
     *
     * @return the harmonized id of the concept if it is in the Harmonized DB. Null otherwise.
     */
    public String getHarmConcept() {
        return harmConcept;
    }

    /**
     * Sets the Harmonized Id for the term if it is in the Harmonized DB.
     * Check EuroClass documentation <a href=https://it.oami.europa.eu/wiki/EuroClass>here</a> and
     * <a href=http://tm.itawiki.org/wiki/EuroClass_Main_Page>here</a>.
     *
     * @param externalReference the harmonized id of the concept if it is in the Harmonized DB.
     */
    public void setHarmConcept(String externalReference) {
        this.harmConcept = externalReference;
    }

    /**
     * If the term is in the Harmonized DB, this field is filled with its taxonomy path.
     * Check EuroClass documentation <a href=https://it.oami.europa.eu/wiki/EuroClass>here</a> and
     * <a href=http://tm.itawiki.org/wiki/EuroClass_Main_Page>here</a>.
     *
     * @return the taxonomy path the concept if it is in the Harmonized DB. Null otherwise.
     */
    public String getTaxonomyPath() {
        return taxonomyPath;
    }

    /**
     * Sets the taxonomy path for the term if it is in the Harmonized DB.
     * Check EuroClass documentation <a href=https://it.oami.europa.eu/wiki/EuroClass>here</a> and
     * <a href=http://tm.itawiki.org/wiki/EuroClass_Main_Page>here</a>.
     *
     * @param taxonomyPath the taxonomy path of the concept if it is in the Harmonized DB.
     */
    public void setTaxonomyPath(String taxonomyPath) {
        this.taxonomyPath = taxonomyPath;
    }

    /**
     * Returns whether the term is a generated term or not. 
     *
     * @return true, if is generated
     */
	public boolean isGenerated() {
		return generated;
	}

	/**
	 * Set if the term is generated or not.
	 *
	 * @param generated the new generated
	 */
	public void setGenerated(boolean generated) {
		this.generated = generated;
	}
	
	/**
	 * Gets the term identifier.
	 *
	 * @return the identifier
	 */
    public String getIdentifier() {
		return identifier;
	}

    /**
     * Sets the term identifier.
     *
     * @param identifier the new identifier
     */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(description).append(idClass).toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		TermForm other = (TermForm) obj;

		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (idClass == null) {
			if (other.idClass != null) {
				return false;
			}
		} else if (!idClass.equals(other.idClass)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(TermForm o) {
		if ((o!=null) && (this!=null)) {
			return this.hashCode() - o.hashCode();
		}
		return 0;
	}
}
