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
public class LocarnoClassHeading implements Serializable {

	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** The class code. */
	@JsonProperty("ClassCode")
	private Integer classCode;

	/** The class description. */
	@JsonProperty("ClassDescription")
	private String classDescription;

	/** The Locarno Subclasses. */
	@JsonProperty("LocarnoSubClasses")
	private List<LocarnoSubClassHeading> locarnoSubClasses;

	/**
	 * gets the class code.
	 *
	 * @return
	 */
	public Integer getClassCode() {
		return classCode;
	}

	/**
	 * sets the class code.
	 *
	 * @param classCode
	 */
	public void setClassCode(Integer classCode) {
		this.classCode = classCode;
	}

	/**
	 * gets the class description.
	 *
	 * @return
	 */
	public String getClassDescription() {
		return classDescription;
	}

	/**
	 * sets the class description.
	 *
	 * @param classDescription
	 */
	public void setClassDescription(String classDescription) {
		this.classDescription = classDescription;
	}

	/**
	 * gets the subclasses.
	 *
	 * @return
	 */
	public List<LocarnoSubClassHeading> getLocarnoSubClasses() {
		return locarnoSubClasses;
	}

	/**
	 * sets the subclasses.
	 *
	 * @param locarnoSubClasses
	 */
	public void setLocarnoSubClasses(List<LocarnoSubClassHeading> locarnoSubClasses) {
		this.locarnoSubClasses = locarnoSubClasses;
	}
}
