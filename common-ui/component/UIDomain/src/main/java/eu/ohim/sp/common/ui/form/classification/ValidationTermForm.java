/*******************************************************************************
 * * $Id:: ValidationTermForm.java 49264 2012-10-29 13:23:34Z karalch            $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.classification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ValidationTermForm implements Serializable {

    private static final long serialVersionUID = 1L;

	private Collection<TermForm> termForms;
	
	private Boolean validated;
	

	public ValidationTermForm() {
		termForms = new ArrayList<TermForm>();
		validated = Boolean.FALSE;
	}

	public ValidationTermForm(Boolean validated) {
		termForms = new ArrayList<TermForm>();
		this.validated = validated;
	}

	public Boolean getValidated() {
		return validated;
	}



	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

	/**
	 * Method that returns the termForms
	 * @return the termForms
	 */
	public Collection<TermForm> getTermForms() {
		return termForms;
	}

	/**
	 * Method that sets the termForms
	 * @param termForms the termForms to set
	 */
	public void setTermForms(Collection<TermForm> termForms) {
		this.termForms = termForms;
	}
	
	public void addTermForm(TermForm termForm) {
		termForms.add(termForm);
	}
	
	
	
}
