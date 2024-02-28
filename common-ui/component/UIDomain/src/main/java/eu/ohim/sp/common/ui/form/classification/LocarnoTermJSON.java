/*******************************************************************************
 * * $Id:: LocarnoTermJSON.java                      $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.classification;

import java.util.Collection;

/**
 * The Locarno Term JSON.
 */
public class LocarnoTermJSON {

	private String id;
	private String locarnoClass;
	private String locarnoSubclass;
	private String locarnoClassSubclass;
	private String indication;
	private String validationState;
	private String termStatus;
	private Collection<LocarnoTermJSON> relatedTerms;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocarnoClass() {
		return locarnoClass;
	}

	public void setLocarnoClass(String locarnoClass) {
		this.locarnoClass = locarnoClass;
	}

	public String getLocarnoSubclass() {
		return locarnoSubclass;
	}

	public void setLocarnoSubclass(String locarnoSubclass) {
		this.locarnoSubclass = locarnoSubclass;
	}

	public String getLocarnoClassSubclass() {
		return locarnoClassSubclass;
	}

	public void setLocarnoClassSubclass(String locarnoClassSubclass) {
		this.locarnoClassSubclass = locarnoClassSubclass;
	}

	public String getIndication() {
		return indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	public String getValidationState() {
		return validationState;
	}

	public void setValidationState(String validationState) {
		this.validationState = validationState;
	}

	public String getTermStatus() {
		return termStatus;
	}

	public void setTermStatus(String termStatus) {
		this.termStatus = termStatus;
	}

	public Collection<LocarnoTermJSON> getRelatedTerms() {
		return relatedTerms;
	}

	public void setRelatedTerms(Collection<LocarnoTermJSON> relatedTerms) {
		this.relatedTerms = relatedTerms;
	}

}
