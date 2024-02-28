/**
 *  $Id$: LocarnoInformation.java
 *       . * .
 *     * RRRR  *    Copyright (c) 2015 OHIM: Office for Harmonization
 *   .   RR  R   .  in the Internal Market (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 */
package eu.ohim.sp.common.ui.form.classification;

import java.util.Set;

/**
 * The type Locarno information.
 */
public class LocarnoInformation {

	/**
	 * The Num designs.
	 */
	private int numDesigns;

	/**
	 * The Draft open.
	 */
	private Boolean draftOpen;

	/**
	 * The All locarno classes.
	 */
	private Set<Integer> allLocarnoClasses;
	/**
	 * The Designs locarno classes.
	 */
	private Set<Integer> designsLocarnoClasses;

	/**
	 * The Allowed locarno classes.
	 */
	private Set<Integer> allowedLocarnoClasses;

	/**
	 * Sets all locarno classes.
	 *
	 * @param allLocarnoClasses the all locarno classes
	 */
	public void setAllLocarnoClasses(Set<Integer> allLocarnoClasses) {
		this.allLocarnoClasses = allLocarnoClasses;
	}

	/**
	 * Sets designs locarno classes.
	 *
	 * @param designsLocarnoClasses the designs locarno classes
	 */
	public void setDesignsLocarnoClasses(Set<Integer> designsLocarnoClasses) {
		this.designsLocarnoClasses = designsLocarnoClasses;
	}

	/**
	 * Gets all locarno classes.
	 *
	 * @return the all locarno classes
	 */
	public Set<Integer> getAllLocarnoClasses() {

		return allLocarnoClasses;
	}

	/**
	 * Gets designs locarno classes.
	 *
	 * @return the designs locarno classes
	 */
	public Set<Integer> getDesignsLocarnoClasses() {
		return designsLocarnoClasses;
	}

	/**
	 * Gets num designs.
	 *
	 * @return the num designs
	 */
	public int getNumDesigns() {
		return numDesigns;
	}

	/**
	 * Sets num designs.
	 *
	 * @param numDesigns the num designs
	 */
	public void setNumDesigns(int numDesigns) {
		this.numDesigns = numDesigns;
	}

	/**
	 * Gets allowed locarno classes.
	 *
	 * @return the allowed locarno classes
	 */
	public Set<Integer> getAllowedLocarnoClasses() {
		return allowedLocarnoClasses;
	}

	/**
	 * Sets allowed locarno classes.
	 *
	 * @param allowedLocarnoClasses the allowed locarno classes
	 */
	public void setAllowedLocarnoClasses(Set<Integer> allowedLocarnoClasses) {
		this.allowedLocarnoClasses = allowedLocarnoClasses;
	}

	/**
	 * Is draft open.
	 *
	 * @return the boolean
	 */
	public Boolean getDraftOpen() {
		return draftOpen;
	}

	/**
	 * Sets draft open.
	 *
	 * @param draftOpen the draft open
	 */
	public void setDraftOpen(Boolean draftOpen) {
		this.draftOpen = draftOpen;
	}
}
