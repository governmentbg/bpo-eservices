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

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by estevca on 08/04/2015.
 */
public class ClassificationHeading implements Serializable {

	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** The language. */
	@JsonProperty("Language")
	private String language;

	/** The classifications. */
	@JsonProperty("Classification")
	private List<LocarnoClassHeading> classification;

	/**
	 * gets the language.
	 * 
	 * @return
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * sets the language.
	 * 
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * gets the classification.
	 * 
	 * @return
	 */
	public List<LocarnoClassHeading> getClassification() {
		return classification;
	}

	/**
	 * sets the classification.
	 * 
	 * @param classification
	 */
	public void setClassification(List<LocarnoClassHeading> classification) {
		this.classification = classification;
	}
}
