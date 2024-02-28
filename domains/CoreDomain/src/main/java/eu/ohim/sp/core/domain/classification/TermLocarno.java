//@formatter:off
/**
 *  $Id$
 *       . * .
 *     * RRRR  *    Copyright (c) 2015 OHIM: Office for Harmonization
 *   .   RR  R   .  in the Internal Market (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 */
//@formatter:on
package eu.ohim.sp.core.domain.classification;

import java.io.Serializable;
import java.util.List;

/**
 * #DS Class Integration changes.
 * The Extended Class Term.
 * @author Ramittal
 *
 */
public class TermLocarno implements Serializable {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    /** The term text. */
	private String productIndication;

    /** The identifier. */
    private String identifier;

    /** The class Code. */
    private Integer classCode;

    /** The subclass Code. */
    private Integer subclassCode;

    /** Group title flag. */
    private boolean group;
    
    /** Headings flag. */
    private boolean heading;
    
    /**
     * The Related terms.
     */
    private List<TermLocarno> relatedTerms;

    /**
     * gets the product indication.
     *
     * @return product indication
     */
    public String getProductIndication() {
        return productIndication;
    }

    /**
     * sets the product indication.
     *
     * @param productIndication the product indication
     */
    public void setProductIndication(String productIndication) {
        this.productIndication = productIndication;
    }

    /**
     * gets the identifier.
     *
     * @return identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * sets the identifier.
     *
     * @param identifier the identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * gets the class code.
     *
     * @return class code
     */
    public Integer getClassCode() {
        return classCode;
    }

    /**
     * sets the class code.
     *
     * @param classCode the class code
     */
    public void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }

    /**
     * gets the subclass code.
     *
     * @return subclass code
     */
    public Integer getSubclassCode() {
        return subclassCode;
    }

    /**
     * sets the subclass code.
     *
     * @param subclassCode the subclass code
     */
    public void setSubclassCode(Integer subclassCode) {
        this.subclassCode = subclassCode;
    }

    /**
     * Gets related terms.
     *
     * @return the related terms
     */
    public List<TermLocarno> getRelatedTerms() {
        return relatedTerms;
    }

    /**
     * Sets related terms.
     *
     * @param relatedTerms the related terms
     */
    public void setRelatedTerms(List<TermLocarno> relatedTerms) {
        this.relatedTerms = relatedTerms;
    }

	/**
	 * Checks if is group.
	 *
	 * @return true, if is group
	 */
	public boolean isGroup() {
		return group;
	}

	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup(boolean group) {
		this.group = group;
	}

	/**
	 * Checks if is heading.
	 *
	 * @return true, if is heading
	 */
	public boolean isHeading() {
		return heading;
	}

	/**
	 * Sets the heading.
	 *
	 * @param heading the new heading
	 */
	public void setHeading(boolean heading) {
		this.heading = heading;
	}
}
